package protocol.packet.response;

import lombok.Data;
import protocol.packet.Packet;

import static protocol.cmd.Command.QUIT_GROUP_RESPONSE;

@Data
public class QuitGroupResponsePacket extends Packet {
    private boolean success;
    private String reason;
    @Override
    public Byte getCommand() {
        return QUIT_GROUP_RESPONSE;
    }
}
