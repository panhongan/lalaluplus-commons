package com.github.lalalu.utils.dag;

/**
 * lalalu plus
 */

public class TestEdge {

	public static void main(String[] args) {
		Vertex root = new Vertex("root");
		Vertex a = new Vertex("a");
		Edge edge = new Edge(root, a);
		System.out.println(edge.toString()
				+ ", from vertex = " + edge.getFromVertex().hashCode() + ", to vertex = " + edge.getToVertex().hashCode());

		Vertex b = new Vertex("b");
		Edge edge1 = new Edge(root, b);
		System.out.println(edge1.toString()
				+ ", from vertex = " + edge1.getFromVertex().hashCode() + ", to vertex = " + edge1.getToVertex().hashCode());
	}

}
