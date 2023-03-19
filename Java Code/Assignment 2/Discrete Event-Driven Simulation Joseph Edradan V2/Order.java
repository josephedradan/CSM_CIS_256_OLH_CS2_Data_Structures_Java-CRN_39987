public class Order{
    private Person personWhoOrdered;
    private Food genericItemThatPersonOrdered;
    private int timeAbsoluteBoughtAt;

    public Order(Person personWhoOrdered, Food genericItemThatPersonOrdered, int timeAbsoluteBoughtAt) {
        this.personWhoOrdered = personWhoOrdered;

        this.genericItemThatPersonOrdered = genericItemThatPersonOrdered;
        this.timeAbsoluteBoughtAt = timeAbsoluteBoughtAt;
    }

    public Person getPersonWhoOrdered() {
        return personWhoOrdered;
    }

    public Food getFoodItemThatPersonOrdered() {
        return genericItemThatPersonOrdered;
    }

    public int getTimeAbsoluteBoughtAt() {
        return timeAbsoluteBoughtAt;
    }

    @Override
    public String toString() {
//        return String.format(
//                "Group number %s %s bought %s at Absolute time %s",
//                personWhoOrdered.getGroupNumber(),
//                personWhoOrdered,
//                genericItemThatPersonOrdered,
//                timeAbsoluteBoughtAt);
        return String.format("Time: %-5s Group: %-5s Person: %-7s Bought: %s",
                timeAbsoluteBoughtAt,
                personWhoOrdered.getGroup().getGroupNumber(),
                personWhoOrdered.getID(),
                genericItemThatPersonOrdered);
    }
}
