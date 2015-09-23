package htw.vs1.filesystem.Network.Protocol.Replies.Codes;

import htw.vs1.filesystem.FileSystem.exceptions.CouldNotDeleteException;
import htw.vs1.filesystem.FileSystem.exceptions.FileSystemException;
import htw.vs1.filesystem.Network.Protocol.Replies.Type.SimpleProtocolReplyType;
import htw.vs1.filesystem.Network.Protocol.Replies.Type.Type;

/**
 * Created by markus on 21.06.15.
 */

public class ReplyCode407 extends ReplyCode {

    public static final Type REPLY_TYPE = SimpleProtocolReplyType.ERROR;
    public static final int CODE = 407;
    public static final String DESCRIPTION = "";
    public static final String STANDARD_MESSAGE = "COULDNOTDELETE";


    public ReplyCode407() {
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
    public FileSystemException getException() {
        return new CouldNotDeleteException(additionalMessage);
    }
}
