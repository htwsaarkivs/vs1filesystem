package htw.vs1.filesystem.Network.Protocol.Server;

import htw.vs1.filesystem.FileSystem.exceptions.FileSystemException;
import htw.vs1.filesystem.FileSystem.virtual.FileSystemInterface;
import htw.vs1.filesystem.Main;
import htw.vs1.filesystem.Network.Protocol.Commands.CommandFactory;
import htw.vs1.filesystem.Network.Protocol.Exceptions.SimpleProtocolInitializationErrorException;
import htw.vs1.filesystem.Network.Protocol.Exceptions.SimpleProtocolTerminateConnection;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCode200;
import htw.vs1.filesystem.Network.Protocol.Replies.ServerReply;
import htw.vs1.filesystem.Network.Protocol.Replies.SimpleServerProtocolReply;
import htw.vs1.filesystem.Network.Protocol.Requests.RequestAnalyzer;
import htw.vs1.filesystem.Network.Protocol.SimpleProtocol;
import htw.vs1.filesystem.Network.Protocol.State.SimpleProtocolState;

import java.net.Socket;

/**
 * The servers protocol implementation holding the
 * {@link FileSystemInterface} implementation the server
 * works with.
 *
 * Created by markus on 24.06.15.
 */
public class SimpleServerProtocol extends SimpleProtocol implements ServerProtocol {

    /**
     * The FileSystem the server works with.
     */
    private FileSystemInterface fileSystem;



    /**
     * Creates a new SimpleProtocol instance.
     *
     * @param socket     Reference to a ServerSocket
     * @param fileSystem {@link FileSystemInterface} implementation the server should work with.
     * @throws SimpleProtocolInitializationErrorException
     */
    public SimpleServerProtocol(Socket socket, FileSystemInterface fileSystem) throws SimpleProtocolInitializationErrorException {
        super(socket);
        this.fileSystem = fileSystem;
    }

    public void run() {
        RequestAnalyzer analyzer = new RequestAnalyzer(new CommandFactory());

        new SimpleServerProtocolReply(new ReplyCode200(Main.VERSION), null).putReply(this);
        this.setState(SimpleProtocolState.READY);

        while (true) {
            try {
                readLine();
                ServerReply reply = analyzer.parseCommand(this);
                if (null != reply) {
                    reply.putReply(this);
                    if (reply.terminatesConnection()) throw new SimpleProtocolTerminateConnection();
                }
            } catch (FileSystemException e) {
                putLine("Connection terminated: " + e.toString());
                e.printStackTrace();
                break;
            }
        }

    }

    public FileSystemInterface getFileSystem() {
        return this.fileSystem;
    }
}
