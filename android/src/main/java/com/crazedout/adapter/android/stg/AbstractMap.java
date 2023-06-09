package com.crazedout.adapter.android.stg;

import com.crazedout.adapter.android.Bitmap;
import com.crazedout.adapter.android.Canvas;
import com.crazedout.adapter.android.Paint;

public abstract class AbstractMap implements MapData {
    public int nextLevel() {
        return 0;
    }

    public int getNumGhosts() {
        return 0;
    }

    public int getGunTile() {
        return 0;
    }

    public int getAltTile() {
        return 0;
    }

    public int[] getWallsTiles() {
        return new int[0];
    }

    public int[] getTreesTiles() {
        return new int[0];
    }

    public int[] getGrassTiles() {
        return new int[0];
    }

    public int[] getGraveTiles() {
        return new int[0];
    }

    public int[] getExitTiles() {
        return new int[0];
    }

    public int[] getGateTiles() {
        return new int[0];
    }

    public Bitmap getDefaultImage() {
        return null;
    }

    public Bitmap getDefaultBgImage() {
        return null;
    }

    public void draw(Canvas c, Paint paint) {

    }

    public int[] getMapWalls() {
        return new int[0];
    }

    public Bitmap isGraveImage() {
        return null;
    }

    public Bitmap getWallImage() {
        return null;
    }

    public int getPillTile() {
        return 0;
    }

    public int getRPGTile() {
        return 0;
    }
}
