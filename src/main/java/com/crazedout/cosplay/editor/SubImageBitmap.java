package com.crazedout.cosplay.editor;

import com.crazedout.cosplay.editor.CosImageBitmap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class SubImageBitmap extends CosImageBitmap {

    String fileName;

    public SubImageBitmap(Image i, File file) {
        super(i, file);
    }

    public void setFileName(String fileName){
        this.file = fileName;
        this.name = fileName;
        this.fileName=fileName;
    }

    public String getFileName(){
        return this.fileName;
    }

    public void saveImage(FileOutputStream fos, String ext) throws IOException {
        System.out.println("Save:" + this.file);
        ImageIO.write((BufferedImage)this.image,ext, fos);
    }
}
