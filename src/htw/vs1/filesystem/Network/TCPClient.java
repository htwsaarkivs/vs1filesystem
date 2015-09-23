package htw.vs1.filesystem.Network;
import htw.vs1.filesystem.FileSystem.exceptions.FileSystemException;
import htw.vs1.filesystem.FileSystem.exceptions.FSRemoteException;
import htw.vs1.filesystem.Network.Protocol.Client.SimpleClientProtocol;
import htw.vs1.filesystem.Network.Protocol.Commands.Command;
import htw.vs1.filesystem.Network.Protocol.Exceptions.SimpleProtocolFatalError;
import htw.vs1.filesystem.Network.Protocol.Exceptions.SimpleProtocolInitializationErrorException;
import htw.vs1.filesystem.Network.Protocol.Exceptions.SimpleProtocolTerminateConnection;
import htw.vs1.filesystem.Network.Protocol.Replies.ClientReply;

import java.io.*;
import java.net.Socket;
import java.util.List;

/**
 * Created by ray on 24.06.2015.
 */
public class TCPClient {

    private SimpleClientProtocol clientProtocol;

    //TODO: über Client regeln
    private boolean isAuthenticated = false;

    public TCPClient() throws FileSystemException {
        this("localhost", TCPParallelServer.DEFAULT_PORT, TCPParallelServer.DEFAULT_USER, TCPParallelServer.DEFAULT_PASS);
    }

    public TCPClient(String ip, int port, String user, String pass) throws FileSystemException {
        try {
            clientProtocol = new SimpleClientProtocol(new Socket(ip, port));
            clientProtocol.readLine(); // First skip the Server-Ready output // TODO: evaluate ServerReadyOutput
            authenticate(user, pass);
        } catch (SimpleProtocolInitializationErrorException | IOException | SimpleProtocolFatalError e) {
            throw new FSRemoteException("Connection error.");
        }
    }

    private void authenticate(String user, String pass) throws FileSystemException {
        if (isAuthenticated) return;

        try {
            ClientReply reply;
            reply = Command.SetUser(clientProtocol, user);
            // TODO: reply auswerten, ggf. Exception werfen

            reply = Command.SetPass(clientProtocol, pass);

            isAuthenticated = true;
        } catch (SimpleProtocolTerminateConnection simpleProtocolTerminateConnection) {
            simpleProtocolTerminateConnection.printStackTrace();
        }
    }

    public List<String> listFolderContent() throws FileSystemException {
        try {
            ClientReply reply = Command.LS(clientProtocol);
            if (!reply.success()) {
                throw new FSRemoteException("Something went terribly wrong.");
            }
            return reply.getData();
        } catch (SimpleProtocolTerminateConnection simpleProtocolTerminateConnection) {
            simpleProtocolTerminateConnection.printStackTrace();
        }
        return null;
    }

    public void changeDirectory(String remoteAbsolutePath) throws FileSystemException {
        try {
            ClientReply reply = Command.CD(clientProtocol, remoteAbsolutePath);
            if (!reply.success()) {
                throw new FSRemoteException("Something went terribly wrong.");
            }
        } catch (SimpleProtocolTerminateConnection simpleProtocolTerminateConnection) {
            simpleProtocolTerminateConnection.printStackTrace();
        }
    }

    public void mkdir(String name) throws FileSystemException {
        try {
            ClientReply reply = Command.MKDIR(clientProtocol, name);
            if (!reply.success()) {
                throw new FSRemoteException("Something went terribly wrong.");
            }
        } catch (SimpleProtocolTerminateConnection simpleProtocolTerminateConnection) {
            simpleProtocolTerminateConnection.printStackTrace();
        }
    }

    public void touch(String name) throws FileSystemException {
        try {
            ClientReply reply = Command.TOUCH(clientProtocol, name);
            if (!reply.success()) {
                throw new FSRemoteException("Something went terribly wrong.");
            }
        } catch (SimpleProtocolTerminateConnection simpleProtocolTerminateConnection) {
            simpleProtocolTerminateConnection.printStackTrace();
        }
    }

    public void delete(String name) throws FileSystemException {
        try {
            ClientReply reply = Command.DELETE(clientProtocol, name);
            if (!reply.success()) {
                throw new FSRemoteException("Something went terribly wrong.");
            }
        } catch(SimpleProtocolTerminateConnection simpleProtocolTerminateConnection) {
            simpleProtocolTerminateConnection.printStackTrace();
        }

    }

    public void rename(String name, String newName) throws FileSystemException {
        try {
            ClientReply reply = Command.RENAME(clientProtocol, name, newName);
            if (!reply.success()) {
                throw new FSRemoteException("Something went terribly wrong.");
            }
        } catch (SimpleProtocolTerminateConnection simpleProtocolTerminateConnection) {
            simpleProtocolTerminateConnection.printStackTrace();
        }
    }
}
