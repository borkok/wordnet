import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

import java.util.HashMap;
import java.util.Optional;

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
    private String[] synsetArray;
    private SAP sap;

    //usuń tę metodę - tylko do testów
    static WordNet fromCsv(String[] synsets, String[] hypernyms) {
        return new WordNet(synsets, hypernyms);
    }

    private WordNet(String[] synsets, String[] hypernyms) {
        initialize(synsets, hypernyms);
    }

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        WordNetValidator.validateNotNull(synsets, hypernyms);
        initialize(new In(synsets).readAllLines(), new In(hypernyms).readAllLines());
    }

    private void initialize(String[] synsetsCsv, String[] hypernymsCsv) {
        initializeSynset(synsetsCsv);
        initializeSAP(hypernymsCsv);
    }

    private void initializeSynset(String[] synsetsCsv) {
        int synsetCount = synsetsCsv.length;
        synsetArray = new String[synsetCount];
        wordSet = new HashMap<>();
        for (int i = 0; i < synsetCount; i++) {
            String[] line = synsetsCsv[i].split(",");

            String synset = line[1];
            synsetArray[i] = synset;

            int vertex = Integer.parseInt(line[0]);
            String[] words = synset.split(" ");
            for (String word : words) {
                wordSet.put(word, vertex);
            }
        }
    }

    private void initializeSAP(String[] hypernymsCsv) {
        Digraph digraph = new Digraph(synsetArray.length);
        for (String line : hypernymsCsv) {
            String[] hypernyms = line.split(",");
            for (int i = 1; i < hypernyms.length; i++) {
                digraph.addEdge(Integer.parseInt(hypernyms[0]), Integer.parseInt(hypernyms[i]));
            }
        }
        sap = new SAP(digraph);
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
        int indexA = findIndex(nounA).orElseThrow(IllegalArgumentException::new);
        int indexB = findIndex(nounB).orElseThrow(IllegalArgumentException::new);
        return sap.length(indexA, indexB);
    }

    private Optional<Integer> findIndex(String nounA) {
        return Optional.ofNullable(wordSet.get(nounA));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        WordNetValidator.validateNotNull(nounA, nounB);
        int indexA = findIndex(nounA).orElseThrow(IllegalArgumentException::new);
        int indexB = findIndex(nounB).orElseThrow(IllegalArgumentException::new);
        int ancestorIndex = sap.ancestor(indexA, indexB);
        return synsetArray[ancestorIndex];

    }

    // do unit testing of this class
    public static void main(String[] args) {
    }
}
