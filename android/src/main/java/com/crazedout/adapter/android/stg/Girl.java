package com.crazedout.adapter.android.stg;

import com.crazedout.adapter.android.*;
import com.crazedout.adapter.android.Canvas;
import com.crazedout.adapter.android.Paint;
import com.crazedout.adapter.android.stg.*;

import java.awt.*;

public class Girl extends Ghost {

    Sprite pacMan;
    boolean follow = false;

    public Girl(GameView g, Tile t, Sprite pacMan){
        this(t.x,t.y,t.width,t.height,g);
        this.pacMan = pacMan;
    }

    public Girl(int x, int y, int width, int height, GameView g){
        this.x=x;
        this.y=y;
        this.width=width;
        this.height = height;
        rect = new Rect(x,y,x+width,y+height);
        this.game = g;
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                images.add(loadImage(R.drawable.sex,i,j));
            }
        }
        speed=1;
    }

    public void reloadImages(int index){
        images.clear();
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                images.add(loadImage(index,i,j));
            }
        }
    }

    int z = 0;
    int f = 0;
    int j = 0;
    @Override
    public void move(){
        if((game.map.getMapData() instanceof EndMap) && !game.map.hasSpruta) {
            if ((j++ % 8) == 0) z++;
            if (follow) {
                int space = 400 - z;//58;
                dir = pacMan.dir;
                if (f > space) {
                    x = game.pacTracs.get(f - space).x;
                    y = game.pacTracs.get(f - space).y;
                }
                f++;
            } else {
                super.move();
            }
        }else move2();
    }

    void move2(){
        if(follow){
            int space = 58;
            dir = pacMan.dir;
            if(f>space) {
                x = game.pacTracs.get(f-space).x;
                y = game.pacTracs.get(f-space).y;
            }
            f++;
        }else{
            super.move();
        }
    }

    int anim = 0;
    int n = 0;
    public void draw(Canvas c, Paint paint){
        // Util.drawImage(c,images.get(0),x,y,width,height,paint);
        if(!visible) return;
        int size = game.map.size+8;
        paint.setColor(Color.YELLOW);
        if(images.size()>0 && images.get(0)!=null){
            if(dir==moves.DOWN) Util.drawImage(c,images.get(anim),x-4,y-4,size,size,paint);
            if(dir==moves.LEFT) Util.drawImage(c,images.get(4+anim),x-4,y-4,size,size,paint);
            if(dir==moves.RIGHT) Util.drawImage(c,images.get(8+anim),x-4,y-4,size,size,paint);
            if(dir==moves.UP) Util.drawImage(c,images.get(12+anim),x-4,y-4,size,size,paint);
            if(dir==moves.STOP) Util.drawImage(c,images.get(0),x-4,y-4,size,size,paint);
            if((n++%4)==0 && !game.GAME_OVER) anim++;
            if(anim>3) anim = 0;
        }else {
            c.drawRect(x, y, x+width, y+height,paint);
        }
    }

}
