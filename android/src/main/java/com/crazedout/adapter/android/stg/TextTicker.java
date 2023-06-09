package com.crazedout.adapter.android.stg;

import com.crazedout.adapter.android.*;
import com.crazedout.adapter.android.Canvas;
import com.crazedout.adapter.android.Paint;

import java.awt.*;

public class TextTicker {

    Bitmap image;
    GameView view;
    int x,y;

    public TextTicker(GameView view, int y) {
        this.view=view;
        x=view.displayMetrics.widthPixels;
        this.y=y;
        try{
            image = BitmapFactory.decodeResource(view.getResources(), R.drawable.legend);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public void draw(Canvas c, Paint paint){
        if(image==null) return;
        x-=1;

        c.g.drawImage(image.getImage(),x,y, Util.observer);
        if(x < (image.getWidth(Util.observer)-(image.getWidth(Util.observer)*2))) {
            x = view.getWidth();
        }
    }
}
