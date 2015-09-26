package htw.vs1.filesystem.Network.Protocol.Commands;

import htw.vs1.filesystem.FileSystem.exceptions.FileSystemException;
import htw.vs1.filesystem.Network.Protocol.Client.ClientProtocol;
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
 * Created by markus on 06.09.15.
 */
public class SEARCH extends AbstractCommand {


    public static String COMMAND_STRING = "SEARCH";

    @Override
    public ServerReply execute(ServerProtocol prot, RequestList requestList) {
        if (prot.getState() != SimpleProtocolState.AUTHENTICATED)
            return new SimpleServerProtocolReply(new ReplyCode406(), this);

        if (requestList.getCurrentElement().numOfArguments() != 1)
            return new SimpleServerProtocolReply(
                    new ReplyCode401(COMMAND_STRING + " must have exactly one arguments"),
                    this);

        prot.getFileSystem().search(
                requestList
                        .getCurrentElement()
                        .getArguments()
                        .get(0)
        );


        return new SimpleServerProtocolReply(new ReplyCode219(), this);

    }

    @Override
    public ClientReply invoke(ClientProtocol prot, String... parameters) throws FileSystemException {
        prot.putLine(COMMAND_STRING);
        ReplyCode reply = prot.analyzeReply();

        if (reply.getException() != null) {
            throw reply.getException();
        }

        if (reply.getCode() != ReplyCode210.CODE) {
            throw new SimpleProtocolUnexpectedServerBehaviour();
        }


        SimpleClientProtocolReply result = new SimpleClientProtocolReply();
        return LS.getReplyData(prot, result);
    }
}
