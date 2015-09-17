package htw.vs1.scrapyard.GUI;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    @FXML
    private TableView tableView;
    @FXML
    private TableView tableViewSearch;
    @FXML
    private ListView listViewTabLog;
    @FXML
    private TextField textFieldDirectory;
    @FXML
    private TextField textFieldSearch;
    @FXML
    private TableColumn<String,String> tableColumnName;
    @FXML
    private TableColumn<String,String> tableColumnType;

    private ObservableList<String> currentDirectory;
    private ObservableList<String> searchResults;
    private ObservableList<String> logEntry;

    public void changeDirectory (){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("CD");
        alert.showAndWait();
    }

    public void listDirectoryContent (){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("CD");
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
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("search");
        alert.showAndWait();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        textFieldDirectory.setText("Current Path ....");
        textFieldSearch.setText("Enter value ...");

    }
}
