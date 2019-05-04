package com.github.lalalu.utils.dag;

import java.util.ArrayList;
import java.util.List;

/**
 * lalalu plus
 */

public class TestDagTopo {

    public static void main(String [] args) {
        DagTopo topo = new DagTopo();

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
