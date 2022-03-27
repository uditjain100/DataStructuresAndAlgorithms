package Stack;

import java.util.*;

import LinkedList.intro_ll.DLLMiddle;
import LinkedList.intro_ll.LinkedList;
import Queue.intro_queue.Queue;;

public class intro_stack {

    // ***** Creation

    public static class Stack {

        int[] arr;
        int top;

        public Stack(int size) {
            this.arr = new int[size];
            this.top = -1;
        }

        public int size() {
            return this.arr.length;
        }

        public boolean isEmpty() {
            return this.top == -1;
        }

        public boolean isFull() {
            return this.top == this.arr.length - 1;
        }

        public void push(int ele) {
            if (isFull())
                return;
            this.arr[++this.top] = ele;
        }

        public int pop() {
            if (this.isEmpty())
                return -1;
            int re = arr[this.top];
            arr[this.top--] = 0;
            return re;
        }

        public int peek() {
            if (this.isEmpty())
                return -1;
            return arr[this.top];
        }

    }

    public static class DynamicStack extends Stack {

        public DynamicStack(int len) {
            super(len);
        }

        @Override
        public void push(int ele) {
            if (isFull()) {
                int[] newArr = new int[arr.length * 2];
                for (int i = 0; i < arr.length; i++)
                    newArr[i] = arr[i];
                newArr[++this.top] = ele;
                arr = newArr;
            }
        }

    }

    public static class StackLL {

        LinkedList list;

        public StackLL() {
            this.list = new LinkedList();
        }

        public boolean isEmpty() {
            return list.isEmpty();
        }

        public void push(int ele) {
            list.addLast(ele);
        }

        public int pop() {
            return list.removeLast().value;
        }

        public int peek() {
            return list.getLast().value;
        }

        public void reverse() {
            list.reverseList();
        }

    }

    class StackUsingQueue {

        Queue queue;
        Queue helper;

        public StackUsingQueue(int len) {
            this.queue = new Queue(len);
            this.helper = new Queue(len);
        }

        public boolean isEmpty() {
            return queue.isEmpty();
        }

        public boolean isFull() {
            return queue.isFull();
        }

        public void push(int ele) {
            queue.enqueue(ele);
        }

        public int pop() {

            if (queue.isEmpty())
                return -1;

            while (!queue.isEmpty())
                helper.enqueue(queue.dequeue());

            int re = helper.dequeue();

            while (!helper.isEmpty())
                queue.enqueue(helper.dequeue());

            return re;
        }

        public int peek() {

            if (queue.isEmpty())
                return -1;

            while (!queue.isEmpty())
                helper.enqueue(queue.dequeue());

            int re = helper.frontEle();

            while (!helper.isEmpty())
                queue.enqueue(helper.dequeue());

            return re;
        }

    }

    class StackUsingPriorityQueue {

        PriorityQueue<Integer> pq;

        public StackUsingPriorityQueue() {
            pq = new PriorityQueue<>();
        }

        public boolean isEmpty() {
            return pq.isEmpty();
        }

        public void push(int ele) {
            pq.offer(ele);
        }

        public int pop() {
            if (pq.isEmpty())
                return -1;

            return pq.poll();
        }

        public int peek() {
            if (pq.isEmpty())
                return -1;

            return pq.peek();
        }

    }

    class TwoStacks {

        int[] arr;
        int top1;
        int top2;

        public TwoStacks(int len) {
            this.arr = new int[len];
            this.top1 = -1;
            this.top2 = arr.length;
        }

        public boolean isEmpty1() {
            return this.top1 == -1;
        }

        public boolean isEmpty2() {
            return this.top2 == -arr.length;
        }

        public boolean isFull() {
            return this.top1 + 1 == this.top2;
        }

        public void push1(int ele) {
            if (isFull())
                return;

            this.arr[++this.top1] = ele;
        }

        public void push2(int ele) {
            if (isFull())
                return;

            this.arr[--this.top2] = ele;
        }

        public int pop1() {
            if (isEmpty1())
                return -1;

            int re = this.arr[this.top1];
            this.arr[this.top1--] = 0;
            return re;
        }

        public int pop2() {
            if (isEmpty2())
                return -1;

            int re = this.arr[this.top2];
            this.arr[this.top2++] = 0;
            return re;
        }

        public int peek1() {
            if (isEmpty1())
                return -1;

            return this.arr[this.top1];
        }

        public int peek2() {
            if (isEmpty2())
                return -1;

            return this.arr[this.top2];
        }

    }

    // ? Important
    class kStacks {

        int[] arr;
        int[] top;
        int[] next;
        int freeIdx;
        int idx;

        public kStacks(int len, int k) {
            this.arr = new int[len];
            this.top = new int[k];
            Arrays.fill(top, -1);
            this.next = new int[len];
            for (int i = 0; i < len - 1; i++)
                next[i] = i + 1;
            next[len - 1] = -1;
            this.idx = 0;
            this.freeIdx = 0;
        }

        public boolean isEmpty(int k) {
            return this.top[k - 1] == -1;
        }

        public boolean isFull() {
            return this.freeIdx == -1;
        }

        public void push(int ele, int k) {
            if (isFull())
                return;

            this.idx = this.freeIdx;
            this.freeIdx = this.next[this.idx];
            this.arr[this.idx] = ele;
            this.next[this.idx] = this.top[k - 1];
            this.top[k - 1] = this.idx;
        }

        public int pop(int k) {
            if (isEmpty(k))
                return -1;

            this.idx = this.top[k - 1];
            int re = this.arr[this.idx];
            this.arr[this.idx] = 0;
            this.top[k - 1] = this.next[this.idx];
            this.next[this.idx] = this.freeIdx;
            this.freeIdx = this.idx;
            return re;
        }

        public int peek(int k) {
            if (isEmpty(k))
                return -1;
            return this.arr[this.top[k - 1]];
        }

    }

    class StackWithMiddle {

        DLLMiddle list;

        public StackWithMiddle() {
            list = new DLLMiddle();
        }

        public boolean isEmpty() {
            return list.isEmpty();
        }

        public void push(int ele) {
            list.addLast(ele);
        }

        public int pop() {
            return list.removeLast().value;
        }

        public int peek() {
            return list.getLast().value;
        }

        public int middle() {
            return list.getMiddle().value;
        }

    }

    // ***** Using Extra Stack can be Done
    // ***** By storing min upto that instant
    class StackWithMin {

        int[] arr;
        int top;
        int min;

        public StackWithMin(int size) {
            this.arr = new int[size];
            this.top = -1;
            this.min = Integer.MAX_VALUE;
        }

        public boolean isEmpty() {
            return this.top == -1;
        }

        public boolean isFull() {
            return this.top == this.arr.length - 1;
        }

        public void push(int ele) {
            if (isFull())
                return;

            if (isEmpty()) {
                this.arr[++this.top] = ele;
                this.min = ele;
            } else {
                if (ele < min) {
                    this.arr[++this.top] = 2 * ele - min; // **** y = 2x-min
                    this.min = ele;
                } else
                    this.arr[++this.top] = ele;
            }
        }

        public int pop() {
            if (this.isEmpty())
                return -1;

            int re = arr[this.top];
            arr[this.top--] = 0;
            if (re < this.min) {
                re = this.min;
                this.min = 2 * min - re; // ****** x = 2min-y;
            }
            return re;
        }

        public int peek() {
            if (this.isEmpty())
                return -1;

            int re = arr[this.top];
            if (re < this.min)
                re = this.min;
            return re;
        }

        public int getMin() {
            if (this.isEmpty())
                return -1;
            return this.min;
        }

    }
}
