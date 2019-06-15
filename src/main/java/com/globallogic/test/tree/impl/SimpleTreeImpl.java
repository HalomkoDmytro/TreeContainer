package com.globallogic.test.tree.impl;

import com.globallogic.test.tree.SimpleTree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * Basic implementation of Simple Tree Container. Store based on nodes.
 * A node is implemented as inner static class {@link Node<T>}.
 *
 * @param <T> generic type parameter
 */
public class SimpleTreeImpl<T extends Comparable> implements SimpleTree<T> {
    private int size;
    private Node<T> root;

    public SimpleTreeImpl() {
    }

    public SimpleTreeImpl(List<T> elements) {
        Objects.requireNonNull(elements);
        insertListElements(elements);
    }

    private SimpleTreeImpl(Node root) {
        Objects.requireNonNull(root);
        this.root = root;
        this.size = countElements(root);
    }

    @Override
    public boolean add(T element) {
        Objects.requireNonNull(element);

        if (root == null) {
            root = new Node<>(element);
        } else if (contained(element)) {
            return false;
        } else {
            insertInNode(root, element);
        }

        size++;

        return true;
    }


    @Override
    public boolean remove(T element) {
        Objects.requireNonNull(element);
        if (size == 0) return false;

        List<T> listElementsToReplace = new ArrayList<>();
        if (root.element == element) {
            listElementsToReplace = removeRootElement();
        } else {
            if (!tryRemoveNoteRootElement(element, listElementsToReplace)) return false;
        }

        if (size > 1) {
            size--;
        }

        insertListElements(listElementsToReplace);
        size -= listElementsToReplace.size();

        return true;
    }

    /**
     * Remove root element
     * @return List of children's of root element
     */
    private List<T> removeRootElement() {
        List<T> listElementsToReplace;
        listElementsToReplace = filter(x -> true);
        listElementsToReplace.remove(0);
        root = null;
        return listElementsToReplace;
    }

    /**
     * Try to remove note root element if exist
     * @param element to remove
     * @param listElementsToReplace to store leaves from removing element
     * @return
     */
    private boolean tryRemoveNoteRootElement(T element, List<T> listElementsToReplace) {
        Node<T> nodeWithChildElement = findNodeWithChildLookingElement(root, element);
        if (nodeWithChildElement == null) return false;

        removeNotRootNode(element, listElementsToReplace, nodeWithChildElement);
        if (!listElementsToReplace.isEmpty()) {
            listElementsToReplace.remove(0);
        }
        return true;
    }

    private void removeNotRootNode(T element, List<T> listElementsToReplace, Node<T> nodeWithChildElement) {
        Node<T> childToRemove;
        if (nodeWithChildElement.leftChild.element.compareTo(element) > 0) {
            childToRemove = nodeWithChildElement.leftChild;
            nodeWithChildElement.leftChild = null;
        } else {
            childToRemove = nodeWithChildElement.rightChild;
            nodeWithChildElement.rightChild = null;
        }

        filterNode(childToRemove, x -> true, listElementsToReplace);
    }

    /**
     * Insert list of elements in Tree
     * @param listElements
     */
    private void insertListElements(List<T> listElements) {
        for (T el : listElements) {
            add(el);
        }
    }

    @Override
    public boolean contained(T element) {
        Objects.requireNonNull(element);
        if (root == null) return false;

        return (findNodeWithElement(root, element) != null);
    }

    private Node<T> findNodeWithElement(Node node, T element) {
        if (node == null) return null;

        if (node.element.compareTo(element) == 0) return node;

        Node<T> lookingNode = node.element.compareTo(element) > 0 ?
                findNodeWithElement(node.leftChild, element) :
                findNodeWithElement(node.rightChild, element);

        return lookingNode;
    }

    /**
     * Looking node with looking element on left or right child
     * @param node to search on
     * @param ChildElement looking value
     * @return {@link Node<T>}
     */
    private Node<T> findNodeWithChildLookingElement(Node<T> node, T ChildElement) {
        if (node == null) return null;

        if (node.leftChild != null && node.leftChild.element.compareTo(ChildElement) == 0) return node;
        if (node.rightChild != null && node.rightChild.element.compareTo(ChildElement) == 0) return node;

        Node<T> lookingNode = node.element.compareTo(ChildElement) < 0 ?
                findNodeWithChildLookingElement(node.leftChild, ChildElement) :
                findNodeWithChildLookingElement(node.rightChild, ChildElement);

        return lookingNode;
    }

    @Override
    public boolean modify(T element, T elementNewVal) {
        Objects.requireNonNull(element);
        Objects.requireNonNull(elementNewVal);
        if (size == 0) return false;

        List<T> listElementsToReplace = new ArrayList<>();

        if (root.element == element) {
            listElementsToReplace = updateRooElement(elementNewVal);
        } else {
            if (updateNoteRootElement(element, elementNewVal, listElementsToReplace)) return false;
        }

        insertListElements(listElementsToReplace);
        size -= listElementsToReplace.size();

        return true;
    }

    private boolean updateNoteRootElement(T element, T elementNewVal, List<T> listElementsToReplace) {
        Node<T> nodeWithChildElement = findNodeWithChildLookingElement(root, element);
        if (nodeWithChildElement == null) return true;

        if (nodeWithChildElement.leftChild.element == element) {
            nodeWithChildElement.leftChild.element = elementNewVal;
        } else {
            nodeWithChildElement.rightChild.element = elementNewVal;
        }
        removeNotRootNode(element, listElementsToReplace, nodeWithChildElement);
        return false;
    }

    private List<T> updateRooElement(T elementNewVal) {
        List<T> listElementsToReplace;
        root.element = elementNewVal;
        listElementsToReplace = filter(x -> true);
        root = null;
        return listElementsToReplace;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public SimpleTree getSubTree(T element) {
        Objects.requireNonNull(element);

        return new SimpleTreeImpl(findNodeWithElement(root, element));
    }

    @Override
    public List<T> filter(Predicate predicate) {
        Objects.requireNonNull(predicate);
        if (root == null) return Collections.emptyList();

        List list = new ArrayList();
        filterNode(root, predicate, list);

        return list;
    }

    /**
     * Store in list all matching to predicate elements.
     * @param node to search on.
     * @param predicate
     * @param list to store elements.
     */
    private void filterNode(Node<T> node, Predicate predicate, List list) {
        if (node == null) return;

        if (predicate.test(node.element)) {
            list.add(node.element);
        }

        filterNode(node.leftChild, predicate, list);
        filterNode(node.rightChild, predicate, list);
    }

    /**
     * Add new child to Node
     * @param node
     * @param element
     */
    private void insertInNode(Node node, T element) {
        if (node.element.compareTo(element) > 0) {
            if (node.leftChild != null) {
                insertInNode(node.leftChild, element);
            } else {
                node.leftChild = new Node<>(element);
            }
        } else {
            if (node.rightChild != null) {
                insertInNode(node.rightChild, element);
            } else {
                node.rightChild = new Node<>(element);
            }
        }
    }

    /**
     * Count all elements on Tree.
     * @param node
     * @return
     */
    private int countElements(Node node) {
        int count = 0;

        if (node != null) {
            count++;
        }

        if (node.leftChild != null) {
            count += countElements(node.leftChild);
        }

        if (node.rightChild != null) {
            count += countElements(node.rightChild);
        }

        return count;
    }

    private static class Node<T extends Comparable> {
        T element;
        Node<T> leftChild;
        Node<T> rightChild;

        Node(T element) {
            this.element = element;
        }
    }
}
