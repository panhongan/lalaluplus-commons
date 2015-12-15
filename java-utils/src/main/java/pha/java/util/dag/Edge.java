package pha.java.util.dag;


public class Edge {
	
	private Vertex from_vertex = null;	// 指向顶点
	
	private Vertex to_vertex = null;	// 被指向顶点
	
	public Edge(Vertex from, Vertex to) {
		this.from_vertex = from;
		this.to_vertex = to;
	}
	
	public Vertex getFromVertex() {
		return from_vertex;
	}
	
	public Vertex getToVertex() {
		return to_vertex;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("edge : from = ");
		sb.append(from_vertex.getName());
		sb.append(", to = ");
		sb.append(to_vertex.getName());
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
