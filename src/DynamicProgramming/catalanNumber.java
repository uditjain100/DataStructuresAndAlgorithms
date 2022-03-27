package DynamicProgramming;

public class catalanNumber {

    public int getCatalanNumber(int n) {
        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = 1;

        for (int i = 2; i < n + 1; i++)
            for (int j = 0; j < i; j++)
                dp[i] += (dp[j] * dp[i - j - 1]);

        return dp[n];
    }

    public int numberOfBSTsPossible(int n) {
        return getCatalanNumber(n);
    }

    public int mountainsAndValleys(int n) {
        return getCatalanNumber(n);
    }

    public int numberOfBalancedParenthesis(int n) {
        return getCatalanNumber(n);
    }

    public int nonIntersectingChords(int n) {
        return getCatalanNumber(n / 2);
    }

    public int numberOfTriangulations(int n) {
        return (n > 1) ? getCatalanNumber(n - 2) : 0;
    }

    // *************** HV Words
    public int dyckWords(int n) {
        return getCatalanNumber(n / 2);
    }

    public int pathsInMaze(int n) {
        return getCatalanNumber(n - 1);
    }

}
