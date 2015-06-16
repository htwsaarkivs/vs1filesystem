package htw.vs1.filesystem.Network.Protocol.Commands;

import htw.vs1.filesystem.Network.Protocol.Protocol;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCode219;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCode406;
import htw.vs1.filesystem.Network.Protocol.Replies.Reply;
import htw.vs1.filesystem.Network.Protocol.Replies.SimpleProtocolReply;
import htw.vs1.filesystem.Network.Protocol.Requests.RequestList;
import htw.vs1.filesystem.Network.Protocol.State.SimpleProtocolState;

/**
 * Created by markus on 13.06.15.
 */
public class LS extends AbstractCommand {
    public static String COMMAND_STRING = "LS";

    public Reply execute(Protocol prot, RequestList requestList) {
        if(prot.getState().equals(SimpleProtocolState.AUTHENTICATED)) {
            prot.putLine("YoloSwag Liste");
        } else {
            return new SimpleProtocolReply(new ReplyCode406(), this);
        }



        return new SimpleProtocolReply(new ReplyCode219(), this);
    }
}
