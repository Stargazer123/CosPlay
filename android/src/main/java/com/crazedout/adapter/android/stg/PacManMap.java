package com.crazedout.adapter.android.stg;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.crazedout.adapter.android.*;

public class PacManMap  {

    protected int size = 63;
    protected int cols = 17;
    protected int rows = 16;
    protected int offset = 0;

    int INIT_TICKS = 120;
    int INIT_TICK_COUNTDOWN = INIT_TICKS;
    int START_TILE = 263-16;
    protected Bitmap readyImage, gameOverImage;
    protected Bitmap graveImage1, graveImage2, graveImage3, gravelImage;
    protected Bitmap stonesImage, image, greenTreeImage,grassImage;
    protected Bitmap gunImage,diamondImage,zombie1;
    protected Bitmap lifeFullImage,life1Image,life2Image,life3Image,life4Image,life5Image;
    protected Bitmap gates1Image,gates2Image;
    protected Bitmap z1_rightImage,z1_leftImage,z2_rightImage,z2_leftImage,z3_rightImage,z3_leftImage;
    protected Bitmap pillerImage, sledgeImage, handImage;
    protected Bitmap hand1Image,hand2Image,hand3Image,hand4Image,handEarthImage,heartImage,hospitalImage;
    protected Bitmap splatImage, gates3Image,chapelImage,textur1,textur2,textur3, textur4;
    protected Bitmap bed1, bed2, bed3, bed4, drz1, drz2, dr_dead1,dr_dead2,bazookaImage,sprutaImage;
    protected Bitmap heliImage;
    protected Bitmap bossRight1,bossRight2,bossRight3,bossRight4,bossRight5;
    protected Bitmap bossLeft1,bossLeft2,bossLeft3,bossLeft4,bossLeft5;
    protected Bitmap bossBorn1,bossBorn2,bossBorn3,bossBorn4,bossBorn5,bossBorn6;

    public static final int START_MAP = 0;
    public static final int BASEMENT_MAP = 1;
    public static final int GRAVE_YARD_MAP = 2;
    public static final int COURT_YARD_MAP = 3;
    public static final int WOODS_MAP = 4;
    public static final int HOSPITAL_MAP = 5;
    public static final int END_MAP = 6;

    boolean GRID_LOADING = false;

    //int GUN_TILE = 30;
    //int SLEDGE_TILE = 51;
    int HAND_TILE;
    boolean hasGun = false;
    boolean hasSlegde = false;
    boolean hasBazooka = false;
    boolean hasSpruta = false;
    Random ran = new Random();
    boolean debug = false;

    int notAllowed[] = {
            0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,31,32,47,48,63,64,79,80,95,96,111,112,127,128,143,144,159,160,175,176,191,192,207,208,223,224,239,240,255,256,257,258,259,260,261,262,263,264,265,266,267,268,269,270,271,
    };


    protected List<Tile> grid;

    Bitmap hands[];
    GameView view;
    Bitmap health[];
    List<MapData> mapDataList;
    int mapIndex = 0;

    public PacManMap(GameView view){
        mapDataList = new ArrayList<MapData>();
        grid = new ArrayList<Tile>();
        this.view = view;
        size = view.displayMetrics.widthPixels/cols;

        try {
            readyImage = BitmapFactory.decodeResource(view.getResources(), R.drawable.ready2);
            gameOverImage = BitmapFactory.decodeResource(view.getResources(), R.drawable.gameover2);
            graveImage1 = BitmapFactory.decodeResource(view.getResources(), R.drawable.grave1);
            graveImage2 = BitmapFactory.decodeResource(view.getResources(), R.drawable.grave2 );
            graveImage3 = BitmapFactory.decodeResource(view.getResources(), R.drawable.grave3 );
            gravelImage = BitmapFactory.decodeResource(view.getResources(), R.drawable.gravel);
            image = BitmapFactory.decodeResource(view.getResources(), R.drawable.wall);
            greenTreeImage = BitmapFactory.decodeResource(view.getResources(), R.drawable.green_tree);
            grassImage = BitmapFactory.decodeResource(view.getResources(), R.drawable.grass);
            gunImage = BitmapFactory.decodeResource(view.getResources(), R.drawable.shotgun1);
            life1Image = BitmapFactory.decodeResource(view.getResources(), R.drawable.life1);
            life2Image = BitmapFactory.decodeResource(view.getResources(), R.drawable.life2);
            life3Image = BitmapFactory.decodeResource(view.getResources(), R.drawable.life3);
            life4Image = BitmapFactory.decodeResource(view.getResources(), R.drawable.life4);
            life5Image = BitmapFactory.decodeResource(view.getResources(), R.drawable.life5);
            lifeFullImage = BitmapFactory.decodeResource(view.getResources(), R.drawable.life6);
            health = new Bitmap[] {life1Image,life2Image,life3Image,life4Image,life5Image,lifeFullImage};
            gates1Image = BitmapFactory.decodeResource(view.getResources(), R.drawable.gates);
            gates2Image = BitmapFactory.decodeResource(view.getResources(), R.drawable.gates2);
            gates3Image = BitmapFactory.decodeResource(view.getResources(), R.drawable.gates3);
            z1_rightImage = BitmapFactory.decodeResource(view.getResources(), R.drawable.z1_right);
            z1_leftImage = BitmapFactory.decodeResource(view.getResources(), R.drawable.z1_left);
            z2_rightImage = BitmapFactory.decodeResource(view.getResources(), R.drawable.z2_right);
            z2_leftImage = BitmapFactory.decodeResource(view.getResources(), R.drawable.z2_left);
            z3_rightImage = BitmapFactory.decodeResource(view.getResources(), R.drawable.z3_right);
            z3_leftImage = BitmapFactory.decodeResource(view.getResources(), R.drawable.z3_left);
            pillerImage = BitmapFactory.decodeResource(view.getResources(), R.drawable.medkit);
            sledgeImage = BitmapFactory.decodeResource(view.getResources(), R.drawable.sledge);
            handImage = BitmapFactory.decodeResource(view.getResources(), R.drawable.hand);
            hand1Image = BitmapFactory.decodeResource(view.getResources(), R.drawable.hand);
            hand2Image = BitmapFactory.decodeResource(view.getResources(), R.drawable.hand1);
            hand3Image = BitmapFactory.decodeResource(view.getResources(), R.drawable.hand2);
            hand4Image = BitmapFactory.decodeResource(view.getResources(), R.drawable.hand3);
            handEarthImage = BitmapFactory.decodeResource(view.getResources(), R.drawable.hand_earth);
            heartImage = BitmapFactory.decodeResource(view.getResources(), R.drawable.heart);
            hospitalImage = BitmapFactory.decodeResource(view.getResources(), R.drawable.hospital);
            splatImage = BitmapFactory.decodeResource(view.getResources(), R.drawable.splat);
            chapelImage = BitmapFactory.decodeResource(view.getResources(), R.drawable.chapel);
            textur1 = BitmapFactory.decodeResource(view.getResources(), R.drawable.textur1);
            textur2 = BitmapFactory.decodeResource(view.getResources(), R.drawable.textur2);
            textur3 = BitmapFactory.decodeResource(view.getResources(), R.drawable.textur3);
            textur4 = BitmapFactory.decodeResource(view.getResources(), R.drawable.textur4);
            bed1 = BitmapFactory.decodeResource(view.getResources(), R.drawable.bed1);
            bed2 = BitmapFactory.decodeResource(view.getResources(), R.drawable.bed2);
            bed3 = BitmapFactory.decodeResource(view.getResources(), R.drawable.bed3);
            bed4 = BitmapFactory.decodeResource(view.getResources(), R.drawable.bed4);
            drz1 = BitmapFactory.decodeResource(view.getResources(), R.drawable.dr_zombie);
            drz2 = BitmapFactory.decodeResource(view.getResources(), R.drawable.dr_zombie1);
            dr_dead1 = BitmapFactory.decodeResource(view.getResources(), R.drawable.doktor7);
            bazookaImage = BitmapFactory.decodeResource(view.getResources(), R.drawable.bazooka);
            sprutaImage = BitmapFactory.decodeResource(view.getResources(), R.drawable.syringe);
            heliImage = BitmapFactory.decodeResource(view.getResources(), R.drawable.helikopter1);

            bossRight1 = BitmapFactory.decodeResource(view.getResources(), R.drawable.boss1);
            bossRight2 = BitmapFactory.decodeResource(view.getResources(), R.drawable.boss2);
            bossRight3 = BitmapFactory.decodeResource(view.getResources(), R.drawable.boss3);
            bossRight4 = BitmapFactory.decodeResource(view.getResources(), R.drawable.boss4);
            bossRight5 = BitmapFactory.decodeResource(view.getResources(), R.drawable.boss5);

            bossLeft1 = BitmapFactory.decodeResource(view.getResources(), R.drawable.boss6);
            bossLeft2 = BitmapFactory.decodeResource(view.getResources(), R.drawable.boss7);
            bossLeft3 = BitmapFactory.decodeResource(view.getResources(), R.drawable.boss8);
            bossLeft4 = BitmapFactory.decodeResource(view.getResources(), R.drawable.boss9);
            bossLeft5 = BitmapFactory.decodeResource(view.getResources(), R.drawable.boss10);

            bossBorn1 = BitmapFactory.decodeResource(view.getResources(), R.drawable.start1);
            bossBorn2 = BitmapFactory.decodeResource(view.getResources(), R.drawable.start2);
            bossBorn3 = BitmapFactory.decodeResource(view.getResources(), R.drawable.start3);
            bossBorn4 = BitmapFactory.decodeResource(view.getResources(), R.drawable.start4);
            bossBorn5 = BitmapFactory.decodeResource(view.getResources(), R.drawable.start5);
            bossBorn6 = BitmapFactory.decodeResource(view.getResources(), R.drawable.start6);

            hands = new Bitmap[]{hand1Image,hand2Image,hand3Image,hand4Image,handImage};
        }catch(Exception ex){
            ex.printStackTrace();
        }

        mapDataList.add(new StartMap(this));
        mapDataList.add(new BasementMap(this));
        mapDataList.add(new GraveYardMap(this));
        mapDataList.add(new CourtYardMap(this));
        mapDataList.add(new WoodsMap(this));
        mapDataList.add(new HospitalMap(this));
        mapDataList.add(new EndMap(this));

        initGrid();
        setBuddies(8,263);
    }

    public synchronized void initGrid(){
        GRID_LOADING = true;
        int n = 0;
        grid.clear();
        for(int c = 0; c < cols; c++){
            for(int r = 0; r < rows; r++){
                Tile t = new Tile(offset+(c*size),offset+(r*size),size,size,n++);
                t.setImage(getMapData().getDefaultImage());
                t.wall = isMapWall(t.index);

                if(t.index==getMapData().getGunTile()) {
                    t.setImage(gunImage);
                    t.bgImage = getMapData().getDefaultBgImage();
                }
                if(t.index==getMapData().getAltTile()) {
                    t.bgImage = grassImage;
                    t.image = sledgeImage;
                }
                if(isWall(t)) {
                    if(getMapData().getWallImage()!=null){
                        t.image = getMapData().getWallImage();
                    }else {
                        t.image = image;
                    }
                    t.wall = true;
                    t.bgImage = grassImage;
                }
                if(isGrave(t.index)) {
                    Random ran = new Random();
                    int g = (int) Math.floor(Math.random() * (3 - 1 - 0 + 1) + 0);
                    if(getMapData().isGraveImage()!=null){
                        t.image = getMapData().isGraveImage();
                        t.bgImage = gravelImage;
                        t.wall=false;
                    }else {
                        Bitmap[] imgs = {graveImage3, graveImage2, graveImage1};
                        t.image = imgs[g];
                        t.bgImage = grassImage;
                        t.wall=true;
                    }
                }
                if(isTree(t.index)) {
                    t.image = greenTreeImage;
                    t.bgImage = getMapData().getDefaultBgImage();
                    t.wall=true;
                }
                if(getMapData().getPillTile() == t.index) {
                    t.image = pillerImage;
                    t.bgImage = getMapData().getDefaultBgImage();
                    t.wall=false;
                }
                if(getMapData().getRPGTile() == t.index) {
                    t.image = bazookaImage;
                    t.bgImage = getMapData().getDefaultBgImage();
                    t.wall=false;
                }
                if(isGrass(t.index)) {
                    t.image = grassImage;
                    t.wall=isMapWall(t.index);
                }
                if(isGate(t.index)){
                    t.image = gates3Image;
                    t.bgImage = getMapData().getDefaultBgImage();
                    t.wall=true;
                }
                grid.add(t);
            }
        }
        grid.get(getMapData().getExitTiles()[0]).image = gates1Image;
        grid.get(getMapData().getExitTiles()[0]).bgImage = gravelImage;
        grid.get(getMapData().getExitTiles()[1]).image = gates2Image;
        grid.get(getMapData().getExitTiles()[1]).bgImage = gravelImage;
        GRID_LOADING = false;
    }

    protected MapData getMapData(){
        return mapDataList.get(this.mapIndex);
    }

    public Tile getRandTile(){
        List<Tile> tiles = new ArrayList<Tile>();
        for(Tile t:grid){
            if(!t.wall){
                tiles.add(t);
            }
        }
        int r = Math.round((r = (int) Math.floor(Math.random() * (tiles.size() - 1 - 0 + 1) + 0)));
        return tiles.get(r);
    }

    public Bitmap getLifeImage(int lives){
        if(lives < health.length && lives>-1) {
            return health[lives];
        }else{
            return health[0];
        }
    };

    public TextTicker getTextTicker(){
        return new TextTicker(view,(rows*size)+40);
    }

    public Bitmap[] getWallImages(){
        Bitmap i[] ={image,grassImage,greenTreeImage,graveImage1,graveImage2,graveImage3,stonesImage};
        return i;
    }

    public void setBuddies(int b1, int b2){
        grid.get(b1).setBuddyTile(grid.get(b2));
        grid.get(b2).setBuddyTile(grid.get(b1));
    }

    public List<Tile> getGrid(){
        return this.grid;
    }

    public Tile getTile(int index){
        return this.grid.get(index);
    }

    public Bitmap getReadyImage(){
        return readyImage;
    }

    public Bitmap getGameOverImage(){
        return gameOverImage;
    }

    public Tile getTileAt(int x, int y){
        for(Tile t:grid){
            if(t.getRect().contains(x,y)) return t;
        }
        return null;
    }

    public int getRows(){
        return this.rows;
    }

    public int getWidth(){
        return cols*size;
    }

    public int getHeight(){
        return rows*size;
    }

    public int getCols(){
        return this.cols;
    }

    public int getOffset(){
        return this.offset;
    }

    public int getSize(){
        return this.size;
    }


    public boolean isGrass(int index){
        for(Integer i:getMapData().getGrassTiles()){
            if(index==i) return true;
        }
        return false;
    }

    public boolean isTree(int index){
        for(Integer i:getMapData().getTreesTiles()){
            if(index==i) return true;
        }
        return false;
    }

    public boolean isGate(int index){
        for(Integer i:getMapData().getGateTiles()){
            if(index==i) return true;

        }
        return false;
    }

    public boolean isGrave(int index){
        for(Integer i:getMapData().getGraveTiles()){
            if(index==i) return true;
        }
        return false;
    }

    public boolean isExitTile(int index){
        for(Integer i:getMapData().getExitTiles()){
            if(index==i) return true;
        }
        return false;
    }

    public boolean isWall(Tile t) {
        return this.isWall(t.index);
    }

    public boolean isWall(int index) {

        if(getMapData().getWallsTiles().length==0) return true;

        for(Integer i:getMapData().getWallsTiles()){
            if(index==i) return true;
        }
        return false;
    }

    public boolean isMapWall(int index) {

        for(Integer i:getMapData().getMapWalls()){
            if(index==i) return true;
        }
        return false;
    }

    public boolean isNotAllowed(int index){
        for(Integer i:notAllowed){
            if(i==index) return true;
        }
        return false;
    }



}
