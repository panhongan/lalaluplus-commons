package com.github.lalalu.utils.dag;

/**
 * lalalu plus
 */

public class Edge {

	private Vertex fromVertex;	// 出发顶点
	
	private Vertex toVertex;	// 被指向顶点
	
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
		StringBuffer sb = new StringBuffer();
		sb.append("edge : from = ");
		sb.append(fromVertex.getName());
		sb.append(", to = ");
		sb.append(toVertex.getName());
		return sb.toString();
	}

}
