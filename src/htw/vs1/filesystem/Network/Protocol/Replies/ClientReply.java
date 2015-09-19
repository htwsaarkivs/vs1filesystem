package htw.vs1.filesystem.Network.Protocol.Replies;

import java.util.List;

/**
 * Created by markus on 24.06.15.
 */
public interface ClientReply {

    List<String> getData();

    void setSuccess();

    void setFailure();

    boolean success();

}
