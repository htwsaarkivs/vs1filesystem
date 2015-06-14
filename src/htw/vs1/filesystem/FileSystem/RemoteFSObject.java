package htw.vs1.filesystem.FileSystem;

/**
 * Represents a object of our file system tree which is located
 * on a remote computer.
 *
 * Created by felix on 14.06.15.
 */
public abstract class RemoteFSObject extends AbstractFSObject {
    /**
     * Creates a new FSObject.
     *
     * @param name name of the FSObject.
     */
    public RemoteFSObject(String name) {
        super(name);
    }
}
