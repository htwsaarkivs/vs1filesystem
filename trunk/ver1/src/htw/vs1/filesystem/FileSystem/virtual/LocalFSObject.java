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
     * Creates a new FSObject.
     *
     * @param name name of the FSObject.
     * @param path path of the FSObject.
     */
    public LocalFSObject(String name, Path path) throws FileAlreadyExistsException, InvalidFilenameException, CouldNotRenameExeption {
        super(name);
        setPath(path);
    }

    /**
     * Creates a new FSObject.
     *
     * @param name name of the FSObject.
     */
    public LocalFSObject(String name) throws FileAlreadyExistsException, InvalidFilenameException, CouldNotRenameExeption {
        super(name);
    }

    /**
     * Set the new name of a FSObject and modifies its path
     * @param name new name of this object.
     * @throws FileAlreadyExistsException
     */
    @Override
    public void setName(String name) throws FileAlreadyExistsException, CouldNotRenameExeption, InvalidFilenameException {
        if (getParentFolder() != null && getParentFolder().exists(name)) {
            throw new FileAlreadyExistsException(name, null, "in Folder: " + getParentFolder().getAbsolutePath());
        }

        if (null != getPath()) {
            try {
                Path newPath = getPath().resolveSibling(name);
                Files.move(getPath(), newPath);
                setPath(newPath);
            } catch (IOException e) {
                throw new CouldNotRenameExeption(this, FSObjectException.COULDNOTRENAME, e);
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
     * If this is a LocalFolder it deletes
     * the directory and its contents recursively.
     */
    abstract void delete() throws ObjectNotFoundException, CouldNotDeleteExeption;

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
            throw new IllegalArgumentException("The new object has to be a LocalFSObject");
        }
    }

}
