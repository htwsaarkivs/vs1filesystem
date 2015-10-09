package htw.vs1.filesystem.Network.Discovery;

import com.sun.istack.internal.NotNull;
import htw.vs1.filesystem.Network.TCPParallelServer;

import java.util.*;

/**
 * Created by Felix on 22.09.2015.
 */
public class DiscoveryManager {

    protected static final int DISCOVERY_PORT = TCPParallelServer.DEFAULT_PORT;

    private static DiscoveryManager mInstance = new DiscoveryManager();

    public static DiscoveryManager getInstance() {
        return mInstance;
    }

    private HashSet<FileSystemServer> discoveredServerInstances = new HashSet<>();

    private int currentSetHash = 0;

    private DiscoveryListener listener;

    private List<DiscoveredServersObserver> observers = new LinkedList<>();

    private DiscoveryManager() {
    }

    public Collection<FileSystemServer> getDiscoveredServers() {
        return discoveredServerInstances;
    }

    public void startAnnouncement(int serverPort) {
        TimerThread.getInstance().startBroadcaster(serverPort);
    }

    public void stopAnnouncement() {
        TimerThread.getInstance().stopBroadcaster();
    }

    public void startListener() {
        if (listener != null) {
            throw new IllegalStateException("DiscoveryListener already running");
        }

        listener = new DiscoveryListener();
        listener.start();
        // Is the listener active the cleanUp service should be active, too.
        TimerThread.getInstance().startDiscoveredServerListCleanUp(true);
    }

    public void stopListener() {
        if (listener == null) {
            return;
        }

        // Is the listener inactive the cleanUp service should be inactive, too.
        TimerThread.getInstance().startDiscoveredServerListCleanUp(false);
        listener.stopDiscoveryThread();
        listener = null;
    }

    public synchronized void add(@NotNull String host, int port, @NotNull String hostName) {
        saveCurrentSetState();

        // TODO: ist nicht wirklich effizient. bessere methode ??

        FileSystemServer newObject = new FileSystemServer(host, port, hostName);
        if (discoveredServerInstances.contains(newObject)) {
            discoveredServerInstances.remove(newObject);
        }
        discoveredServerInstances.add(new FileSystemServer(host, port, hostName));

        notifyDataSetChanged();
    }

    public synchronized void deleteOutdatedEntries() {
        saveCurrentSetState();

        for (Iterator<FileSystemServer> it = discoveredServerInstances.iterator() ; it.hasNext() ;) {
            FileSystemServer server = it.next();
            if (server.isOutdated()) {
                it.remove();
            }
        }

        notifyDataSetChanged();
    }

    private void saveCurrentSetState() {
        currentSetHash = 0;
        for (FileSystemServer instance : discoveredServerInstances) {
            currentSetHash += instance.hashCode();
        }
    }

    private void notifyDataSetChanged() {
        if (currentSetHash == discoveredServerInstances.hashCode()) {
            // hat sich nichts verändert, so muss auch niemand benachrichtigt werden.
            return;
        }

        for (DiscoveredServersObserver observer : observers) {
            observer.discoveredServersUpdated();
        }
    }

    public void attachObserver(DiscoveredServersObserver observer) {
        observers.add(observer);
    }

    public void detachObserver(DiscoveredServersObserver observer) {
        observers.remove(observer);
    }
}
