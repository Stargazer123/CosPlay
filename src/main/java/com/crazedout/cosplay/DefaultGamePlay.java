package com.crazedout.cosplay;

import com.crazedout.cosplay.sprites.DefaultActorSprite;
import com.crazedout.cosplay.sprites.DefaultUserSprite;

import java.util.ArrayList;
import java.util.List;

/**
 * A default game play.
 */

public class DefaultGamePlay extends GamePlay {

    public DefaultGamePlay(){
        currentGamePlay=this;
    }

    public void addActorSprites(List<Sprite> sprites){
        for(Sprite s:sprites){
            if(s instanceof DefaultActorSprite){
                addGamePlayListener((DefaultActorSprite)s);
            }
        }
    }

    @Override
    public void spriteAction(Map map, Sprite sprite, Action action) {

        //Remove item image if hit by User sprite
        switch (action.getAction()) {
            case SPRITE_MOVING:
                for(Sprite s:map.getSprites()){
                    if(!(s instanceof UserSprite)){
                        if(s.isVisible() && sprite.contains(s.getX()+(s.getWidth()/2),s.getY()+(s.getHeight()/2))){
                            sprite.hit(s);
                        }
                    }
                }
                if(sprite instanceof UserSprite) {
                    for (Tile t : map.getGrid()) {
                        if (t.index == sprite.getTileIndex() && t.getItem() != null) {
                            sprite.hit(t.getItem());
                        }
                    }
                }
                break;
            case HIT_ITEM:
                Tile t = map.getGrid().get(sprite.getTileIndex());
                System.out.println(t.getIndex());
                if (t.getItem() != null && t.getItem().isActive() && sprite instanceof DefaultUserSprite) {
                    t.getItem().setActive(false);
                    t.setImage(null);
                    if(t.getItem().getSoundId()==0){
                        GamePlay.getAudio().playSound(Audio.OPEN_GATE);
                    }else {
                        GamePlay.getAudio().playSound(t.getItem().getSoundId());
                    }
                }
                break;
            case SHOTGUN:
                //GamePlay.getAudio().playSound(Audio.SHOTGUN);
                break;
        }


        // Handle shots.
        if(sprite instanceof DefaultUserSprite) {
            List<Shot> remove = new ArrayList<>();
            for (Shot shot : ((DefaultUserSprite) sprite).getShots()) {
                if (shot.x < 0 || shot.x > map.getCols() * map.getSize() ||
                        shot.y < 0 || shot.y > map.getRows() * map.getSize()) {
                    remove.add(shot);
                    continue;
                }
                for (Sprite sp : map.getSprites()) {
                    if(sp instanceof DefaultActorSprite) {
                        if (sp.contains(shot.x, shot.y) && sp.isVisible()) {
                            sp.hit(shot);
                            shot.visible = false;
                            remove.add(shot);
                        }
                    }
                }
            }
            for (Shot st : remove) {
                ((DefaultUserSprite) sprite).getShots().remove(st);
            }
        }
    }
}
