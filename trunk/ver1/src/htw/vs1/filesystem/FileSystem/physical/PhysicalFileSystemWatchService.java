package htw.vs1.filesystem.FileSystem.physical;

import htw.vs1.filesystem.FileSystem.exceptions.CouldNotDeleteExeption;
import htw.vs1.filesystem.FileSystem.exceptions.ObjectNotFoundException;
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
        try {
            fileSystem.changeDirectory(LocalFolder.getRootFolder().getPath().relativize(dir).toString());
            try {
                fileSystem.getWorkingDirectory().delete(child.toFile().getName());
            } catch (CouldNotDeleteExeption couldNotDeleteExeption) {
                couldNotDeleteExeption.printStackTrace();
            }
        } catch (ObjectNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onEntryCreate(Path child, Path parent) {
        System.out.format(
                "Created %s %s in directory %s\n",
                (child.toFile().isDirectory()) ? "folder" : "file",
                child.toFile().getName(),
                parent);

        System.out.println("Relative path from local-root: " + LocalFolder.getRootFolder().getPath().relativize(parent));

        /*if (fileSystem.getWorkingDirectory().exists(child.toFile().getName())){
            // the file was created by our program
            return;
        }


        try {
            fileSystem.changeDirectory(LocalFolder.getRootFolder().getPath().relativize(parent).toString());
        } catch (ObjectNotFoundException e) {
            e.printStackTrace();
        }
            if(child.toFile().isFile()){
                try {
                    LocalFile toCreate = new LocalFile(child.toFile().getName(),child);

                    try {
                        fileSystem.getWorkingDirectory().add(toCreate);
                    } catch (CouldNotCreateExeption couldNotCreateExeption) {
                        couldNotCreateExeption.printStackTrace();
                    }
                } catch (CouldNotRenameExeption couldNotRenameExeption) {
                    couldNotRenameExeption.printStackTrace();
                } catch (FileAlreadyExistsException e) {
                    e.printStackTrace();
                } catch (InvalidFilenameException e) {
                    e.printStackTrace();
                }
            }else{
                try {
                    LocalFolder toCreate = new LocalFolder(child.toFile().getName(),child);
                    if (fileSystem.getWorkingDirectory().exists(toCreate.toString())){
                        throw new FileAlreadyExistsException(FSObjectException.COULDNOTCREATE);
                    }
                    try {
                        fileSystem.getWorkingDirectory().add(toCreate);
                    } catch (CouldNotCreateExeption couldNotCreateExeption) {
                        couldNotCreateExeption.printStackTrace();
                    }
                } catch (CouldNotRenameExeption couldNotRenameExeption) {
                    couldNotRenameExeption.printStackTrace();
                } catch (FileAlreadyExistsException e) {
                    e.printStackTrace();
                } catch (InvalidFilenameException e) {
                    e.printStackTrace();
                }
            }*/

    }

}
