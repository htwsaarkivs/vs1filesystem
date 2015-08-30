package htw.vs1.filesystem.Network.Protocol.Client;

import htw.vs1.filesystem.Network.Protocol.Commands.Command;
import htw.vs1.filesystem.Network.Protocol.Exceptions.SimpleProtocolFatalError;
import htw.vs1.filesystem.Network.Protocol.Exceptions.SimpleProtocolTerminateConnection;
import htw.vs1.filesystem.Network.Protocol.Protocol;
import htw.vs1.filesystem.Network.Protocol.Replies.ClientReply;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCode;

/**
 * Created by markus on 24.06.15.
 */
public interface ClientProtocol extends Protocol {

    void readLine() throws SimpleProtocolFatalError;
    ClientReply executeCommand(Command command) throws SimpleProtocolTerminateConnection;
    ReplyCode analyzeReply() throws SimpleProtocolFatalError;

}
