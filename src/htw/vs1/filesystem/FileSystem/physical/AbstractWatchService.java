package htw.vs1.filesystem.FileSystem.physical;

import com.sun.nio.file.SensitivityWatchEventModifier;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

/**
 * Created by felix on 17.06.15.
 */
public abstract class AbstractWatchService extends Thread {

    private boolean stop = false;

    private final WatchService watcher;
    private final Map<WatchKey,Path> keys;
    private final boolean recursive;
    private boolean trace = false;

    /**
     * Creates a WatchService and registers the given directory
     */
    AbstractWatchService(Path dir, boolean recursive) throws IOException {
        this.watcher = FileSystems.getDefault().newWatchService();
        this.keys = new HashMap<>();
        this.recursive = recursive;

        if (recursive) {
            System.out.format("Scanning %s ...\n", dir);
            registerAll(dir);
            System.out.println("Done.");
        } else {
            register(dir);
        }

        // enable trace after initial registration
        this.trace = true;
    }

    private void register(Path dir) throws IOException {
        WatchKey key = dir.register(watcher,
                new WatchEvent.Kind[] {ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY},
                SensitivityWatchEventModifier.HIGH); // Sensitive high will update immediately !

        keys.put(key, dir);

    }

    private void registerAll(final Path start) throws IOException {
        // register directory and sub-directories
        Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
                    throws IOException
            {
                register(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    private void processEvents() {
        while (!stop) {
            // wait for key to be signalled
            WatchKey key;
            try {
                key = watcher.take();
            } catch (InterruptedException x) {
                continue;
            }

            Path dir = keys.get(key);
            if (null == dir) {
                // key not recognized !
                // TODO: What shall I do with this f*cking exception ?!
                System.err.println("Unknown WatchKey!");
                continue;
            }

            for (WatchEvent<?> watchEvent : key.pollEvents()) {
                WatchEvent.Kind kind = watchEvent.kind();

                if (kind == StandardWatchEventKinds.OVERFLOW) {
                    continue;
                }

                // Context for directory entry event is the file name of entry
                WatchEvent<Path> pathWatchEvent = (WatchEvent<Path>) watchEvent;
                // in case of kind=(create,delete,modify) the context is the relative path between
                // the directory that is registered and the entry which is created/deleted/modified.
                Path name = pathWatchEvent.context();
                // the child is the entry whic is created/deleted/modified.
                Path child = dir.resolve(name);

                if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
                    onEntryCreate(child, dir);
                    if (recursive && Files.isDirectory(child, LinkOption.NOFOLLOW_LINKS)) {
                        try {
                            registerAll(child);
                        } catch (IOException e) {
                            // TODO: What shall I do with this f*cking exception ?!
                            e.printStackTrace();
                        }
                    }

                } else if (kind == StandardWatchEventKinds.ENTRY_DELETE) {
                    onEntryDelete(child, dir);
                } else {
                    // we do not handle the modify event.
                }

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

    protected abstract void onEntryDelete(Path child, Path dir);

    protected abstract void onEntryCreate(Path child, Path dir);

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        processEvents();
        System.out.println("Watcher is dead now...");
    }

    public void requestStop() {
        stop = true;
        interrupt();
    }
}
