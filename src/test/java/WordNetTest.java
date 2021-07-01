import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class WordNetTest {

    @Test
    void whenAnyArgumentIsNull_throwsIllegalArgumentException() {
        WordNet someWordNet = new WordNet("A", "B");
        assertAll(
                () -> assertThatThrownBy(() -> new WordNet(null, null))
                        .isInstanceOf(IllegalArgumentException.class),
                () -> assertThatThrownBy(() -> someWordNet.isNoun(null))
                        .isInstanceOf(IllegalArgumentException.class),
                () -> assertThatThrownBy(() -> someWordNet.sap(null, null))
                        .isInstanceOf(IllegalArgumentException.class),
                () -> assertThatThrownBy(() -> someWordNet.distance(null, null))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void nouns() {
    }

    @Test
    void isNoun() {
    }

    @Test
    void distance() {
    }

    @Test
    void sap() {
    }
}