/*
Discrete Event-Driven Simulation Software (Software Gurus Bar) (Cleaner and Better Code)
Project 2
Joseph Edradan
3/25/19

Purpose and usage of this application:
	Use Min Priority Queue to order events

This is the main file

Note:
	Start 3/17/19
	End 3/25/19

    Every int is a minute

*** VERY IMPORTANT NOTE ***:
* Does not handle multiple groups at a table
* Bar has a probability distribution for Number of Groups going to the bar per hour
* Bar has a probability distribution for Amount of Groups going to bar per hour
* Bar has a probability distribution for Group Size
* Bar has a probability distribution for Waiting to Order
* Bar has a probability distribution for Waiting to Leave
* Person has a probability distribution for Ordering per Order Method Call
* Person has a probability distribution for the Amount of Orders per Order Method Call
* Person has a probability distribution for Amount of time to wait until making an Order if they haven't Ordered when a Order Method was Called
* Person DOES NOT have a probability distribution for the TYPE of Drink preference
* Menu HAS SOMETHING LIKE A PROBABILITY DISTRIBUTION, but it's not based on INDEX (It's the alternative for Person's probability distribution for Drinking type preference)

 */

import java.util.ArrayList;
import java.util.Collections;

public class SoftwareGurusBar {

    // The Event running thingy
    private SimulationFramework simulationThatRunsEverything = new SimulationFramework();

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private ArrayList<Person> peopleWhoWentToLocation = new ArrayList<>();

    // Total Amount of Groups that went to the bar regardless of actually buying
    private ArrayList<Group> groupsThatWentToLocation = new ArrayList<>();

    // The table ArrayList ordered by Size
    private ArrayList<Table> tablesAtTheLocation = new ArrayList<>();

    // The Orders made in the bar
    private ArrayList<Order> ordersMadeAtLocation = new ArrayList<>();

    // The Menu Object (Contains ArrayList of objects)
    private Menu barMenuObject = new Menu();

    // Profit Must use int because Double type is bad for this type of operation!!!!!!!!!!!!!!
    private int profit = 0;

    // Person Counter
    private int numberOfPeopleThatWentToBar = 0;

    // Set when the bar is open
    private int barOpeningHour = 0;

    // Simulate hours of bar operation for example (6)am to 2am (but the operational range is arbitrary)
    private int barOperatingHours;

    private int barOperatingMinutes;

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Probabilities
    private int[] arrayProbabilityDistributionsOfGroupsGoingToBarPerHourOutOf100;

    // ArrayList made from arrayProbabilityDistributionsOfGroupsGoingToBarPerHourOutOf100
    private ArrayList<ProbabilityDistributionIndex> arrayListProbabilityDistributionsOfGroupsGoingToBarPerHourOutOf100Index = new ArrayList<>();

    private ProbabilityDistributionIndex probabilityDistributionIndexGroupSize; // .getRandomNumber(),  index + 1

    private ProbabilityDistributionIndex probabilityDistributionIndexTimeWaitOrder; // .getRandomNumber(),  index + 1

    private ProbabilityDistributionIndex probabilityDistributionIndexOfAmountOfGroupsGoingToBarAtMinute; // .getRandomNumber(),  index + 1

    private ProbabilityDistributionIndex probabilityDistributionIndexTimeWaitLeave; // .getRandomNumber(),  index + 1

    public SoftwareGurusBar(int barOpeningHour, int barOperatingHours, int[] arrayProbabilityDistributionsOfGroupsGoingToBarPerHourOutOf100, ProbabilityDistributionIndex probabilityDistributionIndexGroupSize, ProbabilityDistributionIndex probabilityDistributionIndexTimeWaitOrder, ProbabilityDistributionIndex probabilityDistributionIndexTimeWaitLeave, ProbabilityDistributionIndex probabilityDistributionIndexOfAmountOfGroupsGoingToBarAtMinute) {
        // Set when the bar is open
        this.barOpeningHour = barOpeningHour;
        this.barOperatingHours = barOperatingHours;
        this.barOperatingMinutes = this.barOperatingHours * 60;

        // Probability distribution of Time to wait until served at bar
        this.probabilityDistributionIndexTimeWaitOrder = probabilityDistributionIndexTimeWaitOrder;

        // Probability distribution of Time to wait until leaving the bar
        this.probabilityDistributionIndexTimeWaitLeave = probabilityDistributionIndexTimeWaitLeave;

        // Probability distribution of amount of groupsThatWentToLocation going to bar
        this.probabilityDistributionIndexOfAmountOfGroupsGoingToBarAtMinute = probabilityDistributionIndexOfAmountOfGroupsGoingToBarAtMinute;

        // From bar opening hour (6am), 24 indexes starting from 6am (The bar staring operational hour is arbitrary)
        // Example 4% chance of someone going to the bar at 0 index
        this.arrayProbabilityDistributionsOfGroupsGoingToBarPerHourOutOf100 = arrayProbabilityDistributionsOfGroupsGoingToBarPerHourOutOf100;
//        int[] arrayProbabilityDistributionsOfGroupsGoingToBarPerHourOutOf100 = new int[]{100, 0, 0, 100, 0, 0, 100, 0, 0, 0, 0, 0, 0, 0, 0, 50, 0, 0, 0, 0, 0, 0, 0, 0};

        // Probability distribution (Index + 1 represents size of group, the + 1 is in the if (goingToTheBar != 0){})
        // + 1 is there so that you can't have group size of 0 entering the bar
        this.probabilityDistributionIndexGroupSize = probabilityDistributionIndexGroupSize;

        // Make bar menu
        generateMenu();

        // Make Tables
        generateTables();

        // Adds probability of going to the bar at certain hour
        generateProbabilityOfArrivals(this.arrayProbabilityDistributionsOfGroupsGoingToBarPerHourOutOf100);

        // Run everything
        runBar();
    }

    public static void main(String[] args) {
        int[] probabilityDistributionsOfGroupsGoingToBarPerHourOutOf100 = new int[]{2, 4, 5, 10, 15, 30, 60, 60, 40, 30, 30, 40, 50, 50, 70, 65, 50, 40, 30, 0, 0, 0, 0, 0};
        ProbabilityDistributionIndex probabilityDistributionIndexOfAmountOfGroupsGoingToBarAtMinute = new ProbabilityDistributionIndex(new int[]{100, 30, 10, 5, 1});
        ProbabilityDistributionIndex probabilityDistributionIndexGroupSize = new ProbabilityDistributionIndex(new int[]{5, 10, 20, 60, 60, 55, 15, 8, 2, 1});
        ProbabilityDistributionIndex probabilityDistributionIndexTimeWaitOrder = new ProbabilityDistributionIndex(new int[]{5, 10, 20, 20, 30, 50, 70});
        ProbabilityDistributionIndex probabilityDistributionIndexTimeWaitLeave = new ProbabilityDistributionIndex(new int[]{5, 5, 10, 10, 20, 30, 30, 40, 50, 80});

        int barOperatingHours = 24;
        int barOpeningHour = 6;

        // barOperatingHours can also be treated as Simulation hour run time
        SoftwareGurusBar world = new SoftwareGurusBar(barOpeningHour, barOperatingHours, probabilityDistributionsOfGroupsGoingToBarPerHourOutOf100, probabilityDistributionIndexGroupSize, probabilityDistributionIndexTimeWaitOrder, probabilityDistributionIndexTimeWaitLeave, probabilityDistributionIndexOfAmountOfGroupsGoingToBarAtMinute);

        world.runSimulationThatRunsEverything();
    }

    private void runBar() {
        int personNumberOffset = 0;     // Person Offset
        int groupCounter = 1;           // Group Counter
        int timeOfTheDay = 0;           // Time of the day

        while (timeOfTheDay < barOperatingMinutes) {

            // Current Hour based on index and relative to arrayProbabilityDistributionsOfGroupsGoingToBarPerHourOutOf100.length which in theory should be 24
            int currentHour = ((timeOfTheDay / 60) % arrayProbabilityDistributionsOfGroupsGoingToBarPerHourOutOf100.length);

            // Temp probability hour
            ProbabilityDistributionIndex probabilityDistributionIndexTemp = arrayListProbabilityDistributionsOfGroupsGoingToBarPerHourOutOf100Index.get(currentHour);

            // Going to the bar or not (1 == yes, 0 = no)
            int goingToTheBar;

            // A Group should go at this time?
            if (probabilityDistributionIndexTemp.getProbabilityArraySize() == 1) {
                if (probabilityDistributionIndexTemp.getProbabilityDistributionIndexArray()[0] == 2) {
                    goingToTheBar = 1;
                } else {
                    goingToTheBar = 0;
                }
            } else {
                goingToTheBar = arrayListProbabilityDistributionsOfGroupsGoingToBarPerHourOutOf100Index.get(currentHour).getRandomNumber();
            }

//            System.out.println("Hour: " + currentHour);
//            System.out.println("Going to the bar " + goingToTheBar);
            if (goingToTheBar != 0) {

                // from 1 to 5 groupsThatWentToLocation going
                int amountOfGroupsGoingToBar = this.probabilityDistributionIndexOfAmountOfGroupsGoingToBarAtMinute.getRandomNumber() + 1;

                for (int j = 0; j < amountOfGroupsGoingToBar; j++) {
                    // group size from 1-10 with probability distribution (+ 1 because there are nulls for 0 prob)
                    int groupSize = this.probabilityDistributionIndexGroupSize.getRandomNumber() + 1;

                    // The Group created
                    Group group = new Group(groupCounter, groupSize, timeOfTheDay, personNumberOffset);

                    // Give the group a menu
                    group.giveGroupMenu(barMenuObject);

                    // Add to numberOfPeopleThatWentToBar who went in bar
                    numberOfPeopleThatWentToBar += group.getGroupNumber();

                    // Add to peopleThatWentToBar
                    for (Person person : group.getGroupMembers()) {
                        peopleWhoWentToLocation.add(person);
                    }

                    // Add to groupsThatWentToLocation
                    groupsThatWentToLocation.add(group);

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

    }

    public void runSimulationThatRunsEverything() {
        simulationThatRunsEverything.run();

//        System.out.println(this.getSimulationThatRunsEverything());
        System.out.println();
        System.out.println(String.format("Total profits $%s", profit));

    }

    private void probabilityOfGoingToBarRelativeToBarOpening(int probabilityOutOf100) {
        int notGoingToBar = 100 - probabilityOutOf100;
        if (notGoingToBar == 100) {
            // 0% numberOfPeopleThatWentToBar going to bar every minute
            arrayListProbabilityDistributionsOfGroupsGoingToBarPerHourOutOf100Index.add(new ProbabilityDistributionIndex(new int[]{1}));
//            System.out.println("0% of numberOfPeopleThatWentToBar going");
        } else if (notGoingToBar == 0) {
            // 100% numberOfPeopleThatWentToBar going to bar every minute
            arrayListProbabilityDistributionsOfGroupsGoingToBarPerHourOutOf100Index.add(new ProbabilityDistributionIndex(new int[]{2}));
//            System.out.println("100% of numberOfPeopleThatWentToBar going");

        } else {
            // Probability
            arrayListProbabilityDistributionsOfGroupsGoingToBarPerHourOutOf100Index.add(new ProbabilityDistributionIndex(new int[]{notGoingToBar, probabilityOutOf100}));
//            System.out.println("Probability of numberOfPeopleThatWentToBar going");
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
        barMenuObject.addDrink(new Drink("Give Me Money By Joseph", 100, 10, 1));

        // Custom Debug
//        barMenuObject.addDrink(new Drink("Debug Beer By Joseph", 100, 2, 1));

    }

    private void generateTablesHelper(int amount, int tableSize) {

        for (int i = 0; i < amount; i++) {
//            System.out.println("Amount " + amount);
//            System.out.println("Table Number " + i);
//            System.out.println("Table Size " + tableSize);
            Table tableTemp = new Table(i, tableSize);
            tablesAtTheLocation.add(tableTemp);
//            pq.insert(tableTemp);
        }

    }

    private void generateTables() {
//        PriorityQueueHeap pq = new PriorityQueueHeapMin(new ComparatorTable(), 10);

        // The bar makes tablesAtTheLocation here (DO NOT MAKE MULTIPLE COPIES, LOOKING UP A TABLE DOES NOT WORK BY OBJECT ID)

        // Big Test
        generateTablesHelper(18, 1);
        generateTablesHelper(14, 2);
        generateTablesHelper(11, 3);
        generateTablesHelper(9, 4);
        generateTablesHelper(8, 5);
        generateTablesHelper(7, 6);
        generateTablesHelper(6, 7);
        generateTablesHelper(6, 8);
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

        // * Sorts tablesAtTheLocation by ascending order by default using TimSort
        // * Cannot use PriorityQueueHeapMin because heap isn't sorted unless you call remove()
        // * to get a sorted min array
        // Easy solution to sort although can solve with a Min Priority Queue (But the process is too ugly!)
        Collections.sort(tablesAtTheLocation, new ComparatorTable());

        // Min Priority Queue solution
        // 1. temp Min PQ
        // 2. Call (Min PQ).remove() and Add it in table arrayList for easy access in a loop.

        // Testing with Min Priority Queue
//        System.out.println("ArrayList");
//        System.out.println(tablesAtTheLocation);
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

    public ArrayList<Order> getOrdersMadeAtLocation() {
        return ordersMadeAtLocation;
    }

    public int getBarOperatingHours() {
        return barOperatingHours;
    }

    public int getBarOperatingMinutes() {
        return barOperatingMinutes;
    }

    public int getNumberOfPeopleThatWentToBar() {
        return numberOfPeopleThatWentToBar;
    }

    public ArrayList<Person> getPeopleWhoWentToLocation() {
        return peopleWhoWentToLocation;
    }

    public int getBarOpeningHour() {
        return barOpeningHour;
    }

    public SimulationFramework getSimulationThatRunsEverything() {
        return simulationThatRunsEverything;
    }

    public ArrayList<Table> getTablesAtTheLocation() {
        return tablesAtTheLocation;
    }

    // Seats (Group) numberOfPeopleThatWentToBar if they can sit in bar (Sequential part 1)
    public Table findTableForGroup(Group group) {
//        System.out.println(String.format("FIND TABLE IS RUNNING"));
        for (Table table : tablesAtTheLocation) {
            if (!table.isTableOccupied() && (group.getGroupSize() <= table.getTableSize())) {
                return table;
            }
        }
        return null;

    }

    // Group ordersMadeAtLocation (Sequential part 2)
    private void orderAtLocation(Order order) {
//        String tempString = String.format("Bar served %s's order %s at Absolute Time %s",
//                order.getPersonWhoOrdered(),
//                order.getFoodItemThatPersonOrdered(),
//                simulationThatRunsEverything.getCurrentTimeOfSimulationByEvent());
//        System.out.println(tempString);

        // Add order ot orders ArrayList
        ordersMadeAtLocation.add(order);

        // Print the order made
        System.out.println(String.format("%s", order));

        // Add cost of order to profits
        profit += order.getFoodItemThatPersonOrdered().getFoodCost();
    }

    // Group (Sequential part 3)
    private void leaveBar(Group group) {

        for (Table table : tablesAtTheLocation) {
            if (table.getGroup() == group) {
                table.removeGroup();
                break;
            }
        }

    }

    public ArrayList<Group> getGroupsThatWentToLocation() {
        return groupsThatWentToLocation;
    }

    public String getTablesString() {
        String tempString = "";
        for (Table table : tablesAtTheLocation) {
            tempString = tempString + table.toString() + "\n";
        }
        return tempString.substring(0, tempString.length() - 1);
    }

    public int[] getArrayProbabilityDistributionsOfGroupsGoingToBarPerHourOutOf100() {
        return arrayProbabilityDistributionsOfGroupsGoingToBarPerHourOutOf100;
    }

    public ArrayList<ProbabilityDistributionIndex> getArrayListProbabilityDistributionsOfGroupsGoingToBarPerHourOutOf100Index() {
        return arrayListProbabilityDistributionsOfGroupsGoingToBarPerHourOutOf100Index;
    }

    public ProbabilityDistributionIndex getProbabilityDistributionIndexGroupSize() {
        return probabilityDistributionIndexGroupSize;
    }

    public ProbabilityDistributionIndex getProbabilityDistributionIndexTimeWaitOrder() {
        return probabilityDistributionIndexTimeWaitOrder;
    }

    public ProbabilityDistributionIndex getProbabilityDistributionIndexOfAmountOfGroupsGoingToBarAtMinute() {
        return probabilityDistributionIndexOfAmountOfGroupsGoingToBarAtMinute;
    }

    public ProbabilityDistributionIndex getProbabilityDistributionIndexTimeWaitLeave() {
        return probabilityDistributionIndexTimeWaitLeave;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    class ArriveEvent extends EventSimulation {

        ArriveEvent(Group group) {
            super(group.getTimeAbsoluteArrive(), group);
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

                // Set group the table
                group.setTableForJavaFX(table);

                // Get objects Wait time (+ 1 because it isn't real to wait 0 minutes)
                int timeWaitRelative = probabilityDistributionIndexTimeWaitOrder.getRandomNumber() + 1;

                // Set Group Time Relative Wait Order
                group.setTimeRelativeWaitOrder(timeWaitRelative);

                // Show that the group is waiting
                String string2 = stringFormat(simulationThatRunsEverything.getCurrentTimeOfSimulationByEvent(), group, "Waits to Order");
                strings.add(string2);
                System.out.println(string2);

                // Create Wait Order Event
                WaitOrderEvent waitOrderEvent = new WaitOrderEvent(group, table);

                // Add Wait Order Event to group event ArrayList
                group.getGroupEvents().add(waitOrderEvent);

                // Add Wait Order Event to Priority Queue
                simulationThatRunsEverything.scheduleEvent(waitOrderEvent);


            } else {
                String string2 = stringFormat(simulationThatRunsEverything.getCurrentTimeOfSimulationByEvent(), group, "Leaves Bar because of no available Tables");
                strings.add(string2);
                System.out.println(string2);
            }
        }

        @Override
        public String toString() {
            return String.format("ArriveEvent at Absolute time %s", getTime());
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    class WaitOrderEvent extends EventSimulation {
        private Table table;

        WaitOrderEvent(Group group, Table table) {
            super(group.getTimeAbsoluteArrive() + group.getTimeRelativeWaitOrder(), group);
            this.table = table;
        }

        public void processEvent() {
            String string = stringFormat(simulationThatRunsEverything.getCurrentTimeOfSimulationByEvent(), group, "Finishes Waiting to Order");
            strings.add(string);
            System.out.println(string);

            // Group ordersMadeAtLocation Single Instance
            group.orderFoodItemsAllAtOnce();

            // Create individual OrderEvents
            for (Order order : group.getTotalGroupOrders()) {

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

            // Create Wait Leave Event
            WaitLeaveEvent waitLeaveEvent = new WaitLeaveEvent(group);

            // Add Leave Event to group event ArrayList
            group.getGroupEvents().add(waitLeaveEvent);

            // Add Leave Event to Priority Queue
            simulationThatRunsEverything.scheduleEvent(waitLeaveEvent);
        }

        @Override
        public String toString() {
            return String.format("OrderEvent at Absolute time %s", getTime());
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    class OrderEvent extends EventSimulation {
        private Order order;

        OrderEvent(Group group, Order order) {
            super(order.getTimeAbsoluteBoughtAt(), group);
            this.order = order;
        }

        public void processEvent() {
            String orderString = String.format("%s bought %s", order.getPersonWhoOrdered(), order.getFoodItemThatPersonOrdered());
            String string1 = stringFormat(simulationThatRunsEverything.getCurrentTimeOfSimulationByEvent(), group, orderString);
            strings.add(String.format(string1));

            // Call method to handle order made
            orderAtLocation(order);
        }

        @Override
        public String toString() {
            return String.format("OrderEvent at Absolute time %s", getTime());
        }

        public Order getOrder() {
            return order;
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    class WaitLeaveEvent extends EventSimulation {

        WaitLeaveEvent(Group group) {
            super(group.getTimeAbsoluteLeave(), group);
        }

        public void processEvent() {
            String string = stringFormat(simulationThatRunsEverything.getCurrentTimeOfSimulationByEvent(), group, "Waits to Leave");
            strings.add(string);
            System.out.println(string);

            // Get objects Wait time (+ 1 because it isn't real to wait 0 minutes)
            int timeWaitRelative = probabilityDistributionIndexTimeWaitLeave.getRandomNumber() + 1;

            // Set Group Time Relative Wait Leave
            group.setTimeRelativeWaitLeave(timeWaitRelative);

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

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    class LeaveEvent extends EventSimulation {
        LeaveEvent(Group group) {
            super(group.getTimeAbsoluteLeave(), group);
        }

        public void processEvent() {
            String string1 = stringFormat(simulationThatRunsEverything.getCurrentTimeOfSimulationByEvent(), group, "Finishes Waiting to Leave");
            strings.add(string1);
            System.out.println(string1);

            String string2 = stringFormat(simulationThatRunsEverything.getCurrentTimeOfSimulationByEvent(), group, "Leaves Bar");
            strings.add(string2);
            System.out.println(string2);

            // Group leaves table and bar
            leaveBar(group);
        }

        @Override
        public String toString() {
            return String.format("LeaveEvent at Absolute time %s", getTime());
        }
    }
}


