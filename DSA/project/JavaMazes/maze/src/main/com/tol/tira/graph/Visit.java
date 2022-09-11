package com.tol.tira.graph;

/**
 * Holds a step in traversing the graph via edges.
 * <p>
 * Used by Dijkstra to track the path traversed. Members
 * are public for faster access.
 * 
 * @author Antti Juustila
 * @version 1.0
 */
public class Visit<T extends Comparable<T>> {

    /**
     * The type of the visit.
     */
    public enum Type {
        /** Starting visit is the beginning of the path. */
        START,
        /** After the starting visit, rest of the visit steps are edge visits. */
        EDGE,
    }

    /**
     * Constructs a visit with a type and an edge.
     * @param type Type of the visit.
     * @param edge The edge of the visit.
     */
    Visit(Type type, Edge<T> edge) {
        this.type = type;
        this.edge = edge;
    }

    /**
     * Constructs a visit of type START with a null edge.
     */
    Visit() {
        this.type = Type.START;
        this.edge = null;
    }

    public Type type;
    public Edge<T> edge = null;
}
