package htw.vs1.filesystem.Network.Discovery;

import java.io.IOException;
import java.net.*;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by Felix on 20.09.2015.
 */
public class DiscoveryListener extends DiscoveryThread {

    private static final String THREAD_NAME = "DiscoveryListenerThread";

    @Override
    protected DatagramSocket getDatagramSocket() throws SocketException {
        try {
            return new DatagramSocket(DiscoveryManager.DISCOVERY_PORT, InetAddress.getByName("0.0.0.0"));
        } catch (UnknownHostException e) {
            e.printStackTrace();
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
        } catch (IOException e) {
            e.printStackTrace();
        }

        String portStr = new String(packet.getData()).trim();


        try {
            if (!itsme(packet.getAddress())) {
                int port = Integer.parseInt(portStr);

                DiscoveryManager.getInstance().add(
                        packet.getAddress().getHostAddress(), port, packet.getAddress().getHostName());

                /*System.out.printf(
                        "Ehh do han ich was von %s mit der IP %s\n",
                        packet.getAddress().getHostName(),
                        packet.getAddress().getHostAddress());*/
            }
        } catch (NumberFormatException | SocketException e) {
            e.printStackTrace();
        }
    }

    // Frage: bin ich es, wenn der Server einen anderen Port nutzt, aber auf meiner Adresse unterwegs ist?
    private boolean itsme(InetAddress remoteAddress) throws SocketException {
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
