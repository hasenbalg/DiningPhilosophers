import java.util.ArrayList;
import java.util.concurrent.Semaphore;

/**
 * Created by frank on 2016-12-22.
 */
enum States {
    THINKING, HUNGRY, EATING
}

public class Philosopher extends Thread {
    private long averageThinkingTime;
    private long averageEatingTime;
    private long averageHungryTime;
    private long numberOfEatingTurns;

    private final int MAXTIME = 10;

    private int id;
    private ArrayList<Semaphore> chopsticks;
    private States status;

    public Philosopher(int id, ArrayList<Semaphore> chopsticks) {
        this.id = id;
        this.chopsticks = chopsticks;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void run() {
        super.run();
        try {
            think();
            eat();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private synchronized void eat() throws InterruptedException {
        System.out.println("P" + id + " wants to get Chopstick " + id + " and its state is " + chopsticks.get(id).tryAcquire());
        chopsticks.get(id).acquire();
        System.out.println("P" + id + " got Chopstick " + id + " and its state is " +          chopsticks.get(id).tryAcquire());
        System.out.println("P" + id + " got Chopstick " + id);
        chopsticks.get((id + 1) % chopsticks.size()).acquire();
        System.out.println("P" + id + " got Chopstick " + (id + 1) % chopsticks.size());
        this.status = States.EATING;
        System.out.println("P" + id + " is " + this.status);
        this.sleep((long) (Math.random() * MAXTIME) + 1);
        chopsticks.get(id).release();
        chopsticks.get((id + 1) % chopsticks.size()).release();
    }

    private void think() throws InterruptedException {
        this.status = States.THINKING;
        System.out.println("P" + id + " is " + this.status);
        this.sleep((long) (Math.random() * MAXTIME) + 1);
        this.status = States.HUNGRY;
        System.out.println("P" + id + " is " + this.status);
    }

    public long getAverageThinkingTime() {
        return averageThinkingTime;
    }

    public long getAverageEatingTime() {
        return averageEatingTime;
    }

    public long getAverageHungryTime() {
        return averageHungryTime;
    }

    public long getNumberOfEatingTurns() {
        return numberOfEatingTurns;
    }
}
