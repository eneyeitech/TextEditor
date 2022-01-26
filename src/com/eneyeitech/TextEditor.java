package com.eneyeitech;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class TextEditor extends JFrame {
    public TextEditor() {
        super("Text Editor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        initComponent();

        setLocationRelativeTo(null);
        setVisible(true);
        //setLayout(null);
    }

    public void initComponent() {
        JPanel controlPanel = new JPanel();

        JTextField filenameField = new JTextField();
        filenameField.setName("FilenameField");
        filenameField.setColumns(10);  ;
        controlPanel.add(filenameField);

        JButton saveButton = new JButton();
        saveButton.setName("SaveButton");
        saveButton.setText("Save");
        controlPanel.add(saveButton);

        JButton loadButton = new JButton();
        loadButton.setText("Load");
        loadButton.setName("LoadButton");
        controlPanel.add(loadButton);


        JTextComponent jTextArea = new JTextArea(50,50);
        jTextArea.setName("TextArea");

        JScrollPane jScrollPane = new JScrollPane(jTextArea);
        jScrollPane.setName("ScrollPane");

        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);


        add(controlPanel, BorderLayout.NORTH);
        add(jScrollPane, BorderLayout.CENTER);

        saveButton.addActionListener(e -> {
            String fileName = filenameField.getText();
            if (fileName != null && fileName.trim().length() > 0) {
                try {
                    BufferedWriter myWriter = new BufferedWriter(new FileWriter(fileName, StandardCharsets.UTF_8));
                    myWriter.write(jTextArea.getText());
                    myWriter.close();
                    System.out.println("Successfully wrote to the file.");
                } catch (IOException r) {
                    System.out.println("An error occurred.");
                    r.printStackTrace();
                }
            }
        });

        loadButton.addActionListener(e -> {
            String fileName = filenameField.getText();
            if (fileName != null && fileName.trim().length() > 0) {
                File selectedFile = new File(fileName);
                try {
                    jTextArea.setText(new String(Files.readAllBytes(selectedFile.toPath())));
                } catch (IOException r) {
                    jTextArea.setText(null);
                }
            }
        });
    }
}
