package htw.vs1.filesystem.Network.Protocol.Exceptions;

/**
 * Will be thrown if any connection error occurs.
 * (e.g. timeouts etc.)
 *
 * Created by Felix on 21.09.2015.
 */
public class ConnectionException extends Exception {

    /**
     * Constructor with the message which
     * will be displayed to the user.
     *
     * @param message message displayed to the user.
     */
    public ConnectionException(String message) {
        super(message);
    }

    /**
     * Constructor with a message and the
     * cause for the error.
     *
     * @param message message displayed to the user.
     * @param cause which caused the error.
     */
    public ConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

}
