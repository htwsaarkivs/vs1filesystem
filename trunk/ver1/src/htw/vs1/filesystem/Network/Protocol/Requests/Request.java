package htw.vs1.filesystem.Network.Protocol.Requests;

import java.util.List;

/**
 * Created by markus on 12.06.15.
 */
public interface Request {
    String getCommandString();

    boolean hasArguments();
    int numOfArguments();
    List<String> getArguments();


}
