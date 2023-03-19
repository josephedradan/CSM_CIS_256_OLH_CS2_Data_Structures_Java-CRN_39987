/*
The ArrayQueue will loop around
 */
import java.util.Arrays;
import java.util.NoSuchElementException;

public class ArrayQueue implements Queue {
    private int maxSize;
    private Object[] queueArray;
    private int front;
    private int rear;
    private int numberOfItems;

    public ArrayQueue(int initialCapacity) {
        if (initialCapacity < 1)
            throw new IllegalArgumentException
                    ("initialCapacity must be >= 1");
        maxSize = initialCapacity;
        queueArray = new Object[initialCapacity];
        front = 0;
        rear = -1;
        numberOfItems = 0;
    }

    public ArrayQueue() {
        this(10);
    }

    @Override
    public boolean isEmpty() {
        return (numberOfItems == 0);
    }

    @Override
    public boolean isFull() {
        return (numberOfItems == maxSize);
    }

    @Override
    public Object getFrontElement() {
        return queueArray[front];
    }

    @Override
    public Object getRearElement() {
        return queueArray[rear];
    }

    // Put object in queue but tell me if it was successful or not
    @Override
    public boolean offer(Object theObject) {
        if (isFull())
//            throw new IllegalStateException();
            return false;
        if (rear == maxSize - 1){
            // If the back of the queue is the end of the array wrap around to the front
            rear = -1;
        }
        rear++;
        queueArray[rear] = theObject;
        numberOfItems++;
        return true;
    }

    // put object in queue and no crash
    @Override
    public void add(Object theObject) {
        if (isFull())
            throw new IllegalStateException("queueSize (initialCapacity) May not be Adequate, Try a larger queueSize.");
//            return false;
        if (rear == maxSize - 1) {
            // If the back of the queue is the end of the array wrap around to the front
            rear = -1;
        }
        rear++;
        queueArray[rear] = theObject;
        numberOfItems++;
//        return true;
    }

    // Basically give me head of queue and then remove head from queue
    @Override
    public Object remove() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Object tempObject = queueArray[front];
        queueArray[front] = null; // Garbage collect
        front++;
        if (front == maxSize) // Dealing with wrap-around again
            front = 0;
        numberOfItems--;
        return tempObject;
    }

    public int getFront() {
        return front;
    }

    public int getRear() {
        return rear;
    }

    public void printQueueArray() {
        System.out.println(Arrays.toString(queueArray));
    }
}
