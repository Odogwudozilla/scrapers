package odogwudozilla.leetcode.problems.medium;

/**
 *<h2>No. 208: Implement Trie (Prefix Tree)</h2>
 *Link: <em><a href="https://leetcode.com/problems/implement-trie-prefix-tree">Implement Trie (Prefix Tree)</a></em>
 *Difficulty: <strong>Medium</strong><hr>

 *<html><body><p>A <a href="https://en.wikipedia.org/wiki/Trie" target="_blank"><strong>trie</strong></a> (pronounced as "try") or <strong>prefix tree</strong> is a tree data structure used to efficiently store and retrieve keys in a dataset of strings. There are various applications of this data structure, such as autocomplete and spellchecker.</p><p>Implement the Trie class:</p><ul>
 *	<li><code>Trie()</code> Initializes the trie object.</li>
 *	<li><code>void insert(String word)</code> Inserts the string <code>word</code> into the trie.</li>
 *	<li><code>boolean search(String word)</code> Returns <code>true</code> if the string <code>word</code> is in the trie (i.e., was inserted before), and <code>false</code> otherwise.</li>
 *	<li><code>boolean startsWith(String prefix)</code> Returns <code>true</code> if there is a previously inserted string <code>word</code> that has the prefix <code>prefix</code>, and <code>false</code> otherwise.</li>
 *</ul><p>&nbsp;</p><p><strong class="example">Example 1:</strong></p><pre><strong>Input</strong>
 *["Trie", "insert", "search", "search", "startsWith", "insert", "search"]
 *[[], ["apple"], ["apple"], ["app"], ["app"], ["app"], ["app"]]
 *<strong>Output</strong>
 *[null, null, true, false, true, null, true]
 *
 *<strong>Explanation</strong>
 *Trie trie = new Trie();
 *trie.insert("apple");
 *trie.search("apple");   // return True
 *trie.search("app");     // return False
 *trie.startsWith("app"); // return True
 *trie.insert("app");
 *trie.search("app");     // return True
 *</pre><p>&nbsp;</p><p><strong>Constraints:</strong></p><ul>
 *	<li><code>1 &lt;= word.length, prefix.length &lt;= 2000</code></li>
 *	<li><code>word</code> and <code>prefix</code> consist only of lowercase English letters.</li>
 *	<li>At most <code>3 * 10<sup>4</sup></code> calls <strong>in total</strong> will be made to <code>insert</code>, <code>search</code>, and <code>startsWith</code>.</li>
 *</ul></body></html>
 */

public class ImplementTrie {

public static void main(String[] args) {
    System.out.println("Hello, World!");
}

//
//    public Trie() {
//        
//    }
//    
//    public void insert(String word) {
//        
//    }
//    
//    public boolean search(String word) {
//        
//    }
//    
//    public boolean startsWith(String prefix) {
//        
//    }
//}
//
///**
// * Your Trie object will be instantiated and called 
//as such:
// * Trie obj = new Trie();
// * obj.insert(word);
// * boolean param_2 = obj.search(word);
// * boolean param_3 = obj.startsWith(prefix);
// */

}