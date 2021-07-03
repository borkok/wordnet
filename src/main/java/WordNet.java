import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Throw an IllegalArgumentException in the following situations:
 * - Any argument to the constructor or an instance method is null
 * - The input to the constructor does not correspond to a rooted DAG.
 * - Any of the noun arguments in distance() or sap() is not a WordNet noun.
 * <p>
 * You may assume that the input files are in the specified format.
 */
public class WordNet {

    //one noun can be part of a few synsets, each with distinct index
    private HashMap<String, List<Integer>> synsetIndicesByNouns;
    private String[] synsetArray;
    private SAP sap;

    //usuń tę metodę - tylko do testów
    static WordNet fromCsv(String[] synsets, String[] hypernyms) {
        return new WordNet(synsets, hypernyms);
    }

    private WordNet(String[] synsets, String[] hypernyms) {
        initialize(synsets, hypernyms);
    }
    //koniec usuń

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        WordNetValidator.notNull(synsets, hypernyms);
        initialize(new In(synsets).readAllLines(), new In(hypernyms).readAllLines());
    }

    private void initialize(String[] synsetsCsv, String[] hypernymsCsv) {
        initializeSynset(synsetsCsv);
        Digraph digraph = createDigraph(hypernymsCsv);
        WordNetValidator.isAcyclicRooted(digraph);
        sap = new SAP(digraph);
    }

    private void initializeSynset(String[] synsetsCsv) {
        int synsetCount = synsetsCsv.length;
        synsetArray = new String[synsetCount];
        synsetIndicesByNouns = new HashMap<>();
        for (int i = 0; i < synsetCount; i++) {
            String[] line = synsetsCsv[i].split(",");

            String synset = line[1];
            synsetArray[i] = synset;

            int vertex = Integer.parseInt(line[0]);
            String[] words = synset.split(" ");
            for (String word : words) {
                synsetIndicesByNouns.putIfAbsent(word, new LinkedList<>());
                synsetIndicesByNouns.get(word).add(vertex);
            }
        }
    }

    private Digraph createDigraph(String[] hypernymsCsv) {
        Digraph digraph = new Digraph(synsetArray.length);
        for (String line : hypernymsCsv) {
            String[] hypernyms = line.split(",");
            for (int i = 1; i < hypernyms.length; i++) {
                digraph.addEdge(Integer.parseInt(hypernyms[0]), Integer.parseInt(hypernyms[i]));
            }
        }
        return digraph;
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return synsetIndicesByNouns.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        WordNetValidator.notNull(word);
        return synsetIndicesByNouns.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) throw new IllegalArgumentException();
        List<Integer> indexA = findIndices(nounA);
        List<Integer> indexB = findIndices(nounB);
        return sap.length(indexA, indexB);
    }

    private List<Integer> findIndices(String nounA) {
        return synsetIndicesByNouns.getOrDefault(nounA, Collections.emptyList());
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) throw new IllegalArgumentException();
        List<Integer> indexA = findIndices(nounA);
        List<Integer> indexB = findIndices(nounB);
        int ancestorIndex = sap.ancestor(indexA, indexB);
        return synsetArray[ancestorIndex];
    }
}
