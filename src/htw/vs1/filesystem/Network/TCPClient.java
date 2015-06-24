package htw.vs1.filesystem.Network;
import htw.vs1.filesystem.Network.Protocol.Exceptions.SimpleProtocolFatalError;

import java.io.*;
import java.net.Socket;

/**
 * Created by ray on 24.06.2015.
 */
public class TCPClient {

        private InputStream input;
        private OutputStream output;
        private String currentLine;

        public static void main(String[] args) throws Exception {
            TCPClient client = new TCPClient();
            try {
                client.run();
                client.readLine();
                if (client.getCurrentLine().contains("200")) {
                    client.putLine("GETFEAT");
                    client.readLine();
                    client.readLine();
                    System.out.print(client.getCurrentLine());
                    return;
                }
                throw new Exception("Server im Arsch");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    void run() throws IOException {
            String ip = "127.0.0.1"; // localhost
            int port = 4322;
            Socket socket = new java.net.Socket(ip,port); // verbindet sich mit Server
            this.input = socket.getInputStream();
            this.output = socket.getOutputStream();

        }


    public String getCurrentLine() {
        return this.currentLine;
    }

    public void putLine(String line) {
        PrintWriter printWriter =
                new PrintWriter(
                        new OutputStreamWriter(output));
        printWriter.println(line);
        printWriter.flush();
    }

    /**
     * Read from the Socket.
     * @throws SimpleProtocolFatalError
     */
    public void readLine() throws SimpleProtocolFatalError {
        try {
            BufferedReader bufferedReader =
                    new BufferedReader(
                            new InputStreamReader(input));

            this.currentLine = bufferedReader.readLine();

        } catch (IOException e) {
            throw new SimpleProtocolFatalError();
        }
    }

}
