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

    /**
     * Path of the Local Folder in the real Filesystem
     */
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

    /**
     * Set the new name of a FSObject and modidies its path
     * @param name new name of this object.
     * @throws FileAlreadyExistsException
     */
    @Override
    public void setName(String name) throws FileAlreadyExistsException {
        if (getParentFolder() != null && getParentFolder().exists(name)) {
            throw new FileAlreadyExistsException(name, null, "in Folder: " + getParentFolder().getAbsolutePath());
        }

        if (null != getPath()) {
            try {
                Path newPath = path.resolveSibling(name);
                Files.move(path, newPath);
                setPath(newPath);
            } catch (IOException e) {
                // TODO: What shall I do with this f*cking exception??
                e.printStackTrace();
            }
        }


        super.setName(name); // It is important to set the name after checking if it exists!
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
     * Checks whether a {@link FSObject} exists in this folder
     * identified by the given name.
     *
     * @param name identifier for the {@link FSObject} to check.
     * @return {@code true}, iff a {@link FSObject} exists in this folder identified by the given name.
     */
    @Override
    public boolean exists(String name) {
        for (FSObject object : contents) {
            if (object.getName().equals(name)) {
                return true;
            }
        }

        return false;
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

    /**
     *  Add a new {@link FSObject} to this Folder.
     * Precondition: the new object has to be either a
     * {@link LocalFile} or a {@link LocalFolder}.
     *
     * @param object the new folder, which can be either a
     *               {@link LocalFile} or a {@link LocalFolder}.
     * @param pathOfFile can be a null value if the FSObject does
     *                   not exist, or will be not null for the
     *                   first run of loadFileSystemDirectory()
     * @throws FileAlreadyExistsException
     */
    public void add(FSObject object, @Nullable Path pathOfFile) throws FileAlreadyExistsException {
        checkPrecondition(object);

        if (exists(object.getName())) {
            throw new FileAlreadyExistsException(object.getName());
        }

        object.setParentFolder(this);

        if (null == pathOfFile && null != getPath()) {
            pathOfFile = Paths.get(getPath().toAbsolutePath().toString(), object.getName());
            if (object instanceof LocalFile) {
                try {
                    Files.createFile(pathOfFile);
                } catch (IOException e) {
                    // TODO: What shall I do with this f*cking exception?
                    e.printStackTrace();
                }
            } else {
                try {
                    Files.createDirectory(pathOfFile);
                } catch (IOException e) {
                    // TODO: What shall I do with this f*cking exception?
                    e.printStackTrace();
                }
            }
        }

        ((LocalFSObject) object).setPath(pathOfFile); // Type checked by #checkPrecondition(FSObject)

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
        checkPrecondition(object);
        LocalFSObject localFSObject = (LocalFSObject)object;
        localFSObject.delete();
        contents.remove(object);


        // TODO: is there a path available delete the object on the file system.
        // Caution: is the object a folder delete it recursively.
    }

    /**
     * Iterates recursivly through the filetree and calls delete()
     * Removes the file from the filetree and in the real Filesystem
     */
    @Override
    public void delete() {
        for (FSObject object : getContent()) {
            checkPrecondition(object);
            LocalFSObject localFSObject = (LocalFSObject)object;
            localFSObject.delete();
        }

        // TODO: delete *this* Folder in the real file system.
        Path path = getPath();
        try {
            Files.delete(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        setParentFolder(null);

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
     * {@link LocalFolder} or a {@link LocalFile}, which means it has
     * to be a {@link LocalFSObject}.
     *
     * @param object {@link FSObject} which has to match the precondition.
     */
    private void checkPrecondition(FSObject object) {
        if (!(object instanceof LocalFSObject)) {
            // this case should never happen -> precondition !
            throw new IllegalArgumentException("The new object has to be a LocalFSObject");
        }
    }

    /**
     * Returns the current path of the FSObject
     * @return current Path
     */
    public Path getPath() {
        return path;
    }

    /**
     * Set the given path as new Path for the FSObject
     * @param path  new Path
     */
    public void setPath(Path path) {
        this.path = path;
    }
}
