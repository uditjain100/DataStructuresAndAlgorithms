package DynamicProgramming;

public class cutProblems {

    public int matrixChainMultiplication(int[] arr, int si, int ei) {
        if (si + 1 >= ei)
            return 0;

        int minCost = Integer.MAX_VALUE;
        for (int i = si + 1; i <= ei - 1; i++) {
            int leftCost = matrixChainMultiplication(arr, si, i);
            int rightCost = matrixChainMultiplication(arr, i, ei);
            int selfCost = arr[si] * arr[i] * arr[ei];
            minCost = Math.min(minCost, leftCost + rightCost + selfCost);
        }

        return minCost;
    }

    public int matrixChainMultiplicationMemorization(int[] arr, int si, int ei, int[][] dp) {
        if (si + 1 >= ei)
            return dp[si][ei] = 0;

        if (dp[si][ei] != -1)
            return dp[si][ei];

        int minCost = Integer.MAX_VALUE;
        for (int i = si + 1; i <= ei - 1; i++) {
            int leftCost = matrixChainMultiplicationMemorization(arr, si, i, dp);
            int rightCost = matrixChainMultiplicationMemorization(arr, i, ei, dp);
            int selfCost = arr[si] * arr[i] * arr[ei];
            minCost = Math.min(minCost, leftCost + rightCost + selfCost);
        }

        return dp[si][ei] = minCost;
    }

    public int matrixMultiplicationTabulation(int[] arr) {

        int[][] dp = new int[arr.length][arr.length];
        String[][] res = new String[arr.length][arr.length];

        String ans = "";
        for (int gap = 1; gap < arr.length; gap++) {
            for (int si = 0, ei = gap; ei < arr.length; si++, ei++) {
                if (si + 1 == ei) {
                    dp[si][ei] = 0;
                    res[si][ei] = "" + (char) (si + 'A');
                    continue;
                }

                int minCost = Integer.MAX_VALUE;
                ans = "";
                for (int i = si + 1; i < ei; i++) {
                    int leftCost = dp[si][i];
                    int rightCost = dp[i][ei];
                    int myCost = leftCost + (arr[si] * arr[i] * arr[ei]) + rightCost;
                    if (minCost > myCost) {
                        minCost = myCost;
                        ans = "(" + res[si][i] + res[i][ei] + ")";
                    }
                }
                dp[si][ei] = minCost;
                res[si][ei] = ans;
            }
        }

        System.out.println(res[0][arr.length - 1]);
        return dp[0][arr.length - 1];
    }

    public int optimalBST(int[] freq, int si, int ei, int[] prefixSum) {

        int minCost = Integer.MAX_VALUE;
        for (int i = si; i <= ei; i++) {

            int leftCost = (i == si) ? 0 : optimalBST(freq, si, i - 1, prefixSum);
            int rightCost = (i == ei) ? 0 : optimalBST(freq, i + 1, ei, prefixSum);
            int selfCost = prefixSum[ei + 1] - prefixSum[si];

            minCost = Math.min(minCost, leftCost + selfCost + rightCost);
        }

        return minCost;
    }

    public int optimalBSTMemorization(int[] freq, int si, int ei, int[] prefixSum, int[][] dp) {

        if (dp[si][ei] != -1)
            return dp[si][ei];

        int minCost = Integer.MAX_VALUE;
        for (int i = si; i <= ei; i++) {

            int leftCost = (i == si) ? 0 : optimalBSTMemorization(freq, si, i - 1, prefixSum, dp);
            int rightCost = (i == ei) ? 0 : optimalBSTMemorization(freq, i + 1, ei, prefixSum, dp);
            int selfCost = prefixSum[ei + 1] - prefixSum[si];

            minCost = Math.min(minCost, leftCost + selfCost + rightCost);
        }

        return dp[si][ei] = minCost;
    }

    public int optimalBST_Table(int[] freq) {

        int[][] dp = new int[freq.length][freq.length];
        int[] prefixSum = new int[freq.length + 1];

        for (int i = 1; i < prefixSum.length; i++)
            prefixSum[i] = prefixSum[i - 1] + freq[i - 1];

        for (int gap = 0; gap < freq.length; gap++) {
            for (int si = 0, ei = gap; ei < freq.length; si++, ei++) {

                int minCost = Integer.MAX_VALUE;
                for (int i = si; i <= ei; i++) {
                    int leftCost = (i == si) ? 0 : dp[si][i - 1];
                    int rightCost = (i == ei) ? 0 : dp[i + 1][ei];
                    int selfCost = prefixSum[ei + 1] - prefixSum[si];

                    minCost = Math.min(minCost, leftCost + selfCost + rightCost);
                }

                dp[si][ei] = minCost;
            }
        }
        return dp[0][freq.length - 1];
    }

    // *https://leetcode.com/problems/burst-balloons/
    public int burstBalloons(int[] arr, int si, int ei, int[][] dp) {

        if (dp[si][ei] != -1)
            return dp[si][ei];

        int leftVal = (si == 0) ? 1 : arr[si - 1];
        int rightVal = (ei == arr.length - 1) ? 1 : arr[ei + 1];

        int maxCost = 0;
        for (int i = si; i <= ei; i++) {

            int leftCost = (i == si) ? 0 : burstBalloons(arr, si, i - 1, dp);
            int rightCost = (i == ei) ? 0 : burstBalloons(arr, i + 1, ei, dp);
            int selfCost = leftVal * arr[i] * rightVal;

            maxCost = Math.max(maxCost, leftCost + selfCost + rightCost);
        }

        return dp[si][ei] = maxCost;
    }

    // *https://www.geeksforgeeks.org/cutting-a-rod-dp-13/
    public int rodCutting(int[] arr, int n) {
        if (n == 0)
            return 0;

        int maxCost = 0;
        for (int i = 0; i < n; i++) {
            int selfCost = arr[i];
            int rightCost = rodCutting(arr, n - i - 1);

            maxCost = Math.max(maxCost, selfCost + rightCost);
        }
        return maxCost;
    }

    public int rodCuttingMemorization(int[] arr, int n, int[] dp) {
        if (n == 0)
            return dp[n] = 0;

        if (dp[n] != -1)
            return dp[n];

        int maxCost = 0;
        for (int i = 0; i < n; i++) {
            int selfCost = arr[i];
            int rightCost = rodCuttingMemorization(arr, n - i - 1, dp);

            maxCost = Math.max(maxCost, selfCost + rightCost);
        }
        return dp[n] = maxCost;
    }

    public int rodCuttingTabulation(int[] arr) {
        int n = arr.length;

        int[] dp = new int[n];
        dp[0] = arr[0];

        for (int i = 1; i < n + 1; i++) {

            int maxCost = Integer.MIN_VALUE;
            for (int j = 0; j < n; j++)
                maxCost = Math.max(maxCost, dp[j] + dp[i - j - 1]);

            dp[i] = maxCost;
        }

        return dp[n];
    }

    // *https://leetcode.com/problems/palindrome-partitioning-ii/
    public int palindromePartitioning(String str, int idx, boolean[][] isPalindrome) {
        if (idx == str.length() - 1 || isPalindrome[idx][str.length() - 1])
            return 0;

        int minCuts = str.length();
        for (int i = idx + 1; i <= str.length(); i++)
            if (isPalindrome[idx][i])
                minCuts = Math.min(minCuts, 1 + palindromePartitioning(str, i + 1, isPalindrome));

        return minCuts;
    }

    public int palindromePartitioningMemorization(String str, int idx, boolean[][] isPalindrome, int[] dp) {

        if (idx == str.length() - 1 || isPalindrome[idx][str.length() - 1])
            return dp[idx] = 0;

        if (dp[idx] != -1)
            return dp[idx];

        int minCuts = str.length();
        for (int i = idx; i < str.length(); i++)
            if (isPalindrome[idx][i])
                minCuts = Math.min(minCuts, 1 + palindromePartitioningMemorization(str, i + 1, isPalindrome, dp));

        return dp[idx] = minCuts;
    }

    public int palindromePartitioningTabulation(String str, boolean[][] isPalindrome) {

        int[] dp = new int[str.length()];

        for (int idx = str.length(); idx >= 0; idx--) {
            if (idx == str.length() - 1 || isPalindrome[idx][str.length() - 1])
                continue;

            int minCuts = str.length();
            for (int i = idx; i < str.length(); i++)
                if (isPalindrome[idx][i])
                    minCuts = Math.min(minCuts, 1 + dp[i + 1]);

            dp[idx] = minCuts;
        }

        return dp[0];
    }

    // *https://www.geeksforgeeks.org/boolean-parenthesization-problem-dp-37/
    public int[] booleanParenthesization(int[] arr, char[] operators, int si, int ei) {

        if (si + 1 == ei) {
            int res = 0;
            if (operators[si] == '&')
                res = arr[si] & arr[ei];
            else if (operators[si] == '|')
                res = arr[si] | arr[ei];
            else
                res = arr[si] ^ arr[ei];

            return (res == 0) ? new int[] { 1, 0 } : new int[] { 0, 1 };
        }

        int[] max1s = new int[2];
        for (int i = si; i < ei; i++) {

            int[] leftAns = (i != si) ? booleanParenthesization(arr, operators, si, i)
                    : (arr[i] == 1) ? new int[] { 0, 1 } : new int[] { 1, 0 };
            int[] rightAns = (i != ei) ? booleanParenthesization(arr, operators, i + 1, ei)
                    : (arr[i] == 1) ? new int[] { 0, 1 } : new int[] { 1, 0 };

            int[] res = new int[2];
            if (operators[si] == '&') {
                res[0] = (leftAns[0] * rightAns[1]) + (rightAns[0] * leftAns[1]) + (leftAns[0] * rightAns[0]);
                res[1] = (leftAns[1] * rightAns[1]);
            } else if (operators[si] == '|') {
                res[0] = (leftAns[0] * rightAns[0]);
                res[1] = (leftAns[0] * rightAns[1]) + (rightAns[0] * leftAns[1]) + (leftAns[1] * rightAns[1]);
            } else {
                res[0] = (leftAns[0] * rightAns[0]) + (leftAns[1] * rightAns[1]);
                res[1] = (leftAns[0] * rightAns[1]) + (leftAns[1] * rightAns[0]);
            }

            if (max1s[1] < res[1])
                max1s = res;
        }

        return max1s;
    }

    public int[] booleanParenthesizationMemorization(int[] arr, char[] operators, int si, int ei, int[][] dpTrue,
            int[][] dpFalse) {

        if (si + 1 == ei) {
            int res = 0;
            if (operators[si] == '&')
                res = arr[si] & arr[ei];
            else if (operators[si] == '|')
                res = arr[si] | arr[ei];
            else
                res = arr[si] ^ arr[ei];

            if (res == 1)
                dpTrue[si][ei] = 1;
            else
                dpFalse[si][ei] = 1;

            return (res == 0) ? new int[] { 1, 0 } : new int[] { 0, 1 };
        }

        if (dpTrue[si][ei] != -1 && dpFalse[si][ei] != -1)
            return new int[] { dpFalse[si][ei], dpTrue[si][ei] };

        int[] max1s = new int[2];
        for (int i = si; i < ei; i++) {

            int[] leftAns = (i != si) ? booleanParenthesizationMemorization(arr, operators, si, i, dpTrue, dpFalse)
                    : (arr[i] == 1) ? new int[] { 0, 1 } : new int[] { 1, 0 };
            int[] rightAns = (i != ei) ? booleanParenthesizationMemorization(arr, operators, i + 1, ei, dpTrue, dpFalse)
                    : (arr[i] == 1) ? new int[] { 0, 1 } : new int[] { 1, 0 };

            int[] res = new int[2];
            if (operators[si] == '&') {
                res[0] = (leftAns[0] * rightAns[1]) + (rightAns[0] * leftAns[1]) + (leftAns[0] * rightAns[0]);
                res[1] = (leftAns[1] * rightAns[1]);
            } else if (operators[si] == '|') {
                res[0] = (leftAns[0] * rightAns[0]);
                res[1] = (leftAns[0] * rightAns[1]) + (rightAns[0] * leftAns[1]) + (leftAns[1] * rightAns[1]);
            } else {
                res[0] = (leftAns[0] * rightAns[0]) + (leftAns[1] * rightAns[1]);
                res[1] = (leftAns[0] * rightAns[1]) + (leftAns[1] * rightAns[0]);
            }

            if (max1s[1] < res[1])
                max1s = res;
        }

        dpFalse[si][ei] = max1s[0];
        dpTrue[si][ei] = max1s[1];
        return max1s;
    }

    public int booleanParenthesizationTabulation(int[] arr, char[] operators) {

        int n = arr.length;
        int[][] dpTrue = new int[n][n];
        int[][] dpFalse = new int[n][n];

        for (int gap = 0; gap < n; gap++) {
            for (int si = 0, ei = gap; si < n && ei < n; si++, ei++) {

                if (si + 1 == ei) {
                    int res = 0;
                    if (operators[si] == '&')
                        res = arr[si] & arr[ei];
                    else if (operators[si] == '|')
                        res = arr[si] | arr[ei];
                    else
                        res = arr[si] ^ arr[ei];

                    if (res == 1)
                        dpTrue[si][ei] = 1;
                    else
                        dpFalse[si][ei] = 1;
                    continue;
                }

                int[] max1s = new int[2];
                for (int i = si; i < ei; i++) {

                    int[] leftAns = (i != si)
                            ? new int[] { dpFalse[si][i], dpTrue[si][i] }
                            : (arr[i] == 1) ? new int[] { 0, 1 } : new int[] { 1, 0 };
                    int[] rightAns = (i != ei)
                            ? new int[] { dpFalse[i + 1][ei], dpTrue[i + 1][ei] }
                            : (arr[i] == 1) ? new int[] { 0, 1 } : new int[] { 1, 0 };

                    int[] res = new int[2];
                    if (operators[si] == '&') {
                        res[0] = (leftAns[0] * rightAns[1]) + (rightAns[0] * leftAns[1]) + (leftAns[0] * rightAns[0]);
                        res[1] = (leftAns[1] * rightAns[1]);
                    } else if (operators[si] == '|') {
                        res[0] = (leftAns[0] * rightAns[0]);
                        res[1] = (leftAns[0] * rightAns[1]) + (rightAns[0] * leftAns[1]) + (leftAns[1] * rightAns[1]);
                    } else {
                        res[0] = (leftAns[0] * rightAns[0]) + (leftAns[1] * rightAns[1]);
                        res[1] = (leftAns[0] * rightAns[1]) + (leftAns[1] * rightAns[0]);
                    }

                    if (max1s[1] < res[1])
                        max1s = res;
                }

                dpFalse[si][ei] = max1s[0];
                dpTrue[si][ei] = max1s[1];
            }
        }

        return dpTrue[0][n - 1];
    }

}
