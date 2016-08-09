package wordnet;

import java.util.LinkedList;
import java.util.Stack;

/**
 * SHORTEST ANCESTOR PATH
 * 
 * @author noxor
 * @version 1.0
 */
public final class SAP {
	/**
	 * the graph used to traverse.
	 */
	private Diagraph myGraph;
	/**
	 * The path that was traversed last.
	 * this is a bit messy.
	 */
	private LinkedList<Integer> myPath;
	/**
	 * @param theArgs command line arguments  
	 * @throws Exception if the nouns are not valid
	 */
	public static void main(final String[] theArgs) throws Exception {
		Diagraph diagraph = new Diagraph("./synsets.txt", "./hypernyms.txt");
		SAP sap = new SAP(diagraph);
		int v = Integer.parseInt(theArgs[0]);
		int w = Integer.parseInt(theArgs[1]);
		if (v > diagraph.mySynsetEntries - 1 || w > diagraph.mySynsetEntries - 1
				|| v < 0 || w < 0) {
			System.err.println("Out of bounds!");
			System.out.println("SAP: -1 Ancestor: -1");
			throw new IndexOutOfBoundsException();
		} if (v == w) {
			System.out.println("Sap: 0 Ancestor: 0");
		} else {
			System.out.println("SAP: " + sap.length(v, w) +
					" Ancestor: " + sap.ancestorldx(v, w));			
		}
		
		
	}
	/**
	 * constructor for SAP
	 * 
	 * @param G is the graph to traverse
	 * @throws if the graph is null
	 */
	public SAP(final Diagraph G) {
		if (G == null) {
			System.err.println("Graph is null");
			throw new IllegalArgumentException();
		} else {
			myPath = new LinkedList<Integer>();
			myGraph = G;			
		}
	}
	/**
	 * Finds the path to the root.
	 * 
	 * @param theV the vertex to start at
	 * @return a stack of IDs to the root, in order
	 */
	public Stack<Integer> findPath(final Vertex theV) {
		Stack<Integer> path = new Stack<Integer>();
		findAncestors(path, theV);
		resetVisited(theV);
		return path;
	}
	/**
	 * Resets the visited flag in the Vertex.
	 * 
	 * @param theV
	 */
	private void resetVisited(final Vertex theV) {
		for(Vertex v: theV.myEdges) {
			if (v != null && v.myVisited) {
				resetVisited(v);
				v.myVisited = false;
			}
		}
	}
	/**
	 * Finds the common ancestor of the two vertices.
	 * Recursive function that does DFS.
	 * 
	 * @param path the current path of the DFS traversal
	 * @param theV the current vertex of the DFS traversal
	 */
	public void findAncestors(Stack<Integer> path, final Vertex theV) {		
		//recursive
		path.push(theV.myID);
		for(Vertex v: theV.myEdges) {
			if (v != null && !v.myVisited) {
				findAncestors(path, v);
				v.myVisited = true;
			}
		}
		
	}
	/**
	 * length between v and w.
	 * -1 if no such path.
	 * 
	 * @param v first vertex ID
	 * @param w second Vertex ID
	 * @return the length from v to the common ancestor then back to w
	 */
	public int length(final int v,final int w) {
		myPath.clear();
		ancestorldx(v, w);
		int retVal = -1;
		if (myPath.size() > -1) {
			retVal = myPath.size();
		}
		return retVal;
	}
	/**
	 * a common ancestor of v and w that participates
	 * returns -1 if not found
	 * 
	 * @param v the first ID
	 * @param w the second ID
	 * @return the ID of the common ancestor
	 */
	public int ancestorldx(final int v, final int w) {
		myPath.clear();
		int retVal = -1;
		Stack<Integer> path1 = findPath(myGraph.myVerticies.get(v));
		Stack<Integer> path2 = findPath(myGraph.myVerticies.get(w));
		for (Integer vertID: path1) {
			if(path2.contains(vertID)) {
				retVal = vertID;
				break;
			} else {
				myPath.push(vertID);
			}
		}
		boolean addRest = false;
		while (!path2.isEmpty()) {
			if (path2.peek() == retVal || addRest) {
				addRest = true;
				myPath.push(path2.peek());
			}
			path2.pop();
		}
		return retVal;
	}
	/**
	 * takes in the ancestor index returned by the
	 * earlier method and returns the string of it
	 * 
	 * Gets the word associated with the common ancestor
	 */
	public String ancestor(final int ancldx) {
		return myGraph.myVerticies.get(ancldx).getWord();
	}
	
}