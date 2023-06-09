package com.crazedout.adapter.android.stg;

import com.crazedout.adapter.android.*;
import com.crazedout.adapter.android.Canvas;
import com.crazedout.adapter.android.Paint;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static com.crazedout.adapter.android.stg.PacManMap.END_MAP;
import static com.crazedout.adapter.android.stg.Sprite.moves.DOWN;
import static com.crazedout.adapter.android.stg.Sprite.moves.RIGHT;

public class GameView extends View implements Runnable {

    Paint paint;
    int GAME_SPEED = 14 ;
    //int GAME_SPEED = 5;
    boolean GAME_OVER = false;
    int GAME_OVER_TICK = 0;
    boolean MARQUEE = true;
    boolean PAUSE = false;
    boolean FADE_OUT = false;
    boolean CAN_KILL_NICKE = true;
    Sprite pacMan;
    List<Ghost> ghosts = new ArrayList<Ghost>();
    List<Girl> actors = new ArrayList<Girl>();
    List<Point> pacTracs = new ArrayList<Point>();
    List<Integer> dirtTiles = new ArrayList<Integer>();
    Audio audio;
    BigBoss b;
    Thread runner;
    PacManMap map;
    int LIVES = 5;
    boolean editMode=true;

    List<Item> items = new ArrayList<Item>();
    int selectedItem = 0;
    DisplayMetrics displayMetrics;
    Activity ctx;
    Controls ctrl;
    Marquee splash;
    TextTicker textTicker;
    boolean record = false;
    boolean openGatedPlayed = false;
    Bitmap arcade;
    boolean ARCADE = true;
    Activity act;
    boolean hasMedKit = false;

    final static int SHOTGUN = 0;
    final static int RPG = 1;
    final static int AXE = 2;
    final static int SYRINGE = 3;

    HeliDrawer heliDrawer;

    public GameView(Activity c, int w, int h){
        super(w,h);
        setFocusable(true);
        grabFocus();
        Util.observer = this;
        this.act = c;
        setBackgroundColor(Color.BLACK);
        this.ctx = c;
        displayMetrics = getResources().getDisplayMetrics();
        audio = new Audio(this);
        splash = new Marquee(this);
        map = new PacManMap(this);
        heliDrawer = new HeliDrawer(map);
        pacMan = new Sprite(this,map.getGrid().get(map.START_TILE));
        pacMan.dir = Sprite.moves.LEFT;
        pacMan.setSpeed(1);
        textTicker = map.getTextTicker();
        ctrl = new Controls(this);
        ctrl.setSprite(pacMan);

        runner = new Thread(this);
        runner.start();
        Thread s = new Thread(new Runnable() {
            public void run() {
                while(!audio.themeReady){
                }
                audio.playTheme();
            }
        });
        s.start();
    }

    public void start(){
        MARQUEE=false;
        items.add(new Item(map.gunImage, false));
        items.add(new Item(map.bazookaImage, false));
        items.add(new Item(map.sledgeImage, false));
        items.add(new Item(map.sprutaImage, false));
        textTicker = map.getTextTicker();
        audio.playGong();
        audio.stopTheme();
        if(ctrl.soundDown) audio.playBgSound();
        playArounds++;
        //map.mapIndex = END_MAP;
        repaint();
    }

    public void initWait(){
    }

    public void pacHit(){
        if(!map.debug) LIVES--;
        if(LIVES>0) {
            if(LIVES==3) pacMan.reloadImages(1);
            if(LIVES==2) pacMan.reloadImages(2);
            if(LIVES<=1) pacMan.reloadImages(3);
            audio.playSplat();
            pacMan.shots.clear();
            int n = 0;
            PAC_INVULNERABLE=true;
            PAC_IN_TICKS = 100;
            repaint();
        }else{
            if(!GAME_OVER) setGameOver();
        }
    }

    boolean lansWasPlying = false;
    public void pause(){

        if(!MARQUEE) {
            this.PAUSE = !this.PAUSE;
            audio.pauseTheme(PAUSE);
            if(this.PAUSE) audio.stopBg();
            else audio.playBgSound();
        }
    }

    int visibleItems(){
        int n = 0;
        for(Item i:items){
            if(i.visible) n++;
        }
        return n;
    }

    public void alt(){
        audio.playClick();
        if(visibleItems()>1) {
            if(selectedItem < visibleItems()-1) selectedItem++;
            else selectedItem = 0;
        }
    }

    int playArounds = 0;
    public void reset(boolean marquee){
        audio.stopTroll();
        map.mapIndex=0;
        map.initGrid();
        LIVES=5;
        audio.stopBg();
        pacMan.reloadImages(0);
        FADE_OUT = false;
        MARQUEE = marquee;
        if(marquee) audio.playTheme();
        GAME_OVER = false;
        pacMan.x = map.getGrid().get(map.START_TILE).x;
        pacMan.y = map.getGrid().get(map.START_TILE).y;
        pacMan.dir = Sprite.moves.LEFT;
        ghosts.clear();
        pacMan.shots.clear();
        map.hasGun=false;
        hasMedKit=false;
        map.hasSlegde=false;
        map.hasBazooka=false;
        map.hasSpruta=false;
        dirtTiles.clear();
        add_tick=0;
        n_add=0;
        map.INIT_TICK_COUNTDOWN=map.INIT_TICKS;
        hitList.clear();
        items.clear();
        items.add(new Item(map.gunImage, false));
        items.add(new Item(map.bazookaImage, false));
        items.add(new Item(map.sledgeImage, false));
        items.add(new Item(map.sprutaImage, false));
        actors.clear();
        HEART = false;
        audio.stopLansPlayer();
        repaint();
        playArounds++;
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        paint = new Paint(this,g);
    }

    public void nextLevel(){
        map.mapIndex = map.getMapData().nextLevel();
        map.initGrid();
        if(playArounds > 0 && map.mapIndex==0){
            // Player has rounded game.
            reset(true);
            if(GAME_SPEED>4) GAME_SPEED -= 1;
            return;
        }
        hasMedKit=false;
        ghosts.clear();
        FADE_OUT = false;
        MARQUEE = false;
        GAME_OVER = false;
        pacMan.x = map.getGrid().get(map.START_TILE).x;
        pacMan.y = map.getGrid().get(map.START_TILE).y;
        pacMan.dir = Sprite.moves.LEFT;
        pacMan.shots.clear();
        add_tick=0;
        n_add=0;
        map.INIT_TICK_COUNTDOWN=map.INIT_TICKS;
        hitList.clear();
        record = false;
        actors.clear();
        pacTracs.clear();
        openGatedPlayed=false;
        dirtTiles.clear();
        audio.stopLansPlayer();
        if(map.mapIndex==map.WOODS_MAP) {
            Girl g = new Girl(this, map.getTile(17), pacMan);
            g.dir = RIGHT;
            g.visible = true;
            actors.add(g);
            saxPlayed = false;
        }else if(map.mapIndex== END_MAP){
            Girl g = new Girl(this, map.getTile(map.START_TILE),pacMan);
            g.reloadImages(R.drawable.josefine3);
            record = true;
            g.follow = true;
            g.visible=true;
            actors.add(g);
            selectedItem=0;
            saxPlayed=true;
            audio.playLansZombie();
            map.grid.get(map.getMapData().getAltTile()).image = map.sprutaImage;
        }else if(map.mapIndex==map.HOSPITAL_MAP){
            Girl g = new Girl(this, map.getTile(map.START_TILE),pacMan);
            record = true;
            g.follow = true;
            g.visible=true;
            actors.add(g);
            selectedItem=0;
            saxPlayed=true;
        }

        if(map.mapIndex==END_MAP){
            b = new BigBoss(map.grid.get(60).x,map.grid.get(60).x,map.size*2, map.size*2,this);
            b.dir = RIGHT;
            b.born();
        }

        audio.playGong();
        if(ctrl.soundDown) audio.playBgSound();
        HAND_ALIVE=false;
        repaint();
    }

    public void addZombie(){

        int r = Math.round((int) Math.floor(Math.random() * (map.getMapData().getGraveTiles().length - 1 - 0 + 1) + 0));
        map.HAND_TILE = map.getMapData().getGraveTiles()[r] + 1;

        handx = map.grid.get(map.HAND_TILE).x;
        handy = map.grid.get(map.HAND_TILE).y;
        //if(map.mapIndex>1) {
            hand_index = 0;
            handani = 0;
            hand_alive_tick = 0;
            hand_ratio = 0;
            HANDS_UP = false;
            HAND_ALIVE = true;
        /*}else{
            spawnZombie(map.HAND_TILE,false);
            HAND_ALIVE=false;
        }*/
    }

    int zm;
    public void spawnZombie(int tile, boolean dirt){
        Ghost g = new Ghost(this, map.grid.get(tile),"6");
        g.visible=true;
        if(dirt) g.dir = (zm%2==0)? RIGHT:Sprite.moves.LEFT;
        else g.dir= RIGHT;
        g.setSpeed(1);
        ghosts.add(g);
        if(dirt) dirtTiles.add(map.HAND_TILE);
        audio.playZombieScream();
    }

    long time = 500;
    long wait;
    public synchronized void invalidate(){
        repaint();
    }

    public void run(){
        while(Thread.currentThread()==runner && runner!=null){
        //while(true){
            try{
                Thread.sleep(GAME_SPEED);
                //invalidate();
                if(!PAUSE) tick();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }

    public void setGameOver(){
        GAME_OVER = true;
        GAME_OVER_TICK = 300;
        audio.playZombieScream();
        audio.playGameOver();
        audio.stopLansPlayer();
        playArounds=0;
    }

    public int ghostsAlive(){
        int n = 0;
        if(ghosts.size()<map.getMapData().getNumGhosts()-1) return 1;
        for(Ghost g:ghosts){
            if(g.visible) n++;
        }
        return n;
    }

    int add_tick = 0;
    int n_add = 0;

    public void tick(){

        if(PAC_INVULNERABLE){
            PAC_IN_TICKS--;
            if(PAC_IN_TICKS<0){
                PAC_INVULNERABLE=false;
                PAC_IN_TICKS=0;
            }
        }

        if(GAME_OVER && GAME_OVER_TICK==0) {
            reset(true);
            return;
        }
        if(GAME_OVER_TICK-- > 0){
            repaint();
            return;
        }

        if(MARQUEE) {
            //repaint();
            invalidate();
            return;
        }

        if(FADE_OUT){
            for(Tile t:map.grid){
                t.x+=1;
            }
        }

        if(map.INIT_TICK_COUNTDOWN-- > 0) {
            initWait();
            return;
        }

        if(pacMan!=null && !GAME_OVER){
            if(add_tick++>200 && n_add<map.getMapData().getNumGhosts() &&
                        !HAND_ALIVE){
                        addZombie();
                        n_add++;
                        add_tick = 0;
            }/*else if(ghosts.size() < map.getMapData().getNumGhosts()){
                addZombie();
                n_add=0;
                add_tick=0;
            }*/


            if(b!=null) {
                b.move();
            }

            if(!HEART) pacMan.move();
            if(record){
                pacTracs.add(new Point(pacMan.x,pacMan.y));
            }
            for(Ghost g:ghosts) {
                if(!HEART) g.move();
            }
            for(final Girl g:actors) {
                g.move();
                if(pacMan.getRect().contains(g.x+g.width/2,g.y+g.height/2) && !saxPlayed){
                    audio.playSax();
                    if(ctrl.soundDown) audio.stopBg();
                    g.dir= Sprite.moves.STOP;
                    HEART=true;
                    saxPlayed = true;
                    if(ghostsAlive()==0 && !openGatedPlayed) {
                        audio.playOpenGate();
                        map.grid.get(8).image = map.getMapData().getDefaultImage();
                        map.grid.get(8).wall = false;
                        openGatedPlayed=true;
                    }
                    new Thread(new Runnable() {
                        public void run() {
                            while(audio.saxPlaying()){

                            }
                            HEART=false;
                            record=true;
                            g.follow=true;
                        }
                    }).start();
                }
            }
        }
        //repaint();
        invalidate();
    }
    boolean saxPlayed = false;

    public void pickUpStuff() throws IndexOutOfBoundsException {

        if(map.mapIndex== END_MAP && heliDrawer.getMode()== HeliDrawer.HeliMode.HELI_STAY &&
                heliDrawer.getHeliTile().index == pacMan.getTileIndex()) {
                pacMan.setVisible(false);
                actors.get(0).setVisible(false);
                heliDrawer.setMode(HeliDrawer.HeliMode.HELI_OUT);
                return;
        }
        if(map.mapIndex== END_MAP && !map.hasSpruta &&
                map.getTile(map.getMapData().getAltTile()).getRect().contains(pacMan.x+pacMan.width/2,pacMan.y+pacMan.height/2)){

            Bitmap tmp = map.getTile(map.getMapData().getAltTile()).image;
            map.getTile(map.getMapData().getAltTile()).image = map.getTile(map.getMapData().getAltTile()).bgImage;
            map.getTile(map.getMapData().getAltTile()).bgImage = tmp;
            map.hasSpruta=true;
            items.get(SYRINGE).visible=true;
            selectedItem=SYRINGE;
            actors.get(0).reloadImages(R.drawable.sex);
            audio.stopLansPlayer();
            //if(!map.hasGun) {
                items.get(SHOTGUN).visible=true;
                map.hasGun = true;
            //}
        }
        if(map.getMapData().getGunTile()>-1 && !map.hasGun &&
                map.getTile(map.getMapData().getGunTile()).getRect().contains(pacMan.x+pacMan.width/2,pacMan.y+pacMan.height/2)){
            audio.playReloadSound();
            Bitmap tmp = map.getTile(map.getMapData().getGunTile()).image;
            map.getTile(map.getMapData().getGunTile()).image = map.getTile(map.getMapData().getGunTile()).bgImage;
            map.getTile(map.getMapData().getGunTile()).bgImage = tmp;
            map.hasGun=true;
            items.get(SHOTGUN).visible=true;
            selectedItem=SHOTGUN;
        }
        if(map.mapIndex != END_MAP && map.getMapData().getAltTile()>-1 &&
                !map.hasSlegde && map.getTile(map.getMapData().getAltTile()).getRect().contains(pacMan.x+pacMan.width/2,pacMan.y+pacMan.height/2)){
            audio.playAxe();
            Bitmap tmp = map.getTile(map.getMapData().getAltTile()).image;
            map.getTile(map.getMapData().getAltTile()).image = map.getTile(map.getMapData().getAltTile()).bgImage;
            map.getTile(map.getMapData().getAltTile()).bgImage = tmp;
            map.hasSlegde=true;
            items.get(AXE).visible=true;
            selectedItem=AXE;
        }
        if(map.getMapData().getPillTile()>-1
                && !hasMedKit && LIVES < 5 &&
                map.getTile(map.getMapData().getPillTile()).getRect().contains(pacMan.x+pacMan.width/2,pacMan.y+pacMan.height/2)){
            audio.playOpenGate();
            Bitmap tmp = map.getTile(map.getMapData().getPillTile()).image;
            map.getTile(map.getMapData().getPillTile()).image = map.getTile(map.getMapData().getPillTile()).bgImage;
            map.getTile(map.getMapData().getPillTile()).bgImage = tmp;
            hasMedKit = true;
            if(LIVES<5) LIVES++;
        }
        if(map.getMapData().getRPGTile()>-1 &&
                !map.hasBazooka &&
                map.getTile(map.getMapData().getRPGTile()).getRect().contains(pacMan.x+pacMan.width/2,pacMan.y+pacMan.height/2)){
            audio.playOpenGate();
            Bitmap tmp = map.getTile(map.getMapData().getRPGTile()).image;
            map.getTile(map.getMapData().getRPGTile()).image = map.getTile(map.getMapData().getRPGTile()).bgImage;
            map.getTile(map.getMapData().getRPGTile()).bgImage = tmp;
            map.hasBazooka = true;
            items.get(RPG).visible=true;
            selectedItem=RPG;
        }
    }

    List<Ghost> hitList = new ArrayList<Ghost>();
    boolean PAC_INVULNERABLE = false;
    int PAC_IN_TICKS = 0;
    void checkCollison(){
        for(Ghost g:ghosts){
            if(!PAC_INVULNERABLE && CAN_KILL_NICKE && g.isVisible() && g.getRect().contains(pacMan.x+pacMan.width/2,pacMan.y+pacMan.height/2)){
                pacHit();
            }
        }
        if(map.mapIndex== END_MAP){
            for(Girl g:actors){
                if(!map.hasSpruta && !PAC_INVULNERABLE && CAN_KILL_NICKE && g.isVisible() && g.getRect().contains(pacMan.x+pacMan.width/2,pacMan.y+pacMan.height/2)){
                    pacHit();
                }
            }
        }
    }

    int ZOMBIE_KILLED_TICK = 0;
    Rect splatRect;
    public void zombieKilled(Ghost gh){
        gh.visible = false;
        audio.playSplat();
        ZOMBIE_KILLED_TICK=60;
        splatRect = gh.getRect();
    }

    boolean HEART = false;
    boolean HAND_ALIVE = false;
    boolean HANDS_UP = false;
    int hand_alive_tick = 0;
    int handani = 0;
    int handy,handx;
    int hand_index = 0;
    double hand_ratio = 0.2;
    RectF rotRight,rotLeft,rotUp,rotDown,fireBtn,rotPause,soundBtn;
    RectF resetBtn, altBtn;

    public void onDraw(Canvas c) {
        super.onDraw(c);
        if(paint==null) paint = new Paint(this,c.g);

        c.g.setColor(Color.BLACK);
        c.drawRect(0,0,map.cols*map.size,map.rows*map.size,paint);

        if(!MARQUEE) {
            try {
                for (Tile t : map.getGrid()) {
                    t.draw(c, paint);
                }
                map.getMapData().draw(c, paint);
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }

        // -------- ANIMATE HAND
        if(HAND_ALIVE){
            if(handani++>10 && !HANDS_UP){
                handy-=1;
                hand_index++;
                handani=0;
                hand_ratio += 0.2;
                //handy-=18;
            }
            Util.drawImage(c,map.hands[hand_index],handx,handy,
                    map.size,(int)(map.size*hand_ratio),paint);
            if(HANDS_UP){
                if(hand_alive_tick++>60){
                    HAND_ALIVE = false;
                    spawnZombie(map.HAND_TILE,true);
                }
            }
            if(hand_index>=map.hands.length-1){
                HANDS_UP = true;
            }
        }
        // -------- ANIMATE HAND

        if(!MARQUEE){
            for(Integer i:dirtTiles){
                try {
                    Util.drawImage(c, map.handEarthImage, map.grid.get(i).x,
                            map.grid.get(i).y + map.size - 16, map.size, 10, paint);
                }catch(Exception ex){}
            }
            if(pacMan!=null){
                pacMan.draw(c,paint);
                if(!GAME_OVER) {
                    pickUpStuff();
                    checkCollison();
                }
            }
            for (Ghost gh : ghosts) {
                gh.draw(c, paint);
                if(ZOMBIE_KILLED_TICK-->0){
                    Util.drawImage(c,map.splatImage,(int)splatRect.left,splatRect.top,map.size,map.size,paint);
                }else ZOMBIE_KILLED_TICK=0;
            }
            for (Ghost gh : actors) {
                gh.draw(c, paint);
            }
            if(b!=null) b.draw(c,paint);
            heliDrawer.draw(c,paint);
            //---------
        }

        ArrayList<Shot> remove = new ArrayList<Shot>();
        for(Shot s:pacMan.getShots()) {
            s.draw(c, paint);
            if (map.getTileAt(s.x, s.y) != null && map.getTileAt(s.x, s.y).wall) {
                s.x = -4;
                s.y = -4;
            }
            for (Ghost gh : ghosts) {
                if (gh.getRect().contains(s.x, s.y) && gh.isVisible()) {
                    zombieKilled(gh);
                    if(!s.rpg) {
                        s.x = -4;
                        s.y = -4;
                    }
                }
            }
            if (ghostsAlive() == 0 && map.mapIndex == map.START_MAP) {
                if(!openGatedPlayed) audio.playOpenGate();
                openGatedPlayed=true;
                map.grid.get(8).image = map.getMapData().getDefaultBgImage();
                map.grid.get(8).wall = false;
                if (map.grid.get(8).getRect().contains(pacMan.x + (map.size / 2), pacMan.y + (map.size / 2))) {
                    nextLevel();
                    return;
                }
            } else if ((ghostsAlive() == 0 && map.mapIndex == map.BASEMENT_MAP) ||
                    (ghostsAlive() == 0 && map.mapIndex == map.GRAVE_YARD_MAP)
                    || (ghostsAlive() == 0 && map.mapIndex == map.COURT_YARD_MAP)) {
                if(!openGatedPlayed) audio.playOpenGate();
                openGatedPlayed=true;
                map.grid.get(8).image = map.getMapData().getDefaultBgImage();
                map.grid.get(8).wall = false;
                if (map.grid.get(8).getRect().contains(pacMan.x + (map.size / 2), pacMan.y + (map.size / 2))) {
                    nextLevel();
                    return;
                }
            }else if(ghostsAlive() == 0 && (map.mapIndex == map.WOODS_MAP ||
                    map.mapIndex == map.HOSPITAL_MAP) & record) {
                if (!openGatedPlayed) audio.playOpenGate();
                openGatedPlayed = true;
                map.grid.get(8).image = map.getMapData().getDefaultBgImage();
                map.grid.get(8).wall = false;
                if (map.grid.get(8).getRect().contains(pacMan.x + (map.size / 2), pacMan.y + (map.size / 2))) {
                    nextLevel();
                    return;
                }
            }else if(map.hasSpruta && ghostsAlive() == 0 &&
                    map.mapIndex == END_MAP & record && heliDrawer.getMode()==HeliDrawer.HeliMode.HELI_OFF) {
                audio.playHelicopter();
                heliDrawer.setMode(HeliDrawer.HeliMode.HELI_IN);
            }
        }

        if(MARQUEE){
            textTicker.draw(c,paint);
            splash.draw(c,paint);
        }
        ctrl.draw(c,paint);

        if(HEART){
            int in = pacMan.getTileIndex();
            if(in>-1) {
                Util.drawImage(c, map.heartImage, map.grid.get(in).getRectF(), paint);
            }
        }

        if(!MARQUEE && map.INIT_TICK_COUNTDOWN>0 && map.getReadyImage()!=null){
            Util.drawImage(c,map.getReadyImage(),(getWidth()/2)-50, (getHeight()/2)-180, 100,75,paint);
        }
        if(GAME_OVER){
            Util.drawImage(c,map.getGameOverImage(),(getWidth()/2)-100, (getHeight()/2)-180, 200,100,paint);
        }

        if(!MARQUEE) {
            c.g.setColor(Color.GRAY);
            paint.setStyle(Paint.Style.FILL);
            c.drawRect(0, (map.rows * map.size), (map.cols * map.size) + 8, (map.rows * map.size) + 60, paint);
            int lw = 100;
            int lh = 50;
            Util.drawImage(c, map.getLifeImage(LIVES), 4, (map.rows * map.size)+4, lw, lh, paint);

            for (int i = 0; i < items.size(); i++) {
                if(!items.get(i).visible) continue;
                Util.drawImage(c, items.get(i).image, ((map.cols * map.size) - (map.size + 12)) - (i * (map.size*2)+20),
                        (map.rows * map.size) + 10,
                        map.size+(map.size/2),
                        map.size+(map.size/2), paint);

                if(i==selectedItem) {
                    paint.setStyle(Paint.Style.STROKE);
                    paint.setStrokeWidth(3);
                    c.g.setColor(Color.RED);
                    RectF rft = Util.getRectF(((map.cols * map.size) - (map.size + 12)) - (i * (map.size*2)+20),
                            (map.rows * map.size) + 10,
                            map.size+(map.size/2),
                            map.size+(map.size/2));
                    c.drawRect(rft,paint);
                }
            }
        }

        if(map.debug){
            c.g.setColor(Color.RED);
            paint.setStrokeWidth(3);
            paint.setStyle(Paint.Style.STROKE);
            c.drawRect(2,2,getWidth()-2,getHeight()-2,null);
        }

    }

    public void drawCenteredString(String string, Canvas canvas, Paint p){
        //p.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(string, canvas.getWidth()/2, canvas.getHeight()/2  , p);
    }

}

