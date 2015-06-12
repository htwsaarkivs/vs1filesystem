package htw.vs1.filesystem.FileSystem;

import com.sun.istack.internal.NotNull;

import java.nio.file.Path;

/**
 * This class is part of the package htw.vs1.filesystem.FileSystem and project ver1
 * Created by Marc Otting on 10.06.2015.
 * This class provides the following function(s):
 */
public interface LocalFSObject {
    /**
     * Set the given path as new Path for the FSObject
     * @param path  new Path
     */
    void setPath(@NotNull Path path);

    /**
     * Returns the current path of the FSObject
     * @return current Path
     */
    Path getPath();

    /**
     * Deletes the LocalFSObject itself.
     * If this is a LocalFolder it deletes
     * the directory and its contents recursively.
     */
    void delete();

}
