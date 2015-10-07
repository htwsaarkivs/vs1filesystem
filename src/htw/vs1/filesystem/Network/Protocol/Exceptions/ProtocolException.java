package htw.vs1.filesystem.Network.Protocol.Exceptions;

import htw.vs1.filesystem.FileSystem.exceptions.FileSystemException;

/**
 * Created by markus on 16.06.15.
 */
public abstract class ProtocolException extends FileSystemException {

    public ProtocolException() {

    }

    public ProtocolException(String message) {
        super(message);
    }


}
