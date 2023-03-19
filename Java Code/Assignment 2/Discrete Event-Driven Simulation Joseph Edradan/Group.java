import java.util.ArrayList;

// Groupds SHOULD HAVE
public class Group {

    // Group Information
    private int groupNumber;
    private int groupSize;
    private ArrayList<Person> groupMembers = new ArrayList<>();

    private int timeArrive;
    private int timeWaitRelative;
    private int timeLeave;
    // Bar Menu
    private Menu barMenu;

    // Group Order Information
    private int totalMoneySpent;
    private int totalNumberOfOrders;
    private ArrayList<Order> totalOrders = new ArrayList<>();

    // All the events the group makes
    private ArrayList<Event> groupEvents = new ArrayList<>();

    // This is the time set by the person who wants to stay at the bar for the most time because they drank too much
    private int mostTimeSpentAtBarSetByAPerson;

    // Meta information on people
    private int personNumberOffset = 0;

    // Java FX information (For putting the ShapePerson onto the ShapeTable)
    private Table table;

    public Group(int groupNumber, int groupSize, int timeArrive, Menu barMenu){
        this(groupNumber,groupSize, timeArrive,barMenu, 0);
    }

    public Group(int groupNumber, int groupSize, int timeArrive, Menu barMenu , int personNumberOffset){
        // Java FX information (For putting the ShapePerson onto the ShapeTable)
        table  = null;

        totalMoneySpent = 0;
        totalNumberOfOrders = 0;

        mostTimeSpentAtBarSetByAPerson = 0;

        this.groupNumber = groupNumber;

        this.groupSize = groupSize;
        this.timeArrive = timeArrive;

        this.barMenu = barMenu;

        this.personNumberOffset = personNumberOffset;

        this.generateMembers();
    }

    private void generateMembers(){
        for(int i = 0; i < groupSize; i++){
            Person person = new Person(i + 1 + personNumberOffset ,this.groupNumber, this.timeArrive, this.barMenu);

            // Add person
            groupMembers.add(person);

        }
    }

    public void orderDrinks(int timeWaitRelative){
        this.timeWaitRelative = timeWaitRelative;

        for(Person person : groupMembers){
            person.setWaitTime(timeWaitRelative);

            person.orderDrinks();

            // Add person's number of orders
            totalNumberOfOrders += person.getNumberOfOrders();

            // Add person's money spent
            totalMoneySpent += person.getTotalMoneySpent();

            // Add person's orders
            for(Order order: person.getOrders()) {
                totalOrders.add(order);
            }
        }
        this.setMostTimeSpentAtBarSetByAPerson();
    }

    private void setMostTimeSpentAtBarSetByAPerson(){
        for(Person person: groupMembers){
            if (person.getTimeSpentAtBar() > mostTimeSpentAtBarSetByAPerson){
                mostTimeSpentAtBarSetByAPerson = person.getTimeSpentAtBar();
            }
        }
        timeLeave = timeArrive + mostTimeSpentAtBarSetByAPerson + timeWaitRelative;
        for(Person person: groupMembers){
            person.setMostTimeRelativeSpentDrinkingSetByAPerson(timeLeave);
        }
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

    public int getTimeArrive() {
        return timeArrive;
    }

    public int getTimeLeave() {
        return timeLeave;
    }

    public Menu getBarMenu() {
        return barMenu;
    }

    public int getTotalMoneySpent() {
        return totalMoneySpent;
    }

    public int getTotalNumberOfOrders() {
        return totalNumberOfOrders;
    }

    public ArrayList<Order> getTotalOrders() {
        return totalOrders;
    }

    public int getMostTimeSpentAtBarSetByAPerson(){
        return mostTimeSpentAtBarSetByAPerson;
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
    public String toString(){
        return String.format("Group number %s", getGroupNumber());
    }


    // Test
//    public static void main(String[] args) {
//        Menu testMenu = new Menu();
//        testMenu.addDrink(new Drink("Generic Beer", 50, 10, 50));
//        testMenu.addDrink(new Drink("Brandy", 50, 10, 50));
//        testMenu.addDrink(new Drink("Rum", 50, 10, 50));
//        testMenu.addDrink(new Drink("Gin", 50, 10, 50));
//
//        int personNumberOffset = 0;
//        for (int i = 0; i < 100; i++) {
//            Group testGroup = new Group(i,4, 77, testMenu, personNumberOffset);
//
//            // Person Counter Offset
//            personNumberOffset += testGroup.getGroupSize();
//
//            System.out.println(testGroup.getGroupMembers());
//            System.out.println(testGroup.getTotalOrders());
//            System.out.println(String.format("Group Number: %s",testGroup.getGroupNumber()));
//            System.out.println(String.format("Group Max Size: %s",testGroup.getGroupSize()));
//            System.out.println(String.format("Arrival Time: %s",testGroup.getTimeArrive()));
//            System.out.println(String.format("Total Number of Orders: %s",testGroup.getTotalNumberOfOrders()));
//            System.out.println(String.format("Max Time spent Drinking: %s",testGroup.getMostTimeRelativeSpentDrinkingSetByAPerson()));
//            System.out.println(String.format("Total Amount of Money spent: %s",testGroup.getTotalMoneySpent()));
//            for(Person testPerson : testGroup.getGroupMembers()){
//                System.out.println(String.format("\t%s", testPerson.getOrders()));
//                System.out.println(String.format("\tGroup Number: %s",testPerson.getGroupNumber()));
//                System.out.println(String.format("\tPerson Number: %s",testPerson.getPersonNumber()));
//                System.out.println(String.format("\tArrival Time: %s",testPerson.getArrivalTime()));
//                System.out.println(String.format("\tNumber of Orders: %s",testPerson.getNumberOfOrders()));
//                System.out.println(String.format("\tTime spent Drinking: %s",testPerson.getTimeSpentDrinkingTotal()));
//                System.out.println(String.format("\tAmount of Money spent: %s",testPerson.getTotalMoneySpent()));
//                System.out.println();
//            }
//            System.out.println();
//        }
//    }


}
