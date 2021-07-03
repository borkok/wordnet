import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.net.URL;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.Collections.emptySet;
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
                        .isInstanceOf(IllegalArgumentException.class),

                () -> assertThatThrownBy(() -> new SAP(DigraphBuilder.vertices(5).build()).length(-1,0))
                        .isInstanceOf(IllegalArgumentException.class),
                () -> assertThatThrownBy(() -> new SAP(DigraphBuilder.vertices(5).build()).length(0,-1))
                        .isInstanceOf(IllegalArgumentException.class),
                () -> assertThatThrownBy(() -> new SAP(DigraphBuilder.vertices(5).build()).length(5,0))
                        .isInstanceOf(IllegalArgumentException.class),
                () -> assertThatThrownBy(() -> new SAP(DigraphBuilder.vertices(5).build()).length(0, 5))
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
                ),
                Arguments.of(
                        DigraphBuilder.theRootedButNotATree(),
                        5, 2, 2, 5
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
                ),
                //empty iterables
                Arguments.of(
                        DigraphBuilder.theRootedButNotATree(),
                        emptySet(), Set.of(8), -1, -1
                ),
                Arguments.of(
                        DigraphBuilder.theRootedButNotATree(),
                        Set.of(0, 1), emptySet(), -1, -1
                ),
                Arguments.of(
                        DigraphBuilder.theRootedButNotATree(),
                        emptySet(), emptySet(), -1, -1
                )

        );
    }

    @Test
    void memoryTest() {
        SAP sap = new SAP(new Digraph(new In(findUrl("digraph-wordnet.txt"))));
        for (int i = 0; i < 1000; i++) {
            int v = RandomUtils.nextInt(0, 82192);
            int w = RandomUtils.nextInt(0, 82192);
            sap.length(v, w);
        }
        for (int i = 0; i < 1000; i++) {
            Set<Integer> firstSet = new HashSet<>();
            Set<Integer> secondSet = new HashSet<>();
            for (int j = 0; j < 5; j++) {
                firstSet.add(RandomUtils.nextInt(0, 82192));
                secondSet.add(RandomUtils.nextInt(0, 82192));
            }
            sap.length(firstSet, secondSet);
        }
    }

    private URL findUrl(String filename) {
        return Objects.requireNonNull(getClass().getResource(filename));
    }
}