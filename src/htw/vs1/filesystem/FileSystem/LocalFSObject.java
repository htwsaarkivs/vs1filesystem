package htw.vs1.filesystem.FileSystem;

import com.sun.istack.internal.NotNull;

import java.nio.file.Path;

/**
 * This class is part of the package htw.vs1.filesystem.FileSystem and project ver1
 * Created by Marc Otting on 10.06.2015.
 * This class provides the following function(s):
 */
public abstract class LocalFSObject extends AbstractFSObject {
    /**
     * Creates a new FSObject.
     *
     * @param name name of the FSObject.
     */
    public LocalFSObject(String name) {
        super(name);
    }

    /**
     * Set the given path as new Path for the FSObject
     * @param path  new Path
     */
    abstract void setPath(@NotNull Path path);

    /**
     * Returns the current path of the FSObject
     * @return current Path
     */
    abstract Path getPath();

    /**
     * Deletes the LocalFSObject itself.
     * If this is a LocalFolder it deletes
     * the directory and its contents recursively.
     */
    abstract void delete();

}
