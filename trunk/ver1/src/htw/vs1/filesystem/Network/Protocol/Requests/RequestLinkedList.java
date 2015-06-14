package htw.vs1.filesystem.Network.Protocol.Requests;

import java.util.*;

/**
 * Created by markus on 14.06.15.
 */
public class RequestLinkedList extends LinkedList<Request> implements RequestList{

    public Request getNthElementFromEnd(int n) throws IndexOutOfBoundsException {
        ListIterator<Request> it = this.listIterator();
        while (it.hasNext()) {
            it.next();
        }

        Request currentRequest = null;
        for (int i = 0; i <= n; i++) {
            if(it.hasPrevious()) {
               currentRequest = it.previous();
            } else {
                throw new IndexOutOfBoundsException();
            }
        }
        if (currentRequest == null) throw new IndexOutOfBoundsException();
        return currentRequest;

    }

    public static RequestList getUnmodifiableRequestList(RequestLinkedList requestList) {
        return requestList;
        //return null;
    }



}
