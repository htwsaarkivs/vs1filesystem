package htw.vs1.filesystem.Network.Discovery;

import htw.vs1.filesystem.Trials.Thread.FileSystemManager;

import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Created by Felix on 20.09.2015.
 */
public abstract class DiscoveryThread extends Thread {

    protected volatile boolean running = true;

    private DatagramSocket socket;

    protected abstract void discovery(DatagramSocket socket) throws InterruptedException;

    protected abstract DatagramSocket getDatagramSocket() throws SocketException;

    @Override
    public void run() {
        try {
            socket = getDatagramSocket();

            while (running) {
                try {
                    discovery(socket);
                } catch (InterruptedException e) {
                    if (FileSystemManager.DEBUG) {
                        e.printStackTrace();
                    }
                    // the thread may be interrupted by closing the socket.
                }
            }

            if (null != socket) {
                socket.close();
            }

        } catch (SocketException e) {
            if (FileSystemManager.DEBUG) {
                e.printStackTrace();
            }
        }
    }

    public void stopDiscoveryThread() {
        running = false;
        if (null != socket && !socket.isClosed()) {
            socket.close();
        }
    }
}
