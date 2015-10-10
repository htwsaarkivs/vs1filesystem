package htw.vs1.filesystem.Network.Log;

import java.util.Date;

/**
 * Created by felix on 02.10.15.
 */
public class LogEntry {

    private String log;

    private LogType type;

    private Date date;

    public LogEntry(String log, LogType type) {
        this.log = log;
        this.type = type;
        date = new Date();
    }

    public String getLog() {
        return log;
    }

    public LogType getType() {
        return type;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return getLog();
    }
}
