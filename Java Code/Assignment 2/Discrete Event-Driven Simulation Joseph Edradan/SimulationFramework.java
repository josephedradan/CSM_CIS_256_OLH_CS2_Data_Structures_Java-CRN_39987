import java.util.ArrayList;

public class SimulationFramework {

    // Current Simulation Time
    private int currentTimeOfSimulation = 0;

    // Min Priority Queue for Events
    private PriorityQueueHeap eventPriorityQueue = new PriorityQueueHeapMin(new ComparatorEvent(), 10);

    // For Java FX (Just leave it here)
    public ArrayList<Event> eventArrayList = new ArrayList<>();

    public void scheduleEvent(Event newEvent) {
        // put or addElement “newEvent” to the “eventPriorityQueue”
        // MinHeap Priority Queue (left for you)
        eventPriorityQueue.insert(newEvent);
    }

    public void run() {
        // Essentially, every time an ArriveEvent is called a OrderEvent and sequentially a LeaveEvent are created
        // and added and eventually called
        while (!eventPriorityQueue.isEmpty()) {
            // remove min element from priority queue (Min Heap)
            Event nextEvent = (Event) eventPriorityQueue.remove();

            // For Java FX
            eventArrayList.add(nextEvent);

            currentTimeOfSimulation = nextEvent.getTime();
            nextEvent.processEvent(); // what do you see here???
        }

    }

    public int getCurrentTimeOfSimulationByEvent() {
        return currentTimeOfSimulation;
    }

}

