package htw.vs1.filesystem.Network.Protocol.Commands;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

/**
 * Created by markus on 12.06.15.
 */

public abstract class AbstractCommand implements Command {

    /**
     * Builds a full command string composed of a command and
     * the parameters.<br>
     * Format: COMMAND PARAM_1 ... PARAM_N
     *
     * @param commandString
     * @param parameters
     * @return
     */
    protected String getCommandString(@NotNull String commandString, @Nullable String... parameters) {
        StringBuilder sb = new StringBuilder(commandString);
        if (parameters != null) {
            for (String parameter : parameters) {
                sb.append(" ");
                sb.append(parameter);
            }
        }

        return sb.toString();
    }

}
