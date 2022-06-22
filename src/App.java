import java.util.Arrays;
import java.util.Scanner;

public class App {

    // *https://practice.geeksforgeeks.org/explore?page=1&sortBy=submissions
    public static void main(String[] args) throws Exception {

        Scanner scn = new Scanner(System.in);
        int n = scn.nextInt();

        int[][] board = new int[n][n];
        for (int[] arr : board)
            Arrays.fill(arr, -1);
        for (int i = 0; i < n * n; i++) {
            if ((i & 1) == 0)
                System.out.println("Player A turn :");
            else
                System.out.println("Player B turn :");
            int r = scn.nextInt();
            int c = scn.nextInt();

            if ((i & 1) == 0)
                board[r][c] = 0;
            else
                board[r][c] = 1;
            int res = doesAnyOneWin(board, n);

            if (res == 1)
                System.out.println("Player A wins the Game");
            else if (res == 2)
                System.out.println("Player B wins the Game");
            if (res != -1)
                return;
        }

        System.out.println("Tie Tie Tie");
    }

    public static int doesAnyOneWin(int[][] arr, int n) {

        for (int i = 0; i < n; i++) {
            boolean isAllEqual = true;
            for (int j = 1; j < n; j++)
                if (arr[i][0] != -1 && arr[i][0] != arr[i][j])
                    isAllEqual = false;

            if (isAllEqual)
                return arr[i][0];
        }

        for (int i = 0; i < n; i++) {
            boolean isAllEqual = true;
            for (int j = 1; j < n; j++)
                if (arr[0][i] != -1 && arr[0][i] != arr[j][i])
                    isAllEqual = false;

            if (isAllEqual)
                return arr[0][i];
        }

        boolean isAllEqual = true;
        for (int i = 0; i < n; i++)
            if (arr[0][0] != -1 && arr[0][0] != arr[i][i])
                isAllEqual = false;
        if (isAllEqual)
            return arr[0][0];

        isAllEqual = true;
        for (int i = 0; i < n; i++)
            if (arr[0][n - 1] != -1 && arr[0][n - 1] != arr[i][n - i - 1])
                isAllEqual = false;
        if (isAllEqual)
            return arr[0][n - 1];

        return -1;
    }

    public static int fun(int[] arr, int idx, int[] dp) {
        if (idx == arr.length - 1)
            return dp[idx] = 0;

        if (dp[idx] != -1)
            return dp[idx];

        int minJumps = Integer.MAX_VALUE;
        for (int i = idx + 1; i <= idx + arr[idx] && i < arr.length; i++)
            minJumps = Math.min(minJumps, fun(arr, i, dp));
        return dp[idx] = minJumps + ((minJumps != Integer.MAX_VALUE) ? 1 : 0);
    }

}
