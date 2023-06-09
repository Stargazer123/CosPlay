package com.crazedout.adapter.android.stg;

import com.crazedout.adapter.android.Bitmap;
import com.crazedout.adapter.android.Canvas;
import com.crazedout.adapter.android.Paint;
import com.crazedout.adapter.android.Util;

public class StartMap extends AbstractMap{

    PacManMap map;
    public StartMap(PacManMap map){
        this.map = map;
    }

    int wallTiles[] = {
            6,10,7,8,9,17,18,19,20,21,22,26,27,28,29,30,33,46,49,51,52,53,55,59,60,62,65,69,71,73,75,78,81,83,84,85,87,88,89,91,93,94,97,99,101,103,105,107,110,113,117,119,121,125,126,129,142,145,147,148,149,150,151,152,153,154,155,157,158,161,173,174,177,179,180,181,182,184,185,186,187,189,190,193,195,203,205,206,209,211,213,214,216,217,219,221,222,225,229,233,238,241,242,243,244,245,249,250,251,252,253,254,261,262,263,264,265,
    };

    int grassTiles[] = {
            0,1,2,3,4,5,6,8,10,11,12,13,14,15,16,31,32,47,48,63,64,79,80,95,96,111,112,127,128,143,144,159,160,175,176,191,192,207,208,223,224,239,240,255,256,257,258,259,260,266,267,268,269,270,271,
    };
    int exitTiles[] = {8, 263,};

    int graveTiles[] = {
            131,57,166,235,
    };

    @Override
    public Bitmap getWallImage(){
        return map.textur1;
    }

    @Override
    public int nextLevel() {
        return ++map.mapIndex;
    }

    @Override
    public int getNumGhosts() {
        return 4;
    }

    @Override
    public int getGunTile() {
        return 68;
    }

    @Override
    public int getAltTile() {
        return -1;
    }

    @Override
    public int[] getWallsTiles() {
        return wallTiles;
    }

    @Override
    public int[] getTreesTiles() {
        return new int[0];
    }

    @Override
    public int[] getGrassTiles() {
        return grassTiles;
    }

    @Override
    public int[] getGraveTiles() {
        return this.graveTiles;
    }

    @Override
    public int[] getExitTiles() {
        return this.exitTiles;
    }

    int gateTiles[] = {8};
    @Override
    public int[] getGateTiles() {
        return gateTiles;

    }

    @Override
    public Bitmap getDefaultImage() {
        return map.gravelImage;
    }

    @Override
    public Bitmap getDefaultBgImage() {
        return map.gravelImage;
    }

    int n = 0;
    @Override
    public void draw(Canvas c, Paint paint) {
        try {
            if (!map.view.MARQUEE && map.grid.size() > 227) {
                Util.drawImage(c, map.bed1, map.grid.get(102).x, map.grid.get(102).y, map.size, map.size, paint);
                Util.drawImage(c, map.bed2, map.grid.get(141).x, map.grid.get(141).y, map.size, map.size, paint);
                Util.drawImage(c, map.bed3, map.grid.get(100).x, map.grid.get(100).y, map.size, map.size, paint);
                Util.drawImage(c, map.bed4, map.grid.get(70).x, map.grid.get(70).y, map.size, map.size, paint);
                Util.drawImage(c, map.bed2, map.grid.get(109).x, map.grid.get(109).y, map.size, map.size, paint);
                Util.drawImage(c, map.bed3, map.grid.get(76).x, map.grid.get(76).y, map.size, map.size, paint);
                Util.drawImage(c, map.bed4, map.grid.get(227).x, map.grid.get(227).y, map.size, map.size, paint);
                Util.drawImage(c, map.bed4, map.grid.get(204).x, map.grid.get(204).y, map.size, map.size, paint);
            }
        }catch (Exception ex){
            ex.printStackTrace();;
        }
    }

    @Override
    public int[] getMapWalls() {
        int w[] = {};
        return w;
    }

    @Override
    public Bitmap isGraveImage(){
        return map.gravelImage;
    }

    @Override
    public int getPillTile(){
        return -1;
    }

    @Override
    public int getRPGTile(){
        return -1;
    }

}
