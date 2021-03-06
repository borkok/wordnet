import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

class SapForVerticeGroups {
    private final Digraph digraph;
    private final LimitedCache<Set<Integer>, BreadthFirstDirectedPaths> bfsCache;

    SapForVerticeGroups(Digraph digraph) {
        this.digraph = digraph;
        this.bfsCache = new LimitedCache<>();
    }

    Optional<Ancestor> findSAP(Iterable<Integer> v, Iterable<Integer> w) {
        WordNetValidator.notNull(v, w);
        Set<Integer> vSet = StreamSupport.stream(v.spliterator(), false)
                                            .collect(Collectors.toSet());
        Set<Integer> wSet = StreamSupport.stream(w.spliterator(), false)
                                         .collect(Collectors.toSet());
        if (vSet.isEmpty() || wSet.isEmpty()) return Optional.empty();
        return findDistancesSumByGroup(vSet, wSet).stream().sorted().findFirst();
    }

    private List<Ancestor> findDistancesSumByGroup(Set<Integer> v, Set<Integer> w) {
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

    private BreadthFirstDirectedPaths findBfsForV(Set<Integer> v) {
        if (!bfsCache.contains(v)) {
            bfsCache.store(v, new BreadthFirstDirectedPaths(digraph, v));
        }
        return bfsCache.find(v);
    }
}
