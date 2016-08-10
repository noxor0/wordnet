package wordnet;

import java.util.LinkedList;

//TODO: What is the point of the definition? we don't even use it
/**
 * Vertex hold edges, IDs, and the word used to build a graph.
 * 
 * @author noxor
 * @version 1.0
 *
 */
public final class Vertex {
	/**
	 * the ID.
	 */
	public int myID;
	/**
	 * A list of egdes this vertex points to.
	 */
	public LinkedList<Vertex> myEdges;	
	/**
	 * If the vertex is visited in traversal.
	 */
	public boolean myVisited = false;
	/**
	 * Distance from the start of the traversal
	 */
	public int myDistance = 0;
	/**
	 * Word associated with the ID
	 */
	private String myWord;
	/**
	 * The definition
	 * (unused)
	 */
	private String myDef;
	/**
	 * Constructor for Vertex
	 * 
	 * @param theID the ID
	 * @param theWord the Word
	 */
	public Vertex(final int theID, final String theWord, final String theDef) {
		if (theID < 0 || theWord == null) {
			System.err.println("One of the vertex params are null!");
			throw new IllegalArgumentException();
		}
		myID = theID;
		myEdges = new LinkedList<Vertex>();
		myWord = theWord;
		myDef = theDef;
	}
	
	/**
	 * Gets the word...
	 * 
	 * @return the word associated with the ID
	 */
	public String getWord() {
		return myWord;
	}
	public String getDef() {
		return myDef;
	}
}
