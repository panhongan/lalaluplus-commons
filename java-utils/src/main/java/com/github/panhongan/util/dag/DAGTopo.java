package com.github.panhongan.util.dag;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.panhongan.util.StringUtil;

public class DAGTopo {
	
	private static Logger logger = LoggerFactory.getLogger(DAGTopo.class);
	
	private List<Vertex> vertexs = new ArrayList<Vertex>();
	
	public List<Vertex> getVertexs() {
		return vertexs;
	}
	
	public void addEdge(Vertex from, Vertex to) {
		if (from == null) {
			logger.warn("from vertex is null");
			return;
		}
		if (to == null) {
			logger.warn("to vertex is null");
			return;
		}
		if (StringUtil.isEmpty(from.getName())) {
			logger.warn("the name of from vertex is empty");
			return;
		}
		if (StringUtil.isEmpty(to.getName())) {
			logger.warn("the name of to vertex is empty");
			return;
		}
		
		Vertex fromVertex = this.find(from.getName());
		Vertex toVertex = this.find(to.getName());
		
		if (fromVertex != null && toVertex != null) {
			Edge edge = fromVertex.findEdge(to.getName());
			if (edge == null) {
				fromVertex.addDownstream(toVertex);
			} else {
				logger.warn("edge already exist : {} -> {}", from.getName(), to.getName());
			}
		}else if (fromVertex != null && toVertex == null) {
			fromVertex.addDownstream(to);
			vertexs.add(to);
		} else if (fromVertex == null && toVertex != null) {
			from.addDownstream(toVertex);
			vertexs.add(from);
		} else {
			from.addDownstream(to);
			vertexs.add(from);
			vertexs.add(to);
		}
	}
	
	public boolean traverse(List<List<Vertex>> traverse_result) {
		boolean ret = false;
		
		if (traverse_result == null) {
			logger.warn("traverse result container is null, no need to traverse");
			return ret;
		}
		
		// 深度拷贝避免修改原始结构数据，因为遍历过程中会递减入度
		Map<String, Vertex> cloneVertexs = new HashMap<String, Vertex>();
		for (Vertex v : vertexs) {
			cloneVertexs.put(v.getName(), Vertex.copy(v));
		}
		
		// 按层次遍历
		int zeroInDegreeVertexCount = 0;
		int totalVertexCount = vertexs.size();
		Set<Vertex> set = new HashSet<Vertex>();
		this.getZeroInDegreeVertex(set);
		
		while (true) {
			if (set.isEmpty()) {
				break;
			}
			
			List<Vertex> list = new ArrayList<Vertex>();
			int size = set.size();
			zeroInDegreeVertexCount += size;
			Object [] arr = set.toArray();
			
			for (int i = 0; i < size; ++i) {
				Vertex v = (Vertex)arr[i];
				if (v == null) {
					break;
				}
				
				v.markTraversed();
				list.add(v);
				for (Edge e : v.getEdges()) {
					e.getToVertex().decreaseInDegree();
				}
			}
			
			if (!list.isEmpty()) {
				traverse_result.add(list);
			}
			
			// 继续获取入库为0的顶点
			this.getZeroInDegreeVertex(set);
		}
		
		// 恢复各顶点的入度
		for (Vertex v : vertexs) {
			v.restore(cloneVertexs.get(v.getName()));
		}
		
		ret = (zeroInDegreeVertexCount == totalVertexCount);

		return ret;
	}
	
	public Vertex find(String vertexName) {
		Vertex v = null;
		
		for (Vertex vertex : vertexs) {
			if (vertex.getName().contentEquals(vertexName)) {
				v = vertex;
				break;
			}
		}
		
		return v;
	}
	
	private void getZeroInDegreeVertex(Set<Vertex> set) {
		set.clear();
		
		for (Vertex v : vertexs) {
			if (v.getInDegree() == 0 && !v.isTraversed()) {
				set.add(v);
			}
		}
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (Vertex v : vertexs) {
			sb.append(v.getName());
			sb.append(" : [");
			
			List<Edge> edges = v.getEdges();
			if (!edges.isEmpty()) {
				for (int i = 0; i < edges.size() - 1; ++i) {
					sb.append(edges.get(i).getToVertex().getName());
					sb.append(",");
				}
				sb.append(edges.get(edges.size() - 1).getToVertex().getName());
			}
			sb.append("]\n");
		}
		return sb.toString();
	}
	
	public static void main(String [] args) {
		DAGTopo topo = new DAGTopo();
		
		Vertex root = new Vertex("root");
		Vertex a = new Vertex("a");
		topo.addEdge(root, a);
		
		Vertex b = new Vertex("b");
		topo.addEdge(root, b);
		
		topo.addEdge(a, b);
		
		Vertex c = new Vertex("c");
		topo.addEdge(a, c);
		
		Vertex d = new Vertex("d");
		topo.addEdge(b, d);
		
		Vertex e = new Vertex("e");
		topo.addEdge(b, e);
		
		List<List<Vertex>> traverse_result = new ArrayList<List<Vertex>>();
		boolean ret = topo.traverse(traverse_result);
		if (ret) {
			for (List<Vertex> list : traverse_result) {
				System.out.println(list.toString());
			}
		} else {
			logger.warn("not a normal dag");
		}
		
	}

}
