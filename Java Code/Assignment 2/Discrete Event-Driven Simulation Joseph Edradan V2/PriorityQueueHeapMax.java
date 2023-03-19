import java.util.Arrays;

public class PriorityQueueHeapMax extends PriorityQueueHeap {
    public PriorityQueueHeapMax(ComparatorDefault comparator) {
        this(comparator, 10);
    }

    public PriorityQueueHeapMax(ComparatorDefault comparator, int maxSize) {
        super(comparator, maxSize);
    }

    // Function to heapify the node at position
    // It makes a heap
    private void minHeapify() {

        int positionCurrent = 0;
        while (positionCurrent < size / 2) {

            int rightChild = getRightChild(positionCurrent);
            int leftChild = getLeftChild(positionCurrent);

            if (rightChild < size && comparator.compare(heap[leftChild], heap[rightChild]) == -1) {
                // heap[positionCurrent] >= heap[rightChild] // 1 and 0
                if (comparator.compare(heap[positionCurrent], heap[rightChild]) >= 0) {
                    break;
                }
                swap(positionCurrent, rightChild);
                positionCurrent = rightChild;

            } else {
                // heap[positionCurrent] >= heap[leftChild] // 1 and 0
                if (comparator.compare(heap[positionCurrent], heap[leftChild]) >= 0) {
                    break;
                }
                swap(positionCurrent, leftChild);
                positionCurrent = leftChild;
            }
        }
//        debug();
    }

    // Function to insert a node into the heap
    @Override
    public void insert(Object object) {
        if (!isFull()) {
            int positionCurrent = size;
            heap[size] = object;


            // Climb
            while (positionCurrent > 0) {
                if (comparator.compare(heap[getParent(positionCurrent)], heap[positionCurrent]) >= 0) {
                    break;
                }

                swap(positionCurrent, getParent(positionCurrent));
                positionCurrent = getParent(positionCurrent);
            }

            size++;

        } else {
            // Resize array
            resize(maxSize * 2);
            insert(object);
        }
    }

    // Function to remove and return the minimum
    // element from the heap
    @Override
    public Object remove() {
        if (size == 0) {
            throw new IllegalStateException();
        }

        Object objectReturn = heap[0];
        // Sink (Swap last element in heap which is not the array length with top)
        heap[0] = heap[size - 1];

        // Make heap again
        minHeapify();

        size--;

        return objectReturn;
    }

    public Object max() {
        return heap[0];
    }

    private void debug() {
        System.out.println("-------------------------------------------");
        System.out.println(Arrays.toString(heap));
        System.out.println(String.format("MaxSize: %s ", maxSize));
        System.out.println(String.format("Size: %s ", size));
        System.out.println("-------------------------------------------");
    }

}
