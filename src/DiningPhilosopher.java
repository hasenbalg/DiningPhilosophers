import java.util.ArrayList;
import java.util.concurrent.Semaphore;

/**
 * Created by frank on 2016-12-22.
 */
public class DiningPhilosopher {
    private int simulationTime;
    private ArrayList<Philosopher> philosophers;
    private ArrayList<Semaphore> chopsticks;
    private long terminationTime;
    private final int N = 5;

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
            chopsticks.add(new Semaphore(1));
        }

        for (int i = 0; i < N; i++){
            philosophers.add(new Philosopher(i, chopsticks));
        }
    }

    public void start() {
        for(Philosopher p : philosophers) p.start();
        while(System.currentTimeMillis() < terminationTime && deadlockDetected()){

        }
        for(Philosopher p : philosophers) p.interrupt();
    }

    private boolean deadlockDetected() {
        return true;
    }

    public ArrayList<Philosopher> getPhilosophers() {
        return philosophers;
    }
}