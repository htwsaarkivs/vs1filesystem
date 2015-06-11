package htw.vs1.filesystem.FileSystem;

import com.sun.istack.internal.NotNull;

import java.nio.file.Path;

/**
 * This class is part of the package htw.vs1.filesystem.FileSystem and project ver1
 * Created by Marc Otting on 10.06.2015.
 * This class provides the following function(s):
 */
public interface LocalFSObject {

    //TODO to comment
    void setPath(@NotNull Path path);
    //TODO to comment
    Path getPath();

    /**
     * Deletes the LocalFSObject itself.
     * If this is a LocalFolder it deletes
     * the directory and contents recursively.
     */
    void delete();

}
