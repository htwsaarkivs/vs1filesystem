package htw.vs1.filesystem.Network.Protocol.StateMachine;

/**
 * Created by markus on 12.06.15.
 */
interface Statelike {

    void sendReadyMessage();
    void promptForUserAndPw();
}
