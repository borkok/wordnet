import edu.princeton.cs.algs4.Digraph;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

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
                )
        );
    }

/*    @ParameterizedTest
    @MethodSource("params")
    void shortestAncestralPathBetweenNodeGroups(Digraph digraph) {
        assertThat(sap.length(group, otherGroup)).isEqualTo(expectedLength);
        assertThat(sap.ancestor(group, otherGroup)).isEqualTo(expectedAncestor);
    }*/
}