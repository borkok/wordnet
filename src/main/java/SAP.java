import edu.princeton.cs.algs4.Digraph;

import java.util.Arrays;
import java.util.Objects;

/*
Throw an IllegalArgumentException in the following situations:

Any argument is null
Any vertex argument is outside its prescribed range
Any iterable argument contains a null item

You are free to call the relevant methods in BreadthFirstDirectedPaths.java
 */
public class SAP {

    private final Digraph digraph;

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

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        if (digraph.outdegree(v) > 0 || digraph.outdegree(w) > 0) {
            return 1;
        }
        return -1;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        if (digraph.outdegree(v) > 0) {
            return w;
        }
        if (digraph.outdegree(w) > 0) {
            return v;
        }
        return -1;
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

    // do unit testing of this class
    public static void main(String[] args) {}
}