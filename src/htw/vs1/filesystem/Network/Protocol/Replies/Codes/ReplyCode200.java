package htw.vs1.filesystem.Network.Protocol.Replies.Codes;

import htw.vs1.filesystem.Network.Protocol.Replies.Type.SimpleProtocolReplyType;
import htw.vs1.filesystem.Network.Protocol.Replies.Type.Type;

/**
 * Created by markus on 16.06.15.
 */
public class ReplyCode200 extends ReplyCode{

    public static final Type REPLY_TYPE = SimpleProtocolReplyType.CONFIRMATION;
    public static final int CODE = 200;
    public static final String DESCRIPTION = "Informative reply, which supplies additional information to the end-user.";
    public static final String STANDARD_MESSAGE = "SERVER READY";

    /**
     * Generates a new 200-Reply
     * @param version specifies Server version
     */
    public ReplyCode200(double version) {
        this.message = STANDARD_MESSAGE+" VERSION: "+version;
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