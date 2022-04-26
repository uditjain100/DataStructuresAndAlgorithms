package Trie;

import java.util.*;

public class intro_Trie {

    // **** Standard Trie with delete
    public static class Trie2 {

        public class Node {

            Node[] children;
            PriorityQueue<String> pq;
            String word;
            boolean isWord;
            int countOfPrefix;

            public Node() {
                this.children = new Node[26];
                this.pq = new PriorityQueue<>((a, b) -> {
                    return a.compareTo(b);
                });
                this.word = "";
                this.isWord = false;
                this.countOfPrefix = 0;
            }

        }

        private Node root;

        public Trie2() {
            this.root = new Node();
        }

        public boolean isNodeEmpty(Node node) {
            for (int i = 0; i < 26; i++)
                if (node.children[i] != null)
                    return false;
            return true;
        }

        public void constructTrie(String[] arr) {
            for (String str : arr) {
                Node curr = this.root;
                for (char ch : str.toCharArray()) {
                    if (curr.children[ch - 'a'] == null)
                        curr.children[ch - 'a'] = new Node();
                    curr.pq.add(str);
                    curr.countOfPrefix++;
                    curr = curr.children[ch - 'a'];
                }
                curr.word = str;
                curr.isWord = true;
            }
        }

        public void insert(String str) {
            Node curr = this.root;
            for (char ch : str.toCharArray()) {
                if (curr.children[ch - 'a'] == null)
                    curr.children[ch - 'a'] = new Node();
                curr.pq.add(str);
                curr.countOfPrefix++;
                curr = curr.children[ch - 'a'];
            }
            curr.word = str;
            curr.isWord = true;
        }

        public boolean search(String str) {
            if (str.contains("."))
                return searchLikeGoogle(str, this.root, 0);

            Node curr = this.root;
            for (char ch : str.toCharArray()) {
                if (curr.children[ch - 'a'] == null)
                    return false;
                curr = curr.children[ch - 'a'];
            }
            return curr.isWord;
        }

        public boolean searchLikeGoogle(String str, Node curr, int idx) {

            if (idx == str.length())
                return curr.isWord;

            char ch = str.charAt(idx);

            boolean res = false;
            if (ch == '.') {
                for (int i = 0; i < 26; i++) {
                    if (curr.children[i] != null)
                        res = res || searchLikeGoogle(str, curr.children[i], idx + 1);
                }
            } else {
                if (curr.children[ch - 'a'] == null)
                    return false;
                res = res || searchLikeGoogle(str, curr.children[ch - 'a'], idx + 1);
            }
            return res;
        }

        public boolean prefixSearch(String str) {
            Node curr = this.root;
            for (char ch : str.toCharArray()) {
                if (curr.children[ch - 'a'] == null)
                    return false;
                curr = curr.children[ch - 'a'];
            }
            return true;
        }

        public int prefixSearchUptoK(String str, int k) {
            if (k >= str.length())
                return -1;
            Node curr = this.root;
            int i = 0;
            for (char ch : str.toCharArray()) {
                if (i == k)
                    break;
                if (curr.children[ch - 'a'] == null)
                    return -1;
                curr = curr.children[ch - 'a'];
                i++;
            }
            return curr.countOfPrefix;
        }

        public String getShortestUniquePrefix(String str) {
            StringBuilder sb = new StringBuilder();

            Node curr = this.root;
            for (char ch : str.toCharArray()) {
                sb.append(ch);
                if (curr.countOfPrefix == 1)
                    break;
                if (curr.children[ch - 'a'] == null)
                    return "";
                curr = curr.children[ch - 'a'];
            }
            return sb.toString();
        }

        public void delete(String str) {
            if (this.root == null)
                return;
            this.root = delete(this.root, str, 0);
        }

        private Node delete(Node curr, String str, int idx) {
            if (idx == str.length()) {
                if (curr.isWord)
                    curr.isWord = false;

                curr.pq.remove(str);
                curr.countOfPrefix--;

                if (isNodeEmpty(curr))
                    curr = null;

                return curr;
            }

            char ch = str.charAt(idx);
            curr.children[ch - 'a'] = delete(curr.children[ch - 'a'], str, idx + 1);

            curr.pq.remove(str);
            curr.countOfPrefix--;

            if (isNodeEmpty(curr) && !curr.isWord)
                curr = null;
            return curr;
        }

        public void preOrder(Node curr) {
            if (curr == null)
                return;

            if (curr.isWord)
                System.out.println(curr.word);

            for (Node child : curr.children)
                preOrder(child);
        }

        public void postOrder(Node curr) {
            if (curr == null)
                return;

            for (Node child : curr.children)
                postOrder(child);

            if (curr.isWord)
                System.out.println(curr.word);
        }

        // *https://www.geeksforgeeks.org/palindrome-pair-in-an-array-of-words-or-strings/
        public boolean checkPalindromePair(String str) {
            String reverseString = new StringBuilder(str).reverse().toString();
            if (customSearch(this.root, reverseString))
                return true;
            for (int i = 0; i < 26; i++) {
                if (customSearch(this.root.children[i], reverseString))
                    return true;
            }
            if (customSearch(this.root, reverseString.substring(0, str.length() - 1)))
                return true;
            return false;
        }

        private boolean customSearch(Node curr, String str) {

            for (char ch : str.toCharArray()) {
                if (curr.children[ch - 'a'] == null)
                    return false;
                curr = curr.children[ch - 'a'];
            }
            return curr.isWord;
        }

        // *https://www.geeksforgeeks.org/implement-a-phone-directory/
        public ArrayList<String> phoneSearch(String str) {

            ArrayList<String> res = new ArrayList<>();

            Node curr = this.root;
            int i = 0;
            for (char ch : str.toCharArray()) {
                if (curr.children[ch - 'a'] == null)
                    break;
                else
                    res.addAll(curr.pq);
                curr = curr.children[ch - 'a'];
                i++;
            }
            for (int idx = i; idx < str.length(); idx++)
                res.add("0");
            return res;
        }

        public ArrayList<String> getAllMostFrequentElements() {

            list.clear();

            getMaxCount(this.root);
            getMostFrequent(this.root);
            return list;
        }

        private int maxCount = 0;
        private ArrayList<String> list = new ArrayList<>();

        private void getMaxCount(Node curr) {
            if (curr == null)
                return;

            if (curr.isWord)
                maxCount = Math.max(maxCount, curr.pq.size());

            for (Node child : curr.children)
                getMaxCount(child);
        }

        private void getMostFrequent(Node curr) {
            if (curr == null)
                return;

            if (curr.isWord && maxCount == curr.pq.size())
                list.add(curr.word);

            for (Node child : curr.children)
                getMostFrequent(child);
        }

        public ArrayList<String> wordBoggle(char[][] board) {

            int n = board.length;
            int m = board[0].length;

            list.clear();

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    if (this.root.children[board[i][j] - 'a'] != null)
                        dfs8Dir(this.root, board, i, j, new boolean[n][m]);
                }
            }

            return list;

        }

        int[][] eightDir = { { 0, 1 }, { 1, 0 }, { -1, 0 }, { 0, -1 }, { 1, 1 }, { -1, 1 }, { -1, -1 }, { 1, -1 } };

        public void dfs8Dir(Node curr, char[][] board, int i, int j, boolean[][] visited) {

            if (curr.isWord)
                list.add(curr.word);

            visited[i][j] = true;

            for (int d = 0; d < eightDir.length; d++) {

                int x = i + eightDir[d][0];
                int y = j + eightDir[d][1];

                if (x >= 0 && y >= 0 && x < board.length && y < board[0].length && !visited[x][y]
                        && curr.children[board[x][y] - 'a'] != null) {
                    dfs8Dir(curr.children[board[x][y] - 'a'], board, x, y, visited);
                }
            }

            visited[i][j] = false;
        }

        public ArrayList<String> wordBoggle2(char[][] board) {

            int n = board.length;
            int m = board[0].length;

            list.clear();

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    if (this.root.children[board[i][j] - 'a'] != null)
                        dfs8Dir2(this.root, board, i, j, new boolean[n][m]);
                }
            }

            return list;

        }

        public void dfs8Dir2(Node curr, char[][] board, int i, int j, boolean[][] visited) {

            if (curr.isWord) {
                list.add(curr.word);
                delete(curr.word); // **** curr.isWord = false;
            }

            visited[i][j] = true;

            for (int d = 0; d < eightDir.length; d++) {

                int x = i + eightDir[d][0];
                int y = j + eightDir[d][1];

                if (x >= 0 && y >= 0 && x < board.length && y < board[0].length && !visited[x][y]
                        && curr.children[board[x][y] - 'a'] != null) {
                    dfs8Dir2(curr.children[board[x][y] - 'a'], board, x, y, visited);
                }
            }

            visited[i][j] = false;
        }

    }

    // **** for binary numeric values
    public static class Trie3 {
        public class Node {
            Node[] bits;
            int count;

            public Node() {
                this.bits = new Node[2];
                this.count = 0;
            }
        }

        private Node root;

        public Trie3() {
            this.root = new Node();
        }

        public void constructTrie(int[][] arr) {
            for (int[] a : arr) {
                Node curr = root;
                for (int idx : a) {
                    if (curr.bits[idx] == null)
                        curr.bits[idx] = new Node();
                    curr = curr.bits[idx];
                }
                curr.count++;
            }
        }

        public void insert(int[] arr) {
            Node curr = root;
            for (int idx : arr) {
                if (curr.bits[idx] == null)
                    curr.bits[idx] = new Node();
                curr = curr.bits[idx];
            }
            curr.count++;
        }

        public int search(int[] arr) {
            Node curr = root;
            for (int idx : arr) {
                if (curr.bits[idx] == null)
                    return -1;
                curr = curr.bits[idx];
            }
            return curr.count;
        }

    }

    public static class Trie4 {

        public class Node {

            Node[] children;
            boolean isWord;
            String word;

            public Node() {
                this.children = new Node[26];
                this.isWord = false;
                this.word = "";
            }

        }

        private Node root;

        public Trie4() {
            this.root = new Node();
        }

        public void insert(String str) {
            Node curr = this.root;
            for (char ch : str.toCharArray()) {
                if (curr.children[ch - 'a'] == null)
                    curr.children[ch - 'a'] = new Node();
                curr = curr.children[ch - 'a'];
            }
            curr.isWord = true;
            curr.word = str;
        }

        public boolean searchIfConcatenated(String str) {
            return searchConcatenatedWords(str, 0, 0);
        }

        private boolean searchConcatenatedWords(String str, int idx, int subStringNumber) {
            if (idx == str.length())
                return subStringNumber > 1;

            Node curr = this.root;
            boolean res = false;
            for (int i = idx; i < str.length(); i++) {
                char ch = str.charAt(i);
                if (curr.children[ch - 'a'] == null)
                    return false;
                if (curr.children[ch - 'a'].isWord)
                    res = res || searchConcatenatedWords(str, i + 1, subStringNumber + 1);
                if (res)
                    return res;
                curr = curr.children[ch - 'a'];
            }
            return res;
        }

        public boolean searchIfLongest(String str) {
            Node curr = this.root;
            for (char ch : str.toCharArray()) {
                if (curr.children[ch - 'a'] == null || !curr.children[ch - 'a'].isWord)
                    return false;
                curr = curr.children[ch - 'a'];
            }
            return curr.isWord;
        }

        public String searchPrefix(String str) {
            Node curr = this.root;
            for (char ch : str.toCharArray()) {
                if (curr.isWord)
                    return curr.word;
                if (curr.children[ch - 'a'] == null)
                    return (curr.isWord) ? curr.word : str;
                curr = curr.children[ch - 'a'];
            }
            return (curr.isWord) ? curr.word : str;

        }

        public String replaceWord(String[] arr) {
            StringBuilder sb = new StringBuilder();
            for (String str : arr)
                sb.append(searchPrefix(str)).append(" ");
            return sb.toString();
        }

    }

    // *https://leetcode.com/problems/map-sum-pairs/
    class MapSum {

        public class Node {

            Node[] children;
            int valueSum;

            public Node() {
                this.children = new Node[26];
                this.valueSum = 0;
            }

        }

        private Node root;
        HashMap<String, Integer> map;

        public MapSum() {
            this.root = new Node();
            this.map = new HashMap<>();
        }

        public void insert(String str, int value) {
            int oldValue = (map.containsKey(str)) ? map.get(str) : 0;
            map.put(str, value);

            Node curr = this.root;
            for (char ch : str.toCharArray()) {
                if (curr.children[ch - 'a'] == null)
                    curr.children[ch - 'a'] = new Node();
                curr.valueSum += (value - oldValue);
                curr = curr.children[ch - 'a'];
            }
            curr.valueSum += (value - oldValue);
        }

        public int sum(String str) {
            Node curr = this.root;
            for (char ch : str.toCharArray()) {
                if (curr.children[ch - 'a'] == null)
                    return 0;
                curr = curr.children[ch - 'a'];
            }
            return curr.valueSum;
        }
    }

    // *https://leetcode.com/problems/stream-of-characters/
    class StreamChecker {

        public class Node {

            Node[] children;
            boolean isWord;
            String word;

            public Node() {
                this.children = new Node[26];
                this.isWord = false;
                this.word = "";
            }

        }

        private Node root;
        StringBuilder sb;

        public void insert(String[] arr) {
            for (String str : arr) {
                Node curr = root;
                for (int i = str.length() - 1; i >= 0; i--) {
                    char ch = str.charAt(i);
                    if (curr.children[ch - 'a'] == null)
                        curr.children[ch - 'a'] = new Node();
                    curr = curr.children[ch - 'a'];
                }
                curr.isWord = true;
            }
        }

        public boolean search(String str) {
            Node curr = root;
            for (int i = str.length() - 1; i >= 0; i--) {
                char ch = str.charAt(i);
                if (curr.children[ch - 'a'] == null)
                    return false;
                if (curr.children[ch - 'a'].isWord)
                    return true;
                curr = curr.children[ch - 'a'];
            }
            return curr.isWord;
        }

        public StreamChecker(String[] words) {
            this.root = new Node();

            insert(words);

            this.sb = new StringBuilder();
        }

        public boolean query(char letter) {
            return search(sb.append(letter).toString());
        }
    }

}
