/**
 * This class implements a customer, which is used for holding data and update the statistics
 *
 */


import java.util.Random;

/**
 * Note:
 * - each customer can have several orders
 * - orders can be take away orders, and
 * - orders can be eaten orders, which is orders eaten at the bar
 */


public class Customer {


    private static int counter = 0;

    private int id;

    private Random random;
    /**
     *  Creates a new Customer.
     *  Each customer should be given a unique ID
     */
    public Customer() {
        this.id = SushiBar.customerCounter.get();
        SushiBar.customerCounter.increment();       // add one to customer counter
        random = new Random();
    }


    /**
     * Here you should implement the functionality for ordering food as described in the assignment.
     */
    public synchronized void order(){

        // random number of orders
        int numberOfOrders = random.nextInt();

        // random number of total orders are eaten
        int eatenOrders = random.nextInt(numberOfOrders);

        // and the rest is take away orders
        int takeAwayOrders = numberOfOrders - eatenOrders;

        // update statistics
        SushiBar.totalOrders.add(numberOfOrders);
        SushiBar.servedOrders.add(eatenOrders);
        SushiBar.takeawayOrders.add(takeAwayOrders);

        // logging
        String msg = Thread.currentThread().getName() + ": Customer " + this.getCustomerID() + " is now eating";
        SushiBar.write(msg);
        try {
            // customer is eating
            Thread.sleep(SushiBar.customerWait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @return Should return the customerID
     */
    public int getCustomerID() {
        return this.id;
    }

    // Add more methods as you see fit
}


