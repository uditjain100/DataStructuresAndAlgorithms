package Trie;

import java.util.*;

import Trie.intro_Trie.*;

public class methods {

    // ***** Given a binary matrix, print all unique rows of the given matrix.
    // ***** Input:
    // ***** {0, 1, 0, 0, 1}
    // ***** {1, 0, 1, 1, 0}
    // ***** {0, 1, 0, 0, 1}
    // ***** {1, 1, 1, 0, 0}
    // **** Output:
    // **** 0 1 0 0 1
    // **** 1 0 1 1 0
    // **** 1 1 1 0 0

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

    // *** Given an array of strings arr[] of size n and given s a string str and an
    // *** integer k. The task is to find the count of strings in arr[] whose prefix
    // *** of length k matches with the k length prefix of str.
    public int prefixMatch(String[] arr, String str, int k) {
        Trie2 trie = new Trie2();
        trie.constructTrie(arr);

        return trie.prefixSearchUptoK(str, k);
    }

    // *** Given an array of words, find all shortest unique prefixes to represent
    // *** each word in the given array. Assume that no word is prefix of another.
    public String[] shortestUniquePrefix(String[] arr) {

        String[] res = new String[arr.length];

        Trie2 trie = new Trie2();
        trie.constructTrie(arr);

        int i = 0;
        for (String str : arr)
            res[i++] = trie.getShortestUniquePrefix(str);

        return res;
    }

    // *** Given an array of strings arr[] of size N, find if there exists 2 strings
    // *** arr[i] and arr[j] such that arr[i]+arr[j] is a palindrome i.e the
    // *** concatenation of string arr[i] and arr[j] results into a palindrome.
    public boolean palindromicPairs(String[] arr) {

        Trie2 trie = new Trie2();
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
        Trie2 trie = new Trie2();
        trie.constructTrie(arr);
        return trie.phoneSearch(str);
    }

    // **** https://www.geeksforgeeks.org/frequent-word-array-strings/
    public String mostFrequentWord(String[] arr) {
        Trie2 trie = new Trie2();
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
        Trie2 trie = new Trie2();
        trie.constructTrie(arr);

        return trie.wordBoggle(board);
    }

    public ArrayList<String> WordBoggle2(String[] arr, char[][] board) {
        Trie2 trie = new Trie2();
        trie.constructTrie(arr);

        return trie.wordBoggle2(board);
    }

    public boolean WordBreak(String[] arr, String str) {
        Trie2 trie = new Trie2();
        trie.constructTrie(arr);

        return wordBreakBruteForce(trie, str, 0);
    }

    // ? Only Recursion
    public boolean wordBreakBruteForce(Trie2 trie, String str, int idx) {
        if (str.length() == 0)
            return true;

        boolean res = false;
        for (int i = idx + 1; i <= str.length(); i++)
            if (trie.search(str.substring(idx, i))) // **** even for strings done in O(n),
                res = res || wordBreakBruteForce(trie, str, i);
        return res;
    }

    public boolean wordSearch1(char[][] board, String str) {

        int n = board.length;
        int m = board[0].length;

        boolean res = false;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                if (str.charAt(0) == board[i][j])
                    res = res || dfs4Dir(str, 0, board, i, j, new boolean[n][m]);

        return res;
    }

    int[][] dir = { { 0, 1 }, { 1, 0 }, { -1, 0 }, { 0, -1 } };

    public boolean dfs4Dir(String str, int idx, char[][] board, int i, int j, boolean[][] visited) {

        if (idx == str.length() - 1)
            return true;

        visited[i][j] = true;

        boolean res = false;
        for (int d = 0; d < dir.length; d++) {

            int x = i + dir[d][0];
            int y = j + dir[d][1];

            if (x >= 0 && y >= 0 && x < board.length && y < board[0].length && !visited[x][y]
                    && str.charAt(idx + 1) == board[x][y])
                res = res || dfs4Dir(str, idx + 1, board, x, y, visited);
        }

        visited[i][j] = false;
        return res;
    }

    // *https://leetcode.com/problems/replace-words/
    public String replaceWords(String[] arr, String sentence) {

        Trie4 trie = new Trie4();

        for (String s : arr)
            trie.insert(s);

        return trie.replaceWord(sentence.split(" "));
    }

    // *https://leetcode.com/problems/concatenated-words/
    public List<String> concatenatedWords(String[] arr) {
        List<String> res = new ArrayList<>();

        Trie4 trie = new Trie4();
        for (String s : arr)
            trie.insert(s);

        for (String s : arr)
            if (trie.searchIfConcatenated(s))
                res.add(s);

        return res;
    }

    // *https://leetcode.com/problems/longest-word-in-dictionary/
    public String longestString(String[] arr) {

        Trie4 trie = new Trie4();
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
