import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;

import java.util.Arrays;
import java.util.Objects;

class WordNetValidator {

    static void vertexIndices(int count, int... vertices) {
        for (int v : vertices) {
            if (v < 0 || v >= count) {
                throw new IllegalArgumentException();
            }
        }
    }

    static void notNull(Object... objects) {
        if (Arrays.stream(objects).anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException();
        }
    }

    static void isAcyclicRooted(Digraph digraph) {
        if (!hasOnlyOneRoot(digraph)) {
            throw new IllegalArgumentException();
        }
        if (isCyclic(digraph)) {
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

    private static boolean isCyclic(Digraph digraph) {
        return new DirectedCycle(digraph).hasCycle();
    }
}
