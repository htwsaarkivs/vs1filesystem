package htw.vs1.filesystem.Network.Protocol.Requests;

import htw.vs1.filesystem.Network.Protocol.Commands.Command;
import htw.vs1.filesystem.Network.Protocol.Commands.CommandFactory;
import htw.vs1.filesystem.Network.Protocol.Commands.UnsupportedCommand;
import htw.vs1.filesystem.Network.Protocol.Exceptions.SimpleProtocolTerminateConnection;
import htw.vs1.filesystem.Network.Protocol.Protocol;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by markus on 13.06.15.
 */
public class RequestAnalyzer {

    private CommandFactory factory;

    private RequestList requestList = new RequestList();


    public RequestAnalyzer(CommandFactory factory) {
        this.factory = factory;
    }


    private void addToList(Request req) {
        requestList.add(req);
    }

    private List<Request> getUnmodifiableList() {
        return Collections.unmodifiableList(requestList);
    }


    public void parseCommand(Protocol proto) throws SimpleProtocolTerminateConnection {
        Request req = new SimpleProtocolRequest(proto.getCurrentLine());
        Command currCommand = this.factory.getCommand(req.getCommandString());

        //Invalid commands are not added to our stack
        if (!(currCommand instanceof UnsupportedCommand)) {
            addToList(req);
        }
        //Exceute command
        currCommand.execute(proto, getUnmodifiableList());

    }

}
