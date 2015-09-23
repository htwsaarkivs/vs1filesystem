package htw.vs1.filesystem.FileSystem.exceptions;

import htw.vs1.filesystem.FileSystem.virtual.FSObject;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCode;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCode407;

/**
 * This class is part of the package htw.vs1.filesystem.FileSystem.exceptions and project ver1
 * Created by Marc Otting on 14.06.2015.
 * This class provides the following function(s):
 */
public class CouldNotDeleteException extends FSObjectException {
    /**
     * Costructor of a Exeption with 3 Parameters
     *
     * @param object current Object the Exeption is regarding to
     * @param msg    Error message of the Expetion
     * @param ex     Exeption object to deal with
     */
    public CouldNotDeleteException(FSObject object, String msg, Throwable ex) {
        super(object, msg, ex);
    }

    /**
     * Costructor of a Exeption with 2 Parameters
     *
     * @param msg Error message of the Expetion
     * @param ex  Exeption object to deal wit
     */
    public CouldNotDeleteException(String msg, Throwable ex) {
        super(msg, ex);
    }

    /**
     * Costructor of a Exeption with 1 Parameter
     *
     * @param msg Error message of the Expetion
     */
    public CouldNotDeleteException(String msg) {
        super(msg);
    }

    @Override
    public ReplyCode getReplyCode() {
        ReplyCode code = new ReplyCode407();
        code.setAdditionalMessage(getMessage());
        return code;
    }
}
