package odogwudozilla.leetcode.problems.medium;

/**
 *<h2>No. 8: String to Integer (atoi)</h2>
 *Link: <em><a href="https://leetcode.com/problems/string-to-integer-atoi">String to Integer (atoi)</a></em>
 *Difficulty: <strong>Medium</strong><hr>

 *<html><body><p>Implement the <code>myAtoi(string s)</code> function, which converts a string to a 32-bit signed integer (similar to C/C++'s <code>atoi</code> function).</p><p>The algorithm for <code>myAtoi(string s)</code> is as follows:</p><p><strong>Note:</strong></p><ul>
 *	<li>Only the space character <code>' '</code> is considered a whitespace character.</li>
 *	<li><strong>Do not ignore</strong> any characters other than the leading whitespace or the rest of the string after the digits.</li>
 *</ul><p>&nbsp;</p><p><strong class="example">Example 1:</strong></p><pre><strong>Input:</strong> s = "42"
 *<strong>Output:</strong> 42
 *<strong>Explanation:</strong> The underlined characters are what is read in, the caret is the current reader position.
 *Step 1: "42" (no characters read because there is no leading whitespace)
 *         ^
 *Step 2: "42" (no characters read because there is neither a '-' nor '+')
 *         ^
 *Step 3: "<u>42</u>" ("42" is read in)
 *           ^
 *The parsed integer is 42.
 *Since 42 is in the range [-2<sup>31</sup>, 2<sup>31</sup> - 1], the final result is 42.
 *</pre><p><strong class="example">Example 2:</strong></p><pre><strong>Input:</strong> s = "   -42"
 *<strong>Output:</strong> -42
 *<strong>Explanation:</strong>
 *Step 1: "<u>   </u>-42" (leading whitespace is read and ignored)
 *            ^
 *Step 2: "   <u>-</u>42" ('-' is read, so the result should be negative)
 *             ^
 *Step 3: "   -<u>42</u>" ("42" is read in)
 *               ^
 *The parsed integer is -42.
 *Since -42 is in the range [-2<sup>31</sup>, 2<sup>31</sup> - 1], the final result is -42.
 *</pre><p><strong class="example">Example 3:</strong></p><pre><strong>Input:</strong> s = "4193 with words"
 *<strong>Output:</strong> 4193
 *<strong>Explanation:</strong>
 *Step 1: "4193 with words" (no characters read because there is no leading whitespace)
 *         ^
 *Step 2: "4193 with words" (no characters read because there is neither a '-' nor '+')
 *         ^
 *Step 3: "<u>4193</u> with words" ("4193" is read in; reading stops because the next character is a non-digit)
 *             ^
 *The parsed integer is 4193.
 *Since 4193 is in the range [-2<sup>31</sup>, 2<sup>31</sup> - 1], the final result is 4193.
 *</pre><p>&nbsp;</p><p><strong>Constraints:</strong></p><ul>
 *	<li><code>0 &lt;= s.length &lt;= 200</code></li>
 *	<li><code>s</code> consists of English letters (lower-case and upper-case), digits (<code>0-9</code>), <code>' '</code>, <code>'+'</code>, <code>'-'</code>, and <code>'.'</code>.</li>
 *</ul></body></html>
 */

public class StringtoInteger {

public static void main(String[] args) {
    System.out.println("Hello, World!");
}

//    public int myAtoi(String s) {
//        
//    }
//}

}