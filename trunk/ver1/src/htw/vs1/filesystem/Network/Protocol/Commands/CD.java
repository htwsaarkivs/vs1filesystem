package htw.vs1.filesystem.Network.Protocol.Commands;

import htw.vs1.filesystem.FileSystem.exceptions.ObjectNotFoundException;
import htw.vs1.filesystem.FileSystem.virtual.FileSystem;
import htw.vs1.filesystem.Network.Protocol.Protocol;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCode219;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCode406;
import htw.vs1.filesystem.Network.Protocol.Replies.Reply;
import htw.vs1.filesystem.Network.Protocol.Replies.SimpleProtocolReply;
import htw.vs1.filesystem.Network.Protocol.Requests.Request;
import htw.vs1.filesystem.Network.Protocol.Requests.RequestList;
import htw.vs1.filesystem.Network.Protocol.State.SimpleProtocolState;

/**
 * Created by Hendrik on 17.06.2015.
 */
public class CD extends AbstractCommand {
    public static String COMMAND_STRING = "CD";


    protected static boolean isValid(Request req) {
        if (!req.getCommandString().equals(COMMAND_STRING)) return false;
        if (req.numOfArguments() != 1) return false;
        return true;
    }


    public Reply execute(Protocol prot, RequestList requestlist) {
        if(!prot.getState().equals(SimpleProtocolState.AUTHENTICATED)) return new SimpleProtocolReply(new ReplyCode406(), this);
        String path = requestlist.getCurrentElement().getArguments().get(0);

        try {
            prot.getFileSystem().changeDirectory(path);
        } catch (ObjectNotFoundException e) {
            e.printStackTrace();
        }

        return new SimpleProtocolReply(new ReplyCode219(), this);
    }
}
