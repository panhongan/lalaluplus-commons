package com.github.panhongan.util.dag;

import java.util.ArrayList;
import java.util.List;

/**
 * lalalu plus
 */

public class Vertex {

	private String name;	    // 顶点名字
	
	private int inDegree = 0; 	// 顶点入度
	
	private boolean isTraversed = false;
	
	private List<Edge> edges = new ArrayList<>();	// 当前顶点指向的顶点, 构成的边
	
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
	
	public void decreaseInDegree() {
		--inDegree;
		if (inDegree < 0) {
			throw new RuntimeException("exceptional inDegree : vertex_name = " + name + ", inDegree = " + inDegree);
		}
	}
	
	public void markTraversed() {
		isTraversed = true;
	}
	
	public boolean isTraversed() {
		return isTraversed;
	}
	
	public void addDownstream(Vertex v) {
		if (v != null) {
			edges.add(new Edge(this, v));
			v.increaseInDegree();
		}
	}
	
	public Edge findEdge(String toVertexName) {
		for (Edge e : edges) {
			if (e.getToVertex().getName().contentEquals(toVertexName)) {
				return e;
			}
		}
		
		return null;
	}
	
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
		StringBuffer sb = new StringBuffer();
		sb.append("name = ");
		sb.append(name);
		sb.append(", inDegree = ");
		sb.append(inDegree);
		sb.append(", childs = [");
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
	
	public static Vertex copy(Vertex v) {
		Vertex vertex = new Vertex(v.name);
		vertex.inDegree = v.inDegree;
		vertex.edges = v.edges;
		return vertex;
	}

}
