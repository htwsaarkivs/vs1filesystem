package htw.vs1.filesystem.Network;
import htw.vs1.filesystem.FileSystem.exceptions.FileSystemException;
import htw.vs1.filesystem.Network.Protocol.Client.SimpleClientProtocol;
import htw.vs1.filesystem.Network.Protocol.Commands.Command;
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
            clientProtocol = new SimpleClientProtocol(socket);
            clientProtocol.readLine(); // First skip the Server-Ready output // TODO: evaluate ServerReadyOutput
            clientProtocol.setState(SimpleProtocolState.READY);
        } catch (IOException e) {
            throw new SimpleProtocolInitializationErrorException();
        }

    }

    private void authenticate(String user, String pass) throws FileSystemException {
            Command.SetUser(clientProtocol, user);
            Command.SetPass(clientProtocol, pass);
    }

    private void checkAuthStatusTryToLoginIfNecessary() throws FileSystemException {
        if (null == clientProtocol || clientProtocol.getState().equals(SimpleProtocolState.IDLE)) {
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
}
