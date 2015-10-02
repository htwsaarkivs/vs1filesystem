package htw.vs1.filesystem.Network.Log;

/**
 * Created by felix on 02.10.15.
 */
public interface LogSubscriber {

    void publish(LogEntry log);

}
