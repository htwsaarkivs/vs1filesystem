package htw.vs1.filesystem.Tests;

import htw.vs1.filesystem.FileSystem.*;
import htw.vs1.filesystem.FileSystem.exceptions.CouldNotCreateExeption;
import htw.vs1.filesystem.FileSystem.exceptions.ObjectNotFoundException;
import org.junit.Test;

import java.nio.file.FileAlreadyExistsException;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Test-Class to test the methods of {@link LocalFolder}.
 *
 * Created by markus on 03.06.15.
 */
public class LocalFolderTest {

    /**
     * Tests the method add(FSObject) of a {@link LocalFolder}.
     * If a File exists the tested method should throw a
     * exception when adding a file or a folder with the
     * same name.
     *
     * @throws Exception
     */
    @org.junit.Test
    public void testAdd() throws Exception {
        Folder inst = new LocalFolder("Test");

        String sameName = "TestOrdner";

        inst.add(new LocalFolder(sameName));
        try {
            inst.add(new LocalFolder(sameName));
            fail("add(Folder) should throw FileAlreadyExistsException, when trying to add a " +
                    "File with a duplicate Name in the current directory.");
        } catch (FileAlreadyExistsException e) {
            // fine, expected exception thrown by method.
        }


        try {
            inst.add(new LocalFile(sameName));
            fail("add(File) should throw FileAlreadyExistsException, when trying to add a " +
                    "Folder with a duplicate Name in the current directory.");
        } catch (FileAlreadyExistsException e) {
            // fine, expected exception thrown by method.
        }
    }

    @Test
    public void testAddCheckPrecondition() {
        Folder root = new LocalFolder("Test");

        try {
            root.add(new RemoteFile("remoteFile"));
            fail("Method add(FSObject) does not check precondition. It should not be allowed to add a RemoteFile.");
        } catch (IllegalArgumentException e) {
            // Fine, expected exception thrown by method.
        } catch (FileAlreadyExistsException e) {
            e.printStackTrace();
        } catch (CouldNotCreateExeption e) {
            fail("couldNotCreateException should not been thrown.");
        }


        try {
            root.add(new RemoteFolder("remoteFolder"));
            fail("Method add(FSObject) does not check precondition. It should not be allowed to add a RemoteFile.");
        } catch (IllegalArgumentException e) {
            // Fine, expected exception thrown by method.
        } catch (FileAlreadyExistsException e) {
            e.printStackTrace();
        } catch (CouldNotCreateExeption e) {
            fail("CouldNotCreateException should not been thrown.");
        }
    }


    @Test
    public void testGetParentFolder() throws Exception {
        Folder inst2 = new LocalFolder("Test2");
        Folder inst = new LocalFolder("Test");
        inst2.add(inst);

        //Wurzel muss null sein
        assertNull(inst2.getParentFolder());

        //Wurzel muss von Blatt erreichbar sein
        assertEquals(inst.getParentFolder(), inst2);
    }

    @Test
    public void testGetContent() throws Exception {
        Folder inst = new LocalFolder("Test");
        List<FSObject> ret = inst.getContent();

        ret.add(new LocalFile("malicious-file"));

        //Zugriff auf private-Attribut möglich?
        assertNotEquals(ret, inst.getContent());

    }

    @Test
    public void testGetObject() throws Exception {
        Folder inst = new LocalFolder("test");
        try {
            inst.getObject("a");
            fail("FSObjectNotFound Exception wurde nicht geworfen.");
        } catch(ObjectNotFoundException e) {
            //Alles hat geklappt
        }

        File testFile = new LocalFile("test");
        inst.add(testFile);

        assertEquals("Ausgegebene Datei ist nicht die, die eingegeben wurde.",
                inst.getObject("test"), testFile);

    }

}