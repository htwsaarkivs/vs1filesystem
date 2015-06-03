import FileSystem.FileSystemInterface;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by felix on 03.06.15.
 */
public class UserDialog {

    // FIXME: Error-Handling if the user is an idiot !!

    /**
     * Line seperator depending on the os.
     */
    public static final String NEW_LINE = System.getProperty("line.separator");

    private enum Command {
        LS("ls"),
        CD("cd"),
        PWD("pwd"),
        EXIT("exit"),
        UNKNOWN("unknown");

        private static final String VAL_LS = "ls";
        private static final String VAL_CD = "cd";
        private static final String VAL_PWD = "pwd";
        private static final String VAL_EXIT = "exit";
        private static final String VAL_UNKNOWN = "unknown";

        private String[] params;

        private final String cmdText;

        Command(String command) {
            this.cmdText = command;
        }

        public static Command fromString(String command, String... params) {
            Command cmd;
            switch (command) {
                case VAL_LS:
                    cmd = Command.LS;
                    break;
                case VAL_CD:
                    cmd = Command.CD;
                    break;
                case VAL_PWD:
                    cmd =  Command.PWD;
                    break;
                case VAL_EXIT:
                    cmd = Command.EXIT;
                    break;
                case VAL_UNKNOWN:
                    cmd = UNKNOWN;
                    break;
                default:
                    throw new IllegalArgumentException("Cannot create Command from String: " + command);
            }

            cmd.setParams(params);
            return cmd;
        }

        public boolean hasParams() {
            return (params != null) && (params.length > 0);
        }

        public String[] getParams() {
            return params;
        }

        @Override
        public String toString() {
            return cmdText;
        }

        public void setParams(String[] params) {
            this.params = params;
        }
    }

    private FileSystemInterface fileSystem;

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
     * @param command {@link UserDialog.Command} to declare which command should be executed.
     * @return {@code false}, iff the user want to commit.
     */
    private boolean executeCommand(Command command) {
        switch (command) {
            case LS:
                String content = fileSystem.listDirectoryContent();
                System.out.print(content);
                System.out.print(NEW_LINE);
                break;

            case CD:
                String cdParam;
                if (command.hasParams() && command.getParams().length == 1) {
                    cdParam = command.getParams()[0];
                } else {
                    throw new IllegalArgumentException("False parameter....");
                }
                fileSystem.changeDirectory(cdParam);
                break;

            case PWD:
                String workingDirectory = fileSystem.printWorkingDirectory();
                System.out.print(workingDirectory);
                System.out.print(NEW_LINE);
                break;
            case EXIT:
                return false;
        }

        return true;
    }

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

        if (commandArray != null && commandArray.length > 0) {
            command = commandArray[0];
            if (commandArray.length > 1) {
                params = Arrays.copyOfRange(commandArray, 1, commandArray.length);
            }
        }

        return Command.fromString(command, params);
    }

}
