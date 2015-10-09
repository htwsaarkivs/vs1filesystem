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
public class SearchItem {

    private final SimpleObjectProperty<Image> icon;
    private final SimpleStringProperty fileName;
    private final SimpleStringProperty path;


    public SearchItem(String fileName, boolean isFolder, Permissions permissions, String path){
        this.fileName = new SimpleStringProperty(fileName);
        this.path = new SimpleStringProperty(path);
        this.icon = new SimpleObjectProperty<>(Resources.fsObjectIcon(isFolder, permissions));
    }

    public Image getIcon() {
        return icon.get();
    }

    public SimpleObjectProperty<Image> iconProperty() {
        return icon;
    }

    public void setIcon(Image icon) {
        this.icon.set(icon);
    }

    public String getFileName() {
        return fileName.get();
    }

    public SimpleStringProperty fileNameProperty() {
        return fileName;
    }

    public String getPath() {
        return path.get();
    }

    public SimpleStringProperty pathProperty() {
        return path;
    }

    public void setPath(String path) {
        this.path.set(path);
    }
}
