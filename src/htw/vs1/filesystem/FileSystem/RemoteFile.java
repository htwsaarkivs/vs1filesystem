package htw.vs1.filesystem.FileSystem;

import com.sun.istack.internal.Nullable;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * A RemoteFile represents a {@link File} located on a remote filesystem.
 * This can be a leaf of a {@link RemoteFolder}.
 *
 * Created by markus on 03.06.15.
 */
public class RemoteFile extends File {

    /**
     * Creates a new File.
     *
     * @param name name of the new {@link File}.
     */
    public RemoteFile(String name) {
        super(name);
    }

    /**
     * Gets the parent {@link Folder} containing this FSObject. Can be
     * {@link null}, iff this is the root-Folder.
     *
     * @return the parent {@link Folder} or {@code null} iff this is the
     * root-Folder.
     */
    @Override
    public Folder getParentFolder() {
        throw new NotImplementedException();
    }

    /**
     * Sets the parent {@link Folder} containing this FSObject. Can be
     * {@link null}, iff this is the root-Folder.
     *
     * @param parentFolder the parent {@link Folder} or {@code null} iff this is the
     *                     root-Folder.
     */
    @Override
    protected void setParentFolder(@Nullable Folder parentFolder) {
        throw new NotImplementedException();
    }


}
