package htw.vs1.filesystem.Network.Protocol.Commands;

import htw.vs1.filesystem.Network.Protocol.Protocol;

/**
 * Created by markus on 12.06.15.
 */
public interface Command {


    void execute(Protocol prot);

}
