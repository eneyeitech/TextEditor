package com.eneyeitech;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class TextEditor extends JFrame {
    private JTextComponent jTextArea;

    public TextEditor() {
        super("Text Editor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);

        initMenuBar();
        initComponent();

        setLocationRelativeTo(null);
        setVisible(true);
        //setLayout(null);
    }

    public void initMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        menuBar.add(fileMenu);

        JMenuItem saveMenuItem = new JMenuItem("Save");
        saveMenuItem.setName("MenuSave");
        JMenuItem openMenuItem = new JMenuItem("Open");
        openMenuItem.setName("MenuOpen");
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.setName("MenuExit");

        exitMenuItem.addActionListener(evt -> {
            System.exit(0);
        });

        saveMenuItem.addActionListener(evt -> {

        });

        openMenuItem.addActionListener(evt -> {

        });

        fileMenu.add(saveMenuItem);
        fileMenu.add(openMenuItem);

        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);

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


        jTextArea = new JTextArea(50,50);
        jTextArea.setName("TextArea");

        JScrollPane jScrollPane = new JScrollPane(jTextArea);
        jScrollPane.setName("ScrollPane");

        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);


        add(controlPanel, BorderLayout.NORTH);
        add(jScrollPane, BorderLayout.CENTER);

        saveButton.addActionListener(e -> {
            String fileName = filenameField.getText();
            save(fileName);
        });

        loadButton.addActionListener(e -> {
            String fileName = filenameField.getText();
            open(fileName);
        });
    }

    public void save(String fileName){
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
    }

    public void open(String fileName){
        if (fileName != null && fileName.trim().length() > 0) {
            File selectedFile = new File(fileName);
            try {
                jTextArea.setText(new String(Files.readAllBytes(selectedFile.toPath())));
            } catch (IOException r) {
                jTextArea.setText(null);
            }
        }
    }
}