package htw.vs1.filesystem.FileSystem.virtual;

import htw.vs1.filesystem.FileSystem.exceptions.*;
import htw.vs1.filesystem.Network.Protocol.Commands.LS;
import htw.vs1.filesystem.Network.TCPClient;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.nio.file.FileAlreadyExistsException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * A RemoteFolder represents a {@link Folder} located on a remote filesystem.
 * This is a node in our filesystem.
 *
 * Created by felix on 03.06.15.
 */
public class RemoteFolder extends RemoteFSObject implements Folder {


    private String remoteAbsolutePath;

    public RemoteFolder(String name, String remoteIP, int remotePort, String user, String pass) throws FileSystemException {
        super(name, new TCPClient(remoteIP, remotePort, user, pass));
        remoteAbsolutePath = "/";
    }

    /**
     * Creates a new Folder with the given name and an
     * existing SimpleClientProtocol.
     *
     * @param name name of the new {@link Folder}.
     */
    public RemoteFolder(String name,  TCPClient tcpClient, Folder parentFolder) throws FileSystemException
    {
        super(name, tcpClient, parentFolder);
        //this.tcpClient = tcpClient;
        //setParentFolder(parentFolder);
        if (parentFolder instanceof RemoteFolder) {
            String parentPath = ((RemoteFolder) parentFolder).remoteAbsolutePath;
            if (Objects.equals(parentPath, "/")) {
                parentPath = "";
            }
            remoteAbsolutePath = parentPath + "/" + name;
        }
    }

    /**
     * Add a FSObject to the folder.
     *
     * @param object {@link FSObject} to add to this folder.
     * @throws FileSystemException iff the file already exists.
     */
    @Override
    public void add(FSObject object) throws FileSystemException {
        add(object.getName(), object instanceof Folder);
    }

    @Override
    public void add(String name, boolean isFolder) throws FileSystemException {
        if (isFolder) {
            tcpClient.mkdir(name);
        } else {
            //Because we don't care about the contents. Just touching the file is all we need to do!
            tcpClient.touch(name);
        }
    }

    /**
     * Removes a {@link FSObject} from the folder.
     *
     * @param object {@link FSObject} to remove from this folder.
     * @throws ObjectNotFoundException iff the {@link FSObject} is not in this folder.
     */
    @Override
    public void delete(FSObject object) throws FileSystemException {
        throw new NotImplementedException();
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
        tcpClient.delete(name);
    }

    /**
     * Get the Content of this {@link Folder} as a {@link List}
     * of {@link FSObject}s.
     *
     * @return the content of this folder.
     */
    @Override
    public List<FSObject> getContent() throws FileSystemException {
        List<String> result = tcpClient.listFolderContent();
        return toFSObjects(result);
    }

    /**
     * Converts a textual File-List representation into a list of File System Objects
     * @param list
     * @return
     */
    private List<FSObject> toFSObjects(List<String> list) throws FileSystemException {
        List<FSObject> fileList = new LinkedList<>();

        for (String line : list) {
            String[] lineArr = line.split("\t");
            if (lineArr.length < 2) continue;
            String name = lineArr[0];
            String type = lineArr[1];
            FSObject object;

            if (Objects.equals(type, LS.FOLDER)) {
                object = new RemoteFolder(name, tcpClient, this);
            } else {
                object = new RemoteFile(name, tcpClient, this);
            }
            fileList.add(object);

        }

        return fileList;
    }

    /**
     * Checks whether a {@link FSObject} exists in this folder
     * identified by the given name.
     *
     * @param name identifier for the {@link FSObject} to check.
     * @return {@code true}, iff a {@link FSObject} exists in this folder identified by the given name.
     */
    @Override
    public boolean exists(String name) throws FileSystemException {
        List<FSObject> contents = getContent();

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
        List<FSObject> contents = getContent();
        for (FSObject object : contents) {
            if (object.getName().equals(name)) {
                return object;
            }
        }

        throw new ObjectNotFoundException(name, FSObjectException.OBJECTNOTFOUND);
    }

    @Override
    public List<FSObject> search(List<FSObject> list, String name) throws FileSystemException{
        List<String> result = tcpClient.search(name);
        return toFSObjects(result);
    }


    public void changeDir() throws FileSystemException {
        tcpClient.changeDirectory(remoteAbsolutePath);
    }
}
