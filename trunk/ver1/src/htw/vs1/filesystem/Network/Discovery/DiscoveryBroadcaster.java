package htw.vs1.filesystem.Network.Discovery;

import java.io.IOException;
import java.net.*;

/**
 * Created by Felix on 20.09.2015.
 */
public class DiscoveryBroadcaster extends Thread {

    private static final long BROADCAST_INTERVAL = 5000;

    private volatile boolean running = true;

    public static void main(String[] args) {
        DiscoveryBroadcaster broadcaster = new DiscoveryBroadcaster();
        broadcaster.run();
    }

    @Override
    public void run() {
        while (running) {
            try {
                sendBroadcast();
            } catch (SocketException e) {
                e.printStackTrace();
            }
            try {
                sleep(BROADCAST_INTERVAL);
            } catch (InterruptedException e) {
                running = false;
            }
        }
    }

    private void sendBroadcast() throws SocketException {
        DatagramSocket socket = new DatagramSocket();
        socket.setBroadcast(true);

        byte[] data = "Ich bin am Start!".getBytes();

        try {
            DatagramPacket packet = new DatagramPacket(data, data.length, InetAddress.getByName("255.255.255.255"), 4322);
            socket.send(packet);
            System.out.println("Packet gesendet: " + data.toString());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //socket.close();
        }
    }
}
