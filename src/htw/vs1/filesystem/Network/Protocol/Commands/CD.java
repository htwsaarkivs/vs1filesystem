package htw.vs1.filesystem.Network.Protocol.Commands;

import htw.vs1.filesystem.FileSystem.exceptions.ObjectNotFoundException;
import htw.vs1.filesystem.FileSystem.virtual.FileSystem;
import htw.vs1.filesystem.Network.Protocol.Protocol;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.*;
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



    public Reply execute(Protocol prot, RequestList requestlist) {
        if(!prot.getState().equals(SimpleProtocolState.AUTHENTICATED))
            return new SimpleProtocolReply(
                    new ReplyCode406(),
                    this);

        if (requestlist.getCurrentElement().numOfArguments() != 1)
            return new SimpleProtocolReply(
                    new ReplyCode401(COMMAND_STRING+" must have exactly one argument"),
                    this);

        String path = requestlist.getCurrentElement().getArguments().get(0);


        //Command execution
        try {

            prot.getFileSystem().changeDirectory(path);

        } catch (ObjectNotFoundException e) {
            return new SimpleProtocolReply(
                    new ReplyCode403(),
                    this);
        }

        return new SimpleProtocolReply
                (new ReplyCode230(),
                        this);
    }
}
