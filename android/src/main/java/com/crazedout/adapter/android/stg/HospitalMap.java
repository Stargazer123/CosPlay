package com.crazedout.adapter.android.stg;

import com.crazedout.adapter.android.*;

public class HospitalMap extends AbstractMap {

    PacManMap map;

    int wallTiles[] = {22
    };
    int exitTiles[] = {8, 263,};
    int treeTiles[] = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,31,32,47,48,63,64,79,80,95,96,111,112,127,128,143,144,159,160,175,176,191,192,207,208,223,224,239,240,255,256,257,258,259,260,261,262,263,264,265,266,267,268,269,270,271,};
    int graveTiles[] = {
            57,89,121,153,185,217,59,91,123,155,187,219,61,93,125,157,189,221,
    };
    int mapWalls[] = {
            67,68,69,70,86,102,118,134,150,166,182,198,197,196,195,179,163,162,145,146,130,129,113,97,98,82,
    };

    int NUM_GHOSTS = 10;

    public HospitalMap(PacManMap map){
        this.map = map;
    }

    @Override
    public int[] getGateTiles(){
        return new int[0];
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
    public int[] getMapWalls(){
        return this.mapWalls;
    }

    @Override
    public int nextLevel() {
        return ++map.mapIndex;
    }
    @Override
    public int getRPGTile(){
        return -1;
    }

    @Override
    public int getPillTile(){
        return 30;
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
        return this.treeTiles;
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
    public Bitmap getDefaultImage() {
        return map.grassImage;
    }

    @Override
    public Bitmap getDefaultBgImage() {
        return map.grassImage;
    }

    public void draw(Canvas c, Paint paint){
        Util.drawImage(c,map.hospitalImage,map.grid.get(81-16).x,map.grid.get(81-16).y,map.size*9,map.size*6,paint);
    }

}
