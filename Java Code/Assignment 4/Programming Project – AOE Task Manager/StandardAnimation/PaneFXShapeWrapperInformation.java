/**
 * Joseph Edradan
 * <p>
 * This file is the text and the text animation for the FXShapeWrapperActual
 */

package StandardAnimation;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class PaneFXShapeWrapperInformation extends StackPane {

    private final double fontSize = 10;

    private final double shiftLeft = 30;

    private Text textCountForward = new Text();
    private Text textChangeTimeForward = new Text();
    private int changeTimeForward = 0;

    private Text textCountBackward = new Text();
    private Text textChangeTimeBackward = new Text();
    private int changeTimeBackward = 0;

    private FXShapeWrapperActual fxShapeWrapperActual;

    private GridPane gridPaneForward = new GridPane();

    private GridPane gridPaneBackward = new GridPane();

    double height;

    public PaneFXShapeWrapperInformation(FXShapeWrapperActual fxShapeWrapperActual) {
        this.fxShapeWrapperActual = fxShapeWrapperActual;

        height = this.fxShapeWrapperActual.getCircleFXShapeWrapper().getRadius();

        textCountForward.setText(String.format("F Count: None"));
        textCountForward.setFont(Font.font(fontSize));

        textChangeTimeForward.setText(String.format("F changeTime: 0"));
        textChangeTimeForward.setFont(Font.font(fontSize));

        gridPaneForward.add(textCountForward, 0, 0);
        gridPaneForward.add(textChangeTimeForward, 0, 1);
        this.getChildren().add(gridPaneForward);

        textCountBackward.setText(String.format("B Count: None"));
        textCountBackward.setFont(Font.font(fontSize));

        textChangeTimeBackward.setText(String.format("B changeTime: None"));
        textChangeTimeBackward.setFont(Font.font(fontSize));

        gridPaneBackward.add(textCountBackward, 0, 0);
        gridPaneBackward.add(textChangeTimeBackward, 0, 1);
        this.getChildren().add(gridPaneBackward);

    }

    public void setTextCountForward(int textCountForward) {
        this.textCountForward.setText(String.format("F Count: %s", textCountForward));
//        updateTextPositions();
    }

    public void setTextChangeTimeForward(int textChangeTimeForward) {
        this.textChangeTimeForward.setText(String.format("F changeTime: %s", textChangeTimeForward));
        changeTimeForward = textChangeTimeForward;
//        updateTextPositions();
    }

    public void setTextCountBackward(int textCountBackward) {
        this.textCountBackward.setText(String.format("B Count: %s", textCountBackward));
//        updateTextPositions();
    }

    public void setTextChangeTimeBackward(int textChangeTimeBackward) {
        this.textChangeTimeBackward.setText(String.format("B changeTime: %s", textChangeTimeBackward));
        changeTimeBackward = textChangeTimeBackward;
//        updateTextPositions();
    }

    public GridPane getGridPaneForward() {
        return gridPaneForward;
    }

    public GridPane getGridPaneBackward() {
        return gridPaneBackward;
    }


    public Text getTextCountForward() {
        return textCountForward;
    }

    public Text getTextChangeTimeForward() {
        return textChangeTimeForward;
    }

    public Text getTextCountBackward() {
        return textCountBackward;
    }

    public Text getTextChangeTimeBackward() {
        return textChangeTimeBackward;
    }


    public double getFontSize() {
        return fontSize;
    }

    public void updateTextPositions() {
        height = this.fxShapeWrapperActual.getCircleFXShapeWrapper().getRadius();

        gridPaneForward.setTranslateX(fxShapeWrapperActual.getCircleFXShapeWrapper().getCenterX() + gridPaneForward.getWidth() / 2 - shiftLeft);
        gridPaneForward.setTranslateY(-height);
//        gridPaneForward.setTranslateY(-height + 17); // Use this if you are not displaying count/degree

        gridPaneBackward.setTranslateX(fxShapeWrapperActual.getCircleFXShapeWrapper().getCenterX() + gridPaneBackward.getWidth() / 2 - shiftLeft);
        gridPaneBackward.setTranslateY(height * 2);
    }

    public boolean shouldMarkFXShapeWrapperAsCritical() {
        if (changeTimeForward == changeTimeBackward) {
            return true;
        }
        return false;
    }
}
