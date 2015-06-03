package FileSystem;

import java.util.LinkedList;
import java.util.List;

/**
 * A LocalFolder represents a Folder of the local filesystem,
 * which is a node of our filesystem.
 *
 * Precondition: A LocalFolder can only contain {@link LocalFolder}s or {@link LocalFile}s.
 *
 * Created by felix on 03.06.15.
 */
public class LocalFolder extends Folder {

    private Folder parent = null;

    /**
     * List containing the content of this {@link LocalFolder}, this
     * can be either a {@link LocalFolder} or a {@link LocalFile}.
     */
    private LinkedList<FSObject> contents = new LinkedList<>();

    /**
     * Creates a new Folder with the given name.
     *
     * @param name name of the new {@link Folder}.
     */
    public LocalFolder(String name) {
        super(name);
    }

    /**
     * Get the parent {@link Folder} containing this Folder.
     * Can be {@link null}, iff this is the root-Folder.
     *
     * @return the parent {@link Folder} or {@code null} iff this is the root-Folder.
     */
    @Override
    public Folder getParentFolder() {
        return parent;
    }

    /**
     * Get the Content of this {@link Folder} as a {@link List}
     * of {@link FSObject}s.
     *
     * @return the content of this folder.
     */
    @Override
    public List<FSObject> getContent() {
        return contents;
    }

    /**
     * Add a new {@link FSObject} to this Folder.
     * Precondition: the new object has to be either a
     * {@link LocalFile} or a {@link LocalFolder}.
     *
     * @param object the new folder, which can be either a
     *               {@link LocalFile} or a {@link LocalFolder}.
     */
    public void add(FSObject object) {
        checkPrecondition(object);

        contents.add(object);
    }

    /**
     * Sets the given {@link Folder} as the parent Folder of this Object.
     * Precondition: the new object has to be either a
     * {@link LocalFile} or a {@link LocalFolder}.
     *
     * @param parent parent Folder, which is containing this {@link Folder}.
     */
    public void setParent(Folder parent) {
        checkPrecondition(parent);

        this.parent = parent;
    }

    /**
     * Checks the precondition that the given objects has to be a
     * {@link LocalFolder} or a {@link LocalFile}.
     *
     * @param object {@link FSObject} which has to match the precondition.
     */
    private void checkPrecondition(FSObject object) {
        if (!((object instanceof LocalFolder) || (object instanceof LocalFile))) {
            // this case should never happen -> precondition !
            throw new IllegalArgumentException("The new object has to be either a LocalFolder or a LocalFile");
        }
    }
}
