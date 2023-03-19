import java.util.Random;

public class PixelGrid {
    private static final long seed = 100;

    private static int offset = 2;

    private int heightAbsolute;
    private int lengthAbsolute;

    private boolean enableRandom;

    private double density;

    private PixelNode[][] pixelGrid;

    public PixelGrid(int size) {
        this(size, size, .5, false);

    }

    public PixelGrid(int size, boolean enableRandom) {
        this(size, size, .5, enableRandom);

    }

    public PixelGrid(int size, double density) {
        this(size, size, density, false);

    }

    public PixelGrid(int size, double density, boolean enableRandom) {
        this(size, size, density, enableRandom);

    }

    public PixelGrid(int lengthAbsolute, int heightAbsolute, double density) {
        this(heightAbsolute, lengthAbsolute, density, false);
    }

    public PixelGrid(int length, int height, double density, boolean enableRandom) {
        this.lengthAbsolute = length + offset;
        this.heightAbsolute = height + offset;
        this.density = density;
        this.enableRandom = enableRandom;

        pixelGrid = new PixelNode[this.heightAbsolute][this.lengthAbsolute];

        // Generate pixelGrid
        generatePixelNodes();
    }

    // Copy constructor
    PixelGrid(PixelGrid pixelGridToBeCopied) {
        this.heightAbsolute = pixelGridToBeCopied.heightAbsolute;
        this.lengthAbsolute = pixelGridToBeCopied.lengthAbsolute;
        this.density = pixelGridToBeCopied.density;
        this.enableRandom = pixelGridToBeCopied.enableRandom;

        pixelGrid = new PixelNode[this.heightAbsolute][this.lengthAbsolute];

        // Generate pixelGrid
//        this.generatePixelNodes(pixelGridToBeCopied.getPixelGridPrimitive());
        this.generatePixelNodes(pixelGridToBeCopied.getPixelGrid());

    }

    private void generatePixelNodes() {
        // Used for generating values
        Random random = new Random();

        if (!enableRandom) {
            random.setSeed(seed);
        }

        for (int i = 0; i < heightAbsolute; i++) {
            for (int j = 0; j < lengthAbsolute; j++) {
                // Borders will never have value of 1
                // Simplify !(i == 0 || (i == size + 1)) && !(j == 0 || j == size + 1)
                if (!(i == 0 || j == 0 || i == heightAbsolute - 1 || j == lengthAbsolute - 1)) {
                    if (random.nextDouble() <= density) { // density, recall that bounds are 0 to 1
//                    pixelGrid[i][j] = new PixelNode(i,j);
                        pixelGrid[i][j] = new PixelNode(i, j, 1);
                    } else {
                        pixelGrid[i][j] = new PixelNode(i, j, 0);
                    }
                } else {
                    pixelGrid[i][j] = new PixelNode(i, j, 0);
                }
            }
        }
    }

    private void generatePixelNodes(int[][] tempIntArray) {
        for (int i = 0; i < heightAbsolute; i++) {
            for (int j = 0; j < lengthAbsolute; j++) {
                pixelGrid[i][j] = new PixelNode(i, j, tempIntArray[i][j]);
            }
        }
    }

    private void generatePixelNodes(PixelNode[][] tempPixelGrid) {
        for (int i = 0; i < heightAbsolute; i++) {
            for (int j = 0; j < lengthAbsolute; j++) {
                pixelGrid[i][j] = new PixelNode(i, j, tempPixelGrid[i][j].getNodeComponentLabel());
            }
        }
    }

    public int[][] getPixelGridPrimitive() {
        int[][] tempArray = new int[this.heightAbsolute][this.lengthAbsolute];

        for (int i = 0; i < heightAbsolute; i++) {
            for (int j = 0; j < lengthAbsolute; j++) {
                tempArray[i][j] = pixelGrid[i][j].getNodeComponentLabel();
            }
        }
        return tempArray;
    }

    public void printPixelGridPrimitive() {
        for (int i = 0; i < heightAbsolute; i++) {
            for (int j = 0; j < lengthAbsolute; j++) {
                System.out.print(String.format("%3s", pixelGrid[i][j].getNodeComponentLabel()));
            }
            System.out.println();
        }
    }

    public void printPixelGridPrimitiveBorderless() {
        for (int i = 1; i < heightAbsolute - 1; i++) {
            for (int j = 1; j < lengthAbsolute - 1; j++) {
                System.out.print(String.format("%3s", pixelGrid[i][j].getNodeComponentLabel()));
            }
            System.out.println();
        }
    }

    public void printPixelGridPrimitiveDiscoveryOrderBorderless() {
        for (int i = 1; i < heightAbsolute - 1; i++) {
            for (int j = 1; j < lengthAbsolute - 1; j++) {
                System.out.print(String.format("%3s", pixelGrid[i][j].getOrderOfDiscovery()));
            }
            System.out.println();
        }
    }

    public void printPixelGridPrimitiveAssignmentBorderless() {
        for (int i = 1; i < heightAbsolute - 1; i++) {
            for (int j = 1; j < lengthAbsolute - 1; j++) {
                System.out.print(String.format("(%3s, %3s)",
                        pixelGrid[i][j].getNodeComponentLabel(),
                        pixelGrid[i][j].getOrderOfDiscovery()));
            }
            System.out.println();
        }
    }
    public PixelNode getPixelNode(int row, int column) {
        return pixelGrid[row][column];
    }

    public PixelNode[][] getPixelGrid() {
        return pixelGrid;
    }

    public int getHeightAbsolute() {
        return heightAbsolute;
    }

    public int getLengthAbsolute() {
        return lengthAbsolute;
    }


}
