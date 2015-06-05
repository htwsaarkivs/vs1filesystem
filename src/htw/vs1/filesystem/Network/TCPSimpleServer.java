package htw.vs1.filesystem.Network;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by markus on 05.06.15.
 */
public class TCPSimpleServer implements ServerInterface {

    public static void main(String[] args) {
        TCPSimpleServer server = new TCPSimpleServer(1111);
            server.start();

    }


    private int port;

    ServerSocket serverSocket;
    Socket socket;

    String recvdMessage;



    public TCPSimpleServer(int port) {
        this.port = port;
    }



    void start() {
        try {
            serverSocket = new ServerSocket(this.port);
            while(true) {
                System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");

                socket = serverSocket.accept();
                System.out.println("Just connected to " + socket.getRemoteSocketAddress());

                System.out.println("Recieved data:" + leseNachricht(socket));

                String dataToBeSent = "Thank you! We have recieved your data!";

                System.out.println("Sending data: "+dataToBeSent);

                schreibeNachricht(socket, dataToBeSent);
                socket.close();

            }
        }
        catch(UnknownHostException ex) {
            ex.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    void test() throws IOException {
        int port = 11111;
        java.net.ServerSocket serverSocket = new java.net.ServerSocket(port);
        java.net.Socket client = warteAufAnmeldung(serverSocket);
        String nachricht = leseNachricht(client);
        System.out.println(nachricht);
        schreibeNachricht(client, nachricht);
    }

    public Socket warteAufAnmeldung(ServerSocket serverSocket) throws IOException {
        Socket socket = serverSocket.accept(); // blockiert, bis sich ein Client angemeldet hat
        return socket;
    }

    String leseNachricht(Socket socket) throws IOException {
        BufferedReader bufferedReader =
                new BufferedReader(
                        new InputStreamReader(
                                socket.getInputStream()));
        char[] buffer = new char[200];
        int anzahlZeichen = bufferedReader.read(buffer, 0, 200); // blockiert bis Nachricht empfangen
        String nachricht = new String(buffer, 0, anzahlZeichen);
        return nachricht;
    }
    void schreibeNachricht(Socket socket, String nachricht) throws IOException {
        PrintWriter printWriter =
                new PrintWriter(
                        new OutputStreamWriter(
                                socket.getOutputStream()));
        printWriter.print(nachricht);
        printWriter.flush();
    }
}