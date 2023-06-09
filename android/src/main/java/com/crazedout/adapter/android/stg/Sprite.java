package com.crazedout.adapter.android.stg;

import com.crazedout.adapter.android.*;
import com.crazedout.adapter.android.Canvas;
import com.crazedout.adapter.android.Paint;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Sprite {

    Rect rect;
    int x,y,width,height;
    protected java.util.List<Bitmap> images = new ArrayList<Bitmap>();
    List<Shot> shots = new ArrayList<Shot>();
    List<Shot> rpgShots = new ArrayList<Shot>();
    Vector imageCache = new Vector();
    protected boolean visible = true;
    public enum moves {
        STOP,LEFT,RIGHT,UP,DOWN,NAN
    }
    moves dir = moves.LEFT;
    GameView game;
    int speed = 4;

    public Sprite(){

    }

    public Sprite(GameView g, Tile t){
        this(t.x,t.y,t.width,t.height,g);
    }

    public Sprite(int x, int y, int width, int height,GameView g){
        this.x=x;
        this.y=y;
        this.width=width;
        this.height = height;
        rect = new Rect(x,y,x+width,y+height);
        this.game = g;

        int martens[] = {R.drawable.tre, R.drawable.marten1, R.drawable.marten2, R.drawable.marten3};
        for(int index:martens) {
            int n = 0;
            Bitmap iArray[] = new Bitmap[16];
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    iArray[n++] = loadImage(index, i, j);
                }
            }
            imageCache.add(iArray);
        }
        reloadImages(0);
    }

    public void reloadImages(int index){
        images.clear();
        Bitmap[] arr = (Bitmap[])imageCache.get(index);
        for(Bitmap bm:arr){
            images.add(bm);
        }
    }

    public Tile getStartTile(){
        return null;
    }

    public void setVisible(boolean v){
        this.visible=v;
    }

    public boolean isVisible(){
        return this.visible;
    }

    int treeHit = 0;
    public void fire(){

        if(game.selectedItem==game.RPG && game.map.hasBazooka && dir!=moves.STOP) {
            if (game.audio.canPlayShotSound()) {
                game.audio.playShot();
                shots.add(new Shot(x + width / 2, y + width / 2, dir, true));
            }
        }
        else if(game.selectedItem==game.SHOTGUN && game.map.hasGun && dir!=moves.STOP) {
            if(game.audio.canPlayShotSound()) {
                game.audio.playShot();
                shots.add(new Shot(x + width / 2, y + width / 2, dir));
            }
        }else if(game.selectedItem==game.AXE && game.map.hasSlegde && dir!=moves.STOP){
            int index = getTileIndex();
            if(index==-1) return;

            switch(dir) {
                case LEFT:
                    if(game.map.isNotAllowed(index-16)) return;
                    else if (game.map.grid.get(index - 16).image == game.map.greenTreeImage) {
                        game.audio.playAxe();
                        treeHit++;
                        if(treeHit>3) {
                            game.map.grid.get(index - 16).image = game.map.getMapData().getDefaultImage();
                            game.map.grid.get(index - 16).wall=false;
                            treeHit=0;
                        }
                    }
                    break;
                case RIGHT:
                    if(game.map.isNotAllowed(index+16)) return;
                    else if (game.map.grid.get(index + 16).image == game.map.greenTreeImage) {
                        game.audio.playAxe();
                        treeHit++;
                        if(treeHit>3) {
                            game.map.grid.get(index + 16).image = game.map.getMapData().getDefaultImage();
                            game.map.grid.get(index + 16).wall=false;
                            treeHit=0;
                        }
                    }
                    break;
                case UP:
                    if(game.map.isNotAllowed(index-1)) return;
                    else if (game.map.grid.get(index - 1).image == game.map.greenTreeImage) {
                        game.audio.playAxe();
                        treeHit++;
                        if(treeHit>3) {
                            game.map.grid.get(index - 1).image = game.map.getMapData().getDefaultImage();
                            game.map.grid.get(index - 1).wall=false;
                            treeHit=0;
                        }
                    }
                    break;
                case DOWN:
                    if(game.map.isNotAllowed(index+1)) return;
                    else if (game.map.grid.get(index + 1).image == game.map.greenTreeImage) {
                        game.audio.playAxe();
                        treeHit++;
                        if(treeHit>3) {
                            game.map.grid.get(index + 1).image = game.map.getMapData().getDefaultImage();
                            game.map.grid.get(index + 1).wall=false;
                            treeHit=0;
                        }
                    }
                    break;
            }
        }else{
            game.audio.playClick();
        }
    }

    public int getTileIndex(){
        for(Tile t : game.map.grid){
            if(t.getRect().contains(x+width/2,y+height/2)){
                return t.index;
            }
        }
        return -1;
    }

    public List<Shot> getShots(){
        return this.shots;
    }

    public void setSpeed(int s){
        speed = s;
    }

    public void moveToTile(Tile t){
        this.x=t.x;
        this.y=t.y;
    }

    public int canTurn(){
        try {
            for (Tile t : game.map.getGrid()) {
                if (x == t.x && y == t.y) {
                    return t.index;
                }
            }
        }catch(java.util.ConcurrentModificationException ex){
            ex.printStackTrace();
        }
        return -1;
    }

    moves REQUESTED_MOVE = moves.NAN;
    public void requestDirection(moves m){
        REQUESTED_MOVE = m;
    }

    public void move(){

        int t = 0;
        moves old_dir = dir;

        if((REQUESTED_MOVE != dir && REQUESTED_MOVE!=moves.NAN) &&
                (t=canTurn())>-1) {
            dir = REQUESTED_MOVE;
            REQUESTED_MOVE = moves.NAN;
        }
        if((t=canTurn())>-1){
            try {
                if(dir == moves.STOP){
                    return;
                }
                if (dir == moves.LEFT && game.map.getGrid().get(t - game.map.getRows()).wall) {
                    dir = old_dir;
                    return;
                } else if (dir == moves.RIGHT && game.map.getGrid().get(t + game.map.getRows()).wall) {
                    dir = old_dir;
                    return;
                } else if (dir == moves.UP && game.map.getGrid().get(t - 1).wall) {
                    dir = old_dir;
                    return;
                } else if (dir == moves.DOWN && game.map.getGrid().get(t + 1).wall) {
                    dir = old_dir;
                    return;
                }
            }catch(Exception ex){return;}
        }

        switch(dir){
            case LEFT:
                x -= speed;
                break;
            case RIGHT:
                x += speed;
                break;
            case UP:
                y -= speed;
                break;
            case DOWN:
                y += speed;
                break;
            case STOP:
                break;
        }
    }

    public moves getInvertedDir(){
        if(dir==moves.LEFT) return moves.RIGHT;
        else if(dir==moves.RIGHT) return moves.LEFT;
        else if(dir==moves.UP) return moves.DOWN;
        else if(dir==moves.DOWN) return moves.UP;
        else return moves.NAN;
    }

    public Rect getRect(){
        return (rect = new Rect(x,y,x+width,y+height));
    }

    int anim = 0;
    int n = 0;
    public void draw(Canvas c, Paint paint){
        // Util.drawImage(c,images.get(0),x,y,width,height,paint);
        try {
            if (!visible) return;
            int size = game.map.size+8;
            paint.setColor(Color.YELLOW);
            if (images.size() > 0 && images.get(0) != null) {
                if (dir == moves.DOWN) Util.drawImage(c, images.get(anim), x - 4, y - 4, size, size, paint);
                if (dir == moves.LEFT) Util.drawImage(c, images.get(4 + anim), x - 4, y - 4, size, size, paint);
                if (dir == moves.RIGHT) Util.drawImage(c, images.get(8 + anim), x - 4, y - 4, size, size, paint);
                if (dir == moves.UP) Util.drawImage(c, images.get(12 + anim), x - 4, y - 4, size, size, paint);
                if (dir == moves.STOP) Util.drawImage(c, images.get(0), x - 4, y - 4, size, size, paint);
                if ((n++ % 4) == 0 && !game.GAME_OVER) anim++;
                if (anim > 3) anim = 0;
            } else {
                c.drawRect(x, y, x + width, y + height, paint);
            }
        }catch(Exception ex){
            //Log.e()
        }
    }

    Bitmap loadImage(int img, int row, int col){
        return Util.loadImage(game,img,row,col);
    }

}
