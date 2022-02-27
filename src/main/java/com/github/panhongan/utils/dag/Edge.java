package com.github.panhongan.utils.dag;

import lombok.Getter;

/**
 * @author lalalu plus
 * @since 2017.5.6
 */

@Getter
public class Edge {

    private final Vertex fromVertex;    // 出发顶点

    private final Vertex toVertex;    // 被指向顶点

    public Edge(Vertex from, Vertex to) {
        this.fromVertex = from;
        this.toVertex = to;
    }

    @Override
    public String toString() {
        return "edge : from = "
                + fromVertex.getName()
                + ", to = "
                + toVertex.getName();
    }
}
