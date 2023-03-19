import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Iterator;

public class PriorityQueueHeapMin extends PriorityQueueHeap {
    public PriorityQueueHeapMin(ComparatorDefault comparator) {
        this(comparator, 10);
    }

    public PriorityQueueHeapMin(ComparatorDefault comparator, int maxSize) {
        super(comparator, maxSize);
    }

    // Function to heapify the node at position
    private void minHeapify(int position) {

        System.out.println("-------------------------------------------");
        System.out.println(String.format("HERE %s", position));
        System.out.println(Arrays.toString(heap));
        System.out.println(maxSize);
        System.out.println(size);
        System.out.println(heap[position]);
        System.out.println("-------------------------------------------");

        if (!isLeaf(position)) {
            if (comparator.compare(heap[position], heap[leftChild(position)]) == 1 || comparator.compare(heap[position], heap[rightChild(position)]) == 1) {

                // Swap with the left child and heapify
                // the left child
                if (comparator.compare(heap[leftChild(position)], heap[rightChild(position)]) == -1) {
                    swap(position, leftChild(position));
                    minHeapify(leftChild(position));
                }

                // Swap with the right child and heapify
                // the right child
                else {
                    swap(position, rightChild(position));
                    minHeapify(rightChild(position));
                }
            }
        }
    }

    // Function to insert a node into the heap
    @Override
    public void insert(Object object) {
        if (!isFull()) {
            heap[++size] = object;
            int current = size;

            while (comparator.compare(heap[current], heap[parent(current)]) == -1) {
                swap(current, parent(current));
                current = parent(current);
            }
        } else{
            // Resize array
            resize(maxSize * 2);
            insert(object);
        }
    }

    // Function to build the min heap using
    // the minHeapify
    public void minHeap() {
        for (int pos = (size / 2); pos >= 1; pos--) {
            minHeapify(pos);
        }
    }

    // Function to remove and return the minimum
    // element from the heap
    @Override
    public Object remove() {
        Object popped = heap[FRONT];
        heap[FRONT] = heap[size--];
        minHeapify(FRONT);
        return popped;
    }
}
