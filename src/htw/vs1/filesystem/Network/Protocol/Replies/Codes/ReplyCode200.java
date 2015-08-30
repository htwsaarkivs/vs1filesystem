package htw.vs1.filesystem.Network.Protocol.Replies.Codes;

import htw.vs1.filesystem.Network.Protocol.Replies.Type.SimpleProtocolReplyType;
import htw.vs1.filesystem.Network.Protocol.Replies.Type.Type;

/**
 * Created by markus on 16.06.15.
 */
public class ReplyCode200 extends ReplyCode{

    public static final Type REPLY_TYPE = SimpleProtocolReplyType.CONFIRMATION;
    public static final int CODE = 200;
    public static final String DESCRIPTION = "Informative reply, which supplies additional information to the end-user.";
    public static final String STANDARD_MESSAGE = "SERVER READY";
    public static final String VERSION_LABEL = " VERSION: ";

    private double version;

    public ReplyCode200() {

    }

    /**
     * Generates a new 200-Reply
     * @param version specifies Server version
     */
    public ReplyCode200(double version) {
        this.version = version;
        this.message = STANDARD_MESSAGE + VERSION_LABEL + version;
    }

    @Override
    public void setReplyString(String reply) {
        super.setReplyString(reply);
        int start = STANDARD_MESSAGE.length() + VERSION_LABEL.length();
        String verStr = message.substring(start);
        // TODO: NumberFormatException ?
        this.version = Double.parseDouble(verStr);
    }

    @Override
    public int getCode() {
        return CODE;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

    @Override
    public Type getReplyType() {
        return REPLY_TYPE;
    }

    public double getVersion() {
        return version;
    }
}