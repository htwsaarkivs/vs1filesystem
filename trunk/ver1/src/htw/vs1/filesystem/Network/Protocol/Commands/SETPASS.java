package htw.vs1.filesystem.Network.Protocol.Commands;

import htw.vs1.filesystem.Network.Protocol.Exceptions.SimpleProtocolTerminateConnection;
import htw.vs1.filesystem.Network.Protocol.Protocol;
import htw.vs1.filesystem.Network.Protocol.Requests.Request;

/**
 * Created by markus on 12.06.15.
 */
public class SETPASS extends AbstractCommand {
    public static final String COMMAND_STRING = "SETPASS";



    public void execute(Protocol prot, Request req) {
        prot.putLine(prot.getRequestStack().toString());

    }
}
