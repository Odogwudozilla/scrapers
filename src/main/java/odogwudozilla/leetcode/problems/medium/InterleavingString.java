package odogwudozilla.leetcode.problems.medium;

/**
 *<h2>No. 97: Interleaving String</h2>
 *Link: <em><a href="https://leetcode.com/problems/interleaving-string">Interleaving String</a></em>
 *Difficulty: <strong>Medium</strong><hr>

 *<html><body><p>Given strings <code>s1</code>, <code>s2</code>, and <code>s3</code>, find whether <code>s3</code> is formed by an <strong>interleaving</strong> of <code>s1</code> and <code>s2</code>.</p><p>An <strong>interleaving</strong> of two strings <code>s</code> and <code>t</code> is a configuration where <code>s</code> and <code>t</code> are divided into <code>n</code> and <code>m</code> <span data-keyword="substring-nonempty" class=" cursor-pointer relative text-dark-blue-s text-sm"><div class="popover-wrapper inline-block" data-headlessui-state=""><div><div aria-expanded="false" data-headlessui-state="" id="headlessui-popover-button-:rg:"><div>substrings</div></div><div style="position: fixed; z-index: 40; inset: 0px auto auto 0px; transform: translate(321px, 300px);"></div></div></div></span> respectively, such that:</p><ul>
 *	<li><code>s = s<sub>1</sub> + s<sub>2</sub> + ... + s<sub>n</sub></code></li>
 *	<li><code>t = t<sub>1</sub> + t<sub>2</sub> + ... + t<sub>m</sub></code></li>
 *	<li><code>|n - m| &lt;= 1</code></li>
 *	<li>The <strong>interleaving</strong> is <code>s<sub>1</sub> + t<sub>1</sub> + s<sub>2</sub> + t<sub>2</sub> + s<sub>3</sub> + t<sub>3</sub> + ...</code> or <code>t<sub>1</sub> + s<sub>1</sub> + t<sub>2</sub> + s<sub>2</sub> + t<sub>3</sub> + s<sub>3</sub> + ...</code></li>
 *</ul><p><strong>Note:</strong> <code>a + b</code> is the concatenation of strings <code>a</code> and <code>b</code>.</p><p>&nbsp;</p><p><strong class="example">Example 1:</strong></p><pre><strong>Input:</strong> s1 = "aabcc", s2 = "dbbca", s3 = "aadbbcbcac"
 *<strong>Output:</strong> true
 *<strong>Explanation:</strong> One way to obtain s3 is:
 *Split s1 into s1 = "aa" + "bc" + "c", and s2 into s2 = "dbbc" + "a".
 *Interleaving the two splits, we get "aa" + "dbbc" + "bc" + "a" + "c" = "aadbbcbcac".
 *Since s3 can be obtained by interleaving s1 and s2, we return true.
 *</pre><p><strong class="example">Example 2:</strong></p><pre><strong>Input:</strong> s1 = "aabcc", s2 = "dbbca", s3 = "aadbbbaccc"
 *<strong>Output:</strong> false
 *<strong>Explanation:</strong> Notice how it is impossible to interleave s2 with any other string to obtain s3.
 *</pre><p><strong class="example">Example 3:</strong></p><pre><strong>Input:</strong> s1 = "", s2 = "", s3 = ""
 *<strong>Output:</strong> true
 *</pre><p>&nbsp;</p><p><strong>Constraints:</strong></p><ul>
 *	<li><code>0 &lt;= s1.length, s2.length &lt;= 100</code></li>
 *	<li><code>0 &lt;= s3.length &lt;= 200</code></li>
 *	<li><code>s1</code>, <code>s2</code>, and <code>s3</code> consist of lowercase English letters.</li>
 *</ul><p>&nbsp;</p><p><strong>Follow up:</strong> Could you solve it using only <code>O(s2.length)</code> additional memory space?</p></body></html>
 */

public class InterleavingString {

public static void main(String[] args) {
    System.out.println("Hello, World!");
}

//    public boolean isInterleave(String s1, String s2, 
//String s3) {
//        
//    }
//}

}