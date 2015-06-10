package htw.vs1.filesystem.FileSystem;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import htw.vs1.filesystem.FileSystem.exceptions.FSObjectNotFoundException;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

/**
 * A LocalFolder represents a Folder of the local filesystem,
 * which is a node of our filesystem.
 *
 * Precondition: A LocalFolder can only contain {@link LocalFolder}s or {@link LocalFile}s.
 *
 * Created by felix on 03.06.15.
 */
public class LocalFolder extends Folder implements LocalFSObject {

    /**
     * Name of our single root folder instance.
     */
    private static final String ROOT_FOLDER_NAME = "local";

    /**
     * Path to the local folder
     * on our os of the local machine the
     * program is running on.
     */
    private static Path ROOT_FOLDER_PATH = null;

    /**
     * Single instance of our local root folder.
     */
    private static LocalFolder rootFolder = null;

    /**
     * Gets the single instance of our local root
     * folder located on the machine this program
     * is running on.
     *
     * @return root {@link LocalFolder}.
     */
    public static @NotNull LocalFolder getRootFolder() {
        if (null == rootFolder) {
            if (null == ROOT_FOLDER_PATH) {
                throw new IllegalStateException("Root folder path must be set before calling method getRootFolder().");
            }

            rootFolder = new LocalFolder(ROOT_FOLDER_NAME, ROOT_FOLDER_PATH);
        }

        return rootFolder;
    }

    public static void setRootDirectory(@NotNull String rootPath) {
        if (null != ROOT_FOLDER_PATH) {
            throw new IllegalStateException("Root folder path already set.");
        }

        ROOT_FOLDER_PATH = Paths.get(rootPath);
    }

    private Path path;

    /**
     * Reference to the {@link Folder} containing this one.
     * May be {@link null}, iff this is the root folder.
     */
    private Folder parent = null;

    /**
     * List containing the content of this {@link LocalFolder}, this
     * can be either a {@link LocalFolder} or a {@link LocalFile}.
     */
    private LinkedList<FSObject> contents = new LinkedList<>();

    /**
     * Creates a new Folder with the given name.
     *
     * @param name name of the new {@link Folder}.
     */
    public LocalFolder(String name) {
        super(name);
    }

    /**
     * Creates a new Folder with the given name.
     *
     * @param name name of the new {@link Folder}.
     */
    public LocalFolder(String name, Path path) {
        super(name);
        this.path = path;
    }

    @Override
    public void setName(String name) {
        super.setName(name);

        if (null != getPath()) {
            try {
                Files.move(path, path.resolveSibling(name));
            } catch (IOException e) {
                // TODO: What shall I do with this f*cking exception??
                e.printStackTrace();
            }
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
        return parent;
    }

    /**
     * Sets the parent {@link Folder} containing this FSObject. Can be
     * {@link null}, iff this is the root-Folder.
     * Precondition: the new object has to be either a
     * {@link LocalFolder}.
     *
     * @param parentFolder the parent {@link Folder} or {@code null} iff this is the
     *                     root-Folder.
     */
    @Override
    protected void setParentFolder(@Nullable Folder parentFolder) {
        if (null != parentFolder) {
            checkPrecondition(parentFolder);
        }

        this.parent = parentFolder;
    }

    /**
     * Get the Content of this {@link Folder} as a {@link List}
     * of {@link FSObject}s.
     *
     * @return the content of this folder.
     */
    @Override
    public List<FSObject> getContent() {

        return contents;
    }

    /**
     * Gets the {@link FSObject} identified by the given name,
     * iff this {@link Folder} contains it as a direct child.
     *
     * @param name name of the requested {@link FSObject}.
     * @return {@link FSObject} identified by the given name.
     * @throws FSObjectNotFoundException iff this {@link Folder} does not contain a
     *                                   {@link FSObject} identified by the given name as a direct child.
     */
    @Override
    public FSObject getObject(String name) throws FSObjectNotFoundException {
        for (FSObject object : contents) {
            if (object.getName().equals(name)) {
                return object;
            }
        }

        throw new FSObjectNotFoundException();
    }

    @Override
    public LinkedList<FSObject> search(LinkedList<FSObject> list, String name) {
        for (FSObject object : getContent()) {
            if (object.getName().equals(name)) {
                list.add(object);
            }
            if (object instanceof Folder) {
                ((Folder) object).search(list, name);
            }
        }

        return list;
    }

    /**
     * Add a new {@link FSObject} to this Folder.
     * Precondition: the new object has to be either a
     * {@link LocalFile} or a {@link LocalFolder}.
     *
     * @param object the new folder, which can be either a
     *               {@link LocalFile} or a {@link LocalFolder}.
     * @throws FileAlreadyExistsException iff the file already exists.
     */
    public void add(FSObject object) throws FileAlreadyExistsException {
        add(object, null);
    }

    public void add(FSObject object, @Nullable Path pathOfFile) throws FileAlreadyExistsException {
        checkPrecondition(object);

        object.setParentFolder(this);

        if (null == pathOfFile) {
            Path pathOfNewFile = Paths.get(getPath().toAbsolutePath().toString(), object.getName());
            // TODO: set the path to the object.
            if (object instanceof LocalFile) {
                try {
                    Files.createFile(pathOfNewFile);
                } catch (IOException e) {
                    // TODO: What shall I do with this f*cking exception?
                    e.printStackTrace();
                }
            } else {
                try {
                    Files.createDirectory(pathOfNewFile);
                } catch (IOException e) {
                    // TODO: What shall I do with this f*cking exception?
                    e.printStackTrace();
                }
            }
        } else {
            // TODO: Set given path as object path.
        }

        contents.add(object);
    }

    /**
     * Removes a {@link FSObject} from the folder.
     *
     * @param object {@link FSObject} to remove from this folder.
     * @throws FSObjectNotFoundException iff the {@link FSObject} is not in this folder.
     */
    @Override
    public void delete(FSObject object) throws FSObjectNotFoundException {
        getContent().remove(object);
        object.setParentFolder(null);

        // TODO: is there a path available delete the object on the file system.
        // Caution: is the object a folder delete it recursively.
    }

    /**
     * Removes a {@link FSObject} from the folder identified by the
     * given name
     *
     * @param name String to identify the {@link FSObject} which should be removed.
     * @throws FSObjectNotFoundException iff there is no {@link FSObject} identified by this name.
     */
    @Override
    public void delete(String name) throws FSObjectNotFoundException {
        delete(getObject(name));
    }

    /**
     * Checks the precondition that the given objects has to be a
     * {@link LocalFolder} or a {@link LocalFile}.
     *
     * @param object {@link FSObject} which has to match the precondition.
     */
    private void checkPrecondition(FSObject object) {
        if (!((object instanceof LocalFolder) || (object instanceof LocalFile))) {
            // this case should never happen -> precondition !
            throw new IllegalArgumentException("The new object has to be either a LocalFolder or a LocalFile");
        }
    }

    //TODO to comment
    public Path getPath() {
        return path;
    }
    //TODO to comment
    public void setPath(Path path) {
        this.path = path;
    }
}
