package htw.vs1.filesystem.Network.Protocol.Commands;

import htw.vs1.filesystem.FileSystem.exceptions.CouldNotDeleteException;
import htw.vs1.filesystem.FileSystem.exceptions.FSObjectException;
import htw.vs1.filesystem.FileSystem.exceptions.ObjectNotFoundException;
import htw.vs1.filesystem.Network.Protocol.Client.ClientProtocol;
import htw.vs1.filesystem.Network.Protocol.Exceptions.SimpleProtocolTerminateConnection;
import htw.vs1.filesystem.Network.Protocol.Replies.ClientReply;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.*;
import htw.vs1.filesystem.Network.Protocol.Replies.ServerReply;
import htw.vs1.filesystem.Network.Protocol.Replies.SimpleServerProtocolReply;
import htw.vs1.filesystem.Network.Protocol.Requests.RequestList;
import htw.vs1.filesystem.Network.Protocol.Server.ServerProtocol;
import htw.vs1.filesystem.Network.Protocol.State.SimpleProtocolState;

/**
 * Created by markus on 19.09.15.
 */
public class RENAME extends AbstractCommand {


    public static final String COMMAND_STRING = "RENAME";

    @Override
    public ServerReply execute(ServerProtocol prot, RequestList requestList) throws SimpleProtocolTerminateConnection {
        if (prot.getState() != SimpleProtocolState.AUTHENTICATED)
            return new SimpleServerProtocolReply(new ReplyCode406(), this);

        if (requestList.getCurrentElement().numOfArguments() != 2)
            return new SimpleServerProtocolReply(
                    new ReplyCode401(COMMAND_STRING + " must have exactly two arguments"),
                    this);
        try {
            prot.getFileSystem().rename(requestList.getCurrentElement().getArguments().get(0), requestList.getCurrentElement().getArguments().get(1));
        } catch (Exception e) {

        }


        return new SimpleServerProtocolReply(new ReplyCode219(), this);
    }

    @Override
    public ClientReply invoke(ClientProtocol prot, String... parameters) throws SimpleProtocolTerminateConnection {
        return null;
    }
}
