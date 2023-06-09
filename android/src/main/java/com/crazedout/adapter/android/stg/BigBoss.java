package com.crazedout.adapter.android.stg;

import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.List;

import com.crazedout.adapter.android.Bitmap;
import com.crazedout.adapter.android.Canvas;
import com.crazedout.adapter.android.Paint;
import com.crazedout.adapter.android.Util;

public class BigBoss extends Ghost {

    List[] boss = new List[7];

    public BigBoss(int x, int y, int width, int height, GameView g){
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        this.game = g;
        loadResources();
    }

    @Override
    public void move(){
        //super.move();
        if(BEEING_BORN) return;
        if(dir==moves.LEFT){
            i = 2;
        }else if(dir==moves.RIGHT){
            i = 1;
        }else if(dir==moves.UP){
        }else if(dir==moves.DOWN){
        }
    }

    public void fight(){
        if(dir==moves.LEFT) i = 4;
        else if(dir == moves.RIGHT) i = 3;
        anim=0;
    }

    public void born(){
        BEEING_BORN=true;
        DOWN_AND_OUT=true;
        reverse = false;
        dir = moves.RIGHT;
        i=0;
        anim=0;
    }

    public void fall(){
        reverse = !DOWN_AND_OUT;
        if(dir==moves.RIGHT) i=5;
        else if(dir==moves.LEFT) i=6;
        if(dir==moves.UP) i=5;
        else if(dir==moves.DOWN) i=6;
        anim=reverse?boss[i].size()-1:0;
        DOWN_AND_OUT = !DOWN_AND_OUT;
    }

    int anim = 0;
    int n = 0;
    int i = 0;
    int START_TICK=0;
    boolean reverse = false;
    boolean DOWN_AND_OUT=true;
    boolean UP_AND_WALKING=true;
    boolean BEEING_BORN=false;
    int ticker = 0;
    int sd=300;

    public void draw(Canvas c, Paint paint){
        if(START_TICK++<500) return;
        if(boss[i]!=null) {
            Util.drawImage(c,((List<Bitmap>)boss[i]).get(anim), x-width/2, y-height/2, width, height, paint);
        }
        if(ticker++>4) {
            if (!reverse) {
                if (boss[i] != null && anim < ((List<Bitmap>) boss[i]).size() - 1) anim++;
                else {
                    if (i == 0) i = 1;
                    else if (i == 5 || i == 6) anim = 4;
                    else anim = 0;
                    BEEING_BORN=false;
                }
            } else {
                if (boss[i] != null && anim > 0) anim--;
                else {
                    if (i == 0) i = 1;
                    else if (i == 5 || i == 6) {
                        BEEING_BORN=false;
                        anim = 0;
                    }
                    else {
                        BEEING_BORN=false;
                        anim = boss[i].size() - 1;
                    }
                }
            }
            ticker=0;
        }
    }

    void loadResources() {
        try{
            List<Bitmap> list = new ArrayList<>();
            String files[] = {"upp1","upp2","upp3","upp4","upp5","upp6"};
            for(int i = 0; i < files.length; i++){
                list.add(new Bitmap(ImageIO.read(getClass().getResource("/ur_marken/" + files[i] + ".png"))));
            }
            boss[0] = list;

            list = new ArrayList<>();
            String files2[] = {"walk1","walk2","walk3","walk4","walk5","walk6"};
            for(int i = 0; i < files2.length; i++){
                list.add(new Bitmap(ImageIO.read(getClass().getResource("/walk/" + files2[i] + ".png"))));
            }
            boss[1] = list;

            list = new ArrayList<>();
            String files3[] = {"walk_left1","walk_left2","walk_left3","walk_left4","walk_left5","walk_left6"};
            for(int i = 0; i < files3.length; i++){
                list.add(new Bitmap(ImageIO.read(getClass().getResource("/walk_left/" + files3[i] + ".png"))));
            }
            boss[2] = list;

            list = new ArrayList<>();
            String files4[] = {"fight1","fight2","fight3","fight4"};
            for(int i = 0; i < files4.length; i++){
                list.add(new Bitmap(ImageIO.read(getClass().getResource("/fight/" + files4[i] + ".png"))));
            }
            boss[3] = list;

            list = new ArrayList<>();
            String files5[] = {"fight_left1","fight_left2","fight_left3","fight_left4"};
            for(int i = 0; i < files5.length; i++){
                list.add(new Bitmap(ImageIO.read(getClass().getResource("/fight_left/" + files5[i] + ".png"))));
            }
            boss[4] = list;


            list = new ArrayList<>();
            String files6[] = {"faller1","faller2","faller3","faller4","faller5"};
            for(int i = 0; i < files6.length; i++){
                list.add(new Bitmap(ImageIO.read(getClass().getResource("/faller/" + files6[i] + ".png"))));
            }
            boss[5] = list;

            list = new ArrayList<>();
            String files7[] = {"faller_left1","faller_left2","faller_left3","faller_left4","faller_left5"};
            for(int i = 0; i < files7.length; i++){
                list.add(new Bitmap(ImageIO.read(getClass().getResource("/faller_left/" + files7[i] + ".png"))));
            }
            boss[6] = list;

        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
