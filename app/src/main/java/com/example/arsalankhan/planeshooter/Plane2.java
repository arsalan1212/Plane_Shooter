package com.example.arsalankhan.planeshooter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by Arsalan khan on 12/26/2017.
 */

public class Plane2 extends Plane {

    Bitmap plane[] = new Bitmap[10];

    public Plane2(Context context) {
        super(context);

        plane[0] = BitmapFactory.decodeResource(context.getResources(),R.drawable.plane2_1);
        plane[1] = BitmapFactory.decodeResource(context.getResources(),R.drawable.plane2_1);
        plane[2] = BitmapFactory.decodeResource(context.getResources(),R.drawable.plane2_1);
        plane[3] = BitmapFactory.decodeResource(context.getResources(),R.drawable.plane2_1);
        plane[4] = BitmapFactory.decodeResource(context.getResources(),R.drawable.plane2_1);
        plane[5] = BitmapFactory.decodeResource(context.getResources(),R.drawable.plane2_1);
        plane[6] = BitmapFactory.decodeResource(context.getResources(),R.drawable.plane2_1);
        plane[7] = BitmapFactory.decodeResource(context.getResources(),R.drawable.plane2_1);
        plane[8] = BitmapFactory.decodeResource(context.getResources(),R.drawable.plane2_1);
        plane[9] = BitmapFactory.decodeResource(context.getResources(),R.drawable.plane2_1);

        resetPosition();
    }

    @Override
    public void resetPosition() {

        planeX = -(200 + random.nextInt(1500));
        planeY = random.nextInt(400);
        velocity = 5 + random.nextInt(21);
    }

    @Override
    public Bitmap getPlaneBitmap() {
        return plane[planeFrame];
    }

    @Override
    public int getPlaneWidth() {
        return plane[0].getWidth();
    }

    @Override
    public int getPlaneHeight() {
        return plane[0].getHeight();
    }
}
