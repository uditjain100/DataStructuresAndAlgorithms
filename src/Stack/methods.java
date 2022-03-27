package Stack;

import java.util.*;

import Stack.intro_stack.Stack;
import Stack.intro_stack.StackLL;
import Tree.binaryTree.TreeNode;

public class methods {

    // ****** Method 1 -> Recursive O(n^2)
    public void reverseStack1(Stack s) {
        if (s.isEmpty())
            return;

        int re = s.pop();
        reverseStack1(s);

        insertBottom(s, re);
    }

    public void insertBottom(Stack s, int ele) {
        if (s.isEmpty()) {
            s.push(ele);
            return;
        }

        int re = s.pop();
        insertBottom(s, ele);

        s.push(re);
    }

    // ****** Method 2 -> Iterative Using Space O(n)
    public void reverseStack2(Stack s) {

        StackLL stack = new StackLL();
        while (!s.isEmpty())
            stack.push(s.pop());

        stack.reverse();

        while (!stack.isEmpty())
            s.push(stack.pop());
    }

    public void sort(Stack s) {
        if (s.isEmpty())
            return;

        int re = s.pop();
        sort(s);

        insertAtRightPosition(s, re);
    }

    public void insertAtRightPosition(Stack s, int ele) {
        if (s.isEmpty() || s.peek() <= ele) {
            s.push(ele);
            return;
        }

        int re = s.pop();
        insertAtRightPosition(s, ele);
        s.push(re);
    }

    public int[] nextGreater(int[] arr) {
        Stack s = new Stack(arr.length);

        int[] res = new int[arr.length];
        Arrays.fill(res, arr.length);
        for (int i = 0; i < arr.length; i++) {
            while (!s.isEmpty() && arr[s.peek()] < arr[i])
                res[s.pop()] = i;
            s.push(i);
        }
        return res;
    }

    public int[] nextSmaller(int[] arr) {
        Stack s = new Stack(arr.length);

        int[] res = new int[arr.length];
        Arrays.fill(res, arr.length);
        for (int i = 0; i < arr.length; i++) {
            while (!s.isEmpty() && arr[s.peek()] > arr[i])
                res[s.pop()] = i;
            s.push(i);
        }
        return res;
    }

    public int[] previousGreater(int[] arr) {
        Stack s = new Stack(arr.length);

        int[] res = new int[arr.length];
        Arrays.fill(res, -1);
        for (int i = arr.length - 1; i >= 0; i--) {
            while (!s.isEmpty() && arr[s.peek()] < arr[i])
                res[s.pop()] = i;
            s.push(i);
        }
        return res;
    }

    public int[] previousSmaller(int[] arr) {
        Stack s = new Stack(arr.length);

        int[] res = new int[arr.length];
        Arrays.fill(res, -1);
        for (int i = arr.length - 1; i >= 0; i--) {
            while (!s.isEmpty() && arr[s.peek()] > arr[i])
                res[s.pop()] = i;
            s.push(i);
        }
        return res;
    }

    public int[] maxOfMinOfAllWindows(int[] arr) {

        int[] prevSmaller = previousSmaller(arr);
        int[] nextSmaller = nextSmaller(arr);

        int[] res = new int[arr.length];

        for (int i = 0; i < arr.length; i++) {
            int windowSize = nextSmaller[i] - prevSmaller[i] - 1;
            res[windowSize - 1] = Math.max(res[windowSize - 1], arr[i]);
        }

        for (int i = arr.length - 2; i >= 0; i--)
            res[i] = Math.max(res[i], res[i + 1]);

        return res;
    }

    public int rainwaterTrapping1(int[] arr) {

        int[] leftMax = new int[arr.length];
        int[] rightMax = new int[arr.length];

        leftMax[0] = Integer.MIN_VALUE;
        rightMax[arr.length - 1] = Integer.MIN_VALUE;

        for (int i = 1; i < arr.length; i++)
            leftMax[i] = Math.max(arr[i - 1], leftMax[i - 1]);
        for (int i = arr.length - 2; i >= 0; i--)
            rightMax[i] = Math.max(arr[i + 1], rightMax[i + 1]);

        int res = 0;
        for (int i = 0; i < arr.length; i++)
            res += Math.max(0, Math.min(leftMax[i], rightMax[i]) - arr[i]);
        return res;
    }

    public int rainwaterTrapping2(int[] arr) {
        Stack s = new Stack(arr.length);
        int i = 0;
        int res = 0;
        while (i < arr.length) {
            while (!s.isEmpty() && arr[s.peek()] < arr[i]) {
                int curr = s.pop();
                if (s.isEmpty())
                    break;
                int width = i - curr;
                res += width * (Math.min(arr[s.peek()], arr[i]) - arr[curr]);

            }
            s.push(i++);
        }
        return res;
    }

    public int areaInHistogram1(int[] arr) {
        int res = 0;
        for (int i = 0; i < arr.length; i++) {
            int currHeight = arr[i];
            for (int j = i; j < arr.length; j++) {
                if (arr[j] <= arr[i])
                    currHeight = Math.min(currHeight, arr[j]);
            }

            int idx = arr.length - 1;
            for (int j = arr.length - 1; j >= i; j--) {
                if (arr[j] == currHeight) {
                    idx = j;
                    break;
                }
            }
            int width = idx - i + 1;
            res = Math.max(res, width * (currHeight));
        }
        return res;
    }

    public int areaInHistogram2(int[] arr) {
        Stack s = new Stack(arr.length);
        s.push(0);
        int i = 0;
        int res = 0;
        while (i < arr.length) {
            while (!s.isEmpty() && arr[s.peek()] > arr[i]) {
                int curr = s.pop();
                if (s.isEmpty()) {
                    res = Math.max(res, arr[curr] * i);
                    continue;
                }
                int width = i - curr + 1;
                res = Math.max(res, arr[curr] * width);
            }
            s.push(i++);
        }
        return res;
    }

    // ***** The span Si of the stock’s price on a given day i is defined as the
    // ***** maximum number of consecutive days just before the given day, for which
    // ***** the price of the stock on the current day is less than its price on the
    // ***** given day.
    public int[] stockSpan(int[] arr) {

        java.util.Stack<int[]> s = new java.util.Stack<>();

        int[] res = new int[arr.length];
        int i = 0;
        while (i < arr.length) {
            int count = 1;
            if (!s.isEmpty() && arr[s.peek()[0]] < arr[i]) {
                int[] a = s.pop();
                count += a[1];
                res[a[0]] = a[1];
            }
            s.push(new int[] { i++, count });
        }

        while (!s.isEmpty()) {
            int[] a = s.pop();
            res[a[0]] = a[1];
        }

        return res;
    }

    public boolean validStackSequence(int[] pushed, int[] popped) {

        Stack s = new Stack(pushed.length);

        int i = 0;
        int j = 0;

        while (i < pushed.length) {
            while (!s.isEmpty() && popped[j] == s.peek()) {
                s.pop();
                j++;
            }
            s.push(pushed[i++]);
        }
        while (!s.isEmpty() && popped[j] == s.peek()) {
            s.pop();
            j++;
        }
        return s.isEmpty();
    }

    public boolean pattern132(int[] arr) {

        int[] preMin = new int[arr.length];
        preMin[0] = Integer.MAX_VALUE;
        for (int i = 1; i < arr.length; i++)
            preMin[i] = Math.min(preMin[i - 1], arr[i - 1]);

        Stack s = new Stack(arr.length);

        for (int i = arr.length - 1; i >= 0; i--) {
            while (!s.isEmpty() && arr[s.peek()] <= preMin[i])
                s.pop();

            if (!s.isEmpty() && arr[s.peek()] < arr[i] && arr[s.peek()] > preMin[i])
                return true;
            s.push(i);
        }

        return false;
    }

    public int celebrityProblem(int[][] arr) {
        Stack s = new Stack(arr.length);

        for (int i = 0; i < arr.length; i++)
            s.push(i);

        while (s.size() > 1) {
            int p1 = s.pop();
            int p2 = s.pop();

            if (arr[p1][p2] == 1)
                s.push(p2);
            else
                s.push(p1);
        }
        return s.pop();
    }

    public boolean balancedParenthesis(String str) {

        Stack s = new Stack(str.length());

        for (char ch : str.toCharArray()) {
            if (ch == '(')
                s.push(0);
            else {
                if (s.isEmpty())
                    return false;
                s.pop();
            }
        }

        return s.isEmpty();
    }

    // ? Duplicate Parenthesis
    public boolean redundantParenthesis(String str) {

        Stack s = new Stack(str.length());
        for (char ch : str.toCharArray()) {
            if (ch == '(')
                s.push(0);
            else if (ch != ')')
                s.push(1);
            else {
                int count = 0;
                while (!s.isEmpty() && s.peek() != 0) {
                    s.pop();
                    count++;
                }
                if (!s.isEmpty())
                    s.pop();
                if (count <= 1)
                    return true;
            }
        }
        return !s.isEmpty();
    }

    // ***** Minimum number of bracket reversals needed to make an expression
    // ***** balanced
    public int countReversals(String str) {

        java.util.Stack<Character> s = new java.util.Stack<>();
        for (char ch : str.toCharArray()) {
            if (ch == '(')
                s.push(ch);
            else {
                if (!s.isEmpty() && s.peek() == '(')
                    s.pop();
                else
                    s.push(ch);
            }
        }

        int count1 = 0;
        int count2 = 0;
        while (!s.isEmpty()) {
            char ch = s.pop();
            if (ch == ')')
                count1++;
            else
                count2++;
        }
        return (int) Math.ceil(count1 / 2) + (int) Math.ceil(count2 / 2);
    }

    public int longestValidParenthesis(String str) {
        if (str.length() == 0)
            return 0;

        Stack s = new Stack(str.length());
        s.push(-1);

        int res = 0;
        int i = 0;
        for (char ch : str.toCharArray()) {
            if (ch == '(')
                s.push(i);
            else {
                if (!s.isEmpty() && s.peek() != -1 && str.charAt(s.peek()) == '(') {
                    s.pop();
                    res = Math.max(res, i - s.peek());
                } else
                    s.push(i);
            }
            i++;
        }

        return res;
    }

    public class Node {

        TreeNode node;
        boolean left;
        boolean right;
        boolean self;

        public Node(TreeNode node) {
            this.left = this.right = this.self = false;
            this.node = node;
        }
    }

    public ArrayList<Integer> preOrder(TreeNode node) {

        java.util.Stack<Node> s = new java.util.Stack<>();
        s.push(new Node(node));

        ArrayList<Integer> res = new ArrayList<>();

        while (!s.isEmpty()) {
            if (!s.peek().self) {
                s.peek().self = true;
                res.add(s.peek().node.value);
            } else if (!s.peek().left) {
                s.peek().left = true;
                if (s.peek().node.left != null)
                    s.push(new Node(s.peek().node.left));
            } else if (!s.peek().right) {
                s.peek().right = true;
                if (s.peek().node.right != null)
                    s.push(new Node(s.peek().node.right));
            } else {
                s.pop();
            }
        }

        return res;
    }

    public ArrayList<Integer> inOrder(TreeNode node) {

        java.util.Stack<Node> s = new java.util.Stack<>();
        s.push(new Node(node));

        ArrayList<Integer> res = new ArrayList<>();

        while (!s.isEmpty()) {
            if (!s.peek().left) {
                s.peek().left = true;
                if (s.peek().node.left != null)
                    s.push(new Node(s.peek().node.left));
            } else if (!s.peek().self) {
                s.peek().self = true;
                res.add(s.peek().node.value);
            } else if (!s.peek().right) {
                s.peek().right = true;
                if (s.peek().node.right != null)
                    s.push(new Node(s.peek().node.right));
            } else {
                s.pop();
            }
        }

        return res;
    }

    public ArrayList<Integer> postOrder(TreeNode node) {

        java.util.Stack<Node> s = new java.util.Stack<>();
        s.push(new Node(node));

        ArrayList<Integer> res = new ArrayList<>();

        while (!s.isEmpty()) {
            if (!s.peek().left) {
                s.peek().left = true;
                if (s.peek().node.left != null)
                    s.push(new Node(s.peek().node.left));
            } else if (!s.peek().right) {
                s.peek().right = true;
                if (s.peek().node.right != null)
                    s.push(new Node(s.peek().node.right));
            } else if (!s.peek().self) {
                s.peek().self = true;
                res.add(s.peek().node.value);
            } else {
                s.pop();
            }
        }

        return res;
    }

    public void towerOfHanoi1(int n, char a, char b, char c) {

        if (n == 0)
            return;

        towerOfHanoi1(n - 1, a, c, b);
        System.out.println("Move the disk from : " + a + " to " + b);
        towerOfHanoi1(n - 1, c, b, a);
    }

    public class hanoiNode {
        int n;
        char a, b, c;
        boolean aTOcWITHb, self, cTObWITHa;

        public hanoiNode(char a, char b, char c) {
            this.a = a;
            this.b = b;
            this.c = c;
            this.aTOcWITHb = this.self = this.cTObWITHa = false;
        }
    }

    public void towerOfHanoi2(int n, char a, char b, char c) {

        java.util.Stack<hanoiNode> s = new java.util.Stack<>();
        s.push(new hanoiNode(a, b, c));

        while (!s.isEmpty()) {
            hanoiNode rn = s.peek();
            if (rn.aTOcWITHb) {
                rn.aTOcWITHb = true;
                if (rn.n != 0)
                    s.push(new hanoiNode(rn.a, rn.c, rn.b));
            } else if (rn.self) {
                rn.self = true;
                System.out.println("Move the disk from : " + rn.a + " to " + rn.b);
            } else if (rn.cTObWITHa) {
                rn.cTObWITHa = true;
                if (rn.n != 0)
                    s.push(new hanoiNode(rn.c, rn.b, rn.a));
            } else {
                s.pop();
            }
        }
    }

    public static HashMap<Character, Integer> precedence = new HashMap<Character, Integer>();
    public static HashMap<Character, Boolean> associativity = new HashMap<Character, Boolean>();

    public static void builderForConverter() {
        precedence.put('^', 3);
        precedence.put('*', 2);
        precedence.put('/', 2);
        precedence.put('+', 1);
        precedence.put('-', 1);

        associativity.put('^', false);
        associativity.put('*', true);
        associativity.put('/', true);
        associativity.put('+', true);
        associativity.put('-', true);
    }

    public static String infixTOpostfix(String str) {
        builderForConverter();
        StringBuilder sb = new StringBuilder("");

        java.util.Stack<Character> s = new java.util.Stack<>();
        for (char ch : str.toCharArray()) {
            if (ch == '(')
                s.push(ch);
            else if (ch == ')') {
                while (!s.isEmpty() && s.peek() != '(')
                    sb.append(s.pop());
                s.pop();
            } else if (!precedence.keySet().contains(ch)) // *** is it any number or word
                sb.append(ch);
            else {
                if (s.isEmpty() || s.peek() == '(') {
                } else if (precedence.get(s.peek()) != precedence.get(ch)) {
                    while (!s.isEmpty() && s.peek() != '(' && precedence.get(s.peek()) >= precedence.get(ch)) {
                        sb.append(s.pop());
                    }
                } else {
                    if (associativity.get(ch))
                        sb.append(s.pop());
                }
                s.push(ch);
            }
        }

        while (!s.isEmpty())
            sb.append(s.pop());

        return sb.toString();
    }

    public static String infixTOprefix(String str) {
        builderForConverter();
        StringBuilder sb = new StringBuilder("");

        str = new StringBuilder(str).reverse().toString();

        java.util.Stack<Character> s = new java.util.Stack<>();
        for (char ch : str.toCharArray()) {
            if (ch == ')')
                s.push(ch);
            else if (ch == '(') {
                while (!s.isEmpty() && s.peek() != ')')
                    sb.append(s.pop());
                s.pop();
            } else if (!precedence.keySet().contains(ch)) // *** is it any number or word
                sb.append(ch);
            else {
                if (s.isEmpty() || s.peek() == ')') {
                } else if (precedence.get(s.peek()) != precedence.get(ch)) {
                    while (!s.isEmpty() && s.peek() != ')' && precedence.get(s.peek()) < precedence.get(ch)) {
                        sb.append(s.pop());
                    }
                } else {
                    if (associativity.get(ch))
                        sb.append(s.pop());
                }
                s.push(ch);
            }
        }

        while (!s.isEmpty())
            sb.append(s.pop());

        return sb.reverse().toString();
    }

    public String postfixTOinfix(String str) {
        builderForConverter();
        java.util.Stack<StringBuilder> s = new java.util.Stack<>();

        for (char ch : str.toCharArray()) {
            if (!precedence.keySet().contains(ch))
                s.push(new StringBuilder(ch));
            else {
                StringBuilder s1 = s.pop();
                StringBuilder s2 = s.pop();
                s.push(new StringBuilder("(" + s2 + "" + ch + "" + s1 + ")"));
            }
        }

        return s.peek().toString();
    }

    public String prefixTOInfix(String str) {
        builderForConverter();
        java.util.Stack<StringBuilder> s = new java.util.Stack<>();

        str = new StringBuilder(str).reverse().toString();

        for (char ch : str.toCharArray()) {
            if (!precedence.keySet().contains(ch))
                s.push(new StringBuilder(ch));
            else {
                StringBuilder s1 = s.pop();
                StringBuilder s2 = s.pop();
                s.push(new StringBuilder("(" + s1 + "" + ch + "" + s2 + ")"));
            }
        }

        return s.peek().toString();
    }

    public String postfixTOprefix(String str) {
        builderForConverter();
        java.util.Stack<StringBuilder> s = new java.util.Stack<>();

        for (char ch : str.toCharArray()) {
            if (!precedence.keySet().contains(ch))
                s.push(new StringBuilder(ch));
            else {
                StringBuilder s1 = s.pop();
                StringBuilder s2 = s.pop();
                s.push(new StringBuilder("(" + ch + "" + s2 + "" + s1 + ")"));
            }
        }

        return s.peek().toString();
    }

    public String prefixTOpostfix(String str) {
        builderForConverter();
        java.util.Stack<StringBuilder> s = new java.util.Stack<>();

        str = new StringBuilder(str).reverse().toString();

        for (char ch : str.toCharArray()) {
            if (!precedence.keySet().contains(ch))
                s.push(new StringBuilder(ch));
            else {
                StringBuilder s1 = s.pop();
                StringBuilder s2 = s.pop();
                s.push(new StringBuilder("(" + s2 + "" + s1 + "" + ch + ")"));
            }
        }

        return s.peek().toString();
    }

    public int arithmeticExpression(String str, int key) {
        if (key == 0)
            return postfixExpression(str);
        else if (key == 1)
            return infixExpression(str);
        else
            return prefixExpression(str);
    }

    public int operate(int a, int b, char ch) {
        int res = 0;

        if (ch == '+')
            res = a + b;
        else if (ch == '-')
            res = a - b;
        else if (ch == '*')
            res = a * b;
        else if (ch == '/')
            res = a / b;
        else
            res = (int) Math.pow(a, b);

        return res;
    }

    public int postfixExpression(String str) {

        Stack s = new Stack(str.length());
        for (char ch : str.toCharArray()) {
            if (!precedence.keySet().contains(ch))
                s.push(ch - '0');
            else {
                int s1 = s.pop();
                int s2 = s.pop();
                s.push(operate(s2, s1, ch));
            }
        }

        return s.peek();
    }

    public int infixExpression(String str) {
        str = infixTOpostfix(str);
        return postfixExpression(str);
    }

    public int prefixExpression(String str) {

        str = new StringBuilder(str).reverse().toString();

        Stack s = new Stack(str.length());
        for (char ch : str.toCharArray()) {
            if (!precedence.keySet().contains(ch))
                s.push(ch - '0');
            else {
                int s1 = s.pop();
                int s2 = s.pop();
                s.push(operate(s1, s2, ch));
            }
        }

        return s.peek();
    }

}
