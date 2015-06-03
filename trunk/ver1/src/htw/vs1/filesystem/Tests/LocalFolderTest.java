package htw.vs1.filesystem.Tests;

import htw.vs1.filesystem.FileSystem.*;
import htw.vs1.filesystem.FileSystem.exceptions.FSObjectNotFoundException;
import org.junit.Test;

import java.nio.file.FileAlreadyExistsException;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by markus on 03.06.15.
 */
public class LocalFolderTest {

    @org.junit.Test
    public void testAdd() throws Exception {
        Folder inst = new LocalFolder("Test");

        inst.add(new LocalFolder("TestOrdner"));
        try {
            inst.add(new LocalFolder("TestOrdner"));
            fail("#add(Folder) should throw FileAlreadyExistsExeption.");
        } catch (FileAlreadyExistsException e) {
            // fine, expected exception thrown by method.
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
        } catch(FSObjectNotFoundException e) {
            //Alles hat geklappt
        }

        File testFile = new LocalFile("test");
        inst.add(testFile);

        assertEquals(inst.getObject("test"), testFile);

    }

}