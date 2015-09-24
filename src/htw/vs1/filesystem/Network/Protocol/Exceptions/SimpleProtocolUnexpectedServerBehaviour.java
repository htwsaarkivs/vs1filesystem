package htw.vs1.filesystem.Network.Protocol.Exceptions;

import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCode;

/**
 * Created by markus on 24.09.15.
 */
public class SimpleProtocolUnexpectedServerBehaviour extends SimpleProtocolException {

    @Override
    public ReplyCode getReplyCode() {
        return null;
    }
}
