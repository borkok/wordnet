import edu.princeton.cs.algs4.Digraph;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class SAPTest {

    public static final Digraph EMPTY_DIGRAPH = new Digraph(1);

    @Test
    void whenAnyArgumentIsNull_throwsIllegalArgumentException() {
        assertAll(
                () -> assertThatThrownBy(() -> new SAP(null)).isInstanceOf(IllegalArgumentException.class),
                () -> assertThatThrownBy(() -> new SAP(EMPTY_DIGRAPH).ancestor(null, null)).isInstanceOf(IllegalArgumentException.class),
                () -> assertThatThrownBy(() -> new SAP(EMPTY_DIGRAPH).length(null, null)).isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void shortestAncestralPathBetweenSingleNodes() {}
}