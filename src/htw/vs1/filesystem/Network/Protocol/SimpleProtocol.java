package htw.vs1.filesystem.Network.Protocol;

import htw.vs1.filesystem.Network.Protocol.DepcState.SimpleProtocolState;
import htw.vs1.filesystem.Network.Protocol.Exceptions.SimpleProtocolFatalError;
import htw.vs1.filesystem.Network.Protocol.Exceptions.SimpleProtocolInitializationErrorException;
import com.github.oxo42.stateless4j.*;
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

    private enum State {
        IDLE,
        READY,
        AUTHENTICATED
    }

    private enum Trigger {
        CLIENT_CONNECTED,
        CLIENT_AUTHENTICATED
    }

    private StateMachineConfig<State, Trigger> protoConfig = new StateMachineConfig<>();

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




            this.putLine("200 SERVER READY");
            //Signal an Zustandsautomat
            //this.state.clientConnected(this);


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
