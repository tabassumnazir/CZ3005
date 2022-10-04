import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Map.Entry;

public class task1 extends Utility {

	class qNode implements Comparable<qNode>{
		int node;
		float totalDist;
		String path;

		qNode(int node, float dist){
			this.node = node;
			this.totalDist = dist;
			this.path = "";
		}

		@Override
		public int compareTo(qNode node) {
			return Float.compare(this.totalDist, node.totalDist);
		};

		@Override
		public boolean equals(Object o) {
			if (o == this) return true;
			if (!(o instanceof qNode)) return false;
			qNode q = (qNode) o;
			return Integer.compare(this.node, q.node) == 0;
		}
	}

	public void run(int startNode, int endNode)  {
		qNode cur = new qNode(startNode, 0);
		qNode child;
        PriorityQueue<qNode> pq = new PriorityQueue<qNode>();
		Set<Integer> expanded = new HashSet<Integer>();

		// add startNode to the queue
		cur.path = Integer.toString(startNode);
		pq.add(cur);

		// continue until all nodes have been expanded
		while(pq.size() != 0){

			// set current node at head of priority queue
			cur = pq.poll();

			// if current node is goal state, end search
			if (cur.node == endNode) {
				System.out.println("\nShortest Path: " + cur.path);
				System.out.println("Shortest Distance: " + cur.totalDist + "\n"); 
				return;
			}

			// if current node has not been expanded before
			if (!expanded.contains(cur.node)){

				// mark current node as expanded
				expanded.add(cur.node);
            
				// examine each neighbour of the expanded node
				for (Entry<Integer,adjNode> mapNode: adjList.get(cur.node).entrySet()){
					child = new qNode(mapNode.getKey(), mapNode.getValue().dist + cur.totalDist);
					child.path = cur.path + "->" + child.node; // set child's path
					
					if (!pq.contains(child)) {
						pq.add(child);
					} else { // node has been queued before
						for(qNode node: pq){ // find for the child node
							if (node.equals(child) && node.totalDist > child.totalDist){ // if path in queue is longer than this new path, replace entry
								pq.remove(node);
								pq.add(child);
								break;
							}
						}
					}
				}
			}
			
		}
		System.out.println("No path found");
	}
}
