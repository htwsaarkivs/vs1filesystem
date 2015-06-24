package htw.vs1.filesystem.Tests;

import htw.vs1.filesystem.CommandParser;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by markus on 23.06.15.
 */
public class CommandParserTest {


    private CommandParser test;
    @Before
    public void setUp() throws Exception {
        test = new CommandParser();
    }

    @Test
    public void testEmptyCommand() throws Exception {
        try {
            test.parse("");
            assertEquals("getCommand should return empty String",test.getCommand(), "");
            assertEquals("getArgs should return empty String Array",test.getArgs().length, 0);
        } catch(Exception e) {
            fail("Empty String should not throw an exception");
        }
    }
    @Test
    public void testGetArgs() throws Exception {

    }

    @Test
    public void testGetCommand() throws Exception {

    }
}