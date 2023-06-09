package com.crazedout.adapter.android.stg;

import com.crazedout.adapter.android.*;

public class Boss extends Ghost {

    Sprite pacMan;
    boolean beingBorn = false;

    public Boss(GameView g, Tile t, Sprite pacMan){
        this(t.x,t.y,t.width,t.height,g);
        this.pacMan = pacMan;
    }

    public Boss(int x, int y, int width, int height, GameView g){
        this.x=x;
        this.y=y;
        this.width=width;
        this.height = height;
        rect = new Rect(x,y,x+width,y+height);
        this.game = g;
        images.add(game.map.bossRight1);
        images.add(game.map.bossRight2);
        images.add(game.map.bossRight3);
        images.add(game.map.bossRight4);
        images.add(game.map.bossRight5);
        images.add(game.map.bossLeft1);
        images.add(game.map.bossLeft2);
        images.add(game.map.bossLeft3);
        images.add(game.map.bossLeft4);
        images.add(game.map.bossLeft5);
        images.add(game.map.bossBorn1);
        images.add(game.map.bossBorn2);
        images.add(game.map.bossBorn3);
        images.add(game.map.bossBorn4);
        images.add(game.map.bossBorn5);
        //images.add(game.map.bossBorn6);
        speed=1;
    }

    int offset;
    boolean played = false;
    public void draw(Canvas c, Paint paint){
        if(!played){
            game.audio.playTroll();
            played=true;
        }
        if(dir == moves.RIGHT || dir==moves.UP) offset=0;
        if(dir == moves.LEFT || dir==moves.DOWN) offset=5;
        /*
        if(beingBorn) {
            offset = 10;
            anim=0;
            dir = moves.RIGHT;
        }*/
        if(visible){
            Util.drawImage(c,images.get(offset+anim),x-game.map.size,
                    y-game.map.size,game.map.size*2,game.map.size*2,paint);
            if((n++%4)==0 && !game.GAME_OVER) anim++;
            if(anim>4) {
                beingBorn=false;
                anim = 0;
            }
        }
    }

}
