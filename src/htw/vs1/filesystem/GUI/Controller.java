package htw.vs1.filesystem.GUI;

import htw.vs1.filesystem.FileSystem.exceptions.FileSystemException;
import htw.vs1.filesystem.FileSystem.virtual.*;
import htw.vs1.filesystem.FileSystemManger;
import htw.vs1.filesystem.Network.Discovery.DiscoveryManager;
import htw.vs1.filesystem.Network.Discovery.FileSystemServer;
import htw.vs1.filesystem.Network.Log.LogEntry;
import htw.vs1.filesystem.Network.Log.LogType;
import htw.vs1.filesystem.Network.Protocol.ServerStatus;
import htw.vs1.filesystem.Network.TCPParallelServer;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import java.awt.*;
import java.io.*;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller-Class to handle gui events.
 */
public class Controller implements Initializable {

    private static final String BACK_FOLDER_NAME = FileSystem.UP;

    @FXML
    public ImageView imageViewServerIndicator;
    public Button clearSelTabBtn;
    public Button deleteBtn;
    public Button renameBtn;
    public Button lockBtn;
    public Tab tabLog;
    @FXML
    public Button createDirBtn;
    @FXML
    public Button createFileBtn;
    @FXML
    private TableView<FileType> tableView;
    @FXML
    private TableView tableViewSearch;
    @FXML
    private ListView<LogEntry> listViewTabLog;
    @FXML
    private TextField textFieldDirectory;
    @FXML
    private TextField textFieldSearch;
    @FXML
    private TableColumn<FileType, Image> tableColumnIcon;
    @FXML
    private TableColumn<FileType, FileObject> tableColumnName;
    @FXML
    private TableColumn<FileType, String> tableColumnType;
    @FXML
    public TableColumn<SearchItem, Image> tableColumnSearchIcon;
    @FXML
    private TableColumn<SearchItem, String> tableColumnSearchName;
    @FXML
    private TableColumn<SearchItem, String> tableColumnSearchDirectory;
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
    private ObservableList<LogEntry> logEntries = FXCollections.observableArrayList();
    private ObservableList<FileSystemServer> serverEntrys = FXCollections.observableArrayList();

    private FileSystemInterface fileSystem;

    private void setButtons(FileType selectedFileType) {
        if (selectedFileType == null) {
            // if there is no file object selected, delete/rename/lock is not possible
            deleteBtn.setDisable(true);
            renameBtn.setDisable(true);
            lockBtn.setDisable(true);
        } else {
            // there is an item selected, so we have to check if any of these actions are allowed
            deleteBtn.setDisable(!selectedFileType.getPermissions().isDeleteAllowed());
            renameBtn.setDisable(!selectedFileType.getPermissions().isRenameAllowed());
            lockBtn.setDisable(!selectedFileType.getPermissions().isLockingAllowed());
        }

        boolean addAllowed = fileSystem.getWorkingDirectory().getPermissions().isAddAllowed();
        createDirBtn.setDisable(!addAllowed);
        createFileBtn.setDisable(!addAllowed);
    }

    public void changeDirectory(String directory) {
        boolean error = false;
        try {
            fileSystem.changeDirectory(directory);
        } catch (FileSystemException e) {
            if (FileSystemManger.DEBUG) {
                e.printStackTrace();
            }
            showErrorMessage(e);
            error = true;
        }

        // in case directory is an absolute path the working directory
        // could have changed. So we fetch the content and update the view
        // but we do not show an error if updating the view failed,
        // because the error was already shown by changing the directory.
        listDirectoryContent(error);
    }

    public void home(ActionEvent actionEvent) {
        changeDirectory("/");
    }

    public void refresh(ActionEvent actionEvent) {
        listDirectoryContent();
    }

    public void listDirectoryContent() {
        listDirectoryContent(true);
    }

    public void listDirectoryContent(boolean showErrorMessage) {
        List<FSObject> list;
        try {
            list = fileSystem.getWorkingDirectory().getContent();

            currentDirectory.clear();
            currentDirectory.add(new FileType(BACK_FOLDER_NAME, true, new BackFolderPermissions()));
            for (FSObject fsObject : list) {
                currentDirectory.add(
                        new FileType(fsObject.getName(), fsObject instanceof Folder, fsObject.getPermissions()));
            }
            this.textFieldDirectory.setText(fileSystem.printWorkingDirectory());
            tableView.sort();
        } catch (FileSystemException e) {
            if (FileSystemManger.DEBUG) {
                e.printStackTrace();
            }
            if (showErrorMessage) {
                showErrorMessage(e);
            }
        }


        // check if there is any selected object and reset the icon state (enabled/disabled)
        FileType selectedObject = null;
        if (!tableView.getSelectionModel().getSelectedCells().isEmpty()) {
            selectedObject = getSelectedFileType();
        }
        setButtons(selectedObject);
    }

    public void rename() {
        Object cellContent = getSelectedCellContent();
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Rename File/Folder");
        dialog.setHeaderText("Please enter new name");
        dialog.setContentText("New name:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
            try {
                fileSystem.rename(cellContent.toString(), name);
                listDirectoryContent();
            } catch (FileSystemException e) {
                if (FileSystemManger.DEBUG) {
                    e.printStackTrace();
                }
                showErrorMessage(e);
            }
        });
    }

    public void delete() {
        Object cellValue = getSelectedCellContent();
        try {
            fileSystem.delete(cellValue.toString());
            listDirectoryContent();
        } catch (FileSystemException e) {
            if (FileSystemManger.DEBUG) {
                e.printStackTrace();
            }
            showErrorMessage(e);
        }
    }

    public void toggleLock(ActionEvent actionEvent) {
        Object cellValue = getSelectedCellContent();
        try {
            fileSystem.toggleLock(cellValue.toString());
            listDirectoryContent();
        } catch (FileSystemException e) {
            if (FileSystemManger.DEBUG) {
                e.printStackTrace();
            }
            showErrorMessage(e);
        }
    }

    public void createDir() {
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
                if (FileSystemManger.DEBUG) {
                    e.printStackTrace();
                }
                showErrorMessage(e);
            }
        });
    }

    public void createFile() {
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
                if (FileSystemManger.DEBUG) {
                    e.printStackTrace();
                }
                showErrorMessage(e);
            }
        });
    }

    public void mount(ActionEvent actionEvent) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Mount remote file system");
        dialog.setHeaderText("Please enter the foldername, the host IP and port of filesystem");
        dialog.setContentText("Format: foldername:host:port");

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
            if (FileSystemManger.DEBUG) {
                e.printStackTrace();
            }
            showErrorMessage(e);
        }
    }

    public void close(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void initiateFilesystem() throws IOException {
        fileSystem = FileSystemManger.getInstance().getFileSystem(true);

        listDirectoryContent();

    }

    public void changeDirectoryManually(ActionEvent actionEvent) {
        String dirStr = textFieldDirectory.getText();
        if (dirStr == null || dirStr.isEmpty()) return;
        changeDirectory(dirStr);
    }


    public void search() {
        String searchStr = textFieldSearch.getText();
        if (searchStr == null || searchStr.isEmpty()) return;

        searchResults.clear();
        List<FSObject> list;
        try {
            list = fileSystem.search(searchStr);
            for (FSObject fsObject : list) {

                searchResults.add(
                        new SearchItem(fsObject.getName(), (fsObject instanceof Folder), fsObject.getPermissions(), fsObject.getParentFolder().getAbsolutePath()));
            }
        } catch (Throwable e) {
            if (FileSystemManger.DEBUG) {
                e.printStackTrace();
            }
            showErrorMessage(e);
        }
        tableViewSearch.setItems(searchResults);
        tabPane.getSelectionModel().select(tabSearch);


    }

    public void showErrorMessage(Throwable e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(e.getClass().getSimpleName() + ": " + e.getMessage());
        alert.showAndWait();
    }

    public FileType getSelectedFileType() {
        TablePosition position = tableView.getSelectionModel().getSelectedCells().get(0);
        int row = position.getRow();
        //TableColumn column = position.getTableColumn();
        //return column.getCellObservableValue(row).getValue();

        return currentDirectory.get(row);
    }

    public FileObject getSelectedCellContent() {
        return getSelectedFileType().fileNameProperty().getValue();
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
            if (FileSystemManger.DEBUG) {
                e.printStackTrace();
            }
            showErrorMessage(e);
        }


        /**
         * Disable edit buttons @ program start
         * after initiating the file system!
         */
        setButtons(null);

        listViewTabLog.setItems(logEntries);
        listViewTabLog.setCellFactory(new Callback<ListView<LogEntry>, ListCell<LogEntry>>() {
            @Override
            public ListCell<LogEntry> call(ListView<LogEntry> param) {
                return new ListCell<LogEntry>() {
                    @Override
                    protected void updateItem(LogEntry item, boolean empty) {

                        super.updateItem(item, empty);
                        if (null == item) {
                            setText("");
                            setGraphic(null);
                        } else if (item.getType() == LogType.SERVER_LOG) {
                            ImageView image = new ImageView(Controller.class.getResource("images/Button_Blue/Button_Blue_016.png").toString());
                            setText(item.getLog());
                            setTextFill(Color.BLUE);
                            setGraphic(image);
                        } else {
                            ImageView image = new ImageView(Controller.class.getResource("images/Button_Red/Button_Red_016.png").toString());
                            setText(item.toString());
                            setTextFill(Color.RED);
                            setGraphic(image);
                        }
                    }
                };
            }
        });


        FileSystemManger.getInstance().addNetworkLogSubscriber(
                log -> Platform.runLater(() -> logEntries.add(log))
        );

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
        tableView.getSelectionModel().setCellSelectionEnabled(false);
        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {

                Object cellValue = getSelectedCellContent();

                if (cellValue != null && ((FileObject) cellValue).isFolder()) {
                    changeDirectory(cellValue.toString());
                } else if (cellValue != null && !((FileObject) cellValue).isFolder()) {
                    String name = ((FileObject) cellValue).getName();
                    try {
                        FSObject item = fileSystem.getWorkingDirectory().getObject(name);
                        if (item instanceof LocalFile) {
                            File file = ((LocalFile) item).getPhysicalFile();
                            Desktop.getDesktop().open(file);
                        }
                    } catch (FileSystemException | IOException e) {
                        // TODO: blabla
                        e.printStackTrace();
                    }
                }

            }
        });

        tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<FileType>() {
            @Override
            public void changed(ObservableValue<? extends FileType> observable, FileType oldValue, FileType newValue) {
                setButtons(newValue);
            }
        });
        /**
         * Initiate the columns of the tableViewSearch
         */
        tableColumnSearchIcon.setCellValueFactory(cellData -> cellData.getValue().iconProperty());
        tableColumnSearchIcon.setCellFactory(param -> new TableCell<SearchItem, Image>() {
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
                }
        );
        tableColumnSearchName.setCellValueFactory(cellData -> cellData.getValue().fileNameProperty());
        tableColumnSearchDirectory.setCellValueFactory(cellData -> cellData.getValue().pathProperty());


        tableViewSearch.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                int selectedIndex = tableViewSearch.getSelectionModel().getSelectedIndex();

                SearchItem selectedItem = searchResults.get(selectedIndex);

                changeDirectory(selectedItem.getPath());
                                                        // create a dummy fileType to get the corresponding index
                int index = currentDirectory.indexOf(new FileType(selectedItem.getFileName(), true, null));
                if (index != -1) {
                    //tableView.requestFocus();
                    tableView.getSelectionModel().select(index);
                }
            }
        });


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
        DiscoveryManager.getInstance().attachObserver(() -> {
            /*
            With the Platform.runLater()-call it will be granted that the
            refreshServerList()-call will be executed from the javaFX-Thread.
             */
            Platform.runLater(Controller.this::refreshServerList);

        });

        listViewTabServer.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                FileSystemServer currentItem = listViewTabServer.getSelectionModel().getSelectedItem();
                mount(currentItem.getHostName(), currentItem.getHost(), currentItem.getPort());
            }
        });


        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (Objects.equals(newValue.getId(), tabLog.getId())) {
                // Log-Tab visible
                clearSelTabBtn.setVisible(true);
                clearSelTabBtn.getTooltip().setText("Clear Log");
            } else if (Objects.equals(newValue.getId(), tabSearch.getId())) {
                // Log-Tab visible
                clearSelTabBtn.setVisible(true);
                clearSelTabBtn.getTooltip().setText("Clear Search");
            } else {
                // ServerTab or no tab visible
                clearSelTabBtn.setVisible(false);
            }
        });


        tabPane.getSelectionModel().select(tabServer);

        setServerStatusIndicator(FileSystemManger.getInstance().getServerStatus());
        FileSystemManger.getInstance().attachServerStatusObserver(this::setServerStatusIndicator);

    }

    public void clearSelectedTab() {
        Tab currentTab = tabPane.getSelectionModel().getSelectedItem();
        if (currentTab.equals(tabLog)) {
            logEntries.clear();
        } else if (currentTab.equals(tabSearch)) {
            searchResults.clear();
        }
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

    public void showAbout (){
        Alert about = new Alert(Alert.AlertType.INFORMATION);
        about.setTitle("About");
        about.setHeaderText("vfiles v1.0");
        about.setContentText("\u00A9 2015 by Daniel Barth, Felix Blechschmitt, Regine Eboumbou, Hendrik Fritsch, Markus Jungbluth, Adrian Mueller, Marc Otting");
        about.showAndWait();
    }

    public void openHelp () {
        try {
            Desktop.getDesktop().open(FileSystemManger.getInstance().getHelpFile());
        } catch (Throwable e) {
            Alert io = new Alert(Alert.AlertType.ERROR);
            io.setTitle("Error");
            io.setContentText("Could not open Help PDF!");
            io.showAndWait();
        }
    }

}
