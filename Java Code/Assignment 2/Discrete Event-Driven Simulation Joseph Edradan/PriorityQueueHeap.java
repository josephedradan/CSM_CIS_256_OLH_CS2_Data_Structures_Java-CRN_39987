/*
Does have Dynamic queue size and handles objects and uses a custom comparator

*Does not use Java Priority Queue
*Does use heap data structure

Does not implements Collections
Does not extends AbstractCollection
Does not implements Queue

References
    https://www.youtube.com/watch?v=WCm3TqScBM8
    https://www.youtube.com/watch?v=ocO3T5cdaBg

Codes based on here but modified
    https://www.youtube.com/watch?v=GubgNdSYdZg

Partially based on here
    https://www.geeksforgeeks.org/min-heap-in-java/

 */

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Queue;

public abstract class PriorityQueueHeap {

    protected int maxSize;
    protected Object[] heap;
    protected int size;

    // Will call Override compare
    protected ComparatorDefault comparator;

    public PriorityQueueHeap(ComparatorDefault comparator, int maxSize) {
        this.maxSize = maxSize;
        heap = new Object[maxSize];
        this.size = 0;

        this.comparator = comparator;
    }

    // Function to return the position of
    // the getParent for the node currently
    // at pos
    protected int getParent(int pos) {
        return ((pos + 1) / 2) - 1;
    }

    // Function to return the position of the
    // left child for the node currently at pos
    protected int getLeftChild(int pos) {
        return (2 * pos) + 1;
    }

    // Function to return the position of
    // the right child for the node currently
    // at pos
    protected int getRightChild(int pos) {
        return (2 * pos) + 2;
    }

    // Function to swap two nodes of the heap
    protected void swap(int positionLeft, int positionRight) {
        Object tmp;
        tmp = heap[positionLeft];
        heap[positionLeft] = heap[positionRight];
        heap[positionRight] = tmp;
    }

    protected void resize(int newSize) {
        Object[] transferArray = new Object[newSize];

        for (int i = 0; i < heap.length; i++) {
            transferArray[i] = heap[i];
        }
        heap = transferArray;
        maxSize = newSize;
    }

    public boolean isEmpty() {
        return (this.size == 0);
    }

    public boolean isFull() {
        return (size == maxSize);
    }

    public int getSize() {
        return size;
    }

    public abstract void insert(Object element);

    public abstract Object remove();

    public Object[] getHeap() {
        return heap;
    }

    /*
    FROM HERE YOU MUST WRITE EITHER A

        minHeapify and minHeap and min

        OR

        maxHeapify and maxHeap and max

     */

//    public static void main (String[] args){
//
//    }

}
