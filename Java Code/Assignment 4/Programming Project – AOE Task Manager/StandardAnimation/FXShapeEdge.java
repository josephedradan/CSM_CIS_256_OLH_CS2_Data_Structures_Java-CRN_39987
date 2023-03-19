package StandardAnimation;

abstract class FXShapeEdge extends FXShape {

    protected Arrow arrow;

    public FXShapeEdge(PaneGraph paneGraph){
        super(paneGraph);
        this.arrow = new Arrow();
    }
    public FXShapeEdge(PaneGraph paneGraph, Arrow arrow) {
        this(paneGraph);
        this.arrow = arrow;
    }

    abstract void updateEdge();

}
