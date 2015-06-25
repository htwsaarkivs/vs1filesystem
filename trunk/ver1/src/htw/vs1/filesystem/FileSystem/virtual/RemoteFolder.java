package htw.vs1.filesystem.FileSystem.virtual;

import com.sun.istack.internal.Nullable;
import htw.vs1.filesystem.FileSystem.exceptions.CouldNotRenameException;
import htw.vs1.filesystem.FileSystem.exceptions.InvalidFilenameException;
import htw.vs1.filesystem.FileSystem.exceptions.ObjectNotFoundException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.nio.file.FileAlreadyExistsException;
import java.util.LinkedList;
import java.util.List;

/**
 * A RemoteFolder represents a {@link Folder} located on a remote filesystem.
 * This is a node in our filesystem.
 *
 * Created by felix on 03.06.15.
 */
public class RemoteFolder extends RemoteFSObject implements Folder {

    public static RemoteFolder createAsMountPoint(String remoteIP, String remotePort, String user, String pass) {
        throw new NotImplementedException();
    }

    /**
     * Creates a new Folder with the given name.
     *
     * @param name name of the new {@link Folder}.
     */
    public RemoteFolder(String name) throws CouldNotRenameException, FileAlreadyExistsException, InvalidFilenameException {
        super(name);
    }

    /**
     * Get the parent {@link Folder} containing this Folder.
     * Can be {@link null}, iff this is the root-Folder.
     *
     * @return the parent {@link Folder} or {@code null} iff this is the root-Folder.
     */
    @Override
    public Folder getParentFolder() {
        throw new NotImplementedException();
    }

    /**
     * Sets the parent {@link Folder} containing this FSObject. Can be
     * {@link null}, iff this is the root-Folder.
     *
     * @param parentFolder the parent {@link Folder} or {@code null} iff this is the
     *                     root-Folder.
     */
    @Override
    public void setParentFolder(@Nullable Folder parentFolder) {
        throw new NotImplementedException();
    }

    /**
     * Add a FSObject to the folder.
     *
     * @param object {@link FSObject} to add to this folder.
     * @throws FileAlreadyExistsException iff the file already exists.
     */
    @Override
    public void add(FSObject object) throws FileAlreadyExistsException {
        throw new NotImplementedException();
    }

    /**
     * Removes a {@link FSObject} from the folder.
     *
     * @param object {@link FSObject} to remove from this folder.
     * @throws ObjectNotFoundException iff the {@link FSObject} is not in this folder.
     */
    @Override
    public void delete(FSObject object) throws ObjectNotFoundException {
        throw new NotImplementedException();
    }

    /**
     * Removes a {@link FSObject} from the folder identified by the
     * given name
     *
     * @param name String to identify the {@link FSObject} which should be removed.
     * @throws ObjectNotFoundException iff there is no {@link FSObject} identified by this name.
     */
    @Override
    public void delete(String name) throws ObjectNotFoundException {
        throw new NotImplementedException();
    }

    /**
     * Get the Content of this {@link Folder} as a {@link List}
     * of {@link FSObject}s.
     *
     * @return the content of this folder.
     */
    @Override
    public List<FSObject> getContent() {
        throw new NotImplementedException();
    }

    /**
     * Checks whether a {@link FSObject} exists in this folder
     * identified by the given name.
     *
     * @param name identifier for the {@link FSObject} to check.
     * @return {@code true}, iff a {@link FSObject} exists in this folder identified by the given name.
     */
    @Override
    public boolean exists(String name) {
        throw new NotImplementedException();
    }

    /**
     * Gets the {@link FSObject} identified by the given name,
     * iff this {@link Folder} contains it as a direct child.
     *
     * @param name name of the requested {@link FSObject}.
     * @return {@link FSObject} identified by the given name.
     * @throws ObjectNotFoundException iff this {@link Folder} does not contain a
     *                                   {@link FSObject} identified by the given name as a direct child.
     */
    @Override
    public FSObject getObject(String name) throws ObjectNotFoundException {
        throw new NotImplementedException();
    }

    @Override
    public LinkedList<FSObject> search(LinkedList<FSObject> list, String name) {
        throw new NotImplementedException();
    }
}
