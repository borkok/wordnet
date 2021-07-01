

/**
 * Throw an IllegalArgumentException in the following situations:
 * - Any argument to the constructor or an instance method is null
 * - The input to the constructor does not correspond to a rooted DAG.
 * - Any of the noun arguments in distance() or sap() is not a WordNet noun.
 *
 * You may assume that the input files are in the specified format.
 */
public class WordNet {

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        WordNetValidator.validateNotNull(synsets, hypernyms);
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return null;
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        WordNetValidator.validateNotNull(word);
        return false;
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        WordNetValidator.validateNotNull(nounA, nounB);
        return 0;
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        WordNetValidator.validateNotNull(nounA, nounB);
        return nounA;
    }

    // do unit testing of this class
    public static void main(String[] args) {}
}
