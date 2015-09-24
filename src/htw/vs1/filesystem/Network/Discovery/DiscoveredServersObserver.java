package htw.vs1.filesystem.Network.Discovery;

/**
 * Interface for a FileSystemObserver.
 * This observer will be notified if the
 * set of discovered file system servers
 * has changed.
 *
 * Created by felix on 24.09.15.
 */
public interface DiscoveredServersObserver {

    /**
     * This method will be called if the set
     * of discovered file system servers has
     * changed.
     */
    void discoveredServersUpdated();

}
