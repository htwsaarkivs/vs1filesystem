package htw.vs1.filesystem.Network.Protocol.Replies;

import htw.vs1.filesystem.Network.Protocol.Client.ClientProtocol;
import htw.vs1.filesystem.Network.Protocol.Exceptions.SimpleProtocolFatalError;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.*;

/**
 * Created by Felix on 30.08.2015.
 */
public class ReplyAnalyzer {

    public ReplyCode parseServerReply(ClientProtocol proto) throws SimpleProtocolFatalError {
        proto.readLine();
        String currentLine = proto.getCurrentLine();

        if (currentLine == null || currentLine.length() < 3) {
            throw new SimpleProtocolFatalError();
        }

        String codeStr = currentLine.substring(0,3);

        ReplyCode replyCode;
        switch (codeStr) {
            case "200":
                replyCode = new ReplyCode200();
                break;
            case "220":
                replyCode = new ReplyCode220();
                break;
            case "230":
                replyCode = new ReplyCode230();
                break;
            case "300":
                replyCode = new ReplyCode300();
                break;
            case "210":
                replyCode = new ReplyCode210();
                break;
            case "219":
                replyCode = new ReplyCode219();
                break;
            default:
                throw new SimpleProtocolFatalError();
        }

        replyCode.setReplyString(currentLine);
        return replyCode;
    }



}
