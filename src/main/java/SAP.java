import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/*
Throw an IllegalArgumentException in the following situations:

Any argument is null
Any vertex argument is outside its prescribed range
Any iterable argument contains a null item

You are free to call the relevant methods in BreadthFirstDirectedPaths.java
 */
public class SAP {

    private final Digraph digraph;
    //cache BreadthFirstDirectedPaths

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph digraph) {
        validateNotNull(digraph);
        this.digraph = digraph;
    }

    private void validateNotNull(Object... o) {
        if(Arrays.stream(o).anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * @return length of shortest ancestral path between v and w; -1 if no such path
     */
    public int length(int v, int w) {
        return findSAP(v, w).map(Ancestor::getDistanceTo).orElse(-1);
    }

    /**
     * @return a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
     */
    public int ancestor(int v, int w) {
        return findSAP(v, w).map(Ancestor::getAncestor).orElse(-1);
    }

    private Optional<Ancestor> findSAP(int v, int w) {
        if (digraph.outdegree(v) <= 0 && digraph.outdegree(w) <= 0) {
            return Optional.empty();
        }
        return findDistancesSumByVertex(v, w).stream().sorted().findFirst();
    }

    private List<Ancestor> findDistancesSumByVertex(int v, int w) {
        BreadthFirstDirectedPaths bfsForV = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths bfsForW = new BreadthFirstDirectedPaths(digraph, w);

        List<Ancestor> distancesSum = new ArrayList<>();
        for (int i = 0; i < digraph.V(); i++) {
            if (bfsForV.distTo(i) < Integer.MAX_VALUE && bfsForW.distTo(i) < Integer.MAX_VALUE) {
                distancesSum.add(new Ancestor(i, bfsForV.distTo(i) + bfsForW.distTo(i)));
            }
        }
        return distancesSum;
    }


    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        validateNotNull(v, w);
        return -1;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        validateNotNull(v, w);
        return -1;
    }

    private static class Ancestor implements Comparable<Ancestor> {
        int ancestor;
        int distanceTo;

        Ancestor(int ancestor, int distanceTo) {
            this.ancestor = ancestor;
            this.distanceTo = distanceTo;
        }

        int getAncestor() {
            return ancestor;
        }

        int getDistanceTo() {
            return distanceTo;
        }

        @Override
        public int compareTo(Ancestor other) {
            return this.distanceTo - other.distanceTo;
        }
    }
}