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
     * Costructor of a Exception with 2 Parameters
     *
     * @param objectName name of the current Object the Exeption is regarding to
     * @param msg    Error message of the Expetion
     */
    public ObjectNotFoundException(String objectName, String msg) {
        super(msg);
        this.objectName = objectName;
    }

    /**
     * Costructor of a Exception with 2 Parameters
     *
     * @param msg Error message of the Expetion
     * @param ex  Exeption object to deal wit
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
