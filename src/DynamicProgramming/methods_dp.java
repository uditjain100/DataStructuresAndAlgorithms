package DynamicProgramming;

public class methods_dp {

    // ? Number Of Eggs = 2 (by Default)
    // *https://leetcode.com/problems/egg-drop-with-2-eggs-and-n-floors/
    public int eggDrop(int n) {
        int i = 0;
        while ((i * (i + 1) / 2) < n)
            i++;

        return i;
    }

    // *https://leetcode.com/problems/super-egg-drop/
    public int superEggDrop(int n, int k) {
        return 0;
    }

    public int uglyNumbers(int n) {
        int[] dp = new int[n];
        dp[0] = 1;

        int idxOf2 = 0;
        int idxOf3 = 0;
        int idxOf5 = 0;

        for (int i = 1; i < n; i++) {

            int val1 = dp[idxOf2] * 2;
            int val2 = dp[idxOf3] * 3;
            int val3 = dp[idxOf5] * 5;

            int min = Math.min(Math.min(val1, val2), val3);
            dp[i] = min;

            if (min == val1)
                idxOf2++;
            if (min == val2)
                idxOf3++;
            if (min == val3)
                idxOf5++;
        }

        return dp[n - 1];
    }

    public int superUglyNumbers(int[] arr, int n) {

        int[] dp = new int[n];
        dp[0] = 1;

        int[] index = new int[arr.length];

        for (int i = 0; i < n; i++) {

            int min = Integer.MAX_VALUE;
            for (int j = 0; j < arr.length; j++)
                min = Math.min(min, dp[index[j]] * arr[j]);
            dp[i] = min;

            for (int j = 0; j < arr.length; j++)
                if (min == dp[index[j]] * arr[j])
                    index[j]++;
        }

        return dp[n - 1];
    }

    public int[][] dir = { { 1, 2 }, { -1, 2 }, { 1, -2 }, { -1, -2 }, { 2, 1 }, { -2, 1 }, { 2, -1 }, { -2, -1 } };

    // *https://leetcode.com/problems/knight-probability-in-chessboard/
    public double knightProbability(int n, int k, int r, int c) {

        double[][] curr = new double[n][n];
        double[][] next = new double[n][n];

        curr[r][c] = 1;
        for (int m = 0; m < k; m++) {

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {

                    if (curr[i][j] != 0) {
                        for (int d = 0; d < dir.length; d++) {
                            int x = i + dir[d][0];
                            int y = j + dir[d][1];

                            if (x >= 0 && y >= 0 && x < n && y < n)
                                next[x][y] += curr[i][j] / 8.0;
                        }
                    }

                }
            }

            curr = next;
            next = new double[n][n];
        }

        double res = 0;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                res += curr[i][j];
        return res;
    }

    // ? ************************************** Kadane;s Algorithm

    public int maximumSumSubArray(int[] arr) {

        int maxSum = Integer.MIN_VALUE;
        int currSum = 0;

        for (int ele : arr) {
            currSum += ele;
            maxSum = Math.max(maxSum, currSum);

            if (currSum < 0)
                currSum = 0;
        }

        return maxSum;
    }

    public int minimumSumSubArray(int[] arr) {

        int minSum = Integer.MAX_VALUE;
        int currSum = 0;

        for (int ele : arr) {
            currSum += ele;
            minSum = Math.min(minSum, currSum);

            if (currSum > 0)
                currSum = 0;
        }

        return minSum;
    }

    public int maxSumCircularSubArray(int[] arr) {

        int maxSum = Integer.MIN_VALUE;
        int minSum = Integer.MAX_VALUE;

        int currMaxSum = 0;
        int currMinSum = 0;

        int totalSum = 0;

        for (int ele : arr) {
            totalSum += ele;

            currMaxSum += ele;
            maxSum = Math.max(maxSum, currMaxSum);

            currMinSum += ele;
            minSum = Math.min(minSum, currMaxSum);

            if (currMaxSum < 0)
                currMaxSum = 0;

            if (currMinSum > 0)
                currMinSum = 0;
        }

        return Math.max(maxSum, totalSum - minSum);
    }

    public int minSumCircularSubArray(int[] arr) {

        int maxSum = Integer.MIN_VALUE;
        int minSum = Integer.MAX_VALUE;

        int currMaxSum = 0;
        int currMinSum = 0;

        int totalSum = 0;

        for (int ele : arr) {
            totalSum += ele;

            currMaxSum += ele;
            maxSum = Math.max(maxSum, currMaxSum);

            currMinSum += ele;
            minSum = Math.min(minSum, currMaxSum);

            if (currMaxSum < 0)
                currMaxSum = 0;

            if (currMinSum > 0)
                currMinSum = 0;
        }

        return Math.min(minSum, totalSum - maxSum);
    }

    public int kConcatenationMaxSumSubArray(int[] arr, int k) {
        if (k == 1)
            return maximumSumSubArray(arr);

        int totalSum = 0;
        for (int ele : arr)
            totalSum += ele;

        return maxSumCircularSubArray(arr) + ((totalSum > 0) ? ((k - 2) * totalSum) : 0);
    }

    public int maxSumSubArrayWithAtLeastKSize(int[] arr, int k) {

        int n = arr.length;
        int[] dp = new int[n];

        int maxSum = Integer.MIN_VALUE;
        int currSum = 0;

        for (int i = 0; i < n; i++) {
            currSum += arr[i];
            maxSum = Math.max(maxSum, currSum);
            dp[i] = maxSum;

            if (currSum < 0)
                currSum = 0;
        }

        int sumOfCurrentWindow = 0;

        for (int i = 0; i < k; i++)
            sumOfCurrentWindow += arr[i];

        maxSum = sumOfCurrentWindow;
        for (int i = k; i < n; i++) {
            sumOfCurrentWindow -= arr[i - k];
            sumOfCurrentWindow += arr[i];

            maxSum = Math.max(maxSum, Math.max(sumOfCurrentWindow, sumOfCurrentWindow + dp[i - k]));
        }

        return maxSum;
    }

    public int maxDifferenceOf0sAnd1sInBinaryString(String str) {
        int maxDiff = Integer.MIN_VALUE;
        int currDiff = 0;

        for (char ch : str.toCharArray()) {

            currDiff += (ch == '1') ? -1 : 1;
            maxDiff = Math.max(maxDiff, currDiff);

            if (currDiff < 0)
                currDiff = 0;
        }

        return maxDiff;
    }

    public double champagneTower(int poured, int row, int col) {

        double[][] dp = new double[row + 1][row + 1];
        dp[0][0] = (double) poured;

        for (int i = 0; i < dp.length - 1; i++) {
            for (int j = 0; j <= i; j++) {
                double rem = (dp[i][j] - 1) / 2.0;
                if (rem > 0.0) {
                    dp[i + 1][j] += rem;
                    dp[i + 1][j + 1] += rem;
                }
            }
        }

        return (dp[row][col] > 1.0) ? 1.0 : dp[row][col];
    }

    public int maximumSubMatrixWithAll1s(int[][] arr) {

        int[][] dp = new int[arr.length][arr[0].length];

        int res = Integer.MIN_VALUE;
        for (int i = arr.length - 1; i >= 0; i--) {
            for (int j = arr[0].length - 1; j >= 0; j--) {
                if (arr[i][j] == 0)
                    dp[i][j] = 0;
                else if (i == arr.length - 1 || j == arr[0].length - 1)
                    dp[i][j] = arr[i][j];
                else
                    dp[i][j] = 1 + Math.min(dp[i + 1][j], Math.min(dp[i][j + 1], dp[i + 1][j + 1]));

                res = Math.max(res, dp[i][j]);
            }
        }

        return res * res;
    }

    // *https://www.geeksforgeeks.org/temple-offerings/
    public int templeOffering(int[] arr) {
        int n = arr.length;
        int[][] dp = new int[n][2];
        dp[0][0] = 1;

        for (int i = 1; i < n; i++) {
            if (arr[i - 1] < arr[i])
                dp[i][0] = dp[i - 1][0] + 1;
            else
                dp[i][0] = 1;
        }

        dp[n - 1][1] = 1;
        for (int i = n - 2; i >= 0; i--) {
            if (arr[i + 1] < arr[i])
                dp[i][1] = dp[i + 1][1] + 1;
            else
                dp[i][1] = 1;
        }

        int offerings = 0;
        for (int i = 0; i < n; i++)
            offerings += Math.max(dp[i][0], dp[i][1]);

        return offerings;
    }

    // Todo :
    public int optimalStrategy(int[] arr) {
        int[][] dp = new int[arr.length + 1][arr.length + 1];
        for (int gap = 0; gap < arr.length; gap++) {
            for (int i = 0, j = gap; j < arr.length; i++, j++) {
                if (gap == 0)
                    dp[i][j] = arr[i];
                else if (gap == 1)
                    dp[i][j] = Math.max(arr[i], arr[j]);
                else {
                    int res1 = arr[i] + Math.min(dp[i + 1][j - 1], dp[i + 2][j]);
                    int res2 = arr[j] + Math.min(dp[i + 1][j - 1], dp[i][j - 2]);
                    dp[i][j] = Math.max(res1, res2);
                }
            }
        }
        return dp[0][arr.length - 1];
    }

}
