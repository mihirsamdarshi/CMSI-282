package huffman;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Huffman instances provide reusable Huffman Encoding Maps for
 * compressing and decompressing text corpi with comparable
 * distributions of characters.
 */
public class Huffman {
    
    // -----------------------------------------------
    // Construction
    // -----------------------------------------------

    private HuffNode trieRoot;
    private Map<Character, String> encodingMap;
    
    /**
     * Creates the Huffman Trie and Encoding Map using the character
     * distributions in the given text corpus
     * @param corpus A String representing a message / document corpus
     *        with distributions over characters that are implicitly used
     *        throughout the methods that follow. Note: this corpus ONLY
     *        establishes the Encoding Map; later compressed corpi may
     *        differ.
     */
    Huffman (String corpus) {
        compress(corpus);
    }
    
    
    // -----------------------------------------------
    // Compression
    // -----------------------------------------------
    
    /**
     * Compresses the given String message / text corpus into its Huffman coded
     * bitstring, as represented by an array of bytes. Uses the encodingMap
     * field generated during construction for this purpose.
     * @param message String representing the corpus to compress.
     * @return {@code byte[]} representing the compressed corpus with the
     *         Huffman coded bytecode. Formatted as 3 components: (1) the
     *         first byte contains the number of characters in the message,
     *         (2) the bitstring containing the message itself, (3) possible
     *         0-padding on the final byte.
     */
    public byte[] compress (String message) {
        char[] corpusArray = message.toCharArray();
        Map<Character, Integer> characterMap = new HashMap<>();

        for (int i = 0; i < corpusArray.length; i++) {
            if (characterMap.containsKey(corpusArray[i])) {
                characterMap.put(corpusArray[i], characterMap.get(corpusArray[i]) + 1);
            } else {
                characterMap.put(corpusArray[i], 1);
            }
        }
        
//      for each character to encode:
//      create leaf node and add to priority queue
        PriorityQueue<HuffNode> queue = new PriorityQueue();
        for (Map.Entry<Character, Integer> entry : characterMap.entrySet()) {
            queue.add(new HuffNode(entry.getKey(), entry.getValue()));
        }
        
//      while more than 1 node in queue:
//      remove 2 smallest probability nodes from queue
//      create new parent node of these 2 removed with sum of their probabilities
//      enqueue new parent
//  remaining node is the root
        while (queue.size() > 1) {
            HuffNode left = queue.poll();
            HuffNode right = queue.poll();
            
            HuffNode parent = new HuffNode(left, right, left.count + right.count);
            queue.add(parent);
        }
        
        throw new UnsupportedOperationException();
    }
    
    
    // -----------------------------------------------
    // Decompression
    // -----------------------------------------------
    
    /**
     * Decompresses the given compressed array of bytes into their original,
     * String representation. Uses the trieRoot field (the Huffman Trie) that
     * generated the compressed message during decoding.
     * @param compressedMsg {@code byte[]} representing the compressed corpus with the
     *        Huffman coded bytecode. Formatted as 3 components: (1) the
     *        first byte contains the number of characters in the message,
     *        (2) the bitstring containing the message itself, (3) possible
     *        0-padding on the final byte.
     * @return Decompressed String representation of the compressed bytecode message.
     */
    public String decompress (byte[] compressedMsg) {
        throw new UnsupportedOperationException();
    }
    
    
    // -----------------------------------------------
    // Huffman Trie
    // -----------------------------------------------
    
    /**
     * Huffman Trie Node class used in construction of the Huffman Trie.
     * Each node is a binary (having at most a left and right child), contains
     * a character field that it represents (in the case of a leaf, otherwise
     * the null character \0), and a count field that holds the number of times
     * the node's character (or those in its subtrees) appear in the corpus.
     */
    private static class HuffNode implements Comparable<HuffNode> {
        
        HuffNode left, right;
        char character;
        int count;
        
        HuffNode (char character, int count) {
            this.count = count;
            this.character = character;
        }
        
        public HuffNode(HuffNode left, HuffNode right, int count) {
            this.left = left;
            this.right = right;
            this.count = count;
        }
        
        public boolean isLeaf () {
            return left == null && right == null;
        }
        
        public int compareTo (HuffNode other) {
            return this.count - other.count;
        }
        
    }

}
