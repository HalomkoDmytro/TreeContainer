package com.globallogic.test.tree.impl;

import com.globallogic.test.tree.Exceptions.SizeOverflow;

import java.util.Objects;

/**
 * Extension to SimpleTreeImpl. Allowing to define max node quantity in Tree.
 */
public class SimpleTreeLimitedSizeImpl<T extends Comparable> extends SimpleTreeImpl<T> {

    private int maxSize;

    public SimpleTreeLimitedSizeImpl(int maxSize) {
        if (maxSize < 0) throw new IllegalArgumentException("Size can't be less then 0");
        this.maxSize = maxSize;
    }

    @Override
    public boolean add(T element) {
        Objects.requireNonNull(element);

        if (size() >= maxSize)
            throw new SizeOverflow(String.format("Reach max tree size. Tree limited by size=%d elements.", maxSize));

        return super.add(element);
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int newMaxSize) {
        if (size() >= newMaxSize)
            throw new IllegalArgumentException(String.format("New max Tree size can't be less than current size. Current size=%d", size()));

        maxSize = newMaxSize;
    }
}
