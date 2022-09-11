package com.tol.tira.graph;

import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Vector;

import com.tol.tira.graph.Edge.EdgeType;

/**
 * Implementation of the graph data structure and associated algorithms.
 * <p>
 * This abstract class implements the graph data structure and various
 * algorithms like breadth-first search, depth-first search and the Dijkstra
 * path finding algorithm.
 * <p>
 * The class needs your attention, dear student. Implement the methods
 * marked TODO in the comments, based on what you have learned about
 * graphs.
 * <p>
 * You should also study how the extension of Graph, the Maze inherits 
 * and uses this base class to represent mazes as one application of 
 * graphs. Seeing the finished app running also helps in understanding
 * what should be implemented here. So study the course materials, including
 * demonstration videos to see how graphs work.
 * <p>
 * The Graph as a generic (template) class can use any data types conforming to 
 * the Comparable interface.
 * <p>
 * This implementation uses edge lists to store the graph vertices
 * and edges. Which kind of a Map data structure is eventually used,
 * is specified in the createAdjacencyMap() method implementation.
 * 
 * @author Antti Juustila
 * @version 1.0
 * @see com.tol.tira.Maze
 */
public abstract class Graph<T extends Comparable<T>> {

   /** The adjacency list of the grap. Subclass must select and instantiate
    * a suitable type of Map, depending on application needs.
    */
   protected Map<Vertex<T>, Vector<Edge<T>>> adjacencies = null;
   
   /**
    * Constructor should only be called by subclasses, so it is therefore
    * protected.
    * <p>
    * Constructor calls the {@code createAdjacencyMap} method, implemented by
    * a subclass of Graph. This method should allocate a suitable Map data structure
    * depending on the application requirements.
    */
   protected Graph() {
      createAdjacencyMap();
      assert(adjacencies != null);
   }

   /**
    * This method is called to create the internal data structure holding
    * the vertices and edges of the graph, the adjacencies. Since it is not
    * always possible  to know which data structure is best for the case at
    * hand, let the subclasses create the data structure.
    */
   public abstract void createAdjacencyMap();

   /**
    * Creates a vertex holding the dataItem (node in a vertex) in the graph.
    * Use this method always to add vertices to the graph.
    * The newly created vertex must have an empty list of edges.
    * 
    * @param dataItem The data item to put in the vertex of the graph.
    * @return Returns the created vertex, placed in the graph adjacency list.
    */
   public Vertex<T> createVertexFor(T dataItem) {
      return null;
   }

   /**
    * Adds an edge to the graph. Note that the vertices MUST have been created 
    * earlier by calling {@code createVertexFor(T)} and are already in the graph.
    * @param type The type of the edge, either directed or undirected.
    * @param source The source vertex of the edge.
    * @param destination The destination vertex of the edge.
    * @param weight The weight of the edge.
    */
   public void addEdge(Edge.EdgeType type, Vertex<T> source, Vertex<T> destination, double weight) {
   }

   /**
    * Adds a directed edge to the graph. Note that the vertices must have been created 
    * earlier by calling  {@code createVertexFor(T)}.
    * @param source The source vertex of the edge.
    * @param destination The destination vertex of the edge.
    * @param weight The weight of the edge.
    */
   public void addDirectedEdge(Vertex<T> source, Vertex<T> destination, double weight) {
   }

   /**
    * Gets the edges of the specified vertex. The vertex must be
    * already in the graph.
    * @param source The vertex edges of which we wish to get.
    * @return Returns the edges of the vertex.
    */
   public Vector<Edge<T>> getEdges(Vertex<T> source) {
      return null;
   }

   /**
    * Gets a vertex for the specified node (contents) in a vertex, if found.
    * If the vertex with the node value is not found, returns null.
    * @param node The value of T searched for from the graph.
    * @return The vertex containing the node, or null.
    */
   public Vertex<T> getVertexFor(T node) {
      return null;
   }

   /**
    * This method is implemented by the subclass {@code Maze} to gather
    * the path traversed by various algorithms. This is used by the
    * Maze GUI animations to show how the algorithms traverse the graph.
    * <p>
    * Call this method in places where it is instructed by the TODO comments.
    * @param vertex The vertex to add the path traversed so far.
    */
   protected abstract void addToPath(Vertex<T> vertex);

   /**
    * Does breadth first search (BFS) of the graph starting from a vertex.
    * This method is protected because the subclass {@code Maze}
    * wants to do some preliminary work to enable visualization of the
    * algorithms in action. Maze then calls this method.
    * <p>
    * If target is null, search is done for the whole graph. Otherwise,
    * search MUST be stopped when the target vertex is found.
    * <p>
    * Note that implementation MUST call {@link addToPath(Vertex<T>)} for 
    * each <strong>new</strong> vertex found. Otherwise the GUI animation does
    * not show the vertices being traversed.
    *
    * @param from The vertex where the search is started from.
    * @param target An optional ending vertex, null if not given.
    * @return Returns all the visited vertices traversed while doing BFS, in order they were found.
    */
   protected Vector<Vertex<T>> breadthFirstSearch(Vertex<T> from, Vertex<T> target) {
      return new Vector<>();
   }

   /**
    * Does depth first search (DFS) of the graph starting from a vertex.
    * This method is protected because the subclass {@code Maze} 
    * wants to do some preliminary work to enable visualization of the
    * algorithms in action. Maze then calls this method.
    * <p>
    * If target is null, search is done for the whole graph. Otherwise,
    * search MUST be stopped when the target vertex is found.
    * <p>
    * Note that implementation MUST call {@link addToPath} for 
    * each new vertex found. Otherwise the GUI animation does
    * not show the vertices being traversed.
    *
    * @param from The vertex where the search is started from.
    * @param target An optional ending vertex, null if not given.
    * @return Returns all the visited vertices traversed while doing DFS.
    */
   protected Vector<Vertex<T>> depthFirstSearch(Vertex<T> from, Vertex<T> target) {
      return new Vector<>();
   }
   
   /**
    * Returns true if the graph is disconnected. A disconnected graph is a
    * graph that has separate areas without any connecting edges between them.
    *
    * @return Returns true if the graph is disconnected.
    */
   public boolean isDisconnected() {
      return false;
   }

   /**
    * Checks if the graph has cycles.
    * <p>
    * If the graph is directed, provide true as the parameter, false for 
    * undirected graphs. With MazeApp, this parameter should always be false.
    * <p>NB: For this course project it is enough that this method works for
    * connected graphs. It does not need to work on disconnected graphs.
    * @param isDirected If true graph is directed.
    * @return Returns true if the graph has cycles.
    */
   public boolean hasCycles(boolean isDirected) {
      return false;
   }

   // Dijkstra starts here.

   /**
    * Finds a route to a destination using paths already constructed.
    * Before calling this method, cal {@link shortestPathsFrom} to construct
    * the paths from the staring vertex of Dijkstra algorithm.
    *<p>
    * A helper method for implementing the Dijkstra algorithm.
    * Method is protected since it is called only from the subclasses when doing Dijkstra.
    *
    * @see com.tol.tira.Maze#doDijkstra()
    * 
    * @param toDestination The destination vertex to find the route to.
    * @param paths The paths to search the destination.
    * @return Returns the vertices forming the route to the destination.
    */
   protected Vector<Edge<T>> route(Vertex<T> toDestination, Map<Vertex<T>, Visit<T>> paths) {
      return new Vector<>();
   }
   
   /**
    * Finds the shortest paths in the graph from the starting vertex.
    * This method is protected because the subclasses call it.
    *
    * In doing Dijkstra, first call this method, then call {@link route}
    * with the paths collected using this method, to get the shortest path to the destination.
    *
    * @param start The starting vertex for the path searching.
    * @return Returns the visits from the starting vertex.
    * @see com.tol.tira.graph.Graph#route(Vertex, Map)
    * @see com.tol.tira.Maze#doDijkstra()
    */
   protected Map<Vertex<T>, Visit<T>> shortestPathsFrom(Vertex<T> start) {
      return new HashMap<>();
   }

   /**
    * Provides a string representation of the graph, printing  out the vertices and edges.
    * <p>
    * Quite useful if you need to debug issues with algorithms. You can see is the graph
    * what it is supposed to be like.
    * <p>
    * Simple graph as a string would look like this:<br/>
    * <pre>
    * Created simple undirected graph:
    * [1] : [1 -> 2 (1.0), ]
    * [2] : [2 -> 1 (1.0), 2 -> 3 (1.0), 2 -> 4 (1.0), 2 -> 5 (1.0), ]
    * [3] : [3 -> 2 (1.0), 3 -> 4 (1.0), 3 -> 5 (1.0), ]
    * [4] : [4 -> 2 (1.0), 4 -> 3 (1.0), 4 -> 5 (1.0), ]
    * [5] : [5 -> 2 (1.0), 5 -> 3 (1.0), 5 -> 4 (1.0), ]
    * </pre> 
    * @return The graph as a string.
    */
   @Override
   public String toString() {
      // Should use StringBuilder
      String output = new String();
      for (Map.Entry<Vertex<T>, Vector<Edge<T>>> entry : adjacencies.entrySet()) {
         output += "[";
         output += entry.getKey().toString();
         output += "] : [";
         for (Edge<T> edge : entry.getValue()) {
            output += edge.toString() + ", ";
         }
         output += "]\n";
      }
      return output;
   }
}
