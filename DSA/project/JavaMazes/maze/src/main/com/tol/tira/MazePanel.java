package com.tol.tira;

import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Stroke;
import java.awt.geom.Line2D;

import com.tol.tira.graph.Vertex;

/**
 * MazePanel takes care of drawing the maze and handling arrow key presses in game mode.
 *
 * @author Antti Juustila
 * @version 1.0
 */
public class MazePanel extends JPanel implements MazeObserver, KeyListener {
    private Maze maze = null;
    private boolean showEdges = true;

    private static final int MARGIN = 2;
    private static final int VERTEX_SIZE = 8;

    public MazePanel(Maze maze) {
        setBorder(BorderFactory.createLineBorder(Color.black));
        this.maze = maze;
        maze.addObserver(this);
        addKeyListener(this);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1200,780);
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(1200,780);
    }

    void setShowEdgeWeights(boolean show) {
        showEdges = show;
        repaint();
    }

    boolean getShowEdgeWeights() {
        return showEdges;
    }

    /**
     * Draws the maze to canvas. Note that the drawing assumes undirected graph
     * and it draws only the edges to east and south since drawing both directions
     * would not be needed.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);       

        int cellWidth = getSize().width / maze.getWidth();
        int cellHeight = getSize().height / maze.getHeight();

        final int edgeLengthWidth = ((getSize().width - (2 * MARGIN)) / maze.getWidth());
        final int edgeLengthHeight = ((getSize().height - (2 * MARGIN)) / maze.getHeight());

        for (int y = 0; y < maze.getHeight(); y++) {
            for (int x = 0; x < maze.getWidth(); x++) {
                Vertex<MazeNode> vertex = maze.getVertexFor(new MazeNode(x,y));
                if (null != vertex) {
                    int vertexX = (x * cellWidth + cellWidth / 2) + MARGIN;
                    int vertexY = (y * cellHeight + cellHeight / 2) + MARGIN;
                    if (vertex.equals(maze.getStart())) {
                        Color current = g.getColor();
                        g.setColor(Color.BLUE);
                        g.fillOval(vertexX-VERTEX_SIZE/2, vertexY-VERTEX_SIZE/2, VERTEX_SIZE*2, VERTEX_SIZE*2);
                        g.setColor(current);
                    } else if (vertex.equals(maze.getEnd())) {
                        Color current = g.getColor();
                        g.setColor(Color.RED);
                        g.fillRect(vertexX-VERTEX_SIZE/2, vertexY-VERTEX_SIZE/2, VERTEX_SIZE*2, VERTEX_SIZE*2);
                        g.setColor(current);
                    }
                    if (maze.getCurrentPath().contains(vertex)) {
                        Color current = g.getColor();
                        g.setColor(Color.BLUE);
                        g.fillOval(vertexX-2, vertexY-2, VERTEX_SIZE+3, VERTEX_SIZE+3);
                        g.setColor(current);
                    } else {
                        g.drawOval(vertexX, vertexY, VERTEX_SIZE, VERTEX_SIZE);
                    }
                    Vector<EdgeInformation> neighbours = maze.getNeighbourDirections(x, y);
                    Stroke oldStroke;
                    Graphics2D graphics = (Graphics2D)g;
                    for (EdgeInformation edge : neighbours) {
                        switch (edge.direction) {
                            case EAST:
                                oldStroke = graphics.getStroke();
                                graphics.setStroke(new BasicStroke(edge.weight));
                                graphics.draw(new Line2D.Float(vertexX+VERTEX_SIZE, vertexY+VERTEX_SIZE/2, vertexX+edgeLengthWidth, vertexY+VERTEX_SIZE/2));
                                graphics.setStroke(oldStroke);
                                if (showEdges && Math.abs(edge.weight - 1.0) > 0.1) {
                                    graphics.drawString(String.valueOf(edge.weight), vertexX+edgeLengthWidth/2, vertexY - 2);
                                }
                                break;
                            case SOUTH:
                                oldStroke = graphics.getStroke();
                                graphics.setStroke(new BasicStroke(edge.weight));
                                graphics.draw(new Line2D.Float(vertexX+VERTEX_SIZE/2, vertexY+VERTEX_SIZE, vertexX+VERTEX_SIZE/2, vertexY+edgeLengthHeight));
                                graphics.setStroke(oldStroke);
                                if (showEdges && Math.abs(edge.weight - 1.0) > 0.1) {
                                    graphics.drawString(String.valueOf(edge.weight), vertexX+VERTEX_SIZE + 3, vertexY+edgeLengthHeight/2);
                                }
                                break;
                            case NORTH, WEST, NONE:
                                break;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void update(MazeObserver.Event event) {
        SwingUtilities.invokeLater(() ->  {
            switch (event) {
                case MAZE_CHANGED, PATH_CHANGED, DONE, ERROR, GAME_ON, GAME_OFF:
                    repaint();
                    break;
                default:
                    break;
            }
        });
    }

    @Override
    public void update(Event event, String message) {
        // Nothing to do here. MazePanel shows the messages.
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Nothing to do here.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (maze.isPlaying()) {
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                if (maze.moveTo(MazeNode.Direction.EAST)) {
                    repaint();
                }
            } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                if (maze.moveTo(MazeNode.Direction.WEST)) {
                    repaint();
                }
            } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                if (maze.moveTo(MazeNode.Direction.NORTH)) {
                    repaint();
                }
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                if (maze.moveTo(MazeNode.Direction.SOUTH)) {
                    repaint();
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Nothing to do here.
    }

}
