package htw.vs1.filesystem.FileSystem.virtual;

/**
 * Model to representate permissions of a {@link File} or
 * a {@link Folder}.
 *
 * Created by Felix on 29.09.2015.
 */
public class Permissions {

    private boolean locked = false;

    private final boolean lockingAllowed;

    public Permissions(final boolean lockingAllowed) {
        this.lockingAllowed = lockingAllowed;
    }

    public void toggleLock() {
        locked = !locked;
    }

    public boolean isLocked() {
        return locked;
    }

    public boolean isDeleteAllowed() {
        return !locked;
    }

    public boolean isGetContentAllowed() {
        return !locked;
    }

    public boolean isCDAllowed() {
        return !locked;
    }

    public boolean isRenameAllowed() {
        return !locked;
    }

    public boolean isAddAllowed() {
        return !locked;
    }

    public boolean isSearchAllowed() {
        return !locked;
    }

    public boolean isLockingAllowed() {
        return lockingAllowed;
    }

}
