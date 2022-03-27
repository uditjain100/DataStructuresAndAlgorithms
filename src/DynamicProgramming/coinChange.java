package DynamicProgramming;

public class coinChange {

    // ? Memorization

    // ****** Coin Change, Target Sum, SubSet Sum, 0/1 KnapSack

    // ? ************************************** Infinite Coins

    public int permutationsInfiniteBinomial(int[] arr, int sum, int target) {
        if (sum == target)
            return 1;

        int count = 0;
        for (int coinIdx = 0; coinIdx < arr.length; coinIdx++)
            if (sum + arr[coinIdx] <= target)
                count += permutationsInfiniteBinomial(arr, sum + arr[coinIdx], target);
        return count;
    }

    public int permutationsInfiniteBinomialMemorization(int[] arr, int sum, int target, int[] dp) {
        if (sum == target)
            return dp[sum] = 1;

        if (dp[sum] != -1)
            return dp[sum];

        int count = 0;
        for (int coinIdx = 0; coinIdx < arr.length; coinIdx++)
            if (sum + arr[coinIdx] <= target)
                count += permutationsInfiniteBinomialMemorization(arr, sum + arr[coinIdx], target, dp);
        return dp[sum] = count;
    }

    public int permutationsInfiniteBinomialTabulation(int[] arr, int target) {

        int[] dp = new int[target + 1];

        for (int i = dp.length - 1; i >= 0; i--) {

            if (i == target) {
                dp[i] = 1;
                continue;
            }

            int count = 0;
            for (int coinIdx = 0; coinIdx < arr.length; coinIdx++)
                if (i + arr[coinIdx] <= target)
                    count += dp[i + arr[coinIdx]];
            dp[i] = count;
        }

        return dp[0];
    }

    public int permutationsInfiniteSubSequence(int[] arr, int coinIdx, int sum, int target) {
        if (coinIdx == arr.length && sum == target)
            return 1;
        if (coinIdx == arr.length)
            return 0;

        int count = 0;
        if (sum + arr[coinIdx] <= target)
            count += permutationsInfiniteSubSequence(arr, 0, sum + arr[coinIdx], target);
        count += permutationsInfiniteSubSequence(arr, coinIdx + 1, sum, target);
        return count;
    }

    public int permutationsInfiniteSubSequenceMemorization(int[] arr, int coinIdx, int sum, int target, int[] dp) {
        if (coinIdx == arr.length && sum == target)
            return dp[sum] = 1;
        if (coinIdx == arr.length)
            return dp[sum] = 0;

        if (dp[sum] != -1)
            return dp[sum];

        int count = 0;
        if (sum + arr[coinIdx] <= target)
            count += permutationsInfiniteSubSequenceMemorization(arr, 0, sum + arr[coinIdx], target, dp);
        count += permutationsInfiniteSubSequenceMemorization(arr, coinIdx + 1, sum, target, dp);
        return dp[sum] = count;
    }

    // **** {Not Possible Till Date} Permutation Infinite, subsequence Tabulation

    public int combinationsInfiniteBinomial(int[] arr, int coinIdx, int sum, int target) {
        if (coinIdx == arr.length && sum == target)
            return 1;
        if (coinIdx == arr.length)
            return 0;

        int count = 0;
        for (int coin = coinIdx; coin < arr.length; coin++)
            if (sum + arr[coin] <= target)
                count += combinationsInfiniteBinomial(arr, coin, sum + arr[coin], target);
        return count;
    }

    public int combinationsInfiniteBinomialMemorization(int[] arr, int coinIdx, int sum, int target, int[][] dp) {
        if (coinIdx == arr.length && sum == target)
            return dp[coinIdx][sum] = 1;
        if (coinIdx == arr.length)
            return dp[coinIdx][sum] = 0;

        if (dp[coinIdx][sum] != -1)
            return dp[coinIdx][sum];

        int count = 0;
        for (int coin = coinIdx; coin < arr.length; coin++)
            if (sum + arr[coin] <= target)
                count += combinationsInfiniteBinomialMemorization(arr, coin, sum + arr[coin], target, dp);
        return dp[coinIdx][sum] = count;
    }

    public int combinationsInfiniteBinomialTabulation(int[] arr, int target) {

        int[][] dp = new int[arr.length + 1][target + 1];

        for (int i = arr.length; i >= 0; i--) {
            for (int j = target; j >= 0; j--) {

                if (j == target) {
                    dp[i][j] = 1;
                    continue;
                }

                int count = 0;
                for (int coin = i; coin < arr.length; coin++)
                    if (j + arr[coin] <= target)
                        count += dp[coin][j + arr[coin]];
                dp[i][j] = count;
            }
        }

        return dp[0][0];
    }

    // Todo :
    public int combinationsInfiniteTricky(int[] arr, int target) {

        int[] dp = new int[target + 1];
        dp[0] = 1;

        for (int coinIdx : arr)
            for (int i = coinIdx; i <= target; i++)
                dp[i] += dp[i - coinIdx];

        return dp[target];
    }

    public int combinationsInfiniteSubSequence(int[] arr, int coinIdx, int sum, int target) {
        if (coinIdx == arr.length && sum == target)
            return 1;
        if (coinIdx == arr.length)
            return 0;

        int count = 0;
        if (sum + arr[coinIdx] <= target)
            count += combinationsInfiniteSubSequence(arr, coinIdx, sum + arr[coinIdx], target);
        count += combinationsInfiniteSubSequence(arr, coinIdx + 1, sum, target);

        return count;
    }

    public int combinationsInfiniteSubSequenceMemorization(int[] arr, int coinIdx, int sum, int target, int[][] dp) {
        if (coinIdx == arr.length && sum == target)
            return dp[coinIdx][sum] = 1;
        if (coinIdx == arr.length)
            return dp[coinIdx][sum] = 0;

        if (dp[coinIdx][sum] != -1)
            return dp[coinIdx][sum];

        int count = 0;
        if (sum + arr[coinIdx] <= target)
            count += combinationsInfiniteSubSequenceMemorization(arr, coinIdx, sum + arr[coinIdx], target, dp);
        count += combinationsInfiniteSubSequenceMemorization(arr, coinIdx + 1, sum, target, dp);

        return dp[coinIdx][sum] = count;
    }

    public int combinationsInfiniteSubSequenceTabulation(int[] arr, int target) {

        int[][] dp = new int[arr.length + 1][target + 1];

        for (int i = arr.length; i >= 0; i--) {
            for (int j = target; j >= 0; j--) {

                if (i == arr.length && j == target)
                    dp[i][j] = 1;
                else if (i == arr.length)
                    dp[i][j] = 0;
                else {
                    int count = 0;
                    if (j + arr[i] <= target)
                        count += dp[i][j + arr[i]];
                    count += dp[i + 1][j];
                    dp[i][j] = count;
                }
            }
        }

        return dp[0][0];
    }

    // ? ************************************** Finite Coins

    public int permutationsFiniteBinomial(int[] arr, int sum, int target, boolean[] visited) {
        if (sum == target)
            return 1;

        int count = 0;
        for (int coinIdx = 0; coinIdx < arr.length; coinIdx++)
            if (!visited[coinIdx] && sum + arr[coinIdx] < target) {
                visited[coinIdx] = true;
                count += permutationsFiniteBinomial(arr, sum + arr[coinIdx], target, visited);
                visited[coinIdx] = false;
            }
        return count;
    }

    public int permutationsFiniteSubsequence(int[] arr, int coinIdx, int sum, int target, boolean[] visited) {
        if (sum == target)
            return 1;

        int count = 0;
        if (!visited[coinIdx] && sum + arr[coinIdx] < target) {
            visited[coinIdx] = true;
            count += permutationsFiniteSubsequence(arr, 0, sum + arr[coinIdx], target, visited);
            visited[coinIdx] = false;
        }
        count += permutationsFiniteSubsequence(arr, coinIdx + 1, sum, target, visited);
        return count;
    }

    public int combinationsFiniteBinomial(int[] arr, int coinIdx, int sum, int target) {
        if (coinIdx == arr.length && sum == target)
            return 1;
        if (coinIdx == arr.length)
            return 0;

        int count = 0;
        for (int coin = coinIdx; coin < arr.length; coin++)
            if (sum + arr[coin] <= target)
                count += combinationsFiniteBinomial(arr, coin + 1, sum + arr[coin], target);
        return count;
    }

    public int combinationsFiniteBinomialMemorization(int[] arr, int coinIdx, int sum, int target, int[][] dp) {
        if (coinIdx == arr.length && sum == target)
            return dp[coinIdx][sum] = 1;
        if (coinIdx == arr.length)
            return dp[coinIdx][sum] = 0;

        if (dp[coinIdx][sum] != -1)
            return dp[coinIdx][sum];

        int count = 0;
        for (int coin = coinIdx; coin < arr.length; coin++)
            if (sum + arr[coin] <= target)
                count += combinationsFiniteBinomialMemorization(arr, coin + 1, sum + arr[coin], target, dp);
        return dp[coinIdx][sum] = count;
    }

    public int combinationsFiniteBinomialTabulation(int[] arr, int target) {

        int[][] dp = new int[arr.length + 1][target + 1];

        for (int i = arr.length; i >= 0; i--) {
            for (int j = target; j >= 0; j--) {

                if (j == target) {
                    dp[i][j] = 1;
                    continue;
                }

                int count = 0;
                for (int coin = i; coin < arr.length; coin++)
                    if (j + arr[coin] <= target)
                        count += dp[coin + 1][j + arr[coin]];
                dp[i][j] = count;
            }
        }

        return dp[0][0];
    }

    public int combinationsFiniteSubSequence(int[] arr, int coinIdx, int sum, int target) {
        if (coinIdx == arr.length && sum == target)
            return 1;
        if (coinIdx == arr.length)
            return 0;

        int count = 0;
        if (sum + arr[coinIdx] <= target)
            count += combinationsFiniteSubSequence(arr, coinIdx + 1, sum + arr[coinIdx], target);
        count += combinationsFiniteSubSequence(arr, coinIdx + 1, sum, target);
        return count;
    }

    public int combinationsFiniteSubSequenceMemorization(int[] arr, int coinIdx, int sum, int target, int[][] dp) {
        if (coinIdx == arr.length && sum == target)
            return dp[coinIdx][sum] = 1;
        if (coinIdx == arr.length)
            return dp[coinIdx][sum] = 0;

        if (dp[coinIdx][sum] != -1)
            return dp[coinIdx][sum];

        int count = 0;
        if (sum + arr[coinIdx] <= target)
            count += combinationsFiniteSubSequenceMemorization(arr, coinIdx + 1, sum + arr[coinIdx], target, dp);
        count += combinationsFiniteSubSequenceMemorization(arr, coinIdx + 1, sum, target, dp);

        return dp[coinIdx][sum] = count;
    }

    public int combinationsFiniteSubSequenceTabulation(int[] arr, int target) {

        int[][] dp = new int[arr.length + 1][target + 1];

        for (int i = arr.length; i >= 0; i--) {
            for (int j = target; j >= 0; j--) {

                if (i == arr.length && j == target)
                    dp[i][j] = 1;
                else if (i == arr.length)
                    dp[i][j] = 0;
                else {
                    int count = 0;
                    if (j + arr[i] <= target)
                        count += dp[i + 1][j + arr[i]];
                    count += dp[i + 1][j];
                    dp[i][j] = count;
                }
            }
        }

        return dp[0][0];
    }

    // ***************************************** KnapSack (Only Combinations)

    // ? ******************************** Infinite (Unbounded)

    public int knapSackUnBoundedBinomial(int[] price, int[] weight, int weightIdx, int currWeight, int target) {
        if (weightIdx == weight.length || currWeight == target)
            return 0;

        int maxPrice = Integer.MIN_VALUE;
        for (int wt = weightIdx; wt <= weight.length; wt++)
            if (currWeight + weight[wt] <= target)
                maxPrice = Math.max(maxPrice,
                        knapSackUnBoundedBinomial(price, weight, wt, currWeight + weight[wt], target));
        return maxPrice + price[weightIdx];
    }

    public int knapSackUnBoundedBinomialMemorization(int[] price, int[] weight, int weightIdx, int currWeight,
            int target,
            int[][] dp) {
        if (weightIdx == weight.length || currWeight == target)
            return dp[weightIdx][currWeight] = 0;

        if (dp[weightIdx][currWeight] != -1)
            return dp[weightIdx][currWeight];

        int maxPrice = Integer.MIN_VALUE;
        for (int wt = weightIdx; wt <= weight.length; wt++)
            if (currWeight + weight[wt] <= target)
                maxPrice = Math.max(maxPrice,
                        knapSackUnBoundedBinomialMemorization(price, weight, wt, currWeight + weight[wt], target,
                                dp));
        return dp[weightIdx][currWeight] = maxPrice + price[weightIdx];
    }

    public int knapSackUnBoundedBinomialTabulation(int[] price, int[] weight, int target) {
        int[][] dp = new int[weight.length + 1][target + 1];

        for (int i = weight.length; i >= 0; i--) {
            for (int j = target; j >= 0; j--) {

                if (i == weight.length || j == target)
                    return dp[i][j] = 0;

                int maxPrice = Integer.MIN_VALUE;
                for (int wt = i; wt <= weight.length; wt++)
                    if (j + weight[wt] <= target)
                        maxPrice = Math.max(maxPrice, dp[wt][j + weight[wt]]);
                dp[i][j] = maxPrice + price[i];
            }
        }
        return dp[0][0];
    }

    // Todo :
    public int knapSackUnBoundedTricky(int[] price, int[] weight, int target) {
        int[] dp = new int[target + 1];
        dp[0] = 0;

        for (int i = 0; i < weight.length; i++)
            for (int j = weight[i]; j <= target; j++)
                dp[j] = Math.max(dp[j], dp[j - weight[i]] + price[i]);

        return dp[target];
    }

    public int knapSackUnBoundedSubsequence(int[] price, int[] weight, int weightIdx, int currWeight, int target) {
        if (weightIdx == weight.length || currWeight == target)
            return 0;

        int maxPrice = Integer.MIN_VALUE;
        if (currWeight + weight[weightIdx] <= target)
            maxPrice = Math.max(maxPrice, knapSackUnBoundedSubsequence(price, weight,
                    weightIdx, currWeight + weight[weightIdx], target));
        maxPrice = Math.max(maxPrice, knapSackUnBoundedSubsequence(price, weight, weightIdx + 1, currWeight, target));
        return maxPrice + price[weightIdx];
    }

    public int knapSackUnBoundedSubsequenceMemorization(int[] price, int[] weight, int weightIdx, int currWeight,
            int target,
            int[][] dp) {
        if (weightIdx == weight.length || currWeight == target)
            return dp[weightIdx][currWeight] = 0;

        if (dp[weightIdx][currWeight] != -1)
            return dp[weightIdx][currWeight];

        int maxPrice = Integer.MIN_VALUE;
        if (currWeight + weight[weightIdx] <= target)
            maxPrice = Math.max(maxPrice, knapSackUnBoundedSubsequenceMemorization(price, weight, weightIdx,
                    currWeight + weight[weightIdx], target, dp));
        maxPrice = Math.max(maxPrice,
                knapSackUnBoundedSubsequenceMemorization(price, weight, weightIdx + 1, currWeight, target, dp));
        return dp[weightIdx][currWeight] = maxPrice + price[weightIdx];
    }

    public int knapSackUnBoundedSubsequenceTabulation(int[] price, int[] weight, int target) {
        int[][] dp = new int[weight.length + 1][target + 1];

        for (int i = weight.length; i >= 0; i--) {
            for (int j = target; j >= 0; j--) {

                if (i == weight.length || j == target)
                    return dp[i][j] = 0;

                int maxPrice = Integer.MIN_VALUE;
                if (j + weight[i] <= target)
                    maxPrice = Math.max(maxPrice, dp[i][j + weight[i]]);
                maxPrice = Math.max(maxPrice, dp[i + 1][j]);
                dp[i][j] = maxPrice + price[i];
            }
        }
        return dp[0][0];
    }

    // ? ******************************** Finite (Bounded)

    public int knapSackBoundedBinomial(int[] price, int[] weight, int weightIdx, int currWeight, int target) {
        if (weightIdx == weight.length || currWeight == target)
            return 0;

        int maxPrice = Integer.MIN_VALUE;
        for (int wt = weightIdx; wt <= weight.length; wt++)
            if (currWeight + weight[wt] <= target)
                maxPrice = Math.max(maxPrice,
                        knapSackBoundedBinomial(price, weight, wt + 1, currWeight + weight[wt], target));
        return maxPrice + price[weightIdx];
    }

    public int knapSackBoundedBinomialMemorization(int[] price, int[] weight, int weightIdx, int currWeight, int target,
            int[][] dp) {
        if (weightIdx == weight.length || currWeight == target)
            return dp[weightIdx][currWeight] = 0;

        if (dp[weightIdx][currWeight] != -1)
            return dp[weightIdx][currWeight];

        int maxPrice = Integer.MIN_VALUE;
        for (int wt = weightIdx; wt <= weight.length; wt++)
            if (currWeight + weight[wt] <= target)
                maxPrice = Math.max(maxPrice,
                        knapSackBoundedBinomialMemorization(price, weight, wt + 1, currWeight + weight[wt], target,
                                dp));
        return dp[weightIdx][currWeight] = maxPrice + price[weightIdx];
    }

    public int knapSackBoundedBinomialTabulation(int[] price, int[] weight, int target) {
        int[][] dp = new int[weight.length + 1][target + 1];

        for (int i = weight.length; i >= 0; i--) {
            for (int j = target; j >= 0; j--) {

                if (i == weight.length || j == target)
                    return dp[i][j] = 0;

                int maxPrice = Integer.MIN_VALUE;
                for (int wt = i; wt <= weight.length; wt++)
                    if (j + weight[wt] <= target)
                        maxPrice = Math.max(maxPrice, dp[wt + 1][j + weight[wt]]);
                dp[i][j] = maxPrice + price[i];
            }
        }
        return dp[0][0];
    }

    public int knapSackBoundedSubsequence(int[] price, int[] weight, int weightIdx, int currWeight, int target) {
        if (weightIdx == weight.length || currWeight == target)
            return 0;

        int maxPrice = Integer.MIN_VALUE;
        if (currWeight + weight[weightIdx] <= target)
            maxPrice = Math.max(maxPrice, knapSackBoundedSubsequence(price, weight,
                    weightIdx + 1, currWeight + weight[weightIdx], target));
        maxPrice = Math.max(maxPrice, knapSackBoundedSubsequence(price, weight, weightIdx + 1, currWeight, target));
        return maxPrice + price[weightIdx];
    }

    public int knapSackBoundedSubsequenceMemorization(int[] price, int[] weight, int weightIdx, int currWeight,
            int target,
            int[][] dp) {
        if (weightIdx == weight.length || currWeight == target)
            return dp[weightIdx][currWeight] = 0;

        if (dp[weightIdx][currWeight] != -1)
            return dp[weightIdx][currWeight];

        int maxPrice = Integer.MIN_VALUE;
        if (currWeight + weight[weightIdx] <= target)
            maxPrice = Math.max(maxPrice, knapSackBoundedSubsequenceMemorization(price, weight, weightIdx + 1,
                    currWeight + weight[weightIdx], target, dp));
        maxPrice = Math.max(maxPrice,
                knapSackBoundedSubsequenceMemorization(price, weight, weightIdx + 1, currWeight, target, dp));
        return dp[weightIdx][currWeight] = maxPrice + price[weightIdx];
    }

    public int knapSackBoundedSubsequenceTabulation(int[] price, int[] weight, int target) {
        int[][] dp = new int[weight.length + 1][target + 1];

        for (int i = weight.length; i >= 0; i--) {
            for (int j = target; j >= 0; j--) {

                if (i == weight.length || j == target)
                    return dp[i][j] = 0;

                int maxPrice = Integer.MIN_VALUE;
                if (j + weight[i] <= target)
                    maxPrice = Math.max(maxPrice, dp[i + 1][j + weight[i]]);
                maxPrice = Math.max(maxPrice, dp[i + 1][j]);
                dp[i][j] = maxPrice + price[i];
            }
        }
        return dp[0][0];
    }

    // ***** Infinite, Combinations, Binomial or Subsequence or Tricky(Best)
    // *https://www.geeksforgeeks.org/find-number-of-solutions-of-a-linear-equation-of-n-variables/
    public int numberOfSolutionOfLinearEquation(int[] coefficients, int target) {
        return numberOfSolutionOfLinearEquation(
                coefficients, 0, 0, target, new int[coefficients.length + 1][target + 1]);
    }

    public int numberOfSolutionOfLinearEquation(int[] arr, int idx, int sum, int target, int[][] dp) {
        if (idx == arr.length && sum == target)
            return dp[idx][sum] = 1;
        if (idx == arr.length)
            return dp[idx][sum] = 0;

        if (dp[idx][sum] != -1)
            return dp[idx][sum];

        int count = 0;
        for (int i = idx; i < arr.length; i++)
            if (sum + arr[i] <= target)
                count += numberOfSolutionOfLinearEquation(arr, i + 1, sum + arr[i], target, dp);
        return dp[idx][sum] = count;
    }

}
