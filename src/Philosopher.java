import java.util.ArrayList;
import java.util.concurrent.Semaphore;

/**
 * Created by frank on 2016-12-22.
 */
enum States {
    THINKING, HUNGRY, EATING
}

public class Philosopher extends Thread {
    private ArrayList<Long> thinkingTimes;
    private ArrayList<Long> eatingTimes;
    private ArrayList<Long> hungtyTimes;
    private long numberOfEatingTurns;

    private final int MAXTIME = 10;

    private int id;
    private ArrayList<Semaphore> chopsticks;
    private States status;


    public Philosopher(int id, ArrayList<Semaphore> chopsticks) {
        this.id = id;
        this.chopsticks = chopsticks;

        thinkingTimes = new ArrayList<>();
        eatingTimes = new ArrayList<>();
        hungtyTimes = new ArrayList<>();
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void run() {
        try {
            think();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {

            eat();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private synchronized void eat() throws InterruptedException {
        System.out.println("P" + id + " wants to get Chopstick " + id + " and its state is " + chopsticks.get(id).tryAcquire());
        System.out.println("P" + id + " wants to get Chopstick " + (id + 1) % chopsticks.size() + " and its state is " + chopsticks.get((id + 1) % chopsticks.size()).tryAcquire());
        chopsticks.get(id).acquire();
        System.out.println("P" + id + " got Chopstick " + id + " and its state is " +          chopsticks.get(id).tryAcquire());
        System.out.println("P" + id + " got Chopstick " + id);
        chopsticks.get((id + 1) % chopsticks.size()).acquire();
        System.out.println("P" + id + " got Chopstick " + (id + 1) % chopsticks.size());
        this.status = States.EATING;
        System.out.println("P" + id + " is " + this.status);
        long eatingTime = (long) (Math.random() * MAXTIME) + 1;
        System.out.println("eatingTime" + eatingTime);
        Thread.sleep(eatingTime);
        chopsticks.get(id).release();
        chopsticks.get((id + 1) % chopsticks.size()).release();

        eatingTimes.add(eatingTime);
        numberOfEatingTurns ++;
    }

    private void think() throws InterruptedException {
        this.status = States.THINKING;
        System.out.println("P" + id + " is " + this.status);
        long thinkingTime = (long) (Math.random() * MAXTIME) + 1;
        System.out.println("thinkingTime" + thinkingTime);
        Thread.sleep(thinkingTime);
        this.status = States.HUNGRY;
        System.out.println("P" + id + " is " + this.status);
        thinkingTimes.add(thinkingTime);
    }

    public long getAverageThinkingTime() {
        int sum = 0;
        if (thinkingTimes.size() < 1) return 0;
        for(Long t:thinkingTimes) sum += t;
        return sum / numberOfEatingTurns;
    }

    public long getAverageEatingTime() {
        int sum = 0;
        if (eatingTimes.size() < 1) return 0;
        for(Long t:eatingTimes) sum += t;
        return sum / numberOfEatingTurns;
    }

    public long getAverageHungryTime() {
        int sum = 0;
        if (hungtyTimes.size() < 1) return 0;
        for(Long t:hungtyTimes) sum += t;
        return sum / numberOfEatingTurns;
    }

    public long getNumberOfEatingTurns() {
        return numberOfEatingTurns;
    }
}
