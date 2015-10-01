package htw.vs1.filesystem.GUI;

import htw.vs1.filesystem.FileSystem.exceptions.FileSystemException;
import htw.vs1.filesystem.FileSystem.virtual.FSObject;
import htw.vs1.filesystem.FileSystem.virtual.FileSystem;
import htw.vs1.filesystem.FileSystem.virtual.FileSystemInterface;
import htw.vs1.filesystem.FileSystem.virtual.Folder;
import htw.vs1.filesystem.FileSystemManger;
import htw.vs1.filesystem.Network.Discovery.DiscoveryManager;
import htw.vs1.filesystem.Network.Discovery.FileSystemServer;
import htw.vs1.filesystem.Network.Protocol.ServerStatus;
import htw.vs1.filesystem.Network.ServerStatusObserver;
import htw.vs1.filesystem.Network.TCPParallelServer;
import htw.vs1.filesystem.Trials.Thread.FileSystemManager;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    @FXML
    public ImageView imageViewServerIndicator;
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
    @FXML
    private Tab tabSearch;
    @FXML
    private Tab tabServer;
    @FXML
    private TabPane tabPane;
    @FXML
    private ListView<FileSystemServer> listViewTabServer;

    private ObservableList<FileType> currentDirectory = FXCollections.observableArrayList();
    private ObservableList<SearchItem> searchResults = FXCollections.observableArrayList();
    private ObservableList<String> logEntry;
    private ObservableList<FileSystemServer> serverEntrys = FXCollections.observableArrayList();

    private FileSystemInterface fileSystem;

    public void changeDirectory (String directory){
        try {
            fileSystem.changeDirectory(directory);
            listDirectoryContent();
        } catch (FileSystemException e) {
            if (FileSystemManager.DEBUG) {
                e.printStackTrace();
            }
            showErrorMessage(e);
        }
    }

    public void home(ActionEvent actionEvent) {
        changeDirectory("/");
    }

    public void refresh(ActionEvent actionEvent) {
        listDirectoryContent();
    }

    public void listDirectoryContent () {
        List<FSObject> list;
        try {
            list = fileSystem.getWorkingDirectory().getContent();

            currentDirectory.clear();
            currentDirectory.add(new FileType("..", true));
            for (FSObject fsObject : list) {
                currentDirectory.add(new FileType(fsObject.getName(), fsObject instanceof Folder));
            }
            this.textFieldDirectory.setText(fileSystem.printWorkingDirectory());
            tableView.sort();
        } catch (FileSystemException e) {
            if (FileSystemManager.DEBUG) {
                e.printStackTrace();
            }
            showErrorMessage(e);
        }


    }

    public void rename (){
        Object cellContent = getCellContenct();
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Rename File/Folder");
        dialog.setHeaderText("Oh baby please give me a new name. Oh yeah, baby do it now");
        dialog.setContentText("give it to me:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
            try {
                fileSystem.rename(cellContent.toString(), name);
                listDirectoryContent();
            } catch (FileSystemException e) {
                if (FileSystemManager.DEBUG) {
                    e.printStackTrace();
                }
                showErrorMessage(e);
            }
        });
    }

    public void delete (){
        Object cellValue = getCellContenct();
        try {
            fileSystem.delete(cellValue.toString());
            listDirectoryContent();
        } catch (FileSystemException e) {
            if (FileSystemManager.DEBUG) {
                e.printStackTrace();
            }
            showErrorMessage(e);
        }
    }
    public void toggleLock(ActionEvent actionEvent) {
        Object cellValue = getCellContenct();
        try {
            fileSystem.toggleLock(cellValue.toString());
            listDirectoryContent();
        } catch (FileSystemException e) {
            if (FileSystemManager.DEBUG) {
                e.printStackTrace();
            }
            showErrorMessage(e);
        }
    }

    public void createDir(){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Create Folder");
        dialog.setHeaderText("Please enter a foldername");
        dialog.setContentText("Foldername:");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(name -> {
            try {
                fileSystem.createFSObject(name, true);
                listDirectoryContent();
            } catch (FileSystemException e) {
                if (FileSystemManager.DEBUG) {
                    e.printStackTrace();
                }
                showErrorMessage(e);
            }
        });
    }

    public void createFile(){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Create File");
        dialog.setHeaderText("Please enter a filename");
        dialog.setContentText("Filename:");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(name -> {
            try {
                fileSystem.createFSObject(name, false);
                listDirectoryContent();
            } catch (FileSystemException e) {
                if (FileSystemManager.DEBUG) {
                    e.printStackTrace();
                }
                showErrorMessage(e);
            }
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
            mount(arr[0], arr[1], Integer.parseInt(arr[2]));
        });
    }

    public void mount(String folderName, String ip, int port) {
        try {
            fileSystem.mount(
                    folderName, ip, port,
                    TCPParallelServer.DEFAULT_USER, TCPParallelServer.DEFAULT_PASS);
            listDirectoryContent();
        } catch (FileSystemException e) {
            if (FileSystemManager.DEBUG) {
                e.printStackTrace();
            }
            showErrorMessage(e);
        }
    }

    public void close(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void initiateFilesystem () throws IOException {
        fileSystem = new FileSystem(true);

        listDirectoryContent();

    }

    public void changeDirectoryManually(ActionEvent actionEvent) {
        String dirStr = textFieldDirectory.getText();
        if (dirStr == null || dirStr.isEmpty()) return;
        changeDirectory(dirStr);
    }


    public void search(){
        String searchStr = textFieldSearch.getText();
        if (searchStr == null || searchStr.isEmpty()) return;

        searchResults.clear();
        List<FSObject> list;
        try {
            list = fileSystem.search(searchStr);
            for (FSObject fsObject : list) {
                searchResults.add(new SearchItem(fsObject.getName(), fsObject.getAbsolutePath()));
            }
        } catch (Throwable e) {
            if (FileSystemManager.DEBUG) {
                e.printStackTrace();
            }
            showErrorMessage(e);
        }
        tableViewSearch.setItems(searchResults);
        tabPane.getSelectionModel().select(tabSearch);


    }

    public void showErrorMessage(Throwable e){
        Alert alert  = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(e.getClass().getSimpleName() + ": " + e.getMessage());
        alert.showAndWait();
    }

    public Object getCellContenct (){
        TablePosition position = tableView.getSelectionModel().getSelectedCells().get(0);
        int row = position.getRow();
        TableColumn column = position.getTableColumn();
        return column.getCellObservableValue(row).getValue();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        /**
         * Initiate the textfields
         */
        textFieldDirectory.setText("Current Path ....");

        try {
            initiateFilesystem();
        } catch (IOException e) {
            if (FileSystemManager.DEBUG) {
                e.printStackTrace();
            }
            showErrorMessage(e);
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

                Object cellValue = getCellContenct();

                if (cellValue instanceof FileType.FileObject && ((FileType.FileObject) cellValue).isFolder()) {
                    changeDirectory(cellValue.toString());
                }

            }
        });
        /**
         * Initiate the columns of the tableViewSearch
         */
        tableColumnSearchName.setCellValueFactory(cellData -> cellData.getValue().fileNameProperty());
        tableColumnSearchDirectory.setCellValueFactory(cellData -> cellData.getValue().pathProperty());


        refreshServerList();
        listViewTabServer.setItems(serverEntrys);
        listViewTabServer.setCellFactory(new Callback<ListView<FileSystemServer>, ListCell<FileSystemServer>>() {
            @Override
            public ListCell<FileSystemServer> call(ListView<FileSystemServer> param) {
                return new ListCell<FileSystemServer>() {
                    @Override
                    protected void updateItem(FileSystemServer item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item.getHostName() + " (" + item.toString() + ")");
                        } else {
                            setText("");
                        }
                    }
                };
            }
        });
        tabPane.getSelectionModel().select(tabServer);
        DiscoveryManager.getInstance().attachObserver(() -> {
            /*
            With the Platform.runLater()-call it will be granted that the
            refreshServerList()-call will be executed from the javaFX-Thread.
             */
            Platform.runLater(Controller.this::refreshServerList);

        });

       listViewTabServer.setOnMouseClicked(event -> {
           if (event.getClickCount() == 2){
               FileSystemServer currentItem = listViewTabServer.getSelectionModel().getSelectedItem();
               mount(currentItem.getHostName(), currentItem.getHost(), currentItem.getPort());
           }
       });


        setServerStatusIndicator(FileSystemManger.getInstance().getServerStatus());
        FileSystemManger.getInstance().attachServerStatusObserver(this::setServerStatusIndicator);

    }

    private void setServerStatusIndicator(ServerStatus status) {
        switch (status) {
            case RUNNING:
                imageViewServerIndicator.setImage(Resources.server_status_indicator_green);
                break;

            case STOPPED:
                imageViewServerIndicator.setImage(Resources.server_status_indicator_red);
                break;

            default:
                break;
        }
    }

    private void refreshServerList() {
        serverEntrys.clear();
        serverEntrys.addAll(FileSystemManger.getInstance().listAvailableFileSystemServers());
    }
}
