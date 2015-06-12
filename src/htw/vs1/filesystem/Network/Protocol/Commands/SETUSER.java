package htw.vs1.filesystem.Network.Protocol.Commands;

import htw.vs1.filesystem.Network.Protocol.Protocol;
import htw.vs1.filesystem.Network.Protocol.SimpleProtocol;
import htw.vs1.filesystem.Network.Protocol.State.SimpleProtocolState;

/**
 * Created by markus on 12.06.15.
 */
public class SETUSER extends AbstractCommand {
    public static String COMMAND_STRING = "SETUSER";




    @Override
    public void execute(Protocol prot) {
        prot.putLine("Successfully logged in");
        prot.setState(SimpleProtocolState.AUTHENTICATED);
    }
}
