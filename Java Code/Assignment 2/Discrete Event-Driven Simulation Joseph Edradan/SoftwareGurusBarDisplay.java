/*
Software Gurus Bar
Project 2
Joseph Edradan
3/18/19

*** By far one of the most disgusting things i've ever written. ***

 */

import javafx.animation.*;
import javafx.application.Application;
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


public class SoftwareGurusBarDisplay extends Application {
    // Java FX 8 Window OFFSET
    private final int JAVA_FX_WINDOW_OFF_SET = 10;

    // Primary Pane
    private Pane paneRoot;
    private double paneRootWidth;
    private double paneRootHeight;

    // Secondary Pane
    private Pane paneInformation;
    private double paneInformationWidth = 650;
    private double paneInformationHeight = 700;

    // ************** Animation Stuff **************
    // Animation time
    private double animationTimeScalar = .1; // smaller is faster

    // Graphic stuff
    private double shapeDimensionScalar = 15; // Scaling the graphics

    // Bar stuff
    private double barOffsetY = 100;
    private double timeMaxBarOperating;

    // Parallel Transition
    private ParallelTransition parallelTransition;

    // Clock stuff;
    private Clock clockObject;
    // *********************************************

    // ShapeTable information
    private ArrayList<ShapeTable> shapeTables = new ArrayList<>();

    // ShapeSeat information
    private ArrayList<ShapeTable.ShapeSeat> shapeSeats = new ArrayList<>();

    // ShapePerson information
    private ArrayList<ShapePerson> shapePeople = new ArrayList<>();

    // All Sequential Transitions
    private ArrayList<SequentialTransition> sequentialTransitions = new ArrayList<>();

    // SoftwareGurusBar softwareGurusBarWorld
    private SoftwareGurusBar softwareGurusBarWorld;

    // String Event String ArrayList
    ArrayList<String> stringsPrintWithEventDataPerTime;

    public static void main(String[] args) {
        launch(args);
    }

    private Parent createPrimaryPane() {
        paneRoot = new Pane();
//        paneRoot.setPrefSize(paneRootWidth, paneRootHeight);

        //////////////////////////////
//        Menu testMenu = new Menu();
//        testMenu.addDrink(new Drink("Generic Beer", 50, 10, 50));
//        testMenu.addDrink(new Drink("Brandy", 50, 10, 50));
//        testMenu.addDrink(new Drink("Rum", 50, 10, 50));
//        testMenu.addDrink(new Drink("Gin", 50, 10, 50));
//
//        Table testTable = new Table(1, 10);
//        Group testGroup = new Group(1, 5, 100, testMenu);
//
//        testTable.seatGroup(testGroup);

        //**********************

        // Real World Example
//        int[] probabilityDistributionsOfGroupsGoingToBarPerHourOutOf100 = new int[]{4, 8, 10, 15, 20, 30, 60, 60, 40, 30, 30, 40, 50, 50, 70, 65, 50, 40, 30, 0, 0, 0, 0, 0};
//        ProbabilityDistribution probabilityDistributionGroupSize = new ProbabilityDistribution(new int[]{10, 30, 50, 70, 60, 55, 15, 8, 2, 1});
//        ProbabilityDistribution probabilityDistributionWaitTime = new ProbabilityDistribution(new int[]{10, 30, 50, 20, 30, 50, 70});
//        ProbabilityDistribution probabilityDistributionOfAmountOfGroupsGoingToBarAtMinute = new ProbabilityDistribution(new int[]{100, 30, 10, 5, 1});
//        int barOperatingHours = 24;

        // 2 Hour Example ()
//        int[] probabilityDistributionsOfGroupsGoingToBarPerHourOutOf100 = new int[]{80, 80, 80, 80, 80, 80, 80, 80, 80, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 0, 0, 0, 0, 0};
//        ProbabilityDistribution probabilityDistributionGroupSize = new ProbabilityDistribution(new int[]{10, 30, 50, 70, 60, 55, 15, 8, 2, 1});
//        ProbabilityDistribution probabilityDistributionWaitTime = new ProbabilityDistribution(new int[]{10, 30, 50, 20, 30, 50, 70});
//        ProbabilityDistribution probabilityDistributionOfAmountOfGroupsGoingToBarAtMinute = new ProbabilityDistribution(new int[]{100, 30, 10, 5, 1});
//        int barOperatingHours = 2;

        // Heavy Example
//        int[] probabilityDistributionsOfGroupsGoingToBarPerHourOutOf100 = new int[]{0, 100, 0, 0, 100, 0, 0, 60, 0, 100, 0, 100, 50, 50, 70, 65, 50, 40, 30, 10, 10, 0, 0, 0};
//        ProbabilityDistribution probabilityDistributionGroupSize = new ProbabilityDistribution(new int[]{10, 10, 10, 10, 10, 10, 10, 10, 10});
//        ProbabilityDistribution probabilityDistributionWaitTime = new ProbabilityDistribution(new int[]{10, 30, 50, 20, 30, 50, 70});
//        ProbabilityDistribution probabilityDistributionOfAmountOfGroupsGoingToBarAtMinute = new ProbabilityDistribution(new int[]{5, 20, 30, 50});
//        int barOperatingHours = 24;

        // HIGH FREQUENCY OVERLOAD (TOO MANY LOOPS WON'T RUN)
//        int[] probabilityDistributionsOfGroupsGoingToBarPerHourOutOf100 = new int[]{100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 0, 0, 0, 0, 0};
//        ProbabilityDistribution probabilityDistributionGroupSize = new ProbabilityDistribution(new int[]{100, 100, 100, 100, 100, 100, 100, 100, 100, 100});
//        ProbabilityDistribution probabilityDistributionWaitTime = new ProbabilityDistribution(new int[]{10, 20, 20, 20, 30, 50, 70});
//        ProbabilityDistribution probabilityDistributionOfAmountOfGroupsGoingToBarAtMinute = new ProbabilityDistribution(new int[]{1, 1, 1, 1, 1, 1, 1, 1, 100});
//        int barOperatingHours = 10;

//         LIGHT FREQUENCY OVERLOAD (TOO MANY LOOPS WON'T RUN)
        int[] probabilityDistributionsOfGroupsGoingToBarPerHourOutOf100 = new int[]{50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50};
        ProbabilityDistribution probabilityDistributionGroupSize = new ProbabilityDistribution(new int[]{10, 10, 10, 10, 10, 10, 10, 10, 10});
        ProbabilityDistribution probabilityDistributionWaitTime = new ProbabilityDistribution(new int[]{10, 30, 50, 20, 30, 50, 70});
        ProbabilityDistribution probabilityDistributionOfAmountOfGroupsGoingToBarAtMinute = new ProbabilityDistribution(new int[]{5, 20, 30, 50});
        int barOperatingHours = 24;

        // barOperatingHours can also be treated as Simulation hour run time
        softwareGurusBarWorld = new SoftwareGurusBar(barOperatingHours, probabilityDistributionsOfGroupsGoingToBarPerHourOutOf100, probabilityDistributionGroupSize, probabilityDistributionWaitTime, probabilityDistributionOfAmountOfGroupsGoingToBarAtMinute);

        //**********************

        getBarAndEventInformation();
        //*********************

        //*** IMPORTANT ***
        // REMEMBER THAT YOU ARE RELATIVE TO THE BOTTOM LEFT FOR SHAPES

        // Meta window size stuff
        double paneTempWidthBiggest = 0;
        double paneTempHeightBiggest = 0;

        // Make ShapeTable
        double tableSpacingWidthAbsoluteOffset = 0; //20
        double tableSpacingHeightAbsoluteOffset = barOffsetY + 20;

        // Table Spacing offset (shapeDimensionScalar * 2) for table and seat, 3/2 for 1 shapeDimensionScalar spacing
        double tableSpacingHeightOffset = ((shapeDimensionScalar * 2) / 2) * 3;
        double tableSpacingWidthOffset = 20;

        for (int i = 0; i < softwareGurusBarWorld.getTables().size(); i++) {
            Table tempTable = softwareGurusBarWorld.getTables().get(i);

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

            addToRootTable(
                    paneRoot,
                    shapeDimensionScalar,
                    initialPositionX,
                    initialPositionY,
                    tempTable);

            double tempPaneWidth = initialPositionX + spaceByTableSizeRelativeToShapeDimensionScalar;
            double tempPaneHeight = initialPositionY + tableSpacingHeightAbsoluteOffset;

            if (tempPaneWidth > paneTempWidthBiggest) {
                paneTempWidthBiggest = tempPaneWidth;
            }
            if (tempPaneHeight > paneTempHeightBiggest) {
                paneTempHeightBiggest = tempPaneHeight;
            }
        }

        paneRootWidth = paneTempWidthBiggest;
        paneRootHeight = paneTempHeightBiggest;

        paneRoot.setPrefSize(paneRootWidth - JAVA_FX_WINDOW_OFF_SET, paneRootHeight - JAVA_FX_WINDOW_OFF_SET);

        // Make ShapeWall for entrance
        addToRootWallEntrance(paneRoot, barOffsetY, shapeDimensionScalar, 50, paneRootWidth);

        // Does not work properly
        // Add people with the animation to the Pane
//        for (Event event : softwareGurusBarWorld.getSimulationThatRunsEverything().eventArrayList) {
//            for (Person person : ((EventGroup) event).getGroup().getGroupMembers()) {
//                shapePeople.add(new ShapePerson(paneRoot, person, animationTimeScalar, shapeDimensionScalar, 0, 0, shapeSeats, paneRootWidth, barOffsetY));
//            }
//        }

        // Does not work without protected Events in softwareGurusBar
        // Add people with the animation to the Pane
//        for (Event event : softwareGurusBarWorld.getSimulationThatRunsEverything().eventArrayList) {
//            EventGroup eventGroup = (EventGroup) event;
//            if (eventGroup instanceof SoftwareGurusBar.ArriveEvent){
//                for (Person person : ((EventGroup) event).getGroup().getGroupMembers()) {
//                    shapePeople.add(new ShapePerson(paneRoot, person, animationTimeScalar, shapeDimensionScalar, 0, 0, shapeSeats, paneRootWidth, barOffsetY));
//                }
//            }
//        }

        // Works
        // Add people with the animation to the Pane
//        for(Group group: softwareGurusBarWorld.getGroups()){
//            for (Person person : group.getGroupMembers()) {
//                shapePeople.add(new ShapePerson(paneRoot, person, animationTimeScalar, shapeDimensionScalar, 0, 0, shapeSeats, paneRootWidth, barOffsetY));
//
//            }
//        }

        // Work Best
        parallelTransition = new ParallelTransition();
        // Add people with the animation to the Pane
        for (Group group : softwareGurusBarWorld.getGroups()) {
            int randomStaringPosition = getRandBetween(0, (int) paneRootWidth);
            for (Person person : group.getGroupMembers()) {
                int randomVaryingPosition = getRandBetween(randomStaringPosition, randomStaringPosition + 10);
                Color color = getColorMapped(person.getGroupNumber());
                ShapePerson shapePerson = new ShapePerson(paneRoot, person, animationTimeScalar, shapeDimensionScalar, randomVaryingPosition, -10, shapeSeats, paneRootWidth, barOffsetY, color);
                parallelTransition.getChildren().add(shapePerson.getSequentialTransitionPerson());
            }
        }

        // Works Slow
        // Add people with the animation to the Pane
//        for(Group group: softwareGurusBarWorld.getGroups()){
//            for (Person person : group.getGroupMembers()) {
//                ShapePerson shapePerson = new ShapePerson(paneRoot, person, animationTimeScalar, shapeDimensionScalar, 0, 0, shapeSeats, paneRootWidth, barOffsetY);
////                sequentialTransitions.add(shapePerson.getSequentialTransitionPerson());
////                shapePerson.getSequentialTransitionPerson().play();
//            }
//        }
//        for (SequentialTransition sequentialTransition: sequentialTransitions) {
//            sequentialTransition.play();
//        }

        return paneRoot;
    }

    private void getBarAndEventInformation() {

        // Highest time absolute of event
        timeMaxBarOperating = 0;
        for (Event event : softwareGurusBarWorld.getSimulationThatRunsEverything().eventArrayList) {
            if (event.getTime() > timeMaxBarOperating) {
                timeMaxBarOperating = event.getTime();
            }
        }
        System.out.println(String.format("Time Max Bar Operation: %s", timeMaxBarOperating));

        stringsPrintWithEventDataPerTime = new ArrayList<>();
        for (int i = 0; i < timeMaxBarOperating; i++) {
            String tempString = "";
            for (Event event : softwareGurusBarWorld.getSimulationThatRunsEverything().eventArrayList) {
                EventGroup eventGroup = (EventGroup) event;
                if (eventGroup.getTime() == i) {
                    for (String string : eventGroup.getStrings()) {
                        tempString = tempString.concat(string + "\n");
                    }
                }
            }
            stringsPrintWithEventDataPerTime.add(tempString);
//            System.out.println(tempString);
        }

//        for(String string: stringsPrintWithEventDataPerTime){
//            System.out.println(string);
//        }
    }

    private Parent createSecondaryPane() {
        paneInformation = new Pane();
        paneInformation.setPrefSize(paneInformationWidth, paneInformationHeight);

        clockObject = new Clock(animationTimeScalar, stringsPrintWithEventDataPerTime, (int) timeMaxBarOperating);
        paneInformation.getChildren().add(clockObject);

        return paneInformation;
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

        Scene secondaryScene = new Scene(createSecondaryPane());
        Stage secondaryStage = new Stage();
        secondaryStage.setScene(secondaryScene);
        secondaryStage.setResizable(false);
        primaryStage.setTitle("Bar Information");
        primaryStage.setX(200);
        primaryStage.setY(100);
        secondaryStage.show();


//        parallelTransition.setDelay(Duration.seconds(2));
        parallelTransition.play();
        clockObject.playTimeline();
    }


    private void addToRootWallEntrance(Pane pane, double barOffsetY, double shapeDimensionScalar, double shapeWallEntranceSize, double windowWidth) {
        double wallLengthOffset = (shapeWallEntranceSize / 2);
        double x = (windowWidth);
        double wallLength = (windowWidth / 2) - wallLengthOffset;

        ShapeWall leftWallEntrance = new ShapeWall(shapeDimensionScalar, wallLength, 0, barOffsetY);
        ShapeWall rightWallEntrance = new ShapeWall(shapeDimensionScalar, wallLength, (windowWidth / 2) + wallLengthOffset, barOffsetY);

        pane.getChildren().add(leftWallEntrance.getShapeWall());
        pane.getChildren().add(rightWallEntrance.getShapeWall());

    }

    private void addToRootTable(Pane pane, double shapeDimensionScalar, double initialPositionX, double initialPositionY, Table table) {

        // Create ShapeTable
        ShapeTable tempShapeTable = new ShapeTable(table, shapeDimensionScalar, initialPositionX, initialPositionY);

        // Add to tempShapeTable array list
        shapeTables.add(tempShapeTable);

        // Add to tempShapeTable pane
        pane.getChildren().add(tempShapeTable.getShapeTable());

        // Create seats for table and add to pane
        for (int i = 0; i < tempShapeTable.getTable().getTableSize(); i++) {
            // Get ShapeSeat
            ShapeTable.ShapeSeat tempShapeSeat = tempShapeTable.getShapeSeats().get(i);

            // Add to tempShapeSeat array list
            shapeSeats.add(tempShapeSeat);

            // Add to tempShapeSeat pane
            pane.getChildren().add(tempShapeSeat.getShapeSeat());
        }
    }

    private int getRandBetween(int low, int high) {
        return low + (int) ((high - low + 1) * Math.random());
    }

    // From Assignment 1
    public Color getColorMapped(int value) {
        int startHue = 0;
        int endHue = 360;

        return transitionOfHueRange(mappingColorSpaceOnSolver(value), startHue, endHue);

    }

    private double mappingColorSpaceOnSolver(int value) {
//        int solverIndex = pixelLabel - 2;
        // Color hue is 0 to 360
//        return (double) 360 / (softwareGurusBarWorld.getPeopleThatWentToBar() - 1) * solverIndex;
        return (double) 360 / (softwareGurusBarWorld.getGroups().size()) * value;

    }

    // https://stackoverflow.com/questions/46928277/trying-to-convert-integer-range-to-rgb-color
    private Color transitionOfHueRange(double percentage, int startHue, int endHue) {
        // From 'startHue' 'percentage'-many to 'endHue'
        // Finally map from [0°, 360°] -> [0, 1.0] by dividing
        double hue = ((percentage * (endHue - startHue)) + startHue) / 360;

        double saturation = 1.0;
        double lightness = 1.0;
        double alpha = 1.0;


        // Return color
        return Color.hsb(hue, saturation, lightness, alpha);

    }

    public class Clock extends GridPane {
        // https://www.youtube.com/watch?v=BmhW8rhywvA&t
        private Timeline timeline;
        private int time0;
        private int timeMinute;
        private String timeClock = "00:00";

        Label labelClock = new Label(timeClock);
        Label labelCounter = new Label("0");
        Label labelInfo = new Label("");
        private ArrayList<String> strings;
        private int clockEnd;

        int fontSize = 50;

        private Clock(double clockSpeed, ArrayList<String> strings, int clockEnd) {
            this.strings = strings;
            this.clockEnd = clockEnd;

            this.time0 = 0;
            this.timeMinute = 360; // 6 am

            labelClock.setFont(Font.font(fontSize));
//            labelClock.setTranslateX(paneInformationWidth/2);
//            labelClock.setTranslateY(fontSize);
//            labelClock.setMaxWidth(Double.MAX_VALUE);
//            this.add(labelClock, 1, 0);

            labelCounter.setFont(Font.font(fontSize));
//            labelCounter.setTranslateX(paneInformationWidth/2);
//            labelCounter.setTranslateY(fontSize*2);
//            labelCounter.setMaxWidth(Double.MAX_VALUE);
//            labelCounter.setAlignment(Pos.CENTER);
            this.add(labelCounter, 0, 0);

            labelInfo.setFont(Font.font(13));
//            labelInfo.setTranslateX(0);
//            labelInfo.setTranslateY(paneInformationHeight/2);
//            labelInfo.setMaxWidth(Double.MAX_VALUE);
//            labelInfo.setAlignment(Pos.CENTER);
            this.add(labelInfo, 0, 1);

            timeline = new Timeline(new KeyFrame(Duration.seconds(clockSpeed), actionEvent -> this.timeLabel()));
            timeline.setCycleCount((Timeline.INDEFINITE));
        }

        private void playTimeline() {
            timeline.play();

        }

        private void timeLabel() {

            System.out.println(String.format("Parallel Transition: %s", parallelTransition.getCurrentTime()));
            System.out.println(String.format("Clock Timeline: %s", timeline.getCurrentTime()));

            // https://stackoverflow.com/questions/8916472/convert-integer-minutes-into-string-hhmm
            int h = timeMinute / 60 + Integer.parseInt(timeClock.substring(0, 1));
            int m = timeMinute % 60 + Integer.parseInt(timeClock.substring(3, 4));
            String newTime = h + ":" + m;

            labelClock.setText(newTime);

            String time0String = String.valueOf(time0);
            labelCounter.setText(time0String);

            labelInfo.setText(strings.get(time0));

            time0++;
            timeMinute++;

            double tempHeight = fontSize + labelInfo.getHeight() + 10;
//            this.setPrefSize(paneInformationWidth, tempHeight);
            paneInformation.setPrefHeight(tempHeight);
//            System.out.println(time0);
//            System.out.println(strings.get((int)time0));

            if(time0 == timeMaxBarOperating){
                timeline.stop();
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

//class ShapePath {
//    private double initialPositionX;
//    private double initialPositionY;
//
//    private double seatPositionX;
//    private double seatPositionY;
//
//    private double finalPositionX;
//    private double finalPositionY;
//
//    private Path shapePath = new Path();
//
//
//    public ShapePath(double initialPositionX, double initialPositionY, double seatPositionX, double seatPositionY, double finalPositionX, double finalPositionY) {
////        this.pane = pane;
//
//        shapePath = new Path();
//
//        this.initialPositionX = initialPositionX;
//        this.initialPositionY = initialPositionY;
//
//        this.seatPositionX = seatPositionX;
//        this.seatPositionY = seatPositionY;
//
//        this.finalPositionX = finalPositionX;
//        this.finalPositionY = finalPositionY;
////
//////        path.getElements().add(new MoveTo(50,50));
////        path.getElements().add(new MoveTo(paneRootWidth, 100)); // staring position
////        path.getElements().add(new HLineTo(50));
////        path.getElements().add(new VLineTo(200));
////        path.getElements().add(new HLineTo(500));
////        path.getElements().add(new VLineTo(100));
//
//
//        createPaths();
//    }
//
//    private void createPaths() {
//
////        pathToStart();
////        pathToEntrance();
////        pathToLine();
////        pathToSeat();
////        pathToLine();
////        pathToEntrance();
////        pathToEnd();
//
//    }
//
//    private void pathToStart() {
//        shapePath.getElements().add(new MoveTo(initialPositionX, initialPositionY));
//    }
//
//    private void pathToEntrance() {
////        shapePath.getElements().add(new LineTo(psane.getMaxWidth()/2, initialPositionY));
//
//    }
//
//    private void pathToSeat() {
//    }
//
//    private void pathToEnd() {
//    }
//
//    private void pathToLine() {
//    }
//
//
//}

class ShapePerson {

    private Pane pane;

    private Person person;

    private StackPane stackPane;

    // Circle Person
    Circle circlePerson;
    private Color color;

    // Holds the Transitions
    SequentialTransition sequentialTransitionPerson;

    // Transition Person
//    PathTransition pathTransitionPerson;

    // Path Person
//    Path pathPerson;

    private double shapeDimensionScalar;

    private double initialPositionX;
    private double initialPositionY;

    // Where to go
    private ArrayList<ShapeTable.ShapeSeat> shapeSeats;
    private ShapeTable.ShapeSeat shapeSeat;

    // Meta stuff
    private double paneWidth;
    private double paneCenter;
    private double barOffsetY;

    // Animation moving time for
    private double animationFailureTimeMilliSec = 6000; // 6 seconds total so (3 to entrance), (3 to seat and back), (3 leave)

    // Animation time Scalar
    private double animationTimeScalar;

    public ShapePerson(Pane pane, Person person, double animationTimeScalar, double shapeDimensionScalar, double initialPositionX, double initialPositionY, ArrayList<ShapeTable.ShapeSeat> shapeSeats, double paneWidth, double barOffsetY, Color color) {
        this.animationTimeScalar = animationTimeScalar;

        this.pane = pane;

        this.person = person;

        this.shapeDimensionScalar = shapeDimensionScalar;

        this.initialPositionX = initialPositionX;
        this.initialPositionY = initialPositionY;

        this.shapeSeats = shapeSeats;

        this.paneWidth = paneWidth;
        this.color = color;
        this.paneCenter = this.paneWidth / 2;

        this.barOffsetY = barOffsetY;

        createCirclePerson();

        if (findSeat()) {
//            createSequentialTransitionSuccess(person.getWaitTime() * 1000);
            createSequentialTransitionSuccess();
        } else {
//            createSequentialTransitionFailure(animationFailureTimeMilliSec);
            createSequentialTransitionFailure();
        }
    }

    private void createSequentialTransitionSuccess(double duration) {
        double timePerPath = duration / 2;
//        sequentialTransitionPerson = new SequentialTransition(stackPane,
//                pathPause(((person.getArrivalTime() * 1000)) * animationTimeScalar),
//                pathTransitionEntrance(timePerPath * animationTimeScalar),
//                pathTransitionSitDown(timePerPath * animationTimeScalar),
//                pathPause((((person.getMostTimeRelativeSpentDrinkingSetByAPerson() *.5) * 1000)) * animationTimeScalar),
//                pathTransitionBackToEntrance(timePerPath * animationTimeScalar),
//                pathTransitionLeave(timePerPath * animationTimeScalar));

        sequentialTransitionPerson = new SequentialTransition(stackPane);
        sequentialTransitionPerson.getChildren().add(pathPause(((person.getArrivalTime() * 1000)) * animationTimeScalar));
        sequentialTransitionPerson.getChildren().add(pathTransitionEntrance(timePerPath * animationTimeScalar));
        sequentialTransitionPerson.getChildren().add(pathTransitionSitDown(timePerPath * animationTimeScalar));
        sequentialTransitionPerson.getChildren().add(pathPause((((person.getMostTimeRelativeSpentDrinkingSetByAPerson()) * 1000)) * animationTimeScalar));
        sequentialTransitionPerson.getChildren().add(pathTransitionBackToEntrance(timePerPath * animationTimeScalar));
        sequentialTransitionPerson.getChildren().add(pathTransitionLeave(timePerPath * animationTimeScalar));
        sequentialTransitionPerson.play();

    }

    private void createSequentialTransitionSuccess() {
//        double timePerPath = (person.getWaitTime() * 1000) / 2/2;

        // **** divide by 2 because the time line is like this person.getArrivalTime() + .5 + .5
        double timePerPath = 1000 / 2;
//        sequentialTransitionPerson = new SequentialTransition(stackPane,
//                pathPause(((person.getArrivalTime() * 1000)) * animationTimeScalar),
//                pathTransitionEntrance(timePerPath * animationTimeScalar),
//                pathTransitionSitDown(timePerPath * animationTimeScalar),
//                pathPause((((person.getMostTimeRelativeSpentDrinkingSetByAPerson() *.5) * 1000)) * animationTimeScalar),
//                pathTransitionBackToEntrance(timePerPath * animationTimeScalar),
//                pathTransitionLeave(timePerPath * animationTimeScalar));

        // *** VERY VERY COMPLEX TIMING ***
        sequentialTransitionPerson = new SequentialTransition(stackPane);

        sequentialTransitionPerson.getChildren().add(pathPause(((person.getArrivalTime() * 1000)) * animationTimeScalar));
        sequentialTransitionPerson.getChildren().add(pathTransitionEntrance(timePerPath * animationTimeScalar));
        sequentialTransitionPerson.getChildren().add(pathTransitionSitDown(timePerPath * animationTimeScalar));
        sequentialTransitionPerson.getChildren().add(pathPause((((person.getMostTimeRelativeSpentDrinkingSetByAPerson() - person.getArrivalTime()) * 1000)) * animationTimeScalar));
//        sequentialTransitionPerson.getChildren().add(pathPause((((4 * 1000)) * animationTimeScalar)));
        sequentialTransitionPerson.getChildren().add(pathTransitionBackToEntrance(timePerPath * animationTimeScalar));
        sequentialTransitionPerson.getChildren().add(pathTransitionLeave(timePerPath * animationTimeScalar));
    }

    private void createSequentialTransitionFailure(double duration) {
        double timePerPath = duration / 2;
        sequentialTransitionPerson = new SequentialTransition(stackPane);
        sequentialTransitionPerson.getChildren().add(pathPause(((person.getArrivalTime() * 1000)) * animationTimeScalar));
        sequentialTransitionPerson.getChildren().add(pathTransitionFailure(timePerPath * animationTimeScalar));
        sequentialTransitionPerson.play();
    }

    private void createSequentialTransitionFailure() {
//        double timePerPath = person.getWaitTime() * 1000 / 2 /2;
        double timePerPath = 1000 / 2;
//        double timePerPath = 1;
        sequentialTransitionPerson = new SequentialTransition(stackPane);
        sequentialTransitionPerson.getChildren().add(pathPause(((person.getArrivalTime() * 1000)) * animationTimeScalar));
        sequentialTransitionPerson.getChildren().add(pathTransitionFailure(timePerPath * animationTimeScalar));
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

    private void createSequentialTransitionSuccessV2() {
        double wait = (person.getWaitTime() * 1000);
        sequentialTransitionPerson = new SequentialTransition(stackPane);
        sequentialTransitionPerson.getChildren().add(pathPause(((person.getArrivalTime() * 1000)) * animationTimeScalar));
        sequentialTransitionPerson.getChildren().add(pathTransitionPart1(wait * animationTimeScalar));
        sequentialTransitionPerson.getChildren().add(pathPause((((person.getMostTimeRelativeSpentDrinkingSetByAPerson()) * 1000)) * animationTimeScalar));
        sequentialTransitionPerson.getChildren().add(pathTransitionPart2(wait * animationTimeScalar));
    }


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

    private boolean findSeat() {
        for (ShapeTable.ShapeSeat shapeSeat : shapeSeats) {
            if (shapeSeat.getSeat() == person.getSeat()) {
                this.shapeSeat = shapeSeat;
                return true;
            }
        }
        return false;
    }

    private void createCirclePerson() {
        Text personNumberText = new Text(String.valueOf(person.getPersonNumber()));
        personNumberText.setFont(Font.font(shapeDimensionScalar * .5));

        stackPane = new StackPane();

        circlePerson = new Circle(shapeDimensionScalar / 2);
        circlePerson.setFill(color);

        stackPane.getChildren().addAll(circlePerson, personNumberText);
//        pane.getChildren().add(circlePerson);
        pane.getChildren().add(stackPane);

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
            shapeSeats.add(new ShapeSeat(table.getSeats().get(i), shapeSeatRadius, initialPositionXOffset, initialPositionYOffset));
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

    public double getInitialPositionX() {
        return initialPositionX;
    }

    public double getInitialPositionY() {
        return initialPositionY;
    }

    public double getShapeDimensionScalar() {
        return shapeDimensionScalar;
    }

    class ShapeSeat {
        // Seat
        private Table.Seat seat;
        private double initialPositionX;
        private double initialPositionY;

        // ShapeSeat
        private Circle shapeSeat;
        private double shapeSeatRadius;

        public ShapeSeat(Table.Seat seat, double shapeSeatRadius, double initialPositionX, double initialPositionY) {
            // *** IMPORTANT NOTE, CIRCLE STARTS FROM THE BOTTOM LEFT
            this.seat = seat;
            this.initialPositionX = initialPositionX;
            this.initialPositionY = initialPositionY;

//            this.shapeSeatRadius = (int) ((shapeDimensionScalar / 2) * SHAPE_DIMENSION_SCALAR_OFFSET);
            this.shapeSeatRadius = shapeSeatRadius;

            createSeat();
        }

        private void createSeat() {
            shapeSeat = new Circle(shapeSeatRadius);
            shapeSeat.setFill(Color.BLACK);

//            shapeSeat.setCenterX(initialPositionX + shapeSeatRadius + (shapeSeatRadius/2));
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
