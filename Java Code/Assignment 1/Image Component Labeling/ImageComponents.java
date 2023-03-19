/*
 * Image Component Labeling
 * Project 1
 * < Your Name >
 * < Date >
 *
 * Purpose and usage of this application
 *   . . .
 *   . . .
 *
 */

// import . . . ;

import java.util.Scanner;

public class ImageComponents {
    // data members
    /*
     * pixel should be redesigned as an object, instead of int, with two fields,
     * as described in the Assignment Specification document
     */
    private static int[][] pixel;
    private static int size;      // number of rows and columns in the image

    // top-level nested class
    private static class Position {
        // data members
        private int row;   // row number of the position
        private int col;   // column number of the position

        // constructor
        private Position(int theRow, int theCol) {
            row = theRow;
            col = theCol;
        }

        // convert to string suitable for output
        public String toString() {
            return new String(row + " " + col);
        }
    }


    // methods
    /* not yet implemented */
    private static void welcome() {
        System.out.println("HI");
    }

    /**
     * input the image
     */
    private static void inputImage() {
        // define the input stream to be the standard input stream
        Scanner keyboard = new Scanner(System.in);

        System.out.println("Enter image size");
        size = keyboard.nextInt();

        // create and input the pixel array
        pixel = new int[size + 2][size + 2];
        /*
         * Either ask user for input grid or generate random numbers (zeros and ones)
         */
        System.out.println("Enter the pixel array in row-major order");
        for (int i = 1; i <= size; i++) {
            for (int j = 1; j <= size; j++) {

            }
            // pixel[i][j] = readInteger();
        }
    }

    /**
     * label the components
     */
    private static void labelComponents() {
        // initialize offsets
        Position[] offset = new Position[4];
        offset[0] = new Position(0, 1);   // right
        offset[1] = new Position(1, 0);   // down
        offset[2] = new Position(0, -1);  // left
        offset[3] = new Position(-1, 0);  // up

        // initialize wall of 0 pixels
        for (int i = 0; i <= size + 1; i++) {
            // Your code goes here
        }

        int numOfNbrs = 4; // neighbors of a pixel position
        ArrayQueue q = new ArrayQueue();
        Position nbr = new Position(0, 0);
        int id = 1;  // component id

        // scan all pixels labeling components
        for (int r = 1; r <= size; r++)      // row r of image
            for (int c = 1; c <= size; c++)   // column c of image
                if (pixel[r][c] == 1) {

                    /*
                     *  New image Component
                     *
                     *  Your code goes here
                     */

                } // end of if, for c, and for r
    }

    /**
     * output labeled image
     */
    private static void outputImage() {
        System.out.println("The labeled image is");
        for (int i = 1; i <= size; i++) {
            for (int j = 1; j <= size; j++)
                System.out.print(pixel[i][j] + "  ");
            System.out.println();
        }
    }

    /**
     * entry point for component labeling program
     */
    public static void main(String[] args) {
        welcome();
        inputImage();
        labelComponents();
        outputImage();
    }
}
