public class Order {

    private Person person;
    private Drink drink;
    private int timeBoughtAt;

    public Order(Person person,Drink drink, int timeBoughtAt) {
        this.person = person;

        this.drink = drink;
        this.timeBoughtAt = timeBoughtAt;
    }

    public Person getPerson() {
        return person;
    }

    public Drink getDrink() {
        return drink;
    }

    public int getTimeBoughtAt() {
        return timeBoughtAt;
    }

    @Override
    public String toString() {
//        return String.format(
//                "Group number %s %s bought %s at Absolute time %s",
//                person.getGroupNumber(),
//                person,
//                drink,
//                timeBoughtAt);
        return String.format("Time: %-4s Group: %-4s Person: %-4s Bought: %s",
                timeBoughtAt,
                person.getGroupNumber(),
                person.getPersonNumber(),
                drink);
    }

}
