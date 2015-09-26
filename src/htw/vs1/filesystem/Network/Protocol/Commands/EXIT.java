package htw.vs1.filesystem.Network.Protocol.Commands;

import htw.vs1.filesystem.FileSystem.exceptions.FileSystemException;
import htw.vs1.filesystem.Network.Protocol.Client.ClientProtocol;
import htw.vs1.filesystem.Network.Protocol.Exceptions.SimpleProtocolFatalError;
import htw.vs1.filesystem.Network.Protocol.Exceptions.SimpleProtocolTerminateConnection;
import htw.vs1.filesystem.Network.Protocol.Replies.ClientReply;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCode;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCode100;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCode510;
import htw.vs1.filesystem.Network.Protocol.Replies.ServerReply;
import htw.vs1.filesystem.Network.Protocol.Replies.SimpleServerProtocolReply;
import htw.vs1.filesystem.Network.Protocol.Requests.RequestList;
import htw.vs1.filesystem.Network.Protocol.Server.ServerProtocol;

/**
 * Created by markus on 19.06.15.
 */
public class EXIT extends AbstractCommand {
    public static final String COMMAND_STRING = "EXIT";

    @Override
    public ServerReply execute(ServerProtocol prot, RequestList requestList) {
        new SimpleServerProtocolReply(new ReplyCode100("GOODBYE"), this).putReply(prot);
        return new SimpleServerProtocolReply(new ReplyCode510(), this);
    }

    @Override
    public ClientReply invoke(ClientProtocol prot, String... parameters) throws FileSystemException {
        prot.putLine(getCommandString(COMMAND_STRING, parameters));
        ReplyCode reply = prot.analyzeReply();

        if (reply.getException() != null) {
            throw reply.getException();
        }

        reply = prot.analyzeReply();

        if (reply.getException() != null) {
            throw reply.getException();
        }

        return null;
    }
}
