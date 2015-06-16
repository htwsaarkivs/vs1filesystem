package htw.vs1.filesystem.Network.Protocol.State;

/**
 * Holds all valid states of @SimpleProtocol
 */
public enum SimpleProtocolState implements State {
    IDLE,
    READY,
    AUTHENTICATED
}
