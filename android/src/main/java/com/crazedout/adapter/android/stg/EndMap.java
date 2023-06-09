package com.crazedout.adapter.android.stg;

import com.crazedout.adapter.android.*;

public class EndMap extends AbstractMap {

    PacManMap map;

    int wallTiles[] = {
            0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,31,32,47,48,63,64,79,80,95,96,111,112,127,128,143,144,159,160,
            175,176,191,192,207,208,223,224,239,240,255,256,257,258,259,260,261,262,263,264,265,266,267,268,269,270,271,
    };
    int graveTiles[] = {50,130,209,204,75,24,116,184,104,180,};
    int exitTiles[] = {8, 263,};


    public EndMap(PacManMap map){
        this.map=map;
    }

    @Override
    public int nextLevel() {
        return 0;
    }

    @Override
    public int getNumGhosts() {
        return 1;
    }

    @Override
    public int getGunTile() {
        return -1;
    }

    @Override
    public int getAltTile() {
        return 30;
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
        return new int[0];
    }

    @Override
    public int[] getGraveTiles() {
        return this.graveTiles;
    }

    @Override
    public int[] getExitTiles() {
        return exitTiles;
    }

    @Override
    public int[] getGateTiles() {
        return new int[0];
    }

    @Override
    public Bitmap getDefaultImage() {
        return map.grassImage;
    }

    @Override
    public Bitmap getDefaultBgImage() {
        return map.grassImage;
    }

    @Override
    public void draw(Canvas c, Paint paint) {

    }
    @Override
    public int getRPGTile(){
        return -1;
    }

    @Override
    public int getPillTile(){
        return -1;
    }

    @Override
    public int[] getMapWalls() {
        return new int[0];
    }

    @Override
    public Bitmap isGraveImage() {
        return map.grassImage;
    }

    @Override
    public Bitmap getWallImage() {
        return map.textur2;
    }
}
