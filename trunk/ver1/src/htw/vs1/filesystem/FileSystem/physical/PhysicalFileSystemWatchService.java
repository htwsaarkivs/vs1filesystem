package htw.vs1.filesystem.FileSystem.physical;

import htw.vs1.filesystem.FileSystem.virtual.FileSystem;
import htw.vs1.filesystem.FileSystem.virtual.FileSystemInterface;
import htw.vs1.filesystem.FileSystem.virtual.LocalFolder;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Created by felix on 17.06.15.
 */
public class PhysicalFileSystemWatchService extends AbstractWatchService {

    private FileSystemInterface fileSystem;

    /**
     * Creates a WatchService and registers the given directory
     */
    PhysicalFileSystemWatchService() throws IOException {
        super(LocalFolder.getRootFolder().getPath(), true);

        fileSystem = new FileSystem();
    }

    @Override
    protected void onEntryDelete(Path child, Path dir) {
        System.out.format("Deleted file/folder %s in directory %s\n", child.toFile().getName(), dir);
        System.out.format("Relative path from local-root: " + LocalFolder.getRootFolder().getPath().relativize(child));
    }

    @Override
    protected void onEntryCreate(Path child, Path dir) {
        System.out.format(
                "Created %s %s in directory %s\n",
                (child.toFile().isDirectory()) ? "folder" : "file",
                child.toFile().getName(),
                dir);
    }
}
