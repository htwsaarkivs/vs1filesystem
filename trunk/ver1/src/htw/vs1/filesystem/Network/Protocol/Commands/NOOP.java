package htw.vs1.filesystem.Network.Protocol.Commands;

import htw.vs1.filesystem.FileSystem.exceptions.FileSystemException;
import htw.vs1.filesystem.Network.Protocol.Client.ClientProtocol;
import htw.vs1.filesystem.Network.Protocol.Replies.ClientReply;
import htw.vs1.filesystem.Network.Protocol.Replies.ServerReply;
import htw.vs1.filesystem.Network.Protocol.Requests.RequestList;
import htw.vs1.filesystem.Network.Protocol.Server.ServerProtocol;

/**
 * Created by felix on 21.09.15.
 */
public class NOOP extends AbstractCommand {

    @Override
    public ServerReply execute(ServerProtocol prot, RequestList requestList) {
        return null;
    }

    @Override
    public ClientReply invoke(ClientProtocol prot, String... parameters) throws FileSystemException {
        return null;
    }
}
