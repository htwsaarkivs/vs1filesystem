package htw.vs1.filesystem.FileSystem.exceptions;

import htw.vs1.filesystem.FileSystem.virtual.FSObject;

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
}
