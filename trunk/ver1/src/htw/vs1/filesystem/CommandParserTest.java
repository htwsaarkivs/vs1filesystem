package htw.vs1.filesystem;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by felix on 26.06.15.
 */
public class CommandParserTest {

    CommandParser parser;

    @Before
    public void setUp() throws Exception {
        parser = new CommandParser();
    }

    @After
    public void tearDown() throws Exception {
        parser = null;
    }

    @Test
    public void testParse() throws Exception {

    }

    @Test
    public void testGetArgs() throws Exception {
        parser.parse("cmd arg1 arg2");

        assertEquals("Command string is not correct.", "cmd", parser.getCommand());
        assertEquals("Argument 1 is not correct." , "arg1", parser.getArgs()[0]);
        assertEquals("Argument 2 is not correct." , "arg2", parser.getArgs()[1]);
    }

    @Test
    public void testGetCommand() throws Exception {

    }
}