package htw.vs1.filesystem.FileSystem;

import com.sun.istack.internal.NotNull;
import htw.vs1.filesystem.FileSystem.exceptions.FSObjectNotFoundException;

import java.nio.file.FileAlreadyExistsException;
import java.util.LinkedList;
import java.util.List;

/**
 * The {@link FileSystem} represents a file system on which you
 * can perform actions like:
 * <ul>
 *     <li>print the current Working directory: {@link #printWorkingDirectory()}</li>
 *     <li>change the working directory {@link #changeDirectory(String)}</li>
 *     <li>list the content of the current folder {@link #listDirectoryContent()}</li>
 *     <li>{@link #rename(String, String)} / {@link #delete(String)}</li>
 * </ul>
 *
 * Created by felix on 03.06.15.
 */
public class FileSystem implements FileSystemInterface {

    /**
     * String-Definition to change current working
     * directory to the parent folder.
     */
    private static final String UP = "..";

    /**
     * Current working folder.
     */
    private Folder workingFolder;

    public FileSystem() {
    }

    /**
     * Creates a new FileSystem with the given {@link Folder}
     * as working directory.
     *
     * @param workingFolder {@link Folder} to work on.
     */
    public FileSystem(Folder workingFolder) {
        this.workingFolder = workingFolder;
    }

    /**
     * {@inheritDoc}
     */
    public void setWorkingDirectory(Folder workingFolder) {
        this.workingFolder = workingFolder;
    }

    /**
     * Gets the current working directory.
     *
     * @return the current working directory.
     */
    @Override
    public Folder getWorkingDirectory() {
        return workingFolder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void changeDirectory(@NotNull String name) throws FSObjectNotFoundException {
        FSObject o;
        if (name.equals(UP)) {
            o = workingFolder.getParentFolder();
        } else {
            o = workingFolder.getObject(name);
        }

        if (o instanceof Folder) {
            workingFolder = (Folder) o;
        } else {
            throw new FSObjectNotFoundException();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String listDirectoryContent() {
        return FSObject.printFSObjectList(workingFolder.getContent(), false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String printWorkingDirectory() {
        return workingFolder.getAbsolutePath();
    }

    /**
     * Searchs recursively through the current working directory
     * all {@link FSObject} with the given name.
     *
     * @param name name of the {@link FSObject} to search for.
     * @return a {@link List} of {@link FSObject}s matching to the given name.
     */
    @Override
    public List<FSObject> search(String name) {
        LinkedList<FSObject> result = new LinkedList<>();
        workingFolder.search(result, name);
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
     public void rename(@NotNull String name, String newName)
            throws FSObjectNotFoundException, FileAlreadyExistsException
    {
        FSObject toRename = workingFolder.getObject(name);
        toRename.setName(newName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(@NotNull String name) throws FSObjectNotFoundException {
        workingFolder.delete(name);
    }
}