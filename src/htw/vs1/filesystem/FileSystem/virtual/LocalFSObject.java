package htw.vs1.filesystem.FileSystem.virtual;

import com.sun.istack.internal.Nullable;
import htw.vs1.filesystem.FileSystem.exceptions.*;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Represents a object of our file system tree which is located
 * on a *this* computer
 *
 * Created by Marc Otting on 10.06.2015.
 */
public abstract class LocalFSObject extends AbstractFSObject {

    /**
     * Path of the Local Folder in the real Filesystem
     */
    private Path path;

    /**
     * Reference to the {@link Folder} containing this one.
     * May be {@link null}, iff this is the root folder.
     */
    private Folder parent = null;

    /**
     * The Permissions of this LocalFSObject.
     */
    private Permissions permissions = new Permissions(true);

    /**
     * Creates a new FSObject.
     *
     * @param name name of the FSObject.
     * @param path path of the FSObject.
     */
    public LocalFSObject(String name, Path path) throws FileSystemException {
        super(name);
        setPath(path);
    }

    /**
     * Creates a new FSObject.
     *
     * @param name name of the FSObject.
     */
    public LocalFSObject(String name) throws FileSystemException {
        super(name);
    }

    public Permissions getPermissions() {
        return permissions;
    }

    /**
     * Set the new name of a FSObject and modifies its path
     * @param name new name of this object.
     * @throws FileAlreadyExistsException
     */
    @Override
    public void setName(String name) throws FileSystemException {
        if (permissions != null && !getPermissions().isRenameAllowed()) {
            throw new PermissionDeniedException(this);
        }

        if (getParentFolder() != null && getParentFolder().exists(name)) {
            throw new FSObjectAlreadyExistsException("The FSObject " + name + " already exists in this folder");
        }

        // the root of the local folder is always called "local" by default and not
        // the correct name of the physical folder so it doesn't make sense to rename
        // the physical one.
        if (null != getPath() && !( getParentFolder() instanceof MountPointFolder)) {
            try {
                Path newPath = getPath().resolveSibling(name);
                Files.move(getPath(), newPath);
                setPath(newPath);
            } catch (IOException e) {
                if (FileSystemManager.DEBUG) {
                    e.printStackTrace();
                }
                throw new CouldNotRenameException(this, FSObjectException.COULDNOTRENAME, e);
            }
        }


        super.setName(name); // It is important to set the name after checking if it exists!
    }

    /**
     * Get the parent {@link Folder} containing this Folder.
     * Can be {@link null}, iff this is the root-Folder.
     *
     * @return the parent {@link Folder} or {@code null} iff this is the root-Folder.
     */
    @Override
    public Folder getParentFolder() {
        return parent;
    }

    /**
     * Sets the parent {@link Folder} containing this FSObject. Can be
     * {@link null}, iff this is the root-Folder.
     * Precondition: the new object has to be either a
     * {@link LocalFolder}.
     *
     * @param parentFolder the parent {@link Folder} or {@code null} iff this is the
     *                     root-Folder.
     */
    @Override
    public void setParentFolder(@Nullable Folder parentFolder) {
        if (null != parentFolder) {
            checkPrecondition(parentFolder);
        }

        this.parent = parentFolder;
    }

    /**
     * Returns the current path of the FSObject
     * @return current Path
     */
    public Path getPath() {
        return path;
    }

    /**
     * Set the given path as new Path for the FSObject
     * @param path  new Path
     */
    public void setPath(Path path) {
        this.path = path;
    }

    /**
     * Deletes the LocalFSObject itself.
     * If this is a LocalFolder which is not empty
     * it cannot be deleted.
     */
    abstract void delete() throws FileSystemException;

    @Override
    public void toggleLock() {
        permissions.toggleLock();
    }

    /**
     * Checks the precondition that the given objects has to be a
     * {@link LocalFolder} or a {@link LocalFile}, which means it has
     * to be a {@link LocalFSObject}.
     *
     * @param object {@link FSObject} which has to match the precondition.
     */
    protected void checkPrecondition(FSObject object) {
        if (!(object instanceof LocalFSObject)) {
            // this case should never happen -> precondition !
            //throw new IllegalArgumentException("The new object has to be a LocalFSObject");
        }
    }

}
