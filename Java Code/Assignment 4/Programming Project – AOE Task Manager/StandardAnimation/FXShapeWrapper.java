package StandardAnimation;

import Standard.Wrapper;
import Standard.WrapperActual;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

abstract class FXShapeWrapper extends FXShape {
    protected double radius = 30;

    protected StackPane stackPaneFXShapeWrapper = new StackPane();

    protected Circle circleFXShapeWrapper;

    protected Text textFXShapeWrapper = new Text();

    protected Wrapper wrapper;

    // Current Position of the circleFXShapeWrapper
    protected Point2D point2D;

    private FXShapeWrapper(PaneGraph paneGraph) {
        super(paneGraph);
    }

    public FXShapeWrapper(PaneGraph paneGraph, Wrapper wrapper) {
        this(paneGraph);
        this.wrapper = wrapper;
    }

    public FXShapeWrapper(PaneGraph paneGraph,Wrapper wrapper, double radius) {
        this(paneGraph,wrapper);
        this.radius = radius;
    }
    public void addToStackPaneFXShapeWrapper(){
        stackPaneFXShapeWrapper.getChildren().addAll(circleFXShapeWrapper, textFXShapeWrapper);
    }

    public StackPane getStackPaneFXShapeWrapper() {
        return stackPaneFXShapeWrapper;
    }

    protected abstract EventHandler<MouseEvent> eventHandlerMouseReleased();

    protected abstract EventHandler<MouseEvent> eventHandlerMouseDragged();

    public Point2D getPoint2D() {
        return point2D;
    }

    public abstract void setPoint2D(Point2D point2D);

    public Circle getCircleFXShapeWrapper() {
        return circleFXShapeWrapper;
    }

    public void changeColor(Color color) {
        circleFXShapeWrapper.setFill(color);
    }

    public Wrapper getWrapper() {
        return wrapper;
    }
}
