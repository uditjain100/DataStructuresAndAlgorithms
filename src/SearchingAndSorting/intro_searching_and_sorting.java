package SearchingAndSorting;

import java.util.*;

@SuppressWarnings("unchecked")
public class intro_searching_and_sorting {

    public int binarySearch(int[] arr, int si, int ei, int ele) {
        if (si > ei)
            return si; /// ? Position of Element (to be searched) if not found

        int mid = si + (ei - si) / 2;
        if (ele < arr[mid])
            return binarySearch(arr, si, mid - 1, ele);
        else if (ele > arr[mid])
            return binarySearch(arr, mid + 1, ei, ele);
        else
            return mid;
    }

    // *https://leetcode.com/problems/sqrtx/
    public int sqrt(int n) {

        long num = n;

        if (num == 0 || num == 1)
            return (int) num;

        long si = 1;
        long ei = num - 1;
        long res = -1;
        while (si <= ei) {

            long mid = si + (ei - si) / 2;
            long sqr = mid * mid;

            if (sqr == num)
                return (int) mid;
            else if (sqr < num) {
                si = mid + 1;
                res = mid;
            } else
                ei = mid - 1;

        }
        return (int) res;
    }

    public int binarySearchForDuplicates(int[] arr, int ele, boolean isFirstIndexWorkDone) {

        int si = 0;
        int ei = arr.length - 1;

        while (si <= ei) {
            int mid = si + (ei - si) / 2;

            if (ele > arr[mid])
                si = mid + 1;
            else if (ele < arr[mid])
                ei = mid - 1;
            else {
                if (!isFirstIndexWorkDone) {
                    if (mid == 0 || arr[mid] != arr[mid - 1])
                        return mid;
                    ei = mid - 1;
                } else {
                    if (mid == arr.length - 1 || arr[mid] != arr[mid + 1])
                        return mid;
                    si = mid + 1;
                }
            }
        }
        return -1;
    }

    public int[] firstAndLastPositionOfTarget(int[] arr, int ele) {

        int fi = binarySearchForDuplicates(arr, ele, false);
        int li = binarySearchForDuplicates(arr, ele, true);

        return new int[] { fi, li };
    }

    public int lowerBound(int[] arr, int target) {
        int n = arr.length;
        if (target < arr[0])
            return 0;
        if (target > arr[n - 1])
            return -1;

        int si = 0;
        int ei = n - 1;

        int res = -1;

        while (si <= ei) {
            int mid = si + (ei - si) / 2;

            if (arr[mid] >= target) {
                res = mid;
                ei = mid - 1;
            } else
                si = mid + 1;
        }
        return res;
    }

    public int upperBound(int[] arr, int target) {
        int n = arr.length;
        if (target < arr[0])
            return -1;
        if (target > arr[n - 1])
            return n - 1;

        int si = 0;
        int ei = n - 1;

        int res = -1;

        while (si <= ei) {
            int mid = si + (ei - si) / 2;

            if (arr[mid] <= target) {
                res = mid;
                si = mid + 1;
            } else
                ei = mid - 1;
        }
        return res;
    }

    // *https://practice.geeksforgeeks.org/problems/counting-elements-in-two-arrays/1
    public List<Integer> countElementsInTwoSortedArrays(int[] a, int[] b) {
        List<Integer> res = new ArrayList<>();

        Arrays.sort(b);

        for (int i = 0; i < a.length; i++) {
            int idx = lowerBound(b, a[i]);
            res.add(idx + 1);
        }
        return res;
    }

    // *https://leetcode.com/problems/heaters/
    public int heaters(int[] arr, int[] heaters) {

        Arrays.sort(heaters);

        int minRadius = Integer.MIN_VALUE;
        for (int ele : arr) {
            int lb = lowerBound(heaters, ele);
            int ub = upperBound(heaters, ele);

            int leftDistance = Integer.MAX_VALUE;
            int rightDistance = Integer.MAX_VALUE;

            if (lb != -1)
                leftDistance = heaters[lb] - ele;
            if (ub != -1)
                rightDistance = ele - heaters[ub];

            minRadius = Math.max(minRadius, Math.min(leftDistance, rightDistance));
        }
        return minRadius;
    }

    // *https://www.geeksforgeeks.org/find-transition-point-binary-array/
    public int findTransitionPoint(int[] arr) {
        int si = 0;
        int ei = arr.length - 1;

        int res = -1;

        while (si < ei) {
            int mid = si + (ei - si) / 2;

            if (arr[mid] == 0)
                si = mid + 1;
            else {
                ei = mid - 1;
                res = mid;
            }
        }
        return res;
    }

    // *https://www.geeksforgeeks.org/find-the-row-with-maximum-number-1s/
    public int countMax1sInMatrix(int[][] arr) {
        int max = findTransitionPoint(arr[0]);
        int rowNumber = 0;

        for (int i = 1; i < arr.length; i++)
            if (arr[i][max] == 1) {
                rowNumber = i;
                max = findTransitionPoint(arr[i]);
            }
        return rowNumber;
    }

    // *https://leetcode.com/problems/median-of-two-sorted-arrays/
    public double findMedianSortedArrays(int[] a, int[] b) {

        int aLength = a.length;
        int bLength = b.length;

        if (aLength > bLength) {
            int[] temp = a;
            a = b;
            b = temp;
        }

        if (aLength == 0)
            return (bLength % 2 == 0) ? (double) (b[(bLength / 2)] + b[(bLength / 2) - 1]) / 2 : b[bLength / 2];
        if (bLength == 0)
            return (aLength % 2 == 0) ? (double) (a[(aLength / 2)] + a[(aLength / 2) - 1]) / 2 : a[aLength / 2];

        int n = aLength + bLength;

        int si = 0;
        int ei = aLength - 1;

        while (si <= ei) {

            int aLeft = (si + ei) / 2;
            int bLeft = ((n + 1) / 2) - aLeft;

            int aLeftValue = (aLeft >= aLength) ? Integer.MAX_VALUE : a[aLeft];
            int bLeftValue = (bLeft >= bLength) ? Integer.MAX_VALUE : b[bLeft];
            int aLeftMinus1Value = (aLeft <= 0) ? Integer.MIN_VALUE : a[aLeft - 1];
            int bLeftMinus1Value = (bLeft <= 0) ? Integer.MIN_VALUE : b[bLeft - 1];

            if (aLeftMinus1Value <= bLeftValue && bLeftMinus1Value <= aLeftValue)
                return (n % 2 != 0) ? Math.max(aLeftMinus1Value, bLeftMinus1Value)
                        : (Math.max(aLeftMinus1Value, bLeftMinus1Value) + Math.min(aLeftValue, bLeftValue)) / 2.0;
            else if (aLeftMinus1Value > bLeftValue)
                ei = aLeft - 1;
            else
                si = aLeft + 1;

        }

        return -1;
    }

    public int kThElementIn2SortedArrays(int[] a, int[] b, int k) {

        int aLength = a.length;
        int bLength = b.length;

        if (aLength > bLength) {
            int[] temp = a;
            a = b;
            b = temp;
        }

        if (aLength == 0)
            return b[k - 1];
        if (bLength == 0)
            return a[k - 1];

        int si = 0;
        int ei = aLength - 1;

        while (si <= ei) {

            int aLeft = (si + ei) / 2;
            int bLeft = k - aLeft;

            int aLeftValue = (aLeft >= aLength) ? Integer.MAX_VALUE : a[aLeft];
            int bLeftValue = (bLeft >= bLength) ? Integer.MAX_VALUE : b[bLeft];
            int aLeftMinus1Value = (aLeft <= 0) ? Integer.MIN_VALUE : a[aLeft - 1];
            int bLeftMinus1Value = (bLeft <= 0) ? Integer.MIN_VALUE : b[bLeft - 1];

            if (aLeftMinus1Value <= bLeftValue && bLeftMinus1Value <= aLeftValue)
                return Math.max(aLeftMinus1Value, bLeftMinus1Value);
            else if (aLeftMinus1Value > bLeftValue)
                ei = aLeft - 1;
            else
                si = aLeft + 1;

        }

        return -1;

    }

    public void BubbleSort(int[] arr) {

        for (int i = 0; i < arr.length; i++) {
            boolean isSwapped = false;
            for (int j = 0; j < arr.length - i - 1; j++)
                if (arr[j + 1] < arr[j]) {
                    isSwapped = true;
                    int temp = arr[j + 1];
                    arr[j + 1] = arr[j];
                    arr[j] = temp;
                }
            if (!isSwapped)
                break;
        }

    }

    public int minSwapsToMakeArraySorted(int[] arr) {
        int n = arr.length;
        int[][] array = new int[n][2];

        for (int i = 0; i < n; i++) {
            array[i][0] = arr[i];
            array[i][1] = i;
        }

        Arrays.sort(array, (a, b) -> a[0] - b[0]);

        int count = 0;
        for (int i = 0; i < n; i++)
            if (array[i][1] != i) {
                swap(array, i, array[i][1]);
                i--;
                count++;
            }

        return count;
    }

    public void swap(int[][] arr, int i, int j) {
        int temp = arr[i][0];
        arr[i][0] = arr[j][0];
        arr[j][0] = temp;

        temp = arr[i][1];
        arr[i][1] = arr[j][1];
        arr[j][1] = temp;

    }

    // *https://practice.geeksforgeeks.org/problems/punish-the-students5726/1
    public boolean isStudentPunished(int[] arr, int[] marks, int avgMarks) {
        int totalMarks = 0;
        for (int ele : marks)
            totalMarks += ele;

        int minSwaps = minSwapsToMakeArraySorted(arr);

        return ((double) totalMarks / (double) minSwaps) >= avgMarks;
    }

    public void SelectionSort(int[] arr) {

        for (int i = 0; i < arr.length; i++) {
            int idx = i;
            for (int j = i + 1; j < arr.length; j++)
                if (arr[j] < arr[idx])
                    idx = j;
            if (idx != i) {
                int temp = arr[idx];
                arr[idx] = arr[i];
                arr[i] = temp;
            }
        }

    }

    public void InsertionSort(int[] arr) {

        for (int i = 1; i < arr.length; i++) {
            int temp = arr[i];
            int j = i - 1;
            while (j >= 0 && temp < arr[j]) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = temp;
        }

    }

    public void mergeSort(int[] arr) {
        mergeSort(arr, 0, arr.length - 1);
    }

    public void mergeSort(int[] arr, int si, int ei) {
        if (si == ei)
            return;

        int mid = (si + ei) / 2;

        mergeSort(arr, si, mid);
        mergeSort(arr, mid + 1, ei);

        mergeTwoSortedArrays(arr, si, mid, ei);
    }

    public void mergeTwoSortedArrays(int[] arr, int si, int mid, int ei) {
        int[] res = new int[ei - si + 1];

        int i = si;
        int j = mid + 1;
        int k = 0;
        while (i < mid + 1 && j < ei)
            if (arr[i] <= arr[j])
                res[k++] = arr[i++];
            else
                res[k++] = arr[j++];

        while (i < mid + 1)
            res[k++] = arr[i++];
        while (j < ei)
            res[k++] = arr[j++];

        for (int x = si; x < ei; x++)
            arr[x] = res[x - si];
    }

    // *https://www.geeksforgeeks.org/merge-two-sorted-arrays-o1-extra-space/
    public void mergeWithoutExtraSpace(int[] a, int[] b) {

        int i = 0;
        int j = 0;

        int ei = a.length - 1;

        while (i < a.length && j < b.length) {
            if (b[j] < a[i]) {
                int temp = a[ei];
                a[ei] = b[j];
                b[j] = temp;

                temp = a[ei];
                a[ei] = a[i];
                a[i] = temp;
                ei--;
            }
            i++;
            j++;
        }
        Arrays.sort(a);
        Arrays.sort(b);
    }

    // **** i > j and a[i] < a[j] => Number of Elements Smaller than the current
    // **** element on right side
    public int countInversions(int[] arr, int si, int mid, int ei) {
        int[] res = new int[ei - si + 1];

        int count = 0;
        int i = si;
        int j = mid + 1;
        int k = 0;
        while (i < mid + 1 && j < ei)
            if (arr[i] <= arr[j])
                res[k++] = arr[i++];
            else {
                res[k++] = arr[j++];
                count += mid + 1 - i;
            }

        while (i < mid + 1)
            res[k++] = arr[i++];
        while (j < ei)
            res[k++] = arr[j++];

        for (int x = si; x < ei; x++)
            arr[x] = res[x - si];

        return count;
    }

    public int countInversions(int[] arr, int si, int ei) {
        if (si >= ei)
            return 0;

        int mid = (si + ei) / 2;

        int count = 0;
        count += countInversions(arr, si, mid);
        count += countInversions(arr, mid + 1, ei);
        count += countInversions(arr, si, mid, ei);
        return count;
    }

    public int Partition(int[] arr, int si, int ei, int pivot) {
        if (pivot == -1)
            pivot = arr[ei];
        int i = si;

        for (int j = i; j < ei; j++)
            if (arr[j] < pivot) {
                swap(arr, i, j);
                i++;
            }

        int pivotIdx = ei;
        for (int idx = si; idx <= ei; idx++)
            if (pivot == arr[idx]) {
                pivotIdx = idx;
                break;
            }

        swap(arr, i, pivotIdx);
        return i;
    }

    public void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public void QuickSort(int[] arr) {
        QuickSort(arr, 0, arr.length - 1);
    }

    public void QuickSort(int[] arr, int si, int ei) {
        if (si >= ei)
            return;

        int pivotIdx = Partition(arr, si, ei, -1);

        QuickSort(arr, si, pivotIdx - 1);
        QuickSort(arr, pivotIdx + 1, ei);
    }

    public int QuickSelect(int[] arr, int si, int ei, int k) {
        int pivotIdx = Partition(arr, si, ei, -1);
        if (k - 1 < pivotIdx)
            return QuickSelect(arr, si, pivotIdx - 1, k);
        else if (k - 1 > pivotIdx)
            return QuickSelect(arr, pivotIdx + 1, ei, k);
        else
            return arr[pivotIdx];
    }

    // ***** i < j < k and arr[i] >= arr[j] <= arr[k]
    public void waveSort1(int[] arr) {
        for (int i = 1; i < arr.length; i += 2) {
            if (arr[i] > arr[i - 1])
                swap(arr, i, i - 1);
            if (i != arr.length - 1 && arr[i] > arr[i + 1])
                swap(arr, i, i + 1);
        }
    }

    // ***** i < j < k and arr[i] > arr[j] < arr[k]
    public void waveSort2(int[] arr) {

        int mid = (arr.length + 1) / 2;

        int median = QuickSelect(arr, 0, arr.length - 1, mid); // ? Median of Unsorted array using QuickSelect
        int[] temp = new int[arr.length];

        int s = 0;
        int e = arr.length - 1;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] < median)
                temp[s++] = arr[i];
            else if (arr[i] > median)
                temp[e--] = arr[i];
        }
        while (s < mid)
            temp[s++] = median;
        while (e >= mid)
            temp[e--] = median;

        s = mid - 1;
        e = arr.length - 1;
        for (int i = 0; i < arr.length; i++) {
            if (i % 2 == 0)
                arr[i] = temp[s--];
            else
                arr[i] = temp[e--];
        }
    }

    // *https://leetcode.com/problems/minimum-moves-to-equal-array-elements/
    public int minimumMovesToEqualArrayElements1(int[] arr) {
        int min = Integer.MAX_VALUE;

        for (int ele : arr)
            min = Math.min(min, ele);

        int res = 0;
        for (int ele : arr)
            res += ele - min;

        return res;
    }

    // *https://leetcode.com/problems/minimum-moves-to-equal-array-elements-ii/
    public int minimumMovesToEqualArrayElements2(int[] arr) {

        int n = arr.length;
        if (n == 1)
            return 0;
        if (n == 2)
            return (int) Math.abs(arr[0] - arr[1]);

        int mid = (n + 1) / 2;
        int medianIdx = QuickSelect(arr, 0, arr.length - 1, mid);
        int median = arr[medianIdx];

        int res = 0;
        for (int ele : arr)
            res += Math.abs(ele - median);
        return res;
    }

    public void CountingSort(int[] arr) {

        int max = Integer.MIN_VALUE;
        for (int ele : arr)
            max = Math.max(max, ele);

        int[] freq = new int[max + 1];
        for (int ele : arr)
            freq[ele]++;

        int i = 0;
        int k = 0;
        for (int ele : freq) {
            while (ele-- > 0)
                arr[k++] = i;
            i++;
        }
    }

    public void ShellSort(int[] arr) {

        int gap = arr.length / 2;

        int i = 0;
        int j = 0;
        int k = 0;

        while (gap != 0) {

            i = 0;
            j = i + gap;

            while (j != arr.length) {

                if (arr[i] > arr[j]) {
                    int temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;

                    if (i - gap >= 0) {
                        k = i - gap;
                        if (arr[i] < arr[k]) {
                            int temp2 = arr[i];
                            arr[i] = arr[k];
                            arr[k] = temp2;
                        }
                    }

                }
            }

            gap /= 2;
        }

    }

    public void RadixSort(int[] arr) {
        int max = Integer.MIN_VALUE;
        for (int ele : arr)
            max = Math.max(max, ele);
        for (int pos = 1; (max / pos) > 0; pos *= 10)
            radix_pos_count_sort(arr, pos);
    }

    public void radix_pos_count_sort(int[] arr, int pos) {

        int[] op = new int[arr.length];
        int[] count = new int[10];

        for (int i = 0; i < arr.length; i++)
            count[(arr[i] / pos) % 10]++;

        for (int i = 1; i < 10; i++)
            count[i] += count[i - 1];

        for (int i = arr.length - 1; i >= 0; i--)
            op[--count[(arr[i] / pos) % 10]] = arr[i];

        for (int i = 0; i < arr.length; i++)
            arr[i] = op[i];
    }

    // *https://leetcode.com/problems/largest-number/
    public String largestNumber(int[] arr) {
        int n = arr.length;
        String[] strArr = new String[n];

        for (int i = 0; i < n; i++)
            strArr[i] = "" + arr[i];

        Arrays.sort(strArr, (a, b) -> {
            String s1 = a + b;
            String s2 = b + a;
            return s1.compareTo(s2);
        });

        StringBuilder sb = new StringBuilder();
        for (int i = n - 1; i >= 0; i--)
            sb.append(strArr[i]);

        if (sb.charAt(0) == '0')
            return "0";

        return sb.toString();
    }

    public void BucketSort(float[] arr) {

        ArrayList<Float>[] buckets = new ArrayList[arr.length];
        for (int i = 0; i < arr.length; i++)
            buckets[i] = new ArrayList<>();

        for (int i = 0; i < arr.length; i++)
            buckets[(int) (arr[i] * arr.length)].add(arr[i]);

        for (ArrayList<Float> bucket : buckets)
            Collections.sort(bucket);

        int idx = 0;
        for (ArrayList<Float> bucket : buckets)
            for (float ele : bucket)
                arr[idx++] = ele;
    }

    // * Three Way Sorting
    public void DNFSort(int[] arr) {

        int i = 0;
        int j = 0;
        int k = arr.length - 1;

        while (i < k) {
            if (arr[i] == 0) {
                swap(arr, i, j);
                j++;
            } else if (arr[i] == 2) {
                swap(arr, i, k);
                i--;
                k--;
            }
            i++;
        }

    }

}
