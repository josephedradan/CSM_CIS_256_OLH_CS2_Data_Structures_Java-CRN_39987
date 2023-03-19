/*
Image Component Labeling
Project 1
Joseph Edradan
2/12/19

Purpose and usage of this application:
    Use DFS and BFS to traverse adjacent nodes

Note:
    Start 2/12/10
    End 2/19/19
    Every pixelNode knows its neighbors
    Solvers can work on blind mazes
    CODE CAN BE CLEANED UP TO LOOK MORE BEAUTIFUL
    FOR EXAMPLE SOME CODE CAN USE PROTECTED INSTEAD OF PRIVATE WITH GETTERS AND SETTERS
    I DIDN'T MAKE ANY PACKAGES

    CAUTION If the Discovery Order is over 1000 then Panes will look weird (if they display discovery order), this also goes
    for printing on console since spacing on 3
*/

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Scanner;

public class ImageComponentsDisplay extends Application {
    private static int windowInitialPosition = 0;
    private static int windowOffset = 50;

    // Default PixelGrid
    private PixelGrid pixelGrid;
    private ArrayList<SolverDFS> arrayListOfSolversDFS;
    private ArrayList<SolverBFS> arrayListOfSolversBFS;
    private PixelGrid pixelGridDFS;
    private PixelGrid pixelGridBFS;

    public static void main(String[] args) {
        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        double animationDuration = .025;
        int pixelArea = 16;
        int fontSize = 8;

        windowInitialPosition = windowInitialPosition + windowOffset;
        primaryStage.setX(windowInitialPosition);
        primaryStage.setY(windowInitialPosition);
        welcome();

        // Balanced Example
//        askForInfo(88, 52, .50, false, 100);
        // Apparent Algorithm Example
//        askForInfo(89, 54, .585, false, 100);
        // Increased Amount of Solvers Example
//        askForInfo(100, 60, .4, false, 100);
        // FLOOD FILL
//        askForInfo(100, 60, 1, false, 100);
        // Dual Monitor
//        askForInfo(89,54,.585,true,100);
        // Single Monitor
//        askForInfo(52,63,.5,true,100);
        // More Solvers
//        askForInfo(100, 70, .40, false, 100);
        // Custom
//        askForInfo(89, 54, .585, true, 100);

        // Custom Grading Single Monitor 1 Video
        askForInfo(52,63,.5,false,100);
        // Custom Grading Single Monitor 2 Video
//        askForInfo(52, 63, .60, false, 100);
        // Custom Grading Single Monitor 3 Video
//        askForInfo(52, 63, .80, false, 100);

        // ASK THE USER INSTEAD
//        askForInfo();
        // ASK THE USER INSTEAD (Custom dimensions)
//        askForInfoAdvanced();

        // (SET THIS TO true FOR GRADING)
        // (IF YOUR'RE NOT GRADING, SET THIS TO false AND UNCOMMENT a askForInfo() FUNCTION INSTEAD!)
        if (false) {
            // Custom MEANT FOR GRADING
//            askForInfo(50, 30, .585, false, 100);
//            askForInfo(40, 24, .585, false, 100);
            askForInfo();

            animationDuration = .18;
            pixelArea = 20;
            fontSize = 12;
        }

        System.out.println();

        // Display initial grid
        PixelGridWindow pixelGridWindow = new PixelGridWindow(pixelGrid, null, pixelArea, fontSize);
        ///////////////////////////////////////////////////////
        // Options
//        pixelGridWindow.setNoTextValues(true);
//        pixelGridWindow.showDiscoveryOrder(true);
//        pixelGridWindow.setNoInitialZeros(true);
//        pixelGridWindow.setNoInitialOnes(true);
//        pixelGridWindow.setNoInitialColor(true);
        pixelGridWindow.setBorderless(true);
//        pixelGridWindow.setBorderWhite(true);
        pixelGridWindow.setFillEntireCell(true);
//        pixelGridWindow.setRandomColor(true);
//        pixelGridWindow.setModulusColorSpace(true);
        ///////////////////////////////////////////////////////

        Scene scene = new Scene(pixelGridWindow.createWindowAndContent());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Display of PixelGrid");
        primaryStage.show();
        primaryStage.setResizable(false);


        // NOTE RUNNING BOTH No Animation and Animation WILL RESULT IN HIGH CPU USAGE

        // Display Final Grid DFS No Animation
        createDisplayStage(pixelGridDFS, arrayListOfSolversDFS, "Display of DFS PixelGrid (Label)", pixelArea, fontSize, getWindowPosition());

        // Display Final Grid BFS No Animation
        createDisplayStage(pixelGridBFS, arrayListOfSolversBFS, "Display of BFS PixelGrid (Label)", pixelArea, fontSize, getWindowPosition());

        // Display Final Grid DFS Animation
        createDisplayStageAnimated(pixelGrid, arrayListOfSolversDFS, "Display of DFS PixelGrid Animated (Display Order)", animationDuration, pixelArea, fontSize, getWindowPosition());

        // Display Final Grid BFS Animation
        createDisplayStageAnimated(pixelGrid, arrayListOfSolversBFS, "Display of BFS PixelGrid Animated (Display Order)", animationDuration, pixelArea, fontSize, getWindowPosition());

    }

    private int getWindowPosition() {
        windowInitialPosition = windowInitialPosition + windowOffset;
        return windowInitialPosition;
    }

    public void createDisplayStage(PixelGrid givenPixelGrid, ArrayList<?> arrayListOfSolvers, String title, int pixelArea, int fontSize, int thisWindowPosition) {
        Stage stage = new Stage();
        stage.setX(thisWindowPosition);
        stage.setY(thisWindowPosition);

        PixelGridWindow pixelGridWindow = new PixelGridWindow(givenPixelGrid, arrayListOfSolvers, pixelArea, fontSize);
        ///////////////////////////////////////////////////////
        // Options
//        pixelGridWindow.setNoTextValues(true);
//        pixelGridWindow.showDiscoveryOrder(true);
        pixelGridWindow.setNoInitialZeros(true);
        pixelGridWindow.setNoInitialOnes(true);
//        pixelGridWindow.setNoInitialColor(true);
        pixelGridWindow.setBorderless(true);
//        pixelGridWindow.setBorderWhite(true);
        pixelGridWindow.setFillEntireCell(true);
//        pixelGridWindow.setRandomColor(true);
//        pixelGridWindow.setModulusColorSpace(true);
        ///////////////////////////////////////////////////////

        Pane pane = new Pane(pixelGridWindow.createWindowAndContent());
        Scene scene = new Scene(pane);

        stage.setScene(scene);
        stage.setTitle(title);
        stage.show();
        stage.setResizable(false);
    }

    private void createDisplayStageAnimated(PixelGrid givenPixelGrid, ArrayList<?> arrayListOfSolverGiven, String title, double animationDuration, int pixelArea, int fontSize, int thisWindowPosition) {
        Stage stage = new Stage();
        stage.setX(thisWindowPosition);
        stage.setY(thisWindowPosition);

        PixelGridWindowAnimated pixelGridWindow = new PixelGridWindowAnimated(givenPixelGrid, arrayListOfSolverGiven, animationDuration, pixelArea, fontSize);
        ///////////////////////////////////////////////////////
        // Options
//        pixelGridWindow.setNoTextValues(true);
        pixelGridWindow.showDiscoveryOrder(true);
        pixelGridWindow.setNoInitialZeros(true);
        pixelGridWindow.setNoInitialOnes(true);
//        pixelGridWindow.setNoInitialColor(true);
        pixelGridWindow.setBorderless(true);
//        pixelGridWindow.setBorderWhite(true);
        pixelGridWindow.setFillEntireCell(true);
//        pixelGridWindow.setRandomColor(true);
//        pixelGridWindow.setModulusColorSpace(true);
        ///////////////////////////////////////////////////////

        Pane pane = new Pane(pixelGridWindow.createWindowAndContent());
        Scene scene = new Scene(pane);

        stage.setScene(scene);
        stage.setTitle(title);
        stage.show();
        stage.setResizable(false);

    }

    public void procedure(PixelGrid givenPixelGrid, int arrayQueueSize) {
        System.out.println("Solving using Depth First Search");
        solveMazeUsingDFS(givenPixelGrid);
        System.out.println();

        System.out.println("Solving using Breath First Search");
        solveMazeUsingBFS(givenPixelGrid, arrayQueueSize);
    }

    private void askForInfo() {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Enter image size:");
        int size = keyboard.nextInt();

        System.out.println("Enter Density as decimal:");
        double density = keyboard.nextFloat();

        System.out.println("Enable Random Pixel Grid (true/false):");
        boolean random = keyboard.nextBoolean();

        System.out.println("Enter arrayQueueSize:");
        int arrayQueueSize = keyboard.nextInt();

        System.out.println();
        askForInfo(size, size, density, random, arrayQueueSize);
    }

    private void askForInfoAdvanced() {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Enter image length:");
        int length = keyboard.nextInt();

        System.out.println("Enter image height:");
        int height = keyboard.nextInt();

        System.out.println("Enter Density as decimal:");
        double density = keyboard.nextFloat();

        System.out.println("Enable Random Pixel Grid (true/false):");
        boolean random = keyboard.nextBoolean();

        System.out.println("Enter arrayQueueSize:");
        int arrayQueueSize = keyboard.nextInt();

        System.out.println();
        askForInfo(length, height, density, random, arrayQueueSize);
    }

    private void askForInfo(int length, int height, double density, boolean enableRandom, int arrayQueueSize) {
        pixelGrid = new PixelGrid(length, height, density, enableRandom);
        System.out.println("Base Grid");
        pixelGrid.printPixelGridPrimitiveBorderless();
        System.out.println();
        procedure(pixelGrid, arrayQueueSize);
    }

    private void solveMazeUsingDFS(PixelGrid givenPixelGrid) {
        // NOTE: THIS DOES NOT MATTER SINCE ArrayStack's arrayStackSize DYNAMICALLY CHANGES.
        solveMazeUsingDFS(givenPixelGrid, 10);
    }

    private void solveMazeUsingDFS(PixelGrid givenPixelGrid, int arrayStackSize) {

        arrayListOfSolversDFS = new ArrayList<>();
        pixelGridDFS = new PixelGrid(givenPixelGrid);

        int solverCounter = 2;

        // Scan is TOP to BOTTOM
        for (int i = 0; i < pixelGridDFS.getHeightAbsolute(); i++) {
            for (int j = 0; j < pixelGridDFS.getLengthAbsolute(); j++) {
                if (pixelGridDFS.getPixelNode(i, j).getNodeComponentLabel() == 1) {
                    // Create solver and run algorithm at position
                    SolverDFS solverDFS = new SolverDFS(pixelGridDFS, i, j, solverCounter, arrayStackSize);

                    // Solver Traverse Nodes
                    solverDFS.traversePixelNodes();

                    // Print Grid
//                    pixelGridDFS.printPixelGridPrimitiveBorderless();

                    // Print order of nodes Visited
//                    solverDFS.printVisited();
//                    System.out.println();

                    // Add Solver to arrayList of Solvers
                    arrayListOfSolversDFS.add(solverDFS);

                    // Increment Solver Label
                    solverCounter++;
                }
            }
        }

        // Print Final Grid
        System.out.println("Depth First Search Final Grid (Label)");
        pixelGridDFS.printPixelGridPrimitiveBorderless();
        System.out.println();
        System.out.println("Depth First Search Final Grid (Discovery Order)");
        pixelGridDFS.printPixelGridPrimitiveDiscoveryOrderBorderless();

        // IT LOOKS SO UGLY, NOT RECOMMENDED
//        pixelGridDFS.printPixelGridPrimitiveAssignmentBorderless();
    }

    private void solveMazeUsingBFS(PixelGrid givenPixelGrid) {
        // NOTE: THIS DOES MATTER SINCE ArrayQueue's arrayQueueSize DOES NOT DYNAMICALLY CHANGES SIZE BUT LOOPS AROUND!
        solveMazeUsingBFS(givenPixelGrid, 10);
    }

    private void solveMazeUsingBFS(PixelGrid givenPixelGrid, int arrayQueueSize) {
        arrayListOfSolversBFS = new ArrayList<>();
        pixelGridBFS = new PixelGrid(givenPixelGrid);

        int solverCounter = 2;

        // Scan is TOP to BOTTOM
        for (int i = 0; i < pixelGridBFS.getHeightAbsolute(); i++) {
            for (int j = 0; j < pixelGridBFS.getLengthAbsolute(); j++) {
                if (pixelGridBFS.getPixelNode(i, j).getNodeComponentLabel() == 1) {
                    // Create solver and run algorithm at position
                    SolverBFS solverBFS = new SolverBFS(pixelGridBFS, i, j, solverCounter, arrayQueueSize);

                    // Solver Traverse Nodes
                    solverBFS.traversePixelNodes();

                    // Print Grid
//                    pixelGridBFS.printPixelGridPrimitiveBorderless();

                    // Print order of nodes Visited
//                    solverBFS.printVisited();
//                    System.out.println();

                    // Add Solver to arrayList of Solvers
                    arrayListOfSolversBFS.add(solverBFS);

                    // Increment Solver Label
                    solverCounter++;
                }
            }
        }

        // Print Final Grid
        System.out.println("Depth Breath Search Final Grid (Label)");
        pixelGridBFS.printPixelGridPrimitiveBorderless();
        System.out.println();
        System.out.println("Depth Breath Search Final Grid (Discovery Order)");
        pixelGridBFS.printPixelGridPrimitiveDiscoveryOrderBorderless();

        // IT LOOKS SO UGLY, NOT RECOMMENDED
//        pixelGridBFS.printPixelGridPrimitiveAssignmentBorderless();

    }

    private void welcome() {
        System.out.println("Welcome");
    }


}

