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

    private Set<FileSystemServer> discoveredServerInstances = new HashSet<>();

    private int currentSetHash = 0;

    private DiscoveryBroadcaster broadcaster;

    private DiscoveryListener listener;

    private List<DiscoveredServersObserver> observers = new LinkedList<>();

    private DiscoveryManager() {
    }

    public Collection<FileSystemServer> getDiscoveredServers() {
        return discoveredServerInstances;
    }

    public void startAnnouncement(int serverPort) {
        if (broadcaster != null) {
            throw new IllegalStateException("DiscoveryBroadcaster already running");
        }

        broadcaster = new DiscoveryBroadcaster(serverPort);
        broadcaster.start();
    }

    public void stopAnnouncement(int serverPort) {
        if (broadcaster == null) {
            return;
        }
        broadcaster.stopDiscoveryThread();
        broadcaster = null;
    }

    public void startListener() {
        if (listener != null) {
            throw new IllegalStateException("DiscoveryListener already running");
        }

        listener = new DiscoveryListener();
        listener.start();
    }

    public void stopListener() {
        if (listener == null) {
            return;
        }
        listener.stopDiscoveryThread();
        listener = null;
    }

    public void add(@NotNull String host, int port, @NotNull String hostName) {
        saveCurrentSetState();
        discoveredServerInstances.add(new FileSystemServer(host, port, hostName));
        notifyDataSetChanged();
    }

    public void deleteOutdatedEntries() {
        for (FileSystemServer server : discoveredServerInstances) {
            if (server.isOutdated()) {
                saveCurrentSetState();
                discoveredServerInstances.remove(server);
                notifyDataSetChanged();
            }
        }
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
