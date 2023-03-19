/**
 * Joseph Edradan
 * <p>
 * This file is for all FXShapes
 */
package StandardAnimation;

// Base of Every Shape
abstract class FXShape {

    protected PaneGraph paneGraph;

    abstract public void addEventHandlers();

    abstract public void addToPaneGraph();

    abstract public void removeFromPaneGraph();

    public FXShape(PaneGraph paneGraph){
        this.paneGraph = paneGraph;
    }

    public PaneGraph getPaneGraph() {
        return paneGraph;
    }
}
