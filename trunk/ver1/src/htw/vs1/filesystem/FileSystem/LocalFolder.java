package htw.vs1.filesystem.FileSystem;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import htw.vs1.filesystem.FileSystem.exceptions.FSObjectNotFoundException;

import java.nio.file.FileAlreadyExistsException;
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

    /**
     * Name of our single root folder instance.
     */
    private static final String ROOT_FOLDER_NAME = "local";

    /**
     * Single instance of our local root folder.
     */
    private static LocalFolder rootFolder = null;

    /**
     * Gets the single instance of our local root
     * folder located on the machine this program
     * is running on.
     *
     * @return root {@link LocalFolder}.
     */
    public static @NotNull LocalFolder getRootFolder() {
        if (null == rootFolder) {
            rootFolder = new LocalFolder(ROOT_FOLDER_NAME);
        }

        return rootFolder;
    }

    /**
     * Reference to the {@link Folder} containing this one.
     * May be {@link null}, iff this is the root folder.
     */
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
     * Sets the parent {@link Folder} containing this FSObject. Can be
     * {@link null}, iff this is the root-Folder.
     * Precondition: the new object has to be either a
     * {@link LocalFolder}.
     *
     * @param parentFolder the parent {@link Folder} or {@code null} iff this is the
     *                     root-Folder.
     */
    @Override
    protected void setParentFolder(@Nullable Folder parentFolder) {
        checkPrecondition(parentFolder);

        this.parent = parentFolder;
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
     * Gets the {@link FSObject} identified by the given name,
     * iff this {@link Folder} contains it as a direct child.
     *
     * @param name name of the requested {@link FSObject}.
     * @return {@link FSObject} identified by the given name.
     * @throws FSObjectNotFoundException iff this {@link Folder} does not contain a
     *                                   {@link FSObject} identified by the given name as a direct child.
     */
    @Override
    public FSObject getObject(String name) throws FSObjectNotFoundException {
        for (FSObject object : contents) {
            if (object.getName().equals(name)) {
                return object;
            }
        }

        throw new FSObjectNotFoundException();
    }

    @Override
    public LinkedList<FSObject> search(LinkedList<FSObject> list, String name) {
        for (FSObject object : getContent()) {
            if (object.getName().equals(name)) {
                list.add(object);
            }
            if (object instanceof Folder) {
                ((Folder) object).search(list, name);
            }
        }

        return list;
    }

    /**
     * Add a new {@link FSObject} to this Folder.
     * Precondition: the new object has to be either a
     * {@link LocalFile} or a {@link LocalFolder}.
     *
     * @param object the new folder, which can be either a
     *               {@link LocalFile} or a {@link LocalFolder}.
     * @throws FileAlreadyExistsException iff the file already exists.
     */
    public void add(FSObject object) throws FileAlreadyExistsException {
        checkPrecondition(object);

        object.setParentFolder(this);

        contents.add(object);
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
