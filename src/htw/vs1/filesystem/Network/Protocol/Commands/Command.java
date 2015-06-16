package htw.vs1.filesystem.Network.Protocol.Commands;

import htw.vs1.filesystem.Network.Protocol.Exceptions.SimpleProtocolTerminateConnection;
import htw.vs1.filesystem.Network.Protocol.Protocol;
import htw.vs1.filesystem.Network.Protocol.Replies.Reply;
import htw.vs1.filesystem.Network.Protocol.Requests.RequestList;


import java.util.List;

/**
 * Created by markus on 12.06.15.
 */
public interface Command {

    /**
     * Specifies the execute directive for server-side command execution.
     * @param prot A reference to a Protocol context
     * @param requestList A reference to a Stack containing the current and all previous Requests
     * @return
     * @throws SimpleProtocolTerminateConnection
     */
    Reply execute(Protocol prot, RequestList requestList) throws SimpleProtocolTerminateConnection;
}
