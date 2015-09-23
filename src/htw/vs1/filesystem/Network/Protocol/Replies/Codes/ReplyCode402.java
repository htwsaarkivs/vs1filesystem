package htw.vs1.filesystem.Network.Protocol.Replies.Codes;

import htw.vs1.filesystem.FileSystem.exceptions.FileSystemException;
import htw.vs1.filesystem.FileSystem.exceptions.ObjectNotFoundException;
import htw.vs1.filesystem.Network.Protocol.Replies.Type.SimpleProtocolReplyType;
import htw.vs1.filesystem.Network.Protocol.Replies.Type.Type;

/**
 * Created by Hendrik on 17.06.2015.
 */
public class ReplyCode402 extends ReplyCode{


    public static final Type REPLY_TYPE = SimpleProtocolReplyType.ERROR;
    public static final int CODE = 402;
    public static final String DESCRIPTION = "";
    public static final String STANDARD_MESSAGE = "FILE OR FOLDER DOES NOT EXIST";


    public ReplyCode402() { this.message = STANDARD_MESSAGE; }

    @Override
    public int getCode() { return CODE; }

    @Override
    public String getDescription() { return DESCRIPTION; }

    @Override
    public Type getReplyType() { return REPLY_TYPE; }


    @Override
    public FileSystemException getException() {
        return new ObjectNotFoundException(this.additionalMessage);
    }
}
