package com.tol.tira;

import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import java.util.Map;
import java.util.LinkedHashMap;

import com.tol.tira.graph.Graph;
import com.tol.tira.graph.Vertex;
import com.tol.tira.MazeObserver.Event;
import com.tol.tira.graph.Edge;
import com.tol.tira.graph.Edge.EdgeType;
import com.tol.tira.graph.Visit;

/**
 * Maze is a sublcass of Graph to model rectangular graph of a maze.
 * <p>
 * Maze assumes the following limitations and implementation choices from graph data structure:
 * <ul>
 *  <li>All edges are undirectional.
 *  <li>{@code MazeNode} is used as the content (template parameter) of the vertices.
 *  <li>Vertices are drawn in a rectangle, based on the coordinates in {@code MazeNode}.
 *  <li>Graph is implemented as an <strong>edge list</strong>.
 * </ul>
 * Maze implements the role of the Subject in the Observer design pattern.
 * <p>
 * The various algorithms and the graph construction are animated. The animation speed is
 * adjustable. Animation is implemented in {@link #addToPath(Vertex)} which MUST be called 
 * by the algorithm implementations in Graph. After each call to addToPath, the algorithm
 * execution thread sleeps for the specified duration, allowing the GUI to update the UI
 * as new vertex was added to the path to animate.
 * 
 * @author Antti Juustila
 * @version 1.0
 */
public class Maze extends Graph<MazeNode> {

   private int width = 0;
   private int height = 0;
   private Vector<MazeObserver> observers = null;
   private Vector<Vertex<MazeNode>> currentPath = null;
   private Vertex<MazeNode> start = null;
   private Vertex<MazeNode> end = null;

   /** When animation has speed none, no animations are shown, the view is constructed without any delay. */
   public static final int ANIMATION_SPEED_NONE = -1;
   /** For fast animations of the algorithms. */
   public static final int ANIMATION_SPEED_FAST = 50;
   /** For medium fast animations of the algorithms. */
   public static final int ANIMATION_SPEED_MEDIUM = 150;
   /** For slow animations of the algorithms. */
   public static final int ANIMATION_SPEED_SLOW = 200;

   private int animationSpeed = ANIMATION_SPEED_FAST;

   private boolean inGameMode = false;

   /**
    * Constructor for the Maze.
    * Calls the super class constructor and allocates room for observers.
    */
   public Maze() {
      super();
      observers = new Vector<>();
      currentPath = new Vector<>();
   }

   /**
    * Implements the abstract method from Graph, allocating the edge list for vertices.
    */
   @Override
   public void createAdjacencyMap() {
      adjacencies = new LinkedHashMap<Vertex<MazeNode>, Vector<Edge<MazeNode>>>();
   }

   /**
    * Adds a new observer to the subject.
    * @param observer The observer.
    */
   public void addObserver(MazeObserver observer) {
      observers.add(observer);
   }

   /**
    * Removes an observer from the observers.
    * @param observer The observer to remove.
    */
   public void removeObserver(MazeObserver observer) {
      observers.remove(observer);
   }

   /**
    * Notifies all observers of a state change in the Maze.
    * @param event The event notified about.
    */
   private void notifyObservers(MazeObserver.Event event) {
      for (MazeObserver observer : observers) {
         observer.update(event);
      }
   }

   /**
    * Notifies all observers of a state change in the Maze.
    * @param event The event notified about.
    * @param message The message related to the event, shown to the user.
    */
    private void notifyObservers(MazeObserver.Event event, String message) {
      for (MazeObserver observer : observers) {
         observer.update(event, message);
      }
   }

   /**
    * The current animation speed.
    * @return Current animation speed.
    */
   public int getAnimationSpeed() {
      return animationSpeed;
   }

   /**
    * Changes the current animation speed.
    * <p>
    * The animation speed is the pause in milliseconds until the next
    * step in the algorithm is taken.
    * @param newSpeed The new animation speed in milliseconds.
    */
   public void setAnimationSpeed(int newSpeed) {
      animationSpeed = newSpeed;
   }

   /**
    * Creates a maze as a graph, notifying the observers of the process.
    * <p>
    * Maze has width and height, specifying the number of vertices placed
    * horizontally and vertically in the maze.
    * <p>
    * If no random edges are created, then the caller MUST add the edges to the
    * maze graph later. 
    * @param width The width of the maze.
    * @param height The height of the maze.
    * @param createRandomEdges If true, random edges are created for the maze.
    * @throws Exception If something goes wrong, an execption is thrown.
    */
   public void createMaze(int width, int height, boolean createRandomEdges) throws Exception {
      assert (observers != null);
      this.width = width;
      this.height = height;

      notifyObservers(Event.BEGIN_PROCESSING);
      adjacencies.clear();
      currentPath.clear();
      start = null;
      end = null;

      for (int y = 0; y < this.height; y++) {
         for (int x = 0; x < this.width; x++) {
            createVertexFor(new MazeNode(x, y));
         }
      }

      if (createRandomEdges) {
         Thread thread = new Thread(() -> {
            try {
               Vertex<MazeNode> startVertex = getVertexFor(new MazeNode(0, 0));
               Set<Vertex<MazeNode>> visited = new HashSet<>();
               randomlyCreateEdges(startVertex, visited);
               notifyObservers(Event.DONE);                  
            } catch (Exception e) {
               notifyObservers(Event.ERROR, "Exception: " + e.getMessage());
               e.printStackTrace();
            }
         });
         thread.start();
      } else {
         notifyObservers(Event.DONE);
      }
   }

   /**
    * Reads the maze vertice and edge data from a text file and constructs a maze.
    * @param file The file containing the maze description.
    * @throws Exception Throws exception if the file cannot be read or maze constructed from it.
    */
   public void createFromFile(File file) throws Exception {
      AtomicInteger newWidth = new AtomicInteger(0);
      AtomicInteger newHeight = new AtomicInteger(0);
      AtomicBoolean isFirstLine = new AtomicBoolean(true);

      try (Stream<String> stream = Files.lines(Paths.get(file.toURI()))) {
         stream.forEach(line -> {
            try {
               if (line.length() > 0 && line.charAt(0) != '#') {
                  if (isFirstLine.get()) {
                     String[] items = line.split(" ");
                     if (items.length != 3 || !items[0].equalsIgnoreCase("MAZE")) {
                        throw new IOException("Invalid file format for Maze file");
                     }
                     newWidth.set(Integer.parseInt(items[1]));
                     newHeight.set(Integer.parseInt(items[2]));
                     isFirstLine.set(false);
                     width = newWidth.get();
                     height = newHeight.get();
                     createMaze(width, height, false);
                  } else {
                     String[] items = line.split(" ");
                     if (items.length != 3) {
                        throw new IOException("Invalid edge for Maze file");
                     }
                     String[] nodeCoords = items[0].split(",");
                     int x = Integer.parseInt(nodeCoords[0]);
                     int y = Integer.parseInt(nodeCoords[1]);
                     Vertex<MazeNode> vertexFrom = getVertexFor(new MazeNode(x, y));
                     nodeCoords = items[1].split(",");
                     x = Integer.parseInt(nodeCoords[0]);
                     y = Integer.parseInt(nodeCoords[1]);
                     double weight = Double.parseDouble(items[2]);
                     Vertex<MazeNode> vertexTo = getVertexFor(new MazeNode(x, y));
                     addEdge(EdgeType.UNDIRECTED, vertexFrom, vertexTo, weight);
                  }
               }
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         });
      }
   }

   private void randomlyCreateEdges(Vertex<MazeNode> fromVertex, Set<Vertex<MazeNode>> visited) {
      visited.add(fromVertex);

      // final double[] randomEdgeValues =
      // {1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,2.0,2.0,2.0,3.0,3.0,4.0,4.0};
      Vector<Vertex<MazeNode>> neighbours = immediateNeighboursOf(fromVertex.getElement());
      while (!neighbours.isEmpty()) {
         int randomIndex = ThreadLocalRandom.current().nextInt(neighbours.size());
         Vertex<MazeNode> nextVertex = neighbours.get(randomIndex);
         neighbours.remove(randomIndex);
         if (nextVertex != null && !visited.contains(nextVertex)) {
            // double edgeValue =
            // randomEdgeValues[ThreadLocalRandom.current().nextInt(randomEdgeValues.length)];
            addEdge(EdgeType.UNDIRECTED, fromVertex, nextVertex, 1.0);
            if (animationSpeed > 0) {
               notifyObservers(Event.MAZE_CHANGED);
               try {
                  Thread.sleep(animationSpeed);
               } catch (InterruptedException ex) {
                  Thread.currentThread().interrupt();
               }
            }
            randomlyCreateEdges(nextVertex, visited);
         }
      }
   }

   public int getWidth() {
      return width;
   }

   public int getHeight() {
      return height;
   }

   public Vertex<MazeNode> getStart() {
      return start;
   }

   public Vertex<MazeNode> getEnd() {
      return end;
   }

   public String getMazeInformation() {
      int oldSpeed = animationSpeed;
      animationSpeed = ANIMATION_SPEED_NONE;
      boolean disconnected = isDisconnected();
      StringBuilder infoBuilder = new StringBuilder("Maze has ");
      infoBuilder.append(Integer.toString(adjacencies.keySet().size()));
      infoBuilder.append(" vertices.\n");
      int edgeCount = 0;
      for (Map.Entry<Vertex<MazeNode>, Vector<Edge<MazeNode>>> entry : adjacencies.entrySet()) {
         edgeCount += entry.getValue().size();
      }
      infoBuilder.append("Maze has ");
      infoBuilder.append(Integer.toString(edgeCount));
      infoBuilder.append(" edges.\n");
      infoBuilder.append("Is maze disconnected?: ");
      infoBuilder.append(disconnected ? "Yes.\n" : "No.\n");
      infoBuilder.append("Cycles in the maze?: ");
      if (!disconnected) {
         infoBuilder.append(hasCycles(false) ? "Yes.\n" : "No.\n");
      } else {
         infoBuilder.append("Not implemented for disconnected graphs.\n");
      }

      animationSpeed = oldSpeed;
      return infoBuilder.toString();
   }

   public Vector<EdgeInformation> getNeighbourDirections(int x, int y) {
      assert (observers != null);
      assert (width > 0 && height > 0);
      Vector<EdgeInformation> whereNeighbours = new Vector<>();
      Vertex<MazeNode> vertex = getVertexFor(new MazeNode(x, y));
      if (null != vertex) {
         Vector<Edge<MazeNode>> neighbours = getEdges(vertex);
         if (null != neighbours) {
            for (Edge<MazeNode> edge : neighbours) {
               MazeNode.Direction direction = edge.getDestination().getElement().orientationTo(vertex.getElement());
               float weight = (float) edge.getWeigth();
               EdgeInformation information = new EdgeInformation(direction, weight);
               whereNeighbours.add(information);
            }
         }
      }
      return whereNeighbours;
   }

   @Override
   public String toString() {
      StringBuilder builder = new StringBuilder();
      for (Map.Entry<Vertex<MazeNode>, Vector<Edge<MazeNode>>> entry : adjacencies.entrySet()) {
         builder.append(entry.getKey().toString() + " --> [ ");
         for (Edge<MazeNode> edge : entry.getValue()) {
            builder.append(edge.toString() + ", ");
         }
         builder.append("]\n");
      }
      return builder.toString();
   }

   private Vector<Vertex<MazeNode>> immediateNeighboursOf(MazeNode node) {
      Vector<Vertex<MazeNode>> neighbours = new Vector<>();
      Vertex<MazeNode> vertex = getVertexFor(new MazeNode(node.getX() - 1, node.getY()));
      if (vertex != null)
         neighbours.add(vertex);
      vertex = getVertexFor(new MazeNode(node.getX(), node.getY() - 1));
      if (vertex != null)
         neighbours.add(vertex);
      vertex = getVertexFor(new MazeNode(node.getX() + 1, node.getY()));
      if (vertex != null)
         neighbours.add(vertex);
      vertex = getVertexFor(new MazeNode(node.getX(), node.getY() + 1));
      if (vertex != null)
         neighbours.add(vertex);
      return neighbours;
   }

   public void doBreadthFirstSearch() {
      currentPath.clear();
      start = null;
      end = null;
      notifyObservers(Event.BEGIN_PROCESSING);
      int x = ThreadLocalRandom.current().nextInt(getWidth());
      start = getVertexFor(new MazeNode(x, 0));
      Thread thread = new Thread(() -> {
         try {
            System.out.println("Starting BFS from " + x + "," + 0);
            breadthFirstSearch(start, null);
            notifyObservers(Event.DONE);
         } catch (Exception e) {
            notifyObservers(Event.ERROR, "Exception: " + e.getMessage());
            e.printStackTrace();
         }
      });
      thread.start();
   }

   public void doBreadthFirstSearchWithTarget() {
      currentPath.clear();
      start = null;
      end = null;
      notifyObservers(Event.BEGIN_PROCESSING);
      int x = ThreadLocalRandom.current().nextInt(getWidth());
      start = getVertexFor(new MazeNode(x, 0));
      x = ThreadLocalRandom.current().nextInt(getWidth());
      end = getVertexFor(new MazeNode(x, getHeight() - 1));
      Thread thread = new Thread(() -> {
         try {
            breadthFirstSearch(start, end);
            notifyObservers(Event.DONE);
         } catch (Exception e) {
            notifyObservers(Event.ERROR, "Exception: " + e.getMessage());
            e.printStackTrace();
         }
      });
      thread.start();
   }

   public void doDepthFirstSearch() {
      currentPath.clear();
      start = null;
      end = null;
      notifyObservers(Event.BEGIN_PROCESSING);
      int x = ThreadLocalRandom.current().nextInt(getWidth());
      start = getVertexFor(new MazeNode(x, 0));
      Thread thread = new Thread(() -> {
         try {
            System.out.println("Starting DFS from " + x + "," + 0);
            depthFirstSearch(start, null);
            notifyObservers(Event.DONE);               
         } catch (Exception e) {
            notifyObservers(Event.ERROR, "Exception: " + e.getMessage());
            e.printStackTrace();
         }
      });
      thread.start();
   }

   public void doDepthFirstSearchWithTarget() {
      currentPath.clear();
      start = null;
      end = null;
      notifyObservers(Event.BEGIN_PROCESSING);
      int x = ThreadLocalRandom.current().nextInt(getWidth());
      start = getVertexFor(new MazeNode(x, 0));
      x = ThreadLocalRandom.current().nextInt(getWidth());
      end = getVertexFor(new MazeNode(x, getHeight() - 1));
      Thread thread = new Thread(() -> {
         try {
            depthFirstSearch(start, end);
            notifyObservers(Event.DONE);
         } catch (Exception e) {
            notifyObservers(Event.ERROR, "Exception: " + e.getMessage());
            e.printStackTrace();
         }
      });
      thread.start();
   }

   class DijkstraResult {
      boolean pathFound = false;
      int steps = 0;
      double totalWeigth = 0.0;
   }
   
   /**
    * Initiates the path finding using Dijkstra's algorithm
    */
   public void doDijkstra() {
      if (null == start && null == end) {
         int x = ThreadLocalRandom.current().nextInt(getWidth());
         start = getVertexFor(new MazeNode(x, 0));
         x = ThreadLocalRandom.current().nextInt(getWidth());
         end = getVertexFor(new MazeNode(x, getHeight() - 1));
      }
      Thread thread = new Thread(() -> {
         try {
            DijkstraResult result = coreDijkstra(start, end);
            if (result.pathFound) {
               String message = String.format("Path has %d steps, total weigth is %.1f", result.steps, result.totalWeigth);
               notifyObservers(Event.PATH_CHANGED, message);
            } else {
               notifyObservers(Event.PATH_CHANGED, "Path could not be found");
            }            
         } catch (Exception e) {
            notifyObservers(Event.ERROR, "Exception: " + e.getMessage());
            e.printStackTrace();
         }
      });
      thread.start();
   }

   private DijkstraResult coreDijkstra(Vertex<MazeNode> start, Vertex<MazeNode> end) {
      currentPath.clear();
      notifyObservers(Event.BEGIN_PROCESSING);
      DijkstraResult result = new DijkstraResult();
      result.pathFound = false;
      result.steps = 0;
      result.totalWeigth = 0.0;
      if (start != null && end != null && !start.getElement().equals(end.getElement())) {
         System.out.println("Starting Dijkstra from " + start.getElement().getX() + "," + start.getElement().getY() + " to...");
         System.out.println("... " + end.getElement().getX() + "," + end.getElement().getY());
         Map<Vertex<MazeNode>, Visit<MazeNode>> path = shortestPathsFrom(start);
         if (null != path && !path.isEmpty()) {
            Vector<Edge<MazeNode>> routeToEnd = route(end, path);
            if (null != routeToEnd && !routeToEnd.isEmpty()) {
               result.pathFound = true;
               currentPath.add(routeToEnd.firstElement().getSource());
               result.steps = routeToEnd.size();
               for (Edge<MazeNode> edge : routeToEnd) {
                  result.totalWeigth += edge.getWeigth();
                  currentPath.add(edge.getDestination());
                  if (animationSpeed > 0) {
                     notifyObservers(Event.PATH_CHANGED);
                     try {
                        Thread.sleep(animationSpeed);
                     } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                     }
                  }
               }
            }
         }
      }
      return result;
   }

   public Vector<Vertex<MazeNode>> getCurrentPath() {
      return currentPath;
   }

   @Override
   protected void addToPath(Vertex<MazeNode> vertex) {
      currentPath.add(vertex);
      if (animationSpeed > 0) {
         notifyObservers(Event.PATH_CHANGED);
         try {
            Thread.sleep(animationSpeed);
         } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
         }
      }
   }

   public void exportMaze() {
      final String fileName = "new_maze.txt";
      try {
         Set<Edge<MazeNode>> writtenEdgesReversed = new HashSet<>();
         BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
         StringBuilder builder = new StringBuilder();
         writer.write("# Generated maze from MazeApp");
         writer.newLine();
         writer.write("# Maze of width x height y grid");
         writer.newLine();         
         builder.append("MAZE ");
         builder.append(width);
         builder.append(" ");
         builder.append(height);
         writer.write(builder.toString());
         writer.newLine();
         builder.setLength(0);
         for (Map.Entry<Vertex<MazeNode>, Vector<Edge<MazeNode>>> entry : adjacencies.entrySet()) {
            for (Edge<MazeNode> edge : entry.getValue()) {
               if (!writtenEdgesReversed.contains(edge)) {
                  builder.append(edge.getSource().toString());
                  builder.append(" ");
                  builder.append(edge.getDestination().toString() + " ");
                  builder.append(edge.getWeigth());
                  writer.write(builder.toString());
                  writer.newLine();
                  builder.setLength(0);
                  writtenEdgesReversed.add(edge.reversed());
               }
            }
         }
         writer.write("# End");
         writer.close();
      } catch (Exception e) {
         notifyObservers(Event.ERROR, "Failed to export graph to file: " + e.getMessage());
         e.printStackTrace();
      }
   }

   // Gaming

   private Timer gameTimer = null;

   public boolean isPlaying() {
      return inGameMode;
   }

   public void startTheGame() {
      inGameMode = !inGameMode;
      if (inGameMode) {
         currentPath.clear();
         start = getVertexFor(new MazeNode(0,0));
         end = getVertexFor(new MazeNode(getWidth() - 1, getHeight() - 1));
         notifyObservers(Event.GAME_ON);
         animationSpeed = ANIMATION_SPEED_NONE;
         gameTimer = new Timer();
         TimerTask gameTimerTask = new TimerTask() {
            @Override
            public void run() {
               if (null != currentPath && currentPath.size() >= 2) {
                  start = currentPath.get(1);
               }
               coreDijkstra(start, end);
               notifyObservers(Event.PATH_CHANGED);
               if (start.getElement().equals(end.getElement())) {
                  startTheGame();
               }
            }
         };
         gameTimer.scheduleAtFixedRate(gameTimerTask, 1000, 500);
      } else {
         stopTheGame();
      }
   }

   private void stopTheGame() {
      inGameMode = false;
      gameTimer.cancel();
      gameTimer = null;
      start = null;
      end = null;
      notifyObservers(Event.GAME_OFF, "The bug got ya!");
      currentPath.clear();
      animationSpeed = ANIMATION_SPEED_FAST;
   }

   public boolean moveTo(MazeNode.Direction direction) {
      if (inGameMode) {
         Vector<EdgeInformation> neighbours = getNeighbourDirections(end.getElement().getX(), end.getElement().getY());
         for (EdgeInformation neighbour : neighbours) {
            if (neighbour.direction == direction) {
               end = getVertexFor(end.getElement().inDirectionOf(direction));
               if (start.getElement().equals(end.getElement())) {
                  stopTheGame();
               }
               return true;
            }
         }
      }
      return false;
   }

} // class
