package protocol.packet.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import protocol.packet.Packet;

import static protocol.cmd.Command.MESSAGE_RESPONSE;

@EqualsAndHashCode(callSuper = true)
@Data
public class MessageResponsePacket extends Packet {
    private String message;
    @Override
    public byte getCommand() {
        return MESSAGE_RESPONSE;
    }
}
