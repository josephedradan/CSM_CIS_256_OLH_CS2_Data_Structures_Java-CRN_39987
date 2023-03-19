/**
 * Joseph Edradan
 * <p>
 * This file runes the Animation
 */

package StandardAnimation;

import Standard.AdjacencyList;
import Standard.Vertex;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.TreeMap;


public class FXGraph extends Application {

    private static final int PANE_WIDTH = 1600;

    private static final int PANE_HEIGHT = 1000;

    PaneGraph paneGraph;

    private Parent createStageFromAdjacencyList(AdjacencyList adjacencyList) {

        paneGraph = new PaneGraph(adjacencyList);

        paneGraph.setPrefSize(PANE_WIDTH, PANE_HEIGHT);
        paneGraph.runAnimation();
        return paneGraph;
    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        AdjacencyList adjacencyListTemp = getTestCase1();
        adjacencyListTemp.runDFS();
        Parent rootPane = createStageFromAdjacencyList(adjacencyListTemp);

        TreeMap<String, AdjacencyList> testCases = new TreeMap<>();

        testCases.put("Test Case 1 Normal", getTestCase1());
        testCases.put("Test Case 2 Extended", getTestCase2());
        testCases.put("Test Case 3 Cyclic", getTestCase3());
        testCases.put("Test Case 4 Easy", getTestCase4());
        testCases.put("Test Case 5 Joseph's Custom Big", getTestCase5());
        testCases.put("Test Case 6 Joseph's Custom School", getTestCase6());
        testCases.put("Test Case 7 Joseph's Custom Disgusting", getTestCase7());
        testCases.put("Test Case 8 Joseph's Custom Follow The Path", getTestCase8());

        paneGraph.loadAdjacencyListExample(testCases);

        Scene primaryScene = new Scene(rootPane);
        primaryStage.setScene(primaryScene);
//        primaryStage.setX(1000);
//        primaryStage.setY(1000);
        primaryStage.show();

    }

    private AdjacencyList getTestCase1() {
        AdjacencyList adjacencyList = new AdjacencyList();
        // Test 1
        adjacencyList.addVertexToArrayList(new Vertex(0, "Event 0"));
        adjacencyList.addVertexToArrayList(new Vertex(1, "Event 1"));
        adjacencyList.addVertexToArrayList(new Vertex(2, "Event 2"));
        adjacencyList.addVertexToArrayList(new Vertex(3, "Event 3"));
        adjacencyList.addVertexToArrayList(new Vertex(4, "Event 4"));
        adjacencyList.addVertexToArrayList(new Vertex(5, "Event 5"));
        adjacencyList.addVertexToArrayList(new Vertex(6, "Event 6"));
        adjacencyList.addVertexToArrayList(new Vertex(7, "Event 7"));
        adjacencyList.addVertexToArrayList(new Vertex(8, "Event 8"));

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

        return adjacencyList;
    }

    private AdjacencyList getTestCase2() {
        AdjacencyList adjacencyList = new AdjacencyList();

        // Test 2
        adjacencyList.addVertexToArrayList(new Vertex(0, "Event 0"));
        adjacencyList.addVertexToArrayList(new Vertex(1, "Event 1"));
        adjacencyList.addVertexToArrayList(new Vertex(2, "Event 2"));
        adjacencyList.addVertexToArrayList(new Vertex(3, "Event 3"));
        adjacencyList.addVertexToArrayList(new Vertex(4, "Event 4"));
        adjacencyList.addVertexToArrayList(new Vertex(5, "Event 5"));
        adjacencyList.addVertexToArrayList(new Vertex(6, "Event 6"));
        adjacencyList.addVertexToArrayList(new Vertex(7, "Event 7"));
        adjacencyList.addVertexToArrayList(new Vertex(8, "Event 8"));
        adjacencyList.addVertexToArrayList(new Vertex(9, "Event 8"));
        adjacencyList.addVertexToArrayList(new Vertex(10, "Event 10"));
        adjacencyList.addVertexToArrayList(new Vertex(11, "Event 11"));

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

        return adjacencyList;
    }

    private AdjacencyList getTestCase3() {
        AdjacencyList adjacencyList = new AdjacencyList();
        // Test 3
        adjacencyList.addVertexToArrayList(new Vertex(0, "Event 0"));
        adjacencyList.addVertexToArrayList(new Vertex(1, "Event 1"));
        adjacencyList.addVertexToArrayList(new Vertex(2, "Event 2"));
        adjacencyList.addVertexToArrayList(new Vertex(3, "Event 3"));

        adjacencyList.connectNodes(0, 1, 1);
        adjacencyList.connectNodes(1, 2, 1);
        adjacencyList.connectNodes(2, 3, 1);
        adjacencyList.connectNodes(3, 0, 1);

        return adjacencyList;
    }

    private AdjacencyList getTestCase4() {
        AdjacencyList adjacencyList = new AdjacencyList();
        // Test 4
        adjacencyList.addVertexToArrayList(new Vertex(0, "Event 0"));
        adjacencyList.addVertexToArrayList(new Vertex(1, "Event 1"));
        adjacencyList.addVertexToArrayList(new Vertex(2, "Event 2"));
        adjacencyList.addVertexToArrayList(new Vertex(3, "Event 3"));
        adjacencyList.addVertexToArrayList(new Vertex(4, "Event 4"));

        adjacencyList.connectNodes(0, 1, 6);
        adjacencyList.connectNodes(0, 2, 5);
        adjacencyList.connectNodes(1, 4, 8);
        adjacencyList.connectNodes(2, 3, 5);
        adjacencyList.connectNodes(1, 3, 5);
        adjacencyList.connectNodes(3, 4, 2);
        return adjacencyList;
    }

    private AdjacencyList getTestCase5() {
        AdjacencyList adjacencyList = new AdjacencyList();

//        Test Custom Big
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

        return adjacencyList;
    }

    private AdjacencyList getTestCase6() {
        AdjacencyList adjacencyList = new AdjacencyList();

        // Test School
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
        return adjacencyList;
    }

    private AdjacencyList getTestCase7() {
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
        adjacencyList.connectNodes(8, 9, 13);
        adjacencyList.connectNodes(8, 10, 4);
        adjacencyList.connectNodes(9, 16, 7);
        adjacencyList.connectNodes(10, 11, 4);
        adjacencyList.connectNodes(11, 12, 3);
        adjacencyList.connectNodes(11, 14, 5);
        adjacencyList.connectNodes(11, 13, 8);
        adjacencyList.connectNodes(13, 14, 4);
        adjacencyList.connectNodes(12, 14, 4);
        adjacencyList.connectNodes(11, 14, 5);
        adjacencyList.connectNodes(10, 14, 10);
        adjacencyList.connectNodes(7, 10, 5);
        adjacencyList.connectNodes(5, 8, 4);
        adjacencyList.connectNodes(2, 8, 13);
        adjacencyList.connectNodes(1, 5, 4);
        adjacencyList.connectNodes(10, 15, 6);
        adjacencyList.connectNodes(15, 16, 8);
        adjacencyList.connectNodes(16, 17, 1);
        adjacencyList.connectNodes(17, 18, 5);
        adjacencyList.connectNodes(4, 12, 24);
        adjacencyList.connectNodes(6, 11, 16);
        adjacencyList.connectNodes(12, 13, 6);
        adjacencyList.connectNodes(10, 17, 12);
        adjacencyList.connectNodes(3, 2, 4);
        adjacencyList.connectNodes(6, 10, 11);
        adjacencyList.connectNodes(8, 16, 17);
        adjacencyList.connectNodes(13, 17, 13);
        adjacencyList.connectNodes(6, 12, 5);
        adjacencyList.connectNodes(12, 15, 9);
        adjacencyList.connectNodes(14, 18, 3);
        adjacencyList.connectNodes(18, 19, 3);
        adjacencyList.connectNodes(18, 20, 3);
        adjacencyList.connectNodes(18, 21, 3);
        adjacencyList.connectNodes(21, 22, 3);
        adjacencyList.connectNodes(19, 22, 3);
        adjacencyList.connectNodes(20, 22, 3);
        adjacencyList.connectNodes(19, 21, 3);
        adjacencyList.connectNodes(19, 20, 3);
        adjacencyList.connectNodes(8, 20, 3);
        adjacencyList.connectNodes(13, 18, 12);
        adjacencyList.connectNodes(14, 17, 5);
        adjacencyList.connectNodes(14, 19, 7);
        adjacencyList.connectNodes(11, 17, 9);
        adjacencyList.connectNodes(11, 15, 3);
        adjacencyList.connectNodes(7, 6, 4);
        adjacencyList.connectNodes(14, 20, 9);
        adjacencyList.connectNodes(16, 20, 12);
        adjacencyList.connectNodes(15, 20, 7);
        adjacencyList.connectNodes(17, 20, 12);
        adjacencyList.connectNodes(5, 9, 5);
        adjacencyList.connectNodes(1, 7, 3);
        adjacencyList.connectNodes(7, 9, 2);
        adjacencyList.connectNodes(3, 9, 7);
        adjacencyList.connectNodes(10, 16, 3);
        adjacencyList.connectNodes(14, 22, 4);

        return adjacencyList;
    }

    private AdjacencyList getTestCase8() {
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

        adjacencyList.connectNodes(0,1,1);
        adjacencyList.connectNodes(1,2,1);
        adjacencyList.connectNodes(2,3,1);
        adjacencyList.connectNodes(3,4,1);
        adjacencyList.connectNodes(4,5,1);
        adjacencyList.connectNodes(5,6,1);
        adjacencyList.connectNodes(6,7,1);
        adjacencyList.connectNodes(7,8,1);
        adjacencyList.connectNodes(8,9,1);
        adjacencyList.connectNodes(9,10,1);
        adjacencyList.connectNodes(10,11,1);
        adjacencyList.connectNodes(11,12,1);
        adjacencyList.connectNodes(12,13,1);
        adjacencyList.connectNodes(13,14,1);
        adjacencyList.connectNodes(14,15,1);
        adjacencyList.connectNodes(15,16,1);
        adjacencyList.connectNodes(16,17,1);
        adjacencyList.connectNodes(17,18,1);
        adjacencyList.connectNodes(18,19,1);
        adjacencyList.connectNodes(0,19,1);

        adjacencyList.connectNodes(3,19,1);
        adjacencyList.connectNodes(5,18,1);
        adjacencyList.connectNodes(7,17,1);

        adjacencyList.connectNodes(8,15,1);
        adjacencyList.connectNodes(1,14,1);
        adjacencyList.connectNodes(0,16,1);

        adjacencyList.connectNodes(6,10,1);
        adjacencyList.connectNodes(4,11,1);
        adjacencyList.connectNodes(7,12,1);





        return adjacencyList;
    }
}

