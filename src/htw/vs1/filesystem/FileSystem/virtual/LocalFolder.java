package htw.vs1.filesystem.FileSystem.virtual;

import com.sun.istack.internal.Nullable;
import htw.vs1.filesystem.FileSystem.exceptions.*;
import htw.vs1.filesystem.FileSystemManger;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
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
    public List<FSObject> getContent() throws FileSystemException{
        if (!getPermissions().isGetContentAllowed()) {
            throw new PermissionDeniedException(this);
        }

        return Collections.unmodifiableList(contents);
    }

    /**
     * Checks whether a {@link FSObject} exists in this folder
     * identified by the given name.
     *
     * @param name identifier for the {@link FSObject} to check.
     * @return {@code true}, iff a {@link FSObject} exists in this folder identified by the given name.
     */
    @Override
    public boolean exists(String name) throws PermissionDeniedException {
        if (!getPermissions().isGetContentAllowed()) {
            throw new PermissionDeniedException(this);
        }

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
        if (!getPermissions().isGetContentAllowed()) {
            throw new PermissionDeniedException(this);
        }

        for (FSObject object : contents) {
            if (object.getName().equals(name)) {
                return object;
            }
        }

        throw new ObjectNotFoundException(name, FSObjectException.OBJECTNOTFOUND);
    }

    @Override
    public List<FSObject> search(List<FSObject> list, String name) throws FileSystemException {
        if (!getPermissions().isSearchAllowed()) {
            // if search is not allowed, we don't throw any exception but we
            // simply skip the folder.
            return list;
        }

        for (FSObject object : getContent()) {
            if (object.getName().contains(name)) {
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
        if (!getPermissions().isAddAllowed()) {
            throw new PermissionDeniedException(this);
        }

        checkPrecondition(object);

        // we now at the file/folder to our content-list so it won't be added by the file system watch service.
        // If any error happened by creating the "physical" file/folder we have to remove it again from the list.
        addObjectToContent(object);

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
                    if (FileSystemManger.DEBUG) {
                        e.printStackTrace();
                    }
                    throw new CouldNotCreateException(FSObjectException.COULDNOTCREATE, e);
                }
            }
            if (object instanceof LocalFSObject) {
                ((LocalFSObject) object).setPath(pathOfFile);
            }
        } catch (Throwable e) {
            if (FileSystemManger.DEBUG) {
                e.printStackTrace();
            }
            // iff any error occurred we have to remove the object again from the list and pass the error on.
            contents.remove(object);
            throw e;
        }

        //contents.add(object);
    }

    /**
     * Add a given object to the content list.
     * Caution: The permission won't be checked.
     *
     * @param object
     * @throws FileSystemException
     */
    protected void addObjectToContent(FSObject object) throws FileSystemException {
        if (exists(object.getName())) {
            throw new FSObjectAlreadyExistsException(object, "FSObject already exists", null);
        }

        object.setParentFolder(this);
        contents.add(object);
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
        if (object instanceof AbstractFSObject) {
            AbstractFSObject abstractFSObject = (AbstractFSObject) object;
            // First we have to ensure that the file is deleted on the real file system or the
            // file will be deleted on the remote system.
            abstractFSObject.delete();
        }


        // then we can remove it from our list.
        contents.remove(object);
    }

    /**
     * Iterates recursively through the file tree and calls delete()
     * Removes the file from the file tree and in the real Filesystem
     */
    @Override
    public void delete() throws FileSystemException {
        if (!getPermissions().isDeleteAllowed()) {
            throw new PermissionDeniedException(this);
        }

        if(!getContent().isEmpty()){
            throw new CouldNotDeleteException("The folder is not empty.");
        }
        /*for (FSObject object : getContent()) {
            checkPrecondition(object);
            if (object instanceof LocalFSObject) {
                LocalFSObject localFSObject = (LocalFSObject) object;
                localFSObject.delete();
            }
        }*/

        if (null != getPath()) {
            try {
                Files.delete(getPath());
            } catch (IOException e) {
                if (FileSystemManger.DEBUG) {
                    e.printStackTrace();
                }
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
    public void toggleLock() throws FileSystemException {
        if (getPermissions().isLocked()) {
            super.toggleLock();
            toogleLockContent();
        } else {
            toogleLockContent();
            super.toggleLock();
        }

        // first lock or unlock recursively all containing objects.
    }

    private void toogleLockContent() throws FileSystemException {
        for (FSObject object : getContent()) {
            object.toggleLock();
        }
    }

    @Override
    public void setPath(Path path) {
        super.setPath(path);

        if (null == path) {
            contents.stream().filter(
                    object -> object instanceof LocalFSObject).forEach(
                        object -> ((LocalFSObject) object).setPath(null));
        }
    }
}
