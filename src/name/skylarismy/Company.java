package name.skylarismy;

public class Company {

    private static final int NUM_PLANTS = 2;

    // How long do we want to run the juice processing
    public static final long PROCESSING_TIME = 5 * 1000;

    private static void delay(long time, String errMsg) {
        long sleepTime = Math.max(1, time);
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            System.err.println(errMsg);
        }
    }

    public static void main(String[] args) {
        // Startup the plants
        Plant[] plants = new Plant[NUM_PLANTS];
        for (int i = 0; i < NUM_PLANTS; i++) {
            plants[i] = new Plant("Plant #" + i);
            plants[i].startPlant();
        }

        // Give the plants time to do work
        delay(PROCESSING_TIME, "Plant malfunction");

        // Stop the plant, and wait for it to shutdown
        System.out.println("\nShutting down plants...");
        for (Plant p : plants) {
            p.stopPlant();
        }
        for (Plant p : plants) {
            p.waitToStop();
        }

        // Summarize the results
        int totalProvided = 0;
        int totalProcessed = 0;
        int totalBottles = 0;
        int totalWasted = 0;
        for (Plant p : plants) {
            totalProvided += p.getProvidedOranges();
            totalProcessed += p.getProcessedOranges();
            totalBottles += p.getBottles();
            totalWasted += p.getWaste();
        }
        // Leading newline to deal with wrapping uglyness
        System.out.println("\nTotal provided/processed = " + totalProvided + "/" + totalProcessed);
        System.out.println("Created " + totalBottles
                + " bottles, wasted " + totalWasted + " oranges");
    }
}
