package Matrix;

import java.util.*;

public class intro_matrix {

    public int[][] matrixMultiplication(int[][] matrix1, int[][] matrix2) {

        int m1 = matrix1.length;
        int n1 = matrix1[0].length;

        int m2 = matrix2.length;
        int n2 = matrix2[0].length;

        if (n1 != m2)
            return new int[][] { { 0 } };

        int[][] res = new int[m1][n2];

        for (int i = 0; i < m1; i++)
            for (int j = 0; j < n2; j++) {
                int sum = 0;
                for (int k = 0; k < n1; k++)
                    sum += matrix1[i][k] * matrix2[k][j];
                res[i][j] = sum;
            }

        return res;
    }

    public void transpose(int[][] arr) {
        int n = arr.length;
        int m = arr[0].length;

        for (int i = 0; i < n; i++)
            for (int j = i + 1; j < m; j++) {
                int temp = arr[i][j];
                arr[i][j] = arr[j][i];
                arr[j][i] = temp;
            }
    }

    public void transposeAParticularShell(int[][] arr, int shellNo) {
        int n = arr.length;
        int m = arr[0].length;

        for (int j = shellNo; j < m - shellNo - 1; j++) {
            int temp = arr[shellNo][j];
            arr[shellNo][j] = arr[j][shellNo];
            arr[j][shellNo] = temp;
        }

        for (int i = shellNo + 1; i < n - shellNo - 1; i++) {
            int temp = arr[i][n - shellNo - 1];
            arr[i][n - shellNo - 1] = arr[n - shellNo - 1][i];
            arr[n - shellNo - 1][i] = temp;
        }

    }

    // *https://www.geeksforgeeks.org/print-matrix-in-wave-form/
    public ArrayList<Integer> waveTraversal(int[][] arr) {
        int n = arr.length;
        int m = arr[0].length;

        ArrayList<Integer> list = new ArrayList<>();

        for (int j = 0; j < m; j++)
            if (j % 2 == 0)
                for (int i = 0; i < n; i++)
                    list.add(arr[i][j]);
            else
                for (int i = n - 1; i >= 0; i--)
                    list.add(arr[i][j]);

        return list;
    }

    // *https://www.geeksforgeeks.org/print-a-given-matrix-in-spiral-form/
    public ArrayList<Integer> spiralDisplay(int[][] arr) {

        ArrayList<Integer> list = new ArrayList<>();

        int n = arr.length;
        int m = arr[0].length;

        int numberOdSpirals = (Math.min(n, m) + 1) / 2;

        for (int shellNo = 0; shellNo < numberOdSpirals; shellNo++) {
            for (int i = shellNo; i < n - 1 - shellNo; i++)
                list.add(arr[i][shellNo]);

            for (int j = shellNo; j < m - 1 - shellNo; j++)
                list.add(arr[n - shellNo - 1][j]);

            for (int i = n - 1 - shellNo; i > shellNo; i--)
                list.add(arr[i][m - shellNo - 1]);

            for (int j = m - 1 - shellNo; j > shellNo; j--)
                list.add(arr[shellNo][j]);
        }

        return list;
    }

    // *https://www.geeksforgeeks.org/print-kth-element-spiral-form-matrix/
    public int kThElementInSpiralTraversal(int[][] arr, int k) {

        int n = arr.length;
        int m = arr[0].length;

        if (k > (m * n))
            return -1;

        int numberOdSpirals = (Math.min(n, m) + 1) / 2;

        k--;
        for (int shellNo = 0; shellNo < numberOdSpirals; shellNo++) {

            // * *** Dir1 -> Top to Down
            // * *** Dir2 -> Left To Right
            // * *** Dir3 -> Down to Top
            // * *** Dir4 -> Right to Left

            int startRowOfDir1 = shellNo;
            int endRowOfDir1 = n - 2 - shellNo;

            int colOfDir1 = shellNo;

            if (k >= startRowOfDir1 && k <= endRowOfDir1)
                return arr[k][colOfDir1];

            k -= (endRowOfDir1 - startRowOfDir1 + 1);

            int startColOfDir2 = shellNo;
            int endColOfDir2 = m - 2 - shellNo;

            int rowOfDir2 = n - 1 - shellNo;

            if (k >= startColOfDir2 && k <= endColOfDir2)
                return arr[rowOfDir2][k];

            k -= (endColOfDir2 - startColOfDir2 + 1);

            int startRowOfDir3 = n - 1 - shellNo;
            int endRowOfDir3 = shellNo + 1;

            int colOfDir3 = m - 1 - shellNo;

            if (k >= startRowOfDir3 && k <= endRowOfDir3)
                return arr[k][colOfDir3];

            k -= (endRowOfDir3 - startRowOfDir3 + 1);

            int startColOfDir4 = m - 1 - shellNo;
            int endColOfDir4 = shellNo + 1;

            int rowOfDir4 = shellNo;

            if (k >= startColOfDir4 && k <= endColOfDir4)
                return arr[rowOfDir4][k];

            k -= (endColOfDir4 - startColOfDir4 + 1);
        }

        return -1;
    }

    // *https://www.geeksforgeeks.org/exit-point-in-a-binary-matrix/
    public int[] exitPointInMatrix(int[][] arr) {

        int n = arr.length;
        int m = arr[0].length;

        int i = 0;
        int j = 0;

        int dirIndex = 0;

        while (i < n && j < m) {
            if (arr[i][j] == 1)
                dirIndex = (dirIndex + 1) % 4;

            if (dirIndex == 0) // ? East
                i++;
            else if (dirIndex == 1) // ? South
                j++;
            else if (dirIndex == 2) // ? West
                i--;
            else // ? North
                j--;
        }

        return new int[] { i, j };
    }

    public void rotateBy90DegreeClockWise(int[][] arr) {
        int n = arr.length;
        int m = arr[0].length;

        transpose(arr);

        for (int i = 0; i < n; i++) {
            int si = 0;
            int ei = m - 1;

            while (si < ei) {
                int temp = arr[i][si];
                arr[i][si] = arr[i][ei];
                arr[i][ei] = temp;
                si++;
                ei--;
            }
        }
    }

    // *https://www.geeksforgeeks.org/inplace-rotate-square-matrix-by-90-degrees/
    public void rotateBy90DegreeAntiClockWise(int[][] arr) {
        int n = arr.length;
        int m = arr[0].length;

        transpose(arr);

        for (int j = 0; j < m; j++) {
            int si = 0;
            int ei = n - 1;

            while (si < ei) {
                int temp = arr[si][j];
                arr[si][j] = arr[ei][j];
                arr[ei][j] = temp;
                si++;
                ei--;
            }
        }
    }

    public void shellRotateClockWise(int[][] arr, int shellNo, int numberOfRotations) {

        int n = arr.length;
        int m = arr[0].length;

        numberOfRotations %= 4;
        for (int r = 0; r < numberOfRotations; r++) {
            transposeAParticularShell(arr, shellNo);

            int si = shellNo;
            int ei = m - shellNo - 1;

            while (si < ei) { // ? Horizontal
                int temp = arr[shellNo][si];
                arr[shellNo][si] = arr[shellNo][ei];
                arr[shellNo][ei] = temp;

                temp = arr[n - 1 - shellNo][si];
                arr[n - 1 - shellNo][si] = arr[n - 1 - shellNo][ei];
                arr[n - 1 - shellNo][ei] = temp;

                si++;
                ei--;
            }

            for (int i = shellNo + 1; i < n - shellNo - 1; i++) { // ? Vertical
                int temp = arr[i][shellNo];
                arr[i][shellNo] = arr[ei][n - shellNo - 1];
                arr[i][n - shellNo - 1] = temp;
            }

        }

    }

    // *https://www.geeksforgeeks.org/rotate-ring-matrix-anticlockwise-k-elements/
    public void shellRotateAntiClockWise(int[][] arr, int shellNo, int numberOfRotations) {

        int n = arr.length;
        int m = arr[0].length;

        numberOfRotations %= 4;
        for (int r = 0; r < numberOfRotations; r++) {
            transposeAParticularShell(arr, shellNo);

            for (int j = shellNo; j < m - shellNo - 1; j++) { // ? Horizontal
                int temp = arr[shellNo][j];
                arr[shellNo][j] = arr[n - shellNo - 1][j];
                arr[n - shellNo - 1][j] = temp;
            }

            int si = shellNo + 1;
            int ei = n - shellNo - 2;
            while (si < ei) { // ? Vertical

                int temp = arr[si][shellNo];
                arr[si][shellNo] = arr[ei][shellNo];
                arr[ei][shellNo] = temp;

                temp = arr[si][n - shellNo - 1];
                arr[si][n - shellNo - 1] = arr[ei][n - shellNo - 1];
                arr[ei][n - shellNo - 1] = temp;

                si++;
                ei--;
            }
        }

    }

    public ArrayList<Integer> diagonalTraversal(int[][] arr) {
        int n = arr.length;
        int m = arr[0].length;

        ArrayList<Integer> list = new ArrayList<>();

        for (int gap = 0; gap < m; gap++)
            for (int i = 0, j = gap; i < n && j < m; i++, j++)
                list.add(arr[i][j]);
        return list;
    }

    public ArrayList<Integer> antiDiagonalTraversal(int[][] arr) {
        int n = arr.length;
        int m = arr[0].length;

        ArrayList<Integer> list = new ArrayList<>();

        for (int sum = m - 1; sum < n + m - 1; sum++)
            for (int i = sum - m + 1, j = m - 1; i < n && j >= 0; i++, j--)
                list.add(arr[i][j]);

        return list;
    }

    // *https://www.geeksforgeeks.org/sort-a-2d-vector-diagonally/
    public void sortMatrixDiagonally(int[][] arr) {

        int n = arr.length;
        int m = arr[0].length;

        for (int gap = 1; gap < m; gap++) {
            ArrayList<Integer> list = new ArrayList<>();
            for (int i = 0, j = gap; i < n && j < m; i++, j++)
                list.add(arr[i][j]);
            Collections.sort(list);
            int k = 0;
            for (int i = 0, j = gap; i < n && j < m; i++, j++)
                arr[i][j] = list.get(k++);

            list.clear();
            for (int i = gap, j = 0; i < n && j < m; i++, j++)
                list.add(arr[i][j]);
            Collections.sort(list);
            Collections.reverse(list);

            k = 0;
            for (int i = gap, j = 0; i < n && j < m; i++, j++)
                arr[i][j] = list.get(k++);
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                System.out.print(arr[i][j] + ", ");
            }
            System.out.println();
        }

    }

    // *https://www.geeksforgeeks.org/saddle-point-matrix/
    public int saddlePoint(int[][] arr) {

        int n = arr.length;
        int m = arr[0].length;

        for (int i = 0; i < n; i++) {
            int minCol = i;
            int min = arr[i][0];
            for (int j = 1; j < m; j++)
                if (min > arr[i][j]) {
                    minCol = j;
                    min = arr[i][j];
                }

            for (int k = 1; k < n; k++)
                min = Math.max(min, arr[k][minCol]);

            if (min == arr[i][minCol])
                return arr[i][minCol];
        }
        return -1;
    }

    // *https://www.geeksforgeeks.org/find-median-row-wise-sorted-matrix/
    public int medianOfRowWiseSortedMatrix(int[][] arr) {

        int n = arr.length;
        int m = arr[0].length;

        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            max = Math.max(max, arr[i][m - 1]);
            min = Math.min(min, arr[i][0]);
        }

        int si = min;
        int ei = max;

        while (si <= ei) {
            int mid = si + (ei - si) / 2;
            int count = countLessNumbers(arr, mid);

            if (count < (n * m) / 2)
                si = mid + 1;
            else
                ei = mid - 1;
        }
        return si;
    }

    public int countLessNumbers(int[][] arr, int reference) {
        int n = arr.length;

        int count = 0;
        for (int i = 0; i < n; i++)
            count += upperBound(arr[i], reference) + 1;
        return count;
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

    // *https://www.geeksforgeeks.org/common-elements-in-all-rows-of-a-given-matrix/
    public ArrayList<Integer> commonElements(int[][] arr) {
        int n = arr.length;
        int m = arr[0].length;

        HashMap<Integer, Integer> map = new HashMap<>();

        for (int j = 0; j < m; j++)
            map.put(arr[0][j], 0);

        for (int i = 1; i < n; i++)
            for (int j = 0; j < m; j++)
                if (map.containsKey(arr[i][j]) && map.get(arr[i][j]) == i - 1)
                    map.put(arr[i][j], i);

        ArrayList<Integer> list = new ArrayList<>();

        for (int key : map.keySet())
            if (map.get(key) == n - 1)
                list.add(key);
        return list;
    }

    // *https://www.geeksforgeeks.org/find-a-specific-pair-in-matrix/
    public int specificPair(int[][] arr) {

        int n = arr.length;
        int m = arr[0].length;

        int temp[][] = new int[n][m];
        temp[n - 1][m - 1] = arr[n - 1][m - 1];

        int max = arr[n - 1][m - 1];
        for (int j = m - 2; j >= 0; j--) {
            max = Math.max(max, arr[n - 1][j]);
            temp[n - 1][j] = max;
        }

        max = arr[n - 1][n - 1];
        for (int i = n - 2; i >= 0; i--) {
            max = Math.max(max, arr[i][m - 1]);
            temp[i][m - 1] = max;
        }

        max = Integer.MIN_VALUE;
        for (int i = n - 2; i >= 0; i--)
            for (int j = m - 2; j >= 0; j--) {
                max = Math.max(max, temp[i + 1][j + 1] - arr[i][j]);
                temp[i][j] = Math.max(arr[i][j], Math.max(temp[i][j + 1], temp[i + 1][j]));
            }

        return max;
    }

    // *https://practice.geeksforgeeks.org/problems/boolean-matrix-problem-1587115620/1/?page=1&difficulty[]=1&category[]=Matrix&sortBy=submissions#
    public void setMatrix(int[][] arr) {

        int n = arr.length;
        int m = arr[0].length;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (arr[i][j] == 1) {
                    for (int r = 0; r < n; r++)
                        if (arr[r][j] != 1)
                            arr[r][j] = 2;
                    for (int c = 0; c < m; c++)
                        if (arr[i][c] != 1)
                            arr[i][c] = 2;
                }
            }
        }

        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                if (arr[i][j] == 2)
                    arr[i][j] = 1;

    }

    // *https://practice.geeksforgeeks.org/problems/85781966a9db2a1ac17eaaed26a947eba5740d56/1/?page=1&difficulty[]=1&category[]=Matrix&sortBy=submissions#
    public void computeBeforeMatrix(int[][] arr) {

        int n = arr.length;
        int m = arr[0].length;

        int[] sumArr = new int[m];
        sumArr[0] = arr[0][0];

        for (int i = 0; i < n; i++) {
            int sum = 0;
            for (int j = 0; j < m; j++) {
                if (i == 0 && j == 0) {
                    sum += arr[i][j];
                    continue;
                }

                arr[i][j] -= (sumArr[j] + sum);
                sum += arr[i][j];
                sumArr[j] += sum;
            }
        }
    }

    // *https://practice.geeksforgeeks.org/problems/replace-os-with-xs0052/1/?page=1&difficulty[]=1&category[]=Matrix&sortBy=submissions#
    public void replaceOsWithXs(char[][] arr) {

        int n = arr.length;
        int m = arr[0].length;

        boolean[][] vis = new boolean[n][m];
        for (int i = 0; i < n; i++)
            if (arr[i][0] == 'O')
                dfs(arr, i, 0, n, m, vis, 'O', 'S');
        for (int i = 0; i < n; i++)
            if (arr[i][m - 1] == 'O')
                dfs(arr, i, m - 1, n, m, vis, 'O', 'S');
        for (int j = 0; j < m; j++)
            if (arr[0][j] == 'O')
                dfs(arr, 0, j, n, m, vis, 'O', 'S');
        for (int j = 0; j < m; j++)
            if (arr[n - 1][j] == 'O')
                dfs(arr, n - 1, j, n, m, vis, 'O', 'S');

        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                if (arr[i][j] == 'O')
                    dfs(arr, i, j, n, m, vis, 'O', 'X');
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                if (arr[i][j] == 'S')
                    dfs(arr, i, j, n, m, vis, 'S', 'O');

    }

    int[][] dir = { { 0, 1 }, { 1, 0 }, { -1, 0 }, { 0, -1 } };

    public void dfs(char[][] arr, int i, int j, int n, int m, boolean[][] vis, char c1, char c2) {
        arr[i][j] = c2;
        vis[i][j] = true;

        for (int d = 0; d < 4; d++) {
            int x = dir[d][0] + i;
            int y = dir[d][1] + j;
            if (x >= 0 && x < n && y >= 0 && y < m && !vis[x][y] && arr[x][y] == c1)
                dfs(arr, x, y, n, m, vis, c1, c2);
        }
    }

}
