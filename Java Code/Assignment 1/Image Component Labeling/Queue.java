public interface Queue {

    boolean isEmpty();

    Object getFrontElement();

    Object getRearElement();

    void put(Object theObject);

    Object remove();
}
