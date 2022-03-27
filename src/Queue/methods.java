package Queue;

import java.util.ArrayDeque;
import java.util.HashMap;

import LinkedList.intro_ll.DoublyLinkedList;
import Queue.intro_queue.Queue;
import Stack.intro_stack.Stack;

public class methods {

    public void reverse(Queue q) {
        if (q.isEmpty())
            return;

        int re = q.dequeue();
        reverse(q);
        q.enqueue(re);
    }

    public void reverseK(Queue q, int k) {
        if (q.isEmpty() || k == 0)
            return;

        int re = q.dequeue();
        reverseK(q, k - 1);
        q.enqueue(re);
    }

    // ***** Interleave the first half of the queue with second half
    // ***** Input : 11 12 13 14 15 16 17 18 19 20
    // ***** Output: 11 16 12 17 13 18 14 19 15 20
    public void interleave(Queue q) {
        interleave(q, new Stack(q.size), q.size / 2);
        reverse(q);
    }

    private void interleave(Queue q, Stack s, int k) {
        if (q.isEmpty())
            return;

        int re = q.dequeue();
        if (k > 0)
            s.push(re);
        interleave(q, s, k - 1);

        if (k > 0)
            return;

        q.enqueue(re);
        q.enqueue(s.pop());

    }

    public String firstNonRepeatingCharacterFromStream(String str) {

        DoublyLinkedList queue = new DoublyLinkedList();
        HashMap<Character, Integer> map = new HashMap<>();

        StringBuilder sb = new StringBuilder();

        for (char ch : str.toCharArray()) {

            map.putIfAbsent(ch, 0);
            map.put(ch, map.get(ch) + 1);

            queue.addLast(ch);

            while (!queue.isEmpty() && map.get((char) queue.getFirst().value) > 1)
                queue.removeFirst();

            if (!queue.isEmpty())
                sb.append((char) queue.getFirst().value);
            else
                sb.append("#");
        }

        return sb.toString();
    }

    public int[] slidingWindowMin(int[] arr, int k) {

        int n = arr.length;

        DoublyLinkedList queue = new DoublyLinkedList();

        int[] res = new int[n - k + 1];
        res[0] = arr[0];
        for (int i = 0; i < k && i < n; i++) {
            res[0] = Math.min(res[0], arr[i]);
            while (!queue.isEmpty() && arr[queue.getLast().value] > arr[i])
                queue.removeLast();
            queue.addLast(i);
        }

        for (int i = k; i < n; i++) {
            if (queue.getFirst().value < i - k + 1)
                queue.removeFirst();
            while (!queue.isEmpty() && arr[queue.getLast().value] > arr[i])
                queue.removeLast();
            queue.addLast(i);
            res[i - k + 1] = arr[queue.getFirst().value];
        }

        return res;
    }

    public int[] slidingWindowMax(int[] arr, int k) {

        int n = arr.length;

        DoublyLinkedList queue = new DoublyLinkedList();

        int[] res = new int[n - k + 1];
        res[0] = arr[0];
        for (int i = 0; i < k && i < n; i++) {
            res[0] = Math.max(res[0], arr[i]);
            while (!queue.isEmpty() && arr[queue.getLast().value] < arr[i])
                queue.removeLast();
            queue.addLast(i);
        }

        for (int i = k; i < n; i++) {
            if (queue.getFirst().value < i - k + 1)
                queue.removeFirst();
            while (!queue.isEmpty() && arr[queue.getLast().value] < arr[i])
                queue.removeLast();
            queue.addLast(i);
            res[i - k + 1] = arr[queue.getFirst().value];
        }

        return res;
    }

    public int[] slidingWindowFirstNegative(int[] arr, int k) {

        int n = arr.length;

        DoublyLinkedList queue = new DoublyLinkedList();

        int[] res = new int[n - k + 1];
        for (int i = 0; i < k && i < n; i++) {
            if (arr[i] < 0)
                queue.addLast(i);
        }

        if (!queue.isEmpty())
            res[0] = arr[queue.getFirst().value];

        for (int i = k; i < n; i++) {
            if (queue.getFirst().value < i - k + 1)
                queue.removeFirst();
            if (arr[i] < 0)
                queue.addLast(i);
            if (!queue.isEmpty())
                res[i - k + 1] = arr[queue.getFirst().value];
        }

        return res;
    }

    public class LRUCache {

        ArrayDeque<int[]> queue;
        HashMap<Integer, int[]> map;
        int capacity;

        public LRUCache(int cap) {
            queue = new ArrayDeque<>();
            map = new HashMap<>();
            this.capacity = cap;
        }

        public void put(int key, int value) {
            if (map.containsKey(key)) {
                queue.remove(map.get(key));
                map.put(key, new int[] { key, value });
                queue.addLast(map.get(key));
            } else {
                if (map.size() == capacity) {
                    int[] re = queue.removeFirst();
                    map.remove(re[0]);
                }
                int[] nn = new int[] { key, value };
                map.put(key, nn);
                queue.addLast(nn);
            }
        }

        public int get(int key) {
            if (!map.containsKey(key))
                return -1;
            queue.remove(map.get(key));
            queue.addLast(map.get(key));
            return map.get(key)[1];
        }

    }

}
