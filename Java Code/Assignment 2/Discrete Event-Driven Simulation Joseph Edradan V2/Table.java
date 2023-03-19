import java.util.ArrayList;

/*
To keep it simple tables are 1 block to 1 seat ratio
 */
public class Table {

    // Table number
    private int tableNumber;
    // Max table size is a 1 to 1 ratio for size to person
    private int tableSize;
    // Seat objects array list
    private ArrayList<Seat> seats = new ArrayList<>();
    // Group at the table
    private Group group;

    public Table(int tableNumber, int tableSize) {
        this.tableNumber = tableNumber;
        this.tableSize = tableSize;
        this.group = null;

        generateSeats();
    }

    private void generateSeats() {
        for (int i = 0; i < tableSize; i++) {
            Seat seat = new Seat(this);
            seats.add(seat);
        }
    }

    public boolean isTableOccupied() {
        if (!(group == null)) {
//            System.out.println(String.format("Table Number: %s Table Size: %s  Group: %s IS OCCUPIED", tableNumber,tableSize, this.group.getGroupNumber()));
            return true;
        }
        return false;
    }

    public boolean isTablePartiallyOccupied() {
        if (!(group == null) && group.getGroupSize() < tableSize) {
            return true;
        }
        return false;
    }

    public boolean isTableFullyOccupied() {
        if (!(group == null) && group.getGroupSize() == tableSize) {
            return true;
        }
        return false;
    }

    public int availableSeats() {
        int counter = 0;
        for (Seat seat : seats) {
            if (seat.getPerson() == null) {
                counter++;
            }
        }
        return counter;
    }

    public int getTableSize() {
        return tableSize;
    }

    public Group getGroup() {
        return group;
    }

    public void seatGroup(Group group) {
//        System.out.println(this);
//        System.out.println(String.format("CURRENT GROUP TABLE %s AND SIZE %s",tableNumber,tableSize));
//        System.out.println(String.format("CURRENT GROUP THIS: %s",  this.group));
//        System.out.println(String.format("CURRENT GROUP COMMING: %s",  group));

        this.group = group;

        if (group.getGroupSize() <= tableSize) {
            for (int i = 0; i < group.getGroupSize(); i++) {
                seats.get(i).setPerson(group.getGroupMembers().get(i));
            }
        } else {
            System.out.println(String.format("%s cannot occupy Table number %s", group, tableNumber));
        }
//        System.out.println(String.format("Table Number: %s Table Size: %s  Group: %s ENTER", tableNumber,tableSize, this.group.getGroupNumber()));

    }

    public void removeGroup() {
        for (Seat seat : seats) {
            seat.removePerson();
        }
//        System.out.println(String.format("Table Number: %s Table Size: %s  Group: %s LEAVE", tableNumber,tableSize, group.getGroupNumber()));
        this.group = null;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public ArrayList<Seat> getSeats() {
        return seats;
    }

    private String toStringHelper() {
        if (isTableFullyOccupied()) {
            return "Fully Occupied";
        } else if (isTablePartiallyOccupied()) {
            return "Partially Occupied";
        }
        return "Not Occupied";
    }
    // isTablePartiallyOccupied() ? "Fully Occupied" : "Not Occupied")

    protected class Seat {

        // Table where the seat is
        Table currentTable;

        // Person at seat
        Person person;

        // ShapeTable.ShapeSeat
        ShapeTable.ShapeSeat shapeTableShapeSeat = null;

        public Seat(Table currentTable) {
            this.currentTable = currentTable;
            this.person = null;

        }

        public Person getPerson() {
            return person;
        }

        public void setPerson(Person person) {
            this.person = person;

            // For Java FX
            person.setTableSeat(this);
        }

        public void removePerson() {
            this.person = null;
        }

        public ShapeTable.ShapeSeat getShapeTableShapeSeat() {
            return shapeTableShapeSeat;
        }

        // For Java FX so I don't have to loop
        public void setShapeTableShapeSeat(ShapeTable.ShapeSeat shapeTableShapeSeat) {
            this.shapeTableShapeSeat = shapeTableShapeSeat;
        }
    }
//    @Override
//    public String toString() {
//        return String.format("Table %s with %s seats is %s",
//                tableNumber,
//                tableSize,
//                toStringHelper());
//    }
}
