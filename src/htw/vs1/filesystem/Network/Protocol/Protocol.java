package htw.vs1.filesystem.Network.Protocol;

import htw.vs1.filesystem.Network.Protocol.Requests.Request;
import htw.vs1.filesystem.Network.Protocol.State.State;

import java.util.List;
import java.util.Stack;

/**
 * Created by markus on 12.06.15.
 */
public interface Protocol {


    /**
     * Get the last line of text, which has been read from the socket.
     * @return
     */
    String getCurrentLine();

    /**
     * Write to the Socket.
     * @param line
     */
    void putLine(String line);

    /**
     * Change the state of the protocol.
     * @param state
     */
    void setState(State state);

    /**
     * Get the current state of the protocol.
     * @return
     */
    State getState();
}
