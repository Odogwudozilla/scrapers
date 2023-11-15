package odogwudozilla.leetcode.problems.medium;

/**
 *<h2>No. 173: Binary Search Tree Iterator</h2>
 *Link: <em><a href="https://leetcode.com/problems/binary-search-tree-iterator">Binary Search Tree Iterator</a></em>
 *Difficulty: <strong>Medium</strong><hr>

 *<html><body><p>Implement the <code>BSTIterator</code> class that represents an iterator over the <strong><a href="https://en.wikipedia.org/wiki/Tree_traversal#In-order_(LNR)" target="_blank">in-order traversal</a></strong> of a binary search tree (BST):</p><ul>
 *	<li><code>BSTIterator(TreeNode root)</code> Initializes an object of the <code>BSTIterator</code> class. The <code>root</code> of the BST is given as part of the constructor. The pointer should be initialized to a non-existent number smaller than any element in the BST.</li>
 *	<li><code>boolean hasNext()</code> Returns <code>true</code> if there exists a number in the traversal to the right of the pointer, otherwise returns <code>false</code>.</li>
 *	<li><code>int next()</code> Moves the pointer to the right, then returns the number at the pointer.</li>
 *</ul><p>Notice that by initializing the pointer to a non-existent smallest number, the first call to <code>next()</code> will return the smallest element in the BST.</p><p>You may assume that <code>next()</code> calls will always be valid. That is, there will be at least a next number in the in-order traversal when <code>next()</code> is called.</p><p>&nbsp;</p><p><strong class="example">Example 1:</strong></p><pre><strong>Input</strong>
 *["BSTIterator", "next", "next", "hasNext", "next", "hasNext", "next", "hasNext", "next", "hasNext"]
 *[[[7, 3, 15, null, null, 9, 20]], [], [], [], [], [], [], [], [], []]
 *<strong>Output</strong>
 *[null, 3, 7, true, 9, true, 15, true, 20, false]
 *
 *<strong>Explanation</strong>
 *BSTIterator bSTIterator = new BSTIterator([7, 3, 15, null, null, 9, 20]);
 *bSTIterator.next();    // return 3
 *bSTIterator.next();    // return 7
 *bSTIterator.hasNext(); // return True
 *bSTIterator.next();    // return 9
 *bSTIterator.hasNext(); // return True
 *bSTIterator.next();    // return 15
 *bSTIterator.hasNext(); // return True
 *bSTIterator.next();    // return 20
 *bSTIterator.hasNext(); // return False
 *</pre><p>&nbsp;</p><p><strong>Constraints:</strong></p><ul>
 *	<li>The number of nodes in the tree is in the range <code>[1, 10<sup>5</sup>]</code>.</li>
 *	<li><code>0 &lt;= Node.val &lt;= 10<sup>6</sup></code></li>
 *	<li>At most <code>10<sup>5</sup></code> calls will be made to <code>hasNext</code>, and <code>next</code>.</li>
 *</ul><p>&nbsp;</p><p><strong>Follow up:</strong></p><ul>
 *	<li>Could you implement <code>next()</code> and <code>hasNext()</code> to run in average <code>O(1)</code> time and use&nbsp;<code>O(h)</code> memory, where <code>h</code> is the height of the tree?</li>
 *</ul></body></html>
 */

public class BinarySearchTreeIterator {

public static void main(String[] args) {
    System.out.println("Hello, World!");
}

///**
// * Definition for a binary tree node.
// *     int val;
// *     TreeNode left;
// *     TreeNode right;
// *     TreeNode() {}
// *     TreeNode(int val) { this.val = val; }
// *     TreeNode(int val, TreeNode left, TreeNode 
//right) {
// *         this.val = val;
// *         this.left = left;
// *         this.right = right;
// *     }
// * }
// */
//
//    public BSTIterator(TreeNode root) {
//        
//    }
//    
//    public int next() {
//        
//    }
//    
//    public boolean hasNext() {
//        
//    }
//}
//
///**
// * Your BSTIterator object will be instantiated and 
//called as such:
// * BSTIterator obj = new BSTIterator(root);
// * int param_1 = obj.next();
// * boolean param_2 = obj.hasNext();

}