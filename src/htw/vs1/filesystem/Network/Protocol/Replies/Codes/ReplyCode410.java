package htw.vs1.filesystem.Network.Protocol.Replies.Codes;

import htw.vs1.filesystem.Network.Protocol.Replies.Type.SimpleProtocolReplyType;
import htw.vs1.filesystem.Network.Protocol.Replies.Type.Type;

/**
 * Created by markus on 19.09.15.
 */
public class ReplyCode410 extends ReplyCode {


    public static final Type REPLY_TYPE = SimpleProtocolReplyType.ERROR;
    public ReplyCode410() {
        this.message = "LOGIN FAILED";
    }


    @Override
    public int getCode() {
        return 0;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public Type getReplyType() {
        return null;
    }
}
