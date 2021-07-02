import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Outcast {
    private final WordNet wordnet;
    private final Map<NounPair, Integer> distancesByNounPairs = new HashMap<>();

    public Outcast(WordNet wordnet) {
        this.wordnet = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        Map<String, Integer> distanceSumsByNouns = new HashMap<>();
        for (int i = 0; i < nouns.length; i++) {
            String from = nouns[i];
            int distanceSum = 0;
            for (int j = 0; j < nouns.length; j++) {
                String to = nouns[j];
                int distance = findDistance(from, to);
                distanceSum += distance;
            }
            distanceSumsByNouns.put(from, distanceSum);
        }

        return distanceSumsByNouns.entrySet().stream()
                                  .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                                  .findFirst()
                                  .map(Map.Entry::getKey)
                                  .orElse(null);
    }

    private int findDistance(String from, String to) {
        NounPair pair = new NounPair(from, to);
        return Optional.ofNullable(distancesByNounPairs.get(pair))
                       .orElseGet(() -> calculateAndCacheDistance(pair));
    }

    private int calculateAndCacheDistance(NounPair pair) {
        int distance = wordnet.distance(pair.nounA, pair.nounB);
        distancesByNounPairs.put(pair, distance);
        return distance;
    }

    private static class NounPair {
        String nounA;
        String nounB;

        public NounPair(String nounA, String nounB) {
            this.nounA = nounA;
            this.nounB = nounB;
        }

        public boolean equals(Object other) {
            if (this == other) return true;
            if (other == null || getClass() != other.getClass()) return false;
            NounPair nounPair = (NounPair) other;
            return nounA.equals(nounPair.nounA) && nounB.equals(nounPair.nounB)
                    || nounA.equals(nounPair.nounB) && nounB.equals(nounPair.nounA);
        }

        public int hashCode() {
            return nounA.hashCode() + nounB.hashCode();
        }
    }
}