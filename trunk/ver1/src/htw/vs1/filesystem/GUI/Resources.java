package htw.vs1.filesystem.GUI;

import htw.vs1.filesystem.FileSystem.virtual.Permissions;
import javafx.scene.image.Image;

/**
 * Created by Felix on 30.09.2015.
 */
public class Resources {

    public static final Image server_status_indicator_red
            = new Image(FileType.class.getResource("images/Button_Red/Button_Red_032.png").toString());


    public static final Image server_status_indicator_green
            = new Image(FileType.class.getResource("images/Button_Green/Button_Green_032.png").toString());

    public static Image fsObjectIcon(boolean isFolder, Permissions permissions) {
        if (isFolder) {
            return (permissions.isLocked())
                    ? new Image(FileType.class.getResource("images/Folder_Lock/Folder_Lock_032.png").toString())
                    : new Image(FileType.class.getResource("images/Folder/Folder_032.png").toString());
        } else {
            return (permissions.isLocked())
                    ? new Image(FileType.class.getResource("images/File_Lock/File_Lock_032.png").toString())
                    : new Image(FileType.class.getResource("images/File_Text/File_Text_032.png").toString());
        }
    }

}
