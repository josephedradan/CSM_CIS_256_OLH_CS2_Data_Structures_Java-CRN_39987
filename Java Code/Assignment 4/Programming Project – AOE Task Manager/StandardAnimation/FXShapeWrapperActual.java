/**
 * Joseph Edradan
 * <p>
 * This file is the FX version of a WrapperActual
 */

package StandardAnimation;

import Standard.WrapperActual;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


class FXShapeWrapperActual extends FXShapeWrapper {

    private PaneFXShapeWrapperInformation paneFXShapeWrapperInformation;

    private WrapperActual wrapperActualNormal;

    private WrapperActual wrapperActualInverse;

    public FXShapeWrapperActual(PaneGraph paneGraph, WrapperActual wrapperActual, WrapperActual wrapperActualInverse) {
        this(paneGraph, wrapperActual, wrapperActualInverse, 30);
    }

    public FXShapeWrapperActual(PaneGraph paneGraph, WrapperActual wrapperActualNormal, WrapperActual wrapperActualInverse, double radius) {
        super(paneGraph,wrapperActualNormal,radius);

        this.wrapperActualNormal = wrapperActualNormal;
        this.wrapperActualInverse = wrapperActualInverse;

        circleFXShapeWrapper = new Circle(radius);

        circleFXShapeWrapper.setFill(Color.WHITE);
        circleFXShapeWrapper.setStroke(Color.BLACK);

        // Text of node
        textFXShapeWrapper.setText(wrapperActualNormal.getVertex().getEventName());

        // Do not change the Layout, it will effect mouse drag cursor relative to the StackPane
//        stackPaneFXShapeWrapper.setLayoutX(0);
//        stackPaneFXShapeWrapper.setLayoutY(0);

        // Add Event Handlers
        addEventHandlers();

        addToStackPaneFXShapeWrapper();

        createPaneFXShapeWrapperInformation();
    }

    public void createPaneFXShapeWrapperInformation(){
        paneFXShapeWrapperInformation = new PaneFXShapeWrapperInformation(this);

        paneFXShapeWrapperInformation.setTextCountForward(wrapperActualInverse.getArrayListWrapperPseudo().size());
        paneFXShapeWrapperInformation.setTextCountBackward(wrapperActualNormal.getArrayListWrapperPseudo().size());

        stackPaneFXShapeWrapper.getChildren().add(paneFXShapeWrapperInformation);
    }

    // This is used for user editing the pane for a custom adjacencyList
    public void removePaneFXShapeWrapperInformationAndWrappers(){
        stackPaneFXShapeWrapper.getChildren().remove(paneFXShapeWrapperInformation);
        wrapperActualNormal = null;
        wrapperActualInverse = null;
    }


    // When Node is released on the paneGraph then apply the normal vector force onto it
    @Override
    protected EventHandler<MouseEvent> eventHandlerMouseReleased() {
        return new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                paneGraph.setCurrentDraggedNode(null);

            }
        };
    }

    @Override
    protected EventHandler<MouseEvent> eventHandlerMouseDragged() {
        FXShapeWrapperActual fxShapeWrapperActualThis = this;

        return new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                // Note that event is a StackPane

                double mouseX = event.getSceneX();
                double mouseY = event.getSceneY();

//                System.out.println(String.format("Mouse Position %s %s", mouseX, mouseY));
                paneGraph.setCurrentDraggedNode(fxShapeWrapperActualThis);

                paneGraph.setNodeLocation(fxShapeWrapperActualThis, new Point2D(mouseX, mouseY));
            }
        };
    }

    public WrapperActual getWrapperActualNormal() {
        return wrapperActualNormal;
    }

    public WrapperActual getWrapperActualInverse() {
        return wrapperActualInverse;
    }


    @Override
    public void setPoint2D(Point2D point2D) {
        this.point2D = point2D;

        // shiftOffsetWidth because StackPane is bottom left Corner based so you need to
        // shift to get the center of the circleFXShapeWrapper where the mouse is
        double shiftOffsetWidth = stackPaneFXShapeWrapper.getWidth() / 2;
        double shiftOffsetHeight = stackPaneFXShapeWrapper.getHeight() / 2;

        stackPaneFXShapeWrapper.setTranslateX(point2D.getX() - shiftOffsetWidth);
        stackPaneFXShapeWrapper.setTranslateY(point2D.getY() - shiftOffsetHeight);

        paneFXShapeWrapperInformation.updateTextPositions();
    }

    public PaneFXShapeWrapperInformation getPaneFXShapeWrapperInformation() {
        return paneFXShapeWrapperInformation;
    }

    @Override
    public void addEventHandlers() {
        // Add clickable stuff
//        stackPaneFXShapeWrapper.setOnMouseDragged(eventHandlerMouseDragged());
//        stackPaneFXShapeWrapper.setOnMouseReleased(eventHandlerMouseReleased());

        // Add clickable stuff very specific way
        stackPaneFXShapeWrapper.addEventHandler(MouseEvent.MOUSE_DRAGGED,eventHandlerMouseDragged());
        stackPaneFXShapeWrapper.addEventHandler(MouseEvent.MOUSE_RELEASED,eventHandlerMouseReleased());
    }

    @Override
    public void addToPaneGraph() {
        paneGraph.getChildren().add(stackPaneFXShapeWrapper);

    }

    @Override
    public void removeFromPaneGraph() {
        paneGraph.getChildren().remove(stackPaneFXShapeWrapper);

    }

    @Override
    public String toString() {
        String string = String.format("%s %s", wrapperActualNormal, wrapperActualInverse);
        return string;
    }
}
