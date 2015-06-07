package htw.vs1.filesystem.FileSystem;

import com.sun.istack.internal.NotNull;
import htw.vs1.filesystem.FileSystem.exceptions.FSObjectNotFoundException;

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
     * <p>Creates a new file in the current folder.
     * </p>
     *
     * @param name name of the file.
     * @throws FSObjectNotFoundException if the object identified
     *         by the given name is not available.
     */

    /*

    void createFile(@NotNull String name);
    /**
     * <p>Search for all files and folders in the current folder and sub-folders.
     * </p>
     *
     * @param name name of the file or directory.
     * @throws FSObjectNotFoundException if the object identified
     *         by the given name is not available.
     */

    */

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



}
