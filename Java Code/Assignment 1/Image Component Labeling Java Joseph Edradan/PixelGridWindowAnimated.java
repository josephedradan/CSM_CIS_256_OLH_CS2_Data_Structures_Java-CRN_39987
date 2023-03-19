import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Parent;
import javafx.util.Duration;

import java.util.ArrayList;

public class PixelGridWindowAnimated extends PixelGridWindow {

    private static final double DEFAULT_ANIMATION_DURATION = .25;

    // Animation duration
    private double animationDuration;

    public PixelGridWindowAnimated(PixelGrid pixelGrid, ArrayList<?> arrayListOfSolvers) {
        this(pixelGrid, arrayListOfSolvers,DEFAULT_ANIMATION_DURATION);

    }
    public PixelGridWindowAnimated(PixelGrid pixelGrid, ArrayList<?> arrayListOfSolvers, double animationDuration) {
        this(pixelGrid, arrayListOfSolvers, animationDuration, DEFAULT_PIXEL_AREA, DEFAULT_FONT_SIZE );

    }

    public PixelGridWindowAnimated(PixelGrid pixelGrid, ArrayList<?> arrayListOfSolvers, double animationDuration, int pixelArea, int fontSize) {
        super(pixelGrid, arrayListOfSolvers, pixelArea, fontSize);
        this.animationDuration = animationDuration;
    }

    public Parent createWindowAndContent() {

        Parent root = super.createWindowAndContent();
        animateGridWithAlgorithm();
        return root;
    }

    private void animateGridWithAlgorithm() {
        Timeline timeline = new Timeline();

        int pixelNodeCounter = 0;

        for (Object solver : super.arrayListOfSolvers) {
            Solver solverObject = (Solver) solver;
            for (PixelNode pixelNode : solverObject.getVisitedPixelNodes()) {
                pixelNodeCounter++;
                // FIXME: CAN SIMPLIFY
                KeyFrame keyFrame = new KeyFrame(Duration.seconds(pixelNodeCounter * animationDuration), actionEvent -> {


                    // If you want to flip over diagonal, switch getCol() and getRow()
                    PixelGridWindow.Tile pixelGridWindowTile = gridDisplay[pixelNode.getCol()][pixelNode.getRow()];
                    pixelGridWindowTile.updateTileData(pixelNode.getNodeComponentLabel(), pixelNode.getOrderOfDiscovery());
                    pixelGridWindowTile.updateTileVisually();

                });
                timeline.getKeyFrames().add(keyFrame);
//                gridDisplay[pixelNode.getRow()][pixelNode.getCol()].updateTileVisually(pixelNode.getNodeComponentLabel());
//                System.out.println(pixelNode.getNodeComponent Label());

            }
        }

//        for(int i =0; i < arrayListOfSolverGiven.size();i++){
//            Solver solverObject = (Solver) arrayListOfSolverGiven.get(i);
//            for(int j =0; j < solverObject.getVisitedPixelNodes().size(); j++){
//
//            }
//        }

//        timeline.setRate(20);
        timeline.play();
    }
}
