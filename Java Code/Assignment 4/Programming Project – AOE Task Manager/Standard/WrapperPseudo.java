/**
 * @author Joseph Edradan
 * <p>
 * This file is a Wrapper container used in the AdjacencyList
 */
package Standard;

public class WrapperPseudo extends Wrapper {

    WrapperActual wrapperActualParent;

    WrapperActual wrapperActualChild;

    // Edge associated with wrapperPseudo (The Edge is always in the wrapperPseudo not the wrapperActual AKA a wrapperActual with a arrayListWrapper)
    private Edge edge = null;

    public WrapperPseudo(Vertex vertex, WrapperActual wrapperActualParent, WrapperActual wrapperActualChild) {
        super(vertex);
        this.wrapperActualParent = wrapperActualParent;
        this.wrapperActualChild = wrapperActualChild;
    }

    public Edge getEdge() {
        return edge;
    }

    public void setEdge(Edge edge) {
        this.edge = edge;
    }

    public WrapperActual getWrapperActualParent() {
        return wrapperActualParent;
    }

    public WrapperActual getWrapperActualChild() {
        return wrapperActualChild;
    }

    @Override
    public String toString() {
        return String.format("Parent: %-30s     Child: %-30s     Edge: %-30s", wrapperActualParent, wrapperActualChild, edge);
    }
}
