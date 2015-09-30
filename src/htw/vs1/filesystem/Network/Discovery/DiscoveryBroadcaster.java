package htw.vs1.filesystem.Network.Discovery;

import java.io.IOException;
import java.net.*;
import java.util.Enumeration;

/**
 * The DiscoveryBroadcaster is responsible for announcing
 * that there is a server running on the given port.
 *
 * Created by Felix on 20.09.2015.
 */
public class DiscoveryBroadcaster {

    private int serverPort;

    private DatagramSocket socket;

    public DiscoveryBroadcaster(int port) {
        this.serverPort = port;
    }

    public void discovery() throws InterruptedException {
        try {
            sendBroadcastToAll(getDatagramSocket());
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        if (socket == null) {
            return;
        }

        socket.close();
        socket = null;
    }

    protected DatagramSocket getDatagramSocket() throws SocketException {
        if (socket == null) {
            socket = new DatagramSocket();
        }
        return socket;
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
        try {
            sendBroadcast(InetAddress.getByName("224.0.0.1"), socket);
        } catch (UnknownHostException e) {
            throw new SocketException("Should not occur :/");
        }
    }

    private void sendBroadcast(InetAddress address, DatagramSocket socket) throws SocketException {
        socket.setBroadcast(true);

        byte[] data = String.valueOf(serverPort).getBytes();

        try {
            DatagramPacket packet = new DatagramPacket(data, data.length, address, DiscoveryManager.DISCOVERY_PORT);
            socket.send(packet);
        } catch (IOException e) {
            // we don't care, maybe it works the next time.
            //e.printStackTrace();
        }
    }
}
