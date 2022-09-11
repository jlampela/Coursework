package com.tol.tira;

import java.util.LinkedHashMap;
import java.util.Vector;
import java.util.Set;
import java.util.Map;

import com.tol.tira.graph.*;
import com.tol.tira.graph.Edge.EdgeType;

/**
 * Class to test the Graph implementation.
 */
public class TestGraph extends Graph<Integer> {

   private Vector<Vertex<Integer>> currentPath = new Vector<>();

   @Override
   public void createAdjacencyMap() {
      adjacencies = new LinkedHashMap<Vertex<Integer>, Vector<Edge<Integer>>>();
   }

   @Override
   protected void addToPath(Vertex<Integer> vertex) {
      currentPath.add(vertex);
   }

   Vector<Vertex<Integer>> getCurrentPath() {
      return currentPath;
   }

   Vector<Vertex<Integer>> doBreadthFirstSearch(Vertex<Integer> from) {
      return breadthFirstSearch(from, null);
   }

   Vector<Vertex<Integer>> doDepthFirstSearch(Vertex<Integer> from) {
      return depthFirstSearch(from, null);
   }

   Vector<Edge<Integer>> doDijkstra(Vertex<Integer> from, Vertex<Integer> to) {
      Map<Vertex<Integer>, Visit<Integer>> path = shortestPathsFrom(from);
      Vector<Edge<Integer>> routeToEnd = route(to, path);
      return routeToEnd;
   }

   Set<Vertex<Integer>> getVertices() {
      return adjacencies.keySet();
   }

   static TestGraph createSimpleUndirectedGraph() {
      TestGraph testGraph = new TestGraph();
      // Undirected graph from lecture Graphs 1, slide "What is a graph?".
      for (int id = 1; id <= 5; id++) {
         testGraph.createVertexFor(id);
      }
      testGraph.addEdge(Edge.EdgeType.UNDIRECTED, testGraph.getVertexFor(1), testGraph.getVertexFor(2), 1.0);
      testGraph.addEdge(Edge.EdgeType.UNDIRECTED, testGraph.getVertexFor(2), testGraph.getVertexFor(3), 1.0);
      testGraph.addEdge(Edge.EdgeType.UNDIRECTED, testGraph.getVertexFor(2), testGraph.getVertexFor(4), 1.0);
      testGraph.addEdge(Edge.EdgeType.UNDIRECTED, testGraph.getVertexFor(2), testGraph.getVertexFor(5), 1.0);
      testGraph.addEdge(Edge.EdgeType.UNDIRECTED, testGraph.getVertexFor(3), testGraph.getVertexFor(4), 1.0);
      testGraph.addEdge(Edge.EdgeType.UNDIRECTED, testGraph.getVertexFor(3), testGraph.getVertexFor(5), 1.0);
      testGraph.addEdge(Edge.EdgeType.UNDIRECTED, testGraph.getVertexFor(4), testGraph.getVertexFor(5), 1.0);
      System.out.println("Created simple undirected graph:");
      System.out.println(testGraph.toString());
      return testGraph;
   }

   static TestGraph createSimpleDirectedGraph() {
      TestGraph testGraph = new TestGraph();
      // Directed graph from lecture Graphs 1, slide "What is a graph?".
      for (int id = 1; id <= 5; id++) {
         testGraph.createVertexFor(id);
      }
      testGraph.addEdge(Edge.EdgeType.DIRECTED, testGraph.getVertexFor(1), testGraph.getVertexFor(2), 1.0);
      testGraph.addEdge(Edge.EdgeType.DIRECTED, testGraph.getVertexFor(2), testGraph.getVertexFor(4), 0.5);
      testGraph.addEdge(Edge.EdgeType.DIRECTED, testGraph.getVertexFor(2), testGraph.getVertexFor(5), 2.5);
      testGraph.addEdge(Edge.EdgeType.DIRECTED, testGraph.getVertexFor(3), testGraph.getVertexFor(2), 4.0);
      testGraph.addEdge(Edge.EdgeType.DIRECTED, testGraph.getVertexFor(3), testGraph.getVertexFor(5), 1.5);
      testGraph.addEdge(Edge.EdgeType.DIRECTED, testGraph.getVertexFor(4), testGraph.getVertexFor(3), 11.5);
      testGraph.addEdge(Edge.EdgeType.DIRECTED, testGraph.getVertexFor(5), testGraph.getVertexFor(4), 3.5);
      System.out.println("Created simple directed graph:");
      System.out.println(testGraph.toString());
      return testGraph;
   }

   static TestGraph createSimpleUndirectedDisconnectedGraph() {
      TestGraph testGraph = new TestGraph();
      // Undirected graph from lecture Graphs 1, slide "Disconnected or not?".
      for (int id = 1; id <= 5; id++) {
         testGraph.createVertexFor(id);
      }
      testGraph.addEdge(Edge.EdgeType.UNDIRECTED, testGraph.getVertexFor(1), testGraph.getVertexFor(2), 1.0);
      testGraph.addEdge(Edge.EdgeType.UNDIRECTED, testGraph.getVertexFor(2), testGraph.getVertexFor(3), 1.0);
      testGraph.addEdge(Edge.EdgeType.UNDIRECTED, testGraph.getVertexFor(4), testGraph.getVertexFor(5), 1.0);
      System.out.println("Created simple undirected graph:");
      System.out.println(testGraph.toString());
      return testGraph;
   }

   static TestGraph createGraphForDijkstraSearch() {
      TestGraph testGraph = new TestGraph();

      testGraph.createVertexFor(1);
      testGraph.createVertexFor(2);
      testGraph.createVertexFor(3);
      testGraph.createVertexFor(4);
      testGraph.createVertexFor(5);
      testGraph.createVertexFor(6);
      testGraph.createVertexFor(7);
      
      testGraph.addEdge(EdgeType.UNDIRECTED, testGraph.getVertexFor(1), testGraph.getVertexFor(2), 4.0);
      testGraph.addEdge(EdgeType.UNDIRECTED, testGraph.getVertexFor(1), testGraph.getVertexFor(5), 1.0);
      testGraph.addEdge(EdgeType.UNDIRECTED, testGraph.getVertexFor(2), testGraph.getVertexFor(3), 11.0);
      testGraph.addEdge(EdgeType.UNDIRECTED, testGraph.getVertexFor(2), testGraph.getVertexFor(5), 2.0);
      testGraph.addEdge(EdgeType.UNDIRECTED, testGraph.getVertexFor(2), testGraph.getVertexFor(6), 5.0);
      testGraph.addEdge(EdgeType.UNDIRECTED, testGraph.getVertexFor(3), testGraph.getVertexFor(6), 4.0);
      testGraph.addEdge(EdgeType.UNDIRECTED, testGraph.getVertexFor(3), testGraph.getVertexFor(4), 9.0);
      testGraph.addEdge(EdgeType.UNDIRECTED, testGraph.getVertexFor(3), testGraph.getVertexFor(7), 9.0);
      testGraph.addEdge(EdgeType.UNDIRECTED, testGraph.getVertexFor(4), testGraph.getVertexFor(7), 4.0);
      testGraph.addEdge(EdgeType.UNDIRECTED, testGraph.getVertexFor(5), testGraph.getVertexFor(6), 8.0);
      testGraph.addEdge(EdgeType.UNDIRECTED, testGraph.getVertexFor(6), testGraph.getVertexFor(7), 7.0);
      System.out.println("Created graph for dijkstra testing:");
      System.out.println(testGraph.toString());
      return testGraph;
   }

}
