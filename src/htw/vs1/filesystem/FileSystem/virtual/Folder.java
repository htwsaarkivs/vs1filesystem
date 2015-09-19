package htw.vs1.filesystem.FileSystem.virtual;

import htw.vs1.filesystem.FileSystem.exceptions.FSObjectException;
import htw.vs1.filesystem.FileSystem.exceptions.ObjectNotFoundException;

import java.nio.file.FileAlreadyExistsException;
import java.util.LinkedList;
import java.util.List;

/**
 * A Folder represents a node in our file system. It can either be a
 * {@link LocalFolder} or a {@link RemoteFolder}.
 *
 * Created by markus on 01.06.15.
 */
public interface Folder extends FSObject {

    /**
     * Adds a FSObject to the folder.
     *
     * @param object {@link FSObject} to add to this folder.
     * @throws FileAlreadyExistsException iff the file already exists.
     */
    void add(FSObject object) throws FSObjectException;

    void add(String name, boolean isFolder) throws FSObjectException;

    /**
     * Removes a {@link FSObject} from the folder.
     *
     * @param object {@link FSObject} to remove from this folder.
     * @throws ObjectNotFoundException iff the {@link FSObject} is not in this folder.
     */
    void delete(FSObject object) throws FSObjectException;

    /**
     * Removes a {@link FSObject} from the folder identified by the
     * given name
     *
     * @param name String to identify the {@link FSObject} which should be removed.
     * @throws ObjectNotFoundException iff there is no {@link FSObject} identified by this name.
     */
    void delete(String name) throws FSObjectException;

    /**
     * Get the Content of this {@link Folder} as a {@link List} of
     * {@link FSObject}s.
     *
     * @return the content of this folder.
     */
    List<FSObject> getContent() throws FSObjectException;

    /**
     * Checks whether a {@link FSObject} exists in this folder
     * identified by the given name.
     *
     * @param name identifier for the {@link FSObject} to check.
     * @return {@code true}, iff a {@link FSObject} exists in this folder identified by the given name.
     */
    boolean exists(String name) throws FSObjectException;

    /**
     * Gets the {@link FSObject} identified by the given name, iff this
     * {@link Folder} contains it as a direct child.
     *
     * @param name name of the requested {@link FSObject}.
     * @return {@link FSObject} identified by the given name.
     * @throws ObjectNotFoundException iff this {@link Folder} does not
     * contain a {@link FSObject} identified by the given name as a direct
     * child.
     */
    FSObject getObject(String name) throws FSObjectException;

    LinkedList<FSObject> search(LinkedList<FSObject> list, String name);

}
