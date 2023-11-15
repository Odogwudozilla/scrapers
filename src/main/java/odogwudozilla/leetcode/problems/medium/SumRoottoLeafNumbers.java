package odogwudozilla.leetcode.problems.medium;

/**
 *<h2>No. 129: Sum Root to Leaf Numbers</h2>
 *Link: <em><a href="https://leetcode.com/problems/sum-root-to-leaf-numbers">Sum Root to Leaf Numbers</a></em>
 *Difficulty: <strong>Medium</strong><hr>

 *<html><body><p>You are given the <code>root</code> of a binary tree containing digits from <code>0</code> to <code>9</code> only.</p><p>Each root-to-leaf path in the tree represents a number.</p><ul>
 *	<li>For example, the root-to-leaf path <code>1 -&gt; 2 -&gt; 3</code> represents the number <code>123</code>.</li>
 *</ul><p>Return <em>the total sum of all root-to-leaf numbers</em>. Test cases are generated so that the answer will fit in a <strong>32-bit</strong> integer.</p><p>A <strong>leaf</strong> node is a node with no children.</p><p>&nbsp;</p><p><strong class="example">Example 1:</strong></p><pre><strong>Input:</strong> root = [1,2,3]
 *<strong>Output:</strong> 25
 *<strong>Explanation:</strong>
 *The root-to-leaf path <code>1-&gt;2</code> represents the number <code>12</code>.
 *The root-to-leaf path <code>1-&gt;3</code> represents the number <code>13</code>.
 *Therefore, sum = 12 + 13 = <code>25</code>.
 *</pre><p><strong class="example">Example 2:</strong></p><pre><strong>Input:</strong> root = [4,9,0,5,1]
 *<strong>Output:</strong> 1026
 *<strong>Explanation:</strong>
 *The root-to-leaf path <code>4-&gt;9-&gt;5</code> represents the number 495.
 *The root-to-leaf path <code>4-&gt;9-&gt;1</code> represents the number 491.
 *The root-to-leaf path <code>4-&gt;0</code> represents the number 40.
 *Therefore, sum = 495 + 491 + 40 = <code>1026</code>.
 *</pre><p>&nbsp;</p><p><strong>Constraints:</strong></p><ul>
 *	<li>The number of nodes in the tree is in the range <code>[1, 1000]</code>.</li>
 *	<li><code>0 &lt;= Node.val &lt;= 9</code></li>
 *	<li>The depth of the tree will not exceed <code>10</code>.</li>
 *</ul></body></html>
 */

public class SumRoottoLeafNumbers {

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
//    public int sumNumbers(TreeNode root) {
//        
//    }
//}

}