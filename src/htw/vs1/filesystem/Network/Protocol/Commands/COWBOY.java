package htw.vs1.filesystem.Network.Protocol.Commands;

import htw.vs1.filesystem.Network.Protocol.Client.ClientProtocol;
import htw.vs1.filesystem.Network.Protocol.Exceptions.SimpleProtocolTerminateConnection;
import htw.vs1.filesystem.Network.Protocol.Replies.ClientReply;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCode100;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCode599;
import htw.vs1.filesystem.Network.Protocol.Replies.ServerReply;
import htw.vs1.filesystem.Network.Protocol.Replies.SimpleServerProtocolReply;
import htw.vs1.filesystem.Network.Protocol.Requests.RequestList;
import htw.vs1.filesystem.Network.Protocol.Server.ServerProtocol;

/**
 * Created by markus on 20.06.15.
 */
public class COWBOY extends AbstractCommand {
    public static final String COMMAND_STRING = "COWBOY";

    @Override
    public ServerReply execute(ServerProtocol prot, RequestList requestList) throws SimpleProtocolTerminateConnection {
        String cowboy =
                "            ___\n" +
                "         __|___|__\n" +
                "          ('o_o')\n" +
                "          _\\~-~/_    ______.\n" +
                "         //\\__/\\ \\ ~(_]---'\n" +
                "        / )O  O( .\\/_)\n" +
                "        \\ \\    / \\_/\n" +
                "        )/_|  |_\\\n" +
                "       // /(\\/)\\ \\\n" +
                "       /_/      \\_\\\n" +
                "      (_||      ||_)\n" +
                "        \\| |__| |/\n" +
                "         | |  | |\n" +
                "         | |  | |\n" +
                "         |_|  |_|\n" +
                "         /_\\  /_\\";


        String[] lines = cowboy.split("\n");

        for(String eachLine: lines) {
            new SimpleServerProtocolReply(new ReplyCode100(eachLine), this).putReply(prot);
        }

        return new SimpleServerProtocolReply(new ReplyCode599(), this);

    }

    @Override
    public ClientReply invoke(ClientProtocol port) throws SimpleProtocolTerminateConnection {
        return null;
    }
}
