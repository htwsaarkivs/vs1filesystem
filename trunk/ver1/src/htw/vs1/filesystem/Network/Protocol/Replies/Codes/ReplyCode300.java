package htw.vs1.filesystem.Network.Protocol.Replies.Codes;

import htw.vs1.filesystem.Network.Protocol.Replies.SimpleProtocolReply;
import htw.vs1.filesystem.Network.Protocol.Replies.Type.SimpleProtocolReplyType;
import htw.vs1.filesystem.Network.Protocol.Replies.Type.Type;

/**
 * Created by markus on 17.06.15.
 */
public class ReplyCode300 extends ReplyCode {


    public static final Type REPLY_TYPE = SimpleProtocolReplyType.PROMPT;
    public static final int CODE = 300;
    public static final String DESCRIPTION = "";
    public static final String STANDARD_MESSAGE = "LOGIN REQUIRED";


    public ReplyCode300(String user) {
        this.message = STANDARD_MESSAGE + " FOR USER " + user;
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
