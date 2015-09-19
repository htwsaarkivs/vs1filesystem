package htw.vs1.filesystem.FileSystem.exceptions;

import htw.vs1.filesystem.FileSystem.virtual.FSObject;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCode;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCode404;

/**
 * Created by Felix on 19.09.2015.
 */
public class FSObjectAlreadyExistsException extends FSObjectException {
    public FSObjectAlreadyExistsException(FSObject object, String msg, Throwable reason) {
        super(object, msg, reason);
    }

    public FSObjectAlreadyExistsException(String msg, Throwable reason) {
        super(msg, reason);
    }

    public FSObjectAlreadyExistsException(String msg) {
        super(msg);
    }

    @Override
    public ReplyCode getReplyCode() {
        ReplyCode code = new ReplyCode404();
        code.setAdditionalMessage(getMessage());
        return code;
    }
}
