package HashMap;

import java.util.*;

@SuppressWarnings("unchecked")
public class intro_map {

    class HashMap<K, V> {

        private class HMPair {
            K key;
            V value;

            public HMPair(K key, V value) {
                this.key = key;
                this.value = value;
            }

            @Override
            public int hashCode() {
                return (int) key;
            }

            @Override
            public boolean equals(Object op) {
                if (op == null)
                    return false;
                HMPair other = (HMPair) op;
                return this.key.equals(other.key);
            }

            @Override
            public String toString() {
                return "{ " + this.key + " - " + this.value + " }";
            }

        }

        private LinkedList<HMPair>[] bucketArray;
        private int size;
        private final static int DEFAULT_SIZE = 10;

        public HashMap() {
            this(DEFAULT_SIZE);
        }

        public HashMap(int capacity) {
            this.bucketArray = new LinkedList[capacity];
            this.size = 0;
        }

        public int hashFunction(K key) {
            return Math.abs(key.hashCode()) % this.bucketArray.length;
        }

        public void put(K key, V value) {

            int bi = this.hashFunction(key);
            LinkedList<HMPair> bucket = this.bucketArray[bi];
            HMPair pta = new HMPair(key, value);

            if (bucket == null) {
                bucket = new LinkedList<HMPair>();
                bucket.addLast(pta);
                this.bucketArray[bi] = bucket;
                this.size++;
            } else {
                int find_idx = find(bi, pta);
                if (find_idx == -1) {
                    bucket.addLast(pta);
                    this.size++;
                } else
                    bucket.get(find_idx).value = value;
            }

            double l = (this.size * 1.0) / this.bucketArray.length;

            if (l > 0.75)
                this.reHash();
        }

        public V get(K key) {

            int bi = this.hashFunction(key);
            LinkedList<HMPair> bucket = this.bucketArray[bi];
            HMPair ptf = new HMPair(key, null);

            if (bucket == null)
                return null;
            else {
                int find_idx = find(bi, ptf);
                if (find_idx == -1)
                    return null;
                else
                    return bucket.get(find_idx).value;
            }
        }

        public V remove(K key) {

            int bi = this.hashFunction(key);
            LinkedList<HMPair> bucket = this.bucketArray[bi];
            HMPair ptf = new HMPair(key, null);

            if (bucket == null) {
                return null;
            } else {
                int find_idx = find(bi, ptf);
                if (find_idx == -1)
                    return null;
                else {
                    HMPair pair = bucket.get(find_idx);
                    bucket.remove(find_idx);
                    this.size--;
                    return pair.value;
                }
            }
        }

        public void display() {
            for (LinkedList<HMPair> bucket : this.bucketArray) {
                if (bucket != null && !bucket.isEmpty())
                    display(bucket);
                else
                    System.out.println("NULL!");
            }
        }

        public void display(LinkedList<HMPair> bucket) {
            System.out.print("[ ");
            for (HMPair pair : bucket)
                System.out.print(pair);
            System.out.print(" ]");
            System.out.println();
        }

        public void reHash() {
            LinkedList<HMPair>[] oba = this.bucketArray;
            this.bucketArray = new LinkedList[2 * oba.length];
            this.size = 0;
            for (LinkedList<HMPair> bucket : oba)
                while (bucket != null && !bucket.isEmpty()) {
                    HMPair pair = bucket.removeLast();
                    this.put(pair.key, pair.value);
                }

        }

        private int find(int bucketIdx, HMPair pair) {
            for (int i = 0; i < this.bucketArray[bucketIdx].size(); i++)
                if (bucketArray[bucketIdx].get(i).key == pair.key)
                    return i;
            return -1;
        }

    }

}
