package htw.vs1.filesystem.FileSystem.exceptions;
import htw.vs1.filesystem.FileSystem.virtual.FSObject;

/**
 * This class is part of the package htw.vs1.filesystem.FileSystem.exceptions and project ver1
 * Created by Marc Otting on 14.06.2015.
 * This class provides the following function(s): FSObjectException is the super class
 * in the Exeptionhirarchy of our filesystem
 */
public class FSObjectException extends Exception {

    public static final String OBJECTNOTFOUND = "The Object was not found";
    public static final String COULDNOTDELETE = "Could not delete the file/folder";
    public static final String COULDNOTRENAME = "Could not rename the file/folder";
    public static final String COULDNOTCREATE = "Could not create the file/folder";
    public static final String INVALIDFILENAME = "The name of the file/folder is invalid";
    public static final String UNKNOWNEXCEPTION = "An unknown error occurred\nPlease contact your" +
                                                  "administrator of confidence";

    private FSObject object;

    /**
     * Costructor of a Exeption with 3 Parameters
     * @param object current Object the Exeption is regarding to
     * @param msg Error message of the Expetion
     * @param reason Exeption object to deal with
     */
    public FSObjectException(FSObject object, String msg,Throwable reason){
        System.out.println(object.toString());
        System.out.println(msg);
        System.out.println(reason.toString());
    }
    /**
     * Costructor of a Exeption with 2 Parameters
     * @param msg Error message of the Expetion
     * @param reason Exeption object to deal with
     */
    public FSObjectException(String msg,Throwable reason){
        System.out.println(msg);
        System.out.println(reason.toString());
    }

    /**
     * Costructor of a Exeption with 1 Parameter
     * @param msg Error message of the Expetion
     */
    public FSObjectException (String msg){
        System.out.println(msg);
    }

    public FSObject getObject() {
        return object;
    }
}
