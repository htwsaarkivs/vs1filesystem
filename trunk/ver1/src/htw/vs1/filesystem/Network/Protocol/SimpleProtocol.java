package htw.vs1.filesystem.Network.Protocol;


import htw.vs1.filesystem.FileSystem.virtual.FileSystemInterface;
import htw.vs1.filesystem.Main;
import htw.vs1.filesystem.Network.Protocol.Commands.CommandFactory;
import htw.vs1.filesystem.Network.Protocol.Exceptions.SimpleProtocolFatalError;
import htw.vs1.filesystem.Network.Protocol.Exceptions.SimpleProtocolInitializationErrorException;


import htw.vs1.filesystem.Network.Protocol.Exceptions.SimpleProtocolTerminateConnection;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCode200;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCode401;
import htw.vs1.filesystem.Network.Protocol.Replies.Reply;
import htw.vs1.filesystem.Network.Protocol.Replies.SimpleProtocolReply;
import htw.vs1.filesystem.Network.Protocol.Requests.RequestAnalyzer;
import htw.vs1.filesystem.Network.Protocol.State.SimpleProtocolState;
import htw.vs1.filesystem.Network.Protocol.State.State;

import java.io.*;
import java.net.Socket;

/**
 * Created by markus on 11.06.15.
 */
public class SimpleProtocol implements Protocol{

    private InputStream input;
    private OutputStream output;
    private Socket socket;
    private String currentLine;

    private FileSystemInterface fileSystem;
    /*
     * Der RequestStack hat im Protokoll (Klasse) nichts verloren, da er keine Eigenschaft des Protokolls darstellt.
     * Er ist viel mehr ein Hilfskonstrukt, welches es bestimmten Kommandos erlaubt auf vorherige Request zurückzugreifen.
      * Auch würde eine solche Konstruktion die Veränderung des Stacks aus Kommandos herraus erlauben, da im Protokoll zwangsläufig eine pushRequestStack-Methode vorhanden sein muss.
      * Durch Programmierfehler, könnte man so einfacher Lücken im Protokoll ausnutzen.
     */

    private State state = SimpleProtocolState.IDLE;

    /**
     * Creates a new SimpleProtocol instance.
     * @param socket Reference to a ServerSocket
     * @throws SimpleProtocolInitializationErrorException
     */
    public SimpleProtocol(Socket socket, FileSystemInterface fileSystem) throws SimpleProtocolInitializationErrorException {
        try {
            this.fileSystem = fileSystem;
            this.socket = socket;
            this.input = socket.getInputStream();
            this.output = socket.getOutputStream();
        } catch (IOException e) {
            throw new SimpleProtocolInitializationErrorException();
        }
    }

    /**
     * Initiate SimpleProtocol.
     */
    public void run() {
        RequestAnalyzer analyzer = new RequestAnalyzer(new CommandFactory());

        new SimpleProtocolReply(new ReplyCode200(Main.VERSION), null).putReply(this);
        this.setState(SimpleProtocolState.READY);

        while (true) {
            try {
                readLine();
                Reply reply = analyzer.parseCommand(this);
                reply.putReply(this);
                if (reply.terminatesConnection()) throw new SimpleProtocolTerminateConnection();
            } catch (Exception e) {
                putLine("Connection terminated: " + e.toString());
                break;
            }
        }

    }

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

    @Override
    public FileSystemInterface getFileSystem() {
        return this.fileSystem;
    }
}
