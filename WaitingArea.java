import java.util.LinkedList;


/**
 * This class implements a waiting area used as the bounded buffer, in the producer/consumer problem.
 */
public class WaitingArea {

    /**
     * Creates a new waiting area.
     * areaSize decides how many people can be waiting at the same time (how large the shared buffer is)
     *
     * @param size The maximum number of Customers that can be waiting.
     */

    private int areaSize;
    private LinkedList<Customer> waitingArea;

    public WaitingArea(int size) {
        areaSize = size;
        waitingArea = new LinkedList<>();
    }

    /**
     * This method should put the customer into the waitingArea
     *
     * @param customer A customer created by Door, trying to enter the waiting area
     */

    // Produce. Should be used by the producer (door)
    public synchronized void enter(Customer customer) throws InterruptedException {

        while (full()) {
            // wait until there are room for another customer in the waiting area
            this.wait();
        }

        waitingArea.add(customer);

        // logging
        String msg = Thread.currentThread().getName() + ": Customer " + customer.getCustomerID() + " is now waiting";
        SushiBar.write(msg);

        notifyAll();
    }

    /**
     * @return The customer that is first in line.
     */

    // Consume. Should be used by the consumers (waitress)
    public synchronized Customer next() throws InterruptedException {


        // wait until there are customers in the waiting area
        while (empty()) {
            wait();
        }

        Customer nextCustomer = waitingArea.poll();

        // logging
        String msg = Thread.currentThread().getName() + ": Customer " + nextCustomer.getCustomerID() + " is now fetched and ready for ordering food";
        SushiBar.write(msg);

        this.notifyAll();


        return nextCustomer;
    }


    public boolean full() {
        return waitingArea.size() >= areaSize;
    }

    public synchronized boolean empty() {
        return waitingArea.size() == 0;
    }


}
