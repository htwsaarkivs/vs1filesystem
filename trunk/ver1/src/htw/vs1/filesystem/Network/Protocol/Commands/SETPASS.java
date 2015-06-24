package htw.vs1.filesystem.Network.Protocol.Commands;

import htw.vs1.filesystem.Network.Protocol.Client.ClientProtocol;
import htw.vs1.filesystem.Network.Protocol.Exceptions.SimpleProtocolTerminateConnection;
import htw.vs1.filesystem.Network.Protocol.Replies.ClientReply;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.*;
import htw.vs1.filesystem.Network.Protocol.Replies.ServerReply;
import htw.vs1.filesystem.Network.Protocol.Replies.SimpleServerProtocolReply;
import htw.vs1.filesystem.Network.Protocol.Requests.RequestList;
import htw.vs1.filesystem.Network.Protocol.Server.ServerProtocol;
import htw.vs1.filesystem.Network.Protocol.State.SimpleProtocolState;

/**
 * Created by markus on 12.06.15.
 */
public class SETPASS extends AbstractCommand {
    public static final String COMMAND_STRING = "SETPASS";

    public ServerReply execute(ServerProtocol prot, RequestList requestList) {
        if (requestList.getCurrentElement().numOfArguments() != 1) {
            return new SimpleServerProtocolReply(new ReplyCode401(COMMAND_STRING+" must have exactly one argument."), this);
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
            return new SimpleServerProtocolReply(
                new ReplyCode401(
                        COMMAND_STRING+ "  must be preceeded by "+SETUSER.COMMAND_STRING),
                this);

        String user = requestList.getPreviousElement().getArguments().get(0);

        //Username/Passwort korrekt

        if (true) {
            prot.setState(SimpleProtocolState.AUTHENTICATED);
        }

        return new SimpleServerProtocolReply(new ReplyCode220(user), this);


    }

    @Override
    public ClientReply invoke(ClientProtocol prot) throws SimpleProtocolTerminateConnection {
        return null;
    }
}
