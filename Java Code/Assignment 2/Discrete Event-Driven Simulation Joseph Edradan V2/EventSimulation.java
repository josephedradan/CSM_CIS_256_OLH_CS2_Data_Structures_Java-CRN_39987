import java.util.ArrayList;

public abstract class EventSimulation extends Event {
    protected Group group;

    protected ArrayList<String> strings = new ArrayList<>();

    public EventSimulation(int time, Group group) {
        super(time);
        this.group = group;
    }

    public Group getGroup() {
        return group;
    }

    public ArrayList<String> getStrings() {
        return strings;
    }

    public String stringFormat(int time,Group group, String action) {
        return String.format("Time: %-5s Group: %-7s Size: %-7s Action: %s", time, group.getGroupNumber(), group.getGroupSize(), action);
    }

    @Override
    public abstract String toString();
}
