package htw.vs1.filesystem.Network.Protocol.Commands;

import htw.vs1.filesystem.FileSystem.exceptions.FileSystemException;
import htw.vs1.filesystem.Network.Protocol.Client.ClientProtocol;
import htw.vs1.filesystem.Network.Protocol.Replies.ClientReply;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCode;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCode100;
import htw.vs1.filesystem.Network.Protocol.Replies.ServerReply;
import htw.vs1.filesystem.Network.Protocol.Replies.SimpleServerProtocolReply;
import htw.vs1.filesystem.Network.Protocol.Requests.RequestList;
import htw.vs1.filesystem.Network.Protocol.Server.ServerProtocol;

/**
 * Created by felix on 21.09.15.
 */
public class NOOP extends AbstractCommand {

    public static String COMMAND_STRING = "NOOP";

    @Override
    public ServerReply execute(ServerProtocol prot, RequestList requestList) {
        return new SimpleServerProtocolReply(new ReplyCode100("NO OPERATION"), this);
    }

    @Override
    public ClientReply invoke(ClientProtocol prot, String... parameters) throws FileSystemException {
        prot.putLine(getCommandString(COMMAND_STRING, parameters));
        ReplyCode reply = prot.analyzeReply();
        return null;
    }
}
