package com.example.arsalankhan.planeshooter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by Arsalan khan on 12/26/2017.
 */

public class GameView extends View {

    Bitmap backgroundBitmap,tankBitmap;
    Rect rect;
    static int screenWidth, screenHeight,tankWidth,tankHeight;

    Runnable runnable;
    Handler handler;
    final static long MILLIS_DELAY = 30 ;
    ArrayList<Plane> planeArrayList,plane2Arraylist;
    ArrayList<Missile> missileArrayList;
    ArrayList<Explosion> explosionArraylist;
    private int count=0;
    private SoundPool soundPool;
    int fire=0,pointCollission=0;
    final static int TEXT_SIZE =60;
    Paint scorePaint, lifePaint,lifeTextPaint;
    private int life = 10;



    public GameView(Context context) {
        super(context);
        Display display = ((Activity)getContext()).getWindowManager().getDefaultDisplay();
        Point point =new Point();
        display.getSize(point);

        screenWidth= point.x;
        screenHeight =point.y;
        rect = new Rect(0,0,screenWidth,screenHeight);

        backgroundBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.background);

        tankBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.tank);

        tankWidth = tankBitmap.getWidth();
        tankHeight = tankBitmap.getHeight();

        planeArrayList = new ArrayList<>();
        plane2Arraylist = new ArrayList<>();
        missileArrayList = new ArrayList<>();
        explosionArraylist = new ArrayList<>();

        //creating plane
        for(int i =0 ; i<2; i++){
            //plane 1 object creation
            Plane plane = new Plane(context);
            planeArrayList.add(plane);

            //plane2 object creation
            Plane2 plane2 = new Plane2(context);
            plane2Arraylist.add(plane2);
        }

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                //invalidate will the call the onDraw method over and over again automatically
                invalidate();
            }
        };

        //score paint
        scorePaint = new Paint();
        scorePaint.setColor(Color.WHITE);
        scorePaint.setTextSize(TEXT_SIZE);
        scorePaint.setTextAlign(Paint.Align.LEFT);

        //life paint
        lifePaint = new Paint();
        lifePaint.setColor(Color.parseColor("#E87E04"));

        lifeTextPaint = new Paint();
        lifeTextPaint.setTextSize(TEXT_SIZE);
        lifeTextPaint.setColor(Color.RED);
        lifeTextPaint.setTextAlign(Paint.Align.RIGHT);

        //initializing the sound pool
         soundPool = new SoundPool(3, AudioManager.STREAM_MUSIC,1);

         fire = soundPool.load(getContext(),R.raw.fire,1);
         pointCollission = soundPool.load(getContext(),R.raw.point,1);


    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(backgroundBitmap,null,rect,null);


        for(int i = 0; i < planeArrayList.size(); i++) {

            //creating plane1 animation
            canvas.drawBitmap(planeArrayList.get(i).getPlaneBitmap(), planeArrayList.get(i).planeX, planeArrayList.get(i).planeY, null);

            //incrementing the planeFrame of plane class
            planeArrayList.get(i).planeFrame++;

            planeArrayList.get(i).planeX -= planeArrayList.get(i).velocity;

            //checking if the planeFrame is greater than 14 then reset planeFrame to zero
            if (planeArrayList.get(i).planeFrame > 14) {
                planeArrayList.get(i).planeFrame = 0;
            }
            //checking if the plane is outside of left screen
            if (planeArrayList.get(i).planeX < -planeArrayList.get(i).getPlaneWidth()) {
                //reset the plane velocity,planeX, planeY with random num
                planeArrayList.get(i).resetPosition();
                life--;
                GameOver();
            }

            //--------------------- %%%%%%%%%%%%%%%%%%%%%%% ------------------------
            //creating plane2 animation

            canvas.drawBitmap(plane2Arraylist.get(i).getPlaneBitmap(),plane2Arraylist.get(i).planeX,plane2Arraylist.get(i).planeY,null);

            plane2Arraylist.get(i).planeFrame++;

            plane2Arraylist.get(i).planeX += plane2Arraylist.get(i).velocity;

            if(plane2Arraylist.get(i).planeFrame > 9){
                plane2Arraylist.get(i).planeFrame =0;
            }


            if(plane2Arraylist.get(i).planeX > screenWidth + plane2Arraylist.get(i).getPlaneWidth()){
                plane2Arraylist.get(i).resetPosition();
                life--;
                GameOver();
            }

        }

        //----------------------- %%%%%%%%%%%%%%%%%%%%%%% ------------------------------
        //for explosion animation
        for (int j=0; j < explosionArraylist.size(); j++ ){

            canvas.drawBitmap(explosionArraylist.get(j).getExplosionBitmap(explosionArraylist.get(j).explosionFrame),explosionArraylist.get(j).explosionX,explosionArraylist.get(j).explosionY,null);
            explosionArraylist.get(j).explosionFrame++;

            if(explosionArraylist.get(j).explosionFrame > 8){

                explosionArraylist.remove(j);
            }
        }

        // --------------- %%%%%%%%%%%%%%%%%%%%%%%%% ----------------------
        //for moving the missile up
        for(int i=0 ; i< missileArrayList.size(); i++){

            if(missileArrayList.get(i).y > - missileArrayList.get(i).getMissileHeight()){

                missileArrayList.get(i).y -= missileArrayList.get(i).missileVelocity;
                canvas.drawBitmap(missileArrayList.get(i).bitmapMissile,missileArrayList.get(i).x,missileArrayList.get(i).y,null);

                //detecting missile and plane1 collision
                if(missileArrayList.get(i).x >= planeArrayList.get(0).planeX && (missileArrayList.get(i).x + missileArrayList.get(i).getMissileWidth()) <= (planeArrayList.get(0).planeX + planeArrayList.get(0).getPlaneWidth())
                   && missileArrayList.get(i).y >= planeArrayList.get(0).planeY && missileArrayList.get(i).y <= (planeArrayList.get(0).planeY + planeArrayList.get(0).getPlaneHeight())){

                    //detecting explosion x position
                    Explosion explosion = new Explosion(getContext());

                    explosion.explosionX = planeArrayList.get(0).planeX + planeArrayList.get(0).getPlaneWidth()/2 - explosion.getExplosionWidth()/2;
                    explosion.explosionY = planeArrayList.get(0).planeY + planeArrayList.get(0).getPlaneHeight()/2 - explosion.getExplosionHeight()/2;
                    explosionArraylist.add(explosion);

                    planeArrayList.get(0).resetPosition();
                    count++;
                    missileArrayList.remove(i);

                    CollissionSound();
                }
                else if(missileArrayList.get(i).x >= planeArrayList.get(1).planeX && (missileArrayList.get(i).getMissileWidth()) <= (planeArrayList.get(1).planeX +planeArrayList.get(1).getPlaneWidth())
                        && missileArrayList.get(i).y >= planeArrayList.get(1).planeY && missileArrayList.get(i).y <= (planeArrayList.get(1).planeY + planeArrayList.get(1).getPlaneHeight())){

                    //detecting explosion x and y position
                    Explosion explosion = new Explosion(getContext());
                    explosion.explosionX = planeArrayList.get(1).planeX + planeArrayList.get(1).getPlaneWidth()/2 - explosion.getExplosionWidth()/2;
                    explosion.explosionY = planeArrayList.get(1).planeY + planeArrayList.get(1).getPlaneHeight()/2 - explosion.getExplosionHeight()/2;
                    explosionArraylist.add(explosion);

                    planeArrayList.get(1).resetPosition();
                    count++;
                    missileArrayList.remove(i);

                    CollissionSound();
                }

                //detecting missile and plane2 collision
                else if(missileArrayList.get(i).x >= plane2Arraylist.get(0).planeX && (missileArrayList.get(i).x + missileArrayList.get(i).getMissileWidth()) <= (plane2Arraylist.get(0).planeX + plane2Arraylist.get(0).getPlaneWidth())
                        && missileArrayList.get(i).y >= plane2Arraylist.get(0).planeY && missileArrayList.get(i).y <= (plane2Arraylist.get(0).planeY + plane2Arraylist.get(0).getPlaneHeight())){

                    //detecting explosion x position
                    Explosion explosion = new Explosion(getContext());

                    explosion.explosionX = plane2Arraylist.get(0).planeX + plane2Arraylist.get(0).getPlaneWidth()/2 - explosion.getExplosionWidth()/2;
                    explosion.explosionY = plane2Arraylist.get(0).planeY + plane2Arraylist.get(0).getPlaneHeight()/2 - explosion.getExplosionHeight()/2;
                    explosionArraylist.add(explosion);

                    plane2Arraylist.get(0).resetPosition();
                    count++;
                    missileArrayList.remove(i);

                    CollissionSound();
                }
                else if(missileArrayList.get(i).x >= plane2Arraylist.get(1).planeX && (missileArrayList.get(i).getMissileWidth()) <= (plane2Arraylist.get(1).planeX +plane2Arraylist.get(1).getPlaneWidth())
                        && missileArrayList.get(i).y >= plane2Arraylist.get(1).planeY && missileArrayList.get(i).y <= (plane2Arraylist.get(1).planeY + plane2Arraylist.get(1).getPlaneHeight())){

                    //detecting explosion x and y position
                    Explosion explosion = new Explosion(getContext());
                    explosion.explosionX = plane2Arraylist.get(1).planeX + plane2Arraylist.get(1).getPlaneWidth()/2 - explosion.getExplosionWidth()/2;
                    explosion.explosionY = plane2Arraylist.get(1).planeY + plane2Arraylist.get(1).getPlaneHeight()/2 - explosion.getExplosionHeight()/2;
                    explosionArraylist.add(explosion);

                    plane2Arraylist.get(1).resetPosition();
                    count++;
                    missileArrayList.remove(i);

                    CollissionSound();
                }
            }
            else{
                missileArrayList.remove(i);
            }
        }
        handler.postDelayed(runnable,MILLIS_DELAY);

        //drawing the score text
        canvas.drawText("Score: "+(count * 10),10,TEXT_SIZE,scorePaint);

        //drawing the life rectangle indicator
        canvas.drawRect(screenWidth-110,10,screenWidth-110 + life *10,TEXT_SIZE,lifePaint);
        canvas.drawText("Life",screenWidth-130,TEXT_SIZE,lifeTextPaint);

        //drawing the tank
        canvas.drawBitmap(tankBitmap,(screenWidth/2 - tankWidth/2),(screenHeight - tankHeight),null);
    }

    private void GameOver(){
        //gameover
        if(life == 0){

            Intent intent = new Intent(getContext(),GameOver.class);
            intent.putExtra("score",(count *10));
            getContext().startActivity(intent);
            ((Activity)getContext()).finish();
        }
    }
    private void CollissionSound(){

        if(pointCollission!=0){
            soundPool.play(pointCollission,1,1,1,0,1);

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float touchX  = event.getX();
        float touchY = event.getY();

        if(event.getAction() == MotionEvent.ACTION_DOWN){

            if(touchX >= (screenWidth/2 - tankWidth/2) && touchX <= (screenWidth/2 + tankWidth/2) && touchY >= (screenHeight - tankHeight)){

                if(missileArrayList.size() < 3){
                    Missile missile = new Missile(getContext());
                    missileArrayList.add(missile);

                    if(fire!=0){
                        soundPool.play(fire,1,1,1,0,1);
                    }
                }
            }
        }

        return true;
    }
}
