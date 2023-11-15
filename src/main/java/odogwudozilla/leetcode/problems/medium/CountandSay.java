package odogwudozilla.leetcode.problems.medium;

/**
 *<h2>No. 38: Count and Say</h2>
 *Link: <em><a href="https://leetcode.com/problems/count-and-say">Count and Say</a></em>
 *Difficulty: <strong>Medium</strong><hr>

 *<html><body><p>The <strong>count-and-say</strong> sequence is a sequence of digit strings defined by the recursive formula:</p><ul>
 *	<li><code>countAndSay(1) = "1"</code></li>
 *	<li><code>countAndSay(n)</code> is the way you would "say" the digit string from <code>countAndSay(n-1)</code>, which is then converted into a different digit string.</li>
 *</ul><p>To determine how you "say" a digit string, split it into the <strong>minimal</strong> number of substrings such that each substring contains exactly <strong>one</strong> unique digit. Then for each substring, say the number of digits, then say the digit. Finally, concatenate every said digit.</p><p>For example, the saying and conversion for digit string <code>"3322251"</code>:</p><p>Given a positive integer <code>n</code>, return <em>the </em><code>n<sup>th</sup></code><em> term of the <strong>count-and-say</strong> sequence</em>.</p><p>&nbsp;</p><p><strong class="example">Example 1:</strong></p><pre><strong>Input:</strong> n = 1
 *<strong>Output:</strong> "1"
 *<strong>Explanation:</strong> This is the base case.
 *</pre><p><strong class="example">Example 2:</strong></p><pre><strong>Input:</strong> n = 4
 *<strong>Output:</strong> "1211"
 *<strong>Explanation:</strong>
 *countAndSay(1) = "1"
 *countAndSay(2) = say "1" = one 1 = "11"
 *countAndSay(3) = say "11" = two 1's = "21"
 *countAndSay(4) = say "21" = one 2 + one 1 = "12" + "11" = "1211"
 *</pre><p>&nbsp;</p><p><strong>Constraints:</strong></p><ul>
 *	<li><code>1 &lt;= n &lt;= 30</code></li>
 *</ul></body></html>
 */

public class CountandSay {

public static void main(String[] args) {
    System.out.println("Hello, World!");
}

//    public String countAndSay(int n) {
//        
//    }
//}

}