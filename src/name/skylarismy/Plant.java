package name.skylarismy;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class Plant {

    public final int ORANGES_PER_BOTTLE = 3;
    public final int WORKERS_PER_PLANT = 2;

    public int orangesProvided;
    public int orangesProcessed;
    protected volatile boolean timeToWork;

    // A queue of oranges, so the workers can pass them around as needed
    public final Queue<Orange> orangequeue = new ArrayBlockingQueue<>(ORANGES_PER_BOTTLE * WORKERS_PER_PLANT);

    private Drone[] workers;

    Plant() {
        this("Plant");
    }

    Plant(String name) {
        orangesProvided = 0;
        orangesProcessed = 0;
        workers = new Drone[WORKERS_PER_PLANT];
        for (int i = 0; i < WORKERS_PER_PLANT; i++) {
            workers[i] = new Drone(this);
        }
    }

    public void startPlant() {
        timeToWork = true;
        // Yell at all the workers to start working
        for (Drone worker : workers) {
            worker.start();
        }
    }

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
