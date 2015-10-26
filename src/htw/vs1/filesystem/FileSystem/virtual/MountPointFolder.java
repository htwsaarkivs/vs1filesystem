package htw.vs1.filesystem.FileSystem.virtual;

import htw.vs1.filesystem.FileSystem.exceptions.FileSystemException;

/**
 * The MountPointFolder contains all distributed sub filesystems
 * which should be unified in one filesystem.
 *
 * Created by Felix on 30.09.2015.
 */
public class MountPointFolder extends LocalFolder {

    public MountPointFolder(String name) throws FileSystemException {
        super(name);
        getPermissions().setMountable();
    }

    public void addMountPoint(Folder folder) throws FileSystemException {
        addObjectToContent(folder);
    }
}
