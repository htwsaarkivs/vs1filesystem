package htw.vs1.filesystem.Tests;

import htw.vs1.filesystem.FileSystem.exceptions.InvalidFilenameException;
import htw.vs1.filesystem.FileSystem.virtual.LocalFile;
import htw.vs1.filesystem.Trials.Thread.FileSystemManager;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Myx1n on 19.06.2015.
 */
public class LocalFileTest {

    /**
     * Creates a file, delete it (so that the file does not exists) and then to delete a non-existing file.
     * (Nachfragen welche Exception geworfen wird)
     */
    @Test
    public void testDelete() throws Exception {
        LocalFile file = new LocalFile("Test.txt");
        file.delete();

        try {
            file.delete();
            fail("delete(File) should throw CouldNotDeleteException, when trying to delete a " +
                    "File that not exists.");
        } catch (Exception e) {
            if (FileSystemManager.DEBUG) {
                e.printStackTrace();
            }
            // Alles OK
        }
    }
    /**
     * Tests to rename a file with an invalid filename.
     */
    @Test
    public void testSetName() throws Exception {
        LocalFile file = new LocalFile("Test.txt");

        try {
            file.setName(".-/?");
            fail("delete(File) should throw InvalidFilenameException, when trying to setname of a " +
                    "File that already exists.");
        } catch (InvalidFilenameException e) {
            if (FileSystemManager.DEBUG) {
                e.printStackTrace();
            }
            // Alles OK
        }
    }

    @Test
    public void testGetParentFolder() throws Exception {

    }

    @Test
    public void testSetParentFolder() throws Exception {

       // root.setParentFolder();
    }

    @Test
    public void testGetPath() throws Exception {

    }

    @Test
    public void testSetPath() throws Exception {

    }

    @Test
    public void testDelete1() throws Exception {

    }

    @Test
    public void testCheckPrecondition() throws Exception {

    }

    @Test
    public void testSetName1() throws Exception {

    }

    @Test
    public void testGetName() throws Exception {

    }

    @Test
    public void testGetParentFolder1() throws Exception {

    }

    @Test
    public void testSetParentFolder1() throws Exception {

    }

    @Test
    public void testGetAbsolutePath() throws Exception {

    }

    @Test
    public void testToString() throws Exception {

    }
}