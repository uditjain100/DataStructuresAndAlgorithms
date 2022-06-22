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

    public class LRUCache1 {

        ArrayDeque<Integer> queue;
        HashMap<Integer, int[]> map;
        int capacity;

        public LRUCache1(int cap) {
            queue = new ArrayDeque<>();
            map = new HashMap<>();
            this.capacity = cap;
        }

        public void put(int key, int value) {
            if (map.containsKey(key))
                queue.remove(key);
            else {
                if (map.size() == capacity)
                    map.remove(queue.removeFirst());
            }
            map.put(key, new int[] { key, value });
            queue.addLast(key);
        }

        public int get(int key) {
            if (!map.containsKey(key))
                return -1;
            queue.remove(key);
            queue.addLast(key);
            return map.get(key)[1];
        }

    }

    public class LRUCache2 {

        public class Node {

            Node prev, next;
            int key, value;

            public Node(int key, int value) {
                this.prev = this.next = null;
                this.key = key;
                this.value = value;
            }

        }

        public Node head;
        public Node tail;

        HashMap<Integer, Node> map;
        int capacity;

        public LRUCache2(int cap) {
            this.head = this.tail = null;
            map = new HashMap<>();
            this.capacity = cap;
        }

        public void add(Node nn) {
            if (this.head == null) {
                this.head = this.tail = nn;
            } else {
                this.tail.next = nn;
                nn.prev = this.tail;
                this.tail = nn;
            }
        }

        public void remove(Node node) {

            if (node.prev == null && node.next == null) {
                this.head = this.tail = node = null;
            } else if (node.prev == null) { // head
                Node newHead = node.next;
                node.next = null;
                newHead.prev = null;
                this.head = newHead;
            } else if (node.next == null) { // tail
                Node newTail = node.prev;
                node.prev = null;
                newTail.next = null;
                this.tail = newTail;
            } else {
                Node p = node.prev;
                Node n = node.next;
                p.next = n;
                n.prev = p;
                node.prev = node.next = null;
            }

        }

        public void put(int key, int value) {
            Node nn = null;
            if (map.containsKey(key)) {
                nn = map.get(key);
                remove(map.get(key));
                nn.value = value;
            } else {
                if (this.capacity == map.size()) {
                    map.remove(this.head.key);
                    remove(this.head);
                }
                nn = new Node(key, value);
            }
            add(nn);
            map.put(key, nn);
        }

        public int get(int key) {
            if (!map.containsKey(key))
                return -1;
            remove(map.get(key));
            add(map.get(key));
            return map.get(key).value;
        }

    }

}
