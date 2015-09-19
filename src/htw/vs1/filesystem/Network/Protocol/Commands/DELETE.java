package htw.vs1.filesystem.Network.Protocol.Commands;

import htw.vs1.filesystem.FileSystem.exceptions.CouldNotDeleteException;
import htw.vs1.filesystem.FileSystem.exceptions.FSObjectException;
import htw.vs1.filesystem.FileSystem.exceptions.ObjectNotFoundException;
import htw.vs1.filesystem.Network.Protocol.Client.ClientProtocol;
import htw.vs1.filesystem.Network.Protocol.Exceptions.SimpleProtocolFatalError;
import htw.vs1.filesystem.Network.Protocol.Exceptions.SimpleProtocolTerminateConnection;
import htw.vs1.filesystem.Network.Protocol.Replies.ClientReply;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.*;
import htw.vs1.filesystem.Network.Protocol.Replies.ServerReply;
import htw.vs1.filesystem.Network.Protocol.Replies.SimpleServerProtocolReply;
import htw.vs1.filesystem.Network.Protocol.Requests.RequestList;
import htw.vs1.filesystem.Network.Protocol.Server.ServerProtocol;
import htw.vs1.filesystem.Network.Protocol.State.SimpleProtocolState;
import htw.vs1.filesystem.Network.Protocol.State.State;

/**
 * Created by markus on 06.09.15.
 */
public class DELETE extends AbstractCommand {


    public static final String COMMAND_STRING = "DELETE";

    @Override
    public ServerReply execute(ServerProtocol prot, RequestList requestList) throws SimpleProtocolTerminateConnection {
        if (prot.getState() != SimpleProtocolState.AUTHENTICATED)
            return new SimpleServerProtocolReply(new ReplyCode406(), this);

        if (requestList.getCurrentElement().numOfArguments() != 1)
            return new SimpleServerProtocolReply(
                    new ReplyCode401(COMMAND_STRING + " must have exactly one argument"),
                    this);
        try {
            prot.getFileSystem().delete(requestList.getCurrentElement().getArguments().get(0));
        } catch (FSObjectException e) {
            return new SimpleServerProtocolReply(e.getReplyCode(), this);
        }


        return new SimpleServerProtocolReply(new ReplyCode219(), this);
    }

    @Override
    public ClientReply invoke(ClientProtocol prot, String... parameters) throws SimpleProtocolTerminateConnection {
        prot.putLine(getCommandString(COMMAND_STRING, parameters));

        try {
            ReplyCode reply = prot.analyzeReply();
            if (reply.getCode() == ReplyCode219.CODE) {
                // TODO: YOO alles cool, sonst halt fehler...
            }
        } catch (SimpleProtocolFatalError simpleProtocolFatalError) {
            simpleProtocolFatalError.printStackTrace();
        }


        return null;
    }
}
