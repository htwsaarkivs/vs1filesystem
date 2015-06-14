package htw.vs1.filesystem.Network.Protocol;


import htw.vs1.filesystem.Network.Protocol.Commands.CommandFactory;
import htw.vs1.filesystem.Network.Protocol.Exceptions.SimpleProtocolFatalError;
import htw.vs1.filesystem.Network.Protocol.Exceptions.SimpleProtocolInitializationErrorException;

import htw.vs1.filesystem.Network.Protocol.Exceptions.SimpleProtocolNoMoreLinesAvailableException;
import htw.vs1.filesystem.Network.Protocol.Exceptions.SimpleProtocolTerminateConnection;
import htw.vs1.filesystem.Network.Protocol.Requests.Request;
import htw.vs1.filesystem.Network.Protocol.Requests.RequestAnalyzer;
import htw.vs1.filesystem.Network.Protocol.State.SimpleProtocolState;
import htw.vs1.filesystem.Network.Protocol.State.State;

import java.io.*;
import java.net.Socket;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * Created by markus on 11.06.15.
 */
public class SimpleProtocol implements Protocol{

    private InputStream input;
    private OutputStream output;
    private Socket socket;

    private String currentLine;

    /*
     * Der RequestStack hat im Protokoll (Klasse) nichts verloren, da er keine Eigenschaft des Protokolls darstellt.
     * Er ist viel mehr ein Hilfskonstrukt, welches es bestimmten Kommandos erlaubt auf vorherige Request zurückzugreifen.
      * Auch würde eine solche Konstruktion die Veränderung des Stacks aus Kommandos herraus erlauben, da im Protokoll zwangsläufig eine pushRequestStack-Methode vorhanden sein muss.
      * Durch Programmierfehler, könnte man so einfacher Lücken im Protokoll ausnutzen.
     */

    private State state = SimpleProtocolState.IDLE;


    public SimpleProtocol(Socket socket) throws SimpleProtocolInitializationErrorException {
        try {
            this.socket = socket;
            this.input = socket.getInputStream();
            this.output = socket.getOutputStream();
        } catch (IOException e) {
            throw new SimpleProtocolInitializationErrorException();
        }
    }

    public void run() {
        RequestAnalyzer analyzer = new RequestAnalyzer(new CommandFactory());

        this.putLine("200 SERVER READY");
        this.setState(SimpleProtocolState.READY);

        while (true) {
            try {
                readLine();
                analyzer.parseCommand(this);
            } catch (Exception e) {
                putLine("Connection terminated: " + e.toString());
                break;
            }
        }

    }

    public String getCurrentLine() {
        return this.currentLine;
    }



    public void putLine(String line) {
        PrintWriter printWriter =
                new PrintWriter(
                        new OutputStreamWriter(this.output));
        printWriter.println(line);
        printWriter.flush();
    }

    public void readLine() throws SimpleProtocolFatalError {
        try {
            BufferedReader bufferedReader =
                    new BufferedReader(
                            new InputStreamReader(input));

            this.currentLine = bufferedReader.readLine();

        } catch (IOException e) {
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
