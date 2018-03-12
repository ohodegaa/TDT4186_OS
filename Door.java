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

        while (SushiBar.isOpen) {


            Customer nextCustomer = new Customer();     // creates new customer
            try {
                waitingArea.enter(nextCustomer);            // new customer enters the waiting area
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // logging
            String msg = Thread.currentThread().getName() + ": Customer " + nextCustomer.getCustomerID() + " is now created";
            SushiBar.write(msg);


            try {
                // time before new customer enters
                Thread.sleep(SushiBar.doorWait);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }

        // sushi bar closed
        SushiBar.write("***** NO MORE CUSTOMERS - THE SHOP IS NOW CLOSED. *****");
    }


}
