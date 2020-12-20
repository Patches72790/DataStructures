// --== CS400 File Header Information ==--
// Name: Patrick Harvey
// Email: plharvey@wisc.edu
// Team: Team AB
// TA: Sophie Stephenson
// Lecturer: Gary Dahl
// Notes to Grader: 

import java.util.LinkedList;

/**
 * Binary Search Tree implementation with a Node inner class for representing
 * the nodes within a binary search tree.  You can use this class' insert
 * method to build a binary search tree, and its toString method to display
 * the level order (breadth first) traversal of values in that tree.
 * 
 * @author Patrick Harvey
 */
public class RedBlackTree<T extends Comparable<T>> {

    /**
     * This class represents a node holding a single value within a binary tree
     * the parent, left, and right child references are always be maintained.
     */
    protected static class Node<T> {
        public T data;
        public Node<T> parent; // null for root node
        public Node<T> leftChild; 
        public Node<T> rightChild; 
        public boolean isBlack;

        public Node(T data) { 
            this.data = data;
            this.isBlack = false;
        }

        /**
         * @return true when this node has a parent and is the left child of
         * that parent, otherwise return false
         */
        public boolean isLeftChild() {
            return parent != null && parent.leftChild == this;
        }

        /**
         * This method performs a level order traversal of the tree rooted
         * at the current node.  The string representations of each data value
         * within this tree are assembled into a comma separated string within
         * brackets (similar to many implementations of java.util.Collection).
         * @return string containing the values of this tree in level order
         */
        @Override
        public String toString() { // display subtree in order traversal
            String output = "[";
            LinkedList<Node<T>> q = new LinkedList<>();
            q.add(this);
            while(!q.isEmpty()) {
                Node<T> next = q.removeFirst();
                if(next.leftChild != null) q.add(next.leftChild);
                if(next.rightChild != null) q.add(next.rightChild);
                output += next.data.toString();
                if(!q.isEmpty()) output += ", ";
            }
            return output + "]";
        }

        /**
         * This method is used for testing purposes only. This method appends
         * the node's color to the end of each node.
         * @return the string of this tree (the node's color also appended)
         */
        public String toStringWithColor() { // display subtree in order traversal
            String output = "[";
            LinkedList<Node<T>> q = new LinkedList<>();
            q.add(this);
            while(!q.isEmpty()) {
                Node<T> next = q.removeFirst();
                String nextColor = next.isBlack ? "(Black)" : "(Red)";
                if(next.leftChild != null) q.add(next.leftChild);
                if(next.rightChild != null) q.add(next.rightChild);
                output += next.data.toString() + nextColor;
                if(!q.isEmpty()) output += ", ";
            }
            return output + "]";
        }
    }

    protected Node<T> root; // reference to root node of tree, null when empty

    /**
     * Performs a naive insertion into a binary search tree: adding the input
     * data value to a new node in a leaf position within the tree.  After  
     * this insertion, no attempt is made to restructure or balance the tree.
     * This tree will not hold null references, nor duplicate data values.
     * @param data to be added into this binary search tree
     * @throws NullPointerException when the provided data argument is null
     * @throws IllegalArgumentException when the tree already contains data
     */
    public void insert(T data) throws NullPointerException,
				      IllegalArgumentException {
        // null references cannot be stored within this tree
        if(data == null) throw new NullPointerException(
            "This RedBlackTree cannot store null references.");

        Node<T> newNode = new Node<>(data);
        if(root == null) { root = newNode; } // add first node to an empty tree
        else insertHelper(newNode,root); // recursively insert into subtree
        root.isBlack = true; // always set root node back to black
    }

    /** 
     * Recursive helper method to find the subtree with a null reference in the
     * position that the newNode should be inserted, and then extend this tree
     * by the newNode in that position.
     * @param newNode is the new node that is being added to this tree
     * @param subtree is the reference to a node within this tree which the 
     *      newNode should be inserted as a descenedent beneath
     * @throws IllegalArgumentException when the newNode and subtree contain
     *      equal data references (as defined by Comparable.compareTo())
     */
    private void insertHelper(Node<T> newNode, Node<T> subtree) {
        int compare = newNode.data.compareTo(subtree.data);
        // do not allow duplicate values to be stored within this tree
        if(compare == 0) throw new IllegalArgumentException(
            "This RedBlackTree already contains that value.");

        // store newNode within left subtree of subtree
        else if(compare < 0) {
            if(subtree.leftChild == null) { // left subtree empty, add here
                subtree.leftChild = newNode;
                newNode.parent = subtree;
                enforceRBTreePropertiesAfterInsert(newNode); // fixup RBTree properties after inserting red node    
            // otherwise continue recursive search for location to insert
            } else insertHelper(newNode, subtree.leftChild);
        }

        // store newNode within the right subtree of subtree
        else { 
            if(subtree.rightChild == null) { // right subtree empty, add here
                subtree.rightChild = newNode;
                newNode.parent = subtree;
                enforceRBTreePropertiesAfterInsert(newNode); // fixup RBTree properties
            // otherwise continue recursive search for location to insert
            } else insertHelper(newNode, subtree.rightChild);
        }
    }
    /**
    * This method maintains the red black tree properties of black height 
    * and contiguous red nodes (parent / children) after inserting
    * a new red node as a leaf to this tree. In particular, it resolves three cases: 
    *
    * 1) the new node's parent and uncle are both red, 
    * 2) the new node's uncle is black and new node is on the opposite side
    * 3) the new node's uncle is black and new node is on the same side
    *
    * @param newRedNode is the newRedNode leaf added to the tree
    */
    private void enforceRBTreePropertiesAfterInsert(Node<T> newRedNode ) {
        // base case for recursive calls
        // newRedNode's parent is black or parent is root of tree (i.e. parent is null)
        if ((newRedNode.parent == null) || (newRedNode.parent.isBlack)) {
            return;
        }

        // NEW NODE ADDED TO LEFT SUB TREE
        if (newRedNode.parent.isLeftChild()) {

            // case 1 -- new node's parent and uncle are red
            if ( (newRedNode.parent.parent.rightChild != null) && !(newRedNode.parent.parent.rightChild.isBlack)) {
                // change colors of parent & uncle to black
                newRedNode.parent.isBlack = true;
                newRedNode.parent.parent.rightChild.isBlack = true;
                //change color of grandparent to red
                newRedNode.parent.parent.isBlack = false;

                // finished, if at newRedNode.parent is root
                // otherwise, resolve black height imbalance after grandparent set to red
                // by checking recursively through grandparent
                enforceRBTreePropertiesAfterInsert(newRedNode.parent.parent);
            } 
        
            // case 2 -- new node's uncle is black AND on opposite side as new node (i.e. a left child)
            else if ( newRedNode.isLeftChild() ) {
                
                //swap colors of parent and grandparent 
                newRedNode.parent.isBlack = true;
                newRedNode.parent.parent.isBlack = false;

                // right rotate parent with grandparent
                rotate(newRedNode.parent, newRedNode.parent.parent);
            }
          
            // case 3 -- new node's uncle is black AND on same side as new node (i.e. a right child)
            else {
                
                // get into case 2 by rotating new node and its parent
                rotate(newRedNode, newRedNode.parent);

                // fixup case 2 by rotating and color swapping
                newRedNode.isBlack = true;
                newRedNode.parent.isBlack = false;
                rotate(newRedNode, newRedNode.parent);
            }
        }

        // NEW NODE ADDED TO RIGHT SUB TREE
        // SAME AS ABOVE BUT LEFT/RIGHT FLIPPED
         else {
            // case 1 -- new node's parent and uncle are red
            if ( (newRedNode.parent.parent.leftChild != null) && !(newRedNode.parent.parent.leftChild.isBlack)) {
                // change colors of parent & uncle to black
                newRedNode.parent.isBlack = true;
                newRedNode.parent.parent.leftChild.isBlack = true;
                //change color of grandparent to red
                newRedNode.parent.parent.isBlack = false;

                // finished, if at newRedNode.parent is root
                // otherwise, resolve black height imbalance after grandparent set to red
                // by checking recursively through grandparent
                enforceRBTreePropertiesAfterInsert(newRedNode.parent.parent);
            } 
        
            // case 2 -- new node's uncle is black AND on opposite side as new node (i.e. is right child)
            else if ( !newRedNode.isLeftChild() ) {
                
                //swap colors of parent and grandparent 
                newRedNode.parent.isBlack = true;
                newRedNode.parent.parent.isBlack = false;

                // left rotate parent with grandparent
                rotate(newRedNode.parent, newRedNode.parent.parent);
            }
            
            // case 3 -- new node's uncle is black AND on same side as new node (i.e. is left child)
            else {
                // get into case 2 by rotating new node and its parent
                rotate(newRedNode, newRedNode.parent);

                // fixup case 2 by rotating and color swapping
                newRedNode.isBlack = true;
                newRedNode.parent.isBlack = false;
                rotate(newRedNode, newRedNode.parent);
            }
        }
    }

    /**
     * This method performs a level order traversal of the tree. The string 
     * representations of each data value within this tree are assembled into a
     * comma separated string within brackets (similar to many implementations 
     * of java.util.Collection, like java.util.ArrayList, LinkedList, etc).
     * @return string containing the values of this tree in level order
     */
    @Override
    public String toString() { return root.toString(); }

    /**
     * Method used for testing that appends the node's color to the end of the node.
     * @return the to string of the tree with color of node appended to each
     */
    public String toStringWithColor() { return root.toStringWithColor(); }

    /**
     * Performs the rotation operation on the provided nodes within this BST.
     * When the provided child is a leftChild of the provided parent, this
     * method will perform a right rotation (sometimes called a left-right 
     * rotation).  When the provided child is a rightChild of the provided 
     * parent, this method will perform a left rotation (sometimes called a 
     * right-left rotation).  When the provided nodes are not related in one 
     * of these ways, this method will throw an IllegalArgumentException.
     * @param child is the node being rotated from child to parent position
     *      (between these two node arguments)
     * @param parent is the node being rotated from parent to child position
     *      (between these two node arguments)
     * @throws IllegalArgumentException when the provided child and parent
     *      node references are not initially (pre-rotation) related that way
     */
    private void rotate(Node<T> child, Node<T> parent)
	throws IllegalArgumentException {

        if (child == null || parent == null) {
            throw new IllegalArgumentException("Child and/or parent references are null.");
        }

        //the child is the left child of parent
        //right rotate
        if ( (parent.leftChild != null) && (child.data.compareTo(parent.leftChild.data) == 0) )  {
            //put child's right subtree as parent's left subtree
            parent.leftChild = child.rightChild;

            if (child.rightChild != null) {
                child.rightChild.parent = parent;
            }

            //child becomes parent of parent
            child.rightChild = parent;

            //child get's parent's parent
            child.parent = parent.parent;

            //change parent's parent's reference
            // is left child
            if (parent.isLeftChild()) {
                parent.parent.leftChild = child;
            } 
            // is right child
            else if (!parent.isLeftChild() && (parent.data.compareTo(root.data) != 0)) {
                parent.parent.rightChild = child;
            } 
            // is root
            else {
                root = child;
            }

            //set parent's parent to child
            parent.parent = child;

        } 
        
        //child is right child of parent
        //left rotate
        else if ( child.data.compareTo(parent.rightChild.data) == 0 ) {
            //put child's left subtree as parent's right subtree
            parent.rightChild = child.leftChild;

            if (child.leftChild != null) {
                child.leftChild.parent = parent; 
            }
            
            //child becomes parent of parent
            child.leftChild = parent;

            //swap child/parent relation
            child.parent = parent.parent;

            //change parent's parent's reference to child
            // left child
            if (parent.isLeftChild()) {
                parent.parent.leftChild = child;
            }
            //is right child
            else if (!parent.isLeftChild() && (parent.data.compareTo(root.data) != 0)) {
                parent.parent.rightChild = child;
            } 
            // is root
            else {
                root = child;
            }

            //set parent's parent to child
            parent.parent = child;            

        } else {
            throw new IllegalArgumentException("Child is not child of parent node given!");
        }
    }

}
