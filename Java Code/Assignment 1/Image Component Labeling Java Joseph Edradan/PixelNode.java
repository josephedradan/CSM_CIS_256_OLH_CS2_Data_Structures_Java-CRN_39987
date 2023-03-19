public class PixelNode {

    private int row;
    private int col;

    // The group number (from 2 to ...)
    private int nodeComponentLabel = 0;

    // The Discovery Order given by the search algorithm
    private int orderOfDiscovery;

    // Can position be traversed? (IS IMPLIED BY the component Label)
    // private boolean traversable; // remove

    // Distance from origin
    // private int distanceFromOrigin;

    // Has the node been visited (IS IMPLIED BY the component Label)
    // private boolean visited = false;

    // constructor
    public PixelNode(int row, int col, int nodeComponentLabel) {
        this.row = row;
        this.col = col;
        this.nodeComponentLabel = nodeComponentLabel;

    }

    // convert to string suitable for output
    public String toString() {
        return String.format("(Row: %3s | Column: %3s | Label: %3s | Discovery #: %3s)",
                row,
                col,
                nodeComponentLabel,
                orderOfDiscovery);
//        return String.format("(Y:%s, X:%s)",row,col);
    }

    public int getOrderOfDiscovery() {
        return orderOfDiscovery;
    }

    public void setOrderOfDiscovery(int orderOfDiscovery) {
        this.orderOfDiscovery = orderOfDiscovery;
    }

    public int getNodeComponentLabel() {
        return nodeComponentLabel;
    }

    public void setNodeComponentLabel(int nodeComponentLabel) {
        this.nodeComponentLabel = nodeComponentLabel;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }


}
