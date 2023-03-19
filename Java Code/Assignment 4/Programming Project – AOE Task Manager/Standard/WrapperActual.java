/**
 * @author Joseph Edradan
 * <p>
 * This file is a Wrapper container used in the AdjacencyList
 */
package Standard;

import java.util.ArrayList;

public class WrapperActual extends Wrapper {

    // Increment changeTime
    private int changeTime = 0;

    // Count for the number of edges in or out from this Wrapper
    private int count = 0;

    // arrayListWrapperPseudo for connecting Wrappers
    private ArrayList<WrapperPseudo> arrayListWrapperPseudo = new ArrayList<>();

    public WrapperActual(Vertex vertex) {
        super(vertex);
    }

    public ArrayList<WrapperPseudo> getArrayListWrapperPseudo() {
        return arrayListWrapperPseudo;
    }

    public void incrementCount() {
        count++;
    }

    public void decrementCount() {
        count--;
    }

    public int getCount() {
        return count;
    }

    public int getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(int changeTime) {
        this.changeTime = changeTime;
    }

    public boolean isMarked() {
        return vertex.isMarked();
    }

    public void setMarked(boolean marked) {
        vertex.setMarked(marked);
    }

}
