package htw.vs1.filesystem.Network;
import htw.vs1.filesystem.FileSystem.exceptions.FileSystemException;
import htw.vs1.filesystem.FileSystemManger;
import htw.vs1.filesystem.Network.Protocol.Client.SimpleClientProtocol;
import htw.vs1.filesystem.Network.Protocol.Commands.Command;
import htw.vs1.filesystem.Network.Protocol.Exceptions.SimpleProtocolFatalError;
import htw.vs1.filesystem.Network.Protocol.Exceptions.SimpleProtocolInitializationErrorException;
import htw.vs1.filesystem.Network.Protocol.Replies.ClientReply;
import htw.vs1.filesystem.Network.Protocol.State.SimpleProtocolState;

import java.io.*;
import java.net.Socket;
import java.util.List;

/**
 * Created by ray on 24.06.2015.
 */
public class TCPClient {

    private SimpleClientProtocol clientProtocol;

    private String ip;
    private int port;
    private String user;
    private String pass;

    private Socket socket;

    public TCPClient(String ip, int port, String user, String pass) throws FileSystemException {
            this.user = user;
            this.pass = pass;
            this.ip = ip;
            this.port = port;
    }

    private void connect() throws FileSystemException {
        try {
            socket = new Socket(ip, port);
            socket.setKeepAlive(true);
            clientProtocol = new SimpleClientProtocol(socket);
            clientProtocol.readLine(); // First skip the Server-Ready output // TODO: evaluate ServerReadyOutput
            clientProtocol.setState(SimpleProtocolState.READY);
        } catch (IOException e) {
            if (FileSystemManger.DEBUG) {
                e.printStackTrace();
            }
            throw new SimpleProtocolInitializationErrorException();
        }

    }

    private void authenticate(String user, String pass) throws FileSystemException {
            Command.SetUser(clientProtocol, user);
            Command.SetPass(clientProtocol, pass);
    }

    /**
     * Checks whether the protocol is in the connection state.
     *
     * @return {@code true}, iff the protocol is in the connection state.
     * @throws FileSystemException
     */
    private boolean isProtocolConnectedState() throws FileSystemException {
        if (null == clientProtocol || clientProtocol.getState().equals(SimpleProtocolState.IDLE)) {
            // if the protocol is not instantiated or has the idle state the connection is not established
            return false;
        }

        // otherwise we have to ensure that we can execute the noop-command to proof that we are connected
        try {
            Command.NOOP(clientProtocol);
        } catch (SimpleProtocolFatalError e) {
            // we get this exception if the server closed our connection meanwhile
            if (FileSystemManger.DEBUG) {
                e.printStackTrace();
            }
            return false;
        }

        return true;

        //return !(null == clientProtocol || clientProtocol.getState().equals(SimpleProtocolState.IDLE));
    }

    private void checkAuthStatusTryToLoginIfNecessary() throws FileSystemException {
        if (!isProtocolConnectedState()) {
            connect();
        }

        if (clientProtocol.getState() != SimpleProtocolState.AUTHENTICATED) {
            authenticate(this.user, this.pass);
        }
    }

    public List<String> listFolderContent() throws FileSystemException {
        checkAuthStatusTryToLoginIfNecessary();
        ClientReply reply = Command.LS(clientProtocol);
        return reply.getData();
    }

    public void changeDirectory(String remoteAbsolutePath) throws FileSystemException {
        checkAuthStatusTryToLoginIfNecessary();
        Command.CD(clientProtocol, remoteAbsolutePath);
    }

    public void mkdir(String name) throws FileSystemException {
        checkAuthStatusTryToLoginIfNecessary();
        Command.MKDIR(clientProtocol, name);
    }

    public void touch(String name) throws FileSystemException {
        checkAuthStatusTryToLoginIfNecessary();
        Command.TOUCH(clientProtocol, name);
    }

    public void delete(String name) throws FileSystemException {
        checkAuthStatusTryToLoginIfNecessary();
        Command.DELETE(clientProtocol, name);
    }

    public void rename(String name, String newName) throws FileSystemException {
        checkAuthStatusTryToLoginIfNecessary();
        Command.RENAME(clientProtocol, name, newName);
    }

    public List<String> search(String name) throws FileSystemException{
        checkAuthStatusTryToLoginIfNecessary();
        ClientReply reply = Command.SEARCH(clientProtocol, name);
        return reply.getData();
    }

    public void closeConnection(){
        try {
            if (!isProtocolConnectedState()) return;
            Command.EXIT(clientProtocol);
        } catch (FileSystemException e) {
            // if we cannot close the connection it is maybe already closed.
            if (FileSystemManger.DEBUG) {
                e.printStackTrace();
            }
        }

    }
}
