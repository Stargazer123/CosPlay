package com.crazedout.adapter.android.stg;

import com.crazedout.adapter.android.*;

public class GraveYardMap implements MapData {

    int treeTiles[] = {45,237,226,34,39,230,};
    int graves[] = {68,100,132,164,196,203,171,139,107,75,};
    int exitTiles[] = {8, 263,};
    int grassTiles[] = {51,
            67,83,99,115,131,147,163,179,195,211,212,180,148,116,84,52,53,69,85,101,117,133,149,165,181,197,213,
            58,74,90,106,122,138,154,170,186,202,218,219,220,204,188,172,156,140,124,108,92,76,60,59,91,123,155,187,57,217,
    };
    int[] walls = {8, 263,0,1,2,3,4,5,6,7,9,10,11,12,13,14,15,16,31,32,35,36,37,41,42,43,44,47,48,50,61,63,64,66,68,70,73,75,
            77,79,80,82,86,89,93,95,96,98,100,102,105,107,109,111,112,114,118,121,125,127,128,130,132,134,137,139,141,143,144,146,150,153,157,159,160,162,164,166,169,171,173,175,176,178,182,185,189,191,192,194,196,198,201,203,205,207,208,210,221,223,224,227,228,229,233,234,235,236,239,240,255,256,257,258,259,260,261,262,264,265,266,267,268,269,270,271,
    };

    int NUM_GHOSTS = 4;

    Bitmap defaultImage,defaultBgImage;
    PacManMap map;

    public GraveYardMap(PacManMap map){
        this.defaultImage = map.gravelImage;
        this.defaultBgImage = map.gravelImage;
        this.map = map;
    }
    @Override
    public Bitmap isGraveImage(){
        return null;
    }
    @Override
    public Bitmap getWallImage(){
        return map.textur3;
    }

    public void draw(Canvas c, Paint paint){

    }
    @Override
    public int[] getMapWalls(){
        return new int[0];
    }

    @Override
    public int[] getWallsTiles() {
        return walls;
    }

    @Override
    public int[] getTreesTiles() {
        return treeTiles;
    }

    @Override
    public int[] getGateTiles(){
        return new int[0];
    }

    @Override
    public int[] getGrassTiles() {
        return grassTiles;
    }

    @Override
    public int[] getGraveTiles() {
        return graves;
    }

    @Override
    public int[] getExitTiles() {
        return exitTiles;
    }

    @Override
    public Bitmap getDefaultImage() {
        return this.defaultImage;
    }

    public Bitmap getDefaultBgImage(){
        return this.defaultBgImage;
    }

    @Override
    public int getNumGhosts(){
        return NUM_GHOSTS;
    }

    @Override
    public int getGunTile(){
        return map.hasGun?-1:30;
    }

    @Override
    public int getAltTile(){
        return -1;
    }
    @Override
    public int getRPGTile(){
        return -1;
    }

    @Override
    public int nextLevel(){
        return ++map.mapIndex;
    }

    @Override
    public int getPillTile(){
        return 17;
    }

}
