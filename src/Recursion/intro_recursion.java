package Recursion;

import java.util.*;

public class intro_recursion {

    List<String> list;

    // *** Bottom-Up + SubSets
    public List<String> subSequences(String str, int idx) {
        if (idx > str.length())
            return new ArrayList<>(Arrays.asList(""));

        char ch = str.charAt(idx);

        List<String> res = new ArrayList<>();
        List<String> rr = subSequences(str, idx + 1);

        for (String s : rr) {
            res.add(s);
            res.add(s + ch);
        }
        return res;
    }

    public int subSequences(String str) {
        list = new ArrayList<>();
        return subSequences(str, 0, "");
    }

    // **** TopDown + SubSets
    private int subSequences(String str, int idx, String ans) {
        if (idx == str.length()) {
            list.add(ans);
            return 1;
        }

        char ch = str.charAt(idx);

        int count = 0;
        count += subSequences(str, idx + 1, ans);
        count += subSequences(str, idx + 1, ans + ch);
        return count;
    }

    // *** Bottom-Up + SubSets
    public List<String> permutations(String str, int idx) {
        if (idx > str.length())
            return new ArrayList<>(Arrays.asList(""));

        char ch = str.charAt(idx);

        List<String> res = new ArrayList<>();
        List<String> rr = permutations(str, idx + 1);

        for (String s : rr)
            for (int i = 0; i <= s.length(); i++)
                res.add(s.substring(0, i) + ch + s.substring(i));
        return res;
    }

    public int permutations1(String str) {
        list = new ArrayList<>();
        return permutations(str, 0, "");
    }

    // **** TopDown + Subsets
    private int permutations(String str, int idx, String ans) {
        if (idx == str.length()) {
            list.add(ans);
            return 1;
        }

        char ch = str.charAt(idx);

        int count = 0;
        for (int i = 0; i <= ans.length(); i++)
            count += permutations(str, idx + 1, ans.substring(0, i) + ch + ans.substring(i));
        return count;
    }

    public int sequentialPermutations(String str) {
        list = new ArrayList<>();
        return permutations(str, "");
    }

    // *** TopDown + Binomial -> For Sequential Permutations
    private int permutations(String str, String ans) {
        if (str.length() == 0) {
            list.add(ans);
            return 1;
        }

        int count = 0;
        for (int i = 0; i < str.length(); i++)
            count += permutations(str.substring(0, i) + str.substring(i + 1), ans + str.charAt(i));
        return count;
    }

    public int uniquePermutations(String str) {
        list = new ArrayList<>();
        return permutationsForUniqueness(str, "");
    }

    // *** TopDown + Binomial + Visited Array-> For Sequential Permutations
    private int permutationsForUniqueness(String str, String ans) {
        if (str.length() == 0) {
            list.add(ans);
            return 1;
        }

        boolean[] visited = new boolean[256];

        int count = 0;
        for (int i = 0; i < str.length(); i++)
            if (!visited[str.charAt(i)]) {
                visited[str.charAt(i)] = true;
                count += permutationsForUniqueness(str.substring(0, i) + str.substring(i + 1), ans + str.charAt(i));
            }
        return count;
    }

    public int palindromicPermutations(String str) {

        list = new ArrayList<>();

        HashMap<Character, Integer> map = new HashMap<>();
        for (char ch : str.toCharArray())
            map.put(ch, map.getOrDefault(ch, 0) + 1);

        StringBuilder sb = new StringBuilder();
        int count = 0;
        char oddChar = ' ';
        for (char ch : str.toCharArray()) {
            if (map.get(ch) % 2 != 0) {
                count++;
                oddChar = ch;
            }
            for (int i = 0; i < map.get(ch) / 2; i++)
                sb.append(ch);
        }
        if (count > 1)
            return 0;

        return palindromicPermutations(sb.toString(), oddChar, "");
    }

    private int palindromicPermutations(String str, char oddChar, String ans) {
        if (str.length() == 0) {
            if (oddChar != ' ')
                list.add(ans + oddChar + (new StringBuilder(ans).reverse().toString()));
            else
                list.add(ans + (new StringBuilder(ans).reverse().toString()));
            return 1;
        }

        boolean[] visited = new boolean[256];

        int count = 0;
        for (int i = 0; i < str.length(); i++)
            if (!visited[str.charAt(i)]) {
                visited[str.charAt(i)] = true;
                count += palindromicPermutations(str.substring(0, i) + str.substring(i + 1), oddChar,
                        ans + str.charAt(i));
            }
        return count;
    }

    public int keyPad(String str) {
        list = new ArrayList<>();
        return keyPad(str, 0, "");
    }

    public String getCode(String str) {
        if (str.equals("10"))
            return "!@#";
        else if (str.equals("11"))
            return "/*-";
        return "";
    }

    public String getCode(char ch) {
        if (ch == '1')
            return "abc";
        else if (ch == '2')
            return "def";
        else if (ch == '3')
            return "ghi";
        else if (ch == '4')
            return "jk";
        else if (ch == '5')
            return "lmno";
        else if (ch == '6')
            return "pqr";
        else if (ch == '7')
            return "stu";
        else if (ch == '8')
            return "vwx";
        else if (ch == '9')
            return "yz";
        else if (ch == '0')
            return "0#";
        else
            return "";
    }

    // *** TopDown + SubSets
    private int keyPad(String str, int idx, String ans) {
        if (idx == str.length()) {
            list.add(ans);
            return 1;
        }

        char ch = str.charAt(idx);
        int count = 0;

        String code = getCode(ch);
        for (char c : code.toCharArray())
            count += keyPad(str, idx + 1, ans + c);

        if (str.length() <= 1)
            return count;

        code = getCode(str.substring(idx, idx + 2));
        for (char c : code.toCharArray())
            count += keyPad(str, idx + 2, ans + c);

        return count;
    }

    public int nokiaKeyPad(String str) {
        list = new ArrayList<>();
        return nokiaKeyPad(str, 0, "");
    }

    public String[] Code = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "i", "j", "k", "l", "m", "n",
            "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z" };

    // *** TopDown + SubSets
    private int nokiaKeyPad(String str, int idx, String ans) {
        if (idx == str.length()) {
            list.add(ans);
            return 1;
        }

        char ch = str.charAt(idx);
        if (ch == '0')
            return 0;

        int count = 0;
        count += nokiaKeyPad(str, idx + 1, ans + Code[ch - '1']);

        if (str.length() <= 1)
            return count;

        count += nokiaKeyPad(str, idx + 2, ans + Code[Integer.parseInt(str.substring(idx, idx + 2))]);

        return count;
    }

    int[][] dir = { { -1, 0 }, { -1, 1 }, { 0, 1 }, { 1, 1 }, { 1, 0 }, { 1, -1 }, { 0, -1 }, { -1, -1 } };
    String[] moveDir = { "N", "NE", "E", "SE", "S", "SW", "W", "NW" };

    // ****** FloodFill
    public int mazePath(int n, int m) {
        list = new ArrayList<>();

        maxPathLength = Integer.MIN_VALUE;
        minPathLength = Integer.MAX_VALUE;

        return mazePath(0, 0, new boolean[n][m], n, m, "", 0);
    }

    int maxPathLength, minPathLength;

    private int mazePath(int i, int j, boolean[][] vis, int n, int m, String ans, int pathLength) {
        if (i == n && j == m) {
            list.add(ans);
            maxPathLength = Math.max(maxPathLength, pathLength);
            minPathLength = Math.min(minPathLength, pathLength);
            return 1;
        }

        vis[i][j] = true;

        int count = 0;
        for (int moves = 1; moves < n && moves < m; moves++) {
            for (int d = 0; d < dir.length; d++) {
                int x = moves * dir[d][0] + i;
                int y = moves * dir[d][1] + j;
                if (x >= 0 && y >= 0 && x < n && y < m && !vis[x][y])
                    count += mazePath(x, y, vis, n, m, ans + moveDir[d] + "(" + moves + ")", pathLength + 1);
            }
        }

        vis[i][j] = false;

        return count;
    }

    public int queenCombinations(int n, int numberOfQueen) {
        list = new ArrayList<>();
        return queenCombinations(n, numberOfQueen, 0, 0, "");
    }

    private int queenCombinations(int n, int numberOfQueen, int idx, int countOfQueens, String ans) {

        if (idx == n || numberOfQueen == countOfQueens) {
            if (numberOfQueen == countOfQueens) {
                list.add(ans);
                return 1;
            }
            return 0;
        }

        int count = 0;
        for (int i = idx; i < n; i++)
            count += queenCombinations(n, numberOfQueen, i + 1, countOfQueens + 1, ans + "Q" + "(" + i + ")");
        return count;
    }

    public int queenPermutations(int n, int numberOfQueen) {
        list = new ArrayList<>();
        return queenPermutations(n, numberOfQueen, 0, "", new boolean[n]);
    }

    private int queenPermutations(int n, int numberOfQueen, int countOfQueens, String ans, boolean[] vis) {
        if (numberOfQueen == countOfQueens) {
            list.add(ans);
            return 1;
        }

        int count = 0;
        for (int i = 0; i < n; i++)
            if (!vis[i]) {
                vis[i] = true;
                count += queenPermutations(n, numberOfQueen, countOfQueens + 1,
                        ans + "Q" + countOfQueens + "(" + i + ")",
                        vis);
                vis[i] = false;
            }
        return count;
    }

    public int queenCombinations(int n, int m, int numberOfQueen) {
        list = new ArrayList<>();
        return queenCombinations2D(n * m, m, numberOfQueen, 0, 0, "");
    }

    private int queenCombinations2D(int cells, int m, int numberOfQueen, int idx, int countOfQueens, String ans) {

        if (idx == cells || numberOfQueen == countOfQueens) {
            if (numberOfQueen == countOfQueens) {
                list.add(ans);
                return 1;
            }
            return 0;
        }

        int count = 0;
        for (int i = idx; i < cells; i++) {
            int r = cells / m;
            int c = cells % m;
            count += queenCombinations2D(cells, m, numberOfQueen, i + 1, countOfQueens + 1,
                    ans + "Q" + "(" + r + "," + c + ")");
        }
        return count;
    }

    public int queenPermutations(int n, int m, int numberOfQueen) {
        list = new ArrayList<>();
        return queenPermutations2D(n * m, m, numberOfQueen, 0, "", new boolean[n][m]);
    }

    private int queenPermutations2D(int cells, int m, int numberOfQueen, int countOfQueens, String ans,
            boolean[][] vis) {
        if (numberOfQueen == countOfQueens) {
            list.add(ans);
            return 1;
        }

        int count = 0;
        for (int i = 0; i < cells; i++) {
            int r = cells / m;
            int c = cells % m;
            if (!vis[r][c]) {
                vis[r][c] = true;
                count += queenPermutations2D(cells, m, numberOfQueen, countOfQueens + 1,
                        ans + "Q" + countOfQueens + "(" + r + "," + c + ")",
                        vis);
                vis[r][c] = false;
            }
        }
        return count;
    }

    public int nQueens(int n, int m, int numberOfQueen) {

        // **** Combinations
        list = new ArrayList<>();
        nQueensCombinations(n * m, m, 0, numberOfQueen, 0, "", new boolean[n][m]);

        rowArray = colArray = diagonalArray = antiDiagonalArray = new boolean[n + m];
        nQueensCombinations2(n * m, m, 0, numberOfQueen, 0, "");

        rowBoard = colBoard = diagonalBoard = antiDiagonalBoard = 0L;
        nQueensCombinations3(n * m, m, 0, numberOfQueen, 0, "");

        rowBoard = colBoard = diagonalBoard = antiDiagonalBoard = 0L;
        nQueensCombinations4(n, m, 0, numberOfQueen, 0, "", new boolean[n][m]);

        rowBoard = colBoard = diagonalBoard = antiDiagonalBoard = 0L;
        nQueensCombinations5(n, m, 0, numberOfQueen, 0, "", new boolean[n][m]);

        // **** Permutations
        list = new ArrayList<>();
        nQueensPermutations(n * m, m, numberOfQueen, 0, "", new boolean[n][m]);

        rowArray = colArray = diagonalArray = antiDiagonalArray = new boolean[n + m];
        nQueensPermutations2(n * m, m, numberOfQueen, 0, "", new boolean[n][m]);

        rowBoard = colBoard = diagonalBoard = antiDiagonalBoard = 0L;
        nQueensPermutations3(n * m, m, numberOfQueen, 0, "", new boolean[n][m]);
        return 0;
    }

    private int nQueensCombinations(int cells, int m, int cellNo, int numberOfQueen, int countOfQueens, String ans,
            boolean[][] vis) {
        if (numberOfQueen == countOfQueens) {
            list.add(ans);
            return 1;
        }

        int count = 0;
        for (int i = cellNo; i < cells; i++) {
            int r = cellNo / m;
            int c = cellNo % m;
            if (isValidPosition(vis, r, c)) {
                vis[r][c] = true;
                count += nQueensCombinations(cells, m, cellNo + 1, numberOfQueen, countOfQueens + 1,
                        ans + "Q" + countOfQueens + "(" + r + "," + c + ")",
                        vis);
                vis[r][c] = false;
            }
        }
        return count;
    }

    private int nQueensPermutations(int cells, int m, int numberOfQueen, int countOfQueens, String ans,
            boolean[][] vis) {
        if (numberOfQueen == countOfQueens) {
            list.add(ans);
            return 1;
        }

        int count = 0;
        for (int i = 0; i < cells; i++) {
            int r = cells / m;
            int c = cells % m;
            if (isValidPosition(vis, r, c)) {
                vis[r][c] = true;
                count += nQueensPermutations(cells, m, numberOfQueen, countOfQueens + 1,
                        ans + "Q" + countOfQueens + "(" + r + "," + c + ")",
                        vis);
                vis[r][c] = false;
            }
        }
        return count;
    }

    private boolean isValidPosition(boolean[][] board, int row, int col) {
        for (int i = col - 1; i >= 0; i--)
            if (board[row][i])
                return false;
        for (int i = row - 1; i >= 0; i--)
            if (board[i][col])
                return false;
        for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--)
            if (board[i][j])
                return false;
        for (int i = row - 1, j = col + 1; i >= 0 && j < board[row].length; i--, j++)
            if (board[i][j])
                return false;
        return true;
    }

    boolean[] rowArray, colArray, diagonalArray, antiDiagonalArray;

    private int nQueensCombinations2(int cells, int m, int cellNo, int numberOfQueen, int countOfQueens, String ans) {
        if (numberOfQueen == countOfQueens) {
            list.add(ans);
            return 1;
        }

        int count = 0;
        for (int i = cellNo; i < cells; i++) {
            int r = cells / m;
            int c = cells % m;
            if (!rowArray[r] && !colArray[c] && !diagonalArray[r - c + m - 1]
                    && !antiDiagonalArray[r + c]) {

                rowArray[r] = true;
                colArray[c] = true;
                diagonalArray[r - c + m - 1] = true;
                antiDiagonalArray[r + c] = true;

                count += nQueensCombinations2(cells, m, cellNo + 1, numberOfQueen, countOfQueens + 1,
                        ans + "Q" + countOfQueens + "(" + r + "," + c + ")");

                rowArray[r] = false;
                colArray[c] = false;
                diagonalArray[r - c + m - 1] = false;
                antiDiagonalArray[r + c] = false;

            }
        }
        return count;
    }

    private int nQueensPermutations2(int cells, int m, int numberOfQueen, int countOfQueens, String ans,
            boolean[][] vis) {
        if (numberOfQueen == countOfQueens) {
            list.add(ans);
            return 1;
        }

        int count = 0;
        for (int i = 0; i < cells; i++) {
            int r = cells / m;
            int c = cells % m;
            if (!vis[r][c] && !rowArray[r] && !colArray[c] && !diagonalArray[r - c + m - 1]
                    && !antiDiagonalArray[r + c]) {
                vis[r][c] = true;

                rowArray[r] = true;
                colArray[c] = true;
                diagonalArray[r - c + m - 1] = true;
                antiDiagonalArray[r + c] = true;

                count += nQueensPermutations2(cells, m, numberOfQueen, countOfQueens + 1,
                        ans + "Q" + countOfQueens + "(" + r + "," + c + ")",
                        vis);

                rowArray[r] = false;
                colArray[c] = false;
                diagonalArray[r - c + m - 1] = false;
                antiDiagonalArray[r + c] = false;

                vis[r][c] = false;
            }
        }
        return count;
    }

    long rowBoard, colBoard, diagonalBoard, antiDiagonalBoard;

    private int nQueensCombinations3(int cells, int m, int cellNo, int numberOfQueen, int countOfQueens, String ans) {
        if (numberOfQueen == countOfQueens) {
            list.add(ans);
            return 1;
        }

        int count = 0;
        for (int i = cellNo; i < cells; i++) {
            int r = cells / m;
            int c = cells % m;
            if (((rowBoard & (1 << r)) == 0) && ((colBoard & (1 << c)) == 0)
                    && ((diagonalBoard & (1 << (r - c + m - 1))) == 0) &&
                    ((antiDiagonalBoard & (1 << (r + c))) == 0)) {

                rowBoard ^= (1 << r);
                colBoard ^= (1 << c);
                diagonalBoard ^= (1 << (r - c + m - 1));
                antiDiagonalBoard ^= (1 << (r + c));

                count += nQueensCombinations3(cells, m, cellNo + 1, numberOfQueen, countOfQueens + 1,
                        ans + "Q" + countOfQueens + "(" + r + "," + c + ")");

                rowBoard ^= (1 << r);
                colBoard ^= (1 << c);
                diagonalBoard ^= (1 << (r - c + m - 1));
                antiDiagonalBoard ^= (1 << (r + c));

            }
        }
        return count;
    }

    private int nQueensPermutations3(int cells, int m, int numberOfQueen, int countOfQueens, String ans,
            boolean[][] vis) {
        if (numberOfQueen == countOfQueens) {
            list.add(ans);
            return 1;
        }

        int count = 0;
        for (int i = 0; i < cells; i++) {
            int r = cells / m;
            int c = cells % m;
            if (!vis[r][c] && ((rowBoard & (1 << r)) == 0) && ((colBoard & (1 << c)) == 0)
                    && ((diagonalBoard & (1 << (r - c + m - 1))) == 0) &&
                    ((antiDiagonalBoard & (1 << (r + c))) == 0)) {
                vis[r][c] = true;

                rowBoard ^= (1 << r);
                colBoard ^= (1 << c);
                diagonalBoard ^= (1 << (r - c + m - 1));
                antiDiagonalBoard ^= (1 << (r + c));

                count += nQueensPermutations3(cells, m, numberOfQueen, countOfQueens + 1,
                        ans + "Q" + countOfQueens + "(" + r + "," + c + ")",
                        vis);

                rowBoard ^= (1 << r);
                colBoard ^= (1 << c);
                diagonalBoard ^= (1 << (r - c + m - 1));
                antiDiagonalBoard ^= (1 << (r + c));

                vis[r][c] = false;
            }
        }
        return count;
    }

    // **** Only works when Number of Queens = Number of Rows of Board
    private int nQueensCombinations4(int n, int m, int r, int numberOfQueen, int countOfQueens, String ans,
            boolean[][] vis) {
        if (r == n || numberOfQueen == countOfQueens) {
            if (numberOfQueen == countOfQueens) {
                list.add(ans);
                return 1;
            }
            return 0;
        }

        int count = 0;
        for (int c = 0; c < m; c++) {
            if (!vis[r][c] && ((rowBoard & (1 << r)) == 0) && ((colBoard & (1 << c)) == 0)
                    && ((diagonalBoard & (1 << (r - c + m - 1))) == 0) &&
                    ((antiDiagonalBoard & (1 << (r + c))) == 0)) {
                vis[r][c] = true;

                rowBoard ^= (1 << r);
                colBoard ^= (1 << c);
                diagonalBoard ^= (1 << (r - c + m - 1));
                antiDiagonalBoard ^= (1 << (r + c));

                count += nQueensCombinations4(n, m, r + 1, numberOfQueen, countOfQueens + 1,
                        ans + "Q" + countOfQueens + "(" + r + "," + c + ")",
                        vis);

                rowBoard ^= (1 << r);
                colBoard ^= (1 << c);
                diagonalBoard ^= (1 << (r - c + m - 1));
                antiDiagonalBoard ^= (1 << (r + c));

                vis[r][c] = false;
            }
        }
        return count;
    }

    // **** Works for Number of Queens = Number of Rows of Board case also
    private int nQueensCombinations5(int n, int m, int r, int numberOfQueen, int countOfQueens, String ans,
            boolean[][] vis) {
        if (r == n || numberOfQueen == countOfQueens) {
            if (numberOfQueen == countOfQueens) {
                list.add(ans);
                return 1;
            }
            return 0;
        }

        int count = 0;
        for (int house = r; house < n; house++) {
            for (int c = 0; c < m; c++) {
                if (!vis[house][c] && ((rowBoard & (1 << house)) == 0) && ((colBoard & (1 << c)) == 0)
                        && ((diagonalBoard & (1 << (house - c + m - 1))) == 0) &&
                        ((antiDiagonalBoard & (1 << (house + c))) == 0)) {
                    vis[house][c] = true;

                    rowBoard ^= (1 << house);
                    colBoard ^= (1 << c);
                    diagonalBoard ^= (1 << (house - c + m - 1));
                    antiDiagonalBoard ^= (1 << (house + c));

                    count += nQueensCombinations5(n, m, house + 1, numberOfQueen, countOfQueens + 1,
                            ans + "Q" + countOfQueens + "(" + r + "," + c + ")",
                            vis);

                    rowBoard ^= (1 << house);
                    colBoard ^= (1 << c);
                    diagonalBoard ^= (1 << (house - c + m - 1));
                    antiDiagonalBoard ^= (1 << (house + c));

                    vis[house][c] = false;
                }
            }
        }
        return count;
    }

    public int sudoku(int[][] arr) {
        sudoku(arr, 0);

        sudoku2(arr, makeList(arr), 0);

        set_masks(arr);
        sudoku3(arr, makeList(arr), 0);
        return 0;
    }

    private int sudoku(int[][] arr, int idx) {
        if (idx == arr.length * arr[0].length)
            return 1;

        int r = idx / arr[0].length;
        int c = idx % arr[0].length;

        int count = 0;
        if (arr[r][c] == 0) {
            for (int num = 1; num <= arr.length; num++)
                if (isSafeToPlace(arr, r, c, num)) {
                    arr[r][c] = num;
                    count += sudoku(arr, idx + 1);
                    arr[r][c] = 0;
                }
        } else
            count += sudoku(arr, idx + 1);

        return count;
    }

    public boolean isSafeToPlace(int[][] board, int row, int col, int num) {
        for (int i = 0; i < board.length; i++)
            if (board[i][col] == num || board[row][i] == num)
                return false;

        int x = (row / (int) Math.sqrt(board.length)) * (int) Math.sqrt(board.length);
        int y = (col / (int) Math.sqrt(board.length)) * (int) Math.sqrt(board.length);

        for (int i = x; i < x + (int) Math.sqrt(board.length); i++)
            for (int j = y; j < y + (int) Math.sqrt(board.length); j++)
                if (board[i][j] == num)
                    return false;
        return true;
    }

    public ArrayList<Integer> makeList(int[][] arr) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                if (arr[i][j] == 0)
                    list.add((i * arr.length) + j);
            }
        }
        return list;
    }

    private int sudoku2(int[][] arr, ArrayList<Integer> list, int idx) {
        if (idx == arr.length * arr[0].length)
            return 1;

        int index = list.get(idx);
        int r = index / arr[0].length;
        int c = index % arr[0].length;

        int count = 0;
        for (int num = 1; num <= arr.length; num++)
            if (isSafeToPlace(arr, r, c, num)) {
                arr[r][c] = num;
                count += sudoku2(arr, list, idx + 1);
                arr[r][c] = 0;
            }

        return count;
    }

    int[] sudokuRowArray, sudokuColArray;
    int[][] cube;

    public void set_masks(int[][] arr) {

        sudokuColArray = sudokuRowArray = new int[arr.length];
        cube = new int[arr.length][arr[0].length];

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                if (arr[i][j] != 0) {
                    int mask = (1 << arr[i][j]);
                    sudokuRowArray[i] ^= mask;
                    sudokuColArray[j] ^= mask;
                    cube[i / (int) Math.sqrt(arr.length)][j / (int) Math.sqrt(arr.length)] ^= mask;
                }
            }
        }

    }

    private int sudoku3(int[][] arr, ArrayList<Integer> list, int idx) {
        if (idx == arr.length * arr[0].length)
            return 1;

        int index = list.get(idx);
        int r = index / arr[0].length;
        int c = index % arr[0].length;

        int count = 0;
        for (int num = 1; num <= arr.length; num++) {
            int mask = (1 << num);
            if ((sudokuRowArray[r] & mask) == 0 && (sudokuColArray[c] & mask) == 0
                    && (cube[r / (int) Math.sqrt(arr.length)][c / (int) Math.sqrt(arr.length)] & mask) == 0) {

                sudokuRowArray[r] ^= mask;
                sudokuColArray[c] ^= mask;
                cube[r / (int) Math.sqrt(arr.length)][c / (int) Math.sqrt(arr.length)] ^= mask;

                arr[r][c] = num;
                count += sudoku3(arr, list, idx + 1);
                arr[r][c] = 0;

                sudokuRowArray[r] ^= mask;
                sudokuColArray[c] ^= mask;
                cube[r / (int) Math.sqrt(arr.length)][c / (int) Math.sqrt(arr.length)] ^= mask;

            }
        }
        return count;
    }

}
