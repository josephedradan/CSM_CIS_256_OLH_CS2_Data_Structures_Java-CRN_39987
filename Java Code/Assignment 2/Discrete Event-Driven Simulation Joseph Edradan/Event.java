public abstract class Event implements Comparable {
    protected final int time;

    public Event(int time) {
        this.time = time;
    }

    abstract void processEvent();

    @Override
    public int compareTo(Object o) {
        Event right = (Event) o;
        if (this.time < right.time) {
            return -1;
        }
        if (this.time == right.time) {
            return 0;
        }
        return 1;
    }

    public int getTime() {
        return time;
    }
}