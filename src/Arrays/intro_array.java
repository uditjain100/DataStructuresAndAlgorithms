package Arrays;

public class intro_array {

    public long decimalToAnyBase(int num, int base) {
        long res = 0L;
        int b = 1;
        while (num != 0) {
            res += (num % base) * b;
            b *= 10;
            num /= 10;
        }
        return res;
    }

    public long anyBaseToDecimal(int num, int base) {
        long res = 0L;
        int b = 1;
        while (num != 0) {
            res += (num % 10) * b;
            b *= base;
            num /= base;
        }
        return res;
    }

    public long anyBaseToAnyBase(int num, int fromBase, int toBase) {
        long res = 0L;
        int b = 1;
        while (num != 0) {
            res += (num % toBase) * b;
            b *= fromBase;
            num /= fromBase;
        }
        return res;
    }

    public long anyBaseAddition(long a, long b, int base) {
        long res = 0;
        long carry = 0;
        long baseMultiplier = 1;

        while (a != 0 && b != 0) {
            long rem1 = a % 10;
            long rem2 = b % 10;

            long sum = rem1 + rem2 + carry;

            long rem = sum % base;

            carry = sum / base;
            res += (rem * baseMultiplier);

            baseMultiplier *= base;
            a /= 10;
            b /= 10;
        }

        while (a != 0) {
            long rem1 = a % 10;

            long sum = rem1 + carry;

            long rem = sum % base;

            carry = sum / base;
            res += (rem * baseMultiplier);

            baseMultiplier *= base;
            a /= 10;
        }

        while (b != 0) {
            long rem2 = b % 10;

            long sum = rem2 + carry;

            long rem = sum % base;

            carry = sum / base;
            res += (rem * baseMultiplier);

            baseMultiplier *= base;
            b /= 10;
        }

        if (carry != 0)
            res += (carry * baseMultiplier);

        return res;
    }

    public int anyBaseSubtraction(int a, int b, int base) {

        int n1 = Math.max(a, b);
        int n2 = Math.min(a, b);

        int res = 0;
        int baseMultiplier = 1;

        int carry = 0;
        while (n1 != 0 && n2 != 0) {

            int op1 = n1 % 10;
            int op2 = n2 % 10;

            boolean temp = false;
            if (carry != 0) {
                if (op1 == 0) {
                    op1 = base;
                    temp = true;
                }
                op1--;
            }

            if (op1 < op2) {
                op1 += base;
                carry = 1;
            } else if (op1 > op2) {
                if (!temp)
                    carry = 0;
            }

            res += (op1 - op2) * baseMultiplier;

            n1 /= 10;
            n2 /= 10;
            baseMultiplier *= base;
        }

        while (n1 != 0) {

            int op1 = n1 % 10;

            if (carry != 0) {
                if (op1 == 0)
                    op1 = base;
                op1--;
            }

            res += op1 * baseMultiplier;

            n1 /= 10;
            baseMultiplier *= base;
        }

        return res;
    }

    public long anyBaseMultiplication(int a, int b, int base) {

        long res = 0L;
        int outerBaseMultiplier = 1;
        while (a != 0) {

            int remA = a % 10;

            int num = b;
            int ans = 0;
            int baseMultiplier = 1;
            int carry = 0;
            while (num != 0) {
                int value = ((num % 10) * remA) + carry;

                int remB = value % base;
                carry = value / base;

                ans += remB * baseMultiplier;

                baseMultiplier *= base;
                num /= 10;
            }
            if (carry != 0)
                ans += (carry * baseMultiplier);

            ans *= outerBaseMultiplier;
            res = anyBaseAddition(res, ans, base);

            outerBaseMultiplier *= 10;
            a /= 10;
        }

        return res;
    }

    // ? Inplace
    public void inverseOfArray(int[] arr) {

        int n = arr.length;

        int currIdx = 0;
        int currVal = arr[0];
        int nextIdx = -1;

        int i = 0;
        while (i++ < n) {
            nextIdx = arr[currVal];
            arr[currVal] = currIdx;
            currIdx = currVal;
            currVal = nextIdx;
        }

        for (int ele : arr)
            System.out.println(ele);
    }

    // ? Inplace
    public void rotateAnArray(int[] arr, int r) {

        int n = arr.length;
        r %= n;

        reverse(arr, 0, r - 1);
        reverse(arr, r, n - 1);
        reverse(arr, 0, n - 1);
    }

    // ? Inplace
    public void reverse(int[] arr, int si, int ei) {
        while (si < ei) {
            int temp = arr[si];
            arr[si] = arr[ei];
            arr[ei] = temp;
            si++;
            ei--;
        }
    }

}
