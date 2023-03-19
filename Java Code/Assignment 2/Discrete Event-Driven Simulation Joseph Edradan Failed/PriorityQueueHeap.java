import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Queue;

/*
This code is based on
but has a dynamic queue size and handles objects and uses a custom comparator

*Does not use Java Priority Queue
*Does use heap data structure

Do not implements Collections
Do not extends AbstractCollection
Do not implements Queue
 */
public abstract class PriorityQueueHeap {

    protected static final int FRONT = 1;

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
    // the parent for the node currently
    // at pos
    protected int parent(int pos) {
        return pos / 2;
    }

    // Function to return the position of the
    // left child for the node currently at pos
    protected int leftChild(int pos) {
        return (2 * pos);
    }

    // Function to return the position of
    // the right child for the node currently
    // at pos
    protected int rightChild(int pos) {
        return (2 * pos) + 1;
    }

    // Function that returns true if the passed
    // node is a leaf node
    protected boolean isLeaf(int pos) {
        if (pos >= (size / 2) && pos <= size) {
            return true;
        }
        return false;
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
    // Function to print the contents of the heap
    public void print() {
        for (int i = 1; i <= size / 2; i++) {
            System.out.print(" PARENT : " + heap[i]
                    + " LEFT CHILD : " + heap[2 * i]
                    + " RIGHT CHILD :" + heap[2 * i + 1]);
            System.out.println();
        }
    }

    public boolean isEmpty(){
        return (this.size == 0);
    }

    public boolean isFull() {
        // - 1 because first element is 0 so there should be total elements - 1
        return (size == maxSize);
    }

    public abstract void insert(Object element);
    public abstract Object remove();

    /*
    FROM HERE YOU MUST WRITE EITHER A

        minHeapify and minHeap

        OR

        maxHeapify and maxHeap

     */

//    public static void main (String[] args){
//
//    }

}
