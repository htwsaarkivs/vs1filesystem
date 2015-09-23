package htw.vs1.filesystem.FileSystem.exceptions;

import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCode;

/**
 * This class is part of the package htw.vs1.filesystem.FileSystem.exceptions and project ver1
 * Created by Marc Otting on 14.06.2015.
 * This class provides the following function(s): FileSystemException is the super class
 * in the Exeptionhirarchy of our filesystem
 */
public abstract class FileSystemException extends Exception {

    /**
     * Costructor of a Exeption with 2 Parameters
     * @param msg Error message of the Expetion
     * @param reason Exeption object to deal with
     */
    public FileSystemException(String msg, Throwable reason){
        super(msg, reason);
    }

    /**
     * Costructor of a Exeption with 1 Parameter
     * @param msg Error message of the Expetion
     */
    public FileSystemException(String msg){
        super(msg);
    }

    public FileSystemException() {
        super();
    }

    public abstract ReplyCode getReplyCode();
}
