package htw.vs1.filesystem.Network.Log;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by felix on 02.10.15.
 */
public class NetworkLog {

    private List<LogSubscriber> subscribers = new LinkedList<>();

    public void log(LogEntry log) {
        for (LogSubscriber subscriber : subscribers) {
            subscriber.publish(log);
        }
    }

    public void addSubscriber(LogSubscriber subscriber) {
        subscribers.add(subscriber);
    }

}
