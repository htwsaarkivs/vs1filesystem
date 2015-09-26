package htw.vs1.filesystem.Network.Protocol.Commands;

import htw.vs1.filesystem.FileSystem.exceptions.FileSystemException;
import htw.vs1.filesystem.Network.Protocol.Client.ClientProtocol;
import htw.vs1.filesystem.Network.Protocol.Client.SimpleClientProtocol;
import htw.vs1.filesystem.Network.Protocol.Exceptions.SimpleProtocolFatalError;
import htw.vs1.filesystem.Network.Protocol.Exceptions.SimpleProtocolUnexpectedServerBehaviour;
import htw.vs1.filesystem.Network.Protocol.Replies.ClientReply;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCode;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCode210;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCode219;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCode406;
import htw.vs1.filesystem.Network.Protocol.Replies.ServerReply;
import htw.vs1.filesystem.Network.Protocol.Replies.SimpleClientProtocolReply;
import htw.vs1.filesystem.Network.Protocol.Replies.SimpleServerProtocolReply;
import htw.vs1.filesystem.Network.Protocol.Requests.RequestList;
import htw.vs1.filesystem.Network.Protocol.Server.ServerProtocol;
import htw.vs1.filesystem.Network.Protocol.State.SimpleProtocolState;
import sun.java2d.pipe.SpanShapeRenderer;

/**
 * Created by Hendrik on 18.06.2015.
 */
public class PWD extends AbstractCommand {
    public static String COMMAND_STRING = "PWD";

    public ServerReply execute(ServerProtocol prot, RequestList requestList) {
        if (!prot.getState().equals(SimpleProtocolState.AUTHENTICATED))
            return new SimpleServerProtocolReply(new ReplyCode406(), this);


        String ret = prot.getFileSystem().printWorkingDirectory();
        new SimpleServerProtocolReply(new ReplyCode210(), this).putReply(prot);
        prot.putLine(ret);
        return new SimpleServerProtocolReply(new ReplyCode219(), this);
    }

    @Override
    public ClientReply invoke(ClientProtocol prot, String... parameters) throws FileSystemException {

        String currentPath;

        prot.putLine(getCommandString(COMMAND_STRING, parameters));
        //Anfang LISTE
        ReplyCode reply = prot.analyzeReply();

        if (reply.getException() != null) {
            throw reply.getException();
        }

        if (reply.getCode() != ReplyCode210.CODE) {
            throw new SimpleProtocolUnexpectedServerBehaviour();
        }

        //INHALT
        prot.readLine();
        currentPath = prot.getCurrentLine();

        //ENDE LISTE
        reply = prot.analyzeReply();

        if (reply.getException() != null) {
            throw reply.getException();
        }

        if (reply.getCode() != ReplyCode219.CODE) {
            throw new SimpleProtocolUnexpectedServerBehaviour();
        }

        SimpleClientProtocolReply clientReply = new SimpleClientProtocolReply();
        clientReply.feedLine(currentPath);

        return clientReply;
    }
}
