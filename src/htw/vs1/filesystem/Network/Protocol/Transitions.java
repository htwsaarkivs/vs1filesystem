package htw.vs1.filesystem.Network.Protocol;

/**
 * Created by markus on 11.06.15.
 */
interface Transitions {
    public void clientConnected(SimpleProtocol inst);
    public void clientSuccessfullyAuthenticated(SimpleProtocol inst);
    public void clientDisconnected(SimpleProtocol inst);
}
