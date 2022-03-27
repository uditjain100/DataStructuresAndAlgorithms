package DynamicProgramming;

public class intro_dp {

    public int fibonacci(int n) {
        if (n == 0 || n == 1)
            return n;
        return fibonacci(n - 1) + fibonacci(n - 2);
    }

    public int fibonacciMemorization(int n, int[] dp) {
        if (n == 0 || n == 1)
            return dp[n] = n;

        if (dp[n] != -1)
            return dp[n];

        return dp[n] = fibonacciMemorization(n - 1, dp) + fibonacciMemorization(n - 2, dp);
    }

    public int fibonacciTable(int n) {
        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = 1;

        for (int i = 2; i < n + 1; i++)
            dp[i] = dp[i - 1] + dp[i - 2];

        return dp[n];
    }

    public int fibonacciOptimization(int n) {
        int a = 0;
        int b = 1;

        for (int i = 2; i < n; i++) {
            int sum = a + b;
            a = b;
            b = sum;
        }
        return b;
    }

    // *https://www.geeksforgeeks.org/count-number-binary-strings-without-consecutive-1s/
    public int countBinaryStrings(int n) {
        return fibonacciOptimization(n);
    }

    // *https://www.geeksforgeeks.org/count-possible-ways-to-construct-buildings/
    public int arrangeBuildings(int n) {
        int ans = fibonacciOptimization(n);
        return ans * ans;
    }

    // ? Big => 2xn and Small -> 2x1
    // *https://www.geeksforgeeks.org/tiling-problem/
    public int tilingProblem1(int n) {
        return fibonacciOptimization(n + 2);
    }

    // ? Big => mxn and Small -> mx1
    public int tilingProblem2Memorization(int n, int m, int[] dp) {
        if (n < m)
            return dp[n] = 1;
        if (n == m)
            return dp[n] = 2;

        if (dp[n] != -1)
            return dp[n];

        return dp[n] = tilingProblem2Memorization(n - 1, m, dp) + tilingProblem2Memorization(n - m, m, dp);
    }

    public int tilingProblem2MemorizationOptimization(int n, int m) {

        int[] dp = new int[n + 1];

        for (int i = n; i >= 0; i--) {
            if (i < m)
                dp[i] = 1;
            else if (i == m)
                dp[i] = 2;
            else
                dp[i] = dp[i - 1] + dp[i - m];
        }

        return dp[0];
    }

    public int tribonacci(int n) {
        if (n == 0 || n == 1)
            return n;
        if (n == 2)
            return 1;
        return tribonacci(n - 1) + tribonacci(n - 2) + tribonacci(n - 3);
    }

    public int tribonacciMemorization(int n, int[] dp) {
        if (n == 0 || n == 1)
            return dp[n] = n;
        if (n == 2)
            return dp[n] = 1;

        if (dp[n] != -1)
            return dp[n];

        return dp[n] = tribonacciMemorization(n - 1, dp) + tribonacciMemorization(n - 2, dp)
                + tribonacciMemorization(n - 3, dp);
    }

    public int tribonacciTable(int n) {
        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = 1;
        dp[2] = 1;

        for (int i = 2; i < n + 1; i++)
            dp[i] = dp[i - 1] + dp[i - 2];

        return dp[n];
    }

    public int friendsPairing(int n) {
        if (n == 0 || n == 1)
            return 1;
        return friendsPairing(n - 1) + friendsPairing(n - 2) * (n - 1);
    }

    public int friendsPairingMemorization(int n, int[] dp) {
        if (n == 0 || n == 1)
            return dp[n] = 1;

        if (dp[n] != -1)
            return dp[n];

        return dp[n] = friendsPairingMemorization(n - 1, dp) + friendsPairingMemorization(n - 2, dp) * (n - 1);
    }

    public int friendsPairingTable(int n) {
        int[] dp = new int[n + 1];
        dp[0] = dp[1] = 1;

        for (int i = 2; i < n + 1; i++)
            dp[i] = dp[i - 1] + dp[i - 2] * (i - 1);

        return dp[n];
    }

    public int setIntoSubsets(int n, int k) {
        if (k == 0 || n == 0 || k > n)
            return 0;
        if (n == 1 || k == n)
            return 1;

        return setIntoSubsets(n - 1, k - 1) + setIntoSubsets(n - 1, k) * k;
    }

    public int setIntoSubsetsMemorization(int n, int k, int[][] dp) {
        if (n == 0 || k == 0 || n < k)
            return dp[n][k] = 0;
        if (n == 1 || n == k)
            return dp[n][k] = 1;

        if (dp[n][k] != -1)
            return dp[n][k];

        return dp[n][k] = setIntoSubsetsMemorization(n - 1, k - 1, dp) + setIntoSubsetsMemorization(n - 1, k, dp) * k;
    }

    public int setIntoSubsetsTable(int n, int k) {

        int[][] dp = new int[n + 1][k + 1];

        for (int i = 0; i < n + 1; i++) {
            for (int j = 0; j < k + 1; j++) {
                if (i == 0 || j == 0 || i < j)
                    dp[i][j] = 0;
                else if (i == 1 || i == j)
                    dp[i][j] = 1;
                else
                    dp[i][j] = dp[i - 1][j - 1] + dp[i - 1][j] * j;
            }
        }

        return dp[n][k];
    }

    // *https://leetcode.com/problems/decode-ways/
    public int decodeWays1(String str, int idx) {
        if (idx == str.length())
            return 1;

        int count = 0;
        if (str.charAt(idx) != '0')
            count += decodeWays1(str, idx + 1);

        if (idx + 1 < str.length() && Integer.parseInt(str.substring(idx, idx + 2)) >= 10
                && Integer.parseInt(str.substring(idx, idx + 2)) <= 26)
            count += decodeWays1(str, idx + 2);
        return count;
    }

    public int decodeWays1Memorization(String str, int idx, int[] dp) {
        if (idx == str.length())
            return dp[idx] = 1;

        if (dp[idx] != -1)
            return dp[idx];

        int count = 0;
        if (str.charAt(idx) != '0')
            count += decodeWays1Memorization(str, idx + 1, dp);

        if (idx + 1 < str.length() && Integer.parseInt(str.substring(idx, idx + 2)) >= 10
                && Integer.parseInt(str.substring(idx, idx + 2)) <= 26)
            count += decodeWays1Memorization(str, idx + 2, dp);
        return dp[idx] = count;
    }

    public int decodeWays1Tabulation(String str) {

        int n = str.length();
        int[] dp = new int[n + 1];

        for (int i = n; i >= 0; i--) {
            if (i == n) {
                dp[i] = 1;
                continue;
            }

            int count = 0;
            if (str.charAt(i) != '0')
                count += dp[i + 1];

            if (i + 1 < n && Integer.parseInt(str.substring(i, i + 2)) >= 10
                    && Integer.parseInt(str.substring(i, i + 2)) <= 26)
                count += dp[i + 2];
            dp[i] = count;
        }

        return dp[0];
    }

    public int decodeWays1Optimization(String str) {

        int n = str.length();

        int a = 0;
        int b = 1;
        for (int i = n - 1; i >= 0; i--) {
            int count = 0;
            if (str.charAt(i) != '0')
                count += b;

            if (i + 1 < n && Integer.parseInt(str.substring(i, i + 2)) >= 10
                    && Integer.parseInt(str.substring(i, i + 2)) <= 26)
                count += a;

            a = b;
            b = count;
        }

        return b;
    }

    int mod = 1000000007;

    // *https://leetcode.com/problems/decode-ways-ii/
    public int decodeWays2(String str, int idx) {
        if (idx == str.length())
            return 1;

        int count = 0;
        if (str.charAt(idx) != '0') {

            if (str.charAt(idx) == '*') {
                count = (count + (9 * decodeWays2(str, idx + 1)) % mod) % mod; // **** single *

                if (idx + 1 < str.length()) {

                    char ch = str.charAt(idx + 1);
                    if (ch == '*') {
                        count = (count + (15 * decodeWays2(str, idx + 2)) % mod) % mod; // **** -> double **
                    } else {
                        if (ch <= '6')
                            count = (count + (2 * decodeWays2(str, idx + 2)) % mod) % mod; // **** -> *1, *2, ... *6
                        else
                            count = (count + (decodeWays2(str, idx + 2)) % mod) % mod; // **** -> *7, *8, *9
                    }

                }

            } else {
                count = (count + (decodeWays2(str, idx + 1)) % mod) % mod; // **** single num other than 0

                if (idx + 1 < str.length()) {

                    char ch = str.charAt(idx + 1);
                    if (ch == '*') {
                        if (str.charAt(idx) == '1')
                            count = (count + (9 * decodeWays2(str, idx + 2)) % mod) % mod; // **** -> 1*
                        else if (str.charAt(idx) == '2')
                            count = (count + (6 * decodeWays2(str, idx + 2)) % mod) % mod; // **** -> 2*
                    } else {
                        if (Integer.parseInt(str.substring(idx, idx + 2)) >= 10
                                && Integer.parseInt(str.substring(idx, idx + 2)) <= 26)
                            count = (count + (decodeWays2(str, idx + 2)) % mod) % mod;
                    }

                }

            }

        }
        return count;
    }

    public int decodeWays2Memorization(String str, int idx, long[] dp) {
        if (idx == str.length())
            return (int) (dp[idx] = 1);

        if (dp[idx] != -1)
            return (int) dp[idx];

        int count = 0;
        if (str.charAt(idx) != '0') {

            if (str.charAt(idx) == '*') {
                count = (count + (9 * decodeWays2Memorization(str, idx + 1, dp)) % mod) % mod; // **** single *

                if (idx + 1 < str.length()) {

                    char ch = str.charAt(idx + 1);
                    if (ch == '*') {
                        count = (count + (15 * decodeWays2Memorization(str, idx + 2, dp)) % mod) % mod; // **** ->
                                                                                                        // double **
                    } else {
                        if (ch <= '6')
                            count = (count + (2 * decodeWays2Memorization(str, idx + 2, dp)) % mod) % mod; // **** ->
                                                                                                           // *1, *2,
                                                                                                           // ... *6
                        else
                            count = (count + (decodeWays2Memorization(str, idx + 2, dp)) % mod) % mod; // **** -> *7,
                                                                                                       // *8, *9
                    }

                }

            } else {
                count = (count + (decodeWays2Memorization(str, idx + 1, dp)) % mod) % mod; // **** single num other than
                                                                                           // 0

                if (idx + 1 < str.length()) {

                    char ch = str.charAt(idx + 1);
                    if (ch == '*') {
                        if (str.charAt(idx) == '1')
                            count = (count + (9 * decodeWays2Memorization(str, idx + 2, dp)) % mod) % mod; // **** -> 1*
                        else if (str.charAt(idx) == '2')
                            count = (count + (6 * decodeWays2Memorization(str, idx + 2, dp)) % mod) % mod; // **** -> 2*
                    } else {
                        if (Integer.parseInt(str.substring(idx, idx + 2)) >= 10
                                && Integer.parseInt(str.substring(idx, idx + 2)) <= 26)
                            count = (count + (decodeWays2Memorization(str, idx + 2, dp)) % mod) % mod;
                    }

                }

            }

        }
        return (int) (dp[idx] = count);
    }

    public int decodeWays2Tabulation(String str) {

        int n = str.length();
        long[] dp = new long[n + 1];

        for (int idx = n; idx >= 0; idx--) {

            if (idx == n) {
                dp[idx] = 1;
                continue;
            }

            long count = 0;

            if (str.charAt(idx) != '0') {

                if (str.charAt(idx) == '*') {
                    count = (count + (9 * dp[idx + 1]) % mod) % mod; // **** single *

                    if (idx + 1 < n) {

                        char ch = str.charAt(idx + 1);
                        if (ch == '*') {
                            count = (count + (15 * dp[idx + 2]) % mod) % mod; // **** -> double **
                        } else {
                            if (ch <= '6')
                                count = (count + (2 * dp[idx + 2]) % mod) % mod; // **** -> *1, *2, ... *6
                            else
                                count = (count + (dp[idx + 2]) % mod) % mod; // **** -> *7, *8, *9
                        }

                    }

                } else {
                    count = (count + (dp[idx + 1]) % mod) % mod; // **** single num other than 0

                    if (idx + 1 < n) {

                        char ch = str.charAt(idx + 1);
                        if (ch == '*') {
                            if (str.charAt(idx) == '1')
                                count = (count + (9 * dp[idx + 2]) % mod) % mod; // **** -> 1*
                            else if (str.charAt(idx) == '2')
                                count = (count + (6 * dp[idx + 2]) % mod) % mod; // **** -> 2*
                        } else {
                            if (Integer.parseInt(str.substring(idx, idx + 2)) >= 10
                                    && Integer.parseInt(str.substring(idx, idx + 2)) <= 26)
                                count = (count + (dp[idx + 2]) % mod) % mod;
                        }

                    }

                }

            }

            dp[idx] = count % mod;
        }

        return (int) (dp[0] % mod);
    }

    public int decodeWays2Optimization(String str) {

        int n = str.length();

        long a = 0L;
        long b = 1L;
        for (int idx = n - 1; idx >= 0; idx--) {

            long count = 0;

            if (str.charAt(idx) != '0') {

                if (str.charAt(idx) == '*') {
                    count = (count + (9 * b) % mod) % mod; // **** single *

                    if (idx + 1 < n) {

                        char ch = str.charAt(idx + 1);
                        if (ch == '*') {
                            count = (count + (15 * a) % mod) % mod; // **** -> double **
                        } else {
                            if (ch <= '6')
                                count = (count + (2 * a) % mod) % mod; // **** -> *1, *2, ... *6
                            else
                                count = (count + (a) % mod) % mod; // **** -> *7, *8, *9
                        }

                    }

                } else {
                    count = (count + (b) % mod) % mod; // **** single num other than 0

                    if (idx + 1 < n) {

                        char ch = str.charAt(idx + 1);
                        if (ch == '*') {
                            if (str.charAt(idx) == '1')
                                count = (count + (9 * a) % mod) % mod; // **** -> 1*
                            else if (str.charAt(idx) == '2')
                                count = (count + (6 * a) % mod) % mod; // **** -> 2*
                        } else {
                            if (Integer.parseInt(str.substring(idx, idx + 2)) >= 10
                                    && Integer.parseInt(str.substring(idx, idx + 2)) <= 26)
                                count = (count + (a) % mod) % mod;
                        }

                    }

                }

            }

            a = b;
            b = count;
        }

        return (int) b;
    }

    int[][] dir = { { 0, 1 }, { 1, 0 }, { 1, 1 } };

    public int mazePath(int sr, int sc, int er, int ec) {
        if (sr == er && sc == ec)
            return 1;

        int count = 0;
        for (int moves = 1; moves <= er; moves++) {
            for (int d = 0; d < dir.length; d++) {
                int x = moves * dir[d][0] + sr;
                int y = moves * dir[d][1] + sc;
                if (x >= 0 && y >= 0 && x <= er && y <= ec)
                    count += mazePath(x, y, er, ec);
            }
        }
        return count;
    }

    public int mazePathMemorization(int sr, int sc, int er, int ec, int[][] dp) {
        if (sr == er && sc == ec)
            return dp[sr][sc] = 1;

        if (dp[sr][sc] != -1)
            return dp[sr][sc];

        int count = 0;
        for (int moves = 1; moves <= er; moves++) {
            for (int d = 0; d < dir.length; d++) {
                int x = moves * dir[d][0] + sr;
                int y = moves * dir[d][1] + sc;
                if (x >= 0 && y >= 0 && x <= er && y <= ec)
                    count += mazePathMemorization(x, y, er, ec, dp);
            }
        }
        return dp[sr][sc] = count;
    }

    public int mazePathTable(int er, int ec) {

        int[][] dp = new int[er][ec];

        for (int i = er - 1; i >= 0; i--) {
            for (int j = ec - 1; j >= 0; j--) {

                if (i == er - 1 && j == ec - 1) {
                    dp[i][j] = 1;
                    continue;
                }

                int count = 0;
                for (int moves = 1; moves <= er; moves++) {
                    for (int d = 0; d < dir.length; d++) {
                        int x = moves * dir[d][0] + i;
                        int y = moves * dir[d][1] + j;
                        if (x >= 0 && y >= 0 && x <= er && y <= ec)
                            count += dp[x][y];
                    }
                }
                dp[i][j] = count;
            }
        }

        return dp[0][0];
    }

    public int minCostPath(int[][] arr, int sr, int sc, int er, int ec) {
        if (sr == er && sc == ec)
            return arr[sr][sc];

        int currMin = Integer.MAX_VALUE;
        for (int d = 0; d < dir.length; d++) {
            int x = dir[d][0] + sr;
            int y = dir[d][1] + sc;
            if (x >= 0 && y >= 0 && x <= er && y <= ec)
                currMin = Math.min(currMin, minCostPath(arr, x, y, er, ec));
        }

        return currMin + arr[sr][sc];
    }

    public int minCostPathMemorization(int[][] arr, int sr, int sc, int er, int ec, int[][] dp) {
        if (sr == er && sc == ec)
            return dp[sr][sc] = arr[sr][sc];

        if (dp[sr][sc] != -1)
            return dp[sr][sc];

        int currMin = Integer.MAX_VALUE;
        for (int d = 0; d < dir.length; d++) {
            int x = dir[d][0] + sr;
            int y = dir[d][1] + sc;
            if (x >= 0 && y >= 0 && x <= er && y <= ec)
                currMin = Math.min(currMin, minCostPathMemorization(arr, x, y, er, ec, dp));
        }

        return dp[sr][sc] = currMin + arr[sr][sc];
    }

    public int minCostPathTable(int[][] arr) {

        int er = arr.length;
        int ec = arr[0].length;

        int[][] dp = new int[er][ec];

        for (int i = er - 1; i >= 0; i--) {
            for (int j = ec - 1; j >= 0; j--) {

                if (i == er - 1 && j == ec - 1) {
                    dp[i][j] = arr[i][j];
                    continue;
                }

                int currMin = Integer.MAX_VALUE;
                for (int d = 0; d < dir.length; d++) {
                    int x = dir[d][0] + i;
                    int y = dir[d][1] + j;
                    if (x >= 0 && y >= 0 && x <= er && y <= ec)
                        currMin = Math.min(currMin, dp[x][y]);
                }

                dp[i][j] = currMin + arr[i][j];
            }
        }

        return dp[0][0];
    }

    public int goldMine(int[][] arr) {
        int n = arr.length;
        int m = arr[0].length;
        int maxGold = Integer.MIN_VALUE;

        for (int i = 0; i < n; i++)
            maxGold = Math.max(maxGold, goldMine(arr, i, 0, n, m));

        return maxGold;
    }

    int[][] goldDir = { { 0, 1 }, { 1, 1 }, { -1, 1 } };

    public int goldMine(int[][] arr, int sr, int sc, int er, int ec) {
        if (sc == ec)
            return arr[sr][sc];

        int currMin = Integer.MAX_VALUE;
        for (int d = 0; d < goldDir.length; d++) {
            int x = goldDir[d][0] + sr;
            int y = goldDir[d][1] + sc;
            if (x >= 0 && y >= 0 && x <= er && y <= ec)
                currMin = Math.min(currMin, goldMine(arr, x, y, er, ec));
        }

        return currMin + arr[sr][sc];
    }

    public int goldMineMemorization(int[][] arr, int sr, int sc, int er, int ec, int[][] dp) {
        if (sc == ec)
            return dp[sr][sc] = arr[sr][sc];

        if (dp[sr][sc] != -1)
            return dp[sr][sc];

        int currMin = Integer.MAX_VALUE;
        for (int d = 0; d < goldDir.length; d++) {
            int x = goldDir[d][0] + sr;
            int y = goldDir[d][1] + sc;
            if (x >= 0 && y >= 0 && x <= er && y <= ec)
                currMin = Math.min(currMin, goldMineMemorization(arr, x, y, er, ec, dp));
        }

        return dp[sr][sc] = currMin + arr[sr][sc];
    }

    public int goldMineTable(int[][] arr) {

        int er = arr.length;
        int ec = arr[0].length;

        int[][] dp = new int[er][ec];

        for (int j = ec - 1; j >= 0; j--) {
            for (int i = er - 1; i >= 0; i--) {

                if (j == ec - 1) {
                    dp[i][j] = arr[i][j];
                    continue;
                }

                int currMin = Integer.MAX_VALUE;
                for (int d = 0; d < goldDir.length; d++) {
                    int x = goldDir[d][0] + i;
                    int y = goldDir[d][1] + j;
                    if (x >= 0 && y >= 0 && x <= er && y <= ec)
                        currMin = Math.min(currMin, dp[x][y]);
                }

                dp[i][j] = currMin + arr[i][j];

            }
        }

        int maxGold = Integer.MIN_VALUE;
        for (int i = 0; i < er; i++)
            maxGold = Math.max(maxGold, dp[i][0]);

        return maxGold;
    }

    // ? ******************************************** boardPath, Climbing Stairs
    public int boardPath(int si, int ei) {
        if (si == ei)
            return 1;

        int count = 0;
        for (int dice = 1; dice <= 6; dice++)
            if (si + dice <= ei)
                count += boardPath(si + dice, ei);

        return count;
    }

    public int boardPathMemorization(int si, int ei, int[] dp) {
        if (si == ei)
            return dp[si] = 1;

        if (dp[si] != -1)
            return dp[si];

        int count = 0;
        for (int dice = 1; dice <= 6; dice++)
            if (si + dice <= ei)
                count += boardPathMemorization(si + dice, ei, dp);

        return dp[si] = count;
    }

    public int boardPathTable(int ei) {

        int[] dp = new int[ei + 1];
        dp[ei] = 1;

        for (int i = ei - 1; i >= 1; i--) {
            int count = 0;
            for (int dice = 1; dice <= 6; dice++)
                if (i + dice <= ei)
                    count += dp[i + dice];
            dp[i] = count;
        }

        return dp[1];
    }

    // **** arr contains jumps that can be taken at any position
    public int boardPathBiased(int[] arr, int si, int ei) {
        if (si == ei)
            return 1;

        int count = 0;
        for (int dice = 1; dice <= arr[si]; dice++)
            if (si + dice <= ei)
                count += boardPathBiased(arr, si + dice, ei);

        return count;
    }

    public int boardPathBiasedMemorization(int[] arr, int si, int ei, int[] dp) {
        if (si == ei)
            return dp[si] = 1;

        if (dp[si] != -1)
            return dp[si];

        int count = 0;
        for (int dice : arr)
            if (si + dice <= ei)
                count += boardPathBiasedMemorization(arr, si + dice, ei, dp);

        return dp[si] = count;
    }

    public int boardPathTableBiased(int[] arr, int ei) {

        int[] dp = new int[ei + 1];
        dp[ei] = 1;

        for (int i = ei - 1; i >= 1; i--) {
            int count = 0;
            for (int dice : arr)
                if (i + dice <= ei)
                    count += dp[i + dice];
            dp[i] = count;
        }

        return dp[1];
    }

    // **** arr contains jumps that can be taken at that particular position
    public int boardPathBiased2(int[] arr, int si, int ei) {
        if (si == ei)
            return 1;

        int count = 0;
        for (int dice : arr)
            if (si + dice <= ei)
                count += boardPathBiased2(arr, si + dice, ei);

        return count;
    }

    public int boardPathBiasedMemorization2(int[] arr, int si, int ei, int[] dp) {
        if (si == ei)
            return dp[si] = 1;

        if (dp[si] != -1)
            return dp[si];

        int count = 0;
        for (int dice = 1; dice <= arr[si]; dice++)
            if (si + dice <= ei)
                count += boardPathBiasedMemorization2(arr, si + dice, ei, dp);

        return dp[si] = count;
    }

    public int boardPathTableBiased2(int[] arr, int ei) {

        int[] dp = new int[ei + 1];
        dp[ei] = 1;

        for (int i = ei - 1; i >= 1; i--) {
            int count = 0;
            for (int dice = 1; dice <= arr[i]; dice++)
                if (i + dice <= ei)
                    count += dp[i + dice];
            dp[i] = count;
        }

        return dp[1];
    }

    public int boardPathBiasedMinMoves(int[] arr, int si, int ei) {
        if (si == ei)
            return 0;

        int minMoves = Integer.MAX_VALUE;
        for (int dice = 1; dice <= arr[si]; dice++)
            if (si + dice <= ei)
                minMoves = Math.min(minMoves, boardPathBiasedMinMoves(arr, si + dice, ei));

        return minMoves + 1;
    }

    public int boardPathBiasedMinMovesMemorization(int[] arr, int si, int ei, int[] dp) {
        if (si == ei)
            return dp[si] = 0;

        if (dp[si] != -1)
            return dp[si];

        int minMoves = Integer.MAX_VALUE;
        for (int dice = 1; dice <= arr[si]; dice++)
            if (si + dice <= ei)
                minMoves = Math.min(minMoves, boardPathBiasedMinMovesMemorization(arr, si + dice, ei, dp));

        return dp[si] = minMoves + 1;
    }

    public int boardPathTableBiasedTable(int[] arr, int ei) {

        int[] dp = new int[ei + 1];
        dp[ei] = 0;

        for (int i = ei - 1; i >= 1; i--) {
            int minMoves = 0;
            for (int dice : arr)
                if (i + dice <= ei)
                    minMoves = Math.min(minMoves, dp[i + dice]);
            dp[i] = minMoves;
        }

        return dp[1];
    }

}
