package htw.vs1.filesystem.GUI;

import htw.vs1.filesystem.FileSystem.virtual.Permissions;

/**
 * Permissions for the special only visible back folder.
 *
 * Created by Felix on 03.10.2015.
 */
public class BackFolderPermissions extends Permissions {

    public BackFolderPermissions() {
        super(false);
    }

    @Override
    public boolean isRenameAllowed() {
        return false;
    }

    @Override
    public boolean isDeleteAllowed() {
        return false;
    }
}
