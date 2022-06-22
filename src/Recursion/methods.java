package Recursion;

import java.util.*;

public class methods {

    List<String> list;

    public List<String> lexicographicalCounting(int num) {
        list = new ArrayList<>();
        list.add("0");

        lexicographicalCounting(1, num);

        return list;
    }

    public void lexicographicalCounting(int curr, int end) {
        if (curr > end)
            return;

        list.add("" + curr);

        for (int i = 0; i <= 9; i++)
            lexicographicalCounting(curr * 10 + i, end);
    }

    public int[][] dir = { { 1, 2 }, { -1, 2 }, { 1, -2 }, { -1, -2 }, { 2, 1 }, { 2, -1 }, { -2, -1 },
            { -2, 1 } };

    public boolean knightTour(int si, int sj, int n, int m) {
        return knightTour(new boolean[n][n], si, sj, n, m, 0);
    }

    private boolean knightTour(boolean[][] vis, int i, int j, int n, int m, int numberOfVisitedBoxes) {
        if (numberOfVisitedBoxes == n * m)
            return true;

        vis[i][j] = true;

        boolean res = false;
        for (int d = 0; d < dir.length; d++) {
            int x = dir[d][0] + i;
            int y = dir[d][1] + j;
            if (x >= 0 && y >= 0 && x < n && y < m && !vis[x][y])
                res = res || knightTour(vis, x, y, n, m, numberOfVisitedBoxes + 1);
        }
        return res;
    }

    public int wordBreak(List<String> list, String str) {
        return wordBreak(list, str, 0, "");
    }

    public int wordBreak(List<String> list, String str, int idx, String ans) {
        if (str.length() == 0)
            return 1;

        int count = 0;
        for (int i = idx + 1; i <= str.length(); i++)
            if (list.contains(str.substring(idx, i))) // **** even for strings done in O(n),
                count += wordBreak(list, str, i, ans + str.substring(idx, i) + " ");
        return count;
    }

    public int stringToNumber(String str) {

        int res = 0;
        for (char ch : str.toCharArray()) {
            res *= 10;
            res += mapping[ch - 'a'];
        }
        return res;
    }

    public int numUsed;
    public int[] mapping;

    public String findUniqueCharacter(String str1, String str2, String str3) {
        HashSet<Character> set = new HashSet<>();
        for (char ch : str1.toCharArray())
            set.add(ch);
        for (char ch : str2.toCharArray())
            set.add(ch);
        for (char ch : str3.toCharArray())
            set.add(ch);

        StringBuilder sb = new StringBuilder();
        for (char ch : set)
            sb.append(ch);
        return sb.toString();
    }

    public int cryptArithmeticPuzzle(String s1, String s2, String s3) {
        return cryptArithmeticPuzzle(s1, s2, s3, findUniqueCharacter(s1, s2, s3), 0);

    }

    private int cryptArithmeticPuzzle(String s1, String s2, String s3, String str, int idx) {

        if (idx == str.length()) {

            int n1 = stringToNumber(s1);
            int n2 = stringToNumber(s2);
            int n3 = stringToNumber(s3);

            if (n1 + n2 == n3 && mapping[s1.charAt(0) - 'a'] != 0 && mapping[s2.charAt(0) - 'a'] != 0
                    && mapping[s3.charAt(0) - 'a'] != 0) {

                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < 26; i++)
                    if (mapping[i] != 0)
                        sb.append((char) (i + 'a') + mapping[i]);
                list.add(sb.toString());

                return 1;
            }
            return 0;
        }

        char ch = str.charAt(idx);

        int count = 0;
        for (int num = 0; num < 10; num++) {
            int mask = (1 << num);
            if ((numUsed & mask) == 0) {
                numUsed ^= mask;
                mapping[ch - 'a'] = num;

                count += cryptArithmeticPuzzle(s1, s2, s3, str, idx + 1);

                numUsed ^= mask;
                mapping[ch - 'a'] = 0;
            }
        }
        return count;
    }

    public boolean canPlaceWordH(int row, int col, String word, char[][] arr) {
        if (col == 0 && col + word.length() < arr[row].length && arr[row][col + word.length()] != '+')
            return false;
        else if (col + word.length() == arr[row].length && word.length() != arr[row].length
                && arr[row][col - 1] != '+')
            return false;
        else if ((col - 1 >= 0 && arr[row][col - 1] != '+')
                || (col + word.length() < arr[row].length && arr[row][col + word.length()] != '+'))
            return false;

        for (int i = 0; i < word.length(); i++)
            if ((col + i) < arr[row].length && arr[row][col + i] != '-' && arr[row][col + i] != word.charAt(i))
                return false;
        return true;
    }

    public boolean[] placeWordH(int row, int col, String word, char[][] arr) {
        boolean[] mark = new boolean[word.length()];
        for (int i = 0; i < word.length(); i++)
            if (arr[row][col + i] == '-') {
                mark[i] = true;
                arr[row][col + i] = word.charAt(i);
            }

        return mark;
    }

    public void unPlaceWordH(int row, int col, String word, boolean[] mark, char[][] arr) {
        for (int i = 0; i < word.length(); i++)
            if (mark[i])
                arr[row][col + i] = '-';
    }

    public boolean canPlaceWordV(int row, int col, String word, char[][] arr) {
        if (row == 0 && row + word.length() < arr.length && arr[row + word.length()][col] != '+')
            return false;
        else if (row + word.length() == arr.length && word.length() != arr.length && arr[row - 1][col] != '+')
            return false;
        else if ((row - 1 >= 0 && arr[row - 1][col] != '+')
                || (row + word.length() < arr.length && arr[row + word.length()][col] != '+'))
            return false;

        for (int i = 0; i < word.length(); i++)
            if ((row + i) < arr.length && arr[row + i][col] != '-' && arr[row + i][col] != word.charAt(i))
                return false;
        return true;

    }

    public boolean[] placeWordV(int row, int col, String word, char[][] arr) {
        boolean[] mark = new boolean[word.length()];
        for (int i = 0; i < word.length(); i++)
            if (arr[row + i][col] == '-') {
                mark[i] = true;
                arr[row + i][col] = word.charAt(i);
            }

        return mark;
    }

    public void unPlaceWordV(int row, int col, String word, boolean[] mark, char[][] arr) {
        for (int i = 0; i < word.length(); i++)
            if (mark[i])
                arr[row + i][col] = '-';
    }

    public boolean crossWord(char[][] arr, List<String> list, int idx) {

        if (idx == arr.length * arr[0].length)
            return true;

        boolean res = false;

        String word = list.get(idx);
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {

                if (arr[i][j] == '-' || arr[i][j] == word.charAt(0)) {

                    if (canPlaceWordH(i, j, word, arr)) {
                        boolean[] mark = placeWordH(i, j, word, arr);
                        res = res || crossWord(arr, list, idx + 1);
                        unPlaceWordH(i, j, word, mark, arr);
                    }

                    if (canPlaceWordV(i, j, word, arr)) {
                        boolean[] mark = placeWordV(i, j, word, arr);
                        res = res || crossWord(arr, list, idx + 1);
                        unPlaceWordV(i, j, word, mark, arr);
                    }

                }

            }
        }
        return res;
    }

    public int palindromicPartitions(String str) {
        list = new ArrayList<>();
        return palindromicPartitions(str, 0, "", isSubstringPalindrome(str));
    }

    public int palindromicPartitions(String str, int idx, String ans, boolean[][] isPalindrome) {
        if (idx == str.length()) {
            list.add(ans);
            return 1;
        }

        int count = 0;
        for (int i = idx + 1; i <= str.length(); i++) {
            String ss = str.substring(idx, i);
            if (isPalindrome[idx][i])
                count += palindromicPartitions(str, i, ans + ss + ", ", isPalindrome);
        }
        return count;
    }

    public static boolean[][] isSubstringPalindrome(String str) {
        boolean[][] isPalindrome = new boolean[str.length()][str.length()];
        for (int gap = 0; gap < str.length(); gap++) {
            for (int i = 0, j = gap; j < str.length(); i++, j++) {
                if (gap == 0)
                    isPalindrome[i][j] = true;
                else if (gap == 1 && str.charAt(i) == str.charAt(j))
                    isPalindrome[i][j] = true;
                else if (str.charAt(i) == str.charAt(j) && isPalindrome[i + 1][j - 1])
                    isPalindrome[i][j] = true;
            }
        }
        return isPalindrome;
    }

    // ***https://www.youtube.com/watch?list=Q1fLW_zQr3M */
    public int tugOfWar(int[] arr) {
        diff = Integer.MAX_VALUE;
        tugOfWar(arr, 0, 0, 0, 0, 0);
        return diff;
    }

    int diff;

    public void tugOfWar(int[] arr, int idx, int sum1, int sum2, int s1, int s2) {
        if (idx == arr.length) {
            diff = Math.min(diff, sum1 - sum2);
            return;
        }

        if (s1 < (arr.length + 1) / 2)
            tugOfWar(arr, idx + 1, sum1 + arr[idx], sum2, s1 + 1, s2);
        if (s2 < (arr.length + 1) / 2)
            tugOfWar(arr, idx + 1, sum1, sum2 + arr[idx], s1, s2 + 1);
    }

    // *https://www.youtube.com/watch?list=VQVeAQVs1d8&list=PLfqMhTWNBTe0b2nM6JHVCnAkhQRGiZMSJ&index=42
    public int tilingProblem(int n) {
        if (n == 0 || n == 1)
            return n;

        return tilingProblem(n - 1) + tilingProblem(n - 2);
    }

    public String maxNumberAfterKSwaps(String str, int k) {
        maxNumber = str;
        maxNumberAfterKSwapsRecursion(str, k);
        return maxNumber;
    }

    String maxNumber;

    private void maxNumberAfterKSwapsRecursion(String str, int k) {
        if (str.compareTo(maxNumber) > 0)
            maxNumber = str;

        if (k == 0)
            return;

        for (int i = 0; i < str.length(); i++) {
            for (int j = i + 1; j < str.length(); j++) {
                if (str.charAt(j) > str.charAt(i)) {
                    swap(str, i, j);
                    maxNumberAfterKSwapsRecursion(str, k - 1);
                    swap(str, i, j);
                }
            }
        }

    }

    public void swap(String str, int i, int j) {
        StringBuilder sb = new StringBuilder(str);
        char temp = str.charAt(i);
        sb.setCharAt(i, str.charAt(j));
        sb.setCharAt(j, temp);
        str = sb.toString();
    }

    // *https://leetcode.com/problems/permutation-sequence/
    public String kthPermutationSequence(int n, int k) {
        int[] factorials = new int[10];
        factorials[0] = 1;
        for (int i = 1; i < 10; i++)
            factorials[i] = factorials[i - 1] * i;

        StringBuilder sb = new StringBuilder();

        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 1; i <= n; i++)
            list.add(i);

        k--;
        for (int i = n - 1; i >= 0; i--) {
            int num = k / factorials[i];
            sb.append(list.get(num));
            list.remove(num);
            k %= factorials[i];
        }
        return sb.toString();
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

    int[][] dir2 = { { 0, 1 }, { 1, 0 }, { -1, 0 }, { 0, -1 } };

    public boolean dfs4Dir(String str, int idx, char[][] board, int i, int j, boolean[][] visited) {

        if (idx == str.length() - 1)
            return true;

        visited[i][j] = true;

        boolean res = false;
        for (int d = 0; d < dir2.length; d++) {

            int x = i + dir2[d][0];
            int y = j + dir2[d][1];

            if (x >= 0 && y >= 0 && x < board.length && y < board[0].length && !visited[x][y]
                    && str.charAt(idx + 1) == board[x][y])
                res = res || dfs4Dir(str, idx + 1, board, x, y, visited);
        }

        visited[i][j] = false;
        return res;
    }

}
