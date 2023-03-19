/**
 * @author Joseph Edradan
 * <p>
 * This file is the AdjacencyList Data Structure.
 * <p>
 * Notes:
 * This is some sort of data container for data containers
 * Since this is a directed graph
 * The first in the arrayListWrapperActual is the start
 * The last in the arrayListWrapperActual is the end
 * Connections between nodes don't need to be in order except for the first and last nodes
 * Adding vertices needs to be in order
 *
 * Important Node:
 * Listen, im not going to add a remove method to remove connections and vertices, just make a new AdjacencyList
 */

package model;

import StandardAnimation.EdgeAnimationDataContainer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdjacencyList {

    // For JavaFX animating the edge drawing time, ignore. User should never touch this.
    private double javaFXAnimationShapeEdgePauseBeforeAnimation = 0;

    // For JavaFX animating the edge animation duration, ignore. User can modify this value
    private double JavaFXDurationLengthOfAnimation = .5;

    // For JavaFX animating the edge, this is the information to do the animation, ignore.
    private Map<Edge, EdgeAnimationDataContainer> hashMapEdgeAnimationDataContainer = new HashMap<>();

    // ArrayList of all the Wrappers
    private ArrayList<WrapperActual> arrayListWrapperActual = new ArrayList<>();

    // Counts edges created for indexing the edges
    private int edgeCounter = 0;

    // The AdjacencyList that contains the inverse of this object
    private AdjacencyList adjacencyListInverse = null;

    // The AdjacencyList that contains only critical paths
    private AdjacencyList adjacencyListSolution = null;

    // The Object that finds the path given a arrayListWrapperActual
    private PathFinder pathFinder = new PathFinder();

    // You don't need anything to make the AdjacencyList
    public AdjacencyList() {
    }

    public void connectNodes(int vertexIndexStart, int vertexIndexEnd, int edgeDuration){
        connectNodes(vertexIndexStart,vertexIndexEnd,edgeDuration, Integer.toString(edgeDuration));
    }
    public void connectNodes(int vertexIndexStart, int vertexIndexEnd, int edgeDuration, String edgeName) {

        // WrapperActual Start
        WrapperActual wrapperActualStart = arrayListWrapperActual.get(vertexIndexStart);

        // WrapperActual End
        WrapperActual wrapperActualEnd = arrayListWrapperActual.get(vertexIndexEnd);

        // Get the Vertex based on the vertexIndexEnd
        Vertex vertexTemp = wrapperActualEnd.getVertex();

        // Create a new Wrapper based on the the vertexTemp
        WrapperPseudo wrapperPseudoTemp = new WrapperPseudo(vertexTemp, wrapperActualStart, wrapperActualEnd);

        // Create Edge
        Edge edgeTemp = new Edge(edgeCounter, edgeDuration, edgeName);

        // Increment Edge Counter
        edgeCounter++;

        // Set the Duration for wrapperPseudoTemp based on the user input
        wrapperPseudoTemp.setEdge(edgeTemp);

        // Get the wrapperActual based on vertexIndexStart and add wrapperPseudoTemp to it's arrayListWrapperActual
        wrapperActualStart.getArrayListWrapperPseudo().add(wrapperPseudoTemp);

        // Increment the wrapperActual based on vertexIndexEnd
        wrapperActualEnd.incrementCount();
    }

    // Constructor for creating another AdjacencyList given an already created edge
    // Should only be used to create the adjacencyListInverse and adjacencyListSolution
    private void connectNodes(int vertexIndexStart, int vertexIndexEnd, Edge edge) {

        // WrapperActual Start
        WrapperActual wrapperActualStart = arrayListWrapperActual.get(vertexIndexStart);

        // WrapperActual End
        WrapperActual wrapperActualEnd = arrayListWrapperActual.get(vertexIndexEnd);

        // Get the Vertex based on the vertexIndexEnd
        Vertex vertexTemp = wrapperActualEnd.getVertex();

        // Create a new Wrapper based on the the vertexTemp
        WrapperPseudo wrapperPseudoTemp = new WrapperPseudo(vertexTemp, wrapperActualStart, wrapperActualEnd);

        // Get Edge
        Edge edgeTemp = edge;

        // Set the Duration for wrapperPseudoTemp based on the user input
        wrapperPseudoTemp.setEdge(edgeTemp);

        // Get the wrapperActual based on vertexIndexStart and add wrapperPseudoTemp to it's arrayListWrapperActual
        wrapperActualStart.getArrayListWrapperPseudo().add(wrapperPseudoTemp);

        // Increment the wrapperActual based on vertexIndexEnd
        wrapperActualEnd.incrementCount();
    }

    // Create a new Vertex based on the Index number
    public void addVertexToArrayList(Vertex vertex) {

        // Get the vertex index based on the given vertex
        int vertexIndex = vertex.getIndex();

        // Create wrapperActual to add
        WrapperActual wrapperActual = new WrapperActual(vertex);

        // Add wrapperTemp to the arrayListWrapperActual where add(index, Object)
        arrayListWrapperActual.add(vertexIndex, wrapperActual);
    }

    private void forwardPass() {
        for (int i = 0; i < arrayListWrapperActual.size(); i++) {
            // Get wrapperActualParent from index
            WrapperActual wrapperActualParent = arrayListWrapperActual.get(i);
            for (int j = 0; j < wrapperActualParent.getArrayListWrapperPseudo().size(); j++) {
                // Get the wrapperPseudoChild of the wrapperActualParent
                WrapperPseudo wrapperPseudoChild = wrapperActualParent.getArrayListWrapperPseudo().get(j);

                // Get Index of Vertex of wrapperPseudoChild
                int wrapperPseudoIndex = wrapperPseudoChild.getVertex().getIndex();

                // Get wrapperActual from AdjacencyList
                WrapperActual wrapperActualPseudoChild = arrayListWrapperActual.get(wrapperPseudoIndex);

                // Get Duration of wrapperPseudoChild
                int duration = wrapperPseudoChild.getEdge().getEdgeDuration();

                // Calculate Initial changeTime of wrapperActualParent
                int changeTimeInitial = wrapperActualParent.getChangeTime();

                // Calculate Expected time for wrapperActual
                int changeTimeExpected = changeTimeInitial + duration;

                // If wrapperActual changeTime is less than changeTimeExpected then replace wrapperActual changeTime
                if (changeTimeExpected > wrapperActualPseudoChild.getChangeTime()) {
                    // Get the real child and change the changeTime of it
                    arrayListWrapperActual.get(wrapperPseudoIndex).setChangeTime(changeTimeExpected);
                }

                // For FXAnimationShapeEdge
                setJavaFXAnimationShapeEdgeInformationForward(wrapperPseudoChild, wrapperActualPseudoChild.getCount(), adjacencyListInverse.getArrayListWrapperActual().get(wrapperPseudoIndex).getChangeTime());
            }
        }
    }


    private void backwardsPass() {

        // Last WrapperActual in arrayListWrapperActual
//        WrapperActual wrapperActualTempLast = arrayListWrapperActual.get(arrayListWrapperActual.size() - 1);

        // Mark the wrapperActualParent if it's changes time is the same as in the adjacencyListNormal
//        backwardsPassMarkerVertex(wrapperActualTempLast.getVertex().getIndex());

        for (int i = adjacencyListInverse.getArrayListWrapperActual().size(); i > 0; i--) {
            // Get wrapperActualParent from index top
            WrapperActual wrapperActualParent = adjacencyListInverse.getWrapperActual(i - 1);

            // Note that you don't need to reverse read the children
            for (int j = wrapperActualParent.getArrayListWrapperPseudo().size(); j > 0; j--) {
                // Get the wrapperPseudoChild of the wrapperActualParent
                WrapperPseudo wrapperPseudoChild = wrapperActualParent.getArrayListWrapperPseudo().get(j - 1);

                // Get Index of Vertex of wrapperPseudoChild
                int wrapperPseudoIndex = wrapperPseudoChild.getVertex().getIndex();

                // Get wrapperActual from AdjacencyList
                WrapperActual wrapperActualPseudoChild = adjacencyListInverse.getArrayListWrapperActual().get(wrapperPseudoIndex);

                // Get Duration of wrapperPseudoChild
                int duration = wrapperPseudoChild.getEdge().getEdgeDuration();

                // Calculate Initial changeTime of wrapperActualParent
                int changeTimeInitial = wrapperActualParent.getChangeTime();

                // Calculate Expected time for wrapperActual
                int changeTimeExpected = changeTimeInitial - duration;

                // If wrapperActual changeTime is less than changeTimeExpected then replace wrapperActual changeTime
                if (changeTimeExpected < wrapperActualPseudoChild.getChangeTime()) {
                    // Get the real child and change the changeTime of it
                    adjacencyListInverse.getArrayListWrapperActual().get(wrapperPseudoIndex).setChangeTime(changeTimeExpected);
                }

                // For FXAnimationShapeEdge
                setJavaFXAnimationShapeEdgeInformationBackward(wrapperPseudoChild, wrapperActualPseudoChild.getCount(), adjacencyListInverse.getArrayListWrapperActual().get(wrapperPseudoIndex).getChangeTime());

            }
            // Mark the wrapperActualParent if it's changes time is the same as in the adjacencyListNormal
            backwardsPassMarkerVertex(i - 1);
        }
    }

    private void forwardPassDFS() {

        // Create an arrayStack
        ArrayStack<Wrapper> arrayStack = new ArrayStack();

        // First Wrapper Actual in arrayListWrapperActual;
        WrapperActual wrapperActualTemp = arrayListWrapperActual.get(0);

        // Push first into arrayStack
        arrayStack.push(wrapperActualTemp);

        // Call recursive function
        forwardPassDFSHelper(arrayStack);

    }

    private void forwardPassDFSHelper(ArrayStack<Wrapper> arrayStack) {
        /*
        Go to Top of stack
        Become Top
        Pop Top
        Loop through Top's arrayListWrapperActual with index i
            Get wrapperActual from i
            Change wrapperActual's changeTime
            wrapperActual.decrementCount();
            If wrapperActual.getCount() == 0
                add wrapperActual to stack

        Recursive Call
         */

        // If stack is empty leave
        if (arrayStack.isEmpty()) {
            return;
        }

        // Get wrapperActualParent from index
        WrapperActual wrapperActualParent = arrayListWrapperActual.get(arrayStack.peek().getVertex().getIndex());

        // Pop Top of arrayStack
        arrayStack.pop();

        for (int i = 0; i < wrapperActualParent.getArrayListWrapperPseudo().size(); i++) {
            // Get the wrapperPseudoChild of the wrapperActualParent
            WrapperPseudo wrapperPseudoChild = wrapperActualParent.getArrayListWrapperPseudo().get(i);

            // Get Index of Vertex of wrapperPseudoChild
            int wrapperPseudoIndex = wrapperPseudoChild.getVertex().getIndex();

            // Get wrapperActual from AdjacencyList
            WrapperActual wrapperActualPseudoChild = arrayListWrapperActual.get(wrapperPseudoIndex);

            // Get Duration of wrapperPseudoChild
            int duration = wrapperPseudoChild.getEdge().getEdgeDuration();

            // Calculate Initial changeTime of wrapperActualParent
            int changeTimeInitial = wrapperActualParent.getChangeTime();

            // Calculate Expected time for wrapperActual
            int changeTimeExpected = changeTimeInitial + duration;

            // If wrapperActual changeTime is less than changeTimeExpected then replace wrapperActual changeTime
            if (changeTimeExpected > wrapperActualPseudoChild.getChangeTime()) {
                // Get the real child and change the changeTime of it
                arrayListWrapperActual.get(wrapperPseudoIndex).setChangeTime(changeTimeExpected);
            }

            wrapperActualPseudoChild.decrementCount();

            if (wrapperActualPseudoChild.getCount() == 0) {
                arrayStack.push(wrapperActualPseudoChild);
            }

            // For FXAnimationShapeEdge
            setJavaFXAnimationShapeEdgeInformationForward(wrapperPseudoChild, wrapperActualPseudoChild.getCount(), arrayListWrapperActual.get(wrapperPseudoIndex).getChangeTime());

        }
        forwardPassDFSHelper(arrayStack);
    }


    private void backwardsPassDFS() {
        // Create an arrayStack
        ArrayStack<Wrapper> arrayStack = new ArrayStack();

        // Last WrapperActual in arrayListWrapperActual
        WrapperActual wrapperActualTempLast = arrayListWrapperActual.get(arrayListWrapperActual.size() - 1);

        // Mark the wrapperActualParent if it's changes time is the same as in the adjacencyListNormal
//        backwardsPassMarkerVertex(wrapperActualTempLast.getVertex().getIndex());

        // Push last into arrayStack
        arrayStack.push(wrapperActualTempLast);

        // Call recursive function
        backwardsPassDFSHelper(arrayStack);
    }

    private void backwardsPassDFSHelper(ArrayStack<Wrapper> arrayStack) {
        /*
        Go to Top of stack
        Become Top
        Pop Top
        Loop through Top's arrayListWrapperActual backwards with index i
            Get wrapperActual from i
            Change wrapperActual's changeTime
            wrapperActual.decrementCount();
            If wrapperActual.getCount() == 0
                add wrapperActual to stack

        backwardsPassMarkerVertex Call
        Recursive Call
        */


        // If stack is empty leave
        if (arrayStack.isEmpty()) {
            return;
        }

        // Get wrapperActualParent from index
        WrapperActual wrapperActualParent = adjacencyListInverse.getWrapperActual(arrayStack.peek().getVertex().getIndex());

        // Pop Top of arrayStack
        arrayStack.pop();

        // Note that you don't need to reverse read the children
        for (int i = wrapperActualParent.getArrayListWrapperPseudo().size(); i > 0; i--) {
            // Get the wrapperPseudoChild of the wrapperActualParent
            WrapperPseudo wrapperPseudoChild = wrapperActualParent.getArrayListWrapperPseudo().get(i - 1);

            // Get Index of Vertex of wrapperPseudoChild
            int wrapperPseudoIndex = wrapperPseudoChild.getVertex().getIndex();

            // Get wrapperActualPseudo from AdjacencyList
            WrapperActual wrapperActualPseudo = adjacencyListInverse.getArrayListWrapperActual().get(wrapperPseudoIndex);

            // Get Duration of wrapperPseudoChild
            int duration = wrapperPseudoChild.getEdge().getEdgeDuration();

            // Calculate Initial changeTime of wrapperActualParent
            int changeTimeInitial = wrapperActualParent.getChangeTime();

            // Calculate Expected time for wrapperActualPseudo
            int changeTimeExpected = changeTimeInitial - duration;

            // If wrapperActualPseudo changeTime is less than changeTimeExpected then replace wrapperActualPseudo changeTime
            if (changeTimeExpected < wrapperActualPseudo.getChangeTime()) {
                // Get the real child and change the changeTime of it
                adjacencyListInverse.getArrayListWrapperActual().get(wrapperPseudoIndex).setChangeTime(changeTimeExpected);
            }

            wrapperActualPseudo.decrementCount();

            if (wrapperActualPseudo.getCount() == 0) {
                arrayStack.push(wrapperActualPseudo);
            }

            // For FXAnimationShapeEdge
            setJavaFXAnimationShapeEdgeInformationBackward(wrapperPseudoChild, wrapperActualPseudo.getCount(), adjacencyListInverse.getArrayListWrapperActual().get(wrapperPseudoIndex).getChangeTime());
        }

        // Mark the wrapperActualParent if it's changes time is the same as in the adjacencyListNormal
        backwardsPassMarkerVertex(wrapperActualParent.getVertex().getIndex());

        backwardsPassDFSHelper(arrayStack);
    }

    // Taking Advantage of the backwardsPass, Mark both Current and Inverse Wrappers that have the same getChangeTime()
    private void backwardsPassMarkerVertex(int index) {
        WrapperActual wrapperActualNormal = getWrapperActual(index);
        WrapperActual wrapperActualInverse = adjacencyListInverse.getWrapperActual(index);

        if (wrapperActualNormal.getChangeTime() == wrapperActualInverse.getChangeTime()) {
//            System.out.println(wrapperActualNormal.getVertex().getIndex() + " Current");
//            System.out.println(wrapperActualInverse.getVertex().getIndex() + " Inverse");

            wrapperActualNormal.setMarked(true);
            wrapperActualInverse.setMarked(true);

        }
    }

    // TODO: FIND WAY TO MARK EDGE WHILE ON THE BACKWARD PASS
//    private void backwardsPassMarkerVertex(WrapperActual wrapperActualParent, WrapperPseudo wrapperPseudoChild, WrapperActual wrapperActualPseudo) {
//        int indexWrapperPseudoChild = wrapperPseudoChild.getVertex().getIndex();
//        backwardsPassMarkerVertex(indexWrapperPseudoChild);
//        if (wrapperActualParent.isMarked() && wrapperActualPseudo.isMarked()) {
//
//        }
//    }

    private void createAdjacencyListInverse() {
        adjacencyListInverse = new AdjacencyList();

        // For JavaFX, ignore
        adjacencyListInverse.javaFXAnimationShapeEdgePauseBeforeAnimation = this.javaFXAnimationShapeEdgePauseBeforeAnimation;
        adjacencyListInverse.hashMapEdgeAnimationDataContainer = this.hashMapEdgeAnimationDataContainer;

        // Loop through this current object's arrayListWrapperActual's Wrapper's Vertex and
        // call temp's addVertexToArrayList with that Vertex
        for (WrapperActual wrapperActualParent : arrayListWrapperActual) {
            // Get Vertex from arrayListWrapperActual's Wrapper
            Vertex vertexTemp = wrapperActualParent.getVertex();

            // Add Wrapper based on vertexTemp
            adjacencyListInverse.addVertexToArrayList(vertexTemp);

            // Change all adjacencyListInverse's Wrapper's changeTime to the last arrayListWrapperActual's Wrapper's changeTime
            adjacencyListInverse.getWrapperActual(vertexTemp.getIndex()).setChangeTime(getWrapperActual(arrayListWrapperActual.size() - 1).getChangeTime());

        }

        // Loop through current object's arrayListWrapperActual's Wrapper's arrayListWrapperActual
        // connectNodes using arrayListWrapperActual.getVertex().getIndex() as the source and wrapperActualParent.getVertex().getIndex()
        // Also et duration as the arrayListWrapperActual.getEdge().getEdgeDuration()
        for (int i = 0; i < arrayListWrapperActual.size(); i++) {
            WrapperActual wrapperActualParent = arrayListWrapperActual.get(i);
            for (int j = 0; j < wrapperActualParent.getArrayListWrapperPseudo().size(); j++) {
                WrapperPseudo arrayListWrapper = wrapperActualParent.getArrayListWrapperPseudo().get(j);
                adjacencyListInverse.connectNodes(
                        arrayListWrapper.getVertex().getIndex(),
                        wrapperActualParent.getVertex().getIndex(),
                        arrayListWrapper.getEdge()
                );
            }
        }

    }

    private void createAdjacencyListSolution() {
        adjacencyListSolution = new AdjacencyList();

        // Loop through this current object's arrayListWrapperActual's Wrapper's Vertex and
        // call temp's addVertexToArrayList with that Vertex
        for (int i = 0; i < arrayListWrapperActual.size(); i++) {
            Wrapper wrapperActualParent = arrayListWrapperActual.get(i);

            // Get Vertex from arrayListWrapperActual's Wrapper
            Vertex vertexTemp = wrapperActualParent.getVertex();

            // Add Wrapper based on vertexTemp
            adjacencyListSolution.addVertexToArrayList(vertexTemp);

            // Set Changing time of adjacencyListSolution Wrapper to arrayListWrapperActual Wrapper
            adjacencyListSolution.getWrapperActual(i).setChangeTime(getWrapperActual(i).getChangeTime());
        }

        // Loop through current object's arrayListWrapperActual's Wrapper's arrayListWrapperActual
        // If wrapperActualParent.isMarked() and getWrapperActual(arrayListWrapperActual.getVertex().getIndex()).isMarked()
        // Then copy wrapperActualParent as the source and it's connections arrayListWrapperActual.getVertex().getIndex()
        // Also set duration as the arrayListWrapperActual.getEdge().getEdgeDuration()
        for (int i = 0; i < arrayListWrapperActual.size(); i++) {
            WrapperActual wrapperActualParent = arrayListWrapperActual.get(i);
            for (int j = 0; j < wrapperActualParent.getArrayListWrapperPseudo().size(); j++) {
                // j is the index of arrayListWrapperActual NOT the Vertex Number!
                WrapperPseudo wrapperPseudo = wrapperActualParent.getArrayListWrapperPseudo().get(j);

                // If Both wrapperActualParent and getWrapperActual(wrapperPseudo.getVertex().getIndex()) are Marked then call connectNodes
                // Basically if the Actual Parent and the Actual Child are marked (Actual as being on the arrayListWrapperActual )
                if (wrapperActualParent.isMarked() && getWrapperActual(wrapperPseudo.getVertex().getIndex()).isMarked()) {
                    adjacencyListSolution.connectNodes(
                            wrapperActualParent.getVertex().getIndex(),
                            wrapperPseudo.getVertex().getIndex(),
                            wrapperPseudo.getEdge()
                    );
                }
            }
        }
    }

    // This could be faster if the wrapperPseudo knew the parent
    public boolean areWrappersConnected(Wrapper wrapper_1, Wrapper wrapper_2) {
        // Get actual Wrappers, this means that this method can also handle fake Wrappers
        WrapperActual wrapperActual_1 = arrayListWrapperActual.get(wrapper_1.getVertex().getIndex());
        WrapperActual wrapperActual_2 = arrayListWrapperActual.get(wrapper_2.getVertex().getIndex());


        // Loop through wrapperActual_1 children and check if the child is the same as wrapperActual_2
        for (Wrapper wrapperPseudo : wrapperActual_1.getArrayListWrapperPseudo()) {
            if (wrapperPseudo.getVertex().getIndex() == wrapperActual_2.getVertex().getIndex()) {
                return true;
            }
        }

        // Loop through wrapperActual_2 children and check if the child is the same as wrapperActual_1
        for (Wrapper wrapperPseudo : wrapperActual_2.getArrayListWrapperPseudo()) {
            if (wrapperPseudo.getVertex().getIndex() == wrapperActual_1.getVertex().getIndex()) {
                return true;
            }
        }

        return false;
    }

    public boolean isGraphCyclic() {
        // Note that if wrapperPseudo is Marked, it won't matter because wrapperActual is what really matters.
        // You can also check if the wrapperPseudo is < wrapperActual, but this is not guaranteed
        // Just send it to PathFinder to figure it out...

        pathFinder.setArrayListWrapperActual(arrayListWrapperActual);
        return pathFinder.isGraphCyclic();
    }

    public ArrayList<ArrayList<WrapperActual>> getArrayListArrayListWrapperActualAllPossiblePathsToEnd() {
        return getArrayListArrayListWrapperActualAllPossiblePathsToEnd(0);
    }

    public ArrayList<ArrayList<WrapperActual>> getArrayListArrayListWrapperActualAllPossiblePathsToEnd(int initialTraversableWrapperIndex) {
        pathFinder.setArrayListWrapperActual(arrayListWrapperActual, initialTraversableWrapperIndex);
        return pathFinder.getArrayListArrayListWrapperActualAllPossiblePathsToEnd();
    }

    public ArrayList<ArrayList<WrapperActual>> getArrayListArrayListWrapperActualAllCriticalPathsToEnd() {
        return getArrayListArrayListWrapperActualAllCriticalPathsToEnd(0);
    }

    public ArrayList<ArrayList<WrapperActual>> getArrayListArrayListWrapperActualAllCriticalPathsToEnd(int initialTraversableWrapperIndex) {
        pathFinder.setArrayListWrapperActual(arrayListWrapperActual, initialTraversableWrapperIndex);
        return pathFinder.getArrayListArrayListWrapperActualAllCriticalPathsToEnd();
    }

    public void resetWrappers() {
        for (int i = 0; i < arrayListWrapperActual.size(); i++) {
            arrayListWrapperActual.get(i).setChangeTime(0);
            arrayListWrapperActual.get(i).setMarked(false);
        }
    }

    public WrapperActual getWrapperActual(int index) {
        return arrayListWrapperActual.get(index);
    }

    public ArrayList<WrapperActual> getArrayListWrapperActual() {
        return arrayListWrapperActual;
    }

    public AdjacencyList getAdjacencyListInverse() {
        return adjacencyListInverse;
    }

    public AdjacencyList getAdjacencyListSolution() {
        return adjacencyListSolution;
    }

    private void markCriticalPathEdges() {
        // This exists because int the backward pass, parent does not know if the child is marked in the backwards pass
        // and the child doesn't know which of it's parents are marked
        for (WrapperActual wrapperActual : arrayListWrapperActual) {
            for (WrapperPseudo wrapperPseudo : wrapperActual.getArrayListWrapperPseudo()) {
                WrapperActual wrapperActualPseudo = arrayListWrapperActual.get(wrapperPseudo.getVertex().getIndex());


                // This is wrong because the path might not even be a critical one, you to check if wrapperActual is marked first
                // if (wrapperActual.getChangeTime() + wrapperPseudo.getEdge().getEdgeDuration() == wrapperActualPseudo.getChangeTime())

                // Check if wrapperActual.isMarked() && wrapperActualPseudo.isMarked()
                boolean conditionIsMarked = wrapperActual.isMarked() && wrapperActualPseudo.isMarked();

                // Check if wrapperActual.getChangeTime() + wrapperPseudo.getEdge().getEdgeDuration() == wrapperActualPseudo.getChangeTime()
                boolean conditionChangeTime = wrapperActual.getChangeTime() + wrapperPseudo.getEdge().getEdgeDuration() == wrapperActualPseudo.getChangeTime();

                // Mark edges that are critical and lead to the correct following changeTime
                if (conditionIsMarked && conditionChangeTime) {
                    wrapperPseudo.getEdge().setMarked(true);
//                    System.out.println(wrapperPseudo);
                }
            }
        }
    }

    public void run() {
        // Reset wrappers if anything new was added
        resetWrappers();

        // Calculate the changeTime with a double for loop forward
        forwardPass();

        // Create Inverse AdjacencyList
        createAdjacencyListInverse();

        // Calculate the changeTime with a double for loop backward
        backwardsPass();

        // Mark Critical Path Edges
        markCriticalPathEdges();

        // Create Solution AdjacencyList
        createAdjacencyListSolution();

    }

    public void runDFS() {
        // Reset wrappers if anything new was added
        resetWrappers();

        // Calculate the changeTime with a double for loop forward
        forwardPassDFS();

        // Create Inverse AdjacencyList
        createAdjacencyListInverse();

        // Calculate the changeTime with a double for loop backward using DFS
        backwardsPassDFS();

        // Mark Critical Path Edges
        markCriticalPathEdges();

        // Create Solution AdjacencyList
        createAdjacencyListSolution();

    }

    // For JavaFX, ignore
    private void setJavaFXAnimationShapeEdgeInformationForward(WrapperPseudo wrapperPseudo, int countForward, int changeTimeForward) {
        EdgeAnimationDataContainer edgeAnimationDataContainer = new EdgeAnimationDataContainer(wrapperPseudo, JavaFXDurationLengthOfAnimation);

        edgeAnimationDataContainer.setCountForward(countForward);

        edgeAnimationDataContainer.setChangeTimeForward(changeTimeForward);

        edgeAnimationDataContainer.setJavaFXTimePauseBeforeAnimationForward(javaFXAnimationShapeEdgePauseBeforeAnimation);

        hashMapEdgeAnimationDataContainer.put(wrapperPseudo.getEdge(), edgeAnimationDataContainer);

        javaFXAnimationShapeEdgePauseBeforeAnimation += JavaFXDurationLengthOfAnimation;

//        System.out.println("Edge " + wrapperPseudo.getEdge());
//        System.out.println("javaFXAnimationShapeEdgePauseBeforeAnimation " + javaFXAnimationShapeEdgePauseBeforeAnimation);
//        System.out.println("JavaFXDurationLengthOfAnimation " + JavaFXDurationLengthOfAnimation);
//        System.out.println(edgeAnimationDataContainer.toString());
    }

    // For JavaFX, ignore
    private void setJavaFXAnimationShapeEdgeInformationBackward(WrapperPseudo wrapperPseudo, int countBackward, int changeTimeBackward) {
        EdgeAnimationDataContainer edgeAnimationDataContainer = hashMapEdgeAnimationDataContainer.get(wrapperPseudo.getEdge());

        edgeAnimationDataContainer.setCountBackward(countBackward);

        edgeAnimationDataContainer.setChangeTimeBackward(changeTimeBackward);

        edgeAnimationDataContainer.setJavaFXTimePauseBeforeAnimationBackward(javaFXAnimationShapeEdgePauseBeforeAnimation);

        javaFXAnimationShapeEdgePauseBeforeAnimation += JavaFXDurationLengthOfAnimation;

//        System.out.println(wrapperPseudo.getEdge());
//        System.out.println("javaFXAnimationShapeEdgePauseBeforeAnimation " + javaFXAnimationShapeEdgePauseBeforeAnimation);
//        System.out.println("JavaFXDurationLengthOfAnimation " + JavaFXDurationLengthOfAnimation);
//        System.out.println(edgeAnimationDataContainer.toString());

    }

    public Map<Edge, EdgeAnimationDataContainer> getHashMapEdgeAnimationDataContainer() {
        return hashMapEdgeAnimationDataContainer;
    }

    public void print() {
        for (WrapperActual wrapperActualParent : arrayListWrapperActual) {
            System.out.println(wrapperActualParent);
            System.out.println(String.format("\tCount: %s | Changing Time: %s", wrapperActualParent.getCount(), wrapperActualParent.getChangeTime()));
            for (WrapperPseudo wrapperPseudo : wrapperActualParent.getArrayListWrapperPseudo()) {
                System.out.println(String.format("\t\t%s", wrapperPseudo));
            }
            System.out.println();
        }
    }

}
