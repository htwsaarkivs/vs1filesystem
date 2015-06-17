package htw.vs1.filesystem.Network.Protocol.Commands;

import htw.vs1.filesystem.Network.Protocol.Protocol;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCode;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCode100;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCode200;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCode401;
import htw.vs1.filesystem.Network.Protocol.Replies.Reply;
import htw.vs1.filesystem.Network.Protocol.Replies.SimpleProtocolReply;
import htw.vs1.filesystem.Network.Protocol.Requests.RequestList;
import htw.vs1.filesystem.Network.Protocol.State.SimpleProtocolState;

/**
 * Created by markus on 12.06.15.
 */
public class SETPASS extends AbstractCommand {
    public static final String COMMAND_STRING = "SETPASS";

    public Reply execute(Protocol prot, RequestList requestList) {
        if (requestList.getCurrentElement().numOfArguments() != 1) {
            return new SimpleProtocolReply(new ReplyCode401(COMMAND_STRING+" must have exactly one argument."), this);
        }
        String pass = requestList.getCurrentElement().getArguments().get(0);


        //Jetzt muss geprüft werden, ob zuvor SETUSER gesetzt wurde
        boolean failure = true;
        try {
            failure = !SETUSER.isValid(requestList.getPreviousElement());

        } catch (IndexOutOfBoundsException e) {
            //Nothing to do here. Boobs!
        }


        if (failure)
            return new SimpleProtocolReply(
                new ReplyCode401(
                        COMMAND_STRING+ "  must be preceeded by "+SETUSER.COMMAND_STRING),
                this);

        String user = requestList.getPreviousElement().getArguments().get(0);

        //Username/Passwort korrekt

        if (true) {
            prot.setState(SimpleProtocolState.AUTHENTICATED);
        }

        return new SimpleProtocolReply(new ReplyCode100("YO YOO ARE LOGGED IN BIATCH"), this);


    }
}
