package htw.vs1.filesystem.Network.Protocol.Commands;

import htw.vs1.filesystem.Network.Protocol.Protocol;
import htw.vs1.filesystem.Network.Protocol.Requests.Request;

import java.util.List;

/**
 * Created by markus on 12.06.15.
 */
public class SETPASS extends AbstractCommand {
    public static final String COMMAND_STRING = "SETPASS";



    public void execute(Protocol prot, List<Request> requestList) {
        System.out.println(requestList);
        if (requestList.get(requestList.size()-1).getCommandString() != SETUSER.COMMAND_STRING) {
            prot.putLine("ERROR: SETPASS must be preceeded by SETUSER Command");
        }

    }
}
