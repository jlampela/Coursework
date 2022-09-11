package com.tol.tira;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Vector;
import java.util.Set;
import java.util.HashSet;

import com.tol.tira.graph.Vertex;

import org.junit.jupiter.api.Test;

/**
 * Basic unit tests for the Graph class.
 * Tests if the graph has the vertices and edges it should have.
 */
public class SearchGraphTests {

    @Test 
    public void testBreadthFirstSearchUndirected()
    {
        TestGraph testGraph = TestGraph.createSimpleUndirectedGraph();
        assertNotNull(testGraph, () -> "Test graph not created");
        
        Vector<Vertex<Integer>> bfsResult = testGraph.doBreadthFirstSearch(testGraph.getVertexFor(1));
        assertEquals(5, bfsResult.size(), () -> "BFS should find all vertices from the test graph");

        Set<Vertex<Integer>> allVertices = testGraph.getVertices();
        assertTrue(new HashSet<>(bfsResult).equals(new HashSet<>(allVertices)), () -> "BFS result set and all vertices should be equal with test graph");
    }

    @Test 
    public void testDepthFirstSearchUndirected()
    {
        TestGraph testGraph = TestGraph.createSimpleUndirectedGraph();
        assertNotNull(testGraph, () -> "Test graph not created");
        
        Vector<Vertex<Integer>> dfsResult = testGraph.doDepthFirstSearch(testGraph.getVertexFor(1));
        assertEquals(5, dfsResult.size(), () -> "DFS should find all vertices from the test graph");

        Set<Vertex<Integer>> allVertices = testGraph.getVertices();
        assertTrue(new HashSet<>(dfsResult).equals(new HashSet<>(allVertices)), () -> "DFS result set and all vertices should be equal with test graph");
    }

    @Test 
    public void testBreadthFirstSearchDirected()
    {
        TestGraph testGraph = TestGraph.createSimpleDirectedGraph();
        assertNotNull(testGraph, () -> "Test graph not created");
        
        Vector<Vertex<Integer>> bfsResult = testGraph.doBreadthFirstSearch(testGraph.getVertexFor(1));
        assertEquals(5, bfsResult.size(), () -> "BFS should find all vertices from the test graph");

        Set<Vertex<Integer>> allVertices = testGraph.getVertices();
        assertTrue(new HashSet<>(bfsResult).equals(new HashSet<>(allVertices)), () -> "BFS result set and all vertices should be equal with test graph");
    }

    @Test 
    public void testDepthFirstSearchDirected()
    {
        TestGraph testGraph = TestGraph.createSimpleDirectedGraph();
        assertNotNull(testGraph, () -> "Test graph not created");
        
        Vector<Vertex<Integer>> dfsResult = testGraph.doDepthFirstSearch(testGraph.getVertexFor(1));
        assertEquals(5, dfsResult.size(), () -> "DFS should find all vertices from the test graph");

        Set<Vertex<Integer>> allVertices = testGraph.getVertices();
        assertTrue(new HashSet<>(dfsResult).equals(new HashSet<>(allVertices)), () -> "DFS result set and all vertices should be equal with test graph");
    }

}
