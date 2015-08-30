package htw.vs1.filesystem.Network.Protocol.Replies;


/**
 * Created by markus on 25.06.15.
 */
public class SimpleClientProtocolReply implements ClientReply {

    private String data = null;

    public SimpleClientProtocolReply(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

}
