package htw.vs1.filesystem.Network.Protocol.Commands;

import htw.vs1.filesystem.FileSystem.exceptions.FileSystemException;
import htw.vs1.filesystem.FileSystem.exceptions.FSRemoteException;
import htw.vs1.filesystem.FileSystem.virtual.LocalFile;
import htw.vs1.filesystem.Network.Protocol.Client.ClientProtocol;
import htw.vs1.filesystem.Network.Protocol.Exceptions.SimpleProtocolFatalError;
import htw.vs1.filesystem.Network.Protocol.Exceptions.SimpleProtocolUnexpectedServerBehaviour;
import htw.vs1.filesystem.Network.Protocol.Replies.ClientReply;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.*;
import htw.vs1.filesystem.Network.Protocol.Replies.ServerReply;
import htw.vs1.filesystem.Network.Protocol.Replies.SimpleClientProtocolReply;
import htw.vs1.filesystem.Network.Protocol.Replies.SimpleServerProtocolReply;
import htw.vs1.filesystem.Network.Protocol.Requests.RequestList;
import htw.vs1.filesystem.Network.Protocol.Server.ServerProtocol;
import htw.vs1.filesystem.Network.Protocol.State.SimpleProtocolState;

/**
 * Created by Hendrik on 21.06.2015.
 */
public class TOUCH extends AbstractCommand {
    public static String COMMAND_STRING = "TOUCH";


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
            prot.getFileSystem().getWorkingDirectory().add(new LocalFile(name));
        } catch(FileSystemException e) {
            return new SimpleServerProtocolReply(e.getReplyCode(), this);
        }



        return new SimpleServerProtocolReply(new ReplyCode219(), this);
    }

    @Override
    public ClientReply invoke(ClientProtocol prot, String... parameters)
            throws FileSystemException
    {
        prot.putLine(getCommandString(COMMAND_STRING, parameters));
        
        //Reply Code von Server ermitteln
        ReplyCode reply = prot.analyzeReply();

        //Wenn sich der ReplyCode in eine Exception übersetzen lässt...
        if (reply.getException() != null) {
            throw reply.getException();
        }

        return null;
    }

}
