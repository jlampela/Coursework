package com.tol.tira;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSplitPane;

/**
 * The main application class containing the {@code main()} function.
 * <p>
 * MazeApp creates the panels and main menu. Main menu commands are also handled here.
 * @author Antti Juustila
 * @version 1.0
 * */
public class MazeApp implements ActionListener {
    private Maze maze = null;
    private ControlPanel controlPanel = null;
    private MazePanel mazePanel = null;
    private JFrame frame = null;
    private int sizeX = 12;
    private int sizeY = 9;
    private File currentPath = null;

    public static void main(String[] args) {
        MazeApp app = new MazeApp();
        app.createAndShowGUI();
    }

    MazeApp() {
        try {
            maze = new Maze();
            maze.createMaze(sizeX, sizeY, true);
        } catch (Exception e) {
            System.out.println("Could not launch the app because: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void createAndShowGUI() {
        frame = new JFrame("Maze Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JSplitPane rootPanel = new JSplitPane();
        frame.getContentPane().add(rootPanel);
        rootPanel.setLayout(new GridLayout());
        rootPanel.setOrientation(JSplitPane.VERTICAL_SPLIT);
        controlPanel = new ControlPanel(maze);
        mazePanel = new MazePanel(maze);
        rootPanel.setTopComponent(mazePanel);
        rootPanel.setBottomComponent(controlPanel);
        controlPanel.setLayout(new GridLayout());
        frame.setPreferredSize(new Dimension(1600, 900));

        // Menus
        JMenuBar mainMenu = new JMenuBar();
        JMenu mazeMenu = new JMenu("Maze Controls");
        // Maze size
        JMenuItem commandMenu = new JMenuItem("Create small maze");
        commandMenu.setActionCommand("small-maze");
        commandMenu.addActionListener(this);
        mazeMenu.add(commandMenu);
        commandMenu = new JMenuItem("Create medium sized maze");
        commandMenu.setActionCommand("medium-maze");
        commandMenu.addActionListener(this);
        mazeMenu.add(commandMenu);
        commandMenu = new JMenuItem("Create large maze");
        commandMenu.setActionCommand("large-maze");
        commandMenu.addActionListener(this);
        mazeMenu.add(commandMenu);
        mazeMenu.addSeparator();

        // Load maze from file and export it
        commandMenu = new JMenuItem("Load maze file...");
        commandMenu.setActionCommand("load-maze");
        commandMenu.addActionListener(this);
        mazeMenu.add(commandMenu);
        commandMenu = new JMenuItem("Export maze to file");
        commandMenu.setActionCommand("export-maze");
        commandMenu.addActionListener(this);
        mazeMenu.add(commandMenu);
        mazeMenu.addSeparator();

        // Show weights & Animation speed
        ButtonGroup group = new ButtonGroup();
        JRadioButtonMenuItem radioMenu = new JRadioButtonMenuItem("Show edge weights", mazePanel.getShowEdgeWeights());
        radioMenu.setActionCommand("show-weights");
        radioMenu.addActionListener(this);
        mazeMenu.add(radioMenu);
        mazeMenu.addSeparator();
        radioMenu = new JRadioButtonMenuItem("No animations", false);
        radioMenu.setActionCommand("no-anim");
        radioMenu.addActionListener(this);
        group.add(radioMenu);
        mazeMenu.add(radioMenu);
        radioMenu = new JRadioButtonMenuItem("Slow animations", false);
        radioMenu.addActionListener(this);
        radioMenu.setActionCommand("slow-anim");
        group.add(radioMenu);
        mazeMenu.add(radioMenu);
        radioMenu = new JRadioButtonMenuItem("Faster animations", false);
        radioMenu.addActionListener(this);
        radioMenu.setActionCommand("medium-anim");
        group.add(radioMenu);
        mazeMenu.add(radioMenu);
        radioMenu = new JRadioButtonMenuItem("Fastest animations", true);
        radioMenu.addActionListener(this);
        radioMenu.setActionCommand("fast-anim");
        group.add(radioMenu);
        mazeMenu.add(radioMenu);
        // Maze info menu
        mazeMenu.addSeparator();
        commandMenu = new JMenuItem("Maze information...");
        commandMenu.setActionCommand("info-maze");
        commandMenu.addActionListener(this);
        mazeMenu.add(commandMenu);
        mazeMenu.addSeparator();

        // Game menu
        commandMenu = new JMenuItem("Play game");
        commandMenu.setActionCommand("game-mode");
        commandMenu.addActionListener(this);
        mazeMenu.add(commandMenu);

        // Finishing the menu
        mainMenu.add(mazeMenu);
        frame.setJMenuBar(mainMenu);

        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String command = e.getActionCommand();
            if ("show-weights".equals(command)) {
                mazePanel.setShowEdgeWeights(!mazePanel.getShowEdgeWeights());
            } else if ("no-anim".equals(command)) {
                maze.setAnimationSpeed(Maze.ANIMATION_SPEED_NONE);
            } else if ("slow-anim".equals(command)) {
                maze.setAnimationSpeed(Maze.ANIMATION_SPEED_SLOW);
            } else if ("medium-anim".equals(command)) {
                maze.setAnimationSpeed(Maze.ANIMATION_SPEED_MEDIUM);
            } else if ("fast-anim".equals(command)) {
                maze.setAnimationSpeed(Maze.ANIMATION_SPEED_FAST);
            } else if ("small-maze".equals(command)) {
                sizeX = 12;
                sizeY = 9;
                maze.createMaze(sizeX, sizeY, true);
            } else if ("medium-maze".equals(command)) {
                sizeX = 20;
                sizeY = 15;
                maze.createMaze(sizeX, sizeY, true);
            } else if ("large-maze".equals(command)) {
                sizeX = 32;
                sizeY = 24;
                maze.createMaze(sizeX, sizeY, true);
            } else if ("load-maze".equals(command)) {
                JFileChooser mazeChooser = new JFileChooser();
                if (currentPath == null) {
                    mazeChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                } else {
                    mazeChooser.setCurrentDirectory(currentPath);
                }
                int result = mazeChooser.showOpenDialog(controlPanel);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = mazeChooser.getSelectedFile();
                    currentPath = new File(selectedFile.getParent());
                    try {
                        maze.createFromFile(selectedFile);
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(frame, "Something wrong with the maze file.", "Could not create maze",
                                JOptionPane.ERROR_MESSAGE);
                        e1.printStackTrace();
                    }
                    System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                }
            } else if ("export-maze".equals(command)) {
                maze.exportMaze();
            } else if ("info-maze".equals(command)) {
                JOptionPane.showMessageDialog(frame, maze.getMazeInformation(), "Maze facts",
                        JOptionPane.INFORMATION_MESSAGE);
            } else if ("game-mode".equals(command)) {
                maze.startTheGame();
                mazePanel.requestFocusInWindow();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Exception happened because: " + ex.getMessage(), "Something went wrong",
                                          JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}
