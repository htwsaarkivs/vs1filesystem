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
        SimpleProtocolRequest req = new SimpleProtocolRequest(proto.getCurrentLine());
        Command currCommand = this.factory.getCommand(req.getCommandString());
        currCommand.execute(proto, req);
        proto.pushRequestStack(req);
    }

}
