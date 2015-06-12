package htw.vs1.filesystem.Network.Protocol.Commands;


import htw.vs1.filesystem.Network.Protocol.Protocol;

/**
 * Created by markus on 12.06.15.
 */
public class GETFEAT extends AbstractCommand {
    public static String COMMAND_STRING = "FEAT";


    public void execute(Protocol prot) {
        prot.putLine("This Server supports amazingly many features");
        prot.putLine("Current State: "+prot.getState().toString());
    }


}
