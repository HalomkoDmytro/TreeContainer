package com.globallogic.test.tree.impl;

/**
 *  Extension to SimpleTreeImpl. Define max branch depth.
 */
public class SimpleTreeLimitedBranchDepth<T extends Comparable>  extends SimpleTreeImpl<T> {

    private int maxDepth;

    public SimpleTreeLimitedBranchDepth(int maxDepth) {
        if (maxDepth < 1) throw new IllegalArgumentException("Depth can't be less then 1");
        this.maxDepth = maxDepth;
    }

    public int getMaxDepth() {
        return maxDepth;
    }

    @Override
    public boolean add(T element) {
        if (size() > 0) {
            if (countDepth(getRoot(), 0, element) > maxDepth)
                throw new IllegalArgumentException(String.format("Reach max depth. Max allowed depth for branches is %d", maxDepth));
        }
        return super.add(element);
    }

    private int countDepth(Node<T> node, int count, T element) {
        if (node == null) return count;
        if (node.element.compareTo(element) == 0) return count;

        if (node.element.compareTo(element) > 1) {
            return count + countDepth(node.leftChild, ++count, element);
        } else {
            return count + countDepth(node.rightChild, ++count, element);
        }
    }
}
