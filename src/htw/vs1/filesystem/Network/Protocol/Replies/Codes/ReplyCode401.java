package htw.vs1.filesystem.Network.Protocol.Replies.Codes;

import htw.vs1.filesystem.FileSystem.exceptions.FSObjectException;
import htw.vs1.filesystem.FileSystem.exceptions.FSRemoteException;
import htw.vs1.filesystem.Network.Protocol.Replies.Type.SimpleProtocolReplyType;
import htw.vs1.filesystem.Network.Protocol.Replies.Type.Type;

/**
 * Created by markus on 17.06.15.
 */
public class ReplyCode401 extends ReplyCode {

    public static final Type REPLY_TYPE = SimpleProtocolReplyType.ERROR;
    public static final int CODE = 401;
    public static final String DESCRIPTION = "";
    public static final String STANDARD_MESSAGE = "ILLEGAL ARGUMENTS";


    public ReplyCode401(String reason) {
        this.message = STANDARD_MESSAGE + ": "+reason;
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

    @Override
    public FSObjectException getException() {
        return new FSRemoteException(additionalMessage);
    }
}
