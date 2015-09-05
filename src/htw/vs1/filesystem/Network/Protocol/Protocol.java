package htw.vs1.filesystem.Network.Protocol;

import htw.vs1.filesystem.Network.Protocol.State.State;

/**
 * Interface which declares the protocol methods.
 *
 * Created by markus on 12.06.15.
 */
public interface Protocol {


    /**
     * Get the last line of text, which has been read from the socket.
     * @return the last line of text, which has been read from the socket.
     */
    String getCurrentLine();

    /**
     * Write to the Socket.
     * @param line to write to the socket.
     */
    void putLine(String line);

    /**
     * Change the state of the protocol.
     * @param state new state of the protocol.
     */
    void setState(State state);

    /**
     * Get the current state of the protocol.
     * @return the current state of the protocol.
     */
    State getState();


}
