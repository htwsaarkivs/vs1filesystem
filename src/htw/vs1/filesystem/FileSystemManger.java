package htw.vs1.filesystem;

import com.sun.istack.internal.NotNull;
import htw.vs1.filesystem.FileSystem.exceptions.FileSystemException;
import htw.vs1.filesystem.FileSystem.physical.PhysicalFileSystemAdapter;
import htw.vs1.filesystem.FileSystem.virtual.FileSystem;
import htw.vs1.filesystem.FileSystem.virtual.Folder;
import htw.vs1.filesystem.FileSystem.virtual.LocalFolder;
import htw.vs1.filesystem.FileSystem.virtual.MountPointFolder;
import htw.vs1.filesystem.Network.Discovery.DiscoveredServersObserver;
import htw.vs1.filesystem.Network.Discovery.DiscoveryManager;
import htw.vs1.filesystem.Network.Discovery.FileSystemServer;
import htw.vs1.filesystem.Network.Log.LogEntry;
import htw.vs1.filesystem.Network.Log.LogSubscriber;
import htw.vs1.filesystem.Network.Log.LogType;
import htw.vs1.filesystem.Network.Log.NetworkLog;
import htw.vs1.filesystem.Network.Protocol.ServerStatus;
import htw.vs1.filesystem.Network.ServerStatusObserver;
import htw.vs1.filesystem.Network.TCPParallelServer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;

/**
 * Created by Felix on 26.09.2015.
 */
public class FileSystemManger {

    /**
     * Name of our single root folder instance.
     */
    private static final String ROOT_FOLDER_NAME = "local";

    public static final boolean DEBUG = false;

    private static FileSystemManger INSTANCE = new FileSystemManger();

    public static FileSystemManger getInstance() {
        return INSTANCE;
    }

    private Path rootFolderPath;

    private LocalFolder rootFolder;

    private MountPointFolder mountPointFolder = null;

    private NetworkLog networkLog;

    private int serverPort = 0;

    private FileSystemManger() {
        networkLog = new NetworkLog();
    }

    private File helpFile = null;

    public FileSystem getFileSystem(boolean mountAllowed) {
        Folder fsRoot = getRootFolder();
        if (mountAllowed) {
            try {
                if (null == mountPointFolder) {
                    mountPointFolder = new MountPointFolder("");
                    mountPointFolder.addMountPoint(fsRoot);
                }
                fsRoot = mountPointFolder;
            } catch (FileSystemException e) {
                if (FileSystemManger.DEBUG) {
                    e.printStackTrace();
                }
            }
        }

        return new FileSystem(fsRoot, mountAllowed);
    }

    public LocalFolder getRootFolder() {
        if (null == rootFolder) {
            if (null == rootFolderPath) {
                System.out.println("Root folder is not connected to the physical file system.");
            }

            try {
                if (null == rootFolderPath) {
                    rootFolder = new LocalFolder(ROOT_FOLDER_NAME);
                } else {
                    rootFolder = new LocalFolder(ROOT_FOLDER_NAME, rootFolderPath);
                }
            } catch (FileSystemException e) {
                if (FileSystemManger.DEBUG) {
                    e.printStackTrace();
                }
            }
        }
        return rootFolder;
    }

    public void setRootDirectory(@NotNull String rootPath) throws IOException {
        if (null != rootFolderPath) {
            PhysicalFileSystemAdapter.getInstance().stopWatchService();
        }

        this.rootFolderPath = Paths.get(rootPath);

        PhysicalFileSystemAdapter adapter = PhysicalFileSystemAdapter.getInstance();
        System.out.println("Importing directory...");
        String path = adapter.loadFileSystemTree();
        System.out.println("Directory" + ((path.isEmpty()) ? " not" : ": ") + path + " imported.");

        rootFolder = getRootFolder();
    }

    /**
     * Initializes the file system and starts the discovery listener
     * without starting the server.
     *
     * @param pathToLocalFolder path to the connected local folder
     */
    public void init(String pathToLocalFolder) {
        loadFileSystem(pathToLocalFolder);
        startDiscoveryListener(true);
    }

    /**
     * Initializes the file system, starts the server on the given port
     * and starts the discovery listener.
     *
     * @param pathToLocalFolder path to the connected local folder
     * @param serverPort port
     */
    public void init(String pathToLocalFolder, int serverPort) {
        initServerOnlyMode(pathToLocalFolder, serverPort);
        startDiscoveryListener(true);
    }

    /**
     * Starts only the discovery listener.
     */
    public void initClientOnlyMode() {
        startDiscoveryListener(true);
    }

    /**
     * Starts the server and initializes the file system without the
     * discovery listener.
     *
     * @param pathToLocalFolder
     * @param serverPort
     */
    public void initServerOnlyMode(String pathToLocalFolder, int serverPort) {
        loadFileSystem(pathToLocalFolder);
        startFileSystemServer(serverPort);
    }

    public void close() {
        startDiscoveryListener(false);
        stopFileSystemServer();
        PhysicalFileSystemAdapter.getInstance().stopWatchService();
    }

    public void startFileSystemServer(int port) {
        // TODO: was ist wenn der server schon gestartet wurde?!
        serverPort = port;
        TCPParallelServer.getInstance(serverPort).start();
    }

    public void stopFileSystemServer() {
        if (serverPort == 0) {
            return;
        }
        TCPParallelServer.getInstance(serverPort).stopServer();
    }

    public boolean fileSystemServerRunning() {
        if (serverPort == 0) {
            return false;
        }
        return TCPParallelServer.getInstance(serverPort).isRunning();
    }

    public void loadFileSystem(String pathToLocalFolder) {
        try {
            setRootDirectory(pathToLocalFolder);
        } catch (IOException e) {
            if (DEBUG) {
                e.printStackTrace();
            }
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

    public void attachServerStatusObserver(ServerStatusObserver o) {
        TCPParallelServer.getInstance(serverPort).attachServerStatusObserver(o);
    }

    public void detachServerStatusObserver(ServerStatusObserver o) {
        TCPParallelServer.getInstance(serverPort).detachServerStatusObserver(o);
    }

    public ServerStatus getServerStatus() {
        return TCPParallelServer.getInstance(serverPort).getServerStatus();
    }

    public void addNetworkLogSubscriber(LogSubscriber subscriber) {
        networkLog.addSubscriber(subscriber);
    }

    public void putNetworkLog(String log, LogType type) {
        networkLog.log(new LogEntry(log, type));
    }


    public File getHelpFile() {
        return helpFile;
    }

    public void setHelpFile(File helpFile) {
        this.helpFile = helpFile;
    }
}
