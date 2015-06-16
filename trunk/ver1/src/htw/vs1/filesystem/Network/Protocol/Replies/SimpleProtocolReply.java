package htw.vs1.filesystem.Network.Protocol.Replies;

import htw.vs1.filesystem.Network.Protocol.Commands.Command;
import htw.vs1.filesystem.Network.Protocol.Protocol;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCode;
import htw.vs1.filesystem.Network.Protocol.Replies.Type.SimpleProtocolReplyType;
import htw.vs1.filesystem.Network.Protocol.SimpleProtocol;

/**
 * Created by markus on 16.06.15.
 */
public class SimpleProtocolReply implements Reply {

    private ReplyCode code;
    private Command command;


    public SimpleProtocolReply(ReplyCode code, Command command) {
        this.code = code;
        this.command = command;
    }

    public boolean terminatesConnection() {
        return code.getReplyType().equals(SimpleProtocolReplyType.CRITICAL_ERROR);
    }

    public void putReply(Protocol prot) {

        prot.putLine(code.getCode() + " " + code.getMessage());
    }




}
