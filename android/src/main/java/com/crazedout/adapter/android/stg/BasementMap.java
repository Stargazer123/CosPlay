package com.crazedout.adapter.android.stg;

import com.crazedout.adapter.android.Bitmap;
import com.crazedout.adapter.android.Canvas;
import com.crazedout.adapter.android.Paint;
import com.crazedout.adapter.android.Util;

public class BasementMap implements MapData {

    PacManMap map;

    public BasementMap(PacManMap map){
        this.map=map;
    }

    int wallTiles[] = {
            0,1,2,3,4,5,6,7,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,25,26,27,28,29,30,31,32,33,
            41,44,46,47,48,49,51,52,53,54,55,57,62,63,64,65,70,71,73,78,79,80,81,83,84,86,87,89,
            92,94,95,96,97,99,100,102,103,104,105,108,110,111,112,113,115,116,118,119,120,121,122,123,
            124,126,127,128,129,142,143,144,145,146,147,148,149,150,151,152,153,154,156,157,158,159,160,
            164,170,172,175,176,180,186,191,192,198,202,207,208,214,215,216,217,218,220,
            223,224,228,236,239,240,244,245,246,248,249,250,251,252,255,256,257,258,259,260,261,262,264,265,266,267,268,269,270,271,
    };
    //166,182
//    60,204,212,
    int exitTiles[] = {8, 263,};
    int gateTiles[] = {8, 263,};

    int graveTiles[] = {
            178,210,184,205,74,130,
    };

    @Override
    public int nextLevel() {
        return ++map.mapIndex;
    }

    @Override
    public int getNumGhosts() {
        return 5;
    }

    @Override
    public int getGunTile() {
        return -1;
    }

    @Override
    public int getAltTile() {
        return -1;
    }

    @Override
    public int[] getWallsTiles() {
        return this.wallTiles;
    }

    @Override
    public int[] getTreesTiles() {
        return new int[0];
    }

    @Override
    public int[] getGrassTiles() {
        return new int[0];
    }

    @Override
    public int[] getGraveTiles() {
        return this.graveTiles;
    }

    @Override
    public int[] getExitTiles() {
        return this.exitTiles;
    }

    @Override
    public int[] getGateTiles() {
        return this.gateTiles;
    }

    @Override
    public Bitmap getDefaultImage() {
        return map.gravelImage;
    }

    @Override
    public Bitmap getDefaultBgImage() {
        return map.gravelImage;
    }

    int its[] = {42,45,109,253,254,241,242,184,};
    @Override
    public void draw(Canvas c, Paint paint) {
        if(!map.view.MARQUEE && map.grid.size() > 255) {
            Util.drawImage(c, map.bed2, map.grid.get(42).x, map.grid.get(42).y,
                        map.size, map.size, paint);
            Util.drawImage(c, map.bed1, map.grid.get(45).x, map.grid.get(45).y,
                    map.size, map.size, paint);
            Util.drawImage(c, map.bed2, map.grid.get(109).x, map.grid.get(109).y,
                    map.size, map.size, paint);
            Util.drawImage(c, map.bed2, map.grid.get(253).x, map.grid.get(253).y,
                    map.size, map.size, paint);
            Util.drawImage(c, map.bed1, map.grid.get(254).x, map.grid.get(254).y,
                    map.size, map.size, paint);
            Util.drawImage(c, map.bed2, map.grid.get(241).x, map.grid.get(241).y,
                    map.size, map.size, paint);
            Util.drawImage(c, map.bed1, map.grid.get(242).x, map.grid.get(242).y,
                    map.size, map.size, paint);
            Util.drawImage(c, map.bed2, map.grid.get(184).x, map.grid.get(184).y,
                    map.size, map.size, paint);
        }
    }

    @Override
    public int[] getMapWalls() {
        return new int[0];
    }

    @Override
    public Bitmap isGraveImage() {
        return map.gravelImage;
    }

    @Override
    public Bitmap getWallImage() {
        return null;
    }

    @Override
    public int getPillTile() {
        return 61;
    }

    @Override
    public int getRPGTile() {
        return -1;
    }
}
