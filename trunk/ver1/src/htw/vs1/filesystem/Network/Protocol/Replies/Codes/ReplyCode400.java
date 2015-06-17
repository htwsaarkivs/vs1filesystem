package htw.vs1.filesystem.Network.Protocol.Replies.Codes;

import htw.vs1.filesystem.Network.Protocol.Replies.Type.SimpleProtocolReplyType;
import htw.vs1.filesystem.Network.Protocol.Replies.Type.Type;

/**
 * Created by Hendrik on 17.06.2015.
 */
public class ReplyCode400 extends ReplyCode {


    public static final Type REPLY_TYPE = SimpleProtocolReplyType.ERROR;
    public static final int CODE = 400;
    public static final String DESCRIPTION = "";
    public static final String STANDARD_MESSAGE = "Invalid Command.";


    public ReplyCode400() { this.message = STANDARD_MESSAGE; }


    @Override
    public int getCode() { return CODE; }

    @Override
    public String getDescription() { return DESCRIPTION; }

    @Override
    public Type getReplyType() { return REPLY_TYPE; }
}
