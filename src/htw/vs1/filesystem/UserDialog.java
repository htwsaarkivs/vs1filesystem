package htw.vs1.filesystem;

import com.sun.istack.internal.Nullable;
import htw.vs1.filesystem.FileSystem.FSObject;
import htw.vs1.filesystem.FileSystem.FileSystemInterface;
import htw.vs1.filesystem.FileSystem.LocalFile;
import htw.vs1.filesystem.FileSystem.LocalFolder;
import htw.vs1.filesystem.FileSystem.exceptions.FSObjectNotFoundException;
import java.nio.file.FileAlreadyExistsException;

import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * UserDialog to interact with a user via the command line.
 *
 * Created by felix on 03.06.15.
 */
public class UserDialog {

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

        LS("ls"),
        CD("cd"),
        PWD("pwd"),
        MKDIR("mkdir"),
        TOUCH("touch"),
        SEARCH("search"),
        EXIT("exit"),
        UNKNOWN("unknown");

        private static final String VAL_LS = "ls";
        private static final String VAL_CD = "cd";
        private static final String VAL_PWD = "pwd";
        private static final String VAL_MKDIR = "mkdir";
        private static final String VAL_TOUCH = "touch";
        private static final String VAL_SEARCH = "search";
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

            boolean goon = executeCommand(command);

            if (!goon) {
                break;
            }
        }
    }

    /**
     * Executes a given command.
     *
     * @param command {@link UserDialog.Command} to declare which command should
     * be executed.
     * @return {@code false}, iff the user wants to exit this dialog.
     */
    private boolean executeCommand(Command command) {
        switch (command) {
            case LS:
                String content = fileSystem.listDirectoryContent();
                System.out.print(content);
                System.out.print(NEW_LINE);
                break;
            case MKDIR:
                //TODO: Exception Ordner und Datei im selben Verzeichnis dürfen nicht den gleichen Namen tragen
                String folderName;
                if (command.hasParams() && command.getParams().length == 1) {
                    folderName = command.getParams()[0];
                } else {
                    // TODO: error message.
                    break;

                }

                LocalFolder folder = new LocalFolder(folderName);

                try {
                    fileSystem.getWorkingDirectory().add(folder);
                } catch (FileAlreadyExistsException ex) {
                    // TODO: eroor message
                }

                break;
            case TOUCH:
                //TODO: Exception Ordner und Datei im selben Verzeichnis dürfen nicht den gleichen Namen tragen
                String fileName;
                if (command.hasParams() && command.getParams().length == 1) {
                    fileName = command.getParams()[0];
                } else {
                    // TODO: error message.
                    break;

                }

                LocalFile file = new LocalFile(fileName);

                try {
                    fileSystem.getWorkingDirectory().add(file);
                } catch (FileAlreadyExistsException ex) {
                    // TODO: eroor message
                }
                break;
            case CD:
                String cdParam;
                if (command.hasParams() && command.getParams().length == 1) {
                    cdParam = command.getParams()[0];
                } else {
                    // TODO: error message.
                    break;
                }
                try {
                    fileSystem.changeDirectory(cdParam);
                } catch (FSObjectNotFoundException e) {
                    // TODO: error message.
                }
                break;

            case PWD:
                String workingDirectory = fileSystem.printWorkingDirectory();
                System.out.print(workingDirectory);
                System.out.print(NEW_LINE);
                break;
            case SEARCH:
                String searchObject;
                String typ = "";
                String path;
                 if (command.hasParams() && command.getParams().length == 1) {
                    searchObject = command.getParams()[0];
                } else {
                    // TODO: error message.
                    break;
                }
            
               
                for (FSObject object : fileSystem.getWorkingDirectory().search(searchObject)) {
                    if (object instanceof LocalFile) {
                        typ = " (File) ";
                    } else if (object instanceof LocalFolder) {
                        typ = " (Folder) ";
                    } else {
                        //throw exception;
                    }
                     System.out.println(searchObject + typ + "path");
                }
                
        break;

    
    case EXIT:
                return false;

            case UNKNOWN:
                // TODO: Error message.
                break;
        }


return true;
    }

    /**
     * <p>Prompts the user to input a command with its parameters.
     * The command will be executed by the next newline token.</p>
     * <p>Format of the user input: &lt;command&gt; [parameters...]</p>
     * <p>Command and parameters are divided by whitespace.</p>
     *
     * @return {@link htw.vs1.filesystem.UserDialog.Command} entered by the user.
     */
    private Command promptForCommand() {
        String command = null;
        String[] params = null;

        // create a scanner so we can read the command-line input
        Scanner scanner = new Scanner(System.in);

        // prompt user for input
        System.out.println("Enter command: ");

        //  prompt for the user's name
        String commandWithParams =  scanner.nextLine().trim();


        // split command by whitespace
        String[] commandArray = commandWithParams.split("\\s+");

        if (commandArray.length > 0) {
            command = commandArray[0];
            if (commandArray.length > 1) {
                params = Arrays.copyOfRange(commandArray, 1, commandArray.length);
            }
        }

        return Command.fromString(command, params);
    }

}
