package htw.vs1.filesystem.Network.Protocol.Commands;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by markus on 12.06.15.
 */
public class CommandFactory {


    private HashMap<String, Command> commands = new HashMap<String, Command>();


    public CommandFactory() {
        loadCommands();
    }

    /**
     * Initialize the command HashMap
     */
    private void loadCommands() {
        commands.put(GETFEAT.COMMAND_STRING, new GETFEAT());
        commands.put(SETPASS.COMMAND_STRING, new SETPASS());
        commands.put(SETUSER.COMMAND_STRING, new SETUSER());
        commands.put(LS.COMMAND_STRING, new LS());
        commands.put(CD.COMMAND_STRING, new CD());
        commands.put(PWD.COMMAND_STRING, new PWD());
        commands.put(COWBOY.COMMAND_STRING, new COWBOY());
    }

    /**
     * Returns a Set containing all registered commands.
     * @return
     */
    public Set<String> getRegisteredCommands() {
        return commands.keySet();
    }

    /**
     * Gets a Command-instance from the internal HashTable of this Object.
     * This method will always return the very same instance of a the corresponding command class.
     * A @UnsupportedCommand command will be returned, if the supplied command-String does not exists in the internal HashTable.
     * @param command
     * @return
     */
    public Command getCommand(String command) {
        if (commands.containsKey(command)) {
            return commands.get(command);
        } else {
            return new UnsupportedCommand();
        }
    }


}
