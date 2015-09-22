package htw.vs1.filesystem.Network.Discovery;

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

    private DiscoveryManager() {
    }

    public void add(String host, int port) {
        discoveredServerInstances.add(new FileSystemServer(host, port));
    }
}
