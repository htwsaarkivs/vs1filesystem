package htw.vs1.filesystem.FileSystem;

import java.nio.file.FileAlreadyExistsException;
import java.util.List;

/**
 * A Folder represents a node in our file system.
 * It can either be a {@link LocalFolder} or a RemoteFolder (later use).
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
     * Get the parent {@link Folder} containing this Folder.
     * Can be {@link null}, iff this is the root-Folder.
     *
     * @return the parent {@link Folder} or {@code null} iff this is the root-Folder.
     */
    public abstract Folder getParentFolder();

    /**
     * Add a FSObject to the folder.
     */
    public abstract void add(FSObject object) throws FileAlreadyExistsException;


    /**
     * Get the Content of this {@link Folder} as a {@link List}
     * of {@link FSObject}s.
     *
     * @return the content of this folder.
     */
    public abstract List<FSObject> getContent();

    public abstract FSObject getObject(String name);


}
