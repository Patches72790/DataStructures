import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * This class is my own test implementation of a directed/undirected graph. This
 * Graph class also contains implementaions of DFS, BFS, Cycle Detection, and topological Sort!
 * 
 * @author Patrick Harvey
 */
public class Graph<T extends Comparable<T>> {

    // adjacency list of all nodes within the graph
    protected List<GraphNode<T>> nodes;
    private int size;

    // used for dfs and top sort
    private LinkedList<GraphNode<T>> topologicalSort;
    private int time;

    public Graph() {
        nodes = new ArrayList<>();
        size = 0;
        topologicalSort = new LinkedList<>();
    }

    /**
     * This enum contains the status types of the GraphNode for this graph.
     */
    protected static enum NodeStatus {
        UNVISITED, IN_PROGRESS, FINISHED
    };

    /**
     * This class contains the basic graph node for unweighted directed / undirected
     * graphs.
     * 
     */
    protected static class GraphNode<T extends Comparable<T>> implements Comparable<GraphNode<T>>{
        protected T data;
        protected List<GraphNode<T>> successors;
        protected NodeStatus status;
        protected int distance;
        protected GraphNode<T> parent;
        protected int discovery;
        protected int finished;

        /**
         * Constructor sets the data to given value
         * and initializes node to unvisited with an empty list of edge successors.
         */
        public GraphNode(T data) {
            this.data = data;
            this.status = NodeStatus.UNVISITED;
            this.successors = new ArrayList<>();
            distance = 0;
            parent = null;
            discovery = 0;
            finished = 0;
        }

        public String toString() {
            return data.toString();
        }

        public List<GraphNode<T>> getSuccessors() {
            return successors;
        }

        public NodeStatus getStatus() {
            return status;
        }

        public T getData() {
            return data;
        }

        public void setParent(GraphNode<T> parent) {
            this.parent = parent;
        }

        public void setStatus(NodeStatus status) {
            this.status = status;
        }

        public void setSuccessor(GraphNode<T> node) {
            successors.add(node);
        }

        @Override
        public int compareTo(GraphNode<T> other) {
            if (this.getData().compareTo(other.getData()) < 0 ) {
                return -1;
            } else if (this.getData().compareTo(other.getData()) > 0) {
                return 1;
            }
            return 0;
        }

        public void initializeNode() {
            // set to unvisited
            setStatus(NodeStatus.UNVISITED);

            // mark distance as unknown (ie infinity)
            distance = Integer.MAX_VALUE;
        }
    }

    /**
     * Adds a simple un-connected node to the graph
     * 
     * @param data - the data of the node to be added to the graph
     */
    public void addNode(T data) {
        GraphNode<T> newNode = new GraphNode<>(data);
        nodes.add(newNode);
    }

    /**
     * Main method for adding graph nodes as a node object.
     * 
     * @param node
     */
    public void addNode(GraphNode<T> node) {
        nodes.add(node);
        size++;
    }

    /**
     * Adds two nodes to te graph with node1 directed to node2.
     * 
     * @param node1
     * @param node2
     */
    public void addNode(T node1, T node2) {
        GraphNode<T> newNode1 = new GraphNode<>(node1);
        GraphNode<T> newNode2 = new GraphNode<>(node2);
        newNode1.setSuccessor(newNode2);
        nodes.add(newNode1);
        nodes.add(newNode2);
    }

    /**
     * 
     * @return a list of nodes contained within this graph
     */
    public List<GraphNode<T>> getNodes() {
        return nodes;
    }

    /**
     * Simple method to initialize all graph nodes to unvisited
     * for searching algorithms.
     */
    private void initializeNodes() {
        for (GraphNode<T> node : nodes) {
            node.initializeNode();
        }
    }

    /**
     * An implementation of depth first search that finds all of the successors in a
     * path from the root node called.
     * 
     * @param node the initial root called upon
     */
    public void dfs(GraphNode<T> node) {
        // set all nodes in graph to unvisited
        initializeNodes();

        // init time clock to zero
        time = 0;

        // begin dfs
        dfs_visit(node);
    }

    /**
     * Helper method for depth first search method on this graph.
     * @param node
     */
    private void dfs_visit(GraphNode<T> node) {
        // increment time discovered
        node.discovery = ++time;

        // mark node as visited
        node.setStatus(NodeStatus.FINISHED);

        // search through all neighbors of node 
        for (GraphNode<T> next : node.getSuccessors()) {
            if (next.getStatus() != NodeStatus.FINISHED)
                dfs_visit(next);
        }

        // set finished time
        node.finished = ++time;
        System.out.print(node + " ");
    }

    /**
     * A basic method for detecting cycles in a directed or undirected graph
     * using a variation of depth first search.
     * 
     * @return boolean true if cycle detected, otherwise false
     */
    public boolean graphHasCycle() {
        // set all node statuses to unvisited
        initializeNodes();

        // check all nodes in graph for cycles
        for (GraphNode<T> node : nodes) {
            // if any node has a cycle, return true
            if (hasCycle(node)) {
                System.out.println("Cycle detected in graph!");
                return true;
            }
        }
        System.out.println("No cycle detected!");
        return false;
    }

    /**
     * Helper method for determining whether a graph has a cycle.
     */
    private boolean hasCycle(GraphNode<T> node) {

        // set status of node to in progress
        node.setStatus(NodeStatus.IN_PROGRESS);

        // dfs through all successors of node
        for (GraphNode<T> neighbor : node.getSuccessors()) {

            // if you ever come upon a node already in progress, then cycle detected
            if (neighbor.getStatus() == NodeStatus.IN_PROGRESS) {
                return true;
            } 

            // continue checking for all neighbors, use stack
            if (neighbor.getStatus() != NodeStatus.FINISHED) {
                hasCycle(neighbor);
            }
        }

        // set status as done, finished
        node.setStatus(NodeStatus.FINISHED);
        return false;
    }



    /**
     * Stub for implementing Topological Ordering for graph (DFS)
     */
    public void topologicalSort() {
        
        // check for cycle
        if (graphHasCycle()) {
            System.out.println("Error cannot perform topological sort on cyclical graph!");
            return;
        }

        // set nodes to unvisited
        initializeNodes();

        // reinit time
        time = 0;

        // topologically sort graph
        for (GraphNode<T> node: nodes) 
            topSortHelper(node);

        // print sorted list
        for (GraphNode<T> node : topologicalSort) {
            System.out.print(node + " ");
        }
        System.out.println();
    } 

    /**
     * This method is used for helping produce a topological sort (total ordering)
     * of the elements of this graph.
     * 
     * Algorithm: The algorithm uses a DFS on every node of the current graph
     * until it finds the final element (element at which no other vertex points),
     * then it adds this to the top of a stack until it returns to the initial element.
     *
     */
    public void topSortHelper(GraphNode<T> node) {

        // if node is already finished, return
        if (node.getStatus() == NodeStatus.FINISHED) 
            return;

        // iterate through all successors and set order num
        for (GraphNode<T> neighbor : node.getSuccessors()) {
            if (neighbor.getStatus() != NodeStatus.FINISHED)
                topSortHelper(neighbor);
        }

        // finished when node has no more successors
        node.setStatus(NodeStatus.FINISHED);

        // add to top of stack
        topologicalSort.addFirst(node);
    }


    /**
     * An implementation of breadth first search on a graph that includes distance
     * information between the root node and its successors.
     * 
     * @param node the root node being called initially
     */
    public void bfs(GraphNode<T> node) {
        // use queue data structure for maintaining frontier
        Queue<GraphNode<T>> queue = new LinkedList<>();

        // set all node statuses to unvisited
        initializeNodes();

        // initially set root to finished
        node.setStatus(NodeStatus.FINISHED);
        queue.add(node);

        // iterate through queue
        while (!queue.isEmpty()) {

            // get first element
            GraphNode<T> current = queue.remove();
            System.out.println(current + " " + current.distance + " ");

            // iterate through successors of current and add to queue if not finished
            for (GraphNode<T> next : current.getSuccessors()) {
                if (next.getStatus() != NodeStatus.FINISHED) {
                    next.setStatus(NodeStatus.FINISHED);

                    // distance from parent node
                    next.distance = current.distance + 1;
                    queue.add(next);
                }
            }
        }
    }



    public static void main(String[] args) {
        
        Graph<Character> charGraph = new Graph<>();

        GraphNode<Character> node1 = new GraphNode<>('A');
        GraphNode<Character> node2 = new GraphNode<>('B');
        GraphNode<Character> node3 = new GraphNode<>('C');
        GraphNode<Character> node4 = new GraphNode<>('D');
        GraphNode<Character> node5 = new GraphNode<>('E');
        GraphNode<Character> node6 = new GraphNode<>('F');
        GraphNode<Character> node7 = new GraphNode<>('G');
        GraphNode<Character> node8 = new GraphNode<>('H');

        // set edges
        node1.setSuccessor(node2); // A --> B
        node2.setSuccessor(node3); // B --> C
        node3.setSuccessor(node4); // C --> D
        node1.setSuccessor(node4); // A --> D
        node4.setSuccessor(node5); // D --> E
        node5.setSuccessor(node7); // E --> G
        node2.setSuccessor(node6); // B --> F
        node6.setSuccessor(node5); // F --> E
        node8.setSuccessor(node4); // H --> D


        // add node
        charGraph.addNode(node1);
        charGraph.addNode(node2);
        charGraph.addNode(node3);
        charGraph.addNode(node4);
        charGraph.addNode(node5);
        charGraph.addNode(node6);
        charGraph.addNode(node7);
        charGraph.addNode(node8);

        // searches and methods
        System.out.println("Depth First Search: ");
        charGraph.dfs(node1);
        System.out.println("\nBreadth First Search: ");
        charGraph.bfs(node1);        

        // cycle detection
        System.out.println("Cycle Detection: ");
        charGraph.graphHasCycle();

        // topological sort
        System.out.println("Topological Sort: ");
        charGraph.topologicalSort();

    }
}
