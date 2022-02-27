package com.github.panhongan.utils.dag;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lalalu plus
 * @since 2017.6.6
 */

public class Vertex {

    private final String name;        // 顶点名字

    private int inDegree = 0;    // 顶点入度

    private boolean isTraversed = false;

    private List<Edge> edges = new ArrayList<>();    // 当前顶点指向的顶点, 构成的边

    public Vertex(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getInDegree() {
        return inDegree;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void increaseInDegree() {
        ++inDegree;
    }

    /**
     * Decrease in-degree for vertex
     */
    public void decreaseInDegree() {
        --inDegree;
        if (inDegree < 0) {
            throw new RuntimeException("exceptional inDegree : vertex_name = " + name
                    + ", inDegree = " + inDegree);
        }
    }

    public void markTraversed() {
        isTraversed = true;
    }

    public boolean isTraversed() {
        return isTraversed;
    }

    /**
     * @param v Downstream vertex
     */
    public void addDownstream(Vertex v) {
        if (v != null) {
            edges.add(new Edge(this, v));
            v.increaseInDegree();
        }
    }

    /**
     * @param toVertexName Find Edge from current vertex to 'toVertexName'
     * @return Edge
     */
    public Edge findEdge(String toVertexName) {
        for (Edge e : edges) {
            if (e.getToVertex().getName().contentEquals(toVertexName)) {
                return e;
            }
        }

        return null;
    }

    /**
     * @param to Remove edge from current vertex to 'to'
     */
    public void removeEdge(Vertex to) {
        Edge edge = null;
        for (Edge e : edges) {
            if (e.getToVertex() == to) {
                edge = e;
                break;
            }
        }

        if (edge != null) {
            edges.remove(edge);
        }
    }

    /**
     * @param to Remove edge from current vertex to 'to'
     */
    public void removeEdge(String to) {
        Edge edge = null;

        for (Edge e : edges) {
            if (e.getToVertex().getName().contentEquals(to)) {
                edge = e;
                break;
            }
        }

        if (edge != null) {
            edges.remove(edge);
        }
    }

    public void restore(Vertex v) {
        this.inDegree = v.inDegree;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("name = ");
        sb.append(name);
        sb.append(", inDegree = ");
        sb.append(inDegree);
        sb.append(", children = [");
        if (!edges.isEmpty()) {
            for (int i = 0; i < edges.size() - 1; ++i) {
                sb.append(edges.get(i).getToVertex().getName());
                sb.append(", ");
            }

            sb.append(edges.get(edges.size() - 1).getToVertex().getName());
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * @param v Source vertex
     * @return A clone vertex
     */
    public static Vertex copy(Vertex v) {
        Vertex vertex = new Vertex(v.name);
        vertex.inDegree = v.inDegree;
        vertex.edges = v.edges;
        return vertex;
    }
}
