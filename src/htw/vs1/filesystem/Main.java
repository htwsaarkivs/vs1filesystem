package htw.vs1.filesystem;

import FileSystem.FileSystemInterface;
import FileSystem.Folder;
import FileSystem.LocalFolder;
import FileSystem.*;

import java.util.HashSet;

public class Main {

    public static void main(String[] args) {
        if (args.length != 1) {
            usage();
        }

        Folder root = new LocalFolder("root");
        FileSystemInterface fileSystem = instantiateFileSystem(args[0]);
        fileSystem.setWorkingFolder(root);
        UserDialog dialog = new UserDialog(fileSystem);

        dialog.showDialog();
    }

    private static void usage() {
        System.out.println("Usage:");
        System.out.println("Main <fileSystemClass>");
        System.exit(1);
    }

    private static FileSystemInterface instantiateFileSystem(String className) {

        try
        {
            Class<?>	fileSystemClass = Class.forName(className);

            if (! FileSystemInterface.class.isAssignableFrom(fileSystemClass))
            {
                System.out.println(className + " does not implement " + FileSystemInterface.class.getName());
                System.exit(1);
            }

            System.out.println("using file system: " + className);

            return (FileSystemInterface) fileSystemClass.newInstance();

        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (InstantiationException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        System.exit(1);

        // never reached
        return null;
    }
}
