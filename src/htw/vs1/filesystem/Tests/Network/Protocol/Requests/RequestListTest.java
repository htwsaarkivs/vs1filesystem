package htw.vs1.filesystem.Tests.Network.Protocol.Requests;

import htw.vs1.filesystem.Network.Protocol.Requests.Request;
import htw.vs1.filesystem.Network.Protocol.Requests.RequestList;
import htw.vs1.filesystem.Network.Protocol.Requests.SimpleProtocolRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by markus on 14.06.15.
 */
public class RequestListTest {


    private RequestList list = new RequestList();

    @Before
    public void setUp() throws Exception {
        list.add(new SimpleProtocolRequest("A"));
        list.add(new SimpleProtocolRequest("B 123 5566"));
        list.add(new SimpleProtocolRequest("C 112 32 4222"));

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetNthElementFromEnd() throws Exception {
        try {
            list.getNthElementFromEnd(-1);
            fail("Expected IndexOutOfBoundsException for illegal n");
        } catch (IndexOutOfBoundsException e) {
            //Alles okay
        }

        try {
            list.getNthElementFromEnd(42);
            fail("Expected IndexOutOfBoundsException for invalid n");
        } catch (IndexOutOfBoundsException e) {
            //Alles okay
        }

        assertTrue(list.getNthElementFromEnd(1).getCommandString().equals("B"));


    }

    @Test
    public void testGetUnmodifiableRequestList() throws Exception {
        RequestList.getUnmodifiableRequestList(list);


    }
}