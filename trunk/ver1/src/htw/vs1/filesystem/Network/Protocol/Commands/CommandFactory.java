package htw.vs1.filesystem.Network.Protocol.Commands;

import java.util.HashMap;

/**
 * Created by markus on 12.06.15.
 */
public class CommandFactory {


    private HashMap<String, Command> commands = new HashMap<String, Command>();


    public CommandFactory() {
        loadCommands();
    }


    private void loadCommands() {
        commands.put(GETFEAT.COMMAND_STRING, new GETFEAT());
        commands.put(SETPASS.COMMAND_STRING, new SETPASS());
        commands.put(SETUSER.COMMAND_STRING, new SETUSER());
    }


    public Command getCommand(String command) {
        if (commands.containsKey(command)) {
            return commands.get(command);
        } else {
            return new UnsupportedCommand();
        }
    }


}
