package htw.vs1.filesystem.Network.Protocol.Replies;

import htw.vs1.filesystem.Network.Protocol.Client.ClientProtocol;
import htw.vs1.filesystem.Network.Protocol.Exceptions.SimpleProtocolFatalError;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCode;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCodeFactory;

/**
 * Created by Felix on 30.08.2015.
 */
public class ReplyAnalyzer {

    public ReplyCode parseServerReply(ClientProtocol proto) {
        try {
            proto.readLine();
        } catch (SimpleProtocolFatalError simpleProtocolFatalError) {
            simpleProtocolFatalError.printStackTrace();
        }
        String currentLine = proto.getCurrentLine();

        // TODO: Fehlerbehandlung: currentLine=null | count(currentLine) < 3 | keine 3 stellige Zahl

        String codeStr = currentLine.substring(0,3);
        return ReplyCodeFactory.parseReplyCode(codeStr);
    }



}
