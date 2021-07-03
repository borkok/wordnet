import java.util.Comparator;
import java.util.stream.Stream;

public class Outcast {
    private final WordNet wordnet;
    private int[][] distancesByNounPairsCache;

    public Outcast(WordNet wordnet) {
        this.wordnet = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        NounDistance[] distanceSums = new NounDistance[nouns.length];
        initializeCache(nouns.length);

        for (int fromIndex = 0; fromIndex < nouns.length; fromIndex++) {
            String from = nouns[fromIndex];
            int distanceSum = 0;

            for (int toIndex = 0; toIndex < nouns.length; toIndex++) {
                String to = nouns[toIndex];
                int distance = findDistance(from, fromIndex, to, toIndex);
                distanceSum += distance;
            }

            distanceSums[fromIndex] = new NounDistance(from, distanceSum);
        }

        return Stream.of(distanceSums)
                     .max(Comparator.comparingInt(NounDistance::getDistance))
                     .map(NounDistance::getNoun)
                     .orElse(null);
    }

    private void initializeCache(int length) {
        distancesByNounPairsCache = new int[length][length];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                distancesByNounPairsCache[i][j] = i == j ? 0 : -1;
            }
        }
    }

    private int findDistance(String from, int fromIndex, String to, int toIndex) {
        int distance = distancesByNounPairsCache[fromIndex][toIndex];
        if (distance >= 0) return distance;

        return calculateAndCacheDistance(from, fromIndex, to, toIndex);
    }

    private int calculateAndCacheDistance(String from, int fromIndex, String to, int toIndex) {
        int distance = wordnet.distance(from, to);
        distancesByNounPairsCache[fromIndex][toIndex] = distance;
        distancesByNounPairsCache[toIndex][fromIndex] = distance;
        return distance;
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