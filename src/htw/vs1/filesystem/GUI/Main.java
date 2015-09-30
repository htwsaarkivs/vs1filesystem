package htw.vs1.filesystem.GUI;

import htw.vs1.filesystem.FileSystemManger;
import htw.vs1.filesystem.Network.TCPParallelServer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{


        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Virtual Filesystem");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                FileSystemManger.getInstance().close();
            }
        });
    }


    public static void main(String[] args) {
        if (args.length == 0 || args.length > 2) {
            htw.vs1.filesystem.Main.usage();
        }
        String mode = "default";
        if (args.length > 1) {
            if (args[1].equals("c")) {
                mode = "client";
            }
            if (args[1].equals("s")) {
                mode = "server";
            }
        }

        switch (mode) {
            case "client":
                FileSystemManger.getInstance().initClientOnlyMode();
                break;

            case "server":
                FileSystemManger.getInstance().initServerOnlyMode(args[0], TCPParallelServer.DEFAULT_PORT);
                break;

            default:
                FileSystemManger.getInstance().init(args[0], TCPParallelServer.DEFAULT_PORT);
        }


        launch(args);
    }
}
