package htw.vs1.filesystem.Network.Protocol.Commands;

import htw.vs1.filesystem.Network.Protocol.Protocol;
import htw.vs1.filesystem.Network.Protocol.Requests.Request;
import htw.vs1.filesystem.Network.Protocol.Requests.RequestList;
import htw.vs1.filesystem.Network.Protocol.State.SimpleProtocolState;

import java.util.List;

/**
 * Created by markus on 13.06.15.
 */
public class LS extends AbstractCommand {
    public static String COMMAND_STRING = "LS";

    public void execute(Protocol prot, RequestList requestList) {
        if(prot.getState().equals(SimpleProtocolState.AUTHENTICATED)) {
            prot.putLine("YoloSwag Liste");
        } else {
            prot.putLine("Please Login to perfom this command");
        }
    }
}
