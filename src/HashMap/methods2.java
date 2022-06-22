package HashMap;

import java.util.*;

public class methods2 {

    // *https://www.geeksforgeeks.org/count-distinct-elements-in-every-window-of-size-k/
    public ArrayList<Integer> distinctElementsInWindowsOfSizeK(int[] arr, int k) {
        int n = arr.length;
        if (k > n)
            k = n;

        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < k; i++)
            map.put(arr[i], map.getOrDefault(arr[i], 0) + 1);

        ArrayList<Integer> res = new ArrayList<>();
        res.add(map.size());

        for (int i = k; i < n; i++) {
            map.put(arr[i - k], map.get(arr[i - k]) - 1);
            if (map.get(arr[i - k]) == 0)
                map.remove(arr[i - k]);
            map.put(arr[i], map.getOrDefault(arr[i], 0) + 1);
            res.add(map.size());
        }
        return res;
    }

    // *https://www.geeksforgeeks.org/find-the-longest-substring-with-k-unique-characters-in-a-given-string/
    public int longestSubstringWithKDistinctCharacters(String str, int k) {

        if (str.length() == 0 || k == 0)
            return 0;

        HashMap<Character, Integer> map = new HashMap<>();

        int max = Integer.MIN_VALUE;
        int j = 0;
        // ? Acquire
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            map.put(ch, map.getOrDefault(ch, 0) + 1);

            // ? Release
            while (j < i && map.size() > k) {
                ch = str.charAt(j);
                map.put(ch, map.getOrDefault(ch, 0) - 1);
                if (map.get(ch) == 0)
                    map.remove(ch);
                j++;
            }
            if (map.size() == k)
                max = Math.max(max, i - j + 1);
        }

        return max;
    }

    // *https://www.pepcoding.com/resources/data-structures-and-algorithms-in-java-levelup/hashmap-and-heaps/count-of-substrings-with-exactly-k-unique-characters-official/ojquestion
    public int countSubstringWithKDistinctCharacters(String str, int k) {

        if (str.length() == 0 || k == 0)
            return 0;

        if (k == 1)
            return countSubstringWithKDistinctCharactersFor1(str);

        HashMap<Character, Integer> bigMap = new HashMap<>();
        HashMap<Character, Integer> smallMap = new HashMap<>();

        int i = 0;
        int j = 0;
        int count = 0;

        // ? Release
        for (int idx = 0; idx < str.length(); idx++) {
            // ? Acquire Big
            while (i < str.length()) {
                char ch = str.charAt(i);
                if (!bigMap.containsKey(ch) && bigMap.size() == k)
                    break;
                bigMap.put(ch, bigMap.getOrDefault(ch, 0) + 1);
                i++;
            }
            // ? Acquire Small
            while (j < str.length()) {
                char ch = str.charAt(j);
                if (!smallMap.containsKey(ch) && smallMap.size() == k - 1)
                    break;
                smallMap.put(ch, smallMap.getOrDefault(ch, 0) + 1);
                j++;
            }

            count += i - j;

            // ? Release Big and Small
            char ch = str.charAt(idx);
            bigMap.put(ch, bigMap.getOrDefault(ch, 0) - 1);
            smallMap.put(ch, smallMap.getOrDefault(ch, 0) - 1);

            if (bigMap.get(ch) == 0)
                bigMap.remove(ch);
            if (smallMap.get(ch) == 0)
                smallMap.remove(ch);
        }

        return count;
    }

    public int countSubstringWithKDistinctCharactersFor1(String str) {
        HashMap<Character, Integer> bigMap = new HashMap<>();

        int i = 0;
        int count = 0;

        for (int idx = 0; idx < str.length(); idx++) {
            while (i < str.length()) {
                char ch = str.charAt(i);
                if (!bigMap.containsKey(ch) && bigMap.size() == 1)
                    break;
                bigMap.put(ch, bigMap.getOrDefault(ch, 0) + 1);
                i++;
            }

            count += i - idx;

            char ch = str.charAt(idx);
            bigMap.put(ch, bigMap.getOrDefault(ch, 0) - 1);

            if (bigMap.get(ch) == 0)
                bigMap.remove(ch);
        }

        return count;
    }

    // *https://www.pepcoding.com/resources/data-structures-and-algorithms-in-java-levelup/hashmap-and-heaps/equivalent-subarrays-official/ojquestion
    public int countOfEquivalentSubArrays(int[] arr) {

        HashSet<Integer> set = new HashSet<>();
        for (int ele : arr)
            set.add(ele);

        int k = set.size();

        HashMap<Integer, Integer> bigMap = new HashMap<>();
        HashMap<Integer, Integer> smallMap = new HashMap<>();

        int i = 0;
        int j = 0;
        int count = 0;

        // ? Release
        for (int idx = 0; idx < arr.length; idx++) {
            // ? Acquire Big
            while (i < arr.length) {
                int ch = arr[i];
                if (!bigMap.containsKey(ch) && bigMap.size() == k)
                    break;
                bigMap.put(ch, bigMap.getOrDefault(ch, 0) + 1);
                i++;
            }
            // ? Acquire Small
            while (j < arr.length) {
                int ch = arr[j];
                if (!smallMap.containsKey(ch) && smallMap.size() == k - 1)
                    break;
                smallMap.put(ch, smallMap.getOrDefault(ch, 0) + 1);
                j++;
            }

            count += i - j;

            // ? Release Bog and Small
            int ch = arr[idx];
            bigMap.put(ch, bigMap.getOrDefault(ch, 0) - 1);
            smallMap.put(ch, smallMap.getOrDefault(ch, 0) - 1);

            if (bigMap.get(ch) == 0)
                bigMap.remove(ch);
            if (smallMap.get(ch) == 0)
                smallMap.remove(ch);
        }

        return count;

    }

    // *https://www.pepcoding.com/resources/data-structures-and-algorithms-in-java-levelup/hashmap-and-heaps/longest-substring-with-at-most-k-unique-characters-official/ojquestion
    public int longestSubstringWithAtMostKDistinctCharacters(String str, int k) {
        if (str.length() == 0 || k == 0)
            return 0;

        HashMap<Character, Integer> map = new HashMap<>();

        int max = Integer.MIN_VALUE;
        int j = 0;
        // ? Acquire
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            map.put(ch, map.getOrDefault(ch, 0) + 1);

            // ? Release
            while (j < i && map.size() > k) {
                ch = str.charAt(j);
                map.put(ch, map.getOrDefault(ch, 0) - 1);
                if (map.get(ch) == 0)
                    map.remove(ch);
                j++;
            }
            max = Math.max(max, i - j + 1);
        }

        return max;
    }

    // *https://www.pepcoding.com/resources/data-structures-and-algorithms-in-java-levelup/hashmap-and-heaps/count-of-substrings-having-at-most-k-unique-characters-official/ojquestion
    public int countSubstringWithAtMostKDistinctCharacters(String str, int k) {
        if (str.length() == 0 || k == 0)
            return 0;

        HashMap<Character, Integer> map = new HashMap<>();

        int count = 0;
        int j = 0;

        // ? Acquire
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            map.put(ch, map.getOrDefault(ch, 0) + 1);
            count += i - j;

            // ? Release
            while (map.size() > k) {
                ch = str.charAt(j);
                map.put(ch, map.getOrDefault(ch, 0) - 1);
                if (map.get(ch) == 0)
                    map.remove(ch);
                j++;
            }
        }
        count += str.length() - j;

        return count;
    }

    // ? Distinct -> Non Repeating

    // *https://www.pepcoding.com/resources/data-structures-and-algorithms-in-java-levelup/hashmap-and-heaps/longest-substring-with-unique-characters-official/ojquestion
    public int longestSubstringWithDistinctCharacters(String str) {
        if (str.length() == 0)
            return 0;

        HashMap<Character, Integer> map = new HashMap<>();
        int j = 0;
        int res = Integer.MIN_VALUE;

        // ? Acquire
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            map.put(ch, map.getOrDefault(ch, 0) + 1);

            // ? Release
            while (map.get(ch) > 1) {
                char c = str.charAt(j);
                map.put(c, map.getOrDefault(c, 0) - 1);
                if (map.get(c) == 0)
                    map.remove(c);
                j++;
            }

            res = Math.max(res, i - j + 1);
        }
        return res;
    }

    // *https://www.pepcoding.com/resources/data-structures-and-algorithms-in-java-levelup/hashmap-and-heaps/count-of-substrings-having-all-unique-characters-official/ojquestion
    public int countSubstringWithDistinctCharacters(String str) {
        if (str.length() == 0)
            return 0;

        HashMap<Character, Integer> map = new HashMap<>();
        int j = 0;
        int count = 0;
        // ? Acquire
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            map.put(ch, map.getOrDefault(ch, 0) + 1);
            count += i - j;

            // ? Release
            while (map.get(ch) > 1) {
                char c = str.charAt(j);
                map.put(c, map.getOrDefault(c, 0) - 1);
                if (map.get(c) == 0)
                    map.remove(c);
                j++;
            }
        }
        count += str.length() - j;
        return count;
    }

    // *https://www.pepcoding.com/resources/data-structures-and-algorithms-in-java-levelup/hashmap-and-heaps/largest-subarray-with-zero-sum-official/ojquestion
    public int largestSubArrayWithSum0(int[] arr) {

        if (arr.length == 0)
            return 0;

        HashMap<Integer, Integer> map = new HashMap<>();

        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
            map.put(sum, i);
        }

        int res = Integer.MIN_VALUE;
        sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
            res = Math.max(res, map.get(sum) - i);
        }

        return res;
    }

    // *https://www.pepcoding.com/resources/data-structures-and-algorithms-in-java-levelup/hashmap-and-heaps/count-of-all-subarrays-with-zero-sum-official/ojquestion
    public int countSubArrayWithSum0(int[] arr) {
        if (arr.length == 0)
            return 0;

        HashMap<Integer, Integer> map = new HashMap<>();

        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }

        int count = 0;
        for (int ele : map.keySet())
            count += map.get(ele) * (map.get(ele) - 1) / 2;
        return count;
    }

    public int largestSubArrayWithSumK(int[] arr, int k) {
        if (arr.length == 0)
            return 0;

        HashMap<Integer, Integer> map = new HashMap<>();

        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
            map.put(sum, i);
        }

        int res = Integer.MIN_VALUE;
        sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
            if (map.containsKey(sum + k))
                res = Math.max(res, map.get(sum + k) - i);
        }

        return res;
    }

    // * <======|=================|============|==================|=======>
    // * <====> 5 <============> 10 <========> 5 <==============>10 <=====>
    // * | ---------------------- | <== -5 ==> | ------------------------ |

    // *https://www.pepcoding.com/resources/data-structures-and-algorithms-in-java-levelup/hashmap-and-heaps/count-of-subarrays-having-sum-equals-to-k-official/ojquestion
    public int countSubArrayWithSumK(int[] arr, int k) {
        if (arr.length == 0)
            return 0;

        HashMap<Integer, Integer> map = new HashMap<>();

        int sum = 0;
        int count = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
            if (map.containsKey(sum - k))
                count += map.get(sum - k);
            if (sum == k)
                count++;
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }

        return count;
    }

    // *https://www.pepcoding.com/resources/data-structures-and-algorithms-in-java-levelup/hashmap-and-heaps/longest-subarray-with-sum-divisible-by-k-official/ojquestion
    public int longestSubArrayWhoseSumDivisibleByK(int[] arr, int k) {

        if (arr.length == 0)
            return 0;

        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);

        int sum = 0;
        int res = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
            int rem = sum % k;
            if (rem < 0)
                rem += k;
            if (map.containsKey(rem))
                res = Math.max(res, i - map.get(rem));
            map.putIfAbsent(rem, i);
        }

        return res;
    }

    // ? Here -15 and 15 as sum are equivalent as both are divisible by 5;
    // *https://www.pepcoding.com/resources/data-structures-and-algorithms-in-java-levelup/hashmap-and-heaps/count-of-subarrays-with-sum-divisible-by-k-official/ojquestion
    public int countSubArrayWhoseSumDivisibleByK(int[] arr, int k) {
        if (arr.length == 0)
            return 0;

        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);

        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
            int rem = sum % k;
            if (rem < 0)
                rem += k;
            map.put(rem, map.getOrDefault(rem, 0) + 1);
        }

        int count = 0;
        for (int ele : map.keySet())
            count += map.get(ele) * (map.get(ele) - 1) / 2;

        return count;
    }

    // *https://www.pepcoding.com/resources/data-structures-and-algorithms-in-java-levelup/hashmap-and-heaps/longest-subarray-with-equal-number-of-zeroes-and-ones-official/ojquestion
    public int longestSubArrayWithEqual0sAnd1s(int[] arr) {
        if (arr.length == 0)
            return 0;

        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);

        int sum = 0;
        int res = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            sum += (arr[i] == 0) ? -1 : arr[i];
            if (map.containsKey(sum))
                res = Math.max(res, i - map.get(sum));
            map.putIfAbsent(sum, i);
        }

        return res;
    }

    // *https://www.pepcoding.com/resources/data-structures-and-algorithms-in-java-levelup/hashmap-and-heaps/count-of-subarrays-with-equal-number-of-zeroes-and-ones-official/ojquestion
    public int countSubArrayWithEqual0sAnd1s(int[] arr) {
        if (arr.length == 0)
            return 0;

        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);

        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += (arr[i] == 0) ? -1 : arr[i];
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }

        int count = 0;
        for (int ele : map.keySet())
            count += map.get(ele) * (map.get(ele) - 1) / 2;

        return count;
    }

    // *https://www.pepcoding.com/resources/data-structures-and-algorithms-in-java-levelup/hashmap-and-heaps/longest-subarray-with-equal-number-of-0s-1s-and-2s-official/ojquestion
    public int longestSubArrayWithEqual0s1sAnd2s(int[] arr) {
        if (arr.length == 0)
            return 0;

        HashMap<String, Integer> map = new HashMap<>();
        map.put("0#0", -1);

        int res = 0;

        int countOfZeros = 0;
        int countOfOnes = 0;
        int countOfTwos = 0;

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 0)
                countOfZeros++;
            else if (arr[i] == 1)
                countOfOnes++;
            else
                countOfTwos++;

            String key = "" + (countOfOnes - countOfZeros) + "#" + (countOfTwos - countOfOnes);

            if (map.containsKey(key))
                res = Math.max(res, i - map.get(key));
            map.putIfAbsent(key, i);
        }

        return res;
    }

    // *https://www.pepcoding.com/resources/data-structures-and-algorithms-in-java-levelup/hashmap-and-heaps/count-of-subarrays-with-equal-number-of-0s-1s-and-2s-official/ojquestion
    public int countSubArrayWithEqual0s1sAnd2s(int[] arr) {
        if (arr.length == 0)
            return 0;

        HashMap<String, Integer> map = new HashMap<>();
        map.put("0#0", 1);

        int countOfZeros = 0;
        int countOfOnes = 0;
        int countOfTwos = 0;

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 0)
                countOfZeros++;
            else if (arr[i] == 1)
                countOfOnes++;
            else
                countOfTwos++;

            String key = "" + (countOfOnes - countOfZeros) + "#" + (countOfTwos - countOfOnes);

            map.put(key, map.getOrDefault(key, 0) + 1);
        }

        int count = 0;
        for (String ele : map.keySet())
            count += map.get(ele) * (map.get(ele) - 1) / 2;

        return count;
    }

    // *https://www.pepcoding.com/resources/data-structures-and-algorithms-in-java-levelup/hashmap-and-heaps/maximum-consecutive-ones-ii-official/ojquestion
    public int maxConsecutiveOnes(int[] arr, int k) {
        if (arr.length == 0)
            return 0;

        int res = Integer.MIN_VALUE;
        int countOfZeros = 0;

        int j = 0;

        // ? Acquire
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 0)
                countOfZeros++;

            // ? Release
            while (j < arr.length && countOfZeros > k) {
                if (arr[j] == 0)
                    countOfZeros--;
                j++;
            }
            res = Math.max(res, i - j + 1);
        }
        return res;
    }

    // *https://www.pepcoding.com/resources/data-structures-and-algorithms-in-java-levelup/hashmap-and-heaps/largest-subarray-with-contiguous-elements-official/ojquestion
    public int longestSubArrayWithContiguousElements(int[] arr) {

        int res = 0;
        for (int i = 0; i < arr.length; i++) {
            int min = arr[i];
            int max = arr[i];
            HashSet<Integer> set = new HashSet<>(); // ***** for duplicates
            set.add(arr[i]);
            for (int j = i + 1; j < arr.length; j++) {
                if (set.contains(arr[j]))
                    break;
                set.add(arr[j]);
                min = Math.min(min, arr[j]);
                max = Math.max(max, arr[j]);

                if (j - i == max - min) // ? Important Observation
                    res = Math.max(res, j - i + 1);
            }
        }
        return res;
    }

    // *https://www.pepcoding.com/resources/data-structures-and-algorithms-in-java-levelup/hashmap-and-heaps/smallest-subarray-with-all-occurrences-of-the-most-frequent-element-official/ojquestion
    public int[] smallestSubArrayWithHighestFrequency(int[] arr) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int ele : arr)
            map.put(ele, map.getOrDefault(ele, 0) + 1);

        int maxFreq = 0;
        for (int ele : map.keySet())
            maxFreq = Math.max(maxFreq, map.get(ele));

        HashMap<Integer, Integer> smallest = new HashMap<>();
        HashMap<Integer, Integer> largest = new HashMap<>();

        for (int ele : map.keySet())
            if (maxFreq == map.get(ele)) {
                smallest.put(ele, -1);
                largest.put(ele, arr.length);
            }

        for (int i = 0; i < arr.length; i++)
            if (smallest.containsKey(arr[i]) && smallest.get(arr[i]) == -1)
                smallest.put(arr[i], i);
        for (int i = arr.length - 1; i >= 0; i--)
            if (largest.containsKey(arr[i]) && largest.get(arr[i]) == arr.length)
                largest.put(arr[i], i);

        int res = arr.length;
        for (int ele : smallest.keySet())
            res = Math.min(res, largest.get(ele) - smallest.get(ele));

        for (int ele : smallest.keySet())
            if (res == largest.get(ele) - smallest.get(ele))
                return new int[] { ele, smallest.get(ele), largest.get(ele) };
        return new int[] { -1, -1, -1 };
    }

    // *https://www.pepcoding.com/resources/data-structures-and-algorithms-in-java-levelup/hashmap-and-heaps/smallest-substring-of-a-string-containing-all-characters-of-another-string-official/ojquestion
    public String minWindowSubString(String str, String s) {

        HashMap<Character, Integer> small = new HashMap<>();
        for (char ch : s.toCharArray())
            small.put(ch, small.getOrDefault(ch, 0) + 1);

        HashMap<Character, Integer> map = new HashMap<>();
        int j = 0;
        int res = str.length();
        int matchCount = 0;

        int si = -1;
        int ei = str.length();

        // ? Acquire
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            map.put(ch, map.getOrDefault(ch, 0) + 1);
            if (small.containsKey(ch) && map.get(ch) <= small.get(ch))
                matchCount++;

            // ? Release
            while (matchCount == s.length()) {

                if (res >= i - j) {
                    res = i - j;
                    si = j;
                    ei = i;
                }

                ch = str.charAt(j);
                map.put(ch, map.getOrDefault(ch, 0) - 1);
                if (small.containsKey(ch) && map.get(ch) < small.get(ch))
                    matchCount--;
                j++;
            }

        }

        if (si == -1 || ei == str.length())
            return "";
        return str.substring(si, ei + 1);
    }

}
