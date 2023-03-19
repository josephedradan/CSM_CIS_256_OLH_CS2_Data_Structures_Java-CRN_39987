/**
 * Joseph Edradan
 * <p>
 * This is a custom made Pane made for the animations and the graph etc...
 */
package StandardAnimation;

import NotMine.FxMath;
import Standard.AdjacencyList;
import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.control.Alert;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.MenuItem;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

class PaneGraph extends Pane {

    private static final Color COLOR_BACKGROUND = Color.rgb(255, 255, 255);

    private Rectangle rectangleBackground = new Rectangle();

    // Gravity between nodes basically
    public static final double SPRING_FORCE = 1;            // force = SPRING_FORCE*Math.log(distance/SPRING_SCALE);

    // How big the spring is between each node (The length of spring or string that holds the nodes together)
    public static final double SPRING_SCALE = 100;

    // The push distance
    public static final double REPULSION_SCALE = 1000;      // repulsion distance = REPULSION_SCALE/distance²;

    // Speed of Animating movement of nodes
    public static final double ANIMATION_SPEED = 200;

    // Speed of Edge animation TODO: Not working right now...
    public static final double ANIMATION_SPEED_EDGE = 2;    // This speed can be ignored by the user, look inside of FXAnimationShapeEdge

    // Nodes and their position
    private Map<FXShapeWrapper, Point2D> nodeLocations = new HashMap<>();    // R² coordinates for each node

    // Nodes force vector relative to each Node on the nodeLocation
    private Map<FXShapeWrapper, Point2D> nodeTotalForce = new HashMap<>();

    // Requires a FXAdjacencyListActual to draw anything
    private FXAdjacencyListActual fxAdjacencyListActual;

    // For edit mode
    private FXAdjacencyListPseudo fxAdjacencyListPseudo = new FXAdjacencyListPseudo(this);

    // Simulation movement
    private boolean simulationActive = true;

    // The thing that runs the animation
    private AnimationTimer animationTimer;

    // FPS of the animation
    private double stepsPerFrame = 20;                      //simulation steps computed per rendered frame

    // Current dragged node does not have applied force
    private FXShapeWrapper currentDraggedNode = null;       // Node currently being dragged

    // The ContextMenuGraph for the graph AKA the right click menu
    private ContextMenuGraph contextMenuGraph;

    public PaneGraph() {
        this.setBackground(new Background(new BackgroundFill(COLOR_BACKGROUND, CornerRadii.EMPTY, Insets.EMPTY)));
        rectangleBackground.setFill(COLOR_BACKGROUND);
        rectangleBackground.setStroke(COLOR_BACKGROUND);
        this.getChildren().add(rectangleBackground);

    }

    public PaneGraph(AdjacencyList adjacencyList) {
        this();

        // Only for adjacencyList
        setFXAdjacencyListActual(adjacencyList);

        // This needs to be here because a FXAdjacencyListActual must be created
        contextMenuGraph = new ContextMenuGraph(this);

        addFXAdjacencyListToPaneGraph(this.fxAdjacencyListActual);

        initializeMouseEvents();

        generateFXShapeWrapperSpawns();
    }

    private void addFXAdjacencyListToPaneGraph(FXAdjacencyList fxAdjacencyList) {
        // Order in which the shapes will be on the pane
        addFXEdgeToPane(fxAdjacencyList);
        addFXWrapperToPane(fxAdjacencyList);
    }

    private void setFXAdjacencyListActual(AdjacencyList adjacencyList) {
        if (adjacencyList.getAdjacencyListInverse() == null) {
            adjacencyList.runDFS();
        }

        if(adjacencyList.isGraphCyclic()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("PathFinder found that the Loaded AdjacencyList is Cyclic, Animation may not run correctly!");
            alert.showAndWait();
        }

        this.fxAdjacencyListActual = new FXAdjacencyListActual(this, adjacencyList);
    }


    public void runAnimation() {
        PaneGraph paneGraph = this;
        animationTimer = new AnimationTimer() {

            // This Method is called all the time. It's the main game loop basically
            @Override
            public void handle(long now) {
                rectangleBackground.setWidth(paneGraph.getWidth());
                rectangleBackground.setHeight(paneGraph.getHeight());

                if (simulationActive) {
                    for (int i = 0; i < stepsPerFrame; i++) {

                        // If Edit Mode is off then Run Physics Simulation
                        if (!contextMenuGraph.getCheckMenuItemEditMode().isSelected()) {
                            simulateSingleStepForFXShapeWrapperActual(currentDraggedNode);
                        }
                    }
                }
                updateFXShapePositions();

            }
        };

        animationTimer.start();

    }

    // StackPane Circle and Text
    private void addFXWrapperToPane(FXAdjacencyList fxAdjacencyList) {
        for (FXShapeWrapper fxShapeWrapper : fxAdjacencyList.getArrayListFXShapeWrapper()) {
            fxShapeWrapper.addToPaneGraph();
        }
    }

    // StackPane Line and Text
    private void addFXEdgeToPane(FXAdjacencyList fxAdjacencyList) {
        for (FXShapeEdge fxShapeEdge : fxAdjacencyList.getArrayListFXShapeEdge()) {
            fxShapeEdge.addToPaneGraph();
        }
    }

    // Handles Drag, Click, and Release, Move.
    private void initializeMouseEvents() {

        this.setOnMouseClicked(event ->{
            if(contextMenuGraph.getContextMenu().isShowing()){
                contextMenuGraph.getContextMenu().hide();
            }
        });

//        this.setEventHandler(MouseEvent.MOUSE_CLICKED, eventEventHandlerMouseAddFXShapeWrapperPseudo());


//        this.removeEventHandler();

//        setOnMouseDragged(event -> {
//            if (currentDraggedNode != null) { // drag node
////                System.out.println(currentDraggedNode);
//                Point2D location = new Point2D(event.getX(), event.getY());
//                setNodeLocation(currentDraggedNode, location);
//            }
////            else {
////                shiftX = shiftXBuffer + (event.getX() - cursorPressedX);
////                shiftY = shiftYBuffer + (event.getY() - cursorPressedY);
////            }
//
//        });

//        setOnMousePressed(event -> {
//            currentDraggedNode = get(event.getX(), event.getY());
//            if (currentDraggedNode == null) {
//                cursorPressedX = event.getX();
//                cursorPressedY = event.getY();
//                shiftXBuffer = shiftX;
//                shiftYBuffer = shiftY;
//            }
//        });
//
//        // If you release, then stop dragging
//        setOnMouseReleased(event -> currentDraggedNode = null);
//
//        setOnMouseMoved(event -> {
//            tooltipNode = checkForMouseNodeCollision(event.getX(), event.getY());
//            if (tooltipNode != null) {
//                this.getScene().setCursor(Cursor.HAND);
//            } else {
//                this.getScene().setCursor(Cursor.DEFAULT);
//            }
//        });
//
//        setOnMouseClicked(event -> {
//            Vertex<V> node = checkForMouseNodeCollision(event.getX(), event.getY());
//            if (clickConsumer != null && node != null) {
//                clickConsumer.accept(node);
//            }
//        });


    }

    //     ALTERNATIVE DON'T TOUCH
//    private EventHandler<MouseEvent> eventEventHandlerMouseAddFXShapeWrapperPseudo(PaneGraph paneGraph, FXAdjacencyListPseudo fxAdjacencyListPseudo) {
//        return new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                if (contextMenuGraph.getCheckMenuItemEditMode().isSelected()) {
//                    if (event.getButton().equals(MouseButton.PRIMARY)) {
//                        if (event.getClickCount() == 2) {
//                            TextInputDialog dialog = new TextInputDialog("");
//                            dialog.setHeaderText(null);
//                            dialog.setGraphic(null);
//                            dialog.setTitle("New Event");
////                        dialog.setHeaderText("Test");
//                            dialog.setContentText("Enter Event Name");
//                            Optional<String> eventName = dialog.showAndWait();
//                            if (eventName.isPresent()) {
//                                Point2D point2D = new Point2D(event.getX(), event.getY());
//
//                                FXShapeWrapperPseudo fxShapeWrapperPseudo = fxAdjacencyListPseudo.createFXShapeWrapperPseudoUser(paneGraph, eventName.get(), point2D);
//
//                                paneGraph.generateFXShapeWrapperPredefinedSpawn(fxShapeWrapperPseudo);
//                            }
//                        }
//                    }
//                }
//            }
//        };
//    }

    private void generateFXShapeWrapperSpawns() {
        nodeLocations.clear();
        nodeTotalForce.clear();

        for (FXShapeWrapper fxShapeWrapper : fxAdjacencyListActual.getArrayListFXShapeWrapper()) {

//            double widthHalf = this.getPrefWidth() / 2;
//            double heightHalf = this.getPrefHeight() / 2;

//            double locationX = (Math.random() * (((widthHalf + widthHalf / 10) - (widthHalf - widthHalf / 10)) + 1)) + widthHalf;
//            double locationY = (Math.random() * (((heightHalf + heightHalf / 10) - (heightHalf - heightHalf / 10)) + 1)) + heightHalf;

            double locationX = (Math.random() * ((this.getPrefWidth() - 0) + 1) + 0);
            double locationY = (Math.random() * ((this.getPrefHeight() - 0) + 1) + 0);

//            System.out.println(locationX);
//            System.out.println(locationY);
//            System.out.println();

            nodeLocations.put(fxShapeWrapper, new Point2D(locationX, locationY));
        }
    }

    public void generateFXShapeWrapperPredefinedSpawns(FXAdjacencyList fxAdjacencyList) {
        nodeLocations.clear();
        nodeTotalForce.clear();

        for (FXShapeWrapper fxShapeWrapper : fxAdjacencyList.getArrayListFXShapeWrapper()) {

            generateFXShapeWrapperPredefinedSpawn(fxShapeWrapper);
        }
    }

    public void generateFXShapeWrapperPredefinedSpawn(FXShapeWrapper fxShapeWrapper) {
        nodeLocations.put(fxShapeWrapper, new Point2D(fxShapeWrapper.getPoint2D().getX(), fxShapeWrapper.getPoint2D().getY()));
    }


    // This method is spam called all the time, it is part of the handle method from the AnimationTimer()
    public void simulateSingleStepForFXShapeWrapperActual(FXShapeWrapper ignoreNode) {
        initForces();
        computeForces();
        applyForce(ignoreNode);
    }

    // This method calculates the initial nodeTotalForce vectors (math/physics definition)
    private void initForces() {
        // Lambda for getting the mapping of FXShapeWrapperActual, Point2D combination and making a similar mapping for FXShapeWrapperActual, Point2D combination
        nodeLocations.forEach((fxShapeWrapper, point2D) -> nodeTotalForce.put(fxShapeWrapper, new Point2D(0, 0)));
    }

    // This method is spam called all the time (2), it is part of the handle from the timer
    private void computeForces() {
        for (Map.Entry<FXShapeWrapper, Point2D> outerNode : nodeLocations.entrySet()) {
            for (Map.Entry<FXShapeWrapper, Point2D> innerNode : nodeLocations.entrySet()) {

                // If self then skip (if this wasn't here then you will get not a number)
                if (outerNode.getKey() == innerNode.getKey()) {
                    continue;
                }

                // Calculate force between 2 points in space
                Point2D repellingForce = FxMath.repellingForce(outerNode.getValue(), innerNode.getValue(), REPULSION_SCALE);

                // Get the force vector (math/physics vector) of the outer node
                Point2D outerNodeForce = nodeTotalForce.get(outerNode.getKey());

                // Check if nodes are connected
                if (fxAdjacencyListActual.getAdjacencyList().areWrappersConnected(outerNode.getKey().getWrapper(), innerNode.getKey().getWrapper())) {

                    int numberOfVertices = fxAdjacencyListActual.getAdjacencyList().getArrayListWrapperActual().size();

                    // Only nodes connected have an attraction
                    Point2D attractiveForce = FxMath.attractiveForce(outerNode.getValue(), innerNode.getValue(), numberOfVertices, SPRING_FORCE, SPRING_SCALE);

                    // This is repulsion and attraction vector (math/physics vector) between nodes connected (The attraction should cancel the attraction)
                    nodeTotalForce.replace(outerNode.getKey(), new Point2D(
                            outerNodeForce.getX() + attractiveForce.getX() + repellingForce.getX(),
                            outerNodeForce.getY() + attractiveForce.getY() + repellingForce.getY()
                    ));
                } else {

                    // This is the repulsion vector (math/physics vector) between all nodes if there is no connections between nodes
                    nodeTotalForce.replace(outerNode.getKey(), new Point2D(
                            outerNodeForce.getX() + repellingForce.getX(),
                            outerNodeForce.getY() + repellingForce.getY()
                    ));
                }
            }
        }
    }

    // This method applies the Force Vector from nodeTotalForce to the node's Point2D
    private void applyForce(FXShapeWrapper ignoreNode) {
        Set<Map.Entry<FXShapeWrapper, Point2D>> entries = nodeLocations.entrySet();

        for (Map.Entry<FXShapeWrapper, Point2D> node : entries) {
            FXShapeWrapper currentNode = node.getKey();

            // If node is being dragged then skip physics
            if (currentNode == ignoreNode) {
                continue;
            }

            // Get the Point2D position of FXShapeWrapperActual
            Point2D point = node.getValue();

            // Get the Point2D force vector of FXShapeWrapperActual
            Point2D forceVector = nodeTotalForce.get(currentNode);

            double positionX = point.getX() + ANIMATION_SPEED * forceVector.getX();
            double positionY = point.getY() + ANIMATION_SPEED * forceVector.getY();

            double radius = node.getKey().getCircleFXShapeWrapper().getRadius();
            double offset = node.getKey().getCircleFXShapeWrapper().getRadius();

            // Handle border collision by changing the node position
            if (positionX < 0 + radius + offset) {
                positionX = 0 + radius + offset;
            }
            if (positionX > this.getWidth() - (radius + offset)) {
                positionX = this.getWidth() - (radius + offset);
            }
            if (positionY < 0 + radius + offset) {
                positionY = 0 + radius + offset;
            }
            if (positionY > this.getHeight() - (radius + offset)) {
                positionY = this.getHeight() - (radius + offset);
            }

            Point2D point2D = new Point2D(positionX, positionY);

            if (Double.isNaN(positionX) || Double.isNaN(positionY)) {
                generateFXShapeWrapperSpawns();
                System.out.println("Animation can't run giveFXAdjacencyListActual properly, try increasing window resolution!");
                break;
            }
            node.setValue(point2D);
//            System.out.println(node.getKey().getPoint2D());
        }
    }

    public void updateFXShapePositions() {
        updateFXShapeWrapperPosition();

        // Edit Mode On
        if (!contextMenuGraph.getCheckMenuItemEditMode().isSelected()) {
            updateFXShapeEdgePosition(fxAdjacencyListActual);
        }
        // Edit Mode Off
        else {
            updateFXShapeEdgePosition(fxAdjacencyListPseudo);
        }
    }


    private void updateFXShapeWrapperPosition() {
        // Loop through nodeLocations to get the key
        for (FXShapeWrapper fxShapeWrapper : nodeLocations.keySet()) {

            // For fxShapeWrapper, get the Point2D within the nodeLocations given a fxShapeWrapper
            Point2D point2D = nodeLocations.get(fxShapeWrapper);

            // Replace the fxShapeWrapper Point2D with the one from nodeLocations
            fxShapeWrapper.setPoint2D(point2D);

            updateSelectedFXShapeWrapper(fxShapeWrapper);

        }

    }

    private void updateFXShapeEdgePosition(FXAdjacencyList fxAdjacencyList) {
        // Loop through fxAdjacencyList.getArrayListFXShapeEdge()
        for (FXShapeEdge fxShapeEdge : fxAdjacencyList.getArrayListFXShapeEdge()) {

            // Update the fxShapeEdge Point2D points
            fxShapeEdge.updateEdge();
        }
    }

    private void updateSelectedFXShapeWrapper(FXShapeWrapper fxShapeWrapper) {
        if(fxShapeWrapper instanceof FXShapeWrapperPseudo){
            if(fxShapeWrapper == fxAdjacencyListPseudo.fxShapeWrapperPseudoStart || fxShapeWrapper == fxAdjacencyListPseudo.getFxShapeWrapperPseudoEnd){
                fxShapeWrapper.getCircleFXShapeWrapper().setFill(Color.GOLD);
            } else{
                fxShapeWrapper.getCircleFXShapeWrapper().setFill(Color.WHITE);
            }
        }
    }
    public void setNodeLocation(FXShapeWrapper node, Point2D location) {
        nodeLocations.replace(node, location);
    }

    public void setCurrentDraggedNode(FXShapeWrapper fxShapeWrapperActual) {
        currentDraggedNode = fxShapeWrapperActual;
    }

    public FXAdjacencyListActual getFxAdjacencyListActual() {
        return fxAdjacencyListActual;
    }

    public Rectangle getRectangleBackground() {
        return rectangleBackground;
    }

    public void enableEditMode() {
        // Remove all children from the paneGraph currently
        this.getChildren().clear();

        // Re add rectangleBackground so you can right click FIXME: Dirty...
        this.getChildren().add(getRectangleBackground());

        // Run getFxAdjacencyListPseudo
        fxAdjacencyListPseudo.giveFXAdjacencyListActual(fxAdjacencyListActual);

        fxAdjacencyListPseudo.createFXAdjacencyListPseudoFromPreexistingFXAdjacencyList();

        addFXAdjacencyListToPaneGraph(fxAdjacencyListPseudo);

        generateFXShapeWrapperPredefinedSpawns(fxAdjacencyListPseudo);
    }

    public void enableNormalMode() {
        // Remove all children from the paneGraph currently
        this.getChildren().clear();

        // Re add rectangleBackground so you can right click FIXME: Dirty...
        this.getChildren().add(getRectangleBackground());

        // TODO THE FINAL
        fxAdjacencyListPseudo.createFXAdjacencyListActual();

        FXAdjacencyListActual fxAdjacencyListActualNew = fxAdjacencyListPseudo.getFxAdjacencyListActualNew();

        fxAdjacencyListActual = fxAdjacencyListActualNew;

        addFXAdjacencyListToPaneGraph(fxAdjacencyListActual);

        generateFXShapeWrapperPredefinedSpawns(fxAdjacencyListActual);
    }

    public FXAdjacencyListPseudo getFxAdjacencyListPseudo() {
        return fxAdjacencyListPseudo;
    }

    public void runCustomAdjacencyList(AdjacencyList adjacencyList){
//        System.out.println(adjacencyList);
        // Remove all children from the paneGraph currently
        this.getChildren().clear();

        // Re add rectangleBackground so you can right click FIXME: Dirty...
        this.getChildren().add(getRectangleBackground());

        setFXAdjacencyListActual(adjacencyList);

        addFXAdjacencyListToPaneGraph(this.fxAdjacencyListActual);

        generateFXShapeWrapperSpawns();
    }

    public void loadAdjacencyListExample(Map<String, AdjacencyList> mapStringAndAdjacencyList){
        contextMenuGraph.loadAdjacencyListExample(mapStringAndAdjacencyList);
    }
}
