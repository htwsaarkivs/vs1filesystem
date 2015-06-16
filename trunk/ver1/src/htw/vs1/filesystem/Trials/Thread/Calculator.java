package htw.vs1.filesystem.Trials.Thread;

/**
 * Created by Felix on 16.06.2015.
 */
public class Calculator {

    private Object lock = new Object();

    private int result;

    public void add(int value) {
        System.out.println(Thread.currentThread().getName() + ", " +
                "Calculator: starting add process with value of " + value + ". This will take 5 seconds.");
        int temp = result;
        try {
            Thread.sleep(5000); // sleeps 5 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        result = temp + value;
    }

    public void addThreadSafe(int value, boolean threadSafe) {
        if (threadSafe) {
            synchronized (lock) {
                add(value);
            }
        } else {
            add(value);
        }
    }

    public int getResult() {
        return result;
    }
}
