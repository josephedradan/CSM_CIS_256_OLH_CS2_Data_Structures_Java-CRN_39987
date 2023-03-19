/*
This class is literally what you thing a person would be...

* Person does not have a preference for a type of order, but has a preference for the amount of drinks to order
* Person has a probability distribution for the amount of order per orderFoodItems call
* Person has a probability distribution for the order


----------------------------------------------------------------
For Ordering at discrete given time:
    orderFoodItems(Menu menu, int timeAbsoluteCurrent);
    or
    orderFoodItems(Menu menu)
----------------------------------------------------------------
For Generating the orders in one instance:
    orderFoodItemsAllAtOnce(Menu menu);


 */

import java.util.ArrayList;

public class Person {

    // Group Information
    private Group group;

    // Person Information
    private int ID;
    private int numberOfOrdersTotal = 0;
    private int totalMoneySpent = 0;
    private int timeRelativeSpentFromOrders = 0;    // Time from orders
    private int timeRelativeSpentAtLocation = 0;    // Time spent at location includes wait and leave time

    // timeRelativeSpentAtLocation no Order Wait time (For Java Fx)
    // Includes time waiting if they didn't order but not the time
    // from setTimeRelativeWaitOrder(int timeRelativeWait) and setTimeRelativeWaitLeave(int timeRelativeWait)
//    private int timeRelativeSpentAtLocationNoWaitOrderLeave = 0;

    // If person can order based on the size of the Probability Distributions
    private boolean exceedProbabilityDistributionArraySizes = false;

    // Amount of Successful orderFoodItems Method Calls
    private int orderGenericItemMethodSuccessfulCounter = 0;

    // Person's orders
    private ArrayList<Order> orders = new ArrayList<>();

    // Seat Information
    private Table.Seat tableSeat = null;

    // ShapePerson For Java FX
    private ShapePerson shapePerson = null;

    // The ProbabilityDistribution100Percent for Ordering for Initial and Following Orders
    // Probability of ordering per orderGenericItemMethodSuccessfulCounter
    // The max number for orderGenericItemMethodSuccessfulCounter is less than probabilityDistribution100PercentOfOrdering.getSizeProbabilityDistributionIndexArray() - 1
    private ProbabilityDistribution100Percent probabilityDistribution100PercentOfOrdering;

    // The ProbabilityDistributionIndex[] for Amount of Orders for Initial and Following Orders
    // Probability for the amount of orders per orderGenericItemMethodSuccessfulCounter
    // The max number for orderGenericItemMethodSuccessfulCounter is less than probabilityDistributionIndexAmountOfOrderGenericItemMethodSuccessfulCounterArray.length - 1
    private ProbabilityDistributionIndex[] probabilityDistributionIndexAmountOfOrderGenericItemMethodSuccessfulCounterArray;

    // The ProbabilityDistributionIndex for Time waiting instead of ordering per orderGenericItemMethodSuccessfulCounter
    private ProbabilityDistributionIndex probabilityDistributionIndexOfTimeWaitingInsteadOfOrdering; // getRandomValue() + 1

    public Person(Group group, int ID) {
        // Probability Person will order per orderGenericItemMethodSuccessfulCounter
        this(group, ID, new ProbabilityDistribution100Percent(new int[]{100, 100, 100, 100, 100, 100}));
//        this(Group group,ID, new ProbabilityDistribution100Percent(new int[]{100, 501231, 31231230, 12312320, 1235, 23230}));

    }

    public Person(Group group, int ID, ProbabilityDistribution100Percent probabilityDistribution100PercentOfOrdering) {
        // Probability Person will order certain amount per orderGenericItemMethodSuccessfulCounter
        this(group, ID, probabilityDistribution100PercentOfOrdering,
                new ProbabilityDistributionIndex[]{
                        new ProbabilityDistributionIndex(new int[]{10, 45, 45}),
                        new ProbabilityDistributionIndex(new int[]{70, 30}),
                        new ProbabilityDistributionIndex(new int[]{80, 20}),
                        new ProbabilityDistributionIndex(new int[]{90, 10}),
                        new ProbabilityDistributionIndex(new int[]{100})
                });
    }

    public Person(Group group, int ID, ProbabilityDistribution100Percent probabilityDistribution100PercentOfOrdering, ProbabilityDistributionIndex[] probabilityDistributionIndexAmountOfOrderGenericItemMethodSuccessfulCounterArray) {
        // Probability Person will order certain amount per orderGenericItemMethodSuccessfulCounter
        this(group, ID, probabilityDistribution100PercentOfOrdering, probabilityDistributionIndexAmountOfOrderGenericItemMethodSuccessfulCounterArray, new ProbabilityDistributionIndex(new int[]{10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120, 130, 140, 150, 160, 170, 180, 190, 200}));


    }


    public Person(Group group, int ID, ProbabilityDistribution100Percent probabilityDistribution100PercentOfOrdering, ProbabilityDistributionIndex[] probabilityDistributionIndexAmountOfOrderGenericItemMethodSuccessfulCounterArray, ProbabilityDistributionIndex probabilityDistributionIndexOfTimeWaitingInsteadOfOrdering) {
        // Group
        this.group = group;

        // Person information
        this.ID = ID;

        // The ProbabilityDistribution100Percent for Ordering for Initial and Following Orders
        this.probabilityDistribution100PercentOfOrdering = probabilityDistribution100PercentOfOrdering;

        // The ProbabilityDistributionIndex[] for Amount of Orders for Initial and Following Orders
        this.probabilityDistributionIndexAmountOfOrderGenericItemMethodSuccessfulCounterArray = probabilityDistributionIndexAmountOfOrderGenericItemMethodSuccessfulCounterArray;

        // The ProbabilityDistributionIndex for Time waiting instead of ordering
        this.probabilityDistributionIndexOfTimeWaitingInsteadOfOrdering = probabilityDistributionIndexOfTimeWaitingInsteadOfOrdering;
    }


    // This method MUST be called Once if you want to generate al orders not based on Method call
    public void orderFoodItemsAllAtOnce(Menu menu) {
        while (exceedProbabilityDistributionArraySizes == false) {
            orderFoodItems(menu);
        }
    }

    // This method can be called multiple times but may not result in a successful order made by person
    public void orderFoodItems(Menu menu, int timeAbsoluteCurrent) {
        orderFoodItemsCaller(menu, timeAbsoluteCurrent);
    }

    // This method can be called multiple times but may not result in a successful order made by person
    public void orderFoodItems(Menu menu) {
        int timeAbsoluteCurrent = group.getTimeAbsoluteArrive() + timeRelativeSpentAtLocation;
        orderFoodItemsCaller(menu, timeAbsoluteCurrent);
    }

    private void orderFoodItemsCaller(Menu menu, int timeAbsoluteCurrent) {

        /*
        Check if probabilityDistributionIndexAmountOfOrderGenericItemMethodSuccessfulCounterArray.length or
        probabilityDistribution100PercentOfOrdering.getSizeProbabilityDistributionIndexArray()
        is less than orderGenericItemMethodSuccessfulCounter to prevent Array IndexOutOfBounds
         */

        if (orderGenericItemMethodSuccessfulCounter < probabilityDistribution100PercentOfOrdering.getSizeProbabilityDistributionIndexArray() - 1 && orderGenericItemMethodSuccessfulCounter < probabilityDistributionIndexAmountOfOrderGenericItemMethodSuccessfulCounterArray.length - 1) {
            // If Willing to Order True
            if (exceedProbabilityDistributionArraySizes == false) {

                // Should the person Order?
                boolean shouldOrder = probabilityDistribution100PercentOfOrdering.getRandomBooleanFromIndex(orderGenericItemMethodSuccessfulCounter);

                // If should Order True
                if (shouldOrder == true) {

                    // Get objects number of orders based on Probability Distribution based on orderGenericItemMethodSuccessfulCounter
                    int numberOfOrders = probabilityDistributionIndexAmountOfOrderGenericItemMethodSuccessfulCounterArray[orderGenericItemMethodSuccessfulCounter].getRandomNumber();

                    if (numberOfOrders == 0) {
                        int timeRelativeRandomWait = probabilityDistributionIndexOfTimeWaitingInsteadOfOrdering.getRandomNumber() + 1;

                        // Add time spent at bar is modified here
                        timeRelativeSpentAtLocation += timeRelativeRandomWait;

                        // Add time spent at bar is modified here no wait time (For Java Fx)
//                        timeRelativeSpentAtLocationNoWaitOrderLeave += timeRelativeRandomWait;


                    } else {
                        for (int i = 0; i < numberOfOrders; i++) {
                            // Number of orders, Money spent, and Time spend at bar are modified here
                            orderFoodItemHandler(timeAbsoluteCurrent, menu);
                        }
                    }
                }
                // If should Order False
                else if (shouldOrder == false) {
                    exceedProbabilityDistributionArraySizes = true;
                }
                // Increment the number of successful orderFoodItems method calls (Means the person bought during this call)
                orderGenericItemMethodSuccessfulCounter++;
            }
//            System.out.println("orderGenericItemMethodSuccessfulCounter " + orderGenericItemMethodSuccessfulCounter);
//            System.out.println("exceedProbabilityDistributionArraySizes " + exceedProbabilityDistributionArraySizes);
//            System.out.println("shouldOrder " + shouldOrder);
        } else {
            exceedProbabilityDistributionArraySizes = true;
        }
    }

    private int getRandBetween(int low, int high) {
        return low + (int) ((high - low + 1) * Math.random());
    }

    private Food orderFoodItemHandler(int timeAbsoluteCurrent, Menu menu) {
        // Drinks have a range that the menu sets up
        // Menu has total range to get number from
        // 1. Get objects number from menu range
        // 2. Check that number between drink range
        // 3. If in range then return that drink

        // - 1 to compensate for + 1 so you won't get objects number 0 and that's not within the comparison operator ranges
        int randomNumber = getRandBetween(menu.getMenuRangeStart(), menu.getMenuRangeEnd() - 1) + 1;

        for (Food food : menu.getMenu()) {
            if (food.getProbabilityDistributionMenuDrinkRangeStart() < randomNumber && randomNumber <= food.getProbabilityDistributionMenuDrinkRangeEnd()) {

                // This person has ordered
                orders.add(new Order(this, food, timeAbsoluteCurrent));

                // Add 1 to number of drinks ordered
                numberOfOrdersTotal++;

                // Add time from orders to timeRelativeSpentFromOrders
                timeRelativeSpentFromOrders += food.getFoodDuration();

                // Add time from orders to timeRelativeSpentAtLocation
                timeRelativeSpentAtLocation += food.getFoodDuration();

                // Add time spent at bar is modified here without wait time (For Java Fx)
//                timeRelativeSpentAtLocationNoWaitOrderLeave += food.getFoodDuration();

                // Add money to money spent
                totalMoneySpent += food.getFoodCost();

                return food;
            }
        }
        throw new Error("Something Broke with orderFoodItemHandler");
    }

    public int getTimeRelativeSpentAtLocation() {
        return timeRelativeSpentAtLocation;
    }

    public void incrementTimeRelativeSpendAtLocation(int time) {
        timeRelativeSpentAtLocation += time;
    }

    public int getTimeRelativeSpentFromOrders() {
        return timeRelativeSpentFromOrders;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public int getTotalNumberOfOrders() {
        return numberOfOrdersTotal;
    }

    public int getTotalMoneySpent() {
        return totalMoneySpent;
    }

    public int getID() {
        return ID;
    }

    public Table.Seat getTableSeat() {
        return tableSeat;
    }

    public void setTableSeat(Table.Seat tableSeat) {
        this.tableSeat = tableSeat;
    }

    public Group getGroup() {
        return group;
    }

    public ProbabilityDistribution100Percent getProbabilityDistribution100PercentOfOrdering() {
        return probabilityDistribution100PercentOfOrdering;
    }

    public ProbabilityDistributionIndex[] getProbabilityDistributionIndexAmountOfOrderGenericItemMethodSuccessfulCounterArray() {
        return probabilityDistributionIndexAmountOfOrderGenericItemMethodSuccessfulCounterArray;
    }

    public ProbabilityDistributionIndex getProbabilityDistributionIndexOfTimeWaitingInsteadOfOrdering() {
        return probabilityDistributionIndexOfTimeWaitingInsteadOfOrdering;
    }

    public ShapePerson getShapePerson() {
        return shapePerson;
    }

//    public int getTimeRelativeSpentAtLocationNoWaitOrderLeave() {
//        return timeRelativeSpentAtLocationNoWaitOrderLeave;
//    }

    // For java Fx
    public void setShapePerson(ShapePerson shapePerson) {
        this.shapePerson = shapePerson;
    }

    @Override
    public String toString() {
        return String.format("Person %s", ID);
    }



    // Test
//    public static void main(String[] args) {
//        Menu testMenu = new Menu();
//        testMenu.addDrink(new Drink("Generic Beer", 50, 10, 50));
//        testMenu.addDrink(new Drink("Brandy", 50, 10, 50));
//        testMenu.addDrink(new Drink("Rum", 50, 10, 50));
//        testMenu.addDrink(new Drink("Gin", 50, 10, 50));
//
//        ProbabilityDistributionIndex testDrinker = new ProbabilityDistributionIndex(new int[]{1, 100, 100, 100});
//
//        Group testGroup = new Group(1, 5, 10, 0);
//
//        for (int i = 0; i < 10000; i++) {
//            Person testPerson = new Person(testGroup, i);
//
//            System.out.println(String.format("Group Number: %s", testPerson.getGroup().getGroupNumber()));
//            System.out.println(String.format("Arrival Time: %s", testPerson.getGroup().getTimeAbsoluteArrive()));
//
//            // Order
//            testPerson.orderFoodItemsAllAtOnce(testMenu);
//            System.out.println(String.format("Person Number: %s", testPerson.getID()));
//            System.out.println(String.format("Number of Orders: %s", testPerson.getTotalNumberOfOrders()));
//            System.out.println(String.format("Time spent Drinking: %s", testPerson.getTimeRelativeSpentAtLocation()));
//            System.out.println(String.format("Amount of Money spent: %s", testPerson.getTotalMoneySpent()));
//
//            System.out.println("Person's Orders");
//            System.out.println(testPerson.getOrders());
//            System.out.println();
//        }
//
//
//        Person testPerson = new Person(testGroup, 1);
//        for (int i = 0; i < 10; i++) {
//            testPerson.orderFoodItems(testMenu);
//        }
//    }

}
