package htw.vs1.filesystem.FileSystem.exceptions;

import htw.vs1.filesystem.FileSystem.virtual.FSObject;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCode;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCode403;

/**
 * This class is part of the package htw.vs1.filesystem.FileSystem.exceptions and project ver1
 * Created by Marc Otting on 14.06.2015.
 * This class provides the following function(s):
 */
public class CouldNotRenameException extends FSObjectException {
    /**
     * Costructor of a Exception with 3 Parameters
     *
     * @param object current Object the Exception is regarding to
     * @param msg    Error message of the Exception
     * @param ex     Exception object to deal with
     */
    public CouldNotRenameException(FSObject object, String msg, Throwable ex) {
        super(object, msg, ex);
    }

    /**
     * Costructor of a Exeption with 2 Parameters
     *
     * @param msg Error message of the Excpetion
     * @param ex  Exeption object to deal wit
     */
    public CouldNotRenameException(String msg, Throwable ex) {
        super(msg, ex);
    }

    /**
     * Costructor of a Exception with 1 Parameter
     *
     * @param msg Error message of the Exception
     */
    public CouldNotRenameException(String msg) {
        super(msg);
    }

    @Override
    public ReplyCode getReplyCode() {
        ReplyCode code = new ReplyCode403();
        code.setAdditionalMessage(getMessage());
        return code;
    }
}
