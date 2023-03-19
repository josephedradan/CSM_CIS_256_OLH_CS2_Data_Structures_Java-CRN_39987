public interface Stack {
    public boolean isEmpty();

    public boolean isFull();

    public void makeEmpty();

    public Object peek();

    public void push(Object theObject);

    public Object pop();
}
