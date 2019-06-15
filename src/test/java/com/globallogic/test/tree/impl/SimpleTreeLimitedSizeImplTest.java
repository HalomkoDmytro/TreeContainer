package com.globallogic.test.tree.impl;


import com.globallogic.test.tree.Exceptions.SizeOverflow;
import com.globallogic.test.tree.SimpleTree;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class SimpleTreeLimitedSizeImplTest {

    @Test
    public void testInsertInEmptyTreeExpectTrue() {
        SimpleTree<Integer> simpleTree = new SimpleTreeLimitedSizeImpl(2);
        boolean addResult = simpleTree.add(1);
        assertTrue(addResult);
    }

    @Test(expected = SizeOverflow.class)
    public void testExpectExceptionOnOverflowMaxSize() {
        SimpleTree<Integer> simpleTree = new SimpleTreeLimitedSizeImpl(2);
        simpleTree.add(1);
        simpleTree.add(2);
        simpleTree.add(3);
    }

    @Test
    public void testExpectCorrectMaxSize() {
        SimpleTreeLimitedSizeImpl simpleTree = new SimpleTreeLimitedSizeImpl(2);
        assertEquals(2, simpleTree.getMaxSize());
    }

    @Test
    public void testExpectCorrectMaxSizeAfterChangeMaxSize() {
        SimpleTreeLimitedSizeImpl simpleTree = new SimpleTreeLimitedSizeImpl(2);
        simpleTree.setMaxSize(11);
        assertEquals(11, simpleTree.getMaxSize());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExpectExceptionInNegativeMaxSize() {
        SimpleTree<Integer> simpleTree = new SimpleTreeLimitedSizeImpl(-10);
    }

}