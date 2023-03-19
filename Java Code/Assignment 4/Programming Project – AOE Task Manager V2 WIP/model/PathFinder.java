/**
 * @author Joseph Edradan
 *
 * This is an object that can find the path of the AdjacencyList
 *
 * Requirements:
 * In arrayListWrapperActualGiven
 * First added Vertex is the first Vertex and must have a connection
 * Last added Vertex is the last Vertex and must have a connection
 */

package model;

import java.util.ArrayList;

public class PathFinder {
    private ArrayList<WrapperActual> arrayListWrapperActualGiven;

    private ArrayList<ArrayList<WrapperActual>> arrayListArrayListWrapperActualAllPossiblePathsToEnd = new ArrayList<>();

    private ArrayList<ArrayList<WrapperActual>> arrayListArrayListWrapperActualAllCriticalPathsToEnd = new ArrayList<>();

    // Is the graph cyclic
    private boolean isGraphCyclic; // is not known until arrayListWrapperActualGiven is given

    public PathFinder() {
    }

    public PathFinder(ArrayList<WrapperActual> arrayListWrapperActualGiven) {
        this(arrayListWrapperActualGiven, 0);
    }

    public PathFinder(ArrayList<WrapperActual> arrayListWrapperActualGiven, int initialTraversableWrapperIndex) {
        setArrayListWrapperActual(arrayListWrapperActualGiven, initialTraversableWrapperIndex);
    }

    public void setArrayListWrapperActual(ArrayList<WrapperActual> arrayListWrapperActualGiven) {
        setArrayListWrapperActual(arrayListWrapperActualGiven, 0);
    }

    public void setArrayListWrapperActual(ArrayList<WrapperActual> arrayListWrapper, int initialTraversableWrapperIndex) {
        this.arrayListWrapperActualGiven = arrayListWrapper;
        this.isGraphCyclic = false;

        // Find all possible paths to end
        travelerAllPossiblePathsToEnd(arrayListWrapper.get(initialTraversableWrapperIndex));

        // Find all critical paths to end
        travelerAllCriticalPathsToEnd(arrayListWrapper.get(initialTraversableWrapperIndex));
    }

    private void travelerAllPossiblePathsToEnd(WrapperActual initialTraversableWrapperIndex) {
        // These ints are used for wrapperActual
        ArrayStack<WrapperActual> arrayStackWrapperActualTemp = new ArrayStack<>();

        // Using my ArrayStack
        arrayStackWrapperActualTemp.push(initialTraversableWrapperIndex);
        travelerAllPossiblePathsToEndHelper(initialTraversableWrapperIndex, arrayStackWrapperActualTemp, 1, false);
    }

    private void travelerAllCriticalPathsToEnd(WrapperActual initialTraversableWrapperIndex) {
        // These ints are used for wrapperActual
        ArrayStack<WrapperActual> arrayStackWrapperActualTemp = new ArrayStack<>();

        // Using my ArrayStack
        arrayStackWrapperActualTemp.push(initialTraversableWrapperIndex);
        travelerAllPossiblePathsToEndHelper(initialTraversableWrapperIndex, arrayStackWrapperActualTemp, 1, true);
    }

    private void travelerAllPossiblePathsToEndHelper(WrapperActual wrapperActualGiven, ArrayStack<WrapperActual> arrayStackWrapperActualTemp, int arrayStackWrapperActualTempSize, boolean useMarkedEdges) {

        // If wrapperActualGiven is the last in the arrayListWrapperActualGiven
        if (wrapperActualGiven.equals(arrayListWrapperActualGiven.get(arrayListWrapperActualGiven.size() - 1))) {
            // arrayListWrapperActualTemp of the path to the end
            ArrayList<WrapperActual> arrayListWrapperActualTemp = new ArrayList<>();

            for (int i = 0; i < arrayStackWrapperActualTempSize; i++) {

                // Get the wrapperActualTemp inside the arrayStackWrapperActualTemp
                WrapperActual wrapperActualTemp = arrayStackWrapperActualTemp.get(i);

                // Add the wrapperActualTemp into the arrayListWrapperActualTemp
                arrayListWrapperActualTemp.add(wrapperActualTemp);
            }
            // FIXME: CLEAN ME UP THIS IS UGLY
            if (useMarkedEdges) {
                arrayListArrayListWrapperActualAllCriticalPathsToEnd.add(arrayListWrapperActualTemp);
            } else {
                arrayListArrayListWrapperActualAllPossiblePathsToEnd.add(arrayListWrapperActualTemp);
            }
        }

        for (int i = 0; i < wrapperActualGiven.getArrayListWrapperPseudo().size(); i++) {
            WrapperPseudo wrapperPseudo = wrapperActualGiven.getArrayListWrapperPseudo().get(i);

            // Get Vertex Index of wrapperPseudo
            int vertexIndex = wrapperPseudo.getVertex().getIndex();

            // Get the wrapperActualPseudo from wrapperPseudo
            WrapperActual wrapperActualPseudo = arrayListWrapperActualGiven.get(vertexIndex);

            // FIXME: CLEAN ME UP THIS IS UGLY
            if (useMarkedEdges) {
                // Way to check edges by skipping edges that are not marked
//                if (wrapperActualGiven.getChangeTime() + wrapperPseudo.getEdge().getEdgeDuration() != wrapperActualPseudo.getChangeTime()) {
//                    continue;
//                }

                // Way to check edges by skipping edges that are not marked
                if (!wrapperPseudo.getEdge().isMarked()) {
                    continue;
                }
            }

            // Add Vertex Index to arrayStackWrapperActualTemp using my ArrayList
            arrayStackWrapperActualTemp.push(wrapperActualPseudo);

            if (checkIfAlreadyInStack(arrayStackWrapperActualTemp)) {
                System.out.println("checkIfAlreadyInStack() found that the Top of the Stack is already in the Stack.\n" +
                        "This Graph is Cyclic.\n");

                isGraphCyclic = true;
                break;
            }

            // Call travelerAllPossiblePathsToEndHelper on wrapperActualPseudo
            travelerAllPossiblePathsToEndHelper(wrapperActualPseudo, arrayStackWrapperActualTemp, arrayStackWrapperActualTempSize + 1, useMarkedEdges);

            // Remove top of stack after done with recursive call using my ArrayStack
            arrayStackWrapperActualTemp.pop();
        }
    }

    // TODO: FIXME FOR USING WrapperPseudo instead of WrapperActual
//    private void travelerAllCriticalPathsToEnd() {
//        ArrayStack<WrapperPseudo> arrayStackWrapperPseudo = new ArrayStack<>();
//
//
//        arrayStackWrapperPseudo.push()
//        travelerAllCriticalPathsToEndHelper(arrayListWrapperActualGiven.get(0), arrayStackWrapperPseudo, 1);
//
//        for(ArrayList<WrapperPseudo> wrapperPseudoArrayList: arrayListArrayListWrapperActualAllCriticalPathsToEnd){
//
//            System.out.println(wrapperPseudoArrayList);
//
//        }
//
//    }
//
//    private void travelerAllCriticalPathsToEndHelper(WrapperActual wrapperActualGiven, ArrayStack<WrapperPseudo> arrayStackWrapperPseudo, int indexTempCounter) {
//
//        // If wrapperActualGiven is the last in the arrayListWrapperActualGiven
//        if (wrapperActualGiven.equals(arrayListWrapperActualGiven.get(arrayListWrapperActualGiven.size() - 1))) {
//            // ArrayList of the path to the end
//            ArrayList<WrapperPseudo> arrayListWrapperPseudo = new ArrayList<>();
//
//            for (int i = 0; i < indexTempCounter; i++) {
//
//                // Get the wrapperActualTemp inside the arrayStackWrapperActualTemp
//                WrapperPseudo wrapperActualTemp = arrayStackWrapperPseudo.get(i);
//
//                // Add the wrapperActualTemp into the arrayListWrapperActualTemp
//                arrayListWrapperPseudo.add(wrapperActualTemp);
//            }
//            arrayListArrayListWrapperActualAllCriticalPathsToEnd.add(arrayListWrapperPseudo);
//        }
//
//        for (int i = 0; i < wrapperActualGiven.getArrayListWrapperPseudo().size(); i++) {
//            WrapperPseudo wrapperPseudo = wrapperActualGiven.getArrayListWrapperPseudo().get(i);
//
//            // Get Vertex Index of wrapperPseudo
//            int vertexIndex = wrapperPseudo.getVertex().getIndex();
//
//            // Get the wrapperActualPseudo from wrapperPseudo
//            WrapperActual wrapperActualPseudo = arrayListWrapperActualGiven.get(vertexIndex);
//
//            if (wrapperActualGiven.getChangeTime() + wrapperPseudo.getEdge().getEdgeDuration() != wrapperActualPseudo.getChangeTime()) {
//                continue;
//            }
//
//            // Add Vertex Index to arrayStackVertex using my ArrayList
//            arrayStackWrapperPseudo.push(wrapperPseudo);
//
//            if (checkIfAlreadyInStack(arrayStackWrapperPseudo)) {
//                System.out.println("checkIfAlreadyInStack() found that the Top of the Stack is already in the Stack.\n" +
//                        "This Standard is Cyclic.\n");
//
//                isGraphCyclic = true;
//
//                break;
//            }
//
//            // Call travelerAllCriticalPathsToEndHelper on wrapperActualPseudo
//            travelerAllCriticalPathsToEndHelper(wrapperActualPseudo, arrayStackWrapperPseudo, indexTempCounter + 1);
//
//            // Remove top of stack after done with recursive call using my ArrayStack
//            arrayStackWrapperPseudo.pop();
//
//        }
//    }

    private boolean checkIfAlreadyInStack(ArrayStack<WrapperActual> arrayStackVertex) {
        for (int i = 0; i < arrayStackVertex.size() - 1; i++) {
            if (arrayStackVertex.peek() == arrayStackVertex.get(i)) {
                return true;
            }
        }
        return false;
    }

    public boolean isGraphCyclic() {
        return isGraphCyclic;
    }

    public ArrayList<ArrayList<WrapperActual>> getArrayListArrayListWrapperActualAllPossiblePathsToEnd() {
        return arrayListArrayListWrapperActualAllPossiblePathsToEnd;
    }

    public ArrayList<ArrayList<WrapperActual>> getArrayListArrayListWrapperActualAllCriticalPathsToEnd(){
        return arrayListArrayListWrapperActualAllCriticalPathsToEnd;

    }


    public void print() {
        for (ArrayList<WrapperActual> arrayListWrapper : arrayListArrayListWrapperActualAllPossiblePathsToEnd) {
            System.out.println(arrayListWrapper);
        }
    }
}
