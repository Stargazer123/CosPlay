package com.crazedout.cosplay.pojo.test;

import com.crazedout.cosplay.*;
import com.crazedout.cosplay.Scrollable;
import com.crazedout.cosplay.pojo.*;
import com.crazedout.cosplay.editor.CosPlay;
import com.crazedout.cosplay.sprites.DefaultUserSprite;
import com.crazedout.cosplay.sprites.DefaultZombieSprite;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.dnd.DropTarget;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.nio.file.Files;
import java.util.List;

import static com.crazedout.cosplay.GamePlay.GameState.GAME_ON;
import static javax.swing.JOptionPane.ERROR_MESSAGE;

public class TestGame extends JPanel implements GamePanel, GridListener, GamePlayListener {

    List<Tile> grid;
    Map map;
    String userDir = System.getProperty("user.home") + File.separatorChar + "cosplay";
    public GameThread runner;
    POJOMapLoader pojoLoader;
    File dropFile;
    DefaultGamePlay gamePlay;
    Image cos;

    public TestGame(){
        super(null);
        map = new DefaultMap(16,17,32);
        try {
            cos = ImageIO.read(getClass().getResourceAsStream("/cos.png"));
        }catch(Exception ex){
            ex.printStackTrace();
        }
        setFocusable(true);
        grabFocus();
        pojoLoader = new POJOMapLoader();
        DragDropListener myDragDropListener = new DragDropListener(this);
        new DropTarget(this, myDragDropListener);
        gamePlay = new DefaultGamePlay();
        try {
            cos = ImageIO.read(getClass().getResourceAsStream("/cos.png"));
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public TestGame(Map map){

        super(null);
        this.map = map;
        setFocusable(true);
        grabFocus();
        DefaultUserSprite usp = (DefaultUserSprite)map.getUserSprite();

        gamePlay = new DefaultGamePlay();
        if(map.getSprites().size()>0) {
            KeyControls keyControls = new KeyControls(usp);
            keyControls.setFireKey1(KeyEvent.VK_P);
            keyControls.setJumpKey(KeyEvent.VK_SPACE);
            keyControls.setStopKey(KeyEvent.VK_ESCAPE);
            addKeyListener(keyControls);
            usp.addSpriteListener(gamePlay);
            gamePlay.addActorSprites(map.getSprites());
        }
        if(map.getBackgroundColor()!=null) {
            setBackground((Color)map.getBackgroundColor());
        }
        GamePlay.getGamePlay().setGameState(GAME_ON);

        runner = new GameThread(this,10);
        runner.start();
    }

    public void setCosPack(String file){
        try {
            ZipResource res = new ZipResource(file);
            List<String> maps = res.getMapNames();
            map = new DefaultMap(res);
            GamePlay.setAudio(new POJOAudio(map.getResources()));
            GamePlay.getAudio().playSound(Audio.OPEN_GATE);
            ((DefaultMap)map).addGridListener(this);
            pojoLoader.loadMap(map, maps.get(0), res);
            Sprite userSprite = map.getUserSprite();
            KeyControls keyControls = new KeyControls(userSprite, this, map);
            keyControls.setFireKey1(KeyEvent.VK_P);
            keyControls.setJumpKey(KeyEvent.VK_SPACE);
            keyControls.setStopKey(KeyEvent.VK_ESCAPE);
            addKeyListener(keyControls);
            if(userSprite!=null) userSprite.addSpriteListener(gamePlay);
            gamePlay.addActorSprites(map.getSprites());
            gamePlay.addGamePlayListener((DefaultUserSprite)userSprite);
            runner = new GameThread(this,8);
            runner.start();
            if(map.getBackgroundColor()!=null) {
                setBackground((Color)map.getBackgroundColor());
            }
            map.initSprites();
            GamePlay.getGamePlay().setGameState(GAME_ON);
        }catch(Exception ex){
            ex.printStackTrace();
            showErrorDialog("Error", ex.getMessage());
        }
    }

    public void drop(File file){
        dropFile=file;
        setCosPack(file.getAbsolutePath());
        requestFocus();
    }

    private static void copy(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        while (true) {
            int readCount = in.read(buffer);
            if (readCount < 0) {
                break;
            }
            out.write(buffer, 0, readCount);
        }
    }

    @Override
    public synchronized Dimension getPreferredSize(){
        Dimension dim = null;
        if(((Scrollable)map).getViewPort()==null) {
            int c = map.getCols();
            int r = map.getRows();
            int w = c * map.getSize();
            int h = r * map.getSize();
            dim = new Dimension(w+12,h+2);
        }else{
            ViewPort r = ((Scrollable)map).getViewPort();
            dim = new Dimension(r.getPixWidth(),r.getPixHeight());
        }
        return dim;
    }

    public static Object[] openGameMode(Map map){
        JFrame f = new JFrame("CosPlay Test Game");
        try {
            Image icon = ImageIO.read(f.getClass().getResource("/cos.png"));
            f.setIconImage(icon);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        final TestGame tg = new TestGame(map);
        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                tg.runner.stop();
            }
        });
        f.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if(e.getKeyCode()==KeyEvent.VK_F1){
                    map.getSprites().get(0).snapToGrid(map);
                    tg.repaint();
                }
            }
        });

        f.add(tg);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        Object[] obj = new Object[]{f,tg};
        return obj;
    }

    public static void showErrorDialog(String title, String message){
        JOptionPane.showMessageDialog(CosPlay.frame, message, title, ERROR_MESSAGE);
    }

    @Override
    public void tick() {
        if(GamePlay.getGamePlay().getGameState()== GAME_ON) {
            for (Sprite s : map.getSprites()) {
                s.move(map);
            }
        }
        repaint();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        POJOGraphics pg = new POJOGraphics(g,this);
        if(GamePlay.getGamePlay().getGameState()==GAME_ON) {
            if (this.map != null) {
                this.map.draw(pg);
            }
        }else{
            g.drawImage(cos, (getWidth()/2-cos.getWidth(this)/2),
                    (getHeight()/2-cos.getHeight(this)/2),this);
            g.setFont(new Font("monospaced", Font.BOLD, 14));
            drawCenteredString("Drag and drop cosplay.zip file here and press F1 to begin",
            getWidth(),getHeight(),(getHeight()/2-cos.getHeight(this)/2)+cos.getHeight(this)+40,g);
        }
    }

    public static void drawCenteredString(String s, int w, int h, int y, Graphics g) {
        try {
            FontMetrics fm = g.getFontMetrics();
            int x = (w - fm.stringWidth(s)) / 2 ;
            g.drawString(s, x, y);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }


    static JFrame frame;
    public static void main(String argv[]){
        JFrame t = new JFrame("CosPlay Test Game");
        try {
            Image icon = ImageIO.read(t.getClass().getResource("/cos.png"));
            t.setIconImage(icon);
        }catch(Exception ex){
            //ex.printStackTrace();
        }
        frame=t;
        t.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        TestGame tg = new TestGame();
        t.add(tg);
        t.pack();
        t.setLocationRelativeTo(null);
        t.setVisible(true);
        /*
        tg.setCosPack(System.getProperty("user.home") + File.separatorChar + "cosplay" +
                File.separatorChar + "maps" + File.separatorChar + "cosplay.zip");

         */
    }

    @Override
    public void gridChanged() {
        frame.setResizable(true);
        invalidate();
        validate();
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
    }

    @Override
    public void gamePlayChanged(GamePlay gp) {

    }
}
