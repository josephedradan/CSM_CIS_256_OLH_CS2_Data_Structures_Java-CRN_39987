/**
 * Joseph Edradan
 * <p>
 * This file is the FX version of an Edge
 */

package StandardAnimation;

class FXShapeEdgeActual extends FXShapeEdge {

    private FXShapeWrapperActual fxShapeWrapperActualParent;

    private FXShapeWrapperActual fxShapeWrapperActualChild;

    private EdgeAnimationDataContainer edgeAnimationDataContainer;

    private FXAnimationShapeEdge fxAnimationShapeEdgeForward;

    private FXAnimationShapeEdge fxAnimationShapeEdgeBackward;


    public FXShapeEdgeActual(PaneGraph paneGraph, FXShapeWrapperActual fxShapeWrapperActualParent, FXShapeWrapperActual fxShapeWrapperActualChild, EdgeAnimationDataContainer edgeAnimationDataContainer) {
        super(paneGraph);

        this.fxShapeWrapperActualParent = fxShapeWrapperActualParent;
        this.fxShapeWrapperActualChild = fxShapeWrapperActualChild;

        this.edgeAnimationDataContainer = edgeAnimationDataContainer;

        arrow = new Arrow(edgeAnimationDataContainer);

        fxAnimationShapeEdgeForward = new FXAnimationShapeEdge(this, false);
        fxAnimationShapeEdgeBackward = new FXAnimationShapeEdge(this, true);
    }

    // Update the Starting and Ending position of the lineConnecting
    @Override
    public void updateEdge() {

        arrow.updateArrow(fxShapeWrapperActualParent.getPoint2D(), fxShapeWrapperActualChild.getPoint2D());

        // Update FXAnimationShapeEdge animation position
        fxAnimationShapeEdgeForward.updatePoint2D();
        fxAnimationShapeEdgeBackward.updatePoint2D();
    }

    public FXShapeWrapperActual getFXShapeWrapperParent() {
        return fxShapeWrapperActualParent;
    }

    public FXShapeWrapperActual getFXShapeWrapperChild() {
        return fxShapeWrapperActualChild;
    }

    public EdgeAnimationDataContainer getEdgeAnimationDataContainer() {
        return edgeAnimationDataContainer;
    }

    public FXAnimationShapeEdge getFXAnimationShapeEdgeForward() {
        return fxAnimationShapeEdgeForward;
    }

    public FXAnimationShapeEdge getFXAnimationShapeEdgeBackward() {
        return fxAnimationShapeEdgeBackward;
    }


    @Override
    public void addEventHandlers() {
        // There is none... for now I think...
    }

    @Override
    public void addToPaneGraph() {
        paneGraph.getChildren().add(arrow.getGroupArrowLines());

        paneGraph.getChildren().add(fxAnimationShapeEdgeForward.getLineToBeAnimated());
        paneGraph.getChildren().add(fxAnimationShapeEdgeBackward.getLineToBeAnimated());

        paneGraph.getChildren().add(arrow.getGroupArrowLinesText());

    }

    public void removeFromPaneGraph() {
        paneGraph.getChildren().remove(arrow.getGroupArrowLines());

        paneGraph.getChildren().remove(fxAnimationShapeEdgeForward.getLineToBeAnimated());
        paneGraph.getChildren().remove(fxAnimationShapeEdgeBackward.getLineToBeAnimated());

        paneGraph.getChildren().remove(arrow.getGroupArrowLinesText());
    }

    @Override
    public String toString() {
        String string = String.format("%s %s %s", fxShapeWrapperActualParent, fxShapeWrapperActualChild, edgeAnimationDataContainer.getWrapperPseudo().getEdge());
        return string;
    }
}
