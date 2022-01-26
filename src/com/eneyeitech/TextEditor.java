package com.eneyeitech;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextEditor extends JFrame {

    final String OPEN_ICON_FILE_SMALL = "images/load1.png";
    final String SAVE_ICON_FILE_SMALL = "images/save1.png";
    final String START_SEARCH_ICON_SMALL = "images/search_small.png";
    final String PREV_SEARCH_SMALL = "images/prev_small.png";
    final String NEXT_SEARCH_SMALL = "images/next_small.png";

    private JTextComponent jTextArea;
    private JTextField searchField;
    private JFileChooser jFileChooser;
    private JCheckBox jCheckBox;
    private boolean useRegex = false;

    public TextEditor() {
        super("Text Editor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        jFileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        jFileChooser.setName("FileChooser");
        add(jFileChooser);
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
        fileMenu.setName("MenuFile");
        fileMenu.setMnemonic(KeyEvent.VK_F);

        JMenu searchMenu = new JMenu("Search");
        searchMenu.setName("MenuSearch");
        searchMenu.setMnemonic(KeyEvent.VK_S);

        JMenuItem saveMenuItem = new JMenuItem("Save");
        saveMenuItem.setName("MenuSave");
        JMenuItem openMenuItem = new JMenuItem("Load");
        openMenuItem.setName("MenuOpen");
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.setName("MenuExit");

        JMenuItem startSearchMenuItem = new JMenuItem("Start Search");
        startSearchMenuItem.setName("MenuStartSearch");
        JMenuItem previousMatchMenuItem = new JMenuItem("Previous Match");
        previousMatchMenuItem.setName("MenuPreviousMatch");
        JMenuItem nextMatchMenuItem = new JMenuItem("Next Match");
        nextMatchMenuItem.setName("MenuNextMatch");
        JMenuItem useRegExpMenu = new JMenuItem("Use regex");
        useRegExpMenu.setName("MenuUseRegExp");

        fileMenu.add(saveMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);

        searchMenu.add(startSearchMenuItem);
        searchMenu.add(previousMatchMenuItem);
        searchMenu.add(nextMatchMenuItem);
        searchMenu.add(useRegExpMenu);

        menuBar.add(fileMenu);
        menuBar.add(searchMenu);

        exitMenuItem.addActionListener(evt -> {
            System.exit(0);
        });

        saveMenuItem.addActionListener(evt -> {
            saveControl();
        });

        openMenuItem.addActionListener(evt -> {
            loadControl();
        });

        startSearchMenuItem.addActionListener(evt -> {
            //startSearch();
            SearchEngine();
        });

        previousMatchMenuItem.addActionListener(evt -> {
            //previousSearch();
            PrevSearch();
        });

        nextMatchMenuItem.addActionListener(evt -> {
            //nextSearch();
            NextSearch();
        });

        useRegExpMenu.addActionListener(evt -> {

            if (jCheckBox.isSelected()) {
                jCheckBox.setSelected(false);
                useRegex = false;
            } else {
                jCheckBox.setSelected(true);
                useRegex = true;
            }

            System.out.println(jCheckBox.isSelected());
        });




    }

    public void initComponent() {
        JPanel controlPanel = new JPanel();

        JButton loadButton = new JButton(new ImageIcon(OPEN_ICON_FILE_SMALL));
        loadButton.setName("OpenButton");
        controlPanel.add(loadButton);

        JButton saveButton = new JButton(new ImageIcon(SAVE_ICON_FILE_SMALL));
        saveButton.setName("SaveButton");
        controlPanel.add(saveButton);

        searchField = new JTextField();
        searchField.setName("SearchField");
        searchField.setColumns(20);  ;
        controlPanel.add(searchField);

        JButton startSearchButton = new JButton(new ImageIcon(START_SEARCH_ICON_SMALL));
        startSearchButton.setName("StartSearchButton");
        controlPanel.add(startSearchButton);

        JButton previousMatchButton = new JButton(new ImageIcon(PREV_SEARCH_SMALL));
        previousMatchButton.setName("PreviousMatchButton");
        controlPanel.add(previousMatchButton);

        JButton nextMatchButton = new JButton(new ImageIcon(NEXT_SEARCH_SMALL));
        nextMatchButton.setName("NextMatchButton");
        controlPanel.add(nextMatchButton);

        jCheckBox = new JCheckBox("Use regex");
        jCheckBox.setBounds(100,100, 50,50);
        jCheckBox.setName("UseRegExCheckbox");
        controlPanel.add(jCheckBox);


        jTextArea = new JTextArea(50,50);
        jTextArea.setName("TextArea");

        JScrollPane jScrollPane = new JScrollPane(jTextArea);
        jScrollPane.setName("ScrollPane");

        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add(controlPanel, BorderLayout.NORTH);
        add(jScrollPane, BorderLayout.CENTER);


        saveButton.addActionListener(e -> {
            saveControl();
        });

        loadButton.addActionListener(e -> {
            loadControl();
        });

        startSearchButton.addActionListener(e -> {
            //startSearch();
            SearchEngine();
        });

        previousMatchButton.addActionListener(e -> {
            //previousSearch();
            PrevSearch();
        });

        nextMatchButton.addActionListener(e -> {
            //nextSearch();
            NextSearch();
        });

        jCheckBox.addItemListener(e -> {
            if (e.getStateChange() == 1) {
                System.out.println("Checked");
                useRegex = true;
            } else{
                System.out.println("Un-Checked");
                useRegex = false;
            }
        });

    }

    public void saveControl(){

        int returnValue = jFileChooser.showSaveDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jFileChooser.getSelectedFile();
            save(selectedFile);
        }else{

        }
    }

    public void loadControl(){
        int returnValue = jFileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jFileChooser.getSelectedFile();
            open(selectedFile);
        }else{

        }
    }

    public void save(File selectedFile){

        try {
            BufferedWriter myWriter = new BufferedWriter(new FileWriter(selectedFile, StandardCharsets.UTF_8));
            myWriter.write(jTextArea.getText());
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException r) {
            System.out.println("An error occurred.");
            r.printStackTrace();
        }

    }

    public void open(File selectedFile){

        try {
            jTextArea.setText(new String(Files.readAllBytes(selectedFile.toPath())));
        } catch (IOException r) {
            jTextArea.setText(null);
        }

    }

    private ArrayList<Integer> indexFound;
    private ArrayList<Integer> lengthFound;
    private int counter = 0;
    private int nextCounter = 0;

    public void SearchEngine() {
        indexFound = new ArrayList<>();
        lengthFound = new ArrayList<>();
        String findText = searchField.getText();
        String allText = jTextArea.getText();
        int index = -1;
        int lengthFind = findText.length();

        if (useRegex) {       //Если чекбокс UseReg нажат - ищем по регулярным выражениям

            Pattern pattern = Pattern.compile(findText);
            Matcher matcher = pattern.matcher(allText);
            while (matcher.find()) {
                index = matcher.start();
                lengthFind = matcher.end() - index;
                indexFound.add(index);
                lengthFound.add(lengthFind);
            }

        } else {

            while (true) {
                index = allText.indexOf(findText, index + 1);
                if (index == -1) {
                    break;
                }
                indexFound.add(index);
                lengthFound.add(lengthFind);
                //System.out.println("index=" + index);
                //System.out.println("length=" + lengthFind);
            }
        }

        counter = indexFound.size();
        nextCounter = 0;

        if (counter > 0) {
            jTextArea.setCaretPosition(indexFound.get(0) + lengthFound.get(0));
            jTextArea.select(indexFound.get(0), indexFound.get(0) + lengthFound.get(0));
            jTextArea.grabFocus();
        }
    }

    public void NextSearch() {
        if (counter > 0) {
            if (counter - 1 > nextCounter) {
                nextCounter++;
            } else {
                nextCounter = 0;
            }
            jTextArea.setCaretPosition(indexFound.get(nextCounter) + lengthFound.get(nextCounter));
            jTextArea.select(indexFound.get(nextCounter), indexFound.get(nextCounter) + lengthFound.get(nextCounter));
            jTextArea.grabFocus();
        }
    }

    public void PrevSearch() {
        if (counter > 0) {
            if (nextCounter != 0) {
                nextCounter--;
            } else {
                nextCounter = counter - 1;
            }
            jTextArea.setCaretPosition(indexFound.get(nextCounter) + lengthFound.get(nextCounter));
            jTextArea.select(indexFound.get(nextCounter), indexFound.get(nextCounter) + lengthFound.get(nextCounter));
            jTextArea.grabFocus();
        }
    }
}

class StartEndPair {
    private int start;
    private int end;

    public StartEndPair(final int start, final int end) {
        this.start = start;
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public void setStart(final int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(final int end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "StartEndPair{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }
}