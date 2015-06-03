package Tests;

import FileSystem.FSObject;
import FileSystem.Folder;
import FileSystem.LocalFolder;
import FileSystem.RemoteFile;

import static org.junit.Assert.*;

/**
 * Created by markus on 03.06.15.
 */
public class LocalFolderTest {

    @org.junit.Test
    public void testAdd1() throws Exception {
        Folder inst = new LocalFolder("Test");

        inst.add(new LocalFolder("TestOrdner"));
        try {
            inst.add(new LocalFolder("TestOrdner"));
            fail();
        } catch (Throwable e) {
            return;
        }
    }
}