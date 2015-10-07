package htw.vs1.filesystem.FileSystem.exceptions;

import htw.vs1.filesystem.FileSystem.virtual.FSObject;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCode;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCode405;

/**
 * Created by Felix on 29.09.2015.
 */
public class PermissionDeniedException extends FSObjectException {

    private static final String MESSAGE = "Permission denied.";

    public PermissionDeniedException(String message) {
        super(message);
    }

    public PermissionDeniedException(FSObject concerningObject) {
        super(concerningObject, MESSAGE, null);
    }


    @Override
    public ReplyCode getReplyCode() {
        ReplyCode code = new ReplyCode405();
        code.setAdditionalMessage(getMessage());
        return code;
    }
}
