package wordnet;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

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
	public Diagraph myGraph;
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
			System.out.println("SAP: " + sap.length(v, w) 
			+ " Ancestor: " + sap.ancestorldx(v, w));			
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
			myGraph = G;			
		}
	}
	/**
	 * Resets the visited flag in the Vertex.
	 * 
	 * @param theV the vertex to start at
	 */
	private void resetVisited(final Vertex theV) {
		for(Vertex v: theV.myEdges) {
			if (v != null) {
				resetVisited(v);
				v.myVisited = false;
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
		int retVal = -1;
		int lcaID = ancestorldx(v, w);
		if (myGraph.myVerticies.get(lcaID) != null) {
			retVal = myGraph.myVerticies.get(lcaID).myDistance;
		}
		return retVal;
	}
	/**
	 * Does a BFS search through the map 
	 * 
	 * @param theV the start vertex
	 * @return all the visited nodes
	 */
	public ArrayList<Integer> BFS(final Vertex theV) {
		resetVisited(theV);
		ArrayList<Integer> pathRet = new ArrayList<Integer>();
		Queue<Vertex> q = new LinkedList<Vertex>();
		q.add(theV);
		pathRet.add(theV.myID);
		theV.myVisited = true;
		while(!q.isEmpty()) {
			Vertex ver = q.remove();
			for(Vertex x : ver.myEdges) {
				if (!x.myVisited) {
					x.myDistance = x.myDistance + ver.myDistance + 1;
					x.myVisited = true;
					q.add(x);
					pathRet.add(x.myID);					
				}
			}
		}
		return pathRet;
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
		int lcaID = -1;
		ArrayList<Integer> pathV = BFS(myGraph.myVerticies.get(v));
		ArrayList<Integer> pathW = BFS(myGraph.myVerticies.get(w));
		for (Integer vertID: pathV) {
			if(pathW.contains(vertID)) {
				lcaID = vertID;
				break;
			}
		}
		return lcaID;
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

///**
// * Finds the path to the the second value.
// * 
// * @param theV the vertex to start at
// * @return a stack of IDs to the root, in order
// */
//public Stack<Integer> findPath(final Vertex theV, final Vertex theX) {
//	Stack<Integer> path = new Stack<Integer>();
//	findAncestors(path, theV, theX);
//	resetVisited(theV);
//	return path;
//}
///**
// * Finds the common ancestor of the two vertices.
// * Recursive function that does DFS.
// * 
// * @param path the current path of the DFS traversal
// * @param theV the current vertex of the DFS traversal
// */
//public void findAncestors(Stack<Integer> path, 
//		final Vertex theV, final Vertex theX) {		
//	//recursive
//	path.push(theV.myID);
//	for(Vertex v: theV.myEdges) {
//		if (v != null && !v.myVisited) {
//			if (v.myID == theX.myID) {
//				System.out.println("found it?");
//			}
//			findAncestors(path, v, theX);
//			v.myVisited = true;
//		}
//	}
//	
//}
