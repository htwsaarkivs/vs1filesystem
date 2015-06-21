package htw.vs1.filesystem.Network.Protocol.Commands;

import htw.vs1.filesystem.Network.Protocol.Protocol;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCode219;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCode401;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCode406;
import htw.vs1.filesystem.Network.Protocol.Replies.Reply;
import htw.vs1.filesystem.Network.Protocol.Replies.SimpleProtocolReply;
import htw.vs1.filesystem.Network.Protocol.Requests.RequestList;
import htw.vs1.filesystem.Network.Protocol.State.SimpleProtocolState;

/**
 * Created by Hendrik on 21.06.2015.
 */
public class MKDIR extends AbstractCommand {
    public static String COMMAND_STRING = "MKDIR";


    public Reply execute(Protocol prot, RequestList requestlist) {
        if (!prot.getState().equals(SimpleProtocolState.AUTHENTICATED))
            return new SimpleProtocolReply(
                    new ReplyCode406(),
                    this);


        if (requestlist.getCurrentElement().numOfArguments() != 1)
            return new SimpleProtocolReply(
                    new ReplyCode401(COMMAND_STRING + " must have exactly one argument"),
                    this);


        String name = requestlist.getCurrentElement().getArguments().get(0);


        prot.getFileSystem().getWorkingDirectory().add(name);


        return new SimpleProtocolReply(new ReplyCode219(), this);
    }

}
