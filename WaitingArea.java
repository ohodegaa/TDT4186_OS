import java.text.MessageFormat;
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
    public synchronized void enter(Customer customer) {
        try {
            // wait until there are room for another customer in the waiting area
            while (full()) {
                wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        waitingArea.add(customer);


        String msg = "Thread {0}: customer \"{1}\" entered the waiting area";
        Object[] args = {Thread.currentThread().getName(), customer.getCustomerID()};
        printNice(msg, args);

        notify();
    }

    /**
     * @return The customer that is first in line.
     */

    // Consume. Should be used by the consumers (waitress)
    public synchronized Customer next() {


        try {
            // wait until there are customers in the waiting area
            while (empty()) {
                wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Customer customer = waitingArea.poll();
        String msg = "Thread {0}: customer \"{1}\" left the waiting area";
        Object[] args = {Thread.currentThread().getName(), customer.getCustomerID()};
        printNice(msg, args);

        notify();
        return customer;
    }

    private boolean full() {
        return waitingArea.size() >= areaSize;
    }

    private boolean empty() {
        return waitingArea.size() == 0;
    }

    private void printNice(String message, Object[] args) {
        MessageFormat mf = new MessageFormat(message);
        System.out.println(mf.format(args));
    }


}
