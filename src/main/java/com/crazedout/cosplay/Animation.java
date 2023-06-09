package com.crazedout.cosplay;

import com.crazedout.cosplay.pojo.ImageBitmap;

import java.util.ArrayList;
import java.util.List;

public abstract class Animation implements Drawable {

    protected boolean visible;
    protected int delay=0;
    private int delayCount;
    protected int anim=0;
    List<CosBitmap> images = new ArrayList<>();
    protected int x,y,width,height;

    public Animation(Map map, Tile t){
        this(t.x,t.y,t.size,t.size);
    }

    public Animation(int x, int y, int width, int height){
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
    }

    public abstract void loadImages(Resources res, List<String> imagesId);

    public void setVisible(boolean visible){
        this.visible=visible;
    }

    public boolean isVisible(){
        return this.visible;
    }

    public int getDelay(){
        return delay;
    }

    public void setDelay(int delay){
        this.delay=delay;
    }

    @Override
    public void draw(CosGraphics g, int x, int y, int width, int height) {
        if(delayCount++<delay) return;
        delayCount=0;
        g.drawImage(images.get(anim),x,y,width,height);
        if(anim>=images.size()){
            anim=0;
        }else{
            anim++;
        }
    }

    @Override
    public void draw(CosGraphics g) {
        this.draw(g,x,y,width,height);
    }

}
