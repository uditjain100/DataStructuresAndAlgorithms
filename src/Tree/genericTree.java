package Tree;

import java.util.*;

public class genericTree {

    public class TreeNode {

        int value;
        ArrayList<TreeNode> children;

        public TreeNode(int val) {
            this.value = val;
            this.children = new ArrayList<>();
        }

    }

    private TreeNode root;

    public genericTree() {
        this.root = null;
    }

    public void display() {
        display(this.root);
    }

    private void display(TreeNode node) {
        if (node == null)
            return;

        String str = "" + node.value + " : ";
        for (TreeNode child : node.children)
            str += child.value + " , ";

        System.out.println(str);
        for (TreeNode child : node.children)
            display(child);
    }

    public void constructFromPreOrder(int[] preOrder) {
        this.idx = 0;
        this.root = construct(preOrder);
    }

    int idx;

    private TreeNode construct(int[] preOrder) {
        if (idx == preOrder.length || preOrder[idx] == -1) {
            idx++;
            return null;
        }

        TreeNode node = new TreeNode(preOrder[idx++]);

        TreeNode child = construct(preOrder);
        while (child != null) {
            node.children.add(child);
            child = construct(preOrder);
        }

        return node;
    }

    public int size() {
        return size(this.root);
    }

    private int size(TreeNode node) {
        if (node == null)
            return 0;

        int currNodeSize = 0;
        for (TreeNode child : node.children)
            currNodeSize += size(child);
        return currNodeSize + 1;
    }

    public int height() {
        return height(this.root);
    }

    private int height(TreeNode node) {
        if (node == null)
            return 0;

        int currNodeHeight = 0;
        for (TreeNode child : node.children)
            currNodeHeight += Math.max(currNodeHeight, height(child));
        return currNodeHeight + 1;
    }

    public int max() {
        return max(this.root);
    }

    private int max(TreeNode node) {
        if (node == null)
            return Integer.MIN_VALUE;

        int currMax = node.value;
        for (TreeNode child : node.children)
            currMax = Math.max(currMax, max(child));
        return currMax;
    }

    public int min() {
        return min(this.root);
    }

    private int min(TreeNode node) {
        if (node == null)
            return Integer.MAX_VALUE;

        int currMin = node.value;
        for (TreeNode child : node.children)
            currMin = Math.min(currMin, min(child));
        return currMin;
    }

    public boolean find(int num) {
        return find(this.root, num);
    }

    private boolean find(TreeNode node, int num) {
        if (node == null)
            return false;

        boolean res = (node.value == num);
        for (TreeNode child : node.children)
            res = res || find(child, num);
        return res;
    }

    class DPair {

        int diameter, height;

        public DPair() {
            this.diameter = 0;
            this.height = -1;
        }

    }

    public int diameter() {
        return diameter(this.root).diameter;
    }

    public DPair diameter(TreeNode node) {
        if (node == null)
            return new DPair();

        DPair pair = new DPair();

        ArrayList<DPair> currChildDiameter = new ArrayList<>();

        int firstMaxHeight = -1;
        int secondMaxHeight = -1;
        int firstMaxDiameter = -1;
        int secondMaxDiameter = -1;
        for (TreeNode c : node.children) {
            currChildDiameter.add(diameter(c));
            DPair child = currChildDiameter.get(currChildDiameter.size() - 1);
            if (child.height > firstMaxHeight) {
                secondMaxHeight = firstMaxHeight;
                firstMaxHeight = child.height;
            } else if (child.height > secondMaxHeight)
                secondMaxHeight = child.height;
            if (child.diameter > firstMaxDiameter) {
                secondMaxDiameter = firstMaxDiameter;
                firstMaxDiameter = child.diameter;
            } else if (child.diameter > secondMaxHeight)
                secondMaxDiameter = child.diameter;
        }

        pair.height = Math.max(firstMaxHeight, secondMaxHeight) + 1;
        pair.diameter = Math.max(Math.max(firstMaxDiameter, secondMaxDiameter), firstMaxHeight + secondMaxHeight + 2);

        return pair;
    }

    public void preOrder(TreeNode node) {
        if (node == null)
            return;

        System.out.println(node.value);
        for (TreeNode child : node.children)
            preOrder(child);
    }

    public void postOrder(TreeNode node) {
        if (node == null)
            return;

        for (TreeNode child : node.children)
            postOrder(child);
        System.out.println(node.value);
    }

    public void levelOrder(TreeNode node) {
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.add(node);

        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                TreeNode rn = queue.removeFirst();
                System.out.println(rn.value);
                queue.addAll(rn.children);
            }
        }
    }

    public String serialize() {
        return serialize(this.root);
    }

    private String serialize(TreeNode node) {
        if (node == null)
            return "-1";
        String str = "" + node.value + ",";
        for (TreeNode child : node.children)
            str += serialize(child) + ",";

        return str + "-1";
    }

    public void deserialize(String str) {
        String[] arr = str.split(",");
        int[] preOrder = new int[arr.length];

        int i = 0;
        for (String s : arr)
            preOrder[i++] = Integer.parseInt(s);

        this.root = deserialize(preOrder);
    }

    private TreeNode deserialize(int[] preOrder) {
        this.idx = 0;
        return construct(preOrder);
    }

    public boolean isMirror() {
        return isMirror(this.root);
    }

    private boolean isMirror(TreeNode node) {

        if (node == null)
            return false;

        int i = 0;
        int j = node.children.size() - 1;

        while (i < j)
            if (node.children.get(i++).value != node.children.get(j--).value)
                return false;

        for (TreeNode child : node.children)
            if (!isMirror(child))
                return false;

        return true;
    }

    public ArrayList<TreeNode> rootToNodePath(int num) {
        ArrayList<TreeNode> path = new ArrayList<>();
        rootToNodePath(this.root, num, path);
        Collections.reverse(path);
        return path;
    }

    private void rootToNodePath(TreeNode node, int num, ArrayList<TreeNode> path) {
        if (node == null)
            return;

        if (node.value == num) {
            path.add(node);
            return;
        }

        for (TreeNode child : node.children) {
            rootToNodePath(child, num, path);
            if (path.size() != 0) {
                path.add(node);
                return;
            }
        }
    }

    public int lowestCommonAncestor(int a, int b) {
        this.lca = -1;
        lowestCommonAncestor(this.root, a, b);
        return lca;
    }

    int lca;

    private boolean lowestCommonAncestor(TreeNode node, int a, int b) {
        if (node == null)
            return false;

        boolean self = false;
        if (node.value == a || node.value == b)
            self = true;

        boolean anyFirstChild = false;
        boolean anySecondChild = false;
        for (TreeNode child : node.children) {
            boolean res = lowestCommonAncestor(child, a, b);
            if (res && !anyFirstChild)
                anyFirstChild = true;
            else if (res && !anySecondChild)
                anySecondChild = true;
        }

        if (((anyFirstChild && self) || (anySecondChild && self) || (anyFirstChild && anySecondChild))
                && this.lca == -1)
            this.lca = node.value;
        return anyFirstChild || self || anySecondChild;
    }

    public void flatten() {
        flatten(this.root);
    }

    private void flatten(TreeNode node) {
        if (node == null)
            return;

        ArrayList<TreeNode> allChildren = new ArrayList<>();
        for (TreeNode child : node.children) {
            flatten(child);
            allChildren.add(child);
            allChildren.addAll(child.children);
            child.children.clear();
        }
        node.children.clear();
        node.children = allChildren;
    }

    public void toLinearTree() {
        this.root = toLinearTree(this.root);
    }

    private TreeNode toLinearTree(TreeNode node) {
        if (node == null)
            return node;

        TreeNode lastNode = toLinearTree(node.children.get(node.children.size() - 1));
        for (int i = node.children.size() - 2; i > 0; i--) {
            TreeNode secondLast = toLinearTree(node.children.get(i));
            secondLast.children.add(node.children.get(i + 1));
            node.children.remove(node.children.size() - 1);
        }
        return lastNode;
    }

}
