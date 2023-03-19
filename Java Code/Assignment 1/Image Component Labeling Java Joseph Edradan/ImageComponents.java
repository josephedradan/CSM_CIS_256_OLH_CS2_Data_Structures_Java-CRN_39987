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

*/

import java.util.ArrayList;
import java.util.Scanner;

public class ImageComponents {

    private static PixelGrid pixelGrid;

    private static ArrayList<SolverDFS> arrayListOfSolverDFS;
    private static ArrayList<SolverBFS> arrayListOfSolverBFS;

    private static PixelGrid pixelGridDFS;
    private static PixelGrid pixelGridBFS;

    public static void main(String[] args) {
        welcome();
        askForInfo(60, 40, .62, false, 100);
//        askForInfo();
//        askForInfoAdvanced();
        System.out.println();

    }

    public static void procedure(PixelGrid givenPixelGrid, int arrayQueueSize) {
        System.out.println("Solving using Depth First Search");
        solveMazeUsingDFS(givenPixelGrid);
        System.out.println();

        System.out.println("Solving using Breath First Search");
        solveMazeUsingBFS(givenPixelGrid, arrayQueueSize);
    }


    private static void askForInfo() {
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

    private static void askForInfoAdvanced() {
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

    private static void askForInfo(int length, int height, double density, boolean enableRandom, int arrayQueueSize) {
        pixelGrid = new PixelGrid(length, height, density, enableRandom);
        System.out.println("Base Grid");
        pixelGrid.printPixelGridPrimitiveBorderless();
        System.out.println();
        procedure(pixelGrid, arrayQueueSize);
    }

    private static void solveMazeUsingDFS(PixelGrid givenPixelGrid) {
        // NOTE: THIS DOES NOT MATTER SINCE ArrayStack's arrayStackSize DYNAMICALLY CHANGES.
        solveMazeUsingDFS(givenPixelGrid, 10);
    }

    private static void solveMazeUsingDFS(PixelGrid givenPixelGrid, int arrayStackSize) {
        arrayListOfSolverDFS = new ArrayList<>();
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
                    arrayListOfSolverDFS.add(solverDFS);

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

    private static void solveMazeUsingBFS(PixelGrid givenPixelGrid) {
        // NOTE: THIS DOES MATTER SINCE ArrayQueue's arrayQueueSize DOES NOT DYNAMICALLY CHANGES SIZE BUT LOOPS AROUND!
        solveMazeUsingBFS(givenPixelGrid, 10);
    }

    private static void solveMazeUsingBFS(PixelGrid givenPixelGrid, int arrayQueueSize) {
        arrayListOfSolverBFS = new ArrayList<>();
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
                    arrayListOfSolverBFS.add(solverBFS);

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

    private static void welcome() {
        System.out.println("Welcome");
    }

}
