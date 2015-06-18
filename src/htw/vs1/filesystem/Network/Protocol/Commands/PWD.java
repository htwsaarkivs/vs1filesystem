package htw.vs1.filesystem.Network.Protocol.Commands;

import htw.vs1.filesystem.Network.Protocol.Protocol;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCode210;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCode219;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCode406;
import htw.vs1.filesystem.Network.Protocol.Replies.Reply;
import htw.vs1.filesystem.Network.Protocol.Replies.SimpleProtocolReply;
import htw.vs1.filesystem.Network.Protocol.Requests.RequestList;
import htw.vs1.filesystem.Network.Protocol.State.SimpleProtocolState;

/**
 * Created by Hendrik on 18.06.2015.
 */
public class PWD extends AbstractCommand {
    public static String COMMAND_STRING = "PWD";

    public Reply execute(Protocol prot, RequestList requestList) {
        if (!prot.getState().equals(SimpleProtocolState.AUTHENTICATED))
            return new SimpleProtocolReply(new ReplyCode406(), this);


        String ret = prot.getFileSystem().printWorkingDirectory();
        new SimpleProtocolReply(new ReplyCode210(), this).putReply(prot);
        prot.putLine(ret);

        return new SimpleProtocolReply(new ReplyCode219(), this);
    }
}
