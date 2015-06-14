package htw.vs1.filesystem.Network.Protocol.Commands;

import htw.vs1.filesystem.Network.Protocol.Exceptions.SimpleProtocolTerminateConnection;
import htw.vs1.filesystem.Network.Protocol.Protocol;
import htw.vs1.filesystem.Network.Protocol.Requests.RequestList;


import java.util.List;

/**
 * Created by markus on 12.06.15.
 */
public interface Command {


    void execute(Protocol prot, RequestList requestList) throws SimpleProtocolTerminateConnection;
}
