package htw.vs1.filesystem.FileSystem;

import com.sun.istack.internal.NotNull;
import htw.vs1.filesystem.FileSystem.exceptions.FSObjectNotFoundException;

import java.util.List;
import java.util.Objects;

/**
 * Created by markus on 04.06.15.
 */
public class MarkusFileSystem implements FileSystemInterface {


    private Folder workingDirectory;


    @Override
    public void setWorkingDirectory(@NotNull Folder workingFolder) {
        this.workingDirectory = workingFolder;
    }

    @Override
    public Folder getWorkingDirectory() {
        return this.workingDirectory;
    }

    @Override
    public void changeDirectory(@NotNull String name) throws FSObjectNotFoundException {
        for(FSObject el: this.workingDirectory.getContent()) {
            if (Objects.equals(name, el.getName()) && el instanceof Folder) {
                this.workingDirectory = (Folder) el;
                return;

            }
        }
        throw new FSObjectNotFoundException();
    }


    @Override
    public String listDirectoryContent() {
        List<FSObject> liste = this.workingDirectory.getContent();

        String output = "";

        for (FSObject el: liste) {
            output+= el.getName()+" "+(el instanceof File?"[File]":"[Folder]")+"\n";
        }
        return output;
    }


    @Override
    public String printWorkingDirectory() {
        return getPath(this.getWorkingDirectory());
    }

    @Override
    public void rename(@NotNull String name, String newName) {

    }

    @Override
    public void delete(@NotNull String name) {

    }

    private String getPath(Folder folder) {

        if (folder.getParentFolder() == null) {
            return "/"+folder.getName();
        }

        return getPath(folder.getParentFolder()) + "/" + folder.getName();

    }
}
