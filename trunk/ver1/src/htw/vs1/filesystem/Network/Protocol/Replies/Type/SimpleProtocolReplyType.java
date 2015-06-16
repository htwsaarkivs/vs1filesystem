package htw.vs1.filesystem.Network.Protocol.Replies.Type;

/**
 * Created by markus on 16.06.15.
 */
public enum SimpleProtocolReplyType implements Type {
    INFORMATIVE,
    CONFIRMATION,
    PROMPT,
    ERROR,
    CRITICAL_ERROR
}
