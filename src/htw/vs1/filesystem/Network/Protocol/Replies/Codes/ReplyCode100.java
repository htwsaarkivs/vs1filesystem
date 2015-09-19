package htw.vs1.filesystem.Network.Protocol.Replies.Codes;

import htw.vs1.filesystem.Network.Protocol.Replies.Type.SimpleProtocolReplyType;
import htw.vs1.filesystem.Network.Protocol.Replies.Type.Type;

/**
 * Created by markus on 16.06.15.
 */
public class ReplyCode100 extends ReplyCode {

    public static final Type REPLY_TYPE = SimpleProtocolReplyType.INFORMATIVE;
    public static final int CODE = 100;
    public static final String DESCRIPTION = "Informative reply, which supplies additional information to the end-user.";

    public ReplyCode100() {
        this.message = "";
    }

    public ReplyCode100(String message) {
        this.message = message;
    }

    @Override
    public int getCode() {
        return CODE;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

    @Override
    public Type getReplyType() {
        return REPLY_TYPE;
    }
}
