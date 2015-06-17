package htw.vs1.filesystem.Network.Protocol.Commands;

import htw.vs1.filesystem.Network.Protocol.Exceptions.SimpleProtocolTerminateConnection;
import htw.vs1.filesystem.Network.Protocol.Protocol;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCode300;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCode401;
import htw.vs1.filesystem.Network.Protocol.Replies.Reply;
import htw.vs1.filesystem.Network.Protocol.Replies.SimpleProtocolReply;
import htw.vs1.filesystem.Network.Protocol.Requests.Request;
import htw.vs1.filesystem.Network.Protocol.Requests.RequestList;
import htw.vs1.filesystem.Network.Protocol.State.SimpleProtocolState;

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
    public Reply execute(Protocol prot, RequestList requestList) throws SimpleProtocolTerminateConnection {


        if (!isValid(requestList.getCurrentElement())) {
            return new SimpleProtocolReply(new ReplyCode401(COMMAND_STRING+" must have exactly one argument."), this);
        }

        String user = requestList.getCurrentElement().getArguments().get(0);


        return new SimpleProtocolReply(new ReplyCode300(user), this);
    }
}
