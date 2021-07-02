import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class SapForVerticePair {
    private final Digraph digraph;
    private final BreadthFirstDirectedPaths[] bfsCache;

    SapForVerticePair(Digraph digraph) {
        this.digraph = digraph;
        this.bfsCache = new BreadthFirstDirectedPaths[digraph.V()];
    }

    Optional<Ancestor> findSAP(int v, int w) {
        if (v == w) {
            return Optional.of(new Ancestor(v, 0));
        }
        return findDistancesSumByVertex(v, w).stream().sorted().findFirst();
    }

    private List<Ancestor> findDistancesSumByVertex(int v, int w) {
        BreadthFirstDirectedPaths bfsForV = findBfsForV(v);
        BreadthFirstDirectedPaths bfsForW = findBfsForV(w);

        List<Ancestor> distancesSum = new ArrayList<>();
        for (int i = 0; i < digraph.V(); i++) {
            if (bfsForV.distTo(i) < Integer.MAX_VALUE && bfsForW.distTo(i) < Integer.MAX_VALUE) {
                distancesSum.add(new Ancestor(i, bfsForV.distTo(i) + bfsForW.distTo(i)));
            }
        }
        return distancesSum;
    }

    private BreadthFirstDirectedPaths findBfsForV(int v) {
        if (bfsCache[v] == null) {
            bfsCache[v] = new BreadthFirstDirectedPaths(digraph, v);
        }
        return bfsCache[v];
    }

}
