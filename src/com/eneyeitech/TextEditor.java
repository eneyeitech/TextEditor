package com.eneyeitech;

import javax.swing.*;
import javax.swing.text.JTextComponent;

public class TextEditor extends JFrame {
    public TextEditor() {
        super("The first stage");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        initComponent();

        setLocationRelativeTo(null);
        setVisible(true);
        setLayout(null);
    }

    public void initComponent() {
        JTextComponent jTextArea = new JTextArea();
        jTextArea.setName("TextArea");
        add(jTextArea);
    }
}
