/*
Joseph Edradan

Code based on and inspired by:
    Mapping Minesweeper
        https://www.youtube.com/watch?v=JwcyxuKko_M
        https://github.com/AlmasB/FXTutorials/blob/master/src/com/almasb/minesweeper/MinesweeperApp.java

    Mapping color space with hsl
        https://stackoverflow.com/questions/46928277/trying-to-convert-integer-range-to-rgb-color

    Understanding KeyFrames
        https://stackoverflow.com/questions/42821158/javafx-timeline-inside-a-for-loop-is-skipping-keyframes

 */

import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class PixelGridWindow {
    // IF YOU HAVE JAVAFX 8 THEN SET THIS TO 10 ELSE SET IT TO 0 IF JAVAFX 10 AND ABOVE
    private static final int JDK_8_JAVAFX_WINDOW_OFFSET = 10;

    protected static final int DEFAULT_PIXEL_AREA = 16;

    protected static final int DEFAULT_FONT_SIZE = 8;

    // Tile Dimension
    protected int pixelArea;

    // Tile Font Size
    protected int fontSize;

    // Tile Saturation
    private static final double SATURATION = 1;

    // Tile Lightness
    private static final double LIGHTNESS = 1;

    // Tile Alpha Channel (Buggy with PixelGridWindowAnimated)
    private static final double ALPHA = 1;

    // Grid Display
    protected Tile[][] gridDisplay;

    // Array list of solvers
    protected ArrayList<?> arrayListOfSolvers;

    // Amount of Tiles
    private int amountOfTilesY;
    private int amountOfTilesX;

    // Window Dimensions
    private int windowWidth;
    private int windowHeight;

    // Offset for printing the grid
    private static final int OFFSET = 1;

    // Given Pixel grid
    private PixelGrid pixelGrid;

    // Amount of solvers
    private int amountOfSolvers;

    // Enable Modulus color space
    protected boolean modulusColorSpace = false;

    // Enable Random color
    protected boolean randomColor = false;

    // Enable text value
    protected boolean noTextValues = false;

    // Enable borderless
    protected boolean borderless = false;

    // Enable noInitialColor
    protected boolean noInitialColor = false;

    // Enable fillEntireRectangle
    protected boolean fillEntireRectangle = false;

    // Enable white border
    protected boolean borderWhite = false;

    // Enable zeros to be shown
    protected boolean noZeros = false;

    // Enable noOnes to be shown
    protected boolean noOnes = false;

    // Display discovery order
    protected boolean showDiscoveryOrder = false;

    public PixelGridWindow(PixelGrid pixelGrid) {
        this(pixelGrid, null);
    }

    public PixelGridWindow(PixelGrid pixelGrid, ArrayList<?> arrayListOfSolvers) {
        this(pixelGrid, arrayListOfSolvers, DEFAULT_PIXEL_AREA, DEFAULT_FONT_SIZE);
    }

    public PixelGridWindow(PixelGrid pixelGrid, ArrayList<?> arrayListOfSolvers, int pixelArea, int fontSize) {
        this.pixelGrid = pixelGrid;
        this.arrayListOfSolvers = arrayListOfSolvers;

        this.pixelArea = pixelArea;
        this.fontSize = fontSize;

        this.amountOfSolvers = arrayListOfSolvers != null ? this.arrayListOfSolvers.size() : 0;
    }

    public Parent createWindowAndContent() {
        amountOfTilesY = pixelGrid.getHeightAbsolute();
        amountOfTilesX = pixelGrid.getLengthAbsolute();

        Pane root = new Pane();

        // If you want to flip over diagonal, switch amountOfTilesX and amountOfTilesY
        gridDisplay = new Tile[amountOfTilesX - OFFSET][amountOfTilesY - OFFSET];

        // Create tiles and add to gridDisplay
        // Vertical
        for (int row = OFFSET; row < amountOfTilesY - OFFSET; row++) {
            // Horizontal
            for (int column = OFFSET; column < amountOfTilesX - OFFSET; column++) {
                int label = pixelGrid.getPixelNode(row, column).getNodeComponentLabel();
                int orderOfDiscovery = pixelGrid.getPixelNode(row, column).getOrderOfDiscovery();
                Tile tile = new Tile(column, row, label, orderOfDiscovery);

                // If you want to flip over diagonal, switch row and column
                gridDisplay[column][row] = tile;
                root.getChildren().add(tile);
            }
        }

        // If you want to flip over diagonal, switch windowHeight and windowWidth
        windowHeight = (amountOfTilesY - (OFFSET * 2)) * (pixelArea);
        windowWidth = (amountOfTilesX - (OFFSET * 2)) * (pixelArea);

        // IMPORTANT NOTE IF YOU ARE RUNNING >= jdk-10.0.2 YOU DO NOT NEED JDK_8_JAVAFX_WINDOW_OFFSET
//        root.setPrefSize(windowWidth, windowHeight);
        root.setPrefSize(windowWidth - JDK_8_JAVAFX_WINDOW_OFFSET, windowHeight - JDK_8_JAVAFX_WINDOW_OFFSET);

        return root;
    }

    public void setModulusColorSpace(boolean value) {
        this.modulusColorSpace = value;
    }

    public void setRandomColor(boolean value) {
        this.randomColor = value;
    }

    public void setNoTextValues(boolean value) {
        this.noTextValues = value;
    }

    public void setBorderless(boolean value) {
        this.borderless = value;
    }

    public void setNoInitialColor(boolean value) {
        this.noInitialColor = value;
    }

    public void setFillEntireCell(boolean value) {
        this.fillEntireRectangle = value;
    }

    public void setBorderWhite(boolean value) {
        this.borderWhite = value;
    }

    public void setNoInitialZeros(boolean value) {
        this.noZeros = value;
    }

    public void setNoInitialOnes(boolean value) {
        this.noOnes = value;
    }

    public void showDiscoveryOrder(boolean value) {
        this.showDiscoveryOrder = value;
    }

    protected class Tile extends StackPane {
        int x, y;
        int pixelLabel;
        int orderOfDiscovery;

        private Rectangle rectangle = new Rectangle(pixelArea - (OFFSET * 2), pixelArea - (OFFSET * 2));
        protected Text text = new Text();

        public Tile(int x, int y, int pixelLabel, int orderOfDiscovery) {
            this.x = x;
            this.y = y;
            this.pixelLabel = pixelLabel;
            this.orderOfDiscovery = orderOfDiscovery;

            // Stroke size is by 1 pixel * 2 sides * 2 for both top/bot and left/right
            // Border Color

            if (fillEntireRectangle) {
                rectangle.setWidth(pixelArea);
                rectangle.setHeight(pixelArea);
            }

            if (!borderless) {
                if (borderWhite) {
                    rectangle.setStroke(Color.WHITE);

                } else {
                    rectangle.setStroke(Color.LIGHTGRAY);
                }
            }

            // Inner Color
            if (pixelLabel == 0) {
                rectangle.setFill(Color.WHITE);

            } else if (pixelLabel == 1) {
                if (!noInitialColor) {
                    rectangle.setFill((Color.LIGHTGRAY));
                } else {
                    rectangle.setFill((Color.WHITE));
                }
            } else {
                rectangle.setFill(getColorMapped(pixelLabel));
            }

            // Rectangle visibility
            rectangle.setVisible(true);

            // Font
            text.setFont(Font.font(fontSize));

            // Text
            setText(pixelLabel);
//            text.setText(Integer.toString(pixelLabel));

            // Show text
            text.setVisible(true);

            // STYLE IS NOT WHAT CHANGES COLOR
//            rectangle.setStyle("-fx-background-color: #" + "FF0000");

            getChildren().addAll(rectangle, text);

            // Move tile to specified area on window
            setTranslateX((x - OFFSET) * pixelArea);
            setTranslateY((y - OFFSET) * pixelArea);

            // FIXME: IGNORE THE BELOW...
//            setOnMouseClicked(e -> open());

        }

        public void updateTileData(int pixelLabel, int orderOfDiscovery) {
            this.pixelLabel = pixelLabel;
            this.orderOfDiscovery = orderOfDiscovery;
        }

        public void updateTileVisually() {
            setColor(pixelLabel);
            setText(pixelLabel);
        }

        public void setColor(int pixelLabel) {
            Color tempColor = getColorMapped(pixelLabel);

            rectangle.setFill(tempColor);
            if (fillEntireRectangle) {
                rectangle.setStroke(tempColor);
            }

        }

        public void setText(int pixelLabel) {
            this.pixelLabel = pixelLabel;
            if (!noTextValues) {
                if (showDiscoveryOrder) {
                    text.setText(Integer.toString(orderOfDiscovery));

                } else {
                    text.setText(Integer.toString(pixelLabel));

                }

                if (noZeros) {
                    if (pixelLabel == 0) {
                        text.setText("");
                    }
                }
                if (noOnes) {
                    if (pixelLabel == 1) {
                        text.setText("");
                    }
                }

            }
        }

        public Color getColorMapped(int pixelLabel) {
            int startHue = 0;
            int endHue = 360;
            // AMOUNT OF SOLVERS SHOULD BE THE SAME FOR BOTH DFS AND BFS

            // Random color or mapped integer color space
            if (modulusColorSpace) {
                // Modulus color
                return transitionOfHueRange(pixelLabel % 360, startHue, endHue);

            } else {
                // Mapped integer colors
                return transitionOfHueRange(mappingColorSpaceOnSolver(pixelLabel), startHue, endHue);

            }

        }

        private double mappingColorSpaceOnSolver(int pixelLabel) {
            int solverIndex = pixelLabel - 2;
            // Color hue is 0 to 360
            return (double) 360 / (amountOfSolvers - 1) * solverIndex;
        }

        // https://stackoverflow.com/questions/46928277/trying-to-convert-integer-range-to-rgb-color
        private Color transitionOfHueRange(double percentage, int startHue, int endHue) {
            // From 'startHue' 'percentage'-many to 'endHue'
            // Finally map from [0°, 360°] -> [0, 1.0] by dividing
            double hue = ((percentage * (endHue - startHue)) + startHue) / 360;

//            double saturation = 1.0;
//            double lightness = 1.0;

//            return hslColorToRgb(hue, saturation, lightness);

            // Random color or mapped integer color space
            if (randomColor) {
                // Return color objects
                return Color.hsb((int) ((Math.random() * (360 - 0) + 0)), SATURATION, LIGHTNESS, ALPHA);
            } else {
                // Return color
                return Color.hsb(hue, SATURATION, LIGHTNESS, ALPHA);
            }

        }

    }

}