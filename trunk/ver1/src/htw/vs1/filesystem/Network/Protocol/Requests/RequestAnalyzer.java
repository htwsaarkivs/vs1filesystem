package htw.vs1.filesystem.Network.Protocol.Requests;

import htw.vs1.filesystem.Network.Protocol.Commands.Command;
import htw.vs1.filesystem.Network.Protocol.Commands.CommandFactory;
import htw.vs1.filesystem.Network.Protocol.Commands.UnsupportedCommand;
import htw.vs1.filesystem.Network.Protocol.Exceptions.SimpleProtocolTerminateConnection;

import htw.vs1.filesystem.Network.Protocol.Replies.ServerReply;
import htw.vs1.filesystem.Network.Protocol.Server.ServerProtocol;



/**
 * Created by markus on 13.06.15.
 */
public class RequestAnalyzer {

    private CommandFactory factory;

    private RequestLinkedList requestList = new RequestLinkedList();

    /**
     * The RequestAnalyzer is initialized with a reference to a Co
     * @param factory
     */
    public RequestAnalyzer(CommandFactory factory) {
        this.factory = factory;
    }


    public ServerReply parseCommand(ServerProtocol proto) throws SimpleProtocolTerminateConnection {
        Request req = new SimpleProtocolRequest(proto.getCurrentLine());
        Command currCommand = this.factory.getCommand(req.getCommandString());

        //Invalid commands are not added to our stack
        if (!(currCommand instanceof UnsupportedCommand)) {
            requestList.add(req);
        }
        //Exceute command
        return currCommand.execute(proto, requestList);

    }

}