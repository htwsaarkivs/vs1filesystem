package htw.vs1.filesystem.FileSystem.virtual;

import htw.vs1.filesystem.FileSystem.exceptions.CouldNotDeleteException;
import htw.vs1.filesystem.FileSystem.exceptions.FileSystemException;
import htw.vs1.filesystem.FileSystem.exceptions.PermissionDeniedException;
import htw.vs1.filesystem.FileSystemManger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * A LocalFile represents a File in the local filesystem, which
 * is a leaf in a {@link LocalFolder}.
 *
 * Created by felix on 03.06.15.
 */
public class LocalFile extends LocalFSObject implements File {

    /**
     * Creates a new LocalFile.
     *
     * @param name name of the new {@link File}.
     */
    public LocalFile(String name) throws FileSystemException {
        super(name);
    }

    /**
     * Creates a new LocalFile.
     *
     * @param name name of the LocalFile.
     * @param path path of the LocalFile.
     */
    public LocalFile(String name, Path path) throws FileSystemException {
        super(name, path);
    }

    /**
     * Deletes the LocalFSObject itself from
     * the file tree and in the real Filesystem
     *
     */
    @Override
    public void delete() throws FileSystemException {
        if (!getPermissions().isDeleteAllowed()) {
            throw new PermissionDeniedException(this);
        }
        Path path = getPath();
        if (path != null){
            try {
                Files.delete(path);
            } catch (IOException e) {
                if (FileSystemManger.DEBUG) {
                    e.printStackTrace();
                }
                throw new CouldNotDeleteException(this, "", e);
            }
        }
    }

    public java.io.File getPhysicalFile() throws FileSystemException{
        if (null != getPath()) {
            return getPath().toFile();
        }
        return null;
    }
}
