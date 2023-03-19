import java.util.Comparator;
/*
Comparator allows you to compare 2 objects than with an object itself and another object
 */
public abstract class ComparatorDefault implements Comparator {

    // Compare 2 objects
    @Override
    public abstract int compare(Object object1, Object object2);

    // Not Needed/Unnecessary
//    public abstract boolean equals(Object obj);
}
