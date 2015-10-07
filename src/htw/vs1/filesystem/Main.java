package htw.vs1.filesystem;

import htw.vs1.filesystem.FileSystem.virtual.FileSystem;
import htw.vs1.filesystem.FileSystem.virtual.FileSystemInterface;
import htw.vs1.filesystem.FileSystem.virtual.LocalFolder;
import htw.vs1.filesystem.Network.TCPParallelServer;

public class Main {

    public static double VERSION = 0.1;

    /**
     * Starting point in our program.
     *
     * @param args name of the class, which is implementing the {@link FileSystemInterface}.
     */
    public static void main(String[] args) throws Exception {

       if (args.length != 1) {
           usage();
       }

        FileSystemManger.getInstance().init(args[0]);

        FileSystemInterface fileSystem = FileSystemManger.getInstance().getFileSystem(true);
        UserDialog dialog = new UserDialog(fileSystem);

        dialog.showDialog();

        FileSystemManger.getInstance().close();
    }

    /**
     * Prints the programme usage.
     */
    public static void usage() {
        System.out.println("Usage:");
        System.out.println("Main <path to working folder>");
        System.exit(1);
    }

    /**
     * // Maybe we need this method later again.
     *
     * Instantiates the class, which is implementing the {@link FileSystemInterface}, identified by its name.
     *
     * @param className name of the class, which is implementing the {@link FileSystemInterface}.
     * @return implementation of the {@link FileSystemInterface}.
     */
    /*private static @NotNull FileSystemInterface instantiateFileSystem(String className) {
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
            if (FileSystemManager.DEBUG) {
                e.printStackTrace();
            }
        } catch (IllegalAccessException e)
        {
            if (FileSystemManager.DEBUG) {
                e.printStackTrace();
            }
        }
        System.exit(1);

        // never reached
        return null;
    }*/
}
