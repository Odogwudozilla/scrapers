package odogwudozilla.leetcode.problems.medium;

/**
 *<h2>No. 211: Design Add and Search Words Data Structure</h2>
 *Link: <em><a href="https://leetcode.com/problems/design-add-and-search-words-data-structure">Design Add and Search Words Data Structure</a></em>
 *Difficulty: <strong>Medium</strong><hr>

 *<html><body><p>Design a data structure that supports adding new words and finding if a string matches any previously added string.</p><p>Implement the <code>WordDictionary</code> class:</p><ul>
 *	<li><code>WordDictionary()</code>&nbsp;Initializes the object.</li>
 *	<li><code>void addWord(word)</code> Adds <code>word</code> to the data structure, it can be matched later.</li>
 *	<li><code>bool search(word)</code>&nbsp;Returns <code>true</code> if there is any string in the data structure that matches <code>word</code>&nbsp;or <code>false</code> otherwise. <code>word</code> may contain dots <code>'.'</code> where dots can be matched with any letter.</li>
 *</ul><p>&nbsp;</p><p><strong class="example">Example:</strong></p><pre><strong>Input</strong>
 *["WordDictionary","addWord","addWord","addWord","search","search","search","search"]
 *[[],["bad"],["dad"],["mad"],["pad"],["bad"],[".ad"],["b.."]]
 *<strong>Output</strong>
 *[null,null,null,null,false,true,true,true]
 *
 *<strong>Explanation</strong>
 *WordDictionary wordDictionary = new WordDictionary();
 *wordDictionary.addWord("bad");
 *wordDictionary.addWord("dad");
 *wordDictionary.addWord("mad");
 *wordDictionary.search("pad"); // return False
 *wordDictionary.search("bad"); // return True
 *wordDictionary.search(".ad"); // return True
 *wordDictionary.search("b.."); // return True
 *</pre><p>&nbsp;</p><p><strong>Constraints:</strong></p><ul>
 *	<li><code>1 &lt;= word.length &lt;= 25</code></li>
 *	<li><code>word</code> in <code>addWord</code> consists of lowercase English letters.</li>
 *	<li><code>word</code> in <code>search</code> consist of <code>'.'</code> or lowercase English letters.</li>
 *	<li>There will be at most <code>2</code> dots in <code>word</code> for <code>search</code> queries.</li>
 *	<li>At most <code>10<sup>4</sup></code> calls will be made to <code>addWord</code> and <code>search</code>.</li>
 *</ul></body></html>
 */

public class DesignAddandSearchWordsDataStructure {

public static void main(String[] args) {
    System.out.println("Hello, World!");
}

//
//    public WordDictionary() {
//        
//    }
//    
//    public void addWord(String word) {
//        
//    }
//    
//    public boolean search(String word) {
//        
//    }
//}
//
///**
// * Your WordDictionary object will be instantiated 
//and called as such:
// * WordDictionary obj = new WordDictionary();
// * obj.addWord(word);
// * boolean param_2 = obj.search(word);
// */

}