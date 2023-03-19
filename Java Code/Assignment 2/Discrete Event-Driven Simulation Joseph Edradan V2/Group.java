/*
Group Object should make Person objects

----------------------------------------------------------------
Must call:
    setTimeRelativeWaitOrder(int);
    setTimeRelativeWaitLeave(int);
    setTable(Table)
----------------------------------------------------------------
If you call:
    orderFoodItems();

Then you must call this at the end of time:
    addOrdersToTotalGroupOrders();
----------------------------------------------------------------
Generate All Orders
    orderFoodItemsAllAtOnce();
----------------------------------------------------------------
 */

import java.util.ArrayList;

public class Group {

    // Group Information
    private int groupNumber;
    private int groupSize;

    // Group Person ArrayList
    private ArrayList<Person> groupMembers = new ArrayList<>();

    private int timeAbsoluteArrive;

    // Group Wait time order
    private int timeRelativeWaitOrder;

    // Group Wait time order
    private int timeRelativeWaitLeave;

    // Group Leave time
    private int timeAbsoluteLeave = 0;

    // Menu given to group
    private Menu menu = null;

    // Total money spent by group
    private int totalMoneySpent = 0;

    // Total number of orders
    private int totalNumberOfOrders = 0;

    // Group Order ArrayList
    private ArrayList<Order> totalGroupOrders = new ArrayList<>();

    // All the events the group makes
    private ArrayList<Event> groupEvents = new ArrayList<>();

    // Most time spent at location by 1 person
    private int timeRelativeMostSpentSetByAPerson = 0;

    private int timeRelativeMostSpentFromOrders = 0;

    private int timeRelativeMostSpentSetByAPersonNoWaitTime = 0;

    // Meta information on people
    private int personNumberOffset;

    // Table that the group sat at
    private Table table = null;

    public Group(int groupNumber, int groupSize, int timeAbsoluteArrive) {
        this(groupNumber, groupSize, timeAbsoluteArrive, 0);
    }

    public Group(int groupNumber, int groupSize, int timeAbsoluteArrive, int personNumberOffset) {
        // Group number
        this.groupNumber = groupNumber;

        // Group Size
        this.groupSize = groupSize;

        // Group Arrival time
        this.timeAbsoluteArrive = timeAbsoluteArrive;

        // META INFORMATION
        // Person number offset for counting Absolute number of people
        this.personNumberOffset = personNumberOffset;

        // Create Group members
        this.generateMembers();
    }

    public void giveGroupMenu(Menu menu) {
        this.menu = menu;
    }

    private void generateMembers() {
        for (int i = 0; i < groupSize; i++) {
            Person person = new Person(this, i + 1 + personNumberOffset);

            // Add person to Group
            groupMembers.add(person);

        }
    }


    public void orderFoodItemsAllAtOnce(){
        // Loop to for every Group member to order
        for (Person person : groupMembers) {

            // Give person menu so they can order (MUST CALL orderFoodItemsAllAtOnce NOT orderFoodItems)
            person.orderFoodItemsAllAtOnce(menu);

            // Add person's number of orders
            totalNumberOfOrders += person.getTotalNumberOfOrders();

            // Add person's money spent
            totalMoneySpent += person.getTotalMoneySpent();

            // Add person's orders to the group orders
            for (Order order : person.getOrders()) {
                totalGroupOrders.add(order);
            }

        }

        // Set the absolute leave time by the group
        calculateTimeAbsoluteLeave();
    }

    public void orderFoodItems() {
        // Reset Total money spent by group
        totalMoneySpent = 0;

        // Reset Total number of orders by group
        totalNumberOfOrders = 0;

        // Loop to for every Group member to order
        for (Person person : groupMembers) {

            // Give person menu so they can order
            person.orderFoodItems(menu);
        }

        /*
        Loop to get the total amount of Money spent by person
        This is a second loop because orderFoodItems();
        Can be called an unlimited amount of times.
        */
        for (Person person : groupMembers) {
            // Add person's number of orders
            totalNumberOfOrders += person.getTotalNumberOfOrders();

            // Add person's money spent
            totalMoneySpent += person.getTotalMoneySpent();
        }

        // Set the absolute leave time by the group
        calculateTimeAbsoluteLeave();
    }

    public void addOrdersToTotalGroupOrders(){
        for (Person person : groupMembers) {
            // Add person's orders
            for (Order order : person.getOrders()) {
                totalGroupOrders.add(order);
            }
        }
    }

    public int getTimeRelativeMostSpentFromOrders() {
        return timeRelativeMostSpentFromOrders;
    }

    public int getTimeRelativeMostSpentSetByAPersonNoWaitTime() {
        return timeRelativeMostSpentSetByAPersonNoWaitTime;
    }

    private void calculateTimeAbsoluteLeave() {
        for (Person person : groupMembers) {
            if (person.getTimeRelativeSpentAtLocation() > timeRelativeMostSpentSetByAPerson) {
                // Set the relative most time spent by a person at bar
                timeRelativeMostSpentSetByAPerson = person.getTimeRelativeSpentAtLocation();

                // Set the relative most time spent by a person at bar
                // Includes time waiting if they didn't order but not the time
                // from setTimeRelativeWaitOrder(int timeRelativeWait) and setTimeRelativeWaitLeave(int timeRelativeWait)
//                timeRelativeMostSpentSetByAPersonNoWaitTime = person.getTimeRelativeSpentAtLocationNoWaitOrderLeave();


                // Set the relative most time spent from order by person at bar
                timeRelativeMostSpentFromOrders = person.getTimeRelativeSpentFromOrders();

            }
        }

        // Set the absolute leave time
        timeAbsoluteLeave = timeAbsoluteArrive + timeRelativeMostSpentSetByAPerson;
    }

    public int getTimeRelativeWaitOrder() {
        return timeRelativeWaitOrder;
    }

    public int getTimeRelativeWaitLeave() {
        return timeRelativeWaitLeave;
    }

    public void setTimeRelativeWaitOrder(int timeRelativeWait) {
        this.timeRelativeWaitOrder = timeRelativeWait;

        for(Person person: getGroupMembers()){
            person.incrementTimeRelativeSpendAtLocation(this.timeRelativeWaitOrder);
        }
        calculateTimeAbsoluteLeave();
    }

    public void setTimeRelativeWaitLeave(int timeRelativeWait) {
        this.timeRelativeWaitLeave = timeRelativeWait;

        for(Person person: getGroupMembers()){
            person.incrementTimeRelativeSpendAtLocation(this.timeRelativeWaitLeave);
        }

        calculateTimeAbsoluteLeave();
    }

    public int getGroupNumber() {
        return groupNumber;
    }

    public int getGroupSize() {
        return groupSize;
    }

    public ArrayList<Person> getGroupMembers() {
        return groupMembers;
    }

    public int getTimeAbsoluteArrive() {
        return timeAbsoluteArrive;
    }

    public int getTimeAbsoluteLeave() {
        return timeAbsoluteLeave;
    }

    public Menu getMenu() {
        return menu;
    }

    public int getTotalMoneySpent() {
        return totalMoneySpent;
    }

    public int getTotalNumberOfOrders() {
        return totalNumberOfOrders;
    }

    public ArrayList<Order> getTotalGroupOrders() {
        return totalGroupOrders;
    }

    public int getTimeRelativeMostSpentSetByAPerson() {
        return timeRelativeMostSpentSetByAPerson;
    }

    public ArrayList<Event> getGroupEvents() {
        return groupEvents;
    }

    public Table getTable() {
        return table;
    }

    public void setTableForJavaFX(Table table) {
        this.table = table;
    }

    @Override
    public String toString() {
        return String.format("Group number %s", getGroupNumber());
    }
}
