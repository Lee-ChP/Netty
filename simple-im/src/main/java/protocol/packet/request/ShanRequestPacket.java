package protocol.packet.request;

import lombok.Data;
import protocol.packet.Packet;

import static protocol.cmd.Command.SHAN_REQUEST;

@Data
public class ShanRequestPacket extends Packet {
    private String targetId;
    @Override
    public Byte getCommand() {
        return SHAN_REQUEST;
    }
}
