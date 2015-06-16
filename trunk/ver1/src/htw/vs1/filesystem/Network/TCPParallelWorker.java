package htw.vs1.filesystem.Network;

import htw.vs1.filesystem.FileSystem.FileSystem;
import htw.vs1.filesystem.FileSystem.LocalFolder;
import htw.vs1.filesystem.Network.Protocol.SimpleProtocol;

import java.net.Socket;

/**
 * Created by markus on 07.06.15.
 */
public class TCPParallelWorker extends Thread {


    private Socket socket;

    public TCPParallelWorker(Socket socket) {
        this.socket = socket;
    }

    /**
     * Run - method of he Worker
     */
    public void run() {
        System.out.println("Thread gestartet");
        try {

            new SimpleProtocol(socket, new FileSystem(LocalFolder.getRootFolder())).run();
            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }






}
