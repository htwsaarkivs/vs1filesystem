package htw.vs1.filesystem.Network.Protocol.Commands;

import htw.vs1.filesystem.Network.Protocol.Client.ClientProtocol;
import htw.vs1.filesystem.Network.Protocol.Exceptions.SimpleProtocolTerminateConnection;
import htw.vs1.filesystem.Network.Protocol.Replies.ClientReply;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCode300;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCode401;
import htw.vs1.filesystem.Network.Protocol.Replies.ServerReply;
import htw.vs1.filesystem.Network.Protocol.Replies.SimpleServerProtocolReply;
import htw.vs1.filesystem.Network.Protocol.Requests.Request;
import htw.vs1.filesystem.Network.Protocol.Requests.RequestList;
import htw.vs1.filesystem.Network.Protocol.Server.ServerProtocol;

/**
 * Created by markus on 12.06.15.
 */
public class SETUSER extends AbstractCommand {
    public static String COMMAND_STRING = "SETUSER";


    protected static boolean isValid(Request req) {
        if (!req.getCommandString().equals(COMMAND_STRING)) return false;
        if (req.numOfArguments() != 1) return false;
        return true;
    }


    @Override
    public ServerReply execute(ServerProtocol prot, RequestList requestList) throws SimpleProtocolTerminateConnection {


        if (!isValid(requestList.getCurrentElement())) {
            return new SimpleServerProtocolReply(new ReplyCode401(COMMAND_STRING+" must have exactly one argument."), this);
        }

        String user = requestList.getCurrentElement().getArguments().get(0);


        return new SimpleServerProtocolReply(new ReplyCode300(user), this);
    }

    @Override
    public ClientReply invoke(ClientProtocol port) throws SimpleProtocolTerminateConnection {
        return null;
    }
}
