package htw.vs1.filesystem.FileSystem.virtual;

import com.sun.istack.internal.Nullable;
import htw.vs1.filesystem.FileSystem.exceptions.CouldNotRenameException;
import htw.vs1.filesystem.FileSystem.exceptions.FSObjectException;
import htw.vs1.filesystem.FileSystem.exceptions.FileSystemException;
import htw.vs1.filesystem.FileSystem.exceptions.InvalidFilenameException;
import htw.vs1.filesystem.FileSystemManger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Base class for all objects in our file system tree.
 *
 * Created by felix on 14.06.15.
 */
public abstract class AbstractFSObject implements FSObject {

    private String name;

    /**
     * Creates a new FSObject.
     *
     * @param name name of the FSObject.
     */
    public AbstractFSObject(String name) throws FileSystemException {
        try {
            setName(name);
        } catch (CouldNotRenameException couldNotRenameException) {
            // This should not happen by the creation of this object.
            if (FileSystemManger.DEBUG) {
                couldNotRenameException.printStackTrace();
            }
        }
    }


    /**
     * Set the name of this {@link FSObject}.
     *
     * @param name new name of this object.
     */
    @Override
  public void setName(String name) throws FileSystemException {
        name = name.trim();
        Pattern regularExpression = Pattern.compile("^[^/\\:*?\"<>|]+$");
        Matcher filename = regularExpression.matcher(name);
        if(name.isEmpty() || filename.matches()) {
            this.name = name;
        }
        else {
            throw new InvalidFilenameException(FSObjectException.INVALIDFILENAME);
        }
    }

    /**
     * Gets the name of this {@link FSObject}.
     *
     * @return name of this object.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Gets the parent {@link Folder} containing this FSObject. Can be
     * {@link null}, iff this is the root-Folder.
     *
     * @return the parent {@link Folder} or {@code null} iff this is the
     * root-Folder.
     */
    @Override
    public abstract @Nullable Folder getParentFolder();

    /**
     * Returns the absolute path of this FSObject.
     *
     * @return absolute path - e.g. /root/folder
     */
    @Override
    public String getAbsolutePath() {
        if (getParentFolder() == null) {
            return "/";
        }

        return getParentFolder().getAbsolutePath() + getName() + "/";
    }

    @Override
    public String toString() {
        return getName();
    }

    /**
     * Deletes the object itself. If this method is implemented by a
     * LocalFSObject this will take care to remove its associated
     * physical object.
     *
     * @throws FileSystemException
     */
    protected abstract void delete() throws FileSystemException;
}
