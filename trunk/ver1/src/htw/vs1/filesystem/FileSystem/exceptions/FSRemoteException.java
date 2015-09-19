package htw.vs1.filesystem.FileSystem.exceptions;

import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCode;

/**
 * Created by Felix on 19.09.2015.
 */
public class FSRemoteException extends FSObjectException {

    public FSRemoteException(String msg) {
        super(msg);
    }

    @Override
    public ReplyCode getReplyCode() {
        // This method does not have any reply code because it is not cause by a file system error but
        // by a protocol error.
        return null;
    }
}
