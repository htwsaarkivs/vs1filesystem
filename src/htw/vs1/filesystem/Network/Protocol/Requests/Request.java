package htw.vs1.filesystem.Network.Protocol.Requests;

import java.util.List;

/**
 * Created by markus on 12.06.15.
 */
public interface Request {
    /**
     * Get the command-string of the current request.
     * @return
     */
    String getCommandString();

    /**
     * Allows to check whether the current request has any arguments.
     * @return
     */
    boolean hasArguments();

    /**
     * Returns the number of Arguments of the current request.
     * @return
     */
    int numOfArguments();

    /**
     * Returns a @List<String> of the curent command.
     * @return
     */
    List<String> getArguments();


}
