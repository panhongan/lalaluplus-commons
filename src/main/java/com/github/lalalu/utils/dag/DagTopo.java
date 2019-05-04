package com.github.lalalu.utils.dag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

/**
 * lalalu plus
 */

public class DagTopo {

	private List<Vertex> vertexs = new ArrayList<>();
	
	public List<Vertex> getVertexs() {
		return vertexs;
	}
	
	public void addEdge(Vertex from, Vertex to) {
		if (from == null || to == null) {
		    throw new RuntimeException("vertex is null");
		}

		if (StringUtils.isEmpty(from.getName())) {
            throw new RuntimeException("the from vertex name is empty");
		}

		if (StringUtils.isEmpty(to.getName())) {
            throw new RuntimeException("the to vertex name is empty");
		}
		
		Vertex fromVertex = this.find(from.getName());
		Vertex toVertex = this.find(to.getName());
		
		if (fromVertex != null && toVertex != null) {
			Edge edge = fromVertex.findEdge(to.getName());
			if (edge == null) {
				fromVertex.addDownstream(toVertex);
			} else {
			    // already exists
				return;
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
	
	public boolean traverse(List<List<Vertex>> traverseResult) {
		boolean ret = false;
		
		if (traverseResult == null) {
			throw new RuntimeException("traverse result container is null, no need to traverse");
		}
		
		// 深度拷贝避免修改原始结构数据，因为遍历过程中会递减入度
		Map<String, Vertex> cloneVertexs = new HashMap<>();
		for (Vertex v : vertexs) {
			cloneVertexs.put(v.getName(), Vertex.copy(v));
		}
		
		// 按层次遍历
		int zeroInDegreeVertexCount = 0;
		int totalVertexCount = vertexs.size();
		Set<Vertex> set = new HashSet<>();
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
                traverseResult.add(list);
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
		for (Vertex vertex : vertexs) {
			if (vertex.getName().contentEquals(vertexName)) {
				return vertex;
			}
		}
		return null;
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

}
