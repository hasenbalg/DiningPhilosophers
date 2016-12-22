/**
 * Created by frank on 2016-12-22.
 */
public class Chopstick {
    private boolean is_busy;
    private int id;

    public Chopstick(int id) {
        this.id = id;
        this.is_busy = false;
    }

    public int getId() {
        return id;
    }

    public boolean isIs_busy() {
        return is_busy;
    }

    public void acquire() {
        this.is_busy = true;
    }

    public void release() {
        this.is_busy = false;
    }
}
