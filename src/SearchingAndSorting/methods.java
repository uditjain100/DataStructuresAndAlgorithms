package SearchingAndSorting;

import java.util.*;

public class methods {

    public List<Integer> unionOfTwoSortedArray(int[] a, int[] b) {
        int i = 0;
        int j = 0;

        List<Integer> res = new ArrayList<>();

        while (i < a.length && j < b.length) {
            if (a[i] == b[j]) {
                res.add(a[i]);
                i++;
                j++;
            } else if (a[i] < b[j]) {
                res.add(a[i]);
                i++;
            } else {
                res.add(b[j]);
                j++;
            }
        }

        while (i < a.length)
            res.add(a[i++]);
        while (j < b.length)
            res.add(b[j++]);

        return res;
    }

    public List<Integer> unionOfTwoArray(int[] a, int[] b) {

        Arrays.sort(a);
        Arrays.sort(b);

        int i = 0;
        int j = 0;

        List<Integer> res = new ArrayList<>();

        while (i < a.length && j < b.length) {
            if (a[i] == b[j]) {
                res.add(a[i]);
                i++;
                j++;
            } else if (a[i] < b[j]) {
                res.add(a[i]);
                i++;
            } else {
                res.add(b[j]);
                j++;
            }
        }

        while (i < a.length)
            res.add(a[i++]);
        while (j < b.length)
            res.add(b[j++]);

        return res;
    }

    public List<Integer> intersectionOfTwoSortedArray(int[] a, int[] b) {
        int i = 0;
        int j = 0;

        List<Integer> res = new ArrayList<>();

        while (i < a.length && j < b.length) {
            if (a[i] == b[j]) {
                res.add(a[i]);
                i++;
                j++;
            } else if (a[i] < b[j])
                i++;
            else
                j++;
        }

        return res;
    }

    // *https://leetcode.com/problems/intersection-of-two-arrays/
    public List<Integer> intersectionOfTwoArray1(int[] a, int[] b) {

        Arrays.sort(a);
        Arrays.sort(b);

        int i = 0;
        int j = 0;

        List<Integer> res = new ArrayList<>();

        while (i < a.length && j < b.length) {
            if (a[i] == b[j]) {
                if (res.size() == 0 || res.get(res.size() - 1) != a[i])
                    res.add(a[i]);
                i++;
                j++;
            } else if (a[i] < b[j])
                i++;
            else
                j++;
        }

        return res;
    }

    // *https://leetcode.com/problems/intersection-of-two-arrays-ii/
    public List<Integer> intersectionOfTwoArray2(int[] a, int[] b) {

        Arrays.sort(a);
        Arrays.sort(b);

        int i = 0;
        int j = 0;

        List<Integer> res = new ArrayList<>();

        while (i < a.length && j < b.length) {
            if (a[i] == b[j]) {
                res.add(a[i]);
                i++;
                j++;
            } else if (a[i] < b[j])
                i++;
            else
                j++;
        }

        return res;
    }

    // *https://www.geeksforgeeks.org/chocolate-distribution-problem/
    public int chocolateDistributionProblem(int[] arr, int k) {
        Arrays.sort(arr);

        int minDiff = Integer.MAX_VALUE;
        for (int i = 0; i < arr.length - k + 1; i++)
            minDiff = Math.min(minDiff, arr[i + k] - arr[i]);
        return minDiff;
    }

    public ArrayList<ArrayList<Integer>> twoSum(int[] arr, int si, int ei, int sum) {
        Arrays.sort(arr);

        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        int i = si;
        int j = ei;

        while (i < j) {
            if (sum < arr[i] + arr[j])
                i++;
            else if (sum > arr[i] + arr[j])
                j--;
            else {
                res.add(new ArrayList<>(Arrays.asList(arr[i], arr[j])));
                i++;
                j--;

                while (i > j && arr[i] == arr[i - 1])
                    i++;
                while (i > j && arr[j] == arr[j + 1])
                    j--;
            }
        }
        return res;
    }

    public ArrayList<ArrayList<Integer>> threeSum(int[] arr, int si, int ei, int sum) {
        Arrays.sort(arr);

        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        int i = si;
        while (i <= ei) {
            while (i != 0 && arr[i] == arr[i - 1] && i <= ei)
                i++;
            ArrayList<ArrayList<Integer>> ans = twoSum(arr, i + 1, arr.length - 1, sum - arr[i]);
            for (ArrayList<Integer> list : ans)
                list.add(0, arr[i]);
            res.addAll(ans);
            i++;
        }
        return res;
    }

    // *https://leetcode.com/problems/3sum-closest/
    public int threeSumClosest(int[] arr, int sum) {
        Arrays.sort(arr);

        int n = arr.length;
        int res = -100000;

        for (int i = 0; i < n; i++) {
            int si = i + 1;
            int ei = n - 1;

            while (si < ei) {
                int s = arr[si] + arr[ei];

                if (Math.abs(res - sum) > Math.abs((s + arr[i]) - sum))
                    res = s + arr[i];

                if (s > (sum - arr[i]))
                    ei--;
                else
                    si++;
            }
        }

        return res;
    }

    // *https://practice.geeksforgeeks.org/problems/count-triplets-with-sum-smaller-than-x5549/1#
    public long countTripletsWithSumSmallerThanTarget(long[] arr, int n, int t) {

        Arrays.sort(arr);

        long sum = t;

        long count = 0;

        for (int i = 0; i < n; i++) {
            int si = i + 1;
            int ei = n - 1;

            while (si < ei) {
                long s = arr[si] + arr[ei];

                if (s > (sum - arr[i]))
                    ei--;
                else if (s < (sum - arr[i])) {
                    count += ei - si;
                    si++;
                } else {
                    si++;
                    ei--;
                }
            }
        }

        return count;
    }

    // *https://leetcode.com/problems/valid-triangle-number/
    public int countPossibleTriangles(int[] arr) {
        if (arr.length < 3)
            return 0;

        Arrays.sort(arr);

        int count = 0;
        for (int i = arr.length - 1; i >= 2; i--) {

            int si = 0;
            int ei = i - 1;

            while (si < ei) {
                if (arr[si] + arr[ei] <= arr[i])
                    si++;
                else {
                    count += ei - si;
                    ei--;
                }
            }
        }

        return count;
    }

    public ArrayList<ArrayList<Integer>> fourSum(int[] arr, int sum) {
        Arrays.sort(arr);

        int n = arr.length;
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        int i = 0;
        while (i < n) {
            while (i != 0 && arr[i] == arr[i - 1] && i < n)
                i++;
            ArrayList<ArrayList<Integer>> ans = threeSum(arr, i + 1, arr.length - 1, sum - arr[i]);
            for (ArrayList<Integer> list : ans)
                list.add(0, arr[i]);
            res.addAll(ans);
            i++;
        }
        return res;
    }

    // *https://www.geeksforgeeks.org/find-a-pair-with-the-given-difference/
    public ArrayList<ArrayList<Integer>> pairWithGiverDifference(int[] arr, int diff) {

        Arrays.sort(arr);

        int i = 0;
        int j = 1;

        ArrayList<ArrayList<Integer>> res = new ArrayList<>();

        while (i < arr.length && j < arr.length) {
            if (arr[j] - arr[i] < diff)
                i++;
            else if (arr[j] - arr[i] < diff)
                j++;
            else {
                res.add(new ArrayList<>(Arrays.asList(arr[i], arr[j])));
                i++;
                j++;

                while (i + 1 < arr.length && arr[i] == arr[i + 1])
                    i++;
                while (j + 1 < arr.length && arr[j] == arr[j + 1])
                    j++;
            }
        }
        return res;
    }

    // **** Moore's Vooting Algorithm
    // **** Element which is present more than (arr.length + 1) / 2 times
    // *https://leetcode.com/problems/majority-element/
    public int majorityElement1(int[] arr) {
        int res = arr[0];
        int count = 1;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] == arr[i - 1])
                count++;
            else
                count--;

            if (count == 0) {
                res = i;
                count = 1;
            }
        }

        count = 0;
        for (int i = 1; i < arr.length; i++)
            if (arr[i] == arr[res])
                count++;

        if (count > (arr.length + 1) / 2)
            return arr[res];
        return -1;
    }

    // *https://leetcode.com/problems/majority-element-ii/
    public List<Integer> majorityElement2(int[] arr) {

        int res1 = arr[0];
        int res2 = arr[0];
        int count1 = 1;
        int count2 = 0;

        for (int i = 1; i < arr.length; i++) {
            if (arr[i] == res1)
                count1++;
            else if (arr[i] == res2)
                count2++;
            else {
                if (count1 == 0) {
                    res1 = arr[i];
                    count1++;
                } else if (count2 == 0) {
                    res2 = arr[i];
                    count2++;
                } else {
                    count1--;
                    count2--;
                }
            }
        }

        count1 = count2 = 0;
        for (int ele : arr)
            if (res1 == ele)
                count1++;
            else if (res2 == ele)
                count2++;

        List<Integer> res = new ArrayList<>();
        if (count1 > arr.length / 3)
            res.add(res1);
        if (count2 > arr.length / 3)
            res.add(res2);
        return res;
    }

    public ArrayList<Integer> majorityElementGeneral(int[] arr, int k) {
        int n = arr.length;
        HashMap<Integer, Integer> map = new HashMap<>();

        for (int ele : arr)
            map.put(ele, map.getOrDefault(ele, 0) + 1);

        ArrayList<Integer> list = new ArrayList<>();
        for (int key : map.keySet())
            if (map.get(key) > (n / k))
                list.add(key);

        Collections.sort(list);
        return list;
    }

    // *https://leetcode.com/problems/missing-number/
    public int missingElement(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            int idx = arr[i] % (n + 1);
            arr[idx - 1] += (n + 1);
        }
        for (int i = 0; i < n; i++) {
            if (arr[i] < n + 1)
                return i + 1;
        }
        return -1;
    }

    // *https://www.geeksforgeeks.org/find-the-element-that-appears-once-in-a-sorted-array/
    public int findUniqueElement(int[] arr) {

        int n = arr.length;
        if (n == 0 || n % 2 == 0)
            return 0;
        if (n == 1)
            return arr[0];
        if (arr[0] != arr[1])
            return arr[0];
        if (arr[n - 1] != arr[n - 2])
            return arr[n - 1];

        int si = 1;
        int ei = n + 2;

        while (si <= ei) {
            int mid = si + (ei - si) / 2;

            if ((mid == 0 || arr[mid] != arr[mid - 1]) && (mid == n - 1 || arr[mid] != arr[mid + 1]))
                return arr[mid];
            else if (mid > 0 && arr[mid] == arr[mid - 1]) {
                if ((mid - si + 1 % 2) != 0)
                    ei = mid - 1;
                else
                    si = mid + 1;
            } else {
                if ((ei - mid + 1 % 2) != 0)
                    si = mid + 1;
                else
                    ei = mid - 1;
            }
        }
        return -1;
    }

    // *https://www.geeksforgeeks.org/equilibrium-index-of-an-array/
    public int equilibriumIndex(int[] arr) {

        int totalSum = 0;
        for (int ele : arr)
            totalSum += ele;
        int prefixSum = 0;
        for (int i = 0; i < arr.length; i++) {
            if (prefixSum == totalSum - arr[i] - prefixSum)
                return i;
            prefixSum += arr[i];
        }
        return -1;
    }

    // *https://leetcode.com/problems/find-peak-element/
    public int peakElement(int[] arr) {

        int si = 0;
        int ei = arr.length - 1;

        while (si < ei) {
            int mid = (si + ei) / 2;
            if ((mid == 0 || arr[mid] >= arr[mid - 1]) && (mid == arr.length - 1 || arr[mid] >= arr[mid + 1]))
                return mid;
            else if (mid > 0 && arr[mid] < arr[mid - 1])
                ei = mid - 1;
            else
                si = mid + 1;
        }
        return si;
    }

    // ? Works Only for array containing elements without duplicates
    // *https://www.youtube.com/watch?v=vF7gk4iaklA&list=PL-Jc9J83PIiFc7hJ5eeCb579PS8p-en4f&index=15
    public int pivotInSortedRotatedArray(int[] arr) {
        int si = 0;
        int ei = arr.length - 1;

        while (si < ei) {
            int mid = (si + ei) / 2;

            if (arr[mid] <= arr[ei])
                ei = mid;
            else
                si = mid + 1;
        }
        return si;
    }

    // *https://www.geeksforgeeks.org/find-rotation-count-rotated-sorted-array/
    public int findRotationCount(int[] arr) {
        return pivotInSortedRotatedArray(arr);
    }

    public int binarySearch(int[] arr, int si, int ei, int ele) {
        if (si > ei)
            return -1;

        int mid = (si + ei) / 2;
        if (ele < arr[mid])
            return binarySearch(arr, si, mid - 1, ele);
        else if (ele > arr[mid])
            return binarySearch(arr, mid + 1, ei, ele);
        else
            return mid;
    }

    // *https://leetcode.com/problems/search-in-rotated-sorted-array/
    public int searchInSortedAndRotatedArray(int[] arr, int ele) {
        int pivotIdx = pivotInSortedRotatedArray(arr);

        if (arr[pivotIdx] == ele)
            return pivotIdx;

        int res = binarySearch(arr, 0, pivotIdx, ele);
        if (res != -1)
            return res;
        return binarySearch(arr, pivotIdx, arr.length - 1, ele);
    }

    // *https://leetcode.com/problems/search-in-rotated-sorted-array-ii/
    public boolean searchInSortedAndRotatedArray2(int[] arr, int target) {

        int si = 0;
        int ei = arr.length - 1;
        while (si <= ei) {
            int mid = (si + ei) / 2;
            if (arr[mid] == target)
                return true;
            else if ((arr[mid] == arr[si]) && (arr[mid] == arr[ei])) { // ? Equality on both sides
                si++;
                ei--;
            } else if (arr[mid] >= arr[si]) { // ? sorted on left side
                if (target >= arr[si] && target < arr[mid])
                    ei = mid - 1;
                else
                    si = mid + 1;
            } else { // ? sorted on right side
                if (target <= arr[ei] && target > arr[mid])
                    si = mid + 1;
                else
                    ei = mid - 1;
            }
        }
        return false;
    }

    // *https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/
    public int minInSortedRotatedArray1(int[] arr) {
        int pivot = pivotInSortedRotatedArray(arr);
        return arr[pivot];
    }

    // *https://leetcode.com/problems/find-minimum-in-rotated-sorted-array-ii/
    public int minInSortedRotatedArray2(int[] arr) {
        int si = 0;
        int ei = arr.length - 1;

        int res = Integer.MAX_VALUE;

        while (si <= ei) {
            int mid = (si + ei) / 2;

            if (arr[mid] == arr[ei])
                ei--;
            else if (arr[mid] < arr[ei])
                ei = mid - 1;
            else
                si = mid + 1;

            res = Math.min(arr[mid], res);
        }

        return res;
    }

    // *https://leetcode.com/problems/search-a-2d-matrix/
    public boolean binarySearchIn2DMatrix1(int[][] arr, int ele) {

        int n = arr.length;
        int m = arr[0].length;

        int si = 0;
        int ei = n * m;

        while (si <= ei) {
            int mid = (si + ei) / 2;

            int mr = mid / m;
            int mc = mid % m;

            if (ele < arr[mr][mc])
                ei = mid - 1;
            else if (ele > arr[mr][mc])
                si = mid + 1;
            else
                return true;
        }

        return false;
    }

    // *https://leetcode.com/problems/search-a-2d-matrix-ii/
    public boolean binarySearchIn2DMatrix2(int[][] arr, int ele) {

        int n = arr.length;
        int m = arr[0].length;

        int sr = 0;
        int sc = m - 1;

        while (sr != n && sc != -1) {
            if (arr[sr][sc] > ele)
                sc--;
            else if (arr[sr][sc] < ele)
                sr++;
            else
                return true;
        }

        return false;
    }

    // *https://www.geeksforgeeks.org/count-zeros-in-a-row-wise-and-column-wise-sorted-matrix/
    public int count0sInMatrix(int[][] arr) {

        int n = arr.length;
        int m = arr[0].length;

        int sr = 0;
        int sc = m - 1;

        int count = 0;
        while (sr != n && sc != -1) {
            if (arr[sr][sc] == 1)
                sc--;
            else {
                sr++;
                count += sc + 1;
            }
        }

        return count;
    }

    // ? we can also solve this using Heap
    public List<Integer> kClosestElements(int[] arr, int k, int target) {
        int index = Arrays.binarySearch(arr, target);

        if (index < 0) {
            index++;
            index *= -1;
        }

        int i = index;
        int j = index - 1;

        List<Integer> res = new ArrayList<>();

        while (i < arr.length && j >= 0 && k-- > 0) {
            if (arr[i] - target < target - arr[j])
                res.add(arr[i++]);
            else
                res.add(arr[j--]);
        }

        while (i < arr.length && k-- > 0)
            res.add(arr[i++]);
        while (j >= 0 && k-- > 0)
            res.add(arr[j--]);

        Collections.sort(res);
        return res;
    }

    public int maxSumInConfiguration1(int[] arr) {
        Arrays.sort(arr);
        int sum = 0;

        int i = 0;
        for (int ele : arr) {
            sum += ele * i;
            i++;
        }

        return sum;
    }

    // *https://practice.geeksforgeeks.org/problems/max-sum-in-the-configuration/1#
    public int maxSumInConfiguration2(int[] arr) {
        int n = arr.length;

        int totalSum = 0;
        int referenceSum = 0;
        int maxSum = 0;

        for (int i = 0; i < n; i++) {
            totalSum += arr[i];
            referenceSum += arr[i] * i;
        }

        maxSum = referenceSum;

        for (int i = 1; i < n; i++) {
            referenceSum += (totalSum - (5 * arr[n - i - 1]));
            maxSum = Math.max(maxSum, referenceSum);
        }

        return maxSum;
    }

    // *https://leetcode.com/problems/koko-eating-bananas/
    // ***** LeetCode 875 , LeetCode 1283
    public int kokoEatingBanana(int[] arr, int h) {

        int max = Integer.MIN_VALUE;
        for (int ele : arr)
            max = Math.max(max, ele);

        if (h == arr.length)
            return max;

        int si = 1;
        int ei = max;

        int res = max;

        while (si <= ei) {
            int mid = (si + ei) / 2;

            int hoursTaken = calculateHoursTaken(arr, mid);

            if (hoursTaken > h)
                si = mid + 1;
            else
                ei = mid - 1;

            if (hoursTaken <= h)
                res = Math.min(res, mid);
        }

        return res;
    }

    public int calculateHoursTaken(int[] arr, int speed) {
        int hours = 0;
        for (int ele : arr)
            hours += (int) Math.ceil((double) ele / (double) speed); // ? Important
        return hours;
    }

    // *https://www.geeksforgeeks.org/place-k-elements-such-that-minimum-distance-is-maximized/
    public int placeKElementsToMaximizeMinimumDistance(int[] arr, int k) {

        Arrays.sort(arr);

        int n = arr.length;

        int si = arr[0];
        int ei = arr[n - 1];

        int res = Integer.MAX_VALUE;

        while (si <= ei) {

            int mid = (si + ei) / 2;

            boolean isSelectionPossible = isSelectionPossible(arr, mid, k);

            if (isSelectionPossible)
                si = mid + 1;
            else
                ei = mid - 1;

            if (isSelectionPossible)
                res = Math.min(res, mid);
        }

        return res;
    }

    public boolean isSelectionPossible(int[] arr, int gap, int k) {

        int reference = arr[0];

        for (int i = 1; i < arr.length; i++) {
            if (arr[i] - reference >= gap) {
                reference = arr[i];
                k--;
            }
            if (k == 0)
                return true;
        }
        return false;
    }

    // *https://www.geeksforgeeks.org/allocate-minimum-number-pages/
    // *https://www.geeksforgeeks.org/painters-partition-problem/
    // *https://leetcode.com/problems/capacity-to-ship-packages-within-d-days/
    // ? Allocate Minimum Pages, Painter Partition, LeetCode 1011
    public int pageAllocationProblem(int[] arr, int k) {
        Arrays.sort(arr);

        int totalPages = 0;
        for (int ele : arr)
            totalPages += ele;

        int si = arr[0];
        int ei = totalPages;

        int res = Integer.MAX_VALUE;

        while (si <= ei) {

            int mid = (si + ei) / 2;

            boolean isPartitionPossible = isPartitionPossible(arr, mid, k);

            if (isPartitionPossible)
                ei = mid - 1;
            else
                si = mid + 1;

            if (isPartitionPossible)
                res = Math.min(res, mid);
        }

        return res;
    }

    public boolean isPartitionPossible(int[] arr, int pages, int k) {
        int countOfPages = 0;

        k--;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > pages)
                return false;
            countOfPages += arr[i];
            if (countOfPages > pages) {
                countOfPages = arr[i];
                k--;
            }
            if (k < 0)
                return false;
        }
        return true;
    }

}
