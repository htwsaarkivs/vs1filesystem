package htw.vs1.filesystem.FileSystem;

/**
 * A LocalFile represents a File in the local filesystem, which
 * is a leaf in our filesystem.
 *
 * Created by felix on 03.06.15.
 */
public class LocalFile extends File {


    /**
     * Creates a new LocalFile.
     *
     * @param name name of the new {@link File}.
     */
    public LocalFile(String name) {
        super(name);
    }
}
