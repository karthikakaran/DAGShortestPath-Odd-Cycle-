//=============================================================================================================================================//
//  DAGShortestPath: Class that finds the shortest path from the given source to every other node in a DAG ***    	       //
//=============================================================================================================================================//
/*
 *  @dateCreated:		-November-19-2016
 *  @dateLastModified:		-November-20-2016
 *  @author: 			-Karthika, Nevhetha, Kritika
//=============================================================================================================================================//
*/
import java.util.*;

public class DAGShortestPath {

	/*TO FIND SHORTEST PATH
	 *@param : g : Graph : Graph for which the shortest path distances are to be computed
	 *@param : v : Vertex : Source vertex from which the shortest path distance is to be found
	 */
	public static void DAGShortestPaths(Graph g, Vertex v) {
	/*topSortOrder_LinkedList<Vertex> : contains the topological ordering on the vertices of the graph
	 *				      from the given source vertex
	 *it_Iterator<Vertex>		    : Iterator on Vertices in the Topological order
	*/	
		List<Vertex> topSortOrder = new LinkedList<Vertex>();
		/*find the topological order*/
		topSortOrder =  dfs(g, v, topSortOrder);

		/*Initialization*/
		for(Vertex vertex : g) {
    		vertex.d = Integer.MAX_VALUE;
    		vertex.p = null;
    		}
		
		/*Initial value for the given source vertex*/
		v.d = 0;

		/*relaxing vertices in topological order*/
		Iterator<Vertex> it = topSortOrder.iterator();
		while (it.hasNext()) {
			Vertex u = it.next();
			for (Edge e : u.adj) {
				Vertex ver = e.otherEnd(u);
				if (ver.d > u.d + e.weight) {
					ver.d = u.d + e.weight;
					ver.p = u;
				}
			}
		}
	}

	/**
	 * Method to find the topological sort with decreasing finishing time
	 * @param g : Graph : input 
	 * @param v : Vertex : source vertex
	 * @param topSortOrder : List<Vertex> : list object to store the topologically sorted vertices
	 * @return : topSortOrder : List<Vertex> : topological sorted order of vertices
	 */
	public static List<Vertex> dfs(Graph g, Vertex s, List<Vertex> topSortOrder) {
		for (Vertex v : g) {
			v.seen = false;
			v.p = null;
		}
		for (Vertex v : g) {
			if (!v.seen) {
				topSortOrder = dfsVisit(v, topSortOrder);
				topSortOrder.add(0, v);
			} 
		}
		return topSortOrder;
	}
	/**
	 * Method to find the topological sort with decreasing finishing time, helper function
	 * @param v : Vertex : source vertex
	 * @param topSortOrder : List<Vertex> : list object to store the topologically sorted vertices
	 * @return : topSortOrder : List<Vertex> : topological sorted order of vertices
	 */
	public static List<Vertex> dfsVisit(Vertex v, List<Vertex> topSortOrder) {
		v.seen = true;
		for (Edge e : v.adj) {
			Vertex vert = e.otherEnd(v);
			if (!vert.seen) {
				vert.p = v;
				topSortOrder = dfsVisit(vert, topSortOrder);
				topSortOrder.add(0, vert);
			} /*else if (vert.seen && (vert == v.p || vert == v)) {
				System.out.println(vert.name);
			}*/
		}
		return topSortOrder;
	}
}
