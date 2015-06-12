package htw.vs1.filesystem.Network.Protocol;

import htw.vs1.filesystem.Network.Protocol.State.State;

/**
 * Created by markus on 12.06.15.
 */
public interface Protocol {

    String getCurrentLine();

    void putLine(String line);

    //Ein Protokoll muss einen veränderbaren Zustand haben
    void setState(State state);
    State getState();
}
