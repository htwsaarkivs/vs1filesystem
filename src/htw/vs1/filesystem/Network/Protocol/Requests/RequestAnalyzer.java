package htw.vs1.filesystem.Network.Protocol.Requests;

import htw.vs1.filesystem.Network.Protocol.Commands.Command;
import htw.vs1.filesystem.Network.Protocol.Commands.CommandFactory;
import htw.vs1.filesystem.Network.Protocol.Protocol;

/**
 * Created by markus on 13.06.15.
 */
public class RequestAnalyzer {

    private CommandFactory factory;


    public RequestAnalyzer(CommandFactory factory) {
        this.factory = factory;
    }


    public void parseCommand(Protocol proto) {
        Command currCommand = this.factory.getCommand(proto.getCurrentLine());
        currCommand.execute(proto);
    }

}
