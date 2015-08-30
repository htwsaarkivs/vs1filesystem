package htw.vs1.filesystem.FileSystem.virtual;

import com.sun.istack.internal.Nullable;
import htw.vs1.filesystem.FileSystem.exceptions.CouldNotCreateException;
import htw.vs1.filesystem.FileSystem.exceptions.CouldNotRenameException;
import htw.vs1.filesystem.FileSystem.exceptions.InvalidFilenameException;
import htw.vs1.filesystem.FileSystem.exceptions.ObjectNotFoundException;
import htw.vs1.filesystem.Network.Protocol.Client.SimpleClientProtocol;
import htw.vs1.filesystem.Network.Protocol.Commands.CommandFactory;
import htw.vs1.filesystem.Network.Protocol.Commands.LS;
import htw.vs1.filesystem.Network.Protocol.Exceptions.SimpleProtocolFatalError;
import htw.vs1.filesystem.Network.Protocol.Exceptions.SimpleProtocolInitializationErrorException;
import htw.vs1.filesystem.Network.Protocol.Exceptions.SimpleProtocolTerminateConnection;
import htw.vs1.filesystem.Network.Protocol.Replies.ClientReply;
import htw.vs1.filesystem.Network.TCPClient;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.IOException;
import java.net.Socket;
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

    private TCPClient tcpClient;

    private Folder parentFolder;

    public RemoteFolder(String name, String remoteIP, int remotePort, String user, String pass)
            throws CouldNotRenameException, FileAlreadyExistsException, InvalidFilenameException, CouldNotCreateException {
        super(name);
        tcpClient = new TCPClient(remoteIP, remotePort, user, pass);
    }

    /**
     * Creates a new Folder with the given name and an
     * existing SimpleClientProtocol.
     *
     * @param name name of the new {@link Folder}.
     */
    public RemoteFolder(String name,  TCPClient tcpClient)
            throws CouldNotRenameException, FileAlreadyExistsException, InvalidFilenameException
    {
        super(name);
        this.tcpClient = tcpClient;
    }

    /**
     * Get the parent {@link Folder} containing this Folder.
     * Can be {@link null}, iff this is the root-Folder.
     *
     * @return the parent {@link Folder} or {@code null} iff this is the root-Folder.
     */
    @Override
    public Folder getParentFolder() {
        return parentFolder;
    }

    /**
     * Sets the parent {@link Folder} containing this FSObject. Can be
     * {@link null}, iff this is the root-Folder.
     *
     * @param parentFolder the parent {@link Folder} or {@code null} iff this is the
     *                     root-Folder.
     */
    @Override
    public void setParentFolder(@Nullable Folder parentFolder) {
        this.parentFolder = parentFolder;
    }

    /**
     * Add a FSObject to the folder.
     *
     * @param object {@link FSObject} to add to this folder.
     * @throws FileAlreadyExistsException iff the file already exists.
     */
    @Override
    public void add(FSObject object) throws FileAlreadyExistsException {
        throw new NotImplementedException();
    }

    /**
     * Removes a {@link FSObject} from the folder.
     *
     * @param object {@link FSObject} to remove from this folder.
     * @throws ObjectNotFoundException iff the {@link FSObject} is not in this folder.
     */
    @Override
    public void delete(FSObject object) throws ObjectNotFoundException {
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
    public void delete(String name) throws ObjectNotFoundException {
        throw new NotImplementedException();
    }

    /**
     * Get the Content of this {@link Folder} as a {@link List}
     * of {@link FSObject}s.
     *
     * @return the content of this folder.
     */
    @Override
    public List<FSObject> getContent() {
        List<String> result = tcpClient.listFolderContent();

        List<FSObject> fileList = new LinkedList<>();

        for (String line : result) {
            String[] lineArr = line.split("\t");
            if (lineArr.length < 2) continue;
            String name = lineArr[0];
            String type = lineArr[1];
            FSObject object;

            try {
                if (Objects.equals(type, LS.FOLDER)) {
                    object = new RemoteFolder(name, tcpClient);
                } else {
                    object = new RemoteFile(name);
                }
                object.setParentFolder(this);
                fileList.add(object);
            } catch (CouldNotRenameException e) {
                e.printStackTrace();
            } catch (FileAlreadyExistsException e) {
                e.printStackTrace();
            } catch (InvalidFilenameException e) {
                e.printStackTrace();
            }

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
    public boolean exists(String name) {
        throw new NotImplementedException();
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
    public FSObject getObject(String name) throws ObjectNotFoundException {
        throw new NotImplementedException();
    }

    @Override
    public LinkedList<FSObject> search(LinkedList<FSObject> list, String name) {
        throw new NotImplementedException();
    }
}
