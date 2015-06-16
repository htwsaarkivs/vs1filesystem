package htw.vs1.filesystem.Network.Protocol.Replies.Codes;

import htw.vs1.filesystem.Network.Protocol.Replies.Type.SimpleProtocolReplyType;
import htw.vs1.filesystem.Network.Protocol.Replies.Type.Type;

/**
 * Created by markus on 16.06.15.
 */
public abstract class ReplyCode {

    protected String message;


    public String getMessage() {
        return this.message;
    }

    public abstract int getCode();
    public abstract String getDescription();
    public abstract Type getReplyType();



}

