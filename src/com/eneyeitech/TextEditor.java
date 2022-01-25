package com.eneyeitech;

import javax.swing.*;

public class TextEditor extends JFrame {
    public TextEditor() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);

        initComponent();

        setVisible(true);
        setLayout(null);
    }

    public void initComponent() {
        JTextArea jTextArea = new JTextArea();
        add(jTextArea);
    }
}
