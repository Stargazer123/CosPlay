package com.crazedout.adapter.android.stg;

import com.crazedout.adapter.android.Bitmap;

public class Item {

    Bitmap image;
    boolean visible;

    public Item(Bitmap bm, boolean visible){
        this.image = bm;
        this.visible = visible;
    }

}
