/*
Discrete Event-Driven Simulation Software (Software Gurus Bar)
Project 2
Joseph Edradan
3/17/19

Purpose and usage of this application:
	Use Min Priority Queue to order events

This is the main file

Note:
	Start 3/17/19
	End 3/19/19
    I don't like how all the Events are not pre-generated except for the Arrival so you can't enforce
    Sequential programming

    Every int is a minute

*** VERY IMPORTANT NOTE ***:
* There is no 4th Event, instead, Order event is called again
* Does not handle multiple groups at a table
* Bar has a probability distribution for peopleThatWentToBar going to the bar at certain hours of the operational day (created in SoftwareGurusBar())
* Bar has a probability distribution for amount of groups that can go in at the same time in the bar
* Group has a probability distribution for size of group (created in SoftwareGurusBar())
* Person has a probability distribution for the Amount of Drinks preference
* Person DOES NOT have a probability distribution for the TYPE of Drink preference
* Menu HAS SOMETHING LIKE A PROBABILITY DISTRIBUTION, but it's not based on INDEX (It's the alternative for Person's probability distribution for Drinking type preference)
* ProbabilityDistribution cannot handle an index with a value of zero

** Current and Future Order Events are pre-generated when ever a Person object is created
** The alternative solution is to call an order method after the time spent drinking 1 drink is done
** That call method would be called during SoftwareGurusBar()'a main while loop.
** This is costly since it would have to loop through each ArrayList Group and its ArrayList Person and call that method

    Project original sequence:
    1. Generate all Arrival Events First and add it to the Priority Queue
    2. Call Arrive Event's process which calls Order Event
    3. Order Event's process handles the profits and calls Leave Event
    4. Leave Event's process does etc...

    This Project's sequence, it does it differently:
    1. Generate groups and Arrive Events and add it to the Priority Queue
    2. Call Arrive Event's process which handles tables and call Wait Event and at the end calls Leave Event
    3a. Wait Event's process calls individual Order Events
    3b. Order Event's process calls orderDrinks which add to profits
    3c. Leave Event's process calls leaveBar which removes group from table (which removes them from the bar)
 */

import java.util.ArrayList;
import java.util.Collections;

public class SoftwareGurusBar {

    // Total Amount of Groups that went to the bar regardless of actually buying
    private ArrayList<Group> groups = new ArrayList<>();

    // The Menu Object (Contains ArrayList of objects)
    private Menu barMenuObject = new Menu();

    // The table Priority Queue
    private ArrayList<Table> tables = new ArrayList<>();

    // The Event running thingy
    private SimulationFramework simulationThatRunsEverything = new SimulationFramework();

    // Profit Must use int because Double type is bad for this type of operation!!!!!!!!!!!!!!
    private int profit = 0;

    // Person Counter
    private int peopleThatWentToBar;

    // Simulate hours of bar operation for example (6)am to 2am (but the operational range is arbitrary)
    private int barOperatingHours;

    private int barOperatingMinutes;

    // Probabilities
    private int[] probabilitiesOfPeopleGoingToBarPerHour;

    // ArrayList made from probabilitiesOfPeopleGoingToBarPerHour
    private ArrayList<ProbabilityDistribution> arrayListProbabilityDistributionsOfGroupsGoingToBarPerHourOutOf100 = new ArrayList<>();

    private ProbabilityDistribution probabilityDistributionGroupSize; // .getRandomNumber(),  index + 1

    private ProbabilityDistribution probabilityDistributionWaitTime; // .getRandomNumber(),  index + 1

    private ProbabilityDistribution probabilityDistributionOfAmountOfGroupsGoingToBarAtMinute; // .getRandomNumber(),  index + 1

    public static void main(String[] args) {
        int[] probabilityDistributionsOfGroupsGoingToBarPerHourOutOf100 = new int[]{4, 8, 10, 15, 20, 30, 60, 60, 40, 30, 30, 40, 50, 50, 70, 65, 50, 40, 30, 0, 0, 0, 0, 0};
        ProbabilityDistribution probabilityDistributionGroupSize = new ProbabilityDistribution(new int[]{10, 20, 20, 60, 60, 55, 15, 8, 2, 1});
        ProbabilityDistribution probabilityDistributionWaitTime = new ProbabilityDistribution(new int[]{10, 20, 20, 20, 30, 50, 70});
        ProbabilityDistribution probabilityDistributionOfAmountOfGroupsGoingToBarAtMinute = new ProbabilityDistribution(new int[]{100, 30, 10, 5, 1});
        int barOperatingHours = 24;

        // barOperatingHours can also be treated as Simulation hour run time
        SoftwareGurusBar world = new SoftwareGurusBar(barOperatingHours, probabilityDistributionsOfGroupsGoingToBarPerHourOutOf100, probabilityDistributionGroupSize, probabilityDistributionWaitTime, probabilityDistributionOfAmountOfGroupsGoingToBarAtMinute);

    }


    public SoftwareGurusBar(int barOperatingHours, int[] probabilityDistributionsOfGroupsGoingToBarPerHour, ProbabilityDistribution probabilityDistributionGroupSize, ProbabilityDistribution probabilityDistributionWaitTime, ProbabilityDistribution probabilityDistributionOfAmountOfGroupsGoingToBarAtMinute) {
        // Set when the bar is open
        this.barOperatingHours = barOperatingHours;
        this.barOperatingMinutes = this.barOperatingHours * 60;
        this.peopleThatWentToBar = 0;

        // Probability distribution of Time to wait until served at bar
        this.probabilityDistributionWaitTime = probabilityDistributionWaitTime;

        // Probability distribution of amount of groups going to bar
        this.probabilityDistributionOfAmountOfGroupsGoingToBarAtMinute = probabilityDistributionOfAmountOfGroupsGoingToBarAtMinute;

        // From bar opening hour (6am), 24 indexes starting from 6am (The bar staring operational hour is arbitrary)
        // Example 4% chance of someone going to the bar at 0 index
        this.probabilitiesOfPeopleGoingToBarPerHour = probabilityDistributionsOfGroupsGoingToBarPerHour;
//        int[] probabilityDistributionsOfGroupsGoingToBarPerHour = new int[]{100, 0, 0, 100, 0, 0, 100, 0, 0, 0, 0, 0, 0, 0, 0, 50, 0, 0, 0, 0, 0, 0, 0, 0};

        // Probability distribution (Index + 1 represents size of group, the + 1 is in the if (goingToTheBar != 0){})
        // + 1 is there so that you can't have group size of 0 entering the bar
        this.probabilityDistributionGroupSize = probabilityDistributionGroupSize;

        // Make bar menu
        generateMenu();

        // Make Tables
        generateTables();

        // Adds probability of going to the bar at certain hour
        generateProbabilityOfArrivals(this.probabilitiesOfPeopleGoingToBarPerHour);

        int personNumberOffset = 0;     // Person Offset
        int groupCounter = 1;           // Group Counter
        int timeOfTheDay = 0;           // Time of the day


        while (timeOfTheDay < barOperatingMinutes) {

            // Current Hour based on index and relative to probabilityDistributionsOfGroupsGoingToBarPerHour.length which in theory should be 24
            int currentHour = ((timeOfTheDay / 60) % probabilityDistributionsOfGroupsGoingToBarPerHour.length);

            // Temp probability hour
            ProbabilityDistribution probabilityDistributionTemp = arrayListProbabilityDistributionsOfGroupsGoingToBarPerHourOutOf100.get(currentHour);

            // Going to the bar or not (1 == yes, 0 = no)
            int goingToTheBar;

            // A Group should go at this time?
            if (probabilityDistributionTemp.getProbabilityArraySize() == 1) {
                if (probabilityDistributionTemp.getProbabilityArray()[0] == 2) {
                    goingToTheBar = 1;
                } else {
                    goingToTheBar = 0;
                }
            } else {
                goingToTheBar = arrayListProbabilityDistributionsOfGroupsGoingToBarPerHourOutOf100.get(currentHour).getRandomNumber();
            }

//            System.out.println("Hour: " + currentHour);
//            System.out.println("Going to the bar " + goingToTheBar);
            if (goingToTheBar != 0) {

                // from 1 to 5 groups going
                int amountOfGroupsGoingToBar = this.probabilityDistributionOfAmountOfGroupsGoingToBarAtMinute.getRandomNumber() + 1;

                for (int j = 0; j < amountOfGroupsGoingToBar; j++) {
                    // group size from 1-10 with probability distribution (+ 1 because there are nulls for 0 prob)
                    int groupSize = this.probabilityDistributionGroupSize.getRandomNumber() + 1;

                    // The Group created
                    Group group = new Group(groupCounter, groupSize, timeOfTheDay, barMenuObject, personNumberOffset);

                    // Add to peopleThatWentToBar who went in bar
                    peopleThatWentToBar += group.getGroupNumber();

                    // Add to groups
                    groups.add(group);

                    // Person Counter Offset
                    personNumberOffset += group.getGroupSize();

                    // Add to counter
                    groupCounter++;

                    // Make Arrive Event
                    ArriveEvent arriveEvent = new ArriveEvent(group);

                    // Add Arrive Event to group event ArrayList
                    group.getGroupEvents().add(arriveEvent);

                    // Add Arrive Event to Priority Queue
                    simulationThatRunsEverything.scheduleEvent(arriveEvent);
                }

            }
            // Increment Minute
            timeOfTheDay++;
        }

        simulationThatRunsEverything.run();

//        System.out.println(this.getSimulationThatRunsEverything());
        System.out.println();
        System.out.println(String.format("Total profits $%s", profit));
    }

    private void probabilityOfGoingToBarRelativeToBarOpening(int probabilityOutOf100) {
        int notGoingToBar = 100 - probabilityOutOf100;
        if (notGoingToBar == 100) {
            // 0% peopleThatWentToBar going to bar every minute
            arrayListProbabilityDistributionsOfGroupsGoingToBarPerHourOutOf100.add(new ProbabilityDistribution(new int[]{1}));
//            System.out.println("0% of peopleThatWentToBar going");
        } else if (notGoingToBar == 0) {
            // 100% peopleThatWentToBar going to bar every minute
            arrayListProbabilityDistributionsOfGroupsGoingToBarPerHourOutOf100.add(new ProbabilityDistribution(new int[]{2}));
//            System.out.println("100% of peopleThatWentToBar going");

        } else {
            // Probability
            arrayListProbabilityDistributionsOfGroupsGoingToBarPerHourOutOf100.add(new ProbabilityDistribution(new int[]{notGoingToBar, probabilityOutOf100}));
//            System.out.println("Probability of peopleThatWentToBar going");
        }
    }

    private void generateProbabilityOfArrivals(int[] probabilitiesPerHour) {
        // So when ever the bar is open is the starting
        for (int i = 0; i < probabilitiesPerHour.length; i++) {
            probabilityOfGoingToBarRelativeToBarOpening(probabilitiesPerHour[i]);
        }
    }

    private void generateMenu() {
        // Add any Drink here (probability distribution does not need to add up to 100)

        // Using menu from https://www.librarybarla.com/menu
        barMenuObject.addDrink(new Drink("House Old Fashioned", 14, 20, 200));
        barMenuObject.addDrink(new Drink("Corpse Reviver No. 2", 20, 20, 200));
        barMenuObject.addDrink(new Drink("Adventures of Blackberry Finn", 14, 5, 500));
        barMenuObject.addDrink(new Drink("Tequila Mockingbird", 14, 50, 200));
        barMenuObject.addDrink(new Drink("Ready Play Rum", 14, 40, 150));
        barMenuObject.addDrink(new Drink("Fifty Shades of Charcoal", 16, 40, 350));
        barMenuObject.addDrink(new Drink("Of Mice and Mezcal", 15, 30, 300));

        // Using menu from https://static1.squarespace.com/static/58e326c71e5b6c8ff9282911/t/5938d3ea20099e3818e94f37/1496896506786/Black+Pearl+Tourism+Menu+%28compressed%29.compressed.pdf
        barMenuObject.addDrink(new Drink("Javier Wallbuilder", 20, 40, 250));
        barMenuObject.addDrink(new Drink("Three Drop Swizzle", 22, 50, 150));
        barMenuObject.addDrink(new Drink("Brains Bitter", 20, 30, 300));

        // Custom
        barMenuObject.addDrink(new Drink("Debug Beer By Joseph", 100, 100, 1));

    }

    private void generateTablesHelper(int amount, int tableSize) {

        for (int i = 0; i < amount; i++) {
//            System.out.println("Amount " + amount);
//            System.out.println("Table Number " + i);
//            System.out.println("Table Size " + tableSize);
            Table tableTemp = new Table(i, tableSize);
            tables.add(tableTemp);
//            pq.insert(tableTemp);
        }

    }

    private void generateTables() {
//        PriorityQueueHeap pq = new PriorityQueueHeapMin(new ComparatorTable(), 10);

        // The bar makes tables here (DO NOT MAKE MULTIPLE COPIES, LOOKING UP A TABLE DOES NOT WORK BY OBEJECT ID)

        // Big Test
        generateTablesHelper(10, 1);
        generateTablesHelper(10, 2);
        generateTablesHelper(6, 3);
        generateTablesHelper(6, 4);
        generateTablesHelper(5, 5);
        generateTablesHelper(6, 6);
        generateTablesHelper(6, 7);
        generateTablesHelper(5, 8);
        generateTablesHelper(5, 9);
        generateTablesHelper(5, 10);
        generateTablesHelper(4, 11);
        generateTablesHelper(4, 12);

        // Small Test
//        generateTablesHelper(10, 1);
//        generateTablesHelper(10, 2);
//        generateTablesHelper(6, 5);
//        generateTablesHelper(4, 10);

        // Big Test V2
//        generateTablesHelper(30, 1);
//        generateTablesHelper(20, 2);
//        generateTablesHelper(13, 3);
//        generateTablesHelper(13, 4);
//        generateTablesHelper(13, 5);
//        generateTablesHelper(13, 6);
//        generateTablesHelper(8, 7);
//        generateTablesHelper(7, 8);
//        generateTablesHelper(7, 9);
//        generateTablesHelper(5, 10);
//        generateTablesHelper(5, 11);
//        generateTablesHelper(5, 12);

        // * Sorts tables by ascending order by default using TimSort
        // * Cannot use PriorityQueueHeapMin because heap isn't sorted unless you call remove()
        // * to get a sorted min array
        // Easy solution to sort although can solve with a Min Priority Queue (But the process is too ugly!)
        Collections.sort(tables, new ComparatorTable());

        // Min Priority Queue solution
        // 1. temp Min PQ
        // 2. Call (Min PQ).remove() and Add it in table arrayList for easy access in a loop.

        // Testing with Min Priority Queue
//        System.out.println("ArrayList");
//        System.out.println(tables);
//        System.out.println("ArrayPQHeap");
//        System.out.println(Arrays.toString(pq.getHeap()));
//
//        Table[] arrayPQ = new Table[pq.maxSize];
//
//        int i = 0;
//        while (!pq.isEmpty()) {
//            Table tableTemp = (Table) pq.remove();
//            arrayPQ[i] = tableTemp;
////            System.out.println(tableTemp);
//            i++;
//        }
//        System.out.println("ArrayPQ");
//        System.out.println(Arrays.toString(arrayPQ));

    }

    public SimulationFramework getSimulationThatRunsEverything() {
        return simulationThatRunsEverything;
    }

    public ArrayList<Table> getTables() {
        return tables;
    }

    // Get random between (Helper Method basically)
    private int getRandBetween(int low, int high) {
        return low + (int) ((high - low + 1) * Math.random());
    }

    // Seats (Group) peopleThatWentToBar if they can sit in bar (Sequential part 1)
    public Table findTableForGroup(Group group) {
//        System.out.println(String.format("FIND TABLE IS RUNNNIG"));
        for (Table table : tables) {
            if (!table.isTableOccupied() && (group.getGroupSize() <= table.getTableSize())) {
                return table;
            }
        }
        return null;

    }

    // Group orders (Sequential part 2)
    private void orderDrink(Order order) {
        String tempString = String.format("Bar served %s's order %s at Absolute Time %s",
                order.getPerson(),
                order.getDrink(),
                simulationThatRunsEverything.getCurrentTimeOfSimulationByEvent());

//        System.out.println(tempString);
        System.out.println(String.format("%s", order));

        // Add cost of order to profits
        profit += order.getDrink().getDrinkCost();
    }

    // Group (Sequential part 3)
    private void leaveBar(Group group) {

        for (Table table : tables) {
            if (table.getGroup() == group) {
                table.removeGroup();
                break;
            }
        }

    }

    public ArrayList<Group> getGroups() {
        return groups;
    }

    public String getTablesString() {
        String tempString = "";
        for (Table table : tables) {
            tempString = tempString + table.toString() + "\n";
        }
        return tempString.substring(0, tempString.length() - 1);
    }

    public int getBarOperatingHours() {
        return barOperatingHours;
    }

    public int getBarOperatingMinutes() {
        return barOperatingMinutes;
    }

    public int getPeopleThatWentToBar() {
        return peopleThatWentToBar;
    }

    private String stringFormat(int time, Group group, String action) {
        return String.format("Time: %-4s Group: %-4s Size: %-6s Action: %s", time, group.getGroupNumber(), group.getGroupSize(), action);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////

    private class ArriveEvent extends EventGroup {

        ArriveEvent(Group group) {
            super(group.getTimeArrive(), group);
        }

        public void processEvent() {

            String string1 = stringFormat(simulationThatRunsEverything.getCurrentTimeOfSimulationByEvent(), group, "Arrives");
            strings.add(string1);
            System.out.println(string1);

            // Get a Table
            Table table = findTableForGroup(group);

            // If available table
            if (!(table == null)) {

                // Add group to table
                table.seatGroup(group);

                String string2 = stringFormat(simulationThatRunsEverything.getCurrentTimeOfSimulationByEvent(), group, "Waits");
                strings.add(string2);
                System.out.println(string2);

                // Get random Wait time ( +1 because you can't order instantly)
                int timeWaitRelative = probabilityDistributionWaitTime.getRandomNumber() + 1;

                // Create Wait Order Event
                WaitOrderEvent waitOrderEvent = new WaitOrderEvent(group, timeWaitRelative, table);

                // Add Wait Order Event to group event ArrayList
                group.getGroupEvents().add(waitOrderEvent);

                // Add Wait Order Event to Priority Queue
                simulationThatRunsEverything.scheduleEvent(waitOrderEvent);


            } else {
                String string2 = stringFormat(simulationThatRunsEverything.getCurrentTimeOfSimulationByEvent(), group, "Leave Bar because of no available Tables");
                strings.add(string2);
                System.out.println(string2);
            }
        }

        @Override
        public String toString() {
            return String.format("ArriveEvent at Absolute time %s", getTime());
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////

    private class WaitOrderEvent extends EventGroup {
        private Table table;
        private int timeWaitRelative;

        WaitOrderEvent(Group group, int timeWaitRelative, Table table) {
            super(group.getTimeArrive() + timeWaitRelative, group);
            this.timeWaitRelative = timeWaitRelative;
            this.table = table;
        }

        public void processEvent() {
            String string = stringFormat(simulationThatRunsEverything.getCurrentTimeOfSimulationByEvent(), group, "Finishes Waiting");
            strings.add(string);
            System.out.println(string);

            // Group orders
            group.orderDrinks(timeWaitRelative);

            // Create individual OrderEvents
            for (Order order : group.getTotalOrders()) {

                // Make Order Event
                OrderEvent orderEvent = new OrderEvent(group, order);

                // Add Order Event to group event ArrayList
                group.getGroupEvents().add(orderEvent);

                // Add Order Event to Priority Queue
                simulationThatRunsEverything.scheduleEvent(orderEvent);

                // For Java FX
                group.setTableForJavaFX(table);
            }
//                System.out.println(getTablesString());

            // Create Leave Event
            LeaveEvent leaveEvent = new LeaveEvent(group);

            // Add Leave Event to group event ArrayList
            group.getGroupEvents().add(leaveEvent);

            // Add Leave Event to Priority Queue
            simulationThatRunsEverything.scheduleEvent(leaveEvent);
        }

        @Override
        public String toString() {
            return String.format("OrderEvent at Absolute time %s", getTime());
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////
    private class OrderEvent extends EventGroup {
        private Order order;

        OrderEvent(Group group, Order order) {
            super(order.getTimeBoughtAt(), group);
            this.order = order;
        }

        public void processEvent() {
            String orderString = String.format("%s bought %s", order.getPerson(), order.getDrink());
            String string = stringFormat(simulationThatRunsEverything.getCurrentTimeOfSimulationByEvent(), group, orderString);
            strings.add(String.format(string));

            // Group orders drink
            orderDrink(order);
        }

        @Override
        public String toString() {
            return String.format("OrderEvent at Absolute time %s", getTime());
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////

    private class LeaveEvent extends EventGroup {
        LeaveEvent(Group group) {
            super(group.getTimeLeave(), group);
        }

        public void processEvent() {
            String string = stringFormat(simulationThatRunsEverything.getCurrentTimeOfSimulationByEvent(), group, "Leaves Bar");
            strings.add(string);
            System.out.println(string);

            // Group leaves table and bar
            leaveBar(group);
        }

        @Override
        public String toString() {
            return String.format("LeaveEvent at Absolute time %s", getTime());
        }
    }
}