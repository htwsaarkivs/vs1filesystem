package htw.vs1.filesystem.FileSystem.virtual;

import com.sun.istack.internal.Nullable;
import htw.vs1.filesystem.FileSystem.exceptions.CouldNotRenameException;
import htw.vs1.filesystem.FileSystem.exceptions.InvalidFilenameException;
import htw.vs1.filesystem.UserDialog;

import java.nio.file.FileAlreadyExistsException;
import java.util.List;

/**
 * A FSObject represents a object in our filesystem.
 * It can either be a {@link Folder}, which can contain other
 * {@link FSObject}s, or a {@link File}, which is a leaf of the
 * filesystem.
 *
 * Created by markus on 01.06.15.
 */
public interface FSObject {

    /**
     * Prints the given {@link List} of {@link FSObject}s into
     * a String in a human readable format - Objects divided by
     * a newline.
     *
     * @param list {@link List} of {@link FSObject}s to print
     * @param printAbsolutePath {@code true}, iff the absolute path of the object is desired
     * @return String containing the given list in a human readable format.
     */
    static String printFSObjectList(List<FSObject> list, boolean printAbsolutePath) {
        StringBuilder builder = new StringBuilder();
        for (FSObject object : list) {
            builder.append((printAbsolutePath) ? object.getAbsolutePath() : object.getName());
            builder.append(" ");
            builder.append((object instanceof File?"[File]":"[Folder]"));
            builder.append(UserDialog.NEW_LINE);
        }

        return builder.toString();
    }

    /**
     * Set the name of this {@link FSObject}.
     *
     * @param name new name of this object.
     */
    void setName(String name) throws FileAlreadyExistsException, CouldNotRenameException, InvalidFilenameException;

    /**
     * Gets the name of this {@link FSObject}.
     *
     * @return name of this object.
     */
    String getName();

    /**
     * Gets the parent {@link Folder} containing this FSObject. Can be
     * {@link null}, iff this is the root-Folder.
     *
     * @return the parent {@link Folder} or {@code null} iff this is the
     * root-Folder.
     */
    @Nullable Folder getParentFolder();

    /**
     * Sets the parent {@link Folder} containing this FSObject. Can be
     * {@link null}, iff this is the root-Folder.
     *
     * @param parentFolder the parent {@link Folder} or {@code null} iff this is the
     * root-Folder.
     */
    void setParentFolder(@Nullable Folder parentFolder);

    /**
     * Returns the absolute path of this FSObject.
     *
     * @return absolute path - e.g. /root/folder
     */
    String getAbsolutePath();

}
