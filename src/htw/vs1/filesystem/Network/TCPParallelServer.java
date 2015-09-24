package htw.vs1.filesystem.Network;

import htw.vs1.filesystem.FileSystem.virtual.LocalFolder;
import htw.vs1.filesystem.Network.Discovery.DiscoveryManager;


import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by markus on 05.06.15.
 */
public class TCPParallelServer extends Thread implements ServerInterface {

    public static final int DEFAULT_PORT = 4322;
    private static final int DEFAULT_TIMEOUT = 10000;

    public static final String DEFAULT_USER = "A";
    public static final String DEFAULT_PASS = "B";

    private static final String THREAD_NAME = "ServerThread";

    private static TCPParallelServer INSTANCE = null;

    private int port = DEFAULT_PORT;
    private int timeout = DEFAULT_TIMEOUT;

    private volatile boolean running = false;

    /**
     * Start the server without the user dialog.
     */
    private boolean startSingleServer = false;

    public static String path = "/Users/markus/Documents/HTW/test-fs";
    private ServerSocket socket;


    public static void main(String[] args) {
        //TODO: Maybe get rid of this altogether...
        //if (true) throw new RuntimeException("This seems to be broken :/");
        if (args.length != 1) {
            System.out.println("Usage: [path]");
            return;
        }
        path = args[0];
        getInstance().startSingleServer = true;
        getInstance().run();

    }

    public static TCPParallelServer getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TCPParallelServer(DEFAULT_PORT);
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
            if (startSingleServer) {
                //Intialisierung des Filesystems
                LocalFolder.setRootDirectory(path);
            }

            // Erzeugen der Socket/binden an Port/Wartestellung
            socket = new ServerSocket(port);
            System.out.printf("Warten auf Verbindungen (IP: %s, Port: %s) ...\n",
                    InetAddress.getLocalHost().getHostAddress(),
                    String.valueOf(port));

            running = true;
            while (running)
            {
                try {
                    Socket client = socket.accept();
                    System.out.println("Neuer Client verbunden: "+client.getInetAddress().toString());
                    (new TCPParallelWorker(client)).start();
                } catch (IOException e) {
                    // Thread may be interrupted
                }

            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        // Server is stopped now, so we stop the discovery announcement, too.
        DiscoveryManager.getInstance().stopAnnouncement(port);
        INSTANCE = null;
    }

    public void stopServer() {
        if (!running) {
            return;
        }
        this.running = false;
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



}
