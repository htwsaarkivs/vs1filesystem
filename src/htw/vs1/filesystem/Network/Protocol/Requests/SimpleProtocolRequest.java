package htw.vs1.filesystem.Network.Protocol.Requests;

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

    private void parse() {
        String[] tokens = this.requestLine.split(" ");
            this.commandString = tokens[0];
            for (int i = 1; i < tokens.length; i++) {
                this.arguments.add(tokens[i]);
            }

    }

    public String getCommandString() {
        return this.commandString;
    }

    public boolean hasArguments() {
        return !this.arguments.isEmpty();
    }

    public int numOfArguments() {
        return this.arguments.size();
    }

    public List<String> getArguments() {
        return this.arguments;
    }

    @Override
    public String toString() {
        return this.commandString;
    }
}
