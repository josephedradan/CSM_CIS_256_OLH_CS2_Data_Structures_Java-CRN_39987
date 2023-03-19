public class SolverDFS extends Solver {
    // Make stack
    private ArrayStack arrayStack;

    public SolverDFS(PixelGrid pixelNodeGrid,
                     int positionInitialX,
                     int positionInitialY,
                     int componentLabel) {
        super(pixelNodeGrid, positionInitialX, positionInitialY, componentLabel);

        this.arrayStack = new ArrayStack();
    }

    public SolverDFS(PixelGrid pixelNodeGrid,
                     int positionInitialX,
                     int positionInitialY,
                     int componentLabel,
                     int arrayStackSize) {
        super(pixelNodeGrid, positionInitialX, positionInitialY, componentLabel);

        this.arrayStack = new ArrayStack(arrayStackSize);
    }

    public boolean traversePixelNodes() {
        // Add initial node and does
        routineCall();

        // Solver Traverse's Nodes
        while (!arrayStack.isEmpty()) {
            // Get the Top element of the stack and set it as the current PixelNode
            // If you want to mess this algorithm up, remove this line and then
            // setCurrentPixelNode of arrayStack.pop() in backTrack
            this.setCurrentPixelNode((PixelNode) arrayStack.peek());

            // Go to neighboring nodes by checking their label
            // Solver Looping at a node for each neighboring node
            // FIXME: CAN BE FOR LOOP
            while (true) {
//                debugCall();

                // FIXME: CAN BE SIMPLIFIED BUT WILL HAVE 3 LOOPS AS A RESULT
                if (checkNodeRelativeLabel(MoveRelative.UP)) {
                    this.goDirectionRelative(MoveRelative.UP);
                    routineCall();

                } else if (checkNodeRelativeLabel(MoveRelative.RIGHT)) {
                    this.goDirectionRelative(MoveRelative.RIGHT);
                    routineCall();


                } else if (checkNodeRelativeLabel(MoveRelative.DOWN)) {
                    this.goDirectionRelative(MoveRelative.DOWN);
                    routineCall();


                } else if (checkNodeRelativeLabel(MoveRelative.LEFT)) {
                    this.goDirectionRelative(MoveRelative.LEFT);
                    routineCall();


                } else {
                    break;
                }
            }
            // Back track if loop traverse has nowhere to go
            backTrack();

        }
        // If all traversing is done then return true
        return true;
    }

    private void routineCall() {
        // Increment Node order of discovery (Post increment due to being called first)
        // FIXME: CAN BE SIMPLIFIED
        nodeOrderOfDiscoveryCounter++;

        // Add PixelNode to list of visited, it's the final result
        this.getVisitedPixelNodes().add(this.getCurrentPixelNode());

        // Add PixelNode to arrayStack for traveling
        arrayStack.push(this.getCurrentPixelNode());

        // Change current PixelNode's label
        this.getCurrentPixelNode().setNodeComponentLabel(this.getSolverComponentLabel());

        // Assign current PixelNode's order of discovery
        this.getCurrentPixelNode().setOrderOfDiscovery(nodeOrderOfDiscoveryCounter);
    }

    private void backTrack() {
//        System.out.println("Stack Popping");

        // Return top element of stack and then Remove it
        arrayStack.pop();
    }

    private void debugCall() {
        System.out.println("Stack");
        arrayStack.printStackArray();
        System.out.println("Current Node");
        System.out.println(this.getCurrentPixelNode());
//        this.getPixelGrid().printPixelGridPrimitive();

    }
}
