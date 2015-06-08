package htw.vs1.filesystem.FileSystem;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import htw.vs1.filesystem.FileSystem.exceptions.FSObjectNotFoundException;

import java.nio.file.FileAlreadyExistsException;
import java.util.LinkedList;
import java.util.List;

/**
 * A Folder represents a node in our file system. It can either be a
 * {@link LocalFolder} or a {@link RemoteFolder}.
 *
 * Created by markus on 01.06.15.
 */
public abstract class Folder extends FSObject {

    /**
     * Creates a new Folder with the given name.
     *
     * @param name name of the new {@link Folder}.
     */
    public Folder(String name) {
        super(name);
    }

    /**
     * Get the parent {@link Folder} containing this Folder. Can be
     * {@link null}, iff this is the root-Folder.
     *
     * @return the parent {@link Folder} or {@code null} iff this is the
     * root-Folder.
     */
    public abstract @Nullable
    Folder getParentFolder();

    /**
     * Add a FSObject to the folder.
     *
     * @param object {@link FSObject} to add to this folder.
     * @throws FileAlreadyExistsException iff the file already exists.
     */
    public abstract void add(FSObject object) throws FileAlreadyExistsException;

    /**
     * Get the Content of this {@link Folder} as a {@link List} of
     * {@link FSObject}s.
     *
     * @return the content of this folder.
     */
    public abstract List<FSObject> getContent();

    /**
     * Gets the {@link FSObject} identified by the given name, iff this
     * {@link Folder} contains it as a direct child.
     *
     * @param name name of the requested {@link FSObject}.
     * @return {@link FSObject} identified by the given name.
     * @throws FSObjectNotFoundException iff this {@link Folder} does not
     * contain a {@link FSObject} identified by the given name as a direct
     * child.
     */
    public abstract FSObject getObject(String name) throws FSObjectNotFoundException;

    public abstract LinkedList<FSObject> search(LinkedList<FSObject> list, String name);



    /**
     * Returns the absolute path of the given folder.
     *
     * @return absolute path - e.g. /root/folder
     */
    public String getAbsolutePath() {

        if (getParentFolder() == null) {
            return "/"+getName();
        }

        return getAbsolutePath() + "/" + getName();
    }
}
