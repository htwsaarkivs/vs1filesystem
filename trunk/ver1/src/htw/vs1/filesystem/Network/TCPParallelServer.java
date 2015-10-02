package htw.vs1.filesystem.Network;

import htw.vs1.filesystem.FileSystemManger;
import htw.vs1.filesystem.Network.Discovery.DiscoveryManager;
import htw.vs1.filesystem.Network.Protocol.ServerStatus;
import sun.awt.image.ImageWatched;


import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by markus on 05.06.15.
 */
public class TCPParallelServer extends Thread implements ServerInterface {

    public static final int MAX_NUM_THREADS = 200;
    public static final int DEFAULT_PORT = 4322;
    private static final int DEFAULT_TIMEOUT = 10000;
    public static final String DEFAULT_USER = "A";
    public static final String DEFAULT_PASS = "B";
    private static final String THREAD_NAME = "ServerThread";
    private static TCPParallelServer INSTANCE = null;

    private int port = DEFAULT_PORT;
    private int timeout = DEFAULT_TIMEOUT;

    private volatile boolean running = false;

    private ServerSocket socket;
    private ServerStatus serverStatus = ServerStatus.STOPPED;
    private List<ServerStatusObserver> serverStatusObservers = new LinkedList<>();

    protected ExecutorService workerPool = Executors.newFixedThreadPool(MAX_NUM_THREADS);
    protected List<TCPParallelWorker> workerList = new LinkedList<TCPParallelWorker>();

    public static TCPParallelServer getInstance(int port) {
        if (INSTANCE == null) {
            INSTANCE = new TCPParallelServer(port);
        }
        return  INSTANCE;
    }

    private TCPParallelServer(int port) {
        setName(THREAD_NAME);
        this.port = port;
    }

    /**
     * Start the parallel Server.
     */
    public void run() {
        try
        {

            DiscoveryManager.getInstance().startAnnouncement(port);

            // Erzeugen der Socket/binden an Port/Wartestellung
            socket = new ServerSocket(port);
            System.out.printf("Warten auf Verbindungen (IP: %s, Port: %s) ...\n",
                    InetAddress.getLocalHost().getHostAddress(),
                    String.valueOf(port));

            running = true;
            changeServerStatus(ServerStatus.RUNNING);
            while (running)
            {
                try {
                    Socket client = socket.accept();
                    //Connection Timeout
                    client.setSoTimeout(600000);
                    System.out.println("Neuer Client verbunden: " + client.getInetAddress().toString());
                    TCPParallelWorker worker = new TCPParallelWorker(client);
                    workerList.add(worker);
                    workerPool.execute(worker);
                } catch (IOException e) {
                    if (FileSystemManger.DEBUG) {
                        e.printStackTrace();
                    }
                    // Thread may be interrupted
                    /*if (FileSystemManger.DEBUG_MODE) {
                        e.printStackTrace();
                    }*/
                }

            }

        }
        catch (Exception e)
        {
            changeServerStatus(ServerStatus.ERROR);
            if (FileSystemManger.DEBUG) {
                e.printStackTrace();
            }
            /*if (FileSystemManger.DEBUG_MODE) {
                e.printStackTrace();
            }*/
        }

        changeServerStatus(ServerStatus.STOPPED);
        // Server is stopped now, so we stop the discovery announcement, too.
        DiscoveryManager.getInstance().stopAnnouncement();
        INSTANCE = null;
    }

    public boolean isRunning() {
        return running;
    }

    public void stopServer() {
        if (!running) {
            return;
        }

        running = false;
        workerPool.shutdown();
        for(TCPParallelWorker worker : workerList) {
            worker.stopWorker();
        }

        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Gets the port the server is
     * listening on.
     *
     * @return
     */
    public int getPort() {
        return this.port;
    }

    public void attachServerStatusObserver(ServerStatusObserver o) {
        serverStatusObservers.add(o);
    }

    public void detachServerStatusObserver(ServerStatusObserver o) {
        serverStatusObservers.remove(o);
    }

    private void changeServerStatus(ServerStatus newStatus) {
        this.serverStatus = newStatus;
        for (ServerStatusObserver observer : serverStatusObservers) {
            observer.serverStatusChanged(newStatus);
        }
    }


    public ServerStatus getServerStatus() {
        return serverStatus;
    }
}
