package htw.vs1.filesystem.FileSystem;

import htw.vs1.filesystem.FileSystem.exceptions.FSObjectNotFoundException;
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
public class RemoteFolder extends Folder {

    /**
     * Creates a new Folder with the given name.
     *
     * @param name name of the new {@link Folder}.
     */
    public RemoteFolder(String name) {
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
     * Gets the {@link FSObject} identified by the given name,
     * iff this {@link Folder} contains it as a direct child.
     *
     * @param name name of the requested {@link FSObject}.
     * @return {@link FSObject} identified by the given name.
     * @throws FSObjectNotFoundException iff this {@link Folder} does not contain a
     *                                   {@link FSObject} identified by the given name as a direct child.
     */
    @Override
    public FSObject getObject(String name) throws FSObjectNotFoundException {
        throw new NotImplementedException();
    }

    @Override
    public LinkedList<FSObject> search(LinkedList<FSObject> list, String name) {
        throw new NotImplementedException();
    }
}
