package htw.vs1.filesystem.Network.Protocol.Commands;

import htw.vs1.filesystem.FileSystem.virtual.FSObject;
import htw.vs1.filesystem.FileSystem.virtual.Folder;
import htw.vs1.filesystem.Network.Protocol.Client.ClientProtocol;
import htw.vs1.filesystem.Network.Protocol.Exceptions.SimpleProtocolTerminateConnection;
import htw.vs1.filesystem.Network.Protocol.Replies.ClientReply;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCode210;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCode219;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCode406;

import htw.vs1.filesystem.Network.Protocol.Replies.ServerReply;
import htw.vs1.filesystem.Network.Protocol.Replies.SimpleServerProtocolReply;
import htw.vs1.filesystem.Network.Protocol.Requests.RequestList;
import htw.vs1.filesystem.Network.Protocol.Server.ServerProtocol;
import htw.vs1.filesystem.Network.Protocol.State.SimpleProtocolState;

import java.util.List;

/**
 * Created by markus on 13.06.15.
 */
public class LS extends AbstractCommand {
    public static String COMMAND_STRING = "LS";

    public ServerReply execute(ServerProtocol prot, RequestList requestList) {
        if(!prot.getState().equals(SimpleProtocolState.AUTHENTICATED))
            return new SimpleServerProtocolReply(new ReplyCode406(), this);


        List<FSObject> list = prot.getFileSystem().getWorkingDirectory().getContent();

        StringBuffer buf = new StringBuffer();

        for (FSObject obj: list) {
            boolean isFolder = obj instanceof Folder;
            boolean isFile = !isFolder;

            buf.append(obj.getName());
            if (isFolder) {
                buf.append("\t [FOLDER]");
            }
            else buf.append("\t [FILE]");
            buf.append("\n");
        }


        new SimpleServerProtocolReply(new ReplyCode210(), this).putReply(prot);
        prot.putLine(buf.toString());

        return new SimpleServerProtocolReply(new ReplyCode219(), this);
    }

    @Override
    public ClientReply invoke(ClientProtocol port) throws SimpleProtocolTerminateConnection {
        return null;
    }
}
