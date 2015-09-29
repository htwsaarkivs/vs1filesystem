package htw.vs1.filesystem.FileSystem.exceptions;

import htw.vs1.filesystem.FileSystem.virtual.FSObject;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCode;

/**
 * Created by Felix on 23.09.2015.
 */
public abstract class FSObjectException extends FileSystemException {

    public static final String OBJECTNOTFOUND = "The Object was not found";
    public static final String COULDNOTDELETE = "Could not delete the file/folder";
    public static final String COULDNOTRENAME = "Could not rename the file/folder";
    public static final String COULDNOTCREATE = "Could not create the file/folder";
    public static final String INVALIDFILENAME = "The name of the file/folder is invalid";
    public static final String UNKNOWNEXCEPTION = "An unknown error occurred\nPlease contact your" +
            "administrator of confidence";



    private FSObject object;

    /**
     * Constructor of a Exception with 3 Parameters
     * @param object current Object the Exception is regarding to
     * @param msg Error message of the Exception
     * @param reason Exception object to deal with
     */
    public FSObjectException(FSObject object, String msg, Throwable reason) {
        super(msg, reason);
        this.object = object;
        //TODO: Throwable!!!
    }
    /**
     * Constructor of a Exception with 2 Parameters
     * @param msg Error message of the Exception
     * @param reason Exception object to deal with
     */
    public FSObjectException(String msg, Throwable reason){
        super(msg, reason);
        //TODO: Throwable!!!
    }

    /**
     * Constructor of a Exception with 1 Parameter
     * @param msg Error message of the Exception
     */
    public FSObjectException(String msg){
        super(msg);
    }

    public FSObjectException() {
        super();
    }

    public FSObject getObject() {
        return object;
    }

}
