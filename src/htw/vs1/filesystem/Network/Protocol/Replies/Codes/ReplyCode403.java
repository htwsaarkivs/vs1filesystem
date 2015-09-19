package htw.vs1.filesystem.Network.Protocol.Replies.Codes;

import htw.vs1.filesystem.FileSystem.exceptions.CouldNotRenameException;
import htw.vs1.filesystem.FileSystem.exceptions.FSObjectException;
import htw.vs1.filesystem.Network.Protocol.Replies.Type.SimpleProtocolReplyType;
import htw.vs1.filesystem.Network.Protocol.Replies.Type.Type;

/**
 * Created by markus on 19.09.15.
 */
public class ReplyCode403 extends ReplyCode {


    public static final Type REPLY_TYPE = SimpleProtocolReplyType.ERROR;
    public static final int CODE = 403;
    public static final String DESCRIPTION = "";
    public static final String STANDARD_MESSAGE = "FILE OR FOLDER DOES NOT EXIST";

    public  ReplyCode403() {
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

    @Override
    public FSObjectException getException() {
        return new CouldNotRenameException(additionalMessage);
    }

}
