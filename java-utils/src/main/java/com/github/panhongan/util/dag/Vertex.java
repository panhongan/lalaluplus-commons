package com.github.panhongan.util.dag;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Vertex {
	
	private static final Logger logger = LoggerFactory.getLogger(Vertex.class);
	
	private String name = "";	// 顶点名字
	
	private int in_degree = 0; 	// 顶点入度
	
	private boolean is_traversed = false;
	
	private List<Edge> edges = new ArrayList<Edge>();	// 当前顶点指向的顶点
	
	public Vertex(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getInDegree() {
		return in_degree;
	}
	
	public List<Edge> getEdges() {
		return edges;
	}
	
	public void increaseInDegree() {
		++in_degree;
	}
	
	public void decreaseInDegree() {
		--in_degree;
		if (in_degree < 0) {
			logger.warn("exceptional in_degree : vertex_name = {}, in_degree = {}", name, in_degree);
		}
	}
	
	public void markTraversed() {
		is_traversed = true;
	}
	
	public boolean isTraversed() {
		return is_traversed;
	}
	
	public void addDownstream(Vertex v) {
		if (v != null) {
			edges.add(new Edge(this, v));
			v.increaseInDegree();
		}
	}
	
	public Edge findEdge(String toVertexName) {
		Edge edge = null;
		
		for (Edge e : edges) {
			if (e.getToVertex().getName().contentEquals(toVertexName)) {
				edge = e;
				break;
			}
		}
		
		return edge;
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
		this.in_degree = v.in_degree;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("name = ");
		sb.append(name);
		sb.append(", in_degree = ");
		sb.append(in_degree);
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
		vertex.in_degree = v.in_degree;
		vertex.edges = v.edges;
		return vertex;
	}
	


}
