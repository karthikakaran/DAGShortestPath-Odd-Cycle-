//===========================================================================================================================
//	Program : To find odd cycle for a non-bipartite graph
//===========================================================================================================================
//	@author: Karthika, Nevhetha, Kritika
// 	Date created: 2016/11/20
//===========================================================================================================================
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.TreeMap;

class Node {
	Vertex v;
	int lev;

	Node(Vertex ver, int n) {
		ver = v;
		lev = n;
	}
}

public class OddCycle {
	public static void oddCycle(Graph g) {
		if (g.isBipartite()) {
			System.out.println("No odd cycles - The graph is bipartite");
		} else {
			for (Vertex v : g) {
				v.seen = false;
				v.d = 0;
				v.p = null;
			}
			bfs(g);
		}
	}

	public static void bfs(Graph g) {
		bfs(g.getVertex(1), g);
	}

	public static void bfs(Vertex src, Graph g) {
		src.seen = true;
		src.d = 0;
		boolean isCycle = false;
		Vertex c = null;
		Queue<Vertex> q = new LinkedList<>();
		TreeMap<Integer, List<Vertex>> level = new TreeMap<>();
		int l = 1;
		List<Vertex> levelList = new ArrayList<>();
		levelList.add(src);
		src.level = l;
		level.put(l, levelList);
		q.add(src);
		while (!q.isEmpty()) {
			Vertex u = q.remove();
			l++;
			levelList = new ArrayList<>();
			for (Edge e : u.adj) {
				Vertex v = e.otherEnd(u);
				if (v.seen && (u == v || (u.p != null && u.p == v))) {
					isCycle = true;
					c = v;
				}
				if (!v.seen) {
					levelList.add(v);
					v.level = l;
					v.seen = true;
					v.d = u.d + 1;
					v.p = u;
					q.add(v);
				}
			}
			level.put(l, levelList);
		}
		Vertex x = null;
		Vertex y = null;
		if (isCycle) {
			for (Integer i : level.keySet()) { /* for every level **/
				for (Vertex v : level
						.get(i)) { /* for every vertex in that level */
					for (Edge e : v.adj) { /*
											 * check if there is an edge to the
											 * vertex of the same level
											 */
						Vertex u = e.otherEnd(v);
						if (u.level == i) {
							x = u;
							y = v;
							break;
						}
					}
				}
			}
		}
		/* to find least common ancestor */
		/* color all the ancestors of x as red(false) */
		Vertex t = x;
		while (t != src) {
			t.p.color = false;
			t = t.p;
		}
		/* color all ancestors of y as blue(true) */
		t = y;
		while (t != src) {
			t.p.color = true;
			t = t.p;
		}
		/* for each red node increment parent's count by one */
		for (Vertex v : g) {
			if (v.color == true) {
				if (v != src)
					v.p.count++;
			}
		}
		Vertex leastCommonAncestor = null;
		/* least common ancestor is any red node with count 0 */
		for (Vertex v : g) {
			if (v.color == true && v.count == 0) {
				leastCommonAncestor = v;
				break;
			}
		}
		/*find a path from x to least common ancestor*/
		g.bfsOddCycle(x);
		t=leastCommonAncestor;
		List<Vertex> cycle=new ArrayList<>();
		cycle.add(x);
		while(t!=x){
			t=t.p;
			cycle.add(t);
		}
		cycle.add(x);
		/*find a path from least common ancestor to p*/
		g.bfsOddCycle(leastCommonAncestor);
		t=y;
		cycle.add(y);
		while(t!=leastCommonAncestor){
			t=t.p;
			cycle.add(t);
		}
		for(Vertex v:cycle)
			v.seen=false;
		/*print the cycle*/
		for(Vertex v:cycle){
			if(v.seen==false){
				System.out.println(v);
				v.seen=true;
			}
		}
		System.out.println(cycle.get(0));
	}

	public static void main(String[] args) throws FileNotFoundException {
		Scanner in;
		int VERBOSE = 0;
		if (args.length > 0) {
			File inputFile = new File(args[0]);
			in = new Scanner(inputFile);
		} else {
			in = new Scanner(System.in);
		}
		if (args.length > 1) {
			VERBOSE = Integer.parseInt(args[1]);
		}

		Graph g = Graph.readGraph(in);
		oddCycle(g);

	}

}
