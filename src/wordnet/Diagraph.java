package wordnet;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * An adjacency list graph that contains the synsets.
 * 
 * @author noxor
 * @version 1.0
 */
public final class Diagraph {
	/**
	 * Contains map of IDs and corresponding vertices.  
	 */
	public Map<Integer, Vertex> myVerticies;
	/**
	 * Contains map of Nouns and corresponding vertices.
	 */
	public Map<String, Vertex> myNouns;
	public int mySynsetEntries = 0;
 
	/**
	 * Constructor for Diagraph, and creates the graph.
	 * 
	 * @param theSynsets synsets file 
	 * @param theHypernyms hypernyms file
	 */
	public Diagraph(String theSynsets, String theHypernyms) {
		if (theSynsets == null || theHypernyms == null) {
			System.err.println("The file paths are null!");
		} else {
			myVerticies = new HashMap<Integer, Vertex>();
			myNouns = new HashMap<String, Vertex>();
			parseSyns(theSynsets);
			parseHyper(theHypernyms);			
		}
	}

	/**
	 * Helper method that builds the hash maps.
	 * 
	 * @param theVertex adds vertex's to the graph.
	 */
	private void addVertex(final Vertex theVertex) {
		myVerticies.put(theVertex.myID, theVertex);
		myNouns.put(theVertex.getWord(), theVertex);
	}
	
	/**
	 * Reads the synset file and adds the synset data to vertices
	 * 
	 * @param theSynsets synsets file
	 */
	private void parseSyns(final String theSynsets)  {
		try {
			BufferedReader synIn = new BufferedReader(
					new InputStreamReader(new FileInputStream(theSynsets)));
			String input;
			while ((input = synIn.readLine()) != null) {
				mySynsetEntries++;
				String[] lineArr = input.split(",");
				Vertex newVertex = new Vertex(
						Integer.parseInt(lineArr[0]), lineArr[1], getDef(lineArr));
				addVertex(newVertex);
			}
			synIn.close();
		} catch (FileNotFoundException e) {
			System.err.println("Synsets not found");
		} catch (IOException e) {
			System.err.println("IO Exception");
		}
	}
	private String getDef(final String[] theArr) {
		StringBuilder sb = new StringBuilder();
		if(theArr[2] != null) {
			for (int i = 2; i < theArr.length; i++) {
				sb.append(theArr[i]);
			}			
		} else {
			sb.append("No def");
		}
		return sb.toString();
	}
	/**
	 * Reads the hypernyms file and 
	 * adds the edges to the corresponding vertices
	 * 
	 * @param theHypernyms the hypernyms file
	 */
	private void parseHyper(final String theHypernyms) {
		try {
			BufferedReader hyperIn = new BufferedReader(
					new InputStreamReader(new FileInputStream(theHypernyms)));
			String input;
			while ((input = hyperIn.readLine()) != null) {
				String[] lineArr = input.split(",");
				int sizeArr = lineArr.length;
				if(sizeArr > 1) {
					for (int i = 1; i < sizeArr; i++) {
						Vertex source = myVerticies.get(Integer.parseInt(lineArr[0]));
						Vertex dest = myVerticies.get(Integer.parseInt(lineArr[i]));
						source.myEdges.add(dest);
					}					
				}
			}
			hyperIn.close();
		} catch (FileNotFoundException e) {
			System.err.println("Synsets not found");
		} catch (IOException e) {
			System.err.println("IO Exception");
		}
	}
}
