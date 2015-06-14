package htw.vs1.filesystem.Network.Protocol.Commands;

import htw.vs1.filesystem.Network.Protocol.Protocol;
import htw.vs1.filesystem.Network.Protocol.Requests.Request;
import htw.vs1.filesystem.Network.Protocol.Requests.RequestList;

import java.util.List;

/**
 * Created by markus on 12.06.15.
 */
public class SETPASS extends AbstractCommand {
    public static final String COMMAND_STRING = "SETPASS";



    public void execute(Protocol prot, RequestList requestList) {
        prot.putLine(requestList.getNthElementFromEnd(-2).toString());


    }
}
