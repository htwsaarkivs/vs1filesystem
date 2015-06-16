package htw.vs1.filesystem.Trials.Thread;

/**
 * Created by Felix on 16.06.2015.
 */
public class Calculator {

    private Object lock = new Object();

    private int result;

    public void add(int value) {
        int temp = result;
        try {
            Thread.sleep(5000); // sleeps 5 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        result = temp + value;
    }

    public void addThreadSafe(int value) {
        synchronized (lock) {
            add(value);
        }
    }

    public int getResult() {
        return result;
    }
}
