package htw.vs1.filesystem.Network.Protocol.Commands;


import htw.vs1.filesystem.Network.Protocol.Protocol;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCode210;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCode219;
import htw.vs1.filesystem.Network.Protocol.Replies.Reply;
import htw.vs1.filesystem.Network.Protocol.Replies.SimpleProtocolReply;
import htw.vs1.filesystem.Network.Protocol.Requests.RequestList;
import java.util.Set;

/**
 * Created by markus on 12.06.15.
 */
public class GETFEAT extends AbstractCommand {
    public static String COMMAND_STRING = "GETFEAT";


    public Reply execute(Protocol prot, RequestList requestList) {

        Set<String> set = new CommandFactory().getRegisteredCommands();

        StringBuffer buf = new StringBuffer();

        for (String obj : set) {
            buf.append("[" + obj + "]\n");
        }

        new SimpleProtocolReply(new ReplyCode210(), this).putReply(prot);

        prot.putLine(buf.toString());

        return new SimpleProtocolReply(new ReplyCode219(), this);
    }
}
