package htw.vs1.filesystem.Network.Protocol.Commands;

import htw.vs1.filesystem.Network.Protocol.Exceptions.SimpleProtocolTerminateConnection;
import htw.vs1.filesystem.Network.Protocol.Protocol;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCode100;
import htw.vs1.filesystem.Network.Protocol.Replies.Reply;
import htw.vs1.filesystem.Network.Protocol.Replies.SimpleProtocolReply;
import htw.vs1.filesystem.Network.Protocol.Requests.RequestList;

/**
 * Created by markus on 19.06.15.
 */
public class EXIT extends AbstractCommand {
    @Override
    public Reply execute(Protocol prot, RequestList requestList) throws SimpleProtocolTerminateConnection {
        return new SimpleProtocolReply(new ReplyCode100("GOODBYE"), this);
    }
}
