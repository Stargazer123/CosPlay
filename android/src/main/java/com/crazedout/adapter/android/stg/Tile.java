package com.crazedout.adapter.android.stg;

import com.crazedout.adapter.android.*;
import com.crazedout.adapter.android.Canvas;
import com.crazedout.adapter.android.Paint;

import java.awt.*;

public class Tile {

    Rect rect;
    int x,y,width,height;
    int index;
    Bitmap image,bgImage;
    Tile buddyTile;

    boolean wall;

    public Tile(int x, int y, int width, int height, int index){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        rect = new Rect(x,y,x+width,y+height);
        this.index = index;
    }

    public void setBuddyTile(Tile t){
        this.buddyTile = t;
    }

    public Tile getBuddyTile(){
        return this.buddyTile;
    }

    public void setImage(Bitmap img){
        this.image = img;
    }

    public void setBgImage(Bitmap img){
        this.bgImage = img;
    }

    public int getIndex(){
        return this.index;
    }

    public Rect getRect(){
        return (rect = new Rect(x,y,x+width,y+height));
    }

    public RectF getRectF(){
        return new RectF(x,y,x+width,y+height);
    }

    public void draw(Canvas c, Paint paint){
        paint.setColor(Color.GRAY);
        if(wall){
            if(image!=null){
                if(bgImage!=null) Util.drawImage(c,bgImage,x,y,width,height,paint);
                Util.drawImage(c,image,x,y,width,height,paint);
            }else {
                c.drawRect(x, y, x+width, y+height, paint);
            }
        }else {
            if(image!=null) {
                if(bgImage!=null) Util.drawImage(c,bgImage,x,y,width,height,paint);
                Util.drawImage(c,image,x,y,width,height,paint);
            }else{
                c.drawRect(x, y, x+width, y+height, paint);
            }
            /*
            paint.setColor(Color.RED);
            paint.setStyle(Paint.Style.FILL);
            c.drawCircle(x+width/2,y+height/2,6,paint);
            c.drawCircle(x+(width/2)+2,y+(height/2)-2,4,paint);
            c.drawCircle(x+(width/2)-3,y+(height/2)+3,3,paint);
             */
        }

    }

}
