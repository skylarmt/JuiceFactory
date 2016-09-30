package name.skylarismy;

/**
 * A child labor drone. It has little education, but is good at making orange
 * juice.
 *
 * TODO: Add code to pay them a penny a day. For now, they get a free orange,
 * since we have a limitless supply.
 */
public class Drone extends Thread {

    private Plant parentplant;

    /**
     * Create a child worker.  Assigns it to a plant.
     * @param plant The plant the worker is assigned to.
     */
    public Drone(Plant plant) {
        this(plant, "Child laborer #" + String.valueOf(Math.round(Math.random() * 1000)));
    }

    /**
     * Create a child worker.  Assigns it to a plant and names it.
     * @param plant The plant the worker is assigned to.
     * @param name The name of the child worker.
     */
    public Drone(Plant plant, String name) {
        parentplant = plant;
        setName(name);
    }

    @Override
    public void run() {
        System.out.print(Thread.currentThread().getName() + " processing oranges\n");
        while (parentplant.timeToWork) {
            // If there are no oranges fetched, go get one and put it in the queue
            synchronized (parentplant.orangequeue) {
                if (parentplant.orangequeue.isEmpty()) {
                    parentplant.orangequeue.add(new Orange());
                    parentplant.orangesProvided++;
                }
                System.out.print(parentplant.orangequeue.size());
            }

            // Do something to an orange
            Orange orange;
            synchronized (parentplant.orangequeue) {
                // Check if there are any oranges to use
                if (parentplant.orangequeue.peek() != null) {
                    orange = parentplant.orangequeue.remove();
                } else {
                    // There aren't, so we'll go back to the top, check again, 
                    // and add one if we still need to
                    continue;
                }
            }
            if (orange.getState() != Orange.State.Processed) {
                orange.runProcess();
                parentplant.orangequeue.add(orange);
            } else {
                parentplant.orangesProcessed++;
            }
        }
    }
}
