package htw.vs1.filesystem.Trials.Thread;


/**
 * Created by Felix on 16.06.2015.
 */
public class ThreadSafety {

    private Calculator calculator = new Calculator();

    private int resultThread1 = 0;

    private int resultThread2 = 0;

    private void setResult(int thread, int result) {
        switch (thread) {
            case 1:
                resultThread1 = result;
                break;
            case 2:
                resultThread2 = result;
                break;
        }
    }

    public void start() throws InterruptedException {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                calculator.addThreadSafe(3);
                setResult(1, calculator.getResult());
            }
        });
        t1.start();

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                calculator.addThreadSafe(7);
                setResult(2, calculator.getResult());
            }
        });
        t2.start();


        // wait for threads to finish
        t1.join();
        t2.join();

        System.out.println("Result Thread1: " + resultThread1);
        System.out.println("Result Thread2: " + resultThread2);
    }

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        (new ThreadSafety()).start();
        long end = System.currentTimeMillis();

        long diff = end - start;

        System.out.println("Dauer: " + (diff / 1000) + " Sekunden.");
    }

}
