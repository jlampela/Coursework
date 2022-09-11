package com.tol.tira;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Vector;
import java.util.Set;

import com.tol.tira.graph.Vertex;
import com.tol.tira.graph.Edge;

import org.junit.jupiter.api.Test;

/**
 * Basic unit tests for the Graph class.
 * Tests if the graph has the vertices and edges it should have.
 */
public class BasicGraphTests {

    @Test 
    public void testSimpleGraph()
    {
        TestGraph testGraph = TestGraph.createSimpleUndirectedGraph();
        assertNotNull(testGraph, () -> "Test graph not created");
        Set<Vertex<Integer>> vertices = testGraph.getVertices();
        assertEquals(5, vertices.size(), () -> "Graph does not have the expected number of vertices");
        for (Vertex<Integer> vertex : vertices) {
            Vector<Edge<Integer>> edges = testGraph.getEdges(vertex);
            switch (vertex.getElement()) {
                case 1:
                    assertEquals(1, edges.size(), () -> "Vertex does not have the expected number of edges");
                    break;
                case 2:
                    assertEquals(4, edges.size(), () -> "Vertex does not have the expected number of edges");
                    break;
                case 3, 4, 5:
                    assertEquals(3, edges.size(), () -> "Vertex does not have the expected number of edges");
                    break;
                default:
                    fail("Vertex ids should be 1..5");
                break;
            }
        }
    }

    @Test 
    public void testSimpleDirectedGraph()
    {
        TestGraph testGraph = TestGraph.createSimpleDirectedGraph();
        assertNotNull(testGraph, () -> "Test graph not created");
        Set<Vertex<Integer>> vertices = testGraph.getVertices();
        assertEquals(5, vertices.size(), () -> "Graph does not have the expected number of vertices");
        for (Vertex<Integer> vertex : vertices) {
            Vector<Edge<Integer>> edges = testGraph.getEdges(vertex);
            switch (vertex.getElement()) {
                case 1:
                    assertEquals(1, edges.size(), () -> "Vertex does not have the expected number of edges");
                    assertEquals(1.0, edges.get(0).getWeigth(), () -> "Edge weight was not correct");
                    break;
                case 2:
                    assertEquals(2, edges.size(), () -> "Vertex does not have the expected number of edges");
                    double weight = edges.get(0).getWeigth() + edges.get(1).getWeigth();
                    assertEquals(3.0, weight, () -> "Edge weight was not correct");
                    break;
                case 3:
                    assertEquals(2, edges.size(), () -> "Vertex does not have the expected number of edges");
                    weight = edges.get(0).getWeigth() + edges.get(1).getWeigth();
                    assertEquals(5.5, weight, () -> "Edge weight was not correct");
                    break;
                case 4:
                    assertEquals(1, edges.size(), () -> "Vertex does not have the expected number of edges");
                    assertEquals(11.5, edges.get(0).getWeigth(), () -> "Edge weight was not correct");
                    break;
                case 5:
                    assertEquals(1, edges.size(), () -> "Vertex does not have the expected number of edges");
                    assertEquals(3.5, edges.get(0).getWeigth(), () -> "Edge weight was not correct");
                    break;
                default:
                    fail("Vertex ids should be 1..5");
                    break;
            }
        }
    }



}
