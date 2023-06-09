package com.crazedout.adapter.android.stg;

import com.crazedout.adapter.android.*;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Controls {

    Sprite sprite;
    GameView view;
    Bitmap pauseBtn1,pauseBtn2,restartBtn1,restartBtn2;
    Bitmap upImage1,upImage2,downImage1,downImage2,leftImage1,leftImage2;
    Bitmap rightImage1,rightImage2,fireImage1,fireImage2,pauseImage1, pauseImage2;
    Bitmap selectImage1, selectImage2, soundImage1, soundImage2;

    boolean upDown,leftDown,rightDown,downDown,fireDown,resetDown,altDown,pauseDown;
    boolean soundDown=true;
    MouseAdapter mouseAdapter;

    public Controls(GameView view){
        this.view = view;
        this.loadResources();

        view.addMouseListener((mouseAdapter=new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent event) {
                super.mousePressed(event);
                if(view.rotLeft.contains(event.getX(),event.getY())){
                    view.pacMan.requestDirection(Sprite.moves.LEFT);
                    leftDown=true;
                    return;
                }
                else if(view.rotRight.contains(event.getX(),event.getY())){
                    view.pacMan.requestDirection(Sprite.moves.RIGHT);
                    rightDown=true;
                    return;
                }
                else if(view.rotUp.contains(event.getX(),event.getY())){
                    view.pacMan.requestDirection(Sprite.moves.UP);
                    upDown=true;
                    return;
                }
                else if(view.rotDown.contains(event.getX(),event.getY())){
                    view.pacMan.requestDirection(Sprite.moves.DOWN);
                    downDown=true;
                    return;
                }
                else if(view.fireBtn.contains(event.getX(),event.getY())){
                    if(view.MARQUEE) view.start();
                    else view.pacMan.fire();
                    fireDown=true;
                } else if(view.soundBtn.contains(event.getX(),event.getY())){
                    if(view.PAUSE) return;
                    soundDown=!soundDown;
                    if(!soundDown) {
                        if(view.MARQUEE) view.audio.stopTheme();
                        if(!view.MARQUEE) view.audio.stopBg();
                    }else {
                        if (view.MARQUEE) view.audio.playTheme();
                        if (!view.MARQUEE) view.audio.playBgSound();
                    }
                }
                else if(view.splash.getCosRect()!=null && view.splash.getCosRect().contains(event.getX(),event.getY())){
                    /*
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse("https://crazedout.com"));
                    view.act.startActivity(intent);
                     */
                }
                else if(view.resetBtn.contains(event.getX(),event.getY())){
                    if(view.map.debug){
                        view.ghosts.clear();
                        view.nextLevel();
                    }
                }
                else if(view.altBtn.contains(event.getX(),event.getY())){
                    if(view.map.debug && (view.map.grid.get(237).getRect().contains(view.pacMan.x,view.pacMan.y) ||
                            view.map.grid.get(254).getRect().contains(view.pacMan.x,view.pacMan.y))){
                        view.map.debug=false;
                        System.out.println("Alt:" + view.map.debug + " " + view.pacMan.getTileIndex() + " OFF");
                    }
                    else if(!view.map.debug && (view.map.grid.get(237).getRect().contains(view.pacMan.x,view.pacMan.y) ||
                            view.map.grid.get(254).getRect().contains(view.pacMan.x,view.pacMan.y))){
                        view.map.debug = true;
                        view.map.hasGun=true;
                        System.out.println("Alt:" + view.map.debug + " " + view.pacMan.getTileIndex() + " ON");
                    }
                    view.alt();
                    altDown = true;
                    view.repaint();
                } else if(view.rotPause.contains(event.getX(),event.getY())){
                    view.pause();
                    pauseDown = true;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                upDown=false;
                downDown=false;
                resetDown=false;
                rightDown=false;
                leftDown=false;
                fireDown=false;
                altDown=false;
                pauseDown=false;
            }
        }));

        view.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                switch(e.getKeyCode()){
                    case KeyEvent.VK_SPACE:
                        if(view.MARQUEE) view.start();
                        else view.pacMan.fire();
                        fireDown=true;
                    break;
                    case KeyEvent.VK_LEFT:
                        view.pacMan.requestDirection(Sprite.moves.LEFT);
                        leftDown=true;
                        break;
                    case KeyEvent.VK_RIGHT:
                        view.pacMan.requestDirection(Sprite.moves.RIGHT);
                        rightDown=true;
                        break;
                    case KeyEvent.VK_UP:
                        view.pacMan.requestDirection(Sprite.moves.UP);
                        upDown=true;
                        break;
                    case KeyEvent.VK_DOWN:
                        view.pacMan.requestDirection(Sprite.moves.DOWN);
                        downDown=true;
                        break;
                    case KeyEvent.VK_F2:
                        if(view.map.debug){
                            view.ghosts.clear();
                            view.nextLevel();
                        }
                        break;
                    case KeyEvent.VK_F1:
                        if(view.map.debug && (view.map.grid.get(237).getRect().contains(view.pacMan.x,view.pacMan.y) ||
                                view.map.grid.get(254).getRect().contains(view.pacMan.x,view.pacMan.y))){
                            view.map.debug=false;
                        }
                        else if(!view.map.debug && (view.map.grid.get(237).getRect().contains(view.pacMan.x,view.pacMan.y) ||
                                view.map.grid.get(254).getRect().contains(view.pacMan.x,view.pacMan.y))){
                            view.map.debug = true;
                            view.map.hasGun=true;
                        }
                        view.alt();
                        altDown = true;
                        break;
                    case KeyEvent.VK_ESCAPE:
                        System.exit(0);
                        pauseDown = true;
                        break;
                    case KeyEvent.VK_F11:
                        view.heliDrawer.setMode(HeliDrawer.HeliMode.HELI_IN);
                        break;
                    case KeyEvent.VK_F12:
                        view.heliDrawer.setMode(HeliDrawer.HeliMode.HELI_OUT);
                        break;
                }
            }
            public void keyReleased(KeyEvent e) {
                super.keyPressed(e);
                upDown=false;
                downDown=false;
                resetDown=false;
                rightDown=false;
                leftDown=false;
                fireDown=false;
                altDown=false;
                pauseDown=false;
            }
        });

    }

    public void setSprite(Sprite s){

    }

    int n = 0;
    public void draw(Canvas c, Paint paint){

        int offset = view.map.rows*view.map.size + 140;
        int per = view.displayMetrics.heightPixels - (view.map.rows*view.map.size + 180);
        int size = per/4;//view.map.size;

        view.resetBtn = new RectF(20,offset,20+size,offset+size);
        view.soundBtn = new RectF(20,offset+size+size,20+size,offset+size+size+size);
        view.altBtn = new RectF(view.getWidth()-(size+20),offset,view.getWidth()-(size+20)+size,offset+size);

        view.rotUp = new RectF(view.getWidth()/2-(size/2),offset,view.getWidth()/2-(size/2)+size,offset+size);
        view.fireBtn = new RectF(view.getWidth()/2-(size/2),offset+size,view.getWidth()/2-(size/2)+size,offset+size+size);
        view.rotDown = new RectF(view.getWidth()/2-(size/2),offset+size+size,view.getWidth()/2-(size/2)+size,offset+size+size+size);

        view.rotPause = new RectF(view.getWidth()-(size+20),
                offset+size+size,
                view.getWidth()-(size+20)+size,
                offset+size+size+size);

        view.rotLeft = new RectF(view.getWidth()/2-(size*2)+size/2,
                offset+size,view.getWidth()/2-size/2,offset+size+size);

        view.rotRight = new RectF(view.getWidth()/2-(size/2)+size,offset+size,view.getWidth()/2-(size/2)+size+size,offset+size+size);

        if(view.map.debug)
            Util.drawImage(c,resetDown?restartBtn2:restartBtn1,view.resetBtn,paint);

        Util.drawImage(c,soundDown?soundImage2:soundImage1,view.soundBtn,paint);
        Util.drawImage(c,altDown?selectImage2:selectImage1,view.altBtn,paint);

        Util.drawImage(c,upDown?upImage2:upImage1,view.rotUp,paint);

        if(view.MARQUEE && ((n++)==30)){
            fireDown = !fireDown;
            n=0;
        }
        Util.drawImage(c,fireDown?fireImage2:fireImage1,view.fireBtn,paint);

        Util.drawImage(c,downDown?downImage2:downImage1,view.rotDown,paint);
        Util.drawImage(c,leftDown?leftImage2:leftImage1,view.rotLeft,paint);
        Util.drawImage(c,rightDown?rightImage2:rightImage1,view.rotRight,paint);
        Util.drawImage(c,pauseDown?pauseImage2:pauseImage1,view.rotPause,paint);

    }

    public void loadResources(){
        try {
            restartBtn1  = BitmapFactory.decodeResource(view.getResources(), R.drawable.restart);
            restartBtn2  = BitmapFactory.decodeResource(view.getResources(), R.drawable.restart2);
            pauseBtn1    = BitmapFactory.decodeResource(view.getResources(), R.drawable.pause);
            pauseBtn2    = BitmapFactory.decodeResource(view.getResources(), R.drawable.pause2);
            upImage1    = BitmapFactory.decodeResource(view.getResources(), R.drawable.up);
            upImage2    = BitmapFactory.decodeResource(view.getResources(), R.drawable.up2);
            downImage1   = BitmapFactory.decodeResource(view.getResources(), R.drawable.down);
            downImage2  = BitmapFactory.decodeResource(view.getResources(), R.drawable.down2);
            leftImage1  = BitmapFactory.decodeResource(view.getResources(), R.drawable.left);
            leftImage2  = BitmapFactory.decodeResource(view.getResources(), R.drawable.left2);
            rightImage1 = BitmapFactory.decodeResource(view.getResources(), R.drawable.right);
            rightImage2 = BitmapFactory.decodeResource(view.getResources(), R.drawable.right2);
            fireImage2  = BitmapFactory.decodeResource(view.getResources(), R.drawable.fire2);
            fireImage1  = BitmapFactory.decodeResource(view.getResources(), R.drawable.fire);

            pauseImage2  = BitmapFactory.decodeResource(view.getResources(), R.drawable.pause2);
            pauseImage1  = BitmapFactory.decodeResource(view.getResources(), R.drawable.pause);

            selectImage1  = BitmapFactory.decodeResource(view.getResources(), R.drawable.select);
            selectImage2  = BitmapFactory.decodeResource(view.getResources(), R.drawable.select2);
            soundImage1  = BitmapFactory.decodeResource(view.getResources(), R.drawable.sound);
            soundImage2  = BitmapFactory.decodeResource(view.getResources(), R.drawable.sound2);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

}
