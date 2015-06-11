package htw.vs1.filesystem.Network.Protocol.DepcState;

import htw.vs1.filesystem.Network.Protocol.SimpleProtocol;

/**
 * Created by markus on 11.06.15.
 */
interface Transitions {
    public void clientConnected(SimpleProtocol inst);
    public void clientSuccessfullyAuthenticated(SimpleProtocol inst);
    public void clientDisconnected(SimpleProtocol inst);
}
