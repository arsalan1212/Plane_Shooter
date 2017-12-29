package com.example.arsalankhan.planeshooter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by Arsalan khan on 12/29/2017.
 */

public class Explosion {

    Bitmap bitmapExplosion[] = new Bitmap[9];
    int explosionX,explosionY;
    int explosionFrame=0;
    public Explosion(Context context){

        bitmapExplosion[0] = BitmapFactory.decodeResource(context.getResources(),R.drawable.explosion0);
        bitmapExplosion[1] = BitmapFactory.decodeResource(context.getResources(),R.drawable.explosion1);
        bitmapExplosion[2] = BitmapFactory.decodeResource(context.getResources(),R.drawable.explosion2);
        bitmapExplosion[3] = BitmapFactory.decodeResource(context.getResources(),R.drawable.explosion3);
        bitmapExplosion[4] = BitmapFactory.decodeResource(context.getResources(),R.drawable.explosion4);
        bitmapExplosion[5] = BitmapFactory.decodeResource(context.getResources(),R.drawable.explosion5);
        bitmapExplosion[6] = BitmapFactory.decodeResource(context.getResources(),R.drawable.explosion6);
        bitmapExplosion[7] = BitmapFactory.decodeResource(context.getResources(),R.drawable.explosion7);
        bitmapExplosion[8] = BitmapFactory.decodeResource(context.getResources(),R.drawable.explosion8);


    }

    public Bitmap getExplosionBitmap(int Frame){
        return bitmapExplosion[Frame];
    }

    public int getExplosionWidth(){
        return bitmapExplosion[0].getWidth();
    }
    public int getExplosionHeight(){
        return bitmapExplosion[0].getHeight();
    }
}
