package htw.vs1.filesystem.FileSystem.exceptions;
import htw.vs1.filesystem.FileSystem.FSObject;

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

    /**
     * Costructor of a Exeption with 3 Parameters
     * @param object current Object the Exeption is regarding to
     * @param msg Error message of the Expetion
     * @param ex Exeption object to deal with
     */
    public FSObjectException(FSObject object, String msg,Throwable ex){
        System.out.println(object.toString());
        System.out.println(msg);
        System.out.println(ex.toString());
    }
    /**
     * Costructor of a Exeption with 2 Parameters
     * @param msg Error message of the Expetion
     * @param ex Exeption object to deal wit
     */
    public FSObjectException(String msg,Throwable ex){
        System.out.println(msg);
        System.out.println(ex.toString());
    }

}
