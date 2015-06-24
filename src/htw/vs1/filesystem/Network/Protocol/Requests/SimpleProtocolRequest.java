package htw.vs1.filesystem.Network.Protocol.Requests;

import htw.vs1.filesystem.CommandParser;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by markus on 13.06.15.
 */
public class SimpleProtocolRequest implements Request {

    public String requestLine;
    public String commandString;
    public List<String> arguments = new LinkedList<String>();

    public SimpleProtocolRequest(String requestLine) {
        this.requestLine = requestLine;
        this.parse();
    }

    /**
     * Analyzes the String passed to this Object during Construction
     */
    private void parse() {
        CommandParser parser = new CommandParser();
        if (!parser.parse(requestLine)) {
            // TODO: fehler
        }
        this.commandString = parser.getCommand();
        this.arguments.addAll(Arrays.asList(parser.getArgs()));

        /*String[] tokens = this.requestLine.split(" ");
            this.commandString = tokens[0];
            for (int i = 1; i < tokens.length; i++) {
                this.arguments.add(tokens[i]);
            }*/

    }

    /**
     * Returns the Command String
     * Warning: May be Empty, when an empty command has been isseud.
     * @return
     */
    public String getCommandString() {
        return this.commandString;
    }

    /**
     * Tells you whether the amazingly awesome Command you have just read from the client actually contains some nice arguments or not.
     * Who the hell is even reading this idiotic and unnecessary JavaDoc Comment. It's just taking up a whole lot of memory in our repository.
     * @return
     */
    public boolean hasArguments() {
        return !this.arguments.isEmpty();
    }

    /**
     * Tells you how many arguments were appended to the Command
     * @return
     */
    public int numOfArguments() {
        return this.arguments.size();
    }

    /**
     * May or not give you a complete list of all arguments. Please use @hasArguments to see if there's actually anything in here!
     * @return
     */
    public List<String> getArguments() {
        return this.arguments;
    }

    @Override
    public String toString() {
        return "[ " + this.commandString + " | " + getArguments().toString() + " ]";
    }
}
