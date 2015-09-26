package htw.vs1.filesystem.FileSystem.virtual;

import com.sun.istack.internal.NotNull;
import htw.vs1.filesystem.FileSystem.exceptions.*;
import htw.vs1.filesystem.Network.Discovery.FileSystemServer;

import java.util.Collection;
import java.util.List;

/**
 * <p>The FileSystemInterface declares the interface to work on
 * our filesystem.</p>
 *
 * <p>To modify the content of the current working directory use
 * {@link #getWorkingDirectory()} and operate on the {@link Folder}-Object
 * itself.</p>
 *
 * Created by felix on 03.06.15.
 */
public interface FileSystemInterface {

    /**
     * Sets the given {@link Folder} as the working
     * directory.
     *
     * @param workingFolder new working directory.
     */
    void setWorkingDirectory(@NotNull Folder workingFolder);

    /**
     * Gets the current working directory.
     *
     * @return the current working directory.
     */
    Folder getWorkingDirectory();

    /**
     * <p>Changes the current working directory to the
     * {@link Folder} under the given path.</p>
     *
     * @param path path to the {@link Folder}. This can be either a relative path,
     *             an absolute path or simple the name of a {@link Folder} in the
     *             current working directory.
     * @throws ObjectNotFoundException if the object identified
     *         by the given name is not available.
     */
    void changeDirectory(@NotNull String path) throws FileSystemException;

    /**
     * <p>Returns the content of the current directory as a
     * {@link List} of {@link FSObject}'s.</p>
     *
     * @return content of the current directory.
     */
    List<FSObject> listDirectoryContent() throws FileSystemException;

    /**
     * <p>Prints the current working directory in a String.</p>
     * <i>Example: /usr/root/home</i>
     *
     * @return current working directory as String.
     */
    String printWorkingDirectory();

    /**
     * Searchs recursively through the current working directory
     * all {@link FSObject} with the given name.
     *
     * @param name name of the {@link FSObject} to search for.
     * @return a {@link List} of {@link FSObject}s matching to the given name.
     */
    List<FSObject> search(String name) throws FileSystemException;

    /**
     * Creates a new {@link FSObject} and adds it to the
     * current working directory.
     *
     * @param name name of the new object.
     * @param isFolder flag to create a isFolder or a file.
     * @throws FileSystemException
     */
    void createFSObject(String name, boolean isFolder) throws FileSystemException;

    void rename(@NotNull String name,String newName)  throws FileSystemException;

    void delete(@NotNull String name) throws FileSystemException;

    /**
     * Mounts a {@link RemoteFolder} into our file system.
     *
     * @param name Name of the new remote foler
     * @param remoteIP IP-Adress of the remote file system
     * @param remotePort Port of the remote file system
     * @param user username
     * @param pass password
     */
    void mount(String name, String remoteIP, int remotePort, String user, String pass) throws FileSystemException;
}
