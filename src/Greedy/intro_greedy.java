package Greedy;

import java.util.*;

public class intro_greedy {

    class HuffNode {

        int value;
        HuffNode left;
        HuffNode right;
        int idx;

        public HuffNode() {
            this.value = this.idx = 0;
            this.left = this.right = null;
        }

        public HuffNode(int val, int idx) {
            this.value = val;
            this.idx = idx;
            this.left = this.right = null;
        }

    }

    // *https://practice.geeksforgeeks.org/problems/huffman-encoding3345/1
    public String[] HuffmanEncoding(String str, int[] freq) {

        PriorityQueue<HuffNode> pq = new PriorityQueue<>((a, b) -> {
            return a.value - b.value;
        });
        for (int i = 0; i < freq.length; i++)
            pq.add(new HuffNode(freq[i], i));

        while (pq.size() > 1) {
            HuffNode left = pq.remove();
            HuffNode right = pq.remove();

            HuffNode parent = new HuffNode(left.value + right.value, -1);

            parent.left = left;
            parent.right = right;
            pq.add(parent);
        }

        HuffNode root = pq.remove();

        String[] res = new String[freq.length];

        dfs(root, res, "");

        return res;
    }

    public void dfs(HuffNode node, String[] arr, String ans) {
        if (node == null)
            return;

        dfs(node.left, arr, ans + "0");
        dfs(node.right, arr, ans + "1");

        if (node.idx != -1)
            arr[node.idx] = ans;
    }

    public int optimalFileMergePattern(int[] arr) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();

        for (int ele : arr)
            pq.add(ele);

        int res = 0;
        while (pq.size() > 1) {
            int sum = pq.remove() + pq.remove();
            res += sum;
            pq.add(sum);
        }
        return res;
    }

    // *https://practice.geeksforgeeks.org/problems/fractional-knapsack-1587115620/1
    // ? sort on the basis of prize / weight
    public int fractionalKnapsack(int[] wt, int[] pr, int wtLimit) {

        int n = wt.length;

        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> {
            return (b[1] / b[0]) - (a[1] / a[0]);
        });

        for (int i = 0; i < n; i++)
            pq.add(new int[] { wt[i], pr[i] });

        int res = 0;
        while (!pq.isEmpty()) {
            int[] rn = pq.remove();
            if (wtLimit == 0)
                break;
            if (wtLimit >= rn[0]) {
                wtLimit -= rn[0];
                res += rn[1];
            } else {
                double ratio = rn[1] / rn[0];
                res += (int) (wtLimit * ratio);
                wtLimit = 0;
            }
        }
        return res;
    }

    // *https://practice.geeksforgeeks.org/problems/job-sequencing-problem-1587115620/1
    public String jobSequencingProblem(char[] ids, int[] deadline, int[] profit) {
        int n = deadline.length;

        int maxDeadline = 0;
        for (int ele : deadline)
            maxDeadline = Math.max(maxDeadline, ele);

        char[] timeline = new char[maxDeadline + 1];
        Arrays.fill(timeline, '.');

        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> {
            return (b[0] != a[0]) ? b[0] - a[0] : b[1] - a[1];
        });

        for (int i = 0; i < n; i++)
            pq.add(new int[] { profit[i], deadline[i], ids[i] - 'a' });

        while (!pq.isEmpty()) {
            int[] rn = pq.remove();
            while (rn[1]-- > 0)
                if (timeline[rn[1] + 1] != '.') {
                    timeline[rn[1] + 1] = (char) (rn[2] + 'a');
                    break;
                }
        }

        StringBuilder sb = new StringBuilder();
        for (char ch : timeline)
            if (ch != '.')
                sb.append(ch + ',');

        return sb.toString().substring(0, sb.length() - 1);
    }

    // *https://practice.geeksforgeeks.org/problems/n-meetings-in-one-room-1587115620/1
    public int activitySelection(int[] start, int[] end) {

        int n = start.length;

        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> {
            if (a[1] != b[1])
                return a[1] - b[1];
            return a[0] - b[0];
        });

        for (int i = 0; i < n; i++)
            pq.add(new int[] { start[i], end[i] });

        int count = 1;
        int maxEndTime = pq.remove()[1];
        while (!pq.isEmpty()) {
            int[] rn = pq.remove();

            if (rn[0] > maxEndTime) {
                maxEndTime = rn[1];
                count++;
            }
        }
        return count;
    }
}
