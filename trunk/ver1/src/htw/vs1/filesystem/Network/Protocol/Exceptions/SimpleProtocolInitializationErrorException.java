package htw.vs1.filesystem.Network.Protocol.Exceptions;

import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCode;

/**
 * Created by markus on 11.06.15.
 */
public class SimpleProtocolInitializationErrorException extends SimpleProtocolException {

    private static final String MESSAGE = "Network error. Connection refused.";

    public SimpleProtocolInitializationErrorException() {
        super(MESSAGE);
    }

    @Override
    public ReplyCode getReplyCode() {
        return null;
    }
}
