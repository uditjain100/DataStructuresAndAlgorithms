package DynamicProgramming;

import java.util.*;

public class StringDP {

    public String code(char ch) {
        if (ch == '2')
            return "abc";
        else if (ch == '3')
            return "def";
        else if (ch == '4')
            return "ghi";
        else if (ch == '5')
            return "jkl";
        else if (ch == '6')
            return "mno";
        else if (ch == '7')
            return "pqrs";
        else if (ch == '8')
            return "tuv";
        else
            return "wxyz";
    }

    public int letterCombinations(String str, int idx) {
        if (idx == str.length())
            return 1;

        return code(str.charAt(idx)).length() * letterCombinations(str, idx + 1);
    }

    public int letterCombinationsMemorization(String str, int idx, int[] dp) {
        if (idx == str.length())
            return dp[idx] = 1;

        if (dp[idx] != -1)
            return dp[idx];

        return dp[idx] = code(str.charAt(idx)).length() * letterCombinationsMemorization(str, idx + 1, dp);
    }

    public int letterCombinationsTabulation(String str) {

        int[] dp = new int[str.length() + 1];

        for (int i = str.length(); i >= 0; i--) {
            if (i == str.length()) {
                dp[i] = 1;
                continue;
            }

            dp[i] = code(str.charAt(i)).length() * dp[i + 1];
        }

        return dp[0];
    }

    public class Trie {

        public class Node {
            Node[] children;
            boolean isWord;

            public Node() {
                children = new Node[26];
                isWord = false;
            }
        }

        Node root;

        public Trie() {
            root = new Node();
        }

        public void add(String str) {
            Node curr = root;
            for (char ch : str.toCharArray()) {
                if (curr.children[ch - 'a'] == null)
                    curr.children[ch - 'a'] = new Node();
                curr = curr.children[ch - 'a'];
            }
            curr.isWord = true;
        }

        public int insertWithCount(String str) {
            Node curr = root;
            int count = 0;
            for (char ch : str.toCharArray()) {
                if (curr.children[ch - 'a'] == null) {
                    curr.children[ch - 'a'] = new Node();
                    count++;
                }
                curr = curr.children[ch - 'a'];
            }
            curr.isWord = true;
            return count;
        }

        public boolean contains(String str) {
            Node curr = root;
            for (char ch : str.toCharArray()) {
                if (curr.children[ch - 'a'] == null)
                    return false;
                curr = curr.children[ch - 'a'];
            }
            return curr.isWord;
        }

    }

    // *https://leetcode.com/problems/word-break/
    public boolean wordBreak1(String str, List<String> list) {
        Trie trie = new Trie();
        for (String s : list)
            trie.add(s);

        return wordBreak1Memorization(str, trie, 0, new int[str.length() + 1]);
    }

    public boolean wordBreak1(String str, Trie trie, int idx) {
        if (idx == str.length())
            return true;

        boolean res = false;
        for (int i = idx + 1; i <= str.length(); i++)
            if (trie.contains(str.substring(idx, i)))
                res = res || wordBreak1(str, trie, i);
        return res;
    }

    public boolean wordBreak1Memorization(String str, Trie trie, int idx, int[] dp) {
        if (idx == str.length()) {
            dp[idx] = 1;
            return true;
        }

        if (dp[idx] != -1)
            return dp[idx] != 0;

        boolean res = false;
        for (int i = idx + 1; i <= str.length(); i++)
            if (trie.contains(str.substring(idx, i)))
                res = res || wordBreak1Memorization(str, trie, i, dp);

        dp[idx] = (res) ? 1 : 0;
        return res;
    }

    public boolean wordBreak1Tabulation(String str, List<String> list) {

        int n = str.length();
        int[] dp = new int[n + 1];

        Trie trie = new Trie();
        for (String s : list)
            trie.add(s);

        for (int idx = n; idx >= 0; idx--) {
            if (idx == str.length()) {
                dp[idx] = 1;
                continue;
            }

            boolean res = false;
            for (int i = idx + 1; i <= str.length(); i++)
                if (trie.contains(str.substring(idx, i)))
                    res = res || dp[i] != 0;

            dp[idx] = (res) ? 1 : 0;
        }

        return dp[0] != 0;
    }

    // *https://leetcode.com/problems/word-break-ii/
    public List<String> wordBreak2(String str, List<String> list) {
        Trie trie = new Trie();
        for (String s : list)
            trie.add(s);

        res = new ArrayList<>();
        wordBreak2(str, trie, 0, new ArrayList<>());
        return res;
    }

    List<String> res;

    public void wordBreak2(String str, Trie trie, int idx, List<String> ans) {
        if (idx == str.length()) {
            StringBuilder sb = new StringBuilder();
            for (String s : ans)
                sb.append(s).append(" ");
            String s = sb.toString();
            res.add(s.substring(0, s.length() - 1));
            return;
        }

        for (int i = idx + 1; i <= str.length(); i++) {
            if (trie.contains(str.substring(idx, i))) {
                ans.add(str.substring(idx, i));
                wordBreak2(str, trie, i, ans);
                ans.remove(ans.size() - 1);
            }
        }
    }

    // ? Similar Algorithms for SubArrays and SubSets of Arrays

    public boolean[][] isSubstringPalindrome(String str) {
        int n = str.length();
        boolean[][] dp = new boolean[n][n];
        for (int gap = 0; gap < n; gap++) {
            for (int i = 0, j = gap; j < n; i++, j++) {
                if (gap == 0)
                    dp[i][j] = true;
                else if (gap == 1 && str.charAt(i) == str.charAt(j))
                    dp[i][j] = true;
                else if (str.charAt(i) == str.charAt(j) && dp[i + 1][j - 1])
                    dp[i][j] = true;
            }
        }
        return dp;
    }

    public int[] longestPalindromicSubstring(String str) {
        int n = str.length();
        int[][] dp = new int[n][n];
        int maxLen = Integer.MIN_VALUE;
        int si = 0;
        int ei = 0;
        for (int gap = 0; gap < n; gap++) {
            for (int i = 0, j = gap; j < n; i++, j++) {
                if (gap == 0)
                    dp[i][j] = 1;
                else if (gap == 1 && str.charAt(i) == str.charAt(j))
                    dp[i][j] = 2;
                else if (str.charAt(i) == str.charAt(j) && dp[i + 1][j - 1] != 0)
                    dp[i][j] = gap + 1;
                if (maxLen < dp[i][j]) {
                    maxLen = dp[i][j];
                    si = i;
                    ei = j;
                }
            }
        }
        int[] res = new int[] { maxLen, si, ei };
        return res;
    }

    // ? Manchers Algorithm
    public String longestPalindromicSubstringOptimized(String s) {
        if (s.length() == 0 || s.length() == 1)
            return s;

        StringBuilder sb = new StringBuilder("");
        sb.append("@");
        for (char ch : s.toCharArray()) {
            sb.append("#");
            sb.append(ch);
        }
        sb.append("#&");

        String str = sb.toString();

        int[] dp = new int[str.length()];

        int center = 0;
        int rightMost = 0;

        for (int i = 1; i < str.length() - 1; i++) {

            int mirrorImageIdx = center - (i - center);

            int count = (i < rightMost) ? Math.min(dp[mirrorImageIdx], rightMost - i) : 0;

            while (str.charAt(i - 1 - count) == str.charAt(i + 1 + count))
                count++;

            dp[i] = count;

            if (i + count > rightMost) {
                rightMost = i + count;
                center = i;
            }
        }

        int maxLen = 0;
        for (int ele : dp)
            maxLen = Math.max(maxLen, ele);

        int idx = 0;
        for (int i = 0; i < dp.length; i++)
            if (dp[i] == maxLen)
                idx = i;

        int firstIdx = (idx - maxLen - 1) / 2;

        return s.substring(firstIdx, firstIdx + maxLen);
    }

    public int countPalindromicSubstring(String str) {
        int n = str.length();
        int[][] dp = new int[n][n];
        int count = 0;
        for (int gap = 0; gap < n; gap++) {
            for (int i = 0, j = gap; j < n; i++, j++) {
                if (gap == 0)
                    dp[i][j] = 1;
                else if (gap == 1 && str.charAt(i) == str.charAt(j))
                    dp[i][j] = 2;
                else if (str.charAt(i) == str.charAt(j) && dp[i + 1][j - 1] != 0)
                    dp[i][j] = gap + 1;
                count += (dp[i][j] != 0) ? 1 : 0;
            }
        }
        return count;
    }

    public int longestPalindromeSubsequence(String str, int si, int ei, boolean[][] isPalindrome) {
        if (isPalindrome[si][ei])
            return ei - si + 1;

        int maxLength = 0;
        if (str.charAt(si) == str.charAt(ei))
            maxLength = longestPalindromeSubsequence(str, si + 1, ei - 1, isPalindrome) + 2;
        else
            maxLength = Math.max(longestPalindromeSubsequence(str, si + 1, ei, isPalindrome),
                    longestPalindromeSubsequence(str, si, ei - 1, isPalindrome));
        return maxLength;
    }

    public int longestPalindromeSubsequenceMemorization(String str, int si, int ei, int[][] dp,
            boolean[][] isPalindrome) {

        if (isPalindrome[si][ei])
            return dp[si][ei] = ei - si + 1;

        if (dp[si][ei] != -1)
            return dp[si][ei];

        int maxLength = 0;
        if (str.charAt(si) == str.charAt(ei))
            maxLength = longestPalindromeSubsequenceMemorization(str, si + 1, ei - 1, dp, isPalindrome) + 2;
        else
            maxLength = Math.max(longestPalindromeSubsequenceMemorization(str, si + 1, ei, dp, isPalindrome),
                    longestPalindromeSubsequenceMemorization(str, si, ei - 1, dp, isPalindrome));
        return dp[si][ei] = maxLength;
    }

    public int longestPalindromeSubsequenceTabulation(String str, boolean[][] isPalindrome) {

        int[][] dp = new int[str.length()][str.length()];

        for (int gap = 0; gap < str.length(); gap++) {
            for (int si = 0, ei = gap; ei < str.length(); si++, ei++) {
                if (isPalindrome[si][ei]) {
                    dp[si][ei] = ei - si + 1;
                    continue;
                }

                int maxLength = 0;
                if (str.charAt(si) == str.charAt(ei))
                    maxLength = dp[si + 1][ei - 1] + 2;
                else
                    maxLength = Math.max(dp[si + 1][ei], dp[si][ei - 1]);
                dp[si][ei] = maxLength;
            }
        }

        return dp[0][str.length() - 1];
    }

    // *https://www.geeksforgeeks.org/minimum-insertions-to-form-a-palindrome-dp-28/
    public int minInsertionsToMakeStringPalindrome(String str) {
        return str.length() - longestPalindromeSubsequenceTabulation(str, isSubstringPalindrome(str));
    }

    public int countPalindromeSubsequence(String str, int si, int ei) {
        if (si > ei)
            return 0;
        if (si == ei)
            return 1;

        int middleString = countPalindromeSubsequence(str, si + 1, ei - 1);
        int excludeLast = countPalindromeSubsequence(str, si, ei - 1);
        int excludeFirst = countPalindromeSubsequence(str, si + 1, ei);

        int ans = excludeFirst + excludeLast;
        return ans + ((str.charAt(si) == str.charAt(ei)) ? 1 : -middleString);
    }

    public int countPalindromeSubsequenceMemorization(String str, int si, int ei, int[][] dp) {
        if (si > ei)
            return 0;
        if (si == ei)
            return 1;

        if (dp[si][ei] != -1)
            return dp[si][ei];

        int middleString = countPalindromeSubsequenceMemorization(str, si + 1, ei - 1, dp);
        int excludeLast = countPalindromeSubsequenceMemorization(str, si, ei - 1, dp);
        int excludeFirst = countPalindromeSubsequenceMemorization(str, si + 1, ei, dp);

        int ans = excludeFirst + excludeLast;
        return dp[si][ei] = ans + ((str.charAt(si) == str.charAt(ei)) ? 1 : -middleString);
    }

    public int countPalindromeSubsequenceTabulation(String str) {

        int[][] dp = new int[str.length()][str.length()];

        for (int gap = 0; gap < str.length(); gap++) {
            for (int si = 0, ei = gap; ei < str.length(); si++, ei++) {
                if (si == ei) {
                    dp[si][ei] = 1;
                    continue;
                }

                int middleString = dp[si + 1][ei - 1];
                int excludeLast = dp[si][ei - 1];
                int excludeFirst = dp[si + 1][ei];

                int ans = excludeFirst + excludeLast;
                dp[si][ei] = (str.charAt(si) == str.charAt(ei)) ? ans + 1 : ans - middleString;
            }
        }

        return dp[0][str.length() - 1];
    }

    public int maxLength = 0;

    public int longestCommonSubstring(String str1, String str2, int si, int sj) {
        if (si == str1.length() || sj == str2.length())
            return 0;

        longestCommonSubstring(str1, str2, si, sj + 1);
        longestCommonSubstring(str1, str2, si + 1, sj);

        if (str1.charAt(si) == str2.charAt(sj)) {
            int temp = 1 + longestCommonSubstring(str1, str2, si + 1, sj + 1);
            maxLength = Math.max(maxLength, temp);
            return temp;
        }
        return 0;
    }

    public int longestCommonSubstringDP(String str1, String str2, int si, int sj, int[][] dp) {
        if (si == str1.length() || sj == str2.length())
            return dp[si][sj] = 0;

        if (dp[si][sj] != -1)
            return dp[si][sj];

        longestCommonSubstringDP(str1, str2, si, sj + 1, dp);
        longestCommonSubstringDP(str1, str2, si + 1, sj, dp);

        if (str1.charAt(si) == str2.charAt(sj)) {
            int temp = 1 + longestCommonSubstringDP(str1, str2, si + 1, sj + 1, dp);
            maxLength = Math.max(maxLength, temp);
            return dp[si][sj] = temp;
        }
        return dp[si][sj] = 0;
    }

    public int longestCommonSubstringTable(String str1, String str2) {

        int maxLength = 0;
        int[][] dp = new int[str1.length() + 1][str2.length() + 1];

        for (int si = str1.length(); si >= 0; si--) {
            for (int sj = str2.length(); sj >= 0; sj--) {

                if (si == str1.length() || sj == str2.length())
                    continue;
                if (str1.charAt(si) == str2.charAt(sj)) {
                    int temp = 1 + dp[si + 1][sj + 1];
                    maxLength = Math.max(maxLength, temp);
                    dp[si][sj] = temp;
                }

            }
        }

        return dp[0][0];
    }

    public int longestCommonSubsequence(String str1, String str2, int si, int sj) {
        if (si == str1.length() || sj == str2.length())
            return 0;

        int maxLength = 0;
        if (str1.charAt(si) == str2.charAt(sj))
            maxLength += longestCommonSubsequence(str1, str2, si + 1, sj + 1) + 1;
        else
            maxLength += Math.max(longestCommonSubsequence(str1, str2, si + 1, sj),
                    longestCommonSubsequence(str1, str2, si, sj + 1));

        return maxLength;
    }

    public int longestCommonSubsequenceMemorization(String str1, String str2, int si, int sj, int[][] dp) {
        if (si == str1.length() || sj == str2.length())
            return 0;

        if (dp[si][sj] != -1)
            return dp[si][sj];

        int maxLength = 0;
        if (str1.charAt(si) == str2.charAt(sj))
            maxLength = longestCommonSubsequenceMemorization(str1, str2, si + 1, sj + 1, dp) + 1;
        else
            maxLength = Math.max(longestCommonSubsequenceMemorization(str1, str2, si + 1, sj, dp),
                    longestCommonSubsequenceMemorization(str1, str2, si, sj + 1, dp));

        return dp[si][sj] = maxLength;
    }

    public int longestCommonSubsequenceTabulation(String str1, String str2) {

        int[][] dp = new int[str1.length() + 1][str2.length() + 1];

        for (int si = str1.length(); si >= 0; si--) {
            for (int sj = str2.length(); sj >= 0; sj--) {
                if (si == str1.length() || sj == str2.length()) {
                    dp[si][sj] = 0;
                    continue;
                }
                int maxLength = 0;
                if (str1.charAt(si) == str2.charAt(sj))
                    maxLength += dp[si + 1][sj + 1] + 1;
                else
                    maxLength += Math.max(dp[si + 1][sj], dp[si][sj + 1]);
                dp[si][sj] = maxLength;
            }
        }

        return dp[0][0];
    }

    public int longestRepeatingSubsequences(String str) {

        // ? Trick : Find longest common Subsequence with the same string having one
        // ? more condition to same characters (all) that they should have different
        // ? indexes

        int[][] dp = new int[str.length() + 1][str.length() + 1];

        for (int i = str.length() - 1; i >= 0; i--) {
            for (int j = str.length() - 1; j >= 0; j--) {
                if (i != j && str.charAt(i) == str.charAt(j))
                    dp[i][j] += dp[i + 1][j + 1] + 1;
                else
                    dp[i][j] = Math.max(dp[i][j + 1], dp[i + 1][j]);
            }
        }

        return dp[0][0];
    }

    public int minDeletionsToMakeStringsIdentical(String str1, String str2) {
        int longestCommonSubsequence = longestCommonSubsequenceTabulation(str1, str2);
        return str1.length() - longestCommonSubsequence + str2.length() - longestCommonSubsequence;
    }

    // *https://leetcode.com/problems/minimum-ascii-delete-sum-for-two-strings/
    public int minDeletionsASCIIToMakeStringIdentical(String str1, String str2, int si, int sj) {
        if (si == str1.length() || sj == str2.length()) {
            int res = 0;
            int i = si;
            int j = sj;
            while (i < str1.length())
                res += str1.charAt(i++);
            while (j < str2.length())
                res += str2.charAt(j++);
            return res;
        }

        if (str1.charAt(si) == str2.charAt(sj))
            return minDeletionsASCIIToMakeStringIdentical(str1, str2, si + 1, sj + 1);
        return Math.min(str1.charAt(si) + minDeletionsASCIIToMakeStringIdentical(str1, str2, si + 1, sj),
                str2.charAt(sj) + minDeletionsASCIIToMakeStringIdentical(str1, str2, si, sj + 1));
    }

    public int minDeletionsASCIIToMakeStringIdenticalMemorization(String str1, String str2, int si, int sj,
            int[][] dp) {
        if (si == str1.length() || sj == str2.length()) {
            int res = 0;
            int i = si;
            int j = sj;
            while (i < str1.length())
                res += str1.charAt(i++);
            while (j < str2.length())
                res += str2.charAt(j++);
            return dp[si][sj] = res;
        }

        if (dp[si][sj] != -1)
            return dp[si][sj];

        if (str1.charAt(si) == str2.charAt(sj))
            return dp[si][sj] = minDeletionsASCIIToMakeStringIdenticalMemorization(str1, str2, si + 1, sj + 1, dp);
        return dp[si][sj] = Math.min(
                str1.charAt(si) + minDeletionsASCIIToMakeStringIdenticalMemorization(str1, str2, si + 1, sj, dp),
                str2.charAt(sj) + minDeletionsASCIIToMakeStringIdenticalMemorization(str1, str2, si, sj + 1, dp));
    }

    public int minDeletionsASCIIToMakeStringIdenticalTabulation(String str1, String str2) {

        int[][] dp = new int[str1.length() + 1][str2.length() + 1];

        for (int si = str1.length(); si >= 0; si--) {
            for (int sj = str2.length(); sj >= 0; sj--) {

                if (si == str1.length() || sj == str2.length()) {
                    int res = 0;
                    int i = si;
                    int j = sj;
                    while (i < str1.length())
                        res += str1.charAt(i++);
                    while (j < str2.length())
                        res += str2.charAt(j++);
                    dp[si][sj] = res;
                    continue;
                }

                if (str1.charAt(si) == str2.charAt(sj))
                    dp[si][sj] = dp[si + 1][sj + 1];
                else
                    dp[si][sj] = Math.min(str1.charAt(si) + dp[si + 1][sj], str2.charAt(sj) + dp[si][sj + 1]);
            }
        }

        return dp[0][0];
    }

    // *https://leetcode.com/problems/uncrossed-lines/
    public int uncrossedLines(int[] a, int[] b, int i, int j) {
        if (i == a.length || j == b.length)
            return 0;

        int maxLength = 0;
        if (a[i] == b[j])
            maxLength = uncrossedLines(a, b, i + 1, j + 1) + 1;
        else
            maxLength = Math.max(uncrossedLines(a, b, i + 1, j), uncrossedLines(a, b, i, j + 1));
        return maxLength;
    }

    public int uncrossedLinesMemorization(int[] a, int[] b, int i, int j, int[][] dp) {
        if (i == a.length || j == b.length)
            return dp[i][j] = 0;

        if (dp[i][j] != 0)
            return dp[i][j];

        int maxLength = 0;
        if (a[i] == b[j])
            maxLength = uncrossedLinesMemorization(a, b, i + 1, j + 1, dp) + 1;
        else
            maxLength = Math.max(uncrossedLinesMemorization(a, b, i + 1, j, dp),
                    uncrossedLinesMemorization(a, b, i, j + 1, dp));
        return dp[i][j] = maxLength;
    }

    public int uncrossedLinesTabulation(int[] a, int[] b) {

        int[][] dp = new int[a.length + 1][b.length + 1];

        for (int i = a.length; i >= 0; i--) {
            for (int j = b.length; j >= 0; j--) {
                if (i == a.length || j == b.length) {
                    dp[i][j] = 0;
                    continue;
                }

                int maxLength = 0;
                if (a[i] == b[j])
                    maxLength += dp[i + 1][j + 1] + 1;
                else
                    maxLength += Math.max(dp[i + 1][j], dp[i][j + 1]);
                dp[i][j] = maxLength;
            }
        }

        return dp[0][0];
    }

    // *https://leetcode.com/problems/max-dot-product-of-two-subsequences/
    public int maxDotProduct(int[] a, int[] b, int i, int j) {
        if (i == a.length || j == b.length)
            return -10 ^ 7;

        int val = a[i] * b[j];
        int ba = maxDotProduct(a, b, i + 1, j + 1) + val;
        int fa = maxDotProduct(a, b, i + 1, j);
        int sa = maxDotProduct(a, b, i, j + 1);

        return Math.max(Math.max(val, ba), Math.max(fa, sa));
    }

    public int maxDotProductMemorization(int[] a, int[] b, int i, int j, int[][] dp) {

        if (i == a.length || j == b.length)
            return dp[i][j] = -10 ^ 7;

        if (dp[i][j] != -1)
            return dp[i][j];

        int val = a[i] * b[j];
        int ba = maxDotProductMemorization(a, b, i + 1, j + 1, dp) + val;
        int fa = maxDotProductMemorization(a, b, i + 1, j, dp);
        int sa = maxDotProductMemorization(a, b, i, j + 1, dp);

        return dp[i][j] = Math.max(Math.max(val, ba), Math.max(fa, sa));
    }

    public int maxDotProductTabulation(int[] a, int[] b) {

        int[][] dp = new int[a.length + 1][b.length + 1];
        for (int i = a.length; i >= 0; i--) {
            for (int j = b.length; j >= 0; j--) {

                if (i == a.length || j == b.length) {
                    dp[i][j] = -10 ^ 7;
                    continue;
                }

                int val = a[i] * b[j];
                int ba = dp[i + 1][j + 1] + val;
                int fa = dp[i][j + 1];
                int sa = dp[i + 1][j];

                dp[i][j] = Math.max(Math.max(val, ba), Math.max(fa, sa));
            }
        }

        return dp[0][0];
    }

    // *https://leetcode.com/problems/edit-distance/
    public int editDistance(String str1, String str2, int ei, int ej) {
        if (ei == 0 || ej == 0)
            return (ei == 0) ? ej : ei;

        if (str1.charAt(ei - 1) == str2.charAt(ej - 1))
            return editDistance(str1, str2, ei - 1, ej - 1);

        int insert = editDistance(str1, str2, ei, ej - 1);
        int replace = editDistance(str1, str2, ei - 1, ej - 1);
        int delete = editDistance(str1, str2, ei - 1, ej);

        return Math.min(Math.min(insert, replace), delete) + 1;
    }

    public int editDistanceMemorization(String str1, String str2, int ei, int ej, int[][] dp) {
        if (ei == 0 || ej == 0)
            return dp[ei][ej] = (ei == 0) ? ej : ei;

        if (dp[ei][ej] != -1)
            return dp[ei][ej];

        if (str1.charAt(ei - 1) == str2.charAt(ej - 1))
            return dp[ei][ej] = editDistanceMemorization(str1, str2, ei - 1, ej - 1, dp);

        int insert = editDistanceMemorization(str1, str2, ei, ej - 1, dp);
        int replace = editDistanceMemorization(str1, str2, ei - 1, ej - 1, dp);
        int delete = editDistanceMemorization(str1, str2, ei - 1, ej, dp);

        return dp[ei][ej] = Math.min(Math.min(insert, replace), delete) + 1;
    }

    public int editDistanceTabulation(String str1, String str2) {

        int[][] dp = new int[str1.length() + 1][str2.length() + 1];
        for (int ei = 0; ei <= str1.length(); ei++) {
            for (int ej = 0; ej <= str2.length(); ej++) {
                if (ei == 0)
                    dp[ei][ej] = ej;
                else if (ej == 0)
                    dp[ei][ej] = ei;
                else if (str1.charAt(ei - 1) == str2.charAt(ej - 1))
                    dp[ei][ej] = dp[ei - 1][ej - 1];
                else
                    dp[ei][ej] = Math.min(Math.min(dp[ei][ej - 1], dp[ei - 1][ej - 1]), dp[ei - 1][ej]) + 1;
            }
        }

        return dp[str1.length()][str2.length()];
    }

    // *https://leetcode.com/problems/target-sum/submissions/
    public int targetSum(int[] arr, int target) {

        int totalSum = 0;
        for (int ele : arr)
            totalSum += ele;

        if (target > totalSum)
            return 0;

        int[][] dp = new int[arr.length + 1][2 * totalSum + 1];
        for (int i = 0; i < dp.length; i++)
            Arrays.fill(dp[i], -1);
        return targetSum(arr, totalSum + target, totalSum, 0, dp);
    }

    public int targetSum(int[] arr, int target, int currSum, int idx, int[][] dp) {
        if (idx == arr.length)
            return (currSum == target) ? 1 : 0;

        if (dp[idx][currSum] != -1)
            return dp[idx][currSum];

        int include = targetSum(arr, target, currSum + arr[idx], idx + 1, dp);
        int exclude = targetSum(arr, target, currSum - arr[idx], idx + 1, dp);

        return dp[idx][currSum] = include + exclude;
    }

    public int countDistinctSubStrings(String str) {
        Trie trie = new Trie();

        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = i; j < str.length(); j++) {
                sb.append(str.charAt(j));
                count += trie.insertWithCount(sb.toString());
            }
        }

        return count;
    }

    public long countDistinctSubsequences(String str) {
        long[] dp = new long[str.length()];
        dp[0] = 1;

        int[] lastIndex = new int[26];
        Arrays.fill(lastIndex, -1);

        for (int i = 0; i < str.length(); i++) {
            int ch = str.charAt(i) - 'a';
            dp[i + 1] = dp[i] * 2;

            if (lastIndex[ch] != -1)
                dp[i + 1] -= dp[lastIndex[ch] - 1];

            lastIndex[ch] = i + 1;
        }

        return dp[str.length()] - 1; // ? Removing Empty One
    }

    // Todo : KMP Algorithm
    public int countDistinctPalindromicSubStrings(String str) {
        // boolean[][] isSubstringPalindrome = isSubstringPalindrome(str);
        return 0;
    }

    public long countDistinctPalindromicSubsequences(String str) {

        int n = str.length();
        int mod = 1000000007;
        HashMap<Character, Integer> map = new HashMap<>();

        int[] prev = new int[n];
        for (int i = 0; i < n; i++) {
            char ch = str.charAt(i);
            prev[i] = (!map.containsKey(ch)) ? -1 : map.get(ch);
            map.put(ch, i);
        }

        map.clear();

        int[] next = new int[n];
        for (int i = n - 1; i >= 0; i--) {
            char ch = str.charAt(i);
            next[i] = (!map.containsKey(ch)) ? -1 : map.get(ch);
            map.put(ch, i);
        }

        long[][] dp = new long[n][n];
        for (int gap = 0; gap < n; gap++) {
            for (int i = 0, j = gap; j < n; i++, j++) {
                if (gap == 0)
                    dp[i][j] = 1;
                else if (gap == 1 && str.charAt(i) == str.charAt(j))
                    dp[i][j] = 2;
                else {
                    if (str.charAt(i) != str.charAt(j))
                        dp[i][j] = (dp[i + 1][j] + dp[i][j - 1] - dp[i + 1][j - 1]) % mod;
                    else {
                        int nx = next[i];
                        int pr = prev[j];

                        if (nx > pr)
                            dp[i][j] = (2 * dp[i + 1][j - 1] + 2) % mod;
                        else if (nx == pr)
                            dp[i][j] = (2 * dp[i + 1][j - 1] + 1) % mod;
                        else
                            dp[i][j] = (2 * dp[i + 1][j - 1] - dp[nx + 1][pr - 1]) % mod;
                    }
                }
            }
        }
        if (dp[0][n - 1] < 0)
            dp[0][n - 1] += mod;
        return ((int) dp[0][n - 1] % mod);
    }

    public int countDistinctCommonSubsequences(String str, String target, int si, int ti) {
        if (si < ti)
            return 0;
        if (ti == 0)
            return 1;

        if (str.charAt(si - 1) == target.charAt(ti - 1))
            return countDistinctCommonSubsequences(str, target, si - 1, ti - 1)
                    + countDistinctCommonSubsequences(str, target, si - 1, ti);
        return countDistinctCommonSubsequences(str, target, si - 1, ti);
    }

    public int countDistinctCommonSubsequencesMemorization(String str, String target, int si, int ti, int[][] dp) {
        if (si < ti)
            return 0;
        if (ti == 0)
            return dp[si][ti] = 1;
        if (dp[si][ti] != 0)
            return dp[si][ti];

        if (str.charAt(si - 1) == target.charAt(ti - 1))
            return dp[si][ti] = countDistinctCommonSubsequencesMemorization(str, target, si - 1, ti - 1, dp)
                    + countDistinctCommonSubsequencesMemorization(str, target, si - 1, ti, dp);

        return dp[si][ti] = countDistinctCommonSubsequencesMemorization(str, target, si - 1, ti, dp);
    }

    public int countDistinctCommonSubsequencesTabulation(String str, String target) {
        int[][] dp = new int[str.length() + 1][target.length() + 1];
        for (int si = 0; si <= str.length(); si++) {
            for (int ti = 0; ti <= target.length(); ti++) {
                if (si < ti) {
                    dp[si][ti] = 0;
                    continue;
                }
                if (ti == 0) {
                    dp[si][ti] = 1;
                    continue;
                }

                if (str.charAt(si - 1) == target.charAt(ti - 1))
                    dp[si][ti] = dp[si - 1][ti - 1] + dp[si - 1][ti];
                else
                    dp[si][ti] = dp[si - 1][ti];
            }
        }
        return dp[str.length()][target.length()];
    }

    // *https://gammap.geeksforgeeks.org/problems/distinct-transformations/0/?category[]=Dynamic%20Programming&page=1&query=category[]Dynamic%20Programmingpage1
    public int countDistinctTransformations(String str, String target, int si, int ti) {
        if (ti == target.length())
            return 1;
        if (si == str.length())
            return 0;

        if (str.charAt(si) != str.charAt(ti))
            return countDistinctTransformations(str, target, si + 1, ti);
        return countDistinctTransformations(str, target, si + 1, ti + 1)
                + countDistinctTransformations(str, target, si + 1, ti);
    }

    public int countDistinctTransformationsMemorization(String str, String target, int si, int ti) {
        if (ti == target.length())
            return 1;
        if (si == str.length())
            return 0;

        if (str.charAt(si) != str.charAt(ti))
            return countDistinctTransformationsMemorization(str, target, si + 1, ti);
        return countDistinctTransformationsMemorization(str, target, si + 1, ti + 1)
                + countDistinctTransformationsMemorization(str, target, si + 1, ti);
    }

    public int countDistinctTransformations(String str, String target) {

        int[][] dp = new int[str.length() + 1][target.length() + 1];

        for (int i = str.length(); i >= 0; i--) {
            for (int j = target.length(); j >= 0; j--) {
                if (i == str.length())
                    dp[i][j] = 1;
                else if (j == target.length())
                    dp[i][j] = 0;
                else {
                    if (str.charAt(i) == target.charAt(j))
                        dp[i][j] = dp[i + 1][j + 1] + dp[i + 1][j];
                    else
                        dp[i][j] = dp[i + 1][j];
                }

            }
        }

        return dp[0][0];
    }

    // ? We can apply memorization in this but 4D dp is used
    public boolean isScramble(String str1, String str2, int si1, int ei1, int si2, int ei2, Boolean[][][][] dp) {
        if (str1.substring(si1, ei1 + 1).equals(str2.substring(si2, ei2 + 1)))
            return dp[si1][ei1][si2][ei2] = true;

        if (dp[si1][ei1][si2][ei2] != null)
            return dp[si1][ei1][si2][ei2];

        boolean res = false;
        for (int i = 0; i < ei1 - si1; i++) {

            boolean isLeftLeftScramble = isScramble(str1, str2, si1, si1 + i, si2, si2 + i, dp);
            boolean isRightRightScramble = isScramble(str1, str2, si1 + i + 1, ei1, si2 + i + 1, ei2, dp);
            boolean isLeftRightScramble = isScramble(str1, str2, si1, si1 + i, ei2 - i, ei2, dp);
            boolean isRightLeftScramble = isScramble(str1, str2, si1 + i + 1, ei1, si2, ei2 - i - 1, dp);

            res = res || ((isLeftLeftScramble && isRightRightScramble) || (isLeftRightScramble && isRightLeftScramble));
        }

        return dp[si1][ei1][si2][ei2] = res;
    }

    // *https://leetcode.com/problems/scramble-string/
    public boolean isScrambleOptimized(String str1, String str2, int si1, int si2, int length) {
        if (str1.substring(si1, si1 + length).equals(str2.substring(si2, si2 + length)))
            return true;

        boolean res = false;
        for (int i = 1; i < length; i++) {

            boolean isLeftLeftScramble = isScrambleOptimized(str1, str2, si1, si2, i);
            boolean isRightRightScramble = isScrambleOptimized(str1, str2, si1 + i, si2 + i, length - i);
            boolean isLeftRightScramble = isScrambleOptimized(str1, str2, si1, si2 + length - i, i);
            boolean isRightLeftScramble = isScrambleOptimized(str1, str2, si1 + i, si2, length - i);

            res = res || ((isLeftLeftScramble && isRightRightScramble) || (isLeftRightScramble && isRightLeftScramble));
        }

        return res;
    }

    public boolean isScrambleOptimizedMemorization(String str1, String str2, int si1, int si2, int length,
            Boolean[][][] dp) {
        if (str1.substring(si1, si1 + length).equals(str2.substring(si2, si2 + length)))
            return dp[si1][si2][length] = true;

        if (dp[si1][si2][length] != null)
            return dp[si1][si2][length];

        boolean res = false;
        for (int i = 1; i < length; i++) {

            boolean isLeftLeftScramble = isScrambleOptimizedMemorization(str1, str2, si1, si2, i, dp);
            boolean isRightRightScramble = isScrambleOptimizedMemorization(str1, str2, si1 + i, si2 + i, length - i,
                    dp);
            boolean isLeftRightScramble = isScrambleOptimizedMemorization(str1, str2, si1, si2 + length - i, i, dp);
            boolean isRightLeftScramble = isScrambleOptimizedMemorization(str1, str2, si1 + i, si2, length - i, dp);

            res = res || ((isLeftLeftScramble && isRightRightScramble) || (isLeftRightScramble && isRightLeftScramble));
        }

        return dp[si1][si2][length] = res;
    }

}
