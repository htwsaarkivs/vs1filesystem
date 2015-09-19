package htw.vs1.filesystem.GUI;

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

    private boolean isFolder = false;

    public FileType (String fileName, boolean isFolder){
        this.fileName= new SimpleObjectProperty<>(new FileObject(fileName, isFolder));
        this.fileType= new SimpleStringProperty(isFolder ? "Folder" : "File");
        /*  String imgName = "images/";
                    imgName += item.isFolder() ? "folder.png" : "document-icon.png";
                    imageView.setImage(
                            new Image(Controller.class.getResource(imgName).toString())
                    );*/
        String imgName = "images/";
        imgName += isFolder ? "folder.png" : "document-icon.png";
        this.icon = new SimpleObjectProperty<>(new Image(FileType.class.getResource(imgName).toString()));

    }

    public boolean isFolder() {
        return isFolder;
    }

    public FileObject getFileName() {
        return fileName.get();
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
