package htw.vs1.filesystem.Network.Protocol.Replies;


import java.util.LinkedList;
import java.util.List;

/**
 * Created by markus on 25.06.15.
 */
public class SimpleClientProtocolReply implements ClientReply {

    private List<String> data = new LinkedList<>();

    private boolean success = true;

    public SimpleClientProtocolReply() {
    }

    public void feedLine(String line) {
        data.add(line);
    }

    @Override
    public List<String> getData() {
        return data;
    }

    @Override
    public boolean success() {
        return this.success;
    }

    public void setSuccess() {
        this.success = true;
    }

    @Override
    public void setFailure() {
        this.success = false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (String s : data) {
            if (first) {
                first = false;
            } else {
                sb.append("\n");
            }
            sb.append(s);
        }

        return sb.toString();
    }
}
