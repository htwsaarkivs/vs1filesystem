package htw.vs1.filesystem.Network.Protocol.Requests;

import htw.vs1.filesystem.Network.Protocol.Commands.Command;
import htw.vs1.filesystem.Network.Protocol.Commands.CommandFactory;
import htw.vs1.filesystem.Network.Protocol.Commands.UnsupportedCommand;
import htw.vs1.filesystem.Network.Protocol.Exceptions.SimpleProtocolTerminateConnection;

import htw.vs1.filesystem.Network.Protocol.Replies.ServerReply;
import htw.vs1.filesystem.Network.Protocol.Server.ServerProtocol;



/**
 * Analyzes a request sent to the server
 *
 * Created by markus on 13.06.15.
 */
public class RequestAnalyzer {

    /**
     * {@link CommandFactory} to create the {@link Command}
     * by the given string representation.
     */
    private CommandFactory factory;

    /**
     * List of the requests sent to the server in the correct order.
     */
    private RequestLinkedList requestList = new RequestLinkedList();

    /**
     * The RequestAnalyzer is initialized with a reference to a CommandFactory
     * @param factory {@link CommandFactory} to create the {@link Command}
     *                  by the given string representation.
     */
    public RequestAnalyzer(CommandFactory factory) {
        this.factory = factory;
    }


    /**
     * Parses and executes the commands sent by the client over the ServerProtocol.
     *
     * @param proto {@link ServerProtocol} the communicate with the client.
     * @return {@link ServerReply} which should be sent to the client.
     * @throws SimpleProtocolTerminateConnection
     */
    public ServerReply parseCommand(ServerProtocol proto) throws SimpleProtocolTerminateConnection {
        Request req = new SimpleProtocolRequest(proto.getCurrentLine());
        Command currCommand = this.factory.getCommand(req.getCommandString());

        //Invalid commands are not added to our stack
        if (!(currCommand instanceof UnsupportedCommand)) {
            requestList.add(req);
        }
        //Execute command
        return currCommand.execute(proto, requestList);

    }

}
