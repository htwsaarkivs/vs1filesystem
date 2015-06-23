package htw.vs1.filesystem.FileSystem.physical;

import htw.vs1.filesystem.FileSystem.exceptions.CouldNotCreateExeption;
import htw.vs1.filesystem.FileSystem.exceptions.CouldNotRenameExeption;
import htw.vs1.filesystem.FileSystem.exceptions.FSObjectException;
import htw.vs1.filesystem.FileSystem.exceptions.InvalidFilenameException;
import htw.vs1.filesystem.FileSystem.virtual.FSObject;
import htw.vs1.filesystem.FileSystem.virtual.LocalFile;
import htw.vs1.filesystem.FileSystem.virtual.LocalFolder;

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

    boolean physicalFileSystemLinked = false;

    AbstractWatchService watchThread;

    public void startWatchService() {
        if (null != watchThread) {
            return;
        }

        Path localFolderPath = LocalFolder.getRootFolder().getPath();
        if (null == localFolderPath) {
            physicalFileSystemLinked = true;
            return;
        }
        physicalFileSystemLinked = true;
        try {
            watchThread = new PhysicalFileSystemWatchService();
            watchThread.start();
        } catch (IOException e) {
            // TODO: What shall I do with this f*cking exception ?!
            e.printStackTrace();
        }
    }

    public void stopWatchService() {
        if (null != watchThread) {
            watchThread.requestStop();
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
        Path localFolderPath = LocalFolder.getRootFolder().getPath();
        if (null == localFolderPath) {
            physicalFileSystemLinked = true;
            return "";
        }
        physicalFileSystemLinked = true;
        watchThread.register(localFolderPath);
        DirectoryStream<Path> rootDirectoryStream = Files.newDirectoryStream(localFolderPath);
        loadFileSystemDirectories(rootDirectoryStream, LocalFolder.getRootFolder());

        return localFolderPath.toAbsolutePath().toString();
    }

    protected void loadFileSystemDirectories(DirectoryStream<Path> directoryStream, LocalFolder parentFolder) throws IOException {

        for (Path dir : directoryStream) {
            loadFileSystemDirectory(dir, parentFolder);
        }
    }

    protected void loadFileSystemDirectory(Path dir, LocalFolder parentFolder) throws IOException {
        String filename = dir.toFile().getName();

        if (dir.toFile().isDirectory()) {
            watchThread.register(dir);
            LocalFolder subfolder = null;
            try {
                subfolder = new LocalFolder(filename);
            } catch (CouldNotRenameExeption | FileAlreadyExistsException | InvalidFilenameException couldNotRenameExeption) {
                // Todo: What shall I do with this f*cking exception
                couldNotRenameExeption.printStackTrace();
            }
            addFSObject(parentFolder, subfolder, dir);

            try {
                DirectoryStream<Path> subDirectoryStream = Files.newDirectoryStream(dir);
                loadFileSystemDirectories(subDirectoryStream, subfolder);
            } catch (IOException e) {
                // We should not break at this point, because maybe there are other files...
                // But we should do anything..
                e.printStackTrace();
            }
        } else {
            LocalFile subFile = null;
            try {
                subFile = new LocalFile(filename);
            } catch (CouldNotRenameExeption | FileAlreadyExistsException | InvalidFilenameException couldNotRenameExeption) {
                // Todo: What shall I do with this f*cking exception
                couldNotRenameExeption.printStackTrace();
            }
            addFSObject(parentFolder, subFile, dir);
        }
    }


    private void addFSObject(LocalFolder folder, FSObject object, Path path) {
        try {
            folder.add(object, path);
        } catch (FileAlreadyExistsException e) {
            // This should never happen
            e.printStackTrace();
            throw new IllegalStateException("File already exists in this folder. Absolute path: "
                    + path.toFile().getAbsolutePath());
        } catch (CouldNotCreateExeption e) {
            e.printStackTrace();
            // This should never happen at this point we do not create any file or folder
            throw new IllegalStateException(FSObjectException.COULDNOTCREATE);
        }
    }

}
