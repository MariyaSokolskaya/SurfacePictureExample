package com.example.admin215.surfacepictureexample;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Admin215 on 15.01.2018.
 */

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    Resources resources;
    Bitmap image;
    float x = 0, y = 0, touchX, touchY;
    float stepX = 0, stepY = 0;
    int kV = 100;
    SurfaceThread thread;
    Paint paint;

    public MySurfaceView(Context context) {
        super(context);
        resources = getResources();
        image = BitmapFactory.decodeResource(resources,R.drawable.cat);
        getHolder().addCallback(this);
        paint = new Paint();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawColor(Color.YELLOW);
        canvas.drawBitmap(image,x,y,paint);
        changeXY();
    }

    void changeXY(){
        x += stepX;
        y += stepY;
        if(Math.abs(x-touchX)<Math.abs(stepX) || Math.abs(y-touchY)<Math.abs(stepY)){
            stepX = 0;
            stepY = 0;
            image = BitmapFactory.decodeResource(resources, R.drawable.cat1);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            touchX = event.getX();
            touchY = event.getY();
            setSteps();
        }
        return true;
    }

    void setSteps(){
        image = BitmapFactory.decodeResource(resources, R.drawable.cat);
        stepX = Math.abs(touchX-x)/kV;
        stepY = Math.abs(touchY-y)/kV;
        if (touchX<x)
            stepX = -stepX;
        if (touchY < y)
            stepY = -stepY;
    }
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        thread = new SurfaceThread(this, getHolder());
        thread.setRunThread(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        thread.setRunThread(false);
        boolean retry = true;
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
