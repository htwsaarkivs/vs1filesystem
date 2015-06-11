package htw.vs1.filesystem.Network.Protocol;

import htw.vs1.filesystem.Network.Protocol.DepcState.SimpleProtocolState;
import htw.vs1.filesystem.Network.Protocol.Exceptions.SimpleProtocolFatalError;
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

    public void run() throws Exception {
            this.putLine("200 SERVER READY");
            //Signal an Zustandsautomat
            this.state.clientConnected(this);


    }

    public String getCurrentLine() {
        return this.currentLine;
    }


    public void setSimpleProtocolState(SimpleProtocolState state) {
        this.state = state;
    }

    public void putLine(String line) {
        PrintWriter printWriter =
                new PrintWriter(
                        new OutputStreamWriter(this.output));
        printWriter.println(line);
        printWriter.flush();
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
