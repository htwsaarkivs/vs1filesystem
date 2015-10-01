package htw.vs1.filesystem.FileSystem.physical;

import com.sun.nio.file.SensitivityWatchEventModifier;
import htw.vs1.filesystem.FileSystemManger;

import java.io.IOException;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

/**
 * Abstract service to watch a directory on the
 * physical file system.
 *
 * Created by felix on 17.06.15.
 */
public abstract class AbstractWatchService extends Thread {

    private static final String THREAD_NAME = "WatchServiceThread";
    private boolean stop = false;

    private final WatchService watcher;
    private final Map<WatchKey,Path> keys;

    /**
     * Creates a WatchService and registers the given directory
     */
    AbstractWatchService() throws IOException {
        setName(THREAD_NAME);
        this.watcher = FileSystems.getDefault().newWatchService();
        this.keys = new HashMap<>();
    }

    /**
     * Registers a directory given by the {@link Path}.
     * The directory will be added to the watch list.
     *
     * @param dir {@link Path} to the directory.
     * @throws IOException
     */
    protected void register(Path dir) throws IOException {
        WatchKey key = dir.register(watcher,
                new WatchEvent.Kind[] {ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY},
                SensitivityWatchEventModifier.HIGH); // Sensitive high will update immediately !

        keys.put(key, dir);
    }

    private void processEvents() {
        while (!stop) {
            // wait for key to be signalled
            WatchKey key;
            try {
                key = watcher.take();
            } catch (InterruptedException x) {
                if (FileSystemManger.DEBUG) {
                    x.printStackTrace();
                }
                continue;
            }

            Path dir = keys.get(key);
            if (null == dir) {
                // key not recognized !
                continue;
            }

            for (WatchEvent<?> watchEvent : key.pollEvents()) {
                WatchEvent.Kind kind = watchEvent.kind();

                if (kind == StandardWatchEventKinds.OVERFLOW) {
                    continue;
                }

                // Context for directory entry event is the file name of entry
                @SuppressWarnings("unchecked")
                WatchEvent<Path> pathWatchEvent = (WatchEvent<Path>) watchEvent;
                // in case of kind=(create,delete,modify) the context is the relative path between
                // the directory that is registered and the entry which is created/deleted/modified.
                Path name = pathWatchEvent.context();
                // the child is the entry which is created/deleted/modified.
                Path child = dir.resolve(name);

                if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
                    onEntryCreate(child, dir);
                } else if (kind == StandardWatchEventKinds.ENTRY_DELETE) {
                    onEntryDelete(child, dir);
                }
                    // we do not handle the modify event.

                // reset key and remove from set if directory no longer accessible
                boolean valid = key.reset();
                if (!valid) {
                    keys.remove(key);

                    // all directories are inaccessible
                    if (keys.isEmpty()) {
                        break;
                    }
                }
            }

        }
    }

    /**
     * Called, when a object was deleted.
     *
     * @param child affected entry.
     * @param parent parent folder containing the affected entry.
     */
    protected abstract void onEntryDelete(Path child, Path parent);

    /**
     * Called, when a new object was created.
     *
     * @param child affected entry.
     * @param parent parent folder containing the affected entry.
     */
    protected abstract void onEntryCreate(Path child, Path parent);

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        processEvents();
    }

    /**
     * Tells the thread to stop as soon as
     * possible.
     */
    public void requestStop() {
        stop = true;
        interrupt();
    }
}
