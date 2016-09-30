package name.skylarismy;

/**
 * An orange. Has several states of existence. Not to be confused with Donald
 * Trump.
 */
public class Orange {

    /**
     * The state of the orange. Unlike the State of the Union, it's not simply
     * "all gone to hell".
     */
    public enum State {
        Fetched(15),
        Peeled(38),
        Squeezed(29),
        Bottled(17),
        Processed(1);

        private static final int finalIndex = State.values().length - 1;

        final int timeToComplete;

        State(int timeToComplete) {
            this.timeToComplete = timeToComplete;
        }

        State getNext() {
            int currIndex = this.ordinal();
            if (currIndex >= finalIndex) {
                throw new IllegalStateException("Already at final state");
            }
            return State.values()[currIndex + 1];
        }
    }

    private State state;

    public Orange() {
        state = State.Fetched;
        doWork();
    }

    /**
     * Get the orange's state.
     *
     * @return the state.
     */
    public State getState() {
        return state;
    }

    /**
     * Do something to the orange.
     *
     * @throws IllegalStateException if the orange is completely done (the state
     * is processed).
     */
    public void runProcess() {
        // Don't attempt to process an already completed orange
        if (state == State.Processed) {
            throw new IllegalStateException("This orange has already been processed");
        }
        doWork();
        state = state.getNext();
    }

    /**
     * I'm gonna juice an orange, and the orange is gonna pay for it. It doesn't
     * know it will yet, but it will.
     */
    private void doWork() {
        // Sleep for the amount of time necessary to do the work
        try {
            Thread.sleep(state.timeToComplete);
        } catch (InterruptedException e) {
            System.err.println("Incomplete orange processing, juice may be bad");
        }
    }
}
