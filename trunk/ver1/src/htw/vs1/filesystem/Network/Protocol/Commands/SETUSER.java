package htw.vs1.filesystem.Network.Protocol.Commands;

import htw.vs1.filesystem.Network.Protocol.Exceptions.SimpleProtocolTerminateConnection;
import htw.vs1.filesystem.Network.Protocol.Protocol;
import htw.vs1.filesystem.Network.Protocol.Requests.Request;
import htw.vs1.filesystem.Network.Protocol.Requests.RequestList;
import htw.vs1.filesystem.Network.Protocol.State.SimpleProtocolState;

import java.util.List;

/**
 * Created by markus on 12.06.15.
 */
public class SETUSER extends AbstractCommand {
    public static String COMMAND_STRING = "SETUSER";




    @Override
    public void execute(Protocol prot, RequestList requestList) throws SimpleProtocolTerminateConnection {
        prot.putLine("Successfully logged in");
        //prot.setState(SimpleProtocolState.AUTHENTICATED);
        //throw new SimpleProtocolTerminateConnection();
    }
}
