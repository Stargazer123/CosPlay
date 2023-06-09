package com.crazedout.adapter.android.stg;

import com.crazedout.adapter.android.*;

public class WoodsMap extends AbstractMap {

    int treeTiles[] = {//11,3,2,54,72,85,102,108,110,113,121,132,140,158,170,171,253,264,
//            0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,31,32,47,48,63,64,79,80,95,96,111,112,127,128,143,144,159,160,175,176,191,192,207,208,223,224,239,240,255,256,257,258,259,260,261,262,264,265,266,267,268,269,270,271,
//            0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,31,32,34,41,43,46,47,48,53,55,63,64,68,75,77,79,80,82,86,95,96,104,106,108,111,112,116,127,128,130,140,142,143,144,150,152,159,160,165,170,175,176,178,188,191,192,196,198,200,207,208,219,221,223,224,226,229,234,239,240,255,256,257,258,259,260,261,262,264,265,266,267,268,269,270,271,
            0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,19,31,32,35,41,43,46,47,48,49,50,51,53,55,63,64,68,75,77,79,80,82,86,95,96,104,106,108,111,112,116,127,128,130,140,142,143,144,150,152,159,160,165,170,175,176,178,188,191,192,196,198,200,207,208,219,221,223,224,226,229,234,239,240,255,256,257,258,259,260,261,262,264,265,266,267,268,269,270,271,
            20,36,52,67,66,65,81,83,21,37
    };

    int exitTiles[] = {8, 263,};
    int graves[] = {60,148,184,72,137,173};
    int[] walls = {263
    };

    Bitmap defaultImage, defaultBgImage;
    PacManMap map;

    public WoodsMap(PacManMap map){
        this.map = map;
        this.defaultImage = map.grassImage;
        this.defaultBgImage = map.grassImage;
    }

    int NUM_GHOSTS = 5;


    @Override
    public int getRPGTile(){
        return -1;
    }

    @Override
    public int getPillTile(){
        return 193;
    }

    @Override
    public void draw(Canvas c, Paint paint){

    }

    @Override
    public int[] getGateTiles(){
        return new int[0];
    }

    @Override
    public int[] getMapWalls(){
        return new int[0];
    }

    @Override
    public int nextLevel(){
        return ++map.mapIndex;
    }

    @Override
    public int getNumGhosts(){
        return map.debug?1:NUM_GHOSTS;
    }

    @Override
    public int[] getWallsTiles() {
        return this.walls;
    }

    @Override
    public int[] getTreesTiles() {
        return treeTiles;
    }

    @Override
    public int[] getGrassTiles() {
        return new int[0];
    }

    @Override
    public int[] getGraveTiles() {
        return this.graves;
    }

    @Override
    public int[] getExitTiles() {
        return this.exitTiles;
    }

    @Override
    public Bitmap getDefaultImage() {
        return this.defaultImage;
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
    public Bitmap getDefaultBgImage() {
        return this.defaultBgImage;
    }

    @Override
    public int getGunTile(){
        return -1;
    }
    @Override
    public int getAltTile(){
        return map.hasSlegde?-1:30;
    }

}
