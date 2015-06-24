package htw.vs1.filesystem.Network.Protocol.Server;

import htw.vs1.filesystem.FileSystem.virtual.FileSystemInterface;
import htw.vs1.filesystem.Network.Protocol.Protocol;

/**
 * Created by markus on 24.06.15.
 */
public interface ServerProtocol extends Protocol {

    public FileSystemInterface getFileSystem();
}
