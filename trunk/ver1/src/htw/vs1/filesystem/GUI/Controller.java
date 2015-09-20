package htw.vs1.filesystem.GUI;

import htw.vs1.filesystem.FileSystem.exceptions.FSObjectException;
import htw.vs1.filesystem.FileSystem.exceptions.ObjectNotFoundException;
import htw.vs1.filesystem.FileSystem.virtual.*;
import htw.vs1.filesystem.Network.TCPParallelServer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;


public class Controller implements Initializable {

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
    private TableColumn<FileType, Image> tableColumnIcon;
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
            showErrorMessage(e);
        } catch (FSObjectException e) {
            showErrorMessage(e);
        }

        listDirectoryContent();
    }

    public void listDirectoryContent (){
        currentDirectory.clear();
        currentDirectory.add(new FileType("..", true));
        List<FSObject> list;
        try {
            list = fileSystem.getWorkingDirectory().getContent();
            for (FSObject fsObject : list) {
                currentDirectory.add(new FileType(fsObject.getName(), fsObject instanceof Folder));
            }
        } catch (FSObjectException e) {
            showErrorMessage(e);
        }


        this.textFieldDirectory.setText(fileSystem.printWorkingDirectory());

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
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Text Input Dialog");
        dialog.setHeaderText("Look, a Text Input Dialog");
        dialog.setContentText("Please enter a filename:");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(name -> {
            try {
                fileSystem.createFSObject(name, false);
            } catch (FSObjectException e) {
                showErrorMessage(e);
            }
            listDirectoryContent();
        });
    }

    public void mount(ActionEvent actionEvent) {
        // TODO: schöner custom dialog zur Eingabe von Ordername, remote Host und Port.

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Mount remote file system");
        //dialog.setHeaderText("Look, a Text Input Dialog");
        dialog.setContentText("foldername:host:port");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(input -> {
            String[] arr = input.split(":");
            try {
                fileSystem.mount(
                        arr[0], arr[1], Integer.parseInt(arr[2]),
                        TCPParallelServer.DEFAULT_USER, TCPParallelServer.DEFAULT_PASS);
            } catch (FSObjectException e) {
                e.printStackTrace();
            }
            listDirectoryContent();
        });
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
        searchResults.add(new SearchItem("FileS1", "/root/test"));
        tableViewSearch.setItems(searchResults);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Search for " + searchStr);
        alert.showAndWait();
    }

    public void showErrorMessage(Throwable e){
        Alert alert  = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(e.getMessage());
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
        tableColumnIcon.setCellValueFactory(cellData -> cellData.getValue().iconProperty());
        tableColumnIcon.setCellFactory(param -> new TableCell<FileType, Image>() {
            ImageView imageView = new ImageView();
            @Override
            protected void updateItem(Image item, boolean empty) {
                setGraphic(null);
                if (item != null) {
                    HBox box = new HBox();
                    box.setSpacing(12);

                    imageView.setFitWidth(20);
                    imageView.setFitHeight(20);
                    imageView.setImage(item);
                    box.getChildren().add(imageView);
                    setGraphic(box);
                }
            }
        });
        tableColumnName.setCellValueFactory(cellData -> cellData.getValue().fileNameProperty());
        tableColumnType.setCellValueFactory(cellData -> cellData.getValue().fileTypeProperty());
        tableView.setItems(currentDirectory);

        /**
         * Add Mouselistener, check for doubleclick and that the clicked item is a folder
         */
        tableView.getSelectionModel().setCellSelectionEnabled(true);
        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {

                TablePosition position = tableView.getSelectionModel().getSelectedCells().get(0);
                int row = position.getRow();
                TableColumn column = position.getTableColumn();

                Object cellValue = column.getCellObservableValue(row).getValue();

                if (cellValue instanceof FileType.FileObject && ((FileType.FileObject) cellValue).isFolder()) {
                    changeDirectory(cellValue.toString());
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
