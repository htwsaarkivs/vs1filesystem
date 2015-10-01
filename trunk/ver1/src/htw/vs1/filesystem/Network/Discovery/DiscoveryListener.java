package htw.vs1.filesystem.Network.Discovery;

import htw.vs1.filesystem.FileSystemManger;

import java.io.IOException;
import java.net.*;
import java.util.Enumeration;
import java.util.List;

/**
 * The DiscoveryListener is a simple server listening on the
 * udp broadcasting port keeping the list of nearby servers up
 * to date.
 *
 * Created by Felix on 20.09.2015.
 */
public class DiscoveryListener extends DiscoveryThread {

    private static final String THREAD_NAME = "DiscoveryListenerThread";

    @Override
    protected DatagramSocket getDatagramSocket() throws SocketException {
        try {
            return new DatagramSocket(DiscoveryManager.DISCOVERY_PORT, InetAddress.getByName("0.0.0.0"));
        } catch (UnknownHostException e) {
            if (FileSystemManger.DEBUG) {
                e.printStackTrace();
            }
            throw new SocketException();
        }
    }

    public DiscoveryListener() {
        setName(THREAD_NAME);
    }

    @Override
    protected void discovery(DatagramSocket socket) throws InterruptedException {
        byte[] receiveBuffer = new byte[15000];
        DatagramPacket packet = new DatagramPacket(receiveBuffer, receiveBuffer.length);

        try {
            socket.receive(packet);

            String portStr = new String(packet.getData()).trim();


            try {
                if (!itsme(packet.getAddress())) {
                    int port = Integer.parseInt(portStr);

                    DiscoveryManager.getInstance().add(
                            packet.getAddress().getHostAddress(), port, packet.getAddress().getHostName());
                }
            } catch (NumberFormatException | SocketException e) {
                if (FileSystemManger.DEBUG) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            if (FileSystemManger.DEBUG) {
                e.printStackTrace();
            }
        }

    }

    private boolean itsme(InetAddress remoteAddress) throws SocketException {
        if (!FileSystemManger.getInstance().fileSystemServerRunning()) {
            // If I do not have a file system server running the address cannot be mine.
            return false;
        }

        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()) {
            NetworkInterface networkInterface = interfaces.nextElement();

            List<InterfaceAddress> addresses = networkInterface.getInterfaceAddresses();
            for (InterfaceAddress address : addresses) {
                if (address.getAddress().equals(remoteAddress)) {
                    return true;
                }
            }
        }

        return false;
    }
}
