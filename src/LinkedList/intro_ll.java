package LinkedList;

import java.util.*;

public class intro_ll {

    public static class LinkedList {

        public static class Node {
            public Node next;
            public int value;

            public Node(int val) {
                this.next = null;
                this.value = val;
            }

        }

        protected Node head;
        protected Node tail;
        protected int size;

        public LinkedList() {
            this.head = this.tail = null;
            this.size = 0;
        }

        public int size() {
            return this.size;
        }

        public boolean isEmpty() {
            return this.size == 0;
        }

        public void addFirst(int ele) {
            if (isEmpty())
                this.head = this.tail = new Node(ele);
            else {
                Node nn = new Node(ele);
                nn.next = this.head;
                this.head = nn;
            }
            this.size++;
        }

        public void addLast(int ele) {
            if (isEmpty())
                this.head = this.tail = new Node(ele);
            else {
                Node nn = new Node(ele);
                this.tail.next = nn;
                this.tail = nn;
            }
            this.size++;
        }

        public void addAt(int ele, int idx) {

            if (idx == 1)
                addFirst(ele);
            else if (idx == this.size)
                addLast(ele);
            else {
                Node nn = new Node(ele);
                Node pre = this.getAt(this.head, idx - 1);
                Node nex = pre.next;

                pre.next = nn;
                nn.next = nex;
            }
            this.size++;
        }

        public Node removeFirst() {

            if (isEmpty())
                return null;

            Node rn = this.head;
            if (this.size == 1) {
                this.head = this.tail = null;
            } else {
                Node nex = this.head.next;
                this.head.next = null;
                this.head = nex;
            }
            this.size--;
            return rn;
        }

        public Node removeLast() {
            if (isEmpty())
                return null;

            Node rn = this.tail;
            if (this.size == 1) {
                this.head = this.tail = null;
            } else {
                Node pre = this.getAt(this.head, this.size - 1);
                pre.next = null;
                this.tail = pre;
            }
            this.size--;
            return rn;
        }

        public Node removeAt(Node head, int idx) {
            if (isEmpty())
                return null;

            if (idx == 1)
                return removeFirst();
            else if (idx == this.size)
                return removeLast();
            else {
                Node pre = this.getAt(head, idx - 1);
                Node nex = pre.next;

                nex.next = null;

                pre.next = nex.next;
                this.size--;
                return nex;
            }

        }

        public Node getFirst() {

            if (isEmpty())
                return null;

            return this.head;
        }

        public Node getLast() {

            if (isEmpty())
                return null;

            return this.tail;
        }

        public Node getAt(Node head, int idx) {

            if (isEmpty())
                return null;

            Node curr = head;
            while (idx-- > 1)
                curr = curr.next;
            return curr;
        }

        public void reverseList() {

            Node prev = null;
            Node curr = this.head;
            Node next = null;

            while (curr != null) {
                next = curr.next;
                curr.next = prev;

                prev = curr;
                curr = next;
            }

            this.tail = this.head;
            this.head = prev;
        }

        public void display(Node head) {
            Node curr = head;

            while (curr != null) {
                System.out.print(curr.value + " -> ");
                curr = curr.next;
            }
            System.out.println();
        }

    }

    public static class DoublyLinkedList {

        public class Node {
            Node prev;
            Node next;
            public int value;

            public Node(int val) {
                this.next = this.prev = null;
                this.value = val;
            }

        }

        private Node head;
        private Node tail;
        private int size;

        public DoublyLinkedList() {
            this.head = this.tail = null;
            this.size = 0;
        }

        public int size() {
            return this.size;
        }

        public boolean isEmpty() {
            return this.size == 0;
        }

        public void addFirst(int ele) {
            if (isEmpty())
                this.head = this.tail = new Node(ele);
            else {
                Node nn = new Node(ele);
                nn.next = this.head;
                this.head = nn;
                nn.next.prev = nn;
            }
            this.size++;
        }

        public void addLast(int ele) {
            if (isEmpty())
                this.head = this.tail = new Node(ele);
            else {
                Node nn = new Node(ele);
                this.tail.next = nn;
                nn.prev = this.tail;
                this.tail = nn;
            }
            this.size++;
        }

        public void addAt(int ele, int idx) {

            if (idx == 1)
                addFirst(ele);
            else if (idx == this.size)
                addLast(ele);
            else {
                Node nn = new Node(ele);
                Node pre = this.getAt(idx - 1);
                Node nex = pre.next;

                pre.next = nn;
                nn.prev = pre;

                nn.next = nex;
                nex.prev = nn;
            }
            this.size++;
        }

        public Node removeFirst() {

            if (isEmpty())
                return null;

            Node rn = this.head;
            if (this.size == 1) {
                this.head = this.tail = null;
            } else {
                Node nex = this.head.next;
                this.head.next = null;
                nex.prev = null;
                this.head = nex;
            }
            this.size--;
            return rn;
        }

        public Node removeLast() {
            if (isEmpty())
                return null;

            Node rn = this.tail;
            if (this.size == 1) {
                this.head = this.tail = null;
            } else {
                Node pre = this.tail.prev;
                this.tail.prev = null;
                pre.next = null;
                this.tail = pre;
            }
            this.size--;
            return rn;
        }

        public Node removeAt(int idx) {
            if (isEmpty())
                return null;

            if (idx == 1)
                return removeFirst();
            else if (idx == this.size)
                return removeLast();
            else {
                Node pre = this.getAt(idx - 1);
                Node nex = pre.next;

                nex.next = nex.prev = null;

                pre.next = nex.next;
                nex.prev = pre;
                this.size--;
                return nex;
            }

        }

        public Node remove(Node node) {
            if (node.prev == null)
                return removeFirst();
            else if (node.next == null)
                return removeLast();
            else {
                int idx = 1;
                Node curr = this.head;
                while (node != curr) {
                    curr = curr.next;
                    idx++;
                }
                return removeAt(idx);
            }
        }

        public Node getFirst() {

            if (isEmpty())
                return null;

            return this.head;
        }

        public Node getLast() {

            if (isEmpty())
                return null;

            return this.tail;
        }

        public Node getAt(int idx) {

            if (isEmpty())
                return null;

            Node curr = this.head;
            while (idx-- > 1)
                curr = curr.next;
            return curr;
        }

    }

    public static class DLLMiddle {

        public class Node {
            Node prev;
            Node next;
            public int value;

            public Node(int val) {
                this.next = this.prev = null;
                this.value = val;
            }

        }

        private Node head;
        private Node tail;
        private Node middle;
        private int size;

        public DLLMiddle() {
            this.head = this.tail = this.middle = null;
            this.size = 0;
        }

        public int size() {
            return this.size;
        }

        public boolean isEmpty() {
            return this.size == 0;
        }

        public void addFirst(int ele) {
            if (isEmpty())
                this.head = this.tail = this.middle = new Node(ele);
            else {
                Node nn = new Node(ele);
                nn.next = this.head;
                this.head = nn;
                nn.next.prev = nn;
                if (this.size % 2 == 0)
                    this.middle = this.middle.next;
            }
            this.size++;
        }

        public void addLast(int ele) {
            if (isEmpty())
                this.head = this.tail = this.middle = new Node(ele);
            else {
                Node nn = new Node(ele);
                this.tail.next = nn;
                nn.prev = this.tail;
                this.tail = nn;
                if (this.size % 2 == 0)
                    this.middle = this.middle.next;
            }
            this.size++;
        }

        public void addAt(int ele, int idx) {

            if (idx == 1)
                addFirst(ele);
            else if (idx == this.size)
                addLast(ele);
            else {
                Node nn = new Node(ele);
                Node pre = this.getAt(idx - 1);
                Node nex = pre.next;

                pre.next = nn;
                nn.prev = pre;

                nn.next = nex;
                nex.prev = nn;
                if (this.size % 2 == 0)
                    this.middle = this.middle.next;
            }
            this.size++;
        }

        public Node removeFirst() {

            if (isEmpty())
                return null;

            Node rn = this.head;
            if (this.size == 1) {
                this.head = this.tail = this.middle = null;
            } else {
                Node nex = this.head.next;
                this.head.next = null;
                nex.prev = null;
                this.head = nex;
                if (this.size % 2 != 0)
                    this.middle = this.middle.prev;
            }
            this.size--;
            return rn;
        }

        public Node removeLast() {
            if (isEmpty())
                return null;

            Node rn = this.tail;
            if (this.size == 1) {
                this.head = this.tail = this.middle = null;
            } else {
                Node pre = this.tail.prev;
                this.tail.prev = null;
                pre.next = null;
                this.tail = pre;
                if (this.size % 2 != 0)
                    this.middle = this.middle.prev;
            }
            this.size--;
            return rn;
        }

        public Node removeAt(int idx) {
            if (isEmpty())
                return null;

            if (idx == 1)
                return removeFirst();
            else if (idx == this.size)
                return removeLast();
            else {
                Node pre = this.getAt(idx - 1);
                Node nex = pre.next;

                nex.next = nex.prev = null;

                pre.next = nex.next;
                nex.prev = pre;
                if (this.size % 2 != 0)
                    this.middle = this.middle.prev;
                this.size--;
                return nex;
            }

        }

        public Node getFirst() {

            if (isEmpty())
                return null;

            return this.head;
        }

        public Node getLast() {

            if (isEmpty())
                return null;

            return this.tail;
        }

        public Node getAt(int idx) {

            if (isEmpty())
                return null;

            Node curr = this.head;
            while (idx-- > 1)
                curr = curr.next;
            return curr;
        }

        public Node getMiddle() {
            return this.middle;
        }

    }

    class CircularLinkedList {

        public class Node {
            public Node next;
            public int value;

            public Node(int val) {
                this.next = null;
                this.value = val;
            }

        }

        protected Node head;
        protected Node tail;
        protected int size;

        public CircularLinkedList() {
            this.head = this.tail = null;
            this.size = 0;
        }

        public int size() {
            return this.size;
        }

        public boolean isEmpty() {
            return this.size == 0;
        }

        public void addFirst(int ele) {
            if (isEmpty()) {
                this.head = this.tail = new Node(ele);
                this.tail.next = this.head;
            } else {
                Node nn = new Node(ele);
                this.tail.next = nn;
                nn.next = this.head;
                this.head = nn;
            }
            this.size++;
        }

        public void addLast(int ele) {
            if (isEmpty()) {
                this.head = this.tail = new Node(ele);
                this.tail.next = this.head;
            } else {
                Node nn = new Node(ele);
                this.tail.next = nn;
                this.tail = nn;
                nn.next = this.head;
            }
            this.size++;
        }

        public void addAt(int ele, int idx) {

            if (idx == 1)
                addFirst(ele);
            else if (idx == this.size)
                addLast(ele);
            else {
                Node nn = new Node(ele);
                Node pre = this.getAt(this.head, idx - 1);
                Node nex = pre.next;

                pre.next = nn;
                nn.next = nex;
            }
            this.size++;
        }

        public Node removeFirst() {

            if (isEmpty())
                return null;

            Node rn = this.head;
            if (this.size == 1) {
                this.head.next = this.tail.next = null;
                this.head = this.tail = null;
            } else {
                Node nex = this.head.next;
                this.head.next = null;
                this.head = nex;
                this.tail.next = nex;
            }
            this.size--;
            return rn;
        }

        public Node removeLast() {
            if (isEmpty())
                return null;

            Node rn = this.tail;
            if (this.size == 1) {
                this.head.next = this.tail.next = null;
                this.head = this.tail = null;
            } else {
                Node pre = this.getAt(this.head, this.size - 1);
                pre.next = this.head;
                this.tail = pre;
            }
            this.size--;
            return rn;
        }

        public Node removeAt(Node head, int idx) {
            if (isEmpty())
                return null;

            if (idx == 1)
                return removeFirst();
            else if (idx == this.size)
                return removeLast();
            else {
                Node pre = this.getAt(head, idx - 1);
                Node nex = pre.next;

                nex.next = null;

                pre.next = nex.next;
                this.size--;
                return nex;
            }

        }

        public Node getFirst() {

            if (isEmpty())
                return null;

            return this.head;
        }

        public Node getLast() {

            if (isEmpty())
                return null;

            return this.tail;
        }

        public Node getAt(Node head, int idx) {

            if (isEmpty())
                return null;

            Node curr = head;
            while (idx-- > 1)
                curr = curr.next;
            return curr;
        }

    }

    class CircularDoublyLinkedList {

        public class Node {
            Node prev;
            Node next;
            public int value;

            public Node(int val) {
                this.next = this.prev = null;
                this.value = val;
            }

        }

        private Node head;
        private Node tail;
        private int size;

        public CircularDoublyLinkedList() {
            this.head = this.tail = null;
            this.size = 0;
        }

        public int size() {
            return this.size;
        }

        public boolean isEmpty() {
            return this.size == 0;
        }

        public void addFirst(int ele) {
            if (isEmpty()) {
                this.head = this.tail = new Node(ele);
            } else {
                Node nn = new Node(ele);
                nn.next = this.head;
                this.head = nn;
                nn.next.prev = nn;
            }
            this.tail.next = this.head;
            this.head.prev = this.tail;
            this.size++;
        }

        public void addLast(int ele) {
            if (isEmpty())
                this.head = this.tail = new Node(ele);
            else {
                Node nn = new Node(ele);
                this.tail.next = nn;
                nn.prev = this.tail;
                this.tail = nn;
            }
            this.tail.next = this.head;
            this.head.prev = this.tail;
            this.size++;
        }

        public void addAt(int ele, int idx) {

            if (idx == 1)
                addFirst(ele);
            else if (idx == this.size)
                addLast(ele);
            else {
                Node nn = new Node(ele);
                Node pre = this.getAt(idx - 1);
                Node nex = pre.next;

                pre.next = nn;
                nn.prev = pre;

                nn.next = nex;
                nex.prev = nn;
            }
            this.size++;
        }

        public Node removeFirst() {

            if (isEmpty())
                return null;

            Node rn = this.head;
            if (this.size == 1) {
                this.head.prev = this.tail.next = null;
                this.head = this.tail = null;
            } else {
                Node nex = this.head.next;
                this.head.next = null;
                nex.prev = null;
                this.head = nex;
                this.tail.next = this.head;
                this.head.prev = this.tail;
            }
            this.size--;
            return rn;
        }

        public Node removeLast() {
            if (isEmpty())
                return null;

            Node rn = this.tail;
            if (this.size == 1) {
                this.head.prev = this.tail.next = null;
                this.head = this.tail = null;
            } else {
                Node pre = this.tail.prev;
                this.tail.prev = null;
                pre.next = null;
                this.tail = pre;
                this.tail.next = this.head;
                this.head.prev = this.tail;
            }
            this.size--;
            return rn;
        }

        public Node removeAt(int idx) {
            if (isEmpty())
                return null;

            if (idx == 1)
                return removeFirst();
            else if (idx == this.size)
                return removeLast();
            else {
                Node pre = this.getAt(idx - 1);
                Node nex = pre.next;

                nex.next = nex.prev = null;

                pre.next = nex.next;
                nex.prev = pre;
                this.size--;
                return nex;
            }

        }

        public Node remove(Node node) {
            if (node.prev == null)
                return removeFirst();
            else if (node.next == null)
                return removeLast();
            else {
                int idx = 1;
                Node curr = this.head;
                while (node != curr) {
                    curr = curr.next;
                    idx++;
                }
                return removeAt(idx);
            }
        }

        public Node getFirst() {

            if (isEmpty())
                return null;

            return this.head;
        }

        public Node getLast() {

            if (isEmpty())
                return null;

            return this.tail;
        }

        public Node getAt(int idx) {

            if (isEmpty())
                return null;

            Node curr = this.head;
            while (idx-- > 1)
                curr = curr.next;
            return curr;
        }

    }

    class LinkedListRandom extends LinkedList {

        public class Node {
            public Node next;
            public Node random;
            public int value;

            public Node(int val) {
                this.random = this.next = null;
                this.value = val;
            }
        }

        // ? Using O(n) of space
        public Node clone1(Node head) {
            HashMap<Node, Node> map = new HashMap<>();
            Node newHead = new Node(-1);

            Node curr = head;
            Node newCurr = newHead;
            while (curr != null) {
                Node nn = new Node(curr.value);
                map.put(curr, nn);

                newCurr.next = nn;
                curr = curr.next;
                newCurr = newCurr.next;
            }

            newHead = newHead.next;

            newCurr = newHead;
            curr = head;
            while (newCurr != null) {
                newCurr.random = map.get(curr.random);
                curr = curr.next;
                newCurr = newCurr.next;
            }

            return newHead;
        }

        // ? Without Using any Space
        public Node clone2(Node head) {
            if (head == null)
                return head;

            Node newHead = new Node(-1);

            Node curr = head;
            Node newCurr = newHead;
            while (curr != null) {
                Node nn = new Node(curr.value);

                newCurr.next = nn;

                curr = curr.next;
                newCurr = newCurr.next;
            }

            newHead = newHead.next;

            newCurr = newHead;
            curr = head;

            Node h = new Node(-1);

            Node temp = h;
            while (curr != null) {
                temp.next = curr;
                temp = temp.next;
                curr = curr.next;
                temp.next = newCurr;
                temp = temp.next;
                newCurr = newCurr.next;
            }

            h = h.next;

            temp = h;
            while (temp != null) {
                temp.next.random = (temp.random != null) ? temp.random.next : null;
                temp = temp.next.next;
            }

            head = curr = h;
            newHead = newCurr = h.next;

            while (curr != null) {
                curr.next = curr.next.next;
                if (newCurr.next != null)
                    newCurr.next = newCurr.next.next;

                curr = curr.next;
                newCurr = newCurr.next;
            }

            return newHead;
        }

    }

    class LLLists extends LinkedList {

        class Node {
            int value;
            Node next;
            Node down;

            public Node(int val) {
                this.next = this.down = null;
                this.value = val;
            }
        }

        // *https://www.geeksforgeeks.org/flattening-a-linked-list/
        public Node flatten1(Node head) {

            if (head == null)
                return head;

            Node curr = head;
            while (curr != null) {
                Node next = curr.next;
                curr.next = curr.down;
                curr.down = null;

                Node temp = curr.next;
                while (temp.next != null)
                    temp = temp.next;

                temp.next = next;
                curr = next;
            }

            return head;
        }

        public Node merge(Node h1, Node h2) {
            if (h1 == null)
                return h2;
            if (h2 == null)
                return h1;

            Node nn = new Node(-1);
            Node res = nn;

            Node c1 = h1;
            Node c2 = h2;
            while (c1 != null && c2 != null) {
                if (c1.value < c2.value) {
                    nn.next = new Node(c1.value);
                    c1 = c1.next;
                } else if (c1.value > c2.value) {
                    nn.next = new Node(c2.value);
                    c2 = c2.next;
                } else {
                    nn.next = new Node(c1.value);
                    nn = nn.next;
                    nn.next = new Node(c2.value);
                    c1 = c1.next;
                    c2 = c2.next;
                }
                nn = nn.next;
            }

            if (c1 != null)
                nn.next = c1;
            if (c2 != null)
                nn.next = c2;

            return res.next;
        }

        // *** All linked lists are sorted
        // *** The flattened linked list should also be sorted
        public Node flatten2(Node head) {

            if (head == null)
                return head;

            head.next = flatten1(head.next);

            Node down = head.down;
            head.down = null;

            return merge(head, down);
        }

    }

    class MultiLevelLinkedList {

        class Node {
            int value;
            Node next;
            Node child;

            public Node(int val) {
                this.next = this.child = null;
                this.value = val;
            }
        }

        // **https://www.geeksforgeeks.org/flatten-a-multi-level-linked-list-set-2-depth-wise/
        public Node flattenDepthWise(Node head) {
            if (head == null)
                return head;

            head.child = flattenDepthWise(head.child);
            head.next = flattenDepthWise(head.next);

            Node next = head.next;
            head.next = head.child;

            Node curr = head;
            while (curr.next != null)
                curr = curr.next;

            curr.next = next;
            return head;
        }

        public Node flattenBreadthWise(Node head) {
            if (head == null)
                return head;

            head.next = flattenBreadthWise(head.next);
            head.child = flattenBreadthWise(head.child);

            Node child = head.child;
            head.child = null;

            Node curr = head;
            while (curr.next != null)
                curr = curr.next;

            curr.next = child;
            return head;
        }

        // ***https://www.geeksforgeeks.org/flatten-a-linked-list-with-next-and-child-pointers/
        public Node flatten(Node node) {
            java.util.LinkedList<Node> queue = new java.util.LinkedList<>();
            queue.addFirst(node);

            Node newHead = new Node(-1);
            Node newCurr = newHead;

            while (!queue.isEmpty()) {
                int size = queue.size();
                while (size-- > 0) {
                    Node rn = queue.removeFirst();
                    newCurr.next = rn;

                    while (newCurr != null) {
                        if (newCurr.child != null)
                            queue.addLast(newCurr.child);
                        newCurr.child = null;
                        newCurr = newCurr.next;
                    }
                }
            }

            return newHead.next;
        }

    }

}
