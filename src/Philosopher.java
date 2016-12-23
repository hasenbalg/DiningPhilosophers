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
    private long hungryTime = 0;
    private long numberOfEatingTurns;

    private final int MAXTIME = 10;

    private int id;
    private ArrayList<Chopstick> chopsticks;
    private States status;
    private volatile boolean running;


    public Philosopher(int id, ArrayList<Chopstick> chopsticks) {
        this.id = id;
        this.chopsticks = chopsticks;

        thinkingTimes = new ArrayList<>();
        eatingTimes = new ArrayList<>();
        hungtyTimes = new ArrayList<>();
        running = true;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void run() {
        while(running){
            think();
            eat();
        }
    }


    public void finnish(){
        System.out.println("P" + id + " terminating");
        this.running = false;
    }

    private synchronized void eat() {
        print_img();

        try {
            chopsticks.get(id).acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(print_img() + "P" + id + " got Chopstick " + id);

        try {
            chopsticks.get((id + 1) % chopsticks.size()).acquire();
            System.out.println(print_img() + "P" + id + " got Chopstick " + (id + 1) % chopsticks.size());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.status = States.EATING;
        System.out.println(print_img() + "P" + id + " is " + this.status);
        hungtyTimes.add(System.currentTimeMillis()-hungryTime);
        long eatingTime = (long) (Math.random() * MAXTIME) + 1;
        try {
            Thread.sleep(eatingTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        chopsticks.get(id).release();
        chopsticks.get((id + 1) % chopsticks.size()).release();
        System.out.println(print_img() + "P" + id + " releases Chopstick " + id + " and " + (id + 1) % chopsticks.size());

        eatingTimes.add(eatingTime);
        numberOfEatingTurns++;
    }


    private void think() {
        this.status = States.THINKING;
        System.out.println("P" + id + " is " + this.status);
        long thinkingTime = (long) (Math.random() * MAXTIME) + 1;
        try {
            Thread.sleep(thinkingTime);
        } catch (InterruptedException e) {
            System.err.println("error from philospph thinking" + id);
            e.printStackTrace();
        }
        this.status = States.HUNGRY;
        hungryTime = System.currentTimeMillis();
        System.out.println("P" + id + " is " + this.status);
        thinkingTimes.add(thinkingTime);
    }

    public float avg(ArrayList<Long> l){
        float sum = 0;
        if (l.size() < 1) return 0;
        for (Long t : l) sum += t;
        return sum / (float)l.size();
    }

    public float getAverageThinkingTime() {
        return avg(thinkingTimes);
    }

    public float getAverageEatingTime() {
        return avg(eatingTimes);
    }

    public float getAverageHungryTime() {
        return avg(hungtyTimes);
    }

    public long getNumberOfEatingTurns() {
        return numberOfEatingTurns;
    }


    private synchronized String print_img() {
        String output = "";
        for (Chopstick c : chopsticks){
            output += (c.availablePermits() < 1)?"-": "|";
        }

        //if (output.equals("-----"))System.exit(0);
        return output;
    }

}
