package com.github.lalalu.utils.dag;

/**
 * lalalu plus
 */

public class TestVertex {
	
	public static void main(String [] args) {
		Vertex root = new Vertex("root");
		System.out.println(root.toString());
		
		Vertex a = new Vertex("a");
		root.addDownstream(a);
		System.out.println(root.toString());
		System.out.println(a.toString());
		
		Vertex b = new Vertex("b");
		root.addDownstream(b);
		System.out.println(root.toString());
		System.out.println(b.toString());
		
		Vertex c = new Vertex("c");
		root.addDownstream(c);
		System.out.println(root.toString());
		System.out.println(c.toString());
		
		Vertex d = new Vertex("d");
		b.addDownstream(d);
		System.out.println(b.toString());
		System.out.println(d.toString());
	}

}
