package com.crazedout.cosplay.editor;

import com.crazedout.cosplay.ResourcesListener;
import com.crazedout.cosplay.adapter.android.Resources;

import javax.swing.*;
import java.awt.*;

public class LoadDialog extends JDialog implements ResourcesListener {

    JProgressBar progressBar;
    JLabel text;
    JLabel textLabel;

    String defaultText = "     CrazedoutSoft (c) 2023";

    public LoadDialog(JFrame frame) {
        this(frame, "Copying resources...");
    }
    public LoadDialog(JFrame frame, String msg){
        super(frame);
        setModal(false);
        setSize(400,100);
        setLocationRelativeTo(frame);
        setResizable(false);
        setTitle(msg);
        setLayout(new BorderLayout());
        progressBar = new JProgressBar();
        progressBar.setBorderPainted(true);
        progressBar.setIndeterminate(true);
        text = new JLabel(msg);
        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER));
        p.add(progressBar);
        JPanel s = new JPanel(new FlowLayout(FlowLayout.CENTER));
        s.add((textLabel=new JLabel(defaultText)));
        add("South", textLabel);
        add("Center", p);
    }

    @Override
    public void beginLoading(Resources res, Object resource) {
        if(resource!=null) {
            textLabel.setText("Loading:" + resource.toString());
        }
    }

    @Override
    public void doneLoading(Resources res, Object resource) {
        if(resource!=null) {
            textLabel.setText("Loading:" + resource.toString() + " OK!");
        }
    }
}
