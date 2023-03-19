/**
 * @author Joseph Edradan
 * <p>
 * This file is a ArrayStack Data Structure.
 */

package Standard;

import java.util.Arrays;
import java.util.EmptyStackException;

public class ArrayStack<E> implements Stack<E> {
    private int maxCapacity;
    private int indexTop;
    private E[] stackArray;

    public ArrayStack(int capacity) {
        if (capacity < 1)
            throw new IllegalArgumentException
                    ("capacity must be >= 1");

        maxCapacity = capacity;
        stackArray = (E[]) new Object[capacity];
        indexTop = -1;
    }

    public ArrayStack() {
        this(10);
    }

    public int size() {
        return indexTop + 1;
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
    public E peek() {
        if (isEmpty())
            throw new EmptyStackException();
        return stackArray[indexTop];
    }

    @Override
    public void push(E theObject) {
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
        E[] transferArray = (E[]) new Object[newSize];

        for (int i = 0; i < stackArray.length; i++) {
            transferArray[i] = stackArray[i];
        }
        stackArray = transferArray;
        maxCapacity = newSize;
    }

    @Override
    public E pop() {
        if (!isEmpty()) {
            // Get copy of reference of top element
            E topElement = stackArray[indexTop];
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

    public Object[] getStackArray() {
        return stackArray;
    }

    public int getIndexTop() {
        return indexTop;
    }

    public E get(int index) {
        return stackArray[index];
    }

    public void printStackArray() {
        System.out.println(Arrays.toString(stackArray));
    }
}
