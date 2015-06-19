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

    public PhysicalFileSystemAdapter() {
    }

    boolean physicalFileSystemLinked = false;

    AbstractWatchService watchThread;

    public void startWatchService() {
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
        DirectoryStream<Path> rootDirectoryStream = Files.newDirectoryStream(localFolderPath);
        loadFileSystemDirectory(rootDirectoryStream, LocalFolder.getRootFolder());

        return localFolderPath.toAbsolutePath().toString();
    }

    private void loadFileSystemDirectory(DirectoryStream<Path> directoryStream, LocalFolder matchingFolder) {

        for (Path path : directoryStream) {
            String filename = path.toFile().getName();

            if (path.toFile().isDirectory()) {
                LocalFolder subfolder = null;
                try {
                    subfolder = new LocalFolder(filename);
                } catch (CouldNotRenameExeption | FileAlreadyExistsException | InvalidFilenameException couldNotRenameExeption) {
                    // Todo: What shall I do with this f*cking exception
                    couldNotRenameExeption.printStackTrace();
                }
                addFSObject(matchingFolder, subfolder, path);

                try {
                    DirectoryStream<Path> subDirectoryStream = Files.newDirectoryStream(path);
                    loadFileSystemDirectory(subDirectoryStream, subfolder);
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
                addFSObject(matchingFolder, subFile, path);
            }
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
