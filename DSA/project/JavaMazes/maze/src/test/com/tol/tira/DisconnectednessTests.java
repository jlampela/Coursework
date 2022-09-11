package com.tol.tira;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Basic unit tests for the Graph class.
 * Tests if the graph has the vertices and edges it should have.
 */
public class DisconnectednessTests {

    @Test 
    public void testConnectedUndirectedGraph()
    {
        TestGraph testGraph = TestGraph.createSimpleUndirectedGraph();
        assertNotNull(testGraph, () -> "Test graph not created");
        assertFalse(testGraph.isDisconnected(), () -> "This graph should be connected");
    }

    @Test 
    public void testConnectedDirectedGraph()
    {
        TestGraph testGraph = TestGraph.createSimpleDirectedGraph();
        assertNotNull(testGraph, () -> "Test graph not created");
        assertFalse(testGraph.isDisconnected(), () -> "This graph should be connected");
    }

    @Test 
    public void testDisconnectedUndirectedGraph()
    {
        TestGraph testGraph = TestGraph.createSimpleUndirectedDisconnectedGraph();
        assertNotNull(testGraph, () -> "Test graph not created");
        assertTrue(testGraph.isDisconnected(), () -> "This graph should be disconnected");
    }


}
