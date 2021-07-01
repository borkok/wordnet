import edu.princeton.cs.algs4.Digraph;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class SAPTest {

    @Test
    void whenAnyArgumentIsNull_throwsIllegalArgumentException() {
        assertAll(
                () -> assertThatThrownBy(() -> new SAP(null))
                        .isInstanceOf(IllegalArgumentException.class),
                () -> assertThatThrownBy(() -> new SAP(DigraphBuilder.empty()).ancestor(null, null))
                        .isInstanceOf(IllegalArgumentException.class),
                () -> assertThatThrownBy(() -> new SAP(DigraphBuilder.empty()).length(null, null))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @ParameterizedTest
    @MethodSource("params")
    void shortestAncestralPathBetweenSingleNodes(Digraph digraph,
                                                 int v,
                                                 int w,
                                                 int expectedLength,
                                                 int expectedAncestor) {
        SAP sap = new SAP(digraph);

        assertThat(sap.length(v, w)).isEqualTo(expectedLength);
        assertThat(sap.ancestor(v, w)).isEqualTo(expectedAncestor);
    }

    private static Stream<Arguments> params() {
        return Stream.of(
                //digraph without edges
                Arguments.of(
                        new Digraph(2), 0, 1, -1, -1
                ),
                //digraph with one edge
                Arguments.of(
                        DigraphBuilder.vertices(2)
                                .edge(0,1)
                                .build(),
                        0, 1, 1, 1
                ),
                Arguments.of(
                        DigraphBuilder.vertices(2)
                                      .edge(1, 0)
                                      .build(),
                        0, 1, 1, 0
                ),
                //two edges
                Arguments.of(
                        DigraphBuilder.vertices(3)
                                      .edge(1, 0)
                                      .edge(2, 0)
                                      .build(),
                        2, 1, 2, 0
                ),
                //many edges
                Arguments.of(
                        DigraphBuilder.theRootedButNotATree(),
                        0, 8, 4, 5
                ),
                Arguments.of(
                        DigraphBuilder.theRootedButNotATree(),
                        0, 5, 3, 5
                )

        );
    }

    @ParameterizedTest
    @MethodSource("groupParams")
    void shortestAncestralPathBetweenNodeGroups(Digraph digraph,
                                                Set<Integer> group,
                                                Set<Integer> otherGroup,
                                                int expectedLength,
                                                int expectedAncestor) {
        SAP sap = new SAP(digraph);

        assertThat(sap.length(group, otherGroup)).isEqualTo(expectedLength);
        assertThat(sap.ancestor(group, otherGroup)).isEqualTo(expectedAncestor);
    }

    private static Stream<Arguments> groupParams() {
        return Stream.of(
                //digraph without edges
                Arguments.of(
                        new Digraph(2),
                        Set.of(0), Set.of(1), -1, -1
                ),
                //digraph with one edge
                Arguments.of(
                        DigraphBuilder.vertices(2)
                                      .edge(0,1)
                                      .build(),
                        Set.of(0), Set.of(1), 1, 1
                ),
                Arguments.of(
                        DigraphBuilder.vertices(2)
                                      .edge(1, 0)
                                      .build(),
                        Set.of(0), Set.of(1), 1, 0
                ),
                //two edges
                Arguments.of(
                        DigraphBuilder.vertices(3)
                                      .edge(1, 0)
                                      .edge(2, 0)
                                      .build(),
                        Set.of(2), Set.of(1), 2, 0
                ),
                //many edges
                Arguments.of(
                        DigraphBuilder.theRootedButNotATree(),
                        Set.of(0, 1), Set.of(8), 4, 5
                ),
                Arguments.of(
                        DigraphBuilder.theRootedButNotATree(),
                        Set.of(0, 1), Set.of(4, 6), 3, 3
                )

        );
    }

    @Test
    public void checkingSetInMap() {
        Set<Integer> set123 = Set.of(1, 2, 3);
        Set<Integer> set231 = Set.of(2, 3, 1);
        HashMap<Set<Integer>, Integer> map = new HashMap<>();
        map.put(set123, 1);
        map.put(set231, 2);
        assertThat(set123.equals(set231)).isTrue();
        assertThat(map.get(set123)).isEqualTo(2);
    }
}