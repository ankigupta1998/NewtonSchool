// create -- DONE
// traversal -- DONE
// search == DONE
// path -- DONE
// height -- DONE
// diameter
// LCA - paths, general

import java.util.ArrayList;
import java.util.List;

class Node {
    int value;
    Node left;
    Node right;
    Node(int val) {
        value = val;
        left = right = null;
    }
}

class Height { // now this is pass by reference
    int h;
}

class BinaryTree {
    Node root;
    public BinaryTree() {
        // create binary tree
        root = new Node(1);
        root.left = new Node(2);
        root.right = new Node(3);
        root.left.right = new Node(4);
        root.right.left = new Node(5);
        root.right.left.right = new Node(6);
    }

    // n is number of nodes in tree
    public void inorder() { // O(n)
        Node temp = root;
        inorderUtil(temp);
    }
    private void inorderUtil(Node root) {
        if (root == null) {
            return;
        }
        // left
        inorderUtil(root.left);
        // self
        System.out.print(root.value);
        //right
        inorderUtil(root.right);
    }

    public void preorder() { // O(n)
        Node temp = root;
        preorderUtil(temp);
    }
    private void preorderUtil(Node root) {
        if (root == null) {
            return;
        }
        // self
        System.out.print(root.value);
        // left
        preorderUtil(root.left);
        //right
        preorderUtil(root.right);
    }

    public void postorder() { // O(n)
        Node temp = root;
        postorderUtil(temp);
    }
    private void postorderUtil(Node root) {
        if (root == null) {
            return;
        }
        // left
        postorderUtil(root.left);
        //right
        postorderUtil(root.right);
        // self
        System.out.print(root.value);
    }


    public void search(int val) { // O(n)
        searchUtil(root, val);
    }
    private void searchUtil(Node root, int val) {
        if (root == null) {
            return;
        }
        // any traversal is fine, lets try preorder
        if (root.value == val) {
            System.out.println("Yay, we found that value " + root.value);
            return;
        }
        searchUtil(root.left, val);
        searchUtil(root.right, val);
    }

    public void path(int val) { // O(n)
        ArrayList<Integer> ll = new ArrayList<>();
        pathUtil(root, val, ll);
    }
    private void pathUtil(Node root, int val, ArrayList<Integer> ll) {
        if (root == null) {
            return;
        }
        // preorder
        // do for self in preorder
        ll.add(root.value);
        // any traversal is fine, lets try preorder
        if (root.value == val) {
            System.out.println("Yay, we found that value " + root.value);
            // print the path
            for(int i = 0; i < ll.size(); i++) {
                System.out.print(ll.get(i));
            }
            return;
        }

        // left
        pathUtil(root.left, val, ll);
        //right
        pathUtil(root.right, val, ll);

        // remove last element as we could not find the value
        ll.remove(ll.size()-1);
    }

    public int height() { // O(n)
//         max(height(left), height(right)) + 1
        return heightUtil(root);
    }

    private int heightUtil(Node root) {
        // base condition
        if (root == null) {
            return 0;
        }
        return Math.max(heightUtil(root.left), heightUtil(root.right)) + 1;
    }

    public int diameter() { // O(n^2)
        // longest path between any 2 nodes in tree
//        return diamterUtil(root);

        Height h = new Height();
        return diamterOptimized(root, h);
    }

    private int diamterUtil(Node root) {
        if (root == null) {
            return 0;
        }
        // left and right height
        int lHeight = heightUtil(root.left);
        int rHeight = heightUtil(root.right);

        // find max diameter in left subtree
        int lMax = diamterUtil(root.left);
        // find max diameter in right subtree
        int rMax = diamterUtil(root.right);

        return Math.max(lMax, Math.max(rMax, 1 + lHeight + rHeight));
    }

    private int diamterOptimized(Node root, Height h) { // h is height from below // O(n)
        // base case
        if (root == null) {
            h.h = 0;
            return 0;
        }
        Height lH = new Height();
        Height rH = new Height();
        int leftResult = diamterOptimized(root.left, lH);
        int rightResult = diamterOptimized(root.right, rH);

        // current max height
        h.h = Math.max(lH.h, rH.h) + 1;

        return Math.max(leftResult, Math.max(rightResult, lH.h + rH.h + 1));
    }
}

public class BinaryTreeGeneric {
    public static void main(String[] args) {
        BinaryTree tree = new BinaryTree();
        tree.inorder();
        System.out.println();
        tree.preorder();
        System.out.println();
        tree.postorder();
        System.out.println();
        tree.search(5);
        System.out.println();
        tree.path(6);
        System.out.println();
        System.out.print("Height of tree is " + tree.height());
        System.out.println();
        System.out.print("Diameter of tree is " + tree.diameter());
        System.out.println();
    }
}