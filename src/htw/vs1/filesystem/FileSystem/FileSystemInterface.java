package htw.vs1.filesystem.FileSystem;

import com.sun.istack.internal.NotNull;
import htw.vs1.filesystem.FileSystem.exceptions.FSObjectNotFoundException;

import java.nio.file.FileAlreadyExistsException;
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
     * {@link Folder} identified by the given name.</p>
     *
     * @param name name of the {@link Folder}.
     * @throws FSObjectNotFoundException if the object identified
     *         by the given name is not available.
     */
    void changeDirectory(@NotNull String name) throws FSObjectNotFoundException;

    /**
     * <p>Returns the content of the current directory as a String.</p>
     *
     * @return content of the current directory.
     */
    String listDirectoryContent();

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
    List<FSObject> search(String name);

    void rename(@NotNull String name,String newName) throws FSObjectNotFoundException, FileAlreadyExistsException;
    void delete(@NotNull String name) throws FSObjectNotFoundException;

}
