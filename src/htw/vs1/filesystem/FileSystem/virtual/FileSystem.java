package htw.vs1.filesystem.FileSystem.virtual;

import com.sun.istack.internal.NotNull;
import htw.vs1.filesystem.FileSystem.exceptions.*;
import htw.vs1.filesystem.FileSystemManger;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCode;

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
    public static final String UP = "..";

    private static final String THIS_FOLDER = ".";

    private static final String ROOT_FOLDER = "/";

    private final boolean mountAllowed;

    /**
     * The folder to evaluate a absolute
     * path from.
     */
    private Folder rootFolder;

    /**
     * Current working folder.
     */
    private Folder workingFolder;

    /**
     * Creates a new FileSystem with the given {@link Folder}
     * as working directory.
     *
     * @param workingFolder {@link Folder} to work on.
     */
    public FileSystem(Folder workingFolder, boolean mountAllowed) {
        if (mountAllowed) {
            try {
                rootFolder = new MountPointFolder("");
                ((MountPointFolder) rootFolder).addMountPoint(workingFolder);
                this.workingFolder = rootFolder;
            } catch (FileSystemException e) {
                if (FileSystemManger.DEBUG) {
                    e.printStackTrace();
                }
            }
        } else {
            this.workingFolder = workingFolder;
            this.rootFolder = workingFolder;
        }
        this.mountAllowed = mountAllowed;
    }

    /**
     * {@inheritDoc}
     */
    public void setWorkingDirectory(Folder workingFolder) throws FileSystemException {
        if (!workingFolder.getPermissions().isCDAllowed()) {
            throw new PermissionDeniedException(workingFolder);
        }
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

    @Override
    public void createFSObject(String name, boolean isFolder) throws FileSystemException {
        getWorkingDirectory().add(name, isFolder);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void changeDirectory(@NotNull String path) throws FileSystemException {
        path = path.replace("\\", "/");
        if (!path.isEmpty() && path.substring(0,1).equals(ROOT_FOLDER)) {
            // path is an absolute path
            setWorkingDirectory(rootFolder);
        }

        String[] pathArray = path.split("/");
        for (String name : pathArray) {
            if (name.equals(rootFolder.getName()) || name.isEmpty()) {
                continue;
            }
            changeDirectoryToSubFolder(name);
        }
    }

    private void changeDirectoryToSubFolder(@NotNull String name) throws FileSystemException {
        FSObject o;
        switch (name) {
            case UP:
                if (!mountAllowed && (workingFolder.getParentFolder() instanceof MountPointFolder) ) {
                    return;
                }

                o = workingFolder.getParentFolder();
                break;
            case THIS_FOLDER:
                // stay in this folder
                return;
            default:
                o = workingFolder.getObject(name);
                break;
        }

        if (null == o) {
            // if your are in the root folder then there is nothing to do.
            // unix does not show any error message either.
            return;
        }

        if (o instanceof RemoteFolder) {
            ((RemoteFolder) o).changeDir();
        }

        if (o instanceof Folder) {
            if (!o.getPermissions().isCDAllowed()) {
                throw new PermissionDeniedException(o);
            }
            workingFolder = (Folder) o;
        } else {
            throw new ObjectNotFoundException(name, FSObjectException.OBJECTNOTFOUND);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<FSObject>  listDirectoryContent() throws FileSystemException {
        return workingFolder.getContent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String printWorkingDirectory() {
        String pwd = workingFolder.getAbsolutePath();
        if (!pwd.equals("/")) { // remove ending slash
            pwd = pwd.substring(0, pwd.length()-1);
        }

        if (!mountAllowed) {
            if (pwd.length() > 7) {
                pwd = pwd.substring(6);
            } else {
                pwd = ROOT_FOLDER;
            }
        }

        return pwd;
    }

    /**
     * Search recursively through the current working directory
     * all {@link FSObject} with the given name.
     *
     * @param name name of the {@link FSObject} to search for.
     * @return a {@link List} of {@link FSObject}s matching to the given name.
     */
    @Override
    public List<FSObject> search(String name) throws FileSystemException{
        LinkedList<FSObject> result = new LinkedList<>();
        workingFolder.search(result, name);
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
     public void rename(@NotNull String name, String newName) throws FileSystemException {
        FSObject toRename = workingFolder.getObject(name);
        toRename.setName(newName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(@NotNull String name) throws FileSystemException {
        workingFolder.delete(name);
    }

    @Override
    public void toggleLock(@NotNull String name) throws FileSystemException {
        workingFolder.getObject(name).toggleLock();
    }

    /**
     * Mounts a {@link RemoteFolder} into our file system.
     *
     * @param name       Name of the new remote folder
     * @param remoteIP   IP-Address of the remote file system
     * @param remotePort Port of the remote file system
     * @param user       username
     * @param pass       password
     */
    @Override
    public void mount(String name, String remoteIP, int remotePort, String user, String pass) throws FileSystemException {
        if (!mountAllowed || !(rootFolder instanceof MountPointFolder)) {
            return;
        }

        ((MountPointFolder) rootFolder).addMountPoint(new RemoteFolder(name, remoteIP, remotePort, user, pass));
    }
}
