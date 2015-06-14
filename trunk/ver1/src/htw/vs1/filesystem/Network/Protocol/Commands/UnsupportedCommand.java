package htw.vs1.filesystem.Network.Protocol.Commands;

import htw.vs1.filesystem.Network.Protocol.Protocol;
import htw.vs1.filesystem.Network.Protocol.Requests.Request;

import java.util.List;

/**
 * Created by markus on 13.06.15.
 */
public class UnsupportedCommand extends AbstractCommand {

    @Override
    public void execute(Protocol prot, List<Request> requestList) {
        prot.putLine("Invalid Command");
    }
}
