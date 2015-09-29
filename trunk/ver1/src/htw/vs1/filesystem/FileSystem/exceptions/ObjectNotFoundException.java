package htw.vs1.filesystem.FileSystem.exceptions;

import htw.vs1.filesystem.FileSystem.virtual.FSObject;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCode;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCode402;

/**
 * Exception indicating that the requested {@link FSObject}
 * was not found.
 *
 * Created by felix on 03.06.15.
 */

public class ObjectNotFoundException extends FSObjectException {

    private String objectName;

    public ObjectNotFoundException(String msg) {
        super(msg);
    }

    /**
     * Constructor of a Exception with 2 Parameters
     *
     * @param objectName name of the current Object the Exception is regarding to
     * @param msg    Error message of the Exception
     */
    public ObjectNotFoundException(String objectName, String msg) {
        super(msg);
        this.objectName = objectName;
    }

    /**
     * Constructor of a Exception with 2 Parameters
     *
     * @param msg Error message of the Exception
     * @param ex  Exception object to deal wit
     */
    public ObjectNotFoundException(String msg, Throwable ex) {
        super(msg, ex);
    }

    public String getObjectName() {
        return objectName;
    }

    @Override
    public ReplyCode getReplyCode() {
        ReplyCode code = new ReplyCode402();
        code.setAdditionalMessage(getMessage());
        return code;
    }
}
