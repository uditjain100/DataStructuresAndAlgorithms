package DynamicProgramming;

import java.util.*;

public class LIS {

    public int[] longestIncreasingSubsequenceLeftToRight(int[] arr) {
        int n = arr.length;
        int[] dp = new int[n];
        dp[0] = 1;

        int lis = 0;
        for (int i = 1; i < n; i++) {

            int currMaxLength = 0;
            for (int j = i - 1; j >= 0; j--)
                if (arr[j] < arr[i])
                    currMaxLength = Math.max(currMaxLength, arr[j]);
            dp[i] = currMaxLength + 1;
            lis = Math.max(lis, dp[i]);
        }

        return dp;
    }

    public int[] longestDecreasingSubsequenceLeftToRight(int[] arr) {
        int n = arr.length;
        int[] dp = new int[n];
        dp[0] = 1;

        int lis = 0;
        for (int i = 1; i < n; i++) {

            int currMaxLength = 0;
            for (int j = i - 1; j >= 0; j--)
                if (arr[j] > arr[i])
                    currMaxLength = Math.max(currMaxLength, arr[j]);
            dp[i] = currMaxLength + 1;
            lis = Math.max(lis, dp[i]);
        }

        return dp;
    }

    public int[] longestIncreasingSubsequenceRightToLeft(int[] arr) {
        int n = arr.length;
        int[] dp = new int[n];
        dp[n - 1] = 1;

        int lis = 0;
        for (int i = n - 1; i >= 0; i--) {

            int currMaxLength = 0;
            for (int j = i + 1; j < n; j++)
                if (arr[j] < arr[i])
                    currMaxLength = Math.max(currMaxLength, arr[j]);
            dp[i] = currMaxLength + 1;
            lis = Math.max(lis, dp[i]);
        }

        return dp;
    }

    public int[] longestDecreasingSubsequenceRightToLeft(int[] arr) {
        int n = arr.length;
        int[] dp = new int[n];
        dp[n - 1] = 1;

        int lis = 0;
        for (int i = n - 1; i >= 0; i--) {

            int currMaxLength = 0;
            for (int j = i + 1; j < n; j++)
                if (arr[j] > arr[i])
                    currMaxLength = Math.max(currMaxLength, arr[j]);
            dp[i] = currMaxLength + 1;
            lis = Math.max(lis, dp[i]);
        }

        return dp;
    }

    public int[] longestIncDecSubsequence(int[] arr) {
        int n = arr.length;
        int[] dp = new int[n];

        int[] a = longestIncreasingSubsequenceLeftToRight(arr);
        int[] b = longestDecreasingSubsequenceRightToLeft(arr);

        for (int i = 0; i < n; i++)
            dp[i] = a[i] + b[i] - 1;

        int lids = 0;
        for (int ele : dp)
            lids = Math.max(lids, ele);
        return dp;
    }

    public int[] longestDecIncSubsequence(int[] arr) {
        int n = arr.length;
        int[] dp = new int[n];

        int[] a = longestDecreasingSubsequenceLeftToRight(arr);
        int[] b = longestIncreasingSubsequenceRightToLeft(arr);

        for (int i = 0; i < n; i++)
            dp[i] = a[i] + b[i] - 1;

        int lids = 0;
        for (int ele : dp)
            lids = Math.max(lids, ele);
        return dp;
    }

    public int[] longestBitonicSubsequence(int[] arr) {
        int n = arr.length;
        int[] dp = new int[n];

        int[] a = longestIncreasingSubsequenceLeftToRight(arr);
        int[] b = longestDecreasingSubsequenceLeftToRight(arr);
        int[] c = longestIncDecSubsequence(arr);

        for (int i = 0; i < n; i++)
            dp[i] = Math.max(c[i], Math.max(a[i], b[i]));

        int lbs = 0;
        for (int ele : dp)
            lbs = Math.max(lbs, ele);
        return dp;
    }

    public int[] longestInverseBitonicSubsequence(int[] arr) {
        int n = arr.length;
        int[] dp = new int[n];

        int[] a = longestIncreasingSubsequenceRightToLeft(arr);
        int[] b = longestDecreasingSubsequenceRightToLeft(arr);
        int[] c = longestDecIncSubsequence(arr);

        for (int i = 0; i < n; i++)
            dp[i] = Math.max(c[i], Math.max(a[i], b[i]));

        int libs = 0;
        for (int ele : dp)
            libs = Math.max(libs, ele);
        return dp;
    }

    public int[] maxSumIncSubsequenceLeftToRight(int[] arr) {
        int n = arr.length;
        int[] dp = new int[n];
        dp[0] = arr[0];
        int maxSum = arr[0];
        for (int i = 1; i < n; i++) {
            int sum = 0;
            for (int j = i - 1; j >= 0; j--)
                if (arr[j] < arr[i])
                    sum = Math.max(sum, dp[j]);
            dp[i] = sum + arr[i];
            maxSum = Math.max(maxSum, dp[i]);
        }
        return dp;
    }

    public int[] maxSumDecSubsequenceLeftToRight(int[] arr) {
        int n = arr.length;
        int[] dp = new int[n];
        dp[0] = arr[0];
        int maxSum = arr[0];
        for (int i = 1; i < n; i++) {
            int sum = 0;
            for (int j = i - 1; j >= 0; j--)
                if (arr[j] > arr[i])
                    sum = Math.max(sum, dp[j]);
            dp[i] = sum + arr[i];
            maxSum = Math.max(maxSum, dp[i]);
        }
        return dp;
    }

    public int[] maxSumIncSubsequenceRightToLeft(int[] arr) {
        int n = arr.length;
        int[] dp = new int[n];
        dp[n - 1] = arr[n - 1];
        int maxSum = arr[n - 1];
        for (int i = n - 1; i >= 0; i--) {
            int sum = 0;
            for (int j = i + 1; j < n; j++)
                if (arr[j] < arr[i])
                    sum = Math.max(sum, dp[j]);
            dp[i] = sum + arr[i];
            maxSum = Math.max(maxSum, dp[i]);
        }
        return dp;
    }

    public int[] maxSumDecSubsequenceRightToLeft(int[] arr) {
        int n = arr.length;
        int[] dp = new int[n];
        dp[n - 1] = arr[n - 1];
        int maxSum = arr[n - 1];
        for (int i = n - 1; i >= 0; i--) {
            int sum = 0;
            for (int j = i + 1; j < n; j++)
                if (arr[j] > arr[i])
                    sum = Math.max(sum, dp[j]);
            dp[i] = sum + arr[i];
            maxSum = Math.max(maxSum, dp[i]);
        }
        return dp;
    }

    public int[] maxSumIncDecSubsequence(int[] arr) {

        int[] a = maxSumIncSubsequenceLeftToRight(arr);
        int[] b = maxSumDecSubsequenceRightToLeft(arr);

        int[] dp = new int[arr.length];
        for (int i = 0; i < arr.length; i++)
            dp[i] = a[i] + b[i] - arr[i];

        int max = 0;
        for (int ele : dp)
            max = Math.max(max, ele);

        return dp;
    }

    public int[] maxSumDecIncSubsequence(int[] arr) {

        int[] a = maxSumDecSubsequenceLeftToRight(arr);
        int[] b = maxSumIncSubsequenceRightToLeft(arr);

        int[] dp = new int[arr.length];
        for (int i = 0; i < arr.length; i++)
            dp[i] = a[i] + b[i] - arr[i];

        int max = 0;
        for (int ele : dp)
            max = Math.max(max, ele);

        return dp;
    }

    public int[] maxSumBitonicSubsequence(int[] arr) {

        int[] a = maxSumIncSubsequenceLeftToRight(arr);
        int[] b = maxSumDecSubsequenceLeftToRight(arr);
        int[] c = maxSumIncDecSubsequence(arr);
        int[] dp = new int[arr.length];

        for (int i = 0; i < arr.length; i++)
            dp[i] = Math.max(Math.max(a[i], b[i]), c[i]);

        int max = 0;
        for (int ele : dp)
            max = Math.max(max, ele);

        return dp;
    }

    public int[] MaxSumInverseBitonicSubsequence(int[] arr) {

        int[] a = maxSumIncSubsequenceRightToLeft(arr);
        int[] b = maxSumDecSubsequenceRightToLeft(arr);
        int[] c = maxSumDecIncSubsequence(arr);
        int[] dp = new int[arr.length];

        for (int i = 0; i < arr.length; i++)
            dp[i] = Math.max(Math.max(a[i], b[i]), c[i]);

        int max = 0;
        for (int ele : dp)
            max = Math.max(max, ele);

        return dp;
    }

    public int minDeletionsToMakeArraySorted(int[] arr) {
        int n = arr.length;
        int[] dp = new int[n];
        dp[0] = 1;
        int lis = 1;
        for (int i = 1; i < n; i++) {
            int max = 0;
            for (int j = i - 1; j >= 0; j--) {
                if (arr[j] <= arr[i])
                    max = Math.max(max, dp[j]);
            }
            dp[i] = max + 1;
            lis = Math.max(lis, dp[i]);
        }
        return n - lis;
    }

    // *https://leetcode.com/problems/russian-doll-envelopes/
    public int russianDollsEnvelopes(int[][] arr) {
        if (arr.length == 0 || arr[0].length == 0 || arr[0].length == 1)
            return 0;

        Arrays.sort(arr, (a, b) -> (a[0] != b[0]) ? a[0] - b[0] : a[1] - b[1]);

        int[] dp = new int[arr.length];
        dp[0] = 1;
        int lis = 1;
        for (int i = 1; i < arr.length; i++) {
            int max = 0;
            for (int j = i - 1; j >= 0; j--) {
                if (arr[j][0] < arr[i][0] && arr[j][1] < arr[i][1])
                    max = Math.max(max, dp[j]);
            }
            dp[i] = max + 1;
            lis = Math.max(lis, dp[i]);
        }
        return lis;
    }

    // ? *********** Optimized i.e, O(nLog(n))
    public int longestIncreasingSubsequence(int[] arr) {
        int n = arr.length;

        int[] dp = new int[n + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = Integer.MIN_VALUE;

        for (int i = 0; i < n; i++) {
            int ubIdx = upperBound(dp, arr[i]);
            if (arr[i] > dp[ubIdx - 1] && arr[i] < dp[ubIdx])
                dp[i] = arr[i];
        }

        for (int i = n; i >= 0; i--)
            if (dp[i] != Integer.MAX_VALUE)
                return i;

        return 1;
    }

    public int upperBound(int[] arr, int target) {
        int n = arr.length;
        if (target < arr[0])
            return -1;
        if (target > arr[n - 1])
            return n - 1;

        int si = 0;
        int ei = n - 1;

        int res = -1;

        while (si <= ei) {
            int mid = si + (ei - si) / 2;

            if (arr[mid] <= target) {
                res = mid;
                si = mid + 1;
            } else
                ei = mid - 1;
        }
        return res;
    }

    // *https://www.geeksforgeeks.org/minimum-number-of-squares-whose-sum-equals-to-given-number-n/
    public int perfectSquares(int n) {
        int[] dp = new int[n + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        dp[1] = 1;

        for (int i = 2; i < n + 1; i++) {
            int num = 1;
            while (i - (num * num) >= 0) {
                dp[i] = Math.min(dp[i], dp[i - num * num] + 1);
                num++;
            }
        }
        return dp[n];
    }

    // *https://www.geeksforgeeks.org/highway-billboard-problem/
    public int highwayBillboard(int highwayLength, int[] arr, int[] cost, int gap) {

        int[] dp = new int[arr.length];
        dp[arr[0]] = cost[0];

        int maxCost = 0;
        for (int i = 1; i < arr.length; i++) {
            int currMax = 0;
            for (int j = i - 1; j >= 0; j--)
                if (arr[j] - arr[i] > gap)
                    currMax = Math.max(currMax, dp[j]);

            dp[i] = currMax + cost[i];
            maxCost = Math.max(maxCost, dp[i]);
        }

        return maxCost;
    }
}
