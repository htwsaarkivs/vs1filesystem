package htw.vs1.filesystem.FileSystem;

/**
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
