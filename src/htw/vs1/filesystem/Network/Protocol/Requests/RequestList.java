package htw.vs1.filesystem.Network.Protocol.Requests;

/**
 * Created by markus on 15.06.15.
 */
public interface RequestList {

    /**
     * Gets you the nth Element from the End of the list.
     * Exception thrown for out-of-bound n or negative n.
     * @param n
     * @return
     * @throws IndexOutOfBoundsException
     */
    Request getNthElementFromEnd(int n) throws IndexOutOfBoundsException;

    /**
     * Get current element
     * @return
     */
    Request getCurrentElement();

    /**
     * Get previous element
     * @return
     */
    Request getPreviousElement() throws IndexOutOfBoundsException;

    String toString();

}
