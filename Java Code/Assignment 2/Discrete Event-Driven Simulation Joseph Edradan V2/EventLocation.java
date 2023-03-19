import java.util.ArrayList;

public abstract class EventLocation extends Event {
    protected Group group;

    protected ArrayList<String> strings = new ArrayList<>();

    public EventLocation(int time, Group group) {
        super(time);
        this.group = group;
    }

    public Group getGroup() {
        return group;
    }

    public ArrayList<String> getStrings() {
        return strings;
    }

    @Override
    public abstract String toString();
}