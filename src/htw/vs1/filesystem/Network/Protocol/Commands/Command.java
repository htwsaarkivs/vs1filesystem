package htw.vs1.filesystem.Network.Protocol.Commands;

import htw.vs1.filesystem.FileSystem.exceptions.FileSystemException;
import htw.vs1.filesystem.Network.Protocol.Client.ClientProtocol;
import htw.vs1.filesystem.Network.Protocol.Client.SimpleClientProtocol;
import htw.vs1.filesystem.Network.Protocol.Exceptions.SimpleProtocolTerminateConnection;
import htw.vs1.filesystem.Network.Protocol.Replies.ClientReply;
import htw.vs1.filesystem.Network.Protocol.Replies.ServerReply;
import htw.vs1.filesystem.Network.Protocol.Requests.RequestList;
import htw.vs1.filesystem.Network.Protocol.Server.ServerProtocol;

/**
 * Created by markus on 12.06.15.
 */
public interface Command {

    /**
     * Specifies the execute directive for server-side command execution.
     * @param prot A reference to a Protocol context
     * @param requestList A reference to a Stack containing the current and all previous Requests
     * @return
     * @throws SimpleProtocolTerminateConnection
     */
    ServerReply execute(ServerProtocol prot, RequestList requestList) throws SimpleProtocolTerminateConnection;

    /**
     * Specifies the invocation directive for client-side use of commands.
     * @param prot A reference to a Protocol context
     * @return
     * @throws SimpleProtocolTerminateConnection
     */
    ClientReply invoke(ClientProtocol prot, String... parameters) throws SimpleProtocolTerminateConnection, FileSystemException;

    static ClientReply SetUser(ClientProtocol prot, String user)
            throws SimpleProtocolTerminateConnection, FileSystemException
    {
        Command cmd = new SETUSER();
        return cmd.invoke(prot, user);
    }

    static ClientReply SetPass(SimpleClientProtocol prot, String pass)
            throws SimpleProtocolTerminateConnection, FileSystemException
    {
        Command cmd = new SETPASS();
        return cmd.invoke(prot, pass);
    }

    static ClientReply LS(SimpleClientProtocol prot)
            throws SimpleProtocolTerminateConnection, FileSystemException
    {
        Command cmd = new LS();
        return cmd.invoke(prot);
    }

    static ClientReply CD(SimpleClientProtocol prot, String path)
            throws SimpleProtocolTerminateConnection, FileSystemException
    {
        Command cmd = new CD();
        return cmd.invoke(prot, path);
    }

    static ClientReply MKDIR(SimpleClientProtocol prot, String name)
            throws SimpleProtocolTerminateConnection, FileSystemException
    {
        Command cmd = new MKDIR();
        return cmd.invoke(prot, name);
    }

    static ClientReply TOUCH(SimpleClientProtocol prot, String name)
            throws SimpleProtocolTerminateConnection, FileSystemException
    {
        Command cmd = new TOUCH();
        return cmd.invoke(prot, name);
    }

    static ClientReply DELETE(SimpleClientProtocol prot, String name)
            throws SimpleProtocolTerminateConnection, FileSystemException
    {
        Command cmd = new DELETE();
        return cmd.invoke(prot, name);

    }

    static ClientReply RENAME(SimpleClientProtocol clientProtocol, String name, String newName)
            throws SimpleProtocolTerminateConnection, FileSystemException
    {
        Command cmd = new RENAME();
        return cmd.invoke(clientProtocol, name, newName);
    }
}
