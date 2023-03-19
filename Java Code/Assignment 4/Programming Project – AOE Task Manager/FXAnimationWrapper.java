//package StandardAnimation;
//
//import javafx.animation.PathTransition;
//import javafx.animation.PauseTransition;
//import javafx.animation.SequentialTransition;
//import javafx.scene.shape.LineTo;
//import javafx.scene.shape.MoveTo;
//import javafx.scene.shape.Path;
//import javafx.util.Duration;
//
//class FXAnimationWrapper extends FXAnimation {
//
//    private FXShapeWrapper wrapperFX;
//
//    private double positionXInitial;
//
//    private double positionYInitial;
//
//    public FXAnimationWrapper(FXShapeWrapper wrapperFX, double positionXInitial, double positionYInitial) {
//        this.wrapperFX = wrapperFX;
//
//        this.positionXInitial = positionXInitial;
//
//        this.positionYInitial = positionYInitial;
//
//    }
//
//    @Override
//    protected void createSequentialTransition(double initialTime) {
//        sequentialTransition = new SequentialTransition(wrapperFX.addToPaneGraph());
//
//    }
//
//    // Wait time before moving
//    private PauseTransition pathTransitionPause(double time) {
//        PauseTransition pauseTransition = new PauseTransition();
//        pauseTransition.setDuration(Duration.millis(time));
//        return pauseTransition;
//    }
//
//    // Move to position then Line to Position
//    private PathTransition pathTransitionToPosition(double time) {
//        Path path = new Path();
//        path.getElements().add(new MoveTo(positionXInitial, positionYInitial));
//        path.getElements().add(new LineTo(wrapperFX.getPositionXPlacement(), wrapperFX.getPositionYPlacement()));
//
//        PathTransition pathTransition = new PathTransition();
//
////        pathTransition.setNode(circlePerson);
//        pathTransition.setDuration(Duration.millis(time));
//        pathTransition.setPath(path);
////        pathTransition.play();
//
//        return pathTransition;
//    }
//
//}
