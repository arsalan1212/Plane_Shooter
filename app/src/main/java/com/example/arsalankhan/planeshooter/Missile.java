package com.example.arsalankhan.planeshooter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by Arsalan khan on 12/27/2017.
 */

public class Missile {

    int x,y;
    int missileVelocity;
    Bitmap bitmapMissile;

    public Missile(Context context){

        bitmapMissile = BitmapFactory.decodeResource(context.getResources(),R.drawable.missile);
        x = GameView.screenWidth/2 - getMissileWidth()/2;
        y = GameView.screenHeight - GameView.tankHeight - getMissileHeight()/2;

        missileVelocity = 50;
    }

    public int getMissileWidth(){
        return bitmapMissile.getWidth();
    }

    public int getMissileHeight(){
        return bitmapMissile.getHeight();
    }
}
