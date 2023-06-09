package com.crazedout.cosplay.editor;

import com.crazedout.cosplay.Offset;
import com.crazedout.cosplay.ViewPort;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewPortEditor {

    JTextField width,height;
    JCheckBox scrollable;

    private static ViewPortEditor instance = null;

    private ViewPortEditor() {

        scrollable = new JCheckBox("Use view port scrolling", false);
        width = new JTextField("" + 12);
        height = new JTextField("" + 12);

        scrollable.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                width.setEditable(scrollable.isSelected());
                height.setEditable(scrollable.isSelected());
            }
        });
    }

    public static ViewPortEditor getInstance() {
        if (instance == null) {
            instance = new ViewPortEditor();
        }
        return instance;
    }

    public boolean pop(MapPanel panel) {

        boolean ok = false;

        Object[] message = {
                "",scrollable,
                "Tile width:", width,
                "Tile height:", height
        };

        if(panel.map.viewPort!=null){
            width.setText(""+panel.map.viewPort.tileWidth);
            height.setText(""+panel.map.viewPort.tileHeight);
        }
        scrollable.setSelected(panel.map.viewPort!=null);
        width.setEditable(scrollable.isSelected());
        height.setEditable(scrollable.isSelected());

        int option = JOptionPane.showConfirmDialog(panel, message, "Enable view port scrolling", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                if(scrollable.isSelected()) {
                    panel.map.viewPort = new ViewPort(panel,
                            Integer.parseInt(width.getText()),
                            Integer.parseInt(height.getText()),
                            Integer.parseInt("" + panel.map.size));
                }else{
                    panel.map.viewPort=null;
                    panel.map.setOffset(new Offset(0,0));
                }
                ok=true;
            }catch(Exception ex){
                CosPlay.showErrorDialog("Error","Wrong format in input");
            }
        }
        return ok;
    }
}
