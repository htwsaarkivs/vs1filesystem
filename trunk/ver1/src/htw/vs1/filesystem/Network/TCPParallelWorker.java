package htw.vs1.filesystem.Network;

import htw.vs1.filesystem.FileSystem.virtual.FileSystem;
import htw.vs1.filesystem.Network.Protocol.Server.SimpleServerProtocol;

import java.net.Socket;

/**
 * Created by markus on 07.06.15.
 */
public class TCPParallelWorker extends Thread {


    private static final String THREAD_NAME = "WorkerThread";
    private Socket socket;

    public TCPParallelWorker(Socket socket) {
        setName(THREAD_NAME);
        this.socket = socket;
    }

    /**
     * Run - method of the Worker
     */
    public void run() {
        System.out.println("Thread gestartet");
        try {

            new SimpleServerProtocol(socket, new FileSystem()).run();
            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }






}
