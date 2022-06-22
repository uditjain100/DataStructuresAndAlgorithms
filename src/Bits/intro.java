package Bits;

import java.util.*;

public class intro {

    public int on(int num, int idx) {
        int mask = (1 << (idx - 1));
        num |= mask;
        return num;
    }

    public int off(int num, int idx) {
        int mask = (1 << (idx - 1));
        mask = ~mask;
        num &= mask;
        return num;
    }

    public int onTOoff(int num, int idx) {
        int mask = (1 << (idx - 1));
        if ((mask & num) != 0)
            num ^= mask;
        return num;
    }

    public int offTOon(int num, int idx) {
        int mask = (1 << (idx - 1));
        if ((mask & num) == 0)
            num ^= mask;
        return num;
    }

    public int flipBit(int num, int idx) {
        int mask = (1 << (idx - 1));
        num ^= mask;
        return num;
    }

    public int flipBitsInRange(int num, int si, int ei) {
        for (int i = si; i <= ei; i++)
            flipBit(num, i);
        return num;
    }

    public int bitsToBeFlippedToMakeNumbersEqual(int a, int b) {
        int res = a ^ b;
        return countBits(res);
    }

    // *https://www.youtube.com/watch?v=GbH8PcqKosk&list=PL-Jc9J83PIiFJRioti3ZV7QabwoJK6eKe&index=26
    public int swapOddEvenBits(int num) {
        int odd = 0x55555555;
        int even = 0xAAAAAAAA;

        odd &= num;
        even &= num;

        odd <<= 1; // ? left shift => double
        even >>= 1; // ? right shift => half

        return odd | even;
    }

    public int firstMostSignificantSetBit(int num) {
        for (int i = 31; i >= 0; i--)
            if (((1 << i) & num) != 0)
                return i;
        return -1;
    }

    public int firstLeastSignificantSetBit(int num) {
        int n = (~num + 1) & num;
        return (num == 0) ? -1 : (int) Math.log(n);
    }

    // *Kernighan's Algorithm
    public int countBits(int num) {
        int count = 0;
        while (num != 0) {
            num &= (num - 1);
            count++;
        }
        return count;
    }

    // ********* Count the number of Bits from 1 to num
    public int countBitsInRange(int num) {
        if (num == 0 || num == 1)
            return num;

        int powerOf2 = highestPowerOf2(num);
        int n = (int) Math.pow(2, powerOf2 - 1);
        // ****** res contains all the count bits upto highest power of 2 i.e, ((n) *
        // ****** (2^(n-1)))
        int res = powerOf2 * n;
        res += countBitsInRange(num - (2 * n)) + (num - (2 * n) + 1);
        return res;
    }

    // *https://www.youtube.com/watch?v=DMTw6pP5zTg&list=PL-Jc9J83PIiFJRioti3ZV7QabwoJK6eKe&index=15
    public int countSmallerNumbersWithSameSetBits(int n) {
        return (n == 0 || n == 1) ? n : countSmallerNumbersWithSameSetBits(n, countBits(n), 31);
    }

    public int countSmallerNumbersWithSameSetBits(int n, int bits, int i) {
        if (i == 0 || i == 1)
            return 0;

        int mask = (1 << i);
        int ans = 0;
        if ((mask & n) == 0)
            ans += countSmallerNumbersWithSameSetBits(n, bits, i - 1);
        else {
            ans += countSmallerNumbersWithSameSetBits(n, bits - 1, i - 1);
            ans += ncr(i - 1, bits);
        }

        return ans;
    }

    public int ncr(int n, int r) {
        return factorial(n) / (factorial(r) * factorial(n - r));
    }

    public int factorial(int n) {
        return (n == 0 || n == 1) ? n : n * factorial(n - 1);
    }

    public int copyParticularBit(int x, int y, int idx) {
        return ((1 << idx) == 0) ? off(x, idx) : on(x, idx);
    }

    public int copyBitsInRange(int x, int y, int l, int r) {
        for (int i = l; i <= r; i++)
            x = copyParticularBit(x, y, i);
        return x;
    }

    public int reverseBits(int num) {

        int si = 0;
        int ei = firstMostSignificantSetBit(num);

        int length = ei - si + 1;

        int res = 0;

        while (si < ei) {

            int mask1 = (1 << si);
            int mask2 = (1 << ei);

            mask1 &= num;
            mask2 &= num;

            if (mask1 != 0)
                res |= (1 << (length - si - 1));
            if (mask2 != 0)
                res |= (1 << (length - ei - 1));

            si++;
            ei--;
        }

        return res;

    }

    public int reverseBitsInRange(int num, int l, int r) {

        int si = l;
        int ei = r;

        int length = ei - si + 1;

        int res = 0;

        while (si < ei) {

            int mask1 = (1 << si);
            int mask2 = (1 << ei);

            mask1 &= num;
            mask2 &= num;

            if (mask1 != 0)
                res |= (1 << (length - si - 1));
            if (mask2 != 0)
                res |= (1 << (length - ei - 1));

            si++;
            ei--;
        }

        return res;

    }

    public int highestPowerOf2(int num) {
        if (num == 0)
            return num;

        int count = 0;
        while ((1 << count) <= num)
            count++;
        return count - 1;
    }

    public boolean isPowerOf2(int num) {
        return num == 0 || countBits(num) == 1;
    }

    public boolean isPowerOf4(int num) {
        int count = 0;
        for (int i = 0; i < 32; i++) {
            int mask = (1 << i);
            if ((mask & num) != 0) {
                if ((i & 2) != 0)
                    return false;
                count++;
            }
            if (count > 1)
                return false;
        }
        return true;
    }

    // ********** 7n/8 = n - (n/8)
    public int calculate7nBy8(int num) {
        int nBy8 = (num >> 3);
        return num - nBy8;
    }

    public int add(int a, int b) {
        int res = 0;
        int carry = 0;
        for (int i = 0; i < 32; i++) {
            int mask1 = (1 << i);
            int mask2 = (1 << i);

            mask1 &= a;
            mask2 &= b;

            int bit1 = (mask1 != 0) ? 1 : 0;
            int bit2 = (mask2 != 0) ? 1 : 0;

            int ans = bit1 ^ bit2 ^ carry;
            if (ans != 0)
                res |= (1 << i);

            carry = ((bit1 ^ bit2) & carry) | (bit1 & bit2);
        }

        return res;
    }

    public int subtract(int a, int b) {

        int res = 0;
        int carry = 0;
        for (int i = 0; i < 32; i++) {
            int mask1 = (1 << i);
            int mask2 = (1 << i);

            mask1 &= a;
            mask2 &= b;

            int bit1 = (mask1 != 0) ? 1 : 0;
            int bit2 = (mask2 != 0) ? 1 : 0;

            int ans = bit1 ^ bit2 ^ carry;
            if (ans != 0)
                res |= (1 << i);

            carry = (~(bit1 ^ bit2) & carry) | (~bit1 & bit2);
        }

        return res;
    }

    public int division(int a, int b) {
        if (a > 0 && b == 0)
            return Integer.MAX_VALUE;
        if (a < 0 && b == 0)
            return Integer.MIN_VALUE;
        if (a == 0)
            return 0;
        if (b == 1)
            return a;

        int sign = ((a < 0 && b > 0) || (b < 0 && a > 0)) ? 1 : 0;

        a = Math.abs(a);
        b = Math.abs(b);

        if (a == b)
            return (sign == 1) ? -1 : 1;

        int quotient = 0;
        while (a >= b) {
            int i = 0;
            for (i = 0; i < 32; i++)
                if (((1 << i) * b) > a)
                    break;
            a -= (b * (1 << (i - 1)));
            quotient += (1 << (i - 1));
        }
        return (sign == 1) ? -quotient : quotient;
    }

    // ? *we can calculate square without recursion i.e, 15*15 -> 15*(8 + 4 + 2 + 1)
    public int square(int num) {
        if (num == 0 || num == 1)
            return num;

        int x = (num >> 1);

        if ((num & 1) != 0)
            return (square(x) << 2) + (x << 2) + 1;
        else
            return (square(x) << 2);
    }

    public int uniqueNumberInDuplet(int[] arr) {
        if (arr.length == 0)
            return -1;

        int num = 0;
        for (int ele : arr)
            num ^= ele;

        return num;
    }

    public int uniqueNumberInTriplet(int[] arr) {

        int[] count = new int[32];

        for (int ele : arr) {
            for (int i = 0; i < 32; i++) {
                int mask = (1 << i);
                if ((mask & ele) != 0)
                    count[i]++;
                count[i] %= 3;
            }
        }
        int res = 0;
        for (int i = 0; i < 32; i++) {
            int mask = (1 << i);
            if (count[i] != 0)
                res |= mask;
        }
        return res;
    }

    public int[] twoUniqueNumbers(int[] arr) {

        int num = 0;
        for (int ele : arr)
            num ^= ele;

        int mask = (1 << firstLeastSignificantSetBit(num));

        int ans1 = 0;
        int ans2 = 0;

        for (int ele : arr)
            if ((ele & mask) == 0)
                ans1 ^= ele;
            else
                ans2 ^= ele;

        return new int[] { ans1, ans2 };
    }

    public boolean multipleOf3(int num) {

        int count1 = 0;
        int count2 = 0;

        for (int i = 0; i < 32; i++) {
            int mask = (1 << i);
            if ((i & 1) == 0) {
                if ((mask & num) != 0)
                    count1++;
            } else {
                if ((mask & num) != 0)
                    count2++;
            }
        }

        return Math.abs(count1 - count2) % 3 == 0;
    }

    // ******* A number ‘n’ is called Bleak if it cannot be represented as sum of a
    // ******* positive number x and set bit count in x
    public boolean isBleak(int num) {

        // **** The idea is based on the fact that the largest count of set bits in any
        // **** number smaller than n cannot exceed ceiling of Log2n
        int si = (int) Math.ceil(Math.log(num));

        for (int i = num - si; i < num; i++)
            if (i + countBits(i) == num)
                return false;

        return true;
    }

    public int bitDifferenceAllPairs(int[] arr) {
        int n = arr.length;

        int res = 0;
        for (int i = 0; i < 32; i++) {
            int count = 0;
            for (int ele : arr)
                if (((i << i) & ele) != 0)
                    count++;
            res += (count - (n - count)) * 2; // ? ****** Important
        }
        return res;
    }

    // **** Print gray code with n bits;
    public ArrayList<Integer> grayCode(int num) {
        ArrayList<String> list = getGrayCode(num);
        ArrayList<Integer> res = new ArrayList<>();

        for (String str : list)
            res.add(Integer.parseInt(str, 2));
        return res;
    }

    private ArrayList<String> getGrayCode(int n) {
        if (n == 0) {
            ArrayList<String> br = new ArrayList<>();
            br.add("0");
            br.add("1");
            return br;
        }

        ArrayList<String> res = new ArrayList<>();
        ArrayList<String> rr = getGrayCode(n - 1);

        for (int i = 0; i < rr.size(); i++)
            res.add("0" + rr.get(i));
        for (int i = rr.size() - 1; i >= 0; i--)
            res.add("1" + rr.get(i));

        return res;
    }

    // * There are n people standing in a circle waiting to be executed. The
    // * counting out begins at some point in the circle and proceeds around the
    // * circle in a fixed direction. In each step, a certain number of people are
    // * skipped and the next person is executed. The elimination proceeds around
    // * the
    // * circle (which is becoming smaller and smaller as the executed people are
    // * removed), until only the last person remains, who is given freedom. Given
    // * the
    // * total number of persons n and a number k which indicates that k-1 persons
    // * are
    // * skipped and kth person is killed in circle. The task is to choose the place
    // * in the initial circle so that you are the last one remaining and so
    // * survive.
    public int joseph_problem(int num, int k) {
        if (num == 1)
            return 0;

        int res = joseph_problem(num - 1, k);
        return (res + k) % num;
    }

    // ********* k = 2
    public int joseph_problem_alternate(int num) {
        int remaining = num - (1 << highestPowerOf2(num));
        return (remaining << 1) + 1;
    }

    // ? ****** frequency -> (i + 1) * (n - i)
    // **** Similarly for any bitwise operations i.e, OR, AND ...
    public int xorOfAllSubArray(int[] arr) {
        int n = arr.length;
        int res = 0;
        for (int i = 0; i < n; i++) {
            if (((i + 1) * (n - i)) % 2 != 0)
                res ^= arr[i];
        }
        return res;
    }

    public int maxOrValuePair(int[] arr) {
        int max = 0;
        for (int ele : arr)
            max = Math.max(max, ele);
        int ans = 0;
        for (int ele : arr)
            if (ele != max)
                ans = Math.max(ans, max | ele);
        return ans;
    }

    public int maxANDvaluePair(int[] arr) {
        int res = 0;
        int mask = 0;
        for (int i = 31; i >= 0; i--) {
            mask = res;
            mask |= (1 << i);
            int count = 0;
            for (int ele : arr)
                if ((mask & ele) == mask)
                    count++;
            if (count > 1)
                res = mask;
        }
        return res;
    }

    public static class Trie {

        public class Node {
            int value;
            Node[] bits;
            int count;

            public Node() {
                this.value = -1;
                this.bits = new Node[2];
                this.count = 0;
            }
        }

        private Node root;

        public Trie() {
            this.root = new Node();
        }

        public void insert(int ele) {
            Node curr = root;
            for (int i = 31; i >= 0; i--) {
                int idx = (((1 << i) & ele) != 0) ? 1 : 0;
                if (curr.bits[idx] == null)
                    curr.bits[idx] = new Node();
                curr.bits[idx].count++;
                curr = curr.bits[idx];
            }
            curr.value = ele;
        }

        public void constructTrie(int[] arr) {
            for (int ele : arr) {
                Node curr = root;
                for (int i = 31; i >= 0; i--) {
                    int mask = (1 << i);
                    int idx = ((mask & ele) != 0) ? 1 : 0;
                    if (curr.bits[idx] == null)
                        curr.bits[idx] = new Node();
                    curr.bits[idx].count++;
                    curr = curr.bits[idx];
                }
                curr.value = ele;
            }
        }

        public int minXOR(int ele) {

            Node curr = root;
            int ans = 0;
            for (int i = 31; i >= 0; i--) {
                int currBit = (ele & (1 << i)) & 1;
                if (curr.bits[currBit] != null) { // ****** we can go in same direction
                    ans |= (1 << i);
                    curr = curr.bits[currBit];
                } else
                    curr = curr.bits[currBit ^ 1];
            }
            return ans;
        }

        public int maxXOR(int ele) {

            Node curr = root;
            int ans = 0;
            for (int i = 31; i >= 0; i--) {
                int mask = (1 << i);
                int currBit = ((mask & ele) != 0) ? 1 : 0;
                if (curr.bits[currBit ^ 1] != null) { // **** we can go in opposite direction
                    ans |= (1 << i);
                    curr = curr.bits[currBit ^ 1];
                } else
                    curr = curr.bits[currBit];
            }
            return ans;
        }

        public int countLessNumbers(int ele, int bound) {
            return countLessNumbers(this.root, ele, bound, 31);
        }

        private int getCount(Node node) {
            return (node == null) ? 0 : node.count;
        }

        private int countLessNumbers(Node node, int ele, int bound, int idx) {

            if (node == null)
                return 0;
            if (idx == -1)
                return getCount(node);

            int valueBit = (((1 << idx) & ele) != 0) ? 1 : 0;
            int boundBit = (((1 << idx) & bound) != 0) ? 1 : 0;

            if (valueBit == 0) {
                if (boundBit == 0)
                    return countLessNumbers(node.bits[0], ele, bound, idx - 1);
                else
                    return getCount(node.bits[0]) + countLessNumbers(node.bits[1], ele, bound, idx - 1);
            } else {
                if (boundBit == 0)
                    return countLessNumbers(node.bits[1], ele, bound, idx - 1);
                else
                    return getCount(node.bits[1]) + countLessNumbers(node.bits[0], ele, bound, idx - 1);
            }

        }

    }

    public int maxXORvaluePair(int[] arr) {
        Trie trie = new Trie();
        trie.constructTrie(arr);

        int ans = Integer.MIN_VALUE;
        for (int ele : arr)
            ans = Math.max(ans, trie.maxXOR(ele));
        return ans;
    }

    public int minXORvaluePair(int[] arr) {
        Arrays.sort(arr);

        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < arr.length - 1; i++)
            ans = Math.min(ans, arr[i] ^ arr[i + 1]);
        return ans;
    }

    public int xorPairInRange(int[] arr, int l, int r) {
        Trie trie = new Trie();
        trie.insert(arr[0]);

        int ans = 0;
        for (int i = 1; i < arr.length; i++) {
            ans += trie.countLessNumbers(arr[i], r) - trie.countLessNumbers(arr[i], l - 1);
            trie.insert(arr[i]);
        }
        return ans;
    }

    public int maxXORvalueSubarray(int[] arr) {
        Trie trie = new Trie();
        trie.insert(arr[0]);

        int res = Integer.MIN_VALUE;
        for (int i = 1; i < arr.length; i++) {
            int currMax = trie.maxXOR(arr[i]);
            res = Math.max(res, currMax);
            trie.insert(arr[i]);
        }
        return res;
    }

    public int minXORvalueSubarray(int[] arr) {
        Trie trie = new Trie();
        trie.insert(arr[0]);

        int res = Integer.MAX_VALUE;
        for (int i = 1; i < arr.length; i++) {
            int currMin = trie.minXOR(arr[i]);
            res = Math.min(res, currMin);
            trie.insert(arr[i]);
        }
        return res;
    }

    // *https://www.geeksforgeeks.org/find-n-th-number-whose-binary-representation-palindrome/
    public int nthPalindromicBinary(int n) {
        int count = 1;
        int len = 1;

        while (count < n) {
            len++;
            count += (1 << ((len - 1) / 2));
        }

        count -= (1 << ((len - 1) / 2));
        int offset = n - count - 1;

        int ans = ((1 << (len - 1)) | (offset << (len / 2)));
        int reverse = reverseBits((ans >> (len / 2)));

        return ans | reverse;
    }

    // *https://leetcode.com/problems/utf-8-validation/
    public boolean validUtf8(int[] arr) {
        int rb = 0;
        for (int ele : arr) {
            if (rb == 0)
                if ((ele >> 7) == 0b0)
                    rb = 0;
                else if ((ele >> 5) == 0b110)
                    rb = 1;
                else if ((ele >> 4) == 0b1110)
                    rb = 2;
                else if ((ele >> 3) == 0b11110)
                    rb = 3;
                else
                    return false;
            else {
                if ((ele >> 6) == 0b10)
                    rb--;
                else
                    return false;
            }
        }
        return rb == 0;
    }

}