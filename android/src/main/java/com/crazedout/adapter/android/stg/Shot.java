package com.crazedout.adapter.android.stg;

import com.crazedout.adapter.android.Canvas;
import com.crazedout.adapter.android.Paint;

import java.awt.*;


public class Shot {

    int x,y,speed;
    Sprite.moves dir;
    boolean rpg = false;

    public Shot(int x, int y, Sprite.moves dir, boolean rpg){
        this.rpg = rpg;
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.speed = 12;
    }

    public Shot(int x, int y, Sprite.moves dir){
        this(x,y,dir,false);
    }

    public void advance(){
        switch(dir){
            case LEFT:
                x-=speed;
                break;
            case RIGHT:
                x+=speed;
                break;
            case UP:
                y-=speed;
                break;
            case DOWN:
                y+=speed;
                break;
        }
    }

    public void draw(Canvas c, Paint paint){
        paint.setStyle(Paint.Style.FILL);
        if(rpg) {
            paint.setColor(Color.RED);
            c.drawCircle(x - 4, y - 4, 8, paint);
        }else{
            paint.setColor(Color.BLACK);
            c.drawCircle(x - 2, y - 2, 4, paint);
        }
        advance();
    }

}
