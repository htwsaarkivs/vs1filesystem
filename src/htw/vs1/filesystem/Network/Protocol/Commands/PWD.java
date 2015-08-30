package htw.vs1.filesystem.Network.Protocol.Commands;

import htw.vs1.filesystem.Network.Protocol.Client.ClientProtocol;
import htw.vs1.filesystem.Network.Protocol.Exceptions.SimpleProtocolTerminateConnection;
import htw.vs1.filesystem.Network.Protocol.Replies.ClientReply;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCode210;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCode219;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCode406;
import htw.vs1.filesystem.Network.Protocol.Replies.ServerReply;
import htw.vs1.filesystem.Network.Protocol.Replies.SimpleServerProtocolReply;
import htw.vs1.filesystem.Network.Protocol.Requests.RequestList;
import htw.vs1.filesystem.Network.Protocol.Server.ServerProtocol;
import htw.vs1.filesystem.Network.Protocol.State.SimpleProtocolState;

/**
 * Created by Hendrik on 18.06.2015.
 */
public class PWD extends AbstractCommand {
    public static String COMMAND_STRING = "PWD";

    public ServerReply execute(ServerProtocol prot, RequestList requestList) {
        if (!prot.getState().equals(SimpleProtocolState.AUTHENTICATED))
            return new SimpleServerProtocolReply(new ReplyCode406(), this);


        String ret = prot.getFileSystem().printWorkingDirectory();
        new SimpleServerProtocolReply(new ReplyCode210(), this).putReply(prot);
        prot.putLine(ret);
        return new SimpleServerProtocolReply(new ReplyCode219(), this);
    }

    @Override
    public ClientReply invoke(ClientProtocol prot, String... parameters) throws SimpleProtocolTerminateConnection {
        return null;
    }
}
