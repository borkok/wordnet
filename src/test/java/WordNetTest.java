import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class WordNetTest {

    @Test
    void whenAnyArgumentIsNull_throwsIllegalArgumentException() {
        WordNet someWordNet = WordNet.fromCsv(new String[] { }, new String[] { });
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

    @ParameterizedTest
    @MethodSource("params")
    void nouns(String[] synsets, List<String> expectedNouns) {
        WordNet wordNet = WordNet.fromCsv(synsets, new String[] { });

        assertThat(wordNet.nouns()).containsAll(expectedNouns);
        expectedNouns.forEach(noun -> assertThat(wordNet.isNoun(noun)).isTrue());
        assertThat(wordNet.isNoun("not_existing_word")).isFalse();
    }

    private static Stream<Arguments> params() {
        return Stream.of(
                Arguments.of(
                        new String[] { "43,word_net,description of a word" },
                        List.of("word_net")
                ),
                Arguments.of(
                        new String[] { "43,word_net,description of a word", "45,word synonym,desc" },
                        List.of("word", "synonym", "word_net")
                )
        );
    }

    @Test
    void distance() {
    }

    @Test
    void sap() {
    }
}