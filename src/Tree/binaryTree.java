package Tree;

import java.util.*;

public class binaryTree {

    public class TreeNode {

        public int value;
        public TreeNode left, right;

        public TreeNode(int val) {
            this.value = val;
        }

    }

    TreeNode root;

    public binaryTree() {
        this.root = null;
    }

    public void display() {
        display(this.root);
        System.out.println();
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

    public void constructFromPreAndIn(int[] pre, int[] in) {
        this.root = constructFromPreAndIn(pre, in, 0, pre.length - 1, 0, in.length - 1);
    }

    private TreeNode constructFromPreAndIn(int[] pre, int[] in, int psi, int pei, int isi, int iei) {
        if (isi > iei || psi > pei)
            return null;

        int idx = isi;
        while (in[idx] != pre[psi])
            idx++;

        int numberOfElements = idx - isi;

        TreeNode nn = new TreeNode(pre[psi]);
        nn.left = constructFromPreAndIn(pre, in, psi + 1, psi + numberOfElements, isi, idx - 1);
        nn.right = constructFromPreAndIn(pre, in, psi + numberOfElements + 1, pei, idx + 1, iei);

        return nn;
    }

    public void constructFromPostAndIn(int[] post, int[] in) {
        this.root = constructFromPostAndIn(post, in, 0, post.length - 1, 0, in.length - 1);
    }

    private TreeNode constructFromPostAndIn(int[] post, int[] in, int psi, int pei, int isi, int iei) {
        if (isi > iei || psi > pei)
            return null;

        int idx = isi;
        while (in[idx] != post[pei])
            idx++;

        int numberOfElements = idx - isi;

        TreeNode nn = new TreeNode(post[pei]);
        nn.left = constructFromPostAndIn(post, in, psi, psi + numberOfElements - 1, isi, idx - 1);
        nn.right = constructFromPostAndIn(post, in, psi + numberOfElements, pei - 1, idx + 1, iei);

        return nn;
    }

    public void constructFromPostAndPre(int[] post, int[] pre) {
        this.root = constructFromPostAndPre(post, pre, 0, post.length - 1, 0, pre.length - 1);
    }

    private TreeNode constructFromPostAndPre(int[] post, int[] pre, int prsi, int prei, int posi, int poei) {
        if (poei < posi || prsi > prei)
            return null;
        if (poei == posi || prsi == prei)
            return new TreeNode(post[poei]);

        int idx = posi;
        while (post[idx] != pre[prsi + 1])
            idx++;

        int numberOfElements = idx - posi + 1;

        TreeNode nn = new TreeNode(post[poei]);
        nn.left = constructFromPostAndPre(post, pre, prsi + 1, prsi + numberOfElements, posi, idx);
        nn.right = constructFromPostAndPre(post, pre, prsi + numberOfElements + 1, prei, idx + 1, poei - 1);

        return nn;
    }

    public void constructFromParent(int[] parent) {

        int n = parent.length;

        TreeNode[] nodes = new TreeNode[n];
        for (int i = 0; i < n; i++)
            nodes[i] = new TreeNode(i);

        for (int i = 0; i < n; i++) {
            if (parent[i] == -1) {
                this.root = nodes[i];
            } else {
                if (nodes[parent[i]].left == null)
                    nodes[parent[i]].left = nodes[i];
                else
                    nodes[parent[i]].right = nodes[i];
            }
        }
    }

    public class Pair {

        int count;
        int[] arr;
        int idx;

        public Pair(int c, int[] a, int i) {
            this.count = c;
            this.arr = a;
            this.idx = i;
        }

    }

    public void constructFromAncestorMatrix(int[][] arr) {

        int n = arr.length;

        PriorityQueue<Pair> pq = new PriorityQueue<>((a, b) -> {
            return a.count - b.count;
        });

        for (int i = 0; i < n; i++) {
            int count = 0;
            for (int j = 0; j < n; j++)
                if (arr[i][j] == 1)
                    count++;
            pq.add(new Pair(count, arr[i], i));
        }

        TreeNode[] parent = new TreeNode[n];
        TreeNode[] nodes = new TreeNode[n];

        for (int i = 0; i < n; i++)
            nodes[i] = new TreeNode(i);

        while (!pq.isEmpty()) {
            Pair rp = pq.remove();
            if (rp.count != 0) {
                for (int j = 0; j < n; j++) {
                    if (rp.idx != j && arr[rp.idx][j] == 1 && parent[j] == null) {
                        parent[j] = nodes[rp.idx];
                        if (parent[j].left == null)
                            parent[j].left = nodes[j];
                        else
                            parent[j].right = nodes[j];
                    }
                }
            }
            if (pq.isEmpty())
                this.root = nodes[rp.idx];
        }

    }

    public void constructFromBrackets(String str) {
        Stack<TreeNode> s = new Stack<>();

        String num = "";
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (ch != '(' && ch != ')') {
                num += ch;
            } else {
                if (num.length() != 0)
                    s.push(new TreeNode(Integer.parseInt(num)));
                num = "";
                if (ch == ')') {
                    TreeNode node = s.pop();
                    if (s.peek().left == null)
                        s.peek().left = node;
                    else
                        s.peek().right = node;
                }
            }
        }
        this.root = s.pop();
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

    public int max(TreeNode node) {
        if (node == null)
            return Integer.MIN_VALUE;

        return Math.max(Math.max(node.left.value, node.right.value), node.value);
    }

    public int min() {
        return min(this.root);
    }

    public int min(TreeNode node) {
        if (node == null)
            return Integer.MAX_VALUE;

        return Math.min(Math.min(node.left.value, node.right.value), node.value);
    }

    public boolean find(int target) {
        return find(this.root, target);
    }

    public boolean find(TreeNode node, int target) {
        if (node == null)
            return false;

        return find(node.left, target) || find(node.right, target);
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

    public ArrayList<Integer> preOrder() {
        ArrayList<Integer> list = new ArrayList<>();
        preOrder(this.root, list);
        return list;
    }

    private void preOrder(TreeNode node, ArrayList<Integer> list) {

        if (node == null)
            return;

        list.add(node.value);
        preOrder(node.left, list);
        preOrder(node.right, list);
    }

    public ArrayList<Integer> inOrder() {
        ArrayList<Integer> list = new ArrayList<>();
        inOrder(this.root, list);
        return list;
    }

    private void inOrder(TreeNode node, ArrayList<Integer> list) {

        if (node == null)
            return;

        inOrder(node.left, list);
        list.add(node.value);
        inOrder(node.right, list);
    }

    public ArrayList<Integer> postOrder() {
        ArrayList<Integer> list = new ArrayList<>();
        postOrder(this.root, list);
        return list;
    }

    private void postOrder(TreeNode node, ArrayList<Integer> list) {

        if (node == null)
            return;

        postOrder(node.left, list);
        postOrder(node.right, list);
        list.add(node.value);
    }

    public TreeNode rightMostOfLeft(TreeNode node, TreeNode curr) {
        while (node.right != null && node.right != curr)
            node = node.right;
        return node;
    }

    public void morrisPreOrder() {

        TreeNode curr = this.root;
        while (curr != null) {
            if (curr.left == null) {
                System.out.print(curr.value + ", ");
                curr = curr.right;
            } else {
                TreeNode rightMost = rightMostOfLeft(curr.left, curr);
                if (rightMost.right == null) {
                    rightMost.right = curr;
                    System.out.print(curr.value + ", ");
                    curr = curr.left;
                } else {
                    rightMost.right = null;
                    curr = curr.right;
                }
            }
        }
        System.out.println();
    }

    public void morrisInOrder() {

        TreeNode curr = this.root;
        while (curr != null) {
            if (curr.left == null) {
                System.out.print(curr.value + ", ");
                curr = curr.right;
            } else {
                TreeNode rightMost = rightMostOfLeft(curr.left, curr);
                if (rightMost.right == null) {
                    rightMost.right = curr;
                    curr = curr.left;
                } else {
                    rightMost.right = null;
                    System.out.print(curr.value + ", ");
                    curr = curr.right;
                }
            }
        }
        System.out.println();
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

    public int width() {
        leftMostIdx = 0;
        rightMostIdx = 0;

        width(this.root, 0);

        return rightMostIdx - leftMostIdx + 1;
    }

    int leftMostIdx, rightMostIdx;

    private void width(TreeNode node, int level) {
        if (node == null)
            return;

        leftMostIdx = Math.min(leftMostIdx, level);
        rightMostIdx = Math.max(rightMostIdx, level);

        width(node.left, level - 1);
        width(node.right, level + 1);
    }

    public void leftView() {

        int height = height();

        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.addLast(this.root);

        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i <= height; i++)
            list.add(-1);

        int level = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                TreeNode rn = queue.removeFirst();
                if (list.get(level) == -1)
                    list.set(level, rn.value);
                if (rn.left != null)
                    queue.addLast(rn.left);
                if (rn.right != null)
                    queue.addLast(rn.right);
            }
            level++;
        }
        System.out.println(list);
    }

    public void rightView() {

        int height = height();

        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.addLast(this.root);

        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i <= height; i++)
            list.add(-1);

        int level = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                TreeNode rn = queue.removeFirst();
                list.set(level, rn.value);
                if (rn.left != null)
                    queue.addLast(rn.left);
                if (rn.right != null)
                    queue.addLast(rn.right);
            }
            level++;
        }
        System.out.println(list);
    }

    public class ViewPair {

        int level;
        TreeNode node;

        public ViewPair(int l, TreeNode n) {
            this.level = l;
            this.node = n;
        }

    }

    public ArrayList<Integer> topView() {

        int w = width();

        LinkedList<ViewPair> queue = new LinkedList<>();
        queue.addLast(new ViewPair(-leftMostIdx, this.root));

        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < w; i++)
            list.add(-1);

        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                ViewPair rn = queue.removeFirst();
                if (list.get(rn.level) == -1)
                    list.set(rn.level, rn.node.value);
                if (rn.node.left != null)
                    queue.addLast(new ViewPair(rn.level - 1, rn.node.left));
                if (rn.node.right != null)
                    queue.addLast(new ViewPair(rn.level + 1, rn.node.right));
            }
        }
        System.out.println(list);
        return list;
    }

    public ArrayList<Integer> bottomView() {

        int w = width();

        LinkedList<ViewPair> queue = new LinkedList<>();
        queue.addLast(new ViewPair(-leftMostIdx, this.root));

        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < w; i++)
            list.add(-1);

        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                ViewPair rn = queue.removeFirst();
                list.set(rn.level, rn.node.value);
                if (rn.node.left != null)
                    queue.addLast(new ViewPair(rn.level - 1, rn.node.left));
                if (rn.node.right != null)
                    queue.addLast(new ViewPair(rn.level + 1, rn.node.right));
            }
        }
        System.out.println(list);
        return list;
    }

    public ArrayList<Integer> boundaryView() {

        ArrayList<Integer> list = new ArrayList<>();

        list.addAll(topView());

        ArrayList<Integer> bottomView = bottomView();

        for (int i = bottomView.size() - 2; i >= 1; i--)
            list.add(bottomView.get(i));
        System.out.println(list);
        return list;
    }

    public void diagonalOrder() {
        width();

        LinkedList<ViewPair> queue = new LinkedList<>();
        queue.addLast(new ViewPair(0, this.root));

        ArrayList<ArrayList<Integer>> list = new ArrayList<>();
        for (int i = 0; i <= -leftMostIdx; i++)
            list.add(new ArrayList<>());

        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                ViewPair rn = queue.removeFirst();
                list.get(rn.level).add(rn.node.value);
                if (rn.node.left != null)
                    queue.addLast(new ViewPair(rn.level + 1, rn.node.left));
                if (rn.node.right != null)
                    queue.addLast(new ViewPair(rn.level, rn.node.right));
            }
        }
        for (ArrayList<Integer> l : list)
            System.out.println(l);
    }

    public void antiDiagonalOrder() {
        width();

        LinkedList<ViewPair> queue = new LinkedList<>();
        queue.addLast(new ViewPair(0, this.root));

        ArrayList<ArrayList<Integer>> list = new ArrayList<>();
        for (int i = 0; i <= rightMostIdx; i++)
            list.add(new ArrayList<>());

        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                ViewPair rn = queue.removeFirst();
                list.get(rn.level).add(rn.node.value);
                if (rn.node.left != null)
                    queue.addLast(new ViewPair(rn.level, rn.node.left));
                if (rn.node.right != null)
                    queue.addLast(new ViewPair(rn.level + 1, rn.node.right));
            }
        }
        for (ArrayList<Integer> l : list)
            System.out.println(l);
    }

    public ArrayList<TreeNode> rootToNodePath(int target) {
        ArrayList<TreeNode> path = new ArrayList<>();
        rootToNodePath(this.root, target, path);
        Collections.reverse(path);
        return path;
    }

    private void rootToNodePath(TreeNode node, int target, ArrayList<TreeNode> path) {
        if (node == null)
            return;

        if (node.value == target) {
            path.add(node);
            return;
        }

        rootToNodePath(node.left, target, path);
        if (path.size() != 0) {
            path.add(node);
            return;
        }
        rootToNodePath(node.right, target, path);
        if (path.size() != 0) {
            path.add(node);
            return;
        }
    }

    // *** Using Space
    public int lowestCommonAncestor1(int a, int b) {
        ArrayList<TreeNode> pathOfa = rootToNodePath(0);
        ArrayList<TreeNode> pathOfb = rootToNodePath(b);

        int res = -1;
        for (int i = 0; i < pathOfa.size() && i < pathOfb.size(); i++)
            if (pathOfa.get(i) == pathOfb.get(i))
                res = pathOfa.get(i).value;

        return res;
    }

    public int lca;

    public int lowestCommonAncestor2(int a, int b) {
        lca = -1;
        lowestCommonAncestor2(this.root, a, b);
        System.out.println(lca);
        return lca;
    }

    private boolean lowestCommonAncestor2(TreeNode node, int a, int b) {
        if (node == null)
            return false;

        boolean selfDone = false;
        if (node.value == a || node.value == b)
            selfDone = true;

        boolean leftDone = lowestCommonAncestor2(node.left, a, b);
        boolean rightDone = lowestCommonAncestor2(node.right, a, b);

        if (((leftDone && rightDone) || (leftDone && selfDone) || (rightDone && selfDone)) && lca == -1)
            lca = node.value;

        return leftDone || rightDone || selfDone;
    }

    public ArrayList<TreeNode> kDistanceChildren(TreeNode node, int k) {
        ArrayList<TreeNode> list = new ArrayList<>();
        kDistanceChildren(node, k, list);
        return list;
    }

    private void kDistanceChildren(TreeNode node, int k, ArrayList<TreeNode> list) {
        if (node == null)
            return;
        if (k == 0) {
            list.add(node);
            return;
        }

        kDistanceChildren(node.left, k - 1, list);
        kDistanceChildren(node.right, k - 1, list);
    }

    public ArrayList<TreeNode> kDistanceChildrenWithBlockNode(TreeNode node, TreeNode blockNode, int k) {
        ArrayList<TreeNode> list = new ArrayList<>();
        kDistanceChildrenWithBlockNode(node, blockNode, k, list);
        return list;
    }

    private void kDistanceChildrenWithBlockNode(TreeNode node, TreeNode blockNode, int k, ArrayList<TreeNode> list) {
        if (node == null || node == blockNode)
            return;
        if (k == 0) {
            list.add(node);
            return;
        }

        kDistanceChildrenWithBlockNode(node.left, blockNode, k - 1, list);
        kDistanceChildrenWithBlockNode(node.right, blockNode, k - 1, list);
    }

    // ? Using Space
    public ArrayList<TreeNode> kDistanceNodes1(TreeNode node, int k) {
        ArrayList<TreeNode> path = rootToNodePath(node.value);

        ArrayList<TreeNode> list = new ArrayList<>();
        for (int i = path.size() - 1; i >= 0; i--) {
            if (k - (path.size() - i - 1) >= 0) {
                if (i == path.size() - 1)
                    list.addAll(
                            kDistanceChildrenWithBlockNode(path.get(i), null, k - (path.size() - i - 1)));
                else
                    list.addAll(
                            kDistanceChildrenWithBlockNode(path.get(i), path.get(i + 1), k - (path.size() - i - 1)));
            }
        }
        for (TreeNode n : list)
            System.out.print(n.value + ", ");
        System.out.println();
        return list;
    }

    public ArrayList<TreeNode> kDistanceNodes2(int target, int k) {
        ArrayList<TreeNode> list = new ArrayList<>();
        kDistanceNodes2(this.root, target, k, list);
        return list;
    }

    private int kDistanceNodes2(TreeNode node, int target, int k, ArrayList<TreeNode> list) {

        if (node == null)
            return -1;

        if (node.value == target) {
            list.addAll(kDistanceChildren(node, k));
            return 1;
        }

        int leftDistance = kDistanceNodes2(node.left, target, k, list);
        if (leftDistance != -1) {
            list.addAll(kDistanceChildrenWithBlockNode(node, node.left, k - leftDistance));
            return leftDistance + 1;
        }
        int rightDistance = kDistanceNodes2(node.right, target, k, list);
        if (rightDistance != -1) {
            list.addAll(kDistanceChildrenWithBlockNode(node, node.left, k - rightDistance));
            return rightDistance + 1;
        }
        return -1;
    }

    public int maxPathSum() {
        return maxPathSum(this.root);
    }

    private int maxPathSum(TreeNode node) {
        if (node == null)
            return Integer.MIN_VALUE;

        int leftMax = maxPathSum(node.left);
        int rightMax = maxPathSum(node.left);

        int a = leftMax + node.value;
        int b = rightMax + node.value;
        int c = node.value;
        int d = leftMax + node.value + rightMax;

        return Math.max(Math.max(a, b), Math.max(c, d));
    }

    public TreeNode getMirror() {
        return mirror(this.root);
    }

    private TreeNode mirror(TreeNode node) {
        if (node == null)
            return null;

        TreeNode nn = new TreeNode(node.value);

        nn.left = mirror(node.right);
        nn.right = mirror(node.left);

        return nn;
    }

    public boolean isMirror(TreeNode node1, TreeNode node2) {
        if (node1 == null && node2 == null)
            return true;
        if (node1 == null || node2 == null)
            return false;
        if (node1.value != node2.value)
            return false;

        return isMirror(node1.left, node2.right) && isMirror(node1.right, node2.left);
    }

    public boolean isFoldable() {
        return isMirror(this.root, this.root);
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

    public String serialize() {
        return serialize(this.root);
    }

    private String serialize(TreeNode node) {
        if (node == null)
            return "-1";

        String left = serialize(node.left);
        String right = serialize(node.right);

        return "" + node.value + "," + left + "," + right;
    }

    public TreeNode deserialize(String str) {

        String[] arr = str.split(",");
        int[] preOrder = new int[arr.length];

        int i = 0;
        for (String s : arr)
            preOrder[i++] = Integer.parseInt(s);

        this.idx = 0;
        return deserialize(preOrder);
    }

    public int idx;

    private TreeNode deserialize(int[] preOrder) {

        if (idx == preOrder.length)
            return null;

        if (preOrder[idx] == -1) {
            idx++;
            return null;
        }

        TreeNode nn = new TreeNode(preOrder[idx++]);

        nn.left = deserialize(preOrder);
        nn.right = deserialize(preOrder);
        return nn;
    }

    public boolean isDuplicateSubTreeExist() {
        duplicatePresent = false;
        isDuplicateSubTreeExist(this.root, new HashSet<>());
        return duplicatePresent;
    }

    boolean duplicatePresent;

    private String isDuplicateSubTreeExist(TreeNode node, HashSet<String> set) {
        if (node == null)
            return "-1";

        String left = isDuplicateSubTreeExist(node.left, set);
        String right = isDuplicateSubTreeExist(node.right, set);

        String str = "" + node.value + left + right;
        if (set.contains(str))
            duplicatePresent = true;
        else
            set.add(str);
        return str;
    }

    public ArrayList<TreeNode> allDuplicates() {
        ArrayList<TreeNode> res = new ArrayList<>();

        HashMap<String, Integer> map = new HashMap<>();
        allDuplicates(this.root, map);

        for (String str : map.keySet())
            if (map.get(str) != 1)
                res.add(deserialize(str));

        return res;
    }

    private String allDuplicates(TreeNode node, HashMap<String, Integer> map) {
        if (node == null)
            return "-1";

        String left = allDuplicates(node.left, map);
        String right = allDuplicates(node.right, map);

        String str = "" + node.value + "," + left + "," + right;

        map.putIfAbsent(str, 0);
        map.put(str, map.get(str) + 1);

        return str;
    }

    public void treeToSumTree() {
        treeToSumTree(this.root);
    }

    private int treeToSumTree(TreeNode node) {
        if (node == null)
            return 0;

        int value = node.value;
        node.value = treeToSumTree(node.left) + treeToSumTree(node.right);
        return value + node.value;
    }

    public boolean isSumTree() {
        return isSumTree(this.root)[1] == 1;
    }

    private int[] isSumTree(TreeNode node) {
        if (node == null)
            return new int[] { 0, 1 };
        if (node.left == null && node.right == null)
            return new int[] { node.value, 1 };

        int[] left = isSumTree(node.left);
        int[] right = isSumTree(node.right);

        if (left[1] == 0 || right[1] == 0 || node.value != left[0] + right[0])
            return new int[] { 0, 0 };

        return new int[] { node.value + left[0] + right[0], 1 };
    }

    public boolean isIsomorphic(TreeNode node1, TreeNode node2) {
        if (node1 == null && node2 == null)
            return true;
        if (node2 == null || node1 == null)
            return false;
        if (node1.value != node2.value)
            return false;

        return (isIsomorphic(node1.left, node2.left) && isIsomorphic(node1.right, node2.right))
                || (isIsomorphic(node1.left, node2.right) && isIsomorphic(node1.right, node2.left));
    }

    class Packet {
        int in;
        int ex;

        public Packet() {
            this.in = this.ex = 0;
        }

        public Packet(int in, int ex) {
            this.in = in;
            this.ex = ex;
        }
    }

    public int houseRobber3(TreeNode node) {
        Packet p = houseRobber(node);
        return Math.max(p.in, p.ex);
    }

    private Packet houseRobber(TreeNode node) {
        if (node == null)
            return new Packet();

        Packet left = houseRobber(node.left);
        Packet right = houseRobber(node.right);

        int in = left.ex + right.ex + node.value;
        int ex = Math.max(left.ex, left.in) + Math.max(right.ex, right.in);

        return new Packet(in, ex);
    }

    public ArrayList<Integer> levelOrderToInOrder(int[] levelOrder) {
        ArrayList<Integer> list = new ArrayList<>();
        levelOrderToInOrder(levelOrder, 0, list);
        return list;
    }

    private void levelOrderToInOrder(int[] levelOrder, int idx, ArrayList<Integer> list) {
        if (idx >= levelOrder.length)
            return;

        levelOrderToInOrder(levelOrder, idx * 2 + 1, list);
        list.add(levelOrder[idx]);
        levelOrderToInOrder(levelOrder, idx * 2 + 2, list);
    }

    public int minSwapsFromBinaryToBST(int[] levelOrder) {

        ArrayList<Integer> inOrder = levelOrderToInOrder(levelOrder);

        System.out.println(inOrder);

        int[][] arr = new int[inOrder.size()][2];

        for (int i = 0; i < inOrder.size(); i++) {
            arr[i][0] = inOrder.get(i);
            arr[i][1] = i;
        }

        Arrays.sort(arr, (a, b) -> a[0] - b[0]);

        int count = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i][1] != i) {
                swap(arr, i, arr[i][1]);
                count++;
                i--;
            }
        }

        return count;
    }

    private void swap(int[][] arr, int i, int j) {
        int temp = arr[i][0];
        arr[i][0] = arr[j][0];
        arr[j][0] = temp;
        temp = arr[i][1];
        arr[i][1] = arr[j][1];
        arr[j][1] = temp;
    }

    public int distanceBetweenTwoNodes(int a, int b) {
        distance = -1;
        int[] res = distanceBetweenTwoNodes(this.root, a, b);
        return res[0];
    }

    public int distance;

    public int[] distanceBetweenTwoNodes(TreeNode node, int a, int b) {
        if (node == null)
            return new int[] { -1, 0 };

        boolean selfDone = false;
        if (node.value == a || node.value == b)
            selfDone = true;

        int[] left = distanceBetweenTwoNodes(node.left, a, b);
        int[] right = distanceBetweenTwoNodes(node.right, a, b);

        if (distance != -1)
            return new int[] { distance, 1 };

        if ((left[1] == 1 && right[1] == 1) || (selfDone && right[1] == 1)
                || (left[1] == 1 && selfDone) && distance != -1) {
            distance = left[0] + right[0];
            return new int[] { distance, 1 };
        }

        if (selfDone)
            return new int[] { 1, 1 };
        if (left[1] == 1)
            return new int[] { left[0] + 1, 1 };
        if (right[1] == 1)
            return new int[] { right[0] + 1, 1 };
        return new int[] { -1, 0 };
    }

    public ArrayList<ArrayList<Integer>> kSumPathFromRoot(int k) {
        ans = new ArrayList<>();
        kSumPathFromRoot(this.root, k, 0, new ArrayList<>());
        return ans;
    }

    ArrayList<ArrayList<Integer>> ans;

    private void kSumPathFromRoot(TreeNode node, int k, int sum, ArrayList<Integer> list) {
        if (node == null)
            return;

        if (k == sum) {
            ArrayList<Integer> res = new ArrayList<>();
            Collections.copy(list, res);
            ans.add(res);
        }

        list.add(node.value);

        kSumPathFromRoot(node.left, k, sum + node.value, list);
        kSumPathFromRoot(node.right, k, sum + node.value, list);
    }

    // **** https://www.geeksforgeeks.org/print-k-sum-paths-binary-tree/ */
    public ArrayList<ArrayList<Integer>> kSumPaths(int k) {
        ans = new ArrayList<>();
        kSumPath(this.root, k);
        return ans;
    }

    private void kSumPath(TreeNode node, int k) {
        if (node == null)
            return;

        kSumPathFromRoot(node, k, 0, new ArrayList<>());
        kSumPath(node.left, k);
        kSumPath(node.right, k);
    }

    public ArrayList<ArrayList<Integer>> kSumPaths2(int k) {
        preOrder = new ArrayList<>();
        ans = new ArrayList<>();
        kSumPath2(this.root, k);
        return ans;
    }

    ArrayList<Integer> preOrder;

    private void kSumPath2(TreeNode node, int k) {
        if (node == null)
            return;

        preOrder.add(node.value);

        kSumPath2(node.left, k);
        kSumPath2(node.right, k);

        int sum = 0;
        ArrayList<Integer> path = new ArrayList<>();
        for (int j = preOrder.size() - 1; j >= 0; j--) {
            sum += preOrder.get(j);
            path.add(preOrder.get(j));
            if (sum == k) {
                ArrayList<Integer> res = new ArrayList<>();
                Collections.copy(path, res);
                ans.add(res);
            }
        }
        preOrder.remove(preOrder.size() - 1);
    }

}
