package htw.vs1.filesystem.Network.Discovery;

import htw.vs1.filesystem.FileSystemManger;

import java.io.IOException;
import java.net.*;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

/**
 * The DiscoveryBroadcaster is responsible for announcing
 * that there is a server running on the given port.
 *
 * Created by Felix on 20.09.2015.
 */
public class DiscoveryBroadcaster {

    private int serverPort;
    private List<InetAddress> broadcastAddresses = new LinkedList<InetAddress>();

    private DatagramSocket socket;


    public DiscoveryBroadcaster(int port) {
        this.serverPort = port;

    }

    public void discovery() throws InterruptedException {
        try {
            sendBroadcastToAll(getDatagramSocket());
        } catch (SocketException e) {
            if (FileSystemManger.DEBUG) {
                e.printStackTrace();
            }
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
            socket.setBroadcast(true);
        }
        return socket;
    }


    private void initBroadcastAddressList() throws UnknownHostException, SocketException {

        //Universal Broadcast Address
        broadcastAddresses.add(InetAddress.getByName("255.255.255.255"));
        //Multicast in local subnet
        broadcastAddresses.add(InetAddress.getByName("224.0.0.1"));

        //Specific Network's BC address
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()) {
            NetworkInterface networkInterface = interfaces.nextElement();
            if (networkInterface.isLoopback() || !networkInterface.isUp()) {
                continue;
            }

            for (InterfaceAddress address : networkInterface.getInterfaceAddresses()) {
                if (address.getBroadcast() == null) continue;
                broadcastAddresses.add(address.getBroadcast());
            }

        }


    }

    private void sendBroadcastToAll(DatagramSocket socket) throws SocketException {
        //Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        /*
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
        */
        try {
            sendBroadcast(InetAddress.getByName("255.255.255.255"), socket);
        } catch (UnknownHostException e) {
            if (FileSystemManger.DEBUG) {
                e.printStackTrace();
            }
            throw new SocketException("Should not occur :/");
        }
    }

    private void sendBroadcast(InetAddress address, DatagramSocket socket) throws SocketException {

        BroadcastPacket broadcastPacket = new BroadcastPacket(serverPort);

        byte[] data = broadcastPacket.getByteValue();

        try {
            DatagramPacket packet = new DatagramPacket(data, data.length, address, DiscoveryManager.DISCOVERY_PORT);
            socket.send(packet);
        } catch (IOException e) {
            if (FileSystemManger.DEBUG) {
                e.printStackTrace();
            }
            // we don't care, maybe it works the next time.
            //e.printStackTrace();
        }
    }
}
