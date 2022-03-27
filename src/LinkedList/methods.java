package LinkedList;

import java.util.*;

import LinkedList.intro_ll.LinkedList;
import Stack.intro_stack.Stack;

public class methods extends LinkedList {

    // **** When head of List is not given
    public boolean deleteNode(Node nodeToBeDeleted) {
        if (nodeToBeDeleted.next == null)
            return false;

        nodeToBeDeleted.value = nodeToBeDeleted.next.value;
        nodeToBeDeleted.next = nodeToBeDeleted.next.next;
        return true;
    }

    public Node removeAtFromLast(Node node, int k) {
        k = customSize(node) - k + 1;
        return removeAt(node, k);
    }

    public boolean cycleDetection(Node head) {
        Node slow = head;
        Node fast = head;

        while (fast != null && fast.next != null) {
            if (slow == fast)
                return true;
            slow = slow.next;
            fast = fast.next.next;
        }
        return false;
    }

    public Node cycleTailIntersection(Node head) {
        Node slow = head;
        Node fast = head;

        while (fast != null && fast.next != null) {
            if (slow == fast)
                break;
            slow = slow.next;
            fast = fast.next.next;
        }

        if (slow != fast)
            return null;

        slow = head;

        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }

        return slow;
    }

    public Node IntersectionPointOfTwoLL(Node n1, Node n2) {

        Node head = n1;
        while (head.next != null)
            head = head.next;

        while (n2.next != null)
            n2 = n2.next;

        n2.next = n1;

        return cycleTailIntersection(head);
    }

    public Node customReverse(Node head) {

        Node prev = null;
        Node curr = head;
        Node next = null;

        while (curr != null) {
            next = curr.next;
            curr.next = prev;

            prev = curr;
            curr = next;
        }

        return prev;
    }

    public int customSize(Node node) {

        Node curr = node;
        int count = 0;
        while (curr != null) {
            curr = curr.next;
            count++;
        }
        return count;
    }

    public Node customMiddle(Node node) {

        Node slow = node;
        Node fast = node;
        if (customSize(node) % 2 != 0) {
            while (fast != null && fast.next != null) {
                slow = slow.next;
                fast = fast.next.next;
            }
        } else {
            slow = null;
            while (fast != null && fast.next != null) {
                slow = (slow != null) ? slow.next : node;
                fast = fast.next.next;
            }
        }
        return slow;
    }

    public void reverseData(Node head) {

        if (head == null || head.next == null)
            return;

        Node curr = head;
        Node mid = customMiddle(head);

        Node newHead = mid.next;
        newHead = customReverse(newHead);
        mid.next = null;

        Node newCurr = newHead;
        while (curr != null && newCurr != null) {
            swap(curr, newCurr);
            curr = curr.next;
            newCurr = newCurr.next;
        }

        newHead = customReverse(newHead);
        mid.next = newHead;
    }

    public boolean isPalinDrome(Node head) {

        Node curr = head;
        Node mid = customMiddle(head);

        Node newHead = mid.next;
        newHead = customReverse(newHead);
        mid.next = null;

        Node newCurr = newHead;
        while (curr != null && newCurr != null) {
            if (curr.value != newCurr.value)
                return false;
            curr = curr.next;
            newCurr = newCurr.next;
        }

        newHead = customReverse(newHead);
        mid.next = newHead;
        return true;
    }

    public Node reverseSubList(Node head, int si, int ei) {

        int n = customSize(head);

        if (si < 1)
            si = 1;
        if (ei > n)
            ei = n;
        if (si >= ei)
            return head;
        if (si == 1 && ei == n)
            return customReverse(head);

        Node prev = (si == 1) ? null : getAt(head, si - 1);
        Node next = getAt(head, ei);

        Node sn = (prev != null) ? prev.next : head;
        Node en = next;

        next = next.next;

        if (prev != null)
            prev.next = null;

        en.next = null;

        sn = customReverse(sn);

        Node curr = sn;
        while (curr.next != null)
            curr = curr.next;
        curr.next = next;

        if (prev == null)
            return sn;
        prev.next = sn;
        return head;
    }

    public Node reverseInGroupsOfK(Node head, int k) {

        int n = customSize(head);

        int numberOfGroups = n / k;

        int si = 1;
        int ei = k;
        Node curr = head;
        for (int i = 0; i < numberOfGroups; i++) {
            curr = reverseSubList(curr, si, ei);
            si = ei + 1;
            ei = ei + k;
        }

        ei = n;

        return reverseSubList(curr, si, ei);
    }

    public Node rotateList(Node head, int k) {

        int n = customSize(head);

        if (k < 0 || k >= n) {
            k += n;
            k %= n;
        }

        if (k == 0)
            return head;

        Node curr = head;
        while (k-- > 1)
            curr = curr.next;

        Node newHead = curr.next;
        curr.next = null;

        curr = newHead;
        while (curr.next != null)
            curr = curr.next;
        curr.next = head;

        return newHead;
    }

    public Node rotateSubList(Node head, int si, int ei, int k) {

        int n = customSize(head);
        if (si < 1)
            si = 1;
        if (ei > n)
            ei = n;
        if (si >= ei)
            return head;
        if (si == 1 && ei == n)
            return rotateList(head, k);

        Node prev = (si == 1) ? null : getAt(head, si - 1);
        Node next = getAt(head, ei);

        Node sn = (prev != null) ? prev.next : head;
        Node en = next;

        next = next.next;

        if (prev != null)
            prev.next = null;

        en.next = null;

        sn = rotateList(sn, k);

        Node curr = sn;
        while (curr.next != null)
            curr = curr.next;
        curr.next = next;

        if (prev == null)
            return sn;
        prev.next = sn;
        return head;
    }

    // ? size : size of each group and k : rotating factor
    public Node rotateInGroupsOfK(Node head, int size, int k) {

        int n = customSize(head);

        int numberOfGroups = n / size;

        int si = 1;
        int ei = size;

        Node curr = head;
        for (int i = 0; i < numberOfGroups; i++) {
            curr = rotateSubList(curr, si, ei, k);
            si = ei + 1;
            ei = ei + size;
        }

        ei = n;

        return rotateSubList(curr, si, ei, k);
    }

    public Node sum(Node h1, Node h2) {

        int n1 = customSize(h1);
        int n2 = customSize(h2);

        Stack s1 = new Stack(n1);
        Stack s2 = new Stack(n2);

        Node c1 = h1;
        Node c2 = h2;
        while (c1 != null) {
            s1.push(c1.value);
            c1 = c1.next;
        }
        while (c2 != null) {
            s2.push(c2.value);
            c2 = c2.next;
        }

        Node head = new Node(-1);

        int carry = 0;
        Node curr = head;
        while (!s1.isEmpty() && !s2.isEmpty()) {
            int num = s1.pop() + s2.pop() + carry;
            carry = num / 10;
            num = num % 10;
            curr.next = new Node(num);
            curr = curr.next;
        }

        while (!s1.isEmpty()) {
            int num = s1.pop() + carry;
            carry = num / 10;
            num = num % 10;
            curr.next = new Node(num);
            curr = curr.next;
        }
        while (!s2.isEmpty()) {
            int num = s2.pop() + carry;
            carry = num / 10;
            num = num % 10;
            curr.next = new Node(num);
            curr = curr.next;
        }

        if (carry != 0)
            curr.next = new Node(carry);

        Node next = head.next;
        head.next = null;
        head = next;

        return customReverse(head);
    }

    public Node getLargerOne(Node h1, Node h2) {

        int n1 = customSize(h1);
        int n2 = customSize(h2);

        if (n1 > n2)
            return h1;
        if (n2 > n1)
            return h2;

        Node c1 = h1;
        Node c2 = h2;

        while (c1 != null) {
            if (c1.value > c2.value)
                return h1;
            else if (c2.value > c1.value)
                return h2;
            c1 = c1.next;
            c2 = c2.next;
        }
        return h1;
    }

    public Node getSmallerOne(Node h1, Node h2) {

        int n1 = customSize(h1);
        int n2 = customSize(h2);

        if (n1 > n2)
            return h2;
        if (n2 > n1)
            return h1;

        Node c1 = h1;
        Node c2 = h2;

        while (c1 != null) {
            if (c1.value > c2.value)
                return h2;
            else if (c2.value > c1.value)
                return h1;
            c1 = c1.next;
            c2 = c2.next;
        }
        return h2;
    }

    public Node subtract(Node h1, Node h2) {

        Node nh1 = h1;
        Node nh2 = h2;

        h1 = getLargerOne(nh1, nh2);
        h2 = getSmallerOne(nh1, nh2);

        int n1 = customSize(h1);
        int n2 = customSize(h2);

        Stack s1 = new Stack(n1);
        Stack s2 = new Stack(n2);

        Node c1 = h1;
        Node c2 = h2;
        while (c1 != null) {
            s1.push(c1.value);
            c1 = c1.next;
        }
        while (c2 != null) {
            s2.push(c2.value);
            c2 = c2.next;
        }

        Node head = new Node(-1);

        int carry = 0;
        Node curr = head;
        while (!s1.isEmpty() && !s2.isEmpty()) {
            int num = 0;

            int op1 = s1.pop();
            int op2 = s2.pop();

            boolean temp = false;
            if (carry != 0) {
                if (op1 == 0) {
                    op1 = 10;
                    temp = true;
                }
                op1--;
            }

            if (op1 < op2) {
                op1 += 10;
                carry = 1;
            } else if (op1 > op2) {
                if (!temp)
                    carry = 0;
            }

            num = op1 - op2;
            curr.next = new Node(num);
            curr = curr.next;
        }

        while (!s1.isEmpty()) {
            int num = 0;

            int op1 = s1.pop();

            if (carry != 0) {
                if (op1 == 0)
                    op1 = 10;
                op1--;
            }

            num = op1;
            curr.next = new Node(num);
            curr = curr.next;

        }

        Node next = head.next;
        head.next = null;
        head = next;

        head = customReverse(head);

        while (head != null && head.value == 0)
            head = head.next;

        if (head == null)
            return new Node(0);

        return head;
    }

    public int convertListToNumber(Node node) {
        int num = 0;
        Node curr = node;
        while (curr != null) {
            num *= 10;
            num += curr.value;
            curr = curr.next;
        }
        return num;
    }

    public Node convertNumberToList(int num) {

        Node head = new Node(-1);
        Node curr = head;

        while (num != 0) {
            curr.next = new Node(num % 10);
            curr = curr.next;
            num /= 10;
        }

        Node next = head.next;
        head.next = null;
        head = next;

        head = customReverse(head);
        if (head == null)
            head = new Node(0);
        return head;
    }

    public Node multiply(Node h1, Node h2) {
        int num1 = convertListToNumber(h1);
        int num2 = convertListToNumber(h2);

        return convertNumberToList(num1 * num2);
    }

    public Node division(Node h1, Node h2) {
        int num1 = convertListToNumber(h1);
        int num2 = convertListToNumber(h2);

        if (num2 == 0)
            return null;

        return convertNumberToList(num1 / num2);
    }

    public Node oddEven(Node head) {
        if (head == null || head.next == null || head.next.next == null)
            return head;

        Node c1 = head;
        Node c2 = head.next;

        Node head2 = c2;

        while (c1.next != null && c2.next != null) {
            c1.next = c1.next.next;
            c2.next = c2.next.next;

            c1 = c1.next;
            c2 = c2.next;
        }

        c1.next = head2;
        return head;
    }

    public Node swapPairwise(Node head) {

        if (head == null || head.next == null)
            return head;

        Node c1 = head;
        Node c2 = head.next;

        Node head2 = c2;

        while (c1.next != null && c2.next != null) {
            c1.next = c1.next.next;
            c2.next = c2.next.next;

            c1 = c1.next;
            c2 = c2.next;
        }

        c1.next = null;

        Node nn = new Node(-1);
        Node res = nn;
        c1 = head;
        c2 = head2;

        while (c2 != null) {

            Node n1 = c1.next;
            Node n2 = c2.next;

            nn.next = c2;
            nn = nn.next;

            nn.next = c1;
            nn = nn.next;

            c1 = n1;
            c2 = n2;
        }

        return res.next;
    }

    public Node reOrderList(Node head) {

        if (head == null || head.next == null || head.next.next == null)
            return head;

        Node curr = head;
        Node mid = customMiddle(head);

        Node newHead = mid.next;
        newHead = customReverse(newHead);
        mid.next = null;

        Node nn = new Node(-1);
        Node res = nn;

        Node newCurr = newHead;

        while (newCurr != null) {

            Node n1 = curr.next;
            Node n2 = newCurr.next;

            nn.next = curr;
            nn = nn.next;

            nn.next = newCurr;
            nn = nn.next;

            curr = n1;
            newCurr = n2;
        }

        nn.next = curr;

        return res.next;
    }

    public Node intersectionOf2SortedLists(Node h1, Node h2) {

        if (h1 == null)
            return h2;
        if (h2 == null)
            return h1;

        Node nn = new Node(-1);
        Node res = nn;

        Node c1 = h1;
        Node c2 = h2;
        while (c1 != null && c2 != null) {
            if (c1.value < c2.value) {
                c1 = c1.next;
            } else if (c1.value > c2.value) {
                c2 = c2.next;
            } else {
                nn.next = new Node(c2.value);
                c1 = c1.next;
                c2 = c2.next;
                nn = nn.next;
            }
        }

        return res.next;
    }

    public Node unionOf2SortedLists(Node h1, Node h2) {

        if (h1 == null)
            return h2;
        if (h2 == null)
            return h1;

        Node nn = new Node(-1);
        Node res = nn;

        Node c1 = h1;
        Node c2 = h2;
        while (c1 != null && c2 != null) {
            if (c1.value < c2.value) {
                nn.next = new Node(c1.value);
                c1 = c1.next;
            } else if (c1.value > c2.value) {
                nn.next = new Node(c2.value);
                c2 = c2.next;
            } else {
                nn.next = new Node(c2.value);
                c1 = c1.next;
                c2 = c2.next;
            }
            nn = nn.next;
        }

        if (c1 != null)
            nn.next = c1;
        if (c2 != null)
            nn.next = c2;

        return res.next;
    }

    public Node merge2SortedLists(Node h1, Node h2) {

        if (h1 == null)
            return h2;
        if (h2 == null)
            return h1;

        Node nn = new Node(-1);
        Node res = nn;

        Node c1 = h1;
        Node c2 = h2;
        while (c1 != null && c2 != null) {
            if (c1.value < c2.value) {
                nn.next = new Node(c1.value);
                c1 = c1.next;
            } else if (c1.value > c2.value) {
                nn.next = new Node(c2.value);
                c2 = c2.next;
            } else {
                nn.next = new Node(c1.value);
                nn = nn.next;
                nn.next = new Node(c2.value);
                c1 = c1.next;
                c2 = c2.next;
            }
            nn = nn.next;
        }

        if (c1 != null)
            nn.next = c1;
        if (c2 != null)
            nn.next = c2;

        return res.next;
    }

    public Node mergeKSortedLists(ArrayList<Node> lists) {
        return merge(lists, 0, lists.size() - 1);
    }

    private Node merge(ArrayList<Node> lists, int si, int ei) {
        if (si == ei)
            return lists.get(si);

        int mid = (si + ei) / 2;

        Node left = merge(lists, si, mid);
        Node right = merge(lists, mid + 1, ei);

        return merge2SortedLists(left, right);
    }

    public Node mergeSort(Node head) {
        if (head == null || head.next == null)
            return head;

        Node mid = customMiddle(head);
        Node newHead = mid.next;
        mid.next = null;

        Node left = mergeSort(head);
        Node right = mergeSort(newHead);

        return merge2SortedLists(left, right);
    }

    public Node DNFSort(Node head) {

        if (head == null || head.next == null)
            return head;

        Node n0 = new Node(-1);
        Node n1 = new Node(-1);
        Node n2 = new Node(-1);

        Node res1 = n0;
        Node res2 = n1;
        Node res3 = n2;
        Node curr = head;

        while (curr != null) {
            Node next = curr.next;
            if (curr.value == 0) {
                n0.next = curr;
                n0 = n0.next;
            } else if (curr.value == 1) {
                n1.next = curr;
                n1 = n1.next;
            } else {
                n2.next = curr;
                n2 = n2.next;
            }
            curr.next = null;
            curr = next;
        }

        res1 = res1.next;
        res2 = res2.next;
        res3 = res3.next;

        n0.next = res2;
        n1.next = res3;

        return res1;
    }

    public Node[] partition(Node head, int idx) {

        int n = customSize(head);

        if (idx >= n || idx < 0 || head == null || head.next == null)
            return new Node[] { head, head, head };

        Node pivot = head;
        while (idx-- > 0)
            pivot = pivot.next;

        Node c1 = new Node(-1);
        Node c2 = new Node(-1);

        Node h1 = c1;
        Node h2 = c2;

        Node curr = head;
        while (curr != null) {
            if (curr != pivot) {
                if (curr.value <= pivot.value) {
                    c1.next = curr;
                    c1 = c1.next;
                } else {
                    c2.next = curr;
                    c2 = c2.next;
                }
            }
            curr = curr.next;
        }

        c1.next = null;
        c2.next = null;
        pivot.next = null;

        h1 = h1.next;
        h2 = h2.next;

        return new Node[] { h1, pivot, h2 };
    }

    public Node[] merge(Node[] l, Node m, Node[] r) {

        if (l[0] == null && r[0] == null)
            return new Node[] { m, m };
        else if (l[0] == null) {
            m.next = r[0];
            return new Node[] { m, r[1] };
        } else if (r[0] == null) {
            l[1].next = m;
            return new Node[] { l[0], m };
        } else {
            l[1].next = m;
            m.next = r[0];
            return new Node[] { l[0], r[1] };
        }

    }

    public Node[] quickSort(Node head) {
        if (head == null || head.next == null)
            return new Node[] { head, head };

        int n = customSize(head);
        int pivotIdx = n / 2;

        Node[] partitionedList = partition(head, pivotIdx);

        Node[] left = quickSort(partitionedList[0]);
        Node[] right = quickSort(partitionedList[2]);

        Node pivot = partitionedList[1];

        return merge(left, pivot, right);
    }

    public Node removeDuplicatesFromSortedLists(Node head) {

        if (head == null || head.next == null)
            return head;

        Node curr = head;
        while (curr.next != null) {
            if (curr.value == curr.next.value) {
                curr.next = curr.next.next;
            } else {
                curr = curr.next;
            }
        }
        return head;
    }

    public Node removeDuplicatesFromUnsortedLists(Node head) {

        if (head == null || head.next == null)
            return head;

        head = mergeSort(head);

        Node curr = head;
        while (curr.next != null) {
            if (curr.value == curr.next.value) {
                curr.next = curr.next.next;
            } else {
                curr = curr.next;
            }
        }
        return head;
    }

    public Node clone(Node head) {
        if (head == null)
            return null;

        Node newHead = new Node(-1);

        Node curr = head;
        Node newCurr = newHead;
        while (curr != null) {
            newCurr.next = new Node(curr.value);
            curr = curr.next;
            newCurr = newCurr.next;
        }

        return newHead.next;
    }

    public ArrayList<Node[]> twoSumPairsFromOneLinkedList(Node head, int sum) {
        if (head == null || head.next == null)
            return new ArrayList<>();

        ArrayList<Node[]> res = new ArrayList<>();

        head = mergeSort(head);

        Node newHead = clone(head);

        HashMap<Node, Node> map = new HashMap<>();

        Node curr = head;
        Node newCurr = newHead;
        while (curr != null) {
            map.put(newCurr, curr);
            newCurr = newCurr.next;
            curr = curr.next;
        }

        newHead = customReverse(newHead);

        curr = head;
        newCurr = newHead;

        while (curr != null && newCurr != null) {
            if (sum > curr.value + newCurr.value)
                newCurr = newCurr.next;
            else if (sum < curr.value + newCurr.value)
                curr = curr.next;
            else {
                res.add(new Node[] { curr, map.get(newCurr) });
                newCurr = newCurr.next;
                curr = curr.next;
            }
        }

        return res;
    }

    public ArrayList<Node[]> twoSumPairsFromOneLinkedList(Node head, ArrayList<Node> nodeToBeIgnored, int sum) {
        if (head == null || head.next == null)
            return new ArrayList<>();

        ArrayList<Node[]> res = new ArrayList<>();

        head = mergeSort(head);

        Node newHead = clone(head);

        HashMap<Node, Node> map = new HashMap<>();

        Node curr = head;
        Node newCurr = newHead;
        while (curr != null) {
            map.put(newCurr, curr);
            newCurr = newCurr.next;
            curr = curr.next;
        }

        newHead = customReverse(newHead);

        curr = head;
        newCurr = newHead;

        while (curr != null && newCurr != null) {
            while (curr != null && nodeToBeIgnored.contains(curr))
                curr = curr.next;
            while (newCurr != null && nodeToBeIgnored.contains(map.get(newCurr)))
                newCurr = newCurr.next;
            if (curr == null || newCurr == null)
                break;
            if (sum > curr.value + newCurr.value)
                newCurr = newCurr.next;
            else if (sum < curr.value + newCurr.value)
                curr = curr.next;
            else {
                res.add(new Node[] { curr, map.get(newCurr) });
                return res;
            }
        }

        return res;
    }

    public int countTwoSumPairsFromTwoLinkedList(Node head1, Node head2, int sum) {
        if (head1 == null || head2 == null)
            return 0;

        head1 = mergeSort(head1);
        head2 = mergeSort(head2);

        head2 = customReverse(head2);

        Node curr = head1;
        Node newCurr = head2;

        int count = 0;
        while (curr != null && newCurr != null) {
            if (sum > curr.value + newCurr.value)
                newCurr = newCurr.next;
            else if (sum < curr.value + newCurr.value)
                curr = curr.next;
            else {
                count++;
                newCurr = newCurr.next;
                curr = curr.next;
            }
        }

        return count;
    }

    public Node[] twoSumPairFromTwoLinkedList(Node head1, Node head2, int sum) {
        if (head1 == null || head2 == null)
            return new Node[] { null, null };

        head1 = mergeSort(head1);
        head2 = mergeSort(head2);

        head2 = customReverse(head2);

        Node curr = head1;
        Node newCurr = head2;

        while (curr != null && newCurr != null) {
            if (sum > curr.value + newCurr.value)
                newCurr = newCurr.next;
            else if (sum < curr.value + newCurr.value)
                curr = curr.next;
            else
                return new Node[] { curr, newCurr };
        }

        return new Node[] { null, null };
    }

    public int countTripletsFromOneLinkedList(Node head, int sum) {
        if (head == null || head.next == null)
            return 0;

        ArrayList<Node> nodesToBeIgnored = new ArrayList<>();

        int count = 0;

        Node curr = head;
        while (curr != null) {
            if (!nodesToBeIgnored.contains(curr)) {
                ArrayList<Node[]> list = twoSumPairsFromOneLinkedList(head, nodesToBeIgnored, sum - curr.value);
                if (list.size() != 0) {
                    nodesToBeIgnored.add(curr);
                    nodesToBeIgnored.add(list.get(0)[0]);
                    nodesToBeIgnored.add(list.get(0)[1]);
                    count++;
                }
            }
            curr = curr.next;
        }

        return count;
    }

    // **** for 3 Linked Lists we will reverse the third Linked List only.
    public Node[] countTripletsFromThreeLinkedList(Node head1, Node head2, Node head3, int sum) {
        if (head1 == null || head2 == null || head3 == null)
            return new Node[] { null, null, null };

        Node curr = head1;
        while (curr != null) {
            Node[] node = twoSumPairFromTwoLinkedList(head2, head3, sum - curr.value);
            if (node[0] != null)
                return new Node[] { curr, node[0], node[1] };
            curr = curr.next;
        }

        return new Node[] { null, null, null };
    }

    class Pair {
        int value;
        Node first;
        Node second;

        public Pair(int val) {
            this.first = this.second = null;
            this.value = val;
        }

        public Pair(int val, Node f, Node s) {
            this.first = f;
            this.second = s;
            this.value = val;
        }
    }

    public ArrayList<Node[]> quadruplets(Node head, int sum) {

        if (head == null || head.next == null || head.next.next == null)
            return new ArrayList<>();

        ArrayList<Node[]> res = new ArrayList<>();

        HashSet<Pair> set = new HashSet<>();

        Node curr = head;
        while (curr.next != null) {
            Node temp = curr.next;
            while (temp != null) {
                set.add(new Pair(curr.value + temp.value, curr, temp));
                temp = temp.next;
            }
            curr = curr.next;
        }

        ArrayList<Node> nodesToBeIgnored = new ArrayList<>();

        for (Pair p : set) {
            if (!nodesToBeIgnored.contains(p.first) && !nodesToBeIgnored.contains(p.second)) {
                ArrayList<Node[]> list = twoSumPairsFromOneLinkedList(head, nodesToBeIgnored, sum - p.value);
                if (list.size() != 0) {
                    res.add(new Node[] { p.first, p.second, list.get(0)[0], list.get(0)[1] });
                    nodesToBeIgnored.add(p.first);
                    nodesToBeIgnored.add(p.second);
                    nodesToBeIgnored.add(list.get(0)[0]);
                    nodesToBeIgnored.add(list.get(0)[1]);
                }
            }
        }

        return res;
    }

    public Node deleteNodeHavingGreaterValueOnLeftSide(Node head) {
        if (head == null || head.next == null)
            return head;

        Node curr = head;
        int max = curr.value;
        while (curr != null) {
            if (curr.next.value < max) {
                curr.next = curr.next.next;
            } else {
                max = Math.max(max, curr.next.value);
                curr = curr.next;
            }
        }
        return head;
    }

    public Node deleteNodeHavingGreaterValueOnRightSide1(Node head) {
        if (head == null || head.next == null)
            return head;

        head = customReverse(head);
        head = deleteNodeHavingGreaterValueOnLeftSide(head);
        head = customReverse(head);

        return head;
    }

    // *** Traverse the list from the start and delete the node when the current
    // *** Node < next Node
    public Node deleteNodeHavingGreaterValueOnRightSide2(Node head) {
        if (head == null || head.next == null)
            return head;

        Node next = deleteNodeHavingGreaterValueOnLeftSide(head.next);

        if (head.value < next.value)
            return next;

        head.next = next;
        return head;
    }

    public void swap(Node n1, Node n2) {
        int temp = n1.value;
        n1.value = n2.value;
        n2.value = temp;
    }

}
