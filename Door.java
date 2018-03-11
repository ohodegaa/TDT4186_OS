/**
 * This class implements the Door component of the sushi bar assignment
 * The Door corresponds to the Producer in the producer/consumer problem
 */
public class Door implements Runnable {

    /**
     * Creates a new Door. Make sure to save the
     *
     * @param waitingArea   The customer queue waiting for a seat
     */

    private WaitingArea waitingArea;

    public Door(WaitingArea waitingArea) {
        this.waitingArea = waitingArea;
    }

    /**
     * This method will run when the door thread is created (and started)
     * The method should create customers at random intervals and try to put them in the customerQueue
     */
    @Override
    public void run() {
        // TODO: add random production of customers

        while (SushiBar.isOpen) {
            Customer nextCustomer = new Customer();     // creates new customer
            // logging
            String msg = Thread.currentThread().getName() + ": Customer " + nextCustomer.getCustomerID() + " is now created";
            SushiBar.write(msg);
            waitingArea.enter(nextCustomer);            // new customer enters the waiting area
            SushiBar.customerCounter.increment();       // add one to customer counter

            try {
                // wait an amount of time before creating a new customer
                Thread.sleep(SushiBar.doorWait);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
