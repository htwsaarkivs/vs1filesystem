package htw.vs1.filesystem.Network.Protocol.Commands;

import htw.vs1.filesystem.Network.Protocol.Client.ClientProtocol;
import htw.vs1.filesystem.Network.Protocol.Exceptions.SimpleProtocolTerminateConnection;
import htw.vs1.filesystem.Network.Protocol.Replies.ClientReply;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCode100;
import htw.vs1.filesystem.Network.Protocol.Replies.ServerReply;
import htw.vs1.filesystem.Network.Protocol.Replies.SimpleServerProtocolReply;
import htw.vs1.filesystem.Network.Protocol.Requests.RequestList;
import htw.vs1.filesystem.Network.Protocol.Server.ServerProtocol;

/**
 * Created by markus on 19.06.15.
 */
public class EXIT extends AbstractCommand {
    @Override
    public ServerReply execute(ServerProtocol prot, RequestList requestList) throws SimpleProtocolTerminateConnection {
        return new SimpleServerProtocolReply(new ReplyCode100("GOODBYE"), this);
    }

    @Override
    public ClientReply invoke(ClientProtocol prot, String... parameters) throws SimpleProtocolTerminateConnection {
        return null;
    }
}
