package com.crazedout.adapter.android.stg;

import com.crazedout.adapter.android.*;

public class CourtYardMap extends AbstractMap {

    PacManMap map;

    public CourtYardMap(PacManMap map){
        this.map = map;
    }

    int exitTiles[] = {8, 263,};

    int wallTiles[] = {
            0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,31,32,34,35,36,37,42,43,44,45,47,48,50,58,63,64,66,79,80,82,90,95,96,98,99,100,101,106,107,108,109,111,112,127,128,143,144,159,160,162,163,164,165,170,171,172,173,175,176,178,186,191,192,194,207,208,210,218,223,224,226,227,228,229,234,235,236,237,239,240,255,256,257,258,259,260,261,262,263,264,265,266,267,268,269,270,271,
            74,202
    };

    int graveTiles[] = {
            51,67,83,179,195,211,187,203,219,59,75,91,
    };

    int grassTiles[] = {
            52,68,84,180,196,212,188,204,220,60,76,92,
            53,69,85,181,197,213,221,205,189,93,77,61,
    };

    int gateTiles[] = {117,133,149,122,138,154,};
    //74,202,77,205,

    int NUM_GHOSTS = 6;

    @Override
    public int getPillTile(){
        return -1;
    }

    @Override
    public int[] getGateTiles(){
        return this.gateTiles;
    }

    @Override
    public int nextLevel() {
        return ++map.mapIndex;
    }

    @Override
    public int[] getMapWalls(){
        return new int[0];
    }

    @Override
    public int getNumGhosts() {
        return map.debug?1:NUM_GHOSTS;
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
        return this.grassTiles;
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
    public Bitmap getDefaultImage() {
        return map.gravelImage;
    }

    @Override
    public Bitmap getDefaultBgImage() {
        return map.gravelImage;
    }

    public void draw(Canvas c, Paint paint){
        Util.drawImage(c,map.chapelImage,map.grid.get(119).x,map.grid.get(119).y,map.size*3,map.size*2,paint);
    }
    @Override
    public Bitmap isGraveImage(){
        return null;
    }
    @Override
    public Bitmap getWallImage(){
        return null;
    }

    @Override
    public int getRPGTile(){
        return 19;
    }

}

