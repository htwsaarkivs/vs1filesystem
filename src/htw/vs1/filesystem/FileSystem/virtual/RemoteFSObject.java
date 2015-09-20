package htw.vs1.filesystem.FileSystem.virtual;

import com.sun.istack.internal.Nullable;
import htw.vs1.filesystem.FileSystem.exceptions.FSObjectException;
import htw.vs1.filesystem.FileSystem.exceptions.InvalidFilenameException;
import htw.vs1.filesystem.Network.TCPClient;

import java.nio.file.FileAlreadyExistsException;

/**
 * Represents a object of our file system tree which is located
 * on a remote computer.
 *
 * Created by felix on 14.06.15.
 */
public abstract class RemoteFSObject extends AbstractFSObject {

    private Folder parentFolder;
    protected TCPClient tcpClient;

    /**
     * Creates a new FSObject.
     *
     * @param name name of the FSObject.
     */
    public RemoteFSObject(String name) throws FSObjectException {
        super(name);
    }

    protected RemoteFSObject(String name, TCPClient client) throws FSObjectException
    {
        super(name);
        this.tcpClient = client;
        setParentFolder(parentFolder);
    }

    protected RemoteFSObject(String name, TCPClient client, Folder parentFolder) throws FSObjectException
    {
        super(name);
        this.tcpClient = client;
        setParentFolder(parentFolder);
    }

    @Override
    public void setName(String name) throws FSObjectException {
        if (null != tcpClient) {
            this.tcpClient.rename(this.getName(), name);
        } else {
            super.setName(name);
        }
    }

    /**
     * Get the parent {@link Folder} containing this Folder.
     * Can be {@link null}, iff this is the root-Folder.
     *
     * @return the parent {@link Folder} or {@code null} iff this is the root-Folder.
     */
    @Override
    public Folder getParentFolder() {
        return parentFolder;
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
        this.parentFolder = parentFolder;
    }
}
