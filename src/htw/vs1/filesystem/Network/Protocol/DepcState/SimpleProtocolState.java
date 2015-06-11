package htw.vs1.filesystem.Network.Protocol.DepcState;

import htw.vs1.filesystem.Network.Protocol.SimpleProtocol;
import htw.vs1.filesystem.Network.Protocol.StateMachine.depc.Transitions;
import htw.vs1.filesystem.Network.Protocol.DepcState.Transitions;

/**
 * Simple State Machine for our Protocol
 */
public enum SimpleProtocolState implements Transitions {

    IDLE {
        @Override
        public void clientConnected(SimpleProtocol inst) {
            inst.setSimpleProtocolState(READY);
        }

        @Override
        public void clientSuccessfullyAuthenticated(SimpleProtocol inst) {
            return;
        }

        @Override
        public void clientDisconnected(SimpleProtocol inst) {
            return;
        }

    },

    READY {
        @Override
        public void clientConnected(SimpleProtocol inst) {
            return;
        }

        @Override
        public void clientSuccessfullyAuthenticated(SimpleProtocol inst) {
            inst.setSimpleProtocolState(AUTHENTICATED);
        }

        @Override
        public void clientDisconnected(SimpleProtocol inst) {
            return;
        }
    },

    AUTHENTICATED {
        @Override
        public void clientConnected(SimpleProtocol inst) {
            return;
        }

        @Override
        public void clientSuccessfullyAuthenticated(SimpleProtocol inst) {
            return;
        }

        @Override
        public void clientDisconnected(SimpleProtocol inst) {
            inst.setSimpleProtocolState(IDLE);
        }
    }
}