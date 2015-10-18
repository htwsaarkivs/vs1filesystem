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
        // permissions might be null if we have created a dummy object to get the corresponding item from the list.
        if (isFolder) {
            return (null != permissions && permissions.isLocked())
                    ? new Image(FileType.class.getResource("images/Locked/Folder_Locked_new_032.png").toString())
                    : new Image(FileType.class.getResource("images/Folder/Folder_032.png").toString());
        } else {
            return (null != permissions && permissions.isLocked())
                    ? new Image(FileType.class.getResource("images/Locked/File_Text_Locked_new_032.png").toString())
                    : new Image(FileType.class.getResource("images/File_Text/File_Text_032.png").toString());
        }
    }

}
