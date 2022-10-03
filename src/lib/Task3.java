import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Map.Entry;

public class Task3 extends Utility{
    // for implementing the priority queue: automaticallly sorts the queue by using .compareTo
	class qNode implements Comparable<qNode>{
		int node;
		float totalDist;
		int totalCost;
        double shortestDist;
		String path;

		qNode(int node, float distance, int cost, double shortestDist){
			this.node = node;
			this.totalDist = distance;
			this.totalCost = cost;
            this.shortestDist = shortestDist;
			this.path = "";
		}

		@Override
		public int compareTo(qNode temp) {
			return Double.compare(this.totalDist+this.shortestDist, temp.totalDist+temp.shortestDist);
		};

		@Override
		public boolean equals(Object temp) {
			if (temp == this) return true;
			if (!(temp instanceof qNode)) return false;
			qNode q = (qNode) temp;
			return Integer.compare(this.node, q.node) == 0;
		}
	}
    
	public void run(int startingNode, int endingNode)  {
		qNode currentNode = new qNode(startingNode, 0, 0, getShortestDist(startingNode, endingNode));
		// add startNode to the queue
		currentNode.path = Integer.toString(startingNode);
        PriorityQueue<qNode> priorityQueue = new PriorityQueue<qNode>();
		priorityQueue.add(currentNode);
        int nodeVisited = 0;
		// continue until all nodes have been expanded
		Set<Integer> visited = new HashSet<Integer>();
		qNode childNode;
		while(priorityQueue.size() != 0){
			// set current node to top of the priority queue
			nodeVisited++;
			currentNode = priorityQueue.poll();

			// node chosen to expand is goal state, search is complete
			if (currentNode.node == endingNode) {
				System.out.println("\nShortest path: " + currentNode.path);
				System.out.println("Shortest distance: " + currentNode.totalDist); 
                System.out.println("Total energy cost: " + currentNode.totalCost);
				System.out.println("Nodes examined: " + nodeVisited  + "\n");
				return;
			}

			// current node has not previously been expanded
			else if (!visited.contains(currentNode.node)){

				// mark current node as expanded
				visited.add(currentNode.node);
            
				// Examine each neighbour of the expanded node
				for (Entry<Integer,adjNode> mapNode: adjList.get(currentNode.node).entrySet()){
					if (mapNode.getValue().cost + currentNode.totalCost > energyBudget) { // budget exceeded
						continue;
					}
                    if (mapNode.getValue().dist == -1 || mapNode.getValue().cost == -1) {
                        System.out.println("-1");
                    }
					childNode = new qNode(
                                        mapNode.getKey(), 
                                        mapNode.getValue().dist + currentNode.totalDist, 
										mapNode.getValue().cost + currentNode.totalCost, 
                                        getShortestDist(mapNode.getKey(), endingNode));

					childNode.path = currentNode.path + "->" + childNode.node;
					
					if (!priorityQueue.contains(childNode)) { // since queueNode.equals only compares .node, will match regardless of dist
						priorityQueue.add(childNode);
					} else { // node has previously been queued
						for(qNode node: priorityQueue){
							if (node.equals(childNode) && node.totalDist+node.shortestDist > childNode.totalDist+childNode.shortestDist){ // if path in queue is longer than this new path, replace entry
								priorityQueue.remove(node); // will remove old node with same node
								priorityQueue.add(childNode); // add the new node into the queue
								break;
							}
						}
					}
				}
			}
			
		}
		System.out.println("No path found");
	}
	public double getShortestDist(int startingNode, int endingNode){
        int height = Math.abs(coordMap.get(endingNode).get(1) - coordMap.get(startingNode).get(1)); // | y2 - y1 |
        int base = Math.abs(coordMap.get(endingNode).get(0) - coordMap.get(startingNode).get(0)); // | x2 - x1 |
        double hypotenuse = Math.sqrt(Math.pow((double)height, 2) + Math.pow((double)base, 2)); // sqrt(a^2 + b^2)
        return hypotenuse;
    }
}
