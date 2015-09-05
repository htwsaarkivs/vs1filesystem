package htw.vs1.filesystem.Network.Protocol.Server;

import htw.vs1.filesystem.FileSystem.virtual.FileSystemInterface;
import htw.vs1.filesystem.Network.Protocol.Protocol;

/**
 * Interface which declares the base methods of the servers protocol class.
 *
 * Created by markus on 24.06.15.
 */
public interface ServerProtocol extends Protocol {

    FileSystemInterface getFileSystem();
    void run();
}
