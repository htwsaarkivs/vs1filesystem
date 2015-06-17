package htw.vs1.filesystem.Network.Protocol.Commands;

import htw.vs1.filesystem.Network.Protocol.Exceptions.SimpleProtocolTerminateConnection;
import htw.vs1.filesystem.Network.Protocol.Protocol;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCode300;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCode401;
import htw.vs1.filesystem.Network.Protocol.Replies.Reply;
import htw.vs1.filesystem.Network.Protocol.Replies.SimpleProtocolReply;
import htw.vs1.filesystem.Network.Protocol.Requests.RequestList;
import htw.vs1.filesystem.Network.Protocol.State.SimpleProtocolState;

/**
 * Created by markus on 12.06.15.
 */
public class SETUSER extends AbstractCommand {
    public static String COMMAND_STRING = "SETUSER";




    @Override
    public Reply execute(Protocol prot, RequestList requestList) throws SimpleProtocolTerminateConnection {


        if (requestList.getCurrentElement().numOfArguments() != 1) {
            return new SimpleProtocolReply(new ReplyCode401("SETUSER must have exactly one argument."), this);
        }

        String user = requestList.getCurrentElement().getArguments().get(0);



        return new SimpleProtocolReply(new ReplyCode300(user), this);
    }
}
