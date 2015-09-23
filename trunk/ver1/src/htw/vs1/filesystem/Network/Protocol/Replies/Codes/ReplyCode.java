package htw.vs1.filesystem.Network.Protocol.Replies.Codes;

import htw.vs1.filesystem.FileSystem.exceptions.FileSystemException;
import htw.vs1.filesystem.Network.Protocol.Replies.Type.Type;

/**
 * Created by markus on 16.06.15.
 */
public abstract class ReplyCode {

    protected String message;
    protected String additionalMessage;

    /**
     * Returns a textual, human-readable explanation of the issued reply
     * @return
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * Reply code, of the reply as per our convention.
     * @return
     */
    public abstract int getCode();

    /**
     * Get a human-readble of the Reply for debugging purposes and educational use.
     * @return
     */
    public abstract String getDescription();

    /**
     * Get the type of reply as per our convention.
     * @return
     */
    public abstract Type getReplyType();

    public void setReplyString(String reply) {
        this.message = reply.substring(4, getMessage().length() + 4);
        if (reply.length() > getMessage().length() + 5) {
            this.additionalMessage = reply.substring(getMessage().length() + 5);
        }
    }

    public FileSystemException getException() {
        return null;
    }

    public void setAdditionalMessage(String message) {
        this.additionalMessage = message;
    }

    @Override
    public String toString() {
        return getCode() + " " + getMessage() + " " + getAdditionalMessage();
    }

    public String getAdditionalMessage() {
        if (null == additionalMessage) return "";
        return additionalMessage;
    }
}

