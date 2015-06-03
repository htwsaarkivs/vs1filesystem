package htw.vs1.filesystem.FileSystem;

/**
 * A File represents a leaf in our filesystem.
 * It can either be a {@link LocalFile} or a {@link RemoteFile}.
 *
 * Created by markus on 01.06.15.
 */
public abstract class File extends FSObject {


    /**
     * Creates a new File.
     *
     * @param name name of the new {@link File}.
     */
    public File(String name) {
        super(name);
    }
}
