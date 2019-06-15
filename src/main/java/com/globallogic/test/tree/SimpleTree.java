package com.globallogic.test.tree;


import java.util.List;
import java.util.function.Predicate;

public interface SimpleTree<T> {

    boolean add(T element);

    boolean remove(T element);

    boolean contained(T element);

    boolean modify(T element, T newVal);

    int size();

    SimpleTree getSubTree(T element);

    List<T> filter(Predicate predicate);
}

