/*
Software Gurus Bar
Project 2
Joseph Edradan
3/24/19

Notes:
    Handles all the graphical stuff for SoftwareGurusBar

 */

import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Arrays;


public class SoftwareGurusBarDisplay extends Application {
    // Java FX 8 Window OFFSET
    private final int JAVA_FX_WINDOW_OFF_SET = 10;

    /////////////////////////////////////////////////////////
    // String Event String ArrayList
    ArrayList<String> stringsPrintWithEventDataPerTime;

    // Primary Pane
    private Pane paneRoot;
    private double paneRootWidth;
    private double paneRootHeight;

    // Secondary Pane
    private PaneInformationClockEvent paneInformationClockEvent;
    private double paneInformationClockEventWidth = 670;
    private double paneInformationClockEventHeight = 1000;

    // Third Pane
    private PaneInformationBar paneInformationBar;
    private double paneInformationBarWidth;
    private double paneInformationBarHeight = 300;
    /////////////////////////////////////////////////////////

    // ************** Animation Stuff **************
    // Animation time
    private double animationTimeScalar = 1; // smaller is faster

    // Graphic stuff
    private double shapeDimensionScalar = 16.5; // Scaling the graphics

    // Bar stuff
    private double barOffsetY = 50;
    private double timeMaxBarOperating;

    // Parallel Transition
    private ParallelTransition parallelTransition;
    /////////////////////////////////////////////////////////
    // PaneInformationClockEvent stuff;
    private PaneInformationClockEvent paneInformationClockEventObject;

    // ShapeTable information
    private ArrayList<ShapeTable> shapeTableArrayList = new ArrayList<>();

    // ShapeTable.ShapeSeat information
    private ArrayList<ShapeTable.ShapeSeat> shapeSeatsArrayList = new ArrayList<>();

    // ShapePerson information
    private ArrayList<ShapePerson> shapePeople = new ArrayList<>();

    // All Sequential Transitions
    // private ArrayList<SequentialTransition> sequentialTransitions = new ArrayList<>();

    // All Sequential Transitions
    private ArrayList<ArrayList<Order>> arrayListOrdersArrayList = new ArrayList<>();

    // SoftwareGurusBar softwareGurusBarWorld
    private SoftwareGurusBar softwareGurusBarWorld;

    public static void main(String[] args) {
        launch(args);
    }

    private Parent createPrimaryPane() {
        paneRoot = new Pane();

        // 1.
        createWorld();

        // 2. Format Event stringsByTimeFromEvent and Final time for second window
        getLocationAndEventInformation();

        // 3.  This will set the pane width
        createTablesAndAddThemToPane(paneRoot);

        // 4. PanePrefSize based on information given from createTablesAndAddThemToPane(paneRoot);
        paneRoot.setPrefSize(paneRootWidth - JAVA_FX_WINDOW_OFF_SET, paneRootHeight - JAVA_FX_WINDOW_OFF_SET);

        // 5. Create ShapePerson and parallel transition (Requires the PrefSize of Pane)
        createShapePersonAndParallelTransition(paneRoot, paneRootWidth, barOffsetY);

        // 6. Make ShapeWall for entrance
        addShapeWallsToPane(paneRoot, barOffsetY, shapeDimensionScalar, 50, paneRootWidth);

        return paneRoot;
    }

    private Parent getPaneInformationClockEvent() {
        paneInformationClockEvent = new PaneInformationClockEvent(animationTimeScalar, stringsPrintWithEventDataPerTime, arrayListOrdersArrayList, softwareGurusBarWorld.getBarOpeningHour(), (int) timeMaxBarOperating);
        paneInformationClockEvent.setPrefSize(paneInformationClockEventWidth, paneInformationClockEventHeight);

        return paneInformationClockEvent;
    }

    private Parent getPaneInformationBar() {
        paneInformationBar = new PaneInformationBar(softwareGurusBarWorld);
        paneInformationClockEvent.setPrefSize(paneRootWidth, paneInformationBarHeight);

        return paneInformationBar;
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        Scene primaryScene = new Scene(createPrimaryPane());
        primaryStage.setScene(primaryScene);
        primaryStage.setResizable(false);
        primaryStage.setX(0);
        primaryStage.setY(0);
        primaryStage.setTitle("Software Gurus Bar");
        primaryStage.show();

        Scene sceneInformationClockEvent = new Scene(getPaneInformationClockEvent());
        Stage stageInformationClockEvent = new Stage();
        stageInformationClockEvent.setScene(sceneInformationClockEvent);
        stageInformationClockEvent.setResizable(false);
        stageInformationClockEvent.setTitle("Bar Clock Event Information");
        stageInformationClockEvent.setX(paneRoot.getPrefWidth() + 2 + JAVA_FX_WINDOW_OFF_SET);
        stageInformationClockEvent.setY(0);
        stageInformationClockEvent.show();

        Scene sceneInformationBar = new Scene(getPaneInformationBar());
        Stage stageInformationBar = new Stage();
        stageInformationBar.setScene(sceneInformationBar);
        stageInformationBar.setResizable(false);
        stageInformationBar.setTitle("Bar and Person Information");
        stageInformationBar.setX(0);
        stageInformationBar.setY(paneRoot.getPrefHeight() + 2 + JAVA_FX_WINDOW_OFF_SET + 25);
        stageInformationBar.show();

        double timeLineOffsetScaled = 1 * animationTimeScalar;

        parallelTransition.setDelay(Duration.seconds(timeLineOffsetScaled));
        parallelTransition.play();

        paneInformationClockEvent.setTimelineDelay(0);
        paneInformationClockEvent.playTimeline();
    }

    private void createWorld() {
        // Grading Run
        animationTimeScalar = .7;
        int[] probabilityDistributionsOfGroupsGoingToBarPerHourOutOf100 = new int[]{100, 100, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        ProbabilityDistributionIndex probabilityDistributionIndexOfAmountOfGroupsGoingToBarAtMinute = new ProbabilityDistributionIndex(new int[]{100, 30, 10, 5, 1});
        ProbabilityDistributionIndex probabilityDistributionIndexGroupSize = new ProbabilityDistributionIndex(new int[]{10,15,15,40,50,60,60,40,20,10,5,5});
        ProbabilityDistributionIndex probabilityDistributionIndexTimeWaitOrder = new ProbabilityDistributionIndex(new int[]{5, 10, 20, 20, 30, 50, 70});
        ProbabilityDistributionIndex probabilityDistributionIndexTimeWaitLeave = new ProbabilityDistributionIndex(new int[]{5, 5, 10, 10, 20, 30, 30, 40, 50, 80});

        int barOperatingHours = 1;
        int barOpeningHour = 6;

        // Realistic Run
//        animationTimeScalar = .1;
//        int[] probabilityDistributionsOfGroupsGoingToBarPerHourOutOf100 = new int[]{2, 4, 5, 10, 15, 30, 60, 60, 40, 30, 30, 40, 50, 50, 70, 65, 50, 40, 30, 5, 5, 5, 5, 5};
//        ProbabilityDistributionIndex probabilityDistributionIndexOfAmountOfGroupsGoingToBarAtMinute = new ProbabilityDistributionIndex(new int[]{100, 30, 10, 5, 1});
//        ProbabilityDistributionIndex probabilityDistributionIndexGroupSize = new ProbabilityDistributionIndex(new int[]{10,15,15,40,50,60,60,40,20,10,5,5});
//        ProbabilityDistributionIndex probabilityDistributionIndexTimeWaitOrder = new ProbabilityDistributionIndex(new int[]{5, 10, 20, 20, 30, 50, 70});
//        ProbabilityDistributionIndex probabilityDistributionIndexTimeWaitLeave = new ProbabilityDistributionIndex(new int[]{5, 5, 10, 10, 20, 30, 30, 40, 50, 80});
//
//        int barOperatingHours = 16;
//        int barOpeningHour = 6;

        // Overloaded Easy Mode
//        animationTimeScalar = .2;
//        int[] probabilityDistributionsOfGroupsGoingToBarPerHourOutOf100 = new int[]{100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100};
//        ProbabilityDistributionIndex probabilityDistributionIndexOfAmountOfGroupsGoingToBarAtMinute = new ProbabilityDistributionIndex(new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1});
//        ProbabilityDistributionIndex probabilityDistributionIndexGroupSize = new ProbabilityDistributionIndex(new int[]{1});
//        ProbabilityDistributionIndex probabilityDistributionIndexTimeWaitOrder = new ProbabilityDistributionIndex(new int[]{1});
//        ProbabilityDistributionIndex probabilityDistributionIndexTimeWaitLeave = new ProbabilityDistributionIndex(new int[]{1});
//
//        int barOperatingHours = 6;
//        int barOpeningHour = 6;

        // Overloaded Hard Mode
//        animationTimeScalar = .05;
//        int[] probabilityDistributionsOfGroupsGoingToBarPerHourOutOf100 = new int[]{100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100};
//        ProbabilityDistributionIndex probabilityDistributionIndexOfAmountOfGroupsGoingToBarAtMinute = new ProbabilityDistributionIndex(new int[]{1});
//        ProbabilityDistributionIndex probabilityDistributionIndexGroupSize = new ProbabilityDistributionIndex(new int[]{1,1,1,1,1,1,1,1,1,1,1,1});
//        ProbabilityDistributionIndex probabilityDistributionIndexTimeWaitOrder = new ProbabilityDistributionIndex(new int[]{1});
//        ProbabilityDistributionIndex probabilityDistributionIndexTimeWaitLeave = new ProbabilityDistributionIndex(new int[]{1});
//
//        int barOperatingHours = 36;
//        int barOpeningHour = 6;

        // barOperatingHours can also be treated as Simulation hour run time
        softwareGurusBarWorld = new SoftwareGurusBar(barOpeningHour, barOperatingHours, probabilityDistributionsOfGroupsGoingToBarPerHourOutOf100, probabilityDistributionIndexGroupSize, probabilityDistributionIndexTimeWaitOrder, probabilityDistributionIndexTimeWaitLeave, probabilityDistributionIndexOfAmountOfGroupsGoingToBarAtMinute);

        // Run the thing
        softwareGurusBarWorld.runSimulationThatRunsEverything();

    }

    private void getLocationAndEventInformation() {
        // Alternative Solution to Highest time absolute of event
        timeMaxBarOperating = softwareGurusBarWorld.getSimulationThatRunsEverything().getCurrentTimeOfSimulationByEvent();

        System.out.println(String.format("Time Max Bar Operation: %s", timeMaxBarOperating));

        // Get Event Strings and put then into another arrayList with the stringsByTimeFromEvent as one string ordered by time
        // timeMaxBarOperating + 1 because essentially value of index at 0 is 0 and not 1 so timeMaxBarOperating should go up to timeMaxBarOperating + 1 to get final value
        // Example at time 0 you have Event Time 0 and not Time 1 so you need 1 more to get the final value in the
        stringsPrintWithEventDataPerTime = new ArrayList<>();
        for (int i = 0; i < softwareGurusBarWorld.getSimulationThatRunsEverything().eventArrayList.size(); i++) {
            String tempString = "";
            for (Event event : softwareGurusBarWorld.getSimulationThatRunsEverything().eventArrayList) {
                EventSimulation eventSimulation = (EventSimulation) event;
                if (eventSimulation.getTime() == i) {
                    // Event's with same time go here

                    // Concat Strings for Events with same time
                    for (String string : eventSimulation.getStrings()) {
                        tempString = tempString.concat(string + "\n");
                    }
                }
            }
            stringsPrintWithEventDataPerTime.add(tempString);
//            System.out.println(tempString);
        }

        // For changing the color of person who ordered at absolute time
        for (int i = 0; i < timeMaxBarOperating; i++) {
            ArrayList<Order> orderArrayList = new ArrayList<>();
            for (Order order : softwareGurusBarWorld.getOrdersMadeAtLocation()) {
                if (order.getTimeAbsoluteBoughtAt() == i) {
                    orderArrayList.add(order);
                }
            }
            arrayListOrdersArrayList.add(orderArrayList);
        }
    }

    private void createShapePersonAndParallelTransition(Pane pane, double paneWidth, double barOffsetY) {
        // Work Bests
        parallelTransition = new ParallelTransition();
        // Add people with the animation to the Pane
        for (Group group : softwareGurusBarWorld.getGroupsThatWentToLocation()) {
            int randomStaringPosition = getRandBetween(0, (int) paneWidth);
            for (Person person : group.getGroupMembers()) {
                int randomVaryingPosition = getRandBetween(randomStaringPosition, randomStaringPosition + 10);

                // Create shapePerson and add it to the pane etc...
                ShapePerson shapePerson = new ShapePerson(softwareGurusBarWorld, pane, person, shapeDimensionScalar);

                // Create path for shapePerson
                shapePerson.createPath(animationTimeScalar, barOffsetY, randomVaryingPosition, -10);

                // Add to shapePeople ArrayList
                shapePeople.add(shapePerson);

                // Get shapePerson's sequentialTransition and add it to the parallelTransition
                parallelTransition.getChildren().add(shapePerson.getShapePath().getSequentialTransitionPerson());
            }
        }
    }

    private void createTablesAndAddThemToPane(Pane pane) {
        // For Table in World Create ShapeTable given Table

        //*** IMPORTANT ***
        // REMEMBER THAT YOU ARE RELATIVE TO THE BOTTOM LEFT FOR SHAPES

        // Meta window size stuff
        double paneTempHeightBiggestOffset = 25;

        double paneTempWidthBiggest = 0;
        double paneTempHeightBiggest = 0;

        // Make ShapeTable Starting Position
        double tableSpacingWidthAbsoluteOffset = 0; //20
        double tableSpacingHeightAbsoluteOffset = barOffsetY + 0;

        // Table Spacing offset (shapeDimensionScalar * 2) for table and seat, 3/2 for 1 shapeDimensionScalar spacing
        double tableSpacingHeightOffset = ((shapeDimensionScalar * 2) / 2) * 3;
        double tableSpacingWidthOffset = 20;

        for (int i = 0; i < softwareGurusBarWorld.getTablesAtTheLocation().size(); i++) {
            Table tempTable = softwareGurusBarWorld.getTablesAtTheLocation().get(i);

            // Spacing by width
            double spaceByShapeDimensionScalar = (tempTable.getTableNumber() * shapeDimensionScalar);

            // This is the size of the spacing between the table
            double spaceByTableSizeRelativeToShapeDimensionScalar = (tempTable.getTableSize() * shapeDimensionScalar);
            double spaceByTableWidthOffset = (tempTable.getTableNumber() * (tableSpacingWidthOffset + spaceByTableSizeRelativeToShapeDimensionScalar));

            // Spacing by height
            double spaceByTableHeight = (tableSpacingHeightOffset * tempTable.getTableSize());

            // Final initialPositionX
            double initialPositionX = spaceByShapeDimensionScalar + spaceByTableWidthOffset + tableSpacingWidthAbsoluteOffset;
            double initialPositionY = spaceByTableHeight + tableSpacingHeightAbsoluteOffset;

            addShapeTableToPane(
                    pane,
                    shapeDimensionScalar,
                    initialPositionX,
                    initialPositionY,
                    tempTable);

            double tempPaneWidth = initialPositionX + spaceByTableSizeRelativeToShapeDimensionScalar;
            double tempPaneHeight = initialPositionY + paneTempHeightBiggestOffset;

            if (tempPaneWidth > paneTempWidthBiggest) {
                paneTempWidthBiggest = tempPaneWidth;
            }
            if (tempPaneHeight > paneTempHeightBiggest) {
                paneTempHeightBiggest = tempPaneHeight;
            }
        }

        paneRootWidth = paneTempWidthBiggest;
        paneRootHeight = paneTempHeightBiggest;
    }

    private void addShapeTableToPane(Pane pane, double shapeDimensionScalar, double initialPositionX, double initialPositionY, Table table) {

        // Create ShapeTable
        ShapeTable tempShapeTable = new ShapeTable(table, shapeDimensionScalar, initialPositionX, initialPositionY);

        // Add to tempShapeTable array list
        shapeTableArrayList.add(tempShapeTable);

        // Add to tempShapeTable pane
        pane.getChildren().add(tempShapeTable.getShapeTable());

        // Create seats for shapeTable and add to pane
        for (int i = 0; i < tempShapeTable.getTable().getTableSize(); i++) {
            // Get ShapeSeat
            ShapeTable.ShapeSeat tempShapeSeat = tempShapeTable.getShapeSeats().get(i);

            // Add to tempShapeSeat array list
            shapeSeatsArrayList.add(tempShapeSeat);

            // Add to tempShapeSeat pane
            pane.getChildren().add(tempShapeSeat.getShapeSeat());
        }
    }

    private void addShapeWallsToPane(Pane pane, double barOffsetY, double shapeDimensionScalar, double shapeWallEntranceSize, double windowWidth) {
        double wallLengthOffset = (shapeWallEntranceSize / 2);
        double x = (windowWidth);
        double wallLength = (windowWidth / 2) - wallLengthOffset;

        ShapeWall leftWallEntrance = new ShapeWall(shapeDimensionScalar, wallLength, 0, barOffsetY);
        ShapeWall rightWallEntrance = new ShapeWall(shapeDimensionScalar, wallLength, (windowWidth / 2) + wallLengthOffset, barOffsetY);

        pane.getChildren().add(leftWallEntrance.getShapeWall());
        pane.getChildren().add(rightWallEntrance.getShapeWall());

    }

    private int getRandBetween(int low, int high) {
        return low + (int) ((high - low + 1) * Math.random());
    }


    public class PaneInformationClockEvent extends GridPane {
        // https://www.youtube.com/watch?v=BmhW8rhywvA&t
        private Timeline timeline;

        private int fontSize = 50;

        // Label Counter
        private Label labelCounter = new Label("");
        private int time0 = 0;

        // Label Event Information
        private Label labelInformationEvent = new Label("");

        // Label Profits
        private Label labelProfits = new Label("$0");

        // Label Clock Information
        private String timeClock = "00:00";
        private Label labelClock = new Label(timeClock);
        private int timeMinute;

        // Bar profits
        private int profits = 0;

        // Location Information
        private int timeMaxBarOperating;

        private ArrayList<String> stringsByTimeFromEvent;

        private ArrayList<ArrayList<Order>> ordersByTimeFromEvent;

        private PaneInformationClockEvent(double clockSpeed, ArrayList<String> stringsByTimeFromEvent, ArrayList<ArrayList<Order>> ordersByTimeFromEvent, int barActualTime, int timeMaxBarOperating) {
            this.stringsByTimeFromEvent = stringsByTimeFromEvent;
            this.ordersByTimeFromEvent = ordersByTimeFromEvent;
            this.timeMaxBarOperating = timeMaxBarOperating;

            System.out.println(timeMaxBarOperating);
            this.timeMinute = barActualTime * 60; // 6 am

            Pane paneTop = new Pane();
            // Aligning the spacing for the Top pane
            labelCounter.setFont(Font.font(fontSize));
            labelCounter.setTranslateX((paneInformationClockEventWidth / 3 * 1) - (paneInformationClockEventWidth / 3));
            labelCounter.setAlignment(Pos.TOP_LEFT);
            paneTop.getChildren().add(labelCounter);

            labelClock.setFont(Font.font(fontSize));
            labelClock.setTranslateX((paneInformationClockEventWidth / 3 * 2) - (paneInformationClockEventWidth / 3));
            paneTop.getChildren().add(labelClock);

            labelProfits.setFont(Font.font(fontSize));
            labelProfits.setTranslateX((paneInformationClockEventWidth / 3 * 3) - (paneInformationClockEventWidth / 3));
            paneTop.getChildren().add(labelProfits);

            paneTop.setPrefWidth(this.getPrefWidth());
            this.add(paneTop, 0, 0);

            labelInformationEvent.setFont(Font.font(13));
//            labelInformationEvent.setTranslateX(0);
//            labelInformationEvent.setTranslateY(paneInformationClockEventHeight/2);
//            labelInformationEvent.setMaxWidth(Double.MAX_VALUE);
//            labelInformationEvent.setAlignment(Pos.CENTER);
            this.add(labelInformationEvent, 0, 1);

            timeline = new Timeline(new KeyFrame(Duration.seconds(clockSpeed), actionEvent -> this.timeCalls()));
            timeline.setCycleCount((Timeline.INDEFINITE));
        }

        public void playTimeline() {
            timeline.play();

        }

        private void timeCalls() {
            timeLabel();
        }

        private void timeLabel() {

//            System.out.println(String.format("Parallel Transition: %s", parallelTransition.getCurrentTime()));
//            System.out.println(String.format("PaneInformationClockEvent Timeline: %s", timeline.getCurrentTime()));

            // https://stackoverflow.com/questions/8916472/convert-integer-minutes-into-string-hhmm
            int h = timeMinute / 60 + Integer.parseInt(timeClock.substring(0, 1));
            int m = timeMinute % 60 + Integer.parseInt(timeClock.substring(3, 4));
//            String newTime = h + ":" + m;

            int hourModulus = ((h - 1)%12) + 1;
            String newTime = String.format("%02d:%02d", hourModulus, m);

            labelClock.setText(newTime);

            String time0String = String.valueOf(time0);
            labelCounter.setText(time0String);

            labelInformationEvent.setText(stringsByTimeFromEvent.get(time0));

            // stringsByTimeFromEvent starts at index 0 with value of 0
            if (time0 < timeMaxBarOperating){
                changeColorOfShapePersonAndUpdateProfits(time0);
            }

            time0++;
            timeMinute++;

            double tempHeight = fontSize + labelInformationEvent.getHeight() + 10;
            paneInformationClockEvent.setPrefHeight(tempHeight);

//            System.out.println(time0);
//            System.out.println(stringsByTimeFromEvent.get((int)time0));

            // stringsByTimeFromEvent starts at index 0 with value of 0
            if (time0 == timeMaxBarOperating + 1) {
                timeline.stop();
            }

        }

        private void changeColorOfShapePersonAndUpdateProfits(int currentTime0) {
            for (Order order : ordersByTimeFromEvent.get(currentTime0)) {
                order.getPersonWhoOrdered().getShapePerson().changeColorMappedBasedOnOrder();

                // Update profits
                profits += order.getFoodItemThatPersonOrdered().getFoodCost();

                // Update profits label
                String tempString = "$" + String.valueOf(profits);
                labelProfits.setText(tempString);
            }
        }

        public void setTimelineDelay(double time) {
            timeline.setDelay(Duration.millis(time * 1000));
        }
    }

    public class PaneInformationBar extends GridPane {

        private int fontSize = 12;
        private Label labelBarOperationalHours = new Label("");
        private Label labelArrayProbabilityDistributionsOfGroupsGoingToBarPerHourOutOf100 = new Label("");
        private Label labelArrayListProbabilityDistributionsOfGroupsGoingToBarPerHourOutOf100Index = new Label("");
        private Label labelProbabilityDistributionIndexGroupSize = new Label("");
        private Label labelProbabilityDistributionIndexTimeWaitOrder = new Label("");
        private Label labelProbabilityDistributionIndexOfAmountOfGroupsGoingToBarAtMinute = new Label("");
        private Label labelProbabilityDistributionIndexTimeWaitLeave = new Label("");

        private Label labelProbabilityDistribution100PercentOfOrdering = new Label("");
        private Label labelProbabilityDistributionIndexAmountOfOrderGenericItemMethodSuccessfulCounterArray = new Label("");
        private Label labelProbabilityDistributionIndexOfTimeWaitingInsteadOfOrdering = new Label("");


        private SoftwareGurusBar softwareGurusBar;

        public PaneInformationBar(SoftwareGurusBar softwareGurusBar){
            this.softwareGurusBar = softwareGurusBar;

            labelBarOperationalHours.setFont(Font.font(fontSize));
            labelBarOperationalHours.setText(String.format(String.format("Bar Opens at %s    Bar Open until Hour: %s (Index = %s)   Bar Open until Minute: %s    Darkening Color = Ordered Item",softwareGurusBar.getBarOpeningHour(), softwareGurusBar.getBarOperatingHours(),softwareGurusBar.getBarOperatingHours() - 1,softwareGurusBar.getBarOperatingMinutes())));
            this.add(labelBarOperationalHours, 0 ,0);

            labelArrayProbabilityDistributionsOfGroupsGoingToBarPerHourOutOf100.setFont(Font.font(fontSize));
            labelArrayProbabilityDistributionsOfGroupsGoingToBarPerHourOutOf100
                    .setText(String.format("Probability Distribution of going to bar. Index = Hour - 1. [Index] = Probability of going to Bar \n%s", Arrays.toString(this.softwareGurusBar.getArrayProbabilityDistributionsOfGroupsGoingToBarPerHourOutOf100())));
            this.add(labelArrayProbabilityDistributionsOfGroupsGoingToBarPerHourOutOf100, 0 ,1);

            labelArrayListProbabilityDistributionsOfGroupsGoingToBarPerHourOutOf100Index.setFont(Font.font(fontSize));
            labelArrayListProbabilityDistributionsOfGroupsGoingToBarPerHourOutOf100Index.
                    setText(String.format("DON'T PRINT THIS %s",this.softwareGurusBar.getArrayListProbabilityDistributionsOfGroupsGoingToBarPerHourOutOf100Index()));
//            this.add(labelArrayListProbabilityDistributionsOfGroupsGoingToBarPerHourOutOf100Index, 0 ,1);

            labelProbabilityDistributionIndexGroupSize.setFont(Font.font(fontSize));
            labelProbabilityDistributionIndexGroupSize.
                    setText(String.format("Probability Distribution of Group Size Based on Index + 1. Index = Group Size. [Index] = Probability of Group Size = indexValue/totalValuesSum\n%s",this.softwareGurusBar.getProbabilityDistributionIndexGroupSize()));
            this.add(labelProbabilityDistributionIndexGroupSize, 0 ,2);

            labelProbabilityDistributionIndexTimeWaitOrder.setFont(Font.font(fontSize));
            labelProbabilityDistributionIndexTimeWaitOrder.
                    setText(String.format("Probability Distribution for Group Wait Order Time Event based on Index + 1. Index = Wait Order Time. [Index] = Probability of Wait Order Time = [Index]/totalValuesSum.\n%s",this.softwareGurusBar.getProbabilityDistributionIndexTimeWaitOrder()));
            this.add(labelProbabilityDistributionIndexTimeWaitOrder, 0 ,3);

            labelProbabilityDistributionIndexOfAmountOfGroupsGoingToBarAtMinute.setFont(Font.font(fontSize));
            labelProbabilityDistributionIndexOfAmountOfGroupsGoingToBarAtMinute.
                    setText(String.format("Probability Distribution for Amount of Groups per Minute based on Index + 1. Index = Amount of Groups. [Index] = Probability of Groups per Minute = [Index]/totalValuesSum.\n%s",this.softwareGurusBar.getProbabilityDistributionIndexOfAmountOfGroupsGoingToBarAtMinute()));
            this.add(labelProbabilityDistributionIndexOfAmountOfGroupsGoingToBarAtMinute, 0 ,4);

            labelProbabilityDistributionIndexTimeWaitLeave.setFont(Font.font(fontSize));
            labelProbabilityDistributionIndexTimeWaitLeave.
                    setText(String.format("Probability Distribution for Group Wait Leave Time Event based on Index + 1. Index = Wait Leave Time. [Index] = Probability of Wait Leave Time = [Index]/totalValuesSum.\n%s",this.softwareGurusBar.getProbabilityDistributionIndexTimeWaitLeave()));
            this.add(labelProbabilityDistributionIndexTimeWaitLeave, 0 ,5);

            Person person = softwareGurusBar.getPeopleWhoWentToLocation().get(0);
            if(person != null){
                labelProbabilityDistribution100PercentOfOrdering.setFont(Font.font(fontSize));
                labelProbabilityDistribution100PercentOfOrdering.setText(String.format("Probability Distribution Array for Ordering per Order Method Call. Index = Order Method Call. [LeftInnerIndex] = Not Ordering Probability, [InnerIndex] = Ordering Probability\n%s", person.getProbabilityDistribution100PercentOfOrdering()));
                this.add(labelProbabilityDistribution100PercentOfOrdering, 0 , 6);

                labelProbabilityDistributionIndexAmountOfOrderGenericItemMethodSuccessfulCounterArray.setFont(Font.font(fontSize));
                labelProbabilityDistributionIndexAmountOfOrderGenericItemMethodSuccessfulCounterArray.setText(String.format("Probability Distribution Array for Amount of orders based Successful Ordering of a Order Method Call.\nIndex = Order Method Call. InnerIndex = Amount of Orders. [InnerIndex] = Probability for Amount of Orders = [Index]/totalValuesSum.\n%s", Arrays.toString(person.getProbabilityDistributionIndexAmountOfOrderGenericItemMethodSuccessfulCounterArray())));
                this.add(labelProbabilityDistributionIndexAmountOfOrderGenericItemMethodSuccessfulCounterArray, 0 , 7);

                labelProbabilityDistributionIndexOfTimeWaitingInsteadOfOrdering.setFont(Font.font(fontSize));
                labelProbabilityDistributionIndexOfTimeWaitingInsteadOfOrdering.setText(String.format("Probability Distribution of Waiting instead of Ordering. It is the Unsuccessful Ordering based on Index + 1.\nIndex = Amount of time to wait until making an Order. [Index] = Probability for Time Waiting = [Index]/totalValuesSum.\n%s", person.getProbabilityDistributionIndexOfTimeWaitingInsteadOfOrdering()));
                this.add(labelProbabilityDistributionIndexOfTimeWaitingInsteadOfOrdering, 0 , 8);

            }
        }
    }
}

// IGNORE
//class PaneBar{
//    private double shapeDimensionScalar;
//    private double barOffset;
//
//    // Pane width
//    private double paneWidth;
//    private double paneHeight;
//
//    // Bar Wall (Ignore)
//    private ArrayList<ShapeWall> barWalls = new ArrayList<>();
//
//    // Bar Entrance
//    private double entranceSize;
//    
//    private ArrayList<ShapeWall> barWallsEntrance = new ArrayList<>();
//
//    public PaneBar(double shapeDimensionScalar, double barOffset, double paneBarWidth, double paneBarHeight ){
//        this.shapeDimensionScalar = shapeDimensionScalar;
//        this.paneWidth = paneBarWidth;
//        this.paneHeight = paneBarHeight;
//
//
//        this.barOffset = barOffset;
//        this.entranceSize = this.shapeDimensionScalar * .75;
//
//        createBar();
//    }
//
//    private void createBar() {
//        double lengthBarWall = (paneWidth/2) - (entranceSize/2);
//
//        // IGNORE I AM NOT ROTATING WALLS!
////        for(int i =0; i< 3; i++){
////            barWalls.add(new ShapeWall(shapeDimensionScalars, 2,2,2))
////        }
//        
//        barWallsEntrance.add(new ShapeWall(shapeDimensionScalar,lengthBarWall,0,barOffset + 50 ));
//        barWallsEntrance.add(new ShapeWall(shapeDimensionScalar,lengthBarWall,(paneWidth/2)+(entranceSize/2),barOffset + 50 ));
//
//    }
//    public ArrayList<ShapeWall> getBarWallsEntrance() {
//        return barWallsEntrance;
//    }
//
//}

class ShapePath {

    double paneCenter;
    // Transition path
    private SequentialTransition sequentialTransitionPerson;
    // StackPane
    private Pane pane;
    // Person who has this path
    private ShapePerson shapePerson;

    // Seat to go to
    private ShapeTable.ShapeSeat shapeSeat;

    // Initial position
    private double initialPositionX;
    private double initialPositionY;

    // Bar offset
    private double barOffsetY;

    // Animation time scalar
    private double animationTimeScalar;

    // Look up Get objects Wait time (+ 1 because it isn't real to wait 0 minutes) in SoftwareGurusBar
    private double timeRelativeWaitOrderWalk = 500; // must be within range of 1 to 999, small is faster

    public ShapePath(Pane pane, ShapePerson shapePerson, double animationTimeScalar, ShapeTable.ShapeSeat shapeSeat, double barOffsetY, double initialPositionX, double initialPositionY) {
        this.pane = pane;
        this.paneCenter = pane.getPrefWidth() / 2;

        this.barOffsetY = barOffsetY;

        this.shapePerson = shapePerson;
        this.animationTimeScalar = animationTimeScalar;
        this.shapeSeat = shapeSeat;

        this.initialPositionX = initialPositionX;
        this.initialPositionY = initialPositionY;

        if (shapeSeat != null) {
            createSequentialTransitionSuccess();
        } else {
            createSequentialTransitionFailure();
        }
    }

    private void createSequentialTransitionSuccess() {

        Group tempGroup = shapePerson.getPerson().getGroup();

        double timeAbsoluteArrive = tempGroup.getTimeAbsoluteArrive() * 1000;

        double timeRelativeWaitOrder = ((double) tempGroup.getTimeRelativeWaitOrder()) * 1000;
        double timeRelativeWaitOrderWithOffset = (timeRelativeWaitOrder - timeRelativeWaitOrderWalk);

        double timeRelativeWaitLeave = ((double) tempGroup.getTimeRelativeWaitLeave()) * 1000;
        double timeRelativeWaitLeaveWithOffset = (timeRelativeWaitLeave - timeRelativeWaitOrderWalk); // Not Necessary

        double timeRelativeMostSpentSetByAPerson = tempGroup.getTimeRelativeMostSpentSetByAPerson() * 1000;
        double timeRelativeMostSpentSetByAPersonNoWaitTime = (tempGroup.getTimeRelativeMostSpentSetByAPerson() - tempGroup.getTimeRelativeWaitOrder() - tempGroup.getTimeRelativeWaitLeave()) * 1000;
        double timeRelativeMostSpentFromOrders = tempGroup.getTimeRelativeMostSpentFromOrders() * 1000;

        // *** VERY VERY COMPLEX TIMING ***
        // Give the stackPane of the shapePerson the animation
        sequentialTransitionPerson = new SequentialTransition(shapePerson.getStackPane());

        sequentialTransitionPerson.getChildren().add(pathPause(timeAbsoluteArrive * animationTimeScalar));
        sequentialTransitionPerson.getChildren().add(pathTransitionEntrance((timeRelativeWaitOrderWalk / 2) * animationTimeScalar));
        sequentialTransitionPerson.getChildren().add(pathTransitionSitDown((timeRelativeWaitOrderWalk / 2) * animationTimeScalar));
        sequentialTransitionPerson.getChildren().add(pathPause((timeRelativeWaitOrderWithOffset + timeRelativeMostSpentSetByAPersonNoWaitTime + timeRelativeWaitLeave) * animationTimeScalar));
        sequentialTransitionPerson.getChildren().add(pathTransitionBackToEntrance((timeRelativeWaitOrderWalk / 2) * animationTimeScalar));
        sequentialTransitionPerson.getChildren().add(pathTransitionLeave((timeRelativeWaitOrderWalk / 2) * animationTimeScalar));
    }

    private void createSequentialTransitionFailure() {
        double timeRelativeWaitOrderLeave = (shapePerson.getPerson().getGroup().getTimeRelativeWaitOrder() + shapePerson.getPerson().getGroup().getTimeRelativeWaitLeave());

        int timeAbsoluteArrive = shapePerson.getPerson().getGroup().getTimeAbsoluteArrive();

        sequentialTransitionPerson = new SequentialTransition(shapePerson.getStackPane());
        sequentialTransitionPerson.getChildren().add(pathPause(((timeAbsoluteArrive * 1000)) * animationTimeScalar));
        sequentialTransitionPerson.getChildren().add(pathTransitionFailure(timeRelativeWaitOrderWalk * animationTimeScalar));
    }

    private PauseTransition pathPause(double time) {
        PauseTransition pauseTransition = new PauseTransition();
        pauseTransition.setDuration(Duration.millis(time));
        return pauseTransition;
    }

    private PathTransition pathTransitionEntrance(double time) {
        Path path = new Path();
        path.getElements().add(new MoveTo(initialPositionX, initialPositionY));
        path.getElements().add(new LineTo(paneCenter, barOffsetY));

        PathTransition pathTransition = new PathTransition();

//        pathTransition.setNode(circlePerson);
        pathTransition.setDuration(Duration.millis(time));
        pathTransition.setPath(path);
//        pathTransition.play();

        return pathTransition;
    }

    public SequentialTransition getSequentialTransitionPerson() {
        return sequentialTransitionPerson;
    }

    private PathTransition pathTransitionSitDown(double time) {
        Path path = new Path();
        path.getElements().add(new MoveTo(paneCenter, barOffsetY));
        path.getElements().add(new VLineTo(shapeSeat.getShapeSeat().getCenterY()));
        path.getElements().add(new HLineTo(shapeSeat.getShapeSeat().getCenterX()));

        PathTransition pathTransition = new PathTransition();

//        pathTransition.setNode(circlePerson);
        pathTransition.setDuration(Duration.millis(time));
        pathTransition.setPath(path);
//        pathTransition.play();

        return pathTransition;
    }

    private PathTransition pathTransitionBackToEntrance(double time) {
        Path path = new Path();
        path.getElements().add(new MoveTo(shapeSeat.getShapeSeat().getCenterX(), shapeSeat.getShapeSeat().getCenterY()));
        path.getElements().add(new HLineTo(paneCenter));
        path.getElements().add(new VLineTo(barOffsetY));

        PathTransition pathTransition = new PathTransition();

//        pathTransition.setNode(circlePerson);
        pathTransition.setDuration(Duration.millis(time));
        pathTransition.setPath(path);
//        pathTransition.play();

        return pathTransition;
    }

    private PathTransition pathTransitionLeave(double time) {
        Path path = new Path();
        path.getElements().add(new MoveTo(paneCenter, barOffsetY));
        path.getElements().add(new LineTo(initialPositionX, initialPositionY));

        PathTransition pathTransition = new PathTransition();

//        pathTransition.setNode(circlePerson);
        pathTransition.setDuration(Duration.millis(time));
        pathTransition.setPath(path);
//        pathTransition.play();

        return pathTransition;
    }

    private PathTransition pathTransitionFailure(double time) {
        Path path = new Path();
        path.getElements().add(new MoveTo(initialPositionX, initialPositionY));
        path.getElements().add(new LineTo(paneCenter, barOffsetY));
        path.getElements().add(new LineTo(initialPositionX, initialPositionY));

        PathTransition pathTransition = new PathTransition();

//        pathTransition.setNode(circlePerson);
        pathTransition.setDuration(Duration.millis(time));
        pathTransition.setPath(path);
//        pathTransition.play();

        return pathTransition;
    }
    //
//    private void createSequentialTransitionSuccessV2() {
//        double wait = (person.getWaitTime() * 1000);
//        sequentialTransitionPerson = new SequentialTransition(stackPane);
//        sequentialTransitionPerson.getChildren().add(pathPause(((person.getArrivalTime() * 1000)) * animationTimeScalar));
//        sequentialTransitionPerson.getChildren().add(pathTransitionPart1(wait * animationTimeScalar));
//        sequentialTransitionPerson.getChildren().add(pathPause((((person.getMostTimeRelativeSpentDrinkingSetByAPerson()) * 1000)) * animationTimeScalar));
//        sequentialTransitionPerson.getChildren().add(pathTransitionPart2(wait * animationTimeScalar));
//    }


    private PathTransition pathTransitionPart1(double time) {
        Path path = new Path();
        path.getElements().add(new MoveTo(initialPositionX, initialPositionY));
        path.getElements().add(new LineTo(paneCenter, barOffsetY));
        path.getElements().add(new MoveTo(paneCenter, barOffsetY));
        path.getElements().add(new VLineTo(shapeSeat.getShapeSeat().getCenterY()));
        path.getElements().add(new HLineTo(shapeSeat.getShapeSeat().getCenterX()));
        PathTransition pathTransition = new PathTransition();

//        pathTransition.setNode(circlePerson);
        pathTransition.setDuration(Duration.millis(time));
        pathTransition.setPath(path);
//        pathTransition.play();

        return pathTransition;
    }

    private PathTransition pathTransitionPart2(double time) {
        Path path = new Path();
        path.getElements().add(new HLineTo(paneCenter));
        path.getElements().add(new VLineTo(barOffsetY));
        path.getElements().add(new MoveTo(paneCenter, barOffsetY));
        path.getElements().add(new LineTo(initialPositionX, initialPositionY));
        PathTransition pathTransition = new PathTransition();

//        pathTransition.setNode(circlePerson);
        pathTransition.setDuration(Duration.millis(time));
        pathTransition.setPath(path);
//        pathTransition.play();

        return pathTransition;
    }
}

//
// call setTableSeat
class ShapePerson {
    // Circle Person
    Circle circlePerson;

    // Person Text
    Text personNumberText;

    // Bar information
    private SoftwareGurusBar softwareGurusBar;
    // Pane to put person on
    private Pane pane;

    // Pane to put Data on
    private StackPane stackPane;
    private double shapeDimensionScalar;

    // Holds the Transitions
    private Person person;
    private Color color;
    private int changeColorMappedBasedOnOrderCounter = 0;

    // ShapePath
    private ShapePath shapePath;

    // Shape Seat
    private ShapeTable.ShapeSeat shapeSeat;

    public ShapePerson(SoftwareGurusBar softwareGurusBar, Pane pane, Person person, double shapeDimensionScalar) {
        this.softwareGurusBar = softwareGurusBar;
        this.shapeDimensionScalar = shapeDimensionScalar;

        // Pane to put shape one
        this.pane = pane;

        // Person at the seat
        this.person = person;

        // Give Person this
        this.person.setShapePerson(this);

        getShapeSeatFromSeat();

        // Person Color
        this.color = color;

        // Creats the shape and add it to the stackPane and puts that stackPane onto the pane (rootPane)
        createCirclePerson();


    }

    public void createPath(double animationTimeScalar, double barOffsetY, double initialPositionX, double initialPositionY) {
        shapePath = new ShapePath(pane, this, animationTimeScalar, shapeSeat, barOffsetY, initialPositionX, initialPositionY);
    }

    private void getShapeSeatFromSeat() {
        if (person.getTableSeat() != null) {
//            System.out.println(String.format("%s %s",person, person.getTableSeat().getShapeTableShapeSeat()));
            shapeSeat = person.getTableSeat().getShapeTableShapeSeat();
        } else {
//            System.out.println("Person get seat " + person.getTableSeat());
            shapeSeat = null;
        }
    }

    private void createCirclePerson() {
        personNumberText = new Text(String.valueOf(person.getID()));
        personNumberText.setFont(Font.font(shapeDimensionScalar * .5));
        personNumberText.setFill(Color.BLACK);

        // Create StackPane
        stackPane = new StackPane();

        circlePerson = new Circle(shapeDimensionScalar / 2);
        circlePerson.setFill(getColorMapped(person.getGroup().getGroupNumber()));

        // Add the Circle and the Text on the the stackPane
        stackPane.getChildren().addAll(circlePerson, personNumberText);

        // Add stackPane on the pane (rootPane) (Add stackPane containing text and shape onto the rootPane)
        // Esentially it is add the the stackPane to the rootPane and then call the parallelTransition
        pane.getChildren().add(stackPane);

    }

    // From Assignment 1
    public Color getColorMapped(int value) {
        int startHue = 0;
        int endHue = 360;

        return transitionOfHueRange(mappingColorHueSpaceOnSolver(value), startHue, endHue, 0);

    }

    public void changeColorMappedBasedOnOrder() {
        changeColorMappedBasedOnOrderCounter++;
        int startHue = 0;
        int endHue = 360;
        Color color = transitionOfHueRange(mappingColorHueSpaceOnSolver(person.getGroup().getGroupNumber()), startHue, endHue, mappingColorLightnessSpaceOnSolver(changeColorMappedBasedOnOrderCounter));
        circlePerson.setFill(color);
        personNumberText.setFill(Color.WHITE);

    }

    private double mappingColorLightnessSpaceOnSolver(double value) {
        double allottedRangeFromLightnessRange = .6;
        return allottedRangeFromLightnessRange / (person.getTotalNumberOfOrders()) * value;

    }

    private double mappingColorHueSpaceOnSolver(int value) {
        // Color hue is 0 to 360
        return (double) 360 / (softwareGurusBar.getGroupsThatWentToLocation().size()) * value;

    }

    // https://stackoverflow.com/questions/46928277/trying-to-convert-integer-range-to-rgb-color
    private Color transitionOfHueRange(double percentage, int startHue, int endHue, double lightnessOffsetSubtraction) {
        // From 'startHue' 'percentage'-many to 'endHue'
        // Finally map from [0°, 360°] -> [0, 1.0] by dividing

        double hue = ((percentage * (endHue - startHue)) + startHue) / 360;
        double saturation = 1.0; // From White to Color
        double lightness = 1.0 - lightnessOffsetSubtraction; // From Black to Color
        double alpha = 1.0; // Transparency


        // Return color
        return Color.hsb(hue, saturation, lightness, alpha);

    }

    public ShapePath getShapePath() {
        return shapePath;
    }

    public StackPane getStackPane() {
        return stackPane;
    }

    public Person getPerson() {
        return person;
    }

    public Circle getCirclePerson() {
        return circlePerson;
    }
}

class ShapeTable {
    // FX settings
    private final double SHAPE_DIMENSION_SCALAR_OFFSET = .75;
    private double shapeDimensionScalar;
    private double initialPositionX;
    private double initialPositionY;
    // Table
    private Table table;
    // ShapeTable
    private Rectangle shapeTable;
    private double shapeTableHeight;
    private double shapeTableWidth;
    private ArrayList<ShapeSeat> shapeSeats = new ArrayList<>();


    public ShapeTable(Table table, double shapeDimensionScalar, double initialPositionX, double initialPositionY) {
        this.table = table;
        this.shapeDimensionScalar = shapeDimensionScalar;
        this.initialPositionX = initialPositionX;
        this.initialPositionY = initialPositionY;

        this.shapeTableHeight = this.shapeDimensionScalar * SHAPE_DIMENSION_SCALAR_OFFSET;
        this.shapeTableWidth = this.shapeDimensionScalar * table.getTableSize();

        createTable();
    }

    private void createTable() {
        shapeTable = new Rectangle(shapeTableWidth, shapeTableHeight);
        shapeTable.setX(initialPositionX);
        shapeTable.setY(initialPositionY);

        shapeTable.setFill(Color.BROWN);

        for (int i = 0; i < table.getTableSize(); i++) {
            double shapeSeatRadius = ((shapeDimensionScalar) / 2) * SHAPE_DIMENSION_SCALAR_OFFSET;

            // Not Mathematically Correct
//            double initialPositionXOffset = initialPositionX + (i * shapeDimensionScalar);
            // Not Mathematically Correct
//            double initialPositionXOffset = initialPositionX + ((shapeDimensionScalar - (shapeSeatRadius * 2))/2 + shapeDimensionScalar) * i;

            // Mathematically Correct
            // *** IMPORTANT NOTE, CIRCLE STARTS FROM THE BOTTOM LEFT
            // Shift the relative to table x position
            double initialPositionXOffset = initialPositionX + (shapeDimensionScalar * i) + ((shapeDimensionScalar - (shapeSeatRadius * 2)) / 2);

            // Shift the relative to table y position
            double initialPositionYOffset = initialPositionY - ((shapeDimensionScalar - (shapeSeatRadius * 2)) / 2);

            ShapeSeat shapeSeatTemp = new ShapeSeat(this, table.getSeats().get(i), shapeSeatRadius, initialPositionXOffset, initialPositionYOffset);
            shapeSeats.add(shapeSeatTemp);
        }
    }

    public Table getTable() {
        return table;
    }

    public Rectangle getShapeTable() {
        return shapeTable;
    }

    public ArrayList<ShapeSeat> getShapeSeats() {
        return shapeSeats;
    }

    public class ShapeSeat {

        // ShapeTable
        private ShapeTable shapeTable;

        // ShapeSeat
        private Circle shapeSeat;
        private double shapeSeatRadius;

        // ShapeSeat Location
        private double initialPositionX;
        private double initialPositionY;

        // Seat
        private Table.Seat seat;


        public ShapeSeat(ShapeTable shapeTable, Table.Seat seat, double shapeSeatRadius, double initialPositionX, double initialPositionY) {
            this.shapeTable = shapeTable;

            // Assign the actual seat to ShapeSeat
            this.seat = seat;

            // Let the seat know what its ShapeSeat is
            seat.setShapeTableShapeSeat(this);


            // *** IMPORTANT NOTE, CIRCLE STARTS FROM THE BOTTOM LEFT
            this.initialPositionX = initialPositionX;
            this.initialPositionY = initialPositionY;

//            this.shapeSeatRadius = (int) ((shapeDimensionScalar / 2) * SHAPE_DIMENSION_SCALAR_OFFSET);
            this.shapeSeatRadius = shapeSeatRadius;

            createSeat();
        }

        private void createSeat() {
            shapeSeat = new Circle(shapeSeatRadius);
            shapeSeat.setFill(Color.BLACK);

//            shapeTableShapeSeat.setCenterX(initialPositionX + shapeSeatRadius + (shapeSeatRadius/2));
            shapeSeat.setCenterX(initialPositionX + shapeSeatRadius);
            shapeSeat.setCenterY(initialPositionY - shapeSeatRadius);

        }

        public Table.Seat getSeat() {
            return seat;
        }

        public Circle getShapeSeat() {
            return shapeSeat;
        }

        public double getInitialPositionX() {
            return initialPositionX;
        }

        public double getInitialPositionY() {
            return initialPositionY;
        }

        public ShapeTable getShapeTable() {
            return shapeTable;
        }
    }
}

class ShapeWall {

    // Shape Wall
    Rectangle shapeWall;
    private double shapeDimensionScalar;
    private double lengthMultiplier;
    private double initialPositionX;
    private double initialPositionY;

    public ShapeWall(double shapeDimensionScalar, double lengthMultiplier, double initialPositionX, double initialPositionY) {
        this.shapeDimensionScalar = shapeDimensionScalar;
        this.lengthMultiplier = lengthMultiplier;
        this.initialPositionX = initialPositionX;
        this.initialPositionY = initialPositionY;

        createWall();
    }

    private void createWall() {
        // Using Scalar
//        shapeWall = new Rectangle(initialPositionX, initialPositionY, shapeDimensionScalar * lengthMultiplier, shapeDimensionScalar);

        // Using Absolute length
        shapeWall = new Rectangle(initialPositionX, initialPositionY, lengthMultiplier, shapeDimensionScalar);

    }

    public Rectangle getShapeWall() {
        return shapeWall;
    }

    public double getShapeDimensionScalar() {
        return shapeDimensionScalar;
    }

    public double getLengthMultiplier() {
        return lengthMultiplier;
    }

    public double getInitialPositionX() {
        return initialPositionX;
    }

    public double getInitialPositionY() {
        return initialPositionY;
    }
}
