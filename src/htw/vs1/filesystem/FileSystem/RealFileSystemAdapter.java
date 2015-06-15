package htw.vs1.filesystem.FileSystem;

import htw.vs1.filesystem.FileSystem.exceptions.CouldNotCreateExeption;
import htw.vs1.filesystem.FileSystem.exceptions.FSObjectException;

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
public class RealFileSystemAdapter {

    public RealFileSystemAdapter() {
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
        DirectoryStream<Path> rootDirectoryStream = Files.newDirectoryStream(localFolderPath);
        loadFileSystemDirectory(rootDirectoryStream, LocalFolder.getRootFolder());

        return localFolderPath.toAbsolutePath().toString();
    }

    private void loadFileSystemDirectory(DirectoryStream<Path> directoryStream, LocalFolder matchingFolder) {

        for (Path path : directoryStream) {
            String filename = path.toFile().getName();

            if (path.toFile().isDirectory()) {
                LocalFolder subfolder = new LocalFolder(filename);
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
                LocalFile subFile = new LocalFile(filename);
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
