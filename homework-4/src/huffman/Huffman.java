package huffman;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Huffman instances provide reusable Huffman Encoding Maps for
 * compressing and decompressing text corpi with comparable
 * distributions of characters.
 * 
 * @author <DiBiagio, Will>
 * @author <Samdarshi, Mihir>
 */
public class Huffman {

    // -----------------------------------------------
    // Construction
    // -----------------------------------------------

    private HuffNode trieRoot;
    private Map<Character, String> encodingMap;
    // The encoding map maps each character to a string representation of its bitstring, e.g., `{'C': "0", 'A': "10", 'T': "11"}`

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
    	HashMap<Character, Integer> freqMap = new HashMap<Character, Integer>();
    	for (char letter: corpus.toCharArray()) {
    		if(freqMap.containsKey(letter)) {
    			freqMap.put(letter, freqMap.get(letter) + 1);
    		} else {
    			freqMap.put(letter, 1);
    		}
    	}
    	
    	PriorityQueue<HuffNode> pqueue = new PriorityQueue<>();
    	for (Character key: freqMap.keySet()) {
    	    HuffNode node = new HuffNode(key, freqMap.get(key));    
    	    pqueue.add(node);
    	}
    	
    	while(pqueue.size() > 1) {
    		HuffNode leftNode = pqueue.poll();
    		HuffNode rightNode = pqueue.poll();
    		HuffNode parent = new HuffNode('\0', leftNode.count + rightNode.count);
    		parent.left = leftNode;
    		parent.right = rightNode;
    		pqueue.add(parent);
    	}
    	
    	trieRoot = pqueue.poll();
    	encodingMap = new HashMap<Character, String>();
    	fillEncodingMap(trieRoot, "");
    }

    /** Recursive void method to fill the encodingMap based off a root node.
     * @param HuffNode the root node of the Huffman Trie.
     * @param String the 'binary' path to each node. Start with empty string.
     */
    void fillEncodingMap(HuffNode node, String path) {
        if (node.isLeaf()) {
            encodingMap.put(node.character, path);
            return;
        }
        
        fillEncodingMap(node.left, path + "0");
        fillEncodingMap(node.right, path + "1");      

    }
    
    private void createEncodingMap(String bitString, HuffNode node, Map<Character, String> encodingMap) {

        if (node.isLeaf()) {
            createEncodingMap(bitString + "0", node.left, encodingMap);
            createEncodingMap(bitString + "1", node.right, encodingMap);
        } else {
            encodingMap.put(node.character, bitString);
        }
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
    	String binaryString = "";
    	
    	for (char letter: message.toCharArray()) {
    		binaryString += encodingMap.get(letter);
    	}

    	ByteArrayOutputStream output = new ByteArrayOutputStream();
    	output.write(message.length());
    	while (binaryString.length() >= 8) {
        	String substring = binaryString.substring(0, 8);
        	output.write((byte) Integer.parseInt(substring, 2));
        	binaryString = binaryString.substring(8);
    	}

       	output.write((byte) Integer.parseInt(binaryString, 2) << (8 - binaryString.length()));       	
    	return output.toByteArray();
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
    	String bitString = "";
    	for (byte b : compressedMsg){
    		bitString += String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
    	}
    	bitString = bitString.substring(8);
    	
	    StringBuilder output = new StringBuilder();
	    
	    HuffNode currNode = trieRoot;
	    for (Character bit : bitString.toCharArray()) {
	    	if (output.length() >= compressedMsg[0]) {
	    		return output.toString().toString();
	    	}
	    	if (currNode.isLeaf()) {
	    		output.append(currNode.character);
	    		currNode = trieRoot;
	    	}
	    	
	    	currNode = (bit == '0') ? currNode.left : currNode.right;
	    } 
    	return output.toString();

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

        public boolean isLeaf () {
            return left == null && right == null;
        }

        public int compareTo (HuffNode other) {
            return this.count - other.count;
        }

    }

}
