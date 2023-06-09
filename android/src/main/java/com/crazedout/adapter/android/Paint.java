package com.crazedout.adapter.android;

import com.crazedout.adapter.android.stg.GameView;

import java.awt.*;

public class Paint extends Canvas {

    public static class Style {
        public static int FILL = 0;
        public static int STROKE = 1;
    }

    public Paint(GameView view, Graphics g) {
        super(view,g);
    }
    public void setColor(Color c){

    }

    public void setStyle(int s){

    }

    public void setStrokeWidth(int w){

    }

}
