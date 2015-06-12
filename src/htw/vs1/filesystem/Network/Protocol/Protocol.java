package htw.vs1.filesystem.Network.Protocol;

import htw.vs1.filesystem.Network.Protocol.Requests.Request;
import htw.vs1.filesystem.Network.Protocol.State.State;

import java.util.Stack;

/**
 * Created by markus on 12.06.15.
 */
public interface Protocol {

    void pushRequestStack(Request req);
    Stack<Request> getRequestStack();

    //Man muss den aktuelle gelesenen Zeile lesen können
    String getCurrentLine();

    //Ein Protokoll muss ausgeben können
    void putLine(String line);

    //Ein Protokoll muss einen veränderbaren Zustand haben
    void setState(State state);
    State getState();
}
