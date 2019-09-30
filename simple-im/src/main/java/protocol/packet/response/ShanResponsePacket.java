package protocol.packet.response;

import lombok.Data;
import protocol.packet.Packet;

import static protocol.cmd.Command.SHAN_RESPONSE;

@Data
public class ShanResponsePacket extends Packet {
    private boolean success;
    private String reason;
    @Override
    public Byte getCommand() {
        return SHAN_RESPONSE;
    }
}
