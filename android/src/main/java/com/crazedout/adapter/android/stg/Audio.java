package com.crazedout.adapter.android.stg;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.BufferedInputStream;

import static javax.sound.sampled.Clip.LOOP_CONTINUOUSLY;

public class Audio {

    AudioInputStream heliStream, splatStream,reloadStream,gunStream;
    AudioInputStream zScreamStream,bgStream,gongStream,openStream;
    AudioInputStream themeStream,lansStream,saxStream,clickStream,axeStream;
    AudioInputStream trollStream;
    GameView view;
    Clip clip;
    boolean themeReady = true;

    public Audio(GameView view) {
        this.view = view;
    }

    public void playHelicopter() {
        if (!view.ctrl.soundDown) return;
        try {
            heliStream = AudioSystem.getAudioInputStream(new BufferedInputStream(getClass().getResourceAsStream("/helicopter.au")));
            clip = AudioSystem.getClip();
            clip.open(heliStream);
            clip.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    Clip trollClip;
    public void playTroll() {
        if (!view.ctrl.soundDown) return;
        try {
            trollStream = AudioSystem.getAudioInputStream(new BufferedInputStream(getClass().getResourceAsStream("/troll.au")));
            trollClip = AudioSystem.getClip();
            trollClip.open(trollStream);
            setVolume(clip,-15f);
            trollClip.start();
            trollClip.loop(LOOP_CONTINUOUSLY);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void stopTroll(){
        trollClip.stop();
    }

    public void playSplat() {
        if (!view.ctrl.soundDown) return;
        try {
            splatStream = AudioSystem.getAudioInputStream(new BufferedInputStream(getClass().getResourceAsStream("/goresplat.au")));
            Clip clip = AudioSystem.getClip();
            clip.open(splatStream);
            setVolume(clip,-15f);
            clip.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    Clip themeClip;
    public void playTheme() {
        if (!view.ctrl.soundDown) return;
        try {
            themeStream = AudioSystem.getAudioInputStream(new BufferedInputStream(getClass().getResourceAsStream("/bgmusic.au")));
            themeClip = AudioSystem.getClip();
            themeClip.open(themeStream);
            setVolume(themeClip,-11f);
            themeClip.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setVolume(Clip clip, float v){
        FloatControl gainControl =
                (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(v); // Reduce volume by 10 decibels.
    }

    public void stopTheme() {
        themeClip.stop();
    }

    Clip bgClip;
    public void playBgSound() {
        if (!view.ctrl.soundDown) return;
        try {
            bgStream = AudioSystem.getAudioInputStream(new BufferedInputStream(getClass().getResourceAsStream("/bgsound.au")));
            bgClip = AudioSystem.getClip();
            bgClip.open(bgStream);
            //setVolume(clip,-11f);
            bgClip.start();
            bgClip.loop(LOOP_CONTINUOUSLY);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void pauseTheme(boolean pause) {
    }

    public void stopBg() {
        bgClip.stop();
    }

    public void playClick(){
        if (!view.ctrl.soundDown) return;
        try {
            clickStream = AudioSystem.getAudioInputStream(new BufferedInputStream(getClass().getResourceAsStream("/click.au")));
            clip = AudioSystem.getClip();
            clip.open(clickStream);
            clip.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void playLansZombie(){
        if (!view.ctrl.soundDown) return;
        try {
            lansStream = AudioSystem.getAudioInputStream(new BufferedInputStream(getClass().getResourceAsStream("/lans_zombie.au")));
            clip = AudioSystem.getClip();
            clip.open(lansStream);
            setVolume(clip,-10f);
            clip.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void stopLansPlayer(){

    }

    public void playGong() {
        if (!view.ctrl.soundDown) return;
        try {
            gongStream = AudioSystem.getAudioInputStream(new BufferedInputStream(getClass().getResourceAsStream("/gong.au")));
            Clip clip = AudioSystem.getClip();
            clip.open(gongStream);
            setVolume(clip,-15f);
            clip.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void playZombieScream(){
        if (!view.ctrl.soundDown) return;
        try {
            zScreamStream = AudioSystem.getAudioInputStream(new BufferedInputStream(getClass().getResourceAsStream("/zombie_scream1.au")));
            clip = AudioSystem.getClip();
            clip.open(zScreamStream);
            setVolume(clip,-15f);
            clip.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void playGameOver(){

    }

    public void playOpenGate(){
        if (!view.ctrl.soundDown) return;
        try {
            openStream = AudioSystem.getAudioInputStream(new BufferedInputStream(getClass().getResourceAsStream("/opengate.au")));
            Clip clip = AudioSystem.getClip();
            clip.open(openStream);
            clip.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void playAxe(){
        if (!view.ctrl.soundDown) return;
        try {
            axeStream = AudioSystem.getAudioInputStream(new BufferedInputStream(getClass().getResourceAsStream("/choptree.au")));
            clip = AudioSystem.getClip();
            clip.open(axeStream);
            clip.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public boolean canPlayShotSound(){
        return true;
    }

    public void playShot(){
        if (!view.ctrl.soundDown) return;
        try {
            gunStream = AudioSystem.getAudioInputStream(new BufferedInputStream(getClass().getResourceAsStream("/shotgun2.au")));
            clip = AudioSystem.getClip();
            clip.open(gunStream);
            clip.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    Clip saxClip;
    public void playSax(){
        s=0;
        if (!view.ctrl.soundDown) return;
        try {
            saxStream = AudioSystem.getAudioInputStream(new BufferedInputStream(getClass().getResourceAsStream("/sax.au")));
            saxClip = AudioSystem.getClip();
            saxClip.open(saxStream);
            saxClip.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    int s = 0;
    public boolean saxPlaying(){
        if(s++<2500000) {
            return true;
        }
        else {
            return false;
        }
    }

    public void playReloadSound(){
        if (!view.ctrl.soundDown) return;
        try {
            reloadStream = AudioSystem.getAudioInputStream(new BufferedInputStream(getClass().getResourceAsStream("/reload2.au")));
            Clip clip = AudioSystem.getClip();
            clip.open(reloadStream);
            clip.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}