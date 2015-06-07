package htw.vs1.filesystem.FileSystem;

import com.sun.istack.internal.NotNull;
import htw.vs1.filesystem.FileSystem.exceptions.FSObjectNotFoundException;

/**
 * This class is part of the package htw.vs1.filesystem.FileSystem and project ver1
 * Created by Marc Otting on 03.06.2015.
 * This class provides the following function(s):
 */
public class MarcsTeamFileSystem implements FileSystemInterface {
    private Folder workingDirectory = null;
    private FSObject workingFolder = null;
    private static final String UP = "..";
    @Override
    public void setWorkingDirectory(@NotNull Folder workingFolder) {
        this.workingDirectory = workingFolder;

    }

    @Override
    public Folder getWorkingDirectory() {
        return workingDirectory;
    }

    @Override
    public void changeDirectory(@NotNull String name) {

        try {
            if (name == UP){
                this.workingDirectory = workingDirectory.getParentFolder();
            }
                this.workingDirectory = (Folder) workingDirectory.getObject(name);
        } catch (FSObjectNotFoundException e) {
            e.printStackTrace();
        }


    }

//    @Override
//    public String listDirectoryContent() {
//        StringBuffer buffer = new StringBuffer();
//        List<FSObject> list = workingDirectory.getContent();
//        ListIterator<FSObject> listIterator = list.listIterator();
//            while (listIterator.hasNext()){
//                buffer.append(list.toString());
//                buffer.append("\n");
//                listIterator.next();
//            }
//        return buffer.toString();
//    }

    @Override
    public String listDirectoryContent(){
        StringBuffer buffer = new StringBuffer();
        for (FSObject element : workingDirectory.getContent()){
            buffer.append(element.toString());
            buffer.append("\n");
        }
        return buffer.toString();
    }

    @Override
    public String printWorkingDirectory() {
        return workingDirectory.toString();
    }

    @Override
    public void rename(@NotNull String name, String newName) {

        try {
            FSObject toRename = workingDirectory.getObject(name);
            toRename.setName(newName);
        } catch (FSObjectNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void delete(@NotNull String name) {

        try {
            FSObject toDelete = workingDirectory.getObject(name);
            workingDirectory.getContent().remove(toDelete);
        } catch (FSObjectNotFoundException e) {
            e.printStackTrace();
        } finally {
        }

    }
}
