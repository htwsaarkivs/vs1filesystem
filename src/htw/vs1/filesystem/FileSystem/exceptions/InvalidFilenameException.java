package htw.vs1.filesystem.FileSystem.exceptions;

import htw.vs1.filesystem.FileSystem.virtual.FSObject;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCode;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCode409;

/**
 * Created by Adrian on 19.06.2015.
 * Throws an InvalidFilenameException if the user enters an invalid filename.
 */
public class InvalidFilenameException extends FSObjectException {

    public InvalidFilenameException(FSObject object, String msg, Throwable reason) {
        super(object, msg, reason);
    }

    public InvalidFilenameException(String msg, Throwable reason) {
        super(msg, reason);
    }

    public InvalidFilenameException(String msg) {
        super(msg);
    }

    @Override
    public ReplyCode getReplyCode() {
        ReplyCode code = new ReplyCode409();
        code.setAdditionalMessage(getMessage());
        return code;
    }
}
