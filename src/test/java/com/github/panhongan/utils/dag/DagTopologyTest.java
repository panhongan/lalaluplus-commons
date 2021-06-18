package com.github.panhongan.utils.dag;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lalalu plus
 * @since 2018.1.1
 */

public class DagTopologyTest {

    public static void main(String[] args) {
        DagTopology topo = new DagTopology();

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

        List<List<Vertex>> traverse_result = new ArrayList<>();
        boolean ret = topo.traverse(traverse_result);
        if (ret) {
            for (List<Vertex> list : traverse_result) {
                System.out.println(list.toString());
            }
        } else {
            System.out.println("not a normal dag");
        }
    }
}
