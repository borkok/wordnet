import java.util.Comparator;
import java.util.stream.Stream;

public class Outcast {
    private final WordNet wordnet;

    public Outcast(WordNet wordnet) {
        this.wordnet = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        NounDistance[] distanceSums = new NounDistance[nouns.length];

        for (int fromIndex = 0; fromIndex < nouns.length; fromIndex++) {
            String from = nouns[fromIndex];
            int distanceSum = 0;

            for (String to : nouns) {
                int distance = findDistance(from, to);
                distanceSum += distance;
            }

            distanceSums[fromIndex] = new NounDistance(from, distanceSum);
        }

        return Stream.of(distanceSums)
                     .max(Comparator.comparingInt(NounDistance::getDistance))
                     .map(NounDistance::getNoun)
                     .orElse(null);
    }

    private int findDistance(String from, String to) {
        return wordnet.distance(from, to);
    }

    private static class NounDistance {
        private final String noun;
        private final int distance;

        public NounDistance(String noun, int distance) {
            this.noun = noun;
            this.distance = distance;
        }

        public String getNoun() {
            return noun;
        }

        public int getDistance() {
            return distance;
        }
    }
}