package htw.vs1.filesystem.Network.Protocol.Commands;

import htw.vs1.filesystem.Network.Protocol.Client.ClientProtocol;
import htw.vs1.filesystem.Network.Protocol.Exceptions.SimpleProtocolFatalError;
import htw.vs1.filesystem.Network.Protocol.Exceptions.SimpleProtocolTerminateConnection;
import htw.vs1.filesystem.Network.Protocol.Replies.ClientReply;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCode;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCode100;
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
        return new SimpleServerProtocolReply(new ReplyCode100("GOODBYE"), this);
    }

    @Override
    public ClientReply invoke(ClientProtocol prot, String... parameters) throws SimpleProtocolTerminateConnection {
        prot.putLine(getCommandString(COMMAND_STRING, parameters));
        try {
            ReplyCode reply = prot.analyzeReply();
            if (reply.getCode() == ReplyCode100.CODE) {
                // TODO: YOO alles cool, sonst halt fehler...
            }
        } catch (SimpleProtocolFatalError simpleProtocolFatalError) {
            simpleProtocolFatalError.printStackTrace();
        }

        return null;
    }
}
