package htw.vs1.scrapyard.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

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
    private TableColumn<FileType, FileType.FileObject> tableColumnName;
    @FXML
    private TableColumn<FileType, String> tableColumnType;

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

    public void close(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void search(){
        String searchStr = textFieldSearch.getText();
        if (searchStr == null || searchStr.isEmpty()) return;

        //call search
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

        /**
         * Don't know how, but it works
         */
        tableColumnName.setCellValueFactory(cellData -> cellData.getValue().fileNameProperty());
        tableColumnName.setCellFactory(param -> new TableCell<FileType, FileType.FileObject>() {
            ImageView imageView = new ImageView();
            @Override
            protected void updateItem(FileType.FileObject item, boolean empty) {
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
        });
        tableColumnType.setCellValueFactory(cellData -> cellData.getValue().fileTypeProperty());
        tableView.setItems(currentDirectory);

        /**
         * Add Mouselistener, check for doubleclick and that the clicked item is a folder
         */
        tableView.getSelectionModel().setCellSelectionEnabled(true);
        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 ){
                TablePosition position = tableView.getSelectionModel().getSelectedCells().get(0);

                TableColumn<FileType, ?> col = tableView.getColumns().get(position.getRow());
                Object object = col.getCellData(0);
                if (object instanceof FileType.FileObject) {
                    if ( ((FileType.FileObject) object).isFolder() ) {
                        changeDirectory(object.toString());
                    }
                }


                /*TablePosition position = tableView.getSelectionModel().getSelectedCells().get(0);
                int row = position.getRow();
                int col = position.getColumn();
                TableColumn column = position.getTableColumn();
                String cellContent = column.getCellData(row).toString();
                String fileType = currentDirectory.get(row).toString();

                if (col == 0 && fileType.equals(CONDITION)){
                    changeDirectory(cellContent);
                }*/

            }
        });


    }
}
