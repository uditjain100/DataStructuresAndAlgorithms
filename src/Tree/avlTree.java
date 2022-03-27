package Tree;

public class avlTree {

    public class TreeNode {
        int value;
        TreeNode left;
        TreeNode right;
        int height;

        public TreeNode(int value) {
            this.value = value;
            this.height = 1;
            this.left = this.right = null;
        }
    }

    private TreeNode root;

    public avlTree() {
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

    private int maxElement(TreeNode node) {
        TreeNode curr = node;
        while (curr.right != null)
            curr = curr.right;
        return curr.value;
    }

    private int bal_factor(TreeNode node) {
        return (node == null) ? 0 : node.left.height - node.right.height;
    }

    public void construct(int[] arr) {
        for (int ele : arr)
            this.root = add(this.root, ele);
    }

    public void add(int ele) {
        this.root = add(this.root, ele);
    }

    private TreeNode add(TreeNode node, int ele) {
        if (node == null)
            return new TreeNode(ele);

        if (ele < node.value)
            node.left = add(node.left, ele);
        else
            node.right = add(node.right, ele);

        node.height = Math.max(node.left.height, node.right.height) + 1;

        return balance(node, ele);
    }

    public void delete(int ele) {
        delete(this.root, ele);
    }

    private TreeNode delete(TreeNode node, int ele) {
        if (node == null)
            return node;

        if (ele < node.value)
            node.left = delete(node.left, ele);
        else if (ele > node.value)
            node.right = delete(node.right, ele);
        else {
            if (node.left == null || node.right == null)
                node = (node.left == null) ? node.right : node.left;
            else {
                int max = maxElement(node.left);
                node.value = max;
                node.left = delete(node.left, max);
            }
        }

        node.height = Math.max(node.left.height, node.right.height) + 1;

        return balance(node, ele);
    }

    private TreeNode balance(TreeNode node, int ele) {
        if (node == null)
            return node;
        int bf = bal_factor(node);

        // ** LL
        if (bf > 1 && ele < node.left.value)
            return rightRotate(node);
        // ** RR
        if (bf < -1 && ele > node.right.value)
            return leftRotate(node);
        // ** LR
        if (bf > 1 && ele > node.left.value) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        // ** RL
        if (bf < -1 && ele < node.right.value) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    private TreeNode rightRotate(TreeNode node) {
        TreeNode b = node.left;
        TreeNode T3 = b.right;

        b.right = node;
        node.left = T3;

        node.height = Math.max(node.left.height, node.right.height) + 1;
        b.height = Math.max(b.left.height, b.right.height) + 1;
        return b;
    }

    private TreeNode leftRotate(TreeNode node) {
        TreeNode b = node.right;
        TreeNode T3 = b.left;

        b.left = node;
        node.right = T3;

        node.height = Math.max(node.left.height, node.right.height) + 1;
        b.height = Math.max(b.left.height, b.right.height) + 1;
        return b;
    }

}
