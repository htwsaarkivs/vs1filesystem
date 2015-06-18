package htw.vs1.filesystem.Network.Protocol.Commands;

import htw.vs1.filesystem.Network.Protocol.Protocol;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCode400;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCode401;
import htw.vs1.filesystem.Network.Protocol.Replies.Reply;
import htw.vs1.filesystem.Network.Protocol.Replies.SimpleProtocolReply;
import htw.vs1.filesystem.Network.Protocol.Requests.RequestList;

/**
 * Created by markus on 13.06.15.
 */
public class UnsupportedCommand extends AbstractCommand {

    @Override
    public Reply execute(Protocol prot, RequestList requestList) {
        return new SimpleProtocolReply(new ReplyCode400(), this);
    }
}
