package com.globallogic.test.tree;


import java.util.List;
import java.util.function.Predicate;

/**
 * This class provides container to store data on Binary Tree Structure.
 * @param <T> generic type parameter
 */
public interface SimpleTree<T> {

    /**
     * Add and store element on Tree Structure, if it is not already stored.
     * In case add null throw {@link NullPointerException}.
     * @param element implements Comparable.
     * @return boolean result of operation.
     */
    boolean add(T element);

    /**
     * Remove stored element from Tree Structure.
     * In case try to remove null throw {@link NullPointerException}.
     * @param element implements Comparable.
     * @return boolean result of operation.
     */
    boolean remove(T element);

    /**
     * Check if Tree contained looking element.
     * In case looking null, throw {@link NullPointerException}.
     * @param element implements Comparable.
     * @return true -contained, false - not contained.
     */
    boolean contained(T element);

    /**
     * Find and modify element.
     * In case looking null, or replace to null throw {@link NullPointerException}.
     * @param element which should be replaced.
     * @param newVal
     * @return boolean result of operation.
     */
    boolean modify(T element, T newVal);

    /**
     * Get quantity of stored elements.
     * @return int
     */
    int size();

    /**
     * Get SubTree started from chosen element if this element in Tree.
     * In case chosen element not in the Tree, throw {@link NullPointerException}.
     * @param element to be root node for subTree.
     * @return SimpleTree;
     */
    SimpleTree getSubTree(T element);

    /**
     * Find and store all elements which one much the predicate check.
     * @param predicate not null.
     * @return List with elements.
     */
    List<T> filter(Predicate predicate);
}

