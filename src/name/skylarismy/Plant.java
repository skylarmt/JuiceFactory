package name.skylarismy;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * A factory for processing oranges. Filled with infinite oranges and a bunch of
 * child workers/drones.
 */
public class Plant {

    public final int ORANGES_PER_BOTTLE = 3;
    public final int WORKERS_PER_PLANT = 2;

    public int orangesProvided;
    public int orangesProcessed;
    protected volatile boolean timeToWork;

    // A queue of oranges, so the workers can pass them around as needed
    public final Queue<Orange> orangequeue = new ArrayBlockingQueue<>(WORKERS_PER_PLANT);

    // The workers.
    private Drone[] workers;

    /**
     * Grow an all-natural factory plant. They are grown, not built, right?
     */
    Plant() {
        this("Plant");
    }

    /**
     * Make a juice plant/factory.
     *
     * @param name This just goes on a sign over the door, nobody really cares.
     */
    Plant(String name) {
        orangesProvided = 0;
        orangesProcessed = 0;
        workers = new Drone[WORKERS_PER_PLANT];
        for (int i = 0; i < WORKERS_PER_PLANT; i++) {
            workers[i] = new Drone(this);
        }
    }

    /**
     * Start the plant working. Kicks all the worker drones into action.
     */
    public void startPlant() {
        timeToWork = true;
        // Yell at all the workers to start working
        for (Drone worker : workers) {
            worker.start();
        }
    }

    /**
     * Wait for the workers to stop working.
     */
    public void waitToStop() {
        for (Drone worker : workers) {
            try {
                worker.join();
                System.out.print("S");
            } catch (InterruptedException ex) {
                System.err.println(worker.getName() + " stop malfunction");
            }
        }
    }

    /**
     * Tell the plant to shut down.  Should be followed by waitToStop().
     */
    public void stopPlant() {
        timeToWork = false;
    }

    public int getProvidedOranges() {
        return orangesProvided;
    }

    public int getProcessedOranges() {
        return orangesProcessed;
    }

    public int getBottles() {
        return orangesProcessed / ORANGES_PER_BOTTLE;
    }

    public int getWaste() {
        return orangesProcessed % ORANGES_PER_BOTTLE;
    }
}
