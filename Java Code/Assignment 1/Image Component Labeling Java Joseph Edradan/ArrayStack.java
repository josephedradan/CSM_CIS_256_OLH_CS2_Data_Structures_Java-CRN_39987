import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.EmptyStackException;

public class ArrayStack implements Stack {
    private int maxCapacity;
    private int indexTop;
    private Object[] stackArray;


    public ArrayStack(int capacity) {
        if (capacity < 1)
            throw new IllegalArgumentException
                    ("capacity must be >= 1");

        maxCapacity = capacity;
        stackArray = new Object[capacity];
        indexTop = -1;
    }

    public ArrayStack() {
        this(10);
    }

    @Override
    public boolean isEmpty() {
        return indexTop == -1;
    }

    @Override
    public boolean isFull() {
        return (indexTop + 1 == maxCapacity);
    }

    @Override
    public void makeEmpty() {
        indexTop = -1;
    }

    @Override
    public Object peek() {
        if (isEmpty())
            throw new EmptyStackException();
        return stackArray[indexTop];
    }

    @Override
    public void push(Object theObject) {
        if (!isFull()) {
            indexTop++;
            stackArray[indexTop] = theObject;
        } else {
            // Resize array
            resize(maxCapacity * 2);
            push(theObject);
        }
    }

    private void resize(int newSize) {
        Object[] transferArray = new Object[newSize];

        for (int i = 0; i < stackArray.length; i++) {
            transferArray[i] = stackArray[i];
        }
        stackArray = transferArray;
        maxCapacity = newSize;
    }

    @Override
    public Object pop() {
        if (!isEmpty()) {
            // Get copy of reference of top element
            Object topElement = stackArray[indexTop];
            // Replace top element with null in stack
            stackArray[indexTop--] = null;   // enable garbage collection
            // Return top element
            return topElement;
        }
//        if (indexTop < maxCapacity / 4) {
//            // Resize array
//            resize(maxCapacity / 2);
//            return pop();
//        }
        else {
            // Can't pop()
            throw new EmptyStackException();
        }
    }

    public int getIndexTop() {
        return indexTop;
    }

    public void printStackArray() {
        System.out.println(Arrays.toString(stackArray));
    }
}
