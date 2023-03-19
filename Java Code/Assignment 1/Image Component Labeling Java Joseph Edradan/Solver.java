/*
Note:
    This will NOT tell you the shortest distance to the end.
    IF I USED PACKAGE A LOT OF THINGS HERE CAN BE SET TO PROTECTED.
 */

import java.util.ArrayList;

public abstract class Solver {

    // Node Counter
    protected int nodeOrderOfDiscoveryCounter = 0;
    // Current Node
    private PixelNode currentPixelNode;
    // Label of the traverse
    private int solverComponentLabel;
    // Copy of the grid
    private PixelGrid pixelGrid;
    // ArrayList of Traveled Nodes NOT THE ALGORITHM'S STACK
    private ArrayList<PixelNode> visitedPixelNodes = new ArrayList<>();

    public Solver(PixelGrid pixelGrid,
                  int positionInitialX,
                  int positionInitialY,
                  int componentLabel) {

        this.pixelGrid = pixelGrid;
        this.setCurrentPixelNode(positionInitialX, positionInitialY);
        this.solverComponentLabel = componentLabel;

    }

    public abstract boolean traversePixelNodes();

    public PixelNode getCurrentPixelNode() {
        return currentPixelNode;
    }

    public void setCurrentPixelNode(PixelNode pixelNode) {
        this.currentPixelNode = pixelNode;
    }

    public void setCurrentPixelNode(int row, int column) {
        this.currentPixelNode = pixelGrid.getPixelNode(row, column);
    }

    public void goDirectionRelative(MoveRelative direction) {
//        System.out.println(direction.getDirection());
        this.setCurrentPixelNode(
                this.currentPixelNode.getRow() + direction.getRowRelative(),
                this.currentPixelNode.getCol() + direction.getColumnRelative());
    }

    public ArrayList<PixelNode> getVisitedPixelNodes() {
        return visitedPixelNodes;
    }

    public PixelGrid getPixelGrid() {
        return pixelGrid;
    }

    public int getSolverComponentLabel() {
        return solverComponentLabel;
    }

    public boolean checkNodeRelativeLabel(MoveRelative direction) {
        return checkNodeRelativeLabel(direction.getRowRelative(), direction.getColumnRelative());
    }

    public boolean checkNodeRelativeLabel(int rowRelative, int columnRelative) {
        // WARNING: CAN CRASH IF YOU ASK FOR SOMETHING OUT OF RANGE
        return pixelGrid.getPixelNode(this.currentPixelNode.getRow() + rowRelative, this.currentPixelNode.getCol() + columnRelative).getNodeComponentLabel() == 1;
//         return pixelGrid.getPixelGrid()[this.currentPixelNode.getRow() + rowRelative][this.currentPixelNode.getCol() + columnRelative].getNodeComponentLabel() == 1;
    }

    public PixelNode getNodeRelative(MoveRelative direction) {
        return getNodeRelative(direction.getRowRelative(), direction.getColumnRelative());

    }

    public PixelNode getNodeRelative(int rowRelative, int columnRelative) {
        // WARNING: CAN CRASH IF YOU ASK FOR SOMETHING OUT OF RANGE
        return pixelGrid.getPixelNode(this.currentPixelNode.getRow() + rowRelative, this.currentPixelNode.getCol() + columnRelative);
    }

    public void printVisited() {
        for (PixelNode pixelNode : visitedPixelNodes) {
            System.out.println(pixelNode);
        }
    }
}
