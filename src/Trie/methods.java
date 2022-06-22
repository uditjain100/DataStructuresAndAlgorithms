package Trie;

import java.util.*;

import Trie.intro_Trie.*;

public class methods {

    // **** https://www.geeksforgeeks.org/print-unique-rows/
    public ArrayList<int[]> uniqueRows(int[][] arr) {

        ArrayList<int[]> list = new ArrayList<>();

        Trie3 trie = new Trie3();
        trie.constructTrie(arr);

        for (int[] a : arr)
            if (trie.search(a) == 1)
                list.add(a);

        return list;
    }

    public ArrayList<int[]> duplicateRows(int[][] arr) {

        ArrayList<int[]> list = new ArrayList<>();

        Trie3 trie = new Trie3();
        trie.constructTrie(arr);

        for (int[] a : arr)
            if (trie.search(a) > 1)
                list.add(a);

        return list;
    }

    // ****https://www.geeksforgeeks.org/count-of-strings-whose-prefix-match-with-the-given-string-to-a-given-length-k/
    public int prefixMatch(String[] arr, String str, int k) {
        Trie trie = new Trie();
        trie.constructTrie(arr);

        return trie.prefixSearchUptoK(str, k);
    }

    // ***https://www.geeksforgeeks.org/find-all-shortest-unique-prefixes-to-represent-each-word-in-a-given-list/
    public String[] shortestUniquePrefix(String[] arr) {

        String[] res = new String[arr.length];

        Trie trie = new Trie();
        trie.constructTrie(arr);

        int i = 0;
        for (String str : arr)
            res[i++] = trie.getShortestUniquePrefix(str);

        return res;
    }

    // *https://www.geeksforgeeks.org/palindrome-pair-in-an-array-of-words-or-strings/
    public boolean palindromicPairs(String[] arr) {

        Trie trie = new Trie();
        trie.insert(arr[0]);

        for (int i = 1; i < arr.length; i++) {
            if (trie.checkPalindromePair(arr[i]))
                return true;
            trie.insert(arr[i]);
        }

        return false;
    }

    // *** https://www.geeksforgeeks.org/implement-a-phone-directory/
    public ArrayList<String> phoneDirectory(String[] arr, String str) {
        Trie trie = new Trie();
        trie.constructTrie(arr);
        return trie.phoneSearch(str);
    }

    // **** https://www.geeksforgeeks.org/frequent-word-array-strings/
    public String mostFrequentWord(String[] arr) {
        Trie trie = new Trie();
        trie.constructTrie(arr);

        HashMap<String, Integer> map = new HashMap<>();
        int count = 0;
        for (String str : arr)
            map.putIfAbsent(str, count++);

        ArrayList<String> list = trie.getAllMostFrequentElements();
        int min = Integer.MAX_VALUE;
        String res = "";
        for (String str : list) {
            if (min > map.get(str)) {
                res = str;
                min = map.get(str);
            }
        }
        return res;
    }

    // *https://leetcode.com/problems/word-search-ii/
    // ? Same as Word Search 2 (LeetCode)
    public ArrayList<String> WordBoggle1(String[] arr, char[][] board) {
        Trie trie = new Trie();
        trie.constructTrie(arr);

        return trie.wordBoggle(board);
    }

    public ArrayList<String> WordBoggle2(String[] arr, char[][] board) {
        Trie trie = new Trie();
        trie.constructTrie(arr);

        return trie.wordBoggle2(board);
    }

    public boolean WordBreak(String[] arr, String str) {
        Trie trie = new Trie();
        trie.constructTrie(arr);

        return wordBreakBruteForce(trie, str, 0);
    }

    // ? Only Recursion
    public boolean wordBreakBruteForce(Trie trie, String str, int idx) {
        if (str.length() == 0)
            return true;

        boolean res = false;
        for (int i = idx + 1; i <= str.length(); i++)
            if (trie.search(str.substring(idx, i))) // **** even for strings done in O(n),
                res = res || wordBreakBruteForce(trie, str, i);
        return res;
    }

    // *https://leetcode.com/problems/replace-words/
    public String replaceWords(String[] arr, String sentence) {

        Trie trie = new Trie();

        for (String s : arr)
            trie.insert(s);

        return trie.replaceWord(sentence.split(" "));
    }

    // *https://leetcode.com/problems/concatenated-words/
    public List<String> concatenatedWords(String[] arr) {
        List<String> res = new ArrayList<>();

        Trie trie = new Trie();
        for (String s : arr)
            trie.insert(s);

        for (String s : arr)
            if (trie.searchIfConcatenated(s))
                res.add(s);

        return res;
    }

    // *https://leetcode.com/problems/longest-word-in-dictionary/
    public String longestString(String[] arr) {

        Trie trie = new Trie();
        for (String str : arr)
            trie.insert(str);

        String res = "";
        for (String str : arr) {
            if (trie.searchIfLongest(str)) {
                if (res.equals(""))
                    res = str;
                else {
                    if (res.length() == str.length())
                        res = (res.compareTo(str) < 0) ? res : str;
                    else if (res.length() < str.length())
                        res = str;
                }
            }
        }
        return res;
    }

}
