package htw.vs1.scrapyard.GUI;

import javafx.beans.property.SimpleStringProperty;

/**
 * This class is part of the package htw.vs1.scrapyard.GUI and project ver1
 * Created by Marc Otting on 18.09.2015.
 * This class provides the following function(s):
 */
public class FileType {

    private final SimpleStringProperty fileName;
    private final SimpleStringProperty fileType;

    public String getFileName() {
        return fileName.get();
    }

    public SimpleStringProperty fileNameProperty() {
        return fileName;
    }

    public String getFileType() {
        return fileType.get();
    }

    public SimpleStringProperty fileTypeProperty() {
        return fileType;
    }



    public FileType (String fileName, String fileType){
        this.fileName= new SimpleStringProperty(fileName);
        this.fileType= new SimpleStringProperty(fileType);

    }

    public String toString(){
        StringBuffer buffer = new StringBuffer();
        buffer.append(fileType.get());
        return buffer.toString();
    }

}
