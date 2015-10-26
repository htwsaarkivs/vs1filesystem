package htw.vs1.filesystem.Network;

import htw.vs1.filesystem.FileSystemManger;
import htw.vs1.filesystem.Network.Protocol.Server.SimpleServerProtocol;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by markus on 07.06.15.
 */
public class TCPParallelWorker implements Runnable {


    private static final String THREAD_NAME = "WorkerThread";
    private Socket socket;

    public TCPParallelWorker(Socket socket) {
        //setName(THREAD_NAME);
        this.socket = socket;
    }


    public void stopWorker() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Run - method of the Worker
     */
    public void run() {
        System.out.println("Thread gestartet");
        try {

            new SimpleServerProtocol(socket, FileSystemManger.getInstance().getFileSystem(false)).run();
            socket.close();

        } catch (Exception e) {
            if (FileSystemManger.DEBUG) {
                e.printStackTrace();
            }
        }

        System.out.println("Thread beendet");

    }






}
