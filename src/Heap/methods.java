package Heap;

import java.util.*;

import Heap.intro_heap.Heap;
import LinkedList.intro_ll.LinkedList;
import LinkedList.intro_ll.LinkedList.Node;
import Tree.binaryTree.TreeNode;

public class methods {

    public int kthSmallest(int[] arr, int k) {

        Heap heap = new Heap(false);

        for (int ele : arr) {
            heap.insert(ele);
            if (heap.size() > k)
                heap.remove();
        }

        return heap.getTopElement();
    }

    public int kthLargest(int[] arr, int k) {

        Heap heap = new Heap(true);

        for (int ele : arr) {
            heap.insert(ele);
            if (heap.size() > k)
                heap.remove();
        }

        return heap.getTopElement();
    }

    public int[] kthSmallestInStream(int[] arr, int k) {

        Heap heap = new Heap(false);
        int[] res = new int[arr.length];

        int i = 0;
        for (int ele : arr) {
            heap.insert(ele);
            if (heap.size() > k)
                heap.remove();
            res[i++] = heap.getTopElement();
        }

        return res;
    }

    public int[] kthLargestInStream(int[] arr, int k) {

        Heap heap = new Heap(true);
        int[] res = new int[arr.length];

        int i = 0;
        for (int ele : arr) {
            heap.insert(ele);
            if (heap.size() > k)
                heap.remove();
            res[i++] = heap.getTopElement();
        }

        return res;
    }

    public void heapSort(int[] arr) {
        Heap h = new Heap(true);
        h.constructHeap(arr);

        int i = 0;
        while (!h.isEmpty())
            arr[i++] = h.remove();
    }

    public ArrayList<Integer> pancakeSorting(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            int idx = findMaxIndex(arr, 0, arr.length - i - 1);
            reverseArray(arr, 0, idx);
            reverseArray(arr, 0, arr.length - i - 1);
        }

        ArrayList<Integer> res = new ArrayList<>();
        for (int ele : arr)
            res.add(ele);
        return res;
    }

    public void reverseArray(int[] arr, int si, int ei) {
        while (si < ei) {
            int temp = arr[si];
            arr[si] = arr[ei];
            arr[ei] = temp;
            si++;
            ei--;
        }
    }

    public int findMaxIndex(int[] arr, int si, int ei) {
        int res = Integer.MIN_VALUE;
        for (int i = si; i <= ei; i++)
            res = Math.max(res, arr[i]);

        for (int i = si; i <= ei; i++)
            if (res == arr[i])
                return i;
        return -1;
    }

    public void nearlyKSortedArray(int[] arr, int k) {
        Heap h = new Heap(true);

        for (int i = 0; i < k + 1 && i < arr.length; i++)
            h.insert(arr[i]);

        int j = 0;

        arr[j++] = h.remove();
        for (int i = k + 1; i < arr.length; i++) {
            h.insert(arr[i]);
            arr[j++] = h.remove();
        }

        while (j < arr.length)
            arr[j++] = h.remove();
    }

    public int[] medianInStream(int[] arr) {

        Heap maxHeap = new Heap(false);
        Heap minHeap = new Heap(true);

        int[] res = new int[arr.length];

        for (int i = 0; i < arr.length; i++) {

            int ele = arr[i];

            if (maxHeap.isEmpty() && minHeap.isEmpty())
                maxHeap.insert(ele);
            else if (!maxHeap.isEmpty() && minHeap.isEmpty()) {
                if (ele < maxHeap.getTopElement())
                    maxHeap.insert(ele);
                else
                    minHeap.insert(ele);
            } else if (maxHeap.isEmpty() && !minHeap.isEmpty()) {
                if (ele > minHeap.getTopElement())
                    minHeap.insert(ele);
                else
                    maxHeap.insert(ele);
            } else {
                if (ele < maxHeap.getTopElement())
                    maxHeap.insert(ele);
                else
                    minHeap.insert(ele);
            }

            if (maxHeap.size() < minHeap.size())
                maxHeap.insert(minHeap.remove());
            else if (maxHeap.size() > minHeap.size() + 1)
                minHeap.insert(maxHeap.remove());

            res[i] = (minHeap.size() == maxHeap.size()) ? (minHeap.getTopElement() + maxHeap.getTopElement()) / 2
                    : maxHeap.getTopElement();

        }

        return res;
    }

    // *** Huffman Coding
    public int mergeRopeCuts(int[] arr) {
        Heap heap = new Heap(true);
        heap.constructHeap(arr);

        int res = 0;
        while (heap.size() > 1) {
            int ans = heap.remove() + heap.remove();
            heap.insert(ans);
            res += ans;
        }
        return res;
    }

    public ArrayList<Integer> mergeKSortedArrays(int[][] arr) {

        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> {
            return a[0] - b[0];
        });

        for (int i = 0; i < arr.length; i++)
            pq.add(new int[] { arr[i][0], i, 0 });

        ArrayList<Integer> res = new ArrayList<>();

        while (!pq.isEmpty()) {
            int[] ans = pq.remove();
            res.add(ans[0]);
            int i = ans[1];
            int j = ans[2];
            if (j + 1 < arr[i].length)
                pq.add(new int[] { arr[i][j + 1], i, j + 1 });
        }

        return res;
    }

    public class Pair {

        Node node;
        int row;

        public Pair() {
            this.node = null;
            this.row = -1;
        }

        public Pair(Node n, int r) {
            this.node = n;
            this.row = r;
        }

    }

    public ArrayList<Integer> mergeKSortedLists(ArrayList<LinkedList> list) {

        PriorityQueue<Pair> pq = new PriorityQueue<>((a, b) -> {
            return a.node.value - b.node.value;
        });

        for (int i = 0; i < list.size(); i++)
            pq.add(new Pair(list.get(i).getFirst(), i));

        ArrayList<Integer> res = new ArrayList<>();

        while (!pq.isEmpty()) {
            Pair ans = pq.remove();
            res.add(ans.node.value);
            if (ans.node.next != null)
                pq.add(new Pair(ans.node.next, ans.row));
        }

        return res;
    }

    // **https://www.geeksforgeeks.org/find-smallest-range-containing-elements-from-k-lists/
    public int minRange(int[][] arr) {

        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> {
            return a[0] - b[0];
        });

        for (int i = 0; i < arr.length; i++)
            pq.add(new int[] { arr[i][0], i, 0 });

        int low = pq.peek()[0];
        int high = Collections.max(pq, (a, b) -> {
            return a[0] - b[0];
        })[0];

        int res = Integer.MAX_VALUE;

        while (!pq.isEmpty()) {
            int[] ans = pq.remove();
            int i = ans[1];
            int j = ans[2];
            low = ans[0];
            high = Collections.max(pq, (a, b) -> {
                return a[0] - b[0];
            })[0];
            res = Math.min(res, high - low);
            if (j + 1 < arr[i].length)
                pq.add(new int[] { arr[i][j + 1], i, j + 1 });
            else
                break;
        }

        return res;
    }

    // *https://leetcode.com/problems/kth-smallest-element-in-a-sorted-matrix/
    public int kthSmallestElementInMatrix(int[][] arr, int k) {
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);

        for (int i = 0; i < arr.length; i++)
            pq.add(new int[] { arr[i][0], i, 0 });

        while (k-- > 1) {
            int[] rv = pq.remove();

            int r = rv[1];
            int c = rv[2];

            if (c + 1 < arr[r].length)
                pq.add(new int[] { arr[r][c + 1], r, c + 1 });
        }
        return pq.peek()[0];
    }

    public Heap merge2Heaps(Heap h1, Heap h2) {
        ArrayList<Integer> list = new ArrayList<>();

        while (!h1.isEmpty())
            list.add(h1.remove());
        while (!h2.isEmpty())
            list.add(h2.remove());

        Heap res = new Heap(true);
        res.constructHeap(list);

        return res;
    }

    public int kthLargestSubArraySum(int[] arr, int k) {

        Heap heap = new Heap(true);

        for (int i = 0; i < arr.length; i++) {
            int sum = 0;
            for (int j = i; j < arr.length; j++) {
                sum += arr[j];
                heap.insert(sum);
                if (heap.size() > k)
                    heap.remove();
            }
        }

        return heap.getTopElement();
    }

    // **** https://leetcode.com/problems/reorganize-string/
    public String rearrangeString(String str) {

        HashMap<Character, Integer> map = new HashMap<>();
        for (char ch : str.toCharArray()) {
            map.putIfAbsent(ch, 0);
            map.put(ch, map.get(ch) + 1);
        }

        PriorityQueue<Character> pq = new PriorityQueue<>((a, b) -> {
            return map.get(a) - map.get(b);
        });

        pq.addAll(map.keySet());

        StringBuilder sb = new StringBuilder();
        while (pq.size() > 1) {
            char c1 = pq.remove();
            char c2 = pq.remove();

            sb.append(c1);
            sb.append(c2);

            map.put(c1, map.get(c1) - 1);
            map.put(c2, map.get(c2) - 1);

            if (map.get(c1) != 0)
                pq.add(c1);
            if (map.get(c2) != 0)
                pq.add(c2);
        }

        if (map.get(pq.peek()) > 1)
            return "";
        if (map.get(pq.peek()) == 1)
            return sb.append(pq.remove()).toString();
        return sb.toString();
    }

    // ***** https://practice.geeksforgeeks.org/problems/minimum-sum4058/1
    public int findMinSum(int[] arr) {

        Heap heap = new Heap(true);
        heap.constructHeap(arr);

        int num1 = 0;
        int num2 = 0;

        int i = 0;
        while (!heap.isEmpty()) {
            int digit = heap.remove();
            if (i % 2 == 0) {
                num1 *= 10;
                num1 += digit;
            } else {
                num2 *= 10;
                num2 += digit;
            }
            i++;
        }
        return num1 + num2;
    }

    public void BSTToMinHeap(TreeNode node) {
        ArrayList<Integer> list = new ArrayList<>();
        inOrder(node, list);
        preOrder(node, list);
    }

    public void inOrder(TreeNode node, ArrayList<Integer> list) {
        if (node == null)
            return;

        inOrder(node.left, list);
        list.add(node.value);
        inOrder(node.right, list);
    }

    public int idx = 0;

    public void preOrder(TreeNode node, ArrayList<Integer> list) {
        if (node == null)
            return;

        node.value = list.get(idx++);
        preOrder(node.left, list);
        preOrder(node.right, list);
    }

    // *https://leetcode.com/problems/the-skyline-problem/submissions/
    public List<List<Integer>> skyLineProblem(int[][] arr) {

        ArrayList<int[]> list = new ArrayList<>();
        for (int[] building : arr) {
            list.add(new int[] { building[0], -building[2] });
            list.add(new int[] { building[1], building[2] });
        }
        Collections.sort(list, (a, b) -> ((a[0] != b[0]) ? a[0] - b[0] : a[1] - b[1]));

        PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> b - a);
        pq.add(0);
        List<List<Integer>> res = new ArrayList<>();

        int currHeight = 0;

        for (int[] array : list) {
            if (array[1] < 0)
                pq.add(-array[1]);
            else
                pq.remove(array[1]);

            if (pq.peek() != currHeight) {
                res.add(new ArrayList<>(Arrays.asList(array[0], pq.peek())));
                currHeight = pq.peek();
            }
        }

        return res;
    }

    // *https://leetcode.com/problems/find-k-closest-elements/submissions/
    public List<Integer> kClosestElements(int[] arr, int k, int target) {
        PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> {
            int d1 = Math.abs(a - target);
            int d2 = Math.abs(b - target);
            if (d1 != d2)
                return d2 - d1;
            return b - a;
        });

        for (int ele : arr) {
            pq.add(ele);
            if (pq.size() > k)
                pq.remove();
        }

        List<Integer> res = new ArrayList<>(pq);
        Collections.sort(res);

        return res;
    }

}
