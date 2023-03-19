/**
 * Activity on Edge (AOE) Networks
 * Project 4
 *
 * @author Joseph Edradan
 * 04/28/19
 * <p>
 * This File runs the graph without the Animation
 * <p>
 * Requirements:
 * First added Vertex is the first Vertex and must have a connection
 * Last added Vertex is the last Vertex and must have a connection
 * Connections between nodes don't need to be in order except for the first and last nodes
 * Adding vertices needs to be in order
 */

package Standard;

import java.util.ArrayList;

public class Graph {
    public static void main(String[] args) {

        testCase1(); // The Normal one
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        testCase2(); // Extended of Normal
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        testCase3(); // Cyclic
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        testCase4(); // Easy Test
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        testCase5(); // Joseph Custom Long (VERY BIG)
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        testCase6(); // Joseph Custom School
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }


    private static void simpleTester(AdjacencyList adjacencyList, boolean DFS) {

        if (DFS == true) {
            System.out.println("DFS\n");
            adjacencyList.runDFS();
        } else {
            System.out.println("Standard\n");
            adjacencyList.run();
        }

        AdjacencyList adjacencyListInverse = adjacencyList.getAdjacencyListInverse();
        AdjacencyList adjacencyListSolution = adjacencyList.getAdjacencyListSolution();

        System.out.println("\n------------------------------------------------");
        System.out.println("Forward Pass (Normal)\n");

        adjacencyList.print();

        System.out.println("All Possible Paths to end");
        for (ArrayList<WrapperActual> wrapperArrayList : adjacencyList.getArrayListArrayListWrapperActualAllPossiblePathsToEnd()) {
            System.out.println(wrapperArrayList);
        }

        System.out.println("\n------------------------------------------------");
        System.out.println("Backwards Pass (Inverse)\n");

        adjacencyListInverse.print();

//        System.out.println("All Possible Paths to end");
//        for(ArrayList<WrapperActual> wrapperArrayList: adjacencyListInverse.getArrayListArrayListWrapperActualAllPossiblePathsToEnd()){
//            System.out.println(wrapperArrayList);
//        }

        System.out.println("\n------------------------------------------------");
        System.out.println("Solution (Final)\n");

        adjacencyListSolution.print();

        System.out.println("All Critical Paths to end");
        for (ArrayList<WrapperActual> wrapperArrayList : adjacencyListSolution.getArrayListArrayListWrapperActualAllCriticalPathsToEnd()) {
            System.out.println(wrapperArrayList);
        }
    }

    private static void testCase1() {

        System.out.println("Test Case 1");
        AdjacencyList adjacencyList = new AdjacencyList();

        adjacencyList.addVertexToArrayList(new Vertex(0, "hi 0"));
        adjacencyList.addVertexToArrayList(new Vertex(1, "hi 1"));
        adjacencyList.addVertexToArrayList(new Vertex(2, "hi 2"));
        adjacencyList.addVertexToArrayList(new Vertex(3, "hi 3"));
        adjacencyList.addVertexToArrayList(new Vertex(4, "hi 4"));
        adjacencyList.addVertexToArrayList(new Vertex(5, "hi 5"));
        adjacencyList.addVertexToArrayList(new Vertex(6, "hi 6"));
        adjacencyList.addVertexToArrayList(new Vertex(7, "hi 7"));
        adjacencyList.addVertexToArrayList(new Vertex(8, "hi 8"));

        adjacencyList.connectNodes(0, 1, 6);
        adjacencyList.connectNodes(0, 2, 4);
        adjacencyList.connectNodes(0, 3, 5);
        adjacencyList.connectNodes(1, 4, 1);
        adjacencyList.connectNodes(2, 4, 1);
        adjacencyList.connectNodes(3, 5, 2);
        adjacencyList.connectNodes(4, 6, 9);
        adjacencyList.connectNodes(4, 7, 7);
        adjacencyList.connectNodes(5, 7, 4);
        adjacencyList.connectNodes(6, 8, 2);
        adjacencyList.connectNodes(7, 8, 4);

        simpleTesterPinter(adjacencyList);

    }

    private static void testCase2() {
        System.out.println("Test Case 2 Slightly Harder Test Case 1");

        AdjacencyList adjacencyList = new AdjacencyList();

        adjacencyList.addVertexToArrayList(new Vertex(0, "hi 0"));
        adjacencyList.addVertexToArrayList(new Vertex(1, "hi 1"));
        adjacencyList.addVertexToArrayList(new Vertex(2, "hi 2"));
        adjacencyList.addVertexToArrayList(new Vertex(3, "hi 3"));
        adjacencyList.addVertexToArrayList(new Vertex(4, "hi 4"));
        adjacencyList.addVertexToArrayList(new Vertex(5, "hi 5"));
        adjacencyList.addVertexToArrayList(new Vertex(6, "hi 6"));
        adjacencyList.addVertexToArrayList(new Vertex(7, "hi 7"));
        adjacencyList.addVertexToArrayList(new Vertex(8, "hi 8"));
        adjacencyList.addVertexToArrayList(new Vertex(9, "hi 8"));
        adjacencyList.addVertexToArrayList(new Vertex(10, "hi 10"));
        adjacencyList.addVertexToArrayList(new Vertex(11, "hi 11"));

        adjacencyList.connectNodes(0, 1, 6);
        adjacencyList.connectNodes(0, 2, 4);
        adjacencyList.connectNodes(0, 3, 5);
        adjacencyList.connectNodes(1, 4, 1);
        adjacencyList.connectNodes(2, 4, 1);
        adjacencyList.connectNodes(3, 5, 2);
        adjacencyList.connectNodes(4, 6, 9);
        adjacencyList.connectNodes(4, 7, 7);
        adjacencyList.connectNodes(5, 7, 4);
        adjacencyList.connectNodes(6, 8, 2);
        adjacencyList.connectNodes(7, 8, 4);
        adjacencyList.connectNodes(8, 9, 1);
        adjacencyList.connectNodes(8, 10, 1);
        adjacencyList.connectNodes(9, 11, 1);
        adjacencyList.connectNodes(10, 11, 1);

        simpleTesterPinter(adjacencyList);

    }

    private static void testCase3() {
        System.out.println("Test Case 3 Cyclic");

        AdjacencyList adjacencyList = new AdjacencyList();

        adjacencyList.addVertexToArrayList(new Vertex(0, "hi 0"));
        adjacencyList.addVertexToArrayList(new Vertex(1, "hi 1"));
        adjacencyList.addVertexToArrayList(new Vertex(2, "hi 2"));
        adjacencyList.addVertexToArrayList(new Vertex(3, "hi 3"));

        adjacencyList.connectNodes(0, 1, 1);
        adjacencyList.connectNodes(1, 2, 1);
        adjacencyList.connectNodes(2, 3, 1);
        adjacencyList.connectNodes(3, 0, 1);


        simpleTesterPinter(adjacencyList);

    }

    private static void testCase4() {
        System.out.println("Test Case 4 Simple");

        AdjacencyList adjacencyList = new AdjacencyList();

        adjacencyList.addVertexToArrayList(new Vertex(0, "hi 0"));
        adjacencyList.addVertexToArrayList(new Vertex(1, "hi 1"));
        adjacencyList.addVertexToArrayList(new Vertex(2, "hi 2"));
        adjacencyList.addVertexToArrayList(new Vertex(3, "hi 3"));
        adjacencyList.addVertexToArrayList(new Vertex(4, "hi 4"));

        adjacencyList.connectNodes(0, 1, 6);
        adjacencyList.connectNodes(0, 2, 5);
        adjacencyList.connectNodes(1, 4, 8);
        adjacencyList.connectNodes(2, 3, 5);
        adjacencyList.connectNodes(1, 3, 5);
        adjacencyList.connectNodes(3, 4, 2);

        simpleTesterPinter(adjacencyList);
    }

    private static void testCase5() {
        System.out.println("Test Case 5 Joseph's Custom Long");

        AdjacencyList adjacencyList = new AdjacencyList();

        adjacencyList.addVertexToArrayList(new Vertex(0, "Event 0"));
        adjacencyList.addVertexToArrayList(new Vertex(1, "Event 1"));
        adjacencyList.addVertexToArrayList(new Vertex(2, "Event 2"));
        adjacencyList.addVertexToArrayList(new Vertex(3, "Event 3"));
        adjacencyList.addVertexToArrayList(new Vertex(4, "Event 4"));
        adjacencyList.addVertexToArrayList(new Vertex(5, "Event 5"));
        adjacencyList.addVertexToArrayList(new Vertex(6, "Event 6"));
        adjacencyList.addVertexToArrayList(new Vertex(7, "Event 7"));
        adjacencyList.addVertexToArrayList(new Vertex(8, "Event 8"));
        adjacencyList.addVertexToArrayList(new Vertex(9, "Event 9"));
        adjacencyList.addVertexToArrayList(new Vertex(10, "Event 10"));
        adjacencyList.addVertexToArrayList(new Vertex(11, "Event 11"));
        adjacencyList.addVertexToArrayList(new Vertex(12, "Event 12"));
        adjacencyList.addVertexToArrayList(new Vertex(13, "Event 13"));
        adjacencyList.addVertexToArrayList(new Vertex(14, "Event 14"));
        adjacencyList.addVertexToArrayList(new Vertex(15, "Event 15"));
        adjacencyList.addVertexToArrayList(new Vertex(16, "Event 16"));
        adjacencyList.addVertexToArrayList(new Vertex(17, "Event 17"));
        adjacencyList.addVertexToArrayList(new Vertex(18, "Event 18"));
        adjacencyList.addVertexToArrayList(new Vertex(19, "Event 19"));
        adjacencyList.addVertexToArrayList(new Vertex(20, "Event 20"));
        adjacencyList.addVertexToArrayList(new Vertex(21, "Event 21"));
        adjacencyList.addVertexToArrayList(new Vertex(22, "Event 22"));
        adjacencyList.addVertexToArrayList(new Vertex(23, "Event 23"));
        adjacencyList.addVertexToArrayList(new Vertex(24, "Event 24"));
        adjacencyList.addVertexToArrayList(new Vertex(25, "Event 25"));
        adjacencyList.addVertexToArrayList(new Vertex(26, "Event 26"));
        adjacencyList.addVertexToArrayList(new Vertex(27, "Event 27"));
        adjacencyList.addVertexToArrayList(new Vertex(28, "Event 28"));

        adjacencyList.connectNodes(0, 1, 6);
        adjacencyList.connectNodes(0, 2, 14);
        adjacencyList.connectNodes(0, 3, 5);
        adjacencyList.connectNodes(1, 4, 1);
        adjacencyList.connectNodes(2, 11, 50);
        adjacencyList.connectNodes(3, 5, 2);
        adjacencyList.connectNodes(4, 6, 9);
        adjacencyList.connectNodes(4, 7, 7);
        adjacencyList.connectNodes(5, 7, 4);
        adjacencyList.connectNodes(6, 8, 6);
        adjacencyList.connectNodes(7, 8, 4);
        adjacencyList.connectNodes(8, 9, 1);
        adjacencyList.connectNodes(8, 10, 1);
        adjacencyList.connectNodes(9, 11, 3);
        adjacencyList.connectNodes(10, 11, 4);
        adjacencyList.connectNodes(11, 12, 7);
        adjacencyList.connectNodes(12, 13, 2);
        adjacencyList.connectNodes(12, 14, 3);
        adjacencyList.connectNodes(13, 14, 5);
        adjacencyList.connectNodes(14, 15, 7);
        adjacencyList.connectNodes(15, 16, 3);
        adjacencyList.connectNodes(15, 17, 2);
        adjacencyList.connectNodes(16, 18, 7);
        adjacencyList.connectNodes(17, 18, 4);
        adjacencyList.connectNodes(2, 6, 60);
        adjacencyList.connectNodes(9, 12, 10);
        adjacencyList.connectNodes(6, 10, 2);
        adjacencyList.connectNodes(1, 6, 20);
        adjacencyList.connectNodes(9, 16, 4);
        adjacencyList.connectNodes(11, 13, 10);
        adjacencyList.connectNodes(18, 19, 23);
        adjacencyList.connectNodes(19, 22, 4);
        adjacencyList.connectNodes(19, 20, 4);
        adjacencyList.connectNodes(19, 21, 4);
        adjacencyList.connectNodes(20, 22, 4);
        adjacencyList.connectNodes(20, 21, 4);
        adjacencyList.connectNodes(9, 21, 1);
        adjacencyList.connectNodes(8, 22, 1);
        adjacencyList.connectNodes(5, 22, 5);
        adjacencyList.connectNodes(1, 3, 5);
        adjacencyList.connectNodes(21, 22, 15);
        adjacencyList.connectNodes(22, 23, 20);
        adjacencyList.connectNodes(23, 24, 30);
        adjacencyList.connectNodes(23, 25, 40);
        adjacencyList.connectNodes(25, 26, 12);
        adjacencyList.connectNodes(24, 26, 12);
        adjacencyList.connectNodes(5, 25, 100);
        adjacencyList.connectNodes(3, 26, 200);
        adjacencyList.connectNodes(4, 5, 5);
        adjacencyList.connectNodes(26, 27, 25);
        adjacencyList.connectNodes(27, 28, 40);
        adjacencyList.connectNodes(0, 28, 300);
        adjacencyList.connectNodes(9, 18, 20);
        adjacencyList.connectNodes(9, 15, 25);
        adjacencyList.connectNodes(5, 23, 120);
        adjacencyList.connectNodes(18, 21, 13);
        adjacencyList.connectNodes(3, 27, 233);
        adjacencyList.connectNodes(8, 21, 33);

        simpleTesterPinter(adjacencyList);
    }

    public static void testCase6() {
        System.out.println("Test Case 6 Joseph Custom School");

        AdjacencyList adjacencyList = new AdjacencyList();

        adjacencyList.addVertexToArrayList(new Vertex(0, "Start"));
        adjacencyList.addVertexToArrayList(new Vertex(1, "English 100"));
        adjacencyList.addVertexToArrayList(new Vertex(2, "English 110"));
        adjacencyList.addVertexToArrayList(new Vertex(3, "English 168"));
        adjacencyList.addVertexToArrayList(new Vertex(4, "Math 100"));
        adjacencyList.addVertexToArrayList(new Vertex(5, "Math 200"));
        adjacencyList.addVertexToArrayList(new Vertex(6, "Math 250"));
        adjacencyList.addVertexToArrayList(new Vertex(7, "Math 260"));
        adjacencyList.addVertexToArrayList(new Vertex(8, "Math 270"));
        adjacencyList.addVertexToArrayList(new Vertex(9, "Math 275"));
        adjacencyList.addVertexToArrayList(new Vertex(10, "Math 280"));
        adjacencyList.addVertexToArrayList(new Vertex(11, "Physics 250"));
        adjacencyList.addVertexToArrayList(new Vertex(12, "Physics 260"));
        adjacencyList.addVertexToArrayList(new Vertex(13, "Physics 270"));
        adjacencyList.addVertexToArrayList(new Vertex(14, "Music 100"));
        adjacencyList.addVertexToArrayList(new Vertex(15, "Music 200"));
        adjacencyList.addVertexToArrayList(new Vertex(16, "Music 300"));
        adjacencyList.addVertexToArrayList(new Vertex(17, "History 100"));
        adjacencyList.addVertexToArrayList(new Vertex(18, "History 110"));
        adjacencyList.addVertexToArrayList(new Vertex(19, "Art 100"));
        adjacencyList.addVertexToArrayList(new Vertex(20, "Art 200"));
        adjacencyList.addVertexToArrayList(new Vertex(21, "End"));

        adjacencyList.connectNodes(0, 1, 1);
        adjacencyList.connectNodes(1, 2, 1);
        adjacencyList.connectNodes(2, 3, 1);
        adjacencyList.connectNodes(3, 21, 1);
        adjacencyList.connectNodes(0, 4, 1);
        adjacencyList.connectNodes(4, 5, 1);
        adjacencyList.connectNodes(5, 6, 1);
        adjacencyList.connectNodes(6, 7, 1);
        adjacencyList.connectNodes(7, 8, 1);
        adjacencyList.connectNodes(8, 9, 1);
        adjacencyList.connectNodes(8, 10, 1);
        adjacencyList.connectNodes(9, 21, 1);
        adjacencyList.connectNodes(9, 10, 1);
        adjacencyList.connectNodes(10, 21, 1);
        adjacencyList.connectNodes(0, 11, 1);
        adjacencyList.connectNodes(11, 12, 1);
        adjacencyList.connectNodes(12, 13, 1);
        adjacencyList.connectNodes(13, 21, 1);
        adjacencyList.connectNodes(0, 14, 1);
        adjacencyList.connectNodes(14, 15, 1);
        adjacencyList.connectNodes(15, 16, 1);
        adjacencyList.connectNodes(16, 21, 1);
        adjacencyList.connectNodes(0, 17, 1);
        adjacencyList.connectNodes(17, 18, 1);
        adjacencyList.connectNodes(18, 21, 1);
        adjacencyList.connectNodes(0, 19, 1);
        adjacencyList.connectNodes(19, 20, 1);
        adjacencyList.connectNodes(20, 21, 1);

        simpleTesterPinter(adjacencyList);

    }

    private static void simpleTesterPinter(AdjacencyList adjacencyList) {
//        simpleTester(adjacencyList, false);
//        System.out.println(
//                "\n##########################################################" +
//                        "##########################################################");
        simpleTester(adjacencyList, true);
    }

    private static void forwardPassDFS(AdjacencyList adjacencyList) {

    }
}
