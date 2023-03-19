/**
 * @author Joseph Edradan
 * <p>
 * This file is a Vertex object for the graph
 */

package model;

public class Vertex {

    // Vertex Number
    private int vertexIndex;

    // Name of Event
    private String eventName;

    // Mark for critical path
    private boolean isMarked;

    public Vertex(int vertexIndex) {
        this(vertexIndex, Integer.toString(vertexIndex));
    }

    public Vertex(int vertexIndex, String eventName) {
        this.vertexIndex = vertexIndex;
        this.eventName = eventName;
    }

    public int getIndex() {
        return vertexIndex;
    }

    public String getEventName() {
        return eventName;
    }

    public boolean isMarked() {
        return isMarked;
    }

    public void setMarked(boolean marked) {
        isMarked = marked;
    }
}
