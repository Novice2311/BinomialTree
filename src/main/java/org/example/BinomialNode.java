package org.example;

class BinomialNode {
    int key;
    int degree;
    BinomialNode parent;
    BinomialNode child;
    BinomialNode sibling;

    BinomialNode(int key) {
        this.key = key;
        this.degree = 0;
        this.parent = null;
        this.child = null;
        this.sibling = null;
    }
}
