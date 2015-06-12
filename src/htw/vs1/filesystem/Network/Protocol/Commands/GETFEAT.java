package htw.vs1.filesystem.Network.Protocol.Commands;


import htw.vs1.filesystem.Network.Protocol.Protocol;
import htw.vs1.filesystem.Network.Protocol.Requests.Request;

/**
 * Created by markus on 12.06.15.
 */
public class GETFEAT extends AbstractCommand {
    public static String COMMAND_STRING = "GETFEAT";


    public void execute(Protocol prot, Request req) {
        prot.putLine("This Server supports amazingly many features");
        prot.putLine("Current State: "+prot.getState().toString());
        prot.putLine("Current Request"+req.getCommandString());
        prot.putLine("Arguments? "+req.hasArguments());
        prot.putLine("Arguments: "+req.getArguments().toString());

    }


}
