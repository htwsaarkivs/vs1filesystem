package htw.vs1.filesystem.FileSystem.exceptions;

import htw.vs1.filesystem.FileSystem.FSObject;

/**
 * Exception indicating that the requested {@link FSObject}
 * was not found.
 *
 * Created by felix on 03.06.15.
 */

public class ObjectNotFoundException extends FSObjectException {

    /**
     * Costructor of a Exeption with 3 Parameters
     *
     * @param object current Object the Exeption is regarding to
     * @param msg    Error message of the Expetion
     * @param ex     Exeption object to deal with
     */
    public ObjectNotFoundException(FSObject object, String msg, Throwable ex) {
        super(object, msg, ex);
    }

    /**
     * Costructor of a Exeption with 2 Parameters
     *
     * @param msg Error message of the Expetion
     * @param ex  Exeption object to deal wit
     */
    public ObjectNotFoundException(String msg, Throwable ex) {
        super(msg, ex);
    }

    /**
     * Costructor of a Exeption with 1 Parameter
     *
     * @param msg Error message of the Expetion
     */
    public ObjectNotFoundException(String msg) {
        super(msg);
    }
}
