package Greedy;

import java.util.*;

import Tree.binaryTree.TreeNode;

@SuppressWarnings("unchecked")
public class methods_greedy {

    // *https://www.geeksforgeeks.org/water-connection-problem/
    public ArrayList<ArrayList<Integer>> waterConnectionProblem(int n, int p, int[] start, int[] end, int[] diameter) {

        ArrayList<int[]>[] graph = new ArrayList[n];

        for (int i = 0; i < n; i++)
            graph[i] = new ArrayList<>();

        for (int i = 0; i < p; i++)
            graph[start[i]].add(new int[] { end[i], diameter[i] });

        int[] inDegree = inDegree(graph, n);
        int[] outDegree = outDegree(graph, n);

        ArrayList<ArrayList<Integer>> res = new ArrayList<>();

        boolean[] vis = new boolean[n];
        for (int i = 0; i < n; i++)
            if (!vis[i]) {
                startOfEachComponent = endOfEachComponent = -1;
                int minDiameter = dfsForEachComponent(graph, i, vis, inDegree, outDegree);
                res.add(new ArrayList<>(Arrays.asList(startOfEachComponent, endOfEachComponent, minDiameter)));
            }

        return res;
    }

    int startOfEachComponent, endOfEachComponent;

    public int dfsForEachComponent(ArrayList<int[]>[] graph, int u, boolean[] vis, int[] inDegree, int[] outDegree) {

        if (inDegree[u] == 0)
            startOfEachComponent = u;
        if (outDegree[u] == 0)
            endOfEachComponent = u;

        vis[u] = true;

        int min = Integer.MAX_VALUE;
        for (int[] nbr : graph[u])
            if (!vis[nbr[0]])
                min = Math.min(Math.min(min, nbr[1]), dfsForEachComponent(graph, nbr[0], vis, inDegree, outDegree));
        return min;
    }

    public int[] inDegree(ArrayList<int[]>[] graph, int n) {
        int[] inDegree = new int[n];
        for (ArrayList<int[]> list : graph)
            for (int[] nbr : list)
                inDegree[nbr[0]]++;
        return inDegree;
    }

    public int[] outDegree(ArrayList<int[]>[] graph, int n) {
        int[] outDegree = new int[n];
        int i = 0;
        for (ArrayList<int[]> list : graph)
            outDegree[i++] = list.size();
        return outDegree;
    }

    // *https://www.geeksforgeeks.org/swap-all-occurrences-of-two-characters-to-get-lexicographically-smallest-string/
    public String chooseAndSwap(String str) {

        boolean[] isPresent = new boolean[26];
        boolean[] vis = new boolean[26];

        for (char ch : str.toCharArray())
            isPresent[ch - 'a'] = true;

        for (int i = 0; i < str.length(); i++) {
            int ch = str.charAt(i) - 'a';
            for (int j = 0; j < ch; j++)
                if (isPresent[j] && !vis[j])
                    return swap(str, (char) (ch + 'a'), (char) (j + 'a'));
            vis[ch] = true;
        }

        return str;
    }

    public String swap(String str, char a, char b) {
        StringBuilder sb = new StringBuilder();
        for (char ch : str.toCharArray())
            if (ch == a)
                sb.append(b);
            else if (ch == b)
                sb.append(a);
            else
                sb.append(ch);
        return sb.toString();
    }

    // *https://www.geeksforgeeks.org/maximum-trains-stoppage-can-provided/
    // ? m -> Number of Platforms
    public int maxTrainsForWhichCanBeProvided(int[] start, int[] dept, int[] platform, int m) {

        int n = start.length;
        int[][] arr = new int[n][];

        for (int i = 0; i < n; i++)
            arr[i] = new int[] { start[i], dept[i], platform[i] };

        int[] maxEndTime = new int[m];

        Arrays.sort(arr, (a, b) -> (b[1] != a[1]) ? b[1] - a[1] : b[0] - a[0]);

        int[] count = new int[m];
        for (int[] train : arr) {
            int s = train[0];
            int e = train[1];
            int p = train[2];

            if (s > maxEndTime[p]) {
                maxEndTime[p] = e;
                count[p]++;
            }
        }

        int res = 0;
        for (int c : count)
            res += c;
        return res;
    }

    // *https://practice.geeksforgeeks.org/problems/minimum-platforms-1587115620/1
    public int minPlatforms(int[] start, int[] dept) {
        int n = start.length;
        int[][] arr = new int[n][];

        for (int i = 0; i < n; i++)
            arr[i] = new int[] { start[i], dept[i] };

        Arrays.sort(arr, (a, b) -> (b[1] != a[1]) ? b[1] - a[1] : b[0] - a[0]);

        ArrayList<Integer> res = new ArrayList<>();

        for (int[] train : arr) {
            int s = train[0];
            int e = train[1];

            boolean temp = false;
            for (int i = 0; i < res.size(); i++)
                if (s > res.get(i)) {
                    temp = true;
                    res.set(i, e);
                    break;
                }
            if (!temp)
                res.add(e);
        }
        return res.size();
    }

    // *https://www.geeksforgeeks.org/minimum-cost-cut-board-squares/
    public int minCostToCutBoardIntoSquares(int[] hc, int[] vc) {
        int n = hc.length;
        int m = vc.length;

        Arrays.sort(hc);
        Arrays.sort(vc);

        int hCount = 0;
        int vCount = 0;

        int i = 0;
        int j = 0;

        int cost = 0;
        while (i < n && j < m)
            if (hc[i] < vc[j])
                cost += hc[i++] * hCount++;
            else if (hc[i] < vc[j])
                cost += vc[j++] * vCount++;
            else {
                if (hCount < vCount)
                    cost += hc[i++] * hCount++;
                else
                    cost += vc[j++] * vCount++;
            }

        while (i < n)
            cost += hc[i++] * hCount++;
        while (j < m)
            cost += vc[j++] * vCount++;

        return cost;
    }

    // *https://practice.geeksforgeeks.org/problems/largest-number-with-given-sum-1587115620/1/?page=1&difficulty[]=0&category[]=Greedy&sortBy=submissions
    public String largestNumberWithGivenSum(int n, int s) {
        if (s > 9 * n)
            return "-1";

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            if (s > 9) {
                sb.append(9);
                s -= 9;
            } else if (s != 0) {
                sb.append(s);
                s = 0;
            } else
                sb.append(0);
        }
        return sb.toString();
    }

    // *https://practice.geeksforgeeks.org/problems/smallest-number5829/1#
    public String smallestNumberWithGivenSum(int n, int s) {
        if (s < 0 || s > 9 * n)
            return "-1";

        StringBuilder sb = new StringBuilder();
        boolean temp = false;
        for (int i = 0; i < n; i++) {
            if (i == n - 1 && temp) {
                sb.append(1);
                continue;
            }

            if (s > 9) {
                sb.append(9);
                s -= 9;
            } else if (s != 0) {
                if (i == n - 1)
                    sb.append(s);
                else {
                    sb.append(s - 1);
                    s = 0;
                    temp = true;
                }
            } else
                sb.append(s);
        }
        return sb.reverse().toString();
    }

    // *https://www.geeksforgeeks.org/policemen-catch-thieves/
    public int policeAndThieves(char[] arr, int k) {

        ArrayList<Integer> police = new ArrayList<>();
        ArrayList<Integer> thieves = new ArrayList<>();
        int count = 0;

        for (int i = 0; i < arr.length; i++)
            if (arr[i] == 'P')
                police.add(i);
            else
                thieves.add(i);

        int i = 0;
        int j = 0;

        while (i < police.size() && j < thieves.size()) {
            if (Math.abs(police.get(i) - thieves.get(j)) <= k) {
                count++;
                i++;
                j++;
            } else if (police.get(i) < thieves.get(j))
                i++;
            else
                j++;
        }

        return count;
    }

    // Todo ::
    // *https://practice.geeksforgeeks.org/problems/minimize-the-heights-i/1/
    public int minimizeTheHeight1(int[] arr, int k) {
        int n = arr.length;

        Arrays.sort(arr);

        int min = arr[0];
        int max = arr[n - 1];
        int diff = max - min;

        for (int i = 1; i < n; i++) {
            max = Math.max(arr[i - 1] + k, arr[n - 1] - k);
            min = Math.min(arr[i] - k, arr[0] + k);

            diff = Math.min(diff, max - min);
        }

        return diff;
    }

    // *https://practice.geeksforgeeks.org/problems/minimize-the-heights3351/1
    public int minimizeTheHeight2(int[] arr, int k) {
        int n = arr.length;

        Arrays.sort(arr);

        int min = arr[0];
        int max = arr[n - 1];
        int diff = max - min;

        for (int i = 1; i < n; i++) {

            if (arr[i] - k < 0)
                continue;

            max = Math.max(arr[i - 1] + k, arr[n - 1] - k);
            min = Math.min(arr[i] - k, arr[0] + k);

            diff = Math.min(diff, max - min);
        }

        return diff;
    }

    // *https://leetcode.com/problems/house-robber/
    // *https://www.youtube.com/watch?v=VT4bZV24QNo&list=PL-Jc9J83PIiG8fE6rj9F5a6uyQ5WPdqKy&index=22
    public int houseRobber1(int[] arr) {
        if (arr.length == 0)
            return 0;
        if (arr.length == 1)
            return arr[0];
        if (arr.length == 2)
            return Math.max(arr[0], arr[1]);

        int include = 0;
        int exclude = 0;

        for (int ele : arr) {
            int newInclude = exclude + ele;
            exclude = Math.max(include, exclude);
            include = newInclude;
        }

        return Math.max(include, exclude);
    }

    // *https://leetcode.com/problems/house-robber-ii/
    public int houseRobber2(int[] arr) {
        if (arr.length == 0)
            return 0;
        if (arr.length == 1)
            return arr[0];
        if (arr.length == 2)
            return Math.max(arr[0], arr[1]);

        int include = 0;
        int exclude = 0;

        for (int i = 0; i < arr.length - 1; i++) {
            int newInclude = exclude + arr[i];
            exclude = Math.max(include, exclude);
            include = newInclude;
        }

        int maxCost = Math.max(include, exclude);

        include = 0;
        exclude = 0;

        for (int i = 1; i < arr.length; i++) {
            int newInclude = exclude + arr[i];
            exclude = Math.max(include, exclude);
            include = newInclude;
        }

        return Math.max(maxCost, Math.max(include, exclude));
    }

    // *https://leetcode.com/problems/house-robber-iii/
    public int houseRobber3(TreeNode root) {
        if (root == null)
            return 0;

        int[] maxCost = calculateMaxCost(root);
        return Math.max(maxCost[0], maxCost[1]);
    }

    public int[] calculateMaxCost(TreeNode node) {
        if (node == null)
            return new int[] { 0, 0 };

        int[] left = calculateMaxCost(node.left);
        int[] right = calculateMaxCost(node.right);

        // ***** In left[] -> left[0] - left included and left[1] - left excluded

        int include = node.value + left[1] + right[1];
        int excluded = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);

        return new int[] { include, excluded };
    }

    // ? ***************************************** DP + Greedy

    // *https://www.programcreek.com/2014/05/leetcode-paint-house-java/
    public int paintHouses1(int[][] arr) {
        int n = arr.length;
        int[][] dp = new int[n][3];
        dp[0][0] = arr[0][0];
        dp[0][1] = arr[0][1];
        dp[0][2] = arr[0][2];

        for (int i = 1; i < n; i++) {
            dp[i][0] = Math.min(dp[i - 1][1], dp[i - 1][2]);
            dp[i][1] = Math.min(dp[i - 1][0], dp[i - 1][2]);
            dp[i][2] = Math.min(dp[i - 1][0], dp[i - 1][1]);
        }

        return Math.min(Math.min(dp[n - 1][0], dp[n - 1][1]), dp[n - 1][2]);
    }

    // *https://www.programcreek.com/2014/05/leetcode-paint-house-ii-java/
    public int paintHouses2(int[][] arr) {
        int n = arr.length;
        int k = arr[0].length;

        int[][] dp = new int[n][k];

        for (int j = 0; j < k; j++)
            dp[0][j] = arr[0][j];

        for (int i = 1; i < n; i++) {
            for (int j = 0; j < k; j++) {
                int min = Integer.MAX_VALUE;
                for (int x = 0; x < k; x++) {
                    if (x != j)
                        min = Math.min(min, dp[i - 1][x]);
                }
                dp[i][j] = min;
            }
        }

        int minCost = Integer.MAX_VALUE;
        for (int j = 0; j < k; j++)
            minCost = Math.min(minCost, dp[n - 1][j]);

        return minCost;
    }

    // *https://baihuqian.github.io/2018-07-29-paint-fence/
    public long paintFence(int n, int k) {

        long same = k;
        long diff = k * (k - 1);
        long total = same + diff;

        for (int i = 3; i <= n; i++) {
            same = diff;
            diff = total * (k - 1);
            total = same + diff;
        }

        return total;
    }

    // *https://www.geeksforgeeks.org/number-subsequences-form-ai-bj-ck/
    public int aibjckSequences(String str) {
        int a = 0;
        int b = 0;
        int c = 0;

        for (char ch : str.toCharArray()) {
            if (ch == 'a')
                a = 2 * a + 1;
            else if (ch == 'b')
                b = 2 * b + a;
            else
                c = 2 * c + b;
        }

        return c;
    }

}
