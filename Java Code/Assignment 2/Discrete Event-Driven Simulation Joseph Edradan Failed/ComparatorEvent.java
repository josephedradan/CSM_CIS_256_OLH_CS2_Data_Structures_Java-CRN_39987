public class ComparatorEvent extends ComparatorDefault {
    @Override
    public int compare(Object object1, Object object2) {

        Event leftEvent = (Event) object1;
        Event rightEvent = (Event) object2;

        System.out.println("+++++++++++++++++++++++++++++");
        System.out.println(leftEvent);
        System.out.println(rightEvent);
        System.out.println("+++++++++++++++++++++++++++++");

        // Handle nulls (VERY IMPORTANT)
        if(object2 == null || object1 == null){
            return 0;
        }

        if (leftEvent.time > rightEvent.time){
            return 1;
        } else if (leftEvent.time < rightEvent.time) {
            return -1;
        }
        return 0;
    }
}
