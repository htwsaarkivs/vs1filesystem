package htw.vs1.filesystem.Network.Discovery;


import htw.vs1.filesystem.FileSystemManger;

import java.util.Objects;
import java.util.UUID;

/**
 * Created by Felix on 04.10.2015.
 */
public class BroadcastPacket {

    private static final String MY_UUID = UUID.randomUUID().toString();

    private String uuid;
    private int port;

    private boolean valid = false;

    public BroadcastPacket(int port) {
        this.port = port;
        this.uuid = MY_UUID;
    }

    public BroadcastPacket(byte[] bytes) {
        String dataStr = (new String(bytes)).trim();
        String[] dataArr = dataStr.split("\\|");
        if (setPortFromString(dataArr[0])) {
            valid = true;
        }

        if (dataArr.length > 1) {
            uuid = dataArr[1];
        }
    }

    public boolean isValidAndNotFromMe() {
        return valid && !Objects.equals(MY_UUID, uuid);
    }

    public int getPort() {
        return port;
    }

    public byte[] getByteValue() {
        String dataStr = String.valueOf(port) + "|" + MY_UUID;

        return dataStr.getBytes();
    }

    private boolean setPortFromString(String portStr) {
        try {
            port = Integer.parseInt(portStr);
            return true;
        } catch (NumberFormatException e) {
            if (FileSystemManger.DEBUG) {
                e.printStackTrace();
            }
        }
        return false;
    }

}
