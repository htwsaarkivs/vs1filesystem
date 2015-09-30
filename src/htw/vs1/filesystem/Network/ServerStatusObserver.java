package htw.vs1.filesystem.Network;

import htw.vs1.filesystem.Network.Protocol.ServerStatus;

/**
 * Created by Felix on 30.09.2015.
 */
public interface ServerStatusObserver {

    void serverStatusChanged(ServerStatus newStatus);

}
