package com.crazedout.adapter.android;

import com.crazedout.adapter.android.stg.GameView;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Activity extends JFrame {

    GameView gameView;

    public Activity(){

        try{
            Image img = ImageIO.read(getClass().getResource("/icon.png"));
            setIconImage(img);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        setTitle("St:Gertruds Zombieland");
    }

    public void startActiviy(Intent intent){

    }
    public static void main(String argv[]) throws Exception {

        Activity a = new Activity();
        /*
        Object options[] = {"Fullscreen mode","Windows mode","Cancel"};
        int FULLSCREEN = JOptionPane.showOptionDialog(null,"Choose screen mode","SGS", JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,new ImageIcon(a.getIconImage()),options, options[0]);

        if(FULLSCREEN==2 || FULLSCREEN==-1){
            System.exit(0);
        }*/
        int FULLSCREEN=1;

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        a.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int height = 800;//(int)(dim.height*0.9);
        int width = 450;//(int)(height*0.57);
        a.setSize(width, height);
        GameView gameView = new GameView(a, width, height);
        a.add("Center", gameView);
        a.setLocationRelativeTo(null);
        final GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        if(FULLSCREEN==0) {
            JPanel l = new JPanel(){
                public synchronized Dimension getPreferredSize(){
                    return new Dimension(287,100);
                }
            };
            JPanel r = new JPanel(){
                public synchronized Dimension getPreferredSize(){
                    return new Dimension(287,100);
                }
            };
            r.setBackground(Color.BLACK);
            l.setBackground(Color.BLACK);
            a.add("East",r);
            a.add("West",l);
            a.setUndecorated(true);
            gd.setFullScreenWindow(a);
            DisplayMode modes[] = gd.getDisplayModes();
            for (DisplayMode dm : modes) {
                if (dm.getWidth() == 1024 && dm.getHeight() == 768) {
                    gd.setDisplayMode(dm);
                    break;
                }
            }
        }

        a.setVisible(true);
        a.setResizable(false);

    }
}
