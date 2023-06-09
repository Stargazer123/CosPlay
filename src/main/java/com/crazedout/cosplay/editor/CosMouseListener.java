package com.crazedout.cosplay.editor;

import com.crazedout.cosplay.Item;
import com.crazedout.cosplay.Sprite;
import com.crazedout.cosplay.Tile;
import com.crazedout.cosplay.sprites.DefaultUserSprite;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CosMouseListener extends MouseAdapter {

    MapPanel mapPanel;
    CosTile markedTile;

    public CosMouseListener(MapPanel mapPanel){
        this.mapPanel = mapPanel;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        mapPanel.grabFocus();
        int ofx = mapPanel.map.getOffset().x - (mapPanel.map.getOffset().x*2);
        int ofy = mapPanel.map.getOffset().y - (mapPanel.map.getOffset().y*2);

        if (!mapPanel.t.isAlive() && e.getButton()==3){
            Sprite sp = null;
            for (Sprite s : mapPanel.map.getSprites()) {
                if (s.contains(e.getX()+ofx, e.getY()+ofy)) {
                    sp = s;
                }
            }
            if(sp!=null){
                SpriteEditor.getInstance().pop(mapPanel,sp,e.getX(),e.getY());
                return;
            }
            Tile tt = null;
            for(Tile t:mapPanel.map.getGrid()){
                if(t.contains(e.getX()+ofx,e.getY()+ofx)){
                    if(t.getItem()!=null) tt=t;
                }
            }
            if(tt!=null){
                System.out.println("Item");
                ItemEditor.getInstance().edit(mapPanel,tt);
            }
        }

        if((e.getModifiers() & MouseEvent.SHIFT_MASK)!=0){
            CosTile t = (CosTile)mapPanel.getTileAt(e.getX()+ofx,e.getY()+ofy);
            mapPanel.statusBar.setTile(t);
            markedTile=t;
            if(markedTile.getImage()==null) {
                markedTile.setImage(mapPanel.imagePanel.selectedImage);
                if(mapPanel.imagePanel.currentItem!=null){
                    t.setItem(mapPanel.imagePanel.currentItem);
                    mapPanel.imagePanel.currentItem=null;
                }else if(mapPanel.imagePanel.selectedSentence.size()>0){
                    for(int i = 0; i < mapPanel.imagePanel.selectedSentence.size();i++){
                        if(i==0) markedTile.setImage(mapPanel.imagePanel.selectedSentence.get(i));
                        else{
                            Tile st = null;
                            if(mapPanel.imagePanel.sentenceHorizontal) {
                                st = mapPanel.map.getGrid().get((markedTile.getIndex() + (i * mapPanel.map.getCols()) + (1 * i)));
                            }else{
                                st = mapPanel.map.getGrid().get(markedTile.getIndex()+i);
                            }
                            if(st!=null) st.setImage(mapPanel.imagePanel.selectedSentence.get(i));

                        }
                    }
                    mapPanel.imagePanel.selectedSentence.clear();
                }
            }else{
                markedTile.setDefaultBgImage(mapPanel.imagePanel.selectedImage);
            }
            mapPanel.repaint();
        }
        else if((e.getModifiers() & MouseEvent.CTRL_MASK)!=0){
            CosTile t = (CosTile)mapPanel.getTileAt(e.getX()+ofx,e.getY()+ofy);
            mapPanel.statusBar.setTile(t);
            markedTile=t;
            markedTile.setImage(null);
            markedTile.setWall(false);
            markedTile.setItem(null);
            markedTile.setLadder(false);
            mapPanel.repaint();
        }else{
            CosTile t = (CosTile)mapPanel.getTileAt(e.getX()+ofx,e.getY()+ofy);
            if(t!=null) {
                mapPanel.statusBar.setTile(t);
                markedTile=t;
                mapPanel.repaint();
            }
        }
    }
    @Override
    public void mouseReleased(MouseEvent e){
        super.mouseReleased(e);
        int ofx = mapPanel.map.getOffset().x - (mapPanel.map.getOffset().x*2);
        int ofy = mapPanel.map.getOffset().y - (mapPanel.map.getOffset().y*2);
        if(markedTile!=null){
            ((CosTile)markedTile).mark=false;
            markedTile=null;
            mapPanel.statusBar.setTile(markedTile);
            mapPanel.repaint();
        }
        if((e.getModifiers() & MouseEvent.SHIFT_MASK)!=0) {
        }
    }

    @Override
    public void mouseDragged(MouseEvent e){
        int ofx = mapPanel.map.getOffset().x - (mapPanel.map.getOffset().x*2);
        int ofy = mapPanel.map.getOffset().y - (mapPanel.map.getOffset().y*2);
        if((e.getModifiers() & MouseEvent.SHIFT_MASK)!=0){
            CosTile t = (CosTile)mapPanel.getTileAt(e.getX()+ofx,e.getY()+ofy);
            mapPanel.statusBar.setTile(t);
            markedTile=t;
            if(markedTile!=null) markedTile.setImage(mapPanel.imagePanel.selectedImage);
            mapPanel.repaint();
        }
        if((e.getModifiers() & MouseEvent.CTRL_MASK)!=0){
            CosTile t = (CosTile)mapPanel.getTileAt(e.getX()+ofx,e.getY()+ofy);
            mapPanel.statusBar.setTile(t);
            markedTile=t;
            markedTile.setImage(null);
            markedTile.setWall(false);
            markedTile.setItem(null);
            markedTile.setLadder(false);
            mapPanel.repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e){
        int ofx = mapPanel.map.getOffset().x - (mapPanel.map.getOffset().x*2);
        int ofy = mapPanel.map.getOffset().y - (mapPanel.map.getOffset().y*2);
        CosTile t = (CosTile)mapPanel.getTileAt(e.getX()+ofx,e.getY()+ofy);
        mapPanel.statusBar.setTile(t);
    }

}
