package name.skylarismy;

public class Drone extends Thread {

    Plant parentplant;

    public Drone(Plant plant) {
        this(plant, "Child laborer #" + String.valueOf(Math.round(Math.random() * 1000)));
    }

    public Drone(Plant plant, String name) {
        parentplant = plant;
        setName(name);
    }

    public void processEntireOrange(Orange o) {
        while (o.getState() != Orange.State.Bottled) {
            o.runProcess();
        }
        parentplant.orangesProcessed++;
    }

    @Override
    public void run() {
        System.out.print(Thread.currentThread().getName() + " processing oranges\n");
        while (parentplant.timeToWork) {
            // If there are no oranges fetched, go get one and put it in the queue
            synchronized (parentplant.orangequeue) {
                if (parentplant.orangequeue.isEmpty()) {
                    parentplant.orangequeue.add(new Orange());
                }
            }
            parentplant.orangesProvided++;

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
