package htw.vs1.filesystem.Network.Discovery;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Felix on 22.09.2015.
 */
public class DiscoveryManager {

    protected static final int DISCOVERY_PORT = 4322;

    private static DiscoveryManager mInstance = new DiscoveryManager();

    public static DiscoveryManager getInstance() {
        return mInstance;
    }

    private Set<FileSystemServer> discoveredServerInstances = new HashSet<>();

    private DiscoveryBroadcaster broadcaster;

    private DiscoveryListener listener;

    private DiscoveryManager() {
    }

    public void add(String host, int port) {
        discoveredServerInstances.add(new FileSystemServer(host, port));
    }

    public Collection<FileSystemServer> getDiscoveredServers() {
        return discoveredServerInstances;
    }

    public void startAnnouncement(int serverPort) {
        if (broadcaster != null) {
            throw new IllegalStateException("DiscoveryBroadcaster already running");
        }

        broadcaster = new DiscoveryBroadcaster(serverPort);
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
    }

    public void stopListener() {
        if (listener == null) {
            return;
        }
        listener.stopDiscoveryThread();
        listener = null;
    }
}
