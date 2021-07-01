import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

import java.util.HashMap;

/**
 * Throw an IllegalArgumentException in the following situations:
 * - Any argument to the constructor or an instance method is null
 * - The input to the constructor does not correspond to a rooted DAG.
 * - Any of the noun arguments in distance() or sap() is not a WordNet noun.
 * <p>
 * You may assume that the input files are in the specified format.
 */
public class WordNet {

    private HashMap<String, Integer> wordSet;
    private Digraph digraph;

    //usuń tę metodę - tylko do testów
    static WordNet fromCsv(String[] synsets, String[] hypernyms) {
        return new WordNet(synsets, hypernyms);
    }

    private WordNet(String[] synsets, String[] hypernyms) {
        parseCsv(synsets, hypernyms);
    }

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        WordNetValidator.validateNotNull(synsets, hypernyms);
        parseCsv(new In(synsets).readAllLines(), new In(hypernyms).readAllLines());
    }

    private void parseCsv(String[] synsetsCsv, String[] hypernymsCsv) {
        int wordCount = synsetsCsv.length;

        wordSet = new HashMap<>();
        for (int i = 0; i < wordCount; i++) {
            String[] synset = synsetsCsv[i].split(",");
            int vertex = Integer.parseInt(synset[0]);
            String[] words = synset[1].split(" ");
            for (String word : words) {
                wordSet.put(word, vertex);
            }
        }
        digraph = new Digraph(wordCount);
        for (String line : hypernymsCsv) {
            String[] hypernyms = line.split(",");
            for (int i = 1; i < hypernyms.length - 1; i++) {
                digraph.addEdge(Integer.parseInt(hypernyms[0]), Integer.parseInt(hypernyms[i]));
            }
        }
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return wordSet.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        WordNetValidator.validateNotNull(word);
        return wordSet.containsKey(word);
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
    public static void main(String[] args) {
    }
}
