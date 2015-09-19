package htw.vs1.filesystem.Network;
import htw.vs1.filesystem.FileSystem.exceptions.FSObjectException;
import htw.vs1.filesystem.FileSystem.exceptions.FSRemoteException;
import htw.vs1.filesystem.Network.Protocol.Client.SimpleClientProtocol;
import htw.vs1.filesystem.Network.Protocol.Commands.Command;
import htw.vs1.filesystem.Network.Protocol.Commands.CommandFactory;
import htw.vs1.filesystem.Network.Protocol.Exceptions.SimpleProtocolFatalError;
import htw.vs1.filesystem.Network.Protocol.Exceptions.SimpleProtocolInitializationErrorException;
import htw.vs1.filesystem.Network.Protocol.Exceptions.SimpleProtocolTerminateConnection;
import htw.vs1.filesystem.Network.Protocol.Replies.ClientReply;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.*;
import java.net.Socket;
import java.util.List;

/**
 * Created by ray on 24.06.2015.
 */
public class TCPClient {

    private SimpleClientProtocol clientProtocol;

    private boolean isAuthenticated = false;

    public static void main(String[] args) throws Exception {
        TCPClient client = new TCPClient();

    }

    public TCPClient() throws FSObjectException {
        this("localhost", TCPParallelServer.DEFAULT_PORT, TCPParallelServer.DEFAULT_USER, TCPParallelServer.DEFAULT_PASS);
    }

    public TCPClient(String ip, int port, String user, String pass) throws FSObjectException {
        try {
            clientProtocol = new SimpleClientProtocol(new Socket(ip, port));
            clientProtocol.readLine(); // First skip the Server-Ready output // TODO: evaluate ServerReadyOutput
            authenticate(user, pass);
        } catch (SimpleProtocolInitializationErrorException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SimpleProtocolFatalError simpleProtocolFatalError) {
            simpleProtocolFatalError.printStackTrace();
        }
    }

    private void authenticate(String user, String pass) throws FSObjectException {
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

    public List<String> listFolderContent() throws FSObjectException {
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

    public void changeDirectory(String remoteAbsolutePath) throws FSObjectException {
        try {
            ClientReply reply = Command.CD(clientProtocol, remoteAbsolutePath);
            if (!reply.success()) {
                throw new FSRemoteException("Something went terribly wrong.");
            }
        } catch (SimpleProtocolTerminateConnection simpleProtocolTerminateConnection) {
            simpleProtocolTerminateConnection.printStackTrace();
        }
    }

    public void mkdir(String name) throws FSObjectException {
        try {
            ClientReply reply = Command.MKDIR(clientProtocol, name);
            if (!reply.success()) {
                throw new FSRemoteException("Something went terribly wrong.");
            }
        } catch (SimpleProtocolTerminateConnection simpleProtocolTerminateConnection) {
            simpleProtocolTerminateConnection.printStackTrace();
        }
    }

    public void touch(String name) throws FSObjectException {
        try {
            ClientReply reply = Command.TOUCH(clientProtocol, name);
            if (!reply.success()) {
                throw new FSRemoteException("Something went terribly wrong.");
            }
        } catch (SimpleProtocolTerminateConnection simpleProtocolTerminateConnection) {
            simpleProtocolTerminateConnection.printStackTrace();
        }
    }

    public void delete(String name) throws FSObjectException {
        try {
            ClientReply reply = Command.DELETE(clientProtocol, name);
            if (!reply.success()) {
                throw new FSRemoteException("Something went terribly wrong.");
            }
        } catch(SimpleProtocolTerminateConnection simpleProtocolTerminateConnection) {
            simpleProtocolTerminateConnection.printStackTrace();
        }

    }

}
