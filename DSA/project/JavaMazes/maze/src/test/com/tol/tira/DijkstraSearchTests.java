package com.tol.tira;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Vector;

import com.tol.tira.graph.Edge;

import org.junit.jupiter.api.Test;

/**
 * Basic unit tests for the Graph class. Tests if the graph has the vertices and
 * edges it should have.
 */
public class DijkstraSearchTests {

    @Test
    public void testDijkstraSearch() {
        TestGraph testGraph = TestGraph.createGraphForDijkstraSearch();
        assertNotNull(testGraph, () -> "Test graph not created");

        Integer [] pathAsArray = {1,5,2,6,7,4};
        Vector<Edge<Integer>> shortestPath = testGraph.doDijkstra(testGraph.getVertexFor(1), testGraph.getVertexFor(4));
        assertEquals(pathAsArray.length - 1, shortestPath.size(), () -> "Length of the path not expected");
        int counterToExpectedPath = 0;
        double totalWeight = 0.0;
        for (Edge<Integer> edge : shortestPath) {
            totalWeight += edge.getWeigth();
            assertEquals(pathAsArray[counterToExpectedPath], edge.getSource().getElement(), () -> "Not a correct path");
            counterToExpectedPath++;
        }
        assertEquals(pathAsArray[pathAsArray.length-1], shortestPath.get(shortestPath.size()-1).getDestination().getElement(), () -> "Destination was not the expected");
        assertEquals(19, totalWeight, () -> "Total weight of the shortest path was not the expected");

    }
}
