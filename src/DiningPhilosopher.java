import java.util.ArrayList;
import java.util.concurrent.Semaphore;

/**
 * Created by frank on 2016-12-22.
 */
public class DiningPhilosopher {
    private int simulationTime;
    private ArrayList<Philosopher> philosophers;
    private ArrayList<Chopstick> chopsticks;
    private long terminationTime;
    private final int N = 5;
    DeadlockDetecter deadlockDetecter;


    public DiningPhilosopher(){

    }

    public void setSimulationTime(int simulationTime) {
        this.simulationTime = simulationTime;
    }

    public void initialize() {
        terminationTime = System.currentTimeMillis() + simulationTime;
        chopsticks = new ArrayList<>();
        philosophers = new ArrayList<>();

        for (int i = 0; i < N; i++){
            chopsticks.add(new Chopstick(i));
        }

        for (int i = 0; i < N; i++){
            philosophers.add(new Philosopher(i, chopsticks));
        }

        deadlockDetecter = new DeadlockDetecter(chopsticks, philosophers);

    }

    public void start() {
        for(Philosopher p : philosophers) p.start();
        System.err.println("philosophers started");
        deadlockDetecter.start();
        try {
            Thread.sleep(simulationTime);
        } catch (InterruptedException e) {
            System.err.println("fuck");
            e.printStackTrace();
        }

        for(Philosopher p : philosophers) p.finnish();
        deadlockDetecter.finnish();
    }



    public ArrayList<Philosopher> getPhilosophers() {
        return philosophers;
    }
}
