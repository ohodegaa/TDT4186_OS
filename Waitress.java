/**
 * This class implements the consumer part of the producer/consumer problem.
 * One waitress instance corresponds to one consumer.
 */
public class Waitress implements Runnable {

    /**
     * Creates a new waitress. Make sure to save the parameter in the class
     *
     * @param waitingArea The waiting area for customers
     */


    private WaitingArea waitingArea;

    public Waitress(WaitingArea waitingArea) {
        this.waitingArea = waitingArea;
    }

    /**
     * This is the code that will run when a new thread is
     * created for this instance
     */
    @Override
    public void run() {
        while (SushiBar.isOpen) {


            Customer nextCustomer = null;
            try {
                nextCustomer = waitingArea.next();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                // wait an amount of time before taking an order
                Thread.sleep(SushiBar.waitressWait);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            assert nextCustomer != null;
            nextCustomer.orderAndEat();

        }


    }


}
