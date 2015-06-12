package htw.vs1.filesystem.Network.Protocol;


import htw.vs1.filesystem.Network.Protocol.Exceptions.SimpleProtocolInitializationErrorException;

import htw.vs1.filesystem.Network.Protocol.Exceptions.SimpleProtocolNoMoreLinesAvailableException;
import htw.vs1.filesystem.Network.Protocol.State.SimpleProtocolState;
import htw.vs1.filesystem.Network.Protocol.State.State;

import java.io.*;
import java.net.Socket;

/**
 * Created by markus on 11.06.15.
 */
public class SimpleProtocol implements Protocol{

    private InputStream input;
    private OutputStream output;
    private Socket socket;

    private String currentLine;

    private State state = SimpleProtocolState.IDLE;

    public SimpleProtocol(Socket socket) throws SimpleProtocolInitializationErrorException {
        try {
            this.socket = socket;
            this.input = socket.getInputStream();
            this.output = socket.getOutputStream();
        } catch (IOException e) {
            throw new SimpleProtocolInitializationErrorException();
        }
    }

    public void run() throws Exception {
        //this.protoConfig.configure(State.IDLE)

        while (true) {
            try {
                this.readLine();
                this.putLine(this.getCurrentLine());

            } catch (SimpleProtocolNoMoreLinesAvailableException e) {
                break;
            }
        }


    }

    public String getCurrentLine() {
        return this.currentLine;
    }



    public void putLine(String line) {
        PrintWriter printWriter =
                new PrintWriter(
                        new OutputStreamWriter(this.output));
        printWriter.println(line);
        printWriter.flush();
    }

    public void readLine() throws SimpleProtocolNoMoreLinesAvailableException{
        try {
            BufferedReader bufferedReader =
                    new BufferedReader(
                            new InputStreamReader(input));

            this.currentLine = bufferedReader.readLine();

        } catch (IOException e) {
            throw new SimpleProtocolNoMoreLinesAvailableException();
        }
    }


    @Override
    public void setState(State state) {
        this.state = state;
    }

    @Override
    public State getState() {
        return this.state;
    }
}
