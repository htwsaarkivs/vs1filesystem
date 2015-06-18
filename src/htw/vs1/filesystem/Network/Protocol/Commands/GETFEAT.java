package htw.vs1.filesystem.Network.Protocol.Commands;


import htw.vs1.filesystem.Network.Protocol.Protocol;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCode200;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCode210;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCode219;
import htw.vs1.filesystem.Network.Protocol.Replies.Reply;
import htw.vs1.filesystem.Network.Protocol.Replies.SimpleProtocolReply;
import htw.vs1.filesystem.Network.Protocol.Requests.RequestList;

/**
 * Created by markus on 12.06.15.
 */
public class GETFEAT extends AbstractCommand {
    public static String COMMAND_STRING = "GETFEAT";


    public Reply execute(Protocol prot, RequestList requestList) {
        prot.putLine("This Server supports amazingly many features");
        prot.putLine("Current State: "+prot.getState().toString());

        new SimpleProtocolReply(new ReplyCode210(), this).putReply(prot);

        prot.putLine(new CommandFactory().getRegisteredCommands().toString());

        return new SimpleProtocolReply(new ReplyCode219(), this);
    }


}
