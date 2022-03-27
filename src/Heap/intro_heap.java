package Heap;

import java.util.*;

public class intro_heap {

    public static class Heap {

        ArrayList<Integer> list;
        boolean isMin;

        public Heap(boolean isMin) {
            this.list = new ArrayList<>();
            this.isMin = isMin;
        }

        public boolean isEmpty() {
            return list.isEmpty();
        }

        public void constructHeap(int[] arr) {
            for (int ele : arr)
                list.add(ele);

            for (int i = arr.length / 2; i >= 0; i--)
                downHeapify(i);
        }

        public void constructHeap(ArrayList<Integer> arr) {
            for (int ele : arr)
                list.add(ele);

            for (int i = this.list.size() / 2; i >= 0; i--)
                downHeapify(i);
        }

        public int size() {
            return list.size();
        }

        public void insert(int ele) {
            list.add(ele);
            upHeapify(list.size() - 1);
        }

        public int remove() {
            swap(0, list.size() - 1);
            int re = list.remove(list.size() - 1);
            downHeapify(0);
            return re;
        }

        public int getTopElement() {
            if (isEmpty())
                return -1;
            return list.get(0);
        }

        // *** is first index i.e, i is what we want ?
        public int compare(int i, int j) {
            if (list.get(i) == list.get(j))
                return 0;

            if (this.isMin)
                return (list.get(i) < list.get(j)) ? 1 : -1;
            else
                return (list.get(i) > list.get(j)) ? 1 : -1;
        }

        public void upHeapify(int idx) {
            if (idx == 0)
                return;

            int pi = (idx - 1) / 2;

            if (compare(idx, pi) == 1) {
                swap(idx, pi);
                upHeapify(pi);
            }
        }

        public void downHeapify(int idx) {

            if (idx >= this.list.size() / 2)
                return;

            int fci = (2 * idx) + 1;
            int sci = (2 * idx) + 2;

            int ni = idx;

            if (compare(ni, fci) == -1)
                ni = fci;
            if (sci < list.size() && compare(ni, sci) == -1)
                ni = sci;

            if (ni != idx) {
                swap(ni, idx);
                downHeapify(ni);
            }
        }

        public void swap(int i, int j) {
            int temp = list.get(i);
            list.set(i, list.get(j));
            list.set(j, temp);
        }

        public void convert() {
            this.isMin = !this.isMin;
            for (int i = (this.list.size() - 2) / 2; i >= 0; i--)
                downHeapify(i);
        }

        public void display() {

            ArrayList<Integer> temp = new ArrayList<>();
            for (int ele : this.list)
                temp.add(ele);

            ArrayList<Integer> res = new ArrayList<>();
            while (!isEmpty())
                res.add(remove());

            this.list = temp;

            System.out.println(res);
        }

    }

    class medianPriorityQueue {

        PriorityQueue<Integer> minHeap;
        PriorityQueue<Integer> maxHeap;

        public medianPriorityQueue() {
            minHeap = new PriorityQueue<>();
            maxHeap = new PriorityQueue<>();
        }

        public boolean isEmpty() {
            return minHeap.isEmpty() && maxHeap.isEmpty();
        }

        public void add(int ele) {
            if (minHeap.isEmpty() && maxHeap.isEmpty())
                maxHeap.add(ele);
            else if (maxHeap.isEmpty() && !minHeap.isEmpty()) {
                if (ele > minHeap.peek())
                    minHeap.add(ele);
                else
                    maxHeap.add(ele);
            } else if (!maxHeap.isEmpty() && minHeap.isEmpty()) {
                if (ele < maxHeap.peek())
                    maxHeap.add(ele);
                else
                    minHeap.add(ele);
            } else {
                if (ele > minHeap.peek())
                    minHeap.add(ele);
                else
                    maxHeap.add(ele);
            }

            if (maxHeap.size() < minHeap.size())
                maxHeap.add(minHeap.remove());
            else if (maxHeap.size() > minHeap.size() + 1)
                minHeap.add(maxHeap.remove());
        }

        public int remove() {
            if (isEmpty())
                return -1;

            int rEle = maxHeap.remove();

            if (maxHeap.size() < minHeap.size())
                maxHeap.add(minHeap.remove());

            return rEle;
        }

        public int median() {
            if (isEmpty())
                return -1;

            return maxHeap.peek();
        }

        public int peek() {
            if (isEmpty())
                return -1;

            return maxHeap.peek();
        }

    }

}
