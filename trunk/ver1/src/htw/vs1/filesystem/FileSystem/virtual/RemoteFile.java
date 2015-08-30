package htw.vs1.filesystem.FileSystem.virtual;

import htw.vs1.filesystem.FileSystem.exceptions.CouldNotRenameException;
import htw.vs1.filesystem.FileSystem.exceptions.InvalidFilenameException;

import java.nio.file.FileAlreadyExistsException;

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
    public RemoteFile(String name) throws CouldNotRenameException, FileAlreadyExistsException, InvalidFilenameException {
        super(name);
    }

}
