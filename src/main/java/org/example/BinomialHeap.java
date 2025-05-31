package org.example;

class BinomialHeap {
    private BinomialNode head;
    private int operationCount;

    public int getOperationCount() {
        return operationCount;
    }

    public void resetOperationCount() {
        operationCount = 0;
    }

    public void insert(int key) {
        BinomialHeap temp = new BinomialHeap();
        temp.head = new BinomialNode(key);
        this.head = union(this, temp);
    }

    public int getMinimum() {
        BinomialNode x = head;
        int min = Integer.MAX_VALUE;
        while (x != null) {
            operationCount++;
            if (x.key < min) min = x.key;
            x = x.sibling;
        }
        return min;
    }

    public int extractMinimum() {
        BinomialNode prevMin = null, minNode = head, prev = null, curr = head;
        int min = curr.key;
        while (curr != null) {
            operationCount++;
            if (curr.key < min) {
                min = curr.key;
                prevMin = prev;
                minNode = curr;
            }
            prev = curr;
            curr = curr.sibling;
        }
        if (minNode == head) head = minNode.sibling;
        else prevMin.sibling = minNode.sibling;

        BinomialNode child = minNode.child;
        BinomialHeap temp = new BinomialHeap();
        BinomialNode rev = null;
        while (child != null) {
            BinomialNode next = child.sibling;
            child.sibling = rev;
            child.parent = null;
            rev = child;
            child = next;
        }
        temp.head = rev;
        this.head = union(this, temp);
        return minNode.key;
    }

    private BinomialNode union(BinomialHeap h1, BinomialHeap h2) {
        BinomialNode newHead = merge(h1.head, h2.head);
        if (newHead == null) return null;

        BinomialNode prev = null, curr = newHead, next = curr.sibling;

        while (next != null) {
            operationCount++;
            if (curr.degree != next.degree || (next.sibling != null && next.sibling.degree == curr.degree)) {
                prev = curr;
                curr = next;
            } else if (curr.key <= next.key) {
                curr.sibling = next.sibling;
                link(next, curr);
            } else {
                if (prev == null) newHead = next;
                else prev.sibling = next;
                link(curr, next);
                curr = next;
            }
            next = curr.sibling;
        }
        return newHead;
    }

    private BinomialNode merge(BinomialNode h1, BinomialNode h2) {
        if (h1 == null) return h2;
        if (h2 == null) return h1;

        BinomialNode head;
        if (h1.degree <= h2.degree) {
            head = h1;
            h1 = h1.sibling;
        } else {
            head = h2;
            h2 = h2.sibling;
        }

        BinomialNode current = head;
        while (h1 != null && h2 != null) {
            if (h1.degree <= h2.degree) {
                current.sibling = h1;
                h1 = h1.sibling;
            } else {
                current.sibling = h2;
                h2 = h2.sibling;
            }
            current = current.sibling;
        }
        current.sibling = (h1 != null) ? h1 : h2;
        return head;
    }

    private void link(BinomialNode y, BinomialNode z) {
        y.parent = z;
        y.sibling = z.child;
        z.child = y;
        z.degree++;
    }
}