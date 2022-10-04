import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Map.Entry;

public class Task2 extends Utility {
    
	class qNode implements Comparable<qNode>{
		int node;
		float totalDist;
		int totalCost;
		String path;

		qNode(int node, float dist, int cost){
			this.node = node;
			this.totalDist = dist;
			this.totalCost = cost;
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
		qNode cur = new qNode(startNode, 0, 0);
		qNode child;
        PriorityQueue<qNode> pq = new PriorityQueue<qNode>();
		Set<Integer> expanded = new HashSet<Integer>();

		cur.path = Integer.toString(startNode);
		pq.add(cur); // add startNode to priority queue
		//int nodesExamined = 0;

		// continue until all nodes have been expanded
		while(pq.size() != 0){
			//nodesExamined++;
			// set current node to head of priority queue
			cur = pq.poll();

            // if current node is the goal state, end search
			if (cur.node == endNode) {
				System.out.println("\nShortest path: " + cur.path);
				System.out.println("Shortest distance: " + cur.totalDist); 
                System.out.println("Total energy cost: " + cur.totalCost);
				// System.out.println("Nodes Examined: " + nodesExamined + "\n");				
				return;
			}

			// if current node has not been expanded before
			if (!expanded.contains(cur.node)){
				// mark current node as expanded
				expanded.add(cur.node);
            
				// Examine each neighbour of the expanded node
				for (Entry<Integer,adjNode> mapNode: adjList.get(cur.node).entrySet()) {
					// if new cost exceed budget, ignore and continue to next node
                    if (mapNode.getValue().cost + cur.totalCost > energyBudget) {
						continue;
					}

                    if (mapNode.getValue().dist == -1 || mapNode.getValue().cost == -1) {
                        System.out.println("-1");
                    }

					child = new qNode(mapNode.getKey(), mapNode.getValue().dist + cur.totalDist, mapNode.getValue().cost + cur.totalCost);
					child.path = cur.path + "->" + child.node; // set child's path
					
					if (!pq.contains(child)) {
						pq.add(child);
					} else { // node has been queued before
						for(qNode node: pq){ // find for node in priority queue
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