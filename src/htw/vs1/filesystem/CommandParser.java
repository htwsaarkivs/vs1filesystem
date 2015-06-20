package htw.vs1.filesystem;

import java.util.LinkedList;

/**
 * Parses a Command-Argument-String.
 *
 * The words of the input string are divided
 * by whitespace except the word is included
 * in quotes.
 * The first word is the command. The other
 * words are the arguments.
 *
 * Created by felix on 20.06.15.
 */
public class CommandParser {


    /**
     * List of the words read.
     */
    private LinkedList<String> words = new LinkedList<>();

    /**
     * The word which is currently assembled.
     */
    private String currentWord = "";

    /**
     * The current char to evaluate.
     */
    private char currentChar;

    /**
     * {@code true}, iff the space is invalidated by
     * an opening quote.
     */
    private boolean quotationOpen = false;

    /**
     * Is the input already parsed?
     */
    boolean inputParsed = false;

    /**
     * Parses an input String.
     * Use {@link #getCommand()} to get its
     * command-string and {@link #getArgs()}
     * to get its arguments.
     *
     * @param string input string to parse.
     */
    public boolean parse(String string) {
        for (int i = 0; i < string.length(); i++) {
            currentChar = string.charAt(i);
            parseToken();
        }
        inputParsed = !quotationOpen;

        if (inputParsed) {
            addWord(currentWord);
        }

        return inputParsed;
    }

    private void addWord(String word) {
        if (word.isEmpty()) {
            return;
        }

        if (quotationOpen) {
            currentWord += currentChar;
        } else {
            words.add(currentWord);
            currentWord = "";
        }
    }

    private void parseToken() {
        switch (currentChar) {
            case ' ':
                addWord(currentWord);
                break;
            case '\"':
                quotationOpen = !quotationOpen;
                break;
            case '\n':
                break;
            default:
                currentWord += currentChar;
                break;
        }
    }

    /**
     * Gets the arguments of the input string.
     * They are divided by whitespace except they
     * are included in quotes.
     *
     * @return String array of the arguments.
     */
    public String[] getArgs() {
        String[] argsArray = new String[words.size()-1];
        boolean first = true;
        int i = 0;
        for (String arg : words) {
            if (first) {
                first = false;
            } else {
                argsArray[i] = arg;
            }
        }

        return argsArray;
    }

    /**
     * Returns the command String of the parsed
     * input.
     * The command is the first string of the input
     * divided by a whitespace from the rest of it.
     *
     * @return
     */
    public String getCommand() {
        if (!inputParsed) {
            throw new IllegalStateException("You must call the parse method before calling getCommand()");
        }

        if (words.isEmpty()) {
            // TODO: throw exception
        }

        return words.get(0);
    }

}
