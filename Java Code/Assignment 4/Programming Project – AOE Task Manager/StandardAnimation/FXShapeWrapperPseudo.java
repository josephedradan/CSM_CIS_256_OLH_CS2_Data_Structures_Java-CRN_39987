/**
 * Notes:
 * https://stackoverflow.com/questions/44147595/get-more-than-two-inputs-in-a-javafx-dialog
 * https://stackoverflow.com/questions/237159/whats-the-best-way-to-check-if-a-string-represents-an-integer-in-java
 * https://code.makery.ch/blog/javafx-dialogs-official/
 */

package StandardAnimation;


import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.Optional;

public class FXShapeWrapperPseudo extends FXShapeWrapper {

    private FXShapeWrapperActual fxShapeWrapperActual;

    private boolean isVertexStarting = false;

    private boolean isVertexEnding = false;

    private boolean isSelected = false;

    private int index; // This will change by the user

    private String eventName;

    private ArrayList<FXShapeWrapperPseudo> arrayListFXShapeWrapperPseudo = new ArrayList();


    public FXShapeWrapperPseudo(PaneGraph paneGraph, String eventName, int indexTemp, Point2D point2D) {
        super(paneGraph, null);

        this.point2D = point2D;

        this.eventName = eventName;

        this.index = indexTemp;

        followingCall();
    }

    public FXShapeWrapperPseudo(PaneGraph paneGraph, FXShapeWrapperActual fxShapeWrapperActual) {
        super(paneGraph, fxShapeWrapperActual.getWrapper());

        this.fxShapeWrapperActual = fxShapeWrapperActual;

        this.point2D = new Point2D(fxShapeWrapperActual.getPoint2D().getX(), fxShapeWrapperActual.getPoint2D().getY());

        this.eventName = fxShapeWrapperActual.getWrapperActualNormal().getVertex().getEventName();

        this.index = fxShapeWrapperActual.getWrapperActualNormal().getVertex().getIndex();

        followingCall();
    }

    private void followingCall() {
        circleFXShapeWrapper = new Circle(radius);

        circleFXShapeWrapper.setFill(Color.WHITE);
        circleFXShapeWrapper.setStroke(Color.BLACK);

        // Text of node
        textFXShapeWrapper.setText(eventName);

        // Add Event Handlers
        addEventHandlers();

        addToStackPaneFXShapeWrapper();
    }

    public FXShapeWrapperActual getFxShapeWrapperActual() {
        return fxShapeWrapperActual;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public String getEventName() {
        return eventName;
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
    }

    public ArrayList<FXShapeWrapperPseudo> getArrayListFXShapeWrapperPseudo() {
        return arrayListFXShapeWrapperPseudo;
    }

    public boolean isVertexStarting() {
        return isVertexStarting;
    }

    public void setVertexStarting(boolean vertexStarting) {
        isVertexStarting = vertexStarting;
    }

    public boolean isVertexEnding() {
        return isVertexEnding;
    }

    public void setVertexEnding(boolean vertexEnding) {
        isVertexEnding = vertexEnding;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
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
        FXShapeWrapperPseudo fxShapeWrapperPseudo = this;

        return new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                // Note that event is a StackPane

                double mouseX = event.getSceneX();
                double mouseY = event.getSceneY();

//                System.out.println(String.format("Mouse Position %s %s", mouseX, mouseY));
                paneGraph.setCurrentDraggedNode(fxShapeWrapperPseudo);

                paneGraph.setNodeLocation(fxShapeWrapperPseudo, new Point2D(mouseX, mouseY));
            }
        };
    }

    private EventHandler<MouseEvent> eventEventHandlerMouseConnectNodes(PaneGraph paneGraph, FXAdjacencyListPseudo fxAdjacencyListPseudo, FXShapeWrapperPseudo fxShapeWrapperPseudo) {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    if (event.getClickCount() == 2) {
                        if (fxAdjacencyListPseudo.fxShapeWrapperPseudoStart == null) {
                            fxAdjacencyListPseudo.fxShapeWrapperPseudoStart = fxShapeWrapperPseudo;
                            System.out.println("1");
                        } else if (fxAdjacencyListPseudo.getFxShapeWrapperPseudoEnd == null && !fxAdjacencyListPseudo.fxShapeWrapperPseudoStart.equals(fxShapeWrapperPseudo)) {
                            fxAdjacencyListPseudo.getFxShapeWrapperPseudoEnd = fxShapeWrapperPseudo;
                            System.out.println("2");

//                            Dialog<String> dialog = new Dialog<>();
//                            dialog.setTitle("Duration and Edge Name");
//                            dialog.setHeaderText(null);
//                            DialogPane dialogPane = dialog.getDialogPane();
//                            dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
//                            TextField textFieldDuration = new TextField("0");
//                            TextField textFieldName = new TextField("Name");
//                            dialogPane.setContent(new VBox(8, textFieldDuration, textFieldName));
//                            Platform.runLater(textFieldDuration::requestFocus);
//                            dialog.setResultConverter((ButtonType button) -> {
//                                if (button == ButtonType.OK) {
//                                    fxAdjacencyListPseudo.createFXShapeEdgePseudoUser(
//                                            fxAdjacencyListPseudo.fxShapeWrapperPseudoStart,
//                                            fxAdjacencyListPseudo.getFxShapeWrapperPseudoEnd,
//                                            Integer.parseInt(textFieldDuration.getText()),
//                                            textFieldName.getText());
//                                }
//                                return null;
//                            });
//                            dialog.show();

                            TextInputDialog textInputDialog = new TextInputDialog("");
                            textInputDialog.setHeaderText(null);
                            textInputDialog.setGraphic(null);
                            textInputDialog.setTitle("New Connection");
//                        textInputDialog.setHeaderText("Test");
                            textInputDialog.setContentText("Enter Duration");
                            Optional<String> textFieldDuration = textInputDialog.showAndWait();
                            if (textFieldDuration.isPresent() && isInteger(textFieldDuration.get())){
                                fxAdjacencyListPseudo.createFXShapeEdgePseudoUser(
                                        fxAdjacencyListPseudo.fxShapeWrapperPseudoStart,
                                        fxAdjacencyListPseudo.getFxShapeWrapperPseudoEnd,
                                        Integer.parseInt(textFieldDuration.get()),
                                        textFieldDuration.get());
                            } else if (!isInteger(textFieldDuration.get())){
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Not a number");
                                alert.setHeaderText(null);
                                alert.setContentText(null);
                                alert.show();
                                fxAdjacencyListPseudo.fxShapeWrapperPseudoStart = null;
                                fxAdjacencyListPseudo.getFxShapeWrapperPseudoEnd = null;
                            }
                        } else {
                            fxAdjacencyListPseudo.fxShapeWrapperPseudoStart = null;
                            fxAdjacencyListPseudo.getFxShapeWrapperPseudoEnd = null;
                        }
                    }
                }
            }
        };
    }

    @Override
    public void addEventHandlers() {
        stackPaneFXShapeWrapper.addEventHandler(MouseEvent.MOUSE_DRAGGED, eventHandlerMouseDragged());
        stackPaneFXShapeWrapper.addEventHandler(MouseEvent.MOUSE_RELEASED, eventHandlerMouseReleased());
        stackPaneFXShapeWrapper.addEventHandler(MouseEvent.MOUSE_CLICKED, eventEventHandlerMouseConnectNodes(paneGraph, paneGraph.getFxAdjacencyListPseudo(), this));

    }

    @Override
    public void addToPaneGraph() {
        paneGraph.getChildren().add(stackPaneFXShapeWrapper);
    }

    @Override
    public void removeFromPaneGraph() {
        paneGraph.getChildren().remove(stackPaneFXShapeWrapper);
    }

    public static boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        if (length == 0) {
            return false;
        }
        int i = 0;
        if (str.charAt(0) == '-') {
            if (length == 1) {
                return false;
            }
            i = 1;
        }
        for (; i < length; i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }
}
