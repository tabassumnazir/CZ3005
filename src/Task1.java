import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Map.Entry;

public class Task1 extends Utility {

	class qNode implements Comparable<qNode>{
		int node;
		float distance;
		String path;

		qNode(int node, float dist){
			this.node = node;
			this.distance = dist;
			this.path = "";
		}

		@Override
		public int compareTo(qNode node) {
			return Float.compare(this.distance, node.distance);
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
		qNode current = new qNode(startNode, 0);
		qNode child;
        PriorityQueue<qNode> pq = new PriorityQueue<qNode>();
		Set<Integer> expanded = new HashSet<Integer>();

		current.path = Integer.toString(startNode);
		pq.add(current);

		while(pq.size() != 0){

			cur = pq.poll();

			if (current.node == endNode) {
				System.out.println("\nShortest Path: " + current.path);
				System.out.println("Shortest Distance: " + current.distance + "\n"); 
				return;
			}

			if (!expanded.contains(current.node)){

				expanded.add(current.node);
            
				for (Entry<Integer,adjNode> mapNode: adjList.get(current.node).entrySet()){
					child = new qNode(mapNode.getKey(), mapNode.getValue().dist + current.distance);
					child.path = current.path + "->" + child.node; 
					
					if (!pq.contains(child)) {
						pq.add(child);
					} else { 
						for(qNode node: pq){ 
							if (node.equals(child) && node.distance > child.distance){ 
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
