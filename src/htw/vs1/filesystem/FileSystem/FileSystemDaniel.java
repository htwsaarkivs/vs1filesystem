/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package htw.vs1.filesystem.FileSystem;

import com.sun.istack.internal.NotNull;
import htw.vs1.filesystem.FileSystem.exceptions.FSObjectNotFoundException;

/**
 *
 * @author Myx1n
 */
public class FileSystemDaniel implements FileSystemInterface {

    private Folder workingFolder = null;

    @Override
    public void setWorkingDirectory(Folder workingFolder) {
        this.workingFolder = workingFolder;
    }

    @Override
    public Folder getWorkingDirectory() {
        return workingFolder;
    }

    @Override
    public void changeDirectory(String name) throws FSObjectNotFoundException {

        FSObject o = null;

        if (name.equals("..")) {
            o = workingFolder.getParentFolder();
        } else {
            o = workingFolder.getObject(name);
        }

        if (o instanceof Folder) {
            workingFolder = (Folder) o;

        } else {
            throw new FSObjectNotFoundException();
        }

    }

    @Override
    public String listDirectoryContent() {
        StringBuilder b = new StringBuilder();

        boolean first = true;
        for (FSObject object : workingFolder.getContent()) {
            if (first) {
                first = false;
            } else {
                b.append(", ");
            }
            b.append(object.toString());
        }
        return b.toString();
    }

    @Override
    public String printWorkingDirectory() {
        return workingFolder.toString();
    }


    public void createFile(String name) {
        
    }

    @Override
    public void rename(@NotNull String name, String newName) {
        throw new UnsupportedOperationException("Rename not supported in this FileSystem");
    }

    @Override
    public void delete(@NotNull String name) {
        throw new UnsupportedOperationException("Delete not supported in this FileSystem");
    }

    

}
