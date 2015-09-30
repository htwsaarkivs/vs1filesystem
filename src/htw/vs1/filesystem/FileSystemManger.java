package htw.vs1.filesystem;

import htw.vs1.filesystem.FileSystem.physical.PhysicalFileSystemAdapter;
import htw.vs1.filesystem.FileSystem.virtual.LocalFolder;
import htw.vs1.filesystem.Network.Discovery.DiscoveredServersObserver;
import htw.vs1.filesystem.Network.Discovery.DiscoveryManager;
import htw.vs1.filesystem.Network.Discovery.FileSystemServer;
import htw.vs1.filesystem.Network.TCPParallelServer;

import java.io.IOException;
import java.util.Collection;

/**
 * Created by Felix on 26.09.2015.
 */
public class FileSystemManger {

    public static final boolean DEBUG_MODE = false;

    private static FileSystemManger INSTANCE = new FileSystemManger();

    public static FileSystemManger getInstance() {
        return INSTANCE;
    }

    private int serverPort = 0;

    private FileSystemManger() {

    }

    public void init(String pathToLocalFolder, int serverPort) {
        loadFileSystem(pathToLocalFolder);
        startFileSystemServer(serverPort);
        startDiscoveryListener(true);
    }

    public void close() {
        startDiscoveryListener(false);
        stopFileSystemServer();
    }

    public void getFileSystem() {

    }

    public void startFileSystemServer(int port) {
        // TODO: was ist wenn der server schon gestartet wurde?!
        serverPort = port;
        TCPParallelServer.getInstance(serverPort).start();
    }

    public void stopFileSystemServer() {
        TCPParallelServer.getInstance(serverPort).stopServer();
        PhysicalFileSystemAdapter.getInstance().stopWatchService();
    }

    public void loadFileSystem(String pathToLocalFolder) {
        try {
            LocalFolder.setRootDirectory(pathToLocalFolder);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("There was an error loading the file system.", e);
        }
    }

    /**
     * Starts the background task listening for
     * other file system server.
     *
     * @param start {@code true}, to start the listener service.
     */
    public void startDiscoveryListener(boolean start) {
        if (start) {
            DiscoveryManager.getInstance().startListener();
        } else {
            DiscoveryManager.getInstance().stopListener();
        }
    }

    /**
     * Lists all discovered {@link FileSystemServer}s.
     * @return
     */
    public Collection<FileSystemServer> listAvailableFileSystemServers() {
        return DiscoveryManager.getInstance().getDiscoveredServers();
    }

    public void attachDiscoveredServersObserver(DiscoveredServersObserver o, boolean attach) {
        if (attach) {
            DiscoveryManager.getInstance().attachObserver(o);
        } else {
            DiscoveryManager.getInstance().detachObserver(o);
        }
    }


}