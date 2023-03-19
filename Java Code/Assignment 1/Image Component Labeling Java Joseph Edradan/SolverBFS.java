public class SolverBFS extends Solver {

    // FIXME: THIS IS NOT DYNAMIC SO RUNNING WITH A PixelGrid SIZE OF 100 WILL HIT FULL SIZE ARRAY
    private ArrayQueue arrayQueue;

    public SolverBFS(PixelGrid pixelGrid,
                     int positionInitialX,
                     int positionInitialY,
                     int componentLabel) {
        super(pixelGrid, positionInitialX, positionInitialY, componentLabel);

        this.arrayQueue =  new ArrayQueue();
    }

    public SolverBFS(PixelGrid pixelGrid,
                     int positionInitialX,
                     int positionInitialY,
                     int componentLabel,
                     int arrayQueueSize) {
        super(pixelGrid, positionInitialX, positionInitialY, componentLabel);

        this.arrayQueue =  new ArrayQueue(arrayQueueSize);
    }

    @Override
    public boolean traversePixelNodes() {

        // Add the current node to the arrayQueue
        arrayQueue.add(this.getCurrentPixelNode());

        // Change current PixelNode's label
        this.getCurrentPixelNode().setNodeComponentLabel(this.getSolverComponentLabel());

        while (!arrayQueue.isEmpty()) {
//            debugCall();

            // Get head element of arrayQueue and returns it and then set it as a the current PixelNode
            arrayQueue.printQueueArray();
            this.setCurrentPixelNode(((PixelNode) arrayQueue.getFrontElement()));

            // FIXME: CAN BE SIMPLIFIED WITH A LOOP
            if (checkNodeRelativeLabel(MoveRelative.UP)) {
                routineCall(MoveRelative.UP);

            }
            if (checkNodeRelativeLabel(MoveRelative.RIGHT)) {
                routineCall(MoveRelative.RIGHT);

            }
            if (checkNodeRelativeLabel(MoveRelative.DOWN)) {
                routineCall(MoveRelative.DOWN);

            }
            if (checkNodeRelativeLabel(MoveRelative.LEFT)) {
                routineCall(MoveRelative.LEFT);

            }
            finalCall();
        }

        return true;
    }

    private void finalCall() {
        // Increment Node order of discovery (Pre increment due to being called last)
        // FIXME: CAN BE SIMPLIFIED
        ++nodeOrderOfDiscoveryCounter;

        // Add PixelNode to list of visited, it's the final result
        this.getVisitedPixelNodes().add(this.getCurrentPixelNode());

        // Assign current PixelNode's order of discovery
        this.getCurrentPixelNode().setOrderOfDiscovery(nodeOrderOfDiscoveryCounter);

        // Return Head element and also remove it from the queue
        // FIXME: CAN BE SIMPLIFIED
        arrayQueue.remove();

    }

    private void routineCall(MoveRelative direction) {
        // Add the relative node to the arrayQueue
        arrayQueue.add(this.getNodeRelative(direction));

        // Change relative PixelNode's label
        this.getNodeRelative(direction).setNodeComponentLabel(this.getSolverComponentLabel());
    }

    private void debugCall() {
        System.out.println("Queue");
        arrayQueue.printQueueArray();
        System.out.println("Current Node");
        System.out.println(this.getCurrentPixelNode());
//        this.getPixelGrid().printPixelGridPrimitive();
    }
}
