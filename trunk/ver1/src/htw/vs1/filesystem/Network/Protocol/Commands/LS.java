package htw.vs1.filesystem.Network.Protocol.Commands;

import htw.vs1.filesystem.FileSystem.exceptions.FileSystemException;
import htw.vs1.filesystem.FileSystem.exceptions.FSRemoteException;
import htw.vs1.filesystem.FileSystem.virtual.FSObject;
import htw.vs1.filesystem.FileSystem.virtual.Folder;
import htw.vs1.filesystem.Network.Protocol.Client.ClientProtocol;
import htw.vs1.filesystem.Network.Protocol.Exceptions.SimpleProtocolFatalError;
import htw.vs1.filesystem.Network.Protocol.Exceptions.SimpleProtocolUnexpectedServerBehaviour;
import htw.vs1.filesystem.Network.Protocol.Replies.*;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCode;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCode210;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCode219;
import htw.vs1.filesystem.Network.Protocol.Replies.Codes.ReplyCode406;

import htw.vs1.filesystem.Network.Protocol.Requests.RequestList;
import htw.vs1.filesystem.Network.Protocol.Server.ServerProtocol;
import htw.vs1.filesystem.Network.Protocol.State.SimpleProtocolState;

import java.util.List;
import java.util.Objects;

/**
 * Created by markus on 13.06.15.
 */
public class LS extends AbstractCommand {

    public static String COMMAND_STRING = "LS";
    public static String FOLDER = "[FOLDER]";
    public static String FILE = "[FILE]";

    public ServerReply execute(ServerProtocol prot, RequestList requestList) {
        if(!prot.getState().equals(SimpleProtocolState.AUTHENTICATED))
            return new SimpleServerProtocolReply(new ReplyCode406(), this);


        List<FSObject> list;
        try {
            list = prot.getFileSystem().getWorkingDirectory().getContent();
        } catch (FileSystemException e) {
            return new SimpleServerProtocolReply(
                    e.getReplyCode(),
                    this
            );
        }

        StringBuilder buf = new StringBuilder();

        for (FSObject obj: list) {
            boolean isFolder = obj instanceof Folder;

            buf.append(obj.getName());
            if (isFolder) {
                buf.append("\t");
                buf.append(FOLDER);
            }
            else {
                buf.append("\t");
                buf.append(FILE);
            }
            buf.append("\n");
        }


        new SimpleServerProtocolReply(new ReplyCode210(), this).putReply(prot);
        prot.putLine(buf.toString());

        return new SimpleServerProtocolReply(new ReplyCode219(), this);
    }

    @Override
    public ClientReply invoke(ClientProtocol prot, String... parameters)
            throws FileSystemException
    {
        prot.putLine(COMMAND_STRING);
        ReplyCode reply = prot.analyzeReply();


        //Antwort analysieren. Nein IntelliJ ich will hier checken, ob es sich um eine korrekte Excpetion handelt.
        if (reply.getException() != null) {
            throw reply.getException();
        }

        //Start-Zeichen wird nicht empfangen
        if(reply.getCode() != ReplyCode210.CODE) {
            //Irgendetwas stimmt nicht... -> Abbruch
            throw new SimpleProtocolUnexpectedServerBehaviour();
        }

        SimpleClientProtocolReply result = new SimpleClientProtocolReply();
        return getReplyData(prot, result);
    }

    public static SimpleClientProtocolReply getReplyData(ClientProtocol prot, SimpleClientProtocolReply result) throws SimpleProtocolFatalError {
        boolean read = true;

        while(read) {
                prot.readLine();
                String currentLine = prot.getCurrentLine();
                if (currentLine != null && currentLine.length() >= 3
                        && Objects.equals(currentLine.substring(0, 3), String.valueOf(ReplyCode219.CODE))
                        ) {
                    read = false;
                } else {
                    result.feedLine(currentLine);
                }
        }
        return result;
    }
}
