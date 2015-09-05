package htw.vs1.filesystem.Network.Protocol.Requests;

/**
 * Interface to declare a list of requests.
 *
 * Created by markus on 15.06.15.
 */
public interface RequestList {

    /**
     * Gets you the nth Element from the End of the list.
     * Exception thrown for out-of-bound n or negative n.
     * @param n index from the end.
     * @return nth element from the end of the list.
     * @throws IndexOutOfBoundsException
     */
    Request getNthElementFromEnd(int n) throws IndexOutOfBoundsException;

    /**
     * Get current element
     * @return current element of the list.
     */
    Request getCurrentElement();

    /**
     * Get previous element
     * @return previous element of the list.
     */
    Request getPreviousElement() throws IndexOutOfBoundsException;

    String toString();

}
