package com.crazedout.cosplay;

import java.io.Serializable;

public class ViewPort implements Serializable {

    private static final long serialVersionUID = 1L;

    private int size;
    public int tileWidth, tileHeight;
    transient GamePanel gamePanel;

    public ViewPort(GamePanel gamePanel, int tileWidth, int tileHeight, int tileSize){
        this.gamePanel = gamePanel;
        this.tileWidth=tileWidth;
        this.tileHeight=tileHeight;
        this.size=tileSize;
    }

    public GamePanel getGamePanel(){
        return this.gamePanel;
    }

    public int getPixWidth(){
        return tileWidth*size;
    }

    public int getPixHeight(){
        return tileHeight*size;
    }

    public int getSize(){
        return this.size;
    }

    public void setSize(int size){
        this.size=size;
    }
}
