package htw.vs1.filesystem.FileSystem.virtual;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import htw.vs1.filesystem.FileSystem.exceptions.*;
import htw.vs1.filesystem.FileSystem.physical.PhysicalFileSystemAdapter;

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
public class LocalFolder extends LocalFSObject implements Folder {

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
                //throw new IllegalStateException("Root folder path must be set before calling method getRootFolder().");
                System.out.println("Root folder is not connected to the physical file system.");
            }

            try {
                if (null == ROOT_FOLDER_PATH) {
                    rootFolder = new LocalFolder(ROOT_FOLDER_NAME);
                } else {
                    rootFolder = new LocalFolder(ROOT_FOLDER_NAME, ROOT_FOLDER_PATH);
                }
            } catch (FileSystemException e) {
                e.printStackTrace();
            }
        }
        return rootFolder;
    }

    public static void setRootDirectory(@NotNull String rootPath) throws IOException {
        if (null != ROOT_FOLDER_PATH) {
            throw new IllegalStateException("Root folder path already set.");
        }

        ROOT_FOLDER_PATH = Paths.get(rootPath);

        PhysicalFileSystemAdapter adapter = PhysicalFileSystemAdapter.getInstance();
        System.out.println("Importing directory...");
        String path = adapter.loadFileSystemTree();
        System.out.println("Directory" + ((path.isEmpty()) ? " not" : ": ") + path + " imported.");
    }

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
    public LocalFolder(String name) throws FileSystemException {
        super(name);

    }

    /**
     * Creates a new Folder with the given name
     * and a given path
     *
     * @param name name of the new {@link Folder}.
     */
    public LocalFolder(String name, Path path) throws FileSystemException {
        super(name,path);
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
     * @throws ObjectNotFoundException iff this {@link Folder} does not contain a
     *                                   {@link FSObject} identified by the given name as a direct child.
     */
    @Override
    public FSObject getObject(String name) throws FileSystemException {
        for (FSObject object : contents) {
            if (object.getName().equals(name)) {
                return object;
            }
        }

        throw new ObjectNotFoundException(name, FSObjectException.OBJECTNOTFOUND);
    }

    @Override
    public List<FSObject> search(List<FSObject> list, String name) throws FileSystemException {
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
    public void add(FSObject object) throws FileSystemException {
            Path pathOfFile = null;
            if (object instanceof LocalFSObject) {
                pathOfFile = ((LocalFSObject) object).getPath();
            }
            add(object, pathOfFile);
    }

    @Override
    public void add(String name, boolean isFolder) throws FileSystemException {
            FSObject object = (isFolder)
                    ? new LocalFolder(name)
                    : new LocalFile(name);
            add(object);
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
     * @throws FileSystemException
     */
    public void add(FSObject object, @Nullable Path pathOfFile) throws FileSystemException {
        checkPrecondition(object);

        if (exists(object.getName())) {
            throw new FSObjectAlreadyExistsException(object, "FSObject already exists", null);
        }

        object.setParentFolder(this);

        // we now at the file/folder to our content-list so it won't be added by the file system watch service.
        // If any error happened by creating the "physical" file/folder we have to remove it again from the list.
        contents.add(object);

        try {
            if (null == pathOfFile && null != getPath()) {
                pathOfFile = Paths.get(getPath().toAbsolutePath().toString(), object.getName());
                try {
                    if (object instanceof LocalFile) {
                        Files.createFile(pathOfFile);
                    } else {
                        Files.createDirectory(pathOfFile);
                    }
                } catch (UnsupportedOperationException | SecurityException | IOException e) {
                    throw new CouldNotCreateException(FSObjectException.COULDNOTCREATE, e);
                }
            }
            if (object instanceof LocalFSObject) {
                ((LocalFSObject) object).setPath(pathOfFile); // Type checked by #checkPrecondition(FSObject)
            }
        } catch (Throwable e) {
            // iff any error occurred we have to remove the object again from the list and pass the error on.
            contents.remove(object);
            throw e;
        }

        //contents.add(object);
    }

    /**
     * Removes a {@link FSObject} from the folder.
     *
     * @param object {@link FSObject} to remove from this folder.
     * @throws ObjectNotFoundException iff the {@link FSObject} is not in this folder.
     */
    @Override
    public void delete(FSObject object) throws FileSystemException {
        checkPrecondition(object);
        if (object instanceof LocalFSObject) {
            LocalFSObject localFSObject = (LocalFSObject) object; // Type verified in #checkPrecondition(FSObject)
            // First we have to ensure that the file is deleted on the real file system
            localFSObject.delete();
        }

        // then we can remove it from our list.
        contents.remove(object);
    }

    /**
     * Iterates recursivly through the filetree and calls delete()
     * Removes the file from the filetree and in the real Filesystem
     */
    @Override
    public void delete() throws FileSystemException {
        for (FSObject object : getContent()) {
            checkPrecondition(object);
            if (object instanceof LocalFSObject) {
                LocalFSObject localFSObject = (LocalFSObject) object;
                localFSObject.delete();
            }
        }

        if (null != getPath()) {
            try {
                Files.delete(getPath());
            } catch (IOException e) {
                e.printStackTrace();
                throw new CouldNotDeleteException(this, FSObjectException.COULDNOTDELETE, e);
            }
        }
        setParentFolder(null);

    }


    /**
     * Removes a {@link FSObject} from the folder identified by the
     * given name
     *
     * @param name String to identify the {@link FSObject} which should be removed.
     * @throws ObjectNotFoundException iff there is no {@link FSObject} identified by this name.
     */
    @Override
    public void delete(String name) throws FileSystemException {
        delete(getObject(name));
    }

    @Override
    public void setPath(Path path) {
        super.setPath(path);

        if (null == path) {
            for (FSObject object : contents) {
                if (object instanceof LocalFSObject) {
                    ((LocalFSObject) object).setPath(null);
                }
            }
        }
    }
}
