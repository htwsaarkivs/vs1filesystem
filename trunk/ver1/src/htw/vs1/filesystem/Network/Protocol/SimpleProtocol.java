package htw.vs1.filesystem.Network.Protocol;


import htw.vs1.filesystem.Network.Protocol.Exceptions.SimpleProtocolFatalError;
import htw.vs1.filesystem.Network.Protocol.Exceptions.SimpleProtocolInitializationErrorException;

import htw.vs1.filesystem.Network.Protocol.State.SimpleProtocolState;
import htw.vs1.filesystem.Network.Protocol.State.State;

import java.io.*;
import java.net.Socket;

/**
 * Base Protocol class which provides methods to read the
 * next line, get the current line and write a line to
 * the communication partner.
 *
 * Created by markus on 11.06.15.
 */
public abstract class SimpleProtocol implements Protocol {

    /**
     * {@link BufferedReader} to read from the socket.
     */
    private final BufferedReader bufferedReader;

    /**
     * {@link OutputStream} to write on the socket.
     */
    private OutputStream output;

    /**
     * Current line read from the socket.
     */
    private String currentLine;


    /*
     * Der RequestStack hat im Protokoll (Klasse) nichts verloren, da er keine Eigenschaft des Protokolls darstellt.
     * Er ist viel mehr ein Hilfskonstrukt, welches es bestimmten Kommandos erlaubt auf vorherige Request zurückzugreifen.
     * Auch würde eine solche Konstruktion die Veränderung des Stacks aus Kommandos herraus erlauben, da im Protokoll
     * zwangsläufig eine pushRequestStack-Methode vorhanden sein muss.
     * Durch Programmierfehler, könnte man so einfacher Lücken im Protokoll ausnutzen.
     */

    /**
     * Current protocl state.
     */
    private State state = SimpleProtocolState.IDLE;

    /**
     * Creates a new SimpleProtocol instance.
     * @param socket Reference to a ServerSocket
     * @throws SimpleProtocolInitializationErrorException
     */
    public SimpleProtocol(Socket socket) throws SimpleProtocolInitializationErrorException {
        try {
            this.bufferedReader =
                    new BufferedReader(
                            new InputStreamReader(socket.getInputStream()));
            this.output = socket.getOutputStream();
        } catch (IOException e) {
            throw new SimpleProtocolInitializationErrorException();
        }
    }

    /**
     * Initiate SimpleProtocol.
     */


    @Override
    public String getCurrentLine() {
        return this.currentLine;
    }


    @Override
    public void putLine(String line) {
        PrintWriter printWriter =
                new PrintWriter(
                        new OutputStreamWriter(this.output));
        printWriter.println(line);
        printWriter.flush();
    }

    /**
     * Read from the Socket.
     * @throws SimpleProtocolFatalError
     */
    public void readLine() throws SimpleProtocolFatalError {
        try {
            this.currentLine = bufferedReader.readLine();

        } catch (IOException e) {
            setState(SimpleProtocolState.IDLE);
            throw new SimpleProtocolFatalError();
        }
    }


    @Override
    public void setState(State state) {
        this.state = state;
    }

    @Override
    public State getState() {
        return this.state;
    }


}
