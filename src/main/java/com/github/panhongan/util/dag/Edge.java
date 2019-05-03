package com.github.panhongan.util.dag;

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
	
	public static void main(String [] args) {
		Vertex root = new Vertex("root");
		Vertex a = new Vertex("a");
		Edge edge = new Edge(root, a);
		System.out.println("copy addr = " + edge.hashCode() + ", " + edge.toString()
				+ ", from vertex = " + edge.getFromVertex().hashCode() + ", to vertex = " + edge.getToVertex().hashCode());
		
		Vertex b = new Vertex("b");
		Edge edge1 = new Edge(root, b);
		System.out.println("copy addr = " + edge1.hashCode() + ", " + edge1.toString()
				+ ", from vertex = " + edge1.getFromVertex().hashCode() + ", to vertex = " + edge1.getToVertex().hashCode());
	}

}
