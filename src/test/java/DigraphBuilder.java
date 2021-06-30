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
