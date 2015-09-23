package htw.vs1.filesystem.FileSystem.virtual;

import htw.vs1.filesystem.FileSystem.exceptions.FileSystemException;
import htw.vs1.filesystem.Network.TCPClient;

/**
 * A RemoteFile represents a {@link File} located on a remote filesystem.
 * This can be a leaf of a {@link RemoteFolder}.
 *
 * Created by markus on 03.06.15.
 */
public class RemoteFile extends RemoteFSObject implements File {

    /**
     * Creates a new File.
     *
     * @param name name of the new {@link File}.
     */
    public RemoteFile(String name) throws FileSystemException {
        super(name);
    }

    public RemoteFile(String name, TCPClient client, Folder parentFolder) throws FileSystemException
    {
        super(name, client, parentFolder);
    }

}
