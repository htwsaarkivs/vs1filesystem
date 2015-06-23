package htw.vs1.filesystem.Network.Protocol.Replies.Codes;

import htw.vs1.filesystem.Network.Protocol.Replies.Type.SimpleProtocolReplyType;
import htw.vs1.filesystem.Network.Protocol.Replies.Type.Type;

/**
 * Created by markus on 16.06.15.
 */
public class ReplyCode210 extends ReplyCode {


    public static final Type REPLY_TYPE = SimpleProtocolReplyType.CONFIRMATION;
    public static final int CODE = 210;
    public static final String DESCRIPTION = "";
    public static final String STANDARD_MESSAGE = "LIST";


    public ReplyCode210() {
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
