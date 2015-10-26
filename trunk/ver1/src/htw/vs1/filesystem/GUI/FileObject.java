package htw.vs1.filesystem.GUI;

/**
 * Model to represent a file object in our directory table.
 *
 * Created by ottinm on 09.10.2015.
 */
public class FileObject {
    private String name;
    private boolean isFolder;

    public FileObject(String name, boolean isFolder) {
        this.name = name;
        this.isFolder = isFolder;
    }

    public String getName() {
        return name;
    }

    public boolean isFolder() {
        return isFolder;
    }

    @Override
    public String toString() {
        return name;
    }
}
