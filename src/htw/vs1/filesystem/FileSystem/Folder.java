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
     * Adds a FSObject to the folder.
     *
     * @param object {@link FSObject} to add to this folder.
     * @throws FileAlreadyExistsException iff the file already exists.
     */
    public abstract void add(FSObject object) throws FileAlreadyExistsException;

    /**
     * Removes a {@link FSObject} from the folder.
     *
     * @param object {@link FSObject} to remove from this folder.
     * @throws FSObjectNotFoundException iff the {@link FSObject} is not in this folder.
     */
    public abstract void delete(FSObject object) throws FSObjectNotFoundException;

    /**
     * Removes a {@link FSObject} from the folder identified by the
     * given name
     *
     * @param name String to identify the {@link FSObject} which should be removed.
     * @throws FSObjectNotFoundException iff there is no {@link FSObject} identified by this name.
     */
    public abstract void delete(String name) throws FSObjectNotFoundException;

    /**
     * Get the Content of this {@link Folder} as a {@link List} of
     * {@link FSObject}s.
     *
     * @return the content of this folder.
     */
    public abstract List<FSObject> getContent();

    /**
     * Checks whether a {@link FSObject} exists in this folder
     * identified by the given name.
     *
     * @param name identifier for the {@link FSObject} to check.
     * @return {@code true}, iff a {@link FSObject} exists in this folder identified by the given name.
     */
    public abstract boolean exists(String name);

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

}
