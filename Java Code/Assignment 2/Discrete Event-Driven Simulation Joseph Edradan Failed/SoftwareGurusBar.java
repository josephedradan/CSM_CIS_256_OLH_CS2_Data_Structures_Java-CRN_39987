/*
This is the main file
 */
public class SoftwareGurusBar {
    private int freeChairs = 50;
    private double profit = 0.0;

    private SimulationFramework simulationThatRunsEverything = new SimulationFramework();

    public static void main(String[] args) {
        SoftwareGurusBar world = new SoftwareGurusBar();
    }

    // TODO MODIFY THIS
    // TODO BAR OPENING TIME AND CLOSING TIME
    public SoftwareGurusBar() {
        int time = 0;
        while (time < 240) {                                  // simulate 4 hours of bar operation
            time += getRandBetween(2, 5);          // new group every 2-5 minutes

            simulationThatRunsEverything.scheduleEvent(
                    new ArriveEvent(
                            time,
                            getRandBetween(1, 5)   // group size ranges from 1 to 5
                    )
            );

        }

        simulationThatRunsEverything.run();

        System.out.println();
        System.out.println("Total profits " + profit);
    }

    // Get random between (Helper Method basically)
    private int getRandBetween(int low, int high) {
        return low + (int) ((high - low + 1) * Math.random());
    }

    // Seats (Group) people if they can sit in bar (Sequential part 1)
    // TODO ADD TABLES!!!!!!!!!!!!!!!!!!!
    // TODO THIS WILL BE COMPLETELY DIFFERENT IN A REALISTIC VERSION
    // TODO ALSO ADD GROUP NUMBERS
    public boolean canSeat(int numberOfPeople) {
        System.out.println("Group of " + numberOfPeople +
                " customers arrives at getCurrentTimeOfSimulationByEvent " + simulationThatRunsEverything.getCurrentTimeOfSimulationByEvent());
        if (numberOfPeople < freeChairs) {
            System.out.println("Group is seated");
            freeChairs -= numberOfPeople;
            return true;
        } else
            System.out.println("No Room, Group Leaves");
        return false;
    }

    // Group orders (Sequential part 2)
    // TODO EVERY GROUP SHOULD HAVE A TIME TO WAIT FOR REALISTIC VERSION
    private void order(int beerType) {
        System.out.println("Serviced order for beer type " + beerType +
                " at getCurrentTimeOfSimulationByEvent " + simulationThatRunsEverything.getCurrentTimeOfSimulationByEvent());
        // TODO update profit knowing beerType (left for you)
    }

    // Group (Sequential part 3)
    private void leave(int numberOfPeople) {
        System.out.println(
                "Group of size " + numberOfPeople +
                        " leaves at getCurrentTimeOfSimulationByEvent " + simulationThatRunsEverything.getCurrentTimeOfSimulationByEvent());

        freeChairs += numberOfPeople;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////

    // TODO MODIFY THIS
    private class ArriveEvent extends Event {
        private int groupSize;

        ArriveEvent(int time, int groupSize) {
            super(time);
            this.groupSize = groupSize;
        }

        public void processEvent() {
            if (canSeat(groupSize)) {
                // place an order within 2 & 10 minutes
                simulationThatRunsEverything.scheduleEvent(
                        new OrderEvent(
                                time + getRandBetween(2, 10),
                                groupSize
                        )
                );
            }
        }

        @Override
        public String toString() {
            return String.format("%s ArriveEvent",getTime() );
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////

    // TODO MODIFY THIS
    private class OrderEvent extends Event {
        private int groupSize;

        OrderEvent(int time, int groupSize) {
            super(time);
            this.groupSize = groupSize;
        }

        public void processEvent() {
            // each member of the group orders a beer (type 1,2,3)
            for (int i = 0; i < groupSize; i++)
                order(1 + getRandBetween(1, 3));
            // schedule a leaveEvent for this group
            // all the group leaves together (left for you)


        }

        @Override
        public String toString() {
            return String.format("%s OrderEvent",getTime() );
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////

    // TODO MODIFY THIS
    private class LeaveEvent extends Event {
        LeaveEvent(int time, int groupSize) {
            super(time);
            this.groupSize = groupSize;
        }

        private int groupSize;

        public void processEvent() {
            leave(groupSize);
        }

        @Override
        public String toString() {
            return String.format("%s LeaveEvent",getTime() );
        }
    }
}