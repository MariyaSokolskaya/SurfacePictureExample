package com.example.admin215.surfacepictureexample;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by Admin215 on 15.01.2018.
 */

public class SurfaceThread extends Thread {
    MySurfaceView mySurfaceView;
    SurfaceHolder holder;
    boolean runThread = false;
    long nowTime, nextTime, ellapsed;

    public SurfaceThread(MySurfaceView mySurfaceView, SurfaceHolder holder) {
        this.mySurfaceView = mySurfaceView;
        this.holder = holder;
    }

    void setRunThread(boolean runThread){
        this.runThread = runThread;
    }

    @Override
    public void run() {
        nowTime = System.currentTimeMillis();
        while (runThread){
            nextTime = System.currentTimeMillis();
            ellapsed = nextTime - nowTime;
            if(ellapsed > 50){
                Canvas canvas = holder.lockCanvas();
                synchronized (holder){
                    mySurfaceView.draw(canvas);
                }
                holder.unlockCanvasAndPost(canvas);
                nowTime = nextTime;
            }
        }

    }

}
