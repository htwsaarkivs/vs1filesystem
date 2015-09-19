package htw.vs1.filesystem.GUI;

import javafx.beans.property.SimpleStringProperty;

/**
 * This class is part of the package htw.vs1.filesystem.FileSystem.GUI and project ver1
 * Created by Marc Otting on 18.09.2015.
 * This class provides the following function(s):
 */
public class SearchItem {

    private final SimpleStringProperty fileName;
    private final SimpleStringProperty path;

    public SearchItem(String filename,String path){
        this.fileName = new SimpleStringProperty(filename);
        this.path = new SimpleStringProperty(path);
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
}
