package htw.vs1.filesystem.Network.Protocol.Replies;

import htw.vs1.filesystem.Network.Protocol.Commands.Command;
import htw.vs1.filesystem.Network.Protocol.Protocol;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCode;

/**
 * Created by markus on 16.06.15.
 */
public interface ServerReply {

    /**
     * Allows to check whether this reply will lead to an immediate termination of the connection.
     * @return
     */
    boolean terminatesConnection();

    /**
     * Trigger output of this command.
     * @param prot
     */
    void putReply(Protocol prot);
}
