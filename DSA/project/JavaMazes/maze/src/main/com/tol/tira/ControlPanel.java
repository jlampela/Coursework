package com.tol.tira;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * ControlPanel is the area in the bottom of the main window containing command buttons 
 * to launch various graph algorithms.
 * @author Antti Juustila
 * @version 1.0
 */
public class ControlPanel extends JPanel implements ActionListener, MazeObserver {

    private Maze maze = null;
    private JButton bfsButton = null;
    private JButton bfsWithTargetButton = null;
    private JButton dfsButton = null;
    private JButton dfsWithTargetButton = null;
    private JButton dijsktraButton = null;

    public ControlPanel(Maze maze) {
        this.maze = maze;
        maze.addObserver(this);
        bfsButton = new JButton("BFS");
        bfsButton.setActionCommand("bfs");
        bfsButton.addActionListener(this);
        add(bfsButton);
        bfsWithTargetButton = new JButton("BFS w target");
        bfsWithTargetButton.setActionCommand("bfs-target");
        bfsWithTargetButton.addActionListener(this);
        add(bfsWithTargetButton);
        dfsButton = new JButton("DFS");
        dfsButton.setActionCommand("dfs");
        dfsButton.addActionListener(this);
        add(dfsButton);
        dfsWithTargetButton = new JButton("DFS w target");
        dfsWithTargetButton.setActionCommand("dfs-target");
        dfsWithTargetButton.addActionListener(this);
        add(dfsWithTargetButton);
        dijsktraButton = new JButton("Dijkstra");
        dijsktraButton.setActionCommand("dijkstra");
        dijsktraButton.addActionListener(this);
        add(dijsktraButton);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(Integer.MAX_VALUE, 20);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if ("bfs".equals(e.getActionCommand())) {
                maze.doBreadthFirstSearch();
            } else if ("bfs-target".equals(e.getActionCommand())) {
                maze.doBreadthFirstSearchWithTarget();
            } else if ("dfs".equals(e.getActionCommand())) {
                maze.doDepthFirstSearch();
            } else if ("dfs-target".equals(e.getActionCommand())) {
                maze.doDepthFirstSearchWithTarget();
            } else if ("dijkstra".equals(e.getActionCommand())) {
                maze.doDijkstra();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Could not execute: " + ex.getMessage(), "Something went wrong",
                                          JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    @Override
    public void update(MazeObserver.Event event) {
        SwingUtilities.invokeLater( () -> {
            switch (event) {
                case BEGIN_PROCESSING, GAME_ON:
                    bfsButton.setEnabled(false);
                    bfsWithTargetButton.setEnabled(false);
                    dfsButton.setEnabled(false);
                    dfsWithTargetButton.setEnabled(false);
                    dijsktraButton.setEnabled(false);
                    break;
                case DONE, ERROR, GAME_OFF:
                    bfsButton.setEnabled(true);
                    bfsWithTargetButton.setEnabled(true);
                    dfsButton.setEnabled(true);
                    dfsWithTargetButton.setEnabled(true);
                    dijsktraButton.setEnabled(true);
                    break;
                default:
                    break;
            }
        });
    }

    @Override
    public void update(Event event, String message) {
        update(event);
        int type = JOptionPane.ERROR_MESSAGE;
        if (event != Event.ERROR) {
            type = JOptionPane.INFORMATION_MESSAGE;
        }
        if (!maze.isPlaying() || event == Event.ERROR) {
            JOptionPane.showMessageDialog(this, "Result: " + message, "How it went", type);
        }
    }

}
