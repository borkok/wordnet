import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class OutcastTest {

    @ParameterizedTest
    @MethodSource("params")
    void outcast(String[] synsets, String[] hypernyms, String[] nouns,
                 String expectedOutcast) {
        WordNet wordNet = WordNet.fromCsv(synsets, hypernyms);
        String outcast = new Outcast(wordNet).outcast(nouns);
        assertThat(outcast).isEqualTo(expectedOutcast);
    }

    private static Stream<Arguments> params() {
        return Stream.of(
                Arguments.of(
                        new String[] { "0,word_net,description of a word", "1,word synonym,desc" },
                        new String[] { "1,0" },
                        new String[] { "word", "synonym", "word_net" }, "word_net"
                ),
                Arguments.of(
                        new String[] {
                                "0,word_net,description of a word",
                                "1,word_1,desc",
                                "2,word_2,desc",
                                "3,word_3,desc",
                                "4,word_4,desc",
                                "5,word_5,desc",
                                "6,word_6,desc",
                                "7,word_7,desc",
                                "8,word_8,desc"
                        },
                        new String[] {
                                "0,1",
                                "1,2",
                                "2,3",
                                "4,3",
                                "3,5",
                                "6,5",
                                "7,6",
                                "8,7"
                        },
                        new String[] { "word_1", "word_net", "word_3", "word_8" }, "word_8"
                )
        );
    }
}