package com.github.panhongan.utils.dag;

import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author lalalu plus
 * @since 2018.4.20
 */

public class DagTopology {

    private List<Vertex> vertexList = new ArrayList<>();

    public List<Vertex> getVertexList() {
        return vertexList;
    }

    /**
     * @param from From Vertex
     * @param to To Vertex
     */
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

        if (Objects.nonNull(fromVertex)) {
            if (Objects.nonNull(toVertex)) {
                Edge edge = fromVertex.findEdge(to.getName());
                if (Objects.nonNull(edge)) {
                    fromVertex.addDownstream(toVertex);
                }
            } else {
                fromVertex.addDownstream(to);
                vertexList.add(to);
            }
        } else {
            if (Objects.nonNull(toVertex)) {
                from.addDownstream(toVertex);
                vertexList.add(from);
            } else {
                from.addDownstream(to);
                vertexList.add(from);
                vertexList.add(to);
            }
        }
    }

    /**
     * @param traverseResult Traverse DAG
     * @return True if traverse successfully. Else False
     */
    public boolean traverse(List<List<Vertex>> traverseResult) {
        Preconditions.checkArgument(CollectionUtils.isNotEmpty(traverseResult),
                "traverse result container is null, no need to traverse");

        // 深度拷贝避免修改原始结构数据，因为遍历过程中会递减入度
        Map<String, Vertex> cloneVertexList = new HashMap<>();
        for (Vertex v : vertexList) {
            cloneVertexList.put(v.getName(), Vertex.copy(v));
        }

        // 按层次遍历
        int zeroInDegreeVertexCount = 0;
        Set<Vertex> set = new HashSet<>();
        this.getZeroInDegreeVertex(set);

        while (CollectionUtils.isNotEmpty(set)) {
            List<Vertex> list = new ArrayList<>();
            int size = set.size();
            zeroInDegreeVertexCount += size;
            Object[] arr = set.toArray();

            for (int i = 0; i < size; ++i) {
                Vertex v = (Vertex) arr[i];
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
        for (Vertex v : vertexList) {
            v.restore(cloneVertexList.get(v.getName()));
        }

        return (zeroInDegreeVertexCount == vertexList.size());
    }

    private Vertex find(String vertexName) {
        for (Vertex vertex : vertexList) {
            if (vertex.getName().contentEquals(vertexName)) {
                return vertex;
            }
        }
        return null;
    }

    private void getZeroInDegreeVertex(Set<Vertex> set) {
        set.clear();
        for (Vertex v : vertexList) {
            if (v.getInDegree() == 0 && !v.isTraversed()) {
                set.add(v);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Vertex v : vertexList) {
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
