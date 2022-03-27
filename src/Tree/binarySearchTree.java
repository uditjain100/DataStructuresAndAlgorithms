package Tree;

import java.util.*;

public class binarySearchTree {

    public class TreeNode {

        public int value;
        public TreeNode left, right;
        public int idx;

        public TreeNode(int val) {
            this.value = val;
            this.left = this.right = null;
            this.idx = -1;
        }

        public TreeNode(int val, int i) {
            this.value = val;
            this.left = this.right = null;
            this.idx = i;
        }

    }

    TreeNode root;

    public binarySearchTree() {
        this.root = null;
    }

    public void display() {
        display(this.root);
    }

    private void display(TreeNode node) {
        if (node == null)
            return;

        String ans = "" + node.value;

        ans += (node.left != null) ? " -> " + node.left.value : " -> $";
        ans += (node.right != null) ? " , " + node.right.value : " , $";

        System.out.println(ans);

        display(node.left);
        display(node.right);
    }

    public void constructFromInOrder(int[] inOrder) {
        this.root = constructFromInOrder(inOrder, 0, inOrder.length);
    }

    private TreeNode constructFromInOrder(int[] inOrder, int si, int ei) {
        if (si > ei)
            return null;
        if (si == ei)
            return new TreeNode(inOrder[si]);

        int mid = (si + ei) / 2;
        TreeNode nn = new TreeNode(inOrder[si]);

        nn.left = constructFromInOrder(inOrder, si, mid - 1);
        nn.right = constructFromInOrder(inOrder, mid + 1, ei);

        return nn;
    }

    // ? Similarly Height can be calculated from preorder
    public void constructFromPreOrder(int[] preOrder) {
        this.idx = 0;
        this.root = constructFromPreOrder(preOrder, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public int idx;

    private TreeNode constructFromPreOrder(int[] preOrder, int lb, int ub) {
        if (idx == preOrder.length || preOrder[idx] < lb || preOrder[idx] > ub)
            return null;

        int ele = preOrder[idx];

        TreeNode nn = new TreeNode(ele);
        idx++;
        nn.left = constructFromPreOrder(preOrder, lb, ele);
        nn.right = constructFromPreOrder(preOrder, ele, ub);
        return nn;
    }

    public void constructFromPostOrder(int[] postOrder) {
        this.idx = postOrder.length - 1;
        this.root = constructFromPostOrder(postOrder, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private TreeNode constructFromPostOrder(int[] postOrder, int lb, int ub) {
        if (idx == -1 || postOrder[idx] < lb || postOrder[idx] > ub)
            return null;

        int ele = postOrder[idx];

        TreeNode nn = new TreeNode(ele);
        idx--;
        nn.right = constructFromPostOrder(postOrder, ele, ub);
        nn.left = constructFromPostOrder(postOrder, lb, ele);
        return nn;
    }

    public class Pair {
        TreeNode node;
        int lb;
        int ub;

        public Pair() {
            this.node = null;
            this.lb = Integer.MIN_VALUE;
            this.ub = Integer.MAX_VALUE;
        }

        public Pair(TreeNode node) {
            this.node = node;
            this.lb = Integer.MIN_VALUE;
            this.ub = Integer.MAX_VALUE;
        }

        public Pair(TreeNode node, int l, int u) {
            this.node = node;
            this.lb = l;
            this.ub = u;
        }
    }

    public void constructFromLevelOrder(int[] levelOrder) {
        this.root = new TreeNode(levelOrder[0]);

        LinkedList<Pair> queue = new LinkedList<>();
        queue.addLast(new Pair(this.root));

        int idx = 1;
        while (!queue.isEmpty()) {
            Pair rp = queue.removeFirst();

            if (idx >= levelOrder.length)
                break;
            if (levelOrder[idx] > rp.lb && levelOrder[idx] < rp.node.value) {
                TreeNode nn = new TreeNode(levelOrder[idx++]);
                rp.node.left = nn;
                queue.addLast(new Pair(nn, rp.lb, rp.node.value));
            }
            if (idx >= levelOrder.length)
                break;
            if (levelOrder[idx] > rp.node.value && levelOrder[idx] < rp.ub) {
                TreeNode nn = new TreeNode(levelOrder[idx++]);
                rp.node.right = nn;
                queue.addLast(new Pair(nn, rp.node.value, rp.ub));
            }
        }
    }

    public void add(int num) {
        this.root = add(this.root, num);
    }

    private TreeNode add(TreeNode node, int num) {
        if (node == null)
            return new TreeNode(num);

        if (node.value > num)
            node.left = add(node.left, num);
        else
            node.right = add(node.right, num);

        return node;
    }

    public void remove(int num) {
        this.root = remove(this.root, num);
    }

    public TreeNode customMax(TreeNode node) {
        if (node == null)
            return node;
        if (node.right == null)
            return node;
        return customMax(node.right);
    }

    public void swap(TreeNode a, TreeNode b) {
        int temp = a.value;
        a.value = b.value;
        b.value = temp;
    }

    private TreeNode remove(TreeNode node, int num) {
        if (node == null)
            return node;

        if (node.value > num)
            node.left = remove(node.left, num);
        else if (node.value < num)
            node.right = remove(node.right, num);
        else {
            if (node.left == null || node.right == null)
                return (node.left == null) ? node.right : node.left;
            TreeNode max = customMax(node.left);
            node.value = max.value;
            node.left = remove(node.left, max.value);
        }
        return node;
    }

    public int size() {
        return size(this.root);
    }

    private int size(TreeNode node) {
        if (node == null)
            return 0;

        return size(node.left) + size(node.right) + 1;
    }

    public int height() {
        return height(this.root);
    }

    private int height(TreeNode node) {
        if (node == null)
            return -1;

        return Math.max(height(node.left), height(node.right)) + 1;
    }

    public int max() {
        return max(this.root);
    }

    private int max(TreeNode node) {
        if (node == null)
            return -1;
        if (node.right == null)
            return node.value;

        return max(node.right);
    }

    public int min() {
        return min(this.root);
    }

    private int min(TreeNode node) {
        if (node == null)
            return -1;
        if (node.left == null)
            return node.value;

        return max(node.left);
    }

    public boolean find(int target) {
        return find(this.root, target);
    }

    private boolean find(TreeNode node, int target) {

        if (node == null)
            return false;

        if (node.value == target)
            return true;

        return (node.value > target) ? find(node.left, target) : find(node.right, target);
    }

    public int diameter() {
        return diameter(this.root).diameter;
    }

    public class DiaPair {

        int height;
        int diameter;

        public DiaPair(int h, int d) {
            this.height = h;
            this.diameter = d;
        }

    }

    private DiaPair diameter(TreeNode node) {
        if (node == null)
            return new DiaPair(-1, 0);

        DiaPair left = diameter(node.left);
        DiaPair right = diameter(node.right);

        DiaPair ndp = new DiaPair(-1, 0);

        ndp.height = Math.max(left.height, right.height) + 1;
        ndp.diameter = Math.max(Math.max(left.diameter, right.diameter), left.height + right.height + 2);

        return ndp;
    }

    public ArrayList<TreeNode> preOrder() {
        ArrayList<TreeNode> list = new ArrayList<>();
        preOrder(this.root, list);
        return list;
    }

    private void preOrder(TreeNode node, ArrayList<TreeNode> list) {

        if (node == null)
            return;

        list.add(node);
        preOrder(node.left, list);
        preOrder(node.right, list);
    }

    public ArrayList<TreeNode> inOrder() {
        ArrayList<TreeNode> list = new ArrayList<>();
        inOrder(this.root, list);
        return list;
    }

    private void inOrder(TreeNode node, ArrayList<TreeNode> list) {

        if (node == null)
            return;

        inOrder(node.left, list);
        list.add(node);
        inOrder(node.right, list);
    }

    public ArrayList<TreeNode> postOrder() {
        ArrayList<TreeNode> list = new ArrayList<>();
        postOrder(this.root, list);
        return list;
    }

    private void postOrder(TreeNode node, ArrayList<TreeNode> list) {

        if (node == null)
            return;

        postOrder(node.left, list);
        postOrder(node.right, list);
        list.add(node);
    }

    public void levelOrder() {

        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.addLast(this.root);

        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                TreeNode rn = queue.removeFirst();
                System.out.println(rn.value);
                if (rn.left != null)
                    queue.addLast(rn.left);
                if (rn.right != null)
                    queue.addLast(rn.right);
            }
        }
        System.out.println();
    }

    public ArrayList<Integer> rootToNodePath(int num) {
        ArrayList<Integer> path = new ArrayList<>();
        rootToNodePath(this.root, num, path);
        return path;
    }

    public void rootToNodePath(TreeNode node, int num, ArrayList<Integer> path) {
        if (node == null)
            return;

        path.add(node.value);
        if (node.value > num)
            rootToNodePath(node.left, num, path);
        else if (node.value < num)
            rootToNodePath(node.right, num, path);
        return;
    }

    public int lowestCommonAncestor(int a, int b) {
        return lowestCommonAncestor(this.root, a, b);
    }

    private int lowestCommonAncestor(TreeNode node, int a, int b) {
        if (node == null)
            return -1;

        int ans = -1;
        if (node.value < a && node.value < b)
            ans = lowestCommonAncestor(node.right, a, b);
        else if (node.value > a && node.value > b)
            ans = lowestCommonAncestor(node.left, a, b);
        else
            ans = (find(a) && find(b)) ? node.value : -1;
        return ans;
    }

    public boolean isBST(TreeNode node) {
        return isBST(node, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private boolean isBST(TreeNode node, int lb, int ub) {
        if (node == null)
            return true;

        if (node.value < lb || node.value > ub)
            return false;

        return isBST(node.left, lb, node.value) || isBST(node.right, node.value, ub);
    }

    // *** With Space
    public int largestBST1(TreeNode node) {
        ArrayList<TreeNode> list = new ArrayList<>();
        inOrder(this.root, list);

        int res = 1;
        int count = 1;
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i).value < list.get(i + 1).value)
                count++;
            else {
                res = Math.max(res, count);
                count = 1;
            }
        }
        res = Math.max(res, count);
        return res;
    }

    public int largestBST2() {
        return largestBST2(this.root).largestBSTsize;
    }

    class Node {
        int min, max;
        boolean isBst;
        int size;
        int largestBSTsize;

        public Node() {
            this.min = Integer.MAX_VALUE;
            this.max = Integer.MIN_VALUE;
            this.isBst = true;
            this.largestBSTsize = this.size = 0;
        }

        public Node(int min, int max, boolean isBST, int size, int bstSize) {
            this.min = min;
            this.max = max;
            this.isBst = isBST;
            this.largestBSTsize = bstSize;
            this.size = size;
        }
    }

    private Node largestBST2(TreeNode node) {
        if (node == null)
            return new Node();

        Node left = largestBST2(node.left);
        Node right = largestBST2(node.right);

        int min = Math.min(node.value, Math.min(left.min, right.min));
        int max = Math.max(node.value, Math.max(left.max, right.max));
        int size = left.size + right.size + 1;

        if (left.isBst && right.isBst) {
            if (left.max < node.value && right.min > node.value)
                return new Node(min, max, true, size, size);
            else
                return new Node(min, max, false, size, Math.max(left.size, right.size));
        }

        return new Node(min, max, false, size, Math.max(left.largestBSTsize, right.largestBSTsize));
    }

    public void customInOrder(TreeNode node, ArrayList<TreeNode> list) {
        if (node == null)
            return;

        customInOrder(node.left, list);
        node.value = list.get(idx++).value;
        customInOrder(node.right, list);
    }

    public TreeNode binaryToBST(TreeNode node) {
        ArrayList<TreeNode> list = new ArrayList<>();
        inOrder(this.root, list);

        Collections.sort(list, (a, b) -> a.value - b.value);

        this.idx = 0;
        customInOrder(node, list);
        return node;
    }

    public TreeNode rightMostOfLeft(TreeNode node) {
        if (node == null)
            return null;
        while (node.right != null)
            node = node.right;
        return node;
    }

    public TreeNode leftMostOfRight(TreeNode node) {
        if (node == null)
            return null;
        while (node.left != null)
            node = node.left;
        return node;
    }

    public int predecessor(int num) {
        return predecessor(this.root, num);
    }

    private int predecessor(TreeNode node, int num) {
        if (node == null)
            return -1;

        if (node.value == num)
            return rightMostOfLeft(node.left).value;

        int leftAns = predecessor(node.left, num);
        if (leftAns != -1) {
            if (node.value < num)
                return Math.max(node.value, leftAns);
            return leftAns;
        }

        int rightAns = predecessor(node.right, num);
        if (rightAns != -1) {
            if (node.value < num)
                return Math.max(node.value, rightAns);
            return rightAns;
        }
        return -1;
    }

    public int successor(int num) {
        return successor(this.root, num);
    }

    private int successor(TreeNode node, int num) {
        if (node == null)
            return -1;

        if (node.value == num)
            return leftMostOfRight(node.right).value;

        int leftAns = successor(node.left, num);
        if (leftAns != -1) {
            if (node.value > num)
                return Math.max(node.value, leftAns);
            return leftAns;
        }

        int rightAns = successor(node.right, num);
        if (rightAns != -1) {
            if (node.value > num)
                return Math.max(node.value, rightAns);
            return rightAns;
        }
        return -1;
    }

    public ArrayList<TreeNode> mergeTwoInOrder(ArrayList<TreeNode> in1, ArrayList<TreeNode> in2) {
        ArrayList<TreeNode> list = new ArrayList<>();

        int i = 0;
        int j = 0;
        while (i < in1.size() && j < in2.size()) {
            if (in1.get(i).value < in2.get(j).value) {
                list.add(in1.get(i++));
            } else if (in1.get(i).value > in2.get(j).value) {
                list.add(in2.get(j++));
            } else {
                list.add(in1.get(i++));
                list.add(in2.get(j++));
            }
        }
        while (i < in1.size())
            list.add(in1.get(i++));
        while (j < in2.size())
            list.add(in2.get(i++));

        return list;
    }

    public void mergerTwoBST(TreeNode node1, TreeNode node2) {
        ArrayList<TreeNode> in1 = new ArrayList<>();
        inOrder(node1, in1);
        ArrayList<TreeNode> in2 = new ArrayList<>();
        inOrder(node2, in2);

        ArrayList<TreeNode> inOrder = mergeTwoInOrder(in1, in2);

        int[] arr = new int[inOrder.size()];
        for (int i = 0; i < inOrder.size(); i++)
            arr[i] = inOrder.get(i).value;

        constructFromInOrder(arr); // ? Takes O(n) time
    }

    // ? Similarly for KthSmallest
    public int kthLargest(int k) {
        PriorityQueue<Integer> min = new PriorityQueue<>();
        kthLargest(this.root, k, min);
        return min.peek();
    }

    private void kthLargest(TreeNode node, int k, PriorityQueue<Integer> min) {

        if (node == null)
            return;

        kthLargest(node.left, k, min);
        min.offer(node.value);
        if (min.size() > k)
            min.remove();
        kthLargest(node.right, k, min);
    }

    public ArrayList<Integer> rangeIn(int a, int b) {
        ArrayList<Integer> list = new ArrayList<>();
        rangeIn(this.root, a, b, list);
        return list;
    }

    private void rangeIn(TreeNode node, int a, int b, ArrayList<Integer> list) {
        if (node == null)
            return;

        rangeIn(node.left, a, b, list);
        if (node.value <= a && node.value >= b)
            list.add(node.value);
        rangeIn(node.right, a, b, list);
    }

    public int[] leastGreaterElementOnItsRight(int[] arr) {
        this.root = null;
        int i = 0;
        for (int ele : arr)
            this.root = customAdd(this.root, ele, i++);

        ArrayList<TreeNode> list = new ArrayList<>();
        inOrder(this.root, list);

        int[] res = new int[arr.length];
        Arrays.fill(res, -1);

        for (i = 1; i < list.size(); i++)
            res[list.get(i - 1).idx] = list.get(i).value;

        return res;
    }

    private TreeNode customAdd(TreeNode node, int num, int i) {
        if (node == null)
            return new TreeNode(num, i);

        if (node.value > num)
            node.left = customAdd(node.left, num, i);
        else
            node.right = customAdd(node.right, num, i);

        return node;
    }

    public TreeNode prev, first, second, third;

    public void recoverBST(TreeNode node) {
        this.prev = this.first = this.second = this.third = null;
        customTraversal(this.root);
        if (third == null) // *** Adjacent Case
            swap(this.first, this.second);
        else // *** Non Adjacent Case
            swap(this.second, this.third);
    }

    public void customTraversal(TreeNode node) {
        if (node == null)
            return;
        customTraversal(node.left);

        if (prev != null && node.value < prev.value) {
            if (first == null) {
                first = prev;
                second = node;
            } else
                third = node;
        }
        prev = node;

        customTraversal(node.right);
    }

    public TreeNode DLLprev;
    public TreeNode DLLhead;

    public void TreeToDLL() {
        DLLprev = DLLhead = null;
        DLL(this.root);
        this.root = DLLhead;
    }

    private void DLL(TreeNode node) {
        if (node == null)
            return;

        DLL(node.left);

        if (DLLhead == null)
            DLLhead = node;
        else {
            DLLprev.right = node;
            node.left = DLLprev;
        }
        DLLprev = node;

        DLL(node.right);
    }

}
