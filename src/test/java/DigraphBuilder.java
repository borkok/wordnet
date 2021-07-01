/*
 * Copyright (c) 2021. BEST S.A. and/or its affiliates. All rights reserved.
 */

import edu.princeton.cs.algs4.Digraph;
import org.apache.commons.lang3.tuple.Pair;

import java.util.LinkedList;
import java.util.List;

class DigraphBuilder {
    private final int v;
    private List<Pair<Integer, Integer>> edges = new LinkedList<>();

    static Digraph empty() {
        return new Digraph(1);
    }

    static Digraph theRootedButNotATree() {
        return vertices(9)
                .edge(0, 2)
                .edge(1,2)
                .edge(2,3)
                .edge(4,3)
                .edge(3,5)
                .edge(6,5)
                .edge(7,6)
                .edge(8,7)
                .edge(8,5)
                .build();
    }

    static DigraphBuilder vertices(int v) {
        return new DigraphBuilder(v);
    }

    private DigraphBuilder(int v) {
        this.v = v;
    }

    DigraphBuilder edge(int v, int w) {
        edges.add(Pair.of(v, w));
        return this;
    }

    Digraph build() {
        Digraph digraph = new Digraph(v);
        edges.forEach(pair -> digraph.addEdge(pair.getLeft(), pair.getRight()));
        return digraph;
    }
}
