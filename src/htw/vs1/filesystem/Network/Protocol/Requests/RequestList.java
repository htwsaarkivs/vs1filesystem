package htw.vs1.filesystem.Network.Protocol.Requests;

/**
 * Created by markus on 15.06.15.
 */
public interface RequestList {
    Request getNthElementFromEnd(int n) throws IndexOutOfBoundsException;
    Request getCurrentElement();
    Request getPreviousElement();

}
