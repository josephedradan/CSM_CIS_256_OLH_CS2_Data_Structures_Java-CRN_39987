/**
 * @author Joseph Edradan
 * <p>
 * This file is an interface for a Stack
 */

package Standard;

public interface Stack<E> {
    public boolean isEmpty();

    public boolean isFull();

    public void makeEmpty();

    public E peek();

    public void push(E theObject);

    public E pop();
}
