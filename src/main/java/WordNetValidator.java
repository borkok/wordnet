import edu.princeton.cs.algs4.DepthFirstOrder;
import edu.princeton.cs.algs4.Digraph;

import java.util.Arrays;
import java.util.Objects;

class WordNetValidator {
    static void notNull(Object... o) {
        if(Arrays.stream(o).anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException();
        }
    }

    static void isAcyclicRooted(Digraph digraph) {
        if (!hasOnlyOneRoot(digraph)) {
            throw new IllegalArgumentException();
        }
        if (!isAcyclic(digraph)) {
            throw new IllegalArgumentException();
        }
    }

    private static boolean hasOnlyOneRoot(Digraph digraph) {
        int roots = 0;
        for (int v = 0; v < digraph.V(); v++) {
            if (digraph.outdegree(v) == 0) {
                if (roots > 0) return false;
                roots++;
            }
        }
        return roots == 1;
    }

    private static boolean isAcyclic(Digraph digraph) {
        DepthFirstOrder depthFirstOrder = new DepthFirstOrder(digraph);
        Integer rootCandidate = depthFirstOrder.post().iterator().next();
        return digraph.outdegree(rootCandidate) == 0;
    }
}
