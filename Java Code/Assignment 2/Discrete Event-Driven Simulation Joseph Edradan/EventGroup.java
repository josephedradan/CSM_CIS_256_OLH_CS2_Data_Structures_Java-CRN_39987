import java.util.ArrayList;

public abstract class EventGroup extends Event {
    protected Group group;

    protected ArrayList<String> strings = new ArrayList<>();

    public EventGroup(int time, Group group) {
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
