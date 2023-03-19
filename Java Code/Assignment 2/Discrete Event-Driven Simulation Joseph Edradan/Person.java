/*
This class is literally what you thing a person would be...

A person has
    group number
    arrival time
    number of orders
    time spent drinking
    the orders this person made

* Person does not have a preference for a type of order, but has a preference for the amount of drinks to order
* This means that you can can specify the amount of drinks a person will drink or don't and leave it to the default
* probability distribution.

Person is given
    a bar menu
    a probabilityDistributionOfDrinking type

 */

import java.util.ArrayList;

public class Person {

    // Group Information
    private int groupNumber;
    private int arrivalTime;
    private int waitTime;

    // Person Information
    private int personNumber;
    private int numberOfOrders;
    private int totalMoneySpent;
    private ArrayList<Order> orders = new ArrayList<>();    // Person's orders
    private int timeSpentDrinkingTotal;                     // Total time spent drinking (Determined by the drink)
    private int timeSpentAtBar;
    // Bar Menu
    private Menu barMenu;

    // The ProbabilityDistribution object (AKA Probability of this person will make orders)
    private ProbabilityDistribution probabilityDistributionOfDrinking;

    // For Java FX Seat information
    private Table.Seat seat;
    private int mostTimeRelativeSpentDrinkingSetByAPerson;

    public Person(int personNumber, int groupNumber, int arrivalTime, Menu barMenu) {
//        int[] defaultProbabilities = new int[]{80,50,20,10,5};
//        ProbabilityDistribution defaultDrinker = new ProbabilityDistribution(defaultProbabilities);
        this(personNumber, groupNumber, arrivalTime, barMenu, new ProbabilityDistribution(new int[]{5, 80, 50, 20, 5}));
        this.personNumber = personNumber;
    }


    public Person(int personNumber, int groupNumber, int arrivalTime, Menu barMenu, ProbabilityDistribution probabilityDistributionOfDrinking) {
        // For Java FX
        this.seat = null;

        this.personNumber = personNumber;

        this.groupNumber = groupNumber;
        this.arrivalTime = arrivalTime;
        this.waitTime = 0;

        this.numberOfOrders = 0;
        this.totalMoneySpent = 0;
        this.timeSpentDrinkingTotal = 0;

        this.barMenu = barMenu;

        // The probability object for drinking
        this.probabilityDistributionOfDrinking = probabilityDistributionOfDrinking;

//        this.orderDrinks();
    }

    public void orderDrinks() {
        int numberOfOrdersPersonWillMake = probabilityDistributionOfDrinking.getRandomNumber();

        for (int i = 0; i < numberOfOrdersPersonWillMake; i++) {
            orderDrinkHandler(arrivalTime + timeSpentDrinkingTotal + waitTime);
        }
        timeSpentAtBar = timeSpentDrinkingTotal;
    }

    private int getRandBetween(int low, int high) {
        return low + (int) ((high - low + 1) * Math.random());
    }


    private Drink orderDrinkHandler(int timeBoughtAt) {
        // Drinks have a range that the menu sets up
        // Menu has total range to get number from
        // 1. Get random number from menu range
        // 2. Check that number between drink range
        // 3. If in range then return that drink

        int randomNumber = getRandBetween(barMenu.getMenuRangeStart(), barMenu.getMenuRangeEnd());

        for (Drink drink : barMenu.getMenu()) {
            if (drink.getProbabilityDistributionMenuDrinkRangeStart() < randomNumber && randomNumber <= drink.getProbabilityDistributionMenuDrinkRangeEnd()) {

                // This person has ordered this drink
                orders.add(new Order(this, drink, timeBoughtAt));

                // Add 1 to number of drinks ordered
                numberOfOrders++;

                // Add time from drinking to amount of time this person drank the drink
                timeSpentDrinkingTotal += drink.getDrinkDuration();

                // Add money to money spent
                totalMoneySpent += drink.getDrinkCost();

                return drink;
            }
        }
        return new Drink("Failed", 0, 0, 0);
    }

    public int getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }
    public int getTimeSpentDrinkingTotal() {
        return timeSpentDrinkingTotal;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public int getGroupNumber() {
        return groupNumber;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getNumberOfOrders() {
        return numberOfOrders;
    }

    public int getTotalMoneySpent() {
        return totalMoneySpent;
    }

    public int getPersonNumber() {
        return personNumber;
    }

    public Table.Seat getSeat() {
        return seat;
    }

    public void setSeat(Table.Seat seat) {
        this.seat = seat;
    }

    public int getMostTimeRelativeSpentDrinkingSetByAPerson() {
        return mostTimeRelativeSpentDrinkingSetByAPerson;
    }

    public void setMostTimeRelativeSpentDrinkingSetByAPerson(int mostTimeRelativeSpentDrinkingSetByAPerson) {
        this.mostTimeRelativeSpentDrinkingSetByAPerson = mostTimeRelativeSpentDrinkingSetByAPerson;
    }
    public int getTimeSpentAtBar() {
        return timeSpentAtBar;
    }

    @Override
    public String toString() {
        return String.format("Person %s", personNumber);
    }




    // Test
//    public static void main(String[] args) {
//        Menu testMenu = new Menu();
//        testMenu.addDrink(new Drink("Generic Beer", 50, 10, 50));
//        testMenu.addDrink(new Drink("Brandy", 50, 10, 50));
//        testMenu.addDrink(new Drink("Rum", 50, 10, 50));
//        testMenu.addDrink(new Drink("Gin", 50, 10, 50));
//
//        ProbabilityDistribution testDrinker = new ProbabilityDistribution(new int[]{1, 100, 100, 100});
//
////        Person testPerson = new Person(1, 10, testMenu, testDrinker);
////        System.out.println(testPerson.getOrders());
//
//        for (int i = 0; i < 100; i++) {
//            Person testPerson = new Person(i,1, 77, testMenu);
//
//            System.out.println(testPerson.getOrders());
//            System.out.println(String.format("Group Number: %s",testPerson.getGroupNumber()));
//            System.out.println(String.format("Person Number: %s",testPerson.getPersonNumber()));
//            System.out.println(String.format("Arrival Time: %s",testPerson.getArrivalTime()));
//            System.out.println(String.format("Number of Orders: %s",testPerson.getNumberOfOrders()));
//            System.out.println(String.format("Time spent Drinking: %s",testPerson.getTimeSpentDrinkingTotal()));
//            System.out.println(String.format("Amount of Money spent: %s",testPerson.getTotalMoneySpent()));
//            System.out.println();
//        }
//    }
}
