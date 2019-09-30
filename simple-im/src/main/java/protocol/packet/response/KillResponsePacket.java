package protocol.packet.response;

import lombok.Data;
import protocol.packet.Packet;

import static protocol.cmd.Command.KILL_RESPONSE;

@Data
public class KillResponsePacket extends Packet {
    private boolean success;
    private String reason;

    @Override
    public Byte getCommand() {
        return KILL_RESPONSE;
    }
}
