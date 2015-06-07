package htw.vs1.filesystem.Tests;

import htw.vs1.filesystem.FileSystem.*;
import htw.vs1.filesystem.FileSystem.exceptions.FSObjectNotFoundException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by markus on 04.06.15.
 */
public class FileSystemInterfaceTest {


    private FileSystemInterface fs;
    private Folder reference;

    @Before
    public void setUp() throws Exception {

        //Hier die eigene Implementierung vermerken!
        this.fs = new MarkusFileSystem();

        Folder root = new LocalFolder("root");
        root.add(new LocalFile("datei1"));
        root.add(new LocalFile("datei2"));

        Folder subFolder = new LocalFolder("sub");
        subFolder.add(new LocalFolder("subsub"));
        subFolder.add(new LocalFile("datei1"));
        subFolder.add(new LocalFile("datei2"));

        root.add(subFolder);
        this.reference = root;

        this.fs.setWorkingDirectory(root);

    }

    @After
    public void tearDown() throws Exception {
        this.fs = null;
    }

    @Test
    public void testSetWorkingDirectory() throws Exception {

    }

    @Test
    public void testGetWorkingDirectory() throws Exception {

    }

    @Test
    public void testChangeDirectory() throws Exception {

        try {
            //Ordner existiert nicht
            this.fs.changeDirectory("does-not-exist");
            fail("Ordner exisitiert nicht. Erwartete Exception FSObjectNotFoundException wurde nicht geworfen.");
        } catch (FSObjectNotFoundException e) {
            //Alles in Ordnung
        }


        try {
            //Übergebener Name ist eine Datei
            this.fs.changeDirectory("datei1");
            fail("Übergebener Name gehört zu einer Datei. Erwartete Exception FSObjectNotFoundException wurde nicht geworfen.");
        } catch (FSObjectNotFoundException e) {
            //.. Alles in Ordnung
        }

        String subFolder = "sub";
        this.fs.changeDirectory(subFolder);
        //In korrekten Unterordner gewechselt?
        assertEquals(this.fs.getWorkingDirectory().getName(), subFolder);


    }

    @Test
    public void testListDirectoryContent() throws Exception {
        String out = this.fs.listDirectoryContent();
        for(FSObject el: this.reference.getContent()) {
            if(!out.contains(el.getName())) fail("List Directory gibt nicht alle Dateinamen aus, die vorhanden sind.");
        }
    }

    @Test
    public void testPrintWorkingDirectory() throws Exception {
        this.fs.changeDirectory("sub");
        this.fs.changeDirectory("subsub");
        String out = this.fs.printWorkingDirectory();

        assertEquals("/root/sub/subsub", out);

    }
}