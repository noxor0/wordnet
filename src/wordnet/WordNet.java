package wordnet;

import java.util.Map;

/**
 * The word net class that offers ancestor and distance functionality.
 * 
 * @author noxor
 * @version 1.0
 */
public final class WordNet {
	/**
	 * Hash map that contains myNouns 
	 */
	private Map<String, Vertex> myNouns;
	/**
	 * The shortest ancestor object that contains graph traversal functions
	 */
	private SAP mySap;

	/**
	 * Main method, runs the stuff.
	 * 
	 * @param theArgs command line args
	 * @throws Exception if any noun is not contained in diagraph
	 */
	public static void main(final String[] theArgs) throws Exception {
		WordNet net = new WordNet("./synsets.txt", "./hypernyms.txt");
		String nounA = theArgs[0];
		String nounB = theArgs[1]; 
		int v, w;
		if (net.isNoun(nounA)) {
			v = net.myNouns.get(nounA).myID;
		} else {
			v = -1;
			System.err.println(nounB + " is not in the diagraph!");
			throw new java.lang.IllegalArgumentException();
		}
		if (net.isNoun(nounB)) {
			w = net.myNouns.get(nounB).myID;
		} else {
			w = -1;
			System.err.println(nounB + " is not in the diagraph!");
			throw new java.lang.IllegalArgumentException();
		}
		//successful!
		if (net.isNoun(nounA) && net.isNoun(nounB)) {
			System.out.println(
					"Ancestor: " + net.mySap.ancestor(net.mySap.ancestorldx(v, w)));
			System.out.println("Length: " + net.distance(nounA, nounB));			
		}
	}
	  
	/**
	 * constructor takes the name of the two input files and create a Diagraph 
	 * 
	 * @param theSynsets the synsets file
	 * @param theHypernyms the hypernyms file
	 */
	public WordNet(final String theSynsets, final String theHypernyms) {
		if (theSynsets == null || theHypernyms == null) {
			System.err.println("File names are null");
			throw new java.lang.IllegalArgumentException();
		}
		Diagraph graph = new Diagraph(theSynsets, theHypernyms);
		mySap = new SAP(graph);
		myNouns = graph.myNouns;
	}
	/**
	 * returns all WordNet nouns
	 * @return an iterable interface of the nouns.
	 */
	public Iterable<String> nouns(){
		return myNouns.keySet();
	}
	
	/**
	 * is the word a noun?	
	 * 	
	 * @param noun the noun to check
	 * @return if it is contained in map
	 */
	public boolean isNoun(final String noun){
		return myNouns.containsKey(noun);
	}
  
	/**
	 * extra credit: distance between string a and b
	 * adds the distance to the common ancestor and back down to b
	 * 
	 * @param nounA a noun
	 * @param nounB another noun
	 * @return the distance between the noun
	 */
	public int distance(final String nounA, final String nounB) {
		int retVal = -1;
		if (nounA.equals(nounB)) {
			retVal = 0;
		} else if (isNoun(nounA) && isNoun(nounB)) {
			int v = myNouns.get(nounA).myID;
			int w = myNouns.get(nounB).myID;
			retVal = mySap.length(v, w);
		}
		return retVal;
	}
}
