package htw.vs1.filesystem.Network.Protocol.Commands;

import htw.vs1.filesystem.Network.Protocol.Client.ClientProtocol;
import htw.vs1.filesystem.Network.Protocol.Exceptions.SimpleProtocolTerminateConnection;
import htw.vs1.filesystem.Network.Protocol.Replies.ClientReply;
import htw.vs1.filesystem.Network.Protocol.Replies.ServerReply;
import htw.vs1.filesystem.Network.Protocol.Requests.RequestList;
import htw.vs1.filesystem.Network.Protocol.Server.ServerProtocol;

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
    ServerReply execute(ServerProtocol prot, RequestList requestList) throws SimpleProtocolTerminateConnection;

    /**
     * Specifies the invocation directive for client-side use of commands.
     * @param prot A reference to a Protocol context
     * @return
     * @throws SimpleProtocolTerminateConnection
     */
    ClientReply invoke(ClientProtocol prot) throws SimpleProtocolTerminateConnection;
}
