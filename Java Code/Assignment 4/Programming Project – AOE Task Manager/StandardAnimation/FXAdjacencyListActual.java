/**
 * @author Joseph Edradan
 * <p>
 * The FXAdjacencyListActual of a given AdjacencyList.
 * This file is for graphics and stuff...
 */

package StandardAnimation;

import Standard.AdjacencyList;
import Standard.Edge;
import Standard.WrapperActual;
import Standard.WrapperPseudo;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Map;

class FXAdjacencyListActual extends FXAdjacencyList {

    // Animation time stuff
    private EdgeAnimationDataContainer edgeAnimationDataContainerLongest;

    private double edgeAnimationDataContainerLongestAnimationTime = 0; // This is temporary so just use edgeAnimationDataContainerLongest instead

    private double timeOfEntireAnimation;

    // First Backward Pass
    private EdgeAnimationDataContainer edgeAnimationDataContainerBackwardPassFirst; // Should be the same as edgeAnimationDataContainerForwardPassLast

    private double timeUntilBackwardPassFirst = Double.MAX_VALUE;

    // Last Forward Pass
    private EdgeAnimationDataContainer edgeAnimationDataContainerForwardPassLast; // Should be the same as edgeAnimationDataContainerBackwardPassFirst

    private double timeUntilForwardPassLast = 0;

    // FXAdjacencyListActual Stuff here
    private PaneGraph paneGraph;

    private AdjacencyList adjacencyList;

    // This ArrayList is not dependent the actual adjacency list order
    private ArrayList<FXShapeWrapperActual> arrayListFXShapeWrapperActual = new ArrayList<>();

    // This ArrayList is not dependent the actual WrapperActual's WrapperPseudo
    private ArrayList<FXShapeEdgeActual> arrayListFXShapeEdgeActual = new ArrayList<>();

    private ParallelTransition parallelTransition = new ParallelTransition();

    private ArrayList<FXAnimationShapeEdge> arrayListFXAnimationShapeEdge = new ArrayList<>();

    private Map<Edge, EdgeAnimationDataContainer> hashMapEdgeAnimationDataContainer;

    public FXAdjacencyListActual(PaneGraph paneGraph, AdjacencyList adjacencyListRan) {
        arrayListFXShapeWrapper = new ArrayList<>();
        arrayListFXShapeEdge = new ArrayList<>();

        this.paneGraph = paneGraph;
        this.adjacencyList = adjacencyListRan;

        setUpEverythingForGivenRanAdjacencyList();
    }

    private void setUpEverythingForGivenRanAdjacencyList() {
        // Get the EdgeAnimationDataContainers from the AdjacencyLists
        getHashMapEdgeAnimationDataContainer();

        // Get animation times to do specific animation things
        getAnimationTimes();

        // FX Shape Wrapper
        createFXShapeWrapperForGivenRanAdjacencyList();

        // FX Shape Edge
        createFXShapeEdgeForGivenRanAdjacencyList();

        // FX Animation for each FX Shape Edge
        createArrayListFXAnimationShapeEdge();
    }

    private void getHashMapEdgeAnimationDataContainer() {
        hashMapEdgeAnimationDataContainer = adjacencyList.getHashMapEdgeAnimationDataContainer();
    }

    private void getAnimationTimes() {
        for (EdgeAnimationDataContainer edgeAnimationDataContainer : hashMapEdgeAnimationDataContainer.values()) {
//            System.out.println(edgeAnimationDataContainer);
            findEdgeAnimationDataContainerLongest(edgeAnimationDataContainer);
            findEdgeAnimationDataContainerBackwardPassFirst(edgeAnimationDataContainer);
            findEdgeAnimationDataContainerForwardPassLast(edgeAnimationDataContainer);
        }

        // For calculating the time until clearUnmarkedAnimatedLines() is called
        double timePauseBeforeAnimationBackward = edgeAnimationDataContainerLongest.getJavaFXTimePauseBeforeAnimationBackward();
        double durationLengthOfAnimation = edgeAnimationDataContainerLongest.getJavaFXDurationLengthOfAnimation();
        timeOfEntireAnimation = timePauseBeforeAnimationBackward + durationLengthOfAnimation;

    }

    private void createFXShapeWrapperForGivenRanAdjacencyList() {
        for (int i = 0; i < adjacencyList.getArrayListWrapperActual().size(); i++) {
            createFXShapeWrapper(i);

        }
    }

    private void createFXShapeWrapper(int index) {
        WrapperActual wrapperActualNormal = adjacencyList.getWrapperActual(index);
        WrapperActual wrapperActualInverse = adjacencyList.getAdjacencyListInverse().getWrapperActual(index);

        FXShapeWrapperActual fxShapeWrapperActual = new FXShapeWrapperActual(paneGraph, wrapperActualNormal, wrapperActualInverse);

        // Don't uncomment because this will just replace the changeTime Text with the actual changeTime it has.
//            fxShapeWrapperActual.getPaneFXShapeWrapperInformation().setTextChangeTimeForward(wrapperActualNormal.getChangeTime());
//            fxShapeWrapperActual.getPaneFXShapeWrapperInformation().setTextChangeTimeBackward(adjacencyList.getWrapperActual(adjacencyList.getArrayListWrapperActual().size() - 1).getChangeTime());

        if (index == 0) {
            fxShapeWrapperActual.getCircleFXShapeWrapper().setFill(Color.ORANGERED);
        }
        if (index == adjacencyList.getArrayListWrapperActual().size() - 1) {
            fxShapeWrapperActual.getCircleFXShapeWrapper().setFill(Color.LIGHTBLUE);
        }

        arrayListFXShapeWrapperActual.add(fxShapeWrapperActual);

        arrayListFXShapeWrapper.add(fxShapeWrapperActual);
    }

    private void createFXShapeEdgeForGivenRanAdjacencyList() {
        for (int i = 0; i < arrayListFXShapeWrapperActual.size(); i++) {
            createFXShapeEdge(i);
        }
    }

    public void createFXShapeEdge(int index) {
        FXShapeWrapperActual fxShapeWrapperActualActualParent = arrayListFXShapeWrapperActual.get(index);

        WrapperActual wrapperActualParent = fxShapeWrapperActualActualParent.getWrapperActualNormal();

        for (int j = 0; j < wrapperActualParent.getArrayListWrapperPseudo().size(); j++) {

            createFXShapeEdgeHelper(fxShapeWrapperActualActualParent, wrapperActualParent, j);
        }
    }

    private void createFXShapeEdgeHelper(FXShapeWrapperActual fxShapeWrapperActualActualParent, WrapperActual wrapperActualParent, int indexInner) {
        WrapperPseudo wrapperPseudo = wrapperActualParent.getArrayListWrapperPseudo().get(indexInner);

        Edge edge = wrapperPseudo.getEdge();

        int wrapperPseudoVertexIndex = wrapperPseudo.getVertex().getIndex();

        FXShapeWrapperActual fxShapeWrapperActualActualChild = arrayListFXShapeWrapperActual.get(wrapperPseudoVertexIndex);

        EdgeAnimationDataContainer edgeAnimationDataContainer = adjacencyList.getHashMapEdgeAnimationDataContainer().get(edge);

        // Give the paneGraph
        // Give FXShapeEdgeActual fxShapeWrapperActualActualParent
        // Give FXShapeEdgeActual the fxShapeWrapperActualActualChild because wrapperPseudo is a fake
        // Give FXShapeEdgeActual the edgeAnimationDataContainer because it had valuable information inside it
        FXShapeEdgeActual fxShapeEdgeActual = new FXShapeEdgeActual(paneGraph, fxShapeWrapperActualActualParent, fxShapeWrapperActualActualChild, edgeAnimationDataContainer);

        arrayListFXShapeEdgeActual.add(fxShapeEdgeActual);

        arrayListFXShapeEdge.add(fxShapeEdgeActual);

    }

    private void createArrayListFXAnimationShapeEdge() {
        for (FXShapeEdgeActual fxShapeEdgeActual : arrayListFXShapeEdgeActual) {
            arrayListFXAnimationShapeEdge.add(fxShapeEdgeActual.getFXAnimationShapeEdgeForward());
            arrayListFXAnimationShapeEdge.add(fxShapeEdgeActual.getFXAnimationShapeEdgeBackward());
        }
    }

    // TODO: FIND ALTERNATIVE THAN USING A FOR LOOP
    private PauseTransition getPauseTransitionUntilFirstBackwardPassAnimation() {
        PauseTransition pauseTransition = new PauseTransition();
        pauseTransition.setDuration(Duration.seconds(timeUntilBackwardPassFirst));
        pauseTransition.setOnFinished(e -> clearUnmarkedAnimatedLines());

        pauseTransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                for (FXShapeWrapperActual fxShapeWrapperActual : arrayListFXShapeWrapperActual) {
                    fxShapeWrapperActual.getPaneFXShapeWrapperInformation().setTextChangeTimeBackward(adjacencyList.getWrapperActual(adjacencyList.getArrayListWrapperActual().size() - 1).getChangeTime());

                }
            }
        });

        return pauseTransition;
    }

    // Basically get the duration of the entire animation
    private void findEdgeAnimationDataContainerLongest(EdgeAnimationDataContainer edgeAnimationDataContainer) {
//        System.out.println("findEdgeAnimationDataContainerLongest " +edgeAnimationDataContainer);
        if (edgeAnimationDataContainer.getJavaFXTimePauseBeforeAnimationBackward() > edgeAnimationDataContainerLongestAnimationTime) {
            edgeAnimationDataContainerLongest = edgeAnimationDataContainer;
            edgeAnimationDataContainerLongestAnimationTime = edgeAnimationDataContainer.getJavaFXTimePauseBeforeAnimationBackward();
//            System.out.println(edgeAnimationDataContainerLongest);
        }
    }

    private void findEdgeAnimationDataContainerBackwardPassFirst(EdgeAnimationDataContainer edgeAnimationDataContainer) {
        if (edgeAnimationDataContainer.getJavaFXTimePauseBeforeAnimationBackward() < timeUntilBackwardPassFirst) {
            // EdgeAnimationDataContainer that is first in the Backward pass
            edgeAnimationDataContainerBackwardPassFirst = edgeAnimationDataContainer;
            timeUntilBackwardPassFirst = edgeAnimationDataContainer.getJavaFXTimePauseBeforeAnimationBackward();
//            System.out.println(edgeAnimationDataContainerBackwardPassFirst);
        }
    }

    private void findEdgeAnimationDataContainerForwardPassLast(EdgeAnimationDataContainer edgeAnimationDataContainer) {
        // EdgeAnimationDataContainer that is last in the Forward pass
        if (edgeAnimationDataContainer.getJavaFXTimePauseBeforeAnimationForward() > timeUntilForwardPassLast) {
            edgeAnimationDataContainerForwardPassLast = edgeAnimationDataContainer;
            timeUntilForwardPassLast = edgeAnimationDataContainer.getJavaFXTimePauseBeforeAnimationForward();
//            System.out.println(edgeAnimationDataContainerForwardPassLast);
        }
    }


    public AdjacencyList getAdjacencyList() {
        return adjacencyList;
    }

    public ArrayList<FXShapeWrapperActual> getArrayListFXShapeWrapperActual() {
        return arrayListFXShapeWrapperActual;
    }

    public ArrayList<FXShapeEdgeActual> getArrayListFXShapeEdgeActual() {
        return arrayListFXShapeEdgeActual;
    }

    // TODO DO NOT TOUCH THESE AT ALL
//    public FXShapeWrapperActual getFXShapeWrapper(int index) {
//        return arrayListFXShapeWrapperActual.get(index);
//    }
//
//    public FXShapeEdgeActual getFXShapeEdge(int index) {
//        return arrayListFXShapeEdgeActual.get(index);
//    }


    public void clearUnmarkedAnimatedLines() {
        for (FXAnimationShapeEdge fxAnimationShapeEdge : arrayListFXAnimationShapeEdge) {
            if (!fxAnimationShapeEdge.isMarked()) {
                fxAnimationShapeEdge.getLineToBeAnimated().setStrokeWidth(0);
            }
        }
    }

    public void clearAnimatedLines() {
        for (FXAnimationShapeEdge fxAnimationShapeEdge : arrayListFXAnimationShapeEdge) {
            fxAnimationShapeEdge.getLineToBeAnimated().setStrokeWidth(0);
        }
    }

    public void createParallelTransitionDFS() {
        parallelTransition = new ParallelTransition();

        for (FXAnimationShapeEdge fxAnimationShapeEdge : arrayListFXAnimationShapeEdge) {
            parallelTransition.getChildren().add(fxAnimationShapeEdge.getSequentialTransitionDFS());
        }

//        System.out.println(arrayListFXShapeEdgeActual);

        PauseTransition pauseTransition = new PauseTransition();
        pauseTransition.setDuration(Duration.seconds(timeOfEntireAnimation));
        pauseTransition.setOnFinished(e -> clearUnmarkedAnimatedLines());

        // clearUnmarkedAnimatedLines when done
        parallelTransition.getChildren().add(pauseTransition);

        // Update Backward Pass changeTime
        parallelTransition.getChildren().add(getPauseTransitionUntilFirstBackwardPassAnimation());

    }

    // TODO: DON'T USE ME UNTIL I AM FIXED, WHICH I WAS, BUT NOW I'M NOT!!!
    public void createParallelTransitionChangeTimeBased() {
        parallelTransition = new ParallelTransition();

        for (FXAnimationShapeEdge fxAnimationShapeEdge : arrayListFXAnimationShapeEdge) {
            parallelTransition.getChildren().add(fxAnimationShapeEdge.getSequentialTransitionChangeTimeBased());
        }

        PauseTransition pauseTransition = new PauseTransition();
        pauseTransition.setDuration(Duration.seconds(timeOfEntireAnimation));
        pauseTransition.setOnFinished(e -> clearAnimatedLines());

        // clearAnimatedLines when done
        parallelTransition.getChildren().add(pauseTransition);

        // Update Backward Pass changeTime
        parallelTransition.getChildren().add(getPauseTransitionUntilFirstBackwardPassAnimation());

    }

    public EdgeAnimationDataContainer getEdgeAnimationDataContainerLongest() {
        return edgeAnimationDataContainerLongest;
    }

    public EdgeAnimationDataContainer getEdgeAnimationDataContainerForwardPassLast() {
        return edgeAnimationDataContainerForwardPassLast;
    }

    public EdgeAnimationDataContainer getEdgeAnimationDataContainerBackwardPassFirst() {
        return edgeAnimationDataContainerBackwardPassFirst;
    }

    public ParallelTransition getParallelTransition() {
        return parallelTransition;
    }
}
