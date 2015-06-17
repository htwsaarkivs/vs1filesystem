package htw.vs1.filesystem.Network.Protocol.Replies.Codes;

import htw.vs1.filesystem.Network.Protocol.Replies.Type.SimpleProtocolReplyType;
import htw.vs1.filesystem.Network.Protocol.Replies.Type.Type;

/**
 * Created by Hendrik on 17.06.2015.
 */
public class ReplyCode230 extends ReplyCode {


    public static final Type REPLY_TYPE = SimpleProtocolReplyType.CONFIRMATION;
    public static final int CODE = 230;
    public static final String DESCRIPTION = "";
    public static final String STANDARD_MESSAGE ="Filesystem Directory successfully set.";


    public ReplyCode230() { this.message = STANDARD_MESSAGE; }


    @Override
    public int getCode() { return CODE; }

    @Override
    public String getDescription() { return DESCRIPTION; }

    @Override
    public Type getReplyType() { return REPLY_TYPE; }
}
