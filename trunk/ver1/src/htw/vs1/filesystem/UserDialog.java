package htw.vs1.filesystem;

import com.sun.istack.internal.Nullable;
import htw.vs1.filesystem.FileSystem.exceptions.*;
import htw.vs1.filesystem.FileSystem.physical.PhysicalFileSystemAdapter;
import htw.vs1.filesystem.FileSystem.virtual.FSObject;
import htw.vs1.filesystem.FileSystem.virtual.FileSystemInterface;
import htw.vs1.filesystem.Network.Discovery.FileSystemServer;
import htw.vs1.filesystem.Network.TCPParallelServer;

import java.util.Collection;
import java.util.Scanner;

/**
 * UserDialog to interact with a user via the command line.
 *
 * Created by felix on 03.06.15.
 */
public class
        UserDialog {

    // FIXME: Error-Handling if the user is an idiot !!

    /**
     * Line seperator depending on the os.
     */
    public static final String NEW_LINE = System.getProperty("line.separator");

    /**
     * Enum representing a command for our filesystem. Each command has a String
     * representation, which is used to create the
     * {@link htw.vs1.filesystem.UserDialog.Command} with the static method
     * {@link htw.vs1.filesystem.UserDialog.Command#fromString(String, String...)}.
     */
    private enum Command {

        START_SERVER("start_server"),
        STOP_SERVER("stop_server"),
        LIST_SERVERS("list_servers"),
        LS("ls"),
        CD("cd"),
        PWD("pwd"),
        MKDIR("mkdir"),
        RENAME("rename"),
        DELETE("delete"),
        TOUCH("touch"),
        SEARCH("search"),
        MOUNT("mount"),
        EXIT("exit"),
        UNKNOWN("unknown");

        private static final String VAL_START_SERVER = "start_server";
        private static final String VAL_STOP_SERVER = "stop_server";
        private static final String VAL_LIST_SERVERS = "list_servers";
        private static final String VAL_LS = "ls";
        private static final String VAL_CD = "cd";
        private static final String VAL_PWD = "pwd";
        private static final String VAL_RENAME = "rename";
        private static final String VAL_DELETE = "delete";
        private static final String VAL_MKDIR = "mkdir";
        private static final String VAL_TOUCH = "touch";
        private static final String VAL_SEARCH = "search";
        private static final String VAL_MOUNT = "mount";
        private static final String VAL_EXIT = "exit";
        
        /**
         * Parameters associated with the current command.
         */
        private String[] params;

        /**
         * String representation of the current command, may be {@code null}.
         */
        private final String cmdText;

        /**
         * Constructor to associate the
         * {@link htw.vs1.filesystem.UserDialog.Command} with its String
         * representation.
         *
         * @param command String representation of the
         * {@link htw.vs1.filesystem.UserDialog.Command}.
         */
        Command(String command) {
            this.cmdText = command;
        }

        /**
         * Create a new {@link htw.vs1.filesystem.UserDialog.Command} identified
         * by its String representation.
         *
         * @param command String representation of the command.
         * @param params parameters associated with the current command, may be
         * {@code null}.
         * @return associated {@link htw.vs1.filesystem.UserDialog.Command}
         * identified by its String representation.
         */
        public static Command fromString(String command, @Nullable String... params) {
            Command cmd;
            switch (command) {
                case VAL_START_SERVER:
                    cmd = Command.START_SERVER;
                    break;
                case VAL_STOP_SERVER:
                    cmd = Command.STOP_SERVER;
                    break;
                case VAL_LIST_SERVERS:
                    cmd = Command.LIST_SERVERS;
                    break;
                case VAL_LS:
                    cmd = Command.LS;
                    break;
                case VAL_CD:
                    cmd = Command.CD;
                    break;
                case VAL_PWD:
                    cmd = Command.PWD;
                    break;
                case VAL_MKDIR:
                    cmd = Command.MKDIR;
                    break;
                case VAL_TOUCH:
                    cmd = Command.TOUCH;
                    break;
                case VAL_SEARCH:
                    cmd = Command.SEARCH;
                    break;
                case VAL_EXIT:
                    cmd = Command.EXIT;
                    break;
                case VAL_RENAME:
                    cmd = Command.RENAME;
                    break;
                case VAL_DELETE:
                    cmd = Command.DELETE;
                    break;
                case VAL_MOUNT:
                    cmd = Command.MOUNT;
                    break;
                default:
                    cmd = UNKNOWN;
            }

            cmd.setParams(params);
            return cmd;
        }

        /**
         * Checks whether the current
         * {@link htw.vs1.filesystem.UserDialog.Command} has associated
         * parameters.
         *
         * @return {@code true}, iff the current
         * {@link htw.vs1.filesystem.UserDialog.Command} has parameters.
         */
        public boolean hasParams() {
            return (params != null) && (params.length > 0);
        }

        /**
         * Gets the associated parameters.
         *
         * @return the associated parameters, iff there are any.
         */
        public String[] getParams() {
            return params;
        }

        @Override
        public String toString() {
            return cmdText;
        }

        /**
         * Associates parameters to the current
         * {@link htw.vs1.filesystem.UserDialog.Command}
         *
         * @param params associated parameters.
         */
        public void setParams(String[] params) {
            this.params = params;
        }
    }

    /**
     * The current {@link FileSystemInterface} the {@link UserDialog} is working
     * on.
     */
    private FileSystemInterface fileSystem;

    /**
     * Constructor for the {@link UserDialog} associating a concrete
     * {@link FileSystemInterface}.
     *
     * @param fileSystem concrete {@link FileSystemInterface} this
     * {@link UserDialog} is working on.
     */
    public UserDialog(FileSystemInterface fileSystem) {
        this.fileSystem = fileSystem;
    }

    public void showDialog() {
        while (true) {
            Command command = promptForCommand();

            boolean goon = true;
            try {
                goon = executeCommand(command);
            }catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                e.printStackTrace();
            }

            if (!goon) {
                break;
            }
        }

        PhysicalFileSystemAdapter.getInstance().stopWatchService();
    }

    /**
     * Executes a given command.
     *
     * @param command {@link UserDialog.Command} to declare which command should
     * be executed.
     * @return {@code false}, iff the user wants to exit this dialog.
     */
    private boolean executeCommand(Command command) throws FileSystemException {
        switch (command) {
            case START_SERVER:
                TCPParallelServer.getInstance().start();
                break;
            case STOP_SERVER:
                TCPParallelServer.getInstance().stopServer();
                break;
            case LIST_SERVERS:
                Collection<FileSystemServer> servers = fileSystem.listAvailableFileSystemServers();
                if (servers.isEmpty()) {
                    System.out.println("No file system servers available.");
                } else {
                    servers.forEach(System.out::println);
                }
                System.out.print(NEW_LINE);
                break;
            case LS:
                String content = FSObject.printFSObjectList(fileSystem.listDirectoryContent(), false);

                System.out.print(content);
                System.out.print(NEW_LINE);
                break;
            case MKDIR:
                String folderName;
                if (command.hasParams() && command.getParams().length == 1) {
                    folderName = command.getParams()[0];
                } else {
                    // TODO: print usage
                    break;
                }

                fileSystem.createFSObject(folderName, true);

                break;
            case TOUCH:
                String fileName;
                if (command.hasParams() && command.getParams().length == 1) {
                    fileName = command.getParams()[0];
                } else {
                    // TODO: print usage
                    break;

                }

                fileSystem.createFSObject(fileName, false);
                break;
            case CD:
                String cdParam;
                if (command.hasParams() && command.getParams().length == 1) {
                    cdParam = command.getParams()[0];
                } else {
                    // TODO: print usage
                    break;
                }
                fileSystem.changeDirectory(cdParam);
                break;

            case PWD:
                String workingDirectory = fileSystem.printWorkingDirectory();
                System.out.print(workingDirectory);
                System.out.print(NEW_LINE);
                break;
            case SEARCH:
                String searchObject;
                 if (command.hasParams() && command.getParams().length == 1) {
                    searchObject = command.getParams()[0];
                } else {
                    // TODO: print usage
                    break;
                }

                System.out.print(FSObject.printFSObjectList(fileSystem.search(searchObject), true));
                break;

            case EXIT:
                executeCommand(Command.STOP_SERVER);
                return false;
            case RENAME:
                String oldName;
                String newName;
                if(command.hasParams() && command.getParams().length == 2) {
                    oldName = command.getParams()[0];
                    newName = command.getParams()[1];
                    fileSystem.rename(oldName, newName);
                }
                else{
                 //TODO: print usage
                    }
                break;
            case DELETE: {
                String name;
                if (command.hasParams() && command.getParams().length == 1) {
                    name = command.getParams()[0];
                    fileSystem.delete(name);
                }
                break;
            }
            case MOUNT: {
                String remoteFolderName = null;
                String remoteIP = null;
                int remotePort = -1;
                String user = TCPParallelServer.DEFAULT_USER;
                String pass = TCPParallelServer.DEFAULT_PASS;
                boolean printUsage = true;
                if (command.hasParams()) {
                    if (command.getParams().length == 3 || command.getParams().length == 5) {
                        remoteFolderName = command.getParams()[0];
                        remoteIP = command.getParams()[1];
                        String remotePortStr = command.getParams()[2];
                        try {
                            remotePort = Integer.parseInt(remotePortStr);
                            printUsage = false;
                        } catch (NumberFormatException e) {
                            printUsage = true;
                        }
                    }
                    if (command.getParams().length == 5) {
                        user = command.getParams()[3];
                        pass = command.getParams()[4];
                    }
                }
                if (printUsage) {
                    // TODO: print usage;
                    break;
                }

                fileSystem.mount(remoteFolderName, remoteIP, remotePort, user, pass);

                break;
            }
            case UNKNOWN:
                // TODO: print usage
                throw new UnsupportedOperationException("Error message for unknown parameter not implemented");
        }

        return true;
    }

    /**
     * <p>Prompts the user to input a command with its parameters.
     * The command will be executed by the next newline token.</p>
     * <p>Format of the user input: &lt;command&gt; [parameters...]</p>
     * <p>
     *     Command and parameters are divided by whitespace.<br>
     *     Parameters with newline may be quoted by quotation marks.
     * </p>
     *
     * @return {@link htw.vs1.filesystem.UserDialog.Command} entered by the user.
     */
    private Command promptForCommand() {

        // create a scanner so we can read the command-line input
        Scanner scanner = new Scanner(System.in);

        // prompt user for input
        System.out.println("Enter command: ");

        CommandParser parser = new CommandParser();

        boolean inputParsed = false;
        while (!inputParsed) {
            //  prompt for the user's name
            String commandWithParams =  scanner.nextLine();

            inputParsed = parser.parse(commandWithParams);
        }


        return Command.fromString(parser.getCommand(), parser.getArgs());
    }

}
