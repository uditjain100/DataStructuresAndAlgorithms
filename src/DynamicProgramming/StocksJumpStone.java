package DynamicProgramming;

import java.util.*;

public class StocksJumpStone {

    // *https://leetcode.com/problems/best-time-to-buy-and-sell-stock/
    public int buyAndSellStocks1(int[] arr) {
        int n = arr.length;
        if (n == 0 || n == 1)
            return 0;

        int minStockToBuyTillPreviousDay = arr[0];
        int maxProfit = 0;

        for (int i = 1; i < n; i++) {
            maxProfit = Math.max(maxProfit, arr[i] - minStockToBuyTillPreviousDay);
            minStockToBuyTillPreviousDay = Math.min(minStockToBuyTillPreviousDay, arr[i]);
        }

        return maxProfit;
    }

    // *https://leetcode.com/problems/best-time-to-buy-and-sell-stock-ii/
    public int buyAndSellStocks2(int[] arr) {
        int maxProfit = 0;
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] < arr[i + 1])
                maxProfit += arr[i + 1] - arr[i];
        }
        return maxProfit;
    }

    // *https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iii/
    public int buyAndSellStocks3(int[] arr) {

        int n = arr.length;
        int[] sellMandatory = new int[n];
        int[] buyMandatory = new int[n];

        int minStockToBuyTillPreviousDay = arr[0];
        for (int i = 1; i < n; i++) {
            sellMandatory[i] = Math.max(sellMandatory[i - 1], arr[i] - minStockToBuyTillPreviousDay);
            minStockToBuyTillPreviousDay = Math.min(minStockToBuyTillPreviousDay, arr[i]);
        }

        int maxStockToSellTillNextDay = arr[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            buyMandatory[i] = Math.max(buyMandatory[i + 1], maxStockToSellTillNextDay - arr[i]);
            maxStockToSellTillNextDay = Math.max(maxStockToSellTillNextDay, arr[i]);
        }

        int maxProfit = 0;
        for (int i = 0; i < n; i++)
            maxProfit = Math.max(Math.max(sellMandatory[i], buyMandatory[i]),
                    Math.max(sellMandatory[i] + buyMandatory[i], maxProfit));
        return maxProfit;
    }

    // Todo :
    // *https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iv/
    public int buyAndSellStocks4(int[] arr, int k) {
        if (k == 0 || arr.length == 0)
            return 0;

        int[][] dp = new int[k + 1][arr.length];
        for (int i = 1; i < k + 1; i++) {
            for (int j = 1; j < arr.length; j++) {
                int count = dp[i][j - 1];
                for (int x = j - 1; x >= 0; x--)
                    count = Math.max(count, dp[i - 1][x] + arr[j] - arr[x]);
                dp[i][j] = count;
            }
        }
        return dp[k][arr.length - 1];
    }

    // *https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-transaction-fee/
    public int buyAndSellStocks5Fee(int[] arr, int fee) {
        int oldBoughtStateProfit = -arr[0];
        int oldSoldStateProfit = 0;

        for (int i = 1; i < arr.length; i++) {
            int newBoughtStateProfit = 0;
            int newSoldStateProfit = 0;

            if (oldSoldStateProfit - arr[i] > oldBoughtStateProfit)
                newBoughtStateProfit = oldSoldStateProfit - arr[i];
            else
                newBoughtStateProfit = oldBoughtStateProfit;

            if ((arr[i] - fee) + oldBoughtStateProfit > oldSoldStateProfit)
                newSoldStateProfit = (arr[i] - fee) + oldBoughtStateProfit;
            else
                newSoldStateProfit = oldSoldStateProfit;

            oldSoldStateProfit = newSoldStateProfit;
            oldBoughtStateProfit = newBoughtStateProfit;
        }

        return oldSoldStateProfit;
    }

    // *https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-cooldown/
    public int buyAndSellStocks6CoolDown(int[] arr, int fee) {

        int oldBoughtStateProfit = -arr[0];
        int oldSoldStateProfit = 0;
        int oldCoolStateProfit = 0;

        for (int i = 1; i < arr.length; i++) {
            int newBoughtStateProfit = 0;
            int newSoldStateProfit = 0;
            int newCoolStateProfit = 0;

            if (oldCoolStateProfit - arr[i] > oldBoughtStateProfit)
                newBoughtStateProfit = oldCoolStateProfit - arr[i];
            else
                newBoughtStateProfit = oldBoughtStateProfit;

            if (arr[i] + oldBoughtStateProfit > oldSoldStateProfit)
                newSoldStateProfit = arr[i] + oldBoughtStateProfit;
            else
                newSoldStateProfit = oldSoldStateProfit;

            if (oldSoldStateProfit > oldCoolStateProfit)
                newCoolStateProfit = oldSoldStateProfit;
            else
                newCoolStateProfit = oldCoolStateProfit;

            oldBoughtStateProfit = newBoughtStateProfit;
            oldSoldStateProfit = newSoldStateProfit;
            oldCoolStateProfit = newCoolStateProfit;
        }

        return oldSoldStateProfit;
    }

    // *https://leetcode.com/problems/jump-game/
    public boolean jumpGame1(int[] arr) {
        int maxReachableIndex = 0;
        for (int i = 0; i < arr.length; i++) {
            if (i < maxReachableIndex)
                return false;
            maxReachableIndex = Math.max(maxReachableIndex, i + arr[i]);
        }
        return true;
    }

    // *https://leetcode.com/problems/jump-game-ii/
    public int jumpGame2(int[] arr) {

        int n = arr.length;
        int[] dp = new int[n];

        for (int i = n - 2; i >= 0; i--) {
            int minJumps = n;
            for (int j = i + 1; j < n && j <= arr[i] + i; j++)
                minJumps = Math.min(minJumps, dp[j]);
            dp[i] = minJumps + 1;
        }
        return dp[0];
    }

    // *https://leetcode.com/problems/jump-game-iii/
    public boolean jumpGame3(int[] arr, int src) {
        int n = arr.length;

        LinkedList<Integer> queue = new LinkedList<>();
        queue.add(src);

        boolean[] vis = new boolean[n];

        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int idx = queue.remove();

                if (vis[idx])
                    continue;

                if (arr[idx] == 0)
                    return true;

                vis[idx] = true;
                if (idx + arr[idx] < n && !vis[idx + arr[idx]])
                    queue.add(idx + arr[idx]);
                if (idx - arr[idx] >= 0 && !vis[idx - arr[idx]])
                    queue.add(idx - arr[idx]);
            }
        }

        return false;
    }

    // *https://leetcode.com/problems/jump-game-iv/
    public int jumpGame4(int[] arr) {
        int n = arr.length;

        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int ele : arr)
            map.putIfAbsent(ele, new ArrayList<>());
        for (int i = 0; i < n; i++)
            map.get(arr[i]).add(i);

        LinkedList<Integer> queue = new LinkedList<>();
        queue.add(0);

        boolean[] vis = new boolean[n];
        vis[0] = true;
        Map<Integer, Boolean> duplicatesIncluded = new HashMap<>();
        for (int ele : arr)
            duplicatesIncluded.putIfAbsent(ele, false);

        int level = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int idx = queue.remove();

                if (idx == n - 1)
                    return level;

                if (idx + 1 < n && !vis[idx + 1]) {
                    queue.add(idx + 1);
                    vis[idx + 1] = true;
                }
                if (idx - 1 >= 0 && !vis[idx - 1]) {
                    queue.add(idx - 1);
                    vis[idx - 1] = true;
                }

                if (duplicatesIncluded.get(arr[idx]))
                    continue;

                duplicatesIncluded.put(arr[idx], true);
                for (int i : map.get(arr[idx]))
                    if (i != idx && !vis[i]) {
                        queue.add(i);
                        vis[i] = true;
                    }
            }
            level++;
        }

        return -1;
    }

    // *https://leetcode.com/problems/jump-game-v/
    public int jumpGame5(int[] arr, int d, int idx) {

        int n = arr.length;
        int[] dp = new int[n + 1];
        Arrays.fill(dp, -1);

        int maxJumps = 0;
        for (int i = 0; i < n; i++)
            maxJumps = Math.max(maxJumps, jumpGame5Memorization(arr, d, i, dp));

        return maxJumps;
    }

    public int jumpGame5Memorization(int[] arr, int d, int idx, int[] dp) {
        if (idx == arr.length)
            return 0;

        if (dp[idx] != -1)
            return dp[idx];

        int maxJumps = 1;
        for (int i = idx + 1; i <= idx + d && i < arr.length && arr[idx] > arr[i]; i++)
            maxJumps = Math.max(maxJumps, jumpGame5Memorization(arr, d, i, dp) + 1);
        for (int i = idx - 1; i >= idx - d && i >= 0 && arr[idx] > arr[i]; i--)
            maxJumps = Math.max(maxJumps, jumpGame5Memorization(arr, d, i, dp) + 1);

        return dp[idx] = maxJumps;
    }

    // *https://leetcode.com/problems/jump-game-vi/
    public int jumpGame6(int[] arr, int k) {
        if (arr.length < 2)
            return arr[0];

        int n = arr.length;
        int[] dp = new int[n];
        dp[n - 1] = arr[n - 1];

        Deque<Integer> queue = new ArrayDeque<>();
        queue.add(n - 1);

        for (int i = n - 2; i >= 0; i--) {
            if (queue.getFirst() > i + k)
                queue.removeFirst();
            dp[i] = arr[i] + dp[queue.getFirst()];

            while (!queue.isEmpty() && dp[queue.getLast()] < dp[i])
                queue.removeLast();
            queue.addLast(i);
        }
        return dp[0];
    }

    // *https://leetcode.com/problems/jump-game-vii/
    public boolean jumpGame7(String str, int minJump, int maxJump) {
        int n = str.length();

        if (str.charAt(n - 1) != '0')
            return false;

        boolean[] dp = new boolean[n];
        dp[n - 1] = true;

        Deque<Integer> queue = new ArrayDeque<>();

        for (int i = n - 2; i >= 0; i--) {
            char ch = str.charAt(i);
            if (!queue.isEmpty() && queue.getFirst() > i + maxJump)
                queue.removeFirst();
            if (i + minJump < n && dp[i + minJump])
                queue.addLast(i + minJump);
            if (ch == '1')
                continue;
            dp[i] = !queue.isEmpty();
        }
        return dp[0];
    }

    // *https://leetcode.com/problems/frog-jump/
    public boolean frogJump(int[] arr) {
        int n = arr.length;

        LinkedList<int[]> queue = new LinkedList<>();

        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < n; i++)
            map.put(i, new ArrayList<>());
        map.get(0).add(1);

        int src = arr[0];
        int k = 1;

        if (src == arr[n - 1])
            return true;

        int nbr = src + k;
        if (k > 0 && k < n && nbr > arr[0] && nbr <= arr[n - 1]) {
            int idx = Arrays.binarySearch(arr, nbr);
            if (idx > 0 && idx < n && arr[idx] == nbr && !map.get(idx).contains(k)) {
                queue.add(new int[] { arr[idx], k });
                map.get(idx).add(k);
            }
        }

        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int[] rp = queue.remove();

                src = rp[0];
                k = rp[1];

                if (src == arr[n - 1])
                    return true;

                for (int i = k - 1; i <= k + 1; i++) {
                    nbr = src + i;

                    if (i > 0 && i < n && nbr > arr[0] && nbr <= arr[n - 1]) {
                        int idx = Arrays.binarySearch(arr, nbr);
                        if (idx > 0 && idx < n && arr[idx] == nbr && !map.get(idx).contains(i)) {
                            queue.add(new int[] { arr[idx], i });
                            map.get(idx).add(i);
                        }
                    }

                }

            }
        }

        return false;
    }

    // *https://leetcode.com/problems/stone-game/
    public boolean stoneGame1(int[] arr) {
        return true;
    }

    // *https://leetcode.com/problems/stone-game-ii/
    public int stoneGame2(int[] arr) {
        int sum = 0;
        for (int ele : arr)
            sum += ele;

        int n = arr.length;
        int[][] dp = new int[arr.length + 1][201];
        for (int i = 0; i < n + 1; i++)
            Arrays.fill(dp[i], -1);
        int diff = stoneGame2(arr, 0, 1, dp);

        return (sum + diff) / 2;
    }

    public int stoneGame2(int[] arr, int idx, int m, int[][] dp) {

        if (idx == arr.length)
            return dp[idx][m] = 0;

        if (dp[idx][m] != -1)
            return dp[idx][m];

        int maxMoney = Integer.MIN_VALUE;

        int sum = 0;
        for (int i = idx; i < idx + (2 * m) && i < arr.length; i++) {
            sum += arr[i];
            maxMoney = Math.max(maxMoney, sum - stoneGame2(arr, i + 1, Math.max(i + 1 - idx, m), dp));
        }

        return dp[idx][m] = maxMoney;
    }

    // *https://leetcode.com/problems/stone-game-iii/
    public String stoneGame3(int[] arr) {
        int[] dp = new int[arr.length + 1];
        Arrays.fill(dp, -1);
        int res = stoneGame3(arr, 0, dp);
        return (res < 0) ? "Bob" : (res > 0) ? "Alice" : "Tie";
    }

    public int stoneGame3(int[] arr, int idx, int[] dp) {

        if (idx == arr.length)
            return dp[idx] = 0;

        if (dp[idx] != -1)
            return dp[idx];

        int maxMoney = Integer.MIN_VALUE;

        int sum = 0;
        for (int i = idx; i < idx + 3 && i < arr.length; i++) {
            sum += arr[i];
            maxMoney = Math.max(maxMoney, sum - stoneGame3(arr, i + 1, dp));
        }

        return dp[idx] = maxMoney;
    }

    // *https://leetcode.com/problems/stone-game-v/
    public int stoneGame5(int[] arr) {
        int n = arr.length;
        int[][] dp = new int[n + 1][n + 1];

        for (int i = 0; i < n + 1; i++)
            Arrays.fill(dp[i], -1);

        return stoneGame6(arr, 0, n - 1, dp);
    }

    public int stoneGame6(int[] arr, int si, int ei, int[][] dp) {
        if (si > ei)
            return dp[si][ei] = 0;

        if (dp[si][ei] != -1)
            return dp[si][ei];

        int rightSum = 0;
        for (int i = si; i <= ei; i++)
            rightSum += arr[i];

        int leftSum = 0;

        int max = 0;
        for (int i = si; i < ei; i++) {

            leftSum += arr[i];
            rightSum -= arr[i];

            if (leftSum > rightSum)
                max = Math.max(max, rightSum + stoneGame6(arr, i + 1, ei, dp));
            else if (rightSum > leftSum)
                max = Math.max(max, leftSum + stoneGame6(arr, si, i, dp));
            else
                max = Math.max(max,
                        Math.max(rightSum + stoneGame6(arr, i + 1, ei, dp), leftSum + stoneGame6(arr, si, i, dp)));
        }
        return dp[si][ei] = max;
    }

}
