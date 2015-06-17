package htw.vs1.filesystem.Network.Protocol.Commands;

import htw.vs1.filesystem.Network.Protocol.Exceptions.SimpleProtocolTerminateConnection;
import htw.vs1.filesystem.Network.Protocol.Protocol;
import htw.vs1.filesystem.Network.Protocol.Replies.Reply;
import htw.vs1.filesystem.Network.Protocol.Requests.RequestList;
import htw.vs1.filesystem.Network.Protocol.State.SimpleProtocolState;

/**
 * Created by markus on 12.06.15.
 */
public class SETUSER extends AbstractCommand {
    public static String COMMAND_STRING = "SETUSER";




    @Override
    public Reply execute(Protocol prot, RequestList requestList) throws SimpleProtocolTerminateConnection {
        prot.putLine("Successfully logged in");
        prot.setState(SimpleProtocolState.AUTHENTICATED);
        //throw new SimpleProtocolTerminateConnection();
        return null;
    }
}
