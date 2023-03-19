public class SimulationFramework {

    private int currentTime = 0;
//    private MinPQ eventPriorityQueue;

    // A priority queue on party size
    // So in the queue is the party size
    // YOU ARE COMPARING THE EVENT's TIME
    private PriorityQueueHeap eventPriorityQueue = new PriorityQueueHeapMin(new ComparatorEvent(), 10);

    public void scheduleEvent(Event newEvent) {
        // put or addElement “newEvent” to the “eventPriorityQueue”
        // MinHeap Priority Queue (left for you)
        eventPriorityQueue.insert(newEvent);
    }

    public void run() {

        ((PriorityQueueHeapMin) eventPriorityQueue).minHeap();

        while (!eventPriorityQueue.isEmpty()) {
            // remove min element from priority queue (Min Heap)
            Event nextEvent = (Event) eventPriorityQueue.remove();
            currentTime = nextEvent.getTime();
            nextEvent.processEvent(); // what do you see here???
        }
    }

    public int getCurrentTimeOfSimulationByEvent() {
        return currentTime;
    }

}

