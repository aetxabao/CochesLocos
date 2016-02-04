package com.pmdm.cocheslocos;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Sprite {
    private static final int BMP_ROWS = 1;
    private static final int BMP_COLS = 2;
    private int XSPEED = 5;

    private int x = 0;
    private int y = 0;
    private int xSpeed = 5;
    private Bitmap bmp;
    private GameView canvas;

    private int currentFrame = 0;
    private int width = 0;
    private int height = 0;

    public Sprite(GameView canvas, Bitmap bmp, int speed, int row) {
        this.canvas = canvas;
        this.bmp = bmp;
        xSpeed = XSPEED = speed;
        width = bmp.getWidth() / BMP_COLS;
        height = bmp.getHeight() / BMP_ROWS;
        x = -1*width;
        y = 2+row*(height+2);
    }

    public void update() {
        if (x > canvas.getWidth()) {
            xSpeed = -1 * XSPEED;
        }
        if (x < -1*width) {
            xSpeed = 1 * XSPEED;
        }
        x = x + xSpeed;
        currentFrame = xSpeed<0?0:1;
    }

    public void onDraw(Canvas canvas) {
        int srcX = currentFrame * width;
        int srcY = 0;
        Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
        Rect dst = new Rect(x, y, x+width, y+height);
        canvas.drawBitmap(bmp, src, dst, null);
    }

}