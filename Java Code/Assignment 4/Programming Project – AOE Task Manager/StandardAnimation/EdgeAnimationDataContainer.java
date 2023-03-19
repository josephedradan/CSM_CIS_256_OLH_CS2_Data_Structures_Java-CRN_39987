/**
 * @author Joseph Edradan
 * <p>
 * A Container that holds information from the Forward pass and the Backward pass
 */

package StandardAnimation;

import Standard.WrapperPseudo;

public class EdgeAnimationDataContainer {

    private WrapperPseudo wrapperPseudo;

    private double javaFXTimePauseBeforeAnimationForward;

    private double javaFXTimePauseBeforeAnimationBackward;

    // Total length time of Line Animating
    private double javaFXDurationLengthOfAnimation;

    private int countForward;

    private int countBackward;

    private int changeTimeForward;

    private int changeTimeBackward;

    public EdgeAnimationDataContainer(WrapperPseudo wrapperPseudo, double javaFXDurationLengthOfAnimation) {
        // All I care about is it's edge because edges are unique.
        this.wrapperPseudo = wrapperPseudo;

        this.javaFXDurationLengthOfAnimation = javaFXDurationLengthOfAnimation;

    }

    public double getJavaFXDurationLengthOfAnimation() {
        return javaFXDurationLengthOfAnimation;
    }

    public double getJavaFXTimePauseBeforeAnimationForward() {
        return javaFXTimePauseBeforeAnimationForward;
    }

    public void setJavaFXTimePauseBeforeAnimationForward(double javaFXTimePauseBeforeAnimationForward) {
        this.javaFXTimePauseBeforeAnimationForward = javaFXTimePauseBeforeAnimationForward;
    }

    public double getJavaFXTimePauseBeforeAnimationBackward() {
        return javaFXTimePauseBeforeAnimationBackward;
    }

    public void setJavaFXTimePauseBeforeAnimationBackward(double javaFXTimePauseBeforeAnimationBackward) {
        this.javaFXTimePauseBeforeAnimationBackward = javaFXTimePauseBeforeAnimationBackward;
    }

    public WrapperPseudo getWrapperPseudo() {
        return wrapperPseudo;
    }

    public int getCountForward() {
        return countForward;
    }

    public void setCountForward(int countForward) {
        this.countForward = countForward;
    }

    public int getCountBackward() {
        return countBackward;
    }

    public void setCountBackward(int countBackward) {
        this.countBackward = countBackward;
    }

    public int getChangeTimeForward() {
        return changeTimeForward;
    }

    public void setChangeTimeForward(int changeTimeForward) {
        this.changeTimeForward = changeTimeForward;
    }

    public int getChangeTimeBackward() {
        return changeTimeBackward;
    }

    public void setChangeTimeBackward(int changeTimeBackward) {
        this.changeTimeBackward = changeTimeBackward;
    }

    @Override
    public String toString() {

        String string = String.format("AnimationForward %s\nAnimationBackward %s\nDuration %s\ntextCountForward %s\ntextCountBackward %s\nchangeTimeFoward %s\ntextChangeTimeBackward %s\n%s\n%s\n%s",
                javaFXTimePauseBeforeAnimationForward,
                javaFXTimePauseBeforeAnimationBackward,
                javaFXDurationLengthOfAnimation,
                countForward,
                countBackward,
                changeTimeForward,
                changeTimeBackward,
                wrapperPseudo,
                wrapperPseudo.getEdge(),
                wrapperPseudo.getEdge().getEdgeIndex());
        return string;
    }
}
