public class ComparatorEvent extends ComparatorDefault {
    @Override
    public int compare(Object object1, Object object2) {

        Event leftEvent = (Event) object1;
        Event rightEvent = (Event) object2;

        // Handles nulls
        if (leftEvent == null) {
            return -1;
        } else if (rightEvent == null) {
            return 1;
        }

        if (leftEvent.time > rightEvent.time) {
            return 1;
        } else if (leftEvent.time < rightEvent.time) {
            return -1;
        }
        return 0;
    }
}
