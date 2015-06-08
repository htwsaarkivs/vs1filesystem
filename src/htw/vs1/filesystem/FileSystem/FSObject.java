package htw.vs1.filesystem.FileSystem;

import htw.vs1.filesystem.UserDialog;

import java.util.List;

/**
 * A FSObject represents a object in our filesystem.
 * It can either be a {@link Folder}, which can contain other
 * {@link FSObject}s, or a {@link File}, which is a leaf of the
 * filesystem.
 *
 * Created by markus on 01.06.15.
 */
public abstract class FSObject {

    public static String printFSObjectList(List<FSObject> list) {
        StringBuilder builder = new StringBuilder();
        for (FSObject object : list) {
            // TODO: How can I print the absolute path if the FSObject is a File?? - Used to the search...
            builder.append(object.getName());
            builder.append(" ");
            builder.append((object instanceof File?"[File]":"[Folder]"));
            builder.append(UserDialog.NEW_LINE);
        }

        return builder.toString();
    }

    private String name;

    /**
     * Creates a new FSObject.
     *
     * @param name name of the FSObject.
     */
    public FSObject(String name) {
        setName(name);
    }

    /**
     * Set the name of this {@link FSObject}.
     *
     * @param name new name of this object.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the name of this {@link FSObject}.
     *
     * @return name of this object.
     */
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName();
    }



}
