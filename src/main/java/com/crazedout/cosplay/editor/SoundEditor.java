package com.crazedout.cosplay.editor;

import com.crazedout.cosplay.GamePlay;
import com.crazedout.cosplay.Offset;
import com.crazedout.cosplay.ViewPort;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SoundEditor {

    JComboBox<String> sounds;
    JComboBox<GamePlay.GameState> states;
    JButton play,addBtn,removeBtn;
    MapPanel mapPanel;

    public SoundEditor(MapPanel  mp) {
        this.mapPanel = mp;
        File dir = new File(CosPlay.userHome + File.separatorChar + "cosplay" + File.separatorChar + "sound");
        String[] files = dir.list();
        String[] arr = new String[files.length+2];
        arr[0] = "<Default>";
        for(int i = 0; i < files.length;i++){
            arr[i+1] = files[i];
        }
        arr[arr.length-1] = "<No Sound>";
        sounds = new JComboBox<>(arr);
        play = new JButton("Play sound");
        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = sounds.getSelectedIndex();
                if(i!=0 && i!=arr.length-1) {
                    int id = GamePlay.getAudio().findId((String)sounds.getSelectedItem());
                    GamePlay.getAudio().playSound(id);
                }
            }
        });

        addBtn = new JButton("Add sound");
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((CosMap)mp.map).sounds.put((GamePlay.GameState)states.getSelectedItem(),
                        sounds.getSelectedIndex());
            }
        });
        states = new JComboBox<GamePlay.GameState>(GamePlay.GameState.values());
        states.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GamePlay.GameState s = (GamePlay.GameState)states.getSelectedItem();
                Integer id = null;
                if((id=((CosMap)mapPanel.map).sounds.get(s))!=null) {
                    sounds.setSelectedIndex(id);
                }
            }
        });

        removeBtn = new JButton("Remove sound");
        removeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((CosMap)mp.map).sounds.remove((GamePlay.GameState)states.getSelectedItem());
            }
        });
    }

    public boolean pop() {

        boolean ok = false;

        Object[] message = {
                "Game state:",states,
                "Sounds:",sounds,
                play,
                addBtn,
        };


        int option = JOptionPane.showConfirmDialog(mapPanel, message, "Game play sounds", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
        }
        return ok;
    }
}
