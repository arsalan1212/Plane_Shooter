package com.example.arsalankhan.planeshooter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

/**
 * Created by Arsalan khan on 12/26/2017.
 */

public class Plane {
    Bitmap planes[] = new Bitmap[15];
    public int planeX, planeY,planeFrame,velocity;
    Random random;

    public Plane(Context context){

        planes[0] = BitmapFactory.decodeResource(context.getResources(),R.drawable.plane_1);
        planes[1] =BitmapFactory.decodeResource(context.getResources(),R.drawable.plane_2);
        planes[2] =BitmapFactory.decodeResource(context.getResources(),R.drawable.plane_3);
        planes[3] =BitmapFactory.decodeResource(context.getResources(),R.drawable.plane_4);
        planes[4] =BitmapFactory.decodeResource(context.getResources(),R.drawable.plane_5);
        planes[5] =BitmapFactory.decodeResource(context.getResources(),R.drawable.plane_6);
        planes[6] =BitmapFactory.decodeResource(context.getResources(),R.drawable.plane_7);
        planes[7] =BitmapFactory.decodeResource(context.getResources(),R.drawable.plane_8);
        planes[8] =BitmapFactory.decodeResource(context.getResources(),R.drawable.plane_9);
        planes[9] =BitmapFactory.decodeResource(context.getResources(),R.drawable.plane_10);
        planes[10] =BitmapFactory.decodeResource(context.getResources(),R.drawable.plane_11);
        planes[11] =BitmapFactory.decodeResource(context.getResources(),R.drawable.plane_12);
        planes[12] =BitmapFactory.decodeResource(context.getResources(),R.drawable.plane_13);
        planes[13] =BitmapFactory.decodeResource(context.getResources(),R.drawable.plane_14);
        planes[14] =BitmapFactory.decodeResource(context.getResources(),R.drawable.plane_15);


        random = new Random();

        planeFrame =0;
        resetPosition();
    }

    //resetting the planeX, planeY, velocity
    public void resetPosition(){

        planeX = GameView.screenWidth + random.nextInt(1200);
        planeY = random.nextInt(300);
        velocity = 8 + random.nextInt(13);

    }
    public Bitmap getPlaneBitmap(){
        return planes[planeFrame];
    }

    public int getPlaneWidth(){
        return planes[0].getWidth();
    }

    public int getPlaneHeight(){
        return planes[0].getHeight();
    }

}
