package org.example;

public class BinomialHeap {
    private BinomialNode head;

    public BinomialHeap() {
        head = null;
    }

    public void insert(int key, int[] ops) {
        BinomialHeap tempHeap = new BinomialHeap();
        tempHeap.head = new BinomialNode(key);
        ops[1]++;
        union(tempHeap, ops);
    }

    public void union(BinomialHeap other, int[] ops) {
        this.head = merge(this.head, other.head, ops);
        if (head == null) return;

        BinomialNode prev = null;
        BinomialNode curr = head;
        BinomialNode next = curr.sibling;

        while (next != null) {
            ops[0]++;
            if (curr.degree != next.degree ||
                    (next.sibling != null && next.sibling.degree == curr.degree)) {
                ops[0]++;
                prev = curr;
                curr = next;
                ops[1]++;
            } else {
                ops[0]++;
                if (curr.key <= next.key) {
                    curr.sibling = next.sibling;
                    link(next, curr, ops);
                    ops[1]++;
                } else {
                    if (prev == null) {
                        head = next;
                    } else {
                        prev.sibling = next;
                        ops[1]++;
                    }
                    link(curr, next, ops);
                    curr = next;
                }
            }
            next = curr.sibling;
        }
    }

    private BinomialNode merge(BinomialNode h1, BinomialNode h2, int[] ops) {
        if (h1 == null) return h2;
        if (h2 == null) return h1;

        BinomialNode head;
        BinomialNode tail;

        ops[0]++;
        if (h1.degree <= h2.degree) {
            head = h1;
            h1 = h1.sibling;
        } else {
            head = h2;
            h2 = h2.sibling;
        }
        tail = head;

        while (h1 != null && h2 != null) {
            ops[0]++;
            if (h1.degree <= h2.degree) {
                tail.sibling = h1;
                h1 = h1.sibling;
                ops[1]++;
            } else {
                tail.sibling = h2;
                h2 = h2.sibling;
                ops[1]++;
            }
            tail = tail.sibling;
        }

        tail.sibling = (h1 != null) ? h1 : h2;
        if (tail.sibling != null) ops[1]++;
        return head;
    }

    private void link(BinomialNode child, BinomialNode parent, int[] ops) {
        child.parent = parent;
        child.sibling = parent.child;
        parent.child = child;
        parent.degree++;
        ops[1] += 3;
    }

    public BinomialNode search(int key, int[] ops) {
        return search(head, key, ops);
    }

    private BinomialNode search(BinomialNode node, int key, int[] ops) {
        if (node == null) return null;
        ops[0]++;
        if (node.key == key) return node;

        BinomialNode found = search(node.child, key, ops);
        if (found != null) return found;

        return search(node.sibling, key, ops);
    }

    public void delete(int key, int[] ops) {
        BinomialNode node = search(key, ops);
        if (node != null) {
            decreaseKey(node, Integer.MIN_VALUE, ops);
            extractMin(ops);
        }
    }

    public void decreaseKey(BinomialNode node, int newKey, int[] ops) {
        if (node == null || newKey > node.key) return;
        node.key = newKey;
        ops[1]++;
        bubbleUp(node, ops);
    }

    private void bubbleUp(BinomialNode node, int[] ops) {
        BinomialNode parent = node.parent;
        while (parent != null && node.key < parent.key) {
            ops[0]++;
            swapKeys(node, parent, ops);
            node = parent;
            parent = node.parent;
            ops[1]++;
        }
        if (parent != null) ops[0]++;
    }

    private void swapKeys(BinomialNode a, BinomialNode b, int[] ops) {
        int temp = a.key;
        a.key = b.key;
        b.key = temp;
        ops[1] += 3;
    }

    public int extractMin(int[] ops) {
        if (head == null) return -1;

        BinomialNode prevMin = null;
        BinomialNode minNode = head;
        BinomialNode prev = null;
        BinomialNode curr = head;

        while (curr != null) {
            ops[0]++;
            if (curr.key < minNode.key) {
                minNode = curr;
                prevMin = prev;
            }
            prev = curr;
            curr = curr.sibling;
            ops[1]++;
        }

        if (prevMin != null) {
            prevMin.sibling = minNode.sibling;
            ops[1]++;
        } else {
            head = minNode.sibling;
            ops[1]++;
        }

        BinomialHeap newHeap = new BinomialHeap();
        BinomialNode child = minNode.child;
        while (child != null) {
            BinomialNode next = child.sibling;
            child.sibling = newHeap.head;
            child.parent = null;
            newHeap.head = child;
            child = next;
            ops[1] += 3;
        }

        union(newHeap, ops);
        return minNode.key;
    }
}