package htw.vs1.filesystem.Network.Protocol.Commands;

import htw.vs1.filesystem.Network.Protocol.Exceptions.SimpleProtocolTerminateConnection;
import htw.vs1.filesystem.Network.Protocol.Protocol;
import htw.vs1.filesystem.Network.Protocol.Requests.Request;

/**
 * Created by markus on 12.06.15.
 */
public interface Command {


    void execute(Protocol prot, Request req);

}
