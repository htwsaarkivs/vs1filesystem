package htw.vs1.filesystem.FileSystem;

import com.sun.istack.internal.NotNull;

/**
 * Created by felix on 03.06.15.
 */
public interface FileSystemInterface {

    void setWorkingFolder(Folder workingFolder);

    void changeDirectory(@NotNull String name);

    String listDirectoryContent();

    String printWorkingDirectory();

}
