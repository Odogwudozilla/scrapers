package odogwudozilla.leetcode.problems.easy;

/**
 *<h2>No. 141: Linked List Cycle</h2>
 *Link: <em><a href="https://leetcode.com/problems/linked-list-cycle">Linked List Cycle</a></em>
 *Difficulty: <strong>Easy</strong><hr>

 *<html><body><p>Given <code>head</code>, the head of a linked list, determine if the linked list has a cycle in it.</p><p>There is a cycle in a linked list if there is some node in the list that can be reached again by continuously following the&nbsp;<code>next</code>&nbsp;pointer. Internally, <code>pos</code>&nbsp;is used to denote the index of the node that&nbsp;tail's&nbsp;<code>next</code>&nbsp;pointer is connected to.&nbsp;<strong>Note that&nbsp;<code>pos</code>&nbsp;is not passed as a parameter</strong>.</p><p>Return&nbsp;<code>true</code><em> if there is a cycle in the linked list</em>. Otherwise, return <code>false</code>.</p><p>&nbsp;</p><p><strong class="example">Example 1:</strong></p><pre><strong>Input:</strong> head = [3,2,0,-4], pos = 1
 *<strong>Output:</strong> true
 *<strong>Explanation:</strong> There is a cycle in the linked list, where the tail connects to the 1st node (0-indexed).
 *</pre><p><strong class="example">Example 2:</strong></p><pre><strong>Input:</strong> head = [1,2], pos = 0
 *<strong>Output:</strong> true
 *<strong>Explanation:</strong> There is a cycle in the linked list, where the tail connects to the 0th node.
 *</pre><p><strong class="example">Example 3:</strong></p><pre><strong>Input:</strong> head = [1], pos = -1
 *<strong>Output:</strong> false
 *<strong>Explanation:</strong> There is no cycle in the linked list.
 *</pre><p>&nbsp;</p><p><strong>Constraints:</strong></p><ul>
 *	<li>The number of the nodes in the list is in the range <code>[0, 10<sup>4</sup>]</code>.</li>
 *	<li><code>-10<sup>5</sup> &lt;= Node.val &lt;= 10<sup>5</sup></code></li>
 *	<li><code>pos</code> is <code>-1</code> or a <strong>valid index</strong> in the linked-list.</li>
 *</ul><p>&nbsp;</p><p><strong>Follow up:</strong> Can you solve it using <code>O(1)</code> (i.e. constant) memory?</p></body></html>
 */

public class LinkedListCycle {

public static void main(String[] args) {
    System.out.println("Hello, World!");
}

///**
// * Definition for singly-linked list.
// *     int val;
// *     ListNode next;
// *     ListNode(int x) {
// *         val = x;
// *         next = null;
// *     }
// * }
// */
//    public boolean hasCycle(ListNode head) {
//        
//    }
//}

}