package protocol.packet;

import java.util.List;

import static protocol.cmd.Command.ANNOUNCEMENT;

public class Announcement extends Packet {
    private List<String> userId;
    @Override
    public Byte getCommand() {
        return ANNOUNCEMENT;
    }
}
