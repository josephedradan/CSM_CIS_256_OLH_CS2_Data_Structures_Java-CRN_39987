public interface Queue {
    public boolean isEmpty();

    public boolean isFull();

    public Object getFrontElement();

    public Object getRearElement();

    public boolean offer(Object theObject);

    public void add(Object theObject);

    public Object remove();
}
