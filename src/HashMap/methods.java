package HashMap;

import java.util.*;

public class methods {

    // *https://www.geeksforgeeks.org/printing-longest-increasing-consecutive-subsequence/
    public ArrayList<Integer> longestConsecutiveSubsequence(int[] arr) {
        HashMap<Integer, Boolean> map = new HashMap<>();
        for (int ele : arr)
            map.put(ele, true);

        for (int ele : arr)
            if (map.containsKey(ele - 1))
                map.put(ele, false);

        int si = arr.length;
        int size = -1;
        for (int ele : map.keySet()) {
            if (map.get(ele)) {
                int i = ele;
                while (!map.containsKey(i))
                    i++;
                if (size < i - ele + 1) {
                    size = i - ele + 1;
                    si = ele;
                } else if (size == i - ele + 1 && si > ele)
                    si = ele;
            }
        }

        ArrayList<Integer> lcs = new ArrayList<>();
        for (int i = si; i < si + size; i++)
            lcs.add(i);
        return lcs;
    }

    // *https://www.geeksforgeeks.org/count-pairs-in-array-whose-sum-is-divisible-by-k/
    public int pairSumDivisibleByK(int[] arr, int k) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < k; i++)
            map.put(i, 0);

        for (int ele : arr)
            map.put(ele % k, map.get(ele % k) + 1);

        int i = 1;
        int j = k - 1;
        int res = 0;

        res += map.get(0) * (map.get(0) - 1) / 2;
        while (i < j)
            res += map.get(i++) * map.get(j--);

        if (k % 2 == 0)
            res += map.get(k / 2) * (map.get(k / 2) - 1) / 2;

        return res;
    }

    // *https://www.geeksforgeeks.org/check-if-an-array-can-be-divided-into-pairs-whose-sum-is-divisible-by-k/
    public boolean canBeDividedPairSumDivisibleByK(int[] arr, int k) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < k; i++)
            map.put(i, 0);

        for (int ele : arr)
            map.put(ele % k, map.get(ele % k) + 1);

        int i = 1;
        int j = k - 1;

        if (map.get(0) % 2 == 1)
            return false;

        while (i < j)
            if (map.get(i++) != map.get(j--))
                return false;

        if (k % 2 == 0 && map.get(k - 2) % 2 == 1)
            return false;

        return true;
    }

    // * https://leetcode.com/problems/find-all-anagrams-in-a-string/
    public ArrayList<Integer> findAllAnagrams(String str, String s) {

        int n = str.length();
        int k = s.length();
        if (k == 0 || n == 0 || k > n)
            return new ArrayList<>();
        ArrayList<Integer> res = new ArrayList<>();

        HashMap<Character, Integer> sMap = new HashMap<>();
        for (char ch : s.toCharArray()) {
            sMap.putIfAbsent(ch, 0);
            sMap.put(ch, sMap.get(ch) + 1);
        }

        HashMap<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < k; i++) {
            char ch = str.charAt(i);
            map.putIfAbsent(ch, 0);
            map.put(ch, map.get(ch) + 1);
        }

        if (isEqual(sMap, map))
            res.add(0);
        for (int i = k; i < n; i++) {
            map.put(str.charAt(i - k), map.get(str.charAt(i - k)) - 1);
            if (map.get(str.charAt(i - k)) == 0)
                map.remove(str.charAt(i - k));
            map.putIfAbsent(str.charAt(i), 0);
            map.put(str.charAt(i), map.get(str.charAt(i)) + 1);
            if (isEqual(sMap, map))
                res.add(i - k + 1);
        }

        return res;
    }

    public boolean isEqual(HashMap<Character, Integer> map1, HashMap<Character, Integer> map2) {
        if (map1.size() != map2.size())
            return false;

        for (char key : map1.keySet()) {
            if (!map2.containsKey(key))
                return false;
            if (map2.get(key) != map1.get(key))
                return false;
        }
        for (char key : map2.keySet()) {
            if (!map1.containsKey(key))
                return false;
            if (map1.get(key) != map2.get(key))
                return false;
        }

        return true;
    }

    // *https://www.geeksforgeeks.org/check-two-strings-k-anagrams-not/
    public boolean isKAnagram(String str1, String str2, int k) {
        if (str1.length() != str2.length())
            return false;
        HashMap<Character, Integer> map = new HashMap<>();
        for (char ch : str1.toCharArray()) {
            map.putIfAbsent(ch, 0);
            map.put(ch, map.get(ch) + 1);
        }

        for (char ch : str2.toCharArray()) {
            if (!map.containsKey(ch))
                map.put(ch, 1);
            else
                map.put(ch, map.get(ch) - 1);
        }

        for (char ele : map.keySet())
            k -= Math.abs(map.get(ele));

        return k == 0;
    }

    // *https://www.lintcode.com/en/old/problem/find-anagram-mappings/
    public int[] findAnagramMapping(int[] arr1, int[] arr2) {
        HashMap<Integer, ArrayList<Integer>> map = new HashMap<>();
        for (int i = 0; i < arr2.length; i++) {
            map.putIfAbsent(arr2[i], new ArrayList<>());
            map.get(arr2[i]).add(i);
        }

        int[] res = new int[arr1.length];
        for (int i = 0; i < arr1.length; i++)
            res[i] = map.get(arr1[0]).remove(0);
        return res;
    }

    public List<List<String>> groupAnagrams(List<String> list) {
        Map<String, List<String>> map = new HashMap<>();

        for (String str : list) {
            Map<Integer, Integer> m = new HashMap<>();
            for (int i = 0; i < 26; i++)
                m.put(i, 0);
            for (char ch : str.toCharArray())
                m.put(ch - 'a', m.get(ch - 'a') + 1);

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 26; i++)
                sb.append(m.get(i));

            map.putIfAbsent(sb.toString(), new ArrayList<>());
            map.get(sb.toString()).add(str);
        }

        List<List<String>> res = new ArrayList<>();
        for (List<String> l : map.values())
            res.add(l);
        return res;
    }

    // *https://www.geeksforgeeks.org/group-shifted-string/
    public List<List<String>> groupShiftedStrings(List<String> list) {
        Map<String, List<String>> map = new HashMap<>();

        for (String str : list) {
            boolean temp = false;
            for (String s : map.keySet())
                if (isCompatible(str, s)) {
                    temp = true;
                    map.get(str).add(s);
                    break;
                }
            if (!temp) {
                map.put(str, new ArrayList<>());
                map.get(str).add(str);
            }
        }
        List<List<String>> res = new ArrayList<>();
        for (List<String> l : map.values())
            res.add(l);
        return res;
    }

    public boolean isCompatible(String s1, String s2) {
        if (s1.length() != s2.length())
            return false;
        if (s1.length() == 0 || s1.length() == 1)
            return true;

        int diff = s1.charAt(0) - s2.charAt(0);
        for (int i = 1; i < s1.length(); i++)
            if (diff != s1.charAt(i) - s2.charAt(i))
                return false;

        return true;
    }

    // *https://leetcode.com/problems/isomorphic-strings/
    public boolean isIsomorphic(String str1, String str2) {

        if (str1.length() != str2.length())
            return false;

        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < 26; i++)
            map.put(i, -1);

        for (int i = 0; i < str1.length(); i++) {
            int c1 = str1.charAt(i) - 'a';
            int c2 = str2.charAt(i) - 'a';
            if (map.get(c1) == -1)
                map.put(c1, c2);
            else if (map.get(c1) != c2)
                return false;
        }

        for (int i = 0; i < 26; i++)
            map.put(i, -1);

        for (int i = 0; i < str1.length(); i++) {
            int c1 = str1.charAt(i) - 'a';
            int c2 = str2.charAt(i) - 'a';
            if (map.get(c2) == -1)
                map.put(c2, c1);
            else if (map.get(c2) != c1)
                return false;
        }
        return true;
    }

    // * https://leetcode.com/problems/word-pattern/
    public boolean wordPattern(String str1, String str2) {

        String[] arr = str2.split(" ");

        if (arr.length != str1.length())
            return false;
        if (arr.length == 0 && str1.length() == 0)
            return true;
        if (arr.length == 0 || str1.length() == 0)
            return false;

        HashMap<String, String> map = new HashMap<>();

        for (int i = 0; i < str1.length(); i++) {
            String c1 = "" + str1.charAt(i);
            String c2 = arr[i];

            map.putIfAbsent(c1, c2);
            if (!map.get(c1).equals(c2))
                return false;
        }

        map.clear();

        for (int i = 0; i < str1.length(); i++) {
            String c1 = "" + str1.charAt(i);
            String c2 = arr[i];

            map.putIfAbsent(c2, c1);
            if (!map.get(c2).equals(c1))
                return false;
        }
        return true;
    }

    // *https://leetcode.com/problems/fraction-to-recurring-decimal/submissions/
    public String recurringFraction(int numerator, int denominator) {

        long nr = numerator;
        long dr = denominator;

        boolean sign = ((nr < 0 && dr > 0) || (nr > 0 && dr < 0)) ? true : false;

        nr = Math.abs(nr);
        dr = Math.abs(dr);

        long q = nr / dr;
        long r = nr % dr;

        String ans = ((sign) ? "-" : "") + q;

        if (r == 0)
            return ans;

        ans += ".";

        HashMap<Long, Integer> map = new HashMap<>();

        int pos = 0;
        String remainingAns = "";
        while (r != 0) {
            if (map.containsKey(r)) {
                remainingAns = remainingAns.substring(0, map.get(r)) + "(" + remainingAns.substring(map.get(r)) + ")";
                break;
            }
            map.put(r, pos++);

            r *= 10;
            q = r / dr;
            r = r % dr;
            remainingAns += q;
        }
        return ans + remainingAns;
    }

    // *https://leetcode.com/problems/rabbits-in-forest/
    public int rabbitsInAForest(int[] arr) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int ele : arr) {
            map.putIfAbsent(ele, 0);
            map.put(ele, map.get(ele) + 1);
        }

        int ans = 0;
        for (int ele : map.keySet())
            ans += map.get(ele) + ((map.get(ele) % (ele + 1) == 0) ? 0 : (ele + 1 - (map.get(ele) % (ele + 1))));

        return ans;
    }

    // *https://leetcode.com/problems/array-of-doubled-pairs/
    public boolean doublePairArray(int[] arr) {

        ArrayList<Integer> negative = new ArrayList<>();
        ArrayList<Integer> zero = new ArrayList<>();
        ArrayList<Integer> positive = new ArrayList<>();

        HashMap<Integer, Integer> map = new HashMap<>();

        for (int ele : arr) {
            if (ele > 0)
                positive.add(ele);
            else if (ele < 0)
                negative.add(ele);
            else
                zero.add(ele);
            map.putIfAbsent(ele, 0);
            map.put(ele, map.get(ele) + 1);
        }

        Collections.sort(positive);
        Collections.sort(negative);
        Collections.reverse(negative);

        if (zero.size() % 2 != 0)
            return false;

        for (int i = 0; i < positive.size(); i++) {
            int key = positive.get(i);
            if (map.get(key) == 0)
                continue;

            if (!map.containsKey(2 * key) || map.get(2 * key) == 0)
                return false;

            map.put(key, map.get(key) - 1);
            map.put(2 * key, map.get(2 * key) - 1);
        }

        for (int i = 0; i < negative.size(); i++) {
            int key = negative.get(i);
            if (map.get(key) == 0)
                continue;

            if (!map.containsKey(2 * key) || map.get(2 * key) == 0)
                return false;

            map.put(key, map.get(key) - 1);
            map.put(2 * key, map.get(2 * key) - 1);
        }

        return true;
    }

    public boolean isArithmeticSequence(int[] arr) {
        if (arr.length == 0 || arr.length == 1)
            return true;

        int n = arr.length;

        HashSet<Integer> set = new HashSet<>();
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int ele : arr) {
            min = Math.min(min, ele);
            max = Math.max(max, ele);
            set.add(ele);
        }

        int commonDiff = (int) ((max - min) / (n - 1));
        for (int i = 0; i < n; i++)
            if (!set.contains(min + (i * commonDiff)))
                return false;

        return true;
    }

    // *https://www.geeksforgeeks.org/count-pairs-two-sorted-matrices-given-sum/
    public int countPairsFromSortedMatrices(int[][] arr1, int[][] arr2, int target) {
        int m = arr1.length;
        int n = arr1[0].length;

        int count = 0;

        int i = 0;
        int j = n * m - 1;
        while (i < j) {

            int r1 = i / n;
            int c1 = i % n;

            int r2 = j / n;
            int c2 = j % n;

            if (arr1[r1][c1] + arr2[r2][c2] < target) {
                i++;
            } else if (arr1[r1][c1] + arr2[r2][c2] < target) {
                j--;
            } else {
                count++;
                i++;
                j--;
            }

        }
        return count;
    }

    public List<List<Integer>> quadruples1(int[] arr, int target) {
        List<List<Integer>> res = new ArrayList<>();

        Arrays.sort(arr);
        for (int i = 0; i < arr.length; i++) {
            if (i != 0 && arr[i] == arr[i - 1])
                continue;
            for (int j = i + 1; j < arr.length; j++) {
                if (j != 1 && arr[j] == arr[j - 1])
                    continue;
                int si = j + 1;
                int ei = arr.length - 1;

                while (si < ei) {
                    if (arr[si] + arr[ei] < target - arr[i] - arr[j])
                        si++;
                    else if (arr[si] + arr[ei] > target - arr[i] - arr[j])
                        ei--;
                    else {
                        res.add(new ArrayList<>(Arrays.asList(arr[si++], arr[ei--], arr[i], arr[j])));
                        while (si < ei && arr[si] == arr[si - 1])
                            si++;
                        while (si < ei && arr[ei] == arr[ei + 1])
                            ei--;
                    }
                }
            }
        }

        return res;
    }

    public List<List<Integer>> quadruples1UsingHashSet(int[] arr, int target) {
        List<List<Integer>> res = new ArrayList<>();

        Arrays.sort(arr);
        HashSet<Integer> fSet = new HashSet<>();
        for (int i = 0; i < arr.length; i++) {
            if (fSet.contains(arr[i]))
                continue;
            HashSet<Integer> sSet = new HashSet<>();
            for (int j = i + 1; j < arr.length; j++) {
                if (sSet.contains(arr[j]))
                    continue;
                int si = j + 1;
                int ei = arr.length - 1;

                HashSet<Integer> tSet = new HashSet<>();
                while (si < ei) {
                    if (arr[si] + arr[ei] < target - arr[i] - arr[j])
                        si++;
                    else if (arr[si] + arr[ei] > target - arr[i] - arr[j])
                        ei--;
                    else {
                        if (!tSet.contains(arr[si]) && !tSet.contains(arr[ei])) {
                            res.add(new ArrayList<>(Arrays.asList(arr[si], arr[ei], arr[i], arr[j])));
                            tSet.add(arr[si]);
                            tSet.add(arr[ei]);
                        }

                        si++;
                        ei--;
                    }
                }
                sSet.add(arr[j]);
            }
            fSet.add(arr[i]);
        }

        return res;
    }

    public int quadruples2(int[] a, int[] b, int[] c, int[] d, int target) {

        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < a.length; i++)
            for (int j = 0; j < b.length; j++)
                map.put(a[i] + b[j], map.getOrDefault(a[i] + b[j], 0) + 1);

        int count = 0;
        for (int i = 0; i < c.length; i++)
            for (int j = 0; j < d.length; j++)
                count += map.getOrDefault(target - (a[i] + b[j]), 0);

        return count;
    }

    public ArrayList<int[]> getQuadruples2(int[] a, int[] b, int[] c, int[] d, int target) {
        ArrayList<int[]> res = new ArrayList<>();

        Map<Integer, int[]> map = new HashMap<>();
        for (int i = 0; i < a.length; i++)
            for (int j = 0; j < b.length; j++)
                map.put(a[i] + b[j], new int[] { i, j });

        for (int i = 0; i < c.length; i++)
            for (int j = 0; j < d.length; j++)
                if (map.containsKey(target - c[i] - d[j])) {
                    int[] idx = map.get(target - c[i] - d[j]);
                    res.add(new int[] { a[i], b[j], c[idx[0]], d[idx[1]] });
                }

        return res;
    }

    // **https://leetcode.com/problems/powerful-integers/submissions/
    public List<Integer> powerfulIntegers(int x, int y, int bound) {

        ArrayList<Integer> a = new ArrayList<>();
        if (x == 1)
            a.add(1);
        else {
            for (int i = 1; i < bound; i *= x)
                a.add(i);
        }

        ArrayList<Integer> b = new ArrayList<>();
        if (y == 1)
            b.add(1);
        else {
            for (int i = 1; i < bound; i *= y)
                b.add(i);
        }

        HashSet<Integer> res = new HashSet<>();
        for (int i : a)
            for (int j : b)
                if (i + j <= bound)
                    res.add(i + j);

        List<Integer> ans = new ArrayList<>();
        ans.addAll(res);

        return ans;
    }

    // *https://leetcode.com/problems/subdomain-visit-count/submissions/
    public List<String> subDomainVisitCount(String[] domains) {

        int n = domains.length;
        String[][] arr = new String[n][];

        int i = 0;
        for (String str : domains)
            arr[i++] = str.split(" ");

        int[] count = new int[n];
        i = 0;
        for (String[] strArr : arr)
            count[i++] = Integer.parseInt(strArr[0]);

        HashMap<String, Integer> map = new HashMap<>();
        i = 0;
        for (String[] strArr : arr) {
            String[] sArr = strArr[1].replace(".", "@").split("@");

            StringBuilder sb = new StringBuilder(sArr[sArr.length - 1]);
            for (int j = sArr.length - 2; j >= 0; j--) {
                map.put(sb.toString(), map.getOrDefault(sb.toString(), 0) + count[i]);
                sb = new StringBuilder(sArr[j]).append(".").append(sb.toString());
            }
            map.put(sb.toString(), map.getOrDefault(sb.toString(), 0) + count[i]);
            i++;
        }

        List<String> res = new ArrayList<>();
        for (String domain : map.keySet())
            res.add(new StringBuilder("" + map.get(domain)).append(" ").append(domain).toString());
        return res;
    }

    // *https://leetcode.com/problems/x-of-a-kind-in-a-deck-of-cards/submissions/
    public boolean divisionIntoGroups(int[] arr) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int ele : arr)
            map.put(ele, map.getOrDefault(ele, 0) + 1);

        int ans = -1;
        for (int ele : map.keySet())
            if (ans == -1)
                ans = map.get(ele);
            else
                ans = gcd(ans, map.get(ele));
        return ans > 2;
    }

    public int gcd(int a, int b) {
        return (b == 0) ? a : gcd(b, a % b);
    }

    // *https://leetcode.com/problems/brick-wall/submissions/
    public int brickWalls(List<List<Integer>> list) {

        HashMap<Integer, Integer> map = new HashMap<>();
        int count = 0;
        for (int i = 0; i < list.size(); i++) {
            count = 0;
            for (int col : list.get(i)) {
                count += col;
                map.put(count, map.getOrDefault(count, 0) + 1);
            }
        }

        map.remove(count);

        int res = 0;
        for (int ele : map.keySet())
            res = Math.max(res, map.get(ele));
        return list.size() - res;

    }

    // *https://leetcode.com/problems/maximum-frequency-stack/submissions/
    class FreqStack {

        HashMap<Integer, Integer> map;
        HashMap<Integer, Stack<Integer>> stacksMap;
        int maxFreq;

        public FreqStack() {
            map = new HashMap<>();
            stacksMap = new HashMap<>();
            maxFreq = 0;
        }

        public void push(int val) {
            map.put(val, map.getOrDefault(val, 0) + 1);
            stacksMap.putIfAbsent(map.get(val), new Stack<>());
            stacksMap.get(map.get(val)).push(val);
            maxFreq = Math.max(maxFreq, map.get(val));
        }

        public int pop() {
            int re = stacksMap.get(maxFreq).pop();
            map.put(re, map.getOrDefault(re, 0) - 1);

            if (stacksMap.get(maxFreq).isEmpty())
                stacksMap.remove(maxFreq--);
            if (map.get(re) == 0)
                map.remove(re);

            return re;
        }
    }

    // *https://leetcode.com/problems/encode-and-decode-tinyurl/submissions/
    public class TinyURL {

        ArrayList<Character> list;
        HashMap<String, String> map;

        public TinyURL() {
            list = new ArrayList<>();
            for (int i = 0; i < 10; i++)
                list.add((char) (i + '0'));

            for (char ch = 'a'; ch <= 'z'; ch++)
                list.add(ch);

            for (char ch = 'A'; ch <= 'Z'; ch++)
                list.add(ch);
            map = new HashMap<>();
        }

        public String encode(String longUrl) {
            StringBuilder sb = new StringBuilder(getRandom());
            while (!map.isEmpty() && !map.containsKey(sb.toString()))
                sb.append(getRandom());
            map.put(sb.toString(), longUrl);
            return sb.toString();
        }

        public String decode(String shortUrl) {
            return map.get(shortUrl);
        }

        public char getRandom() {
            int max = 61;
            int min = 0;
            int range = max - min + 1;

            int idx = (int) (Math.random() * range) + min;

            return list.get(idx);
        }
    }

    // *https://leetcode.com/problems/insert-delete-getrandom-o1/
    class RandomizedSet {

        HashMap<Integer, Integer> map;
        ArrayList<Integer> list;

        public RandomizedSet() {
            map = new HashMap<>();
            list = new ArrayList<>();
        }

        public boolean insert(int val) {
            if (map.containsKey(val))
                return false;
            list.add(val);
            map.put(val, list.size() - 1);
            return true;
        }

        public boolean remove(int val) {
            if (!map.containsKey(val))
                return false;

            int idx = map.get(val);
            swap(idx, list.size() - 1);
            map.put(list.get(idx), idx);

            list.remove(list.size() - 1);
            map.remove(val);

            return true;
        }

        public void swap(int i, int j) {
            int temp = list.get(i);
            list.set(i, list.get(j));
            list.set(j, temp);
        }

        public int getRandom() {
            int max = list.size() - 1;
            int min = 0;
            int range = max - min + 1;

            int idx = (int) (Math.random() * range) + min;

            return list.get(idx);
        }
    }

    // *https://leetcode.com/problems/insert-delete-getrandom-o1-duplicates-allowed/
    class RandomizedCollection {

        HashMap<Integer, ArrayList<Integer>> map;
        ArrayList<Integer> list;

        public RandomizedCollection() {
            map = new HashMap<>();
            list = new ArrayList<>();
        }

        public boolean insert(int val) {
            if (map.containsKey(val) && map.get(val).size() != 0) {
                list.add(val);
                map.get(val).add(list.size() - 1);
                return false;
            }
            list.add(val);
            map.put(val, new ArrayList<>());
            map.get(val).add(list.size() - 1);
            return true;
        }

        public boolean remove(int val) {
            if (!map.containsKey(val) || map.get(val).size() == 0)
                return false;

            List<Integer> l = map.get(val);
            int idx = l.get(l.size() - 1);

            swap(idx, list.size() - 1);

            map.get(list.get(idx)).remove((Object) (list.size() - 1));
            map.get(list.get(idx)).add(idx);

            map.get(val).remove((Object) idx);
            list.remove(list.size() - 1);

            return true;
        }

        public void swap(int i, int j) {
            int temp = list.get(i);
            list.set(i, list.get(j));
            list.set(j, temp);
        }

        public int getRandom() {
            int max = list.size() - 1;
            int min = 0;
            int range = max - min + 1;

            int idx = (int) (Math.random() * range) + min;

            return list.get(idx);
        }
    }

    // *https://leetcode.com/problems/relative-sort-array/submissions/
    public int[] relativeSortArray(int[] x, int[] y) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < y.length; i++)
            map.put(y[i], i);

        PriorityQueue<Integer> pq = new PriorityQueue<>(
                (a, b) -> map.get(a) - map.get(b));

        for (int ele : x)
            if (map.containsKey(ele))
                pq.add(ele);

        int i = 0;
        int[] res = new int[x.length];
        for (i = 0; i < x.length && !pq.isEmpty(); i++)
            res[i] = pq.remove();

        pq = new PriorityQueue<>();
        for (int ele : x)
            if (!map.containsKey(ele))
                pq.add(ele);
        for (; i < x.length && !pq.isEmpty(); i++)
            res[i] = pq.remove();
        return res;
    }

    // *https://www.geeksforgeeks.org/largest-subset-whose-all-elements-are-fibonacci-numbers/
    public int largestFibonacciSubsequence(int[] arr) {
        int max = Integer.MIN_VALUE;
        for (int ele : arr)
            max = Math.max(max, ele);

        HashSet<Integer> set = new HashSet<>();

        set.add(0);

        int i = 0;
        int j = 0;
        while (i + j < max) {
            int sum = i + j;
            i = j;
            j = sum;
            set.add(sum);
        }

        int count = 0;
        for (int ele : arr)
            if (set.contains(ele))
                count++;
        return count;
    }

}
