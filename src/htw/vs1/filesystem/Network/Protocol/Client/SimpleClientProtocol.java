package htw.vs1.filesystem.Network.Protocol.Client;

import htw.vs1.filesystem.Network.Protocol.Exceptions.SimpleProtocolInitializationErrorException;
import htw.vs1.filesystem.Network.Protocol.SimpleProtocol;

import java.net.Socket;

/**
 * Created by markus on 24.06.15.
 */
public class SimpleClientProtocol extends SimpleProtocol implements ClientProtocol {

    /**
     * Creates a new SimpleProtocol instance.
     *
     * @param socket Reference to a ServerSocket
     * @throws SimpleProtocolInitializationErrorException
     */
    public SimpleClientProtocol(Socket socket) throws SimpleProtocolInitializationErrorException {
        super(socket);
    }
}
