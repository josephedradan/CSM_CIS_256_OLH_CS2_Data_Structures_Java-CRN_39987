/**
 * @author Joseph Edradan
 * <p>
 * This file is a Edge object for the graph
 */
package model;

public class Edge {

    // Edge Value
    private int edgeIndex;

    // Edge Duration required until Vertex event will happen
    private int edgeDuration = 0;

    // If edge has a name
    private String edgeName;

    // Mark for critical path
    private boolean isMarked = false;

    public Edge(int edgeIndex, int edgeDuration) {
        this(edgeIndex, edgeDuration, Integer.toString(edgeDuration));
    }

    public Edge(int edgeIndex, int edgeDuration, String edgeName) {
        this.edgeIndex = edgeIndex;
        this.edgeDuration = edgeDuration;
        this.edgeName = edgeName;
    }

    public int getEdgeIndex() {
        return edgeIndex;
    }

    public int getEdgeDuration() {
        return edgeDuration;
    }

    public boolean isMarked() {
        return isMarked;
    }

    public void setMarked(boolean marked) {
        isMarked = marked;
    }

    public String getEdgeName() {
        return edgeName;
    }

    @Override
    public String toString() {
        return String.format("Edge Duration: %3s", edgeDuration);
    }

}
