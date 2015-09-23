package htw.vs1.filesystem.Network.Protocol.Commands;

import htw.vs1.filesystem.FileSystem.exceptions.FileSystemException;
import htw.vs1.filesystem.Network.Protocol.Client.ClientProtocol;
import htw.vs1.filesystem.Network.Protocol.Exceptions.SimpleProtocolFatalError;
import htw.vs1.filesystem.Network.Protocol.Exceptions.SimpleProtocolTerminateConnection;
import htw.vs1.filesystem.Network.Protocol.Replies.ClientReply;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCode;
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
    public ServerReply execute(ServerProtocol prot, RequestList requestList) {


        if (!isValid(requestList.getCurrentElement())) {
            return new SimpleServerProtocolReply(new ReplyCode401(COMMAND_STRING+" must have exactly one argument."), this);
        }

        String user = requestList.getCurrentElement().getArguments().get(0);


        return new SimpleServerProtocolReply(new ReplyCode300(user), this);
    }

    @Override
    public ClientReply invoke(ClientProtocol prot, String... parameters) throws FileSystemException {
        try {
            prot.putLine(getCommandString(COMMAND_STRING, parameters));
            ReplyCode reply = prot.analyzeReply();
            if (reply.getCode() != ReplyCode300.CODE) {
                // TODO: was sollen wir hier machen ?
            }
        } catch (SimpleProtocolFatalError simpleProtocolFatalError) {
            throw new SimpleProtocolTerminateConnection();
        }
        return null;
    }
}
