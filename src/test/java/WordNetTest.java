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
                        new String[] {
                                "43,word_net,description of a word", "45,word synonym,desc"
                        },
                        List.of("word", "synonym", "word_net")
                )
        );
    }

    @Test
    void selfDistanceIsZero() {
        String[] synsets = new String[] { "0,word_net,description of a word", "1,word synonym,desc" };
        String[] hypernyms = new String[] { "1,0" };
        WordNet wordNet = WordNet.fromCsv(synsets, hypernyms);

        assertThat(wordNet.distance("word", "synonym")).isZero();
        assertThat(wordNet.distance("word", "word")).isZero();
        assertThat(wordNet.distance("word_net", "word_net")).isZero();
    }

    @ParameterizedTest
    @MethodSource("distanceParams")
    void distance(String[] synsets, String[] hypernyms,
             List<String> hyponyms, String ancestor, int distance) {
        WordNet wordNet = WordNet.fromCsv(synsets, hypernyms);

        hyponyms.forEach(hyponym -> assertAll(
                () -> assertThat(wordNet.distance(hyponym, ancestor)).as(hyponym + "->" + ancestor)
                                                                     .isEqualTo(distance),
                () -> assertThat(wordNet.distance(ancestor, hyponym)).as(ancestor + "->" + hyponym)
                                                                     .isEqualTo(distance)
        ));
    }

    private static Stream<Arguments> distanceParams() {
        return Stream.of(
                Arguments.of(
                        new String[] { "0,word_net,description of a word", "1,word synonym,desc" },
                        new String[] { "1,0" },
                        List.of("word", "synonym"), "word_net", 1
                )
        );
    }

    @ParameterizedTest
    @MethodSource("sapParams")
    void sap(String[] synsets, String[] hypernyms,
                  List<String> hyponyms, String ancestor) {
        WordNet wordNet = WordNet.fromCsv(synsets, hypernyms);

        for (int i = 1; i < hyponyms.size(); i++) {
            assertThat(wordNet.sap(hyponyms.get(i - 1), hyponyms.get(i))).isEqualTo(ancestor);
        }
    }

    private static Stream<Arguments> sapParams() {
        return Stream.of(
                Arguments.of(
                        new String[] { "0,word_net,description of a word", "1,word,desc", "2,other_word,description" },
                        new String[] { "1,0", "2,0" },
                        List.of("word", "other_word"), "word_net"
                )
        );
    }
}