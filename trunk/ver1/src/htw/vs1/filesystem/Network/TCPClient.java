package htw.vs1.filesystem.Network;
import com.sun.deploy.util.SessionState;
import htw.vs1.filesystem.FileSystem.exceptions.FileSystemException;
import htw.vs1.filesystem.FileSystem.exceptions.FSRemoteException;
import htw.vs1.filesystem.Network.Protocol.Client.SimpleClientProtocol;
import htw.vs1.filesystem.Network.Protocol.Commands.Command;
import htw.vs1.filesystem.Network.Protocol.Exceptions.SimpleProtocolFatalError;
import htw.vs1.filesystem.Network.Protocol.Exceptions.SimpleProtocolInitializationErrorException;
import htw.vs1.filesystem.Network.Protocol.Exceptions.SimpleProtocolTerminateConnection;
import htw.vs1.filesystem.Network.Protocol.Replies.ClientReply;
import htw.vs1.filesystem.Network.Protocol.SimpleProtocol;
import htw.vs1.filesystem.Network.Protocol.State.SimpleProtocolState;

import java.io.*;
import java.net.Socket;
import java.util.List;

/**
 * Created by ray on 24.06.2015.
 */
public class TCPClient {

    private SimpleClientProtocol clientProtocol;


    private String user;
    private String pass;

    //TODO: über Client regeln
    private boolean isAuthenticated = false;

    public TCPClient() throws FileSystemException {
        this("localhost", TCPParallelServer.DEFAULT_PORT, TCPParallelServer.DEFAULT_USER, TCPParallelServer.DEFAULT_PASS);
    }

    public TCPClient(String ip, int port, String user, String pass) throws FileSystemException {
            try {
                this.user = user;
                this.pass = pass;
                clientProtocol = new SimpleClientProtocol(new Socket(ip, port));
                clientProtocol.readLine(); // First skip the Server-Ready output // TODO: evaluate ServerReadyOutput
                clientProtocol.setState(SimpleProtocolState.READY);
            } catch (IOException e) {
                throw new SimpleProtocolInitializationErrorException();
            }
    }

    private void authenticate(String user, String pass) throws FileSystemException {
            ClientReply reply;
            reply = Command.SetUser(clientProtocol, user);
            reply = Command.SetPass(clientProtocol, pass);
    }

    private void checkAuthStatusTryToLoginIfNecessary() throws FileSystemException {
        if (clientProtocol.getState() != SimpleProtocolState.AUTHENTICATED) {
            authenticate(this.user, this.pass);
        }
        return;
    }

    public List<String> listFolderContent() throws FileSystemException {
        checkAuthStatusTryToLoginIfNecessary();
        ClientReply reply = Command.LS(clientProtocol);
        return reply.getData();
    }

    public void changeDirectory(String remoteAbsolutePath) throws FileSystemException {
        checkAuthStatusTryToLoginIfNecessary();
        ClientReply reply = Command.CD(clientProtocol, remoteAbsolutePath);
        return;
    }

    public void mkdir(String name) throws FileSystemException {
        checkAuthStatusTryToLoginIfNecessary();
        ClientReply reply = Command.MKDIR(clientProtocol, name);
        return;
    }

    public void touch(String name) throws FileSystemException {
        checkAuthStatusTryToLoginIfNecessary();
        ClientReply reply = Command.TOUCH(clientProtocol, name);
        return;
    }

    public void delete(String name) throws FileSystemException {
        checkAuthStatusTryToLoginIfNecessary();
        ClientReply reply = Command.DELETE(clientProtocol, name);
        return;
    }

    public void rename(String name, String newName) throws FileSystemException {
        checkAuthStatusTryToLoginIfNecessary();
        ClientReply reply = Command.RENAME(clientProtocol, name, newName);
        return;
    }

    public List<String> search(String name) throws FileSystemException{
        checkAuthStatusTryToLoginIfNecessary();
        ClientReply reply = Command.SEARCH(clientProtocol, name);
        return reply.getData();
    }
}
