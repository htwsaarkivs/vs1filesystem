package htw.vs1.filesystem;

import com.sun.javaws.exceptions.InvalidArgumentException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by felix on 20.06.15.
 */
public class CommandParser {

    /**
     * The command of the input line.
     * This is the first string of the input divided
     * with a whitespace from the rest of it.
     */

    private LinkedList<String> args = new LinkedList<>();

    private String currentWord = "";

    private char currentChar;

    private boolean spaceInvalidated = false;

    boolean inputParsed = false;

    public void parse(String string) {
        for (int i = 0; i < string.length(); i++) {
            currentChar = string.charAt(i);
            parseToken();
        }
        addWord(currentWord);
        inputParsed = true;
    }

    private void addWord(String word) {
        if (word.isEmpty()) {
            return;
        }

        if (spaceInvalidated) {
            currentWord += currentChar;
        } else {
            args.add(currentWord);
            currentWord = "";
        }
    }

    private void parseToken() {
        switch (currentChar) {
            case ' ':
                addWord(currentWord);
                break;
            case '\"':
                spaceInvalidated = !spaceInvalidated;
                break;
            default:
                currentWord += currentChar;
                break;
        }
    }

    public String[] getArgs() {
        String[] argsArray = new String[args.size()-1];
        boolean first = true;
        int i = 0;
        for (String arg : args) {
            if (first) {
                first = false;
            } else {
                argsArray[i] = arg;
            }
        }

        return argsArray;
    }

    public String getCommand() {
        if (!inputParsed) {
            throw new IllegalStateException("You must call the parse method before calling getCommand()");
        }

        if (args.isEmpty()) {
            // TODO: throw exception
        }

        return args.get(0);
    }

}
