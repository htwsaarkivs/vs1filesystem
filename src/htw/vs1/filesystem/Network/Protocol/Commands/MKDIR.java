package htw.vs1.filesystem.Network.Protocol.Commands;

import htw.vs1.filesystem.FileSystem.exceptions.FSObjectException;
import htw.vs1.filesystem.FileSystem.virtual.LocalFolder;
import htw.vs1.filesystem.Network.Protocol.Client.ClientProtocol;
import htw.vs1.filesystem.Network.Protocol.Exceptions.SimpleProtocolTerminateConnection;
import htw.vs1.filesystem.Network.Protocol.Replies.ClientReply;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCode219;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCode401;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCode406;
import htw.vs1.filesystem.Network.Protocol.Replies.ServerReply;
import htw.vs1.filesystem.Network.Protocol.Replies.SimpleServerProtocolReply;
import htw.vs1.filesystem.Network.Protocol.Requests.RequestList;
import htw.vs1.filesystem.Network.Protocol.Server.ServerProtocol;
import htw.vs1.filesystem.Network.Protocol.State.SimpleProtocolState;

import java.nio.file.FileAlreadyExistsException;

/**
 * Created by Hendrik on 21.06.2015.
 */
public class MKDIR extends AbstractCommand {
    public static String COMMAND_STRING = "MKDIR";


    public ServerReply execute(ServerProtocol prot, RequestList requestlist) {
        if (!prot.getState().equals(SimpleProtocolState.AUTHENTICATED))
            return new SimpleServerProtocolReply(
                    new ReplyCode406(),
                    this);


        if (requestlist.getCurrentElement().numOfArguments() != 1)
            return new SimpleServerProtocolReply(
                    new ReplyCode401(COMMAND_STRING + " must have exactly one argument"),
                    this);


        String name = requestlist.getCurrentElement().getArguments().get(0);

        try {
            prot.getFileSystem().getWorkingDirectory().add(new LocalFolder(name));
        } catch(FileAlreadyExistsException | FSObjectException e) {
            return new SimpleServerProtocolReply(new ReplyCode406(), this);
        }



        return new SimpleServerProtocolReply(new ReplyCode219(), this);
    }

    @Override
    public ClientReply invoke(ClientProtocol prot, String... parameters) throws SimpleProtocolTerminateConnection {
        return null;
    }

}
