/**
 * Joseph Edradan
 * <p>
 * This file runs the animation
 * <p>
 * Timeline Animation:
 * www.java2s.com/Tutorials/Java/JavaFX/1010__JavaFX_Timeline_Animation.htm
 * <p>
 * Timeline Method call:
 * https://stackoverflow.com/questions/44358098/javafx-call-method-on-specific-time
 * <p>
 * Note:
 * Cannot use PathTransition or anything similar to that because it is relative to a single location and you cannot
 * get a location of a node moving in a PathTransition so you have to make your own which this file is
 * <p>
 * This works by modifying the Line object's staring a ending location every tick from a AnimationTimer
 */

package StandardAnimation;

import Standard.AdjacencyList;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;


class FXAnimationShapeEdge {

    private final static int FRAMES_PER_SECOND = 50;

    private double totalFrames;

    private FXShapeEdgeActual fxShapeEdgeActual;

    private double durationLengthOfAnimation;

    private boolean inverse;

    private Line lineToBeAnimated = new Line();

    private Timeline timeline;

    private int frameCurrent = 0;

    private FXShapeWrapperActual fxShapeWrapperActualStart;

    private FXShapeWrapperActual fxShapeWrapperActualEnd;

    private boolean isMarked = false;

    private Color colorForwardPass = Color.rgb(255, 0, 0, 1);

    private Color colorBackwardPass = Color.rgb(0, 0, 255, 1);

    private Color colorCriticalPath = Color.rgb(0, 255, 0, 1);

    public FXAnimationShapeEdge(FXShapeEdgeActual fxShapeEdgeActual, boolean inverse) {
        this(fxShapeEdgeActual, fxShapeEdgeActual.getEdgeAnimationDataContainer().getWrapperPseudo().getEdge().getEdgeDuration(), inverse);
    }

    // It's private because the animation timing is difficult
    private FXAnimationShapeEdge(FXShapeEdgeActual fxShapeEdgeActual, double durationLengthOfAnimation, boolean inverse) {
        this.fxShapeEdgeActual = fxShapeEdgeActual;

        this.durationLengthOfAnimation = durationLengthOfAnimation;
        this.inverse = inverse;

        if (!this.inverse) {
            fxShapeWrapperActualStart = this.fxShapeEdgeActual.getFXShapeWrapperParent();
            fxShapeWrapperActualEnd = this.fxShapeEdgeActual.getFXShapeWrapperChild();

            lineToBeAnimated.setStrokeWidth(10);
            lineToBeAnimated.setStroke(colorForwardPass);

        } else {
            fxShapeWrapperActualStart = this.fxShapeEdgeActual.getFXShapeWrapperChild();
            fxShapeWrapperActualEnd = this.fxShapeEdgeActual.getFXShapeWrapperParent();

            lineToBeAnimated.setStrokeWidth(10);
            lineToBeAnimated.setStroke(colorBackwardPass);
        }


    }


    private void timeCalls() {

        // Don't call this because the main AnimationTimer will updateTextPositions the shape
//        updateEdge();

        frameCurrent++;
//        System.out.println("frameCurrent " + frameCurrent);

        // Used only used for timeline.setCycleCount(Timeline.INDEFINITE)
//        if(frameCurrent >= FRAMES_PER_SECOND * durationLengthOfAnimation){
//            timeline.stop();
//        }
    }


    // Update the Starting and Ending position of the lineToBeAnimated
    public void updatePoint2D() {

        double differenceX = fxShapeWrapperActualEnd.getPoint2D().getX() - fxShapeWrapperActualStart.getPoint2D().getX();
        double differenceY = fxShapeWrapperActualEnd.getPoint2D().getY() - fxShapeWrapperActualStart.getPoint2D().getY();

        // Set lineToBeAnimated starting location
        lineToBeAnimated.setStartX(fxShapeWrapperActualStart.getPoint2D().getX());
        lineToBeAnimated.setStartY(fxShapeWrapperActualStart.getPoint2D().getY());

        // Set lineToBeAnimated ending location
        lineToBeAnimated.setEndX(fxShapeWrapperActualStart.getPoint2D().getX() + (differenceX / (FRAMES_PER_SECOND * durationLengthOfAnimation)) * frameCurrent);
        lineToBeAnimated.setEndY(fxShapeWrapperActualStart.getPoint2D().getY() + (differenceY / (FRAMES_PER_SECOND * durationLengthOfAnimation)) * frameCurrent);
    }

    public Line getLineToBeAnimated() {
        return lineToBeAnimated;
    }

    public void playAnimation() {
        timeline.play();
    }

    /*
     This is based on the changeTime Value and the Duration of the Edge
     Note: The reason why the back pass animation does not have all nodes reaching the end at the same time is because
     back pass looks at the minimum of the changeTimes than the maximum of the changeTimes
     */
    // TODO: DON'T USE ME I'M BROKEN RIGHT NOW! My timing is off and stuff so don't call me
    public SequentialTransition getSequentialTransitionChangeTimeBased() {
        timeline = new Timeline();
        KeyFrame keyFrame = new KeyFrame(Duration.millis(1000 / FRAMES_PER_SECOND), actionEvent -> this.timeCalls());
//        KeyFrame keyFrame = new KeyFrame(Duration.millis(1), actionEvent -> this.timeCalls());
        timeline.getKeyFrames().add(keyFrame);
        totalFrames = FRAMES_PER_SECOND * this.durationLengthOfAnimation;
        timeline.setCycleCount((int) totalFrames);

        if (!inverse) {

            // Get the getFXShapeWrapperParent's Wrapper's ChangeTime
            double timePauseBeforeAnimation = fxShapeEdgeActual.getFXShapeWrapperParent().getWrapperActualNormal().getChangeTime();

            PauseTransition pauseTransition = new PauseTransition();
            pauseTransition.setDuration(Duration.millis(1000 * timePauseBeforeAnimation));

            SequentialTransition sequentialTransition = new SequentialTransition();
            sequentialTransition.getChildren().add(pauseTransition);
            sequentialTransition.getChildren().add(timeline);

            return sequentialTransition;

        } else {

            // Get Wrapper Child Vertex Index
            int AdjacencyListNormalWrapperPseudoThisVertexIndex = fxShapeEdgeActual.getEdgeAnimationDataContainer().getWrapperPseudo().getVertex().getIndex();

            // Temporary copy of the AdjacencyList
            AdjacencyList adjacencyListTemp = fxShapeEdgeActual.getPaneGraph().getFxAdjacencyListActual().getAdjacencyList();

            // Get ChangeTime value of the Wrapper Child Vertex Index that is in the AdjacencyListInverse
            int AdjacencyListInverseWrapperPseudoThisChangeTime = adjacencyListTemp.getAdjacencyListInverse().getWrapperActual(AdjacencyListNormalWrapperPseudoThisVertexIndex).getChangeTime();

            // Get the last index of the AdjacencyList using the AdjacencyList's last element
            int AdjacencyListNormalWrapperActualParentLastVertexIndex = adjacencyListTemp.getArrayListWrapperActual().size() - 1;

            // Get ChangeTime value of the AdjacencyList's last Wrapper
            int AdjacencyListNormalWrapperActualParentLastChangeTime = adjacencyListTemp.getArrayListWrapperActual().get(AdjacencyListNormalWrapperActualParentLastVertexIndex).getChangeTime();

            // ChangeTime value of the AdjacencyList's last Wrapper + ChangeTime value of the AdjacencyList's last Wrapper - ChangeTime value of the AdjacencyListInverse's current Wrapper
            double timePauseBeforeAnimation = AdjacencyListNormalWrapperActualParentLastChangeTime + (AdjacencyListNormalWrapperActualParentLastChangeTime - AdjacencyListInverseWrapperPseudoThisChangeTime);

            PauseTransition pauseTransition = new PauseTransition();
            pauseTransition.setDuration(Duration.millis(1000 * timePauseBeforeAnimation));

            SequentialTransition sequentialTransition = new SequentialTransition();
            sequentialTransition.getChildren().add(pauseTransition);
            sequentialTransition.getChildren().add(timeline);

            return sequentialTransition;
        }
    }

    // Only call this method if you are calling getSequentialTransitionDFS()
    private void setDurationLengthOfAnimationCustom(double durationLengthOfAnimation) {
        this.durationLengthOfAnimation = durationLengthOfAnimation;
        timeline = new Timeline();

        KeyFrame keyFrame = new KeyFrame(Duration.millis(1000 / FRAMES_PER_SECOND), actionEvent -> this.timeCalls());
        timeline.getKeyFrames().add(keyFrame);
        totalFrames = FRAMES_PER_SECOND * this.durationLengthOfAnimation;
        timeline.setCycleCount((int) totalFrames);
    }

    public SequentialTransition getSequentialTransitionDFS() {

        SequentialTransition sequentialTransition = new SequentialTransition();

        EdgeAnimationDataContainer edgeAnimationDataContainer = fxShapeEdgeActual.getEdgeAnimationDataContainer();

        double timePauseBeforeAnimation;

        // Forward Pass animation stuff
        if (!inverse) {

            PaneFXShapeWrapperInformation paneFXShapeWrapperInformationStart = fxShapeWrapperActualStart.getPaneFXShapeWrapperInformation();

            PaneFXShapeWrapperInformation paneFXShapeWrapperInformationEnd = fxShapeWrapperActualEnd.getPaneFXShapeWrapperInformation();

            // Animation to giveFXAdjacencyListActual after sequentialTransition is done
            sequentialTransition.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    int countForward = edgeAnimationDataContainer.getCountForward();

                    int changeTimeForward = edgeAnimationDataContainer.getChangeTimeForward();

                    paneFXShapeWrapperInformationEnd.setTextCountForward(countForward);

                    paneFXShapeWrapperInformationEnd.setTextChangeTimeForward(changeTimeForward);

                    // If the edgeAnimationDataContainer is equal to the getEdgeAnimationDataContainerBackwardPassFirst() or getEdgeAnimationDataContainerForwardPassLast() then modify paneFXShapeWrapperInformationEnd
                    if (edgeAnimationDataContainer.equals(fxShapeEdgeActual.getPaneGraph().getFxAdjacencyListActual().getEdgeAnimationDataContainerBackwardPassFirst())) {
                        paneFXShapeWrapperInformationEnd.getTextChangeTimeForward().setFont(Font.font(Font.getDefault().getName(), FontWeight.BOLD, paneFXShapeWrapperInformationStart.getFontSize()));
                        paneFXShapeWrapperInformationEnd.getTextChangeTimeBackward().setFont(Font.font(Font.getDefault().getName(), FontWeight.BOLD, paneFXShapeWrapperInformationStart.getFontSize()));
                    }
                }
            });

            timePauseBeforeAnimation = edgeAnimationDataContainer.getJavaFXTimePauseBeforeAnimationForward();

            // Backward Pass animation stuff
        } else {

            PaneFXShapeWrapperInformation paneFXShapeWrapperInformationStart = fxShapeWrapperActualStart.getPaneFXShapeWrapperInformation();

            PaneFXShapeWrapperInformation paneFXShapeWrapperInformationEnd = fxShapeWrapperActualEnd.getPaneFXShapeWrapperInformation();

            // If WrapperActual is critical, mark this object and change lineToBeAnimated to colorCriticalPath when the sequentialTransition is over
            // YOU MUST MARK ONLY DURING THE BACKWARD PASS OR ELSE THE TEXT WILL BE UPDATED IMPROPERLY
            // BASICALLY THIS CONDITION MUST BE HERE ONLY
            // THIS GOES OFF OF THE ACTUAL WrapperStart and WrapperEnd WHICH THE TEXT MIGHT NOT DISPLAY YET IF THERE ARE MULTIPLE NODES GOING TOWARDS THE fxShapeWrapperActualEnd
//            if (fxShapeWrapperActualStart.getWrapperActualNormal().isMarked() && fxShapeWrapperActualEnd.getWrapperActualNormal().isMarked()) {
//                isMarked = true;
//            }

            // Here is the correct version where the edges are marked and not the just the vertices
            if (fxShapeEdgeActual.getEdgeAnimationDataContainer().getWrapperPseudo().getEdge().isMarked()) {
                isMarked = true;
            }

            // Animation to giveFXAdjacencyListActual after sequentialTransition is done
            sequentialTransition.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    int countBackward = edgeAnimationDataContainer.getCountBackward();

                    int changeTimeBackward = edgeAnimationDataContainer.getChangeTimeBackward();

                    paneFXShapeWrapperInformationEnd.setTextCountBackward(countBackward);

                    paneFXShapeWrapperInformationEnd.setTextChangeTimeBackward(changeTimeBackward);

                    if (isMarked) {

                        lineToBeAnimated.setStroke(colorCriticalPath);

                        // ONLY BOLD WHEN THE paneFXShapeWrapperInformationEnd.getTextChangeTimeForward() AND paneFXShapeWrapperInformationEnd.getTextChangeTimeBackward() ARE THE SAME!!!!
                        // Basically, bold the text when the changeTime is the same from the Forward and Backward pass
                        if (paneFXShapeWrapperInformationEnd.shouldMarkFXShapeWrapperAsCritical()) {
                            paneFXShapeWrapperInformationEnd.getTextChangeTimeForward().setFont(Font.font(Font.getDefault().getName(), FontWeight.BOLD, paneFXShapeWrapperInformationEnd.getFontSize()));
                            paneFXShapeWrapperInformationEnd.getTextChangeTimeBackward().setFont(Font.font(Font.getDefault().getName(), FontWeight.BOLD, paneFXShapeWrapperInformationEnd.getFontSize()));

                        }
                    }
                }
            });

            timePauseBeforeAnimation = edgeAnimationDataContainer.getJavaFXTimePauseBeforeAnimationBackward();
        }

        setDurationLengthOfAnimationCustom(edgeAnimationDataContainer.getJavaFXDurationLengthOfAnimation());

        PauseTransition pauseTransition = new PauseTransition();
        pauseTransition.setDuration(Duration.millis(1000 * timePauseBeforeAnimation));

        sequentialTransition.getChildren().add(pauseTransition);
        sequentialTransition.getChildren().add(timeline);

        return sequentialTransition;
    }

    public boolean isMarked() {
        return isMarked;
    }

}

