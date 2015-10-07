package htw.vs1.filesystem.FileSystem.physical;

import htw.vs1.filesystem.FileSystem.exceptions.*;
import htw.vs1.filesystem.FileSystem.virtual.LocalFile;
import htw.vs1.filesystem.FileSystem.virtual.LocalFolder;
import htw.vs1.filesystem.FileSystemManger;

import java.io.IOException;
import java.nio.file.*;

/**
 * <p>Adapter to connect a folder of the file system of
 * the local machine with our virtual file system.</p>
 * <p>The root {@link LocalFolder} is the base folder of
 * the folder ot the file system of the local machine this
 * program is running on.</p>
 *
 * Created by Felix on 09.06.2015.
 */
public class PhysicalFileSystemAdapter {

    /**
     * Single instance of the PhysicalFileSystemAdapter.
     */
    private static PhysicalFileSystemAdapter INSTANCE = null;

    /**
     * Gets the single instance of the
     * {@link PhysicalFileSystemAdapter}.
     *
     * @return the single instance of this class.
     */
    public static PhysicalFileSystemAdapter getInstance() {
        if (null == INSTANCE) {
            INSTANCE = new PhysicalFileSystemAdapter();
        }

        return INSTANCE;
    }

    /**
     * Private constructor to ensure that
     * this class will be instantiated
     * once.
     */
    private PhysicalFileSystemAdapter() {
        startWatchService();
    }

    /**
     * Instance of the thread of the watch service.
     * Used to stop it on demand.
     */
    AbstractWatchService watchThread;

    /**
     * Starts the watch service which observes
     * the physical file system for changes.
     */
    public void startWatchService() {
        if (null != watchThread) {
            return;
        }

        Path localFolderPath = FileSystemManger.getInstance().getRootFolder().getPath();
        if (null == localFolderPath) {
            return;
        }
        try {
            watchThread = new PhysicalFileSystemWatchService();
            watchThread.start();
        } catch (IOException e) {
            if (FileSystemManger.DEBUG) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Stops the watch service.
     */
    public void stopWatchService() {
        if (null != watchThread) {
            watchThread.requestStop();
            INSTANCE = null;
        }
    }

    /**
     * Iterates recursively over the directory with
     * the path given in the constructor and puts
     * each folder and file into the root folder of
     * our virtual file system.
     *
     * @return absolute path of the loaded file system tree.
     */
    public String loadFileSystemTree() throws IOException {
        Path localFolderPath = FileSystemManger.getInstance().getRootFolder().getPath(); //LocalFolder.getRootFolder().getPath();
        if (null == localFolderPath) {
            return "";
        }
        watchThread.register(localFolderPath);
        loadFileSystemDirectories(localFolderPath,FileSystemManger.getInstance().getRootFolder());

        return localFolderPath.toAbsolutePath().toString();
    }

    /**
     * Iterates over a given directory and adds all its
     * objects to our virtual file system.
     *
     * @param directory {@link Path} to the directory.
     * @param parentFolder {@link LocalFolder} to add the items to.
     * @throws IOException
     */
    protected void loadFileSystemDirectories(Path directory, LocalFolder parentFolder) throws IOException {
        DirectoryStream<Path> directoryStream = Files.newDirectoryStream(directory);

        for (Path dir : directoryStream) {
            try {
                loadFileSystemObject(dir, parentFolder);
            }  catch (Exception e) {
                if (FileSystemManger.DEBUG) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Imports a object of the physical file system into
     * our virtual file system.
     *
     * @param dir {@link Path} to the object on the physical file system
     * @param parentFolder {@link LocalFolder} to add the object to.
     * @throws IOException
     * @throws InvalidFilenameException
     * @throws CouldNotRenameException
     */
    private void loadFileSystemObject(Path dir, LocalFolder parentFolder) throws IOException, htw.vs1.filesystem.FileSystem.exceptions.FileSystemException {
        String filename = dir.toFile().getName();

        if (dir.toFile().isDirectory()) {
            registerDirectoryToWatchService(dir);
            LocalFolder subfolder;
            subfolder = new LocalFolder(filename);
            parentFolder.add(subfolder, dir);

            loadFileSystemDirectories(dir, subfolder);
        } else {
            LocalFile subFile;
            subFile = new LocalFile(filename);
            parentFolder.add(subFile, dir);
        }
    }

    /**
     * Adds a directory identified by its {@link Path} to the watch service.
     *
     * @param dir {@link Path} to the directory.
     * @throws IOException
     */
    protected void registerDirectoryToWatchService(Path dir) throws IOException {
        watchThread.register(dir);
    }

}
