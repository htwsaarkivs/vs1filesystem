package htw.vs1.filesystem.FileSystem;

import java.nio.file.FileAlreadyExistsException;
import java.util.LinkedList;

/**
 * A LocalFile represents a File in the local filesystem, which
 * is a leaf in a {@link LocalFolder}.
 *
 * Created by felix on 03.06.15.
 */
public class LocalFile extends File {

private LinkedList<FSObject> contents = new LinkedList<>();
private File parent = null; 
/**
     * Creates a new LocalFile.
     *
     * @param name name of the new {@link File}.
     */
    public LocalFile(String name) {
        super(name);
    }

    
        private void setParent(File parent) {
        checkPrecondition(parent);

        this.parent = parent;
    }
    
    
    
    
    
   public void add(FSObject object) throws FileAlreadyExistsException {
        checkPrecondition(object);

        if (object instanceof LocalFile) {
            ((LocalFile) object).setParent(this);
        }

        contents.add(object);
    }


 private void checkPrecondition(FSObject object) {
        if (!((object instanceof LocalFolder) || (object instanceof LocalFile))) {
            // this case should never happen -> precondition !
            throw new IllegalArgumentException("The new object has to be either a LocalFolder or a LocalFile");
        }

}
 
}
