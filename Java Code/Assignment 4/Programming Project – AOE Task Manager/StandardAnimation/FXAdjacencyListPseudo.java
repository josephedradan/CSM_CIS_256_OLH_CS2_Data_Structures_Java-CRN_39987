/**
 * @author Joseph Edradan
 * <p>
 * Purpose:
 * Since my AdjancecyList cannot handle a remove function, you have to make new one every time so this one will make a
 * new AdjacencyList Object given what's on the paneGraph. It works by essentially making a copy of the FXAdjacencyListActual Object
 * and then manually assigning a new vertex to each the the FXShapeWrapperPseudo Objects since the AdjacencyList Object uses
 * a ArrayList instead of an Array or a HashMap.
 */

package StandardAnimation;

import Standard.AdjacencyList;
import Standard.Vertex;
import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FXAdjacencyListPseudo extends FXAdjacencyList {

    private PaneGraph paneGraph;

    private FXAdjacencyListActual fxAdjacencyListActualCurrent;

    private Map<FXShapeWrapperActual, FXShapeWrapperPseudo> hashMapFxShapeWrapperFXShapeWrapperPseudo;

    private ArrayList<FXShapeWrapperPseudo> arrayListFXShapeWrapperPseudo;

    private Map<Vertex, FXShapeWrapperPseudo> hashMapVertexFXShapeWrapperPseudo;

    private ArrayList<FXShapeEdgePseudo> arrayListFXShapeEdgePseudo;

    private AdjacencyList adjacencyListNew;

    private FXAdjacencyListActual fxAdjacencyListActualNew;

    protected FXShapeWrapperPseudo fxShapeWrapperPseudoStart = null;

    protected FXShapeWrapperPseudo getFxShapeWrapperPseudoEnd = null;

    public FXAdjacencyListPseudo(PaneGraph paneGraph) {
        this.paneGraph = paneGraph;
    }

    public void giveFXAdjacencyListActual(FXAdjacencyListActual fxAdjacencyListActualCurrent) {

        arrayListFXShapeWrapperPseudo = new ArrayList<>();
        arrayListFXShapeEdgePseudo = new ArrayList<>();
        hashMapFxShapeWrapperFXShapeWrapperPseudo = new HashMap<>();

        this.fxAdjacencyListActualCurrent = fxAdjacencyListActualCurrent;

    }

    public void createFXAdjacencyListPseudoFromPreexistingFXAdjacencyList() {

        createFXShapeWrapperPseudo();
        createFXShapeEdgePseudo();
//        addToPaneGraph();

    }

    public void createFXAdjacencyListActual() {
        // TODO START AND END NODES NEED TO BE FIXER ESFSDF
        assignIndexToEachFXShapeWrapperPseudo();
        createAdjacencyList();
        createFXAdjacencyList();
        updateFXAdjacencyListNewFXShapeActualPositions();
    }


    private void createFXShapeWrapperPseudo() {
        arrayListFXShapeWrapper = new ArrayList<>();
        for (FXShapeWrapperActual fxShapeWrapperActual : fxAdjacencyListActualCurrent.getArrayListFXShapeWrapperActual()) {

            FXShapeWrapperPseudo fxShapeWrapperPseudo = new FXShapeWrapperPseudo(paneGraph, fxShapeWrapperActual);

            hashMapFxShapeWrapperFXShapeWrapperPseudo.put(fxShapeWrapperActual, fxShapeWrapperPseudo);

            arrayListFXShapeWrapperPseudo.add(fxShapeWrapperPseudo);

            arrayListFXShapeWrapper.add(fxShapeWrapperPseudo);
        }
    }

    public FXShapeWrapperPseudo createFXShapeWrapperPseudoUser(PaneGraph paneGraph, String eventName, Point2D point2D) {
        int indexTemp = arrayListFXShapeWrapperPseudo.size();

        FXShapeWrapperPseudo fxShapeWrapperPseudo = new FXShapeWrapperPseudo(paneGraph, eventName, indexTemp, point2D);

        fxShapeWrapperPseudo.addToPaneGraph();

        fxShapeWrapperPseudo.addEventHandlers();

        arrayListFXShapeWrapperPseudo.add(fxShapeWrapperPseudo);

        arrayListFXShapeWrapper.add(fxShapeWrapperPseudo);

        return fxShapeWrapperPseudo;
    }

    private void createFXShapeEdgePseudo() {
        arrayListFXShapeEdge = new ArrayList<>();
        for (FXShapeEdgeActual fxShapeEdgeActual : fxAdjacencyListActualCurrent.getArrayListFXShapeEdgeActual()) {

            FXShapeWrapperActual fxShapeWrapperActualParent = fxShapeEdgeActual.getFXShapeWrapperParent();

            FXShapeWrapperPseudo fxShapeWrapperPseudoParent = hashMapFxShapeWrapperFXShapeWrapperPseudo.get(fxShapeWrapperActualParent);

            FXShapeWrapperActual fxShapeWrapperActualChild = fxShapeEdgeActual.getFXShapeWrapperChild();

            FXShapeWrapperPseudo fxShapeWrapperPseudoChild = hashMapFxShapeWrapperFXShapeWrapperPseudo.get(fxShapeWrapperActualChild);

            EdgeAnimationDataContainer edgeAnimationDataContainer = fxShapeEdgeActual.getEdgeAnimationDataContainer();

            FXShapeEdgePseudo fxShapeEdgePseudo = new FXShapeEdgePseudo(paneGraph, fxShapeWrapperPseudoParent, fxShapeWrapperPseudoChild, edgeAnimationDataContainer);

            arrayListFXShapeEdgePseudo.add(fxShapeEdgePseudo);

            arrayListFXShapeEdge.add(fxShapeEdgePseudo);
        }
    }

    public void createFXShapeEdgePseudoUser(FXShapeWrapperPseudo fxShapeWrapperPseudoStart, FXShapeWrapperPseudo fxShapeWrapperPseudoEnd, int edgeDuration, String edgeName) {

        FXShapeEdgePseudo fxShapeEdgePseudo = new FXShapeEdgePseudo(paneGraph, fxShapeWrapperPseudoStart, fxShapeWrapperPseudoEnd, edgeDuration, edgeName);

        fxShapeEdgePseudo.addToPaneGraph();

        fxShapeEdgePseudo.addEventHandlers();

        arrayListFXShapeEdgePseudo.add(fxShapeEdgePseudo);

        arrayListFXShapeEdge.add(fxShapeEdgePseudo);

    }

    private void addToPaneGraph() {

        // Edges first
        for (FXShapeEdgePseudo fxShapeEdgePseudo : arrayListFXShapeEdgePseudo) {
            fxShapeEdgePseudo.addToPaneGraph();
        }

        // Wrappers after
        for (FXShapeWrapperPseudo fxShapeWrapperPseudo : arrayListFXShapeWrapperPseudo) {
            fxShapeWrapperPseudo.addToPaneGraph();
        }
    }

    private void assignIndexToEachFXShapeWrapperPseudo() {
        for (int i = 0; i < arrayListFXShapeWrapperPseudo.size(); i++) {
            FXShapeWrapperPseudo fxShapeWrapperPseudo = arrayListFXShapeWrapperPseudo.get(i);
            fxShapeWrapperPseudo.setIndex(i);
        }
    }

    private void createAdjacencyList() {
        hashMapVertexFXShapeWrapperPseudo = new HashMap<>();
        adjacencyListNew = new AdjacencyList();

        for (FXShapeWrapperPseudo fxShapeWrapperPseudo : arrayListFXShapeWrapperPseudo) {

            int index = fxShapeWrapperPseudo.getIndex();

            String eventName = fxShapeWrapperPseudo.getEventName();

            Vertex vertex = new Vertex(index, eventName);

            hashMapVertexFXShapeWrapperPseudo.put(vertex, fxShapeWrapperPseudo);

            adjacencyListNew.addVertexToArrayList(vertex);

            arrayListFXShapeWrapper.add(fxShapeWrapperPseudo);
        }

        for (FXShapeEdgePseudo fxShapeEdgePseudo : arrayListFXShapeEdgePseudo) {

            int vertexIndexStart = fxShapeEdgePseudo.getFxShapeWrapperPseudoParent().getIndex();

            int vertexIndexEnd = fxShapeEdgePseudo.getFxShapeWrapperPseudoChild().getIndex();

            int edgeDuration = fxShapeEdgePseudo.getEdgeDuration();

            String edgeName = fxShapeEdgePseudo.getEdgeName();

            adjacencyListNew.connectNodes(vertexIndexStart, vertexIndexEnd, edgeDuration, edgeName);
        }

        adjacencyListNew.runDFS();
    }

    private void createFXAdjacencyList() {
        fxAdjacencyListActualNew = new FXAdjacencyListActual(paneGraph, adjacencyListNew);
    }

    private void updateFXAdjacencyListNewFXShapeActualPositions() {

        for (FXShapeWrapperActual fxShapeWrapperActualNew : fxAdjacencyListActualNew.getArrayListFXShapeWrapperActual()) {

            Vertex fxShapeWrapperActualNewVertex = fxShapeWrapperActualNew.getWrapperActualNormal().getVertex();

            FXShapeWrapperPseudo fxShapeWrapperPseudo = hashMapVertexFXShapeWrapperPseudo.get(fxShapeWrapperActualNewVertex);

            Point2D point2D = fxShapeWrapperPseudo.getPoint2D();

            fxShapeWrapperActualNew.setPoint2D(point2D);
        }
    }

    public ArrayList<FXShapeEdgePseudo> getArrayListFXShapeEdgePseudo() {
        return arrayListFXShapeEdgePseudo;
    }

    public ArrayList<FXShapeWrapperPseudo> getArrayListFXShapeWrapperPseudo() {
        return arrayListFXShapeWrapperPseudo;
    }

    public FXAdjacencyListActual getFxAdjacencyListActualNew() {
        return fxAdjacencyListActualNew;
    }

    // TODO FIX ME AND ADD ME LATER
//    public boolean areFXShapeWrappersConnected(FXShapeWrapperPseudo wrapper_1, FXShapeWrapperPseudo wrapper_2) {
//        // Get actual Wrappers, this means that this method can also handle fake Wrappers
//        WrapperActual wrapperActual_1 = arrayListWrapperActual.get(wrapper_1.getVertex().getIndex());
//        WrapperActual wrapperActual_2 = arrayListWrapperActual.get(wrapper_2.getVertex().getIndex());
//
//
//        // Loop through wrapperActual_1 children and check if the child is the same as wrapperActual_2
//        for (Wrapper wrapperPseudo : wrapperActual_1.getArrayListWrapperPseudo()) {
//            if (wrapperPseudo.getVertex().getIndex() == wrapperActual_2.getVertex().getIndex()) {
//                return true;
//            }
//        }
//
//        // Loop through wrapperActual_2 children and check if the child is the same as wrapperActual_1
//        for (Wrapper wrapperPseudo : wrapperActual_2.getArrayListWrapperPseudo()) {
//            if (wrapperPseudo.getVertex().getIndex() == wrapperActual_1.getVertex().getIndex()) {
//                return true;
//            }
//        }
//
//        return false;
//    }
}


