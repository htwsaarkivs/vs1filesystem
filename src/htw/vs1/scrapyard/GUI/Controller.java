package htw.vs1.scrapyard.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
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
    private TableColumn<FileType, String> tableColumnName;
    @FXML
    private TableColumn<FileType,String> tableColumnType;

    private ObservableList<FileType> currentDirectory = FXCollections.observableArrayList();
    private ObservableList<SearchItem> searchResults = FXCollections.observableArrayList();
    private ObservableList<String> logEntry;

    public void changeDirectory (String directory){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("CD");
        alert.showAndWait();
    }

    public void listDirectoryContent (){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("LS");
        alert.showAndWait();

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

    public void search(){
        //call search

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /**
         * Initiate the textfields
         */
        textFieldDirectory.setText("Current Path ....");
        textFieldSearch.setText("Enter value ...");

        /**
         * Fill in some dummydata in the list
         */
        currentDirectory.add(new FileType("Folder1", "Folder"));
        currentDirectory.add(new FileType("File1", "File"));

        /**
         * Don't know how, but it works
         */
        tableColumnName.setCellValueFactory(cellData -> cellData.getValue().fileNameProperty());
        tableColumnType.setCellValueFactory(cellData -> cellData.getValue().fileTypeProperty());
        tableView.setItems(currentDirectory);

        /**
         * Add Mouselistener, check for doubleclick and that the clicked item is a folder
         */
        tableView.getSelectionModel().setCellSelectionEnabled(true);
        tableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2 ){
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
            }
        });


    }
}
