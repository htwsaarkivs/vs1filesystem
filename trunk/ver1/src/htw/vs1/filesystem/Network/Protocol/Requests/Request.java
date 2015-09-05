package htw.vs1.filesystem.Network.Protocol.Requests;

import java.util.List;

/**
 * Interface of a request.
 *
 * Created by markus on 12.06.15.
 */
public interface Request {
    /**
     * Get the command-string of the current request.
     * @return commans-string of the current request.
     */
    String getCommandString();

    /**
     * Allows to check whether the current request has any arguments.
     * @return {@code true}, iff the current request has any argument.
     */
    boolean hasArguments();

    /**
     * Returns the number of Arguments of the current request.
     * @return number of arguments of the current request.
     */
    int numOfArguments();

    /**
     * Returns a {@link List} of the current command.
     * @return arguments of the current command.
     */
    List<String> getArguments();


}
