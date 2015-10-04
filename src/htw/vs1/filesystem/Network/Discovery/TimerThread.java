package htw.vs1.filesystem.Network.Discovery;

import htw.vs1.filesystem.FileSystemManger;

/**
 * The TimerThread is responsible for triggering
 * periodical tasks, e.g. the broadcaster or the
 * task cleaning up the list of discovered servers.
 *
 * The timer thread is a singleton.
 *
 * Created by Felix on 27.09.2015.
 */
public class TimerThread extends Thread {

    private static final String THREAD_NAME = "TimerThread";

    protected static final long TIMER_INTERVAL = 1000; // 1 seconds

    private static TimerThread INSTANCE = null;

    private boolean isStarted = false;

    private volatile boolean doBroadcast = false;

    private volatile boolean doCleanUp = false;

    private int runningServices = 0;

    private DiscoveryBroadcaster broadcaster;

    public static TimerThread getInstance() {
        if (null == INSTANCE) {
            INSTANCE = new TimerThread();
        }

        return INSTANCE;
    }

    private TimerThread() {
        setName(THREAD_NAME);
    }

    /**
     * Starts a new BroadcasterService, which announces
     * that there is a server running on this port.
     *
     * Caution: only one broadcaster is supported!
     *
     * @param port port of the running Server.
     */
    public void startBroadcaster(int port) {
        broadcaster = new DiscoveryBroadcaster(port);
        doBroadcast = true;
        runningServices++;
        if (!isStarted) {
            start();
        }
    }

    /**
     * Stops the broadcasterService.
     */
    public void stopBroadcaster() {
        doBroadcast = false;
        broadcaster.stop();
        broadcaster = null;
        runningServices--;
        interrupt();
    }

    public void startDiscoveredServerListCleanUp(boolean start) {
        doCleanUp = start;
        if (start) {
            runningServices++;
            if (!isStarted) {
                start();
            }
        } else {
            runningServices--;
            interrupt();
        }
    }

    @Override
    public void interrupt() {
        if (runningServices == 0) {
            super.interrupt();
        }
    }

    @Override
    public synchronized void start() {
        isStarted = true;
        super.start();
    }

    @Override
    public void run() {
        while (doBroadcast || doCleanUp) {
            if (doBroadcast) {
                long start = System.currentTimeMillis();
                triggerBroadcast();
                long stop = System.currentTimeMillis();
                long diff = stop - start;
                System.out.println("\tDauer Broadcast: " + diff + " ms");
            }
            if (doCleanUp) {
                triggerCleanUp();
            }

            try {
                sleep(TIMER_INTERVAL);
            } catch (InterruptedException e) {
                if (FileSystemManger.DEBUG) {
                    e.printStackTrace();
                }
                // stopping all services will interrupt and stops the timer thread.
                //e.printStackTrace();
            }
        }

        // kill thread instance
        INSTANCE = null;
    }

    private void triggerBroadcast() {
        if (broadcaster == null) {
            throw  new IllegalStateException("Broadcaster should not be null!");
        }
        try {
            broadcaster.discovery();
        } catch (InterruptedException e) {
            // Oh broadcasting didn't work, maybe next time...
            if (FileSystemManger.DEBUG) {
                e.printStackTrace();
            }
        }
    }

    private void triggerCleanUp() {
        // delete outdates servers from list
        DiscoveryManager.getInstance().deleteOutdatedEntries();
    }
}
