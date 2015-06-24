package htw.vs1.filesystem.Network.Protocol.Commands;

import htw.vs1.filesystem.Network.Protocol.Client.ClientProtocol;
import htw.vs1.filesystem.Network.Protocol.Exceptions.SimpleProtocolTerminateConnection;
import htw.vs1.filesystem.Network.Protocol.Replies.ClientReply;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCode400;
import htw.vs1.filesystem.Network.Protocol.Replies.ServerReply;
import htw.vs1.filesystem.Network.Protocol.Replies.SimpleServerProtocolReply;
import htw.vs1.filesystem.Network.Protocol.Requests.RequestList;
import htw.vs1.filesystem.Network.Protocol.Server.ServerProtocol;

/**
 * Created by markus on 13.06.15.
 */
public class UnsupportedCommand extends AbstractCommand {

    @Override
    public ServerReply execute(ServerProtocol prot, RequestList requestList) {
        return new SimpleServerProtocolReply(new ReplyCode400(), this);
    }

    @Override
    public ClientReply invoke(ClientProtocol prot) throws SimpleProtocolTerminateConnection {
        return null;
    }
}
