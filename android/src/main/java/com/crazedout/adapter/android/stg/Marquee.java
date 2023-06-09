package com.crazedout.adapter.android.stg;

import com.crazedout.adapter.android.*;
import com.crazedout.adapter.android.Canvas;
import com.crazedout.adapter.android.Paint;

import java.awt.*;

public class Marquee {

    Bitmap splash,cos;
    Rect rect;
    GameView game;
    String text = "CrazedoutSoft 2023";

    public Marquee(GameView view) {
        game = view;
        splash = BitmapFactory.decodeResource(view.getResources(), R.drawable.sankta_titel);
        cos = BitmapFactory.decodeResource(view.getResources(), R.drawable.cos);
    }
    RectF cosRect;
    public RectF getCosRect(){
        return cosRect;
    }

    public void draw(Canvas c, Paint paint){
        int w = game.displayMetrics.widthPixels;
        int h = game.displayMetrics.heightPixels;
        int ratio = splash.getHeight(Util.observer) / splash.getWidth(Util.observer);
        int iw = (int)Math.round((float)w*0.8);
        int ih = (int)(iw * 0.66);
        paint.setColor(Color.BLACK);

        Util.drawImage(c,splash,
                w/2-iw/2,
                60,iw,ih,paint);

        int c_size = game.displayMetrics.heightPixels/5;
        cosRect = new RectF(Util.getRect(w/2-c_size/2,ih+40,
                c_size,c_size));
        Util.drawImage(c,cos,w/2-c_size/2,ih+40,
                c_size,c_size,
                paint);
    }
}
