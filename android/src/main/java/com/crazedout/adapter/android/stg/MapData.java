package com.crazedout.adapter.android.stg;

import com.crazedout.adapter.android.Bitmap;
import com.crazedout.adapter.android.Canvas;
import com.crazedout.adapter.android.Paint;
import com.sun.istack.internal.NotNull;

public interface MapData {

    public int nextLevel();
    public int getNumGhosts();
    public int getGunTile();
    public int getAltTile();
    public int[] getWallsTiles();
    public int[] getTreesTiles();
    public int[] getGrassTiles();
    public int[] getGraveTiles();
    public int[] getExitTiles();
    public int[] getGateTiles(); // Can not be 0
    
    public Bitmap getDefaultImage();
    public Bitmap getDefaultBgImage();
    public void draw(Canvas c, Paint paint);
    public int[] getMapWalls();
    public Bitmap isGraveImage();
    public Bitmap getWallImage();
    public int getPillTile();
    public int getRPGTile();

}
