package com.globallogic.test.tree.impl;


import com.globallogic.test.tree.SimpleTree;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class SimpleTreeLimitedBranchDepthTest {

    @Test(expected = IllegalArgumentException.class)
    public void testExpectExceptionOnWrongDepthSize() {
        SimpleTree<Integer> simpleTree = new SimpleTreeLimitedBranchDepth(0);
    }

    @Test
    public void testExpectCorrectDepthOnGetDepth() {
        SimpleTreeLimitedBranchDepth<Integer> tree = new SimpleTreeLimitedBranchDepth(10);
        assertEquals(10, tree.getMaxDepth());
    }

    @Test
    public void testExpectTrueAfterAddElementWithFreeDepth() {
        SimpleTreeLimitedBranchDepth<Integer> tree = new SimpleTreeLimitedBranchDepth(4);

        assertTrue(tree.add(1));
        assertTrue(tree.add(2));
        assertTrue(tree.add(3));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExpectExceptionOnReachMaxDepth() {
        SimpleTreeLimitedBranchDepth<Integer> tree = new SimpleTreeLimitedBranchDepth(4);

        assertTrue(tree.add(1));
        assertTrue(tree.add(2));
        assertTrue(tree.add(3));
        assertTrue(tree.add(4));
    }

}