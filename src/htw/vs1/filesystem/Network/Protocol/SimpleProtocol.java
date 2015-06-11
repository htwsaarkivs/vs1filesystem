package htw.vs1.filesystem.Network.Protocol;

import htw.vs1.filesystem.Network.Protocol.Exceptions.SimpleProtocolInitializationErrorException;

import java.io.*;
import java.net.Socket;

/**
 * Created by markus on 11.06.15.
 */
public class SimpleProtocol {

    private InputStream input;
    private OutputStream output;
    private Socket socket;

    private String currentLine;
    private SimpleProtocolState state = SimpleProtocolState.IDLE;

    public SimpleProtocol(Socket socket) throws SimpleProtocolInitializationErrorException {
        try {
            this.socket = socket;
            this.input = socket.getInputStream();
            this.output = socket.getOutputStream();
        } catch (IOException e) {
            throw new SimpleProtocolInitializationErrorException();
        }
    }

    public void run() {
        try {
            this.state.clientConnected(this);
            readLine();
            if (getCurrentLine().equals("SET USER")) {
                this.state.clientSuccessfullyAuthenticated(this);
                System.out.print(this.state);
            }
            System.out.println(getCurrentLine());



        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String getCurrentLine() {
        return this.currentLine;
    }


    public void setSimpleProtocolState(SimpleProtocolState state) {
        this.state = state;
    }

    public void putLine(String line) {
        System.out.println(line);
    }

    public void readLine() {
        try {
            BufferedReader bufferedReader =
                    new BufferedReader(
                            new InputStreamReader(input));

            this.currentLine = bufferedReader.readLine();

        } catch (IOException e) {
            //TODO: Excetion schreiben
        }
    }


}
