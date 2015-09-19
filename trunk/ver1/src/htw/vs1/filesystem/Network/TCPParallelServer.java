package htw.vs1.filesystem.Network;

import htw.vs1.filesystem.FileSystem.virtual.LocalFolder;


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

    private static TCPParallelServer INSTANCE = null;

    private int port = DEFAULT_PORT;
    private int timeout = DEFAULT_TIMEOUT;

    private volatile boolean running = true;

    /**
     * Start the server without the user dialog.
     */
    private boolean startSingleServer = false;

    public static String path = "/Users/markus/Documents/HTW/test-fs";


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
        this.port = port;
    }

    /**
     * Start the parallel Server.
     */
    public void run() {
        try
        {

            if (startSingleServer) {
                //Intialisierung des Filesystems
                LocalFolder.setRootDirectory(path);
            }

            // Erzeugen der Socket/binden an Port/Wartestellung
            ServerSocket socket = new ServerSocket(port);

            while (running)
            {
                System.out.printf("Warten auf Verbindungen (IP: %s, Port: %s) ...\n",
                        InetAddress.getLocalHost().getHostAddress(),
                        String.valueOf(port));
                Socket client = socket.accept();


                (new TCPParallelWorker(client)).start();
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }

    public void stopServer() {
        this.running = false;
    }





}
