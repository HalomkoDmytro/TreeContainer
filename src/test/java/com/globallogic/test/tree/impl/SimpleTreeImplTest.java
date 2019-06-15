package com.globallogic.test.tree.impl;


import com.globallogic.test.tree.SimpleTree;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class SimpleTreeImplTest {

    private SimpleTree<Integer> simpleTree;

    @Before
    public void createSimpleTree() {
        simpleTree = new SimpleTreeImpl<>();
    }

    @Test
    public void testInsertInEmptyTreeExpectTrue() {
        boolean addResult = simpleTree.add(1);
        assertTrue(addResult);
    }

    @Test(expected = NullPointerException.class)
    public void testExpectExceptionOnInsertionNull() {
        simpleTree.add(null);
    }

    @Test
    public void testExpectZeroSizeOnEmptyTree() {
        assertEquals(0, simpleTree.size());
    }

    @Test
    public void testExpectCorrectSizeAfterAddElement() {
        simpleTree.add(3);
        simpleTree.add(5);
        simpleTree.add(7);
        simpleTree.add(1);

        assertEquals(4, simpleTree.size());
    }

    @Test
    public void testExpectFallsOnAddExistingElement() {
        simpleTree.add(3);
        simpleTree.add(5);
        simpleTree.add(1);

        assertFalse(simpleTree.add(5));
    }

    @Test(expected = NullPointerException.class)
    public void testExpectExceptionOnInsertNullList() {
        simpleTree = new SimpleTreeImpl<>(null);
    }

    @Test
    public void testExpectCorrectSizeOnInsertList() {
        List list = new ArrayList();
        list.add(1);
        list.add(2);
        list.add(3);

        simpleTree = new SimpleTreeImpl<>(list);

        assertEquals(list.size(), simpleTree.size());
    }

    @Test
    public void testExpectTrueOnFindExistingElement() {
        simpleTree.add(1);
        simpleTree.add(-10);
        simpleTree.add(3);
        simpleTree.add(5);
        simpleTree.add(4);

        assertTrue(simpleTree.contained(1));
        assertTrue(simpleTree.contained(-10));
        assertTrue(simpleTree.contained(5));
        assertTrue(simpleTree.contained(3));
        assertTrue(simpleTree.contained(4));
    }

    @Test
    public void testExpectFalseOnFindNonExistingElement() {
        simpleTree.add(10);
        simpleTree.add(20);
        simpleTree.add(30);

        assertFalse(simpleTree.contained(1000));
    }

    @Test
    public void testExpectFalseOnFindOnEmptyTree() {
        assertFalse(simpleTree.contained(10));
    }

    @Test(expected = NullPointerException.class)
    public void testExpectExceptionOnFindNullElement() {
        simpleTree.contained(null);
    }

    @Test(expected = NullPointerException.class)
    public void testExpectExceptionWhenGetSubtreeOnEmptyTree() {
        simpleTree.getSubTree(null);
    }

    @Test(expected = NullPointerException.class)
    public void testExpectExceptionWhenAskSubtreeOnNonExistiongElement() {
        simpleTree.getSubTree(10);
    }

    @Test
    public void testExpectNotNullOnGetSubtree() {
        simpleTree.add(1);
        simpleTree.add(2);
        simpleTree.add(3);
        simpleTree.add(4);

        assertTrue(simpleTree.getSubTree(3) != null);
    }

    @Test
    public void testExpectCorrectSizeOfSubtree() {
        simpleTree.add(2);
        simpleTree.add(1);
        simpleTree.add(3);
        simpleTree.add(10);
        simpleTree.add(9);

        assertEquals(1, simpleTree.getSubTree(1).size());
        assertEquals(2, simpleTree.getSubTree(10).size());
        assertEquals(5, simpleTree.getSubTree(2).size());
    }

    @Test
    public void testExpectFalseOnRemoveFromEmptyTree() {
        assertFalse(simpleTree.remove(1));
    }

    @Test(expected = NullPointerException.class)
    public void testExpectExceptionOnRemoveNull() {
        simpleTree.remove(null);
    }

    @Test
    public void testDecreaseSizeAfterRemoveRoot() {
        simpleTree.add(3);
        simpleTree.add(1);
        simpleTree.add(5);
        simpleTree.add(7);

        simpleTree.remove(3);

        assertEquals(3, simpleTree.size());
    }

    @Test
    public void testContainCorrectElementAfterRemoveRoot() {
        simpleTree.add(3);
        simpleTree.add(1);
        simpleTree.add(5);
        simpleTree.add(7);

        simpleTree.remove(3);

        assertTrue(simpleTree.contained(1));
        assertTrue(simpleTree.contained(5));
        assertTrue(simpleTree.contained(7));
    }

    @Test
    public void testDecreaseSizeAfterRemove() {
        simpleTree.add(3);
        simpleTree.add(1);
        simpleTree.add(5);
        simpleTree.add(-3);
        simpleTree.add(22);
        simpleTree.add(7);

        simpleTree.remove(5);
        assertEquals(5, simpleTree.size());

        simpleTree.remove(1);
        assertEquals(4, simpleTree.size());
    }

    @Test
    public void testExpectTrueAfterRemove() {
        simpleTree.add(3);
        simpleTree.add(1);
        simpleTree.add(5);
        simpleTree.add(7);

        assertTrue(simpleTree.remove(3));
        assertTrue(simpleTree.remove(1));
        assertTrue(simpleTree.remove(5));
        assertTrue(simpleTree.remove(7));
    }

    @Test
    public void testExpectReturnFalseOnRemoveNonExistingElement() {
        simpleTree.add(3);
        simpleTree.add(1);
        simpleTree.add(5);
        simpleTree.add(7);

        assertFalse(simpleTree.remove(100));
    }

    @Test(expected = NullPointerException.class)
    public void testExpectExceptionOnNullPredicate() {
        simpleTree.filter(null);
    }

    @Test
    public void testExpectEmptyListOnFilterEmptyTree() {
        List<Integer> list = simpleTree.filter(x -> true);
        assertTrue(list.isEmpty());
    }

    @Test
    public void testExpectCorrectFilterResult() {
        simpleTree.add(1);
        simpleTree.add(2);
        simpleTree.add(3);
        simpleTree.add(4);
        simpleTree.add(5);

        List<Integer> list = simpleTree.filter(x -> (int) x % 2 == 0);

        assertTrue(list.contains(2));
        assertTrue(list.contains(4));
        assertFalse(list.contains(1));
        assertFalse(list.contains(3));
        assertFalse(list.contains(5));
    }

    @Test(expected = NullPointerException.class)
    public void testExpectedExceptionOnModifiedNullElement() {
        simpleTree.modify(null, 1);
    }

    @Test(expected = NullPointerException.class)
    public void testExpectedExceptionOnModifiedNullNewElement() {
        simpleTree.modify(1, null);
    }

    @Test
    public void testExpectedFalseOnModifiedEmptyTree() {
        simpleTree.modify(1, 2);
    }

    @Test
    public void testExpectedCorrectChangeRootElement() {
        simpleTree.add(1);
        simpleTree.add(-100);
        simpleTree.add(100);
        simpleTree.add(22);

        simpleTree.modify(1, 2);

        assertTrue(simpleTree.contained(2));
        assertFalse(simpleTree.contained(1));
    }

    @Test
    public void testExpectedCorrectChangeNonRootElement() {
        simpleTree.add(1);
        simpleTree.add(-100);
        simpleTree.add(100);
        simpleTree.add(101);
        simpleTree.add(22);

        simpleTree.modify(100, 2);

        assertTrue(simpleTree.contained(2));
        assertFalse(simpleTree.contained(100));
    }

    @Test
    public void testExpectedSizeNotChangeAfterModifiedElement() {
        simpleTree.add(1);
        simpleTree.add(-100);
        simpleTree.add(100);
        simpleTree.add(101);
        simpleTree.add(22);

        int sizeBifore = simpleTree.size();

        simpleTree.modify(22, 21);

        assertEquals(sizeBifore, simpleTree.size());
    }

}