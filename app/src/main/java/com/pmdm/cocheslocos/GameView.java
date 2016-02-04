package com.pmdm.cocheslocos;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private GameLoopThread thread;
    private List<Sprite> sprites = new ArrayList<>();

    private boolean isRunning = false;

    public Handler handler;

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if(msg.arg1==1){
                    Log.d("LOG-GameView", "handleMessage msg 1");
                    isRunning = false;
                }
                return false;
            }
        });
        initiateGameView();
    }

    public void update() {
        for (Sprite sprite : sprites) {
            sprite.update();
        }
    }

    public void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        for (Sprite sprite : sprites) {
            sprite.onDraw(canvas);
        }
    }

    public void startGame() {
        Log.d("LOG-GameView","startGame");
        if (thread == null) {
            thread = new GameLoopThread(this);
            thread.startThread();
            isRunning = true;
        }
    }

    public void stopGame() {
        Log.d("LOG-GameView","stopGame");
        if (thread != null) {
            thread.stopThread();
            thread = null;
        }
    }

    public void initiateGameView(){
        createSprites();
    }

    private void createSprites() {
        int id = R.drawable.car;
        int N = 7;
        Random rnd = new Random();
        for(int i=0;i<N;i++){
            sprites.add(createSprite(id, 10 + rnd.nextInt(10), i));
        }
    }

    private Sprite createSprite(int resource, int speed, int row) {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), resource);
        return new Sprite(this, bmp, speed, row);
    }


    @SuppressLint("ClickableViewAccessibility")
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            Log.d("LOG-GameView","onTouchEvent isRunning="+isRunning);
            if (isRunning){
                stopGame();
            }else{
                startGame();
            }
        }
        return true;
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    public void surfaceCreated(SurfaceHolder holder) {
        startGame();
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        stopGame();
    }
}