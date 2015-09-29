package htw.vs1.filesystem.FileSystem.exceptions;

import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCode;

/**
 * This class is part of the package htw.vs1.filesystem.FileSystem.exceptions and project ver1
 * Created by Marc Otting on 14.06.2015.
 * This class provides the following function(s): FileSystemException is the super class
 * in the Exception hierarchy of our filesystem
 */
public abstract class FileSystemException extends Exception {

    /**
     * Constructor of a Exception with 2 Parameters
     * @param msg Error message of the Exception
     * @param reason Exception object to deal with
     */
    public FileSystemException(String msg, Throwable reason){
        super(msg, reason);
    }

    /**
     * Constructor of a Exception with 1 Parameter
     * @param msg Error message of the Exception
     */
    public FileSystemException(String msg){
        super(msg);
    }

    public FileSystemException() {
        super();
    }

    public abstract ReplyCode getReplyCode();
}
