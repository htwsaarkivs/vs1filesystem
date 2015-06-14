package htw.vs1.filesystem.FileSystem;

import com.sun.istack.internal.Nullable;

import java.nio.file.FileAlreadyExistsException;

/**
 * Created by felix on 14.06.15.
 */
public abstract class AbstractFSObject implements FSObject {

    private String name;

    /**
     * Creates a new FSObject.
     *
     * @param name name of the FSObject.
     */
    public AbstractFSObject(String name) {
        this.name = name;
    }


    /**
     * Set the name of this {@link FSObject}.
     *
     * @param name new name of this object.
     */
    @Override
    public void setName(String name) throws FileAlreadyExistsException {
        this.name = name;
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
     * Sets the parent {@link Folder} containing this FSObject. Can be
     * {@link null}, iff this is the root-Folder.
     *
     * @param parentFolder the parent {@link Folder} or {@code null} iff this is the
     * root-Folder.
     */
    protected abstract void setParentFolder(@Nullable Folder parentFolder);

    /**
     * Returns the absolute path of this FSObject.
     *
     * @return absolute path - e.g. /root/folder
     */
    @Override
    public String getAbsolutePath() {
        if (getParentFolder() == null) {
            return "/"+getName();
        }

        return getParentFolder().getAbsolutePath() + "/" + getName();
    }

    @Override
    public String toString() {
        return getName();
    }
}
