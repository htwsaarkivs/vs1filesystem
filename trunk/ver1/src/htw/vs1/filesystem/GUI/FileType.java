package htw.vs1.filesystem.GUI;

import htw.vs1.filesystem.FileSystem.virtual.Permissions;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;

/**
 * This class is part of the package htw.vs1.filesystem.FileSystem.GUI and project ver1
 * Created by Marc Otting on 18.09.2015.
 * This class provides the following function(s):
 */
public class FileType {

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

    private final SimpleObjectProperty<Image> icon;
    private final SimpleObjectProperty<FileObject> fileName;
    private final SimpleStringProperty fileType;

    public FileType (String fileName, boolean isFolder, Permissions permissions) {
        this.fileName= new SimpleObjectProperty<>(new FileObject(fileName, isFolder));
        this.fileType= new SimpleStringProperty(isFolder ? "Folder" : "File");
        this.icon = new SimpleObjectProperty<>(Resources.fsObjectIcon(isFolder, permissions));

    }

    public SimpleObjectProperty<FileObject> fileNameProperty() {
        return fileName;
    }

    public SimpleObjectProperty<Image> iconProperty() {
        return icon;
    }

    public String getFileType() {
        return fileType.get();
    }

    public SimpleStringProperty fileTypeProperty() {
        return fileType;
    }



    public String toString(){
        StringBuffer buffer = new StringBuffer();
        buffer.append(fileType.get());
        return buffer.toString();
    }

}
