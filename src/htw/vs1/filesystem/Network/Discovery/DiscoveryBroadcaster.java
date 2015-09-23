package htw.vs1.filesystem.Network.Discovery;

import java.io.IOException;
import java.net.*;
import java.util.Enumeration;

/**
 * Created by Felix on 20.09.2015.
 */
public class DiscoveryBroadcaster extends DiscoveryThread {

    protected static final long BROADCAST_INTERVAL = 5000;
    private static final String THREAD_NAME = "BroadcasterThread";

    public static void main(String[] args) throws InterruptedException {
        DiscoveryBroadcaster broadcaster = new DiscoveryBroadcaster(4322);
        DiscoveryListener listener = new DiscoveryListener();
        broadcaster.start();
        listener.start();

        broadcaster.join();
        listener.join();
    }

    private int serverPort;

    public DiscoveryBroadcaster(int port) {
        setName(THREAD_NAME);
        this.serverPort = port;
    }

    @Override
    protected void discovery(DatagramSocket socket) throws InterruptedException {
        try {
            sendBroadcastToAll(socket);

            // delete outdates servers from list
            DiscoveryManager.getInstance().deleteOutdatedEntries();
        } catch (SocketException e) {
            e.printStackTrace();
        }

        sleep(BROADCAST_INTERVAL);
    }

    @Override
    protected DatagramSocket getDatagramSocket() throws SocketException {
        return new DatagramSocket();
    }

    private void sendBroadcastToAll(DatagramSocket socket) throws SocketException {
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()) {
            NetworkInterface networkInterface = interfaces.nextElement();
            if (networkInterface.isLoopback() || !networkInterface.isUp()) {
                continue;
            }

            for (InterfaceAddress address : networkInterface.getInterfaceAddresses()) {
                if (address.getBroadcast() == null) continue;
                sendBroadcast(address.getBroadcast(), socket);
            }

        }
    }

    private void sendBroadcast(InetAddress address, DatagramSocket socket) throws SocketException {
        socket.setBroadcast(true);

        byte[] data = String.valueOf(serverPort).getBytes();

        try {
            DatagramPacket packet = new DatagramPacket(data, data.length, address, DiscoveryManager.DISCOVERY_PORT);
            socket.send(packet);
            //System.out.println("Packet gesendet: " + data.toString());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
