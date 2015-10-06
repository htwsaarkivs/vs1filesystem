package htw.vs1.scrapyard.Trials.Thread;


import htw.vs1.filesystem.FileSystemManger;

/**
 * Created by Felix on 16.06.2015.
 */
public class ThreadSafety {

    private Calculator calculator = new Calculator();

    public void start(final boolean threadSafe) throws InterruptedException {
        System.out.println("Initial calculator value " + calculator.getResult());

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                calculator.addThreadSafe(3, threadSafe);
            }
        }, "Thread1");
        t1.start();

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    if (FileSystemManger.DEBUG) {
                        e.printStackTrace();
                    }
                }
                calculator.addThreadSafe(7, threadSafe);
            }
        }, "Thread2");
        t2.start();


        // wait for threads to finish
        t1.join();
        t2.join();

        System.out.println("Result: " + calculator.getResult());
    }

    public static void demonstrate(boolean threadSafe) throws InterruptedException {
        long start = System.currentTimeMillis();
        (new ThreadSafety()).start(threadSafe);
        long end = System.currentTimeMillis();

        long diff = end - start;

        System.out.println("Dauer: " + (diff / 1000) + " Sekunden.");
    }

    public static void main(String[] args) throws InterruptedException {

        System.out.println("Starting two concurrently thread non-thread-safe:");
        demonstrate(false);

        System.out.println("--------------------------------------------------");

        System.out.println("Starting two concurrently thread non-thread-safe:");
        demonstrate(true);

    }

}
