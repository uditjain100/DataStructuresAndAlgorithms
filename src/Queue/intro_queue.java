package Queue;

import LinkedList.intro_ll.DoublyLinkedList;
import Stack.intro_stack.Stack;

public class intro_queue {

    public static class Queue {

        int[] arr;
        int front;
        int rear;
        int size;

        public Queue(int len) {
            this.arr = new int[len];
            this.front = 0;
            this.rear = 0;
            this.size = 0;
        }

        public boolean isFull() {
            return this.size == arr.length;
        }

        public boolean isEmpty() {
            return this.size == 0;
        }

        public void enqueue(int ele) {
            if (isFull())
                return;
            arr[this.rear++] = ele;
            this.size++;
            this.rear %= arr.length;
        }

        public int dequeue() {
            if (isEmpty())
                return -1;

            int re = arr[this.front];
            arr[this.front++] = 0;
            this.size--;
            this.front %= arr.length;
            return re;
        }

        public int frontEle() {
            if (isEmpty())
                return -1;

            return arr[this.front];
        }

        public void display() {
            for (int i = 0; i < this.size; i++)
                System.out.print(arr[(i + this.front) % arr.length] + " -> ");
            System.out.println();
        }

    }

    class QueueDLL {

        DoublyLinkedList list;

        public QueueDLL() {
            list = new DoublyLinkedList();
        }

        public boolean isEmpty() {
            return list.isEmpty();
        }

        public void enqueue(int ele) {
            list.addLast(ele);
        }

        public int dequeue() {
            if (isEmpty())
                return -1;
            return list.removeFirst().value;
        }

        public int frontEle() {
            if (isEmpty())
                return -1;

            return list.getFirst().value;
        }

    }

    class QueueUsingStack {

        Stack s;
        Stack helper;
        int size;

        public QueueUsingStack(int len) {
            this.s = new Stack(len);
            this.helper = new Stack(len);
            this.size = 0;
        }

        public boolean isFull() {
            return this.size == s.size();
        }

        public boolean isEmpty() {
            return s.isEmpty();
        }

        public void enqueue(int ele) {
            s.push(ele);
        }

        public int dequeue() {
            if (isEmpty())
                return -1;

            while (!s.isEmpty())
                helper.push(s.pop());
            int re = helper.pop();
            while (!helper.isEmpty())
                s.push(helper.pop());
            return re;
        }

        public int frontEle() {
            if (isEmpty())
                return -1;

            while (!s.isEmpty())
                helper.push(s.pop());
            int re = helper.peek();
            while (!helper.isEmpty())
                s.push(helper.pop());
            return re;
        }

    }

}
