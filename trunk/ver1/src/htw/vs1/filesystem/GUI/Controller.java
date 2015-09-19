package htw.vs1.filesystem.GUI;

import com.sun.javafx.scene.control.skin.TableViewSkinBase;
import htw.vs1.filesystem.FileSystem.exceptions.FSObjectException;
import htw.vs1.filesystem.FileSystem.exceptions.ObjectNotFoundException;
import htw.vs1.filesystem.FileSystem.virtual.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    private static final String CONDITION = "Folder";

    @FXML
    private TableView<FileType> tableView;
    @FXML
    private TableView tableViewSearch;
    @FXML
    private ListView listViewTabLog;
    @FXML
    private TextField textFieldDirectory;
    @FXML
    private TextField textFieldSearch;
    @FXML
    private TableColumn<FileType, FileType.FileObject> tableColumnName;
    @FXML
    private TableColumn<FileType, String> tableColumnType;
    @FXML
    private TableColumn<SearchItem,String> tableColumnSearchName;
    @FXML
    private TableColumn<SearchItem,String> tableColumnSearchDirectory;

    private ObservableList<FileType> currentDirectory = FXCollections.observableArrayList();
    private ObservableList<SearchItem> searchResults = FXCollections.observableArrayList();
    private ObservableList<String> logEntry;

    private FileSystemInterface fileSystem;

    public void changeDirectory (String directory){
        try {
            fileSystem.changeDirectory(directory);
        } catch (ObjectNotFoundException e) {
            e.printStackTrace();
        } catch (FSObjectException e) {
            e.printStackTrace();
        }

        listDirectoryContent();
    }

    public void listDirectoryContent (){
        currentDirectory.clear();
        currentDirectory.add(new FileType("..", true));
        List<FSObject> list = null;
        try {
            list = fileSystem.getWorkingDirectory().getContent();
            for (FSObject fsObject : list) {
                currentDirectory.add(new FileType(fsObject.getName(), fsObject instanceof Folder));
            }
        } catch (FSObjectException e) {
            e.printStackTrace();
        }




    }

    public void rename (){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("rename");
        alert.showAndWait();
    }

    public void delete (){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("delete");
        alert.showAndWait();
    }

    public void createDir(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("createDir");
        alert.showAndWait();
    }

    public void createFile(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("createFile");
        alert.showAndWait();
    }

    public void close(ActionEvent actionEvent) { System.exit(0);
    }

    public void initiateFilesystem () throws IOException {
        LocalFolder.setRootDirectory("C:\\test");
        fileSystem = new FileSystem(true);
        listDirectoryContent();

    }
    public void search(){
        String searchStr = textFieldSearch.getText();
        if (searchStr == null || searchStr.isEmpty()) return;
        // call searchfunction in filesystem

        searchResults.add(new SearchItem("FolderS1", "/root/test"));
        searchResults.add(new SearchItem("FileS1","/root/test"));
        tableViewSearch.setItems(searchResults);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Search for " + searchStr);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        /**
         * Initiate the textfields
         */
        textFieldDirectory.setText("Current Path ....");

        /**
         * Fill in some dummydata in the list
         */
        currentDirectory.add(new FileType("Folder1", true));
        currentDirectory.add(new FileType("File1", false));
        try {
            initiateFilesystem();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /**
         * Don't know how, but it works
         */
        tableColumnName.setCellValueFactory(cellData -> cellData.getValue().fileNameProperty());
        /*tableColumnName.setCellFactory(param -> new TableCell<FileType, FileType.FileObject>() {
            ImageView imageView = new ImageView();
            @Override
            protected void updateItem(FileType.FileObject item, boolean empty) {
                setText(null);
                if (item != null) {
                    HBox box = new HBox();
                    box.setSpacing(12);
                    VBox vbox = new VBox();
                    Label label = new Label(item.getName());
                    label.setAlignment(Pos.CENTER_LEFT);
                    vbox.getChildren().add(label);

                    imageView.setFitHeight(20);
                    imageView.setFitWidth(20);
                    String imgName = "images/";
                    imgName += item.isFolder() ? "folder.png" : "document-icon.png";
                    imageView.setImage(
                            new Image(Controller.class.getResource(imgName).toString())
                    );
                    box.getChildren().addAll(imageView, vbox);
                    setGraphic(box);
                }
            }
        });*/
        tableColumnType.setCellValueFactory(cellData -> cellData.getValue().fileTypeProperty());
        tableView.setItems(currentDirectory);

        /**
         * Add Mouselistener, check for doubleclick and that the clicked item is a folder
         */
        tableView.getSelectionModel().setCellSelectionEnabled(true);
        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                /*TablePosition position = tableView.getSelectionModel().getSelectedCells().get(0);

                TableColumn<FileType, ?> col = tableView.getColumns().get(position.getRow());
                Object object = col.getCellData(0);
                if (position.getColumn() == 0 && object instanceof FileType.FileObject) {
                    if (((FileType.FileObject) object).isFolder()) {
                        changeDirectory(object.toString());
                    }
                }*/


                TablePosition position = tableView.getSelectionModel().getSelectedCells().get(0);
                int row = position.getRow();
                int col = position.getColumn();
                TableColumn column = position.getTableColumn();
                String cellContent = column.getCellData(row).toString();
                String fileType = currentDirectory.get(row).toString();

                if (col == 0 && fileType.equals(CONDITION)){
                    changeDirectory(cellContent);
                }

            }
        });
        /**
         * Initiate the colums of the tableViewSearch
         */
        tableColumnSearchName.setCellValueFactory(cellData -> cellData.getValue().fileNameProperty());
        tableColumnSearchDirectory.setCellValueFactory(cellData -> cellData.getValue().pathProperty());
    }
}
