import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class SushiBar {

    //SushiBar settings
    private static int waitingAreaCapacity = 20;
    private static int waitressCount = 10;
    private static int duration = 4;
    public static int maxOrder = 10;
    public static int waitressWait = 50; // Used to calculate the time the waitress spends before taking the order
    public static int customerWait = 3000; // Used to calculate the time the customer uses eating
    public static int doorWait = 100; // Used to calculate the interval at which the door tries to create a customer
    public static boolean isOpen = true;

    //Creating log file
    private static File log;
    private static String path = "./";

    //Variables related to statistics
    public static SynchronizedInteger customerCounter;
    public static SynchronizedInteger servedOrders;
    public static SynchronizedInteger takeawayOrders;
    public static SynchronizedInteger totalOrders;




    public static void main(String[] args) {
        log = new File(path + "log.txt");
        // starts clock. Sushi bar closes after duration time
        new Clock(duration);
        new SushiBar();
    }

    public SushiBar() {
        //Initializing shared variables for counting number of orders
        customerCounter = new SynchronizedInteger(0);
        totalOrders = new SynchronizedInteger(0);
        servedOrders = new SynchronizedInteger(0);
        takeawayOrders = new SynchronizedInteger(0);



        // waiting area with a capacity
        WaitingArea waitingArea = new WaitingArea(waitingAreaCapacity);

        // thread list
        List<Thread> threads = new ArrayList<>();

        // door thread init and start
        Thread door = new Thread(new Door(waitingArea), "Door");
        door.start();
        threads.add(door);

        // waitress threads init and start
        for (int i = 1; i <= waitressCount; i++) {
            Thread newWaitress = new Thread(new Waitress(waitingArea), "Waitress #" + i);
            newWaitress.start();
            threads.add(newWaitress);
        }


        // wait until all threads are finished
        for (Thread waitress : threads) {
            try {
                waitress.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        write("________  Threads finished ________");
        stats();
    }


    public void stats() {
        write(" ***************  STATISTICS ***************");
        write(" Total number of orders: " + totalOrders.get());
        write(" Number of take away orders: " + takeawayOrders.get());
        write(" Number of orders eaten in bar: " + servedOrders.get());
    }

    //Writes actions in the log file and console
    public static void write(String str) {
        try {
            FileWriter fw = new FileWriter(log.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(Clock.getTime() + ", " + str + "\n");
            bw.close();
            System.out.println(Clock.getTime() + ", " + str);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
