package com.crazedout.adapter.android.stg;

import java.util.ArrayList;
import java.util.Random;

import com.crazedout.adapter.android.Canvas;
import com.crazedout.adapter.android.Paint;
import com.crazedout.adapter.android.Rect;
import com.crazedout.adapter.android.Util;
import com.crazedout.adapter.android.stg.*;

public class Ghost extends Sprite {

    Random rand;

    public Ghost(){

    }

    public Ghost(GameView g, Tile t, String img){
        this(t.x,t.y,t.width,t.height,g,img);
    }

    public Ghost(int x, int y, int width, int height, GameView g, String img){
        this.x=x;
        this.y=y;
        this.width=width;
        this.height = height;
        rect = new Rect(x,y,x+width,y+height);
        this.game = g;
        rand = new Random();
        int r = Math.round((r = (int) Math.floor(Math.random() * (4 - 1 - 0 + 1) + 0)));
        if(g.map.mapIndex==game.map.START_MAP ||
                g.map.mapIndex==game.map.BASEMENT_MAP) r = 3;
        if(r==0) {
            this.images.add(g.map.z1_rightImage);
            this.images.add(g.map.z1_leftImage);
        }else if(r==1){
            this.images.add(g.map.z2_rightImage);
            this.images.add(g.map.z2_leftImage);
        }else if(r==2){
            this.images.add(g.map.z3_rightImage);
            this.images.add(g.map.z3_leftImage);
        }else{
            this.images.add(g.map.drz1);
            this.images.add(g.map.drz2);
        }
    }

    public moves getRandDir(int tileIndex) {
        return this.getRandDir(tileIndex,false);
    }
    public moves getRandDir(int tileIndex, boolean ignoreReverseDir){
        java.util.List<moves> list = getAllowedMoves(tileIndex);
        if(ignoreReverseDir && list.size()>1) list.remove(getInvertedDir());
        moves m = dir;
        if(list.size()>0) {
            int r = Math.round((r = (int) Math.floor(Math.random() * (list.size() - 1 - 0 + 1) + 0)));
            m = list.get(r);
        }
        return m;
    }

    public java.util.List<moves> getAllowedMoves(int i){
        Tile t = game.map.getTile(i);
        ArrayList<moves> list = new ArrayList<moves>();
        try {
            if (!game.map.getTile(t.index - game.map.getRows()).wall) list.add(moves.LEFT);
            if (!game.map.getTile(t.index + game.map.getRows()).wall) list.add(moves.RIGHT);
            if (!game.map.getTile(t.index - 1).wall) list.add(moves.UP);
            if (!game.map.getTile(t.index + 1).wall) list.add(moves.DOWN);
        }catch(Exception ex){
            System.out.println(ex.getCause());
        }
        return list;
    }

    int r = 0;
    int z = 0;
    public void move(){

        //if(z++%2==0) return;

        int t;
        moves old_dir = dir;

        if((t=canTurn())>-1){
            try {
                if (dir == moves.LEFT && game.map.getGrid().get(t - game.map.getRows()).wall) {
                    dir=getRandDir(t);
                    return;
                } else if (dir == moves.RIGHT && game.map.getGrid().get(t + game.map.getRows()).wall) {
                    dir=getRandDir(t);
                    return;
                } else if (dir == moves.UP && game.map.getGrid().get(t - 1).wall) {
                    dir=getRandDir(t);
                    return;
                } else if (dir == moves.DOWN && game.map.getGrid().get(t + 1).wall) {
                    dir=getRandDir(t);
                    return;
                }
            }catch(Exception ex){return;}
        }

        if(t>-1 && getAllowedMoves(t).size()>0 && (r++%4)==0) {
            dir = getRandDir(t,true);
        }

        switch(dir){
            case LEFT:
                x -= speed;
                break;
            case RIGHT:
                x += speed;
                break;
            case UP:
                y -= speed;
                break;
            case DOWN:
                y += speed;
                break;
            case STOP:
                break;
        }
    }
    int n = 0;
    public void draw(Canvas c, Paint paint){
        if(!visible) return;
        int tx = x;//(n%2==0)?x+1:x-1;
        int ty = y;//(n%2==0)?y+1:y-1;
        n++;
        switch(dir){
            case RIGHT:
                Util.drawImage(c,images.get(0),x,ty,game.map.size,game.map.size,paint);
                break;
            case LEFT:
                Util.drawImage(c,images.get(1),x,ty,game.map.size,game.map.size,paint);
                break;
            case UP:
                Util.drawImage(c,images.get(0),tx,y,game.map.size,game.map.size,paint);
                break;
            case DOWN:
                Util.drawImage(c,images.get(1),tx,y,game.map.size,game.map.size,paint);
                break;
        }
    }

}
