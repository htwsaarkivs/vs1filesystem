package htw.vs1.filesystem.Network;

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

    public void run() {
        System.out.println("Thread gestartet");
        try {

            new SimpleProtocol(socket).run();

            System.out.println("IP: "+ socket.getInetAddress());
            System.out.println("Port:" + socket.getPort());
            Thread.sleep(10000);

            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }






}
