import java.util.ArrayList;

/**
 * Created by frank on 2016-12-23.
 */
public class DeadlockDetecter extends Thread{
    private final ArrayList<Philosopher> philosophers;
    private final ArrayList<Chopstick> chopsticks;
    public volatile boolean running = true;


    public DeadlockDetecter(ArrayList<Chopstick> chopsticks, ArrayList<Philosopher> philosophers) {
        this.chopsticks = chopsticks;
        this.philosophers = philosophers;
    }

    @Override
    public void run() {
        while(running){
            deadlockDetected();
        }
    }

    public void finnish() {
        this.running = false;
    }

    private synchronized boolean deadlockDetected() {
        int sum = 0;
        for (Chopstick c : chopsticks){
            if (c.	availablePermits() < 1){
                sum++;
            }
        }
        if (sum == chopsticks.size()){


            for (Philosopher p : philosophers) p.finnish();
            finnish();
            for (Philosopher p : philosophers){
                try {
                    p.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println();
            System.out.println("DEADLOCK!!!!!!!!");
            System.out.println();

            return true;
        }
        return false;
    }
}
