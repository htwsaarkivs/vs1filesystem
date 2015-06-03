package htw.vs1.filesystem;

import com.sun.istack.internal.NotNull;
import htw.vs1.filesystem.FileSystem.FileSystemInterface;
import htw.vs1.filesystem.FileSystem.Folder;
import htw.vs1.filesystem.FileSystem.LocalFolder;

public class Main {

    /**
     * Starting point in our programme.
     *
     * @param args name of the class, which is implementing the {@link FileSystemInterface}.
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            usage();
        }

        Folder root = new LocalFolder("root");
        FileSystemInterface fileSystem = instantiateFileSystem(args[0]);
        fileSystem.setWorkingDirectory(root);
        UserDialog dialog = new UserDialog(fileSystem);

        dialog.showDialog();
    }

    /**
     * Prints the programme usage.
     */
    private static void usage() {
        System.out.println("Usage:");
        System.out.println("Main <fileSystemClass>");
        System.exit(1);
    }

    /**
     * Instantiates the class, which is implementing the {@link FileSystemInterface}, identified by its name.
     *
     * @param className name of the class, which is implementing the {@link FileSystemInterface}.
     * @return implementation of the {@link FileSystemInterface}.
     */
    private static @NotNull FileSystemInterface instantiateFileSystem(String className) {
        String packageName = Main.class.getPackage().getName();
        int len = packageName.length();
        if (className.length() < len || !className.substring(0, len).equals(packageName)) {
            className = packageName + "." + className;
        }

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
        catch (ClassNotFoundException | InstantiationException e)
        {
            e.printStackTrace();
        } catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        System.exit(1);

        // never reached
        return null;
    }
}
