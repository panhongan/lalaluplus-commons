package com.github.panhongan.utils.dag;

/**
 * @author lalalu plus
 * @since 2017.5.6
 */

public class Edge {

    private final Vertex fromVertex;    // 出发顶点

    private final Vertex toVertex;    // 被指向顶点

    public Edge(Vertex from, Vertex to) {
        this.fromVertex = from;
        this.toVertex = to;
    }

    public Vertex getFromVertex() {
        return fromVertex;
    }

    public Vertex getToVertex() {
        return toVertex;
    }

    @Override
    public String toString() {
        return "edge : from = "
                + fromVertex.getName()
                + ", to = "
                + toVertex.getName();
    }
}
