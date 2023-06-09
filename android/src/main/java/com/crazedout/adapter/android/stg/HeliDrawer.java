package com.crazedout.adapter.android.stg;

import com.crazedout.adapter.android.Canvas;
import com.crazedout.adapter.android.Paint;
import com.crazedout.adapter.android.Util;

public class HeliDrawer {

    PacManMap map;
    enum HeliMode {
        HELI_OFF,
        HELI_IN,
        HELI_OUT,
        HELI_STAY,
        HELI_DONE
    }
    HeliMode mode = HeliMode.HELI_OFF;
    int n;
    int delay;
    int start_tile = 128;
    int x;
    public HeliDrawer(PacManMap map){
        n = 0;
        delay=0;
        this.map = map;
    }

    public void setMode(HeliMode mode){
        this.mode = mode;
    }

    public HeliMode getMode(){
        return this.mode;
    }

    public Tile getHeliTile(){
        Tile t = map.getTileAt(x+map.size,n+map.size);
        //t.setImage(map.textur3);
        return t;
    }

    public void draw(Canvas c, Paint paint){

        x = map.grid.get(128).x-(map.size/2);
        switch(mode){
            case HELI_IN:
                if(delay++<400) break;
                Util.drawImage(c,map.heliImage,x,n,map.size*3,map.size*2,paint);
                if(n > map.size*10){
                    mode = HeliMode.HELI_STAY;
                    delay = 0;
                }
                n++;
                break;
            case HELI_STAY:
                Util.drawImage(c, map.heliImage, x,
                        n,
                        map.size*3, map.size*2, paint);
                break;
            case HELI_OUT:
                Util.drawImage(c,map.heliImage,x,n,map.size*3,map.size*2,paint);
                if(n < (map.size-(map.size*2))){
                    mode = HeliMode.HELI_DONE;
                }
                n--;
                break;
            case HELI_DONE:
                if(delay++<300) break;
                n=0;
                delay = 0;
                map.view.reset(true);
                break;
            case HELI_OFF:
                break;
        }
    }
}
