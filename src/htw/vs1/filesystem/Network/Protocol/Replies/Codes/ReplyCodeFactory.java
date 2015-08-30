package htw.vs1.filesystem.Network.Protocol.Replies.Codes;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by Felix on 30.08.2015.
 */
public class ReplyCodeFactory {

    public static ReplyCode parseReplyCode(String codeStr) {
        switch (codeStr) {
            case "210":
                return new ReplyCode210();
            case "219":
                return new ReplyCode219();
            default:
                throw new NotImplementedException();
        }
    }

}
