package htw.vs1.filesystem.Network.Protocol.Replies.Codes;

import htw.vs1.filesystem.Network.Protocol.Replies.Type.SimpleProtocolReplyType;
import htw.vs1.filesystem.Network.Protocol.Replies.Type.Type;

/**
 * Created by markus on 17.06.15.
 */
public class ReplyCode599 extends ReplyCode {
    public static final Type REPLY_TYPE = SimpleProtocolReplyType.CRITICAL_ERROR;
    public static final int CODE = 599;
    public static final String DESCRIPTION = "";
    public static final String STANDARD_MESSAGE = "ILLEGAL COWBOY";


    public ReplyCode599() {
        this.message = STANDARD_MESSAGE;
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
