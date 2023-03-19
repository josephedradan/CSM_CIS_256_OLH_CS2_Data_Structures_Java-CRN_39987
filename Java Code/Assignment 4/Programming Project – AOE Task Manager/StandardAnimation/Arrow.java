/**
 * @author Joseph Edradan
 * <p>
 * There is no javafx.scene.shape.Arrow so I made one.
 * The arrow head proccedure is relative to the lineArrowBody
 * The lines to make the arrow are part of a group
 */
package StandardAnimation;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class Arrow {

    private static final double ARROW_HEAD_DIVISIONS = 100;

    private Group groupArrowLines = new Group();

    private Line lineArrowBody = new Line();

    private Line lineArrowHeadLeft = new Line();

    private Line lineArrowHeadRight = new Line();

    private Text groupArrowLinesText = new Text();

    private EdgeAnimationDataContainer edgeAnimationDataContainer;

    private Color colorArrow = Color.rgb(0, 0, 0, 0.2);

    private double arrowAngle = 30;

    private double arrowLength = 20;

    private double arrowHeadDivision = 75;

    public Arrow(){
        lineArrowBody.setStrokeWidth(2);
        lineArrowBody.setStroke(colorArrow);

        lineArrowHeadLeft.setStrokeWidth(2);
        lineArrowHeadLeft.setStroke(colorArrow);

        lineArrowHeadRight.setStrokeWidth(2);
        lineArrowHeadRight.setStroke(colorArrow);

        groupArrowLines.getChildren().addAll(lineArrowBody, lineArrowHeadLeft, lineArrowHeadRight);

    }
    public Arrow(EdgeAnimationDataContainer edgeAnimationDataContainer) {
        this();

        this.edgeAnimationDataContainer = edgeAnimationDataContainer;

        // Duration/Length of the edge text
        groupArrowLinesText.setText(String.format("%s", this.edgeAnimationDataContainer.getWrapperPseudo().getEdge().getEdgeName()));

    }

    public void updateArrow(Point2D point2DStart, Point2D point2DEnd) {

        // Update the Starting Position of the line
        lineArrowBody.setStartX(point2DStart.getX());
        lineArrowBody.setStartY(point2DStart.getY());

        // Update the Ending Position of the line
        lineArrowBody.setEndX(point2DEnd.getX());
        lineArrowBody.setEndY(point2DEnd.getY());

        // Update the Position of the groupArrowLinesText
        groupArrowLinesText.setTranslateX(lineArrowBody.getStartX() + ((lineArrowBody.getEndX() - lineArrowBody.getStartX()) / 2));
        groupArrowLinesText.setTranslateY(lineArrowBody.getStartY() + ((lineArrowBody.getEndY() - lineArrowBody.getStartY()) / 2));

        updateArrowSides();
    }

    private void updateArrowSides() {
        double differenceX = lineArrowBody.getEndX() - lineArrowBody.getStartX();
        double differenceY = lineArrowBody.getEndY() - lineArrowBody.getStartY();

        double lineArrowBodyPositionAlongX = lineArrowBody.getStartX() + differenceX / ARROW_HEAD_DIVISIONS * arrowHeadDivision;
        double lineArrowBodyPositionAlongY = lineArrowBody.getStartY() + differenceY / ARROW_HEAD_DIVISIONS * arrowHeadDivision;

        double slope = differenceY / differenceX;

        double angleRelativeToXAxis = (Math.toDegrees(Math.atan(slope)));

        lineArrowHeadLeft.setStartX(lineArrowBodyPositionAlongX);
        lineArrowHeadLeft.setStartY(lineArrowBodyPositionAlongY);

        lineArrowHeadRight.setStartX(lineArrowBodyPositionAlongX);
        lineArrowHeadRight.setStartY(lineArrowBodyPositionAlongY);

        double arrowLengthTemp = -arrowLength;

        if (differenceX < 0) {
            arrowLengthTemp = arrowLength;
        }

        lineArrowHeadLeft.setEndX(lineArrowBodyPositionAlongX + Math.cos(Math.toRadians(angleRelativeToXAxis + arrowAngle)) * arrowLengthTemp);
        lineArrowHeadLeft.setEndY(lineArrowBodyPositionAlongY + Math.sin(Math.toRadians(angleRelativeToXAxis + arrowAngle)) * arrowLengthTemp);
        lineArrowHeadRight.setEndX(lineArrowBodyPositionAlongX + Math.cos(Math.toRadians(angleRelativeToXAxis - arrowAngle)) * arrowLengthTemp);
        lineArrowHeadRight.setEndY(lineArrowBodyPositionAlongY + Math.sin(Math.toRadians(angleRelativeToXAxis - arrowAngle)) * arrowLengthTemp);
    }

    public Group getGroupArrowLines() {
        return groupArrowLines;
    }

    // Don't need a getter for this...
    public Text getGroupArrowLinesText() {
        return groupArrowLinesText;
    }

}
