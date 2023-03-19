package StandardAnimation;

public class FXShapeEdgePseudo extends  FXShapeEdge{

    private final FXShapeWrapperPseudo fxShapeWrapperPseudoParent;

    private final FXShapeWrapperPseudo fxShapeWrapperPseudoChild;

    private EdgeAnimationDataContainer edgeAnimationDataContainer;

    private int edgeDuration;

    private String edgeName;

    public FXShapeEdgePseudo(PaneGraph paneGraph, FXShapeWrapperPseudo fxShapeWrapperPseudoParent, FXShapeWrapperPseudo fxShapeWrapperPseudoChild, int edgeDuration, String edgeName){
        super(paneGraph);

        this.fxShapeWrapperPseudoParent = fxShapeWrapperPseudoParent;

        this.fxShapeWrapperPseudoChild = fxShapeWrapperPseudoChild;

        this.edgeAnimationDataContainer = null;

        this.edgeDuration = edgeDuration;

        this.edgeName = edgeName;

        arrow = new Arrow();
    }

    public FXShapeEdgePseudo(PaneGraph paneGraph, FXShapeWrapperPseudo fxShapeWrapperPseudoParent, FXShapeWrapperPseudo fxShapeWrapperPseudoChild, EdgeAnimationDataContainer edgeAnimationDataContainer) {
        super(paneGraph);

        this.fxShapeWrapperPseudoParent = fxShapeWrapperPseudoParent;

        this.fxShapeWrapperPseudoChild = fxShapeWrapperPseudoChild;

        this.edgeAnimationDataContainer = edgeAnimationDataContainer;

        this.edgeDuration = this.edgeAnimationDataContainer.getWrapperPseudo().getEdge().getEdgeDuration();

        this.edgeName = this.edgeAnimationDataContainer.getWrapperPseudo().getEdge().getEdgeName();

        arrow = new Arrow(edgeAnimationDataContainer);
    }


    public int getEdgeDuration() {
        return edgeDuration;
    }

    public String getEdgeName() {
        return edgeName;
    }
    public FXShapeWrapperPseudo getFxShapeWrapperPseudoParent() {
        return fxShapeWrapperPseudoParent;
    }

    public FXShapeWrapperPseudo getFxShapeWrapperPseudoChild() {
        return fxShapeWrapperPseudoChild;
    }

    @Override
    public void addEventHandlers() {

    }

    @Override
    public void addToPaneGraph() {
        paneGraph.getChildren().add(arrow.getGroupArrowLines());
        paneGraph.getChildren().add(arrow.getGroupArrowLinesText());
    }

    @Override
    public void removeFromPaneGraph() {
        paneGraph.getChildren().remove(arrow.getGroupArrowLines());
        paneGraph.getChildren().remove(arrow.getGroupArrowLinesText());
    }

    @Override
    void updateEdge() {
        arrow.updateArrow(fxShapeWrapperPseudoParent.getPoint2D(), fxShapeWrapperPseudoChild.getPoint2D());
    }
}
